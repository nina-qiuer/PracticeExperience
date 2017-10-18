package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.FollowUpRecordSynchEntity;

@Repository("complaint_dao_sqlmap-follow_up_record")
public interface IFollowUpRecordMap extends IMapBase {

    /**
     * @param paramMap
     * @return
     */
    Map<String, Object> getMinAndMaxDealTime(Map<String, Object> paramMap);

    /**
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getDealPersonExtension(Map<String, Object> paramMap); 
	
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
