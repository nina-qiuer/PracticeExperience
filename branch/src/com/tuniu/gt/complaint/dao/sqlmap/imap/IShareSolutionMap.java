package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-share_solution")
public interface IShareSolutionMap extends IMapBase {
	
	List<Map<String, Object>> getConfirmInfoNum(int agencyId);
	
	int getAgencyPayoutNum(Map<String, Object> paramMap);
	
	List<Map<String, Object>> getAgencyPayInfoList(Map<String, Object> paramMap);
	
	Map<String, Object> getAgencyPayInfoDetail(Map<String, Object> paramMap);
	
	void confirmPayout(Map<String, Object> paramMap);
	
	void appealPayout(Map<String, Object> paramMap);
	
	Map<String, Object> getDataForRepair(Integer complaintId);
	
	Map<String, Object> getOrderIndemnityShareInfo(Integer orderId);

	List<Integer> getShareSolution(Map<String, Object> paramMap);
	
    List<Map<String, Object>> getCmpSpecialByCmpId(Map<String, Object> paramMap);
    
    /**
     * 获取某一日审核通过的质量工具赔款
     */
    List<Map<String, Object>> getQualityToolCost(String dateStr);
    
    /**
     * 获取某一日审核通过的不成团赔款
     */
    List<Map<String, Object>> getUnGroupCost(String dateStr);
    
}
