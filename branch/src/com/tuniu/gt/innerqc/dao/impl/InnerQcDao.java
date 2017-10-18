package com.tuniu.gt.innerqc.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.innerqc.dao.sqlmap.imap.InnerQcMap;
import com.tuniu.gt.innerqc.entity.InnerQcEntity;
import com.tuniu.gt.innerqc.entity.InnerQcQuestionEntity;
import com.tuniu.gt.innerqc.vo.InnerQcPage;


@Repository("innerqc_dao_impl-inner_qc")
public class InnerQcDao extends DaoBase<InnerQcEntity, InnerQcMap> implements InnerQcMap {

	public InnerQcDao() {
		tableName = "qc_inner_qc";
	}
	
	@Autowired
	@Qualifier("innerqc_dao_impl-inner_qc_question")
	private InnerQcQuestionDao questionDao;
	
	@Autowired
	@Qualifier("innerqc_dao_sqlmap-inner_qc")
	public void setMapper(InnerQcMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public long getTotal(InnerQcPage page) {
		return mapper.getTotal(page);
	}

	@Override
	public List<InnerQcEntity> getInnerQcList(InnerQcPage page) {
		List<InnerQcEntity> iqcList = mapper.getInnerQcList(page);
		return iqcList;
	}
	
	public void deleteInnerQc(InnerQcEntity entity) {
		questionDao.deleteByIqcId(entity.getId());
		super.delete(entity.getId());
	}

	public InnerQcEntity getInnerQc(int id) {
		InnerQcEntity iqc = (InnerQcEntity) mapper.get(id);
		InnerQcQuestionEntity question = new InnerQcQuestionEntity();
		question.setIqcId(id);
		iqc.setQuestionList(questionDao.getQuestionList2(question));
		return iqc;
	}

}
