package com.tuniu.infobird.vdnlog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.infobird.vdnlog.dao.impl.VDNLogDao;
import com.tuniu.infobird.vdnlog.service.IVDNLogService;

@Service("infobird_service_vdnlog_impl-vdnlog")
public class VDNLogServiceImpl extends ServiceBaseImpl<VDNLogDao> implements IVDNLogService {
	@Autowired
	@Qualifier("infobird_dao_impl-vdnlog")
	public void setDao(VDNLogDao dao) {
		this.dao = dao;
	}

	@Override
	public List getVDNLogInfo(Map map) {
		return dao.getVDNLogInfo(map);
	}

}
