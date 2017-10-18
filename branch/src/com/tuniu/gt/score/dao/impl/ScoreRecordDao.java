package com.tuniu.gt.score.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.score.dao.sqlmap.imap.ScoreRecordMap;
import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.score.util.ScoreRecordPage;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;


@Repository("score_dao_impl-score_record")
public class ScoreRecordDao extends DaoBase<ScoreRecordEntity, ScoreRecordMap> implements ScoreRecordMap {

	public ScoreRecordDao() {
		tableName = "sr_score_record";		
	}

	@Autowired
	@Qualifier("score_dao_sqlmap-score_record")
	public void setMapper(ScoreRecordMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public Map<String, Object> getTotal(ScoreRecordPage page) {
		return mapper.getTotal(page);
	}

	@Override
	public List<ScoreRecordEntity> getScoreRecordList(ScoreRecordPage page) {
		List<ScoreRecordEntity> srList = mapper.getScoreRecordList(page);
		if (null != srList && srList.size() > 0) {
			for (ScoreRecordEntity sr : srList) {
				String position = (String) CommonUtil.getPositionMap().get(String.valueOf(sr.getPositionId()));
				sr.setPositionName(position);
				switch (sr.getDepId1()) {
				case 1:
					sr.setDepName1("不可抗力");
					break;
				case 2:
					sr.setDepName1("系统/流程问题");
					break;
				case 14:
					sr.setDepName1("其他");
					break;
				default:
					break;
				}
				
				if (StringUtil.isEmpty(sr.getScoreTarget1())) {
					sr.setScoreTarget1("无");
				}
				if (StringUtil.isEmpty(sr.getScoreTarget2())) {
					sr.setScoreTarget2("无");
				}
			}
		}
		return srList;
	}

	@Override
	public void deleteByComplaintId(int complaintId) {
		mapper.deleteByComplaintId(complaintId);
	}

	@Override
	public void batchDel(Map<String, Object> params) {
		mapper.batchDel(params);
	}

	@Override
	public void batchInsert(List<ScoreRecordEntity> list) {
		mapper.batchInsert(list);
	}

}
