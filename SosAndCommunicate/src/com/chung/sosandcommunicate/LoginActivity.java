package com.chung.sosandcommunicate;

import java.io.File;
import java.util.List;

import com.chung.javabean.User;
import com.chung.server.GetAndParseJson;
import com.chung.tools.HeadPicShowerActivity;
import com.chung.tools.HeadPicToCircle;
import com.chung.tools.RoundProgressBar;
import com.chung.sosandcommunicate.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends Activity {
	public static final String SAE_URL = "http://sosandcommunicate.applinzi.com/";
	
	// private String
	// url="http://192.168.23.1:80/0/index.php/Login/login/";//真机URL
	// private String
	// url="http://10.0.3.2:80/0/index.php/Login/login/";//Genymotion URL
	private String url = SAE_URL + "index.php/Login/login/";// SAE URL

	private RoundProgressBar mRoundProgressBar;
	private int progress = 0;

	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	private RelativeLayout rl_login;
	private EditText etUsername;
	private EditText etPassword;
	public static ImageView iv_login_head;
	private File headPIC;
	public static Context loginContext;
	public static boolean LOGINFLAG1 = false;// 缓存登陆状态
	public static boolean LOGINFLAG2 = false;// 缓存登陆状态

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetAndParseJson.LOGIN:
				@SuppressWarnings("unchecked")
				User user = ((List<User>) msg.obj).get(0);
				if (user.getCode().equals("true")) {
					editor.putString("studentID", user.getStudentID());
					editor.putString("password", user.getPassword());
					editor.putString("headPic", user.getHeadPic());
					editor.putString("name", user.getName());
					editor.putString("sex", user.getSex());
					editor.putString("phone", user.getPhone());
					editor.putString("university", user.getUniversity());
					editor.putString("college", user.getCollege());
					editor.putString("major", user.getMajor());
					editor.putString("sign", user.getSign());
					editor.putString("helpTimes", user.getHelpTimes());
					editor.putString("answerTimes", user.getAnswerTimes());
					editor.putString("savedGoals", user.getSavedGoals());
					editor.putString("goalsRank", user.getGoalsRank());
					editor.putString("code", user.getCode());
					editor.putInt("tbnight", user.getTbnight());
					editor.commit();
					Log.i("sharedPreferences---USERINFO", "saved");

					String nick = user.getSex().equals("男") ? "　오빠" : "　여동생";
					progress("어서오세요　" + user.getName() + nick);

					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							Intent intent = new Intent(LoginActivity.this,MainActivity.class);
							startActivity(intent);
							LoginActivity.this.finish();
						}
					}).start();

				}
				if (user.getCode().equals("pswERROR")) {
					Log.i("login", "error");
					progress("客官你密码填错啦");

					LOGINFLAG1 = false;// 重置缓存登陆状态
					LOGINFLAG2 = false;// 重置缓存登陆状态
				}
				if (user.getCode().equals("unERROR")) {
					Log.i("login", "error");
					progress("无此账户哦");

					LOGINFLAG1 = false;// 重置缓存登陆状态
					LOGINFLAG2 = false;// 重置缓存登陆状态
				}
				break;

			case GetAndParseJson.GETINTERNETERROR:
				Toast toast1 = Toast.makeText(LoginActivity.this, "请检查网络设置",Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.cry);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
				LOGINFLAG1 = false;// 重置缓存登陆状态
				LOGINFLAG2 = false;// 重置缓存登陆状态
				break;

			case GetAndParseJson.GETSERVERERROR:
				Toast toast2 = Toast.makeText(LoginActivity.this, "请检查网络设置",Toast.LENGTH_SHORT);
				toast2.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView2 = (LinearLayout) toast2.getView();
				ImageView imageCodeProject2 = new ImageView(
						getApplicationContext());
				imageCodeProject2.setImageResource(R.drawable.cry);
				toastView2.addView(imageCodeProject2, 0);
				toast2.show();
				LOGINFLAG1 = false;// 重置缓存登陆状态
				LOGINFLAG2 = false;// 重置缓存登陆状态
				break;

			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sharedPreferences = getSharedPreferences("USERINFO",Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		loginContext = LoginActivity.this;
		
		headPIC = new File(LoginActivity.this.getFilesDir(),sharedPreferences.getString("studentID", null) + ".png");
		iv_login_head = (ImageView) findViewById(R.id.iv_login_head);
		if (headPIC.canRead()) {
			iv_login_head.setImageBitmap(HeadPicToCircle.toRoundBitmap(BitmapFactory.decodeFile(headPIC.getPath())));
		} else {
			iv_login_head.setImageResource(R.drawable.login);
		}
		iv_login_head.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LoginActivity.this,
						HeadPicShowerActivity.class));
			}
		});

		etUsername = (EditText) findViewById(R.id.et_login_username);
		etPassword = (EditText) findViewById(R.id.et_login_password);
		rl_login = (RelativeLayout) findViewById(R.id.rl_login);
		if(sharedPreferences.getString("studentID", null)!=null){
			rl_login.setFocusableInTouchMode(true);
			etUsername.setText(sharedPreferences.getString("studentID", null));
			if(sharedPreferences.getString("password", null)!=""){
				etPassword.setText(sharedPreferences.getString("password", null));
				login(null);
			}else {
				etPassword.setText("");				
			}
		}
		
		etPassword.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if ((keyCode == KeyEvent.KEYCODE_ENTER) && (!LOGINFLAG1)) {
					LOGINFLAG1 = true;// 解决登陆Activity回车事件响应两次的bug
					login(v);
				}
				return false;
			}
		});

		mRoundProgressBar = (RoundProgressBar) findViewById(R.id.rp_roundProgressBar);

	}

	public void login(View v) {
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		if (username.isEmpty() || password.isEmpty()) {
			Toast toast4 = Toast.makeText(LoginActivity.this, "认真填完账号密码好不", Toast.LENGTH_SHORT);
			toast4.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView4 = (LinearLayout) toast4.getView();
			ImageView imageCodeProject4 = new ImageView(getApplicationContext());
			imageCodeProject4.setImageResource(R.drawable.cry);
			toastView4.addView(imageCodeProject4, 0);
			toast4.show();
		} else if (!LOGINFLAG2) {
			LOGINFLAG2 = true;// 解决登陆Activity按钮点击事件响应两次的bug

			GetAndParseJson getAndParseJson = new GetAndParseJson(handler, url, username, password, 1);
			getAndParseJson.getJsonFromInternet();

		}

	}

	public void tvregister(View v) {
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
		// this.finish();
	}

