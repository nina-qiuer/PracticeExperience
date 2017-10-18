package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.tuniu.gt.common.BaseEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IDataDictionaryConfigMap;
import com.tuniu.gt.complaint.entity.DataDictionaryConfigEntity;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

@Repository("complaint_dao_impl-data_dictionary_config")
public class DataDictionaryConfigDao extends DaoBase<DataDictionaryConfigEntity, IDataDictionaryConfigMap>
		implements IDataDictionaryConfigMap {
	public DataDictionaryConfigDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "data_dictionary_config";
	}

	@Autowired
	@Qualifier("complaint_dao_sqlmap-data_dictionary_config")
	public void setMapper(IDataDictionaryConfigMap mapper) {
		this.mapper = (IDataDictionaryConfigMap) mapper;
	}

	public List<BaseEntity> getDictionaryConfigLists(Integer fatherId) {
		return mapper.getDictionaryConfigLists(fatherId);
	}
	
}
