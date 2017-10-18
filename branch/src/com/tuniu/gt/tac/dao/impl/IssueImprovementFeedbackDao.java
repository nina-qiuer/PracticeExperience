package com.tuniu.gt.tac.dao.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.tac.dao.sqlmap.imap.IIssueImprovementFeedbackMap;
import com.tuniu.gt.tac.entity.IssueImprovementFeedback;

@Repository("tac_dao_impl-issueImprovementFeedback")
public class IssueImprovementFeedbackDao extends DaoBase<IssueImprovementFeedback, IIssueImprovementFeedbackMap>  implements  IIssueImprovementFeedbackMap
{
	public IssueImprovementFeedbackDao() {  
		tableName ="tac_issue_improvement_feedback";
	}
	
	@Autowired
	@Qualifier("tac_dao_sqlmap-issueImprovementFeedback")
	public void setMapper(IIssueImprovementFeedbackMap mapper) {
		this.mapper = mapper;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return mapper.count(paramMap);
    }


}
