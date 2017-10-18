package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.QcBill;

public interface InnerQcBillMapper extends BaseMapper<QcBill, QcBillDto> {
	
	/**
	 * 删除垃圾数据
	 * @param map
	 */
	void deleteAttachByQcId(Map<String, Object> map);
	
	void deleteQc(Map<String, Object> map);
	
	/** 追加质检备注 */
	void addQcRemark(QcBill qc);
	
	/**
	 * 根据质检单号查询投诉等级
	 * @param qcId
	 * @return
	 */
	QcBill getComplaintLevel(Integer qcId);
	
	
	/** 质检任务查询 */
	List<QcBill> listNormalQcJob(QcBillDto dto);
	
	QcBill getQcBill(QcBillDto dto);
	
     
     int getOrderIsExist(Map<String, Object> map);
     
     /**
      * 批量插入尾款超时质检单
      * @param list
      */
     void addOperateBatch(List<QcBill> list);
     
 	List<Integer> listQcId();
 	
 	List<Integer> listQcAndQuality();
 	
 	/**
 	 * 批量插入内部质检单
 	 * @param qcBillList
 	 */
	void batchAddInQcBill(List<QcBill> qcBillList);

	/**
	 * 根据订单号获得内部质检的质检人
	 * @param ordId
	 * @return
	 */
	String getQcPersonByOrderId(Integer ordId);
}
