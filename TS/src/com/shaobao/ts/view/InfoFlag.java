package com.shaobao.ts.view;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.shaobao.ts.CameraActivity;
import com.shaobao.ts.OrderService;
import com.shaobao.ts.R;
import com.shaobao.ts.ReportActivity;
import com.shaobao.ts.R.string;
import com.shaobao.ts.dao.PictureDAO;
import com.shaobao.ts.entity.PictureEntity;
import com.shaobao.ts.entity.StatusEntity;
import com.shaobao.ts.util.ChangePWUtil;
import com.shaobao.ts.util.CommonUtil;
import com.shaobao.ts.util.DisplayUtil;
import com.shaobao.ts.util.IOUtil;
import com.shaobao.ts.util.SSLSocketUtil;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class InfoFlag extends Fragment implements OnItemClickListener {	
	private static int TIMEOUT = 30 * 1000;
	private static final String TAG= "InfoFlag";
	public static final int RESULT_CODE_CAMERA = 1;
	public static final String PICTURE_DATA = "picture_data";
	public static final String ORDER_NUMBER = "order_number";
	public static final String ORDER_STATUS = "order_status";
	
//	private static String oid;
	private final Handler handler = new Handler();  
	private ProgressDialog loginDialog = null;
	private Context context;
	  
    private final Runnable task = new Runnable() {  
  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  

        		if (orderAdapter != null) 
        		{
					orderAdapter.notifyDataSetChanged();
//					ListView.notifyAll();
					
				}
                handler.postDelayed(this, 3000);  
//           
        }  
    };  

	private ListView ListView = null;
	public  OrderAdapter orderAdapter = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{

		context = getActivity().getApplicationContext();
		View parentView = inflater.inflate(R.layout.fragment1, container, false);
		
		handler.post(task);
		initView(parentView);
		
		return parentView;
		
		// return super.onCreateView(, container, savedInstanceState);
	}
	private void initView(View parentView )
	{
		if (loginDialog == null)
    	{
    		  loginDialog =new ProgressDialog(getActivity(), R.style.ProgressDialog_Theme);
		}
		ListView = (ListView)parentView.findViewById(R.id.lv);
		ListView.setOnItemClickListener(this);
		orderAdapter = new OrderAdapter(OrderService.orderEntitys, getActivity());
		ListView.setAdapter(orderAdapter);
//		ChangePWUtil.change(getActivity(), OrderService.userEntity.getToken(),  OrderService.userEntity.getName(), "10");
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		// TODO Auto-generated method stub
		System.out.println("onItemClick arg2:" + arg2 + " arg3:"+ arg3);
//		
		String oID = OrderService.orderEntitys.get(arg2).getId();
	
//		System.out.println("oid:" + oID);
		dialog(oID , arg2);
		

	}
	
	
	private void changeState(final String orderID , final int index) 
	{
		
		String url = getString(R.string.url) +"mobile/updateOrderStatus";
		Log.v("tag", "url:" + url + "orderID:" + orderID);
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();  
		dataList.add(new BasicNameValuePair("action", "MobileUpdateOrderStatus"));
		dataList.add(new BasicNameValuePair("employeeId",OrderService.userEntity.getName()));
		dataList.add(new BasicNameValuePair("token", OrderService.userEntity.getToken()));
		dataList.add(new BasicNameValuePair("orderId", orderID));
		
//		Log.v("test", "employeeId:" + OrderService.userEntity.getName() + " token:" + OrderService.userEntity.getToken() + " orderId:" +orderID);
		
		
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
		client.post(getActivity(), url, entity, null,  new JsonHttpResponseHandler()
		{
			
			@Override
			public void onStart() 
			{
         
                loginDialog.show();
                loginDialog.setContentView(R.layout.dialog_single_ps);
				// TODO Auto-generated method stub
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.v("test", "response:" + response.toString());
				String result = getString(R.string.ts_verify_failed);
//			
				if (response != null) 
				{
					try {
						result = response.getString("msg");
						
						if (result.equals("成功"))
						{
							JSONObject obj = response.getJSONObject("data");
							
							String status = obj.getString("status");
							
							OrderService.orderEntitys.get(index).setStatus(status);
//							orderAdapter.notifyDataSetChanged();
							isTakePicture(status , orderID);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Toast.makeText(getActivity(),result, 4*1000).show();
				
				loginDialog.dismiss();
//				Toast.makeText(getApplicationContext(),userName +  "登录"+result, 4*1000).show();

			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);		
				loginDialog.dismiss();
				Toast.makeText(getActivity(),getActivity().getString(R.string.ts_change_status_failed), 4*1000).show();
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				loginDialog.dismiss();
				Toast.makeText(getActivity(),getActivity().getString(R.string.ts_change_status_failed), 4*1000).show();
		
//				Toast.makeText(getApplicationContext(),getString(R.string.ts_login_failed), 4*1000).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
                // Do something with the response
	
	}
	protected void dialog(final String oid , final int index )
	{
		String status = OrderService.orderEntitys.get(index).getStatus();
		String nextStatus = new StatusEntity().getStatusEntity(status).nextName;
		if (nextStatus.equals("结束"))
		{
			Toast.makeText(getActivity(), getActivity().getString(R.string.ts_order_already_end), 4 *1000).show();
			return;
		}
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage(getString(R.string.confirm_change_status) +"\""+ nextStatus+"\""+ "?");
		builder.setTitle(R.string.hint);
		builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				changeState(oid , index);
				dialog.dismiss();
				
			}

		
		});
		builder.setNegativeButton(R.string.ts_cancel, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				
			}

		
		});
			
		builder.create().show();
	}
	
	private void isTakePicture(String status , String orderNumber)
	{
		
	
		StatusEntity statusEntity = new StatusEntity().getStatusEntity(status);
		if (statusEntity.isTake)
		{
			startCameraActivity(orderNumber , status);
			
		}
		
		
	}
	private void startCameraActivity(String orderNumber ,String status) {
		// TODO Auto-generated method stub
		Intent cameraIntent = new Intent(getActivity(), CameraActivity.class);
		System.out.println("-------infoflag--------------:"+ orderNumber);
		cameraIntent.putExtra(ORDER_NUMBER, orderNumber);
		cameraIntent.putExtra(ORDER_STATUS, status);
		
//		start(cameraIntent);
		startActivityForResult(cameraIntent, RESULT_CODE_CAMERA);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (resultCode == RESULT_CODE_CAMERA)
		{
			String orderNumber = data.getExtras().getString(ORDER_NUMBER);
			String orderStatus = data.getExtras().getString(ORDER_STATUS);
			System.out.println("result-------"+ orderNumber);
			byte[] pictureData = data.getExtras().getByteArray(PICTURE_DATA);
			String pictureName = OrderService.userEntity.getName() +"_" +orderNumber+"_"+OrderService.userEntity.getToken()+ ".jpeg";
			  DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  String date = format.format(new Date());
			if (CommonUtil.getSDCardStatus() )
			{
				
				
				IOUtil.savePicture(context , pictureData, pictureName, orderNumber, date, orderStatus);
//				if (savePicture(pictureData, pictureName)) 
//				{
//				
//					
//					
//					
//					
//					
//				}
			}else 
			{
				DisplayUtil.toast(getActivity(), getString(R.string.ts_onfind_ft));
			}
			
			sendPicture(getActivity(),OrderService.userEntity.getToken(), OrderService.userEntity.getName() , orderNumber , pictureData,date , orderStatus);
//			System.out.println("res:" +res);
		}
		
//		System.out.println("result called!");
		super.onActivityResult(requestCode, resultCode, data);
	}
