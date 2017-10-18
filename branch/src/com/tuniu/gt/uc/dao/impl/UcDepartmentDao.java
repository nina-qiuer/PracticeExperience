package com.tuniu.gt.uc.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.uc.dao.sqlmap.imap.IUcDepartmentDelMap;
import com.tuniu.gt.uc.entity.UcDepartmentDelEntity;

@Repository("uc_dao_impl-ucdepartment")
public class UcDepartmentDao extends DaoBase<UcDepartmentDelEntity, IUcDepartmentDelMap> implements IUcDepartmentDelMap {
	public UcDepartmentDao() {  
		tableName = Constant.appTblPreMap.get("uc") + "department_del";		
	}
	
	@Autowired
	@Qualifier("uc_dao_sqlmap-ucdepartment")
	public void setMapper(IUcDepartmentDelMap mapper) {
		this.mapper = mapper;
	}
	public void addSelectedDepartments(UcDepartmentDelEntity ucDepartmentDel){
		mapper.addSelectedDepartments(ucDepartmentDel);
	}
	
	public void deleteExitDepartments(){
		mapper.deleteExitDepartments();
	}
	
	public List<UcDepartmentDelEntity> getSelectedInfo(Map<String, Object> param){
		return mapper.getSelectedInfo(param);
	}
	
	public List<UcDepartmentDelEntity> getSelectedInfoFromHis(Map<String, Object> param) {
		return mapper.getSelectedInfoFromHis(param);
	}
	
	public void updateForColor(Map<String, Object> param){
		mapper.updateForColor(param);
	}
	
	public void copyConstructsForNextMonth(UcDepartmentDelEntity ucDepartmentDelEntity){
		mapper.copyConstructsForNextMonth(ucDepartmentDelEntity);
	}

	@Override
	public void insertBatch(List<UcDepartmentDelEntity> deptList) {
		mapper.insertBatch(deptList);
	}
	
}
