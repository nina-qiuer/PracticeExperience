package com.tuniu.gt.frm.dao.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.frm.dao.sqlmap.imap.IFestivalMap;
import com.tuniu.gt.frm.entity.FestivalEntity;

@Repository("frm_dao_impl-festival")
public class FestivalDao extends DaoBase<FestivalEntity, IFestivalMap>  implements IFestivalMap {
	public FestivalDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "festival_date";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-festival")
	public void setMapper(IFestivalMap mapper) {
		this.mapper = mapper;
	}
	
	public List<String> getFYearList() {
		return mapper.getFYearList();
	}

	@Override
	public List<FestivalEntity> getFesNameList() {
		return mapper.getFesNameList();
	}

	@Override
	public void batchInsert(List<FestivalEntity> entList) {
		mapper.batchInsert(entList);
	}

	@Override
	public void delFes(Map<String, Object> params) {
		mapper.delFes(params);
	}

	@Override
	public List<FestivalEntity> getFestivalAfterToday(Date today) {
		return mapper.getFestivalAfterToday(today);
	}

}
