package com.tuniu.gt.frm.dao.sqlmap.imap;

import java.util.Map;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("frm_dao_sqlmap-role_privilege")
public interface IRolePrivilegeMap extends IMapBase { 

	/**
	 * 清除系统分配的权限
	 * @param paramMap
	 */
	public void cleanManageLevel(Map<String, Object> paramMap);
	
	public void addManageLevel(Map<String, Object> paramMap);
	
	public void updateDelFlagByRoleId(Map<String, Object> paramMap);

}
