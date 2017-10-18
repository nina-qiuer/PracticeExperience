package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintTaskReminderMapper;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.TaskReminderEntity;
import com.tuniu.gt.complaint.enums.DealDepartIdEnum;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTaskReminderService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.RTXSenderThread;

@Service("cmpTaskReminderService")
public class ComplaintTaskReminderServiceImpl implements IComplaintTaskReminderService {

	private static Logger logger = Logger.getLogger(ComplaintTaskReminderServiceImpl.class);

	@Autowired
	private IComplaintTaskReminderMapper complaintTaskReminderMapper;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;

	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-auto_assign")
	private IAutoAssignService autoAssignService;

	@Override
	public int addTaskReminder(TaskReminderEntity entity) {
		try {
			complaintTaskReminderMapper.addTaskReminder(entity);
			sendTaskRemindRtxReminder(entity);
			return 0;
		} catch (Exception e) {
			logger.error(e.getMessage() + "保存回呼记录失败", e);
			return 1;
		}
	}

	@Override
	public List<TaskReminderEntity> queryTaskReminder(Map<String, Object> map) {
		try {
			return complaintTaskReminderMapper.queryTaskReminder(map);
		} catch (Exception e) {
			logger.error("查询任务提醒列表数据失败：" + e);
			return null;
		}
	}

	@Override
	public int queryTaskReminderCount(Map<String, Object> map) {
		try {
			return complaintTaskReminderMapper.queryTaskReminderCount(map);
		} catch (Exception e) {
			logger.error("查询任务提醒列表总数：" + e);
			return 0;
		}
	}

	@Override
	public void updateTaskReminder(Map<String, Object> map) {
		try {
			complaintTaskReminderMapper.updateTaskReminder(map);
		} catch (Exception e) {
			logger.error("更新回呼状态失败：" + e);
		}
	}

	@Override
	public List<TaskReminderEntity> getTaskList(Map<String, Object> map) {
		return complaintTaskReminderMapper.getTaskList(map);
	}

	@Override
	public TaskReminderEntity getCallBackInfoFromCmp(ComplaintEntity complaint) {
		TaskReminderEntity entity = new TaskReminderEntity();
		entity.setGuestName(complaint.getGuestName());
		entity.setGuestLevel(complaint.getGuestLevel());
		entity.setCmpId(complaint.getId());
		entity.setOrderId(complaint.getOrderId());
		entity.setStartTime(complaint.getStartTime());
		entity.setOrderState(complaint.getOrderState());
		entity.setBuildDate(complaint.getBuildDate());
		entity.setCustomerLeader(complaint.getCustomerLeader());
		entity.setCustomerLeaderId(complaint.getCustomerLeaderId());
		entity.setDealDepart(complaint.getDealDepart());
		entity.setDealName(complaint.getDealName());
		entity.setDeal(complaint.getDeal());
		entity.setLevel(complaint.getLevel());
		entity.setRepeateTime(complaint.getRepeateTime());
		entity.setCmpState(complaint.getState());
		entity.setCbState(Constans.CALL_BACK_WAIT);// 待回呼
		return entity;
	}

	/**
	 * 根据投诉单号新增默认的回呼记录
	 * 
	 * @param complaintId
	 */
	@Override
	public void addDefaultComplaintTask(Integer complaintId) {
		try {
			ComplaintEntity complaint = (ComplaintEntity) complaintService.get(complaintId);
			TaskReminderEntity entity = this.getCallBackInfoFromCmp(complaint);
			entity.setAddPerson(Constans.SYSTEM_ADMINISTRATOR_ACCOUNT);// 系统默认
			entity.setCmpPerson(complaint.getCmpPerson());
			entity.setCmpPhone(complaint.getCmpPhone());
			entity.setContent(Constans.CALL_BACK_DEFAULT_CONTENT);// 默认回呼备注
			// 回呼时间为当前时间三十分钟后
			Date thirtyMinsLater = DateUtil.addMinute(new Date(), 30);
			String orginalTime = DateUtil.formatDateTime(thirtyMinsLater);
			entity.setCallBackTime(orginalTime);
			complaintFollowNoteService.addFollowNote(complaint.getId(), 0, Constans.SYSTEM_ADMINISTRATOR_ACCOUNT,
					Constans.CALL_BACK_DEFAULT_CONTENT, 0, Constans.LAUNCH_CALL_BACK);
			this.addTaskReminder(entity);
		} catch (Exception e) {
			logger.error("新增默认回呼记录失败" + e.getMessage(), e);
		}
	}

