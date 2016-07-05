package com.chung.sosandcommunicate;

import com.chung.sosandcommunicate.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CreditActivity extends Activity {
	private SharedPreferences sharedPreferences;
	
	private TextView tv_head;
	private TextView tv_helpTimes;
	private TextView tv_answerTimes;
	private TextView tv_savedGoals;
	private TextView tv_goalsRank;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit);
		sharedPreferences = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
		
		tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("信用积分");
		
		Log.i("credit", "配置成功");
		tv_helpTimes = (TextView) findViewById(R.id.tv_credit_help_times);
		tv_helpTimes.setText(sharedPreferences.getString("helpTimes", null));
		
		tv_answerTimes = (TextView) findViewById(R.id.tv_credit_answer_times);
		tv_answerTimes.setText(sharedPreferences.getString("answerTimes", null));
		
		tv_savedGoals = (TextView) findViewById(R.id.tv_credit_saved_goals);
		tv_savedGoals.setText(sharedPreferences.getString("savedGoals", null));
		
		tv_goalsRank = (TextView) findViewById(R.id.tv_credit_goals_rank);
		tv_goalsRank.setText(sharedPreferences.getString("goalsRank", null));
		
	}

}
