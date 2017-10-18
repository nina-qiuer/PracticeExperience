package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.QcPunishSegmentTaskMapper;
import com.tuniu.qms.qc.dto.QcPunishSegmentTaskDto;
import com.tuniu.qms.qc.model.QcPunishSegmentTask;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;

@Service
public class QcPunishSegmentTaskServiceImpl implements QcPunishSegmentTaskService {

	@Autowired
	private QcPunishSegmentTaskMapper mapper;
	
	@Override
	public void add(QcPunishSegmentTask obj) {
		
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QcPunishSegmentTask obj) {
		mapper.update(obj);
	}

	@Override
	public QcPunishSegmentTask get(Integer id) {
		return null;
	}

	@Override
	public List<QcPunishSegmentTask> list(QcPunishSegmentTaskDto dto) {
		return null;
	}

	@Override
	public void loadPage(QcPunishSegmentTaskDto dto) {
		
	}

	@Override
	public List<QcPunishSegmentTask> getTaskList() {
		return mapper.getTaskList();
	}

	@Override
	public void builTask(Integer qcId, int b) {
		QcPunishSegmentTask task = new QcPunishSegmentTask();
		
		task.setDataId(qcId);
		task.setTaskType(b);
		mapper.add(task);
	}

}
