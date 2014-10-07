package com.huhu.service.mer;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huhu.dao.mer.MerDAO;
import com.huhu.model.mer.MerModel;


@Service("merService")
@Transactional(value="transactionManager")
public class MerService
{

	@Autowired
	@Qualifier("merDAO")
	private MerDAO merDAO;
	public MerModel getMerModeld(long id)
	{
		return merDAO.getMerByID(id);
	}
	
}
