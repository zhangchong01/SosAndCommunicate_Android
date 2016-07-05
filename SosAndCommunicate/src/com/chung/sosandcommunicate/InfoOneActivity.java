package com.chung.sosandcommunicate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InfoOneActivity extends Activity{
	private TextView tv_head;
	
	private RelativeLayout rl1;
	private RelativeLayout rl2;
	private RelativeLayout rl3;
	private RelativeLayout rl4;
	private RelativeLayout rl5;
	private RelativeLayout rl6;
	private RelativeLayout rl7;
	private RelativeLayout rl8;
	private RelativeLayout rl9;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info1);
		
		tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("π¶ƒ‹ΩÈ…‹");
		
		rl1 = (RelativeLayout) findViewById(R.id.rl1);
		rl2 = (RelativeLayout) findViewById(R.id.rl2);
		rl3 = (RelativeLayout) findViewById(R.id.rl3);
		rl4 = (RelativeLayout) findViewById(R.id.rl4);
		rl5 = (RelativeLayout) findViewById(R.id.rl5);
		rl6 = (RelativeLayout) findViewById(R.id.rl6);
		rl7 = (RelativeLayout) findViewById(R.id.rl7);
		rl8 = (RelativeLayout) findViewById(R.id.rl8);
		rl9 = (RelativeLayout) findViewById(R.id.rl9);
		
		rl1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, OneActivity.class);
				startActivity(intent);
			}
		});
		rl2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, TwoActivity.class);
				startActivity(intent);
			}
		});
		rl3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, ThreeActivity.class);
				startActivity(intent);
			}
		});
		rl4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, FourActivity.class);
				startActivity(intent);
			}
		});
		rl5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, FiveActivity.class);
				startActivity(intent);
			}
		});
		rl6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, SixActivity.class);
				startActivity(intent);
			}
		});
		rl7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, SevenActivity.class);
				startActivity(intent);
			}
		});
		rl8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, EightActivity.class);
				startActivity(intent);
			}
		});
		rl9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(InfoOneActivity.this, NineActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
