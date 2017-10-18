package com.tuniu.qms.qs.service;

import java.util.List;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.CmpImproveDto;
import com.tuniu.qms.qs.model.CmpImprove;

public interface CmpImproveService extends BaseService<CmpImprove, CmpImproveDto>{
	
	/**
	 * 根据投诉id 获得该下的改进报告
	 * @param cmpId
	 * @return
	 */
	List<CmpImprove> listByCmpId(Integer cmpId);
	
	/**
	 * 超过流程处理到期时间列表   添加时间+5个工作日
	 * @return
	 */
	List<CmpImprove> getOutHandleEndTime();
	
	/**
	 * 更新处理人
	 * @param impBillIds
	 * @param assignTo
	 * @param user
	 */
	void updateHandlePerson(List<Integer> impBillIds, String assignTo, User user);
	
	/**
	 * 获得有订单投诉、待分配的改进报告
	 * @return
	 */
	List<CmpImprove> listWaitDistrib();
	
	/**
	 * 获得无订单投诉、待分配的改进报告
	 * @return
	 */
	List<CmpImprove> listWaitDistribNonOrd();
	
	/**
	 * 本地改进报告提交
	 * @param obj
	 * @return
	 */
	String localSubmitImpBill(CmpImprove obj);
	
	/**
	 * CMP系统改进报告提交
	 * @param cmpImprove
	 * @return
	 */
	boolean cmpSubmitImpBill(CmpImprove cmpImprove);

}
