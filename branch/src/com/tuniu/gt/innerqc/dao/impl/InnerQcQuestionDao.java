package com.tuniu.gt.innerqc.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.innerqc.dao.sqlmap.imap.InnerQcQuestionMap;
import com.tuniu.gt.innerqc.entity.InnerQcDutyEntity;
import com.tuniu.gt.innerqc.entity.InnerQcQuestionEntity;


@Repository("innerqc_dao_impl-inner_qc_question")
public class InnerQcQuestionDao extends DaoBase<InnerQcQuestionEntity, InnerQcQuestionMap> implements InnerQcQuestionMap {

	public InnerQcQuestionDao() {
		tableName = "qc_inner_qc_question";
	}
	
	@Autowired
	@Qualifier("innerqc_dao_impl-inner_qc_duty")
	private InnerQcDutyDao dutyDao;

	@Autowired
	@Qualifier("innerqc_dao_sqlmap-inner_qc_question")
	public void setMapper(InnerQcQuestionMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<InnerQcQuestionEntity> getQuestionList(InnerQcQuestionEntity entity) {
		return mapper.getQuestionList(entity);
	}
	
	public List<InnerQcQuestionEntity> getQuestionList2(InnerQcQuestionEntity entity) {
		List<InnerQcQuestionEntity> questionList = mapper.getQuestionList(entity);
		for (InnerQcQuestionEntity question : questionList) {
			InnerQcDutyEntity duty = new InnerQcDutyEntity();
			duty.setQuestionId(question.getId());
			question.setDutyList(dutyDao.getDutyList(duty));
		}
		return questionList;
	}

	public int delete(InnerQcQuestionEntity entity) {
		dutyDao.deleteByQuestionId(entity.getId());
		return super.delete(entity.getId());
	}

	@Override
	public void deleteByIqcId(int iqcId) {
		InnerQcQuestionEntity entity = new InnerQcQuestionEntity();
		entity.setIqcId(iqcId);
		List<InnerQcQuestionEntity> questionList = getQuestionList(entity);
		for (InnerQcQuestionEntity question : questionList) {
			dutyDao.deleteByQuestionId(question.getId());
		}
		mapper.deleteByIqcId(iqcId);
	}

}
