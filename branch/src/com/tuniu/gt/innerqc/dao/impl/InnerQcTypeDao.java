package com.tuniu.gt.innerqc.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.innerqc.dao.sqlmap.imap.InnerQcTypeMap;
import com.tuniu.gt.innerqc.entity.InnerQcTypeEntity;


@Repository("innerqc_dao_impl-inner_qc_type")
public class InnerQcTypeDao extends DaoBase<InnerQcTypeEntity, InnerQcTypeMap> implements InnerQcTypeMap {

	public InnerQcTypeDao() {
		tableName = "qc_inner_qc_type";
	}
	
	@Autowired
	@Qualifier("innerqc_dao_sqlmap-inner_qc_type")
	public void setMapper(InnerQcTypeMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<InnerQcTypeEntity> getIqcTypeList() {
		return mapper.getIqcTypeList();
	}

}
