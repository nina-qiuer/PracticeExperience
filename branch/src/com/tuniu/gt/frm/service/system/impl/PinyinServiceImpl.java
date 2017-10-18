package com.tuniu.gt.frm.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.PinyinDao;
import com.tuniu.gt.frm.service.system.IPinyinService;
@Service("frm_service_system_impl-pinyin")
public class PinyinServiceImpl extends ServiceBaseImpl<PinyinDao> implements IPinyinService {
	@Autowired
	@Qualifier("frm_dao_impl-pinyin")
	public void setDao(PinyinDao dao) {
		this.dao = dao;
	};
}
