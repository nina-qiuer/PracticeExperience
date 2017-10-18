package com.tuniu.qms.qc.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.QcBill;

public interface InnerQcBillService extends BaseService<QcBill, QcBillDto> {
	
	/**
	 * 删除垃圾数据
	 * @param map
	 */
	void deleteUnUseQcAndAttach(Map<String, Object> map);
	
	/**
	 * 分配质检员
	 * @param qcBillIds 待分配质检单id列表
	 * @param assignTo 待分配质检员 
	 * @param roleType 执行分配操作的角色类型
	 */
	void updateQcPerson(List<Integer> qcBillIds,String assignTo, User user);
	
	/**
	 * 返回质检中操作
	 * @param id
	 */
	void back2Processing(Integer id);
	
   /**
    * 退回质检单
    * @param qcBill
    * @return
    */
	void returnQcBill(QcBill qcBill,User user);
	
	/** 追加质检备注 */
	void addQcRemark(QcBill qc);
	
	/** 质检任务查询 */
	void listNormalQcJob(QcBillDto dto);
	
	
    /**
     * 根据条件查询质检单
     * @param complaintId
     * @return
     */
	QcBill getQcBill(QcBillDto dto);
	
	int getOrderIsExist(Map<String, Object> map);
	
	List<Integer> listQcId();
	
	List<Integer> listQcAndQuality();
	
	void addInnerQc(QcBill qc);
	
	/**
	 * 批量插入内部质检单
	 * @param qcBillList
	 */
	void batchAddInQcBill(List<QcBill> qcBillList);
	
	/**
	 * 发送投诉质检报告
	 * @param qcId
	 */
	void updateInnerQcReportEmail(QcBill bill, User user);

	/**
	 * 根据订单号获得内部质检的质检人
	 * @param ordId
	 * @return
	 */
	String getQcPersonByOrderId(Integer ordId);
	
}
