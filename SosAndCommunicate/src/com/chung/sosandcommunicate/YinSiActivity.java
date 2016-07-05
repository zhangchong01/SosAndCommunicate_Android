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

public class YinSiActivity  extends Activity {
	
//	private String url="http://192.168.23.1:80/0/index.php/Getsettings/getsettings/";//’Êª˙URL
//	private String url="http://10.0.3.2:80/0/index.php/Getsettings/getsettings/";//Genymotion URL
	private String url=LoginActivity.SAE_URL+"index.php/Getsettings/getsettings/";//SAE URL
	
	private static boolean tb31Flag = false;
	private static boolean tb32Flag = false;
	private static boolean tb33Flag = false;
	private static boolean tb34Flag = false;
	private static boolean tb35Flag = false;
	private ToggleButton tb_31;
	private ToggleButton tb_32;
	private ToggleButton tb_33;
	private ToggleButton tb_34;
	private ToggleButton tb_35;
	private String[] args = new String[4];

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetAndParseJson.SETTINGS:
				@SuppressWarnings("unchecked")
				SettingsDetails settingsDetails = ((List<SettingsDetails>)msg.obj).get(0);
				if(settingsDetails.getTb31()==1){
					tb31Flag = true;
				}
				if(settingsDetails.getTb32()==1){
					tb32Flag = true;
				}
				if(settingsDetails.getTb33()==1){
					tb33Flag = true;
				}
				if(settingsDetails.getTb34()==1){
					tb34Flag = true;
				}
				if(settingsDetails.getTb35()==1){
					tb35Flag = true;
				}
				tb_31.setChecked(tb31Flag);
				tb_32.setChecked(tb32Flag);
				tb_33.setChecked(tb33Flag);
				tb_34.setChecked(tb34Flag);
				tb_35.setChecked(tb35Flag);
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_yinsi);

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("“˛ÀΩ…Ë÷√");
		
		GetAndParseJson getAndParseJson = new GetAndParseJson(handler, url, LeftMenuFragment.arg0, "", 3);
		getAndParseJson.getJsonFromInternet();
		
		args[0] = LeftMenuFragment.arg0;
		args[1] = "1";
		
		tb_31 = (ToggleButton) findViewById(R.id.tb_31);
		tb_32 = (ToggleButton) findViewById(R.id.tb_32);
		tb_33 = (ToggleButton) findViewById(R.id.tb_33);
		tb_34 = (ToggleButton) findViewById(R.id.tb_34);
		tb_35 = (ToggleButton) findViewById(R.id.tb_35);

		tb_31.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb31Flag){
					tb31Flag = true;
					turnON("tb31");
				}else {
					tb31Flag = false;
					turnOFF("tb31");
				}
				tb_31.setChecked(tb31Flag);
			}
		});
		tb_32.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb32Flag){
					tb32Flag = true;
					turnON("tb32");
				}else {
					tb32Flag = false;
					turnOFF("tb32");
				}
				tb_32.setChecked(tb32Flag);
			}
		});
		tb_33.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb33Flag){
					tb33Flag = true;
					turnON("tb33");
				}else {
					tb33Flag = false;
					turnOFF("tb33");
				}
				tb_33.setChecked(tb33Flag);
			}
		});
		tb_34.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb34Flag){
					tb34Flag = true;
					turnON("tb34");
				}else {
					tb34Flag = false;
					turnOFF("tb34");
				}
				tb_34.setChecked(tb34Flag);
			}
		});
		tb_35.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb35Flag){
					tb35Flag = true;
					turnON("tb35");
				}else {
					tb35Flag = false;
					turnOFF("tb35");
				}
				tb_35.setChecked(tb35Flag);
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