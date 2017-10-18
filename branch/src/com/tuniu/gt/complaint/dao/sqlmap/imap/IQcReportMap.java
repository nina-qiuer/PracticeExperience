package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.QcReportEntity;

@Repository("complaint_dao_sqlmap-qc_report")
public interface IQcReportMap extends IMapBase { 
	List<QcReportEntity> getReportListByQid(int qid);
}
