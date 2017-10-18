package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IFollowUpRecordMap;
import com.tuniu.gt.complaint.entity.FollowUpRecordEntity;
import com.tuniu.gt.complaint.entity.FollowUpRecordSynchEntity;

@Repository("complaint_dao_impl-follow_up_record")
public class FollowUpRecordDao extends DaoBase<FollowUpRecordEntity, IFollowUpRecordMap>  implements IFollowUpRecordMap {
	public FollowUpRecordDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "follow_up_record";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-follow_up_record")
	public void setMapper(IFollowUpRecordMap mapper) {
		this.mapper = mapper;
	}

    /**
     * @param paramMap
     * @return
     */
    public Map<String, Object> getMinAndMaxDealTime(Map<String, Object> paramMap) {
        return mapper.getMinAndMaxDealTime(paramMap);
    }

    /**
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> getDealPersonExtension(Map<String, Object> paramMap) {
        return mapper.getDealPersonExtension(paramMap);
    }
    
    @Override
  	public List<FollowUpRecordSynchEntity> getCrmComplaintFollow(Map<String, Object> paramMap){
  		return mapper.getCrmComplaintFollow(paramMap);
  	}
  	
  	@Override
  	public Integer getCrmComplaintFollowCount(Map<String, Object> paramMap){
  		return mapper.getCrmComplaintFollowCount(paramMap);
  	}
}
