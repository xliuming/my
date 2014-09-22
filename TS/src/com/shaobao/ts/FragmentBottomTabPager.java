package com.shaobao.ts;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.shaobao.ts.util.ChangePWUtil;
import com.shaobao.ts.util.CommonUtil;
import com.shaobao.ts.util.DisplayUtil;
import com.shaobao.ts.util.IOUtil;
import com.shaobao.ts.util.SSLSocketUtil;
import com.shaobao.ts.view.InfoFlag;
import com.shaobao.ts.view.ExcFlag;
import com.shaobao.ts.view.BillsFlag;
import com.shaobao.ts.view.SetFlag;

import android.R.integer;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

public class FragmentBottomTabPager extends FragmentActivity 
		 {

	// 定义FragmentTabHost对象
	
	private FragmentTabHost mTabHost;
	private RadioGroup mTabRg;
	private ViewPager mViewPage;
	private LinearLayout bt_refresh;
	TabsAdapter mTabsAdapter;
	private static int TIMEOUT = 10 * 1000;
	private String OID = ""; 
	private final Class[] fragments = { InfoFlag.class, ExcFlag.class,
			BillsFlag.class, SetFlag.class };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pager);
		initView();
//		obtainUserInfo();
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}

	private void initView() {

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager());
		mViewPage = (ViewPager) findViewById(R.id.pager);
		mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPage, mTabRg);
		// 得到fragment的个数
		int count = fragments.length;
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragments[i], null);

			mTabsAdapter.addTab(mTabHost.newTabSpec(i + "")
					.setIndicator(i + ""), fragments[i], null);
		}

		mTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.tab_rb_1:
					mTabHost.setCurrentTab(0);
					break;
				case R.id.tab_rb_2:
					mTabHost.setCurrentTab(1);

					break;
				case R.id.tab_rb_3:

					mTabHost.setCurrentTab(2);
					break;
				case R.id.tab_rb_4:

					mTabHost.setCurrentTab(3);
					break;

				default:
					break;
				}
			}
		});
		 mTabHost.setCurrentTab(0);
		bt_refresh = (LinearLayout)findViewById(R.id.bt_one_key_refresh);
		bt_refresh.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v) {
				
				String id  = "";
				String status = "";
				for (int i = 0; i < OrderService.orderEntitys.size(); i++) 
				{
					OrderEntity orderEntity = OrderService.orderEntitys.get(i);
					if (orderEntity.isChoose()) {
						id = orderEntity.getId();
						status = orderEntity.getStatus();
					}
				}
				if (id.equals(""))
				{
					DisplayUtil.toast(getApplicationContext(), "unchoose");
				}else {
//					DisplayUtil.toast(getApplicationContext(), "chooseID:" + id);
					startCameraActivity(id , status);
					
					
				}

			}
		});
	}
	
	
	private void startCameraActivity(String orderNumber ,String status) {
		// TODO Auto-generated method stub
		Intent cameraIntent = new Intent(this, CameraActivity.class);
		System.out.println("-------infoflag--------------:"+ orderNumber);
		cameraIntent.putExtra(InfoFlag.ORDER_NUMBER, orderNumber);
		cameraIntent.putExtra(InfoFlag.ORDER_STATUS, status);
		
//		start(cameraIntent);
		startActivityForResult(cameraIntent, InfoFlag.RESULT_CODE_CAMERA);
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (resultCode == InfoFlag.RESULT_CODE_CAMERA)
		{
			String orderNumber = data.getExtras().getString(InfoFlag.ORDER_NUMBER);
			String orderStatus = data.getExtras().getString(InfoFlag.ORDER_STATUS);
			System.out.println("result-------"+ orderNumber);
			byte[] pictureData = data.getExtras().getByteArray(InfoFlag.PICTURE_DATA);
			String pictureName = OrderService.userEntity.getName() +"_" +orderNumber+"_"+OrderService.userEntity.getToken()+ ".jpeg";
			  DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  String date = format.format(new Date());
			if (CommonUtil.getSDCardStatus() )
			{
				
				
				IOUtil.savePicture(getApplicationContext() , pictureData, pictureName, orderNumber, date, orderStatus);

			}else 
			{
				DisplayUtil.toast(this, getString(R.string.ts_onfind_ft));
			}
			
			InfoFlag.sendPicture(getApplicationContext(),OrderService.userEntity.getToken(), OrderService.userEntity.getName() , orderNumber , pictureData,date , orderStatus);
//			System.out.println("res:" +res);
		
		}
	}
	private void obtainUserInfo()
	{
		String url = getString(R.string.url) +"/mobile/sync";
		List<NameValuePair> dataList = new ArrayList<NameValuePair>();  
		dataList.add(new BasicNameValuePair("action", "MobileSync"));
		dataList.add(new BasicNameValuePair("employeeId",OrderService.userEntity.getName()));
		dataList.add(new BasicNameValuePair("token", OrderService.userEntity.getToken()));

		
		
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
         
//               
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.v("test", "response:" + response.toString());
				String result = getString(R.string.ts_sync_failed);

				if (response != null) 
				{
					try {
						result = response.getString("msg");
						
						if (result.equals("成功"))
						{
							JSONObject obj = response.getJSONObject("data");
							JSONArray obj2 = obj.getJSONArray("orders");
							JSONObject obj3 = obj2.getJSONObject(0);
							OID = obj3.getString("id");
							String status = obj3.getString("status");
//							bt_refresh.setText(status );
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				Toast.makeText(getApplicationContext(),getString(R.string.ts_sync_failed), 4*1000).show();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) 
			{
				Toast.makeText(getApplicationContext(),getString(R.string.ts_sync_failed), 4*1000).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
                // Do something with the response
	}
	
	private void changeState(String orderID) 
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
		client.post(this, url, entity, null,  new JsonHttpResponseHandler()
		{
			
			@Override
			public void onStart() 
			{
         
//                loginDialog.show();
//                loginDialog.setContentView(R.layout.dialog_single_ps);
				// TODO Auto-generated method stub
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Log.v("test", "response:" + response.toString());
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
//							Token = obj.getString("token");
//							userEntity.setType(role);
//							userEntity.setToken(Token);
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
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
//				loginDialog.dismiss();
				Log.v("tag", "response:" + responseString);
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
//				loginDialog.dismiss();
//				Toast.makeText(getApplicationContext(),getString(R.string.ts_login_failed), 4*1000).show();
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
                // Do something with the response
	
	}
	protected void dialog(final String orderID)
	{
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(R.string.confirm_change_status);
		builder.setTitle(R.string.hint);
		builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) 
			{
				// TODO Auto-generated method stub
				changeState(orderID);
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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}


	/**
	 * This is a helper class that implements the management of tabs and all
	 * details of connecting a ViewPager with associated TabHost. It relies on a
	 * trick. Normally a tab host has a simple API for supplying a View or
	 * Intent that each tab will show. This is not sufficient for switching
	 * between pages. So instead we make the content part of the tab host 0dp
	 * high (it is not shown) and the TabsAdapter supplies its own dummy view to
	 * show as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct paged in the ViewPager whenever the selected tab
	 * changes.
	 */
	public static class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final RadioGroup mTabRg;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
				ViewPager pager, RadioGroup tabRg) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabRg = tabRg;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
			((RadioButton) mTabRg.getChildAt(position)).setChecked(true);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			// Unfortunately when TabHost changes the current tab, it kindly
			// also takes care of putting focus on it when not in touch mode.
			// The jerk.
			// This hack tries to prevent this from pulling focus out of our
			// ViewPager.
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){     
			 Intent intent = new Intent(Intent.ACTION_MAIN);  
	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意  
	            intent.addCategory(Intent.CATEGORY_HOME);  
	            startActivity(intent);  
			return  true;
			}  
		return super.onKeyDown(keyCode, event);
	}
	

}
