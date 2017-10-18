package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QualityProblemDetailDto;
import com.tuniu.qms.qc.model.CmpAndInnerQcBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QualityProblemDetail;

public interface QcBillMapper extends BaseMapper<QcBill, QcBillDto> {
	
	void addQcBill(QcBill qcBill);
	
	void deleteBatch(Map<String, Object> params);
	
	void addBatch(List<QcBill> list);
	
	/** 追加质检备注 */
	void addQcRemark(QcBill qc);
	
	/**
	 * 根据质检单号查询投诉等级
	 * @param qcId
	 * @return
	 */
	QcBill getComplaintLevel(Integer qcId);
	
	/**
	 * 修改投诉等级
	 * @param qcBill
	 */
	void updateCmpLevel(QcBill qcBill);
	
	/** 质检任务查询 */
	List<QcBill> listNormalQcJob(QcBillDto dto);
	
	/** 质检自动分单查询需要分单的数据 */
	List<QcBill> listWaitDistrib();
    
    List<QcBill> listWaitDistribNonOrd();
	
	QcBill getQcBill(QcBillDto dto);
	
	/**
	 * 查询到期时间为空的质检单
	 * @param map
	 * @return
	 */
     List<QcBill> getExpirationList(Map<String, Object> map);
     
     
     int getOrderIsExist(Map<String, Object> map);
     
     /**
      * 批量插入尾款超时质检单
      * @param list
      */
    void addOperateBatch(List<QcBill> list);
     
 	List<Integer> listQcId();
 	
 	List<Integer> listQcAndQuality();
 	
 	List<QualityProblemDetail> getGuideDetail(QualityProblemDetailDto dto);
 	
 	int getGuideCount(QualityProblemDetailDto dto);
 	
 	 Map<String, Object> getGuideSateCount(QualityProblemDetailDto dto);
 	 
 	List<CmpAndInnerQcBill>  getAirAndTraffic(QcBillDto dto);
 	
 	QcBill getById(Integer qcId);
 	
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
	 * 无订单投诉
	 * @param dto
	 * @return
	 */
	int countNoCmp(QcBillDto dto);
	
	/**
	 * 无订单投诉
	 * @param dto
	 * @return
	 */
	List<QcBill> listNoCmp(QcBillDto dto);
	
	/**
	 * 无投诉质检单中 没有质检期的单子
	 * @return
	 */
	List<QcBill> getNoCmpExpirationList();
	
	/**
	 * 修改质检等级
	 */
	void updateQcLevel(QcBillDto dto);
	
	/**
	 * 获得质检等级
	 * @param qcIds
	 * @return
	 */
	Integer getQcLevelsById(QcBillDto dto);
 	
}
