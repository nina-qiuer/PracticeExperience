package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.PunishStandardDto;
import com.tuniu.qms.qc.model.PunishStandard;

public interface PunishStandardService extends BaseService<PunishStandard, PunishStandardDto>{
	
	
	public List<String> getLevel(PunishStandardDto dto);
}
