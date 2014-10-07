package com.huhu.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huhu.dao.FoodTypeDAO;
import com.huhu.model.food.FoodTypeModel;

@Service("foodTypeService")
@Transactional(value="transactionManager")
public class FoodTypeService 
{
	@Autowired
	@Qualifier("foodTypeDAO")
	private FoodTypeDAO foodTypeDAO;
	
	public FoodTypeModel getFoodTypeByID(long id)
	{
		return foodTypeDAO.getFoodTypeByID(id);
	}
	public List<FoodTypeModel> getFoodTypes(long id)
	{
		return foodTypeDAO.getFoodTypes(id);
	}
	
	public void addFoodType()
	{
		
		FoodTypeModel foodTypeModel = new FoodTypeModel();
		foodTypeModel.setMerId(20);
		foodTypeModel.setName("包点");
		foodTypeModel.setDes("早茶。。。。");
		
		foodTypeDAO.addFoodType(foodTypeModel);
	}
	
}
