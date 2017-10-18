package com.tuniu.gt.innerqc.dao.sqlmap.imap;


import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.innerqc.entity.InnerQcQuestionEntity;


@Repository("innerqc_dao_sqlmap-inner_qc_question")
public interface InnerQcQuestionMap extends IMapBase {
	
	List<InnerQcQuestionEntity> getQuestionList(InnerQcQuestionEntity entity);
	
	void deleteByIqcId(int iqcId);
	
}
