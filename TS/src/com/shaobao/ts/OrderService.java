package com.shaobao.ts;

import java.util.ArrayList;

import com.shaobao.ts.entity.OrderEntity;
import com.shaobao.ts.entity.UserEntity;
import com.shaobao.ts.util.ObtainOrderStatusThread;
import com.shaobao.ts.view.InfoFlag;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class OrderService extends Service
{
	private static final String TAG = "OrderService";
	public static final int MSG_FRESH_LIST = 1;
	public static final int MSG_NOTIFICATION = 2;
	public static UserEntity userEntity = new UserEntity();
	public static ArrayList<OrderEntity> orderEntitys =new ArrayList<OrderEntity>();
	

//	
	@Override
	public IBinder onBind(Intent intent) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate()
	{
		Log.v(TAG, "onCreate");
//		orderEntitys = new ArrayList<OrderEntity>();
		ObtainOrderStatusThread obtainOrderStatusThread = new ObtainOrderStatusThread(getApplicationContext());
		obtainOrderStatusThread.start();
		// TODO Auto-generated method stub
		super.onCreate();
	}

}
