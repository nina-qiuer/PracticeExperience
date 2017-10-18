package com.tuniu.gt.warning.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.warning.dao.impl.WetherCodeDao;
import com.tuniu.gt.warning.service.IWetherCodeService;

@Service("warning_service_impl-wether_code")
public class WetherCodeServiceImpl extends ServiceBaseImpl<WetherCodeDao> implements IWetherCodeService {
	
	@Autowired
	@Qualifier("warning_dao_impl-wether_code")
	public void setDao(WetherCodeDao dao) {
		this.dao = dao;
	}

	@Override
	public void keywordSet(Map<String, Object> params) {
		dao.keywordSet(params);
	}
	
}
