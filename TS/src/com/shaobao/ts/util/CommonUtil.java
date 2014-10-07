package com.shaobao.ts.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shaobao.ts.OrderService;
import com.shaobao.ts.R;
import com.shaobao.ts.entity.OrderEntity;
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
	public static boolean parserOrder(JSONObject jobj ,List< OrderEntity> orderEntities)
	{
		if (jobj== null ||orderEntities ==null) 
		{
			return false;
		}
		try {
		
			String msg = jobj.getString("msg");
			if (msg.equals("成功")) 
			{
				
				JSONObject dataJOBJ = jobj.getJSONObject("data");
				JSONArray orders = dataJOBJ.getJSONArray("orders");
				if (orders == null || orders.length() == 0)
				{
					return true;
				}else 
				{
					int length = orders.length();
					orderEntities.clear();
					for (int i = 0; i < length; i++) {
						OrderEntity od = parserOrderEntity((JSONObject)orders.get(i));
//							
						orderEntities.add(od);
					}
					return true;
				}
			}else {
				return false;
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return false;
	}
	private static OrderEntity parserOrderEntity(JSONObject orderOBJ)
	{
		OrderEntity od = new OrderEntity();
		
//		{"id":3,"cId":"765432","oId":"876890","client":"66","status":"新建","dId":"123456"},
		
		try {
			String id = orderOBJ.getString("id");
			String cId  = orderOBJ.getString("cId");
			String oId  = orderOBJ.getString("oId");
			String client  = orderOBJ.getString("client");
			String status  = orderOBJ.getString("status");
			String dId  = orderOBJ.getString("dId");
			od.setId(id);
			od.setCid(cId);
			od.setOid(oId);
			od.setClient(client);
			od.setStatus(status);
			od.setdId(dId);
//			System.out.println("id:" + id +" cId:" + oId +" oID" + oId +" client:" + client + " status:" + status +" dId:" +dId);
		} catch (JSONException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		return od;
	}
	
}
