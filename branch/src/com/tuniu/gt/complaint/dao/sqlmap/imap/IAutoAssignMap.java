package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.AutoAssignCfgQcEntity;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;

@Repository("complaint_dao_sqlmap-auto_assign")
public interface IAutoAssignMap extends IMapBase {
	
	AutoAssignEntity getByTypeAndUserId(Map<String, Object> params);
	
	void insertCfgQc(AutoAssignCfgQcEntity cfgEnt);
	
	List<AutoAssignCfgQcEntity> getCfgQcList(Map<String, Object> params);
	
	List<AutoAssignEntity> getQcPersonList(Map<String, Object> params);
	
	void deleteCfgQc(int assignId);
	
	void updateLastAssignTime(int id);

	void updateLastAssignTimeByUser(Map<String, Object> paramMap);
}
