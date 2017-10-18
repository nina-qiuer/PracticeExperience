package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;

public interface IComplaintSolutionService extends IServiceBase {
	
	/**
	 * 根据投诉id获取投诉解决方案
	 */
	public ComplaintSolutionEntity getComplaintSolutionBycompalintId(int complaintId);
	
	void saveComplaintSolution(ComplaintSolutionEntity entity);
	
	void updateComplaintSolution(ComplaintSolutionEntity entity);
	
	Map<String, Object> submitComplaintSolution(ComplaintSolutionEntity entity, int userId, String realName);
	
	void auditComplaintSolution(ComplaintSolutionEntity entity, Map<String,Object> info);
	
	void returnComplaintSolution(Integer complaintId, String backName, String backMsg);
	
	ComplaintSolutionEntity getComplaintSolution(Integer complaintId);
	
	/**
	 * 获取待修复数据
	 */
	List<ComplaintSolutionEntity> getNeedRepairList(int scope);
	
	List<ComplaintSolutionEntity> getCmpSolutionByCmpId(Map<String, Object> map );
	
	/**
	 * 获取需要同步的对客解决方案
	 * @param map
	 * @return
	 */
	List<ComplaintSolutionEntity> getNeedSynchSolution(Map<String, Object> map);
	
	/**
	 * 通过id更新对客解决方案银行信息
	 * @param complaintSolutionEntity
	 */
	void updateCardUniqueId(Map<String, Object> map);
	
	/**
	 * 将银行卡信息塞到实体类中
	 * @param cardUniqueId
	 * @param paymentFlag
	 * @param complaintSolutionEntity
	 * @return
	 */
	ComplaintSolutionEntity setCardInfo(ComplaintSolutionEntity entity);
	
	/**
	 * 根据财务现金id获取对客解决方案
	 * @param paramMap
	 * @return
	 */
	List<ComplaintSolutionEntity> getComplaintIdByReFundId(Map<String, Object> paramMap);
}
