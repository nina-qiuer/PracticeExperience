package com.tuniu.gt.frm.service.privilege.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.service.Common;
import tuniu.frm.service.Constant;

import com.tuniu.gt.frm.dao.impl.MenuDao;
import com.tuniu.gt.frm.entity.MenuEntity;
import com.tuniu.gt.frm.service.privilege.IMenuService;
@Service("frm_service_privilege_impl-menu")
public class MenuServiceImpl extends ServiceBaseImpl<MenuDao> implements IMenuService {
	@Autowired
	@Qualifier("frm_dao_impl-menu")
	public void setDao(MenuDao dao) {
		this.dao = dao;
	};
	
	public Map<String, Object> getMenuFullTreeList() {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fields", "id,menu_name,tree_father_id,concat(tree_father_id,tree_id) as fullTreeId");
		paramMap.put("orderBy", "fullTreeId");
		Map<String, Object> menuList = dao.fetchMapMap(paramMap,"fullTreeId");
		return menuList;
	}
	
	
	public List<MenuEntity> fetchlistAndPrivilege() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return dao.fetchlistAndPrivilege(paramMap);
	}
	
	public List<MenuEntity> fetchlistAndPrivilege(String menuIds,String privilegeIds) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menuIds", menuIds);
		paramMap.put("privilegeIds", privilegeIds);
		return dao.fetchlistAndPrivilege(paramMap);   
	}
	
	public List<MenuEntity> getMenuList() {
		return (List<MenuEntity>)dao.fetchList();   
	}
	
	
	/** 对树形菜单进行排序
	 * @return Map with full_tree_id
	 */
	public Map<String, Object> orderTree(Map<String, Object> menuList) {
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
		Map<String, String> tmp = new TreeMap<String, String>();
		int depth = -1;
		
		for(String s:menuList.keySet()) {
			Map<String, Object> row = new HashMap<String, Object>();
			row = (Map<String, Object>)menuList.get(s);
			if(depth != Integer.parseInt(row.get("depth").toString())) {
				tmp.put(row.get("sequence").toString() + row.get("id").toString(),s);
			}	
		}
		return menuList;
	}
	
	
	
	
	
	
	
	/**
	 * 获取到全文字
	 * @param menuList
	 * @return 返回menu_id 与含有父结点名称的 全名称
	 */
	public Map<String, String> makeMenuFullname(Map<String, Object> menuList) {
		String tree_father_id = "";
		String fullText = "";
		int i = 0;
		Map<String, String> ret = new LinkedHashMap<String,String>(); 
		int g_tree_id_len = Integer.parseInt(Constant.CONFIG.get("g_tree_id_len").toString());
		
		for(String s:menuList.keySet()) {
			Map<String, Object> row = new HashMap<String,Object>();
			row = (Map<String, Object>)menuList.get(s);
			fullText = row.get("menuName").toString();
			tree_father_id = row.get("treeFatherId").toString();
			
			while(!tree_father_id.equals("")) {
				fullText = ((Map<String, Object>)menuList.get(tree_father_id)).get("menuName").toString() +"=>"+fullText;
				tree_father_id = ((Map<String, Object>)menuList.get(tree_father_id)).get("treeFatherId").toString();
			}
			ret.put(row.get("id").toString(), fullText); 
		}
		return ret;
	}
	
	
	public Map<String, String> getNextTreeIdByFatherId(int fatherId) {
	
		Map<String, String> ret = new HashMap<String, String>();
		
		int maxTreeId = 1;
		String depth = "0";
		String treeFatherId="";
		
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fields", "max(tree_id) as maxTreeId,tree_father_id,tree_id,depth,id"); //查找下级
		paramMap.put("fatherId", fatherId);
		Map<String,Object> childMap = new HashMap<String, Object>();
		
		childMap = (Map<String,Object>)dao.fetchOne(paramMap,false);
		if(childMap != null){
			maxTreeId =  Integer.parseInt(childMap.get("maxTreeId").toString()) + 1;
			treeFatherId = childMap.get("treeFatherId").toString();
			depth = childMap.get("depth").toString();
		} else {
			if(fatherId > 0) {
				paramMap.remove("fatherId");
				paramMap.put("id", fatherId);
				childMap = (Map<String,Object>)dao.fetchOne(paramMap,false); //查找自身
				treeFatherId = childMap.get("treeFatherId").toString() + childMap.get("treeId").toString();
				depth = (Integer.parseInt(childMap.get("depth").toString())+1) + "";
			}
		}
		
	
		ret.put("treeId", Common.strPad(maxTreeId, "0", 10));
		ret.put("treeFatherId", treeFatherId);
		ret.put("depth", depth);
		
		return ret;
	}
	
	
	public MenuEntity getByFatherId(int fatherId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("father_id", fatherId);
		return  (MenuEntity)dao.fetchOne(paramMap);
	}
	
	
	public void updateChildsTreeFatherId(String oldTreeFatherId,String newTreeFatherId) {
		int len = oldTreeFatherId.length();
		int len1 = newTreeFatherId.length();
		int treeIdLen = Integer.parseInt(Constant.CONFIG.get("g_tree_id_len").toString());
		
		int diff = Math.abs(len - len1) / treeIdLen;  
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		String addSet = ""; 
		if(len > len1) {
			 
			addSet = ",depth = depth - "+ diff;	 
		} else if(len < len1) {
			addSet = ",depth = depth + "+diff;	 
		}
		
		paramMap.put("len",len);
		paramMap.put("addSet",addSet);
		paramMap.put("newFatherId",newTreeFatherId);
		paramMap.put("oldFatherId",oldTreeFatherId); 
		
		dao.updateChildsTreeFatherId(paramMap);
	}
	
}
