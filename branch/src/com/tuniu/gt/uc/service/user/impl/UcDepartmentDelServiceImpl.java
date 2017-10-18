package com.tuniu.gt.uc.service.user.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.uc.dao.impl.UcDepartmentDao;
import com.tuniu.gt.uc.entity.UcDepartmentDelEntity;
import com.tuniu.gt.uc.service.user.IUcDepartmentDelService;
@Service("uc_service_user_impl-ucdepartment")
public class UcDepartmentDelServiceImpl extends ServiceBaseImpl<UcDepartmentDao> implements IUcDepartmentDelService {
	@Autowired
	@Qualifier("uc_dao_impl-ucdepartment")
	public void setDao(UcDepartmentDao dao) {
		this.dao = dao;
	}

	@Override
	public void addSelectedDepartments(UcDepartmentDelEntity ucDepartmentDel){
		dao.addSelectedDepartments(ucDepartmentDel);
	}
	
	@Override
	public void deleteExitDepartments(){
		dao.deleteExitDepartments();
	}
	
	@Override
	public List<UcDepartmentDelEntity> getSelectedInfo(Map<String, Object> param){
		return dao.getSelectedInfo(param);
	}
	
	@Override
	public List<UcDepartmentDelEntity> getSelectedInfoFromHis(Map<String, Object> param){
		return dao.getSelectedInfoFromHis(param);
	}
	
	@Override
	public void updateForColor(Map<String, Object> param){
		dao.updateForColor(param);
	}
	
	@Override
	public void copyConstructsForNextMonth(UcDepartmentDelEntity ucDepartmentDelEntity){
		dao.copyConstructsForNextMonth(ucDepartmentDelEntity);
	}

	@Override
	public void insertBatch(List<UcDepartmentDelEntity> deptList) {
		dao.insertBatch(deptList);
	}
}
