package com.chung.sosandcommunicate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InfoFiveActivity extends Activity{
	private TextView tv_head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info5);
		
		tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("评价反馈");
		
	}
	
	
	public void submit(View v){
		Toast.makeText(InfoFiveActivity.this, "提交成功，即将返回", Toast.LENGTH_SHORT).show();
		InfoFiveActivity.this.finish();
	}
	
}
