package com.tuniu.bi.secondarydep.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.bi.secondarydep.dao.impl.SecondaryDepDao;
import com.tuniu.bi.secondarydep.service.ISecondaryDepService;

@Service("bi_service_secondarydep_impl-secondarydep")
public class SecondaryDepServiceImpl implements ISecondaryDepService {
	
	@Autowired
	@Qualifier("bi_dao_impl-secondarydep")
	private SecondaryDepDao dao;

	@Override
	public Map<String, Object> getSecondaryDep(int productLineId) {
		return dao.getSecondaryDep(productLineId);
	}

}
