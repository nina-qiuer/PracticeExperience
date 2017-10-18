package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.InnerPunishBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;
import com.tuniu.qms.qc.model.InnerPunishBill;

public interface InnerPunishBillService  extends BaseService<InnerPunishBill, InnerPunishBillDto>{
	
	 int getInnerPunishIsExist(InnerPunishBill innerBill);
	
	 void deletePunish(Integer innerId);
	
	 List<InnerPunishBill> listInnerPunish(Integer qcBillId);
	
     void deleteUnUsePunish(InnerPunishBillDto dto);
	
	 void addPunish(InnerPunishBill innerBill);
	
	 void updatePunishBill(InnerPunishBasis basis);
	 
	 Integer exportCount(InnerPunishBillDto dto);
	 
	 List<InnerPunishBill> exportList(InnerPunishBillDto dto);
	 
	 void detetePunishById(InnerPunishBill innerBill);
	 
	 /**
	  * 根据质检id获得内部处罚单列表
	  * @param qcId
	  * @return
	  */
	List<InnerPunishBill> listInnerPunishByQcId(Integer qcId);
	
	/**
	 * 复制内部处罚单
	 * @param qcDetailCopyDto
	 */
	Integer copyInnerPunishBill(QcDetailCopyDto qcDetailCopyDto);

	void updateInnerPunish(InnerPunishBill innerBill, User user);

	Integer getTotalScores(InnerPunishBillDto dto);
}
