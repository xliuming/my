package com.shaobao.ts.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.shaobao.ts.OrderService;
import com.shaobao.ts.R;
import com.shaobao.ts.entity.PictureEntity;

import android.R.integer;
import android.os.Environment;
import android.os.StatFs;

public class CommonUtil 
{
	
	public static String getSDCardPath()
	{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){ 
	        File path =Environment.getExternalStorageDirectory(); 
	        //取得sdcard文件路径 
//	        StatFs statfs=new StatFs(path.getPath()); 
	        return path.toString();
	        //获取block的SIZE
		}else {
			return null;
		}
		
	}
	public static boolean getSDCardStatus()
	{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){ 
	       return true;
	        //获取block的SIZE
		}else {
			return false;
		}
	}
	public static boolean savePicture(String pathString , byte[] data)
	{
		if (pathString == null || pathString.equals("") || data == null)
		{
			return false;
		}
		File file = new File(pathString);
		if (!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(file);
			int length = data.length;
			fileOutputStream.write(data, 0, length);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	
}
