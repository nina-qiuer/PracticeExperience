package com.tuniu.gt.complaint.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.QcQuestionClassEntity;
import com.tuniu.gt.complaint.entity.QcQuestionEntity;
import com.tuniu.gt.complaint.entity.QcReportEntity;
import com.tuniu.gt.complaint.entity.QcTrackerEntity;
import com.tuniu.gt.complaint.entity.ReceiverEmailEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IQcQuestionClassService;
import com.tuniu.gt.complaint.service.IQcQuestionService;
import com.tuniu.gt.complaint.service.IQcReportService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.service.IReceiverEmailService;
import com.tuniu.gt.complaint.vo.ComplaintReasonVo;
import com.tuniu.gt.complaint.vo.ComplaintSearchVo;
import com.tuniu.gt.complaint.vo.ComplaintVo;
import com.tuniu.gt.complaint.vo.QcVo;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.punishprd.service.IPunishPrdService;
import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.score.service.ScoreRecordService;
import com.tuniu.gt.toolkit.BasicHttp;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DataExportExcel;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.ExportPdfUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

@SuppressWarnings("serial")
@Service("complaint_action-qc")
@Scope("prototype")
public class QCAction extends FrmBaseAction<IQcService, QcEntity> {
	
	private Logger logger = Logger.getLogger(getClass());

	// action result 定义
	private static final String QC_LIST = "qc_list";
	private static final String QC_INPUT = "qc_input";
	private static final String QC_VIEW = "qc_view";
	private static final String selfUrl = "qc"; // 当前列表页的地址

	private int status; // 当前状态，PENDING, PROCESSING, PROCESSED.
	private int id;
	private int indexId = 0;// 查看质检报告时获取的页面ID
	private int refill; // 判断是否为重新提交表单 1为重新提交
	private QcEntity qc;

	private IQcQuestionClassService qcQuestionClassService;
	private List<QcQuestionClassEntity> qcQuestionClasses;
	private IComplaintService complaintService;
	private IAppointManagerService appointerService;
	private IDepartmentService departmentService;
	private IQcQuestionService qcQuestionService;
	private IQcReportService qcReportService;
	// 引入投诉事宜service
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
	@Autowired
	@Qualifier("pp_service_impl-punishprd")
	private IPunishPrdService punishPrdService; 
	
	private List<QcQuestionEntity> qcQuestions = new ArrayList<QcQuestionEntity>();
	private Set<Integer> questionChecked = new HashSet<Integer>(); // 用于判断哪些问题选中了
	private List<QcReportEntity> qcReports = new ArrayList<QcReportEntity>();

	private IUserService userService;
	private Boolean isQcOfficer = false; // 是否为质检负责人，质检负责人可以分配质检人

	private List<UserEntity> sameGroupUsers = new ArrayList<UserEntity>(); // 同组用户
	private List<UserEntity> productManagers = new ArrayList<UserEntity>(); // 产品经理列表
	private List<String> bdpNames = new ArrayList<String>(); // 一级部门列表

	private Map<String, Object> officerDept = new LinkedHashMap<String, Object>();// 一级责任归属
	private Map<String, String> officerDeptOld = new LinkedHashMap<String, String>();// 旧责任归属
	private Map<String, Object> positionMap = new LinkedHashMap<String, Object>();// 定义执行岗位

	private String cssEmails = "";// 抄送人
	private String recipientsEmails = "";// 收件人
	
	private ComplaintSearchVo search;
	
	public ComplaintSearchVo getSearch() {
		return search;
	}

	public void setSearch(ComplaintSearchVo search) {
		this.search = search;
	}

	@Autowired
	@Qualifier("complaint_service_impl-qc")
	public void setService(IQcService service) {
		this.service = service;
	}

	@Autowired
	@Qualifier("complaint_service_impl-qc_question_class")
	public void setQcQuestionClassService(
			IQcQuestionClassService qcQuestionClassService) {
		this.qcQuestionClassService = qcQuestionClassService;
	}

	public IQcQuestionService getQcQuestionService() {
		return qcQuestionService;
	}

	@Autowired
	@Qualifier("complaint_service_impl-qc_question")
	public void setQcQuestionService(IQcQuestionService qcQuestionService) {
		this.qcQuestionService = qcQuestionService;
	}
	
