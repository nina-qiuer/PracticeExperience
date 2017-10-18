package com.tuniu.gt.frm.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.frm.entity.FestivalEntity;

import tuniu.frm.core.IMapBase;

@Repository("frm_dao_sqlmap-festival")
public interface IFestivalMap extends IMapBase {
	
	List<String> getFYearList();
	
	List<FestivalEntity> getFesNameList();
	
	void batchInsert(List<FestivalEntity> entList);
	
	void delFes(Map<String, Object> params);
	
	List<FestivalEntity> getFestivalAfterToday(java.sql.Date today);

}
