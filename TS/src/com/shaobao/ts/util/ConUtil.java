package com.shaobao.ts.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ConUtil 
{
	private ConStatusListenner conStatusListenner;
	public interface ConStatusListenner
	{
		void onStart();
		void onSuccess(int statusCode, Header[] headers,
				JSONObject response);
		void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable);
		void onfailure(int statusCode, Header[] headers,
				Throwable throwable, JSONObject errorResponse);
		void onError();
	}
	
	public ConStatusListenner getConStatusListenner() {
		return conStatusListenner;
	}

	public void setConStatusListenner(ConStatusListenner conStatusListenner) {
		this.conStatusListenner = conStatusListenner;
	}

	public void post(Context context ,String url , int timeout ,HttpEntity entity )
	{
		AsyncHttpClient client = new AsyncHttpClient();
		client.setSSLSocketFactory(SSLSocketUtil.createSSLSocketFactory());
		client.setTimeout(8000);
		client.post(context, url, entity, null,  new JsonHttpResponseHandler()
		{
			
			@Override
			public void onStart() 
			{
	     
				conStatusListenner.onStart();
//	            loginDialog.show();
//	            loginDialog.setContentView(R.layout.dialog_single_ps);
	      

				// TODO Auto-generated method stub
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				conStatusListenner.onSuccess(statusCode, headers, response);
//				Log.v(TAG, "response:" + response.toString());
//				String result = getString(R.string.ts_login_faild);
//				String role = null;
//				String userName = null;
//				if (response != null) 
//				{
//					try {
//						result = response.getString("msg");
//						
//						if (result.equals("成功"))
//						{
//							JSONObject obj = response.getJSONObject("data");
//							Log.v(TAG, "data:" + obj.toString());
//							 role = obj.getString("role");
//							 userName = obj.getString("name");
//							String token = obj.getString("token");
//							userEntity.setType(role);
//							userEntity.setToken(token);
//							success();
//						}
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				loginDialog.dismiss();
//				Toast.makeText(getApplicationContext(),userName +  "登录"+result, 4*1000).show();

			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable)
			{
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				conStatusListenner.onFailure(statusCode, headers, responseString, throwable);
//				loginDialog.dismiss();
//				Log.v(TAG, "response:" + responseString);
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) 
			{
				// TODO Auto-generated method stub
//				loginDialog.dismiss();
//				Toast.makeText(getApplicationContext(),getString(R.string.ts_login_faild), 4*1000).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
				conStatusListenner.onfailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}
	public void toast(Context context,String content)
	{
		Toast.makeText(context, content, 4*1000).show();
		
	}
	
}
