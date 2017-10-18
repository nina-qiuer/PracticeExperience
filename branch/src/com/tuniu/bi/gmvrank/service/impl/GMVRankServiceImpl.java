package com.tuniu.bi.gmvrank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.bi.gmvrank.dao.impl.GMVRankDao;
import com.tuniu.bi.gmvrank.service.IGMVRankService;

@Service("bi_service_gmvrank_impl-GMVRank")
public class GMVRankServiceImpl extends ServiceBaseImpl<GMVRankDao> implements IGMVRankService {
	
	@Autowired
	@Qualifier("bi_dao_impl-GMVRank")
	public void setDao(GMVRankDao dao) {
		this.dao = dao;
	}
}
