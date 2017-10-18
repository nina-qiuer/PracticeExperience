package com.tuniu.gt.complaint.action;

import java.io.UnsupportedEncodingException;
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

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintLaunchCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintLaunchEntity;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintLaunchCountService;
import com.tuniu.gt.complaint.service.IComplaintReportService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.vo.ComplaintDtReportVo;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.uc.datastructure.DepartUsersTree;
import com.tuniu.gt.uc.datastructure.TreeNode;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.gt.warning.common.Page;

/**
 * 售后报表action
 * 
 * @author chenyu8
 * 
 */
@Service("complaint_action-complaint_report")
@Scope("prototype")
public class ComplaintReportAction extends
		FrmBaseAction<IComplaintService, ComplaintDtReportVo> {
	private static final long serialVersionUID = -585218883977507612L;

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_complete_count")
	private IComplaintReportService complaintReportService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_launch_count")
	private IComplaintLaunchCountService complaintLaunchCountService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;

	private String finishTimeBgn;//开始时间

	private String finishTimeEnd;//结束时间
	
	private String dealName;//处理人姓名
	
	private String statisticsField;//计件类型（线路类型还是目的地大类）
	
	private String statisticsFieldType;//计件分类
	
	private Integer dep_id;//部门id
	
	private Integer dep_level;//部门级别
	
	private Page page;//分页信息
	
	private String routeType;
	
	private String dep_second;
	
	private String dep_third;

	private String dep_fourth;
	
	private Boolean handOverState;//交接状态

	public String execute() {
		return "complete_cmplist_report";
	}
	
	public String completeCmpList(){
		if (StringUtils.isBlank(finishTimeBgn)) {
			finishTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(finishTimeEnd)) {
			finishTimeEnd = DateUtil.formatDate(new Date());
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("finishTimeBgn", finishTimeBgn + " 00:00:00");
		paramMap.put("finishTimeEnd", finishTimeEnd + " 23:59:59");
		DepartUsersTree vipSalerDepTree = departmentService.buildDepartUsersTree(2580, 4); //会员事业部
		DepartUsersTree guoneiDepTree = departmentService.buildDepartUsersTree(6196, 4); //国内旅游顾问中心
		DepartUsersTree postSaleDepTree = departmentService.buildDepartUsersTree(2581, 4); //售后服务中心
        DepartUsersTree trafficSaleDeptree = departmentService.buildDepartUsersTree(7443, 4); //旅行客服中心
        DepartUsersTree jtSaleDeptree = departmentService.buildDepartUsersTree(7118, 4); //交通运营中心
        List<DepartUsersTree>  depUsersTreeList = new ArrayList<DepartUsersTree>();
        depUsersTreeList.add(postSaleDepTree);
        depUsersTreeList.add(trafficSaleDeptree);
        depUsersTreeList.add(jtSaleDeptree);
        depUsersTreeList.add(vipSalerDepTree);
        depUsersTreeList.add(guoneiDepTree);
        request.setAttribute("depUsersTreeList", depUsersTreeList);
        Map<String, Map<String, Integer>> reportData = complaintReportService
				.getComplaintCompleteCount(paramMap);
        request.setAttribute("reportData", reportData);
		return "complete_cmplist_report";
	}

	public String getOrderList(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("finishTimeBgn", finishTimeBgn + " 00:00:00");
		paramMap.put("finishTimeEnd", finishTimeEnd + " 23:59:59");
        try {
            paramMap.put("dealName",java.net.URLDecoder.decode(dealName,"utf-8"));
            paramMap.put("statisticsFieldType", java.net.URLDecoder.decode(statisticsFieldType,"utf-8"));
            paramMap.put("statisticsField", java.net.URLDecoder.decode(statisticsField,"utf-8"));
        } catch(UnsupportedEncodingException e1) {
            logger.error("getOrderList failed",e1);
        }
        List<Integer> orderList = complaintReportService.getCompleteCmpOrderList(paramMap);
        request.setAttribute("orderList", orderList);
        return "order_list";
    }
	
	public String getLanchCountReport(){
		if (StringUtils.isBlank(finishTimeBgn)) {
			finishTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(finishTimeEnd)) {
			finishTimeEnd = DateUtil.formatDate(new Date());
		}
		return "launch_count_report";
	}
	
	public String getLanchCountReportData(){
		Map<String,Object> paramMap=new HashMap<String, Object>();
		if (StringUtils.isBlank(finishTimeBgn)) {
			finishTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(finishTimeEnd)) {
			finishTimeEnd = DateUtil.formatDate(new Date());
		}
		paramMap.put("finishTimeBgn", finishTimeBgn + " 00:00:00");
		paramMap.put("finishTimeEnd", finishTimeEnd + " 23:59:59");
		paramMap.put("routeType", routeType);
		List<TreeNode<ComplaintLaunchCountEntity>> complaintLaunchCountList=complaintLaunchCountService.getComplaintLaunchCountList(paramMap);
		JsonUtil.writeResponse(complaintLaunchCountList);
		return "launch_count_report";
	}
	
	public String getLanchList(){
		if(page == null){
			page = new Page();
			page.setPageSize(30);
		}
		Map<String,Object> paramMap=new HashMap<String, Object>();
		if (StringUtils.isBlank(finishTimeBgn)) {
			finishTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(finishTimeEnd)) {
			finishTimeEnd = DateUtil.formatDate(new Date());
		}
		paramMap.put("finishTimeBgn", finishTimeBgn + " 00:00:00");
		paramMap.put("finishTimeEnd", finishTimeEnd + " 23:59:59");
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("pageSize", page.getPageSize());
		paramMap.put("routeType", routeType);
		paramMap.put("dep_second", dep_second);
		paramMap.put("dep_third", dep_third);
		paramMap.put("dep_fourth", dep_fourth);
		List<ComplaintLaunchEntity> complaintLaunchList=complaintLaunchCountService.getComplaintLaunchList(paramMap);
		int count = complaintLaunchCountService.getComplaintLaunchListCount(paramMap);
		page.setCount(count);
		page.setCurrentPageCount(complaintLaunchList==null?0:complaintLaunchList.size());
		request.setAttribute("complaintLaunchList", complaintLaunchList);
		return "launch_list";
	}
	
	public String getSpEventCountReport(){
		if (StringUtils.isBlank(finishTimeBgn)) {
			finishTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(finishTimeEnd)) {
			finishTimeEnd = DateUtil.formatDate(new Date());
		}
		return "sp_event_count_report";
	}
	
	/**
	 * 投诉单特殊标识统计报表
	 * @return
	 */
	public String getSpEventCountReportData(){
		Map<String,Object> paramMap=new HashMap<String, Object>();
		if (StringUtils.isBlank(finishTimeBgn)) {
			finishTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(finishTimeEnd)) {
			finishTimeEnd = DateUtil.formatDate(new Date());
		}
		paramMap.put("finishTimeBgn", finishTimeBgn + " 00:00:00");
		paramMap.put("finishTimeEnd", finishTimeEnd + " 23:59:59");
		paramMap.put("routeType", routeType);
		List<TreeNode<ComplaintLaunchCountEntity>> complaintLaunchCountList=complaintLaunchCountService.getComplaintLaunchCountList(paramMap);
		JsonUtil.writeResponse(complaintLaunchCountList);
		return "sp_event_count_report";
	}
	
	public String getHandOverReport() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(finishTimeBgn)) {
			finishTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(finishTimeEnd)) {
			finishTimeEnd = DateUtil.formatDate(new Date());
		}
		paramMap.put("finishTimeBgn", finishTimeBgn + " 00:00:00");
		paramMap.put("finishTimeEnd", finishTimeEnd + " 23:59:59");
		// 二级部门id
		// 748--售后组、3306--华东大区、3658--华南大区、3439--华北大区
		Integer[] idlist_second = { 748, 3306, 3658, 3439, 5931, 3176};
		// 一级部门id
		paramMap.put("idlist_second", idlist_second);
		DepartUsersTree deptUsersTree = departmentService.buildDepartUsersTree(paramMap);
		List<DepartUsersTree> depUsersTreeList = new ArrayList<DepartUsersTree>();
		depUsersTreeList.add(deptUsersTree);
		request.setAttribute("depUsersTreeList", depUsersTreeList);
		Map<String, Map<String, Integer>> reportData = complaintFollowNoteService.getHandOverCountData(paramMap);
		request.setAttribute("reportData", reportData);
		return "handover_report";
	}

	public String getHandOverComplaintList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(finishTimeBgn)) {
			finishTimeBgn = DateUtil.formatDate(new Date());
		}
		if (StringUtils.isBlank(finishTimeEnd)) {
			finishTimeEnd = DateUtil.formatDate(new Date());
		}
		paramMap.put("finishTimeBgn", finishTimeBgn + " 00:00:00");
		paramMap.put("finishTimeEnd", finishTimeEnd + " 23:59:59");
		String flowName=Constans.OUT_WORK_HANDOVER;
		if(handOverState){
			flowName = Constans.WORK_HANDOVER;
		}
		paramMap.put("flowName", flowName);
		try {
			paramMap.put("dealName", java.net.URLDecoder.decode(dealName, "utf-8"));
			List<Integer> complaintList = complaintFollowNoteService.getHandOverComplaintIds(paramMap);
			request.setAttribute("complaintList", complaintList);
		} catch (UnsupportedEncodingException e) {
			logger.error("getHandOverComplaintList End:" + e.getMessage());
		}
		return "complaint_id_list";
	}
	
	public String getFinishTimeBgn() {
		return finishTimeBgn;
	}

	public void setFinishTimeBgn(String finishTimeBgn) {
		this.finishTimeBgn = finishTimeBgn;
	}

	public String getFinishTimeEnd() {
		return finishTimeEnd;
	}

	public void setFinishTimeEnd(String finishTimeEnd) {
		this.finishTimeEnd = finishTimeEnd;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getStatisticsField() {
		return statisticsField;
	}

	public void setStatisticsField(String statisticsField) {
		this.statisticsField = statisticsField;
	}

	public String getStatisticsFieldType() {
		return statisticsFieldType;
	}

	public void setStatisticsFieldType(String statisticsFieldType) {
		this.statisticsFieldType = statisticsFieldType;
	}

	public Integer getDep_id() {
		return dep_id;
	}

	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}

	public Integer getDep_level() {
		return dep_level;
	}

	public void setDep_level(Integer dep_level) {
		this.dep_level = dep_level;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getDep_second() {
		return dep_second;
	}

	public void setDep_second(String dep_second) {
		this.dep_second = dep_second;
	}

	public String getDep_third() {
		return dep_third;
	}

	public void setDep_third(String dep_third) {
		this.dep_third = dep_third;
	}

	public String getDep_fourth() {
		return dep_fourth;
	}

	public void setDep_fourth(String dep_fourth) {
		this.dep_fourth = dep_fourth;
	}

	public Boolean getHandOverState() {
		return handOverState;
	}

	public void setHandOverState(Boolean handOverState) {
		this.handOverState = handOverState;
	}
}
