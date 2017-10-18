package com.tuniu.gt.callloss.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.callloss.entity.CallLossEntity;
import com.tuniu.gt.callloss.entity.CallLossTetailEntity;

public interface CallLossService extends IServiceBase{
	
	//获取最大id
	public Integer getMaxId();
	
	//修改callloss
	void changeCallStatus(Map<String, Object> paramMap);

	//新增呼损详情
	public void addCallTetail(CallLossTetailEntity callLossTetailEntity);
	
	//查询最大呼损详情记录数
	public Integer queryDetailCount(Map<String, Object> paramMap);

	//查询呼损详情
	public List<Map> queryDetail(Map<String, Object> paramMap);
	
	int getCount(Map<String, Object> paramMap);
	
}
