package com.huhu.dao.order;

import org.springframework.stereotype.Repository;

import com.huhu.dao.BaseDAO;
import com.huhu.model.order.OrderItemModel;


@Repository("orderDesDAO")
public class OrderDesDAO extends BaseDAO
{
	public void addOrderDes(OrderItemModel orderDesModel) 
	{
		getSessionFactory().getCurrentSession().save(orderDesModel);
	}
	
}
