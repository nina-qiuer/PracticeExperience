package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.entity.QcReportEntity;
import com.tuniu.gt.complaint.service.IQcReportService;

public class QcReportServiceImplTest extends TestCaseExtend {
	
	@Test
	public void testGetReportListByQid() {
		
		IQcReportService reportService = (IQcReportService)Bean.get("complaint_service_impl-qc_report");
	
		assertNotNull(reportService);
		
		List<QcReportEntity> reports = reportService.getReportListByQid(6);
		
		assertNotNull(reports);
	}

}
