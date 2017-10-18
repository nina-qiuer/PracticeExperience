package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.ComplaintEntity;

import tuniu.frm.core.IServiceBase;

public interface IComplaintEmailConfigService extends IServiceBase{
	
	//获取投诉发起邮件收件人
	List<String> getLaunchReEmails(ComplaintEntity complaint);
	
	//获取投诉发起邮件抄送人
	List<String> getLaunchCcEmails(ComplaintEntity complaint);
	
	/**
	 * 根据条件查询数据总数
	 * @param paramMap
	 * @return
	 */
	Integer getEmailConfigCount(Map<String, Object> paramMap);
}
