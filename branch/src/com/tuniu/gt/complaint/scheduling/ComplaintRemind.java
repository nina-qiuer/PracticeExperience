package com.tuniu.gt.complaint.scheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import com.tuniu.gt.callloss.service.CallLossService;
import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.FollowTimeEntity;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IFollowTimeService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.toolkit.Rtx;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.gt.workorder.entity.WorkOrderFollowTimeEntity;
import com.tuniu.gt.workorder.service.IWorkOrderFollowTimeService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

import tuniu.frm.service.Constant;


public class ComplaintRemind {
	
	private static Logger logger = Logger.getLogger(ComplaintRemind.class);
	
	//引入投诉sercie
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_follow_time_impl-follow_time")
	private IFollowTimeService followTimeService;
	
	//引入工单跟进提醒service
	@Autowired
	@Qualifier("wo_service_complaint_follow_time_impl-follow_time")
	private IWorkOrderFollowTimeService workOrderFollowTimeService;
		
	//用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	// 引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@Autowired
	@Qualifier("callloss_service_callloss_impl-callloss")
	private CallLossService callLossService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;
	
	/**
	 * 投诉处理跟进提醒
	 */
	public void rtxFollowRemind(){
		List<FollowTimeEntity> followTimeList = (List<FollowTimeEntity>) followTimeService.getExpireFollowList();
		if (followTimeList != null && followTimeList.size() > 0) {
			for (FollowTimeEntity followTimeEntity : followTimeList) {
				if (1 == followTimeEntity.getDelFlag()) {
					int userId = followTimeEntity.getUserId();
					String userName = followTimeEntity.getUserName();
					String compId = followTimeEntity.getComplaintId().toString();
					List<UserEntity> managerList = new ArrayList<UserEntity>();
					if (!CommonUtil.isStatus(userService.getExtensionByUserId(userId))) { // 如果当前处理人不在班，发送给交接人
						ComplaintEntity compEnt = (ComplaintEntity) complaintService.get(compId);
						userId = compEnt.getAssociater();
						if (0 == userId || !CommonUtil.isStatus(userService.getExtensionByUserId(userId))) { // 如果交接人不存在或不在班，发送给当前处理人的主管
							int deptId = userService.getUserByID(followTimeEntity.getUserId()).getDepId();
							managerList = userService.getManageByDepartmentId(deptId);
						}
					}
					String title = "投诉单号：" + compId + "[跟进提醒]";
					String content = getFollowContent(followTimeEntity);
					if (userId > 0 && CommonUtil.isStatus(userService.getExtensionByUserId(userId))) {
						new RTXSenderThread(userId, userName, title, content).start();
					} else {
						if (!managerList.isEmpty()) {
							for (UserEntity manager : managerList) {
								if (260 != manager.getId()) { // 过滤孟锐丽
									new RTXSenderThread(manager.getId(), manager.getUserName(), title, content).start();
								}
							}
						}
					}
					
					// 已发送提醒设置为删除
					followTimeEntity.setDelFlag(0);
					followTimeService.update(followTimeEntity);
				}
			}
		}
	}
	
	/**
	 * 工单处理跟进提醒
	 */
	public void rtxWorkOrderFollowRemind() {
		List<WorkOrderFollowTimeEntity> workOrderFollowTimeList = (List<WorkOrderFollowTimeEntity>) workOrderFollowTimeService
				.getExpireFollowList();
		if (!CollectionUtils.isEmpty(workOrderFollowTimeList)) {
			for (WorkOrderFollowTimeEntity workOrderFollowTimeEntity : workOrderFollowTimeList) {
				if (0 == workOrderFollowTimeEntity.getDelFlag()) {
					int userId = workOrderFollowTimeEntity.getUserId();
					String userName = workOrderFollowTimeEntity.getUserName();
					String woId = workOrderFollowTimeEntity.getWoId() + "";
					String title = "工单" + woId + "[跟进提醒]";

					StringBuffer content = new StringBuffer();
					content.append(title).append("，")
							.append(workOrderFollowTimeEntity.getNote());
					if (userId > 0) {
						new RTXSenderThread(userId, userName, title,
								content.toString()).start();
					}
				}

				// 已发送提醒设置为删除
				workOrderFollowTimeEntity.setDelFlag(1);
				workOrderFollowTimeService.update(workOrderFollowTimeEntity);
			}
		}
	}

