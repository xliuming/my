package com.huhu.model.food;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name="foodType")
@Table(name="FoodType")
public class FoodTypeModel 
{
//	  `FT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
//	  `FT_Des` varchar(255) DEFAULT NULL,
//	  `FT_MID` bigint(20) NOT NULL,
//	  `FT_Name` varchar(20) NOT NULL,
	@Id
	@GeneratedValue
	private long id;

	private int merId;

	private String name;

	private String des;
	
	@OneToMany(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "foodTypeID")
	private Set<FoodModel> foodModels;
//	
//	
//	
	

	public long getId() {
		return id;
	}

	public Set<FoodModel> getFoodModels() {
		return foodModels;
	}

	public void setFoodModels(Set<FoodModel> foodModels) {
		this.foodModels = foodModels;
	}

	public void setId(long id) {
		this.id = id;
	}
	public int getMerId() {
		return merId;
	}
	public void setMerId(int merId) {
		this.merId = merId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	
	
	
	
}
