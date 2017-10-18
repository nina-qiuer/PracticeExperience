package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import com.tuniu.gt.complaint.entity.ReasonTypeEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IReasonTypeMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-reason_type")
public class ReasonTypeDao extends DaoBase<ReasonTypeEntity, IReasonTypeMap>  implements IReasonTypeMap {
	public ReasonTypeDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "reason_type";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-reason_type")
	public void setMapper(IReasonTypeMap mapper) {
		this.mapper = mapper;
	}

    /**
     * @param parentName
     * @return
     */
    public List<String> getChilderByParentName(String parentName) {
        return mapper.getChilderByParentName(parentName);
    }
}
