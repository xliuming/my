package com.shaobao.ts.util;

import java.io.File;

import android.content.Context;

import com.shaobao.ts.OrderService;
import com.shaobao.ts.R;
import com.shaobao.ts.dao.PictureDAO;
import com.shaobao.ts.entity.PictureEntity;

public class IOUtil {
	public static  boolean savePicture(Context context ,byte[] data ,String name , String orderNumber , String date ,String des )
	{
		String cameraPath = CommonUtil.getSDCardPath() + "/" + context.getString(R.string.root_file_name) + "/" +  context.getString(R.string.camera_file);
		createCameraFile(cameraPath);
		String picturePath = cameraPath +"/"+name;
		System.out.println("picturePath:" + picturePath);
		CommonUtil.savePicture(picturePath, data);
		
		PictureEntity pictureEntity = new PictureEntity(0, OrderService.userEntity.getName(), orderNumber, picturePath, date, des, PictureEntity.STATUS_ONSEND, 100, 100);
		savePictureToDatabase(context , pictureEntity);
		
		return true;
	}
	
	
	private static void createCameraFile(String cameraPath )
	{
		File file = new File(cameraPath);
		if (!file.exists()) {
			if (file.mkdirs())
			{
				
			}else {
				System.out.println("failed!!!!!!!!!!!!!!");
			}
		}
	}
	private static void  savePictureToDatabase(Context context , PictureEntity pictureEntity)
	{
		PictureDAO pictureDAO = new PictureDAO(context);
		pictureDAO.addPicture(pictureEntity);
	}
}
