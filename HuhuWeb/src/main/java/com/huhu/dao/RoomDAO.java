package com.huhu.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;



@Repository("roomDAO")
public class RoomDAO extends BaseDAO
{
//	public RoomModel getRoomByID(int id)
//	{
//		String hql = "FROM roomModel r WHERE r.id=?";
//		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
//		query.setInteger(0, id);
//		return (RoomModel)query.uniqueResult();
//	}
//	public List<RoomDAO> geRoomsByMerID(long merID)
//	{
//		String hql = "FROM roomModel r WHERE r.merID=?";
//		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
//		query.setLong(0, merID);
//		return query.list();
//	}
//	public void addOrder(RoomModel roomModel) {
//		getSessionFactory().getCurrentSession().save(roomModel);
//	}
//	public boolean delRoom(int id)
//	{
//		String hql = "DELETE roomModel r WHERE r.id = ?";
//		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
//		query.setInteger(0, id);
//		return (query.executeUpdate() > 0);
//	}
//	public boolean updateRoom(RoomModel roomModel)
//	{
//		String hql = "UPDATE roomModel r SET r.merID=:merID,r.name=:name,r.volum=:volum,r.deskNum=:deskNum,r.size=:size,r.status=:status,r.aDMID=:aDMID,r.url=:url,r.des=:des WHERE o.id=:id";
//		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
//		query.setProperties(roomModel);
//		return (query.executeUpdate() > 0);
//		
//	}
}
