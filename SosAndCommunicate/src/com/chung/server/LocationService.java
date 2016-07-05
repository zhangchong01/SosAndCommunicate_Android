package com.chung.server;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service {

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListener myListener = new MyLocationListener();

	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	private final String TAG = "LocationService";

	// 必须要实现的方法
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind方法被调用!");
		return null;
	}

	// Service被创建时调用
	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate方法被调用!");
		super.onCreate();

		sharedPreferences = getSharedPreferences("LOCATIONINFO",Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);

		initLocation();
	}
	
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		option.setScanSpan(10000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
//		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
//		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null) {
				return;
			}else {
				List<Poi> list = location.getPoiList();// POI数据
				if (list != null) {
					int index = 0;
					editor.putString("addr", location.getAddrStr());
					for (Poi p : list) {
						editor.putString("place" + String.valueOf(index), p.getName());
						editor.putString("describe" + String.valueOf(index), 
								location.getProvince()+" · "
								+location.getCity()+" · "
								+location.getDistrict()+" · "
								+location.getStreet()
								+location.getStreetNumber());
						index++;
					}
					editor.commit();
				}
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {}

	}

	// Service被启动时调用
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand方法被调用!");
		return super.onStartCommand(intent, flags, startId);
	}

	// Service被关闭之前回调
	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestory方法被调用!");
		super.onDestroy();
	}
}