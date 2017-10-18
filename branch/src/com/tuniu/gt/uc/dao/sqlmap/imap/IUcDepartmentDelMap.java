package com.tuniu.gt.uc.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.uc.entity.UcDepartmentDelEntity;

@Repository("uc_dao_sqlmap-ucdepartment")
public interface IUcDepartmentDelMap extends IMapBase { 

	public void addSelectedDepartments(UcDepartmentDelEntity ucDepartmentDel);
	
	public void deleteExitDepartments();
	
	public List<UcDepartmentDelEntity> getSelectedInfo(Map<String, Object> param);
	
	public List<UcDepartmentDelEntity> getSelectedInfoFromHis(Map<String, Object> param);
	
	public void updateForColor(Map<String, Object> param);
	
	public void copyConstructsForNextMonth(UcDepartmentDelEntity ucDepartmentDelEntity);
	
	void insertBatch(List<UcDepartmentDelEntity> deptList);
	
}
