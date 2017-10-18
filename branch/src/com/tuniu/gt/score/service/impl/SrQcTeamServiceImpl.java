package com.tuniu.gt.score.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.score.dao.impl.SrQcTeamDao;
import com.tuniu.gt.score.service.SrQcTeamService;


@Service("score_service_impl-sr_qc_team")
public class SrQcTeamServiceImpl extends ServiceBaseImpl<SrQcTeamDao> implements SrQcTeamService {
	
	@Autowired
	@Qualifier("score_dao_impl-sr_qc_team")
	public void setDao(SrQcTeamDao dao) {
		this.dao = dao;
	}


}
