package com.tuniu.gt.innerqc.service;

import java.util.List;

import com.tuniu.gt.innerqc.entity.InnerQcQuestionEntity;

import tuniu.frm.core.IServiceBase;

public interface InnerQcQuestionService extends IServiceBase {
	
	List<InnerQcQuestionEntity> getQuestionList(InnerQcQuestionEntity entity);
	
	void deleteInnerQcQuestion(InnerQcQuestionEntity entity);
	
}
