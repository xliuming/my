package com.shaobao.ts.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaobao.ts.R;
import com.shaobao.ts.R.drawable;
import com.shaobao.ts.entity.PictureEntity;
import com.shaobao.ts.util.DisplayUtil;


public class ChartAdapter extends BaseAdapter
{
	private List<PictureEntity> pictureEntities = null;

	private LayoutInflater mInflater;
	
	
	   
	public ChartAdapter(List<PictureEntity> pictureEntities , Context context)
	{
		this.pictureEntities = pictureEntities;
		 this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictureEntities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stu
         //观察convertView随ListView滚动情况 
		ViewHolder holder = null;
//		 Log.v("MyListViewBase", "getView " + position + " " + convertView);
         if (convertView == null) 
         {
        	 PictureEntity pictureEntity = pictureEntities.get(position);
                  convertView = mInflater.inflate(R.layout.chart_item, null);
                  holder = new ViewHolder();
                 /*得到各个控件的对象*/                    
                  holder.tvOrderNumber = (TextView) convertView.findViewById(R.id.tv_order_number);
                 holder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_send_time);
                holder.ivPicture = (ImageView)convertView.findViewById(R.id.iv_image);
                
                 convertView.setTag(holder);//绑定ViewHolder对象                 
                 
                 
                 holder.tvOrderNumber.setText(": " + pictureEntity.getOrderID());
                 
                 if (pictureEntity.getStatus().equals(PictureEntity.STATUS_SENDED)) 
                 {
					holder.tvSendTime.setText(": " + pictureEntity.getDate());
                 }else 
                 {
                	 holder.tvSendTime.setText(": " + pictureEntity.getStatus());
				 }
                 
                 Bitmap bt = DisplayUtil.getimage(pictureEntity.getPath());
                 holder.ivPicture.setImageBitmap(bt);
                 
         }
         else
         {
                 holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象                
         }
         /*设置TextView显示的内容，即我们存放在动态数组中的数据*/             
        
         
         
         
         
         
         
         return convertView;
     }
	
 
   public class ViewHolder
  {
		 public TextView tvOrderNumber;
		 public TextView tvSendTime;
		 public ImageView ivPicture;
		 
  }
 
}
