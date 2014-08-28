package com.shaobao.ts.entity;

import org.apache.http.TokenIterator;

import android.R.integer;
import android.R.string;

public class UserEntity 
{
	public static final String TS_DRIVER = "货车司机";
	private String name;
	private String pw;
	//1= driver
	private String type;
	private String token;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
