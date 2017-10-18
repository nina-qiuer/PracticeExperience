package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.AgencyDetailEntity;
import com.tuniu.gt.complaint.entity.AgencyToChatEntity;
import com.tuniu.gt.complaint.entity.AttachEntity;

public interface IAgencyCommitService {

	/**
	 * 查询供应商沟通聊天详细记录
	 * @param map
	 * @return
	 */
	public List<AgencyToChatEntity> queryChatDetail(Map<String, Object> map);
	
	/**
	 * 获取最新一条途牛回复
	 * @param map
	 * @return
	 */
	public  AgencyToChatEntity queryMaxChat(Map<String, Object> map);
	
	/**
	 * 新增供应商沟通记录
	 * @param map
	 */
	public String saveCommitDetail(Map<String, Object> map);
	
	/**
	 * 追加沟通记录
	 * @param map
	 */
	public String addCommit(Map<String, Object> map);
	
	/**
	 * 查询投诉与咨询详情
	 * @param map
	 * @return
	 */
	public AgencyDetailEntity queryComplaintDetail(Map<String, Object> map);
	
	/**
	 * 根据处理人获取处理人名称
	 * @param deal
	 * @return
	 */
	public String queryUserName(int  deal);
}
