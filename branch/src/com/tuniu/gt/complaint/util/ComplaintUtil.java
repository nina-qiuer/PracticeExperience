package com.tuniu.gt.complaint.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.MemcachesUtil;

public class ComplaintUtil {
    
    private static Logger logger = LoggerFactory.getLogger(ComplaintUtil.class);
	
	/** 用于计数器 */
	public static final String CRVALUE = "1";
	
	public static final String MEM_PRE_COMPLAINT = "complaint_";
	
	public static final String MEM_PRE_QC = "qc_";
	
	/**
	 * 记录每个客服当天的单数+1
	 */
	public static synchronized void recordUserNums(int dealId, String memPre) {
		String key = memPre + dealId;
		if (StringUtils.isNotBlank(MemcachesUtil.get(key))) {
			MemcachesUtil.increment(key, new Long(CRVALUE)); // 如果已经存在，账号计数器加1
		} else {
			MemcachesUtil.setInToday(key, CRVALUE);
		}
	}
	
	/**
	 * 记录每个客服当天的单数-1
	 */
	public static synchronized void minusUserNums(int dealId, String memPre) {
		String key = memPre + dealId;
		if (StringUtils.isNotBlank(MemcachesUtil.get(key))) {
			MemcachesUtil.decrement(key, new Long(CRVALUE));
		}
	}
	
	/**
	 * 记录每个客服当天分配的订单号+1
	 */
	public static synchronized void recordUserOrders(int dealId, String orderId, String memPre) {
		String key = memPre + "order_" + dealId;
		if (StringUtils.isNotBlank(MemcachesUtil.get(key))) {
			String value = MemcachesUtil.get(key) + "," + orderId;
			MemcachesUtil.setInToday(key, value);
		} else {
			MemcachesUtil.setInToday(key, orderId);
		}
	}
	
	/**
	 * 记录每个客服当天分配的订单号-1
	 */
	public static synchronized void minusUserOrders(int dealId, String orderId, String memPre) {
		String key = memPre + "order_" + dealId;
		if (StringUtils.isNotBlank(MemcachesUtil.get(key))) {
			String[] oldArr = MemcachesUtil.get(key).split(",");
			List<String> newArr = new ArrayList<String>();
			for (String str : oldArr) {
				if (!orderId.equals(str)) {
					newArr.add(str);
				}
			}
			StringBuffer sb = new StringBuffer();
			for (int i=0; i<newArr.size(); i++) {
				if (i < newArr.size() - 1) {
					sb.append(newArr.get(i)).append(",");
				} else {
					sb.append(newArr.get(i));
				}
			}
			MemcachesUtil.setInToday(key, sb.toString());
		}
	}
	
	/**
	 * 记录客服的分单时间
	 */
	public static synchronized void updateAssignTime(int dealId, String memPre) {
		String key = memPre + "time_" + dealId;
		String timeStr = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		MemcachesUtil.setInToday(key, timeStr);
	}
	
	/**
	 * 判断是否是投诉单还是咨询单
	 */
	public static int judgeConsultation(List<ComplaintReasonEntity> reasonList, String routeType) {
		int flag = 1; // 默认是咨询单
		if ("机票".equals(routeType)) {
			return flag;
		}
		
		List<String> consulationList1 = DBConfigManager.getConfigAsList("business.qc.consultation.firstlevel");
		List<String> consulationList2 = DBConfigManager.getConfigAsList("business.qc.consultation.secondlevel");
		for (ComplaintReasonEntity reason : reasonList) {
			String reasonType = reason.getType() + "-" + reason.getSecondType();
			if(!consulationList1.contains(reason.getType())&&!consulationList2.contains(reasonType)){
			    flag = 0;
                break;
			}
		}
		return flag;
	}
}
