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

public class AnQuanActivity  extends Activity {
	
//	private String url="http://192.168.23.1:80/0/index.php/Getsettings/getsettings/";//真机URL
//	private String url="http://10.0.3.2:80/0/index.php/Getsettings/getsettings/";//Genymotion URL
	private String url=LoginActivity.SAE_URL+"index.php/Getsettings/getsettings/";//SAE URL
	
	private static boolean tb41Flag = false;
	private static boolean tb42Flag = false;
	private static boolean tb43Flag = false;
	private static boolean tb44Flag = false;
	private static boolean tb45Flag = false;
	private static boolean tb46Flag = false;
	private ToggleButton tb_41;
	private ToggleButton tb_42;
	private ToggleButton tb_43;
	private ToggleButton tb_44;
	private ToggleButton tb_45;
	private ToggleButton tb_46;
	private String[] args = new String[4];

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetAndParseJson.SETTINGS:
				@SuppressWarnings("unchecked")
				SettingsDetails settingsDetails = ((List<SettingsDetails>)msg.obj).get(0);
				if(settingsDetails.getTb41()==1){
					tb41Flag = true;
				}
				if(settingsDetails.getTb42()==1){
					tb42Flag = true;
				}
				if(settingsDetails.getTb43()==1){
					tb43Flag = true;
				}
				if(settingsDetails.getTb44()==1){
					tb44Flag = true;
				}
				if(settingsDetails.getTb45()==1){
					tb45Flag = true;
				}
				if(settingsDetails.getTb46()==1){
					tb46Flag = true;
				}
				tb_41.setChecked(tb41Flag);
				tb_42.setChecked(tb42Flag);
				tb_43.setChecked(tb43Flag);
				tb_44.setChecked(tb44Flag);
				tb_45.setChecked(tb45Flag);
				tb_46.setChecked(tb46Flag);
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_anquan);

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("安全设置");
		
		GetAndParseJson getAndParseJson = new GetAndParseJson(handler, url, LeftMenuFragment.arg0, "", 3);
		getAndParseJson.getJsonFromInternet();
		
		args[0] = LeftMenuFragment.arg0;
		args[1] = "1";
		
		tb_41 = (ToggleButton) findViewById(R.id.tb_41);
		tb_42 = (ToggleButton) findViewById(R.id.tb_42);
		tb_43 = (ToggleButton) findViewById(R.id.tb_43);
		tb_44 = (ToggleButton) findViewById(R.id.tb_44);
		tb_45 = (ToggleButton) findViewById(R.id.tb_45);
		tb_46 = (ToggleButton) findViewById(R.id.tb_46);

		tb_41.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb41Flag){
					tb41Flag = true;
					turnON("tb41");
				}else {
					tb41Flag = false;
					turnOFF("tb41");
				}
				tb_41.setChecked(tb41Flag);
			}
		});
		tb_42.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb42Flag){
					tb42Flag = true;
					turnON("tb42");
				}else {
					tb42Flag = false;
					turnOFF("tb42");
				}
				tb_42.setChecked(tb42Flag);
			}
		});
		tb_43.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb43Flag){
					tb43Flag = true;
					turnON("tb43");
				}else {
					tb43Flag = false;
					turnOFF("tb43");
				}
				tb_43.setChecked(tb43Flag);
			}
		});
		tb_44.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb44Flag){
					tb44Flag = true;
					turnON("tb44");
				}else {
					tb44Flag = false;
					turnOFF("tb44");
				}
				tb_44.setChecked(tb44Flag);
			}
		});
		tb_45.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb45Flag){
					tb45Flag = true;
					turnON("tb45");
				}else {
					tb45Flag = false;
					turnOFF("tb45");
				}
				tb_45.setChecked(tb45Flag);
			}
		});
		tb_46.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb46Flag){
					tb46Flag = true;
					turnON("tb46");
				}else {
					tb46Flag = false;
					turnOFF("tb46");
				}
				tb_46.setChecked(tb46Flag);
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