package com.tuniu.gt.tac.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.tac.dao.impl.IssueImprovementFeedbackDao;
import com.tuniu.gt.tac.service.IIssueImprovementFeedbackService;
@Service("tac_service_impl-issueImprovementFeedback")
public class IssueImprovementFeedbackServiceImpl extends ServiceBaseImpl<IssueImprovementFeedbackDao> implements IIssueImprovementFeedbackService
{
	private static Logger logger = Logger.getLogger(IssueImprovementFeedbackServiceImpl.class);
	
	@Autowired
	@Qualifier("tac_dao_impl-issueImprovementFeedback")
	public void setDao(IssueImprovementFeedbackDao dao) {
		this.dao = dao;
	}


    @Override
    public int count(Map<String, Object> paramMap) {
        return dao.count(paramMap);
    }

}
