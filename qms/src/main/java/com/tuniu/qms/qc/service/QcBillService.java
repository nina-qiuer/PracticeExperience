package com.tuniu.qms.qc.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QualityProblemDetailDto;
import com.tuniu.qms.qc.model.CmpAndInnerQcBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcPoint;
import com.tuniu.qms.qc.model.QualityProblemDetail;

public interface QcBillService extends BaseService<QcBill, QcBillDto> {
	
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
	void backToProcessing(Integer id);
	
   /**
    * 撤销质检单
    * @param qcBill
    * @return
    */
	void deleteQcBill(QcBill qcBill);
	
	/** 追加质检备注 */
	void addQcRemark(QcBill qc);
	/**
	 * 根据质检单号查询投诉等级数据
	 * @param qcId
	 * @return
	 */
	QcBill getComplaintLevel(Integer qcId);
	
	/**
	 * 更新投诉等级
	 * @param qcBill
	 */
	void updateCmpLevel(QcBill qcBill);
	
	/** 质检任务查询 */
	void listNormalQcJob(QcBillDto dto);
	
	
	/**
	 * 质检自动分单查询需要分单的数据
	 * @param map
	 * @return
	 */
    List<QcBill> listWaitDistrib();
    
    List<QcBill> listWaitDistribNonOrd();
    
    /**
     * 保存质检点信息
     * @param qcPoint
     */
    void addQcPoint(QcPoint qcPoint);
    
    /**
     * 根据条件查询质检单
     * @param complaintId
     * @return
     */
	QcBill getQcBill(QcBillDto dto);
	
	/**
	 * 发送投诉质检报告
	 * @param qcId
	 */
	void updateCmpQcReportEmail(Integer qcId,String reEmails,String ccEmails, String subject, User user,String qcState);
	
	String getCmpQcReportEmailContent(Integer qcId);
	
	/**
	 * 获取到期时间为空的数据
	 * @return
	 */
	List<QcBill> getExpirationList(Map<String, Object> map);
	
	
	int getOrderIsExist(Map<String, Object> map);
	
	List<Integer> listQcId();
	
	List<Integer> listQcAndQuality();
	
	/**
	 * 退回质检单
	 * @param qcId
	 * @param user
	 */
	void updateReturnQcBill(Integer qcId,User user);
	
	void addCopyQcBill(Integer id,QcBill copyQcBill,User user);
	
	List<QualityProblemDetail> getGuideDetail(QualityProblemDetailDto dto);
	
	int getGuideCount(QualityProblemDetailDto dto);
	
	Map<String, Object> getGuideSateCount(QualityProblemDetailDto dto); 
	
	List<CmpAndInnerQcBill>  getAirAndTraffic(QcBillDto dto);
	
	void addQcBill(QcBill qcBill);
	
	QcBill getById(Integer qcId);

	Map<String, Object> QcReportContent(Integer id);
	
	/**
	 * 获得待分配或质检中的投诉质检单
	 * @param map 
	 * @return
	 */
	List<QcBill> getComplaintQcBill(Map<String, Object> map);
	
	/**
	 * 撤销 对应的投诉为无赔偿、非低满意度、无申请质检的质检单
	 * @param id
	 */
	void cancelQcBill(Integer id);
	
	/**
	 * 无订单投诉列表
	 * @param dto
	 */
	void loadPageNoCmp(QcBillDto dto);
	
	/**
	 * 无投诉质检单中 没有质检期的单子
	 * @return
	 */
	List<QcBill> getNoCmpExpirationList();
	
	/**
	 * 更新质检等级
	 * @param dto
	 * @param user
	 */
	void updateQcLevel(QcBillDto dto, User user);
	
	/**
	 * 获得质检等级
	 * @param dto
	 * @return
	 */
	Integer getQcLevelsById(QcBillDto dto);

}
