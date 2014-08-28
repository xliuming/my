package com.shaobao.ts;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

//新版本中TabActivity过时了，使用一下的方式
public class DriverActivity extends  ActivityGroup{

	private TabHost myTabHost;
    public static int mWinheight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver); 
	    Rect fram = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(fram);
        mWinheight = fram.height();
		myTabHost = (TabHost) findViewById(android.R.id.tabhost);
		myTabHost.setup(this.getLocalActivityManager());//实例化了tabWidget和tabContent  
		myTabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
		 View oneKeyUpdateView = (View) LayoutInflater.from(this).inflate(R.layout.tab_widget_item, null);  
	        TextView oKUTextView = (TextView) oneKeyUpdateView.findViewById(R.id.tab_label);  
	        oKUTextView.setText("一键更新"); 
	        oKUTextView.setTextSize(30);
	        View historyDataView = (View) LayoutInflater.from(this).inflate(R.layout.tab_widget_item, null);  
	        TextView hDTextView = (TextView) historyDataView.findViewById(R.id.tab_label);  
	        hDTextView.setText("历史数据"); 
	        hDTextView.setTextSize(30);
		
		myTabHost.addTab(myTabHost.newTabSpec("oneKey").setIndicator(oneKeyUpdateView).setContent(new Intent(this , FragmentBottomTabPager.class)));
		myTabHost.addTab(myTabHost.newTabSpec("history").setIndicator(historyDataView).setContent(R.id.tab2));
//		myTabHost.addTab(myTabHost.newTabSpec("百度").setIndicator("百度",getResources().getDrawable(R.drawable.ic_launcher)).setContent(R.id.tab3));

	}


		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
}
