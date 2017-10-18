package com.tuniu.qms.qc.dao;

import java.util.Map;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.UpLoadAttachDto;
import com.tuniu.qms.qc.model.UpLoadAttach;

public interface UpLoadAttachMapper extends BaseMapper<UpLoadAttach, UpLoadAttachDto>{

	/**
	 * 根据质量问题ID删除附件表
	 * @param qpId
	 */
	 void deleteAttach(Integer qpId);
	 
	 void addCopyFile(Map<String, Object> map);
}
