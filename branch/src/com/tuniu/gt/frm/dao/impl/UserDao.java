package com.tuniu.gt.frm.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.frm.dao.sqlmap.imap.IUserMap;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.uc.vo.UserDepartmentVo;

@Repository("frm_dao_impl-user")
public class UserDao extends DaoBase<UserEntity, IUserMap>  implements IUserMap {
	public UserDao() {  
		tableName = Constant.appTblPreMap.get("frm") + "user";		
	}
	
	@Autowired
	@Qualifier("frm_dao_sqlmap-user")
	public void setMapper(IUserMap mapper) {
		this.mapper = mapper;
	}
	
	public List<UserEntity> getUsersByDepartmentTreeFatherId(String treeFatherId) {
		return mapper.getUsersByDepartmentTreeFatherId(treeFatherId);
	}

	@Override
	public List<UserEntity> getUsersByDepartmentId(int depId) {

		return mapper.getUsersByDepartmentId(depId);
	}

	@Override
	public List<UserEntity> getManageByDepartmentId(int depId) {
		
		return mapper.getManageByDepartmentId(depId);
	}

	@Override
	public List<UserEntity> getManageByDepartmentName(String departmentName) {
		
		return mapper.getManageByDepartmentName(departmentName);
	}

	@Override
	public UserEntity getUserByRealName(String realName) {
		return mapper.getUserByRealName(realName);
	}
	
	@Override
	public List<UserEntity> getManageByFatherDepId(int depId) {
		
		return mapper.getManageByFatherDepId(depId);
	}
	
	@Override
	public UserDepartmentVo getUserDepartmentVoByUserId(Integer userId) {
		return mapper.getUserDepartmentVoByUserId(userId);
	}
	
	public List<Map<String, Object>> getAllUsersWithoutDel() {
		return mapper.getAllUsersWithoutDel();
	}
	
	@Override
	public List<UserEntity> getSameDepUsersWithoutSelf(Integer userId) {
		return mapper.getSameDepUsersWithoutSelf(userId);
	}
}
