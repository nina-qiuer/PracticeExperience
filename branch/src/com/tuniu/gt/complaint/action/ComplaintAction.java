package com.tuniu.gt.complaint.action;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.HttpClientHelper;
import com.tuniu.gt.complaint.entity.AgencyDto;
import com.tuniu.gt.complaint.entity.AgencyToChatEntity;
import com.tuniu.gt.complaint.entity.AgentStatusLog;
import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.entity.CheckEmailEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.ComplaintReleaserEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.FollowNoteThEntity;
import com.tuniu.gt.complaint.entity.FollowUpRecordEntity;
import com.tuniu.gt.complaint.entity.HandlerResult;
import com.tuniu.gt.complaint.entity.OrderEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.QcPointEntity;
import com.tuniu.gt.complaint.entity.ReasonTypeEntity;
import com.tuniu.gt.complaint.entity.ReceiverEmailEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.entity.TaskReminderEntity;
import com.tuniu.gt.complaint.enums.DealDepartEnum;
import com.tuniu.gt.complaint.enums.OrderStateEnum;
import com.tuniu.gt.complaint.enums.SpecialListTypeEnum;
import com.tuniu.gt.complaint.mq.ComplaintResultMQProducer;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IAgencyCommitService;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.complaint.service.IAttachService;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.service.ICheckEmailService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintReleaserService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IComplaintTaskReminderService;
import com.tuniu.gt.complaint.service.IComplaintThirdPartService;
import com.tuniu.gt.complaint.service.IFollowNoteThService;
import com.tuniu.gt.complaint.service.IFollowUpRecordService;
import com.tuniu.gt.complaint.service.IQcNoteService;
import com.tuniu.gt.complaint.service.IQcPointService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.service.IReasonTypeService;
import com.tuniu.gt.complaint.service.IReceiverEmailService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.complaint.service.impl.OrderService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.complaint.vo.CmpImproveVo;
import com.tuniu.gt.complaint.vo.ComplaintReasonVo;
import com.tuniu.gt.complaint.vo.ComplaintSearchVo;
import com.tuniu.gt.complaint.vo.ComplaintVo;
import com.tuniu.gt.complaint.vo.QcVo;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.uc.entity.DepuserMapEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.gt.uc.service.user.IDepuserMapService;
import com.tuniu.gt.warning.common.Page;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;
import com.tuniu.trest.TRestException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Constant;

@Service("complaint_action-complaint")
@Scope("prototype")
public class ComplaintAction extends FrmBaseAction<IComplaintService, ComplaintEntity> {
	
	private static final long serialVersionUID = 1L;

	private static String nbHistoryURL = Constant.CONFIG.getProperty("NB_QUERYHISTORY");//查询咨询投诉沟通单据的群组聊天记录
	
	private static String nbComplaintURL = Constant.CONFIG.getProperty("NB_QUERYCOMPLAINT");//查询咨询投诉沟通单据的群组聊天列表
	
	private static String bossBillURL = Constant.CONFIG.getProperty("BOSS_BILL");//查询BOSS3订单
	
	private static String otherBillURL = Constant.CONFIG.getProperty("crm_order_url");//除BOSS3外的订单
	
	private static Logger logger = Logger.getLogger(ComplaintAction.class);
	
	private List<String> dealDepartments;

	private List<String> upgradeReasons;
	
