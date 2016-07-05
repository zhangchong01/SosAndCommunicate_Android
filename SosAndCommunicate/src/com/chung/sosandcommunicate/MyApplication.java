package com.chung.sosandcommunicate;

import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {
	public static String RegistrationID;
	private static MyApplication application = null;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		
        JPushInterface.setDebugMode(true);//设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
        RegistrationID = JPushInterface.getRegistrationID(this);
        Log.i("RegistrationID", RegistrationID);
		
	}

	public static MyApplication getApplication() {
		return application;
	}

}