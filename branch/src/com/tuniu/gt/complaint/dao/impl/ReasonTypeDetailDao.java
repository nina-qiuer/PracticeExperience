package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IReasonTypeDetailMap;
import com.tuniu.gt.complaint.entity.ReasonTypeEntity;

@Repository("complaint_dao_impl-reason_type_detail")
public class ReasonTypeDetailDao extends DaoBase<ReasonTypeEntity, IReasonTypeDetailMap>  implements IReasonTypeDetailMap {
	public ReasonTypeDetailDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "reason_type_detail";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-reason_type_detail")
	public void setMapper(IReasonTypeDetailMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<ReasonTypeEntity> getReasonTypeDetailList(){
		return mapper.getReasonTypeDetailList();
	}
}
