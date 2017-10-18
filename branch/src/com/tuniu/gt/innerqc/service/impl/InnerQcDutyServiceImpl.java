package com.tuniu.gt.innerqc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.innerqc.dao.impl.InnerQcDutyDao;
import com.tuniu.gt.innerqc.entity.InnerQcDutyEntity;
import com.tuniu.gt.innerqc.service.InnerQcDutyService;


@Service("innerqc_service_impl-inner_qc_duty")
public class InnerQcDutyServiceImpl extends ServiceBaseImpl<InnerQcDutyDao> implements InnerQcDutyService {
	
	@Autowired
	@Qualifier("innerqc_dao_impl-inner_qc_duty")
	public void setDao(InnerQcDutyDao dao) {
		this.dao = dao;
	}

	@Override
	public List<InnerQcDutyEntity> getDutyList(InnerQcDutyEntity entity) {
		return dao.getDutyList(entity);
	}

}
