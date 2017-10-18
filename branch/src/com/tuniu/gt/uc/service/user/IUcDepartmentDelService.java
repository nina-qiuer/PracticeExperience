package com.tuniu.gt.uc.service.user;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.uc.entity.UcDepartmentDelEntity;

public interface IUcDepartmentDelService extends IServiceBase {
	
	public void addSelectedDepartments(UcDepartmentDelEntity ucDepartmentDel);
	
	public void deleteExitDepartments();
	
	public List<UcDepartmentDelEntity> getSelectedInfo(Map<String, Object> param);
	
	public List<UcDepartmentDelEntity> getSelectedInfoFromHis(Map<String, Object> param);
	
	public void updateForColor(Map<String, Object> param);
	
	public void copyConstructsForNextMonth(UcDepartmentDelEntity ucDepartmentDelEntity);
	
	void insertBatch(List<UcDepartmentDelEntity> deptList);
	
}
