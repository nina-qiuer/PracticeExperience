package com.tuniu.gt.callloss.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;

import com.tuniu.gt.callloss.entity.CallLossPeriodEntity;
import com.tuniu.gt.callloss.service.CallLossPeroidService;
import com.tuniu.gt.frm.service.system.IUserService;

@Service("callloss_action-calllossPeriod")
@Scope("prototype")
public class CallLossPeriodAction extends
		FrmBaseAction<CallLossPeroidService, CallLossPeriodEntity> {

	public CallLossPeriodAction() {
		setManageUrl("CallLossPeriod");
	}

	// 用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("callloss_service_callloss_impl-calllossPeriod")
	public void setService(CallLossPeroidService service) {
		this.service = service;
	};

	public String execute() {
		// 投诉处理列表每页显示个数为30
		this.perPage = 30;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap = paramSearch();
		// 调用父类方法返回页面列表
		super.execute(paramMap);
		List<CallLossPeriodEntity> getDatatList = (List<CallLossPeriodEntity>) request.getAttribute("dataList");
		request.setAttribute("dataList", getDatatList);
		String res = "call_loss_period_list";

		return res;
	}
	/**
	 * 封装查询条件
	 * @return
	 */
	public Map<String, Object> paramSearch(){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();  //search使用的map
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);// 放入search内容
		paramMap.put("1",1);
		return paramMap;
	}
	
	public String addPeriod(){
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		CallLossPeriodEntity entity = new CallLossPeriodEntity();
		entity.setStartTime(startTime);
		entity.setEndTime(endTime);
		entity.setCreateTime(new Date());
		entity.setCreatorId( user.getId());
		entity.setCreatorName(user.getUserName());
		entity.setDelFlag(0);
		service.insert(entity);
		
		return execute();
	}
	
	public String delPeriod(){
		String id = request.getParameter("id");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("cancelorId", user.getId());
		paramMap.put("cancelorName", user.getUserName());
		paramMap.put("cancelTime", new Date());
		service.deletePeriodById(paramMap);
		
		return null;
	}
	
	private String formatTime(String time){
		String result = time;
		result = time.split(":")[0]+time.split(":")[1];
		System.out.println(result);
		return result;
	}
    
}