	/**
	 * 根据投诉单号新增默认的回呼记录
	 * 
	 * @param complaintId
	 */
	@Override
	public void addDefaultComplaintTask(Integer complaintId, UserEntity user) {
		try {
			ComplaintEntity complaint = (ComplaintEntity) complaintService.get(complaintId);
			TaskReminderEntity entity = this.getCallBackInfoFromCmp(complaint);
			entity.setAddPerson(user.getRealName());// 系统默认
			entity.setCmpPerson(complaint.getCmpPerson());
			entity.setCmpPhone(complaint.getCmpPhone());
			entity.setContent(Constans.CALL_BACK_DEFAULT_CONTENT);// 默认回呼备注
			// 回呼时间为当前时间三十分钟后
			Date thirtyMinsLater = DateUtil.addMinute(new Date(), 30);
			String orginalTime = DateUtil.formatDateTime(thirtyMinsLater);
			entity.setCallBackTime(orginalTime);
			complaintFollowNoteService.addFollowNote(complaint.getId(), user.getId(), user.getRealName(),
					Constans.CALL_BACK_DEFAULT_CONTENT, 0, Constans.LAUNCH_CALL_BACK);
			this.addTaskReminder(entity);
		} catch (Exception e) {
			logger.error("新增默认回呼记录失败" + e.getMessage(), e);
		}
	}

	// 推送回呼rtx提醒
	private void sendTaskRemindRtxReminder(TaskReminderEntity taskRemind) {
		Integer complaintId = taskRemind.getCmpId();
		try {
			List<UserEntity> sendRtxUsers = new ArrayList<UserEntity>();
			ComplaintEntity complaint = (ComplaintEntity) complaintService.get(complaintId);
			Integer dealId = complaint.getDeal();
			String dealDepart = complaint.getDealDepart();

			//预订中心和会员顾问 回呼逻辑
			if (Constans.CUST_DEPART.equals(dealDepart) || Constans.STAR_CUST_DEPART.equals(dealDepart)
					|| Constans.PREDETERMINE_DEPART.equals(dealDepart) || Constans.CUSTOMER_DEPART.equals(dealDepart)
					|| Constans.VIP_DEPART.equals(dealDepart)) {
				if (!CommonUtil.isStatus(userService.getExtensionByUserId(dealId))) {
					Integer associaterId = complaint.getAssociater();
					// 查看交接人坐席是否在线
					if (!CommonUtil.isStatus(userService.getExtensionByUserId(associaterId))) {
						// 直接升级售后组
						complaintService.changeDealDepart(complaint, Constans.IN_TRAVEL);
						complaintFollowNoteService.addFollowNote(complaint.getId(), 0,
								Constans.SYSTEM_ADMINISTRATOR_ACCOUNT, Constans.OUT_WORK_HANDOVER, 0,
								Constans.UPGRADE_COMPLAINT_INFO);
						return;
					} else {
						// 发送给交接人
						UserEntity associaterPerson = userService.getUserByID(associaterId);
						sendRtxUsers.add(associaterPerson);
					}
				} else {
					// 发送给处理人
					UserEntity dealPerson = userService.getUserByID(dealId);
					sendRtxUsers.add(dealPerson);
				}
			} else {
				//非预订中心和会员顾问
				if (dealId != null && dealId > 0) {
					if (Constans.BEFORE_TRAVEL.equals(dealDepart) || Constans.DISTRIBUTE.equals(dealDepart)) {
						// 查看处理人坐席是否在线
						if (!CommonUtil.isStatus(userService.getExtensionByUserId(dealId))) {
							Integer associaterId = complaint.getAssociater();
							// 查看交接人坐席是否在线
							if (!CommonUtil.isStatus(userService.getExtensionByUserId(associaterId))) {
								// 则获取回呼提醒人
								sendRtxUsers.addAll(getRtxreminderByComplaint(complaint));
							} else {
								// 发送给交接人
								UserEntity associaterPerson = userService.getUserByID(associaterId);
								sendRtxUsers.add(associaterPerson);
							}
						} else {
							// 发送给处理人
							UserEntity dealPerson = userService.getUserByID(dealId);
							sendRtxUsers.add(dealPerson);
						}
					} else {
						// 查看处理人分单是否打开
						if (!autoAssignService.assignStateIsWork(dealId, dealDepart)) {
							Integer associaterId = complaint.getAssociater();
							// 查看交接人分单是否打开
							if (!autoAssignService.assignStateIsWork(associaterId, dealDepart)) {
								// 进行二次分配
								if (!complaintService.autoReturnNotAssigned(complaint)) {
									// 则获取回呼提醒人
									sendRtxUsers.addAll(getRtxreminderByComplaint(complaint));
								}
							} else {
								// 发送给交接人
								UserEntity associaterPerson = userService.getUserByID(associaterId);
								sendRtxUsers.add(associaterPerson);
							}
						} else {
							// 发送给处理人
							UserEntity dealPerson = userService.getUserByID(dealId);
							sendRtxUsers.add(dealPerson);
						}
					}
				}
			}
			String title = Constans.INTIME_CALL_BACK_REMIND;
			String contentDetail = "【投诉系统-回呼提醒】" + "\n投诉单号:" + complaintId + "\n订单号:" + complaint.getOrderId() + "\n"
					+ "处理人：" + complaint.getDealName() + "\n交接人：" + complaint.getAssociaterName() + "\n" + "要求回呼时间："
					+ taskRemind.getCallBackTime();
			for (UserEntity user : sendRtxUsers) {
				if (user != null) {
					new RTXSenderThread(user.getId(), user.getUserName(), title, contentDetail).start();
				}
			}
		} catch (Exception e) {
			logger.error("sendTaskRemindRtxReminder failed:complaint_id:" + complaintId + e.getMessage(), e);
		}
	}

