package com.shaobao.ts.app;

import android.app.Application;

public class CrashApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler catchHandler = CrashHandler.getInstance();
		catchHandler.init(getApplicationContext());
	}
}

