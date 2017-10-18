package com.tuniu.gt.datafix.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.datafix.dao.sqlmap.imap.IDataFixMap;
import com.tuniu.gt.datafix.entity.DataFixEntity;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;

@Repository("datafix_dao_impl-datafix")
public class DataFixDao extends DaoBase<DataFixEntity, IDataFixMap>  implements IDataFixMap {
	public DataFixDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "data_fix";		
	}
	
	@Autowired
	@Qualifier("datafix_dao_sqlmap-datafix")
	public void setMapper(IDataFixMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public Integer getSignSatisfactionDataFixMaxId() {
		return mapper.getSignSatisfactionDataFixMaxId();
	}

	@Override
	public SignSatisfactionEntity getNextSignSatisfactionDataFixId(Map map) {
		return mapper.getNextSignSatisfactionDataFixId(map);
	}

	@Override
	public void updateCityInfo(Map map) {
		mapper.updateCityInfo(map);
	}

	@Override
	public void updateMaxId(Integer id) {
		mapper.updateMaxId(id);
	}


}
