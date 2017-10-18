package com.tuniu.gt.frm.service.privilege;

import java.util.List;
import com.tuniu.gt.frm.entity.MenuPrivilegeEntity;

import tuniu.frm.core.IServiceBase;
public interface IMenuPrivilegeService extends IServiceBase  {
	public List<MenuPrivilegeEntity> getListByMenuId(Integer menuId);
		
	public Integer doAdd(Object e);

}
