package com.shaobao.ts.entity;

public class OrderEntity
{
	
//	{"id":3,"cId":"765432","oId":"876890","client":"66","status":"新建","dId":"123456"},
	private String id;
	private String cid;
	private String oid;
	private String client;
	private String status;
	private String dId;
	private boolean choose;
	
//	private boolean isNew;
	
	public boolean isChoose() {
		return choose;
	}
	public void setChoose(boolean choose) {
		this.choose = choose;
	}
	//	public boolean isNew() {
//		return isNew;
//	}
//	public void setNew(boolean isNew) {
//		this.isNew = isNew;
//	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getStatus() {
		return status;
	}
	public synchronized void setStatus(String status) {
		this.status = status;
	}
	public String getdId() {
		return dId;
	}
	public void setdId(String dId) {
		this.dId = dId;
	}
	
	
	
	
	
}