	private String getFollowContent(FollowTimeEntity followTimeEntity) {
		StringBuffer sb = new StringBuffer();
		sb.append("订单号：").append(followTimeEntity.getOrderId()).append("\r")
		  .append("投诉单号：").append(followTimeEntity.getComplaintId()).append("\r")
		  .append("内容：").append(followTimeEntity.getNote()).append("\r");
		return sb.toString();
	}
	
	public void overtimeUnAssignBef() {
		overtimeUnAssign(Constans.BEFORE_TRAVEL);
	}
	
	public void overtimeUnAssignIn() {
		overtimeUnAssign(Constans.IN_TRAVEL);
	}
	
	public void overtimeUnAssignAft() {
		overtimeUnAssign(Constans.AFTER_TRAVEL);
	}
	
	public void overtimeUnAssignBefSer() {
		overtimeUnAssign(Constans.SPECIAL_BEFORE_TRAVEL);
	}
	
	/**
	 * 超时未分单提醒
	 */
	private void overtimeUnAssign(String dealDepart) {
		Map<String, Object> unAssignInfo = complaintService.getOvertimeUnAssignInfo(dealDepart);
		long num = (Long) unAssignInfo.get("num");
		if (num > 0) {
			String title = "超时未分单提醒";
			String content = getUnAssignContent(dealDepart, num);
			
			List<UserEntity> managerList = new ArrayList<UserEntity>();
			if (Constans.BEFORE_TRAVEL.equals(dealDepart)) {
				new RTXSenderThread(17193, "fangyouming", title, content).start();
			} else if (Constans.IN_TRAVEL.equals(dealDepart)) {
				managerList= userService.getManageByFatherDepId(748);//74：售后组
			} else if (Constans.AFTER_TRAVEL.equals(dealDepart)) {
				managerList= userService.getManageByFatherDepId(74);//74 资深组
				UserEntity u1 = new UserEntity();
				u1.setId(5831);
				u1.setUserName("gaowenyan");
				UserEntity u3 = new UserEntity();
				u3.setId(7331);
				u3.setUserName("zhangjia2");
				managerList.add(u1);
				managerList.add(u3);
			} else if (Constans.SPECIAL_BEFORE_TRAVEL.equals(dealDepart)) {
				managerList = userService.getManageByFatherDepId(1296); // 1296：出游前客服组ID
			}
			
			for (UserEntity manager : managerList) {
				if (260 != manager.getId()) { // 过滤孟锐丽
					new RTXSenderThread(manager.getId(), manager.getUserName(), title, content).start();
				}
			}
		}
	}
	
	private String getUnAssignContent(String dealDepart, long num) {
		StringBuffer sb = new StringBuffer();
		sb.append("【").append(dealDepart).append("】有")
		  .append(num).append("个超时未分配的投诉单，请及时处理。");
		return sb.toString();
	}
	
