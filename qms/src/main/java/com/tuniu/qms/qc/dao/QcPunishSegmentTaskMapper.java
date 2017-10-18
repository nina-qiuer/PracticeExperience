package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcPunishSegmentTaskDto;
import com.tuniu.qms.qc.model.QcPunishSegmentTask;

public interface QcPunishSegmentTaskMapper extends BaseMapper<QcPunishSegmentTask, QcPunishSegmentTaskDto>{
	
	/**
	 * 获得处罚单任务表中数据
	 * @return
	 */
	List<QcPunishSegmentTask> getTaskList();

}
