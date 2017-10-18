package com.tuniu.gt.innerqc.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.innerqc.entity.InnerQcDutyEntity;

public interface InnerQcDutyService extends IServiceBase {
	
	List<InnerQcDutyEntity> getDutyList(InnerQcDutyEntity entity);
	
}
