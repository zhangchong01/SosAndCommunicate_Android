package com.chung.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class BaseFragment extends Fragment {
	private final String TAG = this.getClass().getSimpleName();
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, " >>> onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, " > onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, " - onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.i(TAG, " --- onDetach");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, " >> onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, " -- onStop");
	}
	
}
