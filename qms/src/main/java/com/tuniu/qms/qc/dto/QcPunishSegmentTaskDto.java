package com.tuniu.qms.qc.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcPunishSegmentTask;

public class QcPunishSegmentTaskDto extends BaseDto<QcPunishSegmentTask>{
	
	private Integer dataId;
	
	private Integer taskType;
	
	private Integer failedTimes;

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getFailedTimes() {
		return failedTimes;
	}

	public void setFailedTimes(Integer failedTimes) {
		this.failedTimes = failedTimes;
	}
}
