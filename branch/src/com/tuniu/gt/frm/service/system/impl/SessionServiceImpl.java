package com.tuniu.gt.frm.service.system.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.frm.dao.impl.SessionDao;
import com.tuniu.gt.frm.service.system.ISessionService;
@Service("frm_service_system_impl-session")
public class SessionServiceImpl extends ServiceBaseImpl<SessionDao> implements ISessionService {
	@Autowired
	@Qualifier("frm_dao_impl-session")
	public void setDao(SessionDao dao) {
		this.dao = dao;
	}

	@Override
	public int deleteByUid(int uid) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", uid);
		return dao.delete(paramMap);
	};
}
