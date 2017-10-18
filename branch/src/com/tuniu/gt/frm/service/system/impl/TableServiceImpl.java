package com.tuniu.gt.frm.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.TableDao;
import com.tuniu.gt.frm.service.system.ITableService;
@Service("frm_service_system_impl-table")
public class TableServiceImpl extends ServiceBaseImpl<TableDao> implements ITableService {
	@Autowired
	@Qualifier("frm_dao_impl-table")
	public void setDao(TableDao dao) {
		this.dao = dao;
	};
}
