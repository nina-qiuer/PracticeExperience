package com.tuniu.gt.complaint.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IEmployeeShareMap;
import com.tuniu.gt.complaint.entity.EmployeeShareEntity;
import com.tuniu.gt.frm.dao.impl.UserDao;
import com.tuniu.gt.frm.entity.UserEntity;

@Repository("complaint_dao_impl-employee_share")
public class EmployeeShareDao extends DaoBase<EmployeeShareEntity, IEmployeeShareMap>  implements IEmployeeShareMap {
	
	public EmployeeShareDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "employee_share";		
	}
	
	@Autowired
	@Qualifier("frm_dao_impl-user")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-employee_share")
	public void setMapper(IEmployeeShareMap mapper) {
		this.mapper = mapper;
	}
	
	public void insertList(Integer shareId, List<EmployeeShareEntity> list) {
		for (EmployeeShareEntity entity : list) {
			entity.setShareId(shareId);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("realName", entity.getName());
			UserEntity user = (UserEntity) userDao.fetchOne(paramMap);
			if (user != null) {
				entity.setUserId(user.getId());
			}
			super.insert(entity);
		}
	}

	@Override
	public void deleteByShareId(Integer shareId) {
		mapper.deleteByShareId(shareId);
	}
	
}
