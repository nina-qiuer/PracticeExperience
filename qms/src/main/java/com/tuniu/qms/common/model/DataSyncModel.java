package com.tuniu.qms.common.model;

/**
 * 数据同步模型
 * @author zhangsensen
 *
 */
public class DataSyncModel {
	
	/** 需要同步的数据Id*/
	private Integer dataId;
	/** 数据失败次数*/
	private Integer failTimes;
	public Integer getDataId() {
		return dataId;
	}
	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}
	public Integer getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}
}
