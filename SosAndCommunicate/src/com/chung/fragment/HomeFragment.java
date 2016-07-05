package com.chung.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chung.javabean.Blog;
import com.chung.server.GetAndParseJson;
import com.chung.sosandcommunicate.CommunicateDetailsActivity;
import com.chung.sosandcommunicate.LoginActivity;
import com.chung.sosandcommunicate.R;
import com.chung.sosandcommunicate.SosDetailsActivity;
import com.chung.tools.LvAdapter;
import com.chung.tools.MyListView;
import com.chung.tools.MyListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends BaseFragment {
//	protected static final int flag = 0;

	// String url="http://192.168.23.1:80/0/index.php/Blog/blog/";//真机URL
	// String url="http://10.0.3.2:80/0/index.php/Blog/blog/";//Genymotion URL
	private static String url = LoginActivity.SAE_URL + "index.php/Blog/blog/";// SAE URL
	
	private static boolean blogTypeBtn = true;
	private ProgressDialog progressDialog;
	
	private List<Integer> blogIDs = new ArrayList<Integer>();
	private List<String> studentIDs = new ArrayList<String>();
//	private int[] blogIDs;
//	private String[] studentIDs;

	private TextView tv_sos_blog;
	private TextView tv_communicate_blog;
	private MyListView lv;
	private LvAdapter adapter;

	public ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
	private HashMap<String, Object> map;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressLint("UseValueOf")
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetAndParseJson.BLOG:
				List<Blog> blog = (List<Blog>) msg.obj;
				if (!blog.isEmpty()) {
					try {
						listitem.clear();// 每次进入home界面刷新帖子
//						blogIDs = new int[blog.size()];
//						studentIDs = new String[blog.size()];
						Log.i("Bolg", "cleared");
						for (int i = 0; i < blog.size(); i++) {
							map = new HashMap<String, Object>();
							if(blogTypeBtn){
								if(blog.get(i).getBlogType().equals("1")){
									map.put("img", blog.get(i).getHeadPic());
									map.put("username", blog.get(i).getStudentID());
									map.put("theme", blog.get(i).getTheme());
									map.put("blogTime", blog.get(i).getBlogTime());
									map.put("blogType", "♥");
									map.put("info", "　　"+ blog.get(i).getBlogDetails());
									blogIDs.add(Integer.valueOf(blog.get(i).getBlogID()));
									studentIDs.add(blog.get(i).getStudentID());
//									blogIDs[i] = blog.get(i).getBlogID();
//									studentIDs[i] = blog.get(i).getStudentID();
									listitem.add(map);
								}
							}else{
								if(blog.get(i).getBlogType().equals("0")){
									map.put("img", blog.get(i).getHeadPic());
									map.put("username", blog.get(i).getStudentID());
									map.put("theme", blog.get(i).getTheme());
									map.put("blogTime", blog.get(i).getBlogTime());
									map.put("blogType", "♡");
									map.put("info", "　　"+ blog.get(i).getBlogDetails());
									blogIDs.add(Integer.valueOf(blog.get(i).getBlogID()));
									studentIDs.add(blog.get(i).getStudentID());
//									blogIDs[i] = blog.get(i).getBlogID();
//									studentIDs[i] = blog.get(i).getStudentID();
									listitem.add(map);
								}
							}
						}

						adapter = new LvAdapter(listitem, getActivity(), 0);
						lv.setAdapter(adapter);
						progressDialog.dismiss();
						Log.i("MyAdapter", "seted");
						Log.i("Bolg", "refeshed");
					} catch (Exception e) {
						Log.i("downloadBlog", "failed");
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getActivity(), "暂无帖子", Toast.LENGTH_SHORT).show();
					Log.i("downloadBlog", "error");
				}
				break;
			case GetAndParseJson.GETINTERNETERROR:
				Toast toast1 = Toast.makeText(getActivity(), "请检查网络设置1", Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER,0,0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getActivity().getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.cry);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
				break;
			case GetAndParseJson.GETSERVERERROR:
				Toast toast2 = Toast.makeText(getActivity(), "请检查网络设置2", Toast.LENGTH_SHORT);
				toast2.setGravity(Gravity.CENTER,0,0);
				LinearLayout toastView2 = (LinearLayout) toast2.getView();
				ImageView imageCodeProject2 = new ImageView(getActivity().getApplicationContext());
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		reload();
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("努力加载中...");
		progressDialog.show();
		tv_sos_blog = (TextView) view.findViewById(R.id.tv_sos_blog);
		tv_sos_blog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_sos_blog.setBackgroundColor(Color.parseColor("#FFFFFF"));
				tv_communicate_blog.setBackgroundColor(Color.parseColor("#EEEEEE"));
				blogTypeBtn = true;
				reload();
			}
		});
		
		tv_communicate_blog = (TextView) view.findViewById(R.id.tv_communicate_blog);
		tv_communicate_blog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_sos_blog.setBackgroundColor(Color.parseColor("#EEEEEE"));
				tv_communicate_blog.setBackgroundColor(Color.parseColor("#FFFFFF"));
				blogTypeBtn = false;
				reload();
			}
		});
		
		lv = (MyListView) view.findViewById(R.id.lv);
		
		 //添加单击监听
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tv_blogType = (TextView) view.findViewById(R.id.main_blogType);
				Intent intent = new Intent();
				intent.putExtra("studentID", studentIDs.get(position - 1));//传入studentID
				if(tv_blogType.getText().toString().equals("♥")) {//求助帖
					intent.putExtra("sosBlogID", String.valueOf(blogIDs.get(position - 1)));//传入sosBlogID
					Log.i("chooseSosBlog position", String.valueOf(position));
					Log.i("chooseSosBlogID", String.valueOf(blogIDs.get(position - 1)));
					intent.setClass(getActivity(), SosDetailsActivity.class);
					startActivity(intent);
				}
				if(tv_blogType.getText().toString().equals("♡")) {//交流贴
					intent.putExtra("communicateBlogID", String.valueOf(blogIDs.get(position - 1)));//传入communicateBlogID
					Log.i("chooseCommunicateBlog position", String.valueOf(position));
					Log.i("chooseCommunicateBlogID", String.valueOf(blogIDs.get(position - 1)));
					intent.setClass(getActivity(), CommunicateDetailsActivity.class);
					startActivity(intent);
				}
			}
		});
		
		lv.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1500);
						} catch (Exception e) {
							e.printStackTrace();
						}
						reload();
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						lv.onRefreshComplete();
					}
				}.execute(null, null, null);
			}
		});
		
		return view;
	}
	
	public void reload(){
		blogIDs.clear();
		studentIDs.clear();
		GetAndParseJson getAndParseJson = new GetAndParseJson(handler, url, "","", 2);
		getAndParseJson.getJsonFromInternet();
//		progressDialog = new ProgressDialog(getActivity());
//		progressDialog.setMessage("努力加载中...");
//		progressDialog.show();
	}

}
