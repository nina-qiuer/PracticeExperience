package com.tuniu.gt.uc.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.uc.dao.impl.DepuserMapDao;
import com.tuniu.gt.uc.entity.DepuserMapEntity;
import com.tuniu.gt.uc.service.user.IDepuserMapService;
@Service("uc_service_user_impl-depuser_map")
public class DepuserMapServiceImpl extends ServiceBaseImpl<DepuserMapDao> implements IDepuserMapService {
	@Autowired
	@Qualifier("uc_dao_impl-depuser_map")
	public void setDao(DepuserMapDao dao) {
		this.dao = dao;
	}

	@Override
	public void deleteByUserId(int userId) {
		// TODO Auto-generated method stub
		dao.deleteByUserId(userId);
		
	}

	@SuppressWarnings("unchecked")
	public List<DepuserMapEntity> getDepIdByUser(int userId) {
		Map<String, Object> sqlParaMap = new HashMap<String, Object>();
		sqlParaMap.put("userId", userId);
		return (List<DepuserMapEntity>) dao.fetchList(sqlParaMap);
	};
}
