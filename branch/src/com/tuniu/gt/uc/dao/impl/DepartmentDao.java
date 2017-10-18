package com.tuniu.gt.uc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tuniu.gt.returnvisit.entity.PostSaleReturnVisitEntity;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.entity.UcDepartmentEntity;
import com.tuniu.gt.uc.dao.sqlmap.imap.IDepartmentMap; 

import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("uc_dao_impl-department")
public class DepartmentDao extends DaoBase<DepartmentEntity, IDepartmentMap> {
	public DepartmentDao() {  
		tableName = Constant.appTblPreMap.get("uc") + "department";		
	}
	
	@Autowired
	@Qualifier("uc_dao_sqlmap-department")
	public void setMapper(IDepartmentMap mapper) {
		this.mapper = mapper;
	}
	
	public void updateChildsTreeFatherId(Map<String, Object> paramMap) {
		paramMap.put("table", tableName);
		mapper.updateChildsTreeFatherId(paramMap);
	}
	
	/**
	 * 返回原始数据
	 * @return
	 */
	public List<Map<String, Object>> fetchListOrg() {
		Map<String, String> paramMap = new HashMap<String, String>();
		return mapper.fetchListOrg(paramMap);  
	}


	public List<Map<String, Object>> fetchListOrg(Map<String, String> paramMap) {
		paramMap.put("table", tableName);
		return mapper.fetchListOrg(paramMap);
	}
	
	public void doRestore(int id){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("table", tableName);
		paramMap.put("id", id);
		mapper.doRestore(paramMap);
	}
	
	public String getSecDeptFullName(int depId) {
		return mapper.getSecDeptFullName(depId);
	}
	
	public int getSecondaryDepId(Map<String, Object> map) {
		return (null == mapper.getSecondaryDepId(map))? 0 : mapper.getSecondaryDepId(map);
	}
	
	public List<UcDepartmentEntity> doSearchConstruts(){
		return mapper.doSearchConstruts();
	}
	
	public List<DepartmentEntity> doBackConstruts(Map<String ,Object> map){
		return mapper.doBackConstruts(map);
	}
	
	
	/**
	 * 自动分单中批量变更质检事业部配置
	 * @param param
	 */
	public void batchUpdateUseDeptForQc(Map<String,Object> param)
	{
		mapper.batchUpdateUseDeptForQc(param);
	}
	
	/**
	 * 查询所有qc使用的事业部id
	 * @return qc使用的事业部id集合
	 */
	public List<Integer> getAllDepIdsForQc()
	{
		return mapper.getAllDepIdsForQc();
	}
	
	public PostSaleReturnVisitEntity getFullDepartmentsByUserRealName(String realName) {
	    return mapper.getFullDepartmentsByUserRealName(realName);
	}
	
	/**
	 * 根据部门id查询部门、人员列表
	 * @param paramMap
	 * @return
	 */
	public List<DepartmentEntity> getUserAndDeptList(Map<String, Object> paramMap){
		return mapper.getUserAndDeptList(paramMap);
	}
	
	public List<DepartmentEntity> getDepartmentsByTreeFatherId(Map<String, Object> paramMap){
		return mapper.getDepartmentsByTreeFatherId(paramMap);
	}
	
	public Integer getBussinessIdByUserId(Integer userId){
		return mapper.getBussinessIdByUserId(userId);
	}
}
