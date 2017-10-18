package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.AppointManagerEntity;

import tuniu.frm.core.IServiceBase;
/**
* @ClassName: IAppointManagerService
* @Description:分配负责人service接口
* @author Ocean Zhong
* @date 2012-1-29 下午2:40:41
* @version 1.0
* Copyright by Tuniu.com
*/
public interface IAppointManagerService extends IServiceBase {
	
	/**
	 * 根据用户id判断是否为质检负责人
	 * @param userId user id
	 * @return 如果是质检负责人返回true，否则返回false
	 */
	public boolean isQcOfficer(int userId);
	
	/**
	 * 根据用户id判断是否为投诉负责人
	 * @param userId user id
	 * @return 如果是质检负责人返回true，否则返回false
	 */
	public boolean isCtOfficer(int userId);
	/**
	 * 根据用户id返回用户负责的部门TYPE
	 * @param userId user id
	 * @return Map
	 */
	public List<Integer> getUserOfficerTypes(int userId);
	/**
	* 根据部门id取部门名称
	* @param departmentId
	* @return    
	* String    
	* @throws
	*/
	public String getDepartmentName(int departmentId);
	
	/**
	* 根据用户id去用户名
	* @param userId
	* @return    
	* String    
	* @throws
	*/
	public String getUserName(int userId);
	
	/**
	* 根据类型获取对应类型的list
	* @param type
	* @return    
	* String    
	* @throws
	*/
	public List<AppointManagerEntity> getListByType(int type);
	
	/**
	 * 根据用户id判断是否为type类型配置中的一员
	 * @param userId
	 * @param type
	 * @return
	 */
	boolean isMemberOfType(int userId, int type);

	/**
	 * 获取会员维护人三级组配置
	 * @param type
	 * @return
	 */
	List<AppointManagerEntity> getVipDepartGroupList(int type);

	/**
	 * 根据用户uid获取所负责处理岗
	 * @return
	 */
	List<String> getDealDeaprtSetByUserId(Integer userId);
}
