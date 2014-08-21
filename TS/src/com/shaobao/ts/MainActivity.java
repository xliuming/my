package com.shaobao.ts;


import java.lang.reflect.Field;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{

	private static final String TAG = "MainActivity";
	private Button btLogin = null;
	private ImageView ivClearUserImageView = null;
	private ImageView ivClearPWImageView = null;
	private EditText etInputUser = null;
	private EditText etInputPW = null;
	private Button btTest = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView()
    {
    	btLogin = (Button)findViewById(R.id.btn_login);
    	
    	
    	
    	ivClearUserImageView =(ImageView)findViewById(R.id.iv_clear_user);
    	ivClearPWImageView =(ImageView)findViewById(R.id.iv_clear_pw);
    	etInputPW = (EditText)findViewById(R.id.et_put_pw);
    	etInputUser = (EditText)findViewById(R.id.et_put_user);
    	
    	btTest=  (Button)findViewById(R.id.btn_test);
    	
    	
    	btTest.setOnClickListener(this);
    	
    	btLogin.setOnClickListener(this);
    	ivClearPWImageView.setOnClickListener(this);
    	ivClearUserImageView.setOnClickListener(this);
    	etInputPW.addTextChangedListener(new TextWatcher() {
			
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
					int arg3) {
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
			btLogin.setEnabled(true);
//			ivClearUserImageView.setVisibility(View.VISIBLE);
//				 
			int resId = getImageID( "ts_btn_enable");
			if (resId > 0) {
				btLogin.setBackgroundResource(resId);
			}
		}else 
		{
			btLogin.setEnabled(false);
//			ivClear.setVisibility(View.GONE);
			int resId = getImageID("ts_btn_disenable");
			if (resId > 0) {
				btLogin.setBackgroundResource(resId);
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
			Toast.makeText(this, "login...", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this, DriverActivity.class);
//			Intent intent = new Intent(this, HistoryDataActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_clear_pw:
			etInputPW.setText("");
			
			break;
		case R.id.iv_clear_user:
			etInputUser.setText("");
			break;
		case R.id.btn_test:
			
			Intent cameraIntent = new Intent(this, CameraActivity.class);
			startActivity(cameraIntent);
			break;
		
		default:
			break;
		}
		// TODO Auto-generated method stub
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



}
