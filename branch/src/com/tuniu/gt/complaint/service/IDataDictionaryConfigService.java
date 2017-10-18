package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.common.BaseEntity;

import tuniu.frm.core.IServiceBase;

public interface IDataDictionaryConfigService extends IServiceBase{

	List<BaseEntity> getDictionaryConfigLists(Integer fatherId);
}
