package com.tuniu.gt.score.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.score.util.ScoreRecordPage;

public interface ScoreRecordService extends IServiceBase {
	
	void getScoreRecordPage(ScoreRecordPage page);
	
	void deleteByComplaintId(int complaintId);
	
	void batchDel(Map<String, Object> params);
	
	void batchInsert(List<ScoreRecordEntity> list);
	
	List<ScoreRecordEntity> getScoreRecordList(ScoreRecordPage page);
	
}