	public IAppointManagerService getAppointerService() {
		return appointerService;
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-appoint_manager")
	public void setAppointerService(IAppointManagerService appointerService) {
		this.appointerService = appointerService;
	}

	public IUserService getUserService() {
		return userService;
	}

	@Autowired
	@Qualifier("frm_service_system_impl-user")
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IQcReportService getQcReportService() {
		return qcReportService;
	}

	@Autowired
	@Qualifier("complaint_service_impl-qc_report")
	public void setQcReportService(IQcReportService qcReportService) {
		this.qcReportService = qcReportService;
	}

	public IComplaintService getComplaintService() {
		return complaintService;
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	public void setComplaintService(IComplaintService complaintService) {
		this.complaintService = complaintService;
	}

	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	@Autowired
	@Qualifier("uc_service_user_impl-department")
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Autowired
	@Qualifier("score_service_impl-score_record")
	private ScoreRecordService scoreRecordService;
	
	// 邮件配置service
	@Autowired
	@Qualifier("complaint_service_impl-receiver_email")
	private IReceiverEmailService receiverEmailService;
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	public QCAction() {
		setManageUrl(selfUrl);
		// 定义责任归属（老）
		officerDeptOld.put("1", "不可抗力");
		officerDeptOld.put("2", "系统/流程问题");
		officerDeptOld.put("3", "客服中心");
		officerDeptOld.put("4", "运营中心");
		officerDeptOld.put("5", "营销中心");
		officerDeptOld.put("6", "财务法务中心");
		officerDeptOld.put("7", "人力资源中心");
		officerDeptOld.put("8", "华东事业部");
		officerDeptOld.put("9", "华北事业部");
		officerDeptOld.put("10", "南方事业部");
		officerDeptOld.put("11", "团队事业部");
		officerDeptOld.put("12", "自助游事业部");
		officerDeptOld.put("13", "新产品事业部");
		officerDeptOld.put("14", "其他");

		// 定义执行岗位
		positionMap = CommonUtil.getPositionMap();
	}

	public QcEntity find(int id) {
		return service.find(id);
	}

	/**
	 * 查看质检包括
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String view() {
		// 获取一级责任归属
		officerDept = responsibilityAll();
		qc = service.find(id);
		
		//核实情况加换行转义  add by fujun BOSSGT-479 质检报告优化
		qc.setVerify(CommonUtil.replaceEnter(qc.getVerify()));
		
		// 查找报告及对应问题
		qcReports = qcReportService.getReportListByQid(id);
		if (qcReports == null || qcReports.size() == 0) {
			return QC_VIEW;
		}
		
		//点击  投诉处理单->查看质检报告按钮。BOSSGT-479 质检报告优化 6.当有多份质检报告时，查看和重新填写质检报告均显示最新的一份。
		if(0 == indexId)
		{
		    indexId = qcReports.size();
		}
		
		int index_id = indexId - 1;
		QcReportEntity report = qcReports.get(index_id);
		qcQuestions = report.getQuestions();
		List<QcTrackerEntity> trackers;
		String responsibility = "";
		String position = "";
		String positions = "";
		// 获取责任归属和执行岗位的中文显示
		for (QcQuestionEntity question : qcQuestions) {
		    
		    //质检结论内容换行转义  add by fujun BOSSGT-479 质检报告优化
		    question.setConclusion(CommonUtil.replaceEnter(question.getConclusion()));
		    
			trackers = question.getTrackers();
			
			for (QcTrackerEntity tracker : trackers) {
				position = tracker.getPosition();
				if (!position.equals("0")) {
					positions = (String) positionMap.get(position);
				} else {
					positions = "无 ";
				}
				tracker.setPosition(positions);
				responsibility = getresponsibilities(tracker);
				tracker.setResponsibility(responsibility);
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", qc.getComplaintId());
		List<ComplaintReasonEntity> complaintReason = (List<ComplaintReasonEntity>) reasonService.fetchList(paramMap);
		request.setAttribute("complaintReasons", complaintReason);
		return QC_VIEW;
	}

	/**
	 * 下载质检报告
	 * 
	 * @return
	 * @throws IOException
	 */
	public String download() throws IOException {
		// 获取一级责任归属
		officerDept = responsibilityAll();
		Map<String, Object> convertMap = new HashMap<String, Object>();
		String bigClassName = "";// 23 问题类型
		String responsibility = "";// 24 责任归属
		String position = "";// 25 执行岗位
		String responsiblePerson = "";// 26 责任人
		String improver = "";// 27 改进人
		qc = service.find(id);
		// 查找报告及对应问题
		qcReports = qcReportService.getReportListByQid(id);
		if (qcReports == null || qcReports.size() == 0) {
			return QC_LIST;
		}
		// 根据页面传来的ID号，选取待下载的报告
		int index_id = indexId - 1;
		QcReportEntity report = qcReports.get(index_id);
		qcQuestions = report.getQuestions();
		ComplaintEntity complaints = qc.getComplaint();
		List<QcTrackerEntity> trackers;
		// 获取责任归属和执行岗位的中文显示
		for (QcQuestionEntity question : qcQuestions) {
			trackers = question.getTrackers();
			bigClassName += question.getBigClassName() + " ";
			for (QcTrackerEntity tracker : trackers) {
				if (tracker.getPosition().equals("0")) {
					position += "无 ";
				} else {
					position += (String) positionMap.get(tracker.getPosition())
							+ " ";
				}
				responsibility += getresponsibilities(tracker);
				responsiblePerson += tracker.getResponsiblePerson() + " ";
				improver += tracker.getImprover() + " ";
			}
		}
		// 准备页面转换的数据
		convertMap.put("res_url", Constant.CONFIG.getProperty("res_url"));
		convertMap.put("titles", complaints.getTitle());// 1 标题
		convertMap.put("orderId", qc.getOrderId().toString());// 2 订单号
		convertMap.put("guestName", complaints.getGuestName());// 3 客户姓名
		convertMap.put("guestNum", complaints.getGuestNum());// 4 人数
		convertMap.put("startCity", complaints.getStartCity());// 5 出发地
		convertMap.put("startTime", DateUtil.formatDate(complaints.getStartTime(), "yyyy-MM-dd HH:mm:ss"));// 出发时间
		convertMap.put("route", complaints.getRoute());// 7 线路
		convertMap.put("score", complaints.getScore() + "%");// 8 满意度
		convertMap.put("customer", complaints.getCustomer());// 9 售前客服
		convertMap.put("customerLeader", complaints.getCustomerLeader());// 10
																			// 客服经理
		convertMap.put("serviceManager", complaints.getServiceManager());// 11
																			// 高级客服经理
		convertMap.put("producter", complaints.getProducter());// 12 产品专员
		convertMap.put("productLeader", complaints.getProductLeader());// 13
																		// 产品经理
		convertMap.put("seniorManager", complaints.getSeniorManager());// 14
																		// 高级经理
		convertMap.put("level", complaints.getLevel() + "级");// 15 投诉级别
		convertMap.put("descript", complaints.getDescript());// 16 投诉说明
		convertMap.put("requirement", complaints.getRequirement());// 17 客户要求
		convertMap.put("dealName", complaints.getDealName());// 18 受理人
		convertMap.put("buildDate", DateUtil.formatDate(complaints.getBuildDate(), "yyyy-MM-dd HH:mm:ss"));
		convertMap.put("agencyName", complaints.getAgencyName());// 20 相关供应商
		convertMap.put("verify", qc.getVerify());// 21 核实情况
		//convertMap.put("conclusion", qc.getConclusion());// 22 质检结论
		convertMap.put("bigClassName", bigClassName);// 23 问题类型
		convertMap.put("responsibility", responsibility);// 24 责任归属
		convertMap.put("position", position);// 25 执行岗位
		convertMap.put("responsiblePerson", responsiblePerson);// 26 责任人
		convertMap.put("improver", improver);// 27 改进人
		// 根据现有文件download/jsp/complaint_qc_detail.jsp，进行转换
		String fileName = convertMap.get("orderId")
				+ "_"
				+ complaints.getId()
				+ "_"
				+ indexId
				+ "_view_"
				+ DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".html";
		String filePath = ServletActionContext.getServletContext().getRealPath("/");
		String templateContent = "";
		FileInputStream fileinputstream = new FileInputStream(new File(filePath
				+ "download/jsp/complaint_qc_detail.jsp"));// 读取模板文件
		int lenght = fileinputstream.available();
		byte bytes[] = new byte[lenght];
		fileinputstream.read(bytes);
		fileinputstream.close();
		templateContent = new String(bytes, "UTF-8");
		Set<String> key = convertMap.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String index = (String) it.next();
			templateContent = templateContent.replaceFirst(index, convertMap
					.get(index).toString());// 替换掉模板中相应的地方
		}
		FileOutputStream fileoutputstream = new FileOutputStream(filePath
				+ "download/" + fileName);// 建立文件输出流
		Writer out = new OutputStreamWriter(fileoutputstream, "utf-8");
		out.write(templateContent);
		out.close();
		fileoutputstream.close();
		// 设置PDF下载
		String pdfUrlScheme = Constant.CONFIG.getProperty("pdf_url_scheme");
		String pdfUrlHost = Constant.CONFIG.getProperty("pdf_url_host");
		int pdfPort = Integer.valueOf(Constant.CONFIG.getProperty("pdf_port"))
				.intValue();
		String pdfServiceUrlPath = Constant.CONFIG
				.getProperty("pdf_service_url_path");
		String sendUrl = "http://" + Constant.CONFIG.getProperty("file_url")
				+ "download/" + fileName;
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("sendUrl", sendUrl);
		String returnStr = BasicHttp.execute(map, pdfUrlScheme, pdfUrlHost,
				pdfPort, pdfServiceUrlPath);
		JSONObject returnJson = null;
		String pdfUrl = "";
		if (!"fail".equals(returnStr) && returnStr != null) {
			returnJson = JSONObject.fromObject(returnStr);
		}
		int retFlag = returnJson.has("retFlag") ? returnJson.getInt("retFlag")
				: -1;
		if (retFlag == 0) {
			pdfUrl = returnJson.getString("pdfUrl");
		}
		ExportPdfUtil.download(ServletActionContext.getResponse(), pdfUrl);
		return QC_LIST;
	}
	
	public String getExportDataTotal() {
		int totalNum = service.getExportDataTotal(getRequestParameter());
		CommonUtil.writeResponse(totalNum);
		return QC_LIST;
	}

	/**
	 * 导出查询结果
	 * 
	 * @return
	 */
	public String exports() {
		List<Map<String, Object>> data = service.getExportData(getRequestParameter());
		String fileName = "list" + DateUtil.formatDate(new Date(), "yyyyMMddhhmmss") + ".xls";
		HttpServletResponse response = ServletActionContext.getResponse();
		DataExportExcel.exportExcel(getColumnList(), data, fileName, response);
		return QC_LIST;
	}
	
	private List<String> getColumnList() {
		List<String> columnList = new ArrayList<String>();
		columnList.add("质检日期");
		columnList.add("订单号");
		columnList.add("线路");
		columnList.add("产品类型");
		columnList.add("投诉问题等级");
		columnList.add("投诉日期");
		columnList.add("出游日期");
		columnList.add("归来日期");
		columnList.add("出发地");
		columnList.add("目的地");
		columnList.add("产品经理");
		columnList.add("产品专员");
		columnList.add("客服经理");
		columnList.add("客服专员");
		columnList.add("投诉发起人");
		columnList.add("投诉类型（一级）");
		columnList.add("投诉类型（二级）");
		columnList.add("投诉详情");
		columnList.add("核实情况");
		columnList.add("质检结论");
		columnList.add("责任归属");
		columnList.add("执行岗位");
		columnList.add("责任人");
		columnList.add("改进人");
		columnList.add("问题大类型");
		columnList.add("问题小类型");
		columnList.add("记分等级");
		columnList.add("记分对象1");
		columnList.add("记分值1");
		columnList.add("记分对象2");
		columnList.add("记分值2");
		columnList.add("总记分");
		//columnList.add("损失金额");
		columnList.add("质检人");
		columnList.add("投诉发起时间");
		columnList.add("投诉完成时间");
		columnList.add("质检分配时间");
		columnList.add("质检完成时间");
		return columnList;
	}

	/**
	 * 更新表单初始化时读取数据
	 * 
	 * @return QC_INPUT
	 */
	@SuppressWarnings("unchecked")
	public String update() {
		request.setAttribute("curUrl", request.getParameter("curUrl"));
		// 获取一级责任归属
		officerDept = responsibility();
		// 获取ceo、coo、副总裁的邮箱地址
		// String positionIds = "6,7";
		String cssEmail = "";
		String recipientsEmail = "";
		String names = "";
		// List<UserEntity> cssUsers = new ArrayList<UserEntity>();
		// cssUsers = userService.getUsers("position_ids",positionIds);
		// for (UserEntity cssUser : cssUsers) {
		// cssEmail += cssUser.getEmail() + ";";
		// }
		// 加载所有问题类别
		qcQuestionClasses = qcQuestionClassService.list();
		// 查找报告及对应问题
		qc = service.find(id);
		// 获取
		// 产品经理、产品专员、售前客服、客服经理、高级经理、产品总监、事业部总经理、售后总监、客服中心总监、g-shouhou@tuniu.com、g-qa@tuniu.com的邮箱地址
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("id", qc.getComplaintId());
		ComplaintEntity complaints = (ComplaintEntity) complaintService.fetchOne(sqlParams);
		if (StringUtil.isNotEmpty(complaints.getProductLeader())) {
			names += "'" + complaints.getProductLeader() + "',";// 产品经理
		}
		if (StringUtil.isNotEmpty(complaints.getProducter())) {
			names += "'" + complaints.getProducter() + "',";// 产品专员
		}
		if (StringUtil.isNotEmpty(complaints.getCustomer())) {
			names += "'" + complaints.getCustomer() + "',";// 售前客服
		}
		if (StringUtil.isNotEmpty(complaints.getCustomerLeader())) {
			names += "'" + complaints.getCustomerLeader() + "',";// 客服经理
		}
		if (StringUtil.isNotEmpty(complaints.getSeniorManager())) {
			names += "'" + complaints.getSeniorManager() + "',";// 高级产品经理
		}
		if (StringUtil.isNotEmpty(complaints.getProductManager())) {
			names += "'" + complaints.getProductManager() + "',";// 产品总监
		} 
        if(!"".equals(names.trim())) {
			names = names.substring(0, names.length()-1);
		}
		//String manager = "'" + complaints.getDepName() + "',";// 事业部总经理 TODO
		//names += productManager + manager;// 临时处理
		//names += "''";
		List<UserEntity> recipientsUsers = new ArrayList<UserEntity>();
		if(!"".equals(names.trim())){
			
			recipientsUsers = userService.getUsers("realNames", names);
			for (UserEntity recipientsUser : recipientsUsers) {
				recipientsEmail += recipientsUser.getEmail() + ";";
		}
		}
		// recipientsEmail += "g-shouhou@tuniu.com;";
		// recipientsEmail += "g-qa@tuniu.com;";

		// 设置抄送人邮箱
		List<ReceiverEmailEntity> ccEmailList = receiverEmailService
				.getListByType(6, 0, 0);
		for (ReceiverEmailEntity receiverEmailEntity : ccEmailList) {
			cssEmail += receiverEmailEntity.getUserMail() + ";";
		}

		List<ReceiverEmailEntity> ccGEmailList = receiverEmailService
				.getListByType(6, 1, 0);
		for (ReceiverEmailEntity receiverEmailEntity : ccGEmailList) {
			cssEmail += receiverEmailEntity.getUserMail() + ";";
		}

		qc.setRecipients(recipientsEmail);// 设置收件人邮箱
		qc.setCcs(cssEmail);// 设置抄送人邮箱
		qcReports = qcReportService.getReportListByQid(id);

		// 关闭质检标记
		Boolean closeQcFlag = true;
		// 投诉事宜
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", qc.getComplaintId());
		List<ComplaintReasonEntity> complaintReason = (List<ComplaintReasonEntity>) reasonService.fetchList(paramMap);
		request.setAttribute("complaintReasons", complaintReason);
		if (complaintReason != null && complaintReason.size() >= 1) {
			for(ComplaintReasonEntity reason : complaintReason){
				if (!"其他".equals(reason.getType())) {
					closeQcFlag = false;
				}
			}
		} else {
			closeQcFlag=false;
		}
		request.setAttribute("closeQcFlag", closeQcFlag);
		if (qcReports == null || qcReports.size() == 0) {
			qc.setVerify("");
			//qc.setConclusion("");
			if(closeQcFlag){
				qc.setVerify("其他-咨询");
				//qc.setConclusion("其他-咨询");

				recipientsEmails = "g-sup-res@tuniu.com";
				qc.setRecipients(recipientsEmails);
				qc.setCcs("");

				QcQuestionEntity question = new QcQuestionEntity();
				question.setBigClassId(50);
				question.setBigClassName("其他");
				question.setSmallClassId(35);
				question.setSmallClassName("客人咨询");
				questionChecked.add(50);
				questionChecked.add(35);
				List<QcTrackerEntity> trackers = new ArrayList<QcTrackerEntity>();
				QcTrackerEntity tracker = new QcTrackerEntity();
				tracker.setImprover("");
				tracker.setResponsiblePerson("");
				tracker.setResponsibility("14");
				trackers.add(tracker);
				question.setTrackers(trackers);
				qcQuestions.add(question);
				request.setAttribute("flag", 1);
			}
		} else {
			if(closeQcFlag){
				recipientsEmails = "g-sup-res@tuniu.com";
				qc.setRecipients(recipientsEmails);
				qc.setCcs("");
			}
			QcReportEntity report = qcReports.get(qcReports.size()-1); // 取最新一条报告记录
			//BOSSGT-479 质检报告优化 6.当有多份质检报告时，查看和重新填写质检报告均显示最新的一份。
			
			qcQuestions = report.getQuestions();
			int j = 0;// 判断选中的问题个数
			for (QcQuestionEntity question : qcQuestions) {
				int smallClassId = question.getSmallClassId();
				int bigClassId = question.getBigClassId();
				questionChecked.add(smallClassId);
				questionChecked.add(bigClassId);
				j++;
			}
			request.setAttribute("flag", j);
		}
		
		return QC_INPUT;
	}

	/**
	 * 保存质检报告
	 * 
	 * @return
	 */
	public String doSave() {
		String verify = qc.getVerify();
		qc = service.find(id);
		qc.setStatus(QcEntity.PROCESSING_STATE); // 点击保存后质检状态转为处理中状态
		qc.setVerify(verify);
		return doUpdate();
	}

	/**
	 * 提交质检报告
	 * 
	 * @return
	 */
	public String doSubmit() {
		String verify = qc.getVerify();
		qc = service.find(id);
		int timeOutFlag = service.isTimeout(qc) ? 1 : 0;
		qc.setTimeOutFlag(timeOutFlag);
		qc.setStatus(QcEntity.PROCESSED_STATE); // 点击提交后质检状态转为已处理状态
		qc.setVerify(verify);
		return doUpdate();
	}
	
	/**
	 * 关闭质检
	 */
	public String doClose() {
		String complaintId = request.getParameter("id");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		qc = (QcEntity) service.get(paramMap);
		id = qc.getId();
		int timeOutFlag = service.isTimeout(qc) ? 1 : 0;
		qc.setTimeOutFlag(timeOutFlag);
		qc.setStatus(QcEntity.PROCESSED_STATE); // 点击提交后质检状态转为已处理状态
		qc.setVerify("其他-咨询");

		QcQuestionEntity question = new QcQuestionEntity();
		question.setBigClassId(50);
		question.setBigClassName("其他");
		question.setSmallClassId(35);
		question.setSmallClassName("客人咨询");
		question.setCompLevel(0);
		question.setConclusion("其他-咨询");
		question.setScoreLevel("无");
		question.setScoreValue(0);
		question.setScoreTarget1("无");
		question.setScoreTarget2("无");
		
		List<QcTrackerEntity> trackers = new ArrayList<QcTrackerEntity>();
		QcTrackerEntity tracker = new QcTrackerEntity();
		tracker.setImprover("无");
		tracker.setResponsiblePerson("无");
		tracker.setResponsibility("14");//修改责任归属为“其他” BOSSGT-479  1.一键关闭质检“责任归属”默认为其他；
		trackers.add(tracker);
		question.setTrackers(trackers);
		qcQuestions.add(question);

		recipientsEmails = "g-sup-res@tuniu.com";
		doUpdate();
		return "bill";
	}

	/**
	 * 更新质检报告
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String doUpdate() {
		// 获取一级责任归属
		officerDept = responsibility();
		try {
			QcReportEntity report = null;
			for (QcQuestionEntity question : qcQuestions) {
				for (QcTrackerEntity tracker : question.getTrackers()) {
					if (tracker.getImprover().equals("填写改进人")) {
						tracker.setImprover("");
					}
					if (tracker.getResponsiblePerson().equals("填写责任人")) {
						tracker.setResponsiblePerson("");
					}
				}
			}
			Map<String, Object> sqlParams = new HashMap<String, Object>();
			sqlParams.put("qid", id);
			List<QcReportEntity> reports = (List<QcReportEntity>) qcReportService.fetchList(sqlParams);
			if (reports != null && reports.size() > 0) {
				report = reports.get(reports.size()-1); // 取最新一条报告记录
				report.setQuestions(qcQuestions);
				report.setUpdateTime(new Date());
				if (refill == 0) {
					// 删除 报告原"问题"数据, 重新提交
					qcQuestionService.deleteQuestionsByReportId(report.getId());
					qcReportService.update(report);
				} else if (refill == 1) { // 重填质检报告
					qcReportService.insert(report);
					
					/* 如果是重新填写，则删除该投诉单对应的所有记分单 */
					scoreRecordService.deleteByComplaintId(qc.getComplaintId());
				}
			} else {
				report = new QcReportEntity();
				report.setQcId(id);
				report.setQuestions(qcQuestions);
				report.setBulidTime(new Date());
				report.setDelFlag(QcEntity.NORMAL_FLAG);
				qcReportService.insert(report);
			}
			List<QcQuestionEntity> qcQuestions = report.getQuestions(); // 重新插入新质检问题数据
			for (QcQuestionEntity qcQuestion : qcQuestions) {
				qcQuestion.setReportId(report.getId());
				qcQuestionService.add(qcQuestion);
				
				/* 如果是提交操作，则将数据同步到记分系统 */
				if (qcQuestion.getScoreValue() > 0 && qc.getStatus() == QcEntity.PROCESSED_STATE) {
					ScoreRecordEntity sr = new ScoreRecordEntity();
					sr.setQcDate(DateUtil.getSqlToday());
					sr.setQcPerson(user.getRealName());
					sr.setOrderId(qc.getOrderId());
					sr.setComplaintId(qc.getComplaintId());
					sr.setJiraNum("");
					sr.setQuestionClassId(qcQuestion.getSmallClassId());
					QcTrackerEntity tracker = qcQuestion.getTrackers().get(0);
					sr.setResponsiblePerson(tracker.getResponsiblePerson());
					sr.setImprover(tracker.getImprover());
					sr.setDepId1(Integer.valueOf(tracker.getResponsibility()));
					sr.setDepId2(Integer.valueOf(tracker.getRespSecond()));
					sr.setPositionId(Integer.valueOf(tracker.getPosition()));
					String target1 = qcQuestion.getScoreTarget1();
					String target2 = qcQuestion.getScoreTarget2();
					sr.setScoreTarget1(target1);
					sr.setScoreTarget2(target2);
					if (null != target1 && !"".equals(target1) && !"无".equals(target1)) {
						sr.setScoreValue1(qcQuestion.getScoreValue());
					}
					if (null != target2 && !"".equals(target2) && !"无".equals(target2)) {
						sr.setScoreValue2(qcQuestion.getScoreValue());
					}
					sr.setScoreTypeId(5); // 5：投诉质检单
					sr.setDescription(qcQuestion.getConclusion());
					scoreRecordService.insert(sr);
				}
			}
			qc.setFinishDate(new Date());
			service.update(qc);
			if (qc.getStatus() == QcEntity.PROCESSED_STATE) { // 判断是不是提交操作, 提交成功后发送邮件
				QcEntity qcEnt = service.find(id);
				if (qcEnt.getStatus() == QcEntity.PROCESSED_STATE) {
					Map<String, Object> sqlParams2 = new HashMap<String, Object>();
					sqlParams2.put("id", qcEnt.getComplaintId());
					ComplaintEntity compEnt = (ComplaintEntity) complaintService.fetchOne(sqlParams2);
					
					punishPrdService.touchRedDeal(compEnt, qcEnt, qcQuestions);
					
					String emails[] = recipientsEmails.split(";");
					String ccEmails[] = cssEmails.split(";");
					tspService.sendMail(new MailerThread(emails, ccEmails, getEmailTitle(compEnt, qcEnt), getEmailContent(qcQuestions)));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return execute();
	}
	
	private String getEmailTitle(ComplaintEntity compEnt, QcEntity qcEnt) {
		String title = "";
		String titleStart = "";
		if (compEnt.getOrderState().equals("出游前")) {
			titleStart = "出游前投诉";
		} else if (compEnt.getOrderState().equals("出游中")) {
			titleStart = "出游中投诉";
		} else if (compEnt.getOrderState().equals("出游后")) {
			titleStart = "出游后投诉";
		}
		if(compEnt.getNiuLineFlag()==1)
		{
			titleStart += "[牛人专线]";
		}
		
		//投诉等级取质检报告中质检结论1的投诉问题等级
		title = titleStart + "[" + qcQuestions.get(0).getCompLevel() + "级]["
		+ compEnt.getOrderId() + "]";
		
		String depName = "";// 事业部
		String depManager = "";// 事业部总经理
		String productManagert = "";// 产品总监
		String seniorLeader = "";// 高级经理
		String productLeader = "";// 产品经理
		if (!compEnt.getDepName().equals("")) {// 取事业部
			depName = compEnt.getDepName();
			title += "[" + depName + "]";
		}
		if (!compEnt.getDepManager().equals("")) {// 取事业部总经理
			depManager = compEnt.getDepManager();
			title += "[" + depManager + "]";
		}
		if (!compEnt.getProductManager().equals("")) {// 取产品总监
			productManagert = compEnt.getProductManager();
			title += "[" + productManagert + "]";
		}
		if (!compEnt.getSeniorManager().equals("")) {// 取高级经理
			seniorLeader = compEnt.getSeniorManager();
			title += "[" + seniorLeader + "]";
		} else if (!compEnt.getProductLeader().equals("")) {// 如果没有高级经理。取产品经理
			productLeader = compEnt.getProductLeader();
			title += "[" + productLeader + "]";
		}
		title += compEnt.getOrderState();
		title += "[投诉单号" + compEnt.getId() + "]质检报告-发送自" + qcEnt.getQcPersonName();
		
		return title;
	}
	
	@SuppressWarnings("unchecked")
	private String getEmailContent(List<QcQuestionEntity> qcQuestions) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type='text/css'>.datatable {border:1px solid #fff;border-collapse:collapse;font-size:13px;}.datatable th{border:1px solid #fff;color:#355586;background:#DFEAFB; padding:2px;}.datatable td{padding:2px;background-color: #F9F5F2;border: 1px solid #fff;}</style></head><body>");
		sb.append("<table width='1200' class='datatable'><tr><th align='right' width='150'>问题描述：</th><td colspan='7'><table style='border-collapse: collapse;font-size:12px;'>");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", qc.getComplaintId());
		List<ComplaintReasonEntity> complaintReasons = (List<ComplaintReasonEntity>) reasonService.fetchList(paramMap);
		for (ComplaintReasonEntity reason : complaintReasons) {
			sb.append("<tr><td width='195'>").append(reason.getType()).append("&nbsp;-&nbsp;").append(reason.getSecondType())
			.append("</td><td>").append(reason.getTypeDescript()).append("</td></tr>");
		}
		sb.append("</table></td></tr><tr><th align='right' width='150'>核实情况：</th><td colspan='7'>").append(CommonUtil.replaceEnter(qc.getVerify())).append("</td></tr>");//核实情况邮件内容换行转义
		for (int i=0; i<qcQuestions.size(); i++) {
			QcQuestionEntity question = qcQuestions.get(i);
			sb.append("<tr><td colspan='8'><b>质检结论").append(i+1).append("</b></td></tr>")
			.append("<tr><th align='right' width='300'>问题类型：</th><td width='150'>").append(question.getBigClassName()).append("—").append(question.getSmallClassName())
			.append("</td><th align='right' width='150'>投诉问题等级：</th><td colspan='5'>");
			if (question.getCompLevel() != -1) {
				sb.append(question.getCompLevel()).append("&nbsp;级");
			}
			sb.append("</td></tr><tr><th align='right' width='150'>质检结论：</th><td colspan='7'>").append(CommonUtil.replaceEnter(question.getConclusion()))//质检结论邮件内容换行转义
			.append("</td></tr><tr><th align='right' width='150'>记分等级：</th><td>").append(question.getScoreLevel())
			.append("</td><th align='right' width='150'>记分值：</th><td width='75'>");
			if (0 == question.getScoreValue()) {
				sb.append("无");
			} else {
				sb.append(question.getScoreValue());
			}
			sb.append("</td><th align='right' width='150'>记分对象1：</th><td width='75'>").append(question.getScoreTarget1().trim().equals("")?"无":question.getScoreTarget1())
			.append("</td><th align='right' width='150'>记分对象2：</th><td width='75'>").append(question.getScoreTarget2().trim().equals("")?"无":question.getScoreTarget2()).append("</td></tr>");
			if ("一级乙等-红线".equals(question.getScoreLevel())) {
				sb.append("<tr><th align='right' width='150'>出发地：</th><td>").append(question.getStartCity())
				.append("</td><th align='right' width='150'>目的地：</th><td>").append(question.getEndCity())
				.append("</td><th align='right' width='150'>机票价格（元）：</th><td>").append(question.getAirfare())
				.append("</td><th align='right' width='150'>产品价格（元）：</th><td>").append(question.getProductPrice())
				.append("</td></tr>");
			}
			
			List<QcTrackerEntity> trackerList = question.getTrackers();
			for (int j=0; j<trackerList.size(); j++) {
				QcTrackerEntity tracker = trackerList.get(j);
				String responsibility = getresponsibilities(tracker);
				String position = "无";
				if (!"0".equals(tracker.getPosition())) {
					position = (String) positionMap.get(tracker.getPosition());
				}
				sb.append("<tr><th align='right' width='150'>责任归属").append(j+1).append("：</th><td>")
				.append(responsibility).append("</td><th align='right' width='150'>执行岗位：</th><td>").append(position)
				.append("</td><th align='right' width='150'>责任人：</th><td>").append(tracker.getResponsiblePerson().trim().equals("")?"无":tracker.getResponsiblePerson())
				.append("</td><th align='right' width='150'>改进人：</th><td>").append(tracker.getImprover().trim().equals("")?"无":tracker.getImprover()).append("</td></tr>");
			}
		}
		sb.append("</table></body></html>");
		
		//System.out.println(sb.toString());		
		return sb.toString();
	}
	
	public String toSpecialConsultation() {
		String idStr = request.getParameter("ids"); // 获得所有提交的id
		String[] idArray = idStr.split(",");
		for(int index=0;index<idArray.length;index++){
			Map paramMap = new HashMap();
			paramMap.put("id", idArray[index]);
			QcEntity entity = (QcEntity) service.get(paramMap);
			Integer complaintId = entity.getComplaintId();
			Map map = new HashMap();
			map.put("id", complaintId);
			entity.setSpecialConsultation(1);
			ComplaintEntity ce = (ComplaintEntity) complaintService.get(map);
			service.assignQcPerson(entity, ce);
			service.update(entity);
			
//			/* 发起质检到质量管理系统 */
//			ComplaintVo cmp = complaintService.getCmpBillInfo(complaintId);
//			QcVo qcVo = new QcVo();
//			qcVo.setPrdId(ce.getRouteId());
//			qcVo.setGroupDate(ce.getStartTime().getTime());
//			qcVo.setOrdId(ce.getOrderId());
//			qcVo.setCmpId(ce.getId());
//			qcVo.setQcAffairDesc(getQcAffairDesc(cmp.getReasonList()));
//			ComplaintRestClient.addQcBill(qcVo);
		}
		return "success";
	}
	
	private String getQcAffairDesc(List<ComplaintReasonVo> reasonList) {
		StringBuffer sb = new StringBuffer();
		for (ComplaintReasonVo r : reasonList) {
			sb.append(r.getType()).append("-").append(r.getSecondType())
			.append("：").append(r.getTypeDescript()).append("<br>");
		}
		return sb.toString();
	}
	
	/**
	 * 批量分配任务
	 * 
	 * @return
	 */
	public String doBatchAssign() {
		
		String idStr = request.getParameter("ids"); // 获得所有提交的id
		String[] ids = idStr.split(",");
		String userName = request.getParameter("username");
		String userId = request.getParameter("batchAssignSel");
		String flag = request.getParameter("assignFlag");
		if (null == ids || null == userId || userId.length() == 0 || "0".equals(userId)) {
			return ERROR;
		}
		try {
			Map<String, Object> sqlParams = new HashMap<String, Object>();
			for (String id : ids) {
				QcEntity updateByOrderId = new QcEntity();
				int qid = Integer.parseInt(id);
				sqlParams.put("id", qid);
				QcEntity qc = (QcEntity) service.get(sqlParams);
				if (flag.equals("assignNew")) {// 重新分配
					qc.setOldQcPerson(qc.getQcPerson());
					qc.setOldQcPersonName(qc.getQcPersonName());
					qc.setAssociater(0);// 把交接人清除
					qc.setAssociaterName("");
					updateByOrderId.setOldQcPerson(qc.getQcPerson());
					updateByOrderId.setOldQcPersonName(qc.getQcPersonName());
				}
				// 分配质检人
				qc.setQcPerson(Integer.parseInt(userId));
				qc.setQcPersonName(userName);
				qc.setStatus(QcEntity.PROCESSING_STATE); // 分配后，转换为处理中状态
				qc.setDistributionDate(new Date());
				qc.setUpdateTime(new Date());
				service.update(qc);
				
				/* 将该订单号的所有投诉转给质检人，状态为
				updateByOrderId.setOrderId(qc.getOrderId());
				updateByOrderId.setQcPerson(Integer.parseInt(userId));
				updateByOrderId.setQcPersonName(userName);
				updateByOrderId.setDistributionDate(new Date());
				updateByOrderId.setUpdateTime(new Date());
				updateByOrderId.setStatus(QcEntity.PROCESSING_STATE); // 分配后，转换为处理中状态
				service.updateByOrderId(updateByOrderId);*/
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "success";
	}

	/**
	 * 分配任务
	 * 
	 * @return
	 */
	public String doAssign() {
		QcEntity updateByOrderId = new QcEntity();
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("id", request.getParameter("qid"));
		QcEntity qc = (QcEntity) service.get(sqlParams);
		if (null == qc) {
			return ERROR;
		}
		// 获取质检人
		String user = "assignSelValue" + request.getParameter("qid");
		String userId = request.getParameter(user);
		String userName = request.getParameter("username");
		String flag = request.getParameter("assignFlag");
		if (null == userId || userId.length() == 0) {
			return ERROR;
		}
		if (flag.equals("assignNew")) {// 重新分配
			qc.setOldQcPerson(qc.getQcPerson());
			qc.setOldQcPersonName(qc.getQcPersonName());
			qc.setAssociater(0);// 把交接人清除
			qc.setAssociaterName("");
			updateByOrderId.setOldQcPerson(qc.getQcPerson());
			updateByOrderId.setOldQcPersonName(qc.getQcPersonName());
		}
		qc.setQcPerson(Integer.parseInt(userId));
		qc.setQcPersonName(userName);
		qc.setDistributionDate(new Date());
		qc.setUpdateTime(new Date());
		qc.setStatus(QcEntity.PROCESSING_STATE); // 分配后，转换为处理中状态
		try {
			service.update(qc);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		/* 将该订单号的所有投诉转给质检人
		updateByOrderId.setOrderId(qc.getOrderId());
		updateByOrderId.setQcPerson(Integer.parseInt(userId));
		updateByOrderId.setQcPersonName(userName);
		updateByOrderId.setDistributionDate(new Date());
		updateByOrderId.setUpdateTime(new Date());
		updateByOrderId.setStatus(QcEntity.PROCESSING_STATE); // 分配后，转换为处理中状态
		try {
			service.updateByOrderId(updateByOrderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}*/
		return "success";
	}

	/**
	 * 认领任务
	 * 
	 * @return
	 */
	public String doClaim() {
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("id", request.getParameter("qid"));
		QcEntity qc = (QcEntity) service.get(sqlParams);
		qc.setQcPerson(user.getId());
		qc.setQcPersonName(user.getRealName());
		qc.setUpdateTime(new Date());
		qc.setStatus(QcEntity.PROCESSING_STATE); // 认领后，转换为处理中状态
		try {
			service.update(qc);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return execute();
	}

	/**
	 * 交接任务
	 * 
	 * @return
	 */
	public String doHandover() {
		String[] ids = request.getParameterValues("ids"); // 获得所有提交的id
		String userName = request.getParameter("username");
		String userId = request.getParameter("handoverSel");
		if (ids == null || userId == null || userId.length() == 0) {
			return ERROR;
		}
		try {
			Map<String, Object> sqlParams = new HashMap<String, Object>();
			for (String id : ids) {
				int qid = Integer.parseInt(id);
				sqlParams.put("id", qid);
				QcEntity qc = (QcEntity) service.get(sqlParams);
				// 分配交接人
				qc.setAssociater(Integer.parseInt(userId));
				qc.setAssociaterName(userName);

				service.update(qc);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return execute();
	}

	/**
	 * 标记为重要单子
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String markOrder() throws ServletException, IOException {
		//redirctUrl = request.getParameter("curUrl");
		String complaintId = request.getParameter("complaintId");
		// 标记质检数据
		QcEntity qc = new QcEntity();
		qc.setComplaintId(Integer.parseInt(complaintId));
		qc.setImpFlag(QcEntity.IMPT_FLAG);
		qc.setCheckFlag(null);
		qc.setDistributionDate(null);
		qc.setFinishDate(null);
		qc.setUpdateTime(null);
		service.updateByComplaintId(qc);
		return QC_LIST;
	}
	
	/**
	 * 取消标记质检数据
	 * @return
	 */
	public String cancelMarkOrder(){
		//redirctUrl = request.getParameter("curUrl");
		String complaintId = request.getParameter("complaintId");
		// 取消标记质检数据
		QcEntity qc = new QcEntity();
		qc.setComplaintId(Integer.parseInt(complaintId));
		qc.setImpFlag(QcEntity.NO_IMPT_FLAG);
		qc.setCheckFlag(null);
		qc.setDistributionDate(null);
		qc.setFinishDate(null);
		qc.setUpdateTime(null);
		service.updateByComplaintId(qc);
		return QC_LIST;
	}

	/**
	 * execute
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		this.perPage = 30;
		
		if(!CollectionUtil.isNotEmpty(bdpNames)) {
			bdpNames = complaintService.getBdpNames();
		}
		
		super.execute(getRequestParameter());

		List<QcEntity> qcList = (List<QcEntity>) request.getAttribute("dataList");
		List<QcReportEntity> reports = new ArrayList<QcReportEntity>();
		for (QcEntity qc : qcList) {
			reports = qcReportService.getReportListByQid(qc.getId());
			qc.setQcReports(reports);
			if (reports.size() == 0) { // 未发送质检报告
				if (service.isTimeout(qc)) {
					qc.setRed_color_flag(1);
				}
				qc.setHaveReport(0);
			} else {
				qc.setRed_color_flag(0);
				qc.setHaveReport(1);
			}
		}

		request.setAttribute("qe", qcList);
		request.setAttribute("dataList", qcList);
		jspTpl = QC_LIST;
		return jspTpl;
	}
	
	private Map<String, Object> getRequestParameter() {
		officerDept = responsibility(); // 获取一级责任归属
		isQcOfficer = appointerService.isQcOfficer(user.getId());
		
		// 同组客服，认领任务；工作交接
		sameGroupUsers = userService.getSameGroupUsers(user);
		sameGroupUsers.add(userService.getUserByName("蔡淼2"));
		sameGroupUsers.add(userService.getUserByName("黄伟2"));
		sameGroupUsers.add(userService.getUserByName("史云"));
		sameGroupUsers.add(userService.getUserByName("张晶茜"));
		sameGroupUsers.add(userService.getUserByName("魏雅妮"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> searchMap = new HashMap<String, String>();
		if (null != search) {
			searchMap = search.toMap();
		}
		paramMap.putAll(searchMap);
		paramMap.put("status", status);
		paramMap.put("consultation", 0);
		
		//status=3的时候是咨询单页签
		if ((Integer) paramMap.get("status") != 3) {
			String ids = "";
			for (UserEntity userEntity : sameGroupUsers) {
				if ("".equals(ids)) {
					ids += "'" + userEntity.getId() + "'";
				} else {
					ids += ",'" + userEntity.getId() + "'";
				}
			}

			if (!isSuperManager(user.getRealName())) {// 判断是否是超级用户
				if (isQcOfficer) {
					if (status != QcEntity.PENDING_STATE) {
						paramMap.put("qc_person", ids);
					}
				} else {
					if (status != QcEntity.PENDING_STATE) {
						paramMap.put("qc_person", user.getId());
					}
				}
			}
		} else {
			if ((Integer) paramMap.get("status") == 3) {
				paramMap.put("status", null);
				paramMap.put("consultation", 1);
			}
		}
		return paramMap;
	}

	/**
	 * 根据用户名判断是否是超级管理员
	 * 
	 * @param username
	 * @return Boolean
	 */
	public Boolean isSuperManager(String username) {
		String[] superManager = Constant.CONFIG.getProperty("superManager")
				.split(",");
		for (int i = 0; i < superManager.length; i++) {
			if (username.equals(superManager[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取责任归属中文显示
	 * 
	 * @param QcTrackerEntity
	 *            tracker
	 * @return String
	 */
	public String getresponsibilities(QcTrackerEntity tracker) {
		String responsibility = "";// 一级责任归属
		String respSecond = "";// 二级责任归属
		String respThird = "";// 三级责任归属
		if (!tracker.getResponsibility().equals("0")) {
			if (!getOldRecord(tracker.getResponsibility()).equals("")) {// 判断是否是老数据
				responsibility += getOldRecord(tracker.getResponsibility());
			} else {
				responsibility += (String) officerDept.get(tracker
						.getResponsibility());
				if (!tracker.getRespSecond().equals("0")) {
					tracker.setRespSecond(getDepartmentNameById(Integer
							.valueOf(tracker.getRespSecond())));
					respSecond = tracker.getRespSecond();
					responsibility += "/" + respSecond;
					if (!tracker.getRespThird().equals("0")) {
						tracker.setRespThird(getDepartmentNameById(Integer
								.valueOf(tracker.getRespThird())));
						respThird = tracker.getRespThird();
						responsibility += "/" + respThird;
					}
				}
			}
			if (!responsibility.equals("")) {
				responsibility += " ";
			} else {
				responsibility += "无 ";
			}
		} else {
			responsibility += "无 ";
		}
		return responsibility;
	}

	/**
	 * 获取责任归属历史信息
	 * 
	 * @param String
	 *            mapId
	 * @return String
	 */
	public String getOldRecord(String mapId) {
		String returnStr = "";
		if (Integer.valueOf(mapId) > 0 && Integer.valueOf(mapId) < 14) {
			returnStr = officerDeptOld.get(mapId);
		}
		return returnStr;
	}

	/**
	 * 根据部门号获取部门名
	 * 
	 * @param int depId
	 * @return String depName
	 */
	public String getDepartmentNameById(int depId) {
		DepartmentEntity department = departmentService
				.getDepartmentById(depId);
		if (department != null) {
			return department.getDepName();
		} else {
			return "";
		}
	}

	/**
	 * 获取一级责任归属
	 * 
	 * @return
	 */
	public Map<String, Object> responsibility() {
		Map<String, Object> responsibility = new LinkedHashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fatherId", 1);
		// 获取一级部门
		@SuppressWarnings("unchecked")
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService
				.fetchList(paramMap);
		for (DepartmentEntity department : departments) {
			if (department.getFatherId() == 1
					&& !department.getDepName().equals("其它人员")
					&& department.getDelFlag() == 0) {
				responsibility.put(String.valueOf(department.getId()),
						department.getDepName());
			}
		}
		responsibility.put("1", "不可抗力");
		responsibility.put("2", "系统/流程问题");
		responsibility.put("14", "其他");
		return responsibility;
	}

	/**
	 * 获取一级责任归属
	 * 
	 * @return
	 */
	public Map<String, Object> responsibilityAll() {
		Map<String, Object> responsibility = new LinkedHashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fatherId", 1);
		// 获取一级部门
		@SuppressWarnings("unchecked")
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService
				.fetchList(paramMap);
		for (DepartmentEntity department : departments) {
			if (department.getFatherId() == 1
					&& !department.getDepName().equals("其它人员")) {
				responsibility.put(String.valueOf(department.getId()),
						department.getDepName());
			}
		}
		responsibility.put("1", "不可抗力");
		responsibility.put("2", "系统/流程问题");
		responsibility.put("14", "其他");
		return responsibility;
	}

	/**
	 * 获取下级责任归属
	 * 
	 * @return
	 */
	public String getDeparments() {
		int depId = Integer.valueOf(request.getParameter("depId"));
		Map<String, Object> responsibility = new LinkedHashMap<String, Object>();
		responsibility = getDeparmentsById(depId);
		String json = JSONArray.fromObject(responsibility).toString();
		// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个视图
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置字符集
		response.setContentType("text/plain");// 设置输出为文字流
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			// 直接输出响应的内容
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return QC_LIST;
	}

	/**
	 * 获取下级责任归属
	 * 
	 * @return
	 */
	public String getDeparmentsAll() {
		int depId = Integer.valueOf(request.getParameter("depId"));
		Map<String, Object> responsibility = new LinkedHashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fatherId", depId);
		@SuppressWarnings("unchecked")
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService
				.fetchList(paramMap);
		DepartmentEntity department;
		for (int i = 0; i < departments.size(); i++) {
			department = departments.get(i);
			if (department.getFatherId() == depId) {
				responsibility.put(String.valueOf(department.getId()),
						department.getDepName());
			}
		}
		String json = JSONArray.fromObject(responsibility).toString();
		// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个视图
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置字符集
		response.setContentType("text/plain");// 设置输出为文字流
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			// 直接输出响应的内容
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return QC_LIST;
	}

	/**
	 * 根据部门号获取部门名
	 * 
	 * @param depId
	 * @return
	 */
	public Map<String, Object> getDeparmentsById(int depId) {
		Map<String, Object> responsibility = new LinkedHashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fatherId", depId);
		@SuppressWarnings("unchecked")
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService
				.fetchList(paramMap);
		DepartmentEntity department;
		for (int i = 0; i < departments.size(); i++) {
			department = departments.get(i);
			if (department.getFatherId() == depId
					&& department.getDelFlag() == 0) {
				responsibility.put(String.valueOf(department.getId()),
						department.getDepName());
			}
		}
		return responsibility;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public QcEntity getQc() {
		request.getAttribute("");
		return qc;
	}

	public void setQc(QcEntity qc) {
		this.qc = qc;
	}

	public IQcQuestionClassService getQcQuestionClassService() {
		return qcQuestionClassService;
	}

	public List<QcQuestionClassEntity> getQcQuestionClasses() {
		return qcQuestionClasses;
	}

	public void setQcQuestionClasses(
			List<QcQuestionClassEntity> qcQuestionClasses) {
		this.qcQuestionClasses = qcQuestionClasses;
	}

	public List<QcQuestionEntity> getQcQuestions() {
		return qcQuestions;
	}

	public void setQcQuestions(List<QcQuestionEntity> qcQuestions) {
		this.qcQuestions = qcQuestions;
	}

	public Boolean getIsQcOfficer() {
		return isQcOfficer;
	}

	public void setIsQcOfficer(Boolean isQcOfficer) {
		this.isQcOfficer = isQcOfficer;
	}

	public List<UserEntity> getSameGroupUsers() {
		return sameGroupUsers;
	}

	public void setSameGroupUsers(List<UserEntity> sameGroupUsers) {
		this.sameGroupUsers = sameGroupUsers;
	}

	public List<UserEntity> getProductManagers() {
		return productManagers;
	}

	public void setProductManagers(List<UserEntity> productManagers) {
		this.productManagers = productManagers;
	}

	public Map<String, Object> getOfficerDept() {
		return officerDept;
	}

	public void setOfficerDept(Map<String, Object> officerDept) {
		this.officerDept = officerDept;
	}

	public Map<String, Object> getPositionMap() {
		return positionMap;
	}

	public void setPositionMap(Map<String, Object> positionMap) {
		this.positionMap = positionMap;
	}

	public Set<Integer> getQuestionChecked() {
		return questionChecked;
	}

	public void setQuestionChecked(Set<Integer> questionChecked) {
		this.questionChecked = questionChecked;
	}

	public String getCssEmails() {
		return cssEmails;
	}

	public void setCssEmails(String cssEmails) {
		this.cssEmails = cssEmails;
	}

	public String getRecipientsEmails() {
		return recipientsEmails;
	}

	public void setRecipientsEmails(String recipientsEmails) {
		this.recipientsEmails = recipientsEmails;
	}

	public int getRefill() {
		return refill;
	}

	public void setRefill(int refill) {
		this.refill = refill;
	}

	public List<String> getBdpNames() {
		return bdpNames;
	}

	public void setBdpNames(List<String> bdpNames) {
		this.bdpNames = bdpNames;
	}
}
