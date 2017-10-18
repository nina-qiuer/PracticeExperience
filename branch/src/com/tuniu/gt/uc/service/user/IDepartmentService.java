package com.tuniu.gt.uc.service.user;

import java.util.List;
import java.util.Map;
import java.util.Set;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.uc.datastructure.DepartUsersTree;
import com.tuniu.gt.uc.datastructure.TreeNode;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.entity.UcDepartmentEntity;
import com.tuniu.gt.uc.vo.UserDepartmentVo;

public interface IDepartmentService extends IServiceBase {
	
	public Map<String, String> getNextTreeIdByFatherId(int fatherId);
	
	public DepartmentEntity getByFatherId(int fatherId);
	
	public void updateChildsTreeFatherId(String oldTreeFatherId,String newTreeFatherId);
	
	public void doRestore(int id); 
	
	public DepartmentEntity getDepartmentById(int id);
	
	public Set<DepartmentEntity> getAllDepartmentByFatherId(int id);
	
	public List<UcDepartmentEntity> doSearchConstruts();
	
	public List<DepartmentEntity> doBackConstruts(Map<String ,Object> map);
	/**
	 * 获取二级部门Id
	 * 
	 * */
	public int getSecondDepartmentId(int depId);
	
	/**
	 * 获取二级部门全名
	 * @param depId
	 * @return
	 */
	String getSecDeptFullName(int depId);
	
	/**
	 * 根据一级部门名称和二级部门名称获得二级部门ID
	 */
	int getSecondaryDepId(Map<String, Object> map);
	
	
	/**
	 * 自动分单中批量变更质检事业部配置
	 * @param param
	 */
	void batchUpdateUseDeptForQc(Map<String,Object> param);

    /**
     * 根据根部门id创建深度为depth的部门树
     * @param rootDepartmentId
     * @param depth
     */
	DepartUsersTree buildDepartUsersTree(Integer rootDepartmentId, Integer depth);

    /**
     * @param vo
     */
    public void fillUserDepartmentVo(UserDepartmentVo vo);
    
    /**
	 * 根据部门id查询部门、人员列表(理论赔付)
	 * @param paramMap
	 * @return
	 */
    DepartUsersTree buildPayoutReportTree(Map<String, Object> paramMap);

    /**
	 * 根据部门id查询部门、人员列表
	 * @param paramMap
	 * @return
	 */
	DepartUsersTree buildDepartUsersTree(Map<String, Object> paramMap);
	
	/**
	 * 根据部门id加载该部门下所有的部门
	 * @return
	 */
	List<TreeNode<DepartmentEntity>> getDepartTreeByDeptId(Integer departmentId);

	/**
	 * 根据部门id查询到
	 * @param depId
	 * @return
	 */
	Integer getBussinessIdByUserId(Integer userId);
}
