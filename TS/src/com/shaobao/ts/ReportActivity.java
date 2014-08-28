package com.shaobao.ts;

import android.app.Activity;
import android.app.ActivityGroup;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class ReportActivity extends ActivityGroup
{
	private TabHost myTabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		myTabHost = (TabHost) findViewById(android.R.id.tabhost);
		myTabHost.setup();//实例化了tabWidget和tabContent  
		myTabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
		 View oneKeyUpdateView = (View) LayoutInflater.from(this).inflate(R.layout.tab_widget_item, null);  
	        TextView oKUTextView = (TextView) oneKeyUpdateView.findViewById(R.id.tab_label);  
	        oKUTextView.setText("事故"); 
	        oKUTextView.setTextSize(30);
	        View historyDataView = (View) LayoutInflater.from(this).inflate(R.layout.tab_widget_item, null);  
	        TextView hDTextView = (TextView) historyDataView.findViewById(R.id.tab_label);  
	        hDTextView.setText("加油"); 
	        hDTextView.setTextSize(30);
	        View updateDataView = (View) LayoutInflater.from(this).inflate(R.layout.tab_widget_item, null); 
	        TextView updaTextView = (TextView) updateDataView.findViewById(R.id.tab_label);  
	        updaTextView.setText("维修"); 
	        updaTextView.setTextSize(30);
	        View otherDataView = (View) LayoutInflater.from(this).inflate(R.layout.tab_widget_item, null); 
	        TextView otherTextView = (TextView) otherDataView.findViewById(R.id.tab_label);  
	        otherTextView.setText("其他"); 
	        otherTextView.setTextSize(30);
		myTabHost.addTab(myTabHost.newTabSpec("oneKey").setIndicator(oneKeyUpdateView).setContent(R.id.tab1));
		myTabHost.addTab(myTabHost.newTabSpec("history").setIndicator(historyDataView).setContent(R.id.tab2));
		myTabHost.addTab(myTabHost.newTabSpec("update").setIndicator(updateDataView).setContent(R.id.tab1));
		myTabHost.addTab(myTabHost.newTabSpec("other").setIndicator(otherDataView).setContent(R.id.tab1));
	}
}
