package com.shaobao.ts.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.shaobao.ts.DriverActivity;
import com.shaobao.ts.R;

import android.R.integer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public static void toast(Context context , String content)
	{
		Toast.makeText(context, content, 4*1000).show();
	}
	 private void showNotification(Context context){
	        // 创建一个NotificationManager的引用   
	        NotificationManager notificationManager = (NotificationManager)    
	        		context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);   
	         
	        // 定义Notification的各种属性   
	        Notification notification =new Notification(R.drawable.ic_launcher,   
	                "督导系统", System.currentTimeMillis()); 
	        //FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
	        //FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
	        //FLAG_ONGOING_EVENT 通知放置在正在运行
	        //FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应
	        notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中   
	        notification.flags |= Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用   
	        notification.flags |= Notification.FLAG_SHOW_LIGHTS;   
	        //DEFAULT_ALL     使用所有默认值，比如声音，震动，闪屏等等
	        //DEFAULT_LIGHTS  使用默认闪光提示
	        //DEFAULT_SOUNDS  使用默认提示声音
	        //DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
	        notification.defaults = Notification.DEFAULT_LIGHTS; 
	        //叠加效果常量
	        //notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
	        notification.ledARGB = Color.BLUE;   
	        notification.ledOnMS =5000; //闪光时间，毫秒
	         
	        // 设置通知的事件消息   
	        CharSequence contentTitle ="督导系统标题"; // 通知栏标题   
	        CharSequence contentText ="督导系统内容"; // 通知栏内容   
	        Intent notificationIntent =new Intent(context, DriverActivity.class); // 点击该通知后要跳转的Activity   
	        PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, 0);   
	        notification.setLatestEventInfo(context, contentTitle, contentText, contentItent);   
	         
	        // 把Notification传递给NotificationManager   
	        notificationManager.notify(0, notification);   
	    }
	 	public static String bitmaptoString(byte[] data) {

	        // 将Bitmap转换成字符串
//	    	 Resources res = context.getResources();
	 
	    	int length = data.length;
	    	Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, length);
	    	
	        String string = null;

	        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

	        bmp.compress(CompressFormat.JPEG, 100, bStream);
	        
	        
//	       System.out.println("---------------btsteamsize:" + bStream.size());
		
	        

	        byte[] bytes=  bStream.toByteArray();

	        string = Base64.encodeToString(bytes, Base64.DEFAULT);

	        return string;

	    }
	 	public static Bitmap getimage(String srcPath) {
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			//开始读入图片，此时把options.inJustDecodeBounds 设回true了
			newOpts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
			
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
			float hh = 800f;//这里设置高度为800f
			float ww = 480f;//这里设置宽度为480f
			//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;//be=1表示不缩放
			if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;//设置缩放比例
			//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
			return bitmap;//压缩好比例大小后再进行质量压缩
		}
	 	private Bitmap compressImage(Bitmap image) {  
	 		  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
	        int options = 100;  
	        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
	            baos.reset();//重置baos即清空baos  
	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
	            options -= 10;//每次都减少10  
	        }  
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
	        return bitmap;  
	    }  
	 	
	 	public static  void setListEmptyView(Context context , ListView listView , String msg)
		{
			TextView emptyView = new TextView(context);  
	        emptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
	        emptyView.setText(""+msg);  
	        emptyView.setTextSize(30);
	        emptyView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
	        emptyView.setVisibility(View.GONE);  
//	        emptyView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.item_cloud_back));
	       
	        ((ViewGroup)listView.getParent()).addView(emptyView);  
	        listView.setEmptyView(emptyView);
		}
	 	
}