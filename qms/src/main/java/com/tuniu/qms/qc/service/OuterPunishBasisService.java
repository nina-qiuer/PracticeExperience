package com.tuniu.qms.qc.service;

import java.util.Map;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.OuterPunishBasisDto;
import com.tuniu.qms.qc.dto.PunishStandardDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;

public interface OuterPunishBasisService  extends BaseService<OuterPunishBasis, OuterPunishBasisDto>{

	public void addOuterPunish(PunishStandardDto dto);
	
	public void updatePunish(OuterPunishBasis basis);
	
	public int getPunishBasisIsExist(Map<String, Object> map);
}
