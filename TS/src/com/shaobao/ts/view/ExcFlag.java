package com.shaobao.ts.view;

import android.os.Bundle;

import com.shaobao.ts.R;
import com.umeng.analytics.MobclickAgent;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExcFlag extends Fragment {

	private static final String TAG = "ExcFlag";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
//		return super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment2, container, false);
	}

	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("MainScreen"); //统计页面
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("MainScreen"); 
	}
}
