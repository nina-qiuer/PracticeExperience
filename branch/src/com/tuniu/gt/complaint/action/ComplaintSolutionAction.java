package com.tuniu.gt.complaint.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEmailEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintSolutionEmailService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.complaint.service.impl.OrderService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.toolkit.MD5;
import com.tuniu.gt.toolkit.lang.CollectionUtil;

@Service("complaint_action-complaint_solution")
@Scope("prototype")
public class ComplaintSolutionAction extends FrmBaseAction<IComplaintSolutionService,ComplaintSolutionEntity> { 
	
	private static final long serialVersionUID = 1L;
	
	private int succFlag;
	
	private Map<String,Object> info;
	/**
	 * 对客解决邮件
	 */
	private ComplaintSolutionEmailEntity cseEntity;
	/**
	 * 是否邮件提醒
	 */
	private int sendEmail = 0;
	
	private String groupOrders = "";

	
	public String getGroupOrders() {
        return groupOrders;
    }

    public void setGroupOrders(String groupOrders) {
        this.groupOrders = groupOrders;
    }

    public int getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(int sendEmail) {
        this.sendEmail = sendEmail;
    }

    public ComplaintSolutionEmailEntity getCseEntity() {
        return cseEntity;
    }

    public void setCseEntity(ComplaintSolutionEmailEntity cseEntity) {
        this.cseEntity = cseEntity;
    }

    public int getSuccFlag() {
		return succFlag;
	}

	public void setSuccFlag(int succFlag) {
		this.succFlag = succFlag;
	}

	public ComplaintSolutionAction() {
		setManageUrl("complaint_solution");
		info = new HashMap<String, Object>();
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	public void setService(IComplaintSolutionService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_solution_email")
    private IComplaintSolutionEmailService complaintSolutionEmailService;
	@Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;
	
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
    @Resource
	private IComplaintTSPService tspService;
    
    @Autowired
    private OrderService orderService;
	
	public String execute() {
		this.setPageTitle("对客解决方案");
		String complaintId = request.getParameter("complaintId");
		ComplaintEntity complaint = (ComplaintEntity) complaintService.get(complaintId);
		if(null != complaint)
        {
		    complaint.setRoute(CommonUtil.toHtml(complaint.getRoute()));
        }
		String orderId = request.getParameter("orderId");
		request.setAttribute("orderId", orderId);
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("complaint", complaint);
		
		List<String> canPayoutDealDepartLists = DBConfigManager.getConfigAsList("sys.payout.dealDepart");
		if(canPayoutDealDepartLists.contains(complaint.getDealDepart())){
			request.setAttribute("canAddSoulation", true);
		}
		
		entity = (ComplaintSolutionEntity) service.getComplaintSolution(Integer.valueOf(complaintId));
		String startDate = DateUtil.formatDate(complaint.getStartTime(), "yyyy-MM-dd");
        String routeId = complaint.getRouteId().toString();
		groupOrders = orderService.getSameGroupOrderIds(startDate, routeId); // 获取同团期订单
		if (entity != null) {
			entity=service.setCardInfo(entity);
			cseEntity = complaintSolutionEmailService.getComplaintSolutionEmailBysolutionId(entity.getId());
			if(cseEntity != null){
			    cseEntity.setRoute(CommonUtil.toHtml(cseEntity.getRoute()));
			    request.setAttribute("sendEmail", 1);
			    request.setAttribute("cseEntity", cseEntity);
			}
			int submitFlag = entity.getSubmitFlag();
			if (1 == submitFlag) { // 已提交，展示信息页面
				jspTpl = "solution_info";
			} else { // 未提交，展示修改页面
				jspTpl = "solution_update";
			}
		} else { // 不存在，展示新增页面
			int contactId = Integer.valueOf(request.getParameter("contactId"));
			if (contactId > 0) { // 根据联系人id获取联系人信息
				Map<String, Object> contact = ComplaintRestClient.getTouristById(contactId);
				request.setAttribute("contact", contact);
			}
			entity = new ComplaintSolutionEntity();
			entity.setSatisfactionFlag(1); //默认满意
			
			jspTpl = "solution_add";
		}
		
		String giftInfo = ComplaintRestClient.giftListFromOa();
		request.setAttribute("giftInfo", giftInfo);
		JSONArray provinceList = ComplaintRestClient.queryDistrictByParentId(40002);
		request.setAttribute("provinceList", provinceList);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", complaintId);
		params.put("submitFlag", 1);
		ShareSolutionEntity shareSolution = (ShareSolutionEntity) shareSolutionService.fetchOne(params);
		double shareTotal = 0;
		if (null != shareSolution && 1 == shareSolution.getSubmitFlag()) {
			shareTotal = shareSolution.getTotal();
		}
		request.setAttribute("shareTotal", shareTotal);
		
		return jspTpl;
	}
	

	/**
	 * 添加解决方案
	 */
	public String addSolution() {
		service.saveComplaintSolution(entity);
		ComplaintSolutionEmailEntity csemailEntity = complaintSolutionEmailService.getComplaintSolutionEmailBysolutionId(entity.getId());
        if(csemailEntity == null){
            sendEmail = Integer.parseInt(request.getParameter("sendEmail"));
            if(sendEmail == 1){
                Date date = new Date();
                cseEntity.setComplaintId(entity.getComplaintId());
                cseEntity.setEmailSender(user.getRealName());
                cseEntity.setUpdateTime(date);
                cseEntity.setSolutionId(entity.getId());
                if(cseEntity.getStartDate() == null){
                    cseEntity.setStartDate(DateUtil.parseDate("1001-01-01"));
                }
                cseEntity.setAddTime(date);
                complaintSolutionEmailService.saveComplaintSolutionEmail(cseEntity);
                ComplaintEntity complaint = (ComplaintEntity)complaintService.get(entity.getComplaintId());
                complaintSolutionEmailService.sendComplaintSolutionEmail(cseEntity, complaint, user);
            }
        }else{
            cseEntity = csemailEntity;
        }
		request.setAttribute("sendEmail", sendEmail);
		entity=service.setCardInfo(entity);
		if (1 == entity.getSaveOrSubmit()) {
			request.setAttribute("pageInfo", "submitConfirm");
			jspTpl = "solution_info";
		} else {
			request.setAttribute("giftInfo", "[]");
			succFlag = 1;
			jspTpl = "solution_add";
		}
		
		// 添加有效操作记录
		String noteContent =  Constans.SAVE_SOLUTION + ",备注：" + entity.getDescript();
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), noteContent,0,"投诉处理");
		
		return jspTpl;
	}

