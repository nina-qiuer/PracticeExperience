package com.tuniu.gt.complaint.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.CheckEmailEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.ReceiverEmailEntity;
import com.tuniu.gt.complaint.service.ICheckEmailService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.service.IReceiverEmailService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.MailerThread;


/**
* @ClassName: CheckEmailAction
* @Description:合适请求action
* @author Ocean Zhong
* @date 2012-1-19 上午11:32:47
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_action-check_email")
@Scope("prototype")
public class CheckEmailAction extends FrmBaseAction<ICheckEmailService,CheckEmailEntity> { 
	
	public CheckEmailAction() {
		setManageUrl("check_email");
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_check_email_impl-check_email")
	public void setService(ICheckEmailService service) {
		this.service = service;
	};
	
	//引入投诉sercie
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
		
	//引入投诉事宜service
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;

	//引入核实请求service
	@Autowired
	@Qualifier("complaint_service_complaint_check_email_impl-check_email")
	private ICheckEmailService checkEmailService;
	
	//用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("complaint_service_impl-receiver_email")
	private IReceiverEmailService receiverEmailService;
		
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	//qc service
	@Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	/** 
	 * 跳转到发起核实请求页面
	 * 
	 */
	public String execute() {
		this.setPageTitle("发起核实请求");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String complaintId = request.getParameter("complaintId");
		Integer level = Integer.valueOf(request.getParameter("complaintlevel")).intValue();
		paramMap.put("type", level);
		List<ReceiverEmailEntity> info = (List<ReceiverEmailEntity>) receiverEmailService.fetchList(paramMap);
		String emails = "";
		for(ReceiverEmailEntity item : info){
			emails += item.getUserMail() + ";";
		}
		
		request.setAttribute("orderId", request.getParameter("orderId"));
		request.setAttribute("emails", emails);
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("mark", "1");
		
		return "send_email";
	}
	
	/**
	 * 到反馈核实请求页面
	 * @return
	 */
	public String toReply() {

		String complaintId = request.getParameter("complaintId");
		String id = request.getParameter("id");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		
		if (id != null) {
			//发起核实请求数据
			entity = (CheckEmailEntity) service.get(Integer.valueOf(id));
			//投诉单基本信息
			ComplaintEntity complaint = (ComplaintEntity) complaintService.get(complaintId);
			//投诉事宜
			List<ComplaintReasonEntity> complaintReason = (List<ComplaintReasonEntity>) reasonService.fetchList(paramMap);
			//核实请求记录
			List<CheckEmailEntity> checkMailList = checkEmailService.getCheckMailListByComplaintId(complaintId);
			
			// 将获取的各种对象信息传输到页面
			request.setAttribute("complaint", complaint);
			request.setAttribute("check_mail_list", checkMailList);
			request.setAttribute("complaint_reason", complaintReason);

			jspTpl = "send_email_reply";
		} else {
			jspTpl = "error";
		}

		return jspTpl;
	}

	/**
	 * 保存并发送email,默认发送人和默认接收人来自系统
	 * @return
	 */
	public String save(){
		entity.setMark(0);
		entity.setSender(user.getRealName());
		service.insert(entity);// 插入数据库保存记录
		
		//标记质检数据
		int complaintId = entity.getComplaintId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		QcEntity qc = (QcEntity) qcService.get(paramMap);
		qc.setCheckFlag(1);
		if (2 == qc.getStatus()) { // 如果质检已完成，则返回处理中
			qc.setStatus(1);
		}
		
		if (1 == qc.getConsultation() && 0 == qc.getSpecialConsultation()) { // 如果是咨询单，则变为非咨询单并分单
			paramMap.clear();
			paramMap.put("id", complaintId);
			List<ComplaintEntity> compList = complaintService.getComplaintList(paramMap);
			qc.setConsultation(0);
			qcService.assignQcPerson(qc, compList.get(0));
		}
		qcService.update(qc);
		
		// 调用email接口发送email
		//收件人
		String recipientsTemp = entity.getAddress();
		String ccTemp = entity.getCc();
		String[] recipientsEmail = null;
		String[] ccEmail = null;
		if (recipientsTemp != null && !"".equals(recipientsTemp)) {
			//收件人已“;”结尾
			if(recipientsTemp.charAt(recipientsTemp.length()-1) == ';') {
				recipientsEmail = recipientsTemp.substring(0, recipientsTemp.length()-1).split(";");
			//收件人不已“;”结尾
			} else {
				recipientsEmail = recipientsTemp.split(";");
			}
			
		}
		if (ccTemp != null && !"".equals(ccTemp)) {
			//抄送人已“;”结尾
			if(ccTemp.charAt(ccTemp.length()-1) == ';') {
				ccEmail = ccTemp.substring(0, ccTemp.length()-1).split(";");
			//抄送人不已“;”结尾
			} else {
				ccEmail = ccTemp.split(";");
			}
			
		}
		
		String title = entity.getTitle();
		String orderId = request.getParameter("orderId");
		String url = Constant.CONFIG.getProperty("CHENCK_EMAIL_URL");
		String content = "来自订单" + orderId + "的投诉核实请求请点击以下链接，进行问题反馈：<br><a href='" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId + "' target='_blank'>" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId + "</a>";
		content += "<br>若以上链接无法打开，请复制以下地址操作：" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId;
		tspService.sendMail(new MailerThread(recipientsEmail, ccEmail, title, content));
		
		//添加有效操作记录
		String noteContent = "收件人：" + entity.getAddress() + "，抄送：" + entity.getCc() + "，页面地址：<a href='" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId + "' target='_blank'>" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId + "</a>";
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), noteContent,0,Constans.SUBMIT_CHECK_REQUEST);
		complaintService.updateComplaintUpdateTime(entity.getComplaintId());
		return "send_email";
	}
	
	
	/**
	 * 保存反馈的核实请求
	 * @return
	 */
	public String saveReply() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		UserEntity userEntity = null;
		//获取发起核实请求人邮箱
		String email = "";
		paramMap.put("realName", entity.getSender());
		userEntity = (UserEntity) userService.fetchOne(paramMap);
		if(userEntity != null){
			email = userEntity.getEmail();
		}
		
		Date date = new Date();
		entity.setBuildDate(date);
		entity.setMark(1);
		entity.setAddress(email);
		entity.setSender(user.getRealName());
		service.insert(entity);
		
		//发送邮件至核实请求发起人
		String url = Constant.CONFIG.getProperty("CHENCK_EMAIL_URL");
		String orderId = request.getParameter("orderId");
		String title = "订单" + orderId + "核实请求有了新的回应";
		String content = "来自订单" + orderId + "的投诉核实请求请点击以下链接，进行问题反馈：<br><a href='" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId + "' target='_blank'>" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId + "</a>";
		content += "<br>若以上链接无法打开，请复制以下地址操作：" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId;
		tspService.sendMail(new MailerThread(new String[]{email}, null, title, content));
		
		//添加有效操作记录
		String noteContent = "收件人：" + email + "，页面地址：<a href='" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId + "' target='_blank'>" + url + entity.getId() + "&complaintId=" + entity.getComplaintId() + "&orderId=" + orderId + "</a>";
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), noteContent,0,Constans.FEEDBACK_CHECK_REQUEST);
		
		return "send_email_reply";
	}
}
