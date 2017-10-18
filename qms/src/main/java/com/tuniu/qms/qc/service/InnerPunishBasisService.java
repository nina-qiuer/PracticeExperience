package com.tuniu.qms.qc.service;

import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.InnerPunishBasisDto;
import com.tuniu.qms.qc.dto.PunishStandardDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;

public interface InnerPunishBasisService  extends BaseService<InnerPunishBasis, InnerPunishBasisDto>{

	 void addInnerPunish(PunishStandardDto dto);
	
	 void updatePunish(InnerPunishBasis basis);
	
	 int getPunishBasisIsExist(Map<String, Object> map);
	
}
