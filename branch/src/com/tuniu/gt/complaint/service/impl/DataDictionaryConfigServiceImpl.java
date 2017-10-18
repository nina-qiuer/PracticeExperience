package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.common.BaseEntity;
import com.tuniu.gt.complaint.dao.impl.DataDictionaryConfigDao;
import com.tuniu.gt.complaint.service.IDataDictionaryConfigService;

import tuniu.frm.core.ServiceBaseImpl;

@Service("complaint_service_complaint_impl-data_dictionary_config")
public class DataDictionaryConfigServiceImpl extends ServiceBaseImpl<DataDictionaryConfigDao>
		implements IDataDictionaryConfigService {
	private static Logger logger = Logger.getLogger(DataDictionaryConfigServiceImpl.class);

	@Autowired
	@Qualifier("complaint_dao_impl-data_dictionary_config")
	public void setDao(DataDictionaryConfigDao dao) {
		this.dao = dao;
	}

	@Override
	public List<BaseEntity> getDictionaryConfigLists(Integer fatherId) {
		return dao.getDictionaryConfigLists(fatherId);
	}
}
