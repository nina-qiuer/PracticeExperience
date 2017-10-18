package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.ReasonSynchCrmEntity;
public interface IComplaintReasonService extends IServiceBase {

	List<Map> queryReasonById(Integer complaintId);

	Integer queryReasonCountById(Integer complaintId);
	
	List<ComplaintReasonEntity> getReasonAndQualitycostList(Map<String, Object> paramMap);
	
	void insertReasonList(int userId, String userName, int compId, String compCity, List<ComplaintReasonEntity> reasonList,Integer specialEventFlag) throws Exception;
	
	List<Map<String, Object>> getDistDesc(int complaintId);

    /**
     * 是否为同团投诉预警投诉事宜
     * @param a
     * @return
     */
    boolean isSameGroupComplaintWarningReason(ComplaintReasonEntity entity);

    /**
	 * 根据投诉单号或时间区间同步投诉事宜
	 * @param paramMap
	 * @return
	 */
    List<ReasonSynchCrmEntity> getCrmComplaintReason(Map<String, Object> paramMap);
    
    /**
     * 根据时间区间查询投诉事宜数量
     * @param paramMap
     * @return
     */
	Integer getCrmComplaintReasonCount(Map<String, Object> paramMap);
}