//	public void forgetpassword(View v) {
//		AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//		alertDialog.setIcon(R.drawable.wxts);// 设置对话框图标
//		alertDialog.setTitle("温馨提示");// 设置标题
//		alertDialog.setMessage("请用学号和密码激活账号，若忘记密码，请联系学校教务处解决，点击确定将跳转至学校官");// 设置内容
//
//		/***** 设置按钮 *****/
//		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
//				new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						Uri uri = Uri.parse("http://www.zstu.edu.cn");
//						Intent it = new Intent(Intent.ACTION_VIEW, uri);
//						startActivity(it);
//					}
//				});
//		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
//				new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						// Toast.makeText(LoginActivity.this, "您点击了取消",
//						// Toast.LENGTH_SHORT).show();
//					}
//				});
//
//		alertDialog.show();// 创建对话框并显示
//	}

	private void progress(final String toastMSG) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (progress <= 100) {
					progress += 10;
					mRoundProgressBar.setProgress(progress);

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				mRoundProgressBar.setProgress(0);
				progress = 0;
				Looper.prepare();
				Toast.makeText(LoginActivity.this, toastMSG, Toast.LENGTH_SHORT).show();
				Looper.loop();

			}
		}).start();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(LoginActivity.this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(LoginActivity.this);
	}
}
