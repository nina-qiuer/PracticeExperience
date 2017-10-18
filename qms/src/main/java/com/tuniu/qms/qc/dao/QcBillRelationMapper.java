package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcBillRelationDto;
import com.tuniu.qms.qc.model.QcBillRelation;

public interface QcBillRelationMapper  extends BaseMapper<QcBillRelation, QcBillRelationDto> {

	   void updateRelation(Map<String, Object> map);
	   
	   List<QcBillRelation> listRelation(Map<String, Object> map);
	   
	   void updateDevRelation(Map<String, Object> map);
	   
	   List<QcBillRelation> listByDevId(QcBillRelationDto dto);
	   
	   void updateByCmpAndDev(QcBillRelationDto dto);
	   /**
	    * 根据研发质检单号和投诉单号找到投诉质检单号
	    * @param map
	    * @return
	    */
	   Integer getQcIdByDevIdAndCmpId(Map<String, Object> map);
}
