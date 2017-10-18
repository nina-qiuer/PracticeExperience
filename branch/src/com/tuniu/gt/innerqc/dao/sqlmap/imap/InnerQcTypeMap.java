package com.tuniu.gt.innerqc.dao.sqlmap.imap;


import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.innerqc.entity.InnerQcTypeEntity;


@Repository("innerqc_dao_sqlmap-inner_qc_type")
public interface InnerQcTypeMap extends IMapBase {
	
	List<InnerQcTypeEntity> getIqcTypeList();
	
}
