package com.tuniu.qms.common.service;

import com.tuniu.qms.common.dto.OperationLogDto;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;

public interface OperationLogService extends BaseService<OperationLog, OperationLogDto> {
	
	/**
	 * 添加操作日志
	 * @param qcId
	 * @param user
	 * @param flowName 事件
	 * @param content 内容
	 */
	void addQcOperationLog(Integer qcId, User user, String flowName, String content);

}
