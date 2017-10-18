package com.tuniu.gt.frm.service.privilege;

import java.util.List;
import java.util.Map;
import com.tuniu.gt.frm.entity.MenuEntity;

import tuniu.frm.core.IServiceBase;
public interface IMenuService extends IServiceBase   {
	public Map<String, Object> getMenuFullTreeList();
	
	
	public List<MenuEntity> fetchlistAndPrivilege();
	public List<MenuEntity> fetchlistAndPrivilege(String menuIds,String privilegeIds);
	
	public List<MenuEntity> getMenuList();
	
	
	/** 对树形菜单进行排序
	 * @return Map with full_tree_id
	 */
	public Map<String, Object> orderTree(Map<String, Object> menuList);
	
	
	
	

	/**
	 * 获取到全文字
	 * @param menuList
	 * @return 返回menu_id 与含有父结点名称的 全名称
	 */
	public Map<String, String> makeMenuFullname(Map<String, Object> menuList);
	
	
	public Map<String, String> getNextTreeIdByFatherId(int fatherId);
	
	
	public MenuEntity getByFatherId(int fatherId);
	
	
	public void updateChildsTreeFatherId(String oldTreeFatherId,String newTreeFatherId);
	
}
