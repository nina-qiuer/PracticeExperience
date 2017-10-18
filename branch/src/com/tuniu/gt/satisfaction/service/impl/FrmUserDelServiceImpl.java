package com.tuniu.gt.satisfaction.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.satisfaction.dao.impl.FrmUserDelDao;
import com.tuniu.gt.satisfaction.entity.FrmUserDelEntity;
import com.tuniu.gt.satisfaction.service.IFrmUserDelService;

@Service("uc_service_user_impl-ucuserdel")
public class FrmUserDelServiceImpl extends ServiceBaseImpl<FrmUserDelDao> implements IFrmUserDelService {

	@Autowired
	@Qualifier("satisfaction_dao_impl-ucuserdeldao")
	public void setDao(FrmUserDelDao dao) {
		this.dao = dao;
	}

	@Override
	public void addPersonsByDept(FrmUserDelEntity frmUserDelEntity) {
		this.dao.addPersonsByDept(frmUserDelEntity);
	}
	
	@Override
	public void deleteExitPerson(){
		this.dao.deleteExitPerson();
	}

	@Override
	public void addCompletePNum(Map<String, Object> map){
		this.dao.addCompletePNum(map);
	}
	
	@Override
	public FrmUserDelEntity selectByName(Map<String, Object> map){
		return this.dao.selectByName(map);
	}
	
	@Override
	public void copyPersonForNextMonth(Map<String, Object> map){
		this.dao.copyPersonForNextMonth(map);
	}

	@Override
	public void insertBatch(List<FrmUserDelEntity> userList) {
		dao.insertBatch(userList);
	}
}
