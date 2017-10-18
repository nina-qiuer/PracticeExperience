package com.tuniu.qms.qs.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.PunishPrdDto;
import com.tuniu.qms.qs.model.LowSatisfyDetail;
import com.tuniu.qms.qs.model.PunishPrd;

public interface PunishPrdService extends BaseService<PunishPrd, PunishPrdDto>{
	

	/**
	 * 低满意度输入
	 */
	void lowSatisfactionDeal(List<Long> routeIds);
	
	/**
	 * 低满意度产品邮件提醒
	 */
	void lowSatisSendEmail(List<Long> routeIds);
	
	/**
	 * 低满意度详情
	 * @param id
	 * @return
	 */
	LowSatisfyDetail getLowSatisfactionDetail(Integer id);
	
	/**
	 * 上下线操作
	 * @param vo
	 * @return
	 */
	boolean chgPrdStatus(PunishPrdDto vo);
	
	/**
	 * 外部系统触红输入（暂时供qms推送数据使用）
	 * @param ppe
	 */
	void touchRedDeal(PunishPrd prd);
	
	int exportCount(PunishPrdDto dto);
	
	List<PunishPrd> exportList(PunishPrdDto dto);
}
