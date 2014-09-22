package com.shaobao.ts.entity;

import java.io.Serializable;

import com.shaobao.ts.R.string;

public class PictureEntity implements Serializable
{
	public static final String STATUS_ONSEND = "未上传";
	public static final String STATUS_SENDING = "上传中";
	public static final String STATUS_SENDED = "已上传";
	public static final String STATUS_EXCETION = "异常";
	
	
	public static final String TABLE_NAME ="picture";
	public static final String P_ID = "id";
	public static final String P_USER = "user";
	public static final String P_ORDER_ID = "orderID";
	public static final String P_PATH = "path";
	public static final String P_DATE = "date";
	public static final String P_DES = "des";
	public static final String P_STATUS = "status";
	public static final String P_LONGITUDE = "longitude";
	public static final String P_LATITUDE = "latitude";
	
//	id         用户名         订单号        地址             拍摄时间          
//	描述         x          y             status
	private int id;
	private String user;
	private String orderID;
	private String path;
	private String date;
	private String des;
	private String status;
	private double longitude;
	private double latitude;
	
	public PictureEntity(int id, String user,String orderID,String path , String date,String des,String status ,double longitude,double latitude)
	{
		
		this.id =id;
		this.user = user;
		this.orderID = orderID;
		this.path = path;
		this.date = date;
		this.des = des;
		this.status = status;
		this.longitude = longitude;
		this.latitude = latitude;
		
	}
	public PictureEntity()
	{
		
	}
	
	public String getDate() {
	
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
	
	
	
	
	
	
	
	
}
