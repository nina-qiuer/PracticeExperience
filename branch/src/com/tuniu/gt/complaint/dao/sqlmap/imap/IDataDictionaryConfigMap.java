package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.common.BaseEntity;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-data_dictionary_config")
public interface IDataDictionaryConfigMap extends IMapBase {

	List<BaseEntity> getDictionaryConfigLists(Integer fatherId);
	
}
