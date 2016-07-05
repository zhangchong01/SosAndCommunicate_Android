package com.chung.sosandcommunicate;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.chung.fragment.LeftMenuFragment;
import com.chung.sosandcommunicate.R;
import com.chung.javabean.SettingsDetails;
import com.chung.server.GetAndParseJson;
import com.chung.server.SendAndStoreData;

public class TongZhiActivity extends Activity {
	
//	private String url="http://192.168.23.1:80/0/index.php/Getsettings/getsettings/";//真机URL
//	private String url="http://10.0.3.2:80/0/index.php/Getsettings/getsettings/";//Genymotion URL
	private String url=LoginActivity.SAE_URL+"index.php/Getsettings/getsettings/";//SAE URL
	
	private static boolean tb21Flag = false;
	private static boolean tb22Flag = false;
	private static boolean tb23Flag = false;
	private static boolean tb24Flag = false;
	private static boolean tb25Flag = false;
	private ToggleButton tb_21;
	private ToggleButton tb_22;
	private ToggleButton tb_23;
	private ToggleButton tb_24;
	private ToggleButton tb_25;
	private String[] args = new String[4];

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetAndParseJson.SETTINGS:
				@SuppressWarnings("unchecked")
				SettingsDetails settingsDetails = ((List<SettingsDetails>)msg.obj).get(0);
				if(settingsDetails.getTb21()==1){
					tb21Flag = true;
				}
				if(settingsDetails.getTb22()==1){
					tb22Flag = true;
				}
				if(settingsDetails.getTb23()==1){
					tb23Flag = true;
				}
				if(settingsDetails.getTb24()==1){
					tb24Flag = true;
				}
				if(settingsDetails.getTb25()==1){
					tb25Flag = true;
				}
				tb_21.setChecked(tb21Flag);
				tb_22.setChecked(tb22Flag);
				tb_23.setChecked(tb23Flag);
				tb_24.setChecked(tb24Flag);
				tb_25.setChecked(tb25Flag);
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_tongzhi);

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("通知设置");
		
		GetAndParseJson getAndParseJson = new GetAndParseJson(handler, url, LeftMenuFragment.arg0, "", 3);
		getAndParseJson.getJsonFromInternet();
		
		args[0] = LeftMenuFragment.arg0;
		args[1] = "1";
		
		tb_21 = (ToggleButton) findViewById(R.id.tb_21);
		tb_22 = (ToggleButton) findViewById(R.id.tb_22);
		tb_23 = (ToggleButton) findViewById(R.id.tb_23);
		tb_24 = (ToggleButton) findViewById(R.id.tb_24);
		tb_25 = (ToggleButton) findViewById(R.id.tb_25);
		
		tb_21.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb21Flag){
					tb21Flag = true;
					turnON("tb21");
				}else {
					tb21Flag = false;
					turnOFF("tb21");
				}
				tb_21.setChecked(tb21Flag);
			}
		});
		tb_22.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb22Flag){
					tb22Flag = true;
					turnON("tb22");
				}else {
					tb22Flag = false;
					turnOFF("tb22");
				}
				tb_22.setChecked(tb22Flag);
			}
		});
		tb_23.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb23Flag){
					tb23Flag = true;
					turnON("tb23");
				}else {
					tb23Flag = false;
					turnOFF("tb23");
				}
				tb_23.setChecked(tb23Flag);
			}
		});
		tb_24.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb24Flag){
					tb24Flag = true;
					turnON("tb24");
				}else {
					tb24Flag = false;
					turnOFF("tb24");
				}
				tb_24.setChecked(tb24Flag);
			}
		});
		tb_25.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb25Flag){
					tb25Flag = true;
					turnON("tb25");
				}else {
					tb25Flag = false;
					turnOFF("tb25");
				}
				tb_25.setChecked(tb25Flag);
			}
		});
		
	}
	
	public void turnON(String tbID){
		args[2] = tbID;
		args[3] = "1";
		SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, LeftMenuFragment.settingsURL, args , 4);
		sendAndStoreData.saveDataToInternet();
	}
	
	public void turnOFF(String tbID){
		args[2] = tbID;
		args[3] = "0";
		SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, LeftMenuFragment.settingsURL, args , 4);
		sendAndStoreData.saveDataToInternet();
	}
	
}