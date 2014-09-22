package com.shaobao.ts.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.shaobao.ts.R;


//@RequestMapping(value = "/mobile/image/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	public @ResponseBody String imageAdd(@RequestParam("action") String action, @RequestParam("employeeId") String employeeId,
//			@RequestParam("token") String token, @RequestParam("orderId") String orderId, @RequestParam("picture") String base64Picture,
//			@RequestParam("x") double x, @RequestParam("y") double y, @RequestParam("description") String description,
//			@RequestParam("description") String dateTaken /* yyyy-MM-dd HH:mm:ss */, HttpServletRequest request) throws JSONException, ChannyException,
//			ParseException {
public class ChangePWUtil 
{
	public Context context;
	private static String TAG = "ChangePWUtil";
	public static void change(Context context , String token , String name ,String orderID )
	{
//		String url = context.getString(R.string.url) +"mobile/changePassword";
		
//		String url = context.getString(R.string.url) +"/mobile/queryStatus";
		String url = context.getString(R.string.url) +"mobile/image/add";

		
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();  		
		dataList.add(new BasicNameValuePair("action", "MobileImageAdd"));
		dataList.add(new BasicNameValuePair("employeeId",name));
		dataList.add(new BasicNameValuePair("token", token));
		dataList.add(new BasicNameValuePair("orderId",orderID));
		dataList.add(new BasicNameValuePair("picture", bitmaptoString(context)));
		dataList.add(new BasicNameValuePair("description", "avs"));
		dataList.add(new BasicNameValuePair("dateTaken", "2014-09-16 09:33:40"));
		dataList.add(new BasicNameValuePair("x", "100"));
		dataList.add(new BasicNameValuePair("y","200"));
		
		System.out.println("employeeId:" + name +" token:" +token +"orderId" + orderID);
//		System.out.println("picture:" + bitmaptoString(context));
		
		
		 HttpEntity entity = null;
		try
		{
			entity = new UrlEncodedFormEntity(dataList);
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}  
			
			AsyncHttpClient client = new AsyncHttpClient();
			client.setSSLSocketFactory(SSLSocketUtil.createSSLSocketFactory());
			client.setTimeout(8000);
			client.post(context, url, entity, null,  new JsonHttpResponseHandler()
			{
				
				@Override
				public void onStart() 
				{
	         
//	                loginDialog.show();
//	                loginDialog.setContentView(R.layout.dialog_single_ps);
	          

					// TODO Auto-generated method stub
					super.onStart();
				}
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.v(TAG, "response3:" + response.toString());
//					String result = getString(R.string.ts_login_faild);
//					String role = null;
//					String userName = null;
//					if (response != null) 
//					{
//						try {
//							result = response.getString("msg");
//							
//							if (result.equals("成功"))
//							{
//								JSONObject obj = response.getJSONObject("data");
//								Log.v(TAG, "data:" + obj.toString());
//								 role = obj.getString("role");
//								 userName = obj.getString("name");
//								String token = obj.getString("token");
//								userEntity.setType(role);
//								userEntity.setToken(token);
//								success();
//							}
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					loginDialog.dismiss();
//					Toast.makeText(getApplicationContext(),userName +  "登录"+result, 4*1000).show();

				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable)
				{
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseString, throwable);
//					loginDialog.dismiss();
					Log.v(TAG, "response:" + responseString);
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) 
				{
					Log.v(TAG, "response2:" + errorResponse);
					// TODO Auto-generated method stub
//					loginDialog.dismiss();
//					Toast.makeText(getApplicationContext(),getString(R.string.ts_login_faild), 4*1000).show();
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}
			});
	}
	
    public static String bitmaptoString(Context context) {

        // 将Bitmap转换成字符串
    	 Resources res = context.getResources();
    	
    	  Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);
    	
        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bmp.compress(CompressFormat.PNG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;

    }
    public static String bitmaptoString(String path) {

        // 将Bitmap转换成字符串
//    	 Resources res = context.getResources();
 
    	  Bitmap bmp = BitmapFactory.decodeFile(path);
    	
        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bmp.compress(CompressFormat.PNG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;

    }
    
    public static String bitmaptoString(byte[] data) {

        // 将Bitmap转换成字符串
//    	 Resources res = context.getResources();
 
    	int length = data.length;
    	  Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, length);
    	
        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bmp.compress(CompressFormat.PNG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;

    }
    

}