	public ComplaintAction() {
		setManageUrl("complaint");
		info = new HashMap<String, Object>();
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	public void setService(IComplaintService service) {
		this.service = service;
	};

	@Autowired
	@Qualifier("uc_service_user_impl-depuser_map")
	private IDepuserMapService  depMapService;
	
	// 引入投诉事宜service
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;

	private ComplaintReasonEntity complaintReasonEntity;
	private List<ComplaintReasonEntity> complaintReasonEntityList;

	// 引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_follow_up_record_impl-follow_up_record")
	private IFollowUpRecordService followUpRecordService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService followNoteService;

	// 引入核实请求service
	@Autowired
	@Qualifier("complaint_service_complaint_check_email_impl-check_email")
	private ICheckEmailService checkEmailService;
	
	// 上传文件
	@Autowired
	@Qualifier("complaint_service_impl-attach")
	private IAttachService attachService;

	// 引入解决方案service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	private IComplaintSolutionService complaintSolutionService;

	// 引入分担方案service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;

	// 用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;

	// qc service
	@Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;

	// qc service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-appoint_manager")
	private IAppointManagerService appointManagerService;

	private Boolean isCtOfficer = false; // 是否为投诉负责人，质检负责人可以分配质检人
	private List<UserEntity> sameGroupUsers = new ArrayList<UserEntity>(); // 同组用户

	// department service
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-reason_type")
	private IReasonTypeService reasonTypeService;

	// 邮件配置service
	@Autowired
	@Qualifier("complaint_service_impl-receiver_email")
	private IReceiverEmailService receiverEmailService;

	@Autowired
	private ComplaintResultMQProducer complaintResultMQProducer;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-auto_assign")
	private IAutoAssignService autoAssignService;
	
	@Autowired
	@Qualifier("complaint_service_impl-follow_note_th")
	private IFollowNoteThService noteThService;
	
	@Autowired
	@Qualifier("complaint_service_impl-complaint_releaser")
	private IComplaintReleaserService releaserService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	private IComplaintSolutionService solutionService;
	
	@Autowired
	private IAgencyCommitService  agencyCommitService;
	
	@Autowired
	private IQcPointService qcPointService;
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	@Autowired
	private IComplaintTaskReminderService cmpTaskReminderService;
	
	@Autowired
	@Qualifier("complaint_service_impl-qc_note")
	private IQcNoteService qcNoteService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_third_part")
	private IComplaintThirdPartService complaintThirdPartService;
	
	@Autowired
    private OrderService orderService;
	
	private ComplaintSearchVo search;

	private String reasonId;
	
	private String accuracy;
	
	private String accuracyRe;
	
	private List<File> toolFile;// 质量工具附件
	
	private List<String> toolFileContentType;// 文件名+ContentType
	
	private List<String> toolFileFileName;// 文件名+FileName
	
	private List<File> cmpAttach;
	
	private List<String> cmpAttachFileName;
	
	private String[] orderIds;
	
	private Map<String,Object> info;
	
	private Page page;
	
	private Integer canLink;
	
	private int isHotel = 0; //无订单是否为酒店投诉
	
	private int specialEventFlag = 0;//无订单投诉是否为特殊事件

	public List<File> getCmpAttach() {
		return cmpAttach;
	}

	public void setCmpAttach(List<File> cmpAttach) {
		this.cmpAttach = cmpAttach;
	}

	public List<String> getCmpAttachFileName() {
		return cmpAttachFileName;
	}

	public void setCmpAttachFileName(List<String> cmpAttachFileName) {
		this.cmpAttachFileName = cmpAttachFileName;
	}

	public String[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}

	public ComplaintSearchVo getSearch() {
		return search;
	}

	public void setSearch(ComplaintSearchVo search) {
		this.search = search;
	}

	public List<String> getToolFileContentType() {
		return toolFileContentType;
	}

	public void setToolFileContentType(List<String> toolFileContentType) {
		this.toolFileContentType = toolFileContentType;
	}

	public List<String> getToolFileFileName() {
		return toolFileFileName;
	}

	public void setToolFileFileName(List<String> toolFileFileName) {
		this.toolFileFileName = toolFileFileName;
	}

	public List<File> getToolFile() {
		return toolFile;
	}

	public void setToolFile(List<File> toolFile) {
		this.toolFile = toolFile;
	}

	public String getAccuracyRe() {
		return accuracyRe;
	}

	public void setAccuracyRe(String accuracyRe) {
		this.accuracyRe = accuracyRe;
	}
	
	public int getSpecialEventFlag() {
		return specialEventFlag;
	}

	public void setSpecialEventFlag(int specialEventFlag) {
		this.specialEventFlag = specialEventFlag;
	}

	/**
	 * 发起投诉时，是否分配给自己处理
	 */
	private int dealBySelf = 0;
	
	/**
	 * 为精确查询state=3的时候，需要判断子页签
	 * @param paramMap
	 * @return
	 */
	private Map getSecondType(Map paramMap){
		List<Integer> ids = null;
		if(paramMap.get("tab_flag") != null && !"".equals(paramMap.get("tab_flag"))){
			String second_type = null;
			paramMap.put("normal", null);
			paramMap.put("insurance", null);
			paramMap.put("returnGood", null);
			if("normal".equals(paramMap.get("tab_flag"))){
				second_type = "";
				paramMap.put("normal", "normal");
				ids = service.getComplaintIdsByCon(second_type);
			}else if("insurance".equals(paramMap.get("tab_flag"))){
				second_type = "退货";
				List<Integer> reIds = service.getComplaintIdsByCon(second_type);
				second_type = "保险理赔";
				paramMap.put("insurance", "insurance");
				ids = service.getComplaintIdsByCon(second_type);
				for(Integer i:reIds){  
			        if(ids.contains(i)){  
			        	ids.remove(i);  
			        }  
				}  
			}else if("returnGood".equals(paramMap.get("tab_flag"))){
				second_type = "退货";
				paramMap.put("returnGood", "returnGood");
				ids = service.getComplaintIdsByCon(second_type);
			}
			
			String idss = ids.toString().replace("[", "").replace("]", "");
			paramMap.put("canUseIds", idss);
		}
		return paramMap;
	}
	
	/**
	 * 提醒工作台，需要判断子页签
	 * @param paramMap
	 * @return
	 */
	private Map<String, Object> getParamByCon(Map<String, Object> paramMap){
		if(paramMap.get("tab_flag") != null && !"".equals(paramMap.get("tab_flag"))){
			if("queryAll".equals(paramMap.get("tab_flag"))){
			}else if("waitFirstCall".equals(paramMap.get("tab_flag"))){
				paramMap.put("inProcessState", "0");
				if("-1".equals(paramMap.get("stateWork"))){
					paramMap.put("state","2");
				}else{
					paramMap.put("state",paramMap.get("stateWork"));
				}
			}else if("waitFollowUp".equals(paramMap.get("tab_flag"))){
				paramMap.put("minFollowTime", new Date());
				if("-1".equals(paramMap.get("stateWork"))){
					paramMap.put("state","2,3");
				}else{
					paramMap.put("state",paramMap.get("stateWork"));
				}
			}else if("timeOutNeedFollowUp".equals(paramMap.get("tab_flag"))){
				paramMap.put("firstWorkdayOvertime", new Date());
				if("-1".equals(paramMap.get("stateWork"))){
					paramMap.put("state","2");
				}else{
					paramMap.put("state",paramMap.get("stateWork"));
				}
			}else if("timeOutNeedEnd".equals(paramMap.get("tab_flag"))){
				paramMap.put("secondWorkdayOvertime", new Date());
				if("-1".equals(paramMap.get("stateWork"))){
					paramMap.put("state","2,3");
				}else{
					paramMap.put("state",paramMap.get("stateWork"));
				}
			}else if("waitHandOver".equals(paramMap.get("tab_flag"))){
				boolean isLeader = appointManagerService.isCtOfficer(user.getId());
				if(isLeader){
					paramMap.put("leader", "all");
				}else{
					paramMap.put("associater", user.getId());
				}
				if("-1".equals(paramMap.get("stateWork"))){
					paramMap.put("state","2,3");
				}else{
					paramMap.put("state",paramMap.get("stateWork"));
				}
			}else if("repeatedComplaints".equals(paramMap.get("tab_flag"))){
				paramMap.put("isSticky", "2");
				if("-1".equals(paramMap.get("stateWork"))){
					if((paramMap.get("startBuildDate") != null && !"".equals(paramMap.get("startBuildDate"))) || (paramMap.get("startBuildDate") != null && !"".equals(paramMap.get("startBuildDate")))){
						paramMap.put("state","1,2,3,4,5,6,7,9,10");
					}else{
						paramMap.put("state","1,2");
					}
					
				}else{
					paramMap.put("state",paramMap.get("stateWork"));
				}
				
				if(paramMap.get("startBuildDate") == null || "".equals(paramMap.get("startBuildDate"))){
					Calendar calendar = Calendar.getInstance();
					Date date = new Date();
					calendar.setTime(date);
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					date = calendar.getTime();
					paramMap.put("startBuildDate", DateUtil.formatDate(date)+" 23:00:00");
				}
				if(paramMap.get("endBuildDate") == null || "".equals(paramMap.get("endBuildDate"))){
					paramMap.put("endBuildDate",DateUtil.formatDate(new Date())+" 22:59:59");
				}
			}else if("addComplaintMatter".equals(paramMap.get("tab_flag"))){
				paramMap.put("isSticky", "1,3");
				if("-1".equals(paramMap.get("stateWork"))){
					if((paramMap.get("startBuildDate") != null && !"".equals(paramMap.get("startBuildDate"))) || (paramMap.get("startBuildDate") != null && !"".equals(paramMap.get("startBuildDate")))){
						paramMap.put("state","1,2,3,4,5,6,7,9,10");
					}else{
						paramMap.put("state","1,2");
					}
				}else{
					paramMap.put("state",paramMap.get("stateWork"));
				}
				if(paramMap.get("startBuildDate") == null || "".equals(paramMap.get("startBuildDate"))){
					Calendar calendar = Calendar.getInstance();
					Date date = new Date();
					calendar.setTime(date);
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					date = calendar.getTime();
					paramMap.put("startBuildDate", DateUtil.formatDate(date)+" 23:00:00");
				}
				if(paramMap.get("endBuildDate") == null || "".equals(paramMap.get("endBuildDate"))){
					paramMap.put("endBuildDate",DateUtil.formatDate(new Date())+" 22:59:59");
				}
			}
		}else{
			paramMap.put("inProcessState", "0");
			paramMap.put("state","2");
		}
		return paramMap;
	}
	
	/**
	 * 返回主页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String execute() {
		this.setPageTitle("投诉处理列表");
		// 投诉处理列表每页显示个数为30
		this.perPage = 30;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap = paramSearch();
		
		String res = "";
		//投诉提醒工作台
		if("query".equals(request.getParameter("act"))){
			paramMap = getParamByCon(paramMap);
			res = "complaint_list_query";
		} else {
			if("2".equals(paramMap.get("state")+"")) { // 对客达成、未达成
				if ("custUnAgreed".equals(paramMap.get("tab_flag"))) {
					paramMap.put("custAgreeFlag", 0);
				} else if("custAgreed".equals(paramMap.get("tab_flag"))) {
					paramMap.put("custAgreeFlag", 1);
				}
			}
			if("3".equals(paramMap.get("state")+"")) { // 保险，非保险
				paramMap = getSecondType(paramMap);
			}
			res = "list";
		}
		// 调用父类方法返回页面列表 
		super.execute(paramMap);
		List<ComplaintEntity> compList  = (List<ComplaintEntity>) request.getAttribute("dataList");
		List<ComplaintEntity> list =new ArrayList<ComplaintEntity>();		
		//调用NB接口获取投诉单沟通状态
		if("2".equals(paramMap.get("state")+"")){//投诉处理中
			List<Integer> complaintList =new ArrayList<Integer>();
			Map<String, Object> map =new HashMap<String, Object>();
			if(compList.size()>0){
				
				for(int k=0;k<compList.size();k++){
					
					ComplaintEntity complaint = compList.get(k);
					complaintList.add(complaint.getId());
					
				}
				map.put("complaintIds", complaintList);
				map.put("username", "tuniu-"+user.getUserName());
				List<AgencyToChatEntity> chatList = queryCommitType(map);
				if(chatList.size()>0){
					
					for(int i=0;i<compList.size();i++){
						
						ComplaintEntity complaint = compList.get(i);
						for(int j=0;j<chatList.size();j++){
							
							AgencyToChatEntity agency =chatList.get(j);
							if(String.valueOf(complaint.getId()).equals(agency.getComplaintId())){
								
								complaint.setCommitStatusName(agency.getStatusName());
								complaint.setCommitFlag(agency.getTimeOutflag());
								complaint.setCommitFlagName(agency.getTimeOut());
							}
						}
						
						list.add(complaint);
					}
					
					request.setAttribute("dataList", list);
					
				}else{
					
					request.setAttribute("dataList", compList);
				}
				
			}else{
				
				request.setAttribute("dataList", compList);
				
			}
			
			
		}else{
			
			request.setAttribute("dataList", compList);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custIds", getCustIds(compList));
		map.put("tagTypes", "11");
		JSONArray dataArray = service.getCustTagsByCustIds(map);
		request.setAttribute("tagList", dataArray);
		request.setAttribute("currTime", new Date());
		request.setAttribute("stab_flag", paramMap.get("tab_flag")== null || paramMap.get("tab_flag").equals("")?"waitFirstCall":paramMap.get("tab_flag"));
		
		if (paramMap.containsKey("repeatOrderIdComplaint")) {
			res = "repeatOrderIdComplaintList";
		}

		return res;
	}
	
	private String getCustIds(List<ComplaintEntity> getDatatList) {
		String custIds = "";
		for (ComplaintEntity complaintEntity : getDatatList) {
			Integer custId = complaintEntity.getCustId();
			if(custId == null){
				custId = 0;
			}
			custIds += custId + ",";
		}
		return custIds;
	}
	
	private void dealSameGroupUsers(List<Integer> devDepartMentList, List<Integer> typeList) {
		List<UserEntity> sguTempList = new ArrayList<UserEntity>();
		int deptId = user.getDepId();
		if (devDepartMentList.contains(deptId)) { // 是研发负责人或是值班人员
			sguTempList = userService.getCmpUsers();
		} else {
			int ingType = AppointManagerEntity.TOURING_OFFICER_TRANSFER;
			int aftType = AppointManagerEntity.AFT_OFFICER_TRANSFER;
			if (976 == deptId || 977 == deptId || 2280 == deptId || typeList.contains(ingType)) { // 售后组客服一组、二组或借调人员
				sguTempList.addAll(userService.getUsersByDepartmentId(976));
				sguTempList.addAll(userService.getUsersByDepartmentId(977));
				sguTempList.addAll(userService.getUsersByDepartmentId(2280));
				sguTempList.addAll(userService.selectTransferUsers(ingType));
				if (typeList.contains(ingType)) { // 如果是借调人员，还要考虑到原组织
					if (213 == deptId || 5926 == deptId || 1945 == deptId || 1946 == deptId) {
						sguTempList.addAll(userService.getUsersByDepartmentId(213));
						sguTempList.addAll(userService.getUsersByDepartmentId(5926));
					} else {
						sguTempList.addAll(userService.getAllSameGroupUsers(user)); // 获取同组的人，考虑兼职情况
					}
				}
			} else if (213 == deptId || 5926 == deptId || 1945 == deptId || 1946 == deptId ||1392==deptId || typeList.contains(aftType)) { // 资深组客服一组、二组或借调人员、外联组
				sguTempList.addAll(userService.getUsersByDepartmentId(213));
				sguTempList.addAll(userService.getUsersByDepartmentId(5926));
				sguTempList.addAll(userService.getUsersByDepartmentId(1392));//外联组
				sguTempList.addAll(userService.getUsersByDepartmentId(1945));
				sguTempList.addAll(userService.getUsersByDepartmentId(1946));
				sguTempList.addAll(userService.selectTransferUsers(aftType));
				if (typeList.contains(aftType)) { // 如果是借调人员，还要考虑到原组织
					if (976 == deptId || 977 == deptId || 2280 == deptId) {
						sguTempList.addAll(userService.getUsersByDepartmentId(976));
						sguTempList.addAll(userService.getUsersByDepartmentId(977));
						sguTempList.addAll(userService.getUsersByDepartmentId(2280));
					} else {
						sguTempList.addAll(userService.getAllSameGroupUsers(user)); // 获取同组的人，考虑兼职情况
					}
				}
			} else if (2355 == deptId || 2356 == deptId || 2357 == deptId || 2358 == deptId || 5918==deptId) {
				sguTempList.addAll(userService.getUsersByDepartmentId(1296)); // 出游前服务部
			} else {
				sguTempList.addAll(userService.getAllSameGroupUsers(user)); // 获取同组的人，考虑兼职情况
				if (typeList.contains(AppointManagerEntity.TOURING_OFFICER)) { // 如果是售后组负责人，将所有售后组借调人员设为同组人员
					sguTempList.addAll(userService.selectTransferUsers(ingType));
				}
				if (typeList.contains(AppointManagerEntity.POST_SALES_OFFICER)) { // 如果是资深组负责人，将所有资深组借调人员设为同组人员
					sguTempList.addAll(userService.selectTransferUsers(aftType));
				}
			}
		}
		
		// 去重
		for (UserEntity user : sguTempList) {
			if (!isContain(sameGroupUsers, user)) {
				sameGroupUsers.add(user);
			}
		}
	}
	
	private boolean isContain(List<UserEntity> list, UserEntity user) {
		boolean flag = false;
		for (UserEntity u : list) {
			if (user.getId().equals(u.getId())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	private String getSgUserNames() {
		StringBuffer userNames = new StringBuffer();
		if (sameGroupUsers != null && sameGroupUsers.size() > 0) {
			int userCount = sameGroupUsers.size();
			for (int i = 0; i < userCount; i++) {
				userNames.append(sameGroupUsers.get(i).getRealName());
				if (i < userCount - 1) {
					userNames.append("','");
				}
			}
		}
		return userNames.toString();
	}

	public Map<String, Object> paramSearch() {
		List<Integer> devDepartMentList = getDevDeptIdList();
		List<Integer> typeList = appointManagerService.getUserOfficerTypes(user.getId());
		dealSameGroupUsers(devDepartMentList, typeList);

		Map<String, Object> paramMap = new HashMap<String, Object>(); // search使用的map
		//Map<String, String> search = Common.getSqlMap(getRequestParam(), "search.");
		Map<String, String> searchMap = new HashMap<String, String>();
		if (null != search) {
			searchMap = search.toMap();
		}
		paramMap.putAll(searchMap);// 放入search内容

		// 判断是负责人状态
		int is_after_saler = 0; // 资深经理
		int is_before_saler = 0; //售前经理
		int is_in_saler = 0; //售后经理
		int is_special_before_saler = 0; //出游前经理
		int is_pre_saler = 0; // 售前客服
		int is_air_ticket_saler = 0; // 机票组
		int is_hotel_saler = 0; // 酒店组
		int is_distribute_saler = 0; // 分销组
		int is_traffic_saler = 0; // 分销组
		
		Set<String> dealDepartmentSet = new HashSet<String>(); // 处理岗Set
		String complaintState = "1"; // 默认显示投诉待指定列表
		String userNames = "";
		isCtOfficer = appointManagerService.isCtOfficer(user.getId()); // 投诉处理负责人
		
		List<String> preAuthJobLists = DBConfigManager.getConfigAsList("pre.auth.jobids");
		
		if (preAuthJobLists.contains(""+user.getJobId())||typeList.contains(1)) {// 判断是否为售前客服经理
			isCtOfficer = true;
			is_before_saler = 1;
			dealDepartmentSet.add("'"+Constans.BEFORE_TRAVEL+"'");
			dealDepartmentSet.add("'"+Constans.PREDETERMINE_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.VIP_DEPART+"'");
			
			//TODO 处理岗数据修复后删除 防止意外 先展示
			dealDepartmentSet.add("'"+Constans.CUST_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.STAR_CUST_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.CUSTOMER_DEPART+"'");
			
			complaintState = "1,5,6,7";
			userNames = getSgUserNames(); // 取同组下的所有客服名字，考虑到兼职的情况
		} else if (user.getJobId() == 415 || user.getJobId() == 420
				|| user.getJobId() == 24 || user.getJobId() == 135
				|| user.getJobId() == 511) {
			is_pre_saler = 1;
		}
		
		if (userNames.length() > 0) {
			paramMap.put("dealNames", "'" + userNames.toString() + "'");
			paramMap.put("customerNames", "'" + userNames.toString() + "'");
		}

		// 根据当前登录用户id，判断是否是出游前负责人
		if (typeList.contains(5)) {
			is_special_before_saler = 1;
			dealDepartmentSet.add("'"+Constans.SPECIAL_BEFORE_TRAVEL+"'");
			dealDepartmentSet.add("'"+Constans.BEFORE_TRAVEL+"'");
			complaintState = "1,5,6,7";
		}
		if (typeList.contains(2)) {
			is_in_saler = 1;
			dealDepartmentSet.add("'"+Constans.IN_TRAVEL+"'");
			dealDepartmentSet.add("'"+Constans.PREDETERMINE_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.VIP_DEPART+"'");
			
			//TODO 处理岗数据修复后删除 防止意外 先展示
			dealDepartmentSet.add("'"+Constans.CUST_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.STAR_CUST_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.CUSTOMER_DEPART+"'");
			
			complaintState = "1,5,6,7";
		}
		if (typeList.contains(3)) {
			is_after_saler = 1;
			dealDepartmentSet.add("'"+Constans.AFTER_TRAVEL+"'");
			complaintState = "1,5,6,7,10";
		}
		if (typeList.contains(6)) {
			is_air_ticket_saler = 1;
			dealDepartmentSet.add("'机票组'");
			complaintState = "1,5,6,7,10";
		}
		if (typeList.contains(7)) {
            is_hotel_saler = 1;
            dealDepartmentSet.add("'酒店组'");
            complaintState = "1,5,6,7,10";
        }
		if (typeList.contains(AppointManagerEntity.DISTRIBUTE_OFFICER)) {
            is_distribute_saler = 1;
            dealDepartmentSet.add("'分销组'");
            complaintState = "1,5,6,7,10";
        }
		
		if (typeList.contains(AppointManagerEntity.TRAFFIC_OFFICER)) {
		    is_traffic_saler = 1;
            dealDepartmentSet.add("'交通组'");
            complaintState = "1,5,6,7,10";
        }
		

		// 判断职位是否总监以上级别，若是则可以查看处理中，已待结，已完成列表所有数据，无操作权限
		int isCharger = this.isCharger();
		if (isCharger == 1 || devDepartMentList.contains(user.getDepId())) {
			dealDepartmentSet.add("'"+Constans.BEFORE_TRAVEL+"'");
			dealDepartmentSet.add("'"+Constans.IN_TRAVEL+"'");
			dealDepartmentSet.add("'"+Constans.AFTER_TRAVEL+"'");
			dealDepartmentSet.add("'"+Constans.SPECIAL_BEFORE_TRAVEL+"'");
			dealDepartmentSet.add("'"+Constans.PREDETERMINE_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.CUST_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.STAR_CUST_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.CUSTOMER_DEPART+"'");
			dealDepartmentSet.add("'"+Constans.VIP_DEPART+"'");
			dealDepartmentSet.add("'机票组'");
			dealDepartmentSet.add("'酒店组'");
			dealDepartmentSet.add("'分销组'");
			dealDepartmentSet.add("'交通组'");
			isCtOfficer = true;
			if (devDepartMentList.contains(user.getDepId())) {
				is_after_saler = 1;
				complaintState = "1,5,6,7,10";
			}
		}
		
		// 拼接当前人员能看到的处理岗列表
		String dealDepartStr = "";
		if (dealDepartmentSet.size() > 0) {
			String[] ddArr = dealDepartmentSet.toArray(new String[dealDepartmentSet.size()]);
			dealDepartStr = com.tuniu.gt.toolkit.CommonUtil.arrToStr(ddArr);
		}
		
		// 订单状态查询条件
		if ("0".equals(searchMap.get("orderStateTemp"))) {
			paramMap.put("orderStateTemp", "出游前");
		} else if ("1".equals(searchMap.get("orderStateTemp"))) {
			paramMap.put("orderStateTemp", "出游中");
		} else if ("2".equals(searchMap.get("orderStateTemp"))) {
			paramMap.put("orderStateTemp", "出游后");
		}

		// 根据状态分别显示投诉带指定1、投诉处理中2、投诉已待结3和投诉已完成4
		String state = request.getParameter("c_type");// 请求的标签页列表
		int stateType = 1; // 请求的标签页，默认显示待指定
		if (state != null) {
			if (state.equals("menu_1")) {
				
			} else if (state.equals("menu_2")) {
				stateType = 2;
				complaintState = "2";
				if (!isCtOfficer && isCharger == 0) {
					paramMap.put("orDeal", user.getId());
				}
			} else if (state.equals("menu_3")) {
				complaintState = "3";
				stateType = 3;
				if (is_pre_saler > 0) {
					// 售前客服在已待结状态下，只能看到处理人或交接人是自己的单子
					paramMap.put("deal", user.getId());
				}
			} else if (state.equals("menu_4")) {
				complaintState = "4";
				stateType = 4;
			} else if (state.equals("menu_5")) {
				stateType = 5;
				complaintState = "9";
			}

		} else {
			// 若不是投诉负责人，不显示投诉待分配标签内容
			if (!isCtOfficer && isCharger == 0) {
				stateType = 2;
				complaintState = "2";
				paramMap.put("orDeal", user.getId());
			}
		}
		
		if ("2".equals(searchMap.get("state"))) {
			complaintState = "9";
		}

		paramMap.put("dealDepartTemp", dealDepartStr);
		if (is_before_saler==1) {
			paramMap.put("beforeFlag", 1);
		}
		paramMap.put("state", complaintState);
		paramMap.put("stateType", stateType);
		paramMap.put("managerIdOr", user.getId());
		paramMap.put("managerIdOrName", user.getRealName());

		if (null != paramMap.get("orDeal")) {
			paramMap.put("orDealName", user.getRealName());
		}

		if (!searchMap.isEmpty() && !"menu_4".equals(state)) {
			// 未及时处理筛选
			int notInTimeDeal = Integer.parseInt(searchMap.get("notInTimeDeal") + "");
			String notInTimeDealDate = String.valueOf(searchMap.get("notInTimeDealDate"));
			if (notInTimeDeal > 0) {
				if (null == notInTimeDealDate || "".equals(notInTimeDealDate)) {
					notInTimeDealDate = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
				}

				String notInState = "";
				String routeType = "";
				Map<String, Object> assignedMap = new HashMap<String, Object>();
				List<Integer> assignedComplaintIds = null;
				List<Integer> notInTimeIds = null;
				switch (notInTimeDeal) {
				case 1:
					assignedMap.putAll(paramMap);
					assignedMap.put("isAssigned", 1);
					assignedMap.put("guestLevel", "'四星会员','五星会员'");
					assignedComplaintIds = service.getComplaintIdByParameter(assignedMap);
					notInTimeIds = service.getNotInTimeComplaintId(assignedComplaintIds, 0.5);
					paramMap.put("notInTimeIds", notInTimeIds);
					break;
				case 2:
					assignedMap.putAll(paramMap);
					assignedMap.put("isAssigned", 1);
					assignedMap.put("notInGuestLevel", "'四星会员','五星会员'");
					assignedComplaintIds = service.getComplaintIdByParameter(assignedMap);
					notInTimeIds = service.getNotInTimeComplaintId(assignedComplaintIds, 2);
					paramMap.put("notInTimeIds", notInTimeIds);
					break;
				case 3: // 出境9个自然日未完成
					notInState = "3,4,9";
					routeType = "'出境散客','出境自由行','出境团队','常规自助-自助游产品'";
					paramMap.put("notInState", notInState);
					paramMap.put("routeType", routeType);
					paramMap.put("notInTime", this.getEndTime(notInTimeDealDate, 9));
					break;
				case 4: // 国内5个自然日未完成
					notInState = "3,4,9";
					routeType = "'出境散客','出境自由行','出境团队','常规自助-自助游产品'";
					paramMap.put("notInState", notInState);
					paramMap.put("notInRouteType", routeType);
					paramMap.put("notInTime", this.getEndTime(notInTimeDealDate, 5));
					break;
				case 5: // 国内、出境统一15个自然日未完成
					notInState = "4,9";
					paramMap.put("notInState", notInState);
					paramMap.put("notInTime", this.getEndTime(notInTimeDealDate, 15));
					break;
				default:
					break;
				}
			}
		}
		
		// 是否可以变更处理岗
		int isChangeDealDepart = isCtOfficer ? 1 : 0;
		
		int cancelAuth = 0;
		List<String> cancelAuthList = DBConfigManager.getConfigAsList("business.cmp.cancelAuth");
		if(CollectionUtil.isNotEmpty(cancelAuthList)){
		    if(cancelAuthList.contains(user.getId()+"")){
		        cancelAuth = 1;
		    }
		}
		
		int handOverAll = 0;
		List<String> handOverAllList = DBConfigManager.getConfigAsList("handover.intravel.all.button");
		if(CollectionUtil.isNotEmpty(handOverAllList)){
		    if(handOverAllList.contains(user.getId()+"")){
		    	handOverAll = 1;
		    }
		}
		
		request.setAttribute("users", userService.getCmpAllUsers());
		request.setAttribute("isChangeDealDepart", isChangeDealDepart);
		request.setAttribute("type", stateType);
		request.setAttribute("is_after_saler", is_after_saler);
		request.setAttribute("is_special_before_saler", is_special_before_saler);
		request.setAttribute("is_before_saler", is_before_saler);
		request.setAttribute("is_in_saler", is_in_saler);
		request.setAttribute("is_air_ticket_saler", is_air_ticket_saler);
		request.setAttribute("is_distribute_saler", is_distribute_saler);
		request.setAttribute("cancelAuth", cancelAuth);
		request.setAttribute("handOverAll", handOverAll);
		request.setAttribute("isCharger", isCharger);
		request.setAttribute("search", searchMap);
		request.setAttribute("curDate", new Date());

		return paramMap;
	}
	
	/**
	 * 获取客服经理，job_id为10,21,283,419,418
	 * @return
	 */
	private Set<UserEntity> getCustomLeader() {
		Set<UserEntity> customLeader = new HashSet<UserEntity>();
		String jobIds = "10,13,15,21,283,418,419,564";
		List<UserEntity> list = userService.getUsersByJob(jobIds);
		customLeader.addAll(list);
		// 客服经理下拉框添加基础配置菜单里的负责人标签页的出游前客服负责人。
		List<AppointManagerEntity> beforeList = appointManagerService.getListByType(AppointManagerEntity.PRE_SALES_OFFICER);
		if (beforeList != null && beforeList.size() > 0) {
			for (AppointManagerEntity appointManagerEntity : beforeList) {
				UserEntity user = (UserEntity) userService.fetchOne(appointManagerEntity.getUserId());
				customLeader.add(user);
			}
		}
		return customLeader;
	}
	
	private List<Integer> getDevDeptIdList() {
		List<Integer> devDeptIdList = new ArrayList<Integer>();
		List<AppointManagerEntity> devList = appointManagerService.getListByType(AppointManagerEntity.DEV_OFFICER); // 研发负责人
		for (AppointManagerEntity ame : devList) {
			devDeptIdList.add(ame.getDepartmentId());
		}
		return devDeptIdList;
	}
	
	private Date getEndTime(String date, int day) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate = null;
		try {
			Date currentDate = format.parse(date);
			long currentTime = currentDate.getTime();
			long endTime = currentTime - 3600 * 24 * day * 1000;
			endDate = format.parse(format.format(endTime));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return endDate;
	}

	/**
	 * 判断投诉事宜是否正确
	 */
	public String checkAccuracy(){
		ComplaintReasonEntity cre = new ComplaintReasonEntity();
		cre.setId(Integer.parseInt(reasonId));
		cre.setAccuracy(Integer.parseInt(accuracy));
		cre.setAccuracyRe(accuracyRe);
		cre.setAddTime(null);
		cre.setComplaintId(null);
		cre.setDelFlag(null);
		cre.setDescript(null);
		cre.setOrderId(null);
		cre.setSecondType(null);
		cre.setType(null);
		cre.setTypeDescript(null);
		reasonService.update(cre);
		String res = "bill";
		return res;
	}
	
	
	/**
	 * 投诉处理单页面
	 * 
	 * @return String 页面
	 */
	@SuppressWarnings("unchecked")
	public String toBill() {
		request.setAttribute("user", user);
		String id = request.getParameter("id");
		String view_type = request.getParameter("viewType");// 质检查看投诉内容
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", id);
		paramMap.put("del_flag", 1);
		if (id != null) {
		    
			try{
                List<ComplaintFollowNoteEntity> followNoteList = followNoteService.getNoteByComplaintId(id);
                for(ComplaintFollowNoteEntity cfne : followNoteList) {
                	cfne.setContent(cfne.getContent().replaceAll("(\r\n|\r|\n|\n\r)", " "));
                }
                List<CheckEmailEntity> checkMailList = checkEmailService.getCheckMailListByComplaintId(id);
                List<ComplaintReasonEntity> complaintReason = reasonService.getReasonAndQualitycostList(paramMap);
                ComplaintSolutionEntity complaintSoulution = complaintSolutionService.getComplaintSolutionBycompalintId(Integer.valueOf(id));
                ShareSolutionEntity shareSolutionEntity = shareSolutionService.getShareSolutionBycompalintId(Integer.valueOf(id));
                
                // 跟进记录
                paramMap.clear();
                paramMap.put("complaintId", id);
                List<FollowUpRecordEntity> recordList = (List<FollowUpRecordEntity>) followUpRecordService.fetchList(paramMap);
                for (FollowUpRecordEntity record : recordList) {
                	UserEntity addUser = userService.getUserByID(record.getAddUser());
                	String addUserName = null == addUser ? "" : addUser.getRealName();
                	record.setAddUserName(addUserName);
                }
                
                QcEntity qcEntity = (QcEntity) qcService.get(paramMap);
                ComplaintEntity complaint = (ComplaintEntity) service.get(id);
                
                //设置title  [订单号]投诉处理单
                this.setPageTitle("["+complaint.getOrderId()+"]投诉处理单");
                Integer state = complaint.getState();//传入投诉类型
                if (null == complaint.getGuestLevel() || complaint.getGuestLevel().length() == 0) {
                	complaint.setGuestLevel(getGuestLevel(ComplaintRestClient.getCustLevelNum(complaint.getOrderId())));
                }
                //获取投诉处理人用户user_name
                String userName =  agencyCommitService.queryUserName(complaint.getDeal());
                if(state!=Constans.COMPLAINT_STATE){//投诉状态不是待处理
                	
                	List<AgencyToChatEntity> chatList =new ArrayList<AgencyToChatEntity>();
                	try {
                		//取途牛最新一条数据
                		paramMap.put("flag", 1);
                		AgencyToChatEntity chat = agencyCommitService.queryMaxChat(paramMap);
                		//聊天记录
                		chatList = queryChatDetail(id,userName);//查询该投诉单所有会话
                		
                		request.setAttribute("chatList", chatList);
                		request.setAttribute("chat", chat);
                		
                		} catch (Exception e) {
                			
                			request.setAttribute("chatList", null);
                			request.setAttribute("chat", null);
                	}
                	
                }
                /*String userId = String.valueOf(user.getId());
                String deal = String.valueOf(complaint.getDeal());   没有用到？？？*/
                Date addTime = complaint.getAddTime();
                Date endTime = DateUtil.parseDateTime("2015-11-02 22:20:00");
                boolean flag = addTime.before(endTime);
                int reportFlag = 0;
                if(flag){
                	
                	request.setAttribute("reportFlag", reportFlag);
                	
                }else{
                	
                	reportFlag = 1;
                	request.setAttribute("reportFlag", reportFlag);
                }
                
                //待回呼数据
                Map<String, Object> hashMap =new HashMap<String, Object>();
                hashMap.put("cmpId", id);
                List<TaskReminderEntity> taskList = cmpTaskReminderService.getTaskList(hashMap);
                
                Map<String, Object> fileMap = new HashMap<String, Object>();
                fileMap.put("complaintId", id);
                QcPointEntity qcPoint = qcPointService.getQcPoint(fileMap);
                request.setAttribute("qcPoint", qcPoint);
                //传入投诉状态
                request.setAttribute("state", complaint.getState());
                request.setAttribute("complaint", complaint);
                request.setAttribute("taskList", taskList);
                request.setAttribute("recordList", recordList);
                request.setAttribute("follow_note_list", followNoteList);
                request.setAttribute("check_mail_list", checkMailList);
                request.setAttribute("complaint_reason", complaintReason);
                request.setAttribute("complaintSoulution", complaintSoulution);
                request.setAttribute("shareSolutionEntity", shareSolutionEntity);
                request.setAttribute("qc", qcEntity);

                // 根据联系人id获取联系人信息
                if (complaint.getContactId() > 0 && (null == complaint.getContactPhone()|| "".equals(complaint.getContactPhone()))) {
                	
                	Map<String, Object> contact = ComplaintRestClient.getTouristById(complaint.getContactId() );
                	complaint.setContactPerson(contact.get("name")==null?"":String.valueOf(contact.get("name")));
                	complaint.setContactPhone(contact.get("tel")==null?"":String.valueOf(contact.get("tel")));
                	complaint.setContactMail(contact.get("email")==null?"":String.valueOf(contact.get("email")));
                }

                // 处理岗位为售后组和资深组显示填写分担方案按钮，售前组显示提交售后填写分担方案按钮
                int canAddSoulation = 0; // 是否显示填写分担方案按钮，1-显示，0-显示提交售后填写分担方案按钮
                
                List<String> canPayoutDealDepartLists = DBConfigManager.getConfigAsList("sys.payout.dealDepart");
                if(canPayoutDealDepartLists.contains(complaint.getDealDepart())){
                	canAddSoulation = 1;
                }

                request.setAttribute("canAddSoulation", canAddSoulation);
                request.setAttribute("view_type", view_type);

                // 如果没有分配处理人，所有按钮都禁用
                int isAssignDealer = 0;
                if (complaint.getDeal() > 0) {
                	isAssignDealer = 1;
                }

                String isRepeat = request.getParameter("repeat");
                if (isRepeat != null && Integer.valueOf(isRepeat) == 1) {
                	isAssignDealer = 0;
                }

                request.setAttribute("isAssignDealer", isAssignDealer);
                List<DepuserMapEntity> depUserList = depMapService.getDepIdByUser(user.getId());
                for(DepuserMapEntity dm : depUserList){
                	if(dm.getDepId()==973 || dm.getDepId()==1952 || dm.getDepId()==1953 || user.getId()==17195 || user.getId()==5496){
                		request.setAttribute("deptzj", 1);
                		break;
                	}
                }
                
                if (complaint.getDeal() > 0) {
                	boolean authFlag = false;
                	
                	List<String> userIds = DBConfigManager.getConfigAsList("auth.users.id");
                	
                	if (complaint.getDeal().equals(user.getId()) 
                			|| complaint.getAssociater().equals(user.getId())
                			|| userIds.contains(user.getId() + "")) {
                		authFlag = true;
                	} else {
                		List<Integer> devDepartMentList = getDevDeptIdList();
                		List<Integer> typeList = appointManagerService.getUserOfficerTypes(user.getId());
                		dealSameGroupUsers(devDepartMentList, typeList);
                		
                		UserEntity dealer = userService.getUserByID(complaint.getDeal());
                		authFlag = isContain(sameGroupUsers, dealer);
                		
                		if (!authFlag) {
                			List<UserEntity> qcUserList = new ArrayList<UserEntity>();
                			List<UserEntity> qcUserList1 = userService.getSameDeptUsers(1948); // 质检部
                			List<UserEntity> qcUserList2 = userService.getSameDeptUsers(1949); // 质量部
                			qcUserList.addAll(qcUserList1);
                			qcUserList.addAll(qcUserList2);
                			//质监部张楠
                			UserEntity qcUser = userService.getUserByName("张楠3");
                			qcUserList.add(qcUser);
                			authFlag = isContain(qcUserList, user);
                		}
                		if(dealer.getDelFlag()!=0){
                			
                			if( user.getId() == 5728 || user.getId() == 318  || user.getId() == 7331
                			 || user.getId() == 8822 || user.getId() == 1076 || user.getId() == 3698 
                			 || user.getId() == 4816 || user.getId() == 7930 || user.getId() == 5831){
                				
                				authFlag = true;
                			}
                			
                			if(!authFlag){//当处理人离职，判断登录人是不是处理人主管（汇报人）
                				UserEntity reportUser = ComplaintRestClient.getReporter(dealer.getRealName());
                				
                				if(reportUser != null && reportUser.getId().equals(user.getId())){
                					authFlag = true;
                				}
                			}
                		}
                	}
                	request.setAttribute("authFlag", authFlag);
                	Boolean upgradeThirdPart = authFlag||appointManagerService.isMemberOfType(user.getId(),AppointManagerEntity.POST_SALES_OFFICER);
                	request.setAttribute("upgradeThirdPart", upgradeThirdPart);
                }
                
                //判断登陆用户是否为客服质量组成员（只能查看质检报告）
                List<UserEntity> cqUserList = userService.getSameDeptUsers(2584); // 客服质量组
                boolean isCQGroup = isContain(cqUserList,user);
                request.setAttribute("isCQGroup", isCQGroup);
                
                //判断登录人是否是主管
                isCtOfficer = appointManagerService.isCtOfficer(user.getId());
                int  depId = user.getDepId();
                int depflag = 0;
                if(depId==74||depId==748||depId==1296||depId==2693||depId==2694){//售后服务中心所有员工 售后组 资深组 出游前客服部   上海客户服务部  北京客户服务部
                	
                	depflag =1;
                }
                
                request.setAttribute("depflag", depflag);//售后服务中心所有员工开通投诉等级修改的权限 add 20150610 chenhaitao
                
                /* 查询历史赔付信息 */
                Map<String, Object> queryConMap = new HashMap<String, Object>();
                queryConMap.put("orderId", complaint.getOrderId());
                queryConMap.put("contactPhone", complaint.getContactPhone());
                
                //查询该投诉单下相关赔付信息,对客或分担金额任意不为0 1. 有订单投诉，根据订单号查询    2.无订单投诉，根据手机号查询
                List<Map<String, Object>> payInfoList = service.getHistoryPayInfo(queryConMap);
                request.setAttribute("payInfoList", payInfoList);
                
                try {
                	if (complaint.getOrderId() > 0 && !Constans.AIR_TICKIT.equals(complaint.getDealDepart()) && !Constans.TRAFFIC.equals(complaint.getDealDepart())) { //机票组和交通组不展示同团期订单
                		request.setAttribute("sgOrderList", getSameGroupOrders(complaint));
                	}
                } catch (Exception e) {
                	logger.error("getSameGroupOrders Error complaintId:"+complaint.getId());
                }
                

                jspTpl = "bill";
                
                //投诉单回访开关控制
                List<Integer> userOfficerTypes = appointManagerService.getUserOfficerTypes(user.getId());
                if(CollectionUtil.isNotEmpty(userOfficerTypes)&&userOfficerTypes.contains(AppointManagerEntity.MSG_RETURN_VISIT)) {
                    request.setAttribute("isReturnVisitSwitchOpened", complaint.getReturnVisitSwitch()==1);
                }
                
                //供应商联系列表
                List<SupportShareEntity> supportShareList = getAgencyInfo(complaint.getOrderId());
                JSONArray agencyContactArray = new JSONArray();
                if(CollectionUtil.isNotEmpty(supportShareList)){
                    Integer agencyId = 0;
                    String agencyName = "";
                    JSONObject contactDetail = new JSONObject();
                    JSONObject agencyContact = new JSONObject();
                    for(SupportShareEntity supportShareEntity : supportShareList) {
                        agencyId = supportShareEntity.getCode();
                        agencyName = supportShareEntity.getName();
                        agencyContact.put("agencyName", agencyName);
                        contactDetail = tspService.getAgencyContactInfo(agencyId, complaint.getId());
                        agencyContact.put("contactDetail", contactDetail);
                        agencyContactArray.add(agencyContact);
                    }    
                }
                request.setAttribute("agencyContactArray", agencyContactArray);
                
                Map<String,Object> thirdPartMap = new HashMap<String,Object>();
                thirdPartMap.put("complaint_id", complaint.getId());
                Integer thirdPartCount = complaintThirdPartService.getComplaintThirdPartCount(thirdPartMap); 
                if(thirdPartCount>0){
                	request.setAttribute("haveThirdPart", true);
                }
                
                Map<String,String> contactPhoneMap = tspService.getContactPhoneMapFromPGA(complaint.getOrderId());
                List<String> contactPhoneList = new ArrayList<String>();
                for (String concatTel : contactPhoneMap.keySet()) {
                	contactPhoneList.add(concatTel);
                }
                if(contactPhoneList.size()>0){
                	JSONArray specialCustArr = tspService.getSpecialCustListFromCRM(contactPhoneList);
                	String whiteResult = "";
                	String blackResult  = "";
                	String blackName  = "";
                	String blackResultType  = "";
                	for(int i = 0;i<specialCustArr.size();i++){
                		JSONObject jsonParam = specialCustArr.getJSONObject(i);
                		if(jsonParam.getBoolean("specialList")){
                			String listTypeName = SpecialListTypeEnum.getValue(jsonParam.getString("listType"));
                			if(listTypeName!=null){
                				String result = listTypeName;
                				if("白名单".equals(listTypeName)){
                					result += "-"+jsonParam.getString("addReasonDesc");
                					result += "："+ contactPhoneMap.get(jsonParam.getString("value"));
                					if(StringUtil.isNotEmpty(whiteResult)){
                						whiteResult +="，";
                					}
                					whiteResult += result;
                				}else if("黑名单".equals(listTypeName)){
                					result += "-"+jsonParam.getString("listLevel");
                					result += "："+ contactPhoneMap.get(jsonParam.getString("value"));
                					blackName += contactPhoneMap.get(jsonParam.getString("value"));
                					blackResultType += listTypeName +"-"+jsonParam.getString("listLevel");
                					if(StringUtil.isNotEmpty(blackResult)){
                						blackResult +="，";
                					}
                					blackResult += result;
                				}
                			}
                		}
                	}
                	if(StringUtil.isNotEmpty(whiteResult)){
                		request.setAttribute("whiteResult", whiteResult);
                	}
                	if(StringUtil.isNotEmpty(blackResult)){
                		request.setAttribute("blackName", blackName);
                		request.setAttribute("blackResult", blackResult);
                		request.setAttribute("blackResultType", blackResultType);
                	}
                }
            }
            catch(Exception e){
                logger.error("投诉单详情："+id,e);
            }
			
		} else {
			jspTpl = "error";// 跳转到错误页面
		}
		return jspTpl;
	}
	
	/**
     * @param custLevelNum
     * @return
     */
    private String getGuestLevel(Integer custLevelNum) {
        String guestLevel = "";
        switch(custLevelNum){
            case 0:
                guestLevel = "注册会员";
                break;
            case 1:
                guestLevel = "一星会员";
                break;
            case 2:
                guestLevel = "二星会员";
                break;
            case 3:
                guestLevel = "三星会员";
                break;
            case 4:
                guestLevel = "四星会员";
                break;
            case 5:
                guestLevel = "五星会员";
                break;
            case 6:
                guestLevel = "白金会员";
                break;
            case 7:
                guestLevel = "钻石会员";
                break;
           default:
               guestLevel = "未知";
            
        }
        return guestLevel;
    }

	/**
	 * 查看同团期订单投诉情况
	 */
	private List<Map<String, Object>> getSameGroupOrders(ComplaintEntity complaint) {
		List<Map<String, Object>> sgOrderList = new ArrayList<Map<String, Object>>();
		
		String startDate = DateUtil.formatDate(complaint.getStartTime(), "yyyy-MM-dd");
		String routeId = complaint.getRouteId().toString();
		String orderIds = orderService.getSameGroupOrderIds(startDate, routeId); // 获取同团期订单
		if (StringUtil.isNotEmpty(orderIds)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderIds", orderIds);
			List<Integer> hasCtOrders = service.queryHasCtOrders(params); // 已被投诉的订单
			
			String[] orderArr = orderIds.split(",");
			for (String ordId : orderArr) {
				Map<String, Object> ordMap = new HashMap<String, Object>();
				ordMap.put("orderId", ordId);
				int cmpFlag = 0;
				if (hasCtOrders.contains(Integer.valueOf(ordId))) {
					cmpFlag = 1;
				}
				ordMap.put("cmpFlag", cmpFlag);
				sgOrderList.add(ordMap);
				Collections.sort(sgOrderList, new Comparator<Map<String, Object>>()
				{

					@Override
                    public int compare(Map<String, Object> map1, Map<String, Object> map2)
                    {
	                    return (Integer)map2.get("cmpFlag")-(Integer)map1.get("cmpFlag");
                    }
				});
			}
		}
		
		return sgOrderList;
	}

	/**
	 * @return 数据查询map
	 */
	public Map<String, Object> searchTest() {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		String order_id = request.getParameter("orderId");
		String come_from = request.getParameter("comeFrom");
		String start_city = request.getParameter("startCity");
		String end_city = request.getParameter("endCity");
		String start_build_date = request.getParameter("startBuildDate");
		String end_build_date = request.getParameter("endBuildDate");
		String start_finish_date = request.getParameter("startFinishDate");
		String end_finish_date = request.getParameter("endFinishDate");
		String deal = request.getParameter("deal");
		String manager = request.getParameter("manager");
		String level = request.getParameter("leve1");

		paramMap.put("delFlag", "0");
		paramMap.put("orderId", order_id);
		paramMap.put("comeFrom", come_from);
		paramMap.put("startCity", start_city);
		paramMap.put("endCity", end_city);
		paramMap.put("startBuildDate", start_build_date);
		paramMap.put("endBuildDate", end_build_date);
		paramMap.put("startFinishDate", start_finish_date);
		paramMap.put("endFinishDate", end_finish_date);
		paramMap.put("deal", deal);
		paramMap.put("manager", manager);
		paramMap.put("level", level);

		return paramMap;

	}

	/**
	 * 发起投诉列表页面处理方法
	 * 
	 * @return String
	 * 
	 *         增加的list页面
	 */
	public String addList() {
		canLink=0;
		return addListCommon();
	}
	
	public String addListCanLink(){
		canLink = 1;
		return addListCommon();
	}
	
	public String addListCommon(){
		// 获取当前系统登录用户
		if(page == null){
			page = new Page();
			page.setPageSize(20);
		}
		String userId = String.valueOf(user.getId());
		request.setAttribute("userId", userId);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> searchMap = new HashMap<String, String>();
		if (null != search) {
			searchMap = search.toMap();
		}
		paramMap.putAll(searchMap);
		request.setAttribute("search", searchMap);
		
		String menuId = "menu_1";
		String menuId2 = (String) request.getParameter("menuId");
		if (StringUtil.isNotEmpty(menuId2)) {
			menuId = menuId2;
		}
		request.setAttribute("menuId", menuId);
		if ("menu_2".equals(menuId)) {
			paramMap.put("ownerId", userId);
		}
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("pageSize", page.getPageSize());
		//super.execute(paramMap); // 得到列表数据
		List<ComplaintEntity> list = service.listAll(paramMap);
		int count = service.getListCount(paramMap);
		page.setCount(count);
		page.setCurrentPageCount(list==null?0:list.size());
		
		String custIds = "";
		for (ComplaintEntity complaintEntity : list) {
			Integer custId = complaintEntity.getCustId();
			if(custId==null){
				custId = 0;
			}
			custIds += custId+",";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custIds", custIds);
		map.put("tagTypes", "11");
		JSONArray dataArray = service.getCustTagsByCustIds(map);
		request.setAttribute("tagList", dataArray);
		request.setAttribute("dataList", list==null?"":list);
		return "add_list";
	}

	/**
	 * 访问增加或者编辑页面
	 */
	public String toEditComplaint() {
	    logger.info("投诉申请--进入访问增加或者编辑页面...");
		String id = request.getParameter("id");
		String menuId = request.getParameter("menuId");
		if (StringUtil.isEmpty(menuId)) {
			menuId = "menu_1";
		}

		if (id != null) {
			ComplaintEntity complaintEntity = (ComplaintEntity) service.get(id);
			request.setAttribute("complaint", complaintEntity);
		} else {
			// 设置用户及部门信息
		    logger.info("投诉申请--当前用户id:"+user.getId()+",名字:"+user.getRealName()+",部门:"+user.getDepId());
			entity.setOwnerId(user.getId());
			entity.setOwnerName(user.getRealName());
			entity.setOwnerPartment(departmentService.getDepartmentById(userService.getUserByID(user.getId()).getDepId()).getDepName());
			logger.info("投诉申请--当前用户部门:"+entity.getOwnerPartment());
			if ("menu_1".equals(menuId)) {
				jspTpl = "add_add"; // 有订单投诉页面
			}
			if ("menu_2".equals(menuId)) {
				jspTpl = "non_order_complaint_add"; // 无订单投诉页面
			}
			if("menu_3".equals(menuId)) {
				String orderIds = request.getParameter("orderIds");
				request.setAttribute("orderIds", orderIds);
				
				List<ComplaintEntity> complaintList = new ArrayList<ComplaintEntity>();
				try {
					String[] orderIdArr = orderIds.split(",");
					for (String orderId : orderIdArr) {
						ComplaintEntity comp = service.getOrderInfoMain(orderId);
						complaintList.add(comp);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				request.setAttribute("complaintList", complaintList);
				
				jspTpl = "complaint_add_batch"; // 批量变更页面
			}
		}
		
		request.setAttribute("menuId", menuId);

		return jspTpl;
	}
	
	@SuppressWarnings("unchecked")
	public String complaintAddBatch() {
		int succNum = 0;
		int ownerId = user.getId();
		String ownerName = user.getRealName();
		String comeForm = "其他";
		String dealDepart = Constans.BEFORE_TRAVEL;
		Integer hour = DateUtil.getField(new Date(), Calendar.HOUR_OF_DAY);
		if(hour < 9 || hour >= 18){// 订单状态出游前的18点到第二天9点分给售后
			dealDepart = Constans.IN_TRAVEL;
        }
		List<ComplaintReasonEntity> reasonList = getComplaintReasons();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<br>附件：");
		if (null != cmpAttachFileName && cmpAttachFileName.size() > 0) {
			for (int i=0; i<cmpAttachFileName.size(); i++) {
				String fileName = cmpAttachFileName.get(i);
				File file = cmpAttach.get(i);
				String url = ComplaintRestClient.uploadFile(file, fileName);
				if(!"FAIL".equals(url)){ // 上传失败处理结果
					sb.append("<a href='").append(url).append("'>").append(fileName).append("</a>　　　");
				}
			}
		}
		String typeDesc = reasonList.get(0).getTypeDescript() + sb.toString();
		reasonList.get(0).setTypeDescript(typeDesc);
		
		try {
			String orderIds = request.getParameter("orderIds");
			request.setAttribute("orderIds", orderIds);
			String[] orderIdArr = orderIds.split(",");
			for (String orderId : orderIdArr) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", orderId);
				params.put("state", "1,2,5,7");
				List<ComplaintEntity> complaintList = (List<ComplaintEntity>) service.fetchList(params);
				int addReasonFlag = 0;
				if (null != complaintList && complaintList.size() > 0) {
					ComplaintEntity compEnt = complaintList.get(0);
					if (!("出游中".equals(compEnt.getOrderState()) && Constans.SPECIAL_BEFORE_TRAVEL.equals(compEnt.getDealDepart()))) {
						addReasonFlag = 1; // 新增投诉事宜
						reasonService.insertReasonList(ownerId, ownerName, compEnt.getId(), "", reasonList,-1);
						compEnt.setBatchCompNum(orderIdArr.length);
						int level = getCompLevel(compEnt);
						if (level < compEnt.getLevel()) {
							compEnt.setLevel(level);
						}
						service.update(compEnt);
					}
				}
				
				if (0 == addReasonFlag) {
					ComplaintEntity compEnt = service.getOrderInfoMain(String.valueOf(orderId));
					compEnt.setComeFrom(comeForm);
					compEnt.setDealDepart(dealDepart);
					compEnt.setLevel(getCompLevel(compEnt));
					compEnt.setOwnerId(ownerId);
					compEnt.setOwnerName(ownerName);
					compEnt.setBatchCompNum(orderIdArr.length);
					compEnt.setReasonList(reasonList);
					service.insertComplaint(compEnt);
				}
				
				succNum++;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			request.setAttribute("succNum", succNum);
		}
		return "complaint_add_batch";
	}
	
	private int getCompLevel(ComplaintEntity compEnt) {
		String[] guestNumArr = compEnt.getGuestNum().split("大");
		int guestNum = Integer.valueOf(guestNumArr[0]);
		int level = 3;
		if (guestNum >= 6) {
			level = 2;
		}
		return level;
	}
	
	
	/**
	 * 根据订单号，向页面传递从接口获取的订单相关数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendOrderInfo() {
		String orderId = request.getParameter("orderId").trim();
		/* 判断是否是重复投诉且已在处理中 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", String.valueOf(orderId));
		params.put("state", "1,2,5,7");
		List<ComplaintEntity> complaintList = (List<ComplaintEntity>) service.fetchList(params);
		if (null != complaintList && complaintList.size() > 0) {
			ComplaintEntity compEnt = complaintList.get(0);
			if (!(OrderStateEnum.IN_TRAVEL.getValue().equals(compEnt.getOrderState()) 
			        && DealDepartEnum.SPECIAL_BEFORE_TRAVEL.getValue().equals(compEnt.getDealDepart()))) {
				request.setAttribute("complaintId", compEnt.getId());
				request.setAttribute("dealName", compEnt.getDealName());
				request.setAttribute("associaterName", compEnt.getAssociaterName());
				request.setAttribute("isOrder", "1");
				return "to_add_reason";
			}
		}
		
		String comeFrom = request.getParameter("comeFrom");
		String level = request.getParameter("level");
		String descript = request.getParameter("descript");
		String requirement = request.getParameter("requirement");
		String media = request.getParameter("isMedia");
		String cmpPerson = request.getParameter("cmpPerson");
		String cmpPhone = request.getParameter("cmpPhone");
		int isMedia = 0;
		if (!"".equals(media)) {
			isMedia = Integer.parseInt(media);
		}
		
		try {
			
			entity = service.getOrderInfoMain(orderId);
			if (null != entity) {
				
				if(entity.getErrCode()==1){
					
					request.setAttribute("alertOrderIdInfo", orderId +":"+ entity.getErrorMesg());
					
				}else{
					// 获取订单投诉记录
					Map<String, Object> sqlParams = new HashMap<String, Object>();
					sqlParams.put("orderId", orderId);
					complaintReasonEntityList = (List<ComplaintReasonEntity>) reasonService.fetchList(sqlParams);
					
					// 设置用户及部门信息
					entity.setDescript(descript);
					entity.setRequirement(requirement);
					entity.setOwnerId(user.getId());
					entity.setOwnerName(user.getRealName());
					entity.setOwnerPartment(departmentService.getDepartmentById(user.getDepId()).getDepName());
					
					// 设置其他页面信息
					entity.setComeFrom(comeFrom);
					if (level != null && !"".equals(level)) {
						entity.setLevel(Integer.valueOf(level));
					}
	
					// 设置投诉方是否为媒体方(或有媒体参与)
					entity.setIsMedia(isMedia);
					entity.setCmpPerson(cmpPerson);
					entity.setCmpPhone(cmpPhone);
					request.setAttribute("isGetOrder", 1);
				}
			
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("alertOrderIdInfo", orderId + Constans.QUERY_ORDER_ERROR);
		}
		request.setAttribute("menuId", "menu_1");
		jspTpl = "add_add";
		return jspTpl;
	}

	/**
	 * 增加一条投诉数据
	 * 
	 * @return 页面
	 * @throws Exception
	 */
	public String doAddComplaint() throws Exception {
		logger.info("doAddComplaint Begin");
		// 根据id判断增加或者编辑一条数据
		if (entity.getId() != null && entity.getId()!=0) {
			service.update(entity);
			// 添加有效操作记录
			String noteContent =  "投诉单号：" + entity.getId() + ",投诉来源：" + entity.getComeFrom() + ",投诉等级："
					+ entity.getLevel() + ",其他说明：" + entity.getDescript();
			addFollowNote(entity.getId(),noteContent,Constans.UPDATE_COMPLAINT_INFO);
		} else {
			entity.setOwnerId(user.getId());
			entity.setOwnerName(user.getRealName());
			entity.setOwnerPartment(departmentService.getDepartmentById(user.getDepId()).getDepName());
			
			if (1 == dealBySelf) { // 分配给自己处理(只有订单状态为出游中和出游后的才能看到分配给自己的选项)
				assignCompInfoUpdate("assign", user.getId().toString(), entity);
				entity.setDealBySelf(1);
			}
			entity.setReasonList(getComplaintReasons());
			service.insertComplaint(entity);
		}
		
		logger.info("doAddComplaint End, Order Id is " + entity.getOrderId() + ", Complaint Id is " + entity.getId());

		return "add_add";
	}
	
	private List<ComplaintReasonEntity> getComplaintReasons() {
		List<ComplaintReasonEntity> list = new ArrayList<ComplaintReasonEntity>();
		
		// 获取投诉事宜数据
		String[] typeArr = request.getParameterValues("cr.type");
		String typeDescript = request.getParameter("cr.typeDescript");
		String descript = request.getParameter("cr.descript");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (typeArr != null && typeArr.length > 0) { // 选择了一级分类
			for (int i = 0; i < typeArr.length; i++) {
				paramMap.put("id", typeArr[i]);
				ReasonTypeEntity rtEntity = (ReasonTypeEntity) reasonTypeService.get(paramMap);
				String[] secondTypeName = request.getParameterValues("cr.secondType[" + typeArr[i] + "]");
				if (secondTypeName != null && secondTypeName.length > 0) { // 选择了二级分类
					for (int j = 0; j < secondTypeName.length; j++) {
						ComplaintReasonEntity cr = new ComplaintReasonEntity();
						cr.setOrderId(entity.getOrderId());
						cr.setTypeDescript(typeDescript);
						if (descript != null && descript.equals("用以填写其他相关与客人的核实情况（可空）")) {
							descript = "";
						} else {
							cr.setDescript(descript);
						}
						cr.setType(rtEntity.getName());
						cr.setSecondType(secondTypeName[j]);
						list.add(cr);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * 修改投诉单 add by hcz 2012-09-21
	 */
	public String modifyComplaint() {

		// 设置用户及部门信息
		entity.setOwnerId(user.getId());
		entity.setOwnerName(user.getRealName());
		entity.setOwnerPartment(departmentService.getDepartmentById(
				user.getDepId()).getDepName());
		// 从页面获取的数据
		int orderId = entity.getOrderId();

		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("orderId", orderId);

		ComplaintEntity complaint = (ComplaintEntity) service.get(entity
				.getId());
		if (complaint != null) {
			entity.setIsSticky(complaint.getIsSticky());

		}
		String comeFrom = entity.getComeFrom();// 投诉来源

		// 投诉来源为门市、当地质检的直接进入投诉处理中列表，处理人即为投诉发起人
		/*
		if (comeFrom.equals("门市") || comeFrom.equals("当地质检")) {
			entity.setState(2);// 投诉状态为投诉处理中
			entity.setDeal(entity.getOwnerId());
			entity.setDealName(entity.getOwnerName());
		} else {
			entity.setState(1);// 投诉状态为投诉待指定
		}*/
		
		entity.setState(1);// 投诉状态为投诉待指定
		String orderState = request.getParameter("entity.orderState");
		if (orderState.equals("出游前")) {
			entity.setManagerName(request.getParameter("entity.customerLeader"));
			entity.setManager(Integer.parseInt(request
					.getParameter("entity.customerLeaderId")));
		}
		entity.setDelFlag(0);// 未删除

		// 处理岗位默认和订单状态一致
		String dealDepart="";
		if(entity.getOrderState().equals(OrderStateEnum.BEFORE_TRAVEL)){
			dealDepart=Constans.BEFORE_TRAVEL;
		}else if(entity.getOrderState().equals(OrderStateEnum.IN_TRAVEL)){
			dealDepart=Constans.IN_TRAVEL;
		}else{
			dealDepart=Constans.AFTER_TRAVEL;
		}
		entity.setDealDepart(dealDepart);

		// 更新
		if (entity.getId() != null && !entity.getId().equals(0)) {
			entity.setUpdateTime(new Date());
			service.update(entity);
			// 添加有效操作记录
			String noteContent = "投诉单号："+ entity.getId() + ",投诉来源：" + comeFrom + ",投诉等级："
					+ entity.getLevel() + ",其他说明：" + entity.getDescript();
			addFollowNote(entity.getId(), noteContent, Constans.UPDATE_COMPLAINT_INFO);

		}
		String html = "add_add";
		return html;
	}

	/**
	 * 
	 * 进入编辑或删除一条投诉信息 只能本人操作
	 * 
	 * @return
	 */
	public String toEditOrDeleteComplaint() {

		Map<String, Object> paramMap = this.addOrEditComplaintParam();
		// 系统自动获取数据
		String actFlag = request.getParameter("actFlag");
		String id = request.getParameter("id");
		paramMap.put("id", id);
		// 删除此条数据
		if ("del".equals(actFlag)) {
			// 删除投诉单
			paramMap.put("delFlag", "1");
			paramMap.put("updateTime", new Date());
			paramMap.put("finishDate", new Date());
			service.update(paramMap);

			// 删除质检单
			paramMap.clear();
			paramMap.put("complaintId", id);
			QcEntity qcEntity = (QcEntity) qcService.get(paramMap);
			qcEntity.setDelFlag(1);
			qcService.update(qcEntity);

			// 添加有效操作记录
			String noteContent = "投诉单号：" + id;
			addFollowNote(Integer.parseInt(id),noteContent,Constans.DELETE_COMPLAINT_INFO);

			return this.addList();// 返回到增加页面
		} else {// 编辑数据
			entity = (ComplaintEntity) service.get(id);
			// 投诉事宜
			paramMap.clear();
			paramMap.put("orderId", entity.getOrderId());
			complaintReasonEntityList = (List<ComplaintReasonEntity>) reasonService.fetchList(paramMap);

			// 根据联系人id获取联系人信息
			
			if (entity.getContactId() > 0 && (null == entity.getContactPhone()|| "".equals(entity.getContactPhone()))) {
				
				Map<String, Object> contact = ComplaintRestClient.getTouristById(entity.getContactId() );
				entity.setContactPerson(contact.get("name")==null?"":String.valueOf(contact.get("name")));
				entity.setContactPhone(contact.get("tel")==null?"":String.valueOf(contact.get("tel")));
				entity.setContactMail(contact.get("email")==null?"":String.valueOf(contact.get("email")));
			}

			request.setAttribute("edit", 1);
			String html = "add_add";
			return html;
		}

	}

	public String toComplaintReason() {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		String id = request.getParameter("id");
		paramMap.put("id", id);
		paramMap.put("delFlag", "1");// 0为删除，1为正常
		service.update(paramMap);
		// String html = "complaint_reason";// 返回到添加列表页面
		String html = "edit_reason";
		return html;
	}

	/**
	 * 投诉数据参数获取
	 * 
	 * @return
	 */
	public Map<String, Object> addOrEditComplaintParam() {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 数据来源于页面
		String orderId = request.getParameter("orderId");
		String comeFrom = request.getParameter("comeFrom");
		String leaver = request.getParameter("leaver");
		String descript = request.getParameter("descript");
		String requirement = request.getParameter("requirement");

		// 数据来源于接口
		String guestName = request.getParameter("guestName");
		String guestNum = request.getParameter("guestNum");
		String startCity = request.getParameter("startCity");
		String route = request.getParameter("route");
		String customerLeader = request.getParameter("customerLeader");
		String productLeader = request.getParameter("productLeader");
		String customer = request.getParameter("customer");
		String product = request.getParameter("product");
		String seniorManager = request.getParameter("seniorManager");
		String orderState = request.getParameter("orderState");
		String orderType = request.getParameter("orderType");
		String routeType = request.getParameter("routeType");
		String orderComeFrom = request.getParameter("orderComeFrom");

		paramMap.put("orderId", orderId);
		paramMap.put("comeFrom", comeFrom);
		paramMap.put("leaver", leaver);
		paramMap.put("descript", descript);
		paramMap.put("requirement", requirement);
		paramMap.put("guestName", guestName);
		paramMap.put("guestNum", guestNum);
		paramMap.put("startCity", startCity);
		paramMap.put("route", route);
		paramMap.put("customerLeader", customerLeader);
		paramMap.put("productLeader", productLeader);
		paramMap.put("customer", customer);
		paramMap.put("product", product);
		paramMap.put("orderState", orderState);
		paramMap.put("orderType", orderType);
		paramMap.put("routeType", routeType);
		paramMap.put("orderComeFrom", orderComeFrom);
		paramMap.put("seniorManager", seniorManager);

		return paramMap;
	}

	/**
	 * 分配处理人、工作交接人
	 * 
	 * @return String
	 * @throws 
	 */
	public String dealPeople() {
		String flag = request.getParameter("assignFlag"); // assignFlag: 重新分配 assignNew, 分配 assign, 工作交接 dealjoin
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String dealPeople = request.getParameter("select_delPeople");// 分配处理人
		if (dealPeople == null || "".equals(dealPeople) || "0".equals(dealPeople)) {
			dealPeople = request.getParameter("deal_joinPeople");// 工作交接人
		}
		String[] ids;
		ids = request.getParameter("ids").split(",");
		String dealName = "";

		// 根据用户id获取用户姓名
		if (dealPeople != null && !"".equals(dealPeople)) {
			UserEntity user = (UserEntity) userService.get(Integer.valueOf(dealPeople));
			dealName = user.getRealName();
		}

		// 根据分割后的id数组，将负责人分配给各个投诉，同时将状态改成投诉处理中
		if (ids != null && ids.length > 0) {
		    String title="手动分单提醒";
			for (int i = 0; i < ids.length; i++) {
				if (ids[i] != null && !"".equals(ids[i])) {
					ComplaintEntity complaint = (ComplaintEntity) service.get(ids[i]);
					paramMap.put("id", ids[i]);
					paramMap.put("deal", dealPeople);
					paramMap.put("dealName", dealName);
					paramMap.put("manager", user.getId());// 设置负责人id
					paramMap.put("managerName", user.getRealName());
					if (complaint.getState() == 6) {
						paramMap.put("isSticky", 0);
						paramMap.put("state", 3);// 提交售后填写分担方案投诉单分配处理人后状态置为3
					} else if (complaint.getState() != 3) {
						paramMap.put("state", 2);// 状态变成投诉处理中
					}
					
					String noteContent = null;//有效操作记录内容
					String flowName = null;//有效操作记录事件名称
					
					if (flag.equals("assign")) {
						paramMap.put("inProcessState", 0);
						paramMap.put("assignTime", new Date());
						
						noteContent = Constans.ASSIGN_DEALER + ":" + dealName;
						flowName = Constans.ASSIGN_DEALER;
						
					}
					if (flag.equals("dealjoin")) {// 工作交接
						paramMap.put("state", complaint.getState());
						paramMap.put("associater", dealPeople);
						paramMap.put("associaterName", dealName);
						paramMap.put("deal", complaint.getDeal());
						paramMap.put("dealName", complaint.getDealName());

						noteContent = Constans.ASSOCIATE_DEALER + ":" + dealName;
						flowName = "工作交接";
					}
					if (flag.equals("assignNew")) {// 重新分配
						paramMap.put("assignTime", new Date());
						paramMap.put("oldDeal", complaint.getDeal());
						paramMap.put("oldDealName", complaint.getDealName());
						paramMap.put("associater", -1);
						paramMap.put("associaterName", "");
						
						//如果是重新分配给原处理人，则不将处理状态重置为 0：待首呼(此处为了规避误操作，业务逻辑上不允许重新分配给原处理人)
						if (!dealName.equals(complaint.getDealName())) {
							paramMap.put("inProcessState", 0);
						}

						noteContent = Constans.ASSIGN_NEW_DEALER + ":" + dealName;
						flowName = Constans.ASSIGN_DEALER;
					}
					
					// 添加有效操作记录
					addFollowNote(Integer.parseInt(ids[i]), noteContent, flowName);
					
					service.update(paramMap);
					paramMap.clear();// 清空map
					
					/* 分单后，与自动分单一并记录客服分单数量 */
					assignCompInfoUpdate(flag, dealPeople, complaint);
					
					if(!flag.equals("dealjoin")){
					    String content="【投诉质检-新分配单】"+"\n投诉单号:"+complaint.getId()+"\n订单号:"+complaint.getOrderId()+"\n";
					    new RTXSenderThread(Integer.valueOf(dealPeople), dealName, title, content).start();
					}
				}
			}
		}

		return "success";
	}
	
	/**
	 * 更新分单记录信息
	 * @param typeFlag 分单类型
	 * @param dealPeople 处理人ID
	 * @param complaint
	 */
	private void assignCompInfoUpdate(String typeFlag, String dealPeople, ComplaintEntity complaint) {
		if ("assign".equals(typeFlag) || "assignNew".equals(typeFlag)) { // 处理人分单情况+1
			int dealId = Integer.valueOf(dealPeople);
			ComplaintUtil.recordUserNums(dealId, ComplaintUtil.MEM_PRE_COMPLAINT);
			ComplaintUtil.recordUserOrders(dealId, String.valueOf(complaint.getOrderId()), ComplaintUtil.MEM_PRE_COMPLAINT);
			
			if ("assignNew".equals(typeFlag)) { // 原处理人分单情况-1
				ComplaintUtil.minusUserNums(complaint.getDeal(), ComplaintUtil.MEM_PRE_COMPLAINT);
				ComplaintUtil.minusUserOrders(complaint.getDeal(), String.valueOf(complaint.getOrderId()), ComplaintUtil.MEM_PRE_COMPLAINT);
			}
		}
	}

	/**
	 * 修改投诉单客服经理
	 * 
	 * @return
	 */
	public String changeCustomLeader() {

		String ids = request.getParameter("id");
		String idArr[] = ids.split(",");
		String name = request.getParameter("select_customer");
		int idCount = idArr.length;
		for (int i = 0; i < idCount; i++) {
			entity = (ComplaintEntity) service.get(idArr[i]);
			entity.setCustomerLeader(name);
			service.update(entity);
		}
		return "success";
	}
	
	public String custAgree() {
		String id = request.getParameter("id");
		String custAgreeFlag = request.getParameter("custAgreeFlag");
		if (id != null && !"".equals(id)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", id);
			paramMap.put("custAgreeFlag", custAgreeFlag);
			service.update(paramMap);
		}
		return "list";
	}

	/**
	 * 撤销投诉
	 * 
	 * @return String
	 * @throws
	 */
	public String cancel() {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		if (id != null && !"".equals(id)) {
			// 处理人当前分单量-1
			ComplaintEntity complaint = (ComplaintEntity) service.get(id);
			minusAssignInfo(complaint);
			
			paramMap.put("id", id);
			paramMap.put("state", 9);// 状态位9表示撤销
			service.update(paramMap);
			
			// 删除质检单
			paramMap.clear();
			paramMap.put("complaintId", id);
			QcEntity qcEntity = (QcEntity) qcService.get(paramMap);
			if(qcEntity!=null){
				qcEntity.setDelFlag(1);
				qcService.update(qcEntity);
			}

			// 添加有效操作记录
			String noteContent = "投诉单号：" + id;
			addFollowNote(Integer.parseInt(id), noteContent, Constans.CANCEL_COMPLAINT_INFO);

			/* 投诉已完成或撤销时将消息推送至MQ */
			complaintResultMQProducer.sendMessage(id, "Canceled");
			
			// 调用前台网站接口，发送投诉订单状态
			ComplaintEntity ce = (ComplaintEntity) service.fetchOne(Integer
					.parseInt(id));
			service.sendOrderStatus(ce.getOrderId(), 3);

			// 投诉已完成或撤销时，通知点评系统
			if ("点评".equals(ce.getOwnerName())) {
				ComplaintRestClient.complaintState2Crm(ce);
			}

		}

		return execute();
	}
	
	/**
	 * 处理人分单情况-1
	 * @param complaint
	 */
	private void minusAssignInfo(ComplaintEntity complaint) {
		ComplaintUtil.minusUserNums(complaint.getDeal(), ComplaintUtil.MEM_PRE_COMPLAINT);
		ComplaintUtil.minusUserOrders(complaint.getDeal(), String.valueOf(complaint.getOrderId()), ComplaintUtil.MEM_PRE_COMPLAINT);
	}

	/**
	 * （升级售后的投诉）退回
	 * 
	 * @return String
	 * @throws
	 */
	public String sendBack() {
		// Map<String, Object> paramMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String reason = request.getParameter("reason");
		entity = (ComplaintEntity) service.get(id);
		if (id != null && !"".equals(id)) {
			// paramMap.put("id", id);
			// paramMap.put("state", 1);//状态位1表示未分配
			// paramMap.put("dealDepart", entity.getOrderState());
			entity.setState(1);
			entity.setDeal(0);
			entity.setDealName("");
			// 处理岗位默认和订单状态一致
			String dealDepart=entity.getDealDepart();
			if(Constans.AFTER_TRAVEL.equals(dealDepart)){
				if("机票".equals(entity.getRouteType())){
					dealDepart = Constans.AIR_TICKIT;
				} else {
					dealDepart = Constans.IN_TRAVEL;
				}
				entity.setDealDepart(dealDepart);
				service.update(entity);
				// 添加有效操作记录
				String noteContent = Constans.BACK_COMPLAINT_INFO + "，投诉单号："+ id +"，退回原因为：" + reason;
				addFollowNote(Integer.parseInt(id),noteContent,Constans.BACK_COMPLAINT_INFO);
			}
		}

		try {
			// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
			HttpServletResponse response = ServletActionContext.getResponse();
			// 设置字符集
			response.setContentType("text/plain");// 设置输出为文字流
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			out = response.getWriter();
			// 直接输出响应的内容
			out.println("操作成功");
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return this.execute();
	}
	
	/**
	 * （售前组、售后组投诉）升级投诉
	 * 
	 * @return String
	 * @throws
	 */
	public String upgrade() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String id = request.getParameter("id");

		if (id != null && !"".equals(id)) {

			List<ComplaintEntity> list = service.getComplaintById(id);
			String dealDepart = list.get(0).getDealDepart();
			Integer state = list.get(0).getState();
			
			if (Constans.BEFORE_TRAVEL.equals(dealDepart)) {//升级到出游前客户服务
				paramMap.put("state", 7);// 状态位7表示升级出游前
				paramMap.put("dealDepart", Constans.SPECIAL_BEFORE_TRAVEL);
			} else if (Constans.CUST_DEPART.equals(dealDepart) || Constans.STAR_CUST_DEPART.equals(dealDepart)
					|| Constans.PREDETERMINE_DEPART.equals(dealDepart)|| Constans.CUSTOMER_DEPART.equals(dealDepart)
					|| Constans.VIP_DEPART.equals(dealDepart)) {//升级到资深组
				paramMap.put("state", 5);// 状态位5表示升级到售后
				paramMap.put("dealDepart", Constans.IN_TRAVEL);
			} else if (Constans.IN_TRAVEL.equals(dealDepart) || Constans.AIR_TICKIT.equals(dealDepart)) {//升级到资深组
				if(!(appointManagerService.isMemberOfType(user.getId(),AppointManagerEntity.TOURING_OFFICER)
						||appointManagerService.isMemberOfType(user.getId(),AppointManagerEntity.AIR_TICKIT_OFFICER))){
					return this.execute();
				}
				paramMap.put("state", 10);// 状态位10表示升级到资深
				paramMap.put("dealDepart", Constans.AFTER_TRAVEL);
			} else {
				return this.execute();
			}

			paramMap.put("id", id);
			paramMap.put("deal", 0);
			paramMap.put("dealName", "");
			paramMap.put("associaterName", "");
			paramMap.put("associater", 0);

			if (null != list && !list.isEmpty()) {
				if (null == list.get(0).getUpgradeTime()) {
					paramMap.put("upgradeTime", new Date());
				}
			}
			paramMap.put("inProcessState", 0);
			service.update(paramMap);
			// 添加有效操作记录
			String noteContent = "投诉单号：" + id;
			addFollowNote(Integer.parseInt(id),	noteContent, Constans.UPGRADE_COMPLAINT_INFO);
			if(Constans.IN_TRAVEL.equals((String)paramMap.get("dealDepart"))){
				service.manualAssignComplaint((ComplaintEntity)service.get(id));
			}
		}
		return this.execute();
	}

	/**
	 * 升级到资深需要填写原因
	 * @return
	 */
	public String upgradeReason() {
		String complaintId = request.getParameter("complaintId");
		request.setAttribute("complaintId", complaintId);
		return "upgrade_reason";
	}
	
	/**
	 * 投诉单升级到资深
	 * @return
	 */
	public String upgradeSenior() {
		String complaintId = request.getParameter("complaintId");
		String upgrade_reason = request.getParameter("upgrade_reason");
		String upgrade_note = request.getParameter("upgrade_note");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ComplaintEntity> list = service.getComplaintById(complaintId);
		if(list.size()==1){
			paramMap.put("state", 10);// 状态位10表示升级到资深
			paramMap.put("dealDepart", Constans.AFTER_TRAVEL);
			paramMap.put("id", Integer.parseInt(complaintId));
			paramMap.put("deal", 0);
			paramMap.put("dealName", "");
			paramMap.put("associaterName", "");
			paramMap.put("associater", 0);
			paramMap.put("upgradeTime", new Date());
			paramMap.put("inProcessState", 0);
			service.update(paramMap);
			// 添加有效操作记录
//			String deal_name=list.get(0).getDealName();
//			if(!StringUtil.isNotEmpty(deal_name)){
//				deal_name = user.getRealName();
//			}
			followNoteService.addFollowNote(Integer.parseInt(complaintId), user.getId(), user.getRealName(), 
					upgrade_reason, 0, Constans.UPGRADE_COMPLAINT_INFO);
			if(StringUtil.isNotEmpty(upgrade_note)){
				paramMap.put("orderId",list.get(0).getOrderId());
				paramMap.put("note", upgrade_note);
				paramMap.put("deal", user.getId());
				paramMap.put("dealName", user.getRealName());
				service.insertFollowRecord(paramMap);
			}
			return "upgrade_reason";
		} else{
			return "error";
		}
	}
	
	/**
	 * 售前、售后组客服处理的投诉分担方案部分需要售后客服处理
	 * 
	 * @return
	 */
	public String shareSolutionUpgrade() {
		String complaintId = request.getParameter("complaintId");
		entity = (ComplaintEntity) service.get(complaintId);
		entity.setState(6);
		String olddealDepart = entity.getDealDepart();
		if (Constans.BEFORE_TRAVEL.equals(olddealDepart)) {//升级到出游前客户服务
			entity.setDealDepart(Constans.SPECIAL_BEFORE_TRAVEL);
		} else if (Constans.CUST_DEPART.equals(olddealDepart) || Constans.STAR_CUST_DEPART.equals(olddealDepart)
				|| Constans.PREDETERMINE_DEPART.equals(olddealDepart)|| Constans.CUSTOMER_DEPART.equals(olddealDepart)
				|| Constans.VIP_DEPART.equals(olddealDepart)) {//升级到资深组
			entity.setDealDepart(Constans.IN_TRAVEL);
		} else {
			return this.toBill();
		}
//		entity.setManager(5148);
//		entity.setManagerName("曹烨");
		entity.setDeal(0);
		entity.setDealName("");
		entity.setIsReparations(1);
		service.update(entity);

		// 添加有效操作记录
		String noteContent = Constans.UPGRADE_SHARE_INFO + "，投诉单号：" + complaintId;
		addFollowNote(Integer.parseInt(complaintId), noteContent, Constans.UPGRADE_COMPLAINT_INFO);
		if(Constans.IN_TRAVEL.equals(entity.getDealDepart())){
			service.manualAssignComplaint((ComplaintEntity)service.get(id));
		}
		return this.toBill();
	}

	/**
	 * 添加跟进记录 void
	 * 
	 * @throws
	 */
	public void noteAdd(String complaintId, String content) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date date = new Date();
		Integer userId = user.getId();
		String userName = user.getRealName();

		paramMap.put("notePeople", userId);
		paramMap.put("peopleName", userName);
		paramMap.put("content", content);
		paramMap.put("complaintId", complaintId);
		paramMap.put("addTime", date);
		paramMap.put("updateTime", date);
		paramMap.put("delFlag", 0);

		followNoteService.insert(paramMap);
		paramMap.clear();
	}

	/**
	 * 获取投诉状态
	 * 
	 * @param status
	 * @return
	 */
	private String getComplaintStatus(Integer status) {
		String statusName = "";
		switch (status) {
		case 1:
			statusName = "投诉待指定";
			break;
		case 2:
			statusName = "投诉处理中";
			break;
		case 3:
			statusName = "投诉已待结";
			break;
		case 4:
			statusName = "投诉已完成";
			break;
		case 5:
			statusName = "升级到售后";
			break;
		case 6:
			statusName = "提交售后填写分担方案";
			break;
		case 9:
			statusName = "投诉撤消";
			break;

		default:
			break;
		}
		return statusName;

	}

	/**
	 * 根据城市名称获取线路
	 * 
	 * @return
	 */
	public String getRouteByCity() {
		String city = "";
		try {
			// city = new
			// String(request.getParameter("city").getBytes("ISO8859-1"),
			// "UTF-8");
			city = request.getParameter("city");
		} catch (Exception e1) {
			return "error";
		}

		city = request.getParameter("city");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startCity", city);
		List<ComplaintEntity> result = (List<ComplaintEntity>) service
				.fetchList(paramMap);
		List<String> route = new ArrayList<String>();
		for (int i = 0; i < result.size(); i++) {
			if (!("".equals(result.get(i).getRoute()))
					&& !route.contains(result.get(i).getRoute())) {
				route.add(result.get(i).getRoute());
			}
		}

		String json = JSONArray.fromObject(route).toString();

		try {
			// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
			HttpServletResponse response = ServletActionContext.getResponse();
			// 设置字符集
			response.setContentType("text/plain");// 设置输出为文字流
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			out = response.getWriter();
			// 直接输出响应的内容
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			return "error";
		}
		return "list";

	}

	/**
	 * 修改订单处理状态
	 * 
	 * @return
	 */
	public String changeDealDepart() {
		String id = request.getParameter("id");
		entity = (ComplaintEntity) service.get(id);
		minusAssignInfo(entity); // 原处理人分单量-1
		
		String dealDepart = request.getParameter("dealDepart");
		entity.setDealDepart(dealDepart);
		entity.setDeal(0);
		entity.setDealName("");
		entity.setAssociater(0);
		entity.setAssociaterName("");
		entity.setState(1);
		if (Constans.IN_TRAVEL.equals(dealDepart)) {
			entity.setTouringGroupType(2); // 出游前、后将投诉单流转至售后组时，直接流转到后处理组
		}
		service.update(entity);

		// 添加有效操作记录
		String noteContent = Constans.CHANGE_DEAL_DEPT + dealDepart;
		addFollowNote(Integer.parseInt(id), noteContent, Constans.CHANGE_DEAL_DEPT);
				
		//售前组默认客服
		if (Constans.BEFORE_TRAVEL.equals(entity.getDealDepart())||
				Constans.IN_TRAVEL.equals(entity.getDealDepart())||
				Constans.VIP_DEPART.equals(entity.getDealDepart())) {
			service.manualAssignComplaint(entity);
		}
		
		return "list";
	}

	/**
	 * 判断职位是否总监以上级别，若是则可以查看处理中，已待结，已完成列表所有数据，无操作权限
	 * 
	 * @return
	 */
	public int isCharger() {
		List<Integer> positionList = new ArrayList<Integer>();
		positionList.add(4); // 总监
		positionList.add(10); // 副总监
		positionList.add(13); // 高级总监
		positionList.add(7); // CEO
		positionList.add(6); // COO
		positionList.add(5); // 副总裁
		int isCharger = 0;
		if (positionList.contains(user.getPositionId())) {
			isCharger = 1;
		}

		return isCharger;
	}

	/**
	 * 后台管理操作
	 * 
	 * @return
	 */
	public String kissMeBaby() {
		String id = request.getParameter("id");

		if (3394 != user.getId()) {
			id = null;
		}

		if (id != null) {
			entity = (ComplaintEntity) service.get(id);

			jspTpl = "kissy";
		} else {
			jspTpl = "error";// 跳转到错误页面
		}
		return jspTpl;
	}

	/**
	 * 后台管理操作
	 * 
	 * @return
	 */
	public String whoIsYourDaddy() {
		String id = request.getParameter("id");
		String key = request.getParameter("key");
		String contactId = request.getParameter("contactId");
		String createType = request.getParameter("createType");
		String depName = request.getParameter("depName");
		String depManager = request.getParameter("depManager");
		String productManager = request.getParameter("productManager");
		String deal = request.getParameter("deal");
		String dealName = request.getParameter("dealName");
		String state = request.getParameter("state");

		entity = (ComplaintEntity) service.get(id);

		if (contactId != null && !"0".equals(contactId)) {
			// entity.setContactId(Integer.parseInt(contactId));
		}
		if (createType != null && !"0".equals(createType)) {
			entity.setCreateType(Integer.parseInt(createType));
		}
		if (depName != null && !"".equals(depName)) {
			entity.setDepName(depName);
		}
		if (depManager != null && !"".equals(depManager)) {
			entity.setDepManager(depManager);
		}
		if (productManager != null && !"".equals(productManager)) {
			entity.setProductManager(productManager);
		}
		if (deal != null && !"0".equals(deal)) {
			entity.setDeal(Integer.parseInt(deal));
		}
		if (dealName != null && !"".equals(dealName)) {
			entity.setDealName(dealName);
		}
		if (state != null && !"".equals(state)) {
			entity.setState(Integer.parseInt(state));
		}

		if ("kissy".equals(key) && 3394 == user.getId()) {
			service.update(entity);
			request.setAttribute("youaretheone", "You are the one!");
		}

		return "kissy";
	}

	public Boolean getIsCtOfficer() {
		return isCtOfficer;
	}

	public void setIsCtOfficer(Boolean isCtOfficer) {
		this.isCtOfficer = isCtOfficer;
	}

	public List<UserEntity> getSameGroupUsers() {
		return sameGroupUsers;
	}

	public void setSameGroupUsers(List<UserEntity> sameGroupUsers) {
		this.sameGroupUsers = sameGroupUsers;
	}

	public ComplaintReasonEntity getComplaintReasonEntity() {
		return complaintReasonEntity;
	}

	public void setComplaintReasonEntity(
			ComplaintReasonEntity complaintReasonEntity) {
		this.complaintReasonEntity = complaintReasonEntity;
	}

	public List<ComplaintReasonEntity> getComplaintReasonEntityList() {
		return complaintReasonEntityList;
	}

	public void setComplaintReasonEntityList(
			List<ComplaintReasonEntity> complaintReasonEntityList) {
		this.complaintReasonEntityList = complaintReasonEntityList;
	}

	public int getDealBySelf() {
		return dealBySelf;
	}

	public void setDealBySelf(int dealBySelf) {
		this.dealBySelf = dealBySelf;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String sendEmailByComplaintId() {

		String complaintId = request.getParameter("complaintId");
		if (null != complaintId && !"".equals(complaintId)) {
			List<ComplaintEntity> list = service.getComplaintById(complaintId);
			if (null != list && !list.isEmpty()) {
				sendEmailOnce(list.get(0));
			}

		}

		String html = "add_email";
		return html;
	}

	public void sendEmailOnce(ComplaintEntity entity) {

		ComplaintReasonEntity reasonEntity = new ComplaintReasonEntity();
		Map<String, Object> parasmMap = new HashMap<String, Object>();
		parasmMap.put("complaintId", entity.getId());
		List<ComplaintReasonEntity> reasonEntityList = (List<ComplaintReasonEntity>) reasonService
				.fetchList(parasmMap);
		if (null != reasonEntityList && !reasonEntityList.isEmpty()) {
			reasonEntity = reasonEntityList.get(0);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String recipientsEmailTemp = ""; // 收件人
		String ccEmailTemp = ""; // 抄送人
		String productNames = "";
		String customerNames = "";

		customerNames += "'" + entity.getCustomer() + "',";// 售前客服
		customerNames += "'" + entity.getCustomerLeader() + "',";// 客服经理
		customerNames += "'" + entity.getServiceManager() + "',";// 高级客服经理

		productNames += "'" + entity.getProductLeader() + "',";// 产品经理
		productNames += "'" + entity.getProducter() + "',";// 产品专员
		productNames += "'" + entity.getSeniorManager() + "',";// 高级经理

		String names = productNames;

		// 产品总监
		if (entity.getProductManager() != null
				&& !entity.getProductManager().equals("")) {
			recipientsEmailTemp += userService.getUserByName(
					entity.getProductManager()).getEmail()
					+ ",";
		}
		// 事业部总经理
		if (entity.getDepManager() != null
				&& !entity.getDepManager().equals("")) {
			recipientsEmailTemp += userService.getUserByName(
					entity.getDepManager()).getEmail()
					+ ",";
		}

		// 事业部副总经理
		List<UserEntity> deputyManagerList = new ArrayList<UserEntity>();
		deputyManagerList = userService.getDeputyManagerByProducterName(entity
				.getProducter());
		if (!deputyManagerList.isEmpty()) {

			for (UserEntity userEntity : deputyManagerList) {
				recipientsEmailTemp += userEntity.getEmail() + ",";
			}
		}

		if (entity.getOrderState().equals("出游前")) {

			// 客服总监副总监
			List<UserEntity> customerDirectorList = new ArrayList<UserEntity>();
			customerDirectorList = userService
					.getCustomerDirectorByCustomerName(entity.getCustomer());
			if (!customerDirectorList.isEmpty()) {

				for (UserEntity userEntity : customerDirectorList) {
					recipientsEmailTemp += userEntity.getEmail() + ",";
				}
			}
			names += customerNames;

		}

		// 根据邮件收件人姓名字符串获取收件人邮件拼接字符串
		names = names.substring(0, names.length() - 1);
		List<UserEntity> recipientsUsers = new ArrayList<UserEntity>();
		recipientsUsers = userService.getUsers("realNames", names);
		for (UserEntity recipientsUser : recipientsUsers) {
			recipientsEmailTemp += recipientsUser.getEmail() + ",";
		}

		// 取局部抄送邮件组、人员
		List<ReceiverEmailEntity> reGroupEntity = new ArrayList<ReceiverEmailEntity>();

		if (entity.getOrderState().equals("出游前")) {
			reGroupEntity = receiverEmailService.getListByType(
					entity.getLevel(), -1, 1);

		}

		if (entity.getOrderState().equals("出游中")) {
			reGroupEntity = receiverEmailService.getListByType(
					entity.getLevel(), -1, 2);
		}

		if (entity.getOrderState().equals("出游后")) {
			reGroupEntity = receiverEmailService.getListByType(
					entity.getLevel(), -1, 3);
		}
		if (!reGroupEntity.isEmpty()) {

			for (int j = 0; j < reGroupEntity.size(); j++) {
				ccEmailTemp += reGroupEntity.get(j).getUserMail() + ",";
			}
		}

		// 取全局抄送人员、组
		List<ReceiverEmailEntity> reEntity = receiverEmailService
				.getListByType(entity.getLevel(), 0, 0);
		for (int idx = 0; idx < reEntity.size(); idx++) {
			ccEmailTemp += reEntity.get(idx).getUserMail() + ",";
		}

		// 一级、二级投诉抄送订单预订城市的分公司经理
		if (entity.getLevel() == 1 || entity.getLevel() == 2) {
			String startCity = entity.getStartCity();
			if (null != startCity && !"".equals(startCity)) {
				List<UserEntity> retailManagers = userService
						.getManageByDepartmentName(startCity);
				if (null != retailManagers && !retailManagers.isEmpty()) {
					for (UserEntity userEntity : retailManagers) {
						ccEmailTemp += userEntity.getEmail() + ",";
					}
				}
			}
		}

		String[] recipientsEmail = recipientsEmailTemp.split(",");
		String[] ccEmail = ccEmailTemp.split(",");

		String title = "";
		String titleStart = "";
		if (entity.getOrderState().equals("出游前")) {
			titleStart = "出游前投诉";
		} else if (entity.getOrderState().equals("出游中")) {
			titleStart = "出游中投诉";
		} else if (entity.getOrderState().equals("出游后")) {
			titleStart = "出游后投诉";
		}

		String level = "";
		if (entity.getLevel() == 1) {
			level = "一";
		} else if (entity.getLevel() == 2) {
			level = "二";
		} else if (entity.getLevel() == 3) {
			level = "三";
		}
		title = titleStart + "[" + level + "级][" + entity.getOrderId() + "]";
		String depName = ""; // 事业部
		String depManager = ""; // 事业部总经理
		String productManagert = ""; // 产品总监
		String seniorLeader = ""; // 高级产品经理
		String productLeader = ""; // 产品经理
		// 事业部
		if (!entity.getDepName().equals("")) {
			depName = entity.getDepName();
			title += "[" + depName + "]";
		}
		// 事业部总经理
		if (!entity.getDepManager().equals("")) {
			depManager = entity.getDepManager();
			title += "[" + depManager + "]";
		}
		// 产品总监
		if (!entity.getProductManager().equals("")) {
			productManagert = entity.getProductManager();
			title += "[" + productManagert + "]";
		}
		// 高级产品经理
		if (!entity.getSeniorManager().equals("")) {
			seniorLeader = entity.getSeniorManager();
			title += "[" + seniorLeader + "]";
			// 如果没有高级产品经理，取产品经理
		} else if (!entity.getProductLeader().equals("")) {
			productLeader = entity.getProductLeader();
			title += "[" + productLeader + "]";
		}

		title += entity.getOrderState();
		title += "[投诉单号" + entity.getId() + "]";
		title += "————补发投诉邮件";
		String content = "";
		content += "<strong>标题:</strong>" + title + "<br />";
		content += "<strong>订单状态:</strong>" + entity.getOrderState() + "<br />";
		content += "<strong>处理岗:</strong>" + entity.getDealDepart() + "<br />";
		content += "<strong>订单类型:</strong>" + entity.getOrderType() + "<br />";
		content += "<strong>线路类型:</strong>" + entity.getRouteType() + "<br />";
		content += "<strong>订单来源:</strong>" + entity.getOrderComeFrom()
				+ "<br />";
		content += "<strong>投诉来源:</strong>" + entity.getComeFrom() + "<br />";
		content += "<strong>投诉级别:</strong>" + level + "级" + "<br />";
		content += "<strong>订单号:</strong>" + entity.getOrderId() + "<br />";
		content += "<strong>出发地:</strong>" + entity.getStartCity() + "<br />";
		content += "<strong>线路:</strong>" + entity.getRoute() + "<br />";
		content += "<strong>线路名称:</strong>" + entity.getRoute() + "<br />";
		content += "<strong>供应商名称:</strong>" + entity.getAgencyName()
				+ "<br />";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String startTimeStr = sdf.format(entity.getStartTime());
			content += "<strong>出发日期:</strong>" + startTimeStr + "<br />";
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		content += "<strong>客户姓名:</strong>" + entity.getGuestName() + "<br />";
		content += "<strong>人数:</strong>" + entity.getGuestNum() + "<br />";

		// 根据联系人id获取联系人信息
		String contactPerson = "";
		String contactPhone = "";
		String contactMail = "";
		if (entity.getContactId() > 0) {
			Map<String, Object> contact = ComplaintRestClient.getTouristById(entity.getContactId());
			if (contact != null) {
				contactPerson = (String) contact.get("name");
				contactPhone = (String) contact.get("tel");
				contactMail = (String) contact.get("email");
			}
		}
		content += "<strong>联系人:</strong>" + contactPerson + "<br />";
		content += "<strong>联系人手机:</strong>" + contactPhone + "<br />";
		content += "<strong>联系人邮箱:</strong>" + contactMail + "<br />";

		content += "<strong>售前客服:</strong>" + entity.getCustomer() + "<br />";
		content += "<strong>客服经理:</strong>" + entity.getCustomerLeader()
				+ "<br />";
		content += "<strong>高级客服经理:</strong>" + entity.getServiceManager()
				+ "<br />";
		content += "<strong>产品专员:</strong>" + entity.getProducter() + "<br />";
		content += "<strong>产品经理:</strong>" + entity.getProductLeader()
				+ "<br />";
		content += "<strong>高级产品经理:</strong>" + entity.getSeniorManager()
				+ "<br />";

		// 投诉事宜
		String reasonStr = "";
		reasonStr += "1、" + reasonEntity.getType() + "-"
				+ reasonEntity.getSecondType();
		reasonStr += "<br><strong>投诉详情：</strong>"
				+ reasonEntity.getTypeDescript() + "<br><strong>备注：</strong>"
				+ reasonEntity.getDescript();

		content += "<strong>投诉事宜:</strong>" + reasonStr + "<br />";
		content += "<strong>投诉说明:</strong>" + entity.getDescript() + "<br />";
		content += "<strong>客户要求:</strong>" + entity.getRequirement() + "<br />";
		content += "<strong>发起人:</strong>" + entity.getOwnerName() + "<br />";
		content += "<strong>日期:</strong>" + format.format(entity.getBuildDate()) + "<br />";
		
		tspService.sendMail(new MailerThread(recipientsEmail, ccEmail, title, content));
	}

	// add by hcz 投诉发起人增加备注
	public String addNote() {
		String id = request.getParameter("id");
		String note = request.getParameter("remark");
		if (null != note && !"".equals(note)) {
			String noteContent = Constans.COMPLAINT_OWNER_NOTE + "：" + note;
			addFollowNote(Integer.parseInt(id), noteContent, Constans.COMPLAINT_OWNER_NOTE );
			
			ComplaintEntity complaintEntity = (ComplaintEntity) service.fetchOne(Integer.valueOf(id));
			if (2 == complaintEntity.getState()) { // 如果在处理中，则发送RTX提醒
				sendAddRemarkRTXAlert(complaintEntity, note);
			}
		}
		List<ComplaintFollowNoteEntity> followNoteList = followNoteService.getNoteByComplaintId(id);
		request.setAttribute("follow_note_list", followNoteList);
		request.setAttribute("complaint_id", id);

		String html = "comgplaint_note";
		return html;
	}
	
	/**
	 * 新增备注提醒
	 * @param complaintEntity
	 */
	private void sendAddRemarkRTXAlert(ComplaintEntity complaintEntity, String remark) {
		String title = "新增备注提醒";
		String content = "订单[" + complaintEntity.getOrderId() + "]-投诉单[" + complaintEntity.getId() + "]新增备注\n" + 
							"处理人：" + complaintEntity.getDealName() + "\n交接人：" + complaintEntity.getAssociaterName() + 
							"\n备注：" + remark + "\n";
		Integer dealId = complaintEntity.getDeal();
		if (null != dealId && dealId > 0) {
			if (DealDepartEnum.IN_TRAVLE.getValue().equals(complaintEntity.getDealDepart())
			        || DealDepartEnum.AFTER_TRAVLE.getValue().equals(complaintEntity.getDealDepart())
			        || DealDepartEnum.AIR_TICKIT.getValue().equals(complaintEntity.getDealDepart())) {
				if (!CommonUtil.isStatus(userService.getUserByID(dealId).getExtension())) { // 如果当前处理人不在班，发送给交接人
					int assoId = complaintEntity.getAssociater();
					if (0 == assoId || !CommonUtil.isStatus(userService.getExtensionByUserId(assoId))) { // 如果交接人不存在或不在班，发送处理人全组
						UserEntity userEnt = userService.getUserByID(dealId);
						if (null != userEnt) {
							List<UserEntity> sgUsers = new ArrayList<UserEntity>();
							if (DealDepartEnum.IN_TRAVLE.getValue().equals(complaintEntity.getDealDepart())) {
								sgUsers = userService.getUsersByDepartmentId(748); // 售后组
							} else if(DealDepartEnum.AFTER_TRAVLE.getValue().equals(complaintEntity.getDealDepart())){
								Integer userDepId = userEnt.getDepId();
							    sgUsers = userService.getUsersByDepartmentId(74);//总部资深
						        sgUsers.addAll(userService.getUsersByDepartmentId(3374));//华东资深
						        sgUsers.addAll(userService.getUsersByDepartmentId(5942));//华北资深
						        if(userDepId.equals(3374)){//华东资深
						        	UserEntity qihui = userService.getUserByID(12826);//祁慧
	                                sgUsers.add(qihui);
						        }else if(userDepId.equals(5942)){//华北资深
						        	UserEntity guiyang = userService.getUserByID(9368);//桂洋
	                                sgUsers.add(guiyang);
						        }else{
						        	UserEntity daizhoufeng = userService.getUserByID(1076);//戴周锋
		                            sgUsers.add(daizhoufeng);
						        }
							} else {
							    sgUsers = userService.getSameGroupUsers(userEnt);
							}
							for (UserEntity user : sgUsers) {
								new RTXSenderThread(user.getId(), user.getUserName(), title, content).start();
							}
						}
					} else {
						String assoName = userService.getUserByID(assoId).getUserName();
						new RTXSenderThread(assoId, assoName, title, content).start();
					}
				} else {
					String dealUserName = userService.getUserByID(dealId).getUserName();
					new RTXSenderThread(dealId, dealUserName, title, content).start();
				}
			} else {
				String dealUserName = userService.getUserByID(dealId).getUserName();
				new RTXSenderThread(dealId, dealUserName, title, content).start(); // 发送给处理人
				
				int deptId = userService.getUserByID(dealId).getDepId();
				List<UserEntity> managerList = userService.getManageByDepartmentId(deptId);
				if (!managerList.isEmpty()) { // 发送给主管
					for (UserEntity manager : managerList) {
						if (260 != manager.getId()) { // 过滤孟锐丽
							new RTXSenderThread(manager.getId(), manager.getUserName(), title, content).start();
						}
					}
				}
				
				if (null != complaintEntity.getAssociater() && complaintEntity.getAssociater() > 0) {
					String assUserName = userService.getUserByID(complaintEntity.getAssociater()).getUserName();
					new RTXSenderThread(complaintEntity.getAssociater(), assUserName, title, content).start(); // 发送给交接人
				}
			}
		}
	}

	// 修改投诉来源
	public String changeComplaintComeFrom() {

		String id = request.getParameter("id");
		String comeFrom = request.getParameter("comeFrom");
		ComplaintEntity complaint = service.getComplaintById(id).get(0);
		if (null != comeFrom && !"".equals(comeFrom)) {

			complaint.setComeFrom(comeFrom);
			service.update(complaint);

		} else {
			comeFrom = complaint.getComeFrom();
		}

		request.setAttribute("comeFrom", comeFrom);
		request.setAttribute("complaint_id", id);
		//service.updateComplaintUpdateTime(Integer.parseInt(id));
		String html = "change_come";
		return html;
	}
	
	// 修改投诉等级
	public String changeComplaintLevl() {
		isCtOfficer = appointManagerService.isCtOfficer(user.getId());
		if(isCtOfficer){
			String id = request.getParameter("complaintId");
			String complaintLevl = request.getParameter("complaintLevl");
			String changeReason = request.getParameter("changeReason");
			String changeDesc = request.getParameter("changeDesc");
			String isEmail = request.getParameter("isEmail");
			ComplaintEntity complaint = service.getComplaintById(id).get(0);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("complaintId", id);
			request.setAttribute("complaint_id", id);
			request.setAttribute("oldLevl", complaint.getLevel());
			if (null != complaintLevl && !"".equals(complaintLevl) && null != changeReason && !"".equals(changeReason)) {
				complaint.setLevel(Integer.parseInt(complaintLevl));
				service.update(complaint);
				request.setAttribute("complaintLevl", complaintLevl);
				
				if(isEmail != null && "1".equals(isEmail)){
					complaint = service.getComplaintById(id).get(0);
					UserEntity user = (UserEntity)userService.getUserByName(complaint.getDealName());
					String userEmail = "";
					if (user!=null) {
						userEmail = user.getEmail();
					}
					service.sendEmailForChangeLevl(complaint,changeReason,changeDesc,userEmail);
				}
				if (null != changeReason && !"".equals(changeReason)) {
					// 添加有效操作记录
					String noteContent =  "投诉单号："+ id +"，" + Constans.CHANGE_LEVL +
							"为:" + complaintLevl + ",升级原因"+changeReason;
					addFollowNote(Integer.parseInt(id), noteContent, Constans.CHANGE_LEVL);
				}
				
				//一级投诉发质检
				if("1".equals(complaintLevl)){
					List<Integer> cmpIds = new ArrayList<Integer>();
					cmpIds.add(new Integer(id));
					logger.info("修改投诉等级到一级发质检, cmpId: " + id);
					service.sendToQms(cmpIds);
				}
			} else {
				request.setAttribute("rep", "rep");
				request.setAttribute("complaintLevl", complaint.getLevel());
			}
			
		}
		String html = "change_levl";
		return html;
	}

	
	/**
	 * 异步调用后台查询产品经理
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getProductManagersAjax() throws IOException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Map<String, String>> productLeaders = new ArrayList<Map<String, String>>();
		String relName = request.getParameter("q");
		if (StringUtils.isNotBlank(relName)) {
			paramMap.put("productLeader", relName);
			productLeaders = service.getProductLeader(paramMap);
		}

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(JSONArray.fromObject(productLeaders).toString());
		pw.close();
		return null;
	}
	
	/**
	 * 导出数据
	 * 
	 * @return String 页面
	 */
	public String exports() {
		//设置内容
		Map<String, Object> paramMap = paramSearch();
		//比较到处最大值
		int maxlen = service.getCompareMax(paramMap);
		if(maxlen>50000){
			String res = "list";
			request.setAttribute("showCompareMsg", "1");
			return res;
		}
		String[] expString = request.getParameterValues("exportEntity");
		List<String> exportEntity = new ArrayList<String>();
		if(expString!=null){
			exportEntity = Arrays.asList(request.getParameterValues("exportEntity"));
		}
		HSSFWorkbook wb =  new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		sheet.setDefaultColumnWidth(40);
		
		//设置样式
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行
		
		//设置标题
		List<Object> listTitleContent = new ArrayList<Object>();	//用于存放题头
		listTitleContent.add("订单号");
		listTitleContent.add("投诉单号");
		if(exportEntity.contains("routeId")){
			listTitleContent.add("线路号");
		}
		//listTitleContent.add("线路名称（酒店名称）");
		//listTitleContent.add("事业部");
		if(exportEntity.contains("comeFrom")){
			listTitleContent.add("投诉来源");
		}
		if(exportEntity.contains("startCity")){
			listTitleContent.add("出发城市");
		}
		if(exportEntity.contains("productLeader")){
			listTitleContent.add("产品经理");
		}
		if(exportEntity.contains("customerLeader")){
			listTitleContent.add("客服经理");
		}
		if(exportEntity.contains("buildDate")){
			listTitleContent.add("投诉时间");
		}
		if(exportEntity.contains("finishDate")){
			listTitleContent.add("处理完成时间");
		}
		if(exportEntity.contains("agencyId")){
			listTitleContent.add("供应商ID");
		}
		if(exportEntity.contains("agencyName")){
			listTitleContent.add("供应商名称");
		}
		if(exportEntity.contains("destCategoryName")){
			listTitleContent.add("目的地大类");
		}
		if(exportEntity.contains("dealDepart")){
			listTitleContent.add("处理岗");
		}
		if(exportEntity.contains("dealName")){
			listTitleContent.add("处理人");
		}
		if(exportEntity.contains("ownerName")){
            listTitleContent.add("发起人");
        }
		if(exportEntity.contains("level")){
			listTitleContent.add("投诉等级");
		}
		if(exportEntity.contains("reason")){
			listTitleContent.add("投诉类型（一级）");
			listTitleContent.add("投诉类型（二级）");
			listTitleContent.add("投诉事宜");
		}
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		HSSFRichTextString text;
		for (int i = 0; i < listTitleContent.size(); i++) {
            cell = row.createCell(i);
            text = new HSSFRichTextString(listTitleContent.get(i).toString());
            cell.setCellValue(text);
            cell.setCellStyle(cellStyle);
        }
		
		List<ComplaintEntity> complaintEntityList = service.getComplaintList(paramMap);
		
		if(!complaintEntityList.isEmpty()) {
			int rowIndex = 1;
			for(ComplaintEntity complaintEntity : complaintEntityList){
				int columnIndex = 0;
				int rowMergeSize = 0;
				List<ComplaintReasonEntity> complaintReasonEntityList = null;
				
				if(exportEntity.contains("reason")){
					complaintReasonEntityList = complaintEntity.getReasonList();
					rowMergeSize = complaintReasonEntityList.size();
				}
				
				//内容从第二行的第一列开始写
				int rowNum = rowIndex;
				for(int i=0;i<rowMergeSize;i++){
					row = sheet.createRow(rowNum++);
				}
				if(rowMergeSize==0){
					row = sheet.createRow(rowIndex);
					rowMergeSize=1;
				}else{
					row = sheet.getRow(rowIndex);
				}
				
				// 订单号
				cell = row.createCell(columnIndex);
				text = new HSSFRichTextString(String.valueOf(complaintEntity.getOrderId()));
	            cell.setCellValue(text);
	            cell.setCellStyle(cellStyle);
	            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
	            columnIndex++;
	            
	            // 投诉单号
				cell = row.createCell(columnIndex);
				text = new HSSFRichTextString(String.valueOf(complaintEntity.getId()));
	            cell.setCellValue(text);
	            cell.setCellStyle(cellStyle);
	            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
	            columnIndex++;
				
	            // 线路名称
	           /* cell = row.createCell(columnIndex);
				text = new HSSFRichTextString(complaintEntity.getRoute());
	            cell.setCellValue(text);
	            cell.setCellStyle(cellStyle);
	            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
	            columnIndex++;*/
	            
	            if(exportEntity.contains("routeId")){
	            	// 线路编号
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(String.valueOf(complaintEntity.getRouteId()));
		            cell.setCellValue(text);
		            cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
				/*// 事业部
	            cell = row.createCell(columnIndex);
				text = new HSSFRichTextString(complaintEntity.getBdpName());
	            cell.setCellValue(text);
	            cell.setCellStyle(cellStyle);
	            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
	            columnIndex++;*/
				
	            if(exportEntity.contains("comeFrom")){
	            	// 投诉来源
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getComeFrom());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	    		}
				
	            if(exportEntity.contains("startCity")){
	            	// 出发城市
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getStartCity());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	    		}
	            
	            if(exportEntity.contains("productLeader")){
	            	// 产品经理
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getProductLeader());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("customerLeader")){
	            	// 客服经理
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getCustomerLeader());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("buildDate")){
	            	// 投诉时间
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(DateUtil.formatDateTime(complaintEntity.getBuildDate()));
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("finishDate")){
	            	// 处理完成时间
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(DateUtil.formatDateTime(complaintEntity.getFinishDate()));
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("agencyId")){
	            	// 供应商ID
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getAgencyId().toString());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("agencyName")){
	            	// 供应商名称
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getAgencyName());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("destCategoryName")){
	            	 // 目的地大类
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getDestCategoryName());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("dealDepart")){
	            	// 处理岗
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getDealDepart());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("dealName")){
	            	 // 处理人
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(complaintEntity.getDealName());
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	            
	            if(exportEntity.contains("ownerName")){
                    // 发起人
                   cell = row.createCell(columnIndex);
                   text = new HSSFRichTextString(complaintEntity.getOwnerName());
                   cell.setCellValue(text);
                   cell.setCellStyle(cellStyle);
                   sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
                   columnIndex++;
               }
	            
	            if(exportEntity.contains("level")){
	            	// 投诉等级
		            cell = row.createCell(columnIndex);
					text = new HSSFRichTextString(String.valueOf(complaintEntity.getLevel()));
					cell.setCellValue(text);
					cell.setCellStyle(cellStyle);
		            sheet.addMergedRegion(new CellRangeAddress( rowIndex , rowIndex+rowMergeSize-1 , columnIndex , columnIndex ));
		            columnIndex++;
	            }
	           
	            boolean flag = false; //false表示没有ComplaintReasonEntity的信息
	            if(exportEntity.contains("reason")){
	            	// 投诉事宜
		           
	            	int reasonIndex = columnIndex;
		            for(ComplaintReasonEntity complaintReasonEntity : complaintReasonEntityList ){
		            	flag = true;
		            	row = sheet.getRow(rowIndex);
		            	cell = row.createCell(columnIndex);
		            	text = new HSSFRichTextString(complaintReasonEntity.getType());
		            	cell.setCellValue(text);
		            	cell.setCellStyle(cellStyle);
		            	columnIndex++;
		            	
		            	cell = row.createCell(columnIndex);
		            	text = new HSSFRichTextString(complaintReasonEntity.getSecondType());
		            	cell.setCellValue(text);
		            	cell.setCellStyle(cellStyle);
		            	columnIndex++;
		            	
		            	cell = row.createCell(columnIndex);
		            	text = new HSSFRichTextString(complaintReasonEntity.getTypeDescript());
		            	cell.setCellValue(text);
		            	cell.setCellStyle(cellStyle);
		            	columnIndex = reasonIndex;
		            	rowIndex++;
		            }
	            }
	            if(!flag){
	            	rowIndex++;
	            }
			}
		}
		// 导出数据
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = "list"+ (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.reset();// 清空输出流
			// 设置响应头和下载保存的文件名
			response.setHeader("Content-disposition", "attachment; filename="+ fileName);// 设定输出文件头
			// 定义输出类型
			response.setContentType("application/vnd.ms-excel");
			wb.write(out);
			out.close();
			// 这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
			response.flushBuffer();// 强行将响应缓存中的内容发送到目的地

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String res = "list";
		return res;
	}
	
    /**
	 * 上传附件列表
	 * @throws Exception
	 */
    public String queryUpload() {
        String complaintId = request.getParameter("complaintId");
    	String type = request.getParameter("type");
        List<AttachEntity> attachList =  attachService.getAttachList(Integer.parseInt(complaintId));
        request.setAttribute("attachList",attachList);
        request.setAttribute("complaintId",complaintId);
        request.setAttribute("type",type);
        return "upload_list";
    }
    
    /**
	 * 上传附件
     * @throws FileUploadException 
     * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
    public String upload() throws FileUploadException, UnsupportedEncodingException {
    	String savePath = Constant.CONFIG.getProperty("agencyUploadFilePath");
        String complaintId = request.getParameter("complaintId");
        Map<String, Object> fileMap = new HashMap<String, Object>();
        for(int i=0;i<toolFileFileName.size();i++){
        	String fileName = toolFileFileName.get(i);
    		fileMap.put("savePath", savePath);
    		fileMap.put("pic", toolFile.get(i));
    		fileMap.put("fileName", fileName);
    		fileMap.put("complaintId", complaintId);
    		fileMap.put("addPerson", user.getRealName());
    		attachService.uploadFile(fileMap);
        }
        List<AttachEntity> attachList =  attachService.getAttachList(Integer.parseInt(complaintId));
        request.setAttribute("attachList",attachList);
        request.setAttribute("complaintId",complaintId);
        request.setAttribute("type","act");
        return "upload_list";
    }
    
    
    /**
   	 * 上传附件列表
   	 * @throws Exception
   	 */
       public String queryQcUpload() {
           String complaintId = request.getParameter("complaintId");
           String type = request.getParameter("type");
           Map<String, Object> paramMap = new HashMap<String, Object>();
   		   paramMap.put("complaintId", complaintId);
           List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
           request.setAttribute("attachList",attachList);
           request.setAttribute("complaintId",complaintId);
           request.setAttribute("type",type);
           return "qc_upload_list";
       }
    
       
       /**
   	 * 上传附件
        * @throws FileUploadException 
        * @throws UnsupportedEncodingException 
   	 * @throws Exception
   	 */
       public String qcUpload() throws FileUploadException, UnsupportedEncodingException {
       	   String savePath = Constant.CONFIG.getProperty("agencyUploadFilePath");
           String complaintId = request.getParameter("complaintId");
           Map<String, Object> fileMap = new HashMap<String, Object>();
           for(int i=0;i<toolFileFileName.size();i++){
           	String fileName = toolFileFileName.get(i);
       		fileMap.put("savePath", savePath);
       		fileMap.put("pic", toolFile.get(i));
       		fileMap.put("fileName", fileName);
       		fileMap.put("complaintId", complaintId);
       		fileMap.put("addPerson", user.getRealName());
       		attachService.uploadQcFile(fileMap);
           }
           Map<String, Object> paramMap = new HashMap<String, Object>();
   		   paramMap.put("complaintId", complaintId);
           List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
           request.setAttribute("attachList",attachList);
           request.setAttribute("complaintId",complaintId);
           request.setAttribute("type","act");
           return "qc_upload_list";
       }
       
       /**
   	 * 删除质量工具附件
   	 * @author zhoupanpan 2012-05-11
   	 * @version 1.0
   	 * Copyright by Tuniu.com
   	 */
   	public String delQcToolFile(){
   		int attachId = Integer.parseInt(request.getParameter("attachId"));
   		String complaintId = request.getParameter("complaintId");
   		Map<String, Object> fileMap = new HashMap<String, Object>();
   		fileMap.put("id", attachId);
   		AttachEntity toolFile = (AttachEntity) attachService.getQcAttach(fileMap);
   		toolFile.setDelFlag(0);
   		toolFile.setUpdateTime(new Date());
   		attachService.updateQcAttach(toolFile);
   		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
           request.setAttribute("attachList",attachList);
           request.setAttribute("complaintId",complaintId);
           request.setAttribute("type","act");
           return "qc_upload_list";
   	}
       
    /**
	 * 删除质量工具附件
	 * @author zhoupanpan 2012-05-11
	 * @version 1.0
	 * Copyright by Tuniu.com
	 */
	public String delToolFile(){
		int attachId = Integer.parseInt(request.getParameter("attachId"));
		String complaintId = request.getParameter("complaintId");
		Map<String, Object> fileMap = new HashMap<String, Object>();
		fileMap.put("id", attachId);
		AttachEntity toolFile = (AttachEntity) attachService.fetchOne(fileMap);
		toolFile.setDelFlag(0);
		toolFile.setUpdateTime(new Date());
		attachService.update(toolFile);
		List<AttachEntity> attachList =  attachService.getAttachList(Integer.parseInt(complaintId));
        request.setAttribute("attachList",attachList);
        request.setAttribute("complaintId",complaintId);
        request.setAttribute("type","act");
        return "upload_list";
	}
    
    /**
	 * 增加一条无订单投诉数据
	 * @throws Exception
	 */
	public String doAddNonOrderComplaint() throws Exception {
		entity.setOwnerId(user.getId());
		entity.setOwnerName(user.getRealName());
		entity.setOwnerPartment(departmentService.getDepartmentById(user.getDepId()).getDepName());
		entity.setState(1);// 投诉状态为投诉待指定
		entity.setDelFlag(0);
		entity.setComplaintNum(1);
		entity.setCustId(0);
		entity.setCustomer("-");
		entity.setCustomerLeader("-");
		entity.setServiceManager("-");
		entity.setAgencyTel("");
		entity.setProductLineId(0);
		entity.setSecondaryDepId(0);
		entity.setDestCategoryId(0);
		entity.setDestCategoryName("");
		if(isHotel==1){
		    entity.setDealDepart(Constans.HOTEL); //酒店的无订单投诉交给酒店组处理岗处理
		}else{
		    entity.setDealDepart(Constans.SPECIAL_BEFORE_TRAVEL); // 无订单投诉有出游前客服组处理
		}
		entity.setSpecial_event_flag(specialEventFlag);
		entity.setGuestName(entity.getContactPerson());
		entity.setReasonList(getComplaintReasons());
		entity.setCmpPerson(entity.getContactPerson());
		entity.setCmpPhone(entity.getContactPhone());
		service.insertNonOrdComp(entity);
		
		return "non_order_complaint_add";
	}
	
	public String getCompDetail() {
		String orderId = request.getParameter("orderId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		paramMap.put("complaintSolution", "1");
		paramMap.put("state", "1,2,3,4,5,6,7,10");
		List<ComplaintEntity> compDetailList = service.getComplaintList(paramMap);
		
		request.setAttribute("compDetailList", compDetailList);
		
		return "comp_detail";
	}
	

	
	
	public String toThirdList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> searchMap = new HashMap<String, String>();
		if (null != search) {
			searchMap = search.toMap();
		}
		paramMap.putAll(searchMap);
		paramMap.put("third", "third");
		ComplaintReleaserEntity releaser = releaserService.getByUserId(user.getId());
		if (null != releaser) {
			paramMap.put("compCitys", releaser.getCitys());
			if ("'Others'".equals(releaser.getCitys())) {
				paramMap.put("distCitys", releaserService.getDistinctCitys());
			}
		}
		super.execute(paramMap);
		
		return "complaint_third_list";
	}
	
	@SuppressWarnings("unchecked")
	public String toThirdDeal() {
		String complaintId = request.getParameter("complaintId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		List<FollowNoteThEntity> noteThs = (List<FollowNoteThEntity>) noteThService.fetchList(paramMap);
		request.setAttribute("noteThs", noteThs);
		return "complaint_third_deal";
	}
	
	@SuppressWarnings("unchecked")
	public String chgVisible() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("visibleFlag", request.getParameter("flag"));
		noteThService.update(paramMap);
		
		paramMap.clear();
		paramMap.put("complaintId", request.getParameter("complaintId"));
		paramMap.put("visibleFlag", 0);
		List<FollowNoteThEntity> noteList = (List<FollowNoteThEntity>) noteThService.fetchList(paramMap);
		if (null == noteList || noteList.size() == 0) {
			paramMap.clear();
			paramMap.put("thFinishFlag", 1);
			paramMap.put("id", request.getParameter("complaintId"));
			service.update(paramMap);
		}
		
		// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");// 设置输出为文字流
		response.setCharacterEncoding("UTF-8");// 设置字符集
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println("Success");
		} catch (IOException e) {
			return "error";
		}finally{
			out.flush();
			out.close();
		}
		return "complaint_third_deal";
	}
	
	public String updateContent() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("content", request.getParameter("content"));
		noteThService.update(paramMap);
		return "complaint_third_deal";
	}
	
	/**
	 * 第三方投诉处理过程查询---补单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addCompTh() {
		String result = "0";
		
		int complaintId = Integer.valueOf(request.getParameter("complaintId").trim());
		ComplaintEntity comp = (ComplaintEntity) service.get(complaintId);
		if (null == comp) {
			result = "投诉单号不正确！~";
		} else {
			if (StringUtil.isNotEmpty(comp.getCompCity())) {
				result = "投诉单信息在第三方处理列表中已存在！~";
			} else {
				/* 补投诉事宜 */
				List<Map<String, Object>> mapList = reasonService.getDistDesc(complaintId);
				for (Map<String, Object> map : mapList) {
					FollowNoteThEntity noteTh = new FollowNoteThEntity();
					noteTh.setComplaintId(complaintId);
					noteTh.setPersonId(user.getId());
					noteTh.setPersonName(user.getRealName());
					noteTh.setFlowName(Constans.SUBMIT_COMPLAINT_REASON);
					noteTh.setAddTime((Date) map.get("add_time"));
					noteTh.setContent((String) map.get("type_descript"));
					noteThService.insert(noteTh);
				}
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("id", complaintId);
				paramMap.put("compCity", request.getParameter("compCity"));
				paramMap.put("compTimeTh", (Date) mapList.get(0).get("add_time"));
				paramMap.put("thFinishFlag", 0);
				service.update(paramMap);
				
				/* 补跟进记录 */
				StringBuffer sb = new StringBuffer();
				sb.append("'").append(Constans.SET_FOLLOW_UP_RECORD).append("','").append(Constans.SUBMIT_SOLUTION).append("'");
				paramMap.clear();
				paramMap.put("complaintId", complaintId);
				paramMap.put("flowNames", sb.toString());
				List<ComplaintFollowNoteEntity> noteList = (List<ComplaintFollowNoteEntity>) followNoteService.fetchList(paramMap);
				for (ComplaintFollowNoteEntity note : noteList) {
					FollowNoteThEntity noteTh = new FollowNoteThEntity();
					noteTh.setComplaintId(complaintId);
					noteTh.setPersonId(note.getNotePeople());
					noteTh.setPersonName(note.getPeopleName());
					noteTh.setFlowName(note.getFlowName());
					noteTh.setAddTime(note.getAddTime());
					noteTh.setContent(note.getContent());
					noteThService.insert(noteTh);
				}
			}
		}
		
		com.tuniu.gt.toolkit.CommonUtil.writeResponse(result);
		
		return "complaint_third_list";
	}
	
