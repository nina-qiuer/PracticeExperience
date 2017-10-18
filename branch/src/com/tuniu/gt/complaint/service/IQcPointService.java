package com.tuniu.gt.complaint.service;

import java.util.Map;

import com.tuniu.gt.complaint.entity.QcPointEntity;


public interface IQcPointService {

	/**
	 * 新增质检点信息
	 * @param qcEntity
	 */
	public int saveQcPoint(QcPointEntity  qcEntity );
	
	public  QcPointEntity getQcPoint(Map<String, Object> map);
	
	public int updateQcPoint(QcPointEntity  qcEntity );
	
	public int revokeQcPoint(QcPointEntity  qcEntity );
	
	public void  deleteQcPoint(QcPointEntity qcEntity );
}
