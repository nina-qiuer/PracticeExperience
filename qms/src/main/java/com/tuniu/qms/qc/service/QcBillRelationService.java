package com.tuniu.qms.qc.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.QcBillRelationDto;
import com.tuniu.qms.qc.model.QcBillRelation;
import com.tuniu.qms.qc.model.QcPoint;

public interface QcBillRelationService  extends BaseService<QcBillRelation, QcBillRelationDto> {

  
   void saveCmpToDev(QcBillRelation bill);
   
   void saveDevToCmp(QcBillRelation bill);

  void updateRelation(Map<String, Object> map);
  
   List<QcBillRelation> listRelation(Map<String, Object> map);
   
   List<QcBillRelation> listByDevId(QcBillRelationDto dto);
   
   void updateByCmpAndDev(QcBillRelationDto dto);
   
   /**
    * 根据研发质检单号和投诉单号找到投诉质检单号
    * @param devId
    * @param cmpId
    * @return
    */
   Integer getQcIdByDevIdAndCmpId(Integer devId, Integer cmpId);
  
   /**
    * 待关联列表返回投诉质检
    * @param dto
    * @param result 
    * @param user 
    */
   void backToCmpQcBill(QcBillRelationDto dto, HandlerResult result, User user);

   void addRelationBill(QcPoint qcPoint, Integer id);
}