	/**
	 * 出游前自动分单提醒+数据修复+分单
	 * 出游前的投诉单有时会出现售前客服、客服经理、高级客服经理为空的情况，
	 * 导致系统无法将该投诉单自动分配给售前客服或客服经理或高级客服经理，
	 * 其原因是由于Boss系统还没有分单该订单就被投诉了，所以没有指定的售前客服等人员。
	 * 应对措施：启动后台任务监听Boss系统是否已经分单，如果已经分单了，把字段补全，然后投诉系统自动分单。
	 */
	public void beforeTravelAssign() {
		/* 查找售前客服、客服经理、高级客服经理为空的投诉单 */
		List<ComplaintEntity> errorDataList = complaintService.getBefThreeNullList();
		
		try {
			/* 查询订单信息，如果订单已经分单，则修复数据，然后分单并发送RTX提醒 */
			for (ComplaintEntity comp : errorDataList) {
				/* 查询订单信息接口 */
				ComplaintEntity orderInfo = complaintService.getOrderInfoMain(comp.getOrderId().toString());
				
				if (null != orderInfo) {
					//如果有数据，则修复
					String customer = orderInfo.getCustomer();
					String customerLeader = orderInfo.getCustomerLeader();
					String serviceManager = orderInfo.getServiceManager();
					if (null != orderInfo &&( (StringUtil.isNotEmpty(customer) && !"null".equals(customer))
							|| (StringUtil.isNotEmpty(customerLeader) && !"null".equals(customerLeader))
							|| (StringUtil.isNotEmpty(serviceManager) && !"null".equals(serviceManager)))) {

						comp.setCustomer(customer);
						comp.setCustomerLeader(customerLeader);
						comp.setServiceManager(serviceManager);
						// 如果处理人为空，分单
						if (comp.getDeal() <= 0) {
							befTravelAssignDeal(comp);
						}
						complaintService.update(comp);

					}
				}
			}
		} catch (Exception e) {
			logger.error("出游前自动分单提醒+数据修复+分单错误", e);
		}
	}
	
	/**
	 * 出游前投诉单自动分配处理人 客服->客服经理->高级客服经理
	 */
	private void befTravelAssignDeal(ComplaintEntity comp) {
		String dealName = "";
		int dealPeople = 0;
		UserEntity user = null;
		String names[] = {comp.getCustomer(), comp.getCustomerLeader(), comp.getServiceManager()};
		for (int i = 0; i < names.length; i++) {
			if (!"".equals(names[i])) {
				user = (UserEntity) userService.getUserByName(names[i]);
				if (user != null && 0 == user.getDelFlag()) {
					dealName = names[i];
					dealPeople = user.getId();
					break;
				}
			}
		}
		if (dealPeople > 0) {
			comp.setDeal(dealPeople);
			comp.setDealName(dealName);
			comp.setState(2);
			comp.setAssignTime(new Date());
			
			// 添加有效操作记录
			String noteContent = "分配给:" + comp.getDealName();
			complaintFollowNoteService.addFollowNote(comp.getId(), 0, "robot", noteContent,0 , Constans.ASSIGN_DEALER);
			
			/* 发送自动分单提醒 */
			String title="自动分单提醒";
			String content="【投诉质检-新分配单】"+"\n投诉单号:"+comp.getId()+"\n订单号:"+comp.getOrderId()+"\n";
			new RTXSenderThread(comp.getDeal(), comp.getDealName(), title, content).start();
		}
	}
	
	/**
	 * 投诉处理列表中超过三天未跟进投诉单发送rtx提醒
	 * 
	 * 投诉待指定列表，rtx发送客服经理及高级客服经理
	 * 
	 * 投诉处理中、投诉已待结列表，rtx发送客服、客服经理、高级客服经理
	 * 
	 * 每天上午10点运行一次
	 */
	public void processRemind(){
		//投诉处理中
		this.complaintFollowRemindByState(2);
		//投诉已待结
		this.complaintFollowRemindByState(3);
		
	}
	
	/**
	 * 投诉处理列表中超过三天未跟进投诉单发送rtx提醒
	 * 投诉待指定列表，rtx发送客服经理及高级客服经理
	 */
	public void complaintUnAssignRemind(){
		if (Constant.CONFIG.getProperty("sendFlag").equals("1")) {
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("state", 1);
	        paramMap.put("orderStateTemp", "出游前");
	        paramMap.put("dealDepart", Constans.BEFORE_TRAVEL);
	        paramMap.put("deal", "");
	        paramMap.put("buildDateOfNotAssignDeal", "usedByRTX");
	        List<ComplaintEntity> complaintList = (List<ComplaintEntity>)complaintService.fetchList(paramMap);
	       
	        if (complaintList != null && complaintList.size() > 0) {
	            for (ComplaintEntity complaint : complaintList) {
	                String title = "投诉单号：" + String.valueOf(complaint.getId()) + "，未分配提醒";
	                String note = "您名下有超过三天未分配的投诉单，投诉单号：" + String.valueOf(complaint.getId()) + "，请及时登录boss-投诉质检系统-投诉待指定列表处理";
	                //客服经理
	                this.rtxSender(complaint.getCustomerLeader(), title, note);
	                //高级客服经理
	                this.rtxSender(complaint.getServiceManager(), title, note);
	            }
	        }
		}
	}
	
