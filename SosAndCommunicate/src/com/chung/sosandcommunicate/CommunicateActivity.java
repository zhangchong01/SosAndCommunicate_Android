package com.chung.sosandcommunicate;

import com.chung.sosandcommunicate.R;
import com.chung.server.SendAndStoreData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CommunicateActivity extends Activity {
	//private String url="http://192.168.23.1:80/0/index.php/Release/communicate/";//真机URL
	//private String url = "http://10.0.3.2:80/0/index.php/Release/communicate/";// Genymotion URL
	private String url = LoginActivity.SAE_URL+"index.php/Release/communicate/";// SAE URL
	
	private String[] args = new String[4];
	private boolean flag = true;
	
	private Spinner sp_communicate_theme;
	private EditText et_communicate_question;
	private EditText et_communicate_more;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SendAndStoreData.DATAUPLOADSUCCEED:
				Toast toast1 = Toast.makeText(CommunicateActivity.this,"发布成功，等待回复哦", Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.smile);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
				CommunicateActivity.this.finish();
				break;
			case SendAndStoreData.SENDINTERNETERROR:
				Toast toast2 = Toast.makeText(CommunicateActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
				toast2.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView2 = (LinearLayout) toast2.getView();
				ImageView imageCodeProject2 = new ImageView(getApplicationContext());
				imageCodeProject2.setImageResource(R.drawable.cry);
				toastView2.addView(imageCodeProject2, 0);
				toast2.show();
				break;
			case SendAndStoreData.SENDSERVERERROR:
				Toast toast3 = Toast.makeText(CommunicateActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
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
		setContentView(R.layout.activity_communicate);

		Intent intent = getIntent();
		args[0] = intent.getStringExtra("studentID");
		
		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("发帖交流");
		
		sp_communicate_theme = (Spinner) findViewById(R.id.sp_communicate_theme);
		sp_communicate_theme.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				args[1] = CommunicateActivity.this.getResources().getStringArray(R.array.spinner_communicate_theme)[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		et_communicate_question = (EditText) findViewById(R.id.et_communicate_question);
		et_communicate_more = (EditText) findViewById(R.id.et_communicate_more);
	}

	@SuppressLint("SimpleDateFormat")
	public void communicate(View v){
		args[2] = et_communicate_question.getText().toString();
		args[3] = et_communicate_more.getText().toString();
		
		for(int i=0;i<4;i++){
			if(args[i].isEmpty()||args[i].equals("请选择")){
				flag = false;
				break;
			}
		}
		if(flag){
/*			File headPIC = new File(CommunicateActivity.this.getFilesDir(),args[0] + ".png");	
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("img", headPIC.getPath());
			map.put("username", args[0]);
			map.put("theme", args[1]);
			map.put("blogTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map.put("blogType", "♡");
			map.put("info", "　　"+ args[3]);
			HomeFragment.listitem.add(map);
			HomeFragment.adapter.notifyDataSetChanged();*/
			
			SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, url, args , 4);
			sendAndStoreData.saveDataToInternet();
			
		}else {
			Toast toast4 = Toast.makeText(CommunicateActivity.this, "认真填完中不？", Toast.LENGTH_SHORT);
			toast4.setGravity(Gravity.CENTER,0,0);
			LinearLayout toastView4 = (LinearLayout) toast4.getView();
			ImageView imageCodeProject4 = new ImageView(getApplicationContext());
			imageCodeProject4.setImageResource(R.drawable.cry);
			toastView4.addView(imageCodeProject4, 0);
			toast4.show();
			flag = true;
		}
		

	}
}
