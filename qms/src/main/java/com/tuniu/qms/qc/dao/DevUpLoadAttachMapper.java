package com.tuniu.qms.qc.dao;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.DevUpLoadAttachDto;
import com.tuniu.qms.qc.model.DevUpLoadAttach;

public interface DevUpLoadAttachMapper extends BaseMapper<DevUpLoadAttach, DevUpLoadAttachDto>{

	/**
	 * 根据故障单ID删除附件表
	 * @param qpId
	 */
	 void deleteAttach(Integer devId);
}
