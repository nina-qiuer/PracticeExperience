package com.tuniu.gt.satisfaction.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.satisfaction.entity.FrmUserDelEntity;

public interface IFrmUserDelService extends IServiceBase {
	
	public void addPersonsByDept(FrmUserDelEntity frmUserDelEntity);
	
	public void deleteExitPerson();
	
	public void addCompletePNum(Map<String, Object> map);
	
	public FrmUserDelEntity selectByName(Map<String, Object> map);
	
	public void copyPersonForNextMonth(Map<String, Object> map);
	
	void insertBatch(List<FrmUserDelEntity> userList);
	
}
