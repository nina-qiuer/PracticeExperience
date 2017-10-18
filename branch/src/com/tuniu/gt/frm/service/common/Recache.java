package com.tuniu.gt.frm.service.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.tuniu.gt.frm.dao.impl.MenuDao;
import com.tuniu.gt.frm.entity.MenuEntity;
import com.tuniu.gt.frm.entity.MenuPrivilegeEntity;
import com.tuniu.gt.frm.service.privilege.IMenuService;
import com.tuniu.gt.frm.service.privilege.IRolePrivilegeService;
import com.tuniu.gt.frm.service.privilege.IRoleService;
import com.tuniu.gt.frm.service.privilege.IRoleUserAttrService;

import tuniu.frm.service.Bean;
import tuniu.frm.service.Common;
import tuniu.frm.service.Constant;

@Service("frm_service_common-recache")
public class Recache {
	

	@Autowired
	@Qualifier("frm_service_privilege_impl-role_user_attr")
	private IRoleUserAttrService roleUserAttrService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role")
	private IRoleService roleService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-role_privilege")
	private  IRolePrivilegeService rolePrivilegeService;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu")
	private IMenuService menuService;
	
	public Recache() {
		
	}
	public void menuRecache() {
		MenuDao dao = (MenuDao)Bean.get("frm_dao_impl-menu");
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		List<Map<String, Object>> l = dao.fetchListOrg();
		Map<String, Object> m = dao.list2map(l,"id"); 
		Map<String, Map<String, Object>> ret = new LinkedHashMap<String, Map<String,Object>>();
		
		for(String s:m.keySet()) {
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			tmpMap = (Map<String, Object>)m.get(s);
			if(!tmpMap.get("father_id").toString().equals("0") ) {
				((Map<String, Object>)m.get(tmpMap.get("father_id").toString())).put("has_child",1);
			} else {
				tmpMap.put("has_child", 0);
			}
		}
		
		JSONObject json = new  JSONObject();
		String s = "var g_menu_obj = " + json.fromObject(m).toString(); 
		if(new File(Constant.CONFIG.getProperty("SRC_ROOT_PATH")).exists()) { 
			Common.filePutContents(Constant.CONFIG.getProperty("SRC_ROOT_PATH")+"WebContent/cache/data/menu.js",s);
		}
		Common.filePutContents(Constant.ROOT_PATH+"cache/data/menu.js",s);
		ExEhcache.set("menu", m);  
	}
	
	public void allPrivilegesRecache() {
		Map<String,List<Map<String, String>>> ret = new HashMap<String, List<Map<String,String>>>();
		
		MenuDao dao = (MenuDao)Bean.get("frm_dao_impl-menu");
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		List<MenuEntity> list = (List<MenuEntity>) dao.fetchList();
		
		addMenuUrl2Map(ret, list);
		ExEhcache.set("all_privileges", ret);  
	}

	
	/**
	 * 用户权限缓存
	 * @param uid
	 */
	public void userPrivilegesRecache(Integer uid) {
		
		Map<String,List<Map<String, String>>> ret = new HashMap<String, List<Map<String,String>>>();
		
		String roleIds = roleUserAttrService.getUserRoles(uid);
		String menuIds = "";
		String privilegeIds = "";
		List<MenuEntity>  list = null;
		if(!roleIds.equals("")) {
			menuIds = rolePrivilegeService.getManageMenuIds(roleIds, 1);
			privilegeIds = rolePrivilegeService.getManagePrivilegeIds(roleIds, 1);
			list = menuService.fetchlistAndPrivilege(menuIds, privilegeIds);
			//将url列表加入到ret里
			addMenuUrl2Map(ret,list);
		}
		
		roleIds = roleService.getSuperCanManageRoles(uid);
		//获取超管角色
		if(!roleIds.equals("")) {
			menuIds = rolePrivilegeService.getManageMenuIds(roleIds, 4);
			privilegeIds = rolePrivilegeService.getManagePrivilegeIds(roleIds, 4);
			list = menuService.fetchlistAndPrivilege(menuIds, privilegeIds);
			addMenuUrl2Map(ret,list);
		}
		
		//获取普管角色
		
		roleIds = roleService.getCanManageRoles(uid);
		if(!roleIds.equals("")) {
			menuIds = rolePrivilegeService.getManageMenuIds(roleIds, 2);
			privilegeIds = rolePrivilegeService.getManagePrivilegeIds(roleIds, 2);
			list = menuService.fetchlistAndPrivilege(menuIds, privilegeIds);
			addMenuUrl2Map(ret,list);
		}
		//将权限添加到ehcache里
		String key = ExEhcache.makeKey("user_privileges", uid);
		ExEhcache.set(key, ret); 
		
	}
	
	
	
	private void addMenuUrl2Map(Map<String,List<Map<String, String>>> ret  ,List<MenuEntity>  list) {
		
		List<MenuPrivilegeEntity> l = null;
		String url = "";
		for(int i=0;i<list.size();i++) {
			url = list.get(i).getMenuUrl();
			if(!url.equals("")) {
				Map<String, Object> urlMap = Common.parseUrl(url);
				//添加菜单权限
				List<Map<String, String>> paramList =  new ArrayList<Map<String,String>>();
				if(!ret.containsKey(urlMap.get("url"))) {
					paramList.add((Map<String, String>)urlMap.get("query"));
					ret.put(urlMap.get("url").toString(),paramList);
				} else {
					paramList = ret.get(urlMap.get("url").toString());
					if(!paramList.contains(urlMap.get("query"))) {
						paramList.add((Map<String, String>)urlMap.get("query"));
						ret.put(urlMap.get("url").toString(),paramList);
					}
				}
				
				//添加权限点 权限
				l = list.get(i).getPrivileges();
				if(l != null) {
					for(int j=0;j<l.size();j++) {
						url = l.get(j).getPrivilegeUrl();
						if(!l.get(j).getPrivilegeUrl().equals("")) {
							urlMap = Common.parseUrl(url);
							if(!ret.containsKey(urlMap.get("url"))) {
								paramList.add((Map<String, String>)urlMap.get("query"));
								ret.put(urlMap.get("url").toString(),paramList);
							} else {
								paramList = ret.get(urlMap.get("url").toString());
								if(!paramList.contains(urlMap.get("query"))) {
									paramList.add((Map<String, String>)urlMap.get("query"));
									ret.put(urlMap.get("url").toString(),paramList);
								}	
							}
						}
					}
				}
				
			}
			
		}
	}
	
	
}
