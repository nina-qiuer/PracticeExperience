package com.tuniu.gt.callloss.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.callloss.dao.sqlmap.imap.CallLossMap;
import com.tuniu.gt.callloss.entity.CallLossEntity;
import com.tuniu.gt.callloss.entity.CallLossTetailEntity;



@Repository("callloss_dao_impl-callloss")
public class CallLossDao extends DaoBase<CallLossEntity, CallLossMap>  implements CallLossMap {

	public CallLossDao() {  
		tableName = "call_loss";		
	}

	@Autowired
	@Qualifier("callloss_dao_sqlmap-callloss")
	public void setMapper(CallLossMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public Integer getMaxId() {
		Integer maxId = mapper.getMaxId();
		if(maxId==null){
			maxId = 0;
		}
		return maxId;
	}
	
	public void changeCallStatus(
		Map<String, Object> paramMap) {
		mapper.changeCallStatus(paramMap);
	}

	public void addCallTetail(CallLossTetailEntity callLossTetailEntity) {
		mapper.addCallTetail(callLossTetailEntity);
	}

	public Integer queryDetailCount(Map<String, Object> paramMap) {
		return mapper.queryDetailCount(paramMap);
	}

	public List<Map> queryDetail(Map<String, Object> paramMap) {
		return mapper.queryDetail(paramMap);
	}

	@Override
	public int getCount(Map<String, Object> paramMap) {
		return mapper.getCount(paramMap);
	}
}
