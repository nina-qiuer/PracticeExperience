package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IAppointManagerMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-appoint_manager")
public class AppointManagerDao extends DaoBase<AppointManagerEntity, IAppointManagerMap>  implements IAppointManagerMap {
	public AppointManagerDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "appoint_manager";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-appoint_manager")
	public void setMapper(IAppointManagerMap mapper) {
		this.mapper = mapper;
	}

    @Override
    public int count(Map paramMap) {
        return mapper.count(paramMap);
    }

    @Override
	public List<AppointManagerEntity> getVipDepartGroupList(Map<String, Object> paramMap) {
		return mapper.getVipDepartGroupList(paramMap);
	}
}
