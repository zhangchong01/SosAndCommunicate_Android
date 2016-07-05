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

public class FuZhuActivity  extends Activity {
	
//	private String url="http://192.168.23.1:80/0/index.php/Getsettings/getsettings/";//’Êª˙URL
//	private String url="http://10.0.3.2:80/0/index.php/Getsettings/getsettings/";//Genymotion URL
	private String url=LoginActivity.SAE_URL+"index.php/Getsettings/getsettings/";//SAE URL
	
	private static boolean tb51Flag = false;
	private static boolean tb52Flag = false;
	private static boolean tb53Flag = false;
	private static boolean tb54Flag = false;
	private static boolean tb55Flag = false;
	private static boolean tb56Flag = false;
	private static boolean tb57Flag = false;
	private ToggleButton tb_51;
	private ToggleButton tb_52;
	private ToggleButton tb_53;
	private ToggleButton tb_54;
	private ToggleButton tb_55;
	private ToggleButton tb_56;
	private ToggleButton tb_57;
	private String[] args = new String[4];

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GetAndParseJson.SETTINGS:
				@SuppressWarnings("unchecked")
				SettingsDetails settingsDetails = ((List<SettingsDetails>)msg.obj).get(0);
				if(settingsDetails.getTb51()==1){
					tb51Flag = true;
				}
				if(settingsDetails.getTb52()==1){
					tb52Flag = true;
				}
				if(settingsDetails.getTb53()==1){
					tb53Flag = true;
				}
				if(settingsDetails.getTb54()==1){
					tb54Flag = true;
				}
				if(settingsDetails.getTb55()==1){
					tb55Flag = true;
				}
				if(settingsDetails.getTb56()==1){
					tb56Flag = true;
				}
				if(settingsDetails.getTb57()==1){
					tb57Flag = true;
				}
				tb_51.setChecked(tb51Flag);
				tb_52.setChecked(tb52Flag);
				tb_53.setChecked(tb53Flag);
				tb_54.setChecked(tb54Flag);
				tb_55.setChecked(tb55Flag);
				tb_56.setChecked(tb56Flag);
				tb_57.setChecked(tb57Flag);
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_fuzhu);

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("∏®÷˙…Ë÷√");
		
		GetAndParseJson getAndParseJson = new GetAndParseJson(handler, url, LeftMenuFragment.arg0, "", 3);
		getAndParseJson.getJsonFromInternet();
		
		args[0] = LeftMenuFragment.arg0;
		args[1] = "1";
		
		tb_51 = (ToggleButton) findViewById(R.id.tb_51);
		tb_52 = (ToggleButton) findViewById(R.id.tb_52);
		tb_53 = (ToggleButton) findViewById(R.id.tb_53);
		tb_54 = (ToggleButton) findViewById(R.id.tb_54);
		tb_55 = (ToggleButton) findViewById(R.id.tb_55);
		tb_56 = (ToggleButton) findViewById(R.id.tb_56);
		tb_57 = (ToggleButton) findViewById(R.id.tb_57);

		tb_51.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb51Flag){
					tb51Flag = true;
					turnON("tb51");
				}else {
					tb51Flag = false;
					turnOFF("tb51");
				}
				tb_51.setChecked(tb51Flag);
			}
		});
		tb_52.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb52Flag){
					tb52Flag = true;
					turnON("tb52");
				}else {
					tb52Flag = false;
					turnOFF("tb52");
				}
				tb_52.setChecked(tb52Flag);
			}
		});
		tb_53.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb53Flag){
					tb53Flag = true;
					turnON("tb53");
				}else {
					tb53Flag = false;
					turnOFF("tb53");
				}
				tb_53.setChecked(tb53Flag);
			}
		});
		tb_54.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb54Flag){
					tb54Flag = true;
					turnON("tb54");
				}else {
					tb54Flag = false;
					turnOFF("tb54");
				}
				tb_54.setChecked(tb54Flag);
			}
		});
		tb_55.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb55Flag){
					tb55Flag = true;
					turnON("tb55");
				}else {
					tb55Flag = false;
					turnOFF("tb55");
				}
				tb_55.setChecked(tb55Flag);
			}
		});
		tb_56.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb56Flag){
					tb56Flag = true;
					turnON("tb56");
				}else {
					tb56Flag = false;
					turnOFF("tb56");
				}
				tb_56.setChecked(tb56Flag);
			}
		});
		tb_57.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!tb57Flag){
					tb57Flag = true;
					turnON("tb57");
				}else {
					tb57Flag = false;
					turnOFF("tb57");
				}
				tb_57.setChecked(tb57Flag);
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