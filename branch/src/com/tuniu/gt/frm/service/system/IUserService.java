package com.tuniu.gt.frm.service.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.vo.UserDepartmentVo;

import tuniu.frm.core.IServiceBase;
public interface IUserService extends IServiceBase  {
	public UserEntity getUserBySessionid(String sid,Integer uid,String pwd);
	
	boolean login(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 获取用户权限
	 * @param userEntity
	 * @return Map key 为 url list 为中的map为参数 
	 */
	public  Map<String,List<Map<String, String>>> loadUserPrivileges(UserEntity userEntity);
	
	public List<Map<String, Object>> loadAllUser();
	
	/**
	 * Get users by job id.
	 * @param jobId job id.
	 * @return a list of users.
	 */
	public List<UserEntity> getUsersByJob(int jobId);
	
	/**Get users by job ids.
	 * @param jobIds job ids.
	 * @return a list of users.
	 */
	public List<UserEntity> getUsersByJob(String jobIds);
	
	/**
	 * Get users by department id
	 * @param departmentId department id
	 * @return a list of users
	 */
	public List<UserEntity> getUsersByDepartmentId(int departmentId);
	
	/**
	 * 根据当前用户获取同组人员列表
	 * @param user 用户
	 * @return 同组人员列表
	 */
	public List<UserEntity> getSameGroupUsers(UserEntity user);
	/**
	 * 根据当前用户获取同组人员列表，考虑到兼职的情况
	 * @param user 用户
	 * @return 同组人员列表
	 */
	public List<UserEntity> getAllSameGroupUsers(UserEntity user);

	/**
	 * get users
	 * @param String columns,String positionId
	 * @return a list of users.
	 */
	public List<UserEntity> getUsers(String columns,String positionId);
	
	/**
	 * 根据crm cookie中取得的用户id
	 * @return
	 */
	public UserEntity getUserByCrmCookieUid(int uid, String pwd);
	
	/**
	 * 通过id获取用户
	 * @param id id
	 * @return 用户
	 */
	public UserEntity getUserByID(int id);
	
    /**
    * 判断添加的员工承担责任人是否存在，若存在，返回1，不存在返回0。
    * @param name
    * @return
    */
    public int checkEmployInfo(String name);

    /**
     * 由realname得到用户
     * @param name
     * @return
     */
    public UserEntity getUserByName(String name);
    
    /**
     * 由realname得到用户 赔款专用
     * @param name
     * @return
     */
    public UserEntity getUserByRealName(String name);
    
    /**
   	 * 获取部门信息配置的部门所有人员列表
   	 */
   	public List<UserEntity> getComplaintDepUsers();
   	
   	
   	
   	public List<UserEntity> getUsersOnlyByDepartmentId(int departmentId);
   	/**
   	 * 取得出游中借调人员
   	 * add by wanghaijun 2012-09-24
   	 * @return
   	 */
   	public List<UserEntity> selectTransferUsers(int type);
   	
   	/**
   	 * 获取部门负责人
   	 * @param custId
   	 * @return
   	 */
   	public List<UserEntity> getManageByDepartmentId(int departmentId);
   	
	/**
   	 * 获取客服总监副总监
   	 * @param custId
   	 * @return
   	 */
   	public List<UserEntity> getCustomerDirectorByCustomerName(String name);
   	
   	/**
   	 * 获取总经理
   	 */
   	UserEntity getDeptGeneralManager(int deptId); 
   	
   	/**
   	 * 获取副总经理
   	 * @param custId
   	 * @return
   	 */
   	public List<UserEntity> getDeputyManagerByProducterName(String name);
   	
   	/**
   	 * 获取部门负责人
   	 */
   	public List<UserEntity> getManageByDepartmentName(String departmentName);
   	
   	/**
   	 * 获取员工所在的N级部门
   	 */
   	DepartmentEntity getBusinessDepartment(UserEntity userEntity, int depth);
   	
   	List<UserEntity> getCmpUsers();
   	
  	List<UserEntity> getCmpUnUsers();
  	
  	List<UserEntity> getCmpAllUsers();
   	
   	List<UserEntity> getSameDeptUsers(Integer deptId);

    /**
     * 通过员工中文名获取该员工基本信息及员工组织架构相关信息
     * @param realName
     * @return
     */
    public UserDepartmentVo getUserDepartmentVoByRealName(String realName);
   	
    /**
   	 * 通过父级部门id获取部门负责人
   	 * @param custId
   	 * @return
   	 */
   	public List<UserEntity> getManageByFatherDepId(int departmentId);
   	
   	/**
     * 通过员工id获取该员工基本信息及员工组织架构相关信息
     * @param userId
     * @return
     */
    public UserDepartmentVo getUserDepartmentVoByUserId(Integer userId);
    
    /**
     * 用户列表
     * @return
     */
	public List<Map<String, Object>> getUserNamesInJSON();

	/**
     * 根据人员id获取同组除去自己的人员
     * @return
     */
	List<UserEntity> getSameDepUsersWithoutSelfByUserId(Integer userId);

	/**
	 * 根据id获取分机号
	 * @param id
	 * @return
	 */
	String getExtensionByUserId(int id);
}
