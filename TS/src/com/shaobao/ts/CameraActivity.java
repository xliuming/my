package com.shaobao.ts;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

@SuppressLint("NewApi") public class CameraActivity extends Activity implements OnClickListener , OnTouchListener {
	/** Called when the activity is first created. */
	private static final String TAG = "CameraActivity";
	// camera 类
	private Camera camera = null;
	private Camera.Parameters parameters = null;
	// 继承surfaceView的自定义view 用于存放照相的图片
	private CameraView cv = null;
	private ECameraStatus mCameraStatus = ECameraStatus.INVALID;
	public enum ECameraStatus
	{
		INVALID(0),
		PREVIEW(1),
		OPEN(2),
		FOCUS(3),
		SHOWPICTURE(4);
		private int status;
		private ECameraStatus(int status)
		{
			this.status = status;
		}
		@Override
		public String toString()
		{
			// TODO Auto-generated method stub
			
			switch (status) {
			case 0:
				return "invalid";
			case 1:
				return "preview";
			case 2:
				return "open";
			case 3:
				return "focus";
			case 4:
				return "showPicture";
			default:
				return "undefine";
			}
			
			
//			return super.toString();
		}
		
	}
	
	
	
	// 回调用的picture，实现里边的onPictureTaken方法，其中byte[]数组即为照相后获取到的图片信息
	private Camera.PictureCallback picture = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
			// 主要就是将图片转化成drawable，设置为固定区域的背景（展示图片），当然也可以直接在布局文件里放一个surfaceView供使用。
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			Drawable d = BitmapDrawable.createFromStream(bais, Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ "/img.jpeg");
//			l.setBackgroundDrawable(d);
			btn_opration.setText(R.string.confirm);;
			mCameraStatus = ECameraStatus.SHOWPICTURE;
		
		}

	};

	// 按钮 布局等定义，不作赘述
	Button btn_opration = null;
//	Button btn2 = null;
	LinearLayout l = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.activity_camera);

		l = (LinearLayout) findViewById(R.id.cameraView);
		btn_opration = (Button) findViewById(R.id.btn_opration);
//		btn2 = (Button) findViewById(R.id.btn2);

		btn_opration.setOnClickListener(this);
//		btn2.setOnClickListener(this);
		l.removeAllViews();
		l.setOnTouchListener(this);
		cv = new CameraView(CameraActivity.this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		l.addView(cv, params);
	}

	//主要的surfaceView，负责展示预览图片，camera的开关
	class CameraView extends SurfaceView {

		//
		private SurfaceHolder holder = null;

		public CameraView(Context context) {
			super(context);
			  
		    setFocusable(true);  
		    setBackgroundColor(TRIM_MEMORY_BACKGROUND);  
			holder = this.getHolder();
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			holder.addCallback(new SurfaceHolder.Callback() {

				@Override
				public void surfaceChanged(SurfaceHolder holder, int format,
						int width, int height) {
					Log.v(TAG, "surfaceChanged");
					parameters = camera.getParameters();
				
					
					camera.setParameters(parameters);
					camera.startPreview();
					mCameraStatus = ECameraStatus.PREVIEW;
//					camera.autoFocus(new AutoFocusCallback() 
//					{
//						@Override
//						public void onAutoFocus(boolean arg0, Camera arg1) {
//							Log.v(TAG, "onAutoFocus");
//							// TODO Auto-generated method stub
//							  if(arg0){
//								  
////								  Log.v(, msg)
//								    parameters=camera.getParameters();  
//						            parameters.setPictureFormat(PixelFormat.JPEG);  
//						            //parameters.setPictureSize(surfaceView.getWidth(), surfaceView.getHeight());  // 部分定制手机，无法正常识别该方法。  
//						            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);     
//						            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对焦  
////						            setDispaly(parameters,camera);  
//			                        //initCamera();//实现相机的参数初始化  
//			                        camera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。  
//			                    }  
//						}
//					});
				}
				
				 
				//控制图像的正确显示方向  
//			      private void setDispaly(Camera.Parameters parameters,Camera camera)  
//			      {  
//			          if (Integer.parseInt(Build.VERSION.SDK) >= 8){  
//			                setDisplayOrientation(camera,90);  
//			            }  
//			        else{  
//			                parameters.setRotation(90);  
//			            }  
//			            
//			      }       
			      
			      //实现的图像的正确显示  
//			      private void setDisplayOrientation(Camera camera, int i) {  
//			          Method downPolymorphic;  
//			         try{  
//			                downPolymorphic=camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});  
//			                if(downPolymorphic!=null) {  
//			                    downPolymorphic.invoke(camera, new Object[]{i});  
//			                }  
//			            }  
//			            catch(Exception e){  
//			                Log.e("Came_e", "图像出错");  
//			            }  
//			      }  

				@Override
				public void surfaceCreated(SurfaceHolder holder) 
				{
					Log.v(TAG, "surfaceCreated");
					camera = Camera.open();
					mCameraStatus = ECameraStatus.OPEN;
					try {
						//设置camera预览的角度，因为默认图片是倾斜90度的
						camera.setDisplayOrientation(90);
						//设置holder主要是用于surfaceView的图片的实时预览，以及获取图片等功能，可以理解为控制camera的操作..
						camera.setPreviewDisplay(holder);

					} catch (IOException e) {
						camera.release();
						camera = null;
						mCameraStatus = ECameraStatus.INVALID;
						e.printStackTrace();
					}
					

				}

				@Override
				public void surfaceDestroyed(SurfaceHolder holder) 
				{
					Log.v(TAG, "surfaceDestroyed");
					//顾名思义可以看懂
					camera.stopPreview();
					camera.release();
					camera = null;
					mCameraStatus = ECameraStatus.INVALID;
				}
			});
//			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id ==R.id.btn_opration) 
		{
			
			if (mCameraStatus == ECameraStatus.PREVIEW)
			{
				camera.autoFocus(new AutoFocusCallback() {
					
					@Override
					public void onAutoFocus(boolean arg0, Camera arg1) {
						mCameraStatus = ECameraStatus.FOCUS;
						// TODO Auto-generated method stub
						Log.v(TAG, "onAutoFocus");
						camera.takePicture(null, null, picture);
						
					}
				});
				  
			}else if(mCameraStatus == ECameraStatus.SHOWPICTURE)
			{
//					l.setBackground(null);
				//l.setBackgroundDrawable(null);
//				l.getBackground().setVisible(false, false);
				
//					l.removeAllViews();
			}
			
			
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) 
	{
		int id = arg0.getId();
		if (id == R.id.cameraView) 
		{
			if (arg1.getAction() == MotionEvent.ACTION_DOWN )
			{
				if (mCameraStatus == ECameraStatus.PREVIEW)
				{
					  
				}else if(mCameraStatus == ECameraStatus.SHOWPICTURE)
				{
					camera.startPreview();
					btn_opration.setText(R.string.take);
					mCameraStatus = ECameraStatus.PREVIEW;
					
				}
				//按下时自动对焦
	          
//	            af =true;
	        }
		}
		// TODO Auto-generated method stub
		return false;
	}
}
