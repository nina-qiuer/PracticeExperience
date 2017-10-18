package com.tuniu.gt.callloss.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.callloss.entity.CallLossEntity;
import com.tuniu.gt.callloss.entity.CallLossTetailEntity;
import com.tuniu.gt.callloss.service.CallLossService;
import com.tuniu.gt.sms.SMSConstans;
import com.tuniu.gt.sms.service.ISmsSendRecordService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.warning.common.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tuniu.frm.core.FrmBaseAction;


@Service("callloss_action-callloss")
@Scope("prototype")
public class CallLossAction extends
	FrmBaseAction<CallLossService, CallLossEntity>{
	
	private Logger logger = Logger.getLogger(getClass());

	private String status;
	
	private String callingId;
	
	private String content;
	
	private String calling;
	
	private String callId;
	
	private String ids;

	private Page page;
	
	public Page getPage() {
		return page;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getCalling() {
		return calling;
	}

	public void setCalling(String calling) {
		this.calling = calling;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCallingId() {
		return callingId;
	}

	public void setCallingId(String callingId) {
		this.callingId = callingId;
	}

	public CallLossAction() {
		setManageUrl("CallLoss");
	}
	
	@Autowired
	@Qualifier("callloss_service_callloss_impl-callloss")
	public void setService(CallLossService service) {
		this.service = service;
	};
	
	// 引入短信发送service
    @Autowired
    @Qualifier("smsSendRecord_service_smsSendRecord_impl-sms_send_record")
    private ISmsSendRecordService smsSendRecordService;
	
	/* (non-Javadoc)
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String execute(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(status != null && !"".equals(status)){
			paramMap.put("status", status);
		}
		if(callingId != null && !"".equals(callingId)){
			paramMap.put("callingId", callingId);
		}
		if("4".equals(status)){
			paramMap.put("status", "0");
		}
		this.perPage=30;
		super.execute(paramMap);
		List<CallLossEntity> callLossEntitys = (List<CallLossEntity>)service.fetchList(paramMap);
		request.setAttribute("callLossEntitys", callLossEntitys);
		request.setAttribute("user", user.getId());
		request.setAttribute("today", DateUtil.formatDate(new Date()));
		String res = "callloss_list";
		return res;
	}
	
	public String checkCall(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(callingId != null && !"".equals(callingId)){
			paramMap.put("callingId", callingId);
		}
		this.perPage=30;
		super.execute(paramMap);
		List<CallLossEntity> callLossEntitys = (List<CallLossEntity>)service.fetchList(paramMap);
		String json = JSONArray.fromObject(callLossEntitys.get(0).getStatus()).toString();
		// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置字符集
		response.setContentType("text/plain");// 设置输出为文字流
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(json);
		} catch (IOException e) {
			return "error";
		}finally{
			out.flush();
			out.close();
		}
		// 直接输出响应的内容
		
		
		String res = "callloss_list";
		return res;
	}
	
	public String changeCallStatus(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(callingId != null && !"".equals(callingId)){
			paramMap.put("callingId", callingId);
		}
		if(status != null && !"".equals(status)){
			if("calling".equals(calling)){
				List<CallLossEntity> callLossEntitys = (List<CallLossEntity>)service.fetchList(paramMap);
				paramMap.put("call_count", callLossEntitys.get(0).getCallCount()+1);
				CallLossTetailEntity cte = new CallLossTetailEntity();
				cte.setCallLossCallingId(callingId);
				cte.setSalerId(user.getId());
				cte.setSalerName(user.getRealName());
				cte.setContent(content);
				cte.setCreateTime(new Date());
				if("1".equals(status) || "2".equals(status)){
					cte.setSuccess(1);
				}else{
					cte.setSuccess(0);
				}
				service.addCallTetail(cte);
				if(!status.equals("2")){
					paramMap.put("oldStatus", 3);
				}
			}
		}
		if(callId != null && !"".equals(callId) && !status.equals("2")){
			if(!"calling".equals(calling)){
				paramMap.put("callId", callId);
			}
		}
		paramMap.put("status", status);
		paramMap.put("lastUpdatedBy", user.getId());
		paramMap.put("lastUpdatedTime", new Date());
		paramMap.put("lastUpdatedName", user.getRealName());
		service.changeCallStatus(paramMap);
		String res = "return_list";
		return res;
	}
	
	public String initChangeCallStatus(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(callingId != null && !"".equals(callingId)){
			paramMap.put("callingId", callingId);
		}
		if(status != null && !"".equals(status)){
			paramMap.put("status", status);
		}
		paramMap.put("oldStatus", "0");
		service.changeCallStatus(paramMap);
		String res = "init_change";
		return res;
	}
	
	public String queryDetail(){
		if(page == null){
			page = new Page();
			page.setPageSize(10);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(callingId != null && !"".equals(callingId)){
			paramMap.put("callingId", callingId);
		}
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("dataLimitEnd", page.getPageSize());
		Integer callLossDetailEntitysCount = service.queryDetailCount(paramMap);
		List<Map> callLossDetailEntitys = service.queryDetail(paramMap);
		page.setCount(callLossDetailEntitysCount);
		page.setCurrentPageCount(callLossDetailEntitys.size());
		page.setUrl("callloss-queryDetail?callingId="+callingId);
		request.setAttribute("callLossDetailEntitys", callLossDetailEntitys);
		String res = "callloss_detail";
		return res;
	}
	
	public String sendSms4lost() {
		String smsContent = "您好，由于系统繁忙，暂时无法给您回电，如需咨询请再次致电4007999999。感谢您对途牛旅游网的关注与支持！";
		entity.setSmsContent(smsContent);
		return "send_sms_lost";
	}
	
	/**
	 * 发送短信
	 * 
	 * @return
	 */
	public String sendSMS() {
		String smsContent = request.getParameter("smsContent");
		int smsType = 3;//呼损短信type=3
		JSONObject result=new JSONObject();
		int num = 0;
		String noteContent = "";
		for (String id : ids.split(",")){
			num += smsSendRecordService.sendMessages(id, 0, smsContent, user.getId()+"", SMSConstans.CUST_SERVICE_TASK_ID,smsType);
			if(num > 0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if(id != null && !"".equals(id)){
					paramMap.put("callingId", id);
				}
				paramMap.put("status", 2);
				paramMap.put("oldStatus", "0");
				service.changeCallStatus(paramMap);
			}
		}
		result.put("num", num);
		try {
			 //获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图    
	        HttpServletResponse response = ServletActionContext.getResponse();    
	        //设置字符集    
	        response.setContentType("text/plain");//设置输出为文字流  
	        response.setCharacterEncoding("UTF-8");    
	        PrintWriter out;
			out = response.getWriter();
			 //直接输出响应的内容    
	        out.println(result.toString());    
	        out.flush();    
	        out.close(); 
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return "send_sms_lost";
	}
	
}
