package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintPointMap;
import com.tuniu.gt.complaint.entity.ComplaintPointEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;

@Repository("complaint_dao_impl-complaint_point")
public class ComplaintPointDao extends DaoBase<ComplaintPointEntity, IComplaintPointMap> implements IComplaintPointMap {
	
	private static Logger logger = Logger.getLogger(ComplaintPointDao.class);
	
	public ComplaintPointDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_point";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_point")
	public void setMapper(IComplaintPointMap mapper) {
		this.mapper = mapper;
	}

    /**
     * @param affectivePointList
     */
    public void batchInsert(List<ComplaintPointEntity> affectivePointList) {
        mapper.batchInsert(affectivePointList);
    }
    
    /**
     * 获得固定时间存在有效理论赔付的投诉id
     * @param map
     * @return
     */
	public List<Integer> getTheoryPayout(Map<String, Object> map) {
		return mapper.getTheoryPayout(map);
	}
	
}
