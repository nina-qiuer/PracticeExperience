package com.tuniu.qms.qc.service;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcPointAttachDto;
import com.tuniu.qms.qc.model.QcPointAttach;

public interface QcPointAttachService extends BaseService<QcPointAttach, QcPointAttachDto> {
	
	void deleteByDto(QcPointAttachDto dto);

	List<Map<String, Object>> listImproveAttach(QcPointAttachDto attachDto);

	List<QcPointAttach> getAttachList(Integer dataId, int attachBillType);
	
}
