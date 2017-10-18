package com.tuniu.gt.complaint.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.vo.AgencyConfirmInfoPage;
import com.tuniu.gt.complaint.vo.AgencyPayInfoPage;
import com.tuniu.gt.frm.entity.UserEntity;
/**
 * 分担方案service接口
 */
public interface IShareSolutionService extends IServiceBase {
	
	public ShareSolutionEntity getShareSolutionBycompalintId(int complaintId);
	
	public List<Map<String, Object>> getConfirmInfoNum(int agencyId);
	
	public AgencyPayInfoPage getAgencyPayInfoPage(Map<String, Object> paramMap);
	
	AgencyConfirmInfoPage getAgencyConfirmInfoPage(Map<String, Object> paramMap);
	
	AgencyConfirmInfoPage getNonbShareAppealInfoPage(Map<String, Object> paramMap);
	
	Map<String, Object> getAgencyPayInfoDetail(Map<String, Object> paramMap);
	
	int confirmPayout(Map<String, Object> paramMap);
	
	int appealPayout(Map<String, Object> paramMap);
	
	ShareSolutionEntity getShareSolution(Integer complaintId);
	
	void saveShareSolution(ShareSolutionEntity entity);
	
	void updateShareSolution(ShareSolutionEntity entity);
	
	void submitShareSolution(ShareSolutionEntity entity);
	
	void adjustShareSolution(ShareSolutionEntity entity, int userId);
	
	void auditShareSolution(ShareSolutionEntity entity, int userId);
	
	void returnShareSolution(ShareSolutionEntity entity, String backMsg);
	
	/**
	 * 修复数据使用
	 */
	Map<String, Object> getDataForRepair(Integer complaintId);

	/**
	 * 订单相关赔付承担信息查询
	 */
	public Map<String, Object> getOrderIndemnityShareInfo(Integer orderId);
	
	/**
	 * 查询规定时间内咨询单赔付金额不为0  和 存在理论赔付的投诉单
	 * @param map
	 * @return
	 */
	List<Integer> getShareSolution(Map<String, Object> map);
	/**
	 * 获取公司承担总额
	 * @param map
	 * @return
	 */
	List<Map<String, Object>>  getCmpSpecialByCmpId(Map<String, Object> map);
	
	/**
     * 获取某一日审核通过的质量工具赔款
     */
    List<Map<String, Object>> getQualityToolCost(String dateStr);
    
    /**
     * 获取某一日审核通过的不成团赔款
     */
    List<Map<String, Object>> getUnGroupCost(String dateStr);
    
    /**
     * 发起申诉流程
     * @param paramMap
     */
    void launchSupplierAppeal(SupportShareEntity support,String appealContent,UserEntity user);
    
    /**
     * 
     * @param approvalResult
     * @param complaintId
     * @param oaId
     * @param newsupport
     * @param auditorId
     * @param companyCharge
     */
    void appealShareSolution(Integer approvalResult,Integer complaintId,String oaId,SupportShareEntity newsupport,
    		Integer auditorId,BigDecimal companyCharge,String approval_comment);
}
