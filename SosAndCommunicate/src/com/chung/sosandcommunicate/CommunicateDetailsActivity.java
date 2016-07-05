package com.chung.sosandcommunicate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chung.sosandcommunicate.R;
import com.chung.javabean.Blog;
import com.chung.javabean.Comments;
import com.chung.server.GetAndParseJson;
import com.chung.server.SendAndStoreData;
import com.chung.tools.LvAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class CommunicateDetailsActivity extends Activity {
	// String url1="http://192.168.23.1:80/0/index.php/Blogdetails/communicateBlogDetails/";//真机URL
	// String url1="http://10.0.3.2:80/0/index.php/Blogdetails/communicateBlogDetails/";//Genymotion URL
	String url1 = LoginActivity.SAE_URL + "index.php/Blogdetails/communicateBlogDetails/";// SAE URL

	// String url2="http://192.168.23.1:80/0/index.php/Comments/comments/";//真机URL
	// String url2="http://10.0.3.2:80/0/index.php/Comments/comments/";//Genymotion URL
	String url2 = LoginActivity.SAE_URL + "index.php/Comments/comments/";// SAE URL

	// String url3="http://192.168.23.1:80/0/index.php/Helporcomment/comment/";//真机URL
	// String url3="http://10.0.3.2:80/0/index.php/Helporcomment/comment/";//Genymotion URL
	String url3= LoginActivity.SAE_URL + "index.php/Helporcomment/comment/";// SAE URL
	
	String url4= LoginActivity.SAE_URL + "index.php/Push/push/";// SAE URL
	
	public static final int COMMENTMSG = 120;
	
	private ProgressDialog progressDialog;
	private LvAdapter helperAdapter;
	private ListView listView;
//	private SimpleAdapter listItemAdapter;
	
	private ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
	private HashMap<String, Object> map;

	private String communicateBlogID;
	private String studentID;

	private ScrollView sv_communicate_details;
	private ImageView iv_communicate_details_headpic;
	private TextView tv_communicate_details_username;
	private TextView tv_communicate_details_time;
	private TextView tv_communicate_details_goalsrank;
	private TextView tv_communicate_details_nickname;
	private TextView tv_communicate_details_theme;
	private TextView tv_communicate_details_question;
	private TextView tv_communicate_details_more;
	
	private EditText et_communicate_details_comment;
	
	private SharedPreferences sharedPreferences;
	private String[] args = new String[4];
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
//			case SendAndStoreData.DATAUPLOADSUCCEED:
//				listView.invalidateViews();
//				break;
			case GetAndParseJson.BLOGDETAILS:
				Blog blog = ((List<Blog>) msg.obj).get(0);
				tv_communicate_details_time.setText(blog.getBlogTime());
				tv_communicate_details_goalsrank.setText(blog.getGoalsRank());
				tv_communicate_details_nickname.setText(blog.getName());
				tv_communicate_details_theme.setText(blog.getTheme());
				tv_communicate_details_question.setText(blog.getQuestion());
				tv_communicate_details_more.setText(blog.getBlogDetails());
				break;
			case GetAndParseJson.COMMENTS:
				List<Comments> comments = (List<Comments>) msg.obj;
				if (!comments.isEmpty()) {
					try {
						listitem.clear();// 每次进入界面刷新
						//Log.i("comments", "cleared");
						for (int flag = 0; flag < comments.size(); flag++) {
							map = new HashMap<String, Object>();
							map.put("commentID", comments.get(flag).getCommentID());
							map.put("img", comments.get(flag).getCommentPic());
							map.put("username", comments.get(flag).getStudentID());
							map.put("floor", comments.get(flag).getCommentFloor());
							map.put("time", comments.get(flag).getCommentTime());
							map.put("like", comments.get(flag).getCommentLike());
							map.put("info", "　　"+ comments.get(flag).getCommentDetails());
							Log.i("CommentDetails", comments.get(flag).getCommentDetails());
							listitem.add(map);
						}
						
						helperAdapter = new LvAdapter(listitem, CommunicateDetailsActivity.this, 2);
						listView.setAdapter(helperAdapter);
						setListViewHeightBasedOnChildren(listView);
						progressDialog.dismiss();
						Log.i("commentAdapter", "seted");
						Log.i("comments", "refeshed");
					} catch (Exception e) {
						Log.i("downloadComments", "failed");
						e.printStackTrace();
					}
				} else {
					Toast.makeText(CommunicateDetailsActivity.this, "暂无评论",Toast.LENGTH_SHORT).show();
					Log.i("downloadComments", "null");
				}
				break;
			case GetAndParseJson.GETINTERNETERROR:
				Toast toast1 = Toast.makeText(CommunicateDetailsActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.cry);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
				break;
			case GetAndParseJson.GETSERVERERROR:
				Toast toast2 = Toast.makeText(CommunicateDetailsActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
				toast2.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView2 = (LinearLayout) toast2.getView();
				ImageView imageCodeProject2 = new ImageView(getApplicationContext());
				imageCodeProject2.setImageResource(R.drawable.cry);
				toastView2.addView(imageCodeProject2, 0);
				toast2.show();
				break;
			default:
				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_communicate_details);

		sharedPreferences = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
		
		Intent intent = getIntent();
		studentID = intent.getStringExtra("studentID");
		communicateBlogID = intent.getStringExtra("communicateBlogID");
		
		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("交流帖详情");
		
		sv_communicate_details = (ScrollView) findViewById(R.id.sv_communicate_details);
		
		iv_communicate_details_headpic = (ImageView) findViewById(R.id.iv_communicate_details_headpic);
		File headPIC = new File(CommunicateDetailsActivity.this.getCacheDir(), studentID + ".png");
		if(headPIC.canRead()){
			iv_communicate_details_headpic.setImageBitmap(BitmapFactory.decodeFile(headPIC.getPath()));
		}else {
			iv_communicate_details_headpic.setImageResource(R.drawable.user);
		}
		tv_communicate_details_username = (TextView) findViewById(R.id.tv_communicate_details_username);
		tv_communicate_details_username.setText(studentID);
		tv_communicate_details_time = (TextView) findViewById(R.id.tv_communicate_details_time);
		tv_communicate_details_goalsrank = (TextView) findViewById(R.id.tv_communicate_details_goalsrank);
		
		tv_communicate_details_nickname = (TextView) findViewById(R.id.tv_communicate_details_nickname);
		tv_communicate_details_theme = (TextView) findViewById(R.id.tv_communicate_details_theme);
		tv_communicate_details_question = (TextView) findViewById(R.id.tv_communicate_details_question);
		tv_communicate_details_more = (TextView) findViewById(R.id.tv_communicate_details_more);

		et_communicate_details_comment = (EditText) findViewById(R.id.et_communicate_details_comment);
		
		listView = (ListView) findViewById(R.id.commentLV);
		
		// 添加单击监听
		// commentLV.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		//
		//
		// }
		// });
		
		init();

	}

	private void init(){
		GetAndParseJson getAndParseJson1 = new GetAndParseJson(handler, url1, studentID, communicateBlogID, 4);
		getAndParseJson1.getJsonFromInternet();

		GetAndParseJson getAndParseJson2 = new GetAndParseJson(handler, url2, communicateBlogID, "", 5);
		getAndParseJson2.getJsonFromInternet();
		
		progressDialog = new ProgressDialog(CommunicateDetailsActivity.this);
		progressDialog.setMessage("加载详情中...");
		progressDialog.show();
	}
	
	@SuppressLint("SimpleDateFormat")
	public void answerta(View v) {
		args[0] = sharedPreferences.getString("studentID", null);
		args[1] = communicateBlogID;
		args[2] = "0";
		args[3] = et_communicate_details_comment.getText().toString();
		
		SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, url3, args, 4);
		sendAndStoreData.saveDataToInternet();
		
		/*JPush*/
		String argsPush[] = new String[2];
		argsPush[0] = sharedPreferences.getString("studentID", null);
		argsPush[1] = et_communicate_details_comment.getText().toString();
		SendAndStoreData sendAndStoreDataPush = new SendAndStoreData(handler, url4, argsPush, 2);
		sendAndStoreDataPush.saveDataToInternet();
		/*JPush*/
		
		et_communicate_details_comment.setText("");
		
		 InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);//得到InputMethodManager的实例 
		 if (imm.isActive()) { //如果开启 
			 imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS); //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的 
		 } 
		 
		Toast.makeText(CommunicateDetailsActivity.this, "已提交评论", Toast.LENGTH_SHORT).show();
		
		File headPIC = new File(CommunicateDetailsActivity.this.getFilesDir(),args[0] + ".png");	
		HashMap<String, Object> cacheMap = new HashMap<String, Object>();//map
		cacheMap.put("img", headPIC.getPath());
		cacheMap.put("username", args[0]);
		cacheMap.put("floor", "0");
		cacheMap.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		cacheMap.put("like", "0");
		cacheMap.put("info", "　　"+ args[3]);
		listitem.add(cacheMap);

		LvAdapter cacheAdapter = new LvAdapter(listitem, CommunicateDetailsActivity.this, 2);
		listView.setAdapter(cacheAdapter);
		setListViewHeightBasedOnChildren(listView);
		cacheAdapter.notifyDataSetChanged();
		
		GetAndParseJson getAndParseJson3 = new GetAndParseJson(handler, url2, communicateBlogID, "", 5);
		getAndParseJson3.getJsonFromInternet();
		
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub				
				sv_communicate_details.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0;i < listAdapter.getCount(); i++) {// listAdapter.getCount()返回数据项的数目
			
			View listItem = listAdapter.getView(i, null, listView);
			
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();// + info.getMeasuredHeight();
			Log.i("MeasuredHeight of " + String.valueOf(i), String.valueOf(listItem.getMeasuredHeight()));
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;// + communicate_details_bottom.getMeasuredHeight();
		// listView.getDividerHeight()获取子项间分隔符占用的高度 
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
	
}
