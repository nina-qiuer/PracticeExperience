package com.tuniu.gt.punishprd.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.bi.gmvrank.entity.GMVRankEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.QcQuestionEntity;
import com.tuniu.gt.complaint.vo.SystemVo;
import com.tuniu.gt.punishprd.entity.PunishPrdEntity;
import com.tuniu.gt.punishprd.vo.LowQualityDetailVo;
import com.tuniu.gt.punishprd.vo.PunishPrdVo;
import com.tuniu.gt.punishprd.vo.LowSatisfyDetailVo;

public interface IPunishPrdService extends IServiceBase{
	
	/**
	 * 触红输入
	 * @param compEnt
	 * @param qcEnt
	 */
	void touchRedDeal(ComplaintEntity compEnt,QcEntity qcEnt,List<QcQuestionEntity> qcQuestions);
	
	/**
	 * 外部系统触红输入（暂时供qms推送数据使用）
	 * @param ppe
	 */
	void outerTouchRedDeal(PunishPrdEntity ppe);
	
	/**
	 * 低满意度输入
	 */
	void lowSatisfactionDeal(List<Long> routeIds);
	
	/**
	 * 低质量输入
	 */
	void lowQualityDeal(List<GMVRankEntity> gmvRankList);
	
	/**
	 * 上下线操作
	 * @param vo
	 * @return
	 */
	boolean chgPrdStatus(PunishPrdVo vo);
	
	/**
	 * 根据id获取低满意度评价详情
	 * @param id
	 * @return
	 */
	LowSatisfyDetailVo getLowSatisfactionDetail(Integer id);
	
	/**
	 * 根据id获取低质量详情
	 * @param id
	 * @return
	 */
	LowQualityDetailVo getLowQualityDetail(Integer id);
	
	/**
	 * 获取list的条数
	 * @param paramMap
	 * @return
	 */
	Integer getListCount(Map<String,Object> paramMap);
	
}
