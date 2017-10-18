package com.tuniu.qms.qs.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qs.dto.CmpImproveDto;
import com.tuniu.qms.qs.model.CmpImprove;

public interface CmpImproveMapper extends BaseMapper<CmpImprove, CmpImproveDto>{
	
	/**
	 * 分页查询
	 * @param dto
	 * @return
	 */
	List<CmpImprove> loadPage(CmpImproveDto dto);
	
	/**
	 * 超过流程处理到期时间列表   添加时间+5个工作日
	 * @return
	 */
	List<CmpImprove> getOutHandleEndTime();
	
	/**
	 * 根据投诉单号查询改进报告
	 * @param cmpId
	 * @return
	 */
	List<CmpImprove> listByCmpId(Integer cmpId);
	
	/**
	 * 获得有订单投诉、待分配的改进报告
	 * @return
	 */
	List<CmpImprove> listWaitDistrib();
	
	/**
	 * 获得无订单投诉、待分配的改进报告
	 * @return
	 */
	List<CmpImprove> listWaitDistribNonOrd();

}
