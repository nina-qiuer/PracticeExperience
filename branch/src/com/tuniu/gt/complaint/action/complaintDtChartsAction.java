package com.tuniu.gt.complaint.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.HandlerResult;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.vo.ComplaintDtReportVo;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.uc.datastructure.DepartUsersTree;

/**
 * 售后报表action
 * 
 * @author chenyu8
 * 
 */
@Service("complaint_action-complaint_dt_charts")
@Scope("prototype")
public class complaintDtChartsAction extends
		FrmBaseAction<IComplaintService, ComplaintDtReportVo> {

	private Logger logger = Logger.getLogger(getClass());

	private String assignTimeBgn;

	private String assignTimeEnd;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;

	public String execute() {
		return "complaint_dt_charts";
	}

	public String echartsDataInit() throws IOException {
		if (StringUtils.isBlank(assignTimeBgn)) {
			assignTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(assignTimeEnd)) {
			assignTimeEnd = DateUtil.formatDate(new Date());
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 二级部门id
		// 74--资深组、748--售后组、1296--出游前服务部、5931--客服部（酒店事业部）、3176--票务服务部
		// 3306--华东大区、3658--华南大区、3439--华北大区
		Integer[] idlist_second = { 74, 748, 1296, 5931, 3176, 3306,
				3658, 3439 };
		// 三级部门
		// 3960--分销
		Integer[] idlist_third = { 3960 };
		paramMap.put("idlist_second", idlist_second);
		paramMap.put("idlist_third", idlist_third);
		paramMap.put("assignTimeBgn", assignTimeBgn + " 00:00:00");
		paramMap.put("assignTimeEnd", assignTimeEnd + " 23:59:59");
		Map<String,BigDecimal[]> complaintDtMap = complaintService.getDealPayoutEcharts(paramMap);
		JsonUtil.writeResponse(complaintDtMap);
		return "complaint_dt_charts";
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

}
