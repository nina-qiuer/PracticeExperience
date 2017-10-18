package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.CheckEmailEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.complaint.service.ICheckEmailService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.entity.DepuserMapEntity;
import com.tuniu.gt.uc.service.user.IDepuserMapService;

/**
 * 投诉单查询action
 * 
 * @author hanxuliang
 * 
 *         2014-5-5
 * 
 *         Copyright by Tuniu.com
 */

@Service("complaint_action-complaintSearch")
@Scope("prototype")
public class ComplaintSearchAction extends FrmBaseAction<IComplaintService, ComplaintEntity> {

	public ComplaintSearchAction() {
		setManageUrl("complaint");
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	public void setService(IComplaintService service) {
		this.service = service;
	};

	@Autowired
	@Qualifier("uc_service_user_impl-depuser_map")
	private IDepuserMapService  depMapService;
	
	// 引入投诉事宜service
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;

	// qc service
		@Autowired
		@Qualifier("complaint_service_complaint_impl-appoint_manager")
		private IAppointManagerService amService;
		
	// 引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;

	// 引入核实请求service
	@Autowired
	@Qualifier("complaint_service_complaint_check_email_impl-check_email")
	private ICheckEmailService checkEmailService;

	// 引入解决方案service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	private IComplaintSolutionService complaintSolutionService;

	// 引入分担方案service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;

	// 用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;

	// qc service
	@Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;

	/**
	 * 线路名称
	 */
	private String routeName;
	
	/**
	 * 返回主页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String execute() {

		DepartmentEntity dep = userService.getBusinessDepartment(user, 1);
		// 投诉处理列表每页显示个数为30
		this.perPage = 30;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap = paramSearch();
		
		if(paramMap!=null){
			if((paramMap.get("id")!=null&&!paramMap.get("id").equals(""))||(paramMap.get("orderId")!=null&&!paramMap.get("orderId").equals(""))){
			List<ComplaintEntity> getDatatList  = new ArrayList<ComplaintEntity>();
			
			// 调用父类方法返回页面列表 
			super.execute(paramMap);
			getDatatList = (List<ComplaintEntity>) request.getAttribute("dataList");
			List<ComplaintEntity> delList = new ArrayList<ComplaintEntity>();
			String custIds = "";
			for (ComplaintEntity complaintEntity : getDatatList) {
				Integer custId = complaintEntity.getCustId();
				if(custId==null){
					custId = 0;
				}
				custIds += custId+",";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("custIds", custIds);
			map.put("tagTypes", "11");
			JSONArray dataArray = service.getCustTagsByCustIds(map);
			
			request.setAttribute("tagList", dataArray);
			request.setAttribute("dataList", getDatatList);
		}
		}
		
		String res = "searchList";

		return res;
	}

	public Map<String, Object> paramSearch() {

		Map<String, Object> paramMap = new HashMap<String, Object>(); // search使用的map
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);// 放入search内容
		request.setAttribute("search", search);
		return paramMap;
	}
	
	/**
	 * 投诉处理单页面
	 * 
	 * @return String 页面
	 */
	public String toBill() {
		String id = request.getParameter("id");
		String view_type = request.getParameter("viewType");// 质检查看投诉内容
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", id);
		paramMap.put("del_flag", 1);
		if (id != null) {
			ComplaintEntity complaint = (ComplaintEntity) service.get(id);
			List<ComplaintFollowNoteEntity> followNoteList = complaintFollowNoteService.getNoteByComplaintId(id);
			List<CheckEmailEntity> checkMailList = checkEmailService.getCheckMailListByComplaintId(id);
			List<ComplaintReasonEntity> complaintReason = reasonService.getReasonAndQualitycostList(paramMap);
			ComplaintSolutionEntity complaintSoulution = complaintSolutionService.getComplaintSolutionBycompalintId(Integer.valueOf(id));
			ShareSolutionEntity shareSolutionEntity = shareSolutionService.getShareSolutionBycompalintId(Integer.valueOf(id));

			paramMap.clear();
			paramMap.put("complaintId", id);
			QcEntity qcEntity = (QcEntity) qcService.get(paramMap);
			// 将获取的各种对象信息传输到页面
			request.setAttribute("complaint", complaint);

			// edit by dingyong2
			if (null == complaint.getGuestLevel() || complaint.getGuestLevel().length() == 0) {
				complaint.setGuestLevel(getGuestLevel(ComplaintRestClient.getCustLevelNum(complaint.getOrderId())));
			}

			request.setAttribute("follow_note_list", followNoteList);
			request.setAttribute("check_mail_list", checkMailList);
			request.setAttribute("complaint_reason", complaintReason);
			request.setAttribute("complaintSoulution", complaintSoulution);
			request.setAttribute("shareSolutionEntity", shareSolutionEntity);
			request.setAttribute("qc", qcEntity);

			// 根据联系人id获取联系人信息
			if (complaint.getContactId() > 0) {
				Map<String, Object> contact = ComplaintRestClient.getTouristById(complaint.getContactId());
				request.setAttribute("contact", contact);
			}

			// 处理岗位为售后组和资深组显示填写分担方案按钮，售前组显示提交售后填写分担方案按钮
			int canAddSoulation = 0; // 是否显示填写分担方案按钮，1-显示，0-显示提交售后填写分担方案按钮
			int buttonNotDisable = 0; // 是否禁用按钮，1-启用，0-禁用
			if (Constans.AFTER_TRAVEL.equals(complaint.getDealDepart())
					|| Constans.IN_TRAVEL.equals(complaint.getDealDepart())
					|| Constans.SPECIAL_BEFORE_TRAVEL.equals(complaint
							.getDealDepart())) {
				canAddSoulation = 1;
			}

			// 订单状态为投诉已待结-3 和提交售后填写分担方案-6 ，填写分担方案按钮可操作
			if (complaint.getState() == 3 || complaint.getState() == 4
					|| complaint.getState() == 6) {
				if (complaintSoulution != null) {
					buttonNotDisable = 1;// 启用按钮
				}
			}

			// 若订单状态为6且处理人为空且当前登录人员和陈长庆不在一个组，则不能操作填写分担方案按钮
			List<UserEntity> sameGroupUsers = userService
					.getSameGroupUsers(user); // 获取当前登录人员同组人员
			boolean isInAfter = false;// 是否售后人员
			if (sameGroupUsers != null) {
				for (UserEntity userEntity : sameGroupUsers) {
					if (userEntity.getId() == 186) {
						isInAfter = true;
						break;
					}
				}
			}
			if (complaint.getState() == 6 && complaint.getDeal() == 0
					&& !isInAfter) {
				buttonNotDisable = 0;
			}

			request.setAttribute("canAddSoulation", canAddSoulation);
			request.setAttribute("buttonNotDisable", buttonNotDisable);
			request.setAttribute("view_type", view_type);

			// 如果没有分配处理人，所有按钮都禁用
			int isAssignDealer = 0;
			if (complaint.getDeal() > 0) {
				isAssignDealer = 1;
			}

			String isRepeat = request.getParameter("repeat");
			if (isRepeat != null && Integer.valueOf(isRepeat) == 1) {
				isAssignDealer = 0;
			}

			request.setAttribute("isAssignDealer", isAssignDealer);
			List<DepuserMapEntity> depUserList = depMapService.getDepIdByUser(user.getId());
			for(DepuserMapEntity dm : depUserList){
				if(dm.getDepId()==973 || user.getId()==5471 || user.getId()==5496){
					request.setAttribute("deptzj", 1);
					break;
				}
			}
			// 判断职位是否总监以上级别，若是则可以查看处理中，已待结，已完成列表所有数据，无操作权限
			// int isCharger = this.isCharger();
			// request.setAttribute("isCharger", isCharger);
			request.setAttribute("currUserId", user.getId());
			
			jspTpl = "search_bill";
		} else {
			jspTpl = "error";// 跳转到错误页面
		}
		return jspTpl;
	}

	/**
     * @param custLevelNum
     * @return
     */
    private String getGuestLevel(Integer custLevelNum) {
        String guestLevel = "";
        switch(custLevelNum){
            case 0:
                guestLevel = "注册会员";
                break;
            case 1:
                guestLevel = "一星会员";
                break;
            case 2:
                guestLevel = "二星会员";
                break;
            case 3:
                guestLevel = "三星会员";
                break;
            case 4:
                guestLevel = "四星会员";
                break;
            case 5:
                guestLevel = "五星会员";
                break;
            case 6:
                guestLevel = "白金会员";
                break;
            case 7:
                guestLevel = "钻石会员";
                break;
           default:
               guestLevel = "未知";
            
        }
        return guestLevel;
    }

}
