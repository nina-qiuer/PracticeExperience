package com.tuniu.gt.callloss.dao.sqlmap.imap;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.callloss.entity.CallLossTetailEntity;

import tuniu.frm.core.IMapBase;


@Repository("callloss_dao_sqlmap-callloss")
public interface CallLossMap extends IMapBase { 

	public Integer getMaxId();
	
	void changeCallStatus(Map<String, Object> paramMap);
	
	public void addCallTetail(CallLossTetailEntity callLossTetailEntity);
	
	public Integer queryDetailCount(Map<String, Object> paramMap);

	public List<Map> queryDetail(Map<String, Object> paramMap);
	
	int getCount(Map<String, Object> paramMap);
	
}
