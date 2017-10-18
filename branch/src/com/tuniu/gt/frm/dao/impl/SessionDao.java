package com.tuniu.gt.frm.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.tuniu.gt.frm.entity.SessionEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.ISessionMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-session")
public class SessionDao extends DaoBase<SessionEntity, ISessionMap>  implements ISessionMap {
	public SessionDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "session";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-session")
	public void setMapper(ISessionMap mapper) {
		this.mapper = mapper;
	}
	
	public int delete(Integer uid) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("uid", uid);
		return delete(data); 
	}

}
