package com.tuniu.gt.complaint.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.vo.ComplaintDtAnalyseVo;
import com.tuniu.gt.complaint.vo.ComplaintDtReportVo;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.uc.datastructure.DepartUsersTree;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.gt.warning.common.Page;

/**
 * 售后报表action
 * 
 * @author chenyu8
 * 
 */
@Service("complaint_action-complaint_dt_report")
@Scope("prototype")
public class complaintDtReportAction extends
		FrmBaseAction<IComplaintService, ComplaintDtReportVo> {

	private Logger logger = Logger.getLogger(getClass());

	private String assignTimeBgn;

	private String assignTimeEnd;

	private String real_name;
	
	private String agencyName;
	
	private String belongDept;
	
	private String startCity;
	
	private String endCity;
	
	private String signCity;
	
	private String routeType;
	
	private String productLeder;
	
	private String orderId;
	
	private String complaintId;
	
	private String routeId;
	
	private String dealDepart;
	
	private Page page;
	
	private String isTheoryPayout;
	
	private String isRealPayout;

	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;

	public String execute() {
		if (StringUtils.isBlank(assignTimeBgn)) {
			assignTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(assignTimeEnd)) {
			assignTimeEnd = DateUtil.formatDate(new Date());
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assignTimeBgn", assignTimeBgn + " 00:00:00");
		paramMap.put("assignTimeEnd", assignTimeEnd + " 23:59:59");
		// 三级部门id
		Integer[] idlist_third = null;
		// 二级部门id
		// 74--资深组、748--售后组、1296--出游前服务部、5931--客服部（待删除）、3176--票务服务部（待删除）
		// 3306--华东大区、3658--华南大区、3439--华北大区、3004--分销
//		Integer[] idlist_second = { 74, 748, 1296, 5931, 3176, 3306,
//				3658, 3439, 3004 };
		// 7444-交通客服部、7445-酒店客服部、7908-国内机票运营部
		Integer[] idlist_second = { 74, 748, 1296, 3306,
                3658, 3439, 3004, 7444, 7445, 7908 };
		// 一级部门id
		Integer[] idlist_first = null;
		paramMap.put("idlist_third", idlist_third);
		paramMap.put("idlist_second", idlist_second);
		paramMap.put("idlist_first", idlist_first);
		paramMap.put("needCount", true);
		DepartUsersTree deptUsersTree = departmentService
				.buildPayoutReportTree(paramMap);
		List<DepartUsersTree> depUsersTreeList = new ArrayList<DepartUsersTree>();
		depUsersTreeList.add(deptUsersTree);
		request.setAttribute("depUsersTreeList", depUsersTreeList);
		Map<String, Map<String, BigDecimal>> reportData = complaintService
				.getDealPayoutReport(paramMap);
		request.setAttribute("reportData", reportData);
		return "complaint_dt_report";
	}
	
	public String getTotalReport(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assignTimeBgn", assignTimeBgn);
		paramMap.put("assignTimeEnd", assignTimeEnd + " 23:59:59");
		// 二级部门id
		// 74--资深组、748--售后组、1296--出游前服务部、5931--客服部（待删除）、3176--票务服务部（待删除）
		// 3306--华东大区、3658--华南大区、3439--华北大区
//		Integer[] idlist_second = { 74, 748, 1296, 5931, 3176, 3306,
//				3658, 3439 };
		// 5294-宿迁客服中心、7444-交通客服部、7445-酒店客服部、7908-国内机票运营部
        Integer[] idlist_second = { 74, 748, 1296, 3306,
                3658, 3439, 3004, 5294, 7444, 7445, 7908 };
		// 三级部门
		// 3960--分销
		Integer[] idlist_third = { 3960 };
		paramMap.put("idlist_second", idlist_second);
		paramMap.put("idlist_third", idlist_third);
		List<ComplaintDtReportVo> reportData = complaintService.getDealPayoutTotal(paramMap);
		request.setAttribute("reportData", reportData);
		return "complaint_dt_total";
	}

	public String getDealPayoutDetail() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assignTimeBgn", assignTimeBgn);
		paramMap.put("assignTimeEnd", assignTimeEnd + " 23:59:59");
		try {
			paramMap.put("real_name",
					java.net.URLDecoder.decode(real_name, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("getDtDetail failed", e);
		}
		List<ComplaintDtReportVo> complaintDetailList = complaintService
				.getDealPayoutDetail(paramMap);
		request.setAttribute("complaintDetailList", complaintDetailList);
		return "complaint_dt_detail";
	}
	
	public String getDtAnalyseList(){
		if(page == null){
			page = new Page();
			page.setPageSize(50);
		}
		
		if (StringUtils.isBlank(assignTimeBgn)) {
			assignTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(assignTimeEnd)) {
			assignTimeEnd = DateUtil.formatDate(new Date());
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assignTimeBgn", assignTimeBgn + " 00:00:00");
		paramMap.put("assignTimeEnd", assignTimeEnd + " 23:59:59");
		paramMap.put("agencyName", agencyName);
		paramMap.put("belongDept", belongDept);
		paramMap.put("startCity", startCity);
		paramMap.put("endCity", endCity);
		paramMap.put("signCity", signCity);
		paramMap.put("routeType", routeType);
		paramMap.put("productLeder", productLeder);
		paramMap.put("dealDepart", dealDepart);
		paramMap.put("orderId", orderId);
		paramMap.put("complaintId", complaintId);
		paramMap.put("routeId", routeId);
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("pageSize", page.getPageSize());
		if("1".equals(isRealPayout)){
			paramMap.put("isRealPayout", isRealPayout);
		}
		if("1".equals(isTheoryPayout)){
			paramMap.put("isTheoryPayout", isTheoryPayout);
		}
		List<ComplaintDtAnalyseVo> reportData = complaintService
				.getDealPayoutAnalyseList(paramMap);
		int count = complaintService.getDtAnalyseCount(paramMap);
		page.setCount(count);
		page.setCurrentPageCount(reportData==null?0:reportData.size());
		request.setAttribute("reportData", reportData);
		return "complaint_dt_analyse";
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getAssignTimeBgn() {
		return assignTimeBgn;
	}

	public void setAssignTimeBgn(String assignTimeBgn) {
		this.assignTimeBgn = assignTimeBgn;
	}

	public String getAssignTimeEnd() {
		return assignTimeEnd;
	}

	public void setAssignTimeEnd(String assignTimeEnd) {
		this.assignTimeEnd = assignTimeEnd;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(String belongDept) {
		this.belongDept = belongDept;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getSignCity() {
		return signCity;
	}

	public void setSignCity(String signCity) {
		this.signCity = signCity;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getProductLeder() {
		return productLeder;
	}

	public void setProductLeder(String productLeder) {
		this.productLeder = productLeder;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public IComplaintService getComplaintService() {
		return complaintService;
	}

	public void setComplaintService(IComplaintService complaintService) {
		this.complaintService = complaintService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getDealDepart() {
		return dealDepart;
	}

	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}

	public String getIsTheoryPayout() {
		return isTheoryPayout;
	}

	public void setIsTheoryPayout(String isTheoryPayout) {
		this.isTheoryPayout = isTheoryPayout;
	}

	public String getIsRealPayout() {
		return isRealPayout;
	}

	public void setIsRealPayout(String isRealPayout) {
		this.isRealPayout = isRealPayout;
	}
}
