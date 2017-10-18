package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.QcReportEntity;

import tuniu.frm.core.IServiceBase;
public interface IQcReportService extends IServiceBase {
	public List<QcReportEntity> getReportListByQid(int qid);
}
