package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.enums.AssignType;


/**
 * IAutoAssignService
 * @author zhangqingsong
 *
 */
public interface IAutoAssignService extends IServiceBase {
	
	/**
	* 根据类型获取对应类型的list
	* @param type
	* @return    
	* String    
	* @throws
	*/
	public List<AutoAssignEntity> getListByType(int type);
	
	/**
	* 根据类型获取对应类型的所有list
	* @param type
	* @return    
	* String    
	* @throws
	*/
	public List<AutoAssignEntity> getListAllByType(int type);
	
	/**
	* 根据类型获取对应类型的所有list
	* @param type
	* @return    
	* String    
	* @throws
	*/
	public List<AutoAssignEntity> getAllListByType(int type, int tourTimeNode);
	
	/**
	 * 查询投诉分单详情
	 * @param memPre
	 * @param tourTimeNode
	 * @return
	 */
	public List<AutoAssignEntity> getAssignCompInfoList(java.sql.Date statDate, int tourTimeNode,AssignType assignType);
	
	public List<AutoAssignEntity> getAssignCompInfoList(int tourTimeNode,AssignType assignType);
	
	AutoAssignEntity getByTypeAndUserId(Map<String, Object> params);
	
	List<AutoAssignEntity> getQcList();
	
	List<AutoAssignEntity> getQcInfoList();
	
	/**
	 * 取分配时间最早的质检人
	 * @param compEnt
	 * @return
	 */
	AutoAssignEntity getQcPerson(ComplaintEntity compEnt);
	
	/**
	 * 质检自动分单插入数据
	 */
	void insertQc(AutoAssignEntity entity);
	
	AutoAssignEntity getQcPerson(Integer id);
	
	void updateQc(AutoAssignEntity entity);
	
	/**
	 * 获取分单时间最早的客服或质检员
	 */
	AutoAssignEntity getDestAssignEnt(List<AutoAssignEntity> list);
	
	void updateLastAssignTime(int id);

	/**
	 * 根据投诉信息获取处理客服列表
	 * @param type
	 * @param complaint
	 * @return
	 */
	List<AutoAssignEntity> getCurrStateList(Integer type,ComplaintEntity complaint);
	
	/**
	 * 判断分单状态存在并开启
	 * @param autoAssignState
	 * @return
	 */
	Boolean assignStateIsWork(Integer userId,String dealDepart);
	
	/**
	 * 判断分单状态存在并关闭
	 * @param autoAssignState
	 * @return
	 */
	Boolean assignStateIsNotWork(Integer userId,String dealDepart);
	
	/**
	 * 根据用户和处理岗更新分配时间
	 * @param userId
	 * @param dealDepart
	 */
	void updateLastAssignTimeByUser(Integer userId,String dealDepart);
}
