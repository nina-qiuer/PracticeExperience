package com.tuniu.gt.callloss.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.callloss.dao.impl.CallLossDao;
import com.tuniu.gt.callloss.entity.CallLossTetailEntity;
import com.tuniu.gt.callloss.service.CallLossService;



/**
* @ClassName: EarlyCallLossServiceImpl
* @Description:EarlyCallLoss接口实现
* @author 
* @date 2012-1-20 下午5:04:51
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("callloss_service_callloss_impl-callloss")
public class CallLossImpl extends ServiceBaseImpl<CallLossDao> implements CallLossService {
	@Autowired
	@Qualifier("callloss_dao_impl-callloss")
	public void setDao(CallLossDao dao) {
		this.dao = dao;
	}

	@Override
	public Integer getMaxId() {
		return dao.getMaxId();
	}
	
	public void changeCallStatus(Map<String, Object> paramMap) {
		dao.changeCallStatus(paramMap);
	}
	
	public void addCallTetail(CallLossTetailEntity callLossTetailEntity){
		dao.addCallTetail(callLossTetailEntity);
	}

	@Override
	public Integer queryDetailCount(Map<String, Object> paramMap) {
		return dao.queryDetailCount(paramMap);
	}

	@Override
	public List<Map> queryDetail(Map<String, Object> paramMap) {
		return dao.queryDetail(paramMap);
	}

	@Override
	public int getCount(Map<String, Object> paramMap) {
		return dao.getCount(paramMap);
	}
	
}
