package com.tuniu.bi.secondarydep.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tuniu.bi.secondarydep.dao.sqlmap.imap.ISecondaryDepMap;

@Repository("bi_dao_impl-secondarydep")
public class SecondaryDepDao implements ISecondaryDepMap {
	
	@Autowired
	@Qualifier("bi_dao_sqlmap-secondarydep")
	private ISecondaryDepMap mapper;

	@Override
	public Map<String, Object> getSecondaryDep(int productLineId) {
		return mapper.getSecondaryDep(productLineId);
	}

}
