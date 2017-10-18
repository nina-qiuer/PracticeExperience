/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author：zhonghaiyang
 * Date：2012-03-31
 * Description: 质检接口
 */
package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.QcEntity;

@Repository("complaint_dao_sqlmap-qc")
public interface IQcMap extends IMapBase { 
	/**
	 * Search QcEntities by params.
	 * @param paramMap search parameters
	 * @return a QC entity list.
	 */
	public List<QcEntity> search(Map<String,Object> paramMap);
	/**
	 * Get qc list by map;
	 * @param map
	 */
	public List<Map<String, Object>> searchMap(Map<String, Object> paramMap);
	/**
	 * Get qc list by id;
	 * @param id
	 */
	public QcEntity searchById(int id);
	/**
	 * insert into ct_qc by qcEntity;
	 * @param id
	 */
	public int insert(QcEntity qcEntity);
	/**
	 * update by orderId;
	 * @param orderId
	 */
	public int updateByOrderId(QcEntity updateOrderId);
	/**
	 * Get name list by map;
	 * @param map
	 */
	public List<Map<String, Object>> searchByName(Map<String, Object> paramMap);
	
	/**
	 * update by complaintid;
	 * @param qcEntity
	 */
	void updateByComplaintId(QcEntity qcEntity);
	
	public void updateSpecialConsultationByIds(Map map) ;
	
	public void updateConsultationById(Map map);
	
	int getExportDataTotal(Map<String, Object> paramMap);
	
	List<Map<String, Object>> getExportData(Map<String, Object> paramMap);
		
}
