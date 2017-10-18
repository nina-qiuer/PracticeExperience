package com.tuniu.gt.workorder.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.workorder.entity.WorkOrderFollowTimeEntity;
import com.tuniu.gt.workorder.service.IWorkOrderFollowTimeService;

import tuniu.frm.core.FrmBaseAction;


/**
* @ClassName: FollowTimeAction
* @Description:跟进信息action
* @author Ocean Zhong
* @date 2012-1-19 上午11:35:38
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("wo_action-follow_time")
@Scope("prototype")
public class WorkOrderFollowTimeAction extends FrmBaseAction<IWorkOrderFollowTimeService,WorkOrderFollowTimeEntity> { 
	
	private static final long serialVersionUID = 1L;

	public WorkOrderFollowTimeAction() {
		setManageUrl("follow_time");
	}
	
	@Autowired
	@Qualifier("wo_service_complaint_follow_time_impl-follow_time")
	public void setService(IWorkOrderFollowTimeService service) {
		this.service = service;
	};
	
	//投诉sercie
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	public String execute() {
		this.setPageTitle("设置下次跟进时间");
		String woId = request.getParameter("woId");
		
		entity.setWoId(Integer.valueOf(woId));
		
		return "follow_time";
	}

	public String save() {
		int userId = user.getId();
		String realName = user.getRealName();
		entity.setUserId(userId);
		entity.setUserName(realName);
		service.insert(entity);
		
		//TODO 添加有效操作记录

		return "follow_time";
	}
}
