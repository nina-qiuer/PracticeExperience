package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AgencySetDto;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.RefundApplyEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IRefundApplyService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

import net.sf.json.JSONObject;


@Service("complaint_action-refund_apply")
@Scope("prototype")
public class RefundApplyAction extends FrmBaseAction<IRefundApplyService, RefundApplyEntity> { 
	
	private static final long serialVersionUID = 1L;

	public RefundApplyAction() {
		setManageUrl("refund_apply");
	}
	
	@Autowired
	@Qualifier("complaint_service_impl-refund_apply")
	public void setService(IRefundApplyService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	public String execute() {
		this.setPageTitle("退款申请");
		String complaintId = request.getParameter("complaintId");
		entity.setComplaintId(Integer.valueOf(complaintId));
		
		ComplaintEntity compEnt = (ComplaintEntity) complaintService.get(complaintId);
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("orderId", compEnt.getOrderId());
		JSONObject paramJson = JSONObject.fromObject(paramMap);
		String handoverSaler = tspService.getHandoverSaler(paramJson);
		String recipients = compEnt.getCustomer() + "," + compEnt.getProducter()+","+compEnt.getOperateName()+","+compEnt.getOperateManagerName();
		if(handoverSaler!=null){
			recipients+=","+handoverSaler;
		}
		entity.setRecipients(recipients);
		
		String ccs = compEnt.getCustomerLeader() + "," + compEnt.getProductLeader() + "," + user.getRealName();
		entity.setCcs(ccs);
		
		return "refund_apply_form";
	}

	public String submitApply() { 
		entity.setApplyId(user.getId());
		entity.setApplyName(user.getRealName());
		service.insert(entity);
		
		ComplaintEntity compEnt = (ComplaintEntity) complaintService.get(entity.getComplaintId());
		
		Map<String, Object> contact = ComplaintRestClient.getTouristById(compEnt.getContactId());
		
		List<AgencySetDto> agencyList = new ArrayList<AgencySetDto>();
		
		String agencyName = compEnt.getAgencyName();
		agencyList.addAll(complaintService.getAgencySet(compEnt.getOrderId()));
		if(agencyList.size()>0){
			agencyName = "";
			for(AgencySetDto agency : agencyList){
				if(StringUtil.isNotEmpty(agencyName)){
					agencyName +="，";
				}
				agencyName += agency.getAgencyName();
			}
		}
		
		// 发送邮件
		String title = "订单号[" + compEnt.getOrderId() + "]退款申请";
		String rtxContent = "订单号：" + compEnt.getOrderId() + "\n"
							+ "退款金额：" + entity.getAmount() + "元\n"
							+ "退款原因：" + entity.getReason() + "\n"
							+ "退款申请人：" + user.getRealName() + "\n";
		String content = "<strong>订单号：</strong>" + compEnt.getOrderId() + "<br>"
							+ "<strong>线路ID：</strong>" + compEnt.getRouteId() + "<br>"
							+ "<strong>线路名称：</strong>" + compEnt.getRoute() + "<br>"
							+ "<strong>客人姓名：</strong>" + compEnt.getGuestName() + "<br>"
							+ "<strong>联系人姓名：</strong>" + contact.get("name") + "<br>"
							+ "<strong>联系人电话：</strong>" + contact.get("tel") + "<br>"
							+ "<strong>售前客服：</strong>" + compEnt.getCustomer() + "<br>"
							+ "<strong>客服经理：</strong>" + compEnt.getCustomerLeader() + "<br>"
							+ "<strong>产品专员：</strong>" + compEnt.getProducter() + "<br>"
							+ "<strong>产品经理：</strong>" + compEnt.getProductLeader() + "<br>"
							+ "<strong>运营专员：</strong>" + compEnt.getOperateName() + "<br>"
							+ "<strong>运营经理：</strong>" + compEnt.getOperateManagerName() + "<br>"
							+ "<strong>供应商名称：</strong>" + agencyName + "<br>"
							+ "<strong>出游人数：</strong>" + compEnt.getGuestNum() + "<br>"
							+ "<strong>退款金额：</strong><span style='color: blue'>" + entity.getAmount() + "</span>元<br>"
							+ "<strong>退款原因：</strong>" + entity.getReason() + "<br>"
							+ "<strong>退款申请人：</strong>" + user.getRealName() + "<br>"
							+ "<span style='background-color: yellow'>@售前：请尽快发起核损，确认核损后，操作退款，如不能线上核损，请尽快通知产品/采购/运营 ；@产品/运营：请尽快反馈核损，如不能线上核损，请尽快修改采购单</span><br>";
		String[] recipients = entity.getRecipients().split(",");
		
		List<String> recList = new ArrayList<String>();
		List<String> ticketSalers = tspService.queryTicketOrder(compEnt.getOrderId());
        for (String name : ticketSalers) {
            if(!"".equals(name.trim())){
                
                UserEntity user = userService.getUserByRealName(name);
                if(null!=user){
                    
                    recList.add(user.getEmail());
                    new RTXSenderThread(user.getId(), user.getUserName(), title, rtxContent).start();
                }
                
            }
            
            
        }
		for (String name : recipients) {
			if(!"".equals(name.trim())){
				
				UserEntity user = userService.getUserByRealName(name);
				if(null!=user){
					
					recList.add(user.getEmail());
					new RTXSenderThread(user.getId(), user.getUserName(), title, rtxContent).start();
				}
				
			}
			
			
		}
		String[] recipientArr = recList.toArray(new String[recList.size()]); 
		
		String[] ccs = entity.getCcs().split(",");
		List<String> ccList = new ArrayList<String>();
		for (String name : ccs) {
			
			UserEntity user = userService.getUserByRealName(name);
			if(null!=user){
				
				ccList.add(user.getEmail());
			}
			
		}
		String[] ccArr = ccList.toArray(new String[ccList.size()]);
		
		tspService.sendMail(new MailerThread(recipientArr, ccArr, title, content));
		
		String noteContent = "申请金额为：" + entity.getAmount() + "元，已邮件（并伴有RTX提醒）主送给：" + entity.getRecipients() 
								+ "，抄送给：" + entity.getCcs();
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), 
				noteContent, 0, Constans.SUBMIT_REFUND_APPLY);
		
		request.setAttribute("succFlag", 1);
		
		return "refund_apply_form";
	}
	
}
