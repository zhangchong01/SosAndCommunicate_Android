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
import com.chung.javabean.Helpers;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SosDetailsActivity extends Activity {
	//String url1="http://192.168.23.1:80/0/index.php/Blogdetails/sosBlogDetails/";//真机URL
	//String url1="http://10.0.3.2:80/0/index.php/Blogdetails/sosBlogDetails/";//Genymotion URL
	String url1=LoginActivity.SAE_URL+"index.php/Blogdetails/sosBlogDetails/";//SAE URL
	
	// String url2="http://192.168.23.1:80/0/index.php/Helpers/helpers/";//真机URL
	// String url2="http://10.0.3.2:80/0/index.php/Helpers/helpers/";//Genymotion URL
	String url2 = LoginActivity.SAE_URL + "index.php/Helpers/helpers/";// SAE URL
	
	// String url3="http://192.168.23.1:80/0/index.php/Helporcomment/help/";//真机URL
	// String url3="http://10.0.3.2:80/0/index.php/Helporcomment/help/";//Genymotion URL
	String url3= LoginActivity.SAE_URL + "index.php/Helporcomment/help/";// SAE URL
	
	String url4= LoginActivity.SAE_URL + "index.php/Push/push/";// SAE URL
	
	private ProgressDialog progressDialog;
	private LvAdapter helperAdapter;
	private ListView listView;
//	private SimpleAdapter listItemAdapter;
	
	private ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
	private HashMap<String, Object> map;

	private String sosBlogID;
	private String studentID;
	
	private ScrollView sv_sos_details;
	private ImageView iv_sos_details_headpic;
	private TextView tv_sos_details_username;
	private TextView tv_sos_details_time;
	private TextView tv_sos_details_goalsrank;
	private TextView tv_sos_details_nickname;
	private TextView tv_sos_details_theme;
	private TextView tv_sos_details_date;
	private TextView tv_sos_details_addr;
	private TextView tv_sos_details_phone;
	private TextView tv_sos_details_more;
//	private TextView helper_accepted;
	
	private SharedPreferences sharedPreferences;
	private String[] args = new String[2];
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
//			case SendAndStoreData.DATAUPLOADSUCCEED:
//				listView.invalidateViews();
//				break;
			case GetAndParseJson.BLOGDETAILS:
				@SuppressWarnings("unchecked")
				Blog blog = ((List<Blog>)msg.obj).get(0);
				tv_sos_details_time.setText(blog.getBlogTime());
				tv_sos_details_goalsrank.setText(blog.getGoalsRank());
				tv_sos_details_nickname.setText(blog.getName());
				tv_sos_details_theme.setText(blog.getTheme());
				tv_sos_details_date.setText(blog.getDate());
				tv_sos_details_addr.setText(blog.getAddr());
				tv_sos_details_phone.setText(blog.getPhone());
				tv_sos_details_more.setText(blog.getBlogDetails());
				break;
			case GetAndParseJson.HELPERS:
				@SuppressWarnings("unchecked")
				List<Helpers> helpers = (List<Helpers>) msg.obj;
				if (!helpers.isEmpty()) {
					try {
						listitem.clear();// 每次进入界面刷新
						//Log.i("helpers", "cleared");
						for (int flag = 0; flag < helpers.size(); flag++) {
							map = new HashMap<String, Object>();
							map.put("img", helpers.get(flag).getHelperPic());
							map.put("username", helpers.get(flag).getStudentID());
							map.put("time", helpers.get(flag).getHelperTime());
							map.put("accepted", helpers.get(flag).getHelperAccepted());
							Log.i("helperUsername", helpers.get(flag).getStudentID());
							listitem.add(map);
						}

						helperAdapter = new LvAdapter(listitem, SosDetailsActivity.this, 1);
						listView.setAdapter(helperAdapter);
						setListViewHeightBasedOnChildren(listView);
						progressDialog.dismiss();
						Log.i("helperAdapter", "seted");
						Log.i("helpers", "refeshed");
					} catch (Exception e) {
						Log.i("downloadHelpers", "failed");
						e.printStackTrace();
					}
				} else {
					Toast.makeText(SosDetailsActivity.this, "暂无评论",Toast.LENGTH_SHORT).show();
					Log.i("downloadHelpers", "null");
				}
				break;
			case GetAndParseJson.GETINTERNETERROR:
				Toast toast1 = Toast.makeText(SosDetailsActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.cry);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
				break;
			case GetAndParseJson.GETSERVERERROR:
				Toast toast2 = Toast.makeText(SosDetailsActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
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
		setContentView(R.layout.activity_sos_details);

		sharedPreferences = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
		
		Intent intent = getIntent();
		studentID = intent.getStringExtra("studentID");
		sosBlogID = intent.getStringExtra("sosBlogID");
		
		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("求助帖详情");
		
		sv_sos_details = (ScrollView) findViewById(R.id.sv_sos_details);
		
		iv_sos_details_headpic = (ImageView) findViewById(R.id.iv_sos_details_headpic);
		File headPIC = new File(SosDetailsActivity.this.getCacheDir(), studentID + ".png");
		if(headPIC.canRead()){
			iv_sos_details_headpic.setImageBitmap(BitmapFactory.decodeFile(headPIC.getPath()));
		}else {
			iv_sos_details_headpic.setImageResource(R.drawable.user);
		}
		tv_sos_details_username = (TextView) findViewById(R.id.tv_sos_details_username);
		tv_sos_details_username.setText(studentID);
		tv_sos_details_time = (TextView) findViewById(R.id.tv_sos_details_time);
		tv_sos_details_goalsrank = (TextView) findViewById(R.id.tv_sos_details_goalsrank);


		tv_sos_details_nickname = (TextView) findViewById(R.id.tv_sos_details_nickname);
		tv_sos_details_theme = (TextView) findViewById(R.id.tv_sos_details_theme);
		tv_sos_details_date = (TextView) findViewById(R.id.tv_sos_details_date);
		tv_sos_details_addr = (TextView) findViewById(R.id.tv_sos_details_addr);
		tv_sos_details_phone = (TextView) findViewById(R.id.tv_sos_details_phone);
		tv_sos_details_more = (TextView) findViewById(R.id.tv_sos_details_more);
		
		//sos_details_bottom = (LinearLayout) findViewById(R.id.sos_details_bottom);
		
		listView = (ListView) findViewById(R.id.helperLV);
        
		// 添加单击监听
		// commentLV.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//
		//
		// }
		// });
		
		GetAndParseJson getAndParseJson1 = new GetAndParseJson(handler, url1, studentID, sosBlogID, 4);
		getAndParseJson1.getJsonFromInternet();

		GetAndParseJson getAndParseJson2 = new GetAndParseJson(handler, url2, sosBlogID, "", 6);
		getAndParseJson2.getJsonFromInternet();
		
		progressDialog = new ProgressDialog(SosDetailsActivity.this);
		progressDialog.setMessage("加载详情中...");
		progressDialog.show();
		
	}


	@SuppressLint("SimpleDateFormat")
	public void helpta(View v) {
		
		args[0] = sharedPreferences.getString("studentID", null);
		args[1] = sosBlogID;
		
		SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, url3, args, 2);
		sendAndStoreData.saveDataToInternet();
		
		/*JPush*/
		String argsPush[] = new String[2];
		argsPush[0] = sharedPreferences.getString("studentID", null);
		argsPush[1] = "愿意帮助您,请您及时回复!";
		SendAndStoreData sendAndStoreDataPush = new SendAndStoreData(handler, url4, argsPush, 2);
		sendAndStoreDataPush.saveDataToInternet();
		/*JPush*/
		
		Toast.makeText(SosDetailsActivity.this, "已提交申请帮助TA", Toast.LENGTH_SHORT).show();
		
		File headPIC = new File(SosDetailsActivity.this.getFilesDir(),args[0] + ".png");	
		HashMap<String, Object> cacheMap = new HashMap<String, Object>();
		cacheMap.put("img", headPIC.getPath());
		cacheMap.put("username", args[0]);
		cacheMap.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		cacheMap.put("accepted", "0");
		listitem.add(cacheMap);

		LvAdapter cacheAdapter = new LvAdapter(listitem, SosDetailsActivity.this, 1);
		listView.setAdapter(cacheAdapter);
		setListViewHeightBasedOnChildren(listView);
		cacheAdapter.notifyDataSetChanged();
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub				
				sv_sos_details.fullScroll(ScrollView.FOCUS_DOWN);
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
		for (int i = 0; i < listAdapter.getCount(); i++) {
			// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			// 计算子项View 的宽高
			listItem.measure(0, 0);
			// 统计所有子项的总高度
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;// + sos_details_bottom.getMeasuredHeight();
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
	
}
