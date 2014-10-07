package com.shaobao.ts.view;

import java.security.PublicKey;
import java.util.ArrayList;

import com.shaobao.ts.R;
import com.shaobao.ts.entity.OrderEntity;
import com.shaobao.ts.entity.StatusEntity;

import android.R.integer;
import android.R.raw;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter 
{
	private static String TAG = "OrderAdapter";
	private ArrayList<OrderEntity> orderEntitys = null;
	private int temp =-1;
	private LayoutInflater mInflater;
	
	
	   
	public OrderAdapter(ArrayList<OrderEntity> orderEntitys , Context context)
	{
		this.orderEntitys = orderEntitys;
		 this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
//		System.out.println("____________getCount:" + orderEntitys.size());
		// TODO Auto-generated method stub
		return orderEntitys.size();
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
//		System.out.println("get_view_out"+ position);
		ViewHolder holder;
//		 Log.v("MyListViewBase", "getView " + position + " " + convertView);
         if (convertView == null) 
         {
        	 holder = new ViewHolder();
//        	 	  System.out.println("get_view");
                  convertView = mInflater.inflate(R.layout.order_item, null);
                 
                 /*得到各个控件的对象*/                    
                  holder.tvOrderNumber = (TextView) convertView.findViewById(R.id.tv_order_number);
                 holder.tvCurStatus = (TextView) convertView.findViewById(R.id.tv_order_status);
                 holder.tvNumber = (TextView)convertView.findViewById(R.id.tv_number);
                             
                 
                 
              
                 holder.radioButton = (RadioButton) convertView.findViewById(R.id.rb_radioButton);
                 holder.radioButton.setId(position); //把position设为radioButton的id
                 
                 convertView.setTag(holder);//绑定ViewHolder对象     
                 holder.radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                 @Override
                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                	 System.out.println("checkedChanged");
                 if(isChecked){
                 //这段代码来实现单选功能
                 if(temp != -1){
                 RadioButton tempButton = (RadioButton)parent.findViewById(temp);
                 if(tempButton != null){
                 tempButton.setChecked(false);
                 orderEntitys.get(temp).setChoose(false);
                 }

                 }

                 temp = buttonView.getId();
                 orderEntitys.get(temp).setChoose(true);
                 
                 Log.i(TAG,"you are women- - " + isChecked + " " + temp);

                 }
                 }

        		
                 });
                 Log.i(TAG,"you are women- - ----" + temp);

                 //这里实现单选框选的回显，解决了单选框移出屏幕范围未选中状态
                 if(temp == position){
                	   holder.radioButton.setChecked(true);
                	 orderEntitys.get(temp).setChoose(true);
                	 System.out.println("temp:" + temp);
                 } 
                 
         }
         else
         {
                 holder = (ViewHolder)convertView.getTag();            
         }

         setView(holder,position);
       
         return convertView;
     }
	
	private void setView(ViewHolder viewholder , int position)
	{
		 OrderEntity orderEntity = orderEntitys.get(position);
//         System.out.println("position:" + position +" convertView:" + convertView.toString());
         viewholder.tvOrderNumber.setText(": " + orderEntity.getId() );
         
         String status = new StatusEntity().getStatusEntity(orderEntity.getStatus()).alias;
         viewholder.tvCurStatus.setText(": " + status);
         viewholder.tvNumber.setText(""+ (position+ 1));

         if (temp == position)
         {
        	 orderEntity.setChoose(true);
		 }
	}
	
 
 
   public class ViewHolder
  {
		 public TextView tvOrderNumber;
		 public TextView tvCurStatus;
		 public TextView tvNumber;
		 public  RadioButton radioButton;
		 
  }


}
