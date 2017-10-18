package com.tuniu.gt.techsupport.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintMap;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcNoteMap;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.QcNoteEntity;
import com.tuniu.gt.techsupport.dao.sqlmap.imap.ITechSupportMap;
import com.tuniu.gt.techsupport.entity.TechSupportEntity;

@Repository("ts_dao_impl-tech_support")
public class TechSupportDao extends DaoBase<TechSupportEntity, ITechSupportMap>  implements ITechSupportMap 
{
	
	@Autowired
	@Qualifier("ts_dao_sqlmap-tech_support")
	public void setMapper(ITechSupportMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public void update(String sqlParam) {
		mapper.update(sqlParam);
	}
	
	public int getAffectRows(String sqlParam){
		return mapper.getAffectRows(sqlParam);
	}


}
