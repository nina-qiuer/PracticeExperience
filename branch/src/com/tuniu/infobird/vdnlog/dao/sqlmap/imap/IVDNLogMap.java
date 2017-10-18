package com.tuniu.infobird.vdnlog.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("infobird_dao_sqlmap-vdnlog")
public interface IVDNLogMap extends IMapBase {

	public List getVDNLogInfo(Map map);
}
