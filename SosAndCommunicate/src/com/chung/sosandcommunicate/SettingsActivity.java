package com.chung.sosandcommunicate;

import com.chung.sosandcommunicate.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("…Ë°°°°÷√");
	}
	
	public void tongYong(View v) {
		intent = new Intent(SettingsActivity.this,TongYongActivity.class);
		startActivity(intent);
	}
	public void tongZhi(View v) {
		intent = new Intent(SettingsActivity.this,TongZhiActivity.class);
		startActivity(intent);
	}
	public void yinSi(View v) {
		intent = new Intent(SettingsActivity.this,YinSiActivity.class);
		startActivity(intent);
	}
	public void anQuan(View v) {
		intent = new Intent(SettingsActivity.this,AnQuanActivity.class);
		startActivity(intent);
	}
	public void fuZhu(View v) {
		intent = new Intent(SettingsActivity.this,FuZhuActivity.class);
		startActivity(intent);
	}

}
