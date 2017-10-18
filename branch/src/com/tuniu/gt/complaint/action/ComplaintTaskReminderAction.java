package com.tuniu.gt.complaint.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.TaskReminderEntity;
import com.tuniu.gt.complaint.service.IComplaintTaskReminderService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.warning.common.Page;

/**
 * 任务提醒列表
 * @author chenhaitao
 *
 */

@Service("complaint_action-taskReminder")
@Scope("prototype")
public class ComplaintTaskReminderAction extends FrmBaseAction<IComplaintTaskReminderService, TaskReminderEntity> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	public ComplaintTaskReminderAction() {
		setManageUrl("taskReminder");
	}
	
	private Page page;
	
	private String dealDepart;//处理岗
	
	private String cmpId;//投诉单号

	private String orderId;//订单号
	
	private String orderState;//订单状态
	
	private String dealName;//处理人
	
	private String level;//投诉等级
	
	private String customerLeader;//客服经理
	
	private String startTimeBegin;//出游开始时间
	
	private String startTimeEnd;//出游结束时间
	
	private List<String> dealDepartments;
	
	
	@Autowired
	private IComplaintTaskReminderService cmpTaskReminderService;
	
	
	public String execute() {
		
		if(page == null){
			page = new Page();
			page.setPageSize(30);
		}
		String state = request.getParameter("c_type");// 请求的标签页列表
		int stateType = 1; // 请求的标签页，默认显示待指定
		if (state != null) {
			if (state.equals("menu_1")) {
				
			} else if (state.equals("menu_2")) {
				stateType = 2;
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("stateType", stateType);
		paramMap.put("dealDepart",dealDepart);
		paramMap.put("cmpId", cmpId);
		paramMap.put("orderId", orderId);
		paramMap.put("orderState", orderState);
		paramMap.put("dealName", dealName);
		paramMap.put("level", level);
		paramMap.put("customerLeader", customerLeader);
		paramMap.put("startTimeBegin", startTimeBegin);
		paramMap.put("startTimeEnd", startTimeEnd);
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("dataLimitEnd", page.getPageSize());
		
		List<String> managerList = DBConfigManager.getConfigAsList("authority.audit.base");
		if(!managerList.contains(""+user.getId())){
			paramMap.put("userId", user.getId());
		}
		
		List<TaskReminderEntity> taskList  =  cmpTaskReminderService.queryTaskReminder(paramMap);
		int count = cmpTaskReminderService.queryTaskReminderCount(paramMap);
		page.setCount(count);
		page.setCurrentPageCount(taskList==null?0:taskList.size());
		request.setAttribute("taskList", taskList==null?"":taskList);
		request.setAttribute("type",stateType);
	    return "complaint_task_reminder";
	}


	public Page getPage() {
		return page;
	}


	public void setPage(Page page) {
		this.page = page;
	}


	public String getDealDepart() {
		return dealDepart;
	}


	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}


	public String getCmpId() {
		return cmpId;
	}


	public void setCmpId(String cmpId) {
		this.cmpId = cmpId;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getOrderState() {
		return orderState;
	}


	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}


	public String getDealName() {
		return dealName;
	}


	public void setDealName(String dealName) {
		this.dealName = dealName;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getCustomerLeader() {
		return customerLeader;
	}


	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
	}


	public String getStartTimeBegin() {
		return startTimeBegin;
	}


	public void setStartTimeBegin(String startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}


	public String getStartTimeEnd() {
		return startTimeEnd;
	}


	public void setStartTimeEnd(String startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}


	public IComplaintTaskReminderService getCmpTaskReminderService() {
		return cmpTaskReminderService;
	}


	public void setCmpTaskReminderService(
			IComplaintTaskReminderService cmpTaskReminderService) {
		this.cmpTaskReminderService = cmpTaskReminderService;
	}


    public List<String> getDealDepartments() {
        return DBConfigManager.getConfigAsList("sys.dealDepart");
    }


    public void setDealDepartments(List<String> dealDepartments) {
        this.dealDepartments = dealDepartments;
    }

	
}
