package com.tuniu.qms.common.service;

import com.tuniu.qms.common.dto.SyncyTaskDto;
import com.tuniu.qms.common.model.SyncyTask;
import com.tuniu.qms.common.service.base.BaseService;

public interface SyncyTaskService extends BaseService<SyncyTask, SyncyTaskDto>{

	/**
	 * 添加任务
	 * @param dataId
	 * @param taskType
	 */
	void builTask(Integer dataId, int taskType);

	/**
	 * 创建供应商责任赔款查漏监控邮件
	 */
	void createSupplierRespMonitor();

}
