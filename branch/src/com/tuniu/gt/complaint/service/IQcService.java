/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author：zhonghaiyang
 * Date：2012-03-31
 * Description: 质检接口
 */
package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.QcQuestionEntity;
public interface IQcService extends IServiceBase {
	
	/**
	 * Get QcEntity by id.
	 * @param id
	 * @return a QC entity.
	 */
	public QcEntity find(int id);
	
	/**
	 * Search QcEntities by params.
	 * @param paramMap search parameters
	 * @return a QC entity list.
	 */
	public List<QcEntity> search(Map<String, Object> paramMap);
	
	/**
	 * Add a QC Entity.
	 * @param qc
	 * @return index of QC Entity.
	 */
	public int add(QcEntity qc);
	
	/**
	 * Delete a QC Entity.
	 * @param id index of QC Entity.
	 */
	public void delete(int id);
	
	/**
	 * Get questions by q_id;
	 * @param id
	 */
	public List<QcQuestionEntity> getQuestionList(int id);
	
	/**
	 * Get qc list by map;
	 * @param map
	 */
	public List<Map<String, Object>> searchMap(Map<String, Object> paramMap);
	/**
	 * update by orderId;
	 * @param orderId
	 */
	public void updateByOrderId(QcEntity updateOrderId);
	
	/**
	 * Get names;
	 * @param map
	 */
	public List<Map<String, Object>> findNames(Map<String, Object> paramMap);
	
	public void updateByComplaintId(QcEntity qcEntity);
	
	boolean isTimeout(QcEntity qcEnt);
	
	/**
	 * 根据ids更新特殊咨询单标志位，使其在待处理等正常页签中显示
	 * @param map
	 */
	public void updateSpecialConsultationByIds(Map  map);
	
	/**
	 * 根据id更新咨询单标志位
	 * @param map
	 */
	public void updateConsultationById(Map  map);
	
	void assignQcPerson(QcEntity qcEnt, ComplaintEntity compEnt);
	
	int getExportDataTotal(Map<String, Object> paramMap);
	
	List<Map<String, Object>> getExportData(Map<String, Object> paramMap);
	
}
