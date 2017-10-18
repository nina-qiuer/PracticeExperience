package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.TaskReminderEntity;

@Repository
public interface IComplaintTaskReminderMapper {

	   void  addTaskReminder(TaskReminderEntity entity);
	
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
}
