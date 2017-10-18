package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IAutoAssignMap;
import com.tuniu.gt.complaint.entity.AutoAssignCfgQcEntity;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;

@Repository("complaint_dao_impl-auto_assign")
public class AutoAssignDao extends DaoBase<AutoAssignEntity, IAutoAssignMap>  implements IAutoAssignMap {
	public AutoAssignDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "auto_assign";
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-auto_assign")
	public void setMapper(IAutoAssignMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public AutoAssignEntity getByTypeAndUserId(Map<String, Object> params) {
		return mapper.getByTypeAndUserId(params);
	}
	
	public void insertCfgQc(AutoAssignCfgQcEntity cfgEnt) {
		mapper.insertCfgQc(cfgEnt);
	}

	@Override
	public List<AutoAssignCfgQcEntity> getCfgQcList(Map<String, Object> params) {
		return mapper.getCfgQcList(params);
	}

	@Override
	public List<AutoAssignEntity> getQcPersonList(Map<String, Object> params) {
		return mapper.getQcPersonList(params);
	}

	@Override
	public void deleteCfgQc(int assignId) {
		mapper.deleteCfgQc(assignId);
	}

	@Override
	public void updateLastAssignTime(int id) {
		mapper.updateLastAssignTime(id);
	}

	@Override
	public void updateLastAssignTimeByUser(Map<String, Object> paramMap) {
		mapper.updateLastAssignTimeByUser(paramMap);
	}
	
}
