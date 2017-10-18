package com.tuniu.gt.satisfaction.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.satisfaction.dao.sqlmap.imap.IFrmUserDelMap;
import com.tuniu.gt.satisfaction.entity.FrmUserDelEntity;

@Repository("satisfaction_dao_impl-ucuserdeldao")
public class FrmUserDelDao  extends DaoBase<FrmUserDelEntity, IFrmUserDelMap>  implements IFrmUserDelMap {

	public FrmUserDelDao() {  
		tableName = "uc_user_del";	
	}
	
	@Autowired
	@Qualifier("satisfaction_dao_sqlmap-ucuserdelmap")
	public void setMapper(IFrmUserDelMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public void addPersonsByDept(FrmUserDelEntity frmUserDelEntity) {
		mapper.addPersonsByDept(frmUserDelEntity);
	}

	@Override
	public void deleteExitPerson(){
		mapper.deleteExitPerson();
	}
	
	@Override
	public void addCompletePNum(Map<String, Object> map){
		mapper.addCompletePNum(map);
	}
	
	@Override
	public FrmUserDelEntity selectByName(Map<String, Object> map){
		return mapper.selectByName(map);
	}
	
	@Override
	public void copyPersonForNextMonth(Map<String, Object> map){
		mapper.copyPersonForNextMonth(map);
	}

	@Override
	public void insertBatch(List<FrmUserDelEntity> userList) {
		mapper.insertBatch(userList);
	}
}
