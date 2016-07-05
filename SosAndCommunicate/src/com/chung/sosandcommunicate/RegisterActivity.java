package com.chung.sosandcommunicate;

import com.chung.sosandcommunicate.R;
import com.chung.server.SendAndStoreData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private Spinner sp0;
	private Spinner sp1;
	private Spinner sp2;
	private EditText et3;
	private EditText et4;
	private Spinner sp5;
	private EditText et6;
	private static boolean flag = true;
	private String[] args = new String[7];
	private String[] mItems = new String[6];

	//private String url="http://192.168.23.1:80/0/index.php/Register/register/";//真机URL
	//private String url = "http://10.0.3.2:80/0/index.php/Register/register/";// Genymotion URL
	private String url = LoginActivity.SAE_URL+"index.php/Register/register/";// SAE URL
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SendAndStoreData.DATAUPLOADSUCCEED:
				Toast toast1 = Toast.makeText(RegisterActivity.this,"恭喜你成为我们的一员猛将,初始密码为您教务系统密码,赶快来战！", Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.smile);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
				Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
				startActivity(intent);
				RegisterActivity.this.finish();
				break;
			case SendAndStoreData.SENDINTERNETERROR:
				Toast toast2 = Toast.makeText(RegisterActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
				toast2.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView2 = (LinearLayout) toast2.getView();
				ImageView imageCodeProject2 = new ImageView(getApplicationContext());
				imageCodeProject2.setImageResource(R.drawable.cry);
				toastView2.addView(imageCodeProject2, 0);
				toast2.show();
				break;
			case SendAndStoreData.SENDSERVERERROR:
				Toast toast3 = Toast.makeText(RegisterActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
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
		setContentView(R.layout.activity_register);
		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("注册激活");

		sp0 = (Spinner) findViewById(R.id.sp_register_university);
		sp1 = (Spinner) findViewById(R.id.sp_register_college);
		sp2 = (Spinner) findViewById(R.id.sp_register_major);
		et3 = (EditText) findViewById(R.id.et_register_studentID);
		et4 = (EditText) findViewById(R.id.et_register_name);
		sp5 = (Spinner) findViewById(R.id.sp_register_sex);
		et6 = (EditText) findViewById(R.id.et_register_phone);
		
		sp0.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				args[0] = RegisterActivity.this.getResources().getStringArray(R.array.spinner_university)[position];
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				args[1] = RegisterActivity.this.getResources().getStringArray(R.array.spinner_college)[position];
				if(position==0){
					mItems = null;
				}else {
					if(position==1){
						mItems = getResources().getStringArray(R.array.spinner_majors1);
					}
					if(position==2){
						mItems = getResources().getStringArray(R.array.spinner_majors2);
					}
					if(position==3){
						mItems = getResources().getStringArray(R.array.spinner_majors3);
					}
					if(position==4){
						mItems = getResources().getStringArray(R.array.spinner_majors4);
					}
					if(position==5){
						mItems = getResources().getStringArray(R.array.spinner_majors5);
					}
					if(position==6){
						mItems = getResources().getStringArray(R.array.spinner_majors6);
					}
					if(position==7){
						mItems = getResources().getStringArray(R.array.spinner_majors7);
					}
					if(position==8){
						mItems = getResources().getStringArray(R.array.spinner_majors8);
					}
					ArrayAdapter<String> adapter=new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item, mItems);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					//绑定 Adapter到控件
					sp2.setAdapter(adapter);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		
		sp2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				args[2] = mItems[position];
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		sp5.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				args[5] = RegisterActivity.this.getResources().getStringArray(R.array.spinner_sex)[position];
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
				
	}

	public void register(View v) {
		
		args[3] = et3.getText().toString();
		args[4] = et4.getText().toString();
		
		args[6] = et6.getText().toString();
		for(int i=0;i<7;i++){
			if(args[i].isEmpty()||args[i].equals("请选择")){
				flag = false;
				break;
			}
		}
		Log.i("flag", String.valueOf(flag));
		if(flag){
			SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, url, args, 7);
			sendAndStoreData.saveDataToInternet();
			Log.i("SendAndStoreData()","Executed");
		}else {
			Toast toast3 = Toast.makeText(RegisterActivity.this, "认真填完中不？", Toast.LENGTH_SHORT);
			toast3.setGravity(Gravity.CENTER,0,0);
			LinearLayout toastView3 = (LinearLayout) toast3.getView();
			ImageView imageCodeProject3 = new ImageView(getApplicationContext());
			imageCodeProject3.setImageResource(R.drawable.cry);
			toastView3.addView(imageCodeProject3, 0);
			toast3.show();
			flag = true;
		}
	}
}