	public String updateSolution() {
		service.updateComplaintSolution(entity);
		ComplaintSolutionEmailEntity csemailEntity = complaintSolutionEmailService.getComplaintSolutionEmailBysolutionId(entity.getId());
        if(csemailEntity == null){
            sendEmail = Integer.parseInt(request.getParameter("sendEmail"));
            if(sendEmail == 1){
                Date date = new Date();
                cseEntity.setComplaintId(entity.getComplaintId());
                cseEntity.setEmailSender(user.getRealName());
                cseEntity.setUpdateTime(date);
                cseEntity.setSolutionId(entity.getId());
                if(cseEntity.getStartDate() == null){
                    cseEntity.setStartDate(DateUtil.parseDate("1001-01-01"));
                }
                cseEntity.setAddTime(date);
                complaintSolutionEmailService.saveComplaintSolutionEmail(cseEntity);
                ComplaintEntity complaint = (ComplaintEntity)complaintService.get(entity.getComplaintId());
                complaintSolutionEmailService.sendComplaintSolutionEmail(cseEntity, complaint, user);
            }
        }else{
            cseEntity = csemailEntity;
        }
        entity = service.setCardInfo(entity);
        request.setAttribute("sendEmail", sendEmail);
		if (1 == entity.getSaveOrSubmit()) {
			request.setAttribute("pageInfo", "submitConfirm");
			jspTpl = "solution_info";
		} else {
		    ComplaintEntity complaint = (ComplaintEntity) complaintService.get(entity.getComplaintId());
		    request.setAttribute("complaint", complaint);
		    request.setAttribute("complaintId", complaint.getId());
			request.setAttribute("giftInfo", "[]");
			succFlag = 1;
			jspTpl = "solution_update";
		}
		
		// 添加有效操作记录
		String noteContent =  Constans.SAVE_SOLUTION + ",备注：" + entity.getDescript();
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), noteContent,0,"投诉处理");
		
		return jspTpl;
	}
	
	public String submitSolution() {
		entity.setDealId(user.getId());
		entity.setDealName(user.getRealName());
//		service.submitComplaintSolution(entity, user.getId(), user.getRealName());
		info = service.submitComplaintSolution(entity, user.getId(), user.getRealName());
		succFlag = 1;
		entity = (ComplaintSolutionEntity) service.getComplaintSolution(entity.getComplaintId());
		entity=service.setCardInfo(entity);
//		ComplaintSolutionEmailEntity csemailEntity = complaintSolutionEmailService.getComplaintSolutionEmailBysolutionId(entity.getId());
//		if(csemailEntity == null){
//		    request.setAttribute("sendEmail", 0);
//		}else {
//		    request.setAttribute("sendEmail", 1);
//		    cseEntity = csemailEntity;
//		    //发送邮件
//		    ComplaintEntity complaint = (ComplaintEntity) complaintService.get(entity.getComplaintId());
//		    complaintSolutionEmailService.sendComplaintSolutionEmail(csemailEntity, complaint,user);
//		}
//		return "solution_info";
		return "info";
	}
	
	public String getCityListByProvinceName(){
	    String province = request.getParameter("province");
	    Integer provinceId = ComplaintRestClient.queryDistrictIdByName(province);
	    JSONArray cityArray = ComplaintRestClient.queryDistrictByParentId(provinceId);
	    if(CollectionUtil.isNotEmpty(cityArray)){
	        info.put("cityList", cityArray);
	    }else{
	        cityArray.add(province);
	        info.put("cityList", cityArray);
	    }
	    return "info";
	}
	
	/**
	 * 用MD5加密
	 * @return
	 */
	public String encryptByMD5(){
		String parameter = request.getParameter("parameter");
		String resultString="";
		try {
			resultString=MD5.sign(parameter, Constant.CONFIG.getProperty("PCI_MD5_KEY"), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.writeResponse(resultString);
		return "info";
	}
	
    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
	
}
