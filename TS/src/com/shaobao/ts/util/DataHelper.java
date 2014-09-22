package com.shaobao.ts.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataHelper 
{    // 数据库名称
    private static String DB_NAME = "ts.db";
    // 数据库版本
    private static int DB_VERSION = 2;
    public static SQLiteDatabase db;
    private static SQLiteHelper dbHelper;
    
   

    public DataHelper(Context context) {
    	if (dbHelper == null)
    	{
    		 dbHelper = new SQLiteHelper(context, DB_NAME, null, DB_VERSION );
		}
    	if (db == null) {
    		db = dbHelper.getWritableDatabase();
		}
         
          
    }

    public void Close() {
    	if (db != null) 
    	{
    		 db.close();
		}
    	if (db != null)
    	{
    		 dbHelper.close();
		}
    }

}
