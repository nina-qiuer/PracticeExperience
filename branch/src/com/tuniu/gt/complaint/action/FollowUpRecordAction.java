package com.tuniu.gt.complaint.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
import com.tuniu.gt.complaint.entity.FollowNoteThEntity;
import com.tuniu.gt.complaint.entity.FollowUpRecordEntity;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTaskReminderService;
import com.tuniu.gt.complaint.service.IFollowNoteThService;
import com.tuniu.gt.complaint.service.IFollowUpRecordService;
import com.tuniu.gt.sms.SMSConstans;
import com.tuniu.gt.sms.entity.SmsSendRecordEntity;
import com.tuniu.gt.sms.service.ISmsSendRecordService;

import net.sf.json.JSONObject;
import tuniu.frm.core.FrmBaseAction;


/**
* @ClassName: FollowUpRecordAction
* @Description:本次跟进记录信息action
* @author Hao Ding
* @date 2014-05-04 上午15:50:38
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_action-follow_up_record")
@Scope("prototype")
public class FollowUpRecordAction extends FrmBaseAction<IFollowUpRecordService,FollowUpRecordEntity> { 
	
	private static final long serialVersionUID = 5641079283955484482L;
	
	private Logger logger = Logger.getLogger(getClass());

	public FollowUpRecordAction() {
		setManageUrl("follow_up_record");
	}
	
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@Autowired
	@Qualifier("complaint_service_impl-follow_note_th")
	private IFollowNoteThService noteThService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_follow_up_record_impl-follow_up_record")
	public void setService(IFollowUpRecordService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	// 引入短信发送service
    @Autowired
    @Qualifier("smsSendRecord_service_smsSendRecord_impl-sms_send_record")
    private ISmsSendRecordService smsSendRecordService;
	
	@Autowired
	private IComplaintTaskReminderService cmpTaskReminderService;
    
	public String execute() {
		this.setPageTitle("设置本次跟进内容");
		String complaintId = request.getParameter("complaintId");
		String orderId = request.getParameter("orderId");
		String tel = request.getParameter("tel");
		String smsContent = "您好，我是售后客服工号："+user.getExtension()+"，分机号："+user.getExtension()+"，很高兴为您服务。";
		Integer deal = request.getParameter("deal")!=null && !"".equals(request.getParameter("deal"))?Integer.parseInt(request.getParameter("deal")):0;
		Integer associater = request.getParameter("associater")!=null && !"".equals(request.getParameter("associater"))?Integer.parseInt(request.getParameter("associater")):0;
		entity.setComplaintId(Integer.valueOf(complaintId));
		entity.setOrderId(Integer.valueOf(orderId));
		entity.setTel(tel==null?"":tel);
		entity.setDeal(deal);
		entity.setSmsContent(smsContent);
		entity.setAssociater(associater);
		return "follow_up_record";
	}
	
	/**
	 * 发送短信
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String sendSMS() {
		String smsTel = request.getParameter("smsTel");
		String smsContent = request.getParameter("smsContent");
		String complaintId = request.getParameter("complaintId");
		int smsType = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("telNum", smsTel);
		paramMap.put("content", smsContent);
		paramMap.put("type", smsType);
		paramMap.put("repeatSend", "repeatSend");
		JSONObject result=new JSONObject();
		List<SmsSendRecordEntity> smss = (List<SmsSendRecordEntity>)smsSendRecordService.fetchList(paramMap);
		int num = 0;
		String noteContent = "";
		if(smss != null && smss.size() > 0){
			result.put("num", -1);
		}else{
			num = smsSendRecordService.sendMessages(smsTel, 0, smsContent, user.getId()+"", SMSConstans.CUST_SERVICE_TASK_ID,smsType);
			result.put("num", num);
		}
		if(num > 0){
			//添加有效操作记录
			noteContent = "（成功）短信号码:"+smsTel+",内容为："+ smsContent;
		}else{
			noteContent = "（失败）短信号码:"+smsTel+",内容为："+ smsContent;
		}
		complaintFollowNoteService.addFollowNote(Integer.parseInt(complaintId), user.getId(), user.getRealName(), noteContent,1,Constans.SAND_SMS);
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
		return "follow_up_record";
	}

	@SuppressWarnings("unchecked")
	public String save() {
		Integer userId = user.getId();
		String realName = user.getRealName();
		Date addTime = new Date();
		entity.setAddTime(addTime);
		entity.setDelFlag(0);
		entity.setAddUser(userId);
		service.insert(entity);
		
		List<ComplaintFollowNoteEntity> complaintFollowNoteEntity = null;
		ComplaintFollowNoteEntity otherComplaintFollowNoteEntity = null;
		ComplaintFollowNoteEntity upgradeSinceLastFstCal = null;
		ComplaintEntity complaint = (ComplaintEntity) complaintService.fetchOne(entity.getComplaintId());
		
		boolean isFirstCall = false;
		
		if(complaint!=null) {
			String dealDepart = complaint.getDealDepart();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("complaintId", entity.getComplaintId());
			paramMap.put("flowName", Constans.FIRST_CALL);
			paramMap.put("dealDepart", dealDepart);
			//当前处理岗首呼记录
			complaintFollowNoteEntity = (List<ComplaintFollowNoteEntity>)complaintFollowNoteService.fetchList(paramMap);
			
			if (!CollectionUtils.isEmpty(complaintFollowNoteEntity)) {
				//判断最近一次的首呼是否为当前处理人所为
				Map<String, Object> otherParamMap = new HashMap<String, Object>();
				otherParamMap.put("complaintId", entity.getComplaintId());
				otherParamMap.put("deal", complaint.getDeal());
				otherParamMap.put("dealDepart", paramMap.get("dealDepart"));
				otherParamMap.put("beforeTime", "beforeTime");
				otherComplaintFollowNoteEntity = (ComplaintFollowNoteEntity) complaintFollowNoteService.fetchOne(otherParamMap);
			}
			
			if(otherComplaintFollowNoteEntity != null){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("complaintId", entity.getComplaintId());
				params.put("flowName", "投诉单升级");
				params.put("addTime",otherComplaintFollowNoteEntity.getAddTime());
				
				upgradeSinceLastFstCal = (ComplaintFollowNoteEntity)complaintFollowNoteService.fetchOne(params);

			}
			paramMap.put("followTime", "");
			paramMap.put("minFollowTime", "0000-00-00 00:00:00");
			paramMap.put("id", entity.getComplaintId());
			
			isFirstCall = (CollectionUtils.isEmpty(complaintFollowNoteEntity) || otherComplaintFollowNoteEntity==null || upgradeSinceLastFstCal != null)
					&& entity.getContactType() == 0;
			
			if (isFirstCall) {
				paramMap.put("inProcessState", 2);
			}
			
			paramMap.put("thFinishFlag", 0);
			complaintService.update(paramMap);
		}
		
		Integer complaintId = entity.getComplaintId();
		String flowName = isFirstCall ? Constans.FIRST_CALL
				: Constans.SET_FOLLOW_UP_RECORD;
		// 添加有效操作记录
		String noteContent = entity.getContactType()==0 ? "电话跟进" : entity.getContactType()==1 ? "邮件跟进" : "第三方沟通工具跟进";
		complaintFollowNoteService.addFollowNote(complaintId, userId, realName, noteContent, 1, flowName);
		if(entity.getContactType()==0){
			//删除待回呼记录
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cmpId", complaintId);
			map.put("userId", user.getId());
			map.put("userName", user.getRealName());
			cmpTaskReminderService.updateTaskReminder(map);
			
		}
		// 跟进记录，插入第三方中间层
		if (StringUtils.isNotBlank(complaint.getCompCity())) {
			FollowNoteThEntity noteThEnt = new FollowNoteThEntity();
			noteThEnt.setComplaintId(complaintId);
			noteThEnt.setPersonId(userId);
			noteThEnt.setPersonName(realName);
			noteThEnt.setFlowName(Constans.SET_FOLLOW_UP_RECORD);
			noteThEnt.setContent(noteContent);
			noteThService.insert(noteThEnt);
		}
		return "follow_time";
	}
	
}
