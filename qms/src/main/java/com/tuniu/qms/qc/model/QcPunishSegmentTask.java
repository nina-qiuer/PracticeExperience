package com.tuniu.qms.qc.model;

public class QcPunishSegmentTask {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer dataId;
	
	private Integer taskType;
	
	private Integer failedTimes;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
