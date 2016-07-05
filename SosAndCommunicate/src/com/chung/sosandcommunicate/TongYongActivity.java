package com.chung.sosandcommunicate;

import java.util.List;

import com.chung.fragment.LeftMenuFragment;
import com.chung.sosandcommunicate.R;
import com.chung.javabean.SettingsDetails;
import com.chung.server.GetAndParseJson;
import com.chung.server.SendAndStoreData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TongYongActivity extends Activity {
	
//	private String url="http://192.168.23.1:80/0/index.php/Getsettings/getsettings/";//真机URL
//	private String url="http://10.0.3.2:80/0/index.php/Getsettings/getsettings/";//Genymotion URL
	private String url=LoginActivity.SAE_URL+"index.php/Getsettings/getsettings/";//SAE URL
	
	private static boolean tb11Flag = false;
	private static boolean tb12Flag = false;
	private static boolean tb13Flag = false;
	private static boolean tb14Flag = false;
	private static boolean tb15Flag = false;
	private static boolean tb16Flag = false;
	private ToggleButton tb_11;
	private ToggleButton tb_12;
	private ToggleButton tb_13;
	private ToggleButton tb_14;
	private ToggleButton tb_15;
	private ToggleButton tb_16;
	private String[] args = new String[4];

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetAndParseJson.SETTINGS:
				@SuppressWarnings("unchecked")
				SettingsDetails settingsDetails = ((List<SettingsDetails>)msg.obj).get(0);
				if(settingsDetails.getTb11()==1){
					tb11Flag = true;
				}
				if(settingsDetails.getTb12()==1){
					tb12Flag = true;
				}
				if(settingsDetails.getTb13()==1){
					tb13Flag = true;
				}
				if(settingsDetails.getTb14()==1){
					tb14Flag = true;
				}
				if(settingsDetails.getTb15()==1){
					tb15Flag = true;
				}
				if(settingsDetails.getTb16()==1){
					tb16Flag = true;
				}
				tb_11.setChecked(tb11Flag);
				tb_12.setChecked(tb12Flag);
				tb_13.setChecked(tb13Flag);
				tb_14.setChecked(tb14Flag);
				tb_15.setChecked(tb15Flag);
				tb_16.setChecked(tb16Flag);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_tongyong);

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("通用设置");

		tb_11 = (ToggleButton) findViewById(R.id.tb_11);
		tb_12 = (ToggleButton) findViewById(R.id.tb_12);
		tb_13 = (ToggleButton) findViewById(R.id.tb_13);
		tb_14 = (ToggleButton) findViewById(R.id.tb_14);
		tb_15 = (ToggleButton) findViewById(R.id.tb_15);
		tb_16 = (ToggleButton) findViewById(R.id.tb_16);
		
		GetAndParseJson getAndParseJson = new GetAndParseJson(handler, url, LeftMenuFragment.arg0, "", 3);
		getAndParseJson.getJsonFromInternet();
		
		args[0] = LeftMenuFragment.arg0;
		args[1] = "1";

		tb_11.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method
				if (!tb11Flag) {
					tb11Flag = true;
					turnON("tb11");
				} else {
					tb11Flag = false;
					turnOFF("tb11");
				}
				tb_11.setChecked(tb11Flag);
			}
		});
		
		tb_12.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method
				if (!tb12Flag) {
					tb12Flag = true;
					turnON("tb12");
				} else {
					tb12Flag = false;
					turnOFF("tb12");
				}
				tb_12.setChecked(tb12Flag);
			}
		});
		
		tb_13.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method
				if (!tb13Flag) {
					tb13Flag = true;
					turnON("tb13");
				} else {
					tb13Flag = false;
					turnOFF("tb13");
				}
				tb_13.setChecked(tb13Flag);
			}
		});
		
		tb_14.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method
				if (!tb14Flag) {
					tb14Flag = true;
					turnON("tb14");
				} else {
					tb14Flag = false;
					turnOFF("tb14");
				}
				tb_14.setChecked(tb14Flag);
			}
		});
		
		tb_15.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method
				if (!tb15Flag) {
					tb15Flag = true;
					turnON("tb15");
				} else {
					tb15Flag = false;
					turnOFF("tb15");
				}
				tb_15.setChecked(tb15Flag);
			}
		});
		
		tb_16.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method
				if (!tb16Flag) {
					tb16Flag = true;
					turnON("tb16");
				} else {
					tb16Flag = false;
					turnOFF("tb16");
				}
				tb_16.setChecked(tb16Flag);
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
