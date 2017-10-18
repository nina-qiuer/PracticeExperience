package com.tuniu.gt.callloss.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.callloss.dao.sqlmap.imap.CallLossPeriodMap;
import com.tuniu.gt.callloss.entity.CallLossPeriodEntity;



@Repository("callloss_dao_impl-calllossPeriod")
public class CallLossPeriodDao extends DaoBase<CallLossPeriodEntity, CallLossPeriodMap>  implements CallLossPeriodMap {

	public CallLossPeriodDao() {  
		tableName = "call_loss_period";		
	}

	@Autowired
	@Qualifier("callloss_dao_sqlmap-calllossPeriod")
	public void setMapper(CallLossPeriodMap mapper) {
		this.mapper = mapper;
	}

	/**
	 * 获取数据同步开始时间
	 * 表中id=1的记录，记录的是整个同步的开始时间，如2014-04-17 19:30:00表示同步这个时间之后的数据
	 * @return 
	 */
	@Override
	public String getRecordBeginTime() {
		return mapper.getRecordBeginTime();
	}

	/**
	 * 根据id设置记录的del_flag为1
	 * id为1的记录不能删
	 * @return 
	 */
	@Override
	public void deletePeriodById(Map map) {
		Integer id = Integer.parseInt((String) map.get("id"));
		if(id>1){
			mapper.deletePeriodById(map);
		}
	}

}
