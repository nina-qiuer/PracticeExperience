package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.FollowUpRecordDao;
import com.tuniu.gt.complaint.entity.FollowUpRecordSynchEntity;
import com.tuniu.gt.complaint.service.IFollowUpRecordService;
@Service("complaint_service_complaint_follow_up_record_impl-follow_up_record")
public class FollowUpRecordServiceImpl extends ServiceBaseImpl<FollowUpRecordDao> implements IFollowUpRecordService {
	@Autowired
	@Qualifier("complaint_dao_impl-follow_up_record")
	public void setDao(FollowUpRecordDao dao) {
		this.dao = dao;
	}
	
	public Map<String, Object> getMinAndMaxDealTime(Integer complaintId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("complaintId", complaintId);
        return dao.getMinAndMaxDealTime(paramMap);
    }

    @Override
    public Map<String, String> getDealPersonExtension(Integer complaintId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("complaintId", complaintId);
        List<Map<String, Object>> resultList = dao.getDealPersonExtension(paramMap);
        Map<String, String> dealPersonExtensionMap = new HashMap<String, String>();
        for (Map<String, Object> result : resultList) {
            dealPersonExtensionMap.put((String) result.get("extension"), (String)result.get("peopleName"));
        }
        return dealPersonExtensionMap;
    
    }
    
    @Override
	public List<FollowUpRecordSynchEntity> getCrmComplaintFollow(Map<String, Object> paramMap){
		return dao.getCrmComplaintFollow(paramMap);
	}
	
	@Override
	public Integer getCrmComplaintFollowCount(Map<String, Object> paramMap){
		return dao.getCrmComplaintFollowCount(paramMap);
	}
}
