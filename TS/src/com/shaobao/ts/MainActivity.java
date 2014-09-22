package com.shaobao.ts;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.KeyStore;
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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.*;
import com.shaobao.ts.R.string;
import com.shaobao.ts.entity.StatusEntity;
import com.shaobao.ts.entity.UserEntity;
import com.shaobao.ts.util.ChangePWUtil;
import com.shaobao.ts.util.SSLSocketUtil;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener , OnCheckedChangeListener{

	private static final String TAG = "MainActivity";
	
	public static  String Token = "";
	public static final String TS_PS_USER_CONFIG = "user_config";
	public static final String TS_FIELD_ISREMEMBER = "ts_is_remember";
	public static final String TS_FIELD_USER = "ts_user";
	public static final String TS_FIELD_PW = "ts_pw";
	public static final String TS_ORDER_ID = "ts_order_id";
	private Button btLogin = null;
	private ImageView ivClearUserImageView = null;
	private ImageView ivClearPWImageView = null;
	private EditText etInputUser = null;
	private EditText etInputPW = null;
	private Button btChangePW = null;
	private ProgressDialog loginDialog = null;
	private CheckBox cbRemeber = null;
	private SharedPreferences sp = null;
	private boolean isRemember = false; //0= no 1= yes
	private int timeout = 10 *1000;
	
	

	
	
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        	
        initView();

    }
    private void initView()
    {
    	btLogin = (Button)findViewById(R.id.btn_login); 	
    	ivClearUserImageView =(ImageView)findViewById(R.id.iv_clear_user);
    	ivClearPWImageView =(ImageView)findViewById(R.id.iv_clear_pw);
    	etInputPW = (EditText)findViewById(R.id.et_put_pw);
    	etInputUser = (EditText)findViewById(R.id.et_put_user);
    	cbRemeber = (CheckBox)findViewById(R.id.cb_remmber_pw);
    	btChangePW=  (Button)findViewById(R.id.btn_test);
    	
    	
    	btChangePW.setOnClickListener(this);
    	cbRemeber.setOnCheckedChangeListener(this);
    	btLogin.setOnClickListener(this);
    	ivClearPWImageView.setOnClickListener(this);
    	ivClearUserImageView.setOnClickListener(this);
    	
    	if (loginDialog == null)
    	{
    		  loginDialog =new ProgressDialog(this, R.style.ProgressDialog_Theme);
		}
    	btLogin.setEnabled(false);
    	btChangePW.setEnabled(false);
    	initUserInfo();
    	etInputPW.addTextChangedListener(new TextWatcher()
    	{
    		
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				CharSequence user = etInputUser.getText();
				CharSequence pw = etInputPW.getText();
				textChanged(user ,pw);
//				Log.v(TAG, "start:" + arg1);
				if (arg0.length() > 0) 
				{
					ivClearPWImageView.setVisibility(View.VISIBLE);
				}else 
				{
					ivClearPWImageView.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	etInputUser.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				CharSequence user = etInputUser.getText();
				CharSequence pw = etInputPW.getText();
				textChanged(user ,pw);
//				Log.v(TAG, "start:" + arg1);
				if (arg0.length() > 0) 
				{
					ivClearUserImageView.setVisibility(View.VISIBLE);
				}else 
				{
					ivClearUserImageView.setVisibility(View.GONE);
				}
				etInputPW.setText("");
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
    public void initUserInfo()
    {
    	if (sp == null) 
    	{
			sp = getSharedPreferences(TS_PS_USER_CONFIG,Context.MODE_PRIVATE);
			isRemember = sp.getBoolean(TS_FIELD_ISREMEMBER, false);
			if (isRemember)
			{
				cbRemeber.setChecked(true);
				String user = sp.getString(TS_FIELD_USER, "");
				String pw = sp.getString(TS_FIELD_PW, "");
				if (user != null && pw != null && user != "" && pw != null)
				{
					etInputUser.setText(user);
					etInputPW.setText(pw);
					btLogin.setEnabled(true);
					btChangePW.setEnabled(true);
					ivClearUserImageView.setVisibility(View.VISIBLE);
					ivClearPWImageView.setVisibility(View.VISIBLE);
//						 
					int resId = getImageID( "ts_btn_enable");
					if (resId > 0) {
						btLogin.setBackgroundResource(resId);
						btChangePW.setBackgroundResource(resId);
					}
				}
			}
			
		}
    }
    public void textChanged(CharSequence user, CharSequence pw ) {
		// TODO 如果输入框木有，就隐藏delbtn
		if(user.length() > 0 && pw.length() > 0)
		{
			btLogin.setEnabled(true);
			btChangePW.setEnabled(true);
//			ivClearUserImageView.setVisibility(View.VISIBLE);
//				 
			int resId = getImageID( "ts_btn_enable");
			if (resId > 0) {
				btLogin.setBackgroundResource(resId);
				btChangePW.setBackgroundResource(resId);
			}
		}else 
		{
			btLogin.setEnabled(false);
			btChangePW.setEnabled(false);
//			ivClear.setVisibility(View.GONE);
			int resId = getImageID("ts_btn_disenable");
			if (resId > 0) {
				btLogin.setBackgroundResource(resId);
				btChangePW.setBackgroundResource(resId);
			}
		}
	}
   public void clearBtnChange(CharSequence str , ImageView iv)
   {
	   if (str.length() >0)
	   {
		   iv.setVisibility(View.VISIBLE);
	   }else
	   {
		  iv.setVisibility(View.GONE);
	   }
   }
	@Override
	public void onClick(View arg0)
	{
		int id = arg0.getId();
		switch (id) {
		case R.id.btn_login:
			if (btLogin.isEnabled()) 
			{
				login(0);
			}
//			Toast.makeText(this, "login...", Toast.LENGTH_LONG).show();
//		
			break;
		case R.id.iv_clear_pw:
			etInputPW.setText("");
			
			break;
		case R.id.iv_clear_user:
			etInputUser.setText("");
			break;
		case R.id.btn_test:
			login(1);
//			post();
//			Intent cameraIntent = new Intent(this, ReportActivity.class);
//			startActivity(cameraIntent);
			
//			Intent cameraIntent = new Intent(this, ChangePWActivity.class);
//			startActivity(cameraIntent);
			break;
		
		default:
			break;
		}
		// TODO Auto-generated method stub
	}
	private void login(int type)
	{
		String user = etInputUser.getText().toString();
		String pw = etInputPW.getText().toString();
//		userEntity.setName(user);
//		userEntity.setPw(pw);
		if (user == null || pw == null)
		{
			Toast.makeText(this,R.string.user_and_pw_null, 4*1000).show();
			return;
		}
//		Toast.makeText(this,R.string.user_and_pw_null, 4*1000).show();
		loginPost(type , user , pw);
	}

	private  void loginPost(final int type , final String user ,final String pw)
	{  
		Log.v(TAG, "thread_name1:" + Thread.currentThread().getName());

		String url = getString(R.string.url) +"mobile/auth";
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();  
		dataList.add(new BasicNameValuePair("action", "MobileAuthenticate"));
		dataList.add(new BasicNameValuePair("employeeId",user));
		dataList.add(new BasicNameValuePair("password",pw));
		
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
		SSLSocketFactory sslSocketFactory = SSLSocketUtil.createSSLSocketFactory();
		client.setSSLSocketFactory(sslSocketFactory);
		client.setTimeout(timeout);
		client.post(this, url, entity, null,  new JsonHttpResponseHandler()
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
				Log.v(TAG, "response:" + response.toString());
				String result = getString(R.string.ts_verify_failed);
				String role = null;
				String userName = null;
				if (response != null) 
				{
					try {
						result = response.getString("msg");
						
						if (result.equals("成功"))
						{
							JSONObject obj = response.getJSONObject("data");
							Log.v(TAG, "data:" + obj.toString());
							 role = obj.getString("role");
							 userName = obj.getString("name");
							Token = obj.getString("token");
							OrderService.userEntity.setType(role);
							OrderService.userEntity.setToken(Token);
							OrderService.userEntity.setName(user);
							OrderService.userEntity.setPw(pw);
							success(type);
							Toast.makeText(getApplicationContext(),userName + getString(R.string.ts_verify) +result, 4*1000).show();
						}else {
							Toast.makeText(getApplicationContext(),getString(R.string.ts_verify) +result, 4*1000).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				loginDialog.dismiss();
				
				

			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				loginDialog.dismiss();
				Toast.makeText(getApplicationContext(),getString(R.string.ts_verify_failed), 4*1000).show();
				Log.v(TAG, "response:" + responseString);
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				loginDialog.dismiss();
				Toast.makeText(getApplicationContext(),getString(R.string.ts_verify_failed), 4*1000).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
                // Do something with the response
	}  
	
	private void success(int type)
	{
//		Log.v(TAG, "type:" + type + " token:" + token);
		if (isRemember)
		{
			if (sp == null)
			{
				sp = getSharedPreferences(TS_PS_USER_CONFIG, Context.MODE_PRIVATE);
				
			}
			Editor editor = sp.edit();
			editor.putString(TS_FIELD_USER, OrderService.userEntity.getName());
			editor.putString(TS_FIELD_PW, OrderService.userEntity.getPw());
//			editor.putString(, value)
			editor.commit();
		}
		
		if (type == 0) {
			Intent service = new Intent(getApplicationContext(), OrderService.class);
			startService(service);
			Intent intent = new Intent(getApplicationContext(), DriverActivity.class);
			startActivity(intent);
			
			this.finish();
		}else
		{
			Intent cameraIntent = new Intent(this, ChangePWActivity.class);
			startActivity(cameraIntent);
		}
		
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (loginDialog != null)
		{
			loginDialog.dismiss();
			loginDialog = null;
		}
		super.onDestroy();
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
	public void onCheckedChanged(CompoundButton arg0, boolean arg1)
	{
		// TODO Auto-generated method stub
		int id = arg0.getId();
		if (id == R.id.cb_remmber_pw)
		{
			isRemember = arg1;
			if (sp == null)
			{
				sp = getSharedPreferences(TS_PS_USER_CONFIG, Context.MODE_PRIVATE);
				
			}
			Editor editor = sp.edit();
			editor.putBoolean(TS_FIELD_ISREMEMBER, isRemember);
			editor.commit();
			
			
			
		}
		
	}

	

}
