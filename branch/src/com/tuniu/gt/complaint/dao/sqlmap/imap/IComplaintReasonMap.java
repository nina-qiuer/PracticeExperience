package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.ReasonSynchCrmEntity;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-complaint_reason")
public interface IComplaintReasonMap extends IMapBase { 
	List<Map> queryReasonById(Integer complaintId);
	
	Integer queryReasonCountById(Integer complaintId);
	
	List<ComplaintReasonEntity> getReasonAndQualitycostList(Map<String, Object> paramMap);
	
	List<Map<String, Object>> getDistDesc(int complaintId);

    /**
     * 根据投诉id获取非低满意度点评投诉事宜数量
     * @param complaintId
     * @return
     */
    Integer getNotDisSatisfiedReasonCountByCmpId(int complaintId);

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
