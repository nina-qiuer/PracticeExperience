package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.PunishStandardDto;
import com.tuniu.qms.qc.model.PunishStandard;

public interface PunishStandardMapper extends BaseMapper<PunishStandard, PunishStandardDto> {

	
	 List<String> getLevel(PunishStandardDto dto);
}
