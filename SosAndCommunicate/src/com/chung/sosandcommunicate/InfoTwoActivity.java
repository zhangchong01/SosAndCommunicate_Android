package com.chung.sosandcommunicate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoTwoActivity extends Activity{
	private TextView tv_head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info2);
		
		tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("ÑûÇëºÃÓÑ");
		
	}
}
