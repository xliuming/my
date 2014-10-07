package com.huhu.dao;



import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.huhu.model.food.FoodModel;
import com.huhu.model.food.FoodTypeModel;



@Repository("foodTypeDAO")
public class FoodTypeDAO extends BaseDAO
{
	public FoodTypeModel getFoodTypeByID(long id)
	{
		String hql = "from foodType f where f.id=?";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setLong(0, id);
		return (FoodTypeModel)query.uniqueResult();
	}



	
	public List<FoodTypeModel> getFoodTypes(long merID)
	{
	
//		Session session = getSessionFactory().getCurrentSession();
//
//		Transaction transaction = session.getTransaction();
//		try {
//			transaction.begin();
//			FoodTypeModel foodTypeModel = (FoodTypeModel) session.load(FoodTypeModel.class, 1);
//			List< FoodModel> foodModels = foodTypeModel.getFoodModels();
//			System.out.println("size:" + foodModels.size());
//			session.getTransaction().commit();
//		} catch (Exception e) {
//			transaction.rollback();
//			
//			// TODO: handle exception
//		}finally
//		{
//			 session.close();
//		}
		
		String hql = "from foodType f where f.merId=?";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setLong(0, merID);
		
		return query.list();
//		return null;
		
	}
	

	
	public void addFoodType(FoodTypeModel foodTypeModel) {
		getSessionFactory().getCurrentSession().save(foodTypeModel);
	}


	public boolean delFoodType(long id) {
		
		String hql = "delete foodType f where f.id = ?";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setLong(0, id);
		
		return (query.executeUpdate() > 0);
	}


	public boolean updateFoodType(FoodTypeModel foodTypeModel)
	{
		
		String hql = "update foodType f set f.merId=:merId,f.name=name,f.des=:des where f.id =:id";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setProperties(foodTypeModel);
		
		return (query.executeUpdate() > 0);
	}
	
}
