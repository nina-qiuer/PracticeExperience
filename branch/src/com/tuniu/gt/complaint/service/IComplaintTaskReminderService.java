package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.TaskReminderEntity;
import com.tuniu.gt.frm.entity.UserEntity;


public interface IComplaintTaskReminderService {

     int addTaskReminder(TaskReminderEntity entity);
	
     /**
		 * 查询列表
		 * @param map
		 * @return
	*/
	 List<TaskReminderEntity> queryTaskReminder(Map<String, Object> map) ;
	 
	/**
		 * 查询总数
		 * @param map
		 * @return
	*/
	int queryTaskReminderCount(Map<String, Object> map); 
	
	void updateTaskReminder(Map<String, Object> map);
	
	List<TaskReminderEntity> getTaskList(Map<String, Object> map);

	TaskReminderEntity getCallBackInfoFromCmp(ComplaintEntity complaint);

	/**
	 * 根据投诉单号新增默认的回呼记录
	 * @param complaintId
	 */
	void addDefaultComplaintTask(Integer complaintId);

	/**
	 * 根据投诉单号、人员信息新增默认的回呼记录
	 * @param complaintId
	 * @param user
	 */
	void addDefaultComplaintTask(Integer complaintId, UserEntity user);
}
