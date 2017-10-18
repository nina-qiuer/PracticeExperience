package com.tuniu.gt.frm.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.uc.vo.UserDepartmentVo;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("frm_dao_sqlmap-user")
public interface IUserMap extends IMapBase { 

	List<UserEntity> getUsersByDepartmentTreeFatherId(String treeFatherId);
	
	List<UserEntity> getUsersByDepartmentId(int depId);
	
	List<UserEntity> getManageByDepartmentId(int depId);
	
	List<UserEntity> getManageByDepartmentName(String departmentName);
	
	UserEntity getUserByRealName(String realName);
	
	List<UserEntity> getManageByFatherDepId(int depId);
	
	UserDepartmentVo getUserDepartmentVoByUserId(Integer userId);

	List<Map<String, Object>> getAllUsersWithoutDel();

	List<UserEntity> getSameDepUsersWithoutSelf(Integer userId);
}
