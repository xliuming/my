package com.shaobao.ts.util;

import com.shaobao.ts.R.string;
import com.shaobao.ts.entity.PictureEntity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper{

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) 
	{
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

//	private String orderID;
//	private String path;
//	private string date;
//	private String des;
//	private String status;
//	private String longitude;
//	private String latitude;
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO Auto-generated method stub
		 db.execSQL( "CREATE TABLE IF NOT EXISTS "+
	                PictureEntity.TABLE_NAME+ "("+
	                PictureEntity.P_ID+ " integer primary key,"+
	                PictureEntity.P_USER+ " varchar,"+
	                PictureEntity.P_ORDER_ID+ " varchar,"+
	                PictureEntity.P_PATH+ " varchar,"+
	                PictureEntity.P_DATE+ " varchar,"+
	                PictureEntity.P_DES+ " varchar,"+
	                PictureEntity.P_STATUS+ " varchar,"+
	                PictureEntity.P_LONGITUDE+ " BOLB,"+
	                PictureEntity.P_LATITUDE+ " BOLB"+
	                ")"
	                );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub
		  db.execSQL( "DROP TABLE IF EXISTS " + PictureEntity.TABLE_NAME );
	      onCreate(db);
	}
	 public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
	        try{
	            db.execSQL( "ALTER TABLE " +
	            		PictureEntity.TABLE_NAME+ " CHANGE " +
	                    oldColumn + " "+ newColumn +
	                    " " + typeColumn
	            );
	        } catch(Exception e){
	            e.printStackTrace();
	        }
	 }
}
