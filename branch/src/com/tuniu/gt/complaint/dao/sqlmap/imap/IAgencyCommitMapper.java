package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.AgencyDetailEntity;
import com.tuniu.gt.complaint.entity.AgencyToChatEntity;
import com.tuniu.gt.complaint.entity.AttachEntity;

@Repository
public interface IAgencyCommitMapper {

	/**
	 * 查询供应商沟通聊天详细记录
	 * @param map
	 * @return
	 */
	List<AgencyToChatEntity> queryChatDetail(Map<String, Object> map);
	
	/**
	 * 获取最新一条途牛回复
	 * @param map
	 * @return
	 */
	AgencyToChatEntity queryMaxChat(Map<String, Object> map);
	
	/**
	 * 新增供应商沟通 /追加沟通
	 * @param map
	 */
	void saveCommitDetail(Map<String, Object> map);
	
	/**
	 * 查询投诉与咨询详情
	 * @return
	 */
	AgencyDetailEntity queryComplaintDetail(Map<String, Object> map);
	
	/**
	 * 上传附件
	 * @param attach
	 * @return
	 */
	void uploadFile(AttachEntity attach);
	
	/**
	 * 查询附件
	 * @param roomId
	 * @return
	 */
	AttachEntity queryFile(String roomId);
	
	/**
	 * 根据用户id查询用户名称
	 * @param deal
	 * @return
	 */
	String queryUserName(int deal);
}
