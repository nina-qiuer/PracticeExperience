package com.tuniu.gt.innerqc.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.innerqc.dao.sqlmap.imap.InnerQcAttachMap;
import com.tuniu.gt.innerqc.entity.InnerQcAttachEntity;


@Repository("innerqc_dao_impl-inner_qc_attach")
public class InnerQcAttachDao extends DaoBase<InnerQcAttachEntity, InnerQcAttachMap> implements InnerQcAttachMap {

	public InnerQcAttachDao() {
		tableName = "qc_inner_qc_attach";
	}

	@Autowired
	@Qualifier("innerqc_dao_sqlmap-inner_qc_attach")
	public void setMapper(InnerQcAttachMap mapper) {
		this.mapper = mapper;
	}

}