	// 客服不在班获取rtx提醒人方法
	private List<UserEntity> getRtxreminderByComplaint(ComplaintEntity complaint) {
		List<UserEntity> sendRtxUsers = new ArrayList<UserEntity>();
		Integer dealId = complaint.getDeal();

		// 发送给处理人
		UserEntity dealPerson = userService.getUserByID(dealId);
		sendRtxUsers.add(dealPerson);

		// 发送给交接人
		Integer associaterId = complaint.getAssociater();
		if (associaterId > 0) {
			UserEntity associaterPerson = userService.getUserByID(associaterId);
			sendRtxUsers.add(associaterPerson);
		}

		// 根据处理岗发送给其他人
		String dealDepart = complaint.getDealDepart();
		if (Constans.SPECIAL_BEFORE_TRAVEL.equals(dealDepart)) {// 出游前发送给经理
			sendRtxUsers.addAll(userService.getManageByFatherDepId(DealDepartIdEnum.SPECIAL_BEFORE_TRAVEL.getValue()));
		} else if (Constans.AFTER_TRAVEL.equals(dealDepart)) {// 资深发送资深全部人员
			sendRtxUsers.addAll(userService.getUsersByDepartmentId(DealDepartIdEnum.AFTER_TRAVEL.getValue()));
			sendRtxUsers.addAll(userService.getUsersByDepartmentId(DealDepartIdEnum.AFTER_TRAVEL_EAST.getValue()));
			sendRtxUsers.addAll(userService.getUsersByDepartmentId(DealDepartIdEnum.AFTER_TRAVEL_NORTH.getValue()));
			sendRtxUsers.addAll(userService.getUsersByDepartmentId(DealDepartIdEnum.AFTER_TRAVEL_SOUTH.getValue()));
		} else if (Constans.IN_TRAVEL.equals(dealDepart)) {// 售后发送给经理
			sendRtxUsers.addAll(userService.getManageByFatherDepId(DealDepartIdEnum.IN_TRAVEL.getValue()));
			UserEntity gaowenyan = userService.getUserByID(5830);// 高文艳
			sendRtxUsers.add(gaowenyan);
			UserEntity zhangwei5 = userService.getUserByID(8945);// 张韡5
			sendRtxUsers.add(zhangwei5);
			UserEntity zhaoyue7 = userService.getUserByID(13984);// 赵玥7
			sendRtxUsers.add(zhaoyue7);
		} else if (Constans.AIR_TICKIT.equals(dealDepart)) {// 机票发送给经理
			sendRtxUsers.addAll(userService.getManageByFatherDepId(DealDepartIdEnum.AIR_TICKIT.getValue()));
		} else {// 其他发送给全组
			if (dealId > 0) {
				sendRtxUsers.addAll(userService.getSameDepUsersWithoutSelfByUserId(dealId));
			}
		}
		return sendRtxUsers;
	}
}
