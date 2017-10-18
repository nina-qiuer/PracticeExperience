package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.InnerRespBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.model.InnerRespBill;

public interface InnerRespBillService  extends BaseService<InnerRespBill, InnerRespBillDto> {

	
   int getInnerRespIsExist(InnerRespBill innerBill);
	
   List<InnerRespBill> listInnerResp(InnerRespBillDto dto);
   
   void addRespAndPunish(InnerRespBill innerBill);
   
   Double getRespRate(Integer qcId);
   
   /**
    * 更新内部责任单
    * @param innerBill
    */
   void updateInnerResp(InnerRespBill innerBill);

   /**
    * 删除内部责任单
    * @param innerId
    */
   void deleteRespBill(Integer innerId);
   
   /**
    * 复制内部责任单
    * @param qcDetailCopyDto 
    */
   Integer copyInnerRespBill(QcDetailCopyDto qcDetailCopyDto);
 }
