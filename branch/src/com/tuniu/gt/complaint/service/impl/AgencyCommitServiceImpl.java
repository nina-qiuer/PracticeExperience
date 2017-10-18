package com.tuniu.gt.complaint.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IAgencyCommitMapper;
import com.tuniu.gt.complaint.entity.AgencyDetailEntity;
import com.tuniu.gt.complaint.entity.AgencyToChatEntity;
import com.tuniu.gt.complaint.service.IAgencyCommitService;

@Service("agencyCommitService")
public class AgencyCommitServiceImpl implements IAgencyCommitService{

	private static Logger logger = Logger.getLogger(AgencyCommitServiceImpl.class);

	@Autowired
	private IAgencyCommitMapper agencyCommitMapper;
	/**
	 *  查询供应商沟通聊天详细记录
	 */
	@Override
	public List<AgencyToChatEntity> queryChatDetail(Map<String, Object> map) {
		
		
		return agencyCommitMapper.queryChatDetail(map);
	}
	
	/**
	 * 获取最新一条途牛回复
	 */
	@Override
	public AgencyToChatEntity queryMaxChat(Map<String, Object> map) {
		
		
		return agencyCommitMapper.queryMaxChat(map);
	}
	
	
	
     /**
      * 新增供应商沟通
      */
	@Override
	public String saveCommitDetail(Map<String, Object> map) {
		
		try {
			
			agencyCommitMapper.saveCommitDetail(map);
			return "0";
		} catch (Exception e) {
			
			logger.info(e.getMessage()+"新增供应商沟通失败");
			return "1";
		}
		
		
		
	}

	/**
	 * 追加沟通记录/修改沟通类型
	 */
	@Override
	public String addCommit(Map<String, Object> map) {
		
		try {
			
			agencyCommitMapper.saveCommitDetail(map);
			return "0";
			
		} catch (Exception e) {
			
			logger.info(e.getMessage());
			return "1";
		}
		
		
	}

	@Override
	public AgencyDetailEntity queryComplaintDetail(Map<String, Object> map) {
		
		
		return agencyCommitMapper.queryComplaintDetail(map);
	}

	@Override
	public String queryUserName(int deal) {
		
		
		return agencyCommitMapper.queryUserName(deal);
	}

	
	
	
}
