package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.impl.AppointManagerDao;
import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.enums.AppointManagerEnum;
import com.tuniu.gt.complaint.service.IAppointManagerService;

import tuniu.frm.core.ServiceBaseImpl;

/**
* @ClassName: AppointManagerServiceImpl
* @Description:接口实现
* @author Ocean Zhong
* @date 2012-1-29 下午2:53:14
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_service_complaint_impl-appoint_manager")
public class AppointManagerServiceImpl extends ServiceBaseImpl<AppointManagerDao> implements IAppointManagerService {
	@Autowired
	@Qualifier("complaint_dao_impl-appoint_manager")
	public void setDao(AppointManagerDao dao) {
		this.dao = dao;
	}

	@Override
	public String getDepartmentName(int departmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserName(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppointManagerEntity> getListByType(int type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		return (List<AppointManagerEntity>) this.fetchList(paramMap);
	}

	@Override
	public boolean isQcOfficer(int userId) {
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("type", AppointManagerEntity.QC_OFFICER);
		sqlParams.put("userId", userId);
		@SuppressWarnings("unchecked")
		List<AppointManagerEntity> qcOfficers = (List<AppointManagerEntity>) fetchList(sqlParams);
		
		return (qcOfficers!=null && qcOfficers.size()>0) ? true : false; 
	}
	
	@Override
	public boolean isCtOfficer(int userId) {
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("userId", userId);
		@SuppressWarnings("unchecked")
		List<AppointManagerEntity> ctOfficers = (List<AppointManagerEntity>) fetchList(sqlParams);//取得该人员对应的所有基础配置权限
		
		boolean isOfficer = false ;
		for (AppointManagerEntity appointManagerEntity : ctOfficers) {
			int type = appointManagerEntity.getType();
			if (type == AppointManagerEntity.TOURING_OFFICER || type == AppointManagerEntity.PRE_SALES_OFFICER 
					|| type == AppointManagerEntity.POST_SALES_OFFICER || type == AppointManagerEntity.SPECIAL_BEFORE_OFFICER 
					|| type == AppointManagerEntity.AIR_TICKIT_OFFICER || type==AppointManagerEntity.HOTEL_OFFICER
					|| type == AppointManagerEntity.DISTRIBUTE_OFFICER || type == AppointManagerEntity.TRAFFIC_OFFICER) {
				isOfficer = true ;
				break;
			}
		}
		return isOfficer;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getUserOfficerTypes(int userId) {
		List<Integer> typeList = new ArrayList<Integer>();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("userId", userId);
		List<AppointManagerEntity> ctOfficers = (List<AppointManagerEntity>) fetchList(sqlParams);//取得该人员对应的所有基础配置权限
		for (AppointManagerEntity appointManagerEntity : ctOfficers) {
			typeList.add(appointManagerEntity.getType());
		}
		return typeList; 
	}

    @Override
    public boolean isMemberOfType(int userId, int type) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("type", type);
        return dao.count(paramMap)>0;
    }
	
    @Override
	public List<AppointManagerEntity> getVipDepartGroupList(int type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		return dao.getVipDepartGroupList(paramMap);
	}
    
    @Override
    public List<String> getDealDeaprtSetByUserId(Integer userId){
    	List<Integer> userOfficeTypes = getUserOfficerTypes(userId);
		return AppointManagerEnum.getValueList(userOfficeTypes);
    }
}
