package com.chung.server;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.chung.sosandcommunicate.LoginActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class PhotoUploadOrDownload {
	//private String url="http://192.168.23.1:80/0/index.php/Uploadpic/uploadpic/";//真机URL
	//private String url = "http://10.0.3.2:80/0/index.php/Uploadpic/uploadpic/";// Genymotion URL
	private static String url = LoginActivity.SAE_URL+"index.php/Uploadpic/uploadpic/";// SAE URL
	public static final String SAEHEADPICURL = "http://sosandcommunicate-public.stor.sinaapp.com/";
	
	private static String end = "\r\n";
	private static String twoHyphens = "--";
	private static String boundary = "*****";

//	protected int flag;
	
	
	/* 上传文件至服务器的方法 */
	public static boolean uploadFile(final String path) {

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
					/* 允许Input、Output，不使用Cache */
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					/* 设置传送的method=POST */
					conn.setRequestMethod("POST");
					/* setRequestProperty */
					conn.setRequestProperty("Connection", "Keep-Alive");
					conn.setRequestProperty("Charset", "UTF-8");
					conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

			          /* 设置DataOutputStream */
			          DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
			          ds.writeBytes(twoHyphens + boundary + end);
			          ds.writeBytes("Content-Disposition: form-data; "+ "name=\"headpic\";filename=\""+ path + "\"" + end);
			          ds.writeBytes(end);  
			          /* 取得文件的FileInputStream */
			          FileInputStream fStream =new FileInputStream(path);
			          /* 设置每次写入1024bytes */
			          int bufferSize =1024;
			          byte[] buffer =new byte[bufferSize];
			          int length =-1;
			          /* 从文件读取数据至缓冲区 */
			          while((length = fStream.read(buffer)) !=-1)
			          {
			            /* 将资料写入DataOutputStream中 */
			            ds.write(buffer, 0, length);
			          }
			          ds.writeBytes(end);
			          ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			          /* close streams */
			          fStream.close();
			          ds.flush();
			          /* 取得Response内容 */
			          InputStream is = conn.getInputStream();
			          int ch;
			          StringBuffer b =new StringBuffer();
			          while( ( ch = is.read() ) !=-1 )
			          {
			            b.append( (char)ch );
			          }
			          /* 将Response显示于Dialog */
			          Log.i("UploadHeadPic", "上传成功"+b.toString().trim());
			          //showDialog("上传成功"+b.toString().trim());
			          /* 关闭DataOutputStream */
			          ds.close();
//			          flag = 1;
				} catch (Exception e) {
					Log.i("UploadHeadPic", "上传失败" + e);
//					flag = 0;
				}
			}
		});
		thread1.start();
		
		return thread1.isAlive()?false:true;
	}

	
	/**
	 * 从SAE获取对应studentID的HeadPic
	 */
	public static Bitmap getHeadPic(String stuID){
		Bitmap cacheBitmap = null;
		String imageUrl = SAEHEADPICURL + stuID + ".png";
		Log.i("imageUrl!!!!-----", imageUrl);
        //httpGet连接对象  
        HttpGet httpRequest = new HttpGet(imageUrl);  
        //取得HttpClient 对象  
        HttpClient httpclient = new DefaultHttpClient();
		try {
	        //请求httpClient ，取得HttpRestponse  
	        HttpResponse httpResponse = httpclient.execute(httpRequest);
	        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	             HttpEntity httpEntity = httpResponse.getEntity();  //取得相关信息 取得HttpEntiy
	             InputStream is = httpEntity.getContent();  //获得一个输入流 
	             cacheBitmap = BitmapFactory.decodeStream(is);  
	             is.close();
	             Log.i("getHeadPic", "return--not--null");
	        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cacheBitmap;
	}
	
}