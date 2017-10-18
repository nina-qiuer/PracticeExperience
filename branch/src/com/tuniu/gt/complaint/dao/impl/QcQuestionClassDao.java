package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import com.tuniu.gt.complaint.entity.QcQuestionClassEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcQuestionClassMap; 
import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-qc_question_class")
public class QcQuestionClassDao extends DaoBase<QcQuestionClassEntity, IQcQuestionClassMap>  implements IQcQuestionClassMap {
	public QcQuestionClassDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "qc_question_class";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-qc_question_class")
	public void setMapper(IQcQuestionClassMap mapper) {
		this.mapper = mapper;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QcQuestionClassEntity> list() {
		List<QcQuestionClassEntity> classes = (List<QcQuestionClassEntity>) super.fetchList();
		
		for (int i=0; i<classes.size(); i++) {
			QcQuestionClassEntity parentClass = classes.get(i);
			for (int j=0; j<classes.size(); j++) {
				QcQuestionClassEntity childClass = classes.get(j);
				if (childClass.getParentId() == parentClass.getId()) {
					parentClass.addChildClass(childClass);
				}
			}
		}
		
		return classes;
	}
}
