package com.tuniu.gt.callloss.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.callloss.dao.impl.CallLossPeriodDao;
import com.tuniu.gt.callloss.service.CallLossPeroidService;



/**
* @ClassName: EarlyCallLossServiceImpl
* @Description:EarlyCallLoss接口实现
* @author 
* @date 2012-1-20 下午5:04:51
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("callloss_service_callloss_impl-calllossPeriod")
public class CallLossPeriodImpl extends ServiceBaseImpl<CallLossPeriodDao> implements CallLossPeroidService {
	@Autowired
	@Qualifier("callloss_dao_impl-calllossPeriod")
	public void setDao(CallLossPeriodDao dao) {
		this.dao = dao;
	}

	/**
	 * 获取数据同步开始时间
	 * 表中id=1的记录，记录的是整个同步的开始时间，如2014-04-17 19:30:00表示同步这个时间之后的数据
	 * @return 
	 */
	@Override
	public String getRecordBeginTime() {
		return dao.getRecordBeginTime();
	}

	/**
	 * 根据id设置记录的del_flag为1
	 * @return 
	 */
	@Override
	public void deletePeriodById(Map map) {
		dao.deletePeriodById(map);
		
	}

}
