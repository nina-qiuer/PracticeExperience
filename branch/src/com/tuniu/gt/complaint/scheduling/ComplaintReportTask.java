package com.tuniu.gt.complaint.scheduling;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.service.IComplaintReportService;

/**
 * 报表同步数据
 * @author chenyu
 */
public class ComplaintReportTask {

	private static Logger logger = Logger.getLogger(ComplaintReportTask.class);
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_complete_count")
	private IComplaintReportService complaintReportService;
	
	//新增、更新前一天订单处理的数据
	public void insertComplaintIdByOrderId() throws Exception {
        logger.info("insertComplaintIdByOrderId invoked");
        complaintReportService.insertComplaintIdByOrderId();
    }
	
	//根据用户编号更新姓名、工号、部门信息
	public void updateDepartmentByUserId() throws Exception {
        logger.info("updateDepartmentByUserId invoked");
        complaintReportService.updateDepartmentByUserId();
    }
	
	//新增、更新前一天服务事项数据
	public void insertServicesByOrderId() throws Exception{
		logger.info("insertServicesByOrderId invoked");
		complaintReportService.insertServicesByOrderId();
	}
	
	//服务事项历史数据
	public void insertServicesOld() throws Exception{
		logger.info("insertServicesOld invoked");
		complaintReportService.insertServicesOld();
	}
	
	//新增、更新前一天发起投诉数据
	public void insertLaunchCount() throws Exception{
		logger.info("insertLaunchCount invoked");
		complaintReportService.insertLaunchCountByComplaintId();
	}
	
	//更新发起投诉部门
	public void updateLaunchCountDep() throws Exception{
		logger.info("updateLaunchCountDep invoked");
		complaintReportService.updateLaunchCountDepartment();
	}
}
