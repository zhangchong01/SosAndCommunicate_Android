package com.chung.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chung.javabean.Comments;
import com.chung.javabean.Helpers;
import com.chung.javabean.SettingsDetails;
import com.chung.javabean.Blog;
import com.chung.javabean.User;
import com.chung.sosandcommunicate.LoginActivity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 获取并解析后台Json
 */
public class GetAndParseJson {
	
	public static final int GETSERVERERROR = 10;
	public static final int GETINTERNETERROR = 11;
	public static final int LOGIN = 12;
	public static final int BLOG = 13;
	public static final int SETTINGS = 14;
	public static final int BLOGDETAILS = 15;
	public static final int COMMENTS = 16;
	public static final int HELPERS = 17;
	private Handler handler;
	private String url;
	private int javaBeanType;
	private String studentID;
	
	/**
	 * 构造函数
	 */
	public GetAndParseJson(Handler handler, String url, String arg1, String arg2, int javaBeanType) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		if (arg1.isEmpty()) {
			this.url = url;
		}
		if (!arg1.isEmpty() && arg2.isEmpty()) {
			this.studentID = arg1;
			this.url = url + "?0=" + arg1;
		}
		if (!arg1.isEmpty() && !arg2.isEmpty()) {
			this.studentID = arg1;
			this.url = url + "?0=" + arg1 + "&1=" + arg2;
		}
		this.javaBeanType = javaBeanType;
	}

	/**
	 * 获取后台的Json
	 */
	public void getJsonFromInternet() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
					conn.setConnectTimeout(3000);
					conn.setRequestMethod("GET");
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						InputStream inputStream = conn.getInputStream();
						List<Object> lists = parseJson(inputStream);
						if (lists.size() > 0) {
							Message msg = new Message();
							if (javaBeanType == 1) {
								msg.what = LOGIN; // 通知UI线程Json解析完成
						        Bitmap bm = PhotoUploadOrDownload.getHeadPic(studentID);
						        if(bm != null){
									File loginHeadPIC = new File(LoginActivity.loginContext.getFilesDir(),studentID + ".png");
									try {
										FileOutputStream out = new FileOutputStream(loginHeadPIC);
										bm.compress(Bitmap.CompressFormat.PNG, 100, out);
										out.flush();
										out.close();
										Log.i("loginHeadPIC", "download from sae succeed!");
										Log.i("loginHeadPIC--Path", loginHeadPIC.getPath());
									} catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										Log.i("loginHeadPIC", "download from sae FAILED1!");
										e1.printStackTrace();
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										Log.i("loginHeadPIC", "download from sae FAILED2!");
										e2.printStackTrace();
									}
								}else {
									Log.i("SAE HeadPic!!!!!!!", "!!!!!NOT EXIST!!!!!!");
								}
							}
							if (javaBeanType == 2) {
								msg.what = BLOG; // 通知UI线程Json解析完成
							}
							if (javaBeanType == 3) {
								msg.what = SETTINGS;// 通知UI线程Json解析完成
							}
							if(javaBeanType == 4){
								msg.what = BLOGDETAILS;// 通知UI线程Json解析完成
							}
							if(javaBeanType == 5){
								msg.what = COMMENTS;// 通知UI线程Json解析完成
							}
							if(javaBeanType == 6){
								msg.what = HELPERS;// 通知UI线程Json解析完成
							}
							msg.obj = lists;// 将解析出的数据传递给UI线程
							handler.sendMessage(msg);
							Log.i("JasonPrase", "succeed");
						}
					} else {
						Message msg = new Message();
						msg.what = GETINTERNETERROR;// 通知UI线程本机网络错误
						handler.sendMessage(msg);
						Log.i("GET INTERNET", "ERROR");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Message msg = new Message();
					msg.what = GETSERVERERROR;// 通知UI线程服务器网络错误
					handler.sendMessage(msg);
					Log.i("JsonObtain", "error");
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 解析获取的Json
	 */
	protected List<Object> parseJson(InputStream inputStream) {
		// TODO Auto-generated method stub
		List<Object> lists = new ArrayList<Object>();

		try {
			byte[] jsonBytes = convertIsToByteArray(inputStream);
			String json = new String(jsonBytes);

			switch (javaBeanType) {
			case 1:// User
				JSONObject userJsonObject = new JSONObject(json);
				User user = new User();
				if(userJsonObject.getString("code").equals("true")){
					user.setCode(userJsonObject.getString("code"));
					user.setStudentID(userJsonObject.getString("studentID"));
					user.setPassword(userJsonObject.getString("password"));
					user.setHeadPic(userJsonObject.getString("headPic"));
					user.setName(userJsonObject.getString("name"));
					user.setSex(userJsonObject.getString("sex"));
					user.setPhone(userJsonObject.getString("phone"));
					user.setUniversity(userJsonObject.getString("university"));
					user.setCollege(userJsonObject.getString("college"));
					user.setMajor(userJsonObject.getString("major"));
					user.setSign(userJsonObject.getString("sign"));
					user.setHelpTimes(userJsonObject.getString("helpTimes"));
					user.setAnswerTimes(userJsonObject.getString("answerTimes"));
					user.setSavedGoals(userJsonObject.getString("savedGoals"));
					user.setGoalsRank(userJsonObject.getString("goalsRank"));
					user.setTbnight(userJsonObject.getInt("tbnight"));
				}else{
					user.setCode(userJsonObject.getString("code"));
				}
				lists.add(user);
				break;
			case 2:// Blog
				int index2 = 0;
				JSONArray blogsJsonArry = new JSONArray(json);
				while (!blogsJsonArry.isNull(index2)) {
					Blog blog = new Blog();
					
					File cacheHeadPIC1 = new File(LoginActivity.loginContext.getCacheDir(), blogsJsonArry.getJSONObject(index2).getString("studentID") + ".png");
					if(!cacheHeadPIC1.canRead()){
						Log.i("cacheHeadPIC","can  not  Read  !!");
						try {
							FileOutputStream out = new FileOutputStream(cacheHeadPIC1);
							PhotoUploadOrDownload.getHeadPic(blogsJsonArry.getJSONObject(index2).getString("studentID")).compress(Bitmap.CompressFormat.PNG, 100, out);
							out.flush();
							out.close();
							Log.i("cacheHeadPIC1", "download from sae succeed!");
							Log.i("cacheHeadPIC1--Path", cacheHeadPIC1.getPath());
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							Log.i("cacheHeadPIC1", "download from sae FAILED1!");
							e1.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							Log.i("cacheHeadPIC1", "download from sae FAILED2!");
							e2.printStackTrace();
						}
					}
					blog.setHeadPic(cacheHeadPIC1.getPath());
					blog.setStudentID(blogsJsonArry.getJSONObject(index2).getString("studentID"));
					blog.setTheme(blogsJsonArry.getJSONObject(index2).getString("theme"));
					blog.setBlogTime(blogsJsonArry.getJSONObject(index2).getString("blogTime").substring(0, 10));
					blog.setBlogType(blogsJsonArry.getJSONObject(index2).getString("blogType"));

					if (blogsJsonArry.getJSONObject(index2).getString("blogType").equals("1")) {// 求助帖
						blog.setBlogID(blogsJsonArry.getJSONObject(index2).getInt("sosBlogID"));
						blog.setDate(blogsJsonArry.getJSONObject(index2).getString("time"));
						blog.setAddr(blogsJsonArry.getJSONObject(index2).getString("addr"));
						blog.setPhone(blogsJsonArry.getJSONObject(index2).getString("phone"));
					}

					if (blogsJsonArry.getJSONObject(index2).getString("blogType").equals("0")) {// 交流贴
						blog.setBlogID(blogsJsonArry.getJSONObject(index2).getInt("communicateBlogID"));
						blog.setQuestion(blogsJsonArry.getJSONObject(index2).getString("question"));
					}

					blog.setBlogDetails(blogsJsonArry.getJSONObject(index2).getString("blogDetails"));

					lists.add(blog);
					index2++;
				}
				break;
			case 3:// Settings
				JSONObject settingsJsonObject = new JSONObject(json);
				SettingsDetails settingsDetails = new SettingsDetails();

				settingsDetails.setTb11(settingsJsonObject.getInt("tb11"));
				settingsDetails.setTb12(settingsJsonObject.getInt("tb12"));
				settingsDetails.setTb13(settingsJsonObject.getInt("tb13"));
				settingsDetails.setTb14(settingsJsonObject.getInt("tb14"));
				settingsDetails.setTb15(settingsJsonObject.getInt("tb15"));
				settingsDetails.setTb16(settingsJsonObject.getInt("tb16"));
				settingsDetails.setTb21(settingsJsonObject.getInt("tb21"));
				settingsDetails.setTb22(settingsJsonObject.getInt("tb22"));
				settingsDetails.setTb23(settingsJsonObject.getInt("tb23"));
				settingsDetails.setTb24(settingsJsonObject.getInt("tb24"));
				settingsDetails.setTb25(settingsJsonObject.getInt("tb25"));
				settingsDetails.setTb31(settingsJsonObject.getInt("tb31"));
				settingsDetails.setTb32(settingsJsonObject.getInt("tb32"));
				settingsDetails.setTb33(settingsJsonObject.getInt("tb33"));
				settingsDetails.setTb34(settingsJsonObject.getInt("tb34"));
				settingsDetails.setTb35(settingsJsonObject.getInt("tb35"));
				settingsDetails.setTb41(settingsJsonObject.getInt("tb41"));
				settingsDetails.setTb42(settingsJsonObject.getInt("tb42"));
				settingsDetails.setTb43(settingsJsonObject.getInt("tb43"));
				settingsDetails.setTb44(settingsJsonObject.getInt("tb44"));
				settingsDetails.setTb45(settingsJsonObject.getInt("tb45"));
				settingsDetails.setTb46(settingsJsonObject.getInt("tb46"));
				settingsDetails.setTb51(settingsJsonObject.getInt("tb51"));
				settingsDetails.setTb52(settingsJsonObject.getInt("tb52"));
				settingsDetails.setTb53(settingsJsonObject.getInt("tb53"));
				settingsDetails.setTb54(settingsJsonObject.getInt("tb54"));
				settingsDetails.setTb55(settingsJsonObject.getInt("tb55"));
				settingsDetails.setTb56(settingsJsonObject.getInt("tb56"));
				settingsDetails.setTb57(settingsJsonObject.getInt("tb57"));

				lists.add(settingsDetails);
				break;
			case 4:// BlogDetails
				JSONObject blogJsonObject = new JSONObject(json);
				Blog blog = new Blog();
				blog.setName(blogJsonObject.getString("name"));
				blog.setStudentID(blogJsonObject.getString("studentID"));
				blog.setTheme(blogJsonObject.getString("theme"));
				blog.setBlogTime(blogJsonObject.getString("blogTime").substring(0, 10));
				blog.setBlogType(blogJsonObject.getString("blogType"));
				blog.setGoalsRank(blogJsonObject.getString("goalsRank"));
				
				if (blogJsonObject.getString("blogType").equals("1")) {// 求助帖
					blog.setBlogID(blogJsonObject.getInt("sosBlogID"));
					blog.setDate(blogJsonObject.getString("time"));
					blog.setAddr(blogJsonObject.getString("addr"));
					blog.setPhone(blogJsonObject.getString("phone"));
				}

				if (blogJsonObject.getString("blogType").equals("0")) {// 交流贴
					blog.setBlogID(blogJsonObject.getInt("communicateBlogID"));
					blog.setQuestion(blogJsonObject.getString("question"));
				}

				blog.setBlogDetails(blogJsonObject.getString("blogDetails"));

				lists.add(blog);
				break;
			case 5:// Comments
				int index5 = 0;
				JSONArray commentsJsonArry = new JSONArray(json);
				while (!commentsJsonArry.isNull(index5)) {
					Comments comments = new Comments();
					
					File cacheHeadPIC2 = new File(LoginActivity.loginContext.getCacheDir(), commentsJsonArry.getJSONObject(index5).getString("studentID") + ".png");
					if(!cacheHeadPIC2.canRead()){
						Log.i("cacheHeadPIC","can  not  Read  !!");
						try {
							FileOutputStream out = new FileOutputStream(cacheHeadPIC2);
							PhotoUploadOrDownload.getHeadPic(commentsJsonArry.getJSONObject(index5).getString("studentID")).compress(Bitmap.CompressFormat.PNG, 100, out);
							out.flush();
							out.close();
							Log.i("cacheHeadPIC2", "download from sae succeed!");
							Log.i("cacheHeadPIC2--Path", cacheHeadPIC2.getPath());
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							Log.i("cacheHeadPIC2", "download from sae FAILED1!");
							e1.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							Log.i("cacheHeadPIC2", "download from sae FAILED2!");
							e2.printStackTrace();
						}
					}
					comments.setCommentPic(cacheHeadPIC2.getPath());
					
					comments.setCommentID(commentsJsonArry.getJSONObject(index5).getInt("commentID"));
					comments.setStudentID(commentsJsonArry.getJSONObject(index5).getString("studentID"));
					comments.setCommunicateBlogID(commentsJsonArry.getJSONObject(index5).getString("communicateBlogID"));
					comments.setCommentFloor(commentsJsonArry.getJSONObject(index5).getString("commentFloor"));
					comments.setCommentDetails(commentsJsonArry.getJSONObject(index5).getString("commentDetails"));
					comments.setCommentTime(commentsJsonArry.getJSONObject(index5).getString("commentTime"));
					comments.setCommentLike(commentsJsonArry.getJSONObject(index5).getString("commentLike"));
					Log.i("index5="+String.valueOf(index5), "commentID" + commentsJsonArry.getJSONObject(index5).getString("commentID"));
					Log.i("commentDetails", commentsJsonArry.getJSONObject(index5).getString("commentDetails"));
					lists.add(comments);
					index5++;
				}
				break;
			case 6:// Helpers
				int index6 = 0;
				JSONArray helpersJsonArry = new JSONArray(json);
				while (!helpersJsonArry.isNull(index6)) {
					Helpers helpers = new Helpers();
					
					File cacheHeadPIC3 = new File(LoginActivity.loginContext.getCacheDir(), helpersJsonArry.getJSONObject(index6).getString("studentID") + ".png");
					if(!cacheHeadPIC3.canRead()){
						Log.i("cacheHeadPIC","can  not  Read  !!");
						try {
							FileOutputStream out = new FileOutputStream(cacheHeadPIC3);
							PhotoUploadOrDownload.getHeadPic(helpersJsonArry.getJSONObject(index6).getString("studentID")).compress(Bitmap.CompressFormat.PNG, 100, out);
							out.flush();
							out.close();
							Log.i("cacheHeadPIC3", "download from sae succeed!");
							Log.i("cacheHeadPIC3--Path", cacheHeadPIC3.getPath());
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							Log.i("cacheHeadPIC3", "download from sae FAILED1!");
							e1.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							Log.i("cacheHeadPIC3", "download from sae FAILED2!");
							e2.printStackTrace();
						}
					}
					helpers.setHelperPic(cacheHeadPIC3.getPath());
					
					helpers.setHelperID(helpersJsonArry.getJSONObject(index6).getInt("helperID"));
					helpers.setStudentID(helpersJsonArry.getJSONObject(index6).getString("studentID"));
					helpers.setSosBlogID(helpersJsonArry.getJSONObject(index6).getString("sosBlogID"));
					helpers.setHelperTime(helpersJsonArry.getJSONObject(index6).getString("helperTime"));
					helpers.setHelperAccepted(helpersJsonArry.getJSONObject(index6).getString("helperAccepted"));
					Log.i("index5="+String.valueOf(index6), "helperID = " + helpersJsonArry.getJSONObject(index6).getString("helperID"));
					Log.i("helper studentID",helpersJsonArry.getJSONObject(index6).getString("studentID"));
					lists.add(helpers);
					index6++;
				}
				break;
			default:
				break;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lists;
	}

	/**
	 * 将输入流转化成ByteArray
	 */
	private byte[] convertIsToByteArray(InputStream inputStream) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte buffer[] = new byte[1024];
		int length = 0;
		try {
			while ((length = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			inputStream.close();
			baos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	
	

}
