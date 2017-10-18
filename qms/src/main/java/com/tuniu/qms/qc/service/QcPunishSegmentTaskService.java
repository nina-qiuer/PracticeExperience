package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcPunishSegmentTaskDto;
import com.tuniu.qms.qc.model.QcPunishSegmentTask;

public interface QcPunishSegmentTaskService extends BaseService<QcPunishSegmentTask, QcPunishSegmentTaskDto>{
	
	/**
	 * 获得处罚单任务表中数据
	 * @return
	 */
	List<QcPunishSegmentTask> getTaskList();
	
	/**
	 * 添加处罚单数据切片任务
	 * @param qcId
	 * @param b
	 */
	void builTask(Integer qcId, int b);
	
}
