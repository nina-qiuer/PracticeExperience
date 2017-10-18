package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcInfluenceSystem;
import com.tuniu.qms.qc.model.ResDevExportBill;

public interface ResDevQcBillMapper extends BaseMapper<QcBill, QcBillDto>  {

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
	
	int  queryCount(QcBillDto dto );
	
	List<QcBill>  queryList(QcBillDto dto );
	
	/**
	 * 研发质检导出
	 * @param dto
	 * @return
	 */
	List<ResDevExportBill> exportList(QcBillDto dto);
	
	Integer exportCount(QcBillDto dto);
	
	int getInfluenceSystemCount(Integer qcId);
	
	List<QcBill> getQcPeriodList();
	
	/**
	 * 根据研发质检单号找到关联对应的投诉单号
	 * @param qcId
	 * @return
	 */
	List<Integer> getComplainIdByDevId(Integer qcId);
}
