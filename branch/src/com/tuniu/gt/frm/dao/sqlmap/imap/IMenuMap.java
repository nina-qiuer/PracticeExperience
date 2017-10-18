package com.tuniu.gt.frm.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.frm.entity.MenuEntity;
import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("frm_dao_sqlmap-menu")
public interface IMenuMap extends IMapBase { 
	public  void updateChildsTreeFatherId(Map<String, Object> paramMap);
	public List<MenuEntity> fetchlistAndPrivilege(Map<String, Object> paramMap); 
	public List<Map<String, Object>> fetchListOrg(Map<String, String> paramMap);

}
