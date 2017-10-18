package com.tuniu.gt.satisfaction.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.satisfaction.entity.FrmUserDelEntity;

import tuniu.frm.core.IMapBase;

@Repository("satisfaction_dao_sqlmap-ucuserdelmap")
public interface IFrmUserDelMap extends IMapBase  {
	
	public void addPersonsByDept(FrmUserDelEntity frmUserDelEntity);
	
	public void deleteExitPerson();
	
	public void addCompletePNum(Map<String, Object> map);
	
	public FrmUserDelEntity selectByName(Map<String, Object> map);
	
	public void copyPersonForNextMonth(Map<String,Object> map);
	
	void insertBatch(List<FrmUserDelEntity> userList);
	
}
