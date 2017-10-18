package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.QcQuestionDao;
import com.tuniu.gt.complaint.dao.impl.QcTrackerDao;
import com.tuniu.gt.complaint.entity.QcQuestionEntity;
import com.tuniu.gt.complaint.entity.QcTrackerEntity;
import com.tuniu.gt.complaint.service.IQcQuestionService;
@Service("complaint_service_impl-qc_question")
public class QcQuestionServiceImpl extends ServiceBaseImpl<QcQuestionDao> implements IQcQuestionService {
	private QcTrackerDao trackerDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-qc_question")
	public void setDao(QcQuestionDao dao) {
		this.dao = dao;
	}
	
	public QcTrackerDao getTrackerDao() {
		return trackerDao;
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-qc_tracker")
	public void setTrackerDao(QcTrackerDao trackerDao) {
		this.trackerDao = trackerDao;
	}

	@Override
	public List<QcQuestionEntity> getQuestionListByReportId(int qid) {
		return this.dao.getQuestionListByReportId(qid);
	}

	@Override
	public void deleteQuestionsByReportId(int qid) {
		List<QcQuestionEntity> questions = getQuestionListByReportId(qid);
		
		// 删除question 表中数据
		dao.deleteQuestionsByReportId(qid);
		
		// 联动删除对应 tracker 数据
		for (QcQuestionEntity q : questions) {
			trackerDao.deleteTrackersByQuestioinId(q.getId());			
		}
	}

	@Override
	public void add(QcQuestionEntity question) {
		dao.insert(question);
		int id = question.getId();
		List<QcTrackerEntity> trackers = question.getTrackers();
		
		int count = trackers.size();
		for(int i=0; i<count; i++) {
			QcTrackerEntity tracker = trackers.get(i);
			tracker.setQuestionId(id);
			trackerDao.insert(tracker);
		}
		
	}
}
