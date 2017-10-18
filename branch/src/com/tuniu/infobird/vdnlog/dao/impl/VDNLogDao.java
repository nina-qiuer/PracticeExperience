package com.tuniu.infobird.vdnlog.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.infobird.vdnlog.dao.sqlmap.imap.IVDNLogMap;
import com.tuniu.infobird.vdnlog.entity.VDNLogEntity;

@Repository("infobird_dao_impl-vdnlog")
public class VDNLogDao extends DaoBase<VDNLogEntity, IVDNLogMap>  implements IVDNLogMap {
	public VDNLogDao() {  
		tableName = "VDNLog";		
	}
	
	@Autowired
	@Qualifier("infobird_dao_sqlmap-vdnlog")
	public void setMapper(IVDNLogMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List getVDNLogInfo(Map map) {
		return mapper.getVDNLogInfo(map);
	}

}
