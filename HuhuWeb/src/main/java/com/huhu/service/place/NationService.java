package com.huhu.service.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huhu.dao.place.NationDAO;
import com.huhu.model.place.NationModel;

@Service("nationService")
@Transactional(value="transactionManager")
public class NationService
{
	@Autowired
	@Qualifier("nationDAO")
	private NationDAO nationDAO;
	public boolean addNation(NationModel nationModel)
	{
		long id = nationDAO.addNation(nationModel);
		if (id > 0) 
		{
			return true;
		}
		return false;
	}
	
	
	
}
