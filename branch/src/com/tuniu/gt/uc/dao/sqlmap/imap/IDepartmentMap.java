package com.tuniu.gt.uc.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.returnvisit.entity.PostSaleReturnVisitEntity;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.entity.UcDepartmentEntity;

@Repository("uc_dao_sqlmap-department")
public interface IDepartmentMap extends IMapBase { 

	public  void updateChildsTreeFatherId(Map<String, Object> paramMap);
	public List<Map<String, Object>> fetchListOrg(Map<String, String> paramMap);
	public void doRestore(Map<String, Object> paramMap);
	
	/**
	 * 获取二级部门全名
	 * @param depId
	 * @return
	 */
	String getSecDeptFullName(int depId);
	
	/**
	 * 根据一级部门名称和二级部门名称获得二级部门ID
	 */
	Integer getSecondaryDepId(Map<String, Object> map);
	
	public List<UcDepartmentEntity> doSearchConstruts();
	
	public List<DepartmentEntity> doBackConstruts(Map<String ,Object> map);
	
	/**
	 * 自动分单中批量变更质检事业部配置
	 * @param param
	 */
	void batchUpdateUseDeptForQc(Map<String,Object> param);
	
	/**
	 * 查询所有qc使用的事业部id
	 * @return qc使用的事业部id集合
	 */
	List<Integer> getAllDepIdsForQc();
	
	/**
	 * 根据用户真是姓名获取相关全部组织架构信息并封装到售后回访实体里
	 * @param realName
	 * @return
	 */
	PostSaleReturnVisitEntity getFullDepartmentsByUserRealName(String realName);
	
	/**
	 * 根据部门id查询部门、人员列表
	 * @param paramMap
	 * @return
	 */
	List<DepartmentEntity> getUserAndDeptList(Map<String, Object> paramMap);
	
	/**
	 * 根据条件查询部门列表
	 * @param paramMap
	 * @return
	 */
	List<DepartmentEntity> getDepartmentsByTreeFatherId(Map<String, Object> paramMap);
	
	/**
	 * 根据部门id查询到
	 * @param depId
	 * @return
	 */
	Integer getBussinessIdByUserId(Integer userId);
}