	public String cancelCompTh() {
		int complaintId = Integer.valueOf(request.getParameter("complaintId"));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", complaintId);
		paramMap.put("compCity", "");
		paramMap.put("thFinishFlag", 0);
		service.update(paramMap);
		
		noteThService.deleteByCompId(complaintId);
		
		// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");// 设置输出为文字流
		response.setCharacterEncoding("UTF-8");// 设置字符集
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println("Success");
		} catch (IOException e) {
			return "error";
		}finally{
			out.flush();
			out.close();
		}
		return "complaint_third_list";
	}
	
	
	/**
	 * 流转至后处理组
	 * 
	 * @return
	 */
	public String goToAfterGroup() {
		String complaintId = request.getParameter("complaintId");
		ComplaintEntity entity = (ComplaintEntity) service.get(complaintId);

		entity.setDeal(0);
		entity.setDealName("");
		entity.setAssociater(0);
		entity.setAssociaterName("");
		entity.setState(1);
		entity.setTouringGroupType(2);//在后处理组中
		service.update(entity);
		
		addFollowNote(Integer.valueOf(complaintId), Constans.GOTO_AFTER_GROUP, Constans.GOTO_AFTER_GROUP);
		
		return this.toBill();
	}

	
	/**
	 * 流转资深坐席组
	 * 
	 * @return
	 */
	public String goToSeniorGroup() {
		String complaintId = request.getParameter("complaintId");
		ComplaintEntity entity = (ComplaintEntity) service.get(complaintId);

		entity.setDeal(0);
		entity.setDealName("");
		entity.setAssociater(0);
		entity.setAssociaterName("");
		entity.setState(1);
		entity.setTouringGroupType(3);//在资深坐席组中
		service.update(entity);
		
		addFollowNote(Integer.valueOf(complaintId),Constans.GOTO_SENIOR_GROUP, Constans.GOTO_SENIOR_GROUP);
		
		return this.toBill();
	}
	
	public String updateFollowUpRecord() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("note", request.getParameter("content"));
		followUpRecordService.update(paramMap);
		
		String complaintId = request.getParameter("complaintId");
		addFollowNote(Integer.valueOf(complaintId), Constans.UPDATE_FOLLOW_UP_RECORD, Constans.UPDATE_FOLLOW_UP_RECORD);
		
		return "bill";
	}
	
	/**
	 * 变更投诉单状态
	 */
	public String changeState() {
		String complaintId = request.getParameter("id");
		ComplaintEntity entity = (ComplaintEntity) service.get(complaintId);
		
		String flowName = "";
		int state = entity.getState();
		if (2 == state) { // 如果在处理中，则转至已待结
			entity.setState(3);
			flowName = Constans.GOTO_UPCOMING_FINISH;
		}
		
		if (3 == state || 4 == state) { // 如果在已待结，则返回处理中，并将对客方案由提交状态变为保存状态
			entity.setState(2);
			
			ComplaintSolutionEntity csEnt = solutionService.getComplaintSolutionBycompalintId(Integer.valueOf(complaintId));
			if (null != csEnt && csEnt.getSubmitFlag() == 1) {
				csEnt.setSubmitFlag(0);
				solutionService.update(csEnt);
			}
			flowName = Constans.GOTO_PROCESSING;
		}
		service.update(entity);
		
		addFollowNote(Integer.valueOf(complaintId),	flowName, flowName);
		
		return "bill";
	}
	
	/**
	 * 增加有效操作记录
	 * @param complaintId  投诉单id
	 * @param noteContents 操作内容
	 * @param flowName  操作事件
	 */
	private void addFollowNote(Integer complaintId, String noteContents, String flowName)
	{
		followNoteService.addFollowNote(complaintId, user.getId(), user.getRealName(), 
				noteContents, 0, flowName);
	}
	
	
	
	/**
	 * 新增供应商投诉页面
	 * @return
	 */
	public String supplierCommit() {
		
	    String id = request.getParameter("complaintId");
		Integer orderId = Integer.valueOf(request.getParameter("orderId"));
		Integer createType = Integer.valueOf(request.getParameter("createType"));
	    ComplaintEntity complaint = (ComplaintEntity) service.get(id);
	    List<SupportShareEntity> supplierList = getAgencyInfo(orderId);//从boss中取出采购单对应供应商信息,团队游不读取供应商信息
	    Map<String, Object> map =new HashMap<String, Object>();
	    map.put("complaintId", id);
	    map.put("flag", 1);
	    //-------测试--------
//	    SupportShareEntity aa = new SupportShareEntity();
//	    aa.setCode(1169);
//	    aa.setName("天天江南行");
//	    supplierList.add(aa);
	    //-----------
		request.setAttribute("supplierList",supplierList); 
		if(supplierList!=null &&supplierList.size()>0){
			
			request.setAttribute("supplierCount",supplierList.size());
		}else{
			
			request.setAttribute("supplierCount",0);
		}
	
	    request.setAttribute("complaint", complaint);
	    
		String html = "supplier_commit";
		return html;
	}


	/**
	 * 保存客服沟通记录
	 * @return
	 */
	public String  saveSupplierCommit() {
		
		HandlerResult result =new HandlerResult();
	    String userIp = getIpAddr(request);
		int userId =user.getId();
		String userName ="tuniu-"+user.getUserName();
	    String id = request.getParameter("complaintId");
	    String agencyName = request.getParameter("agencyName");
	    String complaintType = request.getParameter("complaintType");
	    String commitDetail = request.getParameter("commitDetail");
	    String agencyId = request.getParameter("agencyId");
	    String orderId = request.getParameter("orderId");
	    String route = request.getParameter("route");//线路名称
	    String routeId = request.getParameter("routeId");//线路ID
	    //根据供应商编码获取供应商ID
	    if("".equals(agencyId)){
	    	int agency_Id = tspService.checkAgencyInfo(agencyName, new Integer(id));
		    if(agency_Id<=0){
		    	result.setRetObj("error");
	  	    	result.setResMsg("供应商不存在");
	  	    	JSONObject json =JSONObject.fromObject(result);
			    writeResponse(json);
	  	    	return "supplier_commit";
		    }
		    agencyId = String.valueOf(agency_Id);
	    }
	    //将供应商放入缓存
	    if(null==MemcachesUtil.getObj(agencyId)||"".equals(MemcachesUtil.getObj(agencyId))){
	    	
	    	  MemcachesUtil.set(agencyId, agencyName);
	    }
	    //将用户username名放入缓存
	    if(null==MemcachesUtil.getObj(String.valueOf(userId))||"".equals(MemcachesUtil.getObj(String.valueOf(userId)))){
	    	
	    	  MemcachesUtil.set(String.valueOf(userId), user.getUserName());
	    }
	    
	    //供应商是否有权限
	    int nbFlag = service.getNbFlag(Integer.parseInt(agencyId), new Integer(id)); 
	    /*ComplaintRestClient.getNbFlag(Integer.parseInt(agencyId), isDistributeFlag);*/
	    if(nbFlag!=1){
	  	    	result.setRetObj("error");
	  	    	result.setResMsg("供应商没有权限");
	  	    	JSONObject json =JSONObject.fromObject(result);
			    writeResponse(json);
	  	    	return "supplier_commit";
	  	  }
	    //获取roomId
	     Map<String, Object> param =new HashMap<String, Object>();
	     param.put("username", userName); 
	     param.put("salerId", userId); 
	     param.put("agencyId", agencyId);
	     param.put("complaintID", id);
	     param.put("orderID", orderId);
	     param.put("productID", routeId);
	     param.put("productName", route);
	     param.put("ticketType", complaintType);
	     Map<String,Object> resultMap = queryRoomId(param);
	     String roomId =  resultMap.get("roomId")==null?"": resultMap.get("roomId").toString();
	     String msg =  resultMap.get("msg")==null?"": resultMap.get("msg").toString();
	     if("".equals(roomId.trim())){
	    	 
	    	 result.setRetObj("error");
	  	     result.setResMsg(msg);
	  	     JSONObject json =JSONObject.fromObject(result);
			 writeResponse(json);
	  	     return "supplier_commit";
	    	 
	     }
	    
		    Map<String, Object> map =new HashMap<String, Object>();
		    map.put("userId", userId);//
		    map.put("userName", userName);
		    map.put("complaintId", id);//
		    map.put("typeNum", complaintType);//
		    map.put("commitDetail", commitDetail);
		    map.put("orderId", orderId);
		    map.put("agencyId", agencyId);
		    map.put("agencyName",agencyName);
		    map.put("flag", 1);//tuniu端标识
		    map.put("roomId", roomId);//房间号
		    map.put("userIp", userIp);
		    if(Constans.COMMIT_CONSULT.equals(complaintType)){   //1 咨询 2 投诉	
		    	
		    	 map.put("typeName", Constans.COMMIT_CONSULT_NAME);
		    	 
		    }else{
		    	
		    	map.put("typeName", Constans.COMMIT_COMPLAINT_NAME);
		    }
		    map.put("statusNum", Constans.COMMIT_ING);
		    map.put("statusName", Constans.COMMIT_ING_NAME);//处理中
		    
		    String flag= agencyCommitService.saveCommitDetail(map);
		    param.put("userType",1);
		    param.put("roomID", roomId );
		    param.put("contentType", 0);
		    param.put("username", userName);
		    Map<String,Object> contentMap =new HashMap<String, Object>();
		    contentMap.put("text",commitDetail  );
		    param.put("content", contentMap);
		    String requestStr ="";
		    if("0".equals(flag)){
		    	 requestStr =JSONObject.fromObject(param).toString();
		    	 int re_flag = ComplaintRestClient.sendAddChatToNB(requestStr);//发送追加沟通信息到NB
				    if(re_flag==0){
				    	
				    	 result.setRetObj("success");
				    	 
				    }else{
				    	
				    	 result.setRetObj("error");
				    	 result.setResMsg("发送数据失败");
				    }
		    	
		    }else{
		    	
		    	result.setRetObj("error");
		    	result.setResMsg("保存失败");
		    }
			JSONObject json =JSONObject.fromObject(result);
		    writeResponse(json);
		    return "supplier_commit";
	}
	
	
	/**
	 * 初始化投诉单会话列表
	 * @param id
	 * @return
	 * @throws TRestException 
	 */
	public  List<AgencyToChatEntity> queryChatDetail(String id,String userName) {
		
		List<AgencyToChatEntity> listAgency =new ArrayList<AgencyToChatEntity>();
		try {
			
			Map<String , Object> map =new HashMap<String, Object>();
			map.put("username","tuniu-"+userName);
			map.put("complaintID", id);
			map.put("pageindex", 1);
			map.put("pagelimit", 500);
			String requestStr= JSONObject.fromObject(map).toString();
			HttpClientHelper client = new HttpClientHelper();
			String execute =  client.executeGet(nbComplaintURL, requestStr);
			if(!"".equals(execute)){
				
				JSONObject jsb =JSONObject.fromObject(execute);
				
				if ((Boolean) jsb.get("success")) {
					
					listAgency = analyJson(jsb);
				}
			}
			return listAgency;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return listAgency;
			
		}
		
	}
	
	/**
	 * 打开某个会话窗口
	 * @return
	 */
	public String commitList() {
		
		    String roomId = request.getParameter("roomId");
		    String complaintId = request.getParameter("complaintId");
		    String state = request.getParameter("state");
		    ComplaintEntity complaint = (ComplaintEntity) service.get(complaintId);
		    String userId = String.valueOf(user.getId());
		    String deal = String.valueOf(complaint.getDeal());
		    if(!userId.equals(deal)){
		    	
		    	state = "1";
		    }
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("complaintId", complaintId);
			map.put("roomId", roomId);
			map.put("flag", 1);
			//取最新一条途牛回复
			AgencyToChatEntity chat =  agencyCommitService.queryMaxChat(map);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("roomID",roomId);
			data.put("pageindex","1");
			data.put("pagelimit","500");
		
			List<AgencyToChatEntity> listAgency =new ArrayList<AgencyToChatEntity>();
			//调用接口
			listAgency = queryRoomDetail(data);
			
			request.setAttribute("listAgency", listAgency);
			request.setAttribute("roomId", roomId);
			request.setAttribute("state", state);
			request.setAttribute("chat", chat);
			request.setAttribute("complaintId",complaintId);
			return "commit_list";
	}
	
	
	
	/**
	 * 追加沟通页面
	 * @return
	 */
	public String addCommit() {
		
		
	    String complaintId = request.getParameter("complaintId");
	    String roomId = request.getParameter("roomId");
	    request.setAttribute("complaintId", complaintId);
	    request.setAttribute("roomId", roomId);
		String html = "add_supplier_commit";
		return html;
	}

	
	
    
    /**
	 	追加沟通上传图片
     * @throws FileUploadException 
     * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
    public String  commit_upload() throws FileUploadException, UnsupportedEncodingException {
    	String userIp = getIpAddr(request);
        String complaintId = request.getParameter("complaintId");
        String roomId = request.getParameter("roomId");
        Map<String, Object> map =new HashMap<String, Object>();
	    map.put("complaintId", complaintId);
		map.put("roomId", roomId);
	    map.put("flag", 1);
		//取最新一条途牛回复
		AgencyToChatEntity chat =  agencyCommitService.queryMaxChat(map);
		//工作交接之后是否允许新客服进入房间
		if(user.getId()!=Integer.parseInt(chat.getDeal())){
			
			Map<String, Object> addMap =new HashMap<String, Object>();
			addMap.put("roomID", roomId);
			addMap.put("username", "tuniu-"+user.getUserName());
			String responseStr =JSONObject.fromObject(addMap).toString();
			int returnFlag = ComplaintRestClient.checkAddRoomDeal(responseStr);
			if(returnFlag==0){ //0是没有权限 1是有权限
				
				request.setAttribute("upflag", 2);
	        	return "add_supplier_commit";
			}
		}
		
        Map<String, Object> fileMap = new HashMap<String, Object>();
        Map<String, Object> returnMap =new HashMap<String, Object>();
        if(toolFileFileName!=null){
        	
        	String fileName = toolFileFileName.get(0);
        	String type=fileName.substring(fileName.lastIndexOf("."));
			String typeName =type.substring(1,type.length());//去除后缀的"."
			if(!"JPEG".equals(typeName.toUpperCase())&&!"BMP".equals(typeName.toUpperCase())&&!"PNG".equals(typeName.toUpperCase())&&!"GIF".equals(typeName.toUpperCase())&&!"JPG".equals(typeName.toUpperCase())){
				
				request.setAttribute("upflag", 1);
	        	return "add_supplier_commit";
				
			}
         	fileMap.put("pic", toolFile.get(0));
         	fileMap.put("fileName", fileName);
        	fileMap.put("typeName", typeName);
            returnMap = ComplaintRestClient.sendUploadNB(fileMap);//发送追加沟通信息到NB
        }else{
        	
        	request.setAttribute("upflag", 1);
        	return "add_supplier_commit";
        }
        if("".equals(returnMap.get("largeUrl"))){
        	
        	request.setAttribute("upflag", 1);
        	return "add_supplier_commit";
        }
	  
		if(Constans.COMMIT_FINISH.equals(chat.getStatusNum())){//如果最新一条为已完成，则追加记录状态为处理中  不然则取最新一条记录的状态
			
			map.put("statusName", Constans.COMMIT_ING_NAME);
			map.put("statusNum", Constans.COMMIT_ING);
			
		}else{
			
			map.put("statusName", chat.getStatusName());
			map.put("statusNum", chat.getStatusNum());
			
		}
	    map.put("userId", user.getId());
	    map.put("userName","tuniu-"+ user.getUserName());
		map.put("agencyId", chat.getAgencyId());
		map.put("agencyName", chat.getAgencyName());
		map.put("orderId", chat.getOrderId());
		map.put("typeName", chat.getTypeName());
		map.put("typeNum", chat.getTypeNum());
		map.put("userIp", userIp);
		map.put("commitDetail",returnMap.get("largeUrl"));
	    String flag= agencyCommitService.addCommit(map);//插入客服发送信息
	    
	    String requestStr="";
	    Map<String,Object> param =new HashMap<String, Object>();
	    param.put("content", returnMap);
	    param.put("contentType", 1);
	    param.put("userType", 1);
	    param.put("roomID", roomId);
	    param.put("complaintID", complaintId );
	    param.put("ticketType", chat.getTypeNum());
	    param.put("username", "tuniu-"+ user.getUserName());
	    if("0".equals(flag)){
	    	
	        requestStr =JSONObject.fromObject(param).toString();
		    int re_flag = ComplaintRestClient.sendAddChatToNB(requestStr);//发送追加沟通信息到NB
		    if(re_flag==0){
		    	
		   	   if(null==MemcachesUtil.getObj(String.valueOf(user.getId()))||"".equals(MemcachesUtil.getObj(String.valueOf(user.getId())))){
		   	    	
		 	    	  MemcachesUtil.set(String.valueOf(user.getId()), user.getUserName());
		 	    }
		    	 request.setAttribute("upflag", 0);
		    	 
		    }else{
		    	
		    	 request.setAttribute("upflag", 1);
		    }
				  
		  }else{
				  
			  request.setAttribute("upflag", 1);
	    }
    	return "add_supplier_commit";
    }
    
    
	/**
	 * 追加沟通
	 * @return
	 */
	public String  addSupplierCommit()  {
		
		HandlerResult result =new HandlerResult();
		String userIp = getIpAddr(request);
		int userId =user.getId();
	    String id = request.getParameter("complaintId");
	    String roomId = request.getParameter("roomId");
	    String commitDetail = request.getParameter("commitDetail");
	    Map<String, Object> map =new HashMap<String, Object>();
	    map.put("userId", userId);
	    map.put("userName","tuniu-"+ user.getUserName());
	    map.put("commitDetail", commitDetail);
	    map.put("complaintId", id);
	    map.put("userIp", userIp);
	    map.put("flag", 1);
		map.put("roomId", roomId);
		//取最新一条途牛回复
		AgencyToChatEntity chat =  agencyCommitService.queryMaxChat(map);
		//工作交接之后是否允许新客服进入房间
		if(user.getId()!=Integer.parseInt(chat.getDeal())){
			
			Map<String, Object> addMap =new HashMap<String, Object>();
			addMap.put("roomID", roomId);
			addMap.put("username", "tuniu-"+user.getUserName());
			String responseStr =JSONObject.fromObject(addMap).toString();
			int returnFlag = ComplaintRestClient.checkAddRoomDeal(responseStr);
			if(returnFlag== 0){ //0是没有权限 1是有权限
				
				 result.setRetObj("error");
		    	 result.setResMsg("没有权限进入房间");
		    	 JSONObject json =JSONObject.fromObject(result);
		 	     writeResponse(json);
		 	     return "add_supplier_commit";
			}
		}
		if(Constans.COMMIT_FINISH.equals(chat.getStatusNum())){//如果最新一条为已完成，则追加记录状态为处理中  不然则取最新一条记录的状态
			
			map.put("statusName", Constans.COMMIT_ING_NAME);
			map.put("statusNum", Constans.COMMIT_ING);
			
		}else{
			
			map.put("statusName", chat.getStatusName());
			map.put("statusNum", chat.getStatusNum());
			
		}
		map.put("agencyId", chat.getAgencyId());
		map.put("agencyName", chat.getAgencyName());
		map.put("orderId", chat.getOrderId());
		map.put("typeName", chat.getTypeName());
		map.put("typeNum", chat.getTypeNum());
	
	    String flag= agencyCommitService.addCommit(map);//保存到本地
	    
	    String  requestStr="";
	    Map<String, Object> param =new HashMap<String, Object>();
	    param.put("userType", 1);
	    param.put("contentType", 0);
	    param.put("roomID", roomId );
	    param.put("complaintID", id );
	    param.put("ticketType", chat.getTypeNum());
	    param.put("username", "tuniu-"+user.getUserName());
	    Map<String,Object> contentMap =new HashMap<String, Object>();
	    contentMap.put("text", commitDetail);
	    param.put("content", contentMap);
	    
	    if("0".equals(flag)){

	    	    requestStr =JSONObject.fromObject(param).toString();
			    int re_flag = ComplaintRestClient.sendAddChatToNB(requestStr);//发送追加沟通信息到NB
			    if(re_flag==0){
			    	
			    	 if(null==MemcachesUtil.getObj(String.valueOf(userId))||"".equals(MemcachesUtil.getObj(String.valueOf(userId)))){
			 	    	
				    	  MemcachesUtil.set(String.valueOf(userId), user.getUserName());
				    }
				    
			    	 result.setRetObj("success");
			    	 
			    }else{
			    	
			    	 result.setRetObj("error");
			    	 result.setResMsg("发送数据失败");
			    }
	    }else{
	    	
	    	result.setRetObj("error");
	    	result.setResMsg("保存失败");
	    }
		JSONObject json =JSONObject.fromObject(result);
	    writeResponse(json);
		return  "add_supplier_commit";
	}
	
	
	
	/**
	 * 修改沟通类型界面
	 * @return
	 */
	public String updateCommitType() {
		
	    String complaintId = request.getParameter("complaintId");
	    String roomId = request.getParameter("roomId");
	    Map<String, Object> map =new HashMap<String, Object>();
	    map.put("complaintId", complaintId);
	    map.put("roomId", roomId);
	    map.put("flag", 1);
	    //取最新一条途牛回复
		AgencyToChatEntity chat =  agencyCommitService.queryMaxChat(map);
		request.setAttribute("chat", chat);
		return "update_commit";
	}

	
	/**
	 * 修改沟通类型
	 * @return
	 */
	public String  updateCommit() {
		
		Map<String, Object> param =new HashMap<String, Object>();
		HandlerResult result =new HandlerResult();
		String userIp = getIpAddr(request);
		int userId =user.getId();
	    String id = request.getParameter("complaintId");
	    String roomId = request.getParameter("roomId");
	    String complaintType = request.getParameter("complaintType");
	    Map<String, Object> map =new HashMap<String, Object>();
	    map.put("userId", userId);
	    map.put("userName", "tuniu-"+user.getUserName());
	    map.put("complaintId", id);
	    map.put("userIp", userIp);
	    map.put("flag", 1);
	    map.put("roomId", roomId);
		//取最新一条途牛回复
		AgencyToChatEntity chat =  agencyCommitService.queryMaxChat(map);
		map.put("agencyId", chat.getAgencyId());
		map.put("agencyName", chat.getAgencyName());
		map.put("orderId", chat.getOrderId());
		map.put("statusName", chat.getStatusName());
		map.put("statusNum", chat.getStatusNum());
		if(complaintType.equals(Constans.COMMIT_COMPLAINT)){
			
			map.put("typeName", Constans.COMMIT_COMPLAINT_NAME);
			map.put("typeNum",Constans.COMMIT_COMPLAINT);
		    param.put("ticketType", Constans.COMMIT_COMPLAINT);
			map.put("commitDetail", "修改沟通类型："+ Constans.COMMIT_CONSULT_NAME+"-->"+Constans.COMMIT_COMPLAINT_NAME);
		}else{
			
			map.put("typeName", Constans.COMMIT_CONSULT_NAME);
			map.put("typeNum",Constans.COMMIT_CONSULT);
			param.put("ticketType", Constans.COMMIT_CONSULT);
			map.put("commitDetail", "修改沟通类型："+ Constans.COMMIT_COMPLAINT_NAME+"-->"+Constans.COMMIT_CONSULT_NAME);
		}
		
	    String flag = agencyCommitService.addCommit(map);
	 
	    param.put("roomID", roomId);
	    String requestStr="";
	    if("0".equals(flag)){
	        requestStr =JSONObject.fromObject(param).toString();
		    int re_flag = ComplaintRestClient.sendUpdateNB(requestStr);//修改沟通类型接口
		    if(re_flag==0){
		    	
		    	 result.setRetObj("success");
		    	 
		    }else{
		    	
		    	 result.setRetObj("error");
		    	 result.setResMsg("发送数据失败");
		    }
	    }else{
	    	
	    	result.setRetObj("error");
	    }
		JSONObject json =JSONObject.fromObject(result);
	    writeResponse(json);
		return "update_commit";
	}
	
	/**
	 * 转为已完成
	 * @return
	 */
	public String turnFinish(){
		
		HandlerResult result =new HandlerResult();
		String userIp = getIpAddr(request);
		int userId =user.getId();
	    String id = request.getParameter("complaintId");
	    String roomId = request.getParameter("roomId");
	    Map<String, Object> map =new HashMap<String, Object>();
	    map.put("userId", userId);
	    map.put("roomId", roomId);
	    map.put("userName", "tuniu-"+user.getUserName());
	    map.put("complaintId", id);
	    map.put("userIp", userIp);
	    map.put("flag", 1);
		//取最新一条途牛回复
		AgencyToChatEntity chat =  agencyCommitService.queryMaxChat(map);
		map.put("agencyId", chat.getAgencyId());
		map.put("agencyName", chat.getAgencyName());
		map.put("orderId", chat.getOrderId());
		map.put("typeName", chat.getTypeName());
		map.put("typeNum", chat.getTypeNum());
		//状态改为已完成
		map.put("statusName", Constans.COMMIT_FINISH_NAME);
		map.put("statusNum", Constans.COMMIT_FINISH);
		map.put("commitDetail", "转为"+Constans.COMMIT_FINISH_NAME);
	    String flag = agencyCommitService.addCommit(map);
	    Map<String, Object> param =new HashMap<String, Object>();
	    param.put("status", Constans.COMMIT_FINISH);
	    param.put("roomID", roomId);
	    String requestStr="";
	    if("0".equals(flag)){
	    	
	    	    requestStr =JSONObject.fromObject(param).toString();
			    int re_flag = ComplaintRestClient.sendUpdateNB(requestStr);//转为已完成
			    if(re_flag==0){
			    	
			    	 result.setRetObj("success");
			    	 
			    }else{
			    	
			    	 result.setRetObj("error");
			    	 result.setResMsg("发送数据失败");
			    }
	    }else{
	    	
	    	result.setRetObj("error");
	    }
		JSONObject json =JSONObject.fromObject(result);
	    writeResponse(json);
		return "bill";
		
		
	}
	
	/**
	 * 获取某个房间详细聊天记录
	 * @param map
	 * @return
	 */
	public List<AgencyToChatEntity> queryRoomDetail(Map<String, Object> map){
		
		List<AgencyToChatEntity> listAgency =new ArrayList<AgencyToChatEntity>();
		try {
			
				String jsonStr = JSONObject.fromObject(map).toString();
				HttpClientHelper client = new HttpClientHelper();
				String execute =  client.executeGet(nbHistoryURL, jsonStr);
			    JSONObject jsb =JSONObject.fromObject(execute);
				if ((Boolean) jsb.get("success")) {
					
					listAgency = analyticJson(jsb);
					  
				}
				return listAgency;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return listAgency;
			
		}
		
	}
	
	//某个会话列表解析
	private static List<AgencyToChatEntity> analyticJson(JSONObject json) {
		List<AgencyToChatEntity> listAgency =new ArrayList<AgencyToChatEntity>();
		try{
			JSONObject ja = json.getJSONObject("data");
			JSONArray temp = ja.getJSONArray("list");
			for (int i = 0; i < temp.size(); i++) {
				AgencyToChatEntity at = new AgencyToChatEntity();
				if(temp.getJSONObject(i).getInt("contentType")==0){//0是文字
					if(temp.getJSONObject(i).getJSONObject("content").containsKey("text")){
						
						at.setDescript(temp.getJSONObject(i).getJSONObject("content").getString("text"));
					}
					
				}else{
					
					if(temp.getJSONObject(i).getJSONObject("content").containsKey("largeUrl")){
						
						at.setDescript(temp.getJSONObject(i).getJSONObject("content").getString("largeUrl"));
						
					}
					
					if(temp.getJSONObject(i).getJSONObject("content").containsKey("imageName")){
						
						at.setPictName(temp.getJSONObject(i).getJSONObject("content").getString("imageName"));
					}else{
						
						at.setPictName("未知名称");
					}
				
				}
				
				if(temp.getJSONObject(i).getInt("userType")==0){//0是供应商 1是客服
					
					
					at.setDealName(MemcachesUtil.get(temp.getJSONObject(i).getString("agencyId"))==null?"":MemcachesUtil.get(temp.getJSONObject(i).getString("agencyId")));
					
				}else{
					
					at.setDealName(MemcachesUtil.get(temp.getJSONObject(i).getString("salerId"))==null?"":MemcachesUtil.get(temp.getJSONObject(i).getString("salerId")));
					
				}
				
				at.setFlag( temp.getJSONObject(i).getString("userType"));
				at.setContentType( temp.getJSONObject(i).getInt("contentType"));
				at.setCommitTime( temp.getJSONObject(i).getString("creationDate"));
				at.setRoomId( temp.getJSONObject(i).getString("roomID"));
				listAgency.add(at);
			}
			
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}
		return listAgency;
	}
	
	
   //投诉单对应房间列表解析
	private static List<AgencyToChatEntity> analyJson(JSONObject json) {
		List<AgencyToChatEntity> listAgency =new ArrayList<AgencyToChatEntity>();
		try{
			JSONObject ja = json.getJSONObject("data");
			JSONArray temp = ja.getJSONArray("list");
			for(int i=0;i<temp.size();i++){
				AgencyToChatEntity at = new AgencyToChatEntity();
				if(Constans.COMMIT_CONSULT.equals(temp.getJSONObject(i).getString("ticketType"))){//1沟通 2投诉
					
					at.setTypeName(Constans.COMMIT_CONSULT_NAME);
				}else{
					
					at.setTypeName(Constans.COMMIT_COMPLAINT_NAME);
					
				}
				if(Constans.COMMIT_NO_BEGIN.equals(temp.getJSONObject(i).getString("status"))){//未发起
					
					at.setStatusName(Constans.COMMIT_NO_BEGIN_NAME);
				}
				if (Constans.COMMIT_NO_ANSWER.equals(temp.getJSONObject(i).getString("status"))){//未回复
					
					at.setStatusName(Constans.COMMIT_NO_ANSWER_NAME);
					
				}
				if(Constans.COMMIT_ING.equals(temp.getJSONObject(i).getString("status"))){//沟通中
					
					at.setStatusName(Constans.COMMIT_ING_NAME);
				}
				if (Constans.COMMIT_FINISH.equals(temp.getJSONObject(i).getString("status"))){//已完成
					
					at.setStatusName(Constans.COMMIT_FINISH_NAME);
					
				}
				at.setRoomId(temp.getJSONObject(i).getString("roomID"));
				if(temp.getJSONObject(i).getInt("msgContentType")==0){
					
					if(temp.getJSONObject(i).getJSONObject("msgContent").containsKey("text"))
					{
					   at.setDescript(temp.getJSONObject(i).getJSONObject("msgContent").getString("text"));
					}
				}else{
					
					if(temp.getJSONObject(i).getJSONObject("msgContent").containsKey("largeUrl")){
						
						at.setDescript(temp.getJSONObject(i).getJSONObject("msgContent").getString("largeUrl"));
						
					}
					
					if(temp.getJSONObject(i).getJSONObject("msgContent").containsKey("imageName")){
						
						at.setPictName(temp.getJSONObject(i).getJSONObject("msgContent").getString("imageName"));
						
					}else{
						
						at.setPictName("未知名称");
					}
				}
				
				at.setDealName(temp.getJSONObject(i).getString("msgSender"));
				at.setFlag(temp.getJSONObject(i).getString("msgSenderType"));
				at.setContentType(temp.getJSONObject(i).getInt("msgContentType"));
				at.setCommitTime(temp.getJSONObject(i).getString("msgSendTime"));
				at.setComplaintId(temp.getJSONObject(i).getString("complaintID"));
				at.setAgencyName(MemcachesUtil.get(temp.getJSONObject(i).getString("agencyID"))==null?"":MemcachesUtil.get(temp.getJSONObject(i).getString("agencyID")));
				listAgency.add(at);
			}
			
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}
		return listAgency;
	}
	
	
	
	
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request){
		
		
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
		
	}
	
	private List<SupportShareEntity> getAgencyInfo(int orderId) {
        OrderEntity orderEntity = service.queryNewOrderInfo(orderId+"");
        List<AgencyDto>  agencyList = orderEntity.getAgencyList();
        List<SupportShareEntity> sEList = new ArrayList<SupportShareEntity>();
        if(CollectionUtil.isNotEmpty(agencyList)){
            for(AgencyDto agencyDto : agencyList) {
                SupportShareEntity supportShareEntity = new SupportShareEntity();
                supportShareEntity.setCode(agencyDto.getAgencyId());
                supportShareEntity.setName(agencyDto.getAgencyName());
                sEList.add(supportShareEntity);
            }
        }
        
        return sEList;
    }
	
	public  void writeResponse(Object data) {
		HttpServletResponse response = ServletActionContext.getResponse(); // 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
		response.setContentType("text/plain"); // 设置输出为文字流
		response.setCharacterEncoding("UTF-8"); // 设置字符集
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(data);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			out.flush();
			out.close();
		}
	}

	
	//获取房间号
	public Map<String, Object> queryRoomId(Map<String, Object> map) {
			
			String responseStr =JSONObject.fromObject(map).toString();
			Map<String, Object> returnMap = ComplaintRestClient.queryRoomId(responseStr);
			return returnMap;
		
	}
	
	/**
	 * 通过userId 获取投诉单所有沟通状态
	 * @param userId
	 * @return
	 */
	public List<AgencyToChatEntity> queryCommitType(Map<String, Object> map){
		
		String responseStr= JSONObject.fromObject(map).toString();
		return ComplaintRestClient.queryCommitType(responseStr);
		
	}
	
	
	/**
	 * 打开订单页面
	 * @return
	 */
	public String showOrder(){
		
		HandlerResult result =HandlerResult.newInstance();
		
		try {
			Integer orderId = Integer.parseInt(request.getParameter("orderId"));
			if(Integer.valueOf(orderId)>400000000){//BOSS3订单
				
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("orderId", orderId);
				String order = JSONObject.fromObject(map).toString();
				String orderBase = Base64.encodeBase64URLSafeString(order.getBytes("utf8"));
				String orderUrl = bossBillURL+"?"+orderBase;
				result.setRetCode("0");
				result.setRetObj(orderUrl);
				
			}else{
				
				String orderUrl = otherBillURL+"&order_id="+orderId;
				result.setRetCode("0");
				result.setRetObj(orderUrl);
			}
		
		} catch (UnsupportedEncodingException e) {
			
			result.setRetCode("1");
			result.setResMsg("获取路径失败!");
			logger.error(e.getMessage(), e);
		}
		
		JSONObject json =JSONObject.fromObject(result);
	    writeResponse(json);
		return  "list";
	}
	
	public String queryCallRecords() {
		String cmpPhone = request.getParameter("cmpPhone");
		
		List<AgentStatusLog> callRecordList = new ArrayList<AgentStatusLog>();
		if(!StringUtils.isEmpty(cmpPhone)) {
		    List<String> telS = new ArrayList<String>();
		    telS.add(cmpPhone);
			
			Map<String,String> extensionDealPersonMap=followUpRecordService.getDealPersonExtension(id);
			List<String> salerPhones = new ArrayList<String>();
			for (String extension : extensionDealPersonMap.keySet()) {
				salerPhones.add(extension);
			}
			
			Map<String,Object> minMaxDealTimeMap = followUpRecordService.getMinAndMaxDealTime(id);
			Date startTime = (Date) minMaxDealTimeMap.get("minAddTime");
			Date endTime = (Date) minMaxDealTimeMap.get("maxAddTime");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startTime", DateUtil.formatDateTime(startTime));
			paramMap.put("endTime", DateUtil.formatDateTime(endTime));
			paramMap.put("salerPhones", salerPhones);
			paramMap.put("telS", telS);
			callRecordList= ComplaintRestClient.queryComplaintCallRecords(paramMap);
			for (AgentStatusLog agentStatusLog : callRecordList) {
			    if(extensionDealPersonMap.keySet().contains(agentStatusLog.getCallNumber())){
			        agentStatusLog.setCustomerName(extensionDealPersonMap.get(agentStatusLog.getCallNumber()));
			    }else if(extensionDealPersonMap.keySet().contains(agentStatusLog.getCalledNumber())){
			        agentStatusLog.setCustomerName(extensionDealPersonMap.get(agentStatusLog.getCalledNumber()));
			    }
			}
			
		}
		request.setAttribute("callRecordList", callRecordList);
		
		return "call_record";
	};

	/**
	 * 新增质检点页面
	 * @return
	 */
	public String qcPoint() {
		
		Integer userId = user.getId();
	    String id = request.getParameter("complaintId");
	    Map<String, Object> map =new HashMap<String, Object>();
	    map.put("complaintId", id);
	    QcPointEntity qcPoint  = new QcPointEntity();
	    qcPoint = qcPointService.getQcPoint(map);
	    if(null != qcPoint){
	    	
	    	UserEntity reportUser = ComplaintRestClient.getReporter(qcPoint.getUpdatePerson());
	  	    if(null==reportUser){
	  	    	
	  	    	  request.setAttribute("reportUserId", 0);
	  	    }else{
	  	    	
	  	    	 request.setAttribute("reportUserId", reportUser.getId());
	  	    }
	    	
	    }
	  
	    request.setAttribute("qcPoint", qcPoint);
	    request.setAttribute("complaintId", id);
	    request.setAttribute("userId", userId);
	    return  "qc_point";
	}
	
	
	/**
	 * 保存质检点信息
	 * @return
	 */
	public  String saveQcPoint(){
		
		HandlerResult result =new HandlerResult();
		Integer userId = user.getId();
		String userName = user.getRealName();
	    String complaintId = request.getParameter("complaintId");//投诉单号
	    String qcPoint = request.getParameter("qcPoint");//质检点
	    String evidence = request.getParameter("evidence");//证据
	   
	    QcPointEntity  qcEntity = new QcPointEntity();
	    qcEntity.setAddPersonId(userId);
	    qcEntity.setAddPerson(userName);
	    qcEntity.setUpdatePersonId(userId);
	    qcEntity.setUpdatePerson(userName);
	    qcEntity.setComplaintId(Integer.parseInt(complaintId));
	    qcEntity.setQcPoint(qcPoint);
	    qcEntity.setEvidence(evidence);
	    
	    int flag = qcPointService.saveQcPoint(qcEntity);
		  
	    if( flag == 0){
			  
	    	 Map<String, Object> paramMap = new HashMap<String, Object>();
	   		 paramMap.put("complaintId", complaintId);
	         List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
	    	 for(AttachEntity entity :attachList){
	    		 
	    		 entity.setAddTime(null);
	    		 entity.setUpdateTime(null);
	    	 }
			 Map<String, Object> map =new HashMap<String, Object>();
			 map.put("updatePersonId", qcEntity.getUpdatePersonId());
			 map.put("updatePerson", qcEntity.getUpdatePerson());
			 map.put("complaintId", qcEntity.getComplaintId());
			 map.put("qcPoint", qcEntity.getQcPoint());
			 map.put("evidence", qcEntity.getEvidence());
			 map.put("attachList", attachList);
			 map.put("systemProblemFlag", Integer.parseInt(request.getParameter("systemProblemFlag")) == 1 ? true : false );
			 
			 /*获取质检单数据 */
			 Map<String, Object> paraMap =new HashMap<String, Object>(); 
			 paraMap.put("id", complaintId);
			 ComplaintEntity ce = (ComplaintEntity) service.get(paraMap);
			 
			 ComplaintVo cmp = service.getCmpBillInfo(Integer.parseInt(complaintId));
			 QcVo qcVo = new QcVo();
			 qcVo.setPrdId(ce.getRouteId());
			 if(null != ce.getStartTime()){
				 
				 qcVo.setGroupDate(ce.getStartTime().getTime());
				 
			 }
			 qcVo.setOrdId(ce.getOrderId());
			 qcVo.setCmpId(ce.getId());
			 qcVo.setBuildDate(DateUtil.formatDateTime(ce.getBuildDate()));
			 qcVo.setQcAffairDesc(getQcAffairDesc(cmp.getReasonList()));
			 map.put("qcVo", qcVo);
		     String	 requestStr =JSONObject.fromObject(map).toString();
		     
		     int re_flag = ComplaintRestClient.sendQcPointToQMS(requestStr);//发送质检点到QMS
		     
		     if(re_flag==0){
				 result.setRetObj("success");
			 }else{
				qcPointService.deleteQcPoint(qcEntity);
			    result.setRetObj("error");
			    result.setResMsg("发送数据失败");
			 }
		    	
	    }else{
	    	
	    	result.setRetObj("error");
	    	result.setResMsg("保存失败");
	    }
	    
		JSONObject json =JSONObject.fromObject(result);
	    writeResponse(json);
	    return "qc_point";
		
	}
	/**
	 *修改质检点信息
	 * @return
	 */
	public  String updateQcPoint(){
		
		HandlerResult result =new HandlerResult();
		Integer userId = user.getId();
		String userName = user.getRealName();
	    String complaintId = request.getParameter("complaintId");//投诉单号
	    String id = request.getParameter("qcPointId");//质检点单号
	    String qcPoint = request.getParameter("qcPoint");//质检点
	    String evidence = request.getParameter("evidence");//证据
	   
	    QcPointEntity  qcEntity = new QcPointEntity();
	    qcEntity.setUpdatePersonId(userId);
	    qcEntity.setUpdatePerson(userName);
	    qcEntity.setId(Integer.parseInt(id));
	    qcEntity.setQcPoint(qcPoint);
	    qcEntity.setEvidence(evidence);
	    qcEntity.setComplaintId(Integer.parseInt(complaintId));
	    int flag = qcPointService.updateQcPoint(qcEntity);
		  
	    if( flag == 0){
			  
	    	 Map<String, Object> paramMap = new HashMap<String, Object>();
	   		 paramMap.put("complaintId", complaintId);
	         List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
	    	 for(AttachEntity entity :attachList){
	    		 
	    		 entity.setAddTime(null);
	    		 entity.setUpdateTime(null);
	    	 }
			 Map<String, Object> map =new HashMap<String, Object>();
			 map.put("updatePersonId", qcEntity.getUpdatePersonId());
			 map.put("updatePerson", qcEntity.getUpdatePerson());
			 map.put("complaintId", qcEntity.getComplaintId());
			 map.put("qcPoint", qcEntity.getQcPoint());
			 map.put("evidence", qcEntity.getEvidence());
			 map.put("attachList", attachList);
		
		     String	 requestStr =JSONObject.fromObject(map).toString();
		     int re_flag = ComplaintRestClient.sendQcPointToQMS(requestStr);//发送质检点到QMS
		     if(re_flag==0){
				    	
				    result.setRetObj("success");
				    	 
				 }else{
				    	
				    result.setRetObj("error");
				    result.setResMsg("发送数据失败");
				  }
		    	
		    }else{
		    	
		    	result.setRetObj("error");
		    	result.setResMsg("保存失败");
		    }
	    
			JSONObject json =JSONObject.fromObject(result);
		    writeResponse(json);
		    return "qc_point";
		
	}

	
	public  String revokeQcPoint(){
		
		HandlerResult result =new HandlerResult();
		Integer userId = user.getId();
		String userName = user.getRealName();
	    String id = request.getParameter("qcPointId");//投诉单号
	    String complaintId = request.getParameter("complaintId");//投诉单号
	    QcPointEntity  qcEntity = new QcPointEntity();
	    qcEntity.setUpdatePersonId(userId);
	    qcEntity.setUpdatePerson(userName);
	    qcEntity.setId(Integer.parseInt(id));
	    qcEntity.setDelFlag(1);
	    qcEntity.setComplaintId(Integer.parseInt(complaintId));
	    int flag = qcPointService.revokeQcPoint(qcEntity);
		  
	    if( flag == 0){
			  
			 Map<String, Object> map =new HashMap<String, Object>();
			 map.put("complaintId", qcEntity.getComplaintId());
		     String	 requestStr =JSONObject.fromObject(map).toString();
		     int re_flag = ComplaintRestClient.sendQcPointToQMS(requestStr);//发送撤销数据质检点到QMS
		     if(re_flag==0){
				    	
				    result.setRetObj("success");
				    	 
				 }else{
				    	
				    result.setRetObj("error");
				    result.setResMsg("撤销失败");
				  }
		    	
		    }else{
		    	
		    	result.setRetObj("error");
		    	result.setResMsg("撤销失败");
		    }
	    
			JSONObject json =JSONObject.fromObject(result);
		    writeResponse(json);
		    return "qc_point";
		
	}
	
	
	
	public String changeReturnVisitSwitch(){
	    Integer complaintId = Integer.valueOf(request.getParameter("complaintId"));
	    Integer switchState = Integer.valueOf(request.getParameter("switchState"));
	    entity = (ComplaintEntity) service.get(complaintId);
	    entity.setReturnVisitSwitch(switchState);
	    service.update(entity);
	    return "info";
	}
	
	
	/**
	 * 返回处理中逻辑：
	 * 如果该投诉单有处理人且处理人未离职则返回处理中，原处理人不变
	 * 否则返回待指定
	 * @return
	 */
	public String back2Processing(){
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("id", id);
	    entity = (ComplaintEntity) service.get(id);
        if(entity.getDeal() > 0) {
            UserEntity dealUser = userService.getUserByID(entity.getDeal());
            if(dealUser.getDelFlag() == 0) {
                entity.setState(2);
            }else{
                entity.setState(1);
                entity.setDeal(0);
                entity.setDealName("");
            }
        } else {
            entity.setState(1); //如果没有处理人则返回待指定状态
        }
	    
	    service.update(entity);
	    return "info";
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
	 * 回呼页面
	 * @return
	 */
	public String getCallBackDetail() {
		String cmpId = request.getParameter("cmpId");
		ComplaintEntity complaint = (ComplaintEntity) service.get(cmpId);
		request.setAttribute("complaint", complaint);
		Date thirtyMinsLater=DateUtil.addMinute(new Date(), 30);
		String orginalTime = DateUtil.formatDateTime(thirtyMinsLater);
		request.setAttribute("orginalTime", orginalTime);
		return "complaint_callback_detail";
	}
	
	/**
	 * 回呼选择页面
	 * @return
	 */
	public String chooseCallBackType() {
		String complaintId = request.getParameter("complaintId");
		request.setAttribute("complaintId", complaintId);
		return "choose_callback_type";
	}
	
	/**
	 *提交回呼信息
	 * @return
	 */
	public  String callBack(){
		HandlerResult result =new HandlerResult();
		String userName = user.getRealName();
	    String complaint_id = request.getParameter("cmpId");//投诉单号
		ComplaintEntity complaintEntity = (ComplaintEntity) service.get(complaint_id);
	    String cmpPerson = request.getParameter("cmpPerson");//投诉人
	    String cmpPhone = request.getParameter("cmpPhone");//投诉人电话
	    String content = request.getParameter("content");//投诉备注
	    String callBackTime = request.getParameter("callBackTime");//投诉备注
	    TaskReminderEntity entity = cmpTaskReminderService.getCallBackInfoFromCmp(complaintEntity);
	    entity.setAddPerson(userName);
	    entity.setCmpPerson(cmpPerson);
	    entity.setCmpPhone(cmpPhone);
	    entity.setContent(content);
	    entity.setCallBackTime(callBackTime);
	    followNoteService.addFollowNote(Integer.valueOf(complaint_id), user.getId(), user.getRealName(), content,0,Constans.LAUNCH_CALL_BACK);
	    int flag = cmpTaskReminderService.addTaskReminder(entity);
	    if( flag == 0){
			//投诉单自动分配逻辑
			result.setRetObj("success");
		}else{
			result.setRetObj("error");
			result.setResMsg("提交失败");
		}
		JSONObject json =JSONObject.fromObject(result);
	    writeResponse(json);
	    return "complaint_callback_detail";
	}

	/**
	 * 处理优先级初始化
	 * @return
	 */
	public String getPriority() {
		
	    String id = request.getParameter("complaintId");
		ComplaintEntity complaint = (ComplaintEntity) service.get(id);
	    request.setAttribute("complaint", complaint);
	    return  "complaint_priority";
	}
	
	
	public  String updatePriority(){
		
		HandlerResult result =new HandlerResult();
	    String priority = request.getParameter("priority");//处理优先级
	    String priorityContent = request.getParameter("priorityContent");//投诉单号
	    String complaintId = request.getParameter("complaintId");//投诉单号
	    
		ComplaintEntity complaint = (ComplaintEntity) service.get(complaintId);
		complaint.setPriorityContent(priorityContent);
		complaint.setPriority(Integer.parseInt(priority));
		
		try {
			
			  service.update(complaint);  
			  result.setRetObj("success");
			  
		 } catch (Exception e) {
			
			result.setRetObj("error");
	    	result.setResMsg("提交失败");
		 }
	  
	     JSONObject json =JSONObject.fromObject(result);
		 writeResponse(json);
		 return "complaint_priority";
		
	}
	

	/**
	 * 点评修改申请
	 * @return
	 */
	public String getReviewApply() {
		
	    String id = request.getParameter("complaintId");
	    request.setAttribute("complaintId", id);
	    return  "complaint_reviewApply";
	}
	
	
	/**
	 * 提交点评数据
	 * @return
	 */
	public  String submitReview(){
		
		HandlerResult result =new HandlerResult();
	    String qcAffairDesc = request.getParameter("qcAffairDesc");//质检事宜详述
	    String complaintId = request.getParameter("complaintId");//投诉单号
	    
		ComplaintEntity complaint = (ComplaintEntity) service.get(complaintId);
			
		 Map<String , Object> map =new HashMap<String, Object>();
		 map.put("qcAffairDesc", qcAffairDesc);
	     map.put("cmpId", complaintId);
	     map.put("ordId", complaint.getOrderId());
	     map.put("prdId", complaint.getRouteId());
	     map.put("addPerson", user.getRealName());
	     map.put("addPersonId", user.getId());
		 String	 requestStr =JSONObject.fromObject(map).toString();
		 int re_flag = ComplaintRestClient.sendReviewToQMS(requestStr);//发送点评数据到QMS
		 if(re_flag==0){
					    	
		      result.setRetObj("success");
					    	 
		}else{
					    
			 result.setRetObj("error");
			 result.setResMsg("发送数据失败");
		 }
			    	
	     JSONObject json =JSONObject.fromObject(result);
		 writeResponse(json);
		 return "complaint_reviewApply";
		
	}
	
	/**
	 * 工作交接跟进记录填写页面
	 * @return
	 */
	public String follow_handover() {
	    String complaintId = request.getParameter("complaintId");
	    String orderId=request.getParameter("orderId");
	    if (appointManagerService.isMemberOfType(user.getId(),2)) {
	    	request.setAttribute("canBigNight", true);
		}
	    request.setAttribute("complaintId", complaintId);
	    request.setAttribute("orderId", orderId);
	    return  "follow_handover";
	}
	
	/**
	 * 工作交接
	 * 将投诉单返回未分配
	 * @return
	 */
	public String handover(){
		String complaintId = request.getParameter("complaintId");
		String orderId=request.getParameter("orderId");
		String note = request.getParameter("note");
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("id", Integer.valueOf(complaintId));
	    paramMap.put("orderId", Integer.valueOf(orderId));
	    paramMap.put("note", note);
	    paramMap.put("noteContent", Constans.ASSIGN_NEW_DEALER);
	    paramMap.put("flowName", Constans.WORK_HANDOVER);
	    paramMap.put("dealName", user.getRealName());
	    paramMap.put("deal", user.getId());
	    service.insertFollowRecord(paramMap);
	    service.returnNotAssigned(paramMap);
	    return "follow_handover";
	}
	
	/**
	 * 大夜交接
	 * 将投诉单返回未分配
	 * @return
	 */
	public String handoverAndAutoAssign(){
		String complaintId = request.getParameter("complaintId");
		String orderId=request.getParameter("orderId");
		String note = request.getParameter("note");
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("id", Integer.valueOf(complaintId));
	    paramMap.put("orderId", Integer.valueOf(orderId));
	    paramMap.put("note", note);
	    paramMap.put("noteContent", Constans.ASSIGN_NEW_DEALER);
	    paramMap.put("flowName", Constans.BIG_NIGHT_HANDOVER);
	    paramMap.put("dealName", user.getRealName());
	    paramMap.put("deal", user.getId());
	    service.insertFollowRecord(paramMap);
	    service.changeComplaintToUnAssigned(paramMap);
	    return "follow_handover";
	}
	
	/**
	 * 投诉单批量交接
	 * @return
	 */
	public String handoverAll(){
		String[] ids = request.getParameter("ids").split(",");
		service.returnNotAssignedByComplaintIds(ids,user);
		return "info";
	}
	
	/**
	 * 填写投诉改进报告
	 * @return
	 */
	public String improveBill(){
		String complaintId = request.getParameter("complaintId");
		
		//获得投诉事宜，并进行组合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		paramMap.put("del_flag", 1);
		
		List<ComplaintReasonEntity> complaintReasonList = reasonService.getReasonAndQualitycostList(paramMap);
		List<String> reasonList = new ArrayList<String>();
		if(complaintReasonList != null && complaintReasonList.size() > 0){
			buildReasonList(complaintReasonList, reasonList);
		}
		List<Map<String, Object>> allUsers = userService.getUserNamesInJSON();//在职用户
		
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("reasonList", reasonList);
		request.setAttribute("allUsers", allUsers);
		
		return "improve_bill";
	}
	
	/**
	 * 汇总投诉事宜   一级-二级-详情
	 * @param complaintReasonList
	 * @return
	 */
	private void buildReasonList(List<ComplaintReasonEntity> complaintReasonList, List<String> reasonList) {
		StringBuilder str = null;
		for(ComplaintReasonEntity reason : complaintReasonList){
			str = new StringBuilder(reason.getType()).append(" - ").append(reason.getSecondType());
			if(!("").equals(reason.getTypeDescript())){
				str.append(" - ").append(reason.getTypeDescript());
			}
			reasonList.add(str.toString());
		}
	}
	
	 /**
   	 * 上传改进报告附件列表
   	 * @throws Exception
   	 */
	public String improveUpload() {
		String complaintId = request.getParameter("complaintId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		paramMap.put("tableName", "improveBill");
		List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
		request.setAttribute("attachList",attachList);
		request.setAttribute("complaintId",complaintId);
		return "improve_bill_upload";
	}
	   
	/**
   	 * 上传改进报告附件
   	 * @throws Exception
   	 */
	public String improveAttachUpload() throws FileUploadException, UnsupportedEncodingException {
		String complaintId = request.getParameter("complaintId");
		Map<String, Object> fileMap = new HashMap<String, Object>();
		if(toolFileFileName != null){
			for(int i=0;i<toolFileFileName.size();i++){
		       	String fileName = toolFileFileName.get(i);
		   		fileMap.put("pic", toolFile.get(i));
		   		fileMap.put("fileName", fileName);
		   		fileMap.put("complaintId", complaintId);
		   		fileMap.put("addPerson", user.getRealName());
		   		fileMap.put("tableName", "improveBill");//区分改进报告的附件
		   		attachService.uploadQcFile(fileMap);
			 }
		}
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 paramMap.put("complaintId", complaintId);
		 paramMap.put("tableName", "improveBill");
		 List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
		 request.setAttribute("attachList",attachList);
		 request.setAttribute("complaintId",complaintId);
		 return "improve_bill_upload";
	}
	
	/**
	 * 删除改进报告附件
	 * @return
	 */
	public String delImproveAttach(){
   		int attachId = Integer.parseInt(request.getParameter("attachId"));
   		String complaintId = request.getParameter("complaintId");
   		Map<String, Object> fileMap = new HashMap<String, Object>();
   		fileMap.put("id", attachId);
   		AttachEntity toolFile = (AttachEntity) attachService.getQcAttach(fileMap);
   		toolFile.setDelFlag(0);
   		toolFile.setUpdateTime(new Date());
   		attachService.updateQcAttach(toolFile);
   		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		paramMap.put("tableName", "improveBill");
		List<AttachEntity> attachList =  attachService.getQcAttachList(paramMap);
        request.setAttribute("attachList",attachList);
        request.setAttribute("complaintId",complaintId);
        return "improve_bill_upload";
   	}

	/**
	 * 提交投诉改进报告
	 * @return
	 */
	public String saveImproveBill(){
		HandlerResult result =new HandlerResult();
		
	    Integer cmpId = Integer.valueOf(request.getParameter("complaintId"));
		String impPerson = request.getParameter("improvePerson");
		
		String key = cmpId + impPerson;//防重加锁
	    logger.info("重复提交操作加锁 key: " + key);
	    boolean lockFlag  = MemcachesUtil.add(key, impPerson, 5);
	    
	    if(lockFlag){
	    	try{
				String userName = user.getRealName();
			    CmpImproveVo cmpImproveVo = new CmpImproveVo();
			    cmpImproveVo.setCmpId(cmpId);//投诉单号
			    cmpImproveVo.setCmpAffair(request.getParameter("cmpAffair"));//投诉事宜
			    cmpImproveVo.setImprovePoint(request.getParameter("improvePoint"));//改进点
			    cmpImproveVo.setImpPerson(impPerson);//改进人
			    cmpImproveVo.setAddPerson(userName);
			    	
			    service.saveImproveBill(cmpImproveVo, result);
			    
			}catch(Exception e){
				result.setRetObj("error");
				result.setResMsg("改进报告生成失败");
				logger.error("投诉改进报告生成失败", e);
			}finally{
				MemcachesUtil.delete(key);
			}
	    }else{
	    	result.setRetObj("error");
			result.setResMsg("请不要重复提交!");
	    }
	    
	    JSONObject json =JSONObject.fromObject(result);
	    writeResponse(json);
		return "improve_bill";
	}
	
    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public int getIsHotel() {
        return isHotel;
    }

    public void setIsHotel(int isHotel) {
        this.isHotel = isHotel;
    }

    public List<String> getDealDepartments() {
        return DBConfigManager.getConfigAsList("sys.dealDepart");
    }

    public void setDealDepartments(List<String> dealDepartments) {
        this.dealDepartments = dealDepartments;
    }
    
	public List<String> getUpgradeReasons() {
		return DBConfigManager.getConfigAsList("senior.upgrade.reason");
	}

	public void setUpgradeReasons(List<String> upgradeReasons) {
		this.upgradeReasons = upgradeReasons;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getCanLink() {
		return canLink;
	}

	public void setCanLink(Integer canLink) {
		this.canLink = canLink;
	}
}