//	{
//		PictureDAO pictureDAO = new PictureDAO(getActivity());
//		pictureDAO.addPicture(pictureEntity);
//	}
	

	
	public static  void sendPicture(final Context context , String token , String name ,String orderID ,byte[] data ,String date,String des)
	{

		String url = context.getString(R.string.url) +"mobile/image/add";
		
		
	
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();  		
		dataList.add(new BasicNameValuePair("action", "MobileImageAdd"));
		dataList.add(new BasicNameValuePair("employeeId",name));
		dataList.add(new BasicNameValuePair("token", token));
		dataList.add(new BasicNameValuePair("orderId",orderID));
		dataList.add(new BasicNameValuePair("picture", "data:image/jpeg;base64," + DisplayUtil.bitmaptoString(data)));
		dataList.add(new BasicNameValuePair("description", des));
		dataList.add(new BasicNameValuePair("dateTaken", date));
		dataList.add(new BasicNameValuePair("x", "100"));
		dataList.add(new BasicNameValuePair("y","200"));
		
		System.out.println("employeeId:" + name +" token:" +token +"orderId" + orderID);
//		System.out.println("picture:" + bitmaptoString(context));
//		PictureEntity pictureEnti = new PictureEntity(0, name, orderID, "path", date, des, status, longitude, latitude)
		
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
			client.setTimeout(50 * 1000);
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
					
					String result = context.getString(R.string.ts_verify_failed);
//					String role = null;
//					String userName = null;
					if (response != null) 
					{
						try {
							result = response.getString("msg");
							
							if (result.equals("成功"))
							{
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
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






//	private boolean savePicture(byte[] data ,String name , String orderNumber , String date ,String des )
//	{
//		String cameraPath = CommonUtil.getSDCardPath() + "/" + getActivity().getString(R.string.root_file_name) + "/" +  getActivity().getString(R.string.camera_file);
//		createCameraFile(cameraPath);
//		String picturePath = cameraPath +"/"+name;
//		System.out.println("picturePath:" + picturePath);
//		CommonUtil.savePicture(picturePath, data);
//		
//		PictureEntity pictureEntity = new PictureEntity(0, OrderService.userEntity.getName(), orderNumber, picturePath, date, des, PictureEntity.STATUS_ONSEND, 100, 100);
//		savePictureToDatabase(pictureEntity);
//		
//		return true;
//	}
//	private void createCameraFile(String cameraPath )
//	{
//		File file = new File(cameraPath);
//		if (!file.exists()) {
//			if (file.mkdirs())
//			{
//				
//			}else {
//				System.out.println("failed!!!!!!!!!!!!!!");
//			}
//		}
//	}
}
