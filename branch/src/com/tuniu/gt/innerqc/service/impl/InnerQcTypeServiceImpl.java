package com.tuniu.gt.innerqc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.innerqc.dao.impl.InnerQcTypeDao;
import com.tuniu.gt.innerqc.entity.InnerQcTypeEntity;
import com.tuniu.gt.innerqc.service.InnerQcTypeService;


@Service("innerqc_service_impl-inner_qc_type")
public class InnerQcTypeServiceImpl extends ServiceBaseImpl<InnerQcTypeDao> implements InnerQcTypeService {
	
	@Autowired
	@Qualifier("innerqc_dao_impl-inner_qc_type")
	public void setDao(InnerQcTypeDao dao) {
		this.dao = dao;
	}

	@Override
	public List<InnerQcTypeEntity> getIqcTypeList() {
		return dao.getIqcTypeList();
	}

}
