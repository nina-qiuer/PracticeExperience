package com.tuniu.gt.complaint.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.FollowTimeEntity;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IFollowTimeService;
import com.tuniu.gt.toolkit.DateUtil;


/**
* @ClassName: FollowTimeAction
* @Description:跟进信息action
* @author Ocean Zhong
* @date 2012-1-19 上午11:35:38
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_action-follow_time")
@Scope("prototype")
public class FollowTimeAction extends FrmBaseAction<IFollowTimeService,FollowTimeEntity> { 
	
	private static final long serialVersionUID = 1L;

	public FollowTimeAction() {
		setManageUrl("follow_time");
	}
	
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_follow_time_impl-follow_time")
	public void setService(IFollowTimeService service) {
		this.service = service;
	};
	
	//投诉sercie
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	public String execute() {
		this.setPageTitle("设置下次跟进时间");
		String complaintId = request.getParameter("complaintId");
		String orderId = request.getParameter("orderId");
		
		entity.setComplaintId(Integer.valueOf(complaintId));
		entity.setOrderId(Integer.valueOf(orderId));
		
		return "follow_time";
	}

	public String save() {
		int userId = user.getId();
		String realName = user.getRealName();
		entity.setAddTime(new Date());
		entity.setUserId(userId);
		entity.setUserName(realName);
		entity.setDelFlag(1);
		service.insert(entity);
		
		int complaintId = entity.getComplaintId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String dateTime = DateUtil.formatDate(entity.getTime(), DateUtil.DATE_TIME_PAT);
		paramMap.put("followTime", dateTime);
		paramMap.put("minFollowTime", dateTime);
		paramMap.put("id", complaintId);
		complaintService.update(paramMap);
		
		//添加有效操作记录
		String noteContent = "设置下次提醒时间为： " + dateTime + "，备注：" + entity.getNote();
		complaintFollowNoteService.addFollowNote(complaintId, userId, realName, noteContent, 0, Constans.SET_FOLLOW_TIME);
		return "follow_time";
	}
}
