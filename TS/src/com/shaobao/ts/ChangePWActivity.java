package com.shaobao.ts;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
import com.shaobao.ts.util.DisplayUtil;
import com.shaobao.ts.util.SSLSocketUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePWActivity extends Activity implements OnClickListener
{
	private static final String TAG = "ChangePWActivity";
	private Button btChangePW;
	private Button btCancel;
	private EditText etNewPW;
	private EditText etConfirmNewPW;
	private ImageView ivClearPW;
	private ImageView ivClearConfirmPW;
	private ProgressDialog progressDialog;
	private TextView tv_result;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pw);
		btChangePW = (Button)findViewById(R.id.btn_change_pw);
		etNewPW = (EditText)findViewById(R.id.et_put_pw);
		etConfirmNewPW = (EditText)findViewById(R.id.et_put_pw2);
		ivClearPW = (ImageView)findViewById(R.id.iv_clear_pw);
		ivClearConfirmPW = (ImageView)findViewById(R.id.iv_clear_pw2);
		tv_result = (TextView)findViewById(R.id.tv_result);
		btCancel = (Button)findViewById(R.id.btn_cancel); 
		btCancel.setOnClickListener(this);
		btChangePW.setOnClickListener(this);
		ivClearPW.setOnClickListener(this);
		ivClearConfirmPW.setOnClickListener(this);
		btChangePW.setEnabled(false);
		if (progressDialog == null)
    	{
			progressDialog =new ProgressDialog(this, R.style.ProgressDialog_Theme);
		}
		etNewPW.addTextChangedListener(new TextWatcher()
    	{
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				CharSequence newPW = etNewPW.getText();
				CharSequence confirmPW = etConfirmNewPW.getText();
				textChanged(newPW ,confirmPW);
//				Log.v(TAG, "start:" + arg1);
				if (arg0.length() > 0) 
				{
					ivClearPW.setVisibility(View.VISIBLE);
				}else 
				{
					ivClearPW.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		etConfirmNewPW.addTextChangedListener(new TextWatcher()
    	{
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				CharSequence newPW = etNewPW.getText();
				CharSequence confirmPW = etConfirmNewPW.getText();
				textChanged(newPW ,confirmPW);
//				Log.v(TAG, "start:" + arg1);
				if (arg0.length() > 0) 
				{
					ivClearConfirmPW.setVisibility(View.VISIBLE);
				}else 
				{
					ivClearConfirmPW.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
	}
	  public void textChanged(CharSequence user, CharSequence pw ) {
			// TODO 如果输入框木有，就隐藏delbtn
			if(user.length() > 0 && pw.length() > 0)
			{
				
				btChangePW.setEnabled(true);
//				ivClearUserImageView.setVisibility(View.VISIBLE);
//					 
				int resId = getImageID( "ts_btn_enable");
				if (resId > 0) 
				{
					btChangePW.setBackgroundResource(resId);
				}
			}else 
			{
				btChangePW.setEnabled(false);
//				ivClear.setVisibility(View.GONE);
				int resId = getImageID("ts_btn_disenable");
				if (resId > 0)
				{
					btChangePW.setBackgroundResource(resId);
				}
			}
		}
	  
	  public int getImageID(String str)
	    {
	    	Class drawable = R.drawable.class;
	    	try {
				Field field = drawable.getDeclaredField(str);
				try {
					int id = field.getInt(drawable);
					return id;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 0;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 0;
				}
				
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
	    	
	    }
	@Override
	public void onClick(View v)
	{	
		int id = v.getId();
		switch (id) {
		case R.id.btn_change_pw:
//			Log.v("TAG", "CHANGEING.....");
			CharSequence newPW = etNewPW.getText();
			CharSequence confirmPW = etConfirmNewPW.getText();
			String newStr = newPW.toString();
			String confirmStr = confirmPW.toString();
			if (newStr.equals(confirmStr)) 
			{
				change(getApplicationContext() , OrderService.userEntity.getToken(), OrderService.userEntity.getName(), OrderService.userEntity.getPw(), confirmStr);
			}else {
				tv_result.setText(R.string.ts_not_same);
			}
			break;
		case R.id.iv_clear_pw:
			etNewPW.setText("");
			break;
		case R.id.iv_clear_pw2:
			etConfirmNewPW.setText("");
			break;
		case R.id.btn_cancel:
			finish();
			break;
			
		default:
			break;
		}
		// TODO Auto-generated method stub
		
	}
	public void change(Context context , String token , String name ,String oldPW , String newPW)
	{
		String url = context.getString(R.string.url) +"mobile/changePassword";
		

		
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();  
		dataList.add(new BasicNameValuePair("action", "MobileChangePassword"));
		
//		dataList.add(new BasicNameValuePair("action", "MobileSync"));

		dataList.add(new BasicNameValuePair("employeeId",name));
		dataList.add(new BasicNameValuePair("old",oldPW));
		dataList.add(new BasicNameValuePair("new", newPW));
		dataList.add(new BasicNameValuePair("token", token));
		
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
//			SSLSocketFactory sslSocketFactory = 
			client.setSSLSocketFactory(SSLSocketUtil.createSSLSocketFactory());
			client.setTimeout(8000);
			client.post(context, url, entity, null,  new JsonHttpResponseHandler()
			{
				
				@Override
				public void onStart() 
				{
	         
					progressDialog.show();
					progressDialog.setContentView(R.layout.dialog_single_ps);
	          

					// TODO Auto-generated method stub
					super.onStart();
				}
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.v(TAG, "response:" + response.toString());
					String result = getString(R.string.ts_verify_failed);
			
					if (response != null) 
					{
						try {
							result = response.getString("msg");
							
							if (result.equals("成功"))
							{
								finish();
								
							}else {
								tv_result.setText(result);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					progressDialog.dismiss();
					DisplayUtil.toast(getApplicationContext(), result);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable)
				{
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, responseString, throwable);
					closeDialog();
					tv_result.setText(R.string.ts_net_exception);
					Log.v(TAG, "response:" + responseString);
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) 
				{
					// TODO Auto-generated method stub
					closeDialog();
					tv_result.setText(R.string.ts_net_exception);
//					Toast.makeText(getApplicationContext(),getString(R.string.ts_login_faild), 4*1000).show();
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}
			});
	}

	private void closeDialog()
	{
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
	}
}
