package com.huhu.model.order;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "orderItemsModel")
@Table(name ="OrderItems")
public class OrderItemModel
{
	@Id
	@GeneratedValue
	private long id;
	private long foodID;
	private long number;
	private String type;
	private String des;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFoodID() {
		return foodID;
	}
	public void setFoodID(long foodID) {
		this.foodID = foodID;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number)
	{
		this.number = number;
	}
	public String getDes() 
	{
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
	
}
