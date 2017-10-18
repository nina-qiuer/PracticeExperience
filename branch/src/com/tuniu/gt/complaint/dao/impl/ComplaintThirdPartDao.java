package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintThirdPartMap;
import com.tuniu.gt.complaint.entity.ComplaintThirdPartEntity;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

@Repository("complaint_dao_impl-complaint_third_part")
public class ComplaintThirdPartDao extends DaoBase<ComplaintThirdPartEntity, IComplaintThirdPartMap>
		implements IComplaintThirdPartMap {
	public ComplaintThirdPartDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_third_part";
	}

	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_third_part")
	public void setMapper(IComplaintThirdPartMap mapper) {
		this.mapper = (IComplaintThirdPartMap) mapper;
	}
	
	public Integer getComplaintThirdPartCount(Map<String,Object> paramMap){
		return mapper.getComplaintThirdPartCount(paramMap);
	}
	
	public List<ComplaintThirdPartEntity> getComplaintThirdPartLists(Map<String,Object> paramMap){
		return mapper.getComplaintThirdPartLists(paramMap);
	}
}