	/**
	 * 投诉处理列表中超过三天未跟进投诉单发送rtx提醒
	 * 投诉处理中、投诉已待结，rtx发送客服、客服经理、高级客服经理
	 * @param state 投诉处理状态
	 */
	private void complaintFollowRemindByState(int state){
		if (Constant.CONFIG.getProperty("sendFlag").equals("1")) {
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("state", state);
	        paramMap.put("orderStateTemp", "出游前");
	        paramMap.put("dealDepart", Constans.BEFORE_TRAVEL);
	        paramMap.put("buildDateOfNotAssignDeal", "usedByRTX");
	        List<ComplaintEntity> complaintList = (List<ComplaintEntity>)complaintService.fetchList(paramMap);
	       
	        if (complaintList != null && complaintList.size() > 0) {
	            String note = "";
	            
	            for (ComplaintEntity complaint : complaintList) {
	            	String title = "";
	            	if (2 == state) {
		            	title = "投诉质检系统，投诉单号：" + String.valueOf(complaint.getId()) + "，未跟进提醒";
						note = "您名下有超过三天处理中投诉单未跟进，投诉单号：" + String.valueOf(complaint.getId()) + "，请及时登录boss-投诉质检系统-投诉处理中列表处理";
					} else if (3 == state) {
						title = "投诉质检系统，投诉单号：" + String.valueOf(complaint.getId()) + "，未完成提醒";
						note = "您名下有超过三天待结投诉单未完成，投诉单号：" + String.valueOf(complaint.getId()) + "，请及时登录boss-投诉质检系统-投诉已待结列表处理";
					}
	                //客服
	                this.rtxSender(complaint.getCustomer(), title, note);
	                //客服经理
	                this.rtxSender(complaint.getCustomerLeader(), title, note);
	                //高级客服经理
	                this.rtxSender(complaint.getServiceManager(), title, note);
	            }
	        }
		}
	}
	
	/**
	 * 发送rtx提醒，用户名
	 * @param userName
	 * @param title
	 * @param note
	 */
	private void rtxSender(String userName, String title, String note){
		if (StringUtils.isNotBlank(userName)) {
            UserEntity user = userService.getUserByName(userName);
            if (user != null) {
            	Rtx.sendSingleRtx(user.getId(), userName, title, note);
			}
        }
	}
	
	/**
	 * 呼损提醒
	 */
	public void callLossRemind() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", 0);
		paramMap.put("delFlag", 0);
		int num = callLossService.getCount(paramMap);
		if (num > 0) {
			String title = "[呼损提醒]";
			String content = "已产生呼损记录：" + num + "条";
			List<UserEntity> managerList = getManagerListForCallLoss();
			List<Integer> idList = new ArrayList<Integer>();
			for (UserEntity user : managerList) {
				if (!idList.contains(user.getId())) {
					new RTXSenderThread(user.getId(), user.getUserName(), title, content).start();
					idList.add(user.getId());
				}
			}
		}
	}
	
	private List<UserEntity> getManagerListForCallLoss() {
		Set<DepartmentEntity> deptSet = new HashSet<DepartmentEntity>();
		Set<DepartmentEntity> deptSet1 = departmentService.getAllDepartmentByFatherId(369);
		Set<DepartmentEntity> deptSet2 = departmentService.getAllDepartmentByFatherId(1935);
		deptSet.addAll(deptSet1);
		deptSet.addAll(deptSet2);
		List<UserEntity> managerList = new ArrayList<UserEntity>();
		for (DepartmentEntity dept : deptSet) {
			if (dept.getId() != 369 && dept.getId() != 1935) {
				managerList.addAll(userService.getManageByDepartmentId(dept.getId()));
			}
		}
		return managerList;
	}
	
}
