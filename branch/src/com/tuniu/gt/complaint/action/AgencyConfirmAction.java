package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AgencyAppealEntity;
import com.tuniu.gt.complaint.entity.AgencyConfirmInfoEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.service.IAgencyAppealService;
import com.tuniu.gt.complaint.service.IAgencyConfirmInfoService;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.complaint.service.ISupportShareService;
import com.tuniu.gt.complaint.vo.AgencyConfirmInfoPage;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.service.system.IFestivalService;

import tuniu.frm.core.FrmBaseAction;


/**
* @ClassName: AgencyConfirmAction
* @Description: 供应商赔付确认信息Action
* @author WangMingFang
* @date 2014-04-10
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_action-agency_confirm")
@Scope("prototype")
public class AgencyConfirmAction extends FrmBaseAction<IAgencyConfirmInfoService, AgencyConfirmInfoEntity> {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-support_share")
	private ISupportShareService supportShareService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-agency_appeal")
	private IAgencyAppealService appealService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-festival")
	private IFestivalService festivalService;

	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-appoint_manager")
	private IAppointManagerService appointManagerService;
	
	private static Logger logger = Logger.getLogger(AgencyConfirmAction.class);
	
	private AgencyConfirmInfoPage page = new AgencyConfirmInfoPage();
	
	private AgencyAppealEntity appealInfo = new AgencyAppealEntity();
	
	private int succFlag;
	
	public int getSuccFlag() {
		return succFlag;
	}

	public void setSuccFlag(int succFlag) {
		this.succFlag = succFlag;
	}

	public AgencyConfirmInfoPage getPage() {
		return page;
	}

	public void setPage(AgencyConfirmInfoPage page) {
		this.page = page;
	}

	public AgencyAppealEntity getAppealInfo() {
		return appealInfo;
	}

	public void setAppealInfo(AgencyAppealEntity appealInfo) {
		this.appealInfo = appealInfo;
	}

	public String execute() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page", page);
		List<String> authAgencyConfirmList = new ArrayList<String>(DBConfigManager.getConfigAsList("authority.agency.confirm"));
		if(authAgencyConfirmList.contains(""+user.getId())){
		    paramMap.put("canOpenAppeal",true);
		}
		authAgencyConfirmList.addAll(DBConfigManager.getConfigAsList("authority.audit.super"));
		if(!authAgencyConfirmList.contains(""+user.getId())){
			paramMap.put("dealDeparts", appointManagerService.getDealDeaprtSetByUserId(user.getId()));
			paramMap.put("userName",user.getRealName());
		}
		page = shareSolutionService.getAgencyConfirmInfoPage(paramMap);
		return "agency_confirm_list";
	}
	
	public String getNonbShareAppeal(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page", page);
		List<String> authAgencyConfirmList = new ArrayList<String>(DBConfigManager.getConfigAsList("authority.agency.confirm"));
		authAgencyConfirmList.addAll(DBConfigManager.getConfigAsList("authority.audit.super"));
		if(!authAgencyConfirmList.contains(""+user.getId())){
			paramMap.put("dealDeparts", appointManagerService.getDealDeaprtSetByUserId(user.getId()));
			paramMap.put("userName",user.getRealName());
		}
		page = shareSolutionService.getNonbShareAppealInfoPage(paramMap);
		return "nonb_share_appeal";
	}
	
	public String toAgencyPayoutBill() {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("agencyId", request.getParameter("agencyId"));
		requestMap.put("complaintId", request.getParameter("complaintId"));
		
		Map<String, Object> agencyPayoutBill = shareSolutionService.getAgencyPayInfoDetail(requestMap);
		request.setAttribute("agencyPayoutBill", agencyPayoutBill);
		
		return "agency_payout_bill";
	}
	
	public String toAppealInfoBill() {
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("agencyId", request.getParameter("agencyId"));
		requestMap.put("complaintId", request.getParameter("complaintId"));
		appealInfo = appealService.getAppealInfo(requestMap);
		List<String> authAgencyConfirmList = DBConfigManager.getConfigAsList("authority.agency.confirm");
		if(authAgencyConfirmList.contains(""+user.getId())){
			request.setAttribute("canOpenAppeal", true);
		}
		request.setAttribute("confirmStateId", request.getParameter("confirmStateId"));
		
		return "agency_appeal_bill";
	}
	
	public String processAppeal() {
		appealService.processAppeal(appealInfo, user.getId());
		
		succFlag = 1;
		
		return "agency_appeal_bill";
	}
	
	public String openAppeal() {
		Map<String, Object> params = new HashMap<String, Object>();
		String agencyId = request.getParameter("agencyId");
		String complaintId = request.getParameter("complaintId");
		params.put("complaintId", complaintId);
		params.put("code", agencyId);
		params.put("confirmState", 0);
		params.put("expireTime", festivalService.getWorkDaysEndTime(3));
		supportShareService.updateByCodeAndCompId(params);
		
		return "agency_confirm_list";
	}
	
	public String openAppealById() {
		Map<String, Object> params = new HashMap<String, Object>();
		Integer supportId = Integer.valueOf(request.getParameter("supportId"));
		supportShareService.updateByCodeAndCompId(params);
		SupportShareEntity support= (SupportShareEntity) supportShareService.get(supportId);
		support.setId(supportId);
		support.setConfirmState(5);
		supportShareService.update(support);
		return this.getNonbShareAppeal();
	}
	
	public String launchAppealFlow() {
		Integer supportId = Integer.valueOf(request.getParameter("supportId"));
		String appealContent = request.getParameter("appealContent");
		SupportShareEntity support= (SupportShareEntity) supportShareService.get(supportId);
		try {
			shareSolutionService.launchSupplierAppeal(support,appealContent,user);
			complaintFollowNoteService.addFollowNote(support.getComplaintId(), user.getId(), user.getRealName(), appealContent,0,Constans.PRODUCT_APPEAL);
		} catch (Exception e) {
			logger.error("AppealPayout Failed complaintId:" + support.getComplaintId() + ",agencyId:" + support.getCode() + e.getMessage(),e);
		}
		return this.getNonbShareAppeal();
	}
}
