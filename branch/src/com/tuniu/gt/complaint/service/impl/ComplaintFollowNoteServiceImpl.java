package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tuniu.gt.complaint.dao.impl.ComplaintFollowNoteDao;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
import com.tuniu.gt.complaint.entity.FollowNoteCountEntity;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;

import tuniu.frm.core.ServiceBaseImpl;

/**
 * @ClassName: ComplaintFollowNoteServiceImpl
 * @Description:跟进记录service接口
 * @author Ocean Zhong
 * @date 2012-1-19 上午11:01:29
 * @version 1.0 Copyright by Tuniu.com
 */
@Service("complaint_service_complaint_impl-complaint_follow_note")
public class ComplaintFollowNoteServiceImpl extends
		ServiceBaseImpl<ComplaintFollowNoteDao> implements
		IComplaintFollowNoteService {
	private Logger logger = LoggerFactory.getLogger(ComplaintFollowNoteServiceImpl.class);
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_follow_note")
	public void setDao(ComplaintFollowNoteDao dao) {
		this.dao = dao;
	};

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;

	/**
	 * 根据complaintId操作记录
	 * 
	 * @param complaintId
	 * @return
	 */
	public List<ComplaintFollowNoteEntity> getNoteByComplaintId(
			String complaintId) {
		List<ComplaintFollowNoteEntity> dataList = new ArrayList();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		dataList = (List<ComplaintFollowNoteEntity>) dao.fetchList(paramMap);
		return dataList;
	}

	/**
	 * 根据complaintId跟进记录
	 * 
	 * @param complaintId
	 * @return
	 */
	public List<ComplaintFollowNoteEntity> getNoteReByComplaintId(
			String complaintId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		paramMap.put("flowNameCon", "flowNameCon");
		return (List<ComplaintFollowNoteEntity>) dao.fetchList(paramMap);
	}

	/**
	 * 有效操作添加跟进记录
	 * 
	 * @param complaintId
	 *            投诉单id
	 * @param notePeople
	 *            操作人id
	 * @param peopleName
	 *            操作人姓名
	 * @param content
	 *            操作记录及备注
	 * @return void
	 */
	public void addFollowNote(int complaintId, int notePeople,
			String peopleName, String content) {
		addFollowNote(complaintId, notePeople, peopleName, content, 0, "");
	}

	@Override
	public void addFollowNote(int complaintId, int notePeople,
			String peopleName, String content, int isSys, String flowName) {
		try{
			ComplaintFollowNoteEntity complaintFollowNoteEntity = new ComplaintFollowNoteEntity();
			List<ComplaintEntity> complaintEntityList = complaintService
					.getComplaintById(complaintId + "");
			if (!CollectionUtils.isEmpty(complaintEntityList)) {
				complaintFollowNoteEntity.setDealDept(complaintEntityList.get(0)
						.getDealDepart());
			} else {
				complaintFollowNoteEntity.setDealDept("");
			}

			complaintFollowNoteEntity.setComplaintId(complaintId);
			complaintFollowNoteEntity.setNotePeople(notePeople);
			complaintFollowNoteEntity.setPeopleName(peopleName);
			complaintFollowNoteEntity.setContent(content);
			complaintFollowNoteEntity.setDelFlag(1);
			complaintFollowNoteEntity.setIsSys(isSys);
			complaintFollowNoteEntity.setFlowName(flowName);

			this.dao.insert(complaintFollowNoteEntity);
		} catch (Exception e){
			logger.error("addFollowNote Exception:[errorMsg:"+e.getMessage()+"]", e);
		}
	}

	@Override
	public Map<String, Map<String, Integer>> getHandOverCountData(
			Map<String, Object> paramMap) {
		Map<String, Map<String, Integer>> reportData = new HashMap<String, Map<String, Integer>>();
		List<FollowNoteCountEntity> folNoteCountList = dao
				.getHandOverCountData(paramMap);
		for (FollowNoteCountEntity followNoteCountEntity : folNoteCountList) {
			String real_name = followNoteCountEntity.getPeople_name();// 用户姓名
			Integer myzero = new Integer(0);
			Map<String, Integer> folNoteCountMap=null;
			if(reportData.get(real_name)!=null){
				folNoteCountMap = reportData.get(real_name);
			}else{
				folNoteCountMap = new HashMap<String, Integer>();
			}
			folNoteCountMap.put(followNoteCountEntity.getFlow_name(),
					followNoteCountEntity.getCountNum() == null ? myzero
							: followNoteCountEntity.getCountNum());
			reportData.put(real_name, folNoteCountMap);
		}
		return reportData;
	}
	
	@Override
	public List<Integer> getHandOverComplaintIds(Map<String, Object> paramMap){
		return dao.getHandOverComplaintIds(paramMap);
	}
	
	@Override
	public void insertList(Map<String, Object> paramMap){
		dao.insertList(paramMap);
	}
}
