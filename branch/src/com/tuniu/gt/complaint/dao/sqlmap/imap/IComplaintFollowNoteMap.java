package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.FollowNoteCountEntity;

@Repository("complaint_dao_sqlmap-complaint_follow_note")
public interface IComplaintFollowNoteMap extends IMapBase { 

	/**
	 * 获取交接报表数据
	 * @param paHashMap
	 * @return
	 */
	List<FollowNoteCountEntity> getHandOverCountData(Map<String, Object> paramMap);

	/**
	 * 获取交接投诉单号
	 * @param paramMap
	 * @return
	 */
	List<Integer> getHandOverComplaintIds(Map<String, Object> paramMap);

	/**
	 * 批量插入数据
	 * @param paramMap
	 */
	void insertList(Map<String, Object> paramMap);
}
