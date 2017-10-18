package com.tuniu.gt.complaint.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintService;

@Service("complaint_action-fixCustId4Complaint")
@Scope("prototype")
public class FixCustId4ComplaintAction extends FrmBaseAction<IComplaintService, ComplaintEntity> {
	
	public FixCustId4ComplaintAction() {
		setManageUrl("complaint");
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	public void setService(IComplaintService service) {
		this.service = service;
	};
	
	private static final String PAGE = "fix_cust_id_4_complaint";
	
	/**
	 * 返回主页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String execute() {
		return PAGE;
	}
	
	public String fixData(){
		
		HttpServletRequest request = ServletActionContext.getRequest ();
		String startTime = (String) request.getParameter("startTime");
		String endTime = (String) request.getParameter("endTime");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("buildDateBegin", startTime);
		paramMap.put("buildDateEnd", endTime);
		List<String> orderIdList = service.getComplaintEntityListByBuildDateAndState(paramMap);
		
		for(String orderId : orderIdList){
			//System.out.println(orderId);
			Integer custId = 0;
			if(orderId!=null&&!"null".equals(orderId)&&!"".equals(orderId.trim())){
				custId = service.getCustIdByOrderId(Integer.parseInt(orderId));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("custId", custId);
				map.put("orderId", orderId);
				service.updateCustIdByOrderId(map);
			}
		}

		return PAGE;
	}
}
