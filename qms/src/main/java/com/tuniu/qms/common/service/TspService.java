package com.tuniu.qms.common.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.dto.PrdLineDepInfoDto;
import com.tuniu.qms.common.model.MailTask;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.util.Rtx;
import com.tuniu.qms.qs.model.CmpImprove;
import com.tuniu.qms.qs.model.MessageParamEntity;

public interface TspService {
	
	Product getProductInfo(Integer prdId);

	Map<String, Object> getAgencyInfo(String agencyName);
	
	/**
	 * 获取导游信息
	 */
	Map<String, Object> getGuideInfo(Integer orderId) ;
	
	OrderBill getOrderInfo(Integer ordId);
	
	/**
	 * 查询产品线归属组织架构信息
	 */
	PrdLineDepInfoDto getPrdLineDepInfo(Integer prdLineId);
	
	List<PrdLineDepInfoDto> getPrdLineDepInfos(List<Integer> prdLineIds);
	
	/**
	 * @param paramMap
	 * @return
	 */
	boolean changePrdStatus(Map<String, Object> paramMap);
	
	/**
     * 查询跟团线路关联的主从线路id
     * @param routeId
     * @return
     */
    Integer queryMainRouteId(int routeId);
    
    /**
     * 邮件服务走tsp接口
	 * @param paramMap
	 * @return
	 */
	boolean sendMail(MailTask mail );
	
	 /**
     * 批量rtx提醒
	 * @param paramMap
	 * @return
	 */
	boolean sendRtx(Rtx rtx );
	
	/***
	 * 根据ID获取供应商信息
	 * @param agencyId
	 * @return
	 */
    Map<String, Object> queryAgencyDetail(Integer agencyId );
    
    /**
     * 发送短信
     * @param mpe
     * @return
     */
    boolean sendMessage(MessageParamEntity mpe) ;
    
    /**
     * 发送投诉改进报告  向OA推送生成流程
     * @param obj
     * @return
     */
    String sendImproveBill(CmpImprove obj);
    
    /**
     * 根据订单号获取订单详情页URL
     * @param orderId
     * @return
     */
	String getOrderDetailPageUrl(Integer orderId);
}
