package com.shaobao.ts;

import com.shaobao.ts.view.ChartView;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;

public class HistoryDataActivity extends Activity
{
     protected void onCreate(android.os.Bundle savedInstanceState) 
     {
    	 super.onCreate(savedInstanceState);
    	ChartView chartView = new ChartView(this);
    	
    	 final int[] colors = new int[]{           
                 Color.RED,  
                 Color.BLACK,  
                 Color.GREEN,  
                 Color.YELLOW,  
//                 Color.BLUE,                 
             };  
    	chartView.setmColors(colors);
    	int items[] = {20 , 800 ,10 ,160};
    	chartView.setmItems(items);
    	
    	  //屏幕信息  
        DisplayMetrics dm = getResources().getDisplayMetrics();  
        int height = dm.heightPixels;  
        int width = dm.widthPixels;  
        chartView.setmWidth(width);
        chartView.setmHeight(height);
    	
    	
    	setContentView(chartView);
    	 
    	 
     };
}
