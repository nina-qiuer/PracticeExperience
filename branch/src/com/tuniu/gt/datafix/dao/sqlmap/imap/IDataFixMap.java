package com.tuniu.gt.datafix.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;

@Repository("datafix_dao_sqlmap-datafix")
public interface IDataFixMap extends IMapBase { 

	public Integer getSignSatisfactionDataFixMaxId();
	
	public SignSatisfactionEntity getNextSignSatisfactionDataFixId(Map map);
    
	public void updateCityInfo(Map map) ;
	
	public void updateMaxId(Integer id);
}
