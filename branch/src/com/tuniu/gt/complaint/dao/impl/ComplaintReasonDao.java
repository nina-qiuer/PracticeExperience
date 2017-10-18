package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintReasonMap;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.ReasonSynchCrmEntity;

@Repository("complaint_dao_impl-complaint_reason")
public class ComplaintReasonDao extends DaoBase<ComplaintReasonEntity, IComplaintReasonMap> implements IComplaintReasonMap {
	
	private static Logger logger = Logger.getLogger(ComplaintReasonDao.class);
	
	public ComplaintReasonDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_reason";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_reason")
	public void setMapper(IComplaintReasonMap mapper) {
		this.mapper = mapper;
	}

	public List<Map> queryReasonById(Integer complaintId) {
		return mapper.queryReasonById(complaintId);
	}

	public Integer queryReasonCountById(Integer complaintId) {
		return mapper.queryReasonCountById(complaintId);
		
	}

	@Override
	public List<ComplaintReasonEntity> getReasonAndQualitycostList(Map<String, Object> paramMap) {
		return mapper.getReasonAndQualitycostList(paramMap);
	}
	
	public void insertReasonList(int compId, List<ComplaintReasonEntity> reasonList) {
		for (ComplaintReasonEntity reason : reasonList) { // 添加投诉事宜
			logger.info("insertReasonList Begin");
			reason.setComplaintId(compId);
			super.insert(reason);
			logger.info("insertReasonList End, Complaint Id is " + compId);
		}
	}

	@Override
	public List<Map<String, Object>> getDistDesc(int complaintId) {
		return mapper.getDistDesc(complaintId);
	}

    /**
     * @param id
     * @return
     */
    public boolean isPureDisSatisfiedComplaint(int complaintId) {
        Integer count = getNotDisSatisfiedReasonCountByCmpId(complaintId);
        return count== 0;
    }

    @Override
    public Integer getNotDisSatisfiedReasonCountByCmpId(int complaintId) {
        return mapper.getNotDisSatisfiedReasonCountByCmpId(complaintId);
    }
	
    @Override
    public List<ReasonSynchCrmEntity> getCrmComplaintReason(Map<String, Object> paramMap){
    	return mapper.getCrmComplaintReason(paramMap);
    }
    
    @Override
	public Integer getCrmComplaintReasonCount(Map<String, Object> paramMap){
    	return mapper.getCrmComplaintReasonCount(paramMap);
    }
}
