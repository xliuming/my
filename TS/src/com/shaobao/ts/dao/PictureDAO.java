package com.shaobao.ts.dao;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.UserDataHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiConfiguration.Status;

import com.shaobao.ts.entity.PictureEntity;
import com.shaobao.ts.util.DataHelper;

public class PictureDAO extends DataHelper {

	public PictureDAO(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	  public List<PictureEntity> GetPicturesByUser(String  user) {
          List<PictureEntity> pictureList = new ArrayList<PictureEntity>();
//          db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
          Cursor cursor = db.query(PictureEntity.TABLE_NAME, null, PictureEntity.P_USER + "=?" , new String[]{user}, null,
                    null, PictureEntity.P_DATE + " DESC");
          cursor.moveToFirst();
          List<PictureEntity> pictureEntities = new ArrayList<PictureEntity>();
           while (!cursor.isAfterLast() ) 
           {
        	   pictureEntities.add(getPicture(cursor));
              cursor.moveToNext();
          }
          cursor.close();
           return pictureEntities;
     }
	 private PictureEntity getPicture(Cursor cursor)
	 {
		   PictureEntity pictureEntity = new PictureEntity();
	  	   pictureEntity.setId(cursor.getInt(cursor.getColumnIndex(PictureEntity.P_ID)));
	  	   pictureEntity.setUser(cursor.getString(cursor.getColumnIndex(PictureEntity.P_USER)));
	  	   pictureEntity.setPath(cursor.getString(cursor.getColumnIndex(PictureEntity.P_PATH)));
	  	   pictureEntity.setDate(cursor.getString(cursor.getColumnIndex(PictureEntity.P_DATE)));
	  	   pictureEntity.setDes(cursor.getString(cursor.getColumnIndex(PictureEntity.P_DES)));
	  	   pictureEntity.setStatus(cursor.getString(cursor.getColumnIndex(PictureEntity.P_STATUS)));
	  	   pictureEntity.setLongitude(cursor.getDouble(cursor.getColumnIndex(PictureEntity.P_LONGITUDE)));
	  	   pictureEntity.setLatitude(cursor.getDouble(cursor.getColumnIndex(PictureEntity.P_LATITUDE)));
	  	   pictureEntity.setOrderID(cursor.getString(cursor.getColumnIndex(PictureEntity.P_ORDER_ID)));
	  	   
	  	   return pictureEntity;
  	   
	 }
	  
	  public Long addPicture(PictureEntity pictureEntity) {
        
         long id = db.insert(PictureEntity.TABLE_NAME, PictureEntity.P_ID, getContentValues(pictureEntity));
        
//          Long uid = db.insert(SqliteHelper. TB_NAME, UserInfo.ID, values);
//          Log. e("SaveUserInfo", uid + "");
           return id;
     }
	  private ContentValues getContentValues(PictureEntity pictureEntity)
	  {
		  ContentValues values = new ContentValues();
          values.put(PictureEntity.P_ORDER_ID, pictureEntity.getOrderID());
          values.put(PictureEntity.P_USER, pictureEntity.getUser());
          values.put(PictureEntity.P_PATH, pictureEntity.getPath());
          values.put(PictureEntity.P_DATE, pictureEntity.getDate());
          values.put(PictureEntity.P_DES, pictureEntity.getDes());
          values.put(PictureEntity.P_STATUS, pictureEntity.getStatus());
          values.put(PictureEntity.P_LONGITUDE, pictureEntity.getLongitude());
          values.put(PictureEntity.P_LATITUDE, pictureEntity.getLatitude());
          return values;
		  
	  }
	  public List<PictureEntity> obtainPictureByOrderID(String orderID) 
	  {
          Boolean b = false;
          Cursor cursor = db.query(PictureEntity.TABLE_NAME, null, PictureEntity.P_ORDER_ID
                   + "=?", new String[]{orderID}, null, null, null );
          b = cursor.moveToFirst();
          List<PictureEntity> pictureEntities = new ArrayList<PictureEntity>();
          while(!cursor.isAfterLast())
          {
        	  pictureEntities.add(getPicture(cursor));
        	  cursor.moveToNext();
          }
//          Log. e("HaveUserInfo", b.toString());
          cursor.close();
           return pictureEntities;
     }

	  public int updatePicture(PictureEntity pictureEntity)
	  {
         
           int id = db.update(PictureEntity.TABLE_NAME, getContentValues(pictureEntity), PictureEntity.P_ID + "="
                   + pictureEntity.getId(), null);
//          Log. e("UpdateUserInfo", id + "");
           return id;
     }

	  
	 public int updatePictureStatusByOrderID(String orderID , String status)
	 {
		
		 ContentValues values = new ContentValues();
		 values.put(PictureEntity.P_STATUS, status);
		 
		 int id = db.update(PictureEntity.TABLE_NAME, values, PictureEntity.P_ORDER_ID + "="
                 + orderID, null);
//        Log. e("UpdateUserInfo", id + "");
         return id;
	 }
	  
	 public int delPicture(int id) 
     {
       int pid = db.delete(PictureEntity.TABLE_NAME,
    		   PictureEntity.P_ID + "=?", new String[]{""+id});
//          Log. e("DelUserInfo", id + "");
       return pid;
     }

}
