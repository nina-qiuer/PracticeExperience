package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
public interface IComplaintFollowNoteService extends IServiceBase {
	
	public List<ComplaintFollowNoteEntity> getNoteByComplaintId(String complaintId);
	
	public void addFollowNote(int complaintId, int notePeople, String peopleName, String content);
	
	public void addFollowNote(int complaintId, int notePeople, String peopleName, String content , int isSys , String flowName);
	
	public List<ComplaintFollowNoteEntity> getNoteReByComplaintId(String complaintId);
	
	/**
	 * 获取交接报表数据
	 * @param paramMap
	 * @return
	 */
	Map<String, Map<String, Integer>> getHandOverCountData(Map<String, Object> paramMap);
	
	/**
	 * 获取交接投诉单号
	 * @param paramMap
	 * @return
	 */
	List<Integer> getHandOverComplaintIds(Map<String, Object> paramMap);

	/**
	 * 
	 * @param paramMap
	 */
	void insertList(Map<String, Object> paramMap);
}
