package com.huhu.model.food;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;


//菜
@Entity(name = "food")
@Table(name = "Food")
public class FoodModel 
{
//	CREATE TABLE `Food`(
//			  `F_ID` bigint(20) NOT NULL AUTO_INCREMENT,
//			  `F_FTID` bigint(20) NOT NULL,
//			  `F_MID` bigint(20) NOT NULL,
//			  `F_Name` varchar(20) NOT NULL,
//			  `F_URL` varchar(20) NOT NULL,
//			  `F_Price` float ,
//			  `F_DPrice` float,
//			  `F_IsTakeOut` bit DEFAULT 0,
//			  `F_IsChara` bit DEFAULT 0,
//			  `F_Statue` int(5),
//			 PRIMARY KEY (`F_ID`),
//			 FOREIGN KEY(`F_FTID`)REFERENCES FoodType(`FT_ID`),
//			 FOREIGN KEY(`F_MID`)REFERENCES Mer(`M_ID`)
//			)ENGINE=MyISAM DEFAULT CHARSET=utf8;
	@Id
	@GeneratedValue
	private long id;

	//店铺ID
	private long merID;
	//菜名
	private String name;
	//图片地址
	private String url;
	//菜价格
	private float price;
	//折扣价
	private float dPrice;
	//是否支持外卖
	private int isTakeOut;
	//是否是特色菜
	private int isChara;
	//菜状态（正常，售完）
	private int status;
	
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false) 
	@JoinColumn(name="foodTypeID") 
	private FoodTypeModel foodTypeModel;
	

	@OneToOne
	@JoinColumn(name="sortGroupID")
	private SortGroupsModel sortGroupsModel;
	

	
	
	public SortGroupsModel getSortGroupsModel() {
		return sortGroupsModel;
	}
	public void setSortGroupsModel(SortGroupsModel sortGroupsModel) {
		this.sortGroupsModel = sortGroupsModel;
	}
	public FoodTypeModel getFoodTypeModel() {
		return foodTypeModel;
	}
	public void setFoodTypeModel(FoodTypeModel foodTypeModel)
	{
		this.foodTypeModel = foodTypeModel;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
//	public long getFoodTypeID() {
//		return foodTypeID;
//	}
//	public void setFoodTypeID(long foodTypeID) {
//		this.foodTypeID = foodTypeID;
//	}
	public long getMerID() 
	{
		return merID;
	}
	public void setMerID(long merID) {
		this.merID = merID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getdPrice() {
		return dPrice;
	}
	public void setdPrice(float dPrice) {
		this.dPrice = dPrice;
	}
	public int getIsTakeOut() {
		return isTakeOut;
	}
	public void setIsTakeOut(int isTakeOut) {
		this.isTakeOut = isTakeOut;
	}
	public int getIsChara() {
		return isChara;
	}
	public void setIsChara(int isChara) {
		this.isChara = isChara;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
