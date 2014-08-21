package com.shaobao.ts.view;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

@SuppressLint("NewApi") public class ChartView extends View
{

	public int mWidth = -1;
	public int mHeight = -1;
	public int[] mColors;
	public int[] mItems;
	public int[] scale;
	public String[] text;
	
	private Paint arrPaint[];
	private Paint textPaint = null;
	
	
	public ChartView(Context context)
	{
		super(context);
		  //解决4.1版本 以下canvas.drawTextOnPath()不显示问题              
//        this.setLayerType(View.LAYER_TYPE_SOFTWARE,null);  
          

                      
        //设置边缘特殊效果  
        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(  
                                1, BlurMaskFilter.Blur.INNER);  

        arrPaint = new Paint[5];   
        Resources res = this.getResources();  
//        int size = mItems.length;
//        for(int i=0;i<size;i++)  
//        {  
//            arrPaint[i] = new Paint();             
//            arrPaint[i].setColor(mColors[i] );   
//              
//            arrPaint[i].setStyle(Paint.Style.FILL);  
//            arrPaint[i].setStrokeWidth(4);  
//            arrPaint[i].setMaskFilter(PaintBGBlur);  
//        }  
              
        textPaint = new Paint();  
        textPaint.setColor(Color.BLUE);  
        textPaint.setTextSize(30);  
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);  
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//每项统计间隔的宽度
		int inspace = 30;
		
		int initX = 20;
		int initY = 20;
		canvas.drawColor(Color.WHITE);
		
		int size = mItems.length;
		
		for (int i = 0; i < size; i++) 
		{
			textPaint.setColor(mColors[i]);
			canvas.drawText("text:", initX, initY, textPaint);
//			canvas.drawRect(rect, paint);
			canvas.drawRect(initX +80, initY, initX + 200, initY +40, textPaint);
			
			
			initY+=40;
			
		}
		
				
		
		
		
	
	}
	
	public int getmWidth() 
	{
		return mWidth;
	}


	public void setmWidth(int mWidth) {
		this.mWidth = mWidth;
	}


	public int getmHeight() {
		return mHeight;
	}


	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}


	public int[] getmColors() {
		return mColors;
	}


	public void setmColors(int[] mColors) {
		this.mColors = mColors;
	}


	public int[] getmItems() {
		return mItems;
	}


	public void setmItems(int[] mItems) {
		this.mItems = mItems;
	}


	public int[] getScale() {
		return scale;
	}


	public void setScale(int[] scale) {
		this.scale = scale;
	}


	public String[] getText() {
		return text;
	}


	public void setText(String[] text) {
		this.text = text;
	}
	
}
