package com.tuniu.gt.frm.dao.impl;

import com.tuniu.gt.frm.entity.PinyinEntity;
import com.tuniu.gt.frm.dao.sqlmap.imap.IPinyinMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("frm_dao_impl-pinyin")
public class PinyinDao extends DaoBase<PinyinEntity, IPinyinMap>  implements IPinyinMap {
	public PinyinDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "pinyin";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-pinyin")
	public void setMapper(IPinyinMap mapper) {
		this.mapper = mapper;
	}
}
