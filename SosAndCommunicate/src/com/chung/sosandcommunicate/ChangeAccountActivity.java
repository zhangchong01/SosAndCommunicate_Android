package com.chung.sosandcommunicate;

import com.chung.sosandcommunicate.R;
import com.chung.server.SendAndStoreData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeAccountActivity extends Activity {
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	private EditText etChange;
	private TextView tv_head;
	private String changeType;
//	private String strUTF8;

	//private String url="http://192.168.23.1:80/0/index.php/Change/change/";//真机URL
	//private String url = "http://10.0.3.2:80/0/index.php/Change/change/";// Genymotion URL
	private String url = LoginActivity.SAE_URL+"index.php/Change/change/";// SAE URL
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SendAndStoreData.DATAUPLOADSUCCEED:
				Toast toast1 = Toast.makeText(ChangeAccountActivity.this,"客官你的信息修改成功了哦", Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.smile);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
	            
                Intent intent = new Intent();//数据是使用Intent返回
                if(changeType.equalsIgnoreCase("修改手机")){
                	intent.putExtra("changedPhone", etChange.getText().toString());//把返回数据存入Intent
                }
                if(changeType.equalsIgnoreCase("修改签名")){
                	intent.putExtra("changedSign", etChange.getText().toString());//把返回数据存入Intent
                }
                ChangeAccountActivity.this.setResult(AccountActivity.CHANGE_REQUEST_CODE, intent);//设置返回数据
				ChangeAccountActivity.this.finish();//关闭Activity
				break;
			case SendAndStoreData.SENDINTERNETERROR:
				Toast toast2 = Toast.makeText(ChangeAccountActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
				toast2.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView2 = (LinearLayout) toast2.getView();
				ImageView imageCodeProject2 = new ImageView(getApplicationContext());
				imageCodeProject2.setImageResource(R.drawable.cry);
				toastView2.addView(imageCodeProject2, 0);
				toast2.show();
				break;
			case SendAndStoreData.SENDSERVERERROR:
				Toast toast3 = Toast.makeText(ChangeAccountActivity.this,"请检查网络设置", Toast.LENGTH_SHORT);
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
		setContentView(R.layout.activity_account_change);

		Intent intent = getIntent();
		changeType = intent.getStringExtra("TVName");
		Log.i("changeType", changeType);
		tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText(changeType);

		etChange = (EditText) findViewById(R.id.et_change_account);
		if(changeType.equalsIgnoreCase("修改手机")){
			etChange.setInputType(InputType.TYPE_CLASS_NUMBER);
		}

	}
	
	public void saveChange(View v) {//读取sharedPreferences内容然后向服务器发送请求保存数据
		sharedPreferences = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		String[] args = new String[3];
		args[0] = sharedPreferences.getString("studentID", null);
		if(changeType.equalsIgnoreCase("修改手机")){
			args[1] = "0";
			args[2] = etChange.getText().toString();
			editor.putString("phone", etChange.getText().toString());
			editor.commit();
		}
		if(changeType.equalsIgnoreCase("修改签名")){
			args[1] = "1";
			args[2] = etChange.getText().toString();
			editor.putString("sign", args[2]);
			editor.commit();
		}
		
		SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, url, args , 3);
		sendAndStoreData.saveDataToInternet();
		
	}
	
}
