package com.huhu.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="BUser")
@Entity(name="bUserModel")
public class BUserModel 
{
	@Id
	@GeneratedValue
	private long merID;
	private String employeeId;
	private String department;
	public long getMerID() {
		return merID;
	}
	public void setMerID(long merID) {
		this.merID = merID;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	
	
}
