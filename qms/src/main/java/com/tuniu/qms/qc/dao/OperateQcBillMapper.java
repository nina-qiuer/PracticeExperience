package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcInfluenceSystem;

public interface OperateQcBillMapper extends BaseMapper<QcBill, QcBillDto>  {

	 int getQcBillIsExist(Integer qcId);
	 
	 /**
		 * 获取影响系统信息
		 * @param qcId
		 * @return
		 */
	List<QcInfluenceSystem> getInfluenceSystem(Integer qcId);
	/**
	 * 删除影响系统
	 * @param id
	 */
	void deleteSystem(Integer id);
	
	/**
	 * 添加影响系统
	 * @param qcInfo
	 */
	void addSystem(QcInfluenceSystem qcInfo);
}
