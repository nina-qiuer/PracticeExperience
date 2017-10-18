package com.tuniu.gt.frm.action.privilege;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope; 
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.MenuEntity;
import com.tuniu.gt.frm.service.common.Recache;
import com.tuniu.gt.frm.service.privilege.IMenuService;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("frm_action_privilege-menu")
@Scope("prototype")
public class MenuAction extends FrmBaseAction<IMenuService,MenuEntity> { 
	
	public MenuAction() {
		setManageUrl(manageUrl + "frm/action/privilege/menu");
	}
	
	
	@Autowired
	@Qualifier("frm_service_common-recache")
	private Recache recache;
	
	@Autowired
	@Qualifier("frm_service_privilege_impl-menu")
	public void setService(IMenuService service) {
		this.service = service;
	};
	
	public String execute() {
		this.setPageTitle("菜单管理");
		List<MenuEntity> menuList = (List<MenuEntity>)service.fetchList();   
		request.setAttribute("menuList", menuList);
		session.put("referer", getRequestUrl()+"?"+Common.httpBuildQuery(getRequestParam())); 
		jspTpl = "list";		
		return jspTpl;
	}
	
	
	/**
	 * 以_开头,标识为回调方法,非权限方法
	 * @return
	 */
	public void _preAdd() {
		request.setAttribute("menuFullnameMap",service.makeMenuFullname(service.getMenuFullTreeList()));
	}
	
	
	public void _preDoAdd() {
		Map<String, String> extra = new HashMap<String, String>();
		extra = service.getNextTreeIdByFatherId(entity.getFatherId());
		entity.setTreeId(extra.get("treeId"));
		entity.setTreeFatherId(extra.get("treeFatherId"));
		entity.setDepth(Integer.parseInt(extra.get("depth")));		
	}
	
	
	public void _aftDoAdd() {
		recache.menuRecache(); 
	}
	
	public void _preEdit() {
		request.setAttribute("menuFullnameMap",service.makeMenuFullname(service.getMenuFullTreeList()));
	}
	
	
	
	public void _preDoEdit() { 
		MenuEntity oldEntity = (MenuEntity)service.get(entity.getId());
		String oldTreeFatherId = oldEntity.getTreeFatherId() + oldEntity.getTreeId();
		String newTreeFatherId = oldTreeFatherId;
		
		if(oldEntity.getFatherId() != entity.getFatherId()) {
			
			Map<String, String> extra = new HashMap<String, String>();
			extra = service.getNextTreeIdByFatherId(entity.getFatherId());
			entity.setTreeId(extra.get("treeId"));
			entity.setTreeFatherId(extra.get("treeFatherId"));
			entity.setDepth(Integer.parseInt(extra.get("depth"))); 
			
			request.setAttribute("entity.treeId", extra.get("treeId"));
			request.setAttribute("entity.treeFatherId", extra.get("treeFatherId"));
			request.setAttribute("entity.depth", extra.get("depth"));
			
			newTreeFatherId = extra.get("treeFatherId") + extra.get("treeId");
			
		}  
		//lua 的思想,与c/c++ 通过栈交互
		callbackParam.clear();
		
		callbackParam.put("oldTreeFatherId", oldTreeFatherId);
		callbackParam.put("newTreeFatherId", newTreeFatherId);
		callbackMap.put("aft", "_aftDoEdit"); 
	}
	
	
	public void _aftDoEdit(String oldTreeFatherId,String newTreeFatherId) {
		service.updateChildsTreeFatherId(oldTreeFatherId, newTreeFatherId);
		recache.menuRecache(); 
	}
	
	
	
}
