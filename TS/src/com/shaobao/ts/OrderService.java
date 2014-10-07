package com.shaobao.ts;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.shaobao.ts.entity.OrderEntity;
import com.shaobao.ts.entity.UserEntity;
import com.shaobao.ts.util.CommonUtil;
import com.shaobao.ts.util.DisplayUtil;
import com.shaobao.ts.util.ObtainOrderStatusThread;
import com.shaobao.ts.util.SSLSocketUtil;
import com.shaobao.ts.view.InfoFlag;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class OrderService extends Service
{
	
	private static final String TAG = "OrderService";
	public static final int MSG_FRESH_LIST = 1;
	public static final int MSG_NOTIFICATION = 2;
	public static UserEntity userEntity = new UserEntity();
	public static boolean isend = false;
	public static ArrayList<OrderEntity> orderEntitys =new ArrayList<OrderEntity>();
	public static boolean release = false;
	
	
	private static int TIMEOUT = 10 * 1000;
	private final Handler handler = new Handler();  
	private final Runnable task = new Runnable() {  
		  
	        @Override  
	        public void run() {  
	            // TODO Auto-generated method stub  
	        		if (isend) 
	        		{
	        			obtainUserInfo();
					}
	        	
	                handler.postDelayed(this, 3000 );  
	               
	        }  
	    };  
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
		release = false;
//		ObtainOrderStatusThread obtainOrderStatusThread = new ObtainOrderStatusThread(getApplicationContext());
//		obtainOrderStatusThread.start();
		handler.post(task);
		obtainUserInfo();
		// TODO Auto-generated method stub
		super.onCreate();
	}
	private void obtainUserInfo()
	{
		String url = getString(R.string.url) +"/mobile/sync";
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();  
		dataList.add(new BasicNameValuePair("action", "MobileSync"));
		dataList.add(new BasicNameValuePair("employeeId",userEntity.getName()));
		dataList.add(new BasicNameValuePair("token", userEntity.getToken()));

		
		
		HttpEntity entity = null;
		try
		{
			entity = new UrlEncodedFormEntity(dataList);
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}  
		
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		SSLSocketFactory sslSocketFactory = SSLSocketUtil.createSSLSocketFactory();
		client.setSSLSocketFactory(sslSocketFactory);
		client.setTimeout(TIMEOUT);
		client.post(this, url, entity, null,  new JsonHttpResponseHandler()
		{
			
			@Override
			public void onStart() 
			{
         
				isend = false;
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				isend = true;
				Log.v("orderService", "response:" + response.toString());
				String result = getString(R.string.ts_sync_failed);

				
				if (CommonUtil.parserOrder(response, orderEntitys)) 
				{
					
				}else
				{
//					DisplayUtil.toast(getApplicationContext(),getString(R.string.ts_data_exception) );
				}

			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.v(TAG, "throwable:" +throwable.toString());
				isend = true;;
//				Toast.makeText(getApplicationContext(),getString(R.string.ts_sync_failed), 4*1000).show();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) 
			{
				Log.v(TAG, "throwable:" +throwable.toString());
				isend = true;
//				Toast.makeText(getApplicationContext(),getString(R.string.ts_sync_failed), 4*1000).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
                // Do something with the response
	}

}
