package com.chung.tools;

import java.io.File;

import com.chung.sosandcommunicate.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class HeadPicShowerActivity extends Activity {

	private ImageView BigHeadPic;
	private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headpic_shower);

        sharedPreferences = getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
       
        File headPIC = new File(HeadPicShowerActivity.this.getFilesDir(),sharedPreferences.getString("studentID", null) + ".png");
		BigHeadPic = (ImageView) findViewById(R.id.BigHeadPic);
		if(headPIC.canRead()){
			BigHeadPic.setImageURI(Uri.fromFile(headPIC));//.setImageBitmap(BitmapFactory.decodeFile(headPIC.getPath()));
		}else {
			BigHeadPic.setImageResource(R.drawable.user);
		}

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        finish();
        return true;
    }

}