package com.tuniu.gt.uc.dao.sqlmap.imap;

import java.util.Map;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("uc_dao_sqlmap-depuser_map")
public interface IDepuserMapMap extends IMapBase { 
	public void deleteByUserId(Map<String, Object> paramMap);

}
