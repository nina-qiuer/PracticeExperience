package com.tuniu.gt.innerqc.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.innerqc.entity.InnerQcTypeEntity;

public interface InnerQcTypeService extends IServiceBase {
	
	List<InnerQcTypeEntity> getIqcTypeList();
	
}
