package com.tuniu.gt.satisfaction.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("satisfaction_dao_sqlmap-mqLog")
public interface IMQLogMap extends IMapBase  {

	public List getFixData(Map map);
}
