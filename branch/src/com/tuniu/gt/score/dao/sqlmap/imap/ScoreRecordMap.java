package com.tuniu.gt.score.dao.sqlmap.imap;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.score.util.ScoreRecordPage;


@Repository("score_dao_sqlmap-score_record")
public interface ScoreRecordMap extends IMapBase {
	
	Map<String, Object> getTotal(ScoreRecordPage page);
	
	List<ScoreRecordEntity> getScoreRecordList(ScoreRecordPage page);
	
	void deleteByComplaintId(int complaintId);
	
	void batchDel(Map<String, Object> params);
	
	void batchInsert(List<ScoreRecordEntity> list);

}
