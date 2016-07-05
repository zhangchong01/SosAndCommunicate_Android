package com.chung.sosandcommunicate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.chung.sosandcommunicate.R;
import com.chung.baidumap.LocationDemo;
import com.chung.server.SendAndStoreData;
import com.chung.tools.JudgeDate;
import com.chung.tools.MyAlertDialog;
import com.chung.tools.ScreenInfo;
import com.chung.tools.WheelMain;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SosActivity extends Activity {
	// private String
	// url="http://192.168.23.1:80/0/index.php/Release/sos/";//真机URL
	// private String url = "http://10.0.3.2:80/0/index.php/Release/sos/";//
	// Genymotion URL
	private String url = LoginActivity.SAE_URL + "index.php/Release/sos/";// SAE
																			// URL
	private static final int LOCATION_REQUEST_CODE = 2;
	private String[] args = new String[6];
	private boolean flag = true;

	private Spinner sp_sos_theme;
	private LinearLayout ll_sos_date;
	private LinearLayout ll_sos_addr;
	private LinearLayout ll_sos_phone;
	private TextView tv_sos_date;
	private TextView tv_sos_addr;
	private TextView tv_sos_phone;
	private EditText et_sos_more;

	private WheelMain wheelMain;
	@SuppressLint("SimpleDateFormat")
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SendAndStoreData.DATAUPLOADSUCCEED:
				Toast toast1 = Toast.makeText(SosActivity.this, "发布成功，等待支援哦", Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.smile);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
				SosActivity.this.finish();
				break;
			case SendAndStoreData.SENDINTERNETERROR:
				Toast toast2 = Toast.makeText(SosActivity.this, "请检查网络设置", Toast.LENGTH_SHORT);
				toast2.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView2 = (LinearLayout) toast2.getView();
				ImageView imageCodeProject2 = new ImageView(getApplicationContext());
				imageCodeProject2.setImageResource(R.drawable.cry);
				toastView2.addView(imageCodeProject2, 0);
				toast2.show();
				break;
			case SendAndStoreData.SENDSERVERERROR:
				Toast toast3 = Toast.makeText(SosActivity.this, "请检查网络设置", Toast.LENGTH_SHORT);
				toast3.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView3 = (LinearLayout) toast3.getView();
				ImageView imageCodeProject3 = new ImageView(getApplicationContext());
				imageCodeProject3.setImageResource(R.drawable.cry);
				toastView3.addView(imageCodeProject3, 0);
				toast3.show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sos);

		Intent intent = getIntent();
		args[0] = intent.getStringExtra("studentID");

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("发布求助");

		sp_sos_theme = (Spinner) findViewById(R.id.sp_sos_theme);
		sp_sos_theme.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				args[1] = SosActivity.this.getResources().getStringArray(R.array.spinner_sos_theme)[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		ll_sos_date = (LinearLayout) findViewById(R.id.ll_sos_date);
		tv_sos_date = (TextView) findViewById(R.id.tv_sos_date);
		ll_sos_date.setOnClickListener(new OnClickListener() {
			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater.from(SosActivity.this);
				final View timepickerview = inflater.inflate(R.layout.timepicker, null);
				ScreenInfo screenInfo = new ScreenInfo(SosActivity.this);
				wheelMain = new WheelMain(timepickerview);
				wheelMain.screenheight = screenInfo.getHeight();
				String time = tv_sos_date.getText().toString();
				Calendar calendar = Calendar.getInstance();
				if (JudgeDate.isDate(time, "yyyy-MM-dd-hh-mm")) {
					try {
						calendar.setTime(dateFormat.parse(time));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int hour = calendar.get(Calendar.HOUR);
				int minute = calendar.get(Calendar.MINUTE);

				wheelMain.initDateTimePicker(year, month, day, hour, minute);
				final MyAlertDialog dialog = new MyAlertDialog(SosActivity.this).builder().setTitle("请选择求助时间")
						.setView(timepickerview);
				dialog.setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
				dialog.setPositiveButton("保存", new OnClickListener() {
					@Override
					public void onClick(View v) {
						tv_sos_date.setText(wheelMain.getTime());
					}
				});
				dialog.show();

			}
		});

		ll_sos_addr = (LinearLayout) findViewById(R.id.ll_sos_addr);
		tv_sos_addr = (TextView) findViewById(R.id.tv_sos_addr);

		ll_sos_addr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SosActivity.this, LocationDemo.class);
				startActivityForResult(intent, LOCATION_REQUEST_CODE);
			}
		});

		ll_sos_phone = (LinearLayout) findViewById(R.id.ll_sos_phone);
		tv_sos_phone = (TextView) findViewById(R.id.tv_sos_phone);

		ll_sos_phone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_sos_phone.setText("15757126360");
				// 获取手机号码
//				TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//				String deviceid = tm.getDeviceId();// 获取智能设备唯一编号
//				String te1 = tm.getLine1Number();// 获取本机号码
//				String imei = tm.getSimSerialNumber();// 获得SIM卡的序号
//				String imsi = tm.getSubscriberId();// 得到用户Id
			}
		});

		et_sos_more = (EditText) findViewById(R.id.et_sos_more);

	}

	@SuppressLint("SimpleDateFormat")
	public void sos(View v) {

		args[2] = tv_sos_date.getText().toString();
		args[3] = tv_sos_addr.getText().toString();
		args[4] = tv_sos_phone.getText().toString();
		args[5] = et_sos_more.getText().toString();

		for (int i = 0; i < 6; i++) {
			if (args[i].isEmpty() || args[i].equals("请选择")) {
				flag = false;
				break;
			}
		}
		if (flag) {
			/*
			 * File headPIC = new File(SosActivity.this.getFilesDir(),args[0] +
			 * ".png"); HashMap<String, Object> map = new HashMap<String,
			 * Object>(); map.put("img", headPIC.getPath()); map.put("username",
			 * args[0]); map.put("theme", args[1]); map.put("blogTime", new
			 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			 * map.put("blogType", "♥"); map.put("info", "　　"+ args[5]);
			 * HomeFragment.listitem.add(map);
			 * 
			 * HomeFragment.adapter.notifyDataSetChanged();
			 */

			SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, url, args, 6);
			sendAndStoreData.saveDataToInternet();

		} else {
			Toast toast4 = Toast.makeText(SosActivity.this, "认真填完中不？", Toast.LENGTH_SHORT);
			toast4.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView4 = (LinearLayout) toast4.getView();
			ImageView imageCodeProject4 = new ImageView(getApplicationContext());
			imageCodeProject4.setImageResource(R.drawable.cry);
			toastView4.addView(imageCodeProject4, 0);
			toast4.show();
			flag = true;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { // Intent返回值的回调函数
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			if (requestCode == LOCATION_REQUEST_CODE) {
				tv_sos_addr.setText(data.getStringExtra("name"));// 设置求助地点
			}
		}
	}

}
