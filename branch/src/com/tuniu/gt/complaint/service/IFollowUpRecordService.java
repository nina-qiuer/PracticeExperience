package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.FollowUpRecordSynchEntity;
public interface IFollowUpRecordService extends IServiceBase {
    
    Map<String, Object> getMinAndMaxDealTime(Integer complaintId);

    /**
     * @param id
     * @return
     */
    Map<String, String> getDealPersonExtension(Integer complaintId);
    
    /**
	 * 根据投诉单号或时间区间同步跟进记录
	 * @param paramMap
	 * @return
	 */
	List<FollowUpRecordSynchEntity> getCrmComplaintFollow(Map<String, Object> paramMap);
	
	/**
     * 根据时间区间查询跟进记录
     * @param paramMap
     * @return
     */
	Integer getCrmComplaintFollowCount(Map<String, Object> paramMap);
}
