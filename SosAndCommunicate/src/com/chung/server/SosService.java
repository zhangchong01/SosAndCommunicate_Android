package com.chung.server;

import com.chung.tools.SensorManagerHelper;
import com.chung.tools.SensorManagerHelper.OnShakeListener;

import java.util.HashMap;

import com.chung.sosandcommunicate.LoginActivity;
import com.chung.sosandcommunicate.R;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;

@SuppressLint("UseSparseArrays")
public class SosService extends Service {
	private int flag = 0;

	private SoundPool mSoundPool = null;
	private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();

	private Camera camera;
	private Parameters parameters;
	
	private final String TAG = "SosService";

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
		mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
		soundID.put(1, mSoundPool.load(this, R.raw.sos, 1));
		SensorManagerHelper smh = new SensorManagerHelper(LoginActivity.loginContext);
		smh.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
				// TODO Auto-generated method stub
				flag++;
				if (flag == 3) {
					startSOS(null);
					playSOS();
				}
			}
		});
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
		closeSOS(null);
		stopSOS();
	}

	// 主开启
	public void startSOS(View v) {
		handler.post(startThread);
		handler.post(closeThread);
	}

	// 关闭
	public void closeSOS(View v) {
		handler.removeCallbacks(startThread);
		handler.removeCallbacks(closeThread);
		flashclose();
		camera.stopPreview();
		camera.release();
		camera = null;
		Log.i("SOSlight", "closed");
	}

	private void flashopen() {
		if (camera == null) {
			camera = Camera.open();
		}
		parameters = camera.getParameters();

		parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);

		camera.setParameters(parameters);
		camera.startPreview();
	}

	private void flashclose() {
		if (camera == null) {
			camera = Camera.open();
		}
		parameters = camera.getParameters();

		parameters.setFlashMode(Parameters.FLASH_MODE_OFF);

		camera.setParameters(parameters);
	}

	Runnable startThread = new Runnable() {
		// 将要执行的操作写在线程对象的run方法当中
		public void run() {
			flashopen();
			try {
				Thread.sleep(100);
				flashclose();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.post(startThread);
		}

	};

	Runnable closeThread = new Runnable() {
		// 将要执行的操作写在线程对象的run方法当中
		public void run() {
			flashclose();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.post(closeThread);
		}

	};

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	};

	@SuppressWarnings("deprecation")
	public void playSOS() {
		AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		//开启铃声+震动情景模式
		am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,AudioManager.VIBRATE_SETTING_ON);
        am.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_ON);
        
		// 最大音量
		float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		mSoundPool.play(soundID.get(1), // 声音资源
				audioMaxVolumn, // 左声道
				audioMaxVolumn, // 右声道
				1, // 优先级，0最低
				-1, // 循环次数，0是不循环，-1是永远循环
				1); // 回放速度，0.5-2.0之间。1为正常速度
	}

	public void stopSOS() {
		mSoundPool.pause(soundID.get(1));
		Log.i("SOSsound", "stoped");
	}

//	public void catchVideo(){
//		SurfaceView mpreview = (SurfaceView) LoginActivity.loginContext.findViewById(R.id.camera_preview);
//
//		SurfaceHolder mSurfaceHolder = mpreview.getHolder();
//
//		mSurfaceHolder.addCallback(this);
//
//		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//	}
	
}