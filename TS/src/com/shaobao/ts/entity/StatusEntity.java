package com.shaobao.ts.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;


public class StatusEntity 
{
	private static final String TAG = "StatusEntity";
	public String name;
	public String alias;
	public boolean isTake;
	public String nextName;
	public StatusEntity(String name,String alias, String nextName , boolean isTake)
	{
		this.name = name;
		this.alias = alias;
		this.isTake = isTake;
		this.nextName = nextName;
//		init();
	}
	public StatusEntity()
	{
		init();
	}

	private  Map<String, StatusEntity> allStatus = new HashMap<String, StatusEntity>();
	private  void init() {
		// TODO Auto-generated method stub
		StatusEntity statusEntity1 = new StatusEntity("新建", "新建", "正在装货",false);
		StatusEntity statusEntity2 = new StatusEntity("正在装货", "装货中","去程途中", true);
		StatusEntity statusEntity3 = new StatusEntity("去程途中", "去程途中","正在卸货", false);
		StatusEntity statusEntity4 = new StatusEntity("正在卸货", "卸货中", "等待装矿",false);
		StatusEntity statusEntity5 = new StatusEntity("等待装矿", "等待装矿","正在装矿", true);
		StatusEntity statusEntity6 = new StatusEntity("正在装矿", "装矿中","正在装矿", false);
		StatusEntity statusEntity7 = new StatusEntity("回程途中", "回程途中", "回程途中",false);
		StatusEntity statusEntity8 = new StatusEntity("正在卸矿", "卸矿中", "正在卸矿",false);
		StatusEntity statusEntity9 = new StatusEntity("等待货运审核", "审核中", "结束",false);
		
		allStatus.put(statusEntity1.name, statusEntity1);
		allStatus.put(statusEntity2.name, statusEntity2);
		allStatus.put(statusEntity3.name, statusEntity3);
		allStatus.put(statusEntity4.name, statusEntity4);
		allStatus.put(statusEntity5.name, statusEntity5);
		allStatus.put(statusEntity6.name, statusEntity6);
		allStatus.put(statusEntity7.name, statusEntity7);
		allStatus.put(statusEntity8.name, statusEntity8);
		allStatus.put(statusEntity9.name, statusEntity9);
	}
	
	public StatusEntity getStatusEntity(String status)
	{
		
		return allStatus.get(status);
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Log.v(TAG, "name:" +name+ " alias:" +alias+ " isTake:" + isTake);
		return super.toString();
	}
	
}
