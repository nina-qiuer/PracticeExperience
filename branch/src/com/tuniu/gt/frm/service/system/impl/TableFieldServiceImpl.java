package com.tuniu.gt.frm.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.TableFieldDao;
import com.tuniu.gt.frm.service.system.ITableFieldService;
@Service("frm_service_system_impl-table_field")
public class TableFieldServiceImpl extends ServiceBaseImpl<TableFieldDao> implements ITableFieldService {
	@Autowired
	@Qualifier("frm_dao_impl-table_field")
	public void setDao(TableFieldDao dao) {
		this.dao = dao;
	};
}
