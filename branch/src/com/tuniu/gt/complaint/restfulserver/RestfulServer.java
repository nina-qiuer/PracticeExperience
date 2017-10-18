package com.tuniu.gt.complaint.restfulserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AgencyDetailEntity;
import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.entity.ComplaintCompleteCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintCrmEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.ComplaintSynchCrmEntity;
import com.tuniu.gt.complaint.entity.FileEntity;
import com.tuniu.gt.complaint.entity.FollowNoteThEntity;
import com.tuniu.gt.complaint.entity.FollowUpRecordEntity;
import com.tuniu.gt.complaint.entity.FollowUpRecordSynchEntity;
import com.tuniu.gt.complaint.entity.ReasonSynchCrmEntity;
import com.tuniu.gt.complaint.entity.ResponseEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.entity.TaskReminderEntity;
import com.tuniu.gt.complaint.service.IAgencyCommitService;
import com.tuniu.gt.complaint.service.IAttachService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintReportService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IComplaintTaskReminderService;
import com.tuniu.gt.complaint.service.IFollowNoteThService;
import com.tuniu.gt.complaint.service.IFollowUpRecordService;
import com.tuniu.gt.complaint.service.IReasonTypeService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.complaint.vo.AgencyPayInfoPage;
import com.tuniu.gt.complaint.vo.ComplaintVo;
import com.tuniu.gt.complaint.vo.QcVo;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.punishprd.entity.PunishPrdEntity;
import com.tuniu.gt.punishprd.service.IPunishPrdService;
import com.tuniu.gt.returnvisit.service.IPostSaleReturnVisitService;
import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.score.entity.ScoreTypeEntity;
import com.tuniu.gt.score.service.ScoreRecordService;
import com.tuniu.gt.score.service.ScoreTypeService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.FileBrokerUtils;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.workorder.entity.WorkOrder;
import com.tuniu.gt.workorder.entity.WorkOrderConfig;
import com.tuniu.gt.workorder.entity.WorkOrderObj;
import com.tuniu.gt.workorder.entity.WorkOrderOperationLog;
import com.tuniu.gt.workorder.service.IWorkOrderConfigService;
import com.tuniu.gt.workorder.service.IWorkOrderOperationLogService;
import com.tuniu.gt.workorder.service.IWorkOrderService;
import com.tuniu.operation.platform.annotation.MethodRestful;
import com.tuniu.operation.platform.annotation.ParamRestful;
import com.tuniu.operation.platform.annotation.ReturnRestful;
import com.tuniu.operation.platform.annotation.TypeRestful;
import com.tuniu.operation.platform.base.json.JsonFormatter;
import com.tuniu.operation.platform.base.text.StringUtils;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;
import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestServer;

@Controller
@RequestMapping("/complaint")
@TypeRestful(desc = "Restful接口类", author = "于良", version = "1.0")
public class RestfulServer {
	
	private static Logger logger = Logger.getLogger(RestfulServer.class);
	
	@Autowired
	private IAgencyCommitService  agencyCommitService;
	// 引入投诉sercie
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-reason_type")
	private IReasonTypeService reasonTypeService;
	
	// 引入解决方案service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	private IComplaintSolutionService complaintSolutionService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;
	
	@Autowired
	@Qualifier("complaint_service_impl-follow_note_th")
	private IFollowNoteThService noteThService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_follow_up_record_impl-follow_up_record")
	private IFollowUpRecordService followUpRecordService;
	
	// 用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("score_service_impl-score_type")
	private ScoreTypeService scoreTypeService;
	
	@Autowired
	@Qualifier("score_service_impl-score_record")
	private ScoreRecordService scoreRecordService;
	
	@Autowired
    @Qualifier("pp_service_impl-punishprd")
	private IPunishPrdService punishPrdService;
	
    @Autowired
    @Qualifier("rv_service_impl-postSaleReturnVisit")
    private IPostSaleReturnVisitService postSaleReturnService;
    
    @Autowired
    @Qualifier("wo_service_impl-work_order")
    private IWorkOrderService workOrderService;
    
    @Autowired
    @Qualifier("wo_service_impl-config")
    private IWorkOrderConfigService workOrderConfigService;

    @Autowired
    @Qualifier("wo_service_impl-operation_log")
    private IWorkOrderOperationLogService workOrderOperationLogService;
	
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_complete_count")
    private IComplaintReportService complaintReportService;
    
	@Autowired
	private IComplaintTaskReminderService cmpTaskReminderService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
    private IComplaintReasonService complaintReasonService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@Autowired
	@Qualifier("complaint_service_impl-attach")
	private IAttachService attachService;
	
	/**
	 * 外部系统发起投诉接口
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="launchComplaint", method = RequestMethod.POST)
	@MethodRestful(mapping = "/launchComplaint", name = "launchComplaint", method = "post", desc = "发起投诉", params = {
			@ParamRestful(type = "int", name = "orderId", desc = "订单号"),
			@ParamRestful(type = "string", name = "complaintType", desc = "投诉来源"),
			@ParamRestful(type = "int", name = "complaintLevel", desc = "投诉等级"),
			@ParamRestful(type = "string", name = "levelOne", desc = "投诉事宜一级分类"),
			@ParamRestful(type = "string", name = "levelTwo", desc = "投诉事宜二级分类"),
			@ParamRestful(type = "string", name = "content", desc = "投诉详情"),
			@ParamRestful(type = "int", name = "ownId", desc = "投诉发起人id"),
			@ParamRestful(type = "string", name = "cmpPerson", desc = "投诉人姓名"),
			@ParamRestful(type = "string", name = "cmpPhone", desc = "投诉人电话"),
			@ParamRestful(type = "string", name = "ownerName", desc = "投诉发起人姓名") }, 
			returns = @ReturnRestful(type = "json", desc = "{\"success\" : \"true | false\", \"errorCode\" : error-code, \"data\" : \"投诉记录id\"}"), example = "")
	public void launchComplaint(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		try {
			String restData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(restData);
			int orderId = JsonUtil.getIntValue(jsonData, "orderId");
			int ownerId = JsonUtil.getIntValue(jsonData, "ownId");
			String ownerName = JsonUtil.getStringValue(jsonData, "ownerName");
			String cmpPerson = JsonUtil.getStringValue(jsonData, "cmpPerson");
			String cmpPhone = JsonUtil.getStringValue(jsonData, "cmpPhone");
			boolean dealBySelf = JsonUtil.getBooleanValue(jsonData, "dealBySelf");
			Object data = jsonData.get("attachInfo");
			JSONArray attachInfo = JSONArray.fromObject(data);
			
			String compCity = "";
			if (7 == ownerId) {
				compCity = "南京";
			}
			
			int compId = -1;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", String.valueOf(orderId));
			params.put("state", "1,2,5,7");
			List<ComplaintEntity> complaintList = (List<ComplaintEntity>) complaintService.fetchList(params);
			int addReasonFlag = 0;
			if (null != complaintList && complaintList.size() > 0) {
				ComplaintEntity compEnt = complaintList.get(0);
				if (!("出游中".equals(compEnt.getOrderState()) && Constans.SPECIAL_BEFORE_TRAVEL.equals(compEnt.getDealDepart()))) {
					addReasonFlag = 1; // 新增投诉事宜
					compId = compEnt.getId();
					List<ComplaintReasonEntity> reasonList=getReasonList(jsonData);
					reasonService.insertReasonList(ownerId, ownerName, compId, compCity, reasonList,-1);
					//新增投诉事宜默认新增回呼任务
//					complaintService.autoReturnNotAssigned(compEnt);
					cmpTaskReminderService.addDefaultComplaintTask(compId);
					responseStr = generateResultString("Success", 0, compId);
				}
			}
			
			if (0 == addReasonFlag) {
				
				ComplaintEntity entity = complaintService.getOrderInfoMain(String.valueOf(orderId));
				if(entity.getErrCode()==1){
				
					responseStr = generateResultString(entity.getErrorMesg(), 230000, 0);
					
				}else{	
					if(dealBySelf){
						entity.setDealBySelf(1);
						int dealId = ownerId;
						ComplaintUtil.recordUserNums(dealId, ComplaintUtil.MEM_PRE_COMPLAINT);
						ComplaintUtil.recordUserOrders(dealId, String.valueOf(entity.getOrderId()), ComplaintUtil.MEM_PRE_COMPLAINT);
					}
					entity.setComeFrom(JsonUtil.getStringValue(jsonData, "complaintType"));
					entity.setLevel(JsonUtil.getIntValue(jsonData, "complaintLevel"));
					entity.setOwnerId(ownerId);
					entity.setOwnerName(ownerName);
					entity.setReasonList(getReasonList(jsonData));
					entity.setCompCity(compCity);
					entity.setCmpPerson(cmpPerson);
					entity.setCmpPhone(cmpPhone);
					complaintService.insertComplaint(entity);
					compId = entity.getId();
					responseStr = generateResultString("Success", 0, compId);
				}
			}
			
			if (null != attachInfo && !attachInfo.isEmpty() && null != data) {
				for (Object attach : attachInfo) {
					
					JSONObject attachInfoJson = JSONObject.fromObject(attach);
					String fileName = JsonUtil.getStringValue(attachInfoJson, "fileName");
					String url = JsonUtil.getStringValue(attachInfoJson, "url");
					AttachEntity attachEntity = new AttachEntity();
					attachEntity.setAddPerson(ownerName);
					attachEntity.setTableName("ct_complaint");
					attachEntity.setPath(url);
					attachEntity.setName(fileName);
					attachEntity.setType(fileName.substring(fileName.lastIndexOf(".")));
					attachEntity.setDelFlag(1);
					attachEntity.setComplaintId(compId);
					attachService.insertAttach(attachEntity);
				}
			}
			
			
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			responseStr = generateResultString("Parameter decode error", 230000, 0);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			responseStr = generateResultString("Date format error", 230000, 0);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			responseStr = generateResultString("Data insert error", 230000, 0);
		}
		write(response, responseStr);
	}
	
	private List<ComplaintReasonEntity> getReasonList(JSONObject jsonData) {
		List<ComplaintReasonEntity> reasonList = new ArrayList<ComplaintReasonEntity>();
		
		ComplaintReasonEntity reason = new ComplaintReasonEntity();
		reason.setType(JsonUtil.getStringValue(jsonData, "levelOne"));
		reason.setSecondType(JsonUtil.getStringValue(jsonData, "levelTwo"));
		reason.setTypeDescript(JsonUtil.getStringValue(jsonData, "content"));
		reason.setOrderId(JsonUtil.getIntValue(jsonData, "orderId"));
		reasonList.add(reason);
		
		return reasonList;
	}
	
	/**
	 * 生成接口输出字符串
	 */
	private String generateResultString(String msg, int errorCode, Object data) {
		String resStr = "";
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (errorCode == 0) {
			returnMap.put("success", true);
		} else {
			returnMap.put("success", false);
		}

		returnMap.put("msg", msg);
		returnMap.put("errorCode", errorCode);
		returnMap.put("data", data);
		logger.info(JSONObject.fromObject(returnMap).toString());
		try {
			resStr = Base64.encodeBase64String(JSONObject.fromObject(returnMap)
					.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}

		return resStr;
	}
	
	private String generateResultString(String msg) {
        String resStr = "";
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        if ("成功".equals(msg)) {
            dataMap.put("result", true);
        } else {
            dataMap.put("result", false);
        }

        dataMap.put("errormsg", msg);
        try {
            returnMap.put("data", JsonFormatter.toJsonString(dataMap));
            resStr = JsonFormatter.toJsonString(returnMap);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
        

        return resStr;
    }
	
	
	
	/**
	 * 接口返回值写入输出流
	 */
	private void write(HttpServletResponse response, String responseStr) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Methods", "*");
		try {
			response.getWriter().write(responseStr);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 供应商未处理（未确认）、已处理（已确认、默认、申诉）记录条数查询
	 */
	@RequestMapping(value="getConfirmInfoNum", method = RequestMethod.GET)
	public void getConfirmInfoNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String restData = "";
		int agencyId = 0;
		try {
			restData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(restData);
			agencyId = JsonUtil.getIntValue(jsonData, "agencyId");
			List<Map<String, Object>> confirmInfoNum = shareSolutionService.getConfirmInfoNum(agencyId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (null != confirmInfoNum && confirmInfoNum.size() > 0) {
				for (Map<String, Object> map : confirmInfoNum) {
					int processStatus = (Integer) map.get("processStatus");
					if (0 == processStatus) {
						resultMap.put("waitProcessCount", map.get("num"));
					} else if (1 == processStatus) {
						resultMap.put("alreadyProcessCount", map.get("num"));
					}
				}
			} else {
				resultMap.put("waitProcessCount", 0);
				resultMap.put("alreadyProcessCount", 0);
			}
			responseStr = generateResultString("Success", 0, resultMap);
		} catch (TRestException e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	/**
	 * 供应商相关赔付List信息查询接口
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="getAgencyPayoutList", method = RequestMethod.GET)
	public void getAgencyPayoutList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			logger.info("getAgencyPayoutList Ing: requestData = " + requestData);
			Map<String, Object> paramMap = convertAPLRequestData(requestData);
			AgencyPayInfoPage page = shareSolutionService.getAgencyPayInfoPage(paramMap);
			responseStr = generateResultString("Success", 0, page);
		} catch (TRestException e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	private Map<String, Object> convertAPLRequestData(String requestData) {
		JSONObject jsonData = JSONObject.fromObject(requestData);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//区分从接口发起查询和页面请求
		paramMap.put("requestFromRest", true);
		paramMap.put("agencyId", JsonUtil.getIntValue(jsonData, "agencyId"));
		paramMap.put("orderId", JsonUtil.getIntValue(jsonData, "orderId"));
		paramMap.put("complaintId", JsonUtil.getIntValue(jsonData, "complaintId"));
		paramMap.put("routeId", JsonUtil.getIntValue(jsonData, "routeId"));
		
		String startCity = JsonUtil.getStringValue(jsonData, "startCity");
		if (StringUtil.isNotEmpty(startCity)) {
			paramMap.put("startCity", "%" + startCity + "%");
		}
		
		String compTimeBgn = JsonUtil.getStringValue(jsonData, "complaintDateBgn");
		if (StringUtil.isNotEmpty(compTimeBgn)) {
			paramMap.put("compTimeBgn", compTimeBgn + " 00:00:00");
		}
		
		String compTimeEnd = JsonUtil.getStringValue(jsonData, "complaintDateEnd");
		if (StringUtil.isNotEmpty(compTimeEnd)) {
			paramMap.put("compTimeEnd", compTimeEnd + " 23:59:59");
		}
		
		String startTimeBgn = JsonUtil.getStringValue(jsonData, "startDateBgn");
		if (StringUtil.isNotEmpty(startTimeBgn)) {
			paramMap.put("startTimeBgn", startTimeBgn + " 00:00:00");
		}
		
		String startTimeEnd = JsonUtil.getStringValue(jsonData, "startDateEnd");
		if (StringUtil.isNotEmpty(startTimeEnd)) {
			paramMap.put("startTimeEnd", startTimeEnd + " 23:59:59");
		}
		
		int processStatus = JsonUtil.getIntValue(jsonData, "processStatus");
		paramMap.put("processStatus", processStatus);
		if (1 == processStatus) {
			String confirmStateStr = "1,2,3,4,6,8";
			int confirmState = JsonUtil.getIntValue(jsonData, "confirmStateId");
			if (confirmState > 0) {
				confirmStateStr = String.valueOf(confirmState);
				if(confirmState == 3){//已申诉
				    confirmStateStr = "3,4,6,8";
				}
			}
			paramMap.put("confirmStateStr", confirmStateStr);
		}
		paramMap.put("start", JsonUtil.getIntValue(jsonData, "start"));
		paramMap.put("limit", JsonUtil.getIntValue(jsonData, "limit"));
		
		return paramMap;
	}
	
	/**
	 * 供应商相关赔付明细查询接口
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="getAgencyPayoutDetail", method = RequestMethod.GET)
	public void getAgencyPayoutDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			Map<String, Object> paramMap = convertAPRequestData(requestData);
			Map<String, Object> resultMap = shareSolutionService.getAgencyPayInfoDetail(paramMap);
			responseStr = generateResultString("Success", 0, resultMap);
		} catch (TRestException e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	private Map<String, Object> convertAPRequestData(String requestData) {
		JSONObject jsonData = JSONObject.fromObject(requestData);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("agencyId", JsonUtil.getIntValue(jsonData, "agencyId"));
		paramMap.put("complaintId", JsonUtil.getIntValue(jsonData, "complaintId"));
		//区分从接口发起查询和页面请求
		paramMap.put("requestFromRest", true);
		return paramMap;
	}
	
	/**
	 * 确认赔付接口
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="confirmPayout", method = RequestMethod.POST)
	public void confirmPayout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			Map<String, Object> paramMap = convertCPRequestData(requestData);
			int resultCode = shareSolutionService.confirmPayout(paramMap);
			switch (resultCode) {
			    case 0:
				    responseStr = generateResultString("Success", 0, null);
				    break;
			    case 230001:
			    	responseStr = generateResultString("确认失败：此确认信息已被撤销，请刷新后查看！", 230001, null);
			    	break;
			    case 230002:
			    	responseStr = generateResultString("确认失败：数据已修改，请刷新后再试！", 230002, null);
			    	break;
			    case 230003:
			    	responseStr = generateResultString("确认失败：已过确认期限！", 230003, null);
			    	break;
				default:
					responseStr = generateResultString("Success", 0, null);
				    break;
			}
		} catch (TRestException e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	private Map<String, Object> convertCPRequestData(String requestData) {
		JSONObject jsonData = JSONObject.fromObject(requestData);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("agencyId", JsonUtil.getIntValue(jsonData, "agencyId"));
		paramMap.put("complaintId", JsonUtil.getIntValue(jsonData, "complaintId"));
		paramMap.put("localCurrencyAmount", JsonUtil.getDoubleValue(jsonData, "localCurrencyAmount"));
		paramMap.put("confirmer", JsonUtil.getStringValue(jsonData, "confirmer"));
		return paramMap;
	}
	
	/**
	 * 申诉接口
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="appealPayout", method = RequestMethod.POST)
	public void appealPayout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			Map<String, Object> paramMap = convertAPPRequestData(requestData);
			int resultCode = shareSolutionService.appealPayout(paramMap);
			switch (resultCode) {
			    case 0:
			    	responseStr = generateResultString("Success", 0, null);
			    	break;
			    case 230004:
			    	responseStr = generateResultString("申诉失败：此确认信息已被撤销，请刷新后查看！", 230004, null);
			    	break;
			    case 230005:
			    	responseStr = generateResultString("申诉失败：数据已修改，请刷新后再试！", 230005, null);
			    	break;
			    case 230006:
			    	responseStr = generateResultString("申诉失败：已过确认期限！", 230006, null);
			    	break;
			    default:
			    	responseStr = generateResultString("Success", 0, null);
			    	break;
			}
		} catch (TRestException e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, null);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, null);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	private Map<String, Object> convertAPPRequestData(String requestData) {
		JSONObject jsonData = JSONObject.fromObject(requestData);
		logger.info("appealPayout Bgn: requestMsg is " + jsonData);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("agencyId", JsonUtil.getIntValue(jsonData, "agencyId"));
		paramMap.put("complaintId", JsonUtil.getIntValue(jsonData, "complaintId"));
		paramMap.put("confirmState", 3);
		paramMap.put("oldState", 0);
		paramMap.put("localCurrencyAmount", JsonUtil.getDoubleValue(jsonData, "localCurrencyAmount"));
		paramMap.put("appealer", JsonUtil.getStringValue(jsonData, "appealer"));
		paramMap.put("contactPhone", JsonUtil.getStringValue(jsonData, "contactPhone"));
		paramMap.put("contactEmail", JsonUtil.getStringValue(jsonData, "contactEmail"));
		paramMap.put("contactQQ", JsonUtil.getStringValue(jsonData, "contactQQ"));
		paramMap.put("appealContent", JsonUtil.getStringValue(jsonData, "appealContent"));
		return paramMap;
	}
	
	/**
	 * 对客解决方案退回接口
	 */
	@RequestMapping(value = "returnComplaintSolution", method = RequestMethod.POST)
	@ResponseBody
	public String returnComplaintSolution(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer refund_id = JsonUtil.getIntValue(jsonData, "refund_id");
			Integer state = JsonUtil.getIntValue(jsonData, "state");
			String order_id = JsonUtil.getStringValue(jsonData, "order_id");
			String depict = JsonUtil.getStringValue(jsonData, "depict");
			String user_name = JsonUtil.getStringValue(jsonData, "user_name");
			if(state!=1){
				result.put("success", true);
				result.put("msg", "打款成功不处理");
				result.put("data", 0);
			}else if(refund_id==0){
				result.put("success", false);
				result.put("errorCode", 230000);
				result.put("msg", "入参格式错误");
			}else{
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("refund_id", refund_id);
				paramMap.put("order_id", order_id);
				List<ComplaintSolutionEntity> resultList = complaintSolutionService.getComplaintIdByReFundId(paramMap);
				if (resultList.size() == 0) {
					result.put("success", false);
					result.put("errorCode", 230000);
					result.put("msg", "找不到对应的赔款记录");
				} else if (resultList.size() > 1) {
					result.put("success", false);
					result.put("errorCode", 230000);
					result.put("msg", "赔款记录异常，请联系fangyouming");
				} else {
					Integer complaintId = resultList.get(0).getComplaintId();
					complaintSolutionService.returnComplaintSolution(complaintId, Constans.FINANCE_BACK,depict);
					complaintFollowNoteService.addFollowNote(complaintId, 0, user_name, "对客解决方案退回，退回原因："+depict, 0, "赔款单作废");
					logger.info("returnComplaintSolution complaintId:" + complaintId);
					result.put("success", true);
					result.put("msg", "成功");
					result.put("data", complaintId);
				}
			}
		} catch (TRestException e) {
			logger.error("returnComplaintSolution failed" + e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error("returnComplaintSolution failed" + e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	/**
	 * 订单相关赔付承担信息查询接口
	 */
	@RequestMapping(value="getOrderIndemnityShareInfo", method = RequestMethod.GET)
	public void getOrderIndemnityShareInfo(HttpServletRequest request, HttpServletResponse response) {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			int orderId = JsonUtil.getIntValue(jsonData, "orderId");
			Map<String, Object> resultMap = shareSolutionService.getOrderIndemnityShareInfo(orderId);
			responseStr = generateResultString("Success", 0, resultMap);
		} catch (TRestException e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	/**
	 * 第三方投诉信息查询接口
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getThirdCompInfo", method = RequestMethod.GET)
	public void getThirdCompInfo(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("RemoteAddr is: " + request.getRemoteAddr());
		
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("complaintId", JsonUtil.getStringValue(jsonData, "complaintId"));
			paramMap.put("visibleFlag", 1);
			paramMap.put("dataLimitStart", JsonUtil.getIntValue(jsonData, "start"));
			paramMap.put("dataLimitEnd", JsonUtil.getIntValue(jsonData, "limit"));
			int totalCount = noteThService.getTotalCount(paramMap);
			List<FollowNoteThEntity> noteThs = (List<FollowNoteThEntity>) noteThService.fetchList(paramMap);
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			for (FollowNoteThEntity note : noteThs) {
				Map<String, Object> noteMap = new HashMap<String, Object>();
				noteMap.put("addTime", DateUtil.formatDate(note.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
				noteMap.put("flowName", note.getFlowName());
				noteMap.put("content", note.getContent());
				rows.add(noteMap);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("rows", rows);
			resultMap.put("count", totalCount);
			responseStr = generateResultString("Success", 0, resultMap);
		} catch (TRestException e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	/**
	 * 根据订单号查询当前投诉处理信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getCurrentCompInfo", method = RequestMethod.GET)
	public void getCurrentCompInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String restData = "";
		try {
			restData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(restData);
			int orderId = JsonUtil.getIntValue(jsonData, "orderId");
			
			if(orderId == 0){
				responseStr = generateResultString("orderId must greater than 0", 230000, 0);
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", String.valueOf(orderId));
				params.put("state", "1,2,5,7");
				List<ComplaintEntity> complaintList = (List<ComplaintEntity>) complaintService.fetchList(params);
				
				Map<String, Object> resultMap = new HashMap<String, Object>();
				if (null != complaintList && complaintList.size() > 0) {
					ComplaintEntity compEnt = complaintList.get(0);
					resultMap.put("complaintId", compEnt.getId());
					int dealerId = 0;
					String dealerName = "";
					if (compEnt.getAssociater() > 0) {
						dealerId = compEnt.getAssociater();
						dealerName = compEnt.getAssociaterName();
					} else {
						dealerId = compEnt.getDeal();
						dealerName = compEnt.getDealName();
					}
					resultMap.put("dealerId", dealerId);
					resultMap.put("dealerName", dealerName);
					
					String extension = "";
					if (dealerId > 0) {
						UserEntity user = userService.getUserByID(dealerId);
						if (null != user) {
							extension = user.getExtension();
						}
					}
					resultMap.put("extension", extension);
				} else {
					resultMap.put("complaintId", 0);
					resultMap.put("dealerId", 0);
					resultMap.put("dealerName", "");
					resultMap.put("extension", "");
				}
				responseStr = generateResultString("Success", 0, resultMap);
			}
			
		} catch (TRestException e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("Complaint system error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	
	/**
	 * NB 插入沟通数据
	 * @throws IOException 
	 */
	@RequestMapping(value="saveNBAgency", method = RequestMethod.POST)
	public void saveNBAgency(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", JsonUtil.getStringValue(jsonData, "userId"));
			paramMap.put("userName", "供应商"+JsonUtil.getStringValue(jsonData, "userName"));
			paramMap.put("complaintId", JsonUtil.getStringValue(jsonData, "complaintId"));
			paramMap.put("orderId", JsonUtil.getStringValue(jsonData, "orderId"));
			paramMap.put("userIp", JsonUtil.getStringValue(jsonData, "userIp"));
			paramMap.put("flag", 2);//供应商
			paramMap.put("agencyId",JsonUtil.getStringValue(jsonData, "agencyId"));
			paramMap.put("agencyName", JsonUtil.getStringValue(jsonData, "agencyName"));
			paramMap.put("typeName", JsonUtil.getStringValue(jsonData, "typeName"));
			paramMap.put("typeNum", JsonUtil.getStringValue(jsonData, "typeNum"));
			paramMap.put("statusName", JsonUtil.getStringValue(jsonData, "statusName"));
			paramMap.put("statusNum", JsonUtil.getStringValue(jsonData, "statusNum"));
			String flag = agencyCommitService.addCommit(paramMap);
			Map<String, Object> resultMap = new HashMap<String, Object>();

			 if("0".equals(flag)){
				 
				 resultMap.put("count", 1);
				 responseStr = generateResultString("Success", 0, resultMap);
			 }else{
			    	
				 responseStr = generateResultString("saveNBAgency inser into ct_chat_to_agency fail", 230000, 0);
				
			    }
			
		} catch (TRestException e) {
			responseStr = generateResultString("saveNBAgency  error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("saveNBAgency inser into ct_chat_to_agency fail：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	
	/**
	 *查询投诉与咨询详情接口
	 * @throws IOException 
	 */
	@RequestMapping(value="queryComplaintDetail", method = RequestMethod.GET)
	public void queryComplaintDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Map<String, Object> paramMap = new HashMap<String, Object>();
		
			if("".equals(JsonUtil.getStringValue(jsonData, "agencyId"))||"".equals(JsonUtil.getStringValue(jsonData, "complaintId"))||"".equals(JsonUtil.getStringValue(jsonData, "roomId"))){
				
				responseStr = generateResultString("queryComplaintDetail  error", 230000, 0);
			}else{
				
				paramMap.put("complaintId", JsonUtil.getStringValue(jsonData, "complaintId"));
				paramMap.put("roomId", JsonUtil.getStringValue(jsonData, "roomId"));
				paramMap.put("agencyId", JsonUtil.getStringValue(jsonData, "agencyId"));
				AgencyDetailEntity agencyDetail = agencyCommitService.queryComplaintDetail(paramMap);
				responseStr = generateResultString("Success", 0, agencyDetail);
			}
		} catch (TRestException e) {
			responseStr = generateResultString("queryComplaintDetail  error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("queryComplaintDetail  error：" + e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	/**
	 * 查询投诉单详情
	 */
	@RequestMapping(value="getCmpBillInfo", method = RequestMethod.GET)
	public void getCmpBillInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer cmpId = JsonUtil.getIntValue(jsonData, "cmpId");
			if (cmpId > 0) {
				ComplaintVo cmpInfo = complaintService.getCmpBillInfo(cmpId);
				if(null != cmpInfo){
					logger.info("根据投诉单未查询到数据");
					Long addTime = cmpInfo.getAddTime1().getTime();
					cmpInfo.setAddTime(addTime);
					if (null != cmpInfo.getFinishTime1()) {
						Long finishTime = cmpInfo.getFinishTime1().getTime();
						cmpInfo.setFinishTime(finishTime);
					}
					responseStr = generateResultString("Success", 0, cmpInfo);
				}else{
					
					responseStr = generateResultString("getCmpBillInfo error 根据投诉单未查询到数据", 230000, 0);
				}
				
			} else {
				responseStr = generateResultString("getCmpBillInfo error 投诉单id不大于0", 230000, 0);
			}
		} catch (TRestException e) {
			responseStr = generateResultString("getCmpBillInfo error TRestException", 230000, 0);
			logger.error("getCmpBillInfo error TRestException" + e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("getCmpBillInfo error Exception", 230000, 0);
			logger.error("getCmpBillInfo error Exception" + e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	
	/**
	 * 修改投诉等级
	 */
	@RequestMapping(value="updateCmpLevel", method = RequestMethod.GET)
	public void updateCmpLevel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer cmpId = JsonUtil.getIntValue(jsonData, "cmpId");
			Integer level = JsonUtil.getIntValue(jsonData, "level");
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("cmpId", cmpId);
			map.put("level", level);
			if (cmpId > 0) {
				
				complaintService.updateCmpLevel(map);
				responseStr = generateResultString("Success", 0, 0);
			} else {
				responseStr = generateResultString("updateCmpLevel error:投诉单"+cmpId+"不存在", 230000, 0);
			}
		} catch (TRestException e) {
			responseStr = generateResultString("updateCmpLevel error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("updateCmpLevel error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	} 
    
	/**
	 * 获取跟进记录
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getRecordList", method = RequestMethod.GET)
	public void getRecordList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer cmpId = JsonUtil.getIntValue(jsonData, "complaintId");
			Map<String, Object> paramMap =new HashMap<String, Object>();
			paramMap.put("complaintId", cmpId);
			List<FollowUpRecordEntity> recordList = (List<FollowUpRecordEntity>) followUpRecordService.fetchList(paramMap);
			for (FollowUpRecordEntity record : recordList) {
				UserEntity addUser = userService.getUserByID(record.getAddUser());
				String addUserName = null == addUser ? "" : addUser.getRealName();
				record.setAddUserName(addUserName);
				record.setAdd_time(DateUtil.formatDate(record.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
				record.setAddTime(null);
			}
			if(null!=recordList && recordList.size()>0){
				
				responseStr = generateResultString("Success", 0, recordList);
				
			}else{
				
				responseStr = generateResultString("getRecordList error", 230000, 0);
			}
			
			
		} catch (TRestException e) {
			responseStr = generateResultString("getRecordList error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("getRecordList error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
		
	}
	
	/**
	 * 推送记分数据到投诉质检
	 */
	@RequestMapping(value="pushPunishData", method = RequestMethod.GET)
	public void pushPunishData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			logger.info("pushPunishData request :"+requestData );
			JSONObject json = JSONObject.fromObject(requestData);
			Integer complaintId = json.getInt("cmpId");
			
			JSONArray jsonArray = JSONArray.fromObject(json.get("list"));
			if(complaintId!=0){
				
				scoreRecordService.deleteByComplaintId(complaintId);
			}
			
			for(int i=0;i<jsonArray.size();i++){
				
				JSONObject jsonData = jsonArray.getJSONObject(i);
				String  qc_person = JsonUtil.getStringValue(jsonData, "qc_person");
				Integer order_id = JsonUtil.getIntValue(jsonData, "order_id");
				Integer route_id = JsonUtil.getIntValue(jsonData, "route_id");
				Integer complaint_id = JsonUtil.getIntValue(jsonData, "complaint_id");
				String responsible_person = JsonUtil.getStringValue(jsonData, "responsible_person");
				Integer position_id = JsonUtil.getIntValue(jsonData, "position_id");
				String score_target1 = JsonUtil.getStringValue(jsonData, "score_target1");
				Integer score_value1 = JsonUtil.getIntValue(jsonData, "score_value1");	
				Integer dep_id1 = JsonUtil.getIntValue(jsonData, "dep_id1");
				Integer dep_id2 = JsonUtil.getIntValue(jsonData, "dep_id2");
			    String score_type_name = JsonUtil.getStringValue(jsonData, "score_type_name");
			    String score_type_fatherName = JsonUtil.getStringValue(jsonData, "score_type_fatherName");
			    String description = JsonUtil.getStringValue(jsonData, "description");
			    Integer score_type_id = 0;
			    Map<String, Object> map =new HashMap<String, Object>();
			    map.put("scoreTypeName", score_type_name);
			    map.put("scoreTypeFatherName", score_type_fatherName);
				ScoreTypeEntity scoreType =   scoreTypeService.getScoreByName(map);
			    if(null!=scoreType){
			    	
			    	score_type_id = scoreType.getId();
			    }
			
				ScoreRecordEntity sr = new ScoreRecordEntity();
				sr.setQcDate(DateUtil.getSqlToday());
				sr.setQcPerson(qc_person);
				sr.setOrderId(order_id);
				sr.setRouteId(route_id);
				sr.setComplaintId(complaint_id);
				sr.setJiraNum("");
				sr.setQuestionClassId(0);
				sr.setResponsiblePerson(responsible_person);
				sr.setImprover("");
				sr.setDepId1(dep_id1);
				sr.setDepId2(dep_id2);
				sr.setPositionId(position_id);
				sr.setScoreTarget1(score_target1);
				sr.setScoreValue1(score_value1);
				sr.setScoreTarget2("无");
				sr.setScoreValue2(0);
				sr.setScoreTypeId(score_type_id);
				sr.setDescription(description);
				scoreRecordService.insert(sr);
				
			}
			
			responseStr = generateResultString("Success", 0, 0);
			
		} catch(Exception e){
			
			responseStr = generateResultString("pushPunishData error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
			
		}
		write(response, responseStr);
	} 
	
    /**
     * 推送触红数据到投诉质检
     */
    @RequestMapping(value = "pushTouchRedData", method = RequestMethod.GET)
    public void pushTouchRedData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TRestServer server = new TRestServer(request, response);
        String responseStr = "";
        String requestData = "";
        try {
            requestData = server.getRestData();
            logger.info("pushTouchRedData request :" + requestData);
            JSONObject jsonData = JSONObject.fromObject(requestData);
            PunishPrdEntity ppe = new PunishPrdEntity();
            ppe.setOrderId(JsonUtil.getIntValue(jsonData, "orderId"));
            String travelDateBgn = JsonUtil.getStringValue(jsonData, "travelDateBgn");
            if(travelDateBgn != null) {
                ppe.setTravelDateBgn(DateUtil.parseDate(travelDateBgn));
            }
            ppe.setPrdManager(JsonUtil.getStringValue(jsonData, "prdManager"));
            ppe.setPrdOfficer(JsonUtil.getStringValue(jsonData, "prdOfficer"));
            ppe.setRouteName(JsonUtil.getStringValue(jsonData, "routeName"));
            ppe.setBU(JsonUtil.getStringValue(jsonData, "BU"));
            ppe.setRouteId(JsonUtil.getLongValue(jsonData, "routeId"));
            ppe.setSupplier(JsonUtil.getStringValue(jsonData, "supplier"));
            ppe.setQcId(JsonUtil.getIntValue(jsonData, "qcId"));
            ppe.setCmpId(JsonUtil.getIntValue(jsonData, "cmpId"));
            punishPrdService.outerTouchRedDeal(ppe);

            responseStr = generateResultString("Success", 0, 0);

        } catch(Exception e) {

            responseStr = generateResultString("pushTouchRedData  error" + e.getMessage(), 230000, 0);
            logger.error(e.getMessage(), e);

        }
        write(response, responseStr);
    }
    
    
    
    /**
	 * 查询对客解决方案
	 */
	@RequestMapping(value="getCmpSolution", method = RequestMethod.GET)
	public void getCmpSolution(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			JSONArray jsonArray =  jsonData.getJSONArray("ordList");
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("ordIds", jsonArray);
			List<ComplaintSolutionEntity> solutionList = complaintSolutionService.getCmpSolutionByCmpId(map);
			
			responseStr = generateResultString("Success", 0, solutionList);
			logger.info("getCmpBillInfo result:" + responseStr );
		} catch (TRestException e) {
			responseStr = generateResultString("getCmpBillInfo error", 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("getCmpBillInfo error", 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	

    /**
	 * 查询公司承担总额
	 */
	@RequestMapping(value="getCmpSpecial", method = RequestMethod.GET)
	public void getCmpSpecial(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			JSONArray jsonArray =  jsonData.getJSONArray("cmpIds");
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("cmpIds", jsonArray);
			logger.error("getCmpSpecial response:" + map );
			List<Map<String, Object>> solutionList = shareSolutionService.getCmpSpecialByCmpId(map);
			responseStr = generateResultString("Success", 0, solutionList);
			logger.error("getCmpSpecial result:" + responseStr );
		} catch (TRestException e) {
			responseStr = generateResultString("getCmpSpecial error", 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("getCmpSpecial error", 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	
	/**
     * {"data":
     * "{\"tel\":\"13800380038\",\"smsContent\":\"msg\",\"receiveTime\":\"2014-05-13 14:00:00\",\"extendCode\":\"88\"}"
     * }
     * 接口出参：{"data":"{\"result\":true,\"errormsg\":\"errormsg\"}"}
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "pushMsgReply", method = RequestMethod.POST)
    public void pushMsgReply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TRestServer server = new TRestServer(request, response);
        String responseStr = "";
        String requestData = "";
        int resultCode = 0; 
        try {
            requestData = server.getRestData();
            
            Map<String,Object> requestMap = JsonFormatter.toObject(requestData, Map.class);
            JSONObject jsonObj = new JSONObject();
            jsonObj.putAll(requestMap);
            logger.info("request from MSG with param: "+jsonObj);
            
            boolean valid = validateRequestDataFromMsg(jsonObj);
            if(valid){
                JSONObject  dataJSON = jsonObj.getJSONObject("data");
                String tel = JsonUtil.getStringValue(dataJSON, "tel");
                String receiveTime = JsonUtil.getStringValue(dataJSON, "receiveTime");
                String smsContent = JsonUtil.getStringValue(dataJSON, "smsContent");
                Integer prefix = Integer.valueOf(smsContent.substring(0, 1));
                Integer chooseItem = Integer.valueOf(smsContent.substring(1));
               resultCode = postSaleReturnService.dealWithMsgCallBack(tel,receiveTime,prefix,chooseItem);
            }else{
                resultCode = 240001;//入参格式错误
            }
            
            
            resultCode = 0;
            
            switch(resultCode) {
                case 0:
                    responseStr = generateResultString("成功");
                    break;
                case 240001:
                    responseStr = generateResultString("入参格式错误");
                    break;
                default:
                    responseStr = generateResultString("成功");
                    break;
            }
        } catch(TRestException e) {
            responseStr = generateResultString("Complaint system error：" + e.getMessage());
            logger.error(e.getMessage(), e);
        } catch(Exception e) {
            responseStr = generateResultString("Complaint system error：" + e.getMessage());
            logger.error(e.getMessage(), e);
        }
        
        write(response, responseStr);
    }

    /**
     * @param requestData
     * @return
     */
    private boolean validateRequestDataFromMsg(JSONObject jsonObj) {
        JSONObject  dataJSON = jsonObj.getJSONObject("data");
        String tel = JsonUtil.getStringValue(dataJSON, "tel");
        String receiveTime = JsonUtil.getStringValue(dataJSON, "receiveTime");
        String smsContent = JsonUtil.getStringValue(dataJSON, "smsContent");
        
        boolean result = true;
        if("".equals(tel) || tel.length()!=11 ){ // 电话号码校验
            result = false;
        }
        
        if(DateUtil.parseDate(receiveTime, "yyyy-MM-dd HH:mm:ss") ==null) { //接收时间校验
            result = false;
        }
        
        smsContent = smsContent.trim();
        if(!smsContent.matches("[12]{1}[1-4]{1}")){ // 回复内容校验
            result = false;
        }
        
        return result;
    }

	/**
	 * 查询投诉单详情
	 */
	@RequestMapping(value="getQcInfo", method = RequestMethod.GET)
	public void getQcInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer cmpId = JsonUtil.getIntValue(jsonData, "cmpId");
			if (cmpId > 0) {
				
				ComplaintEntity compEnt = (ComplaintEntity) complaintService.fetchOne(cmpId);
				if(null!=compEnt){
					
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("complaintId", cmpId);
					paramMap.put("del_flag", 1);
					List<ComplaintReasonEntity> complaintReason = reasonService.getReasonAndQualitycostList(paramMap);
					QcVo qcVo = new QcVo();
					qcVo.setPrdId(compEnt.getRouteId());
					if(compEnt.getStartTime() != null){
						qcVo.setGroupDate(compEnt.getStartTime().getTime());
					}else{
						Date groupDate = DateUtil.parseDate("1970-01-01");//团期判断
						qcVo.setGroupDate(groupDate.getTime());
					}
					qcVo.setOrdId(compEnt.getOrderId());
					qcVo.setCmpId(compEnt.getId());
					qcVo.setQcAffairDesc(getQcAffairDesc(complaintReason));
					qcVo.setGroupFlag(1); // 投诉质检组
					qcVo.setQcTypeId(4); // 内外部客户反馈、投诉-投诉质检
					qcVo.setQcAffairSummary("客人投诉");
					responseStr = generateResultString("Success", 0, qcVo);
					
				}else{
					
					responseStr = generateResultString("getQcInfo error", 230000, 0);
				}
				
				
			}else{
					
				 responseStr = generateResultString("getQcInfo error", 230000, 0);
			}
				
		} catch (Exception e) {
			
			responseStr = generateResultString("getQcInfo error", 230000, 0);
			logger.error(e.getMessage(), e);
		}
		logger.info("getQcInfo result"+responseStr);
		write(response, responseStr);
	}
    
	@RequestMapping(value="queryComplaintsByPhoneNum", method = RequestMethod.GET)
	public void queryComplaintsByPhoneNum(String phoneNum, HttpServletResponse response){
	    if(StringUtils.isNotEmpty(phoneNum)){
	        List<ComplaintEntity> compList = complaintService
	                .getComplaintsByPhoneNum(phoneNum);
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
	        String url = Constant.CONFIG.getProperty("COMPLAINT_DETAIL_URL");
	        List<Map<String, String>> mpList = new ArrayList<Map<String, String>>();
	        Map<String, String> mp = null;
	        for (ComplaintEntity comp : compList) {
	            mp = new HashMap<String, String>();
	            mp.put("id", comp.getId().toString());
	            mp.put("url", url + comp.getId());
	            mp.put("orderId", comp.getOrderId()+"");
	            mp.put("route", comp.getRoute());
	            //投诉单状态
	            mp.put("state", comp.getState().toString());
	            //投诉人及投诉人电话
	            mp.put("cmpPerson", comp.getCmpPerson());
	            mp.put("cmpPhone", comp.getCmpPhone());
	            
	            //投诉处理人
	            UserEntity user = userService.getUserByID(comp.getDeal());
	            if (null != user) {
	                mp.put("deal", user.getRealName());
	                mp.put("dealId", comp.getDeal()+"");
	            } else {
	                mp.put("deal", "");
	                mp.put("dealId","0");
	            }
	            mp.put("comeFrom", comp.getComeFrom());
	            mp.put("dealDepart", comp.getDealDepart());
	            mpList.add(mp);
	        }
	        
	        write(response, generateResultString("成功", 0, mpList));
	    }else{
	        write(response, generateResultString("电话为空", -1, null));
	    }
        
	}
	private String getQcAffairDesc(List<ComplaintReasonEntity> reasonList) {
		StringBuffer sb = new StringBuffer();
		for (ComplaintReasonEntity r : reasonList) {
			sb.append(r.getType()).append("-").append(r.getSecondType())
			.append("：").append(r.getTypeDescript()).append("<br>");
		}
		return sb.toString();
	}
	
	/**
	 * 查询工单信息
	 */
	@RequestMapping(value="getWorkOrderList", method = RequestMethod.GET)
	public void getWorkOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			String startDate = JsonUtil.getStringValue(jsonData, "startDate").trim();
			String endDate = JsonUtil.getStringValue(jsonData, "endDate").trim();
			if("".equals(startDate)){
				Date nowDate=new Date();
				startDate=DateUtil.formatDate(nowDate);
				endDate=startDate;
			}
			Map<String, Object> paramMap =new HashMap<String, Object>();
			paramMap.put("startDate", startDate+" 00:00:00");
			paramMap.put("endDate", endDate+" 23:59:59");
			List<WorkOrderObj> workOrderList = (List<WorkOrderObj>) workOrderService.getWorkOrderList(paramMap);
			responseStr = generateResultString("Success", 0, workOrderList);
		} catch (TRestException e) {
			responseStr = generateResultString("getWorkOrderList error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("getWorkOrderList error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
		
	}
	
	//判断是否加跨域头
	private void canCrossDomain(HttpServletResponse response){
//		List<String> cross_domain=DBConfigManager.getConfigAsList("restful.cross.domain");
//		if(cross_domain.get(0).equals("0")){
			response.addHeader("Access-Control-Allow-Origin", "*");
//		}
	}
	
	@RequestMapping("/queryWorkOrderDepartments")
	@ResponseBody
	public String queryWorkOrderDepartments(HttpServletResponse response){
		canCrossDomain(response);
		JSONObject result = new JSONObject();
		JSONArray dataArray = new JSONArray();
		
		try {
			List<WorkOrderConfig> configList = workOrderConfigService.getAllConfig();
			if(!CollectionUtils.isEmpty(configList)){
				JSONObject obj;
				for (WorkOrderConfig config : configList) {
					obj = new JSONObject();
					obj.put("id", config.getId());
					obj.put("name", config.getDepartment());
					dataArray.add(obj);
				}
			}
			
			result.put("success", true);
			result.put("msg", "成功");
		} catch (Exception e) {
			logger.error("获取工单配置失败",e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		
		result.put("data", dataArray);
		
		
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	@RequestMapping("/queryReasonTypes")
	@ResponseBody
	public String queryReasonTypes(HttpServletRequest request, HttpServletResponse response){
		
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		JSONArray dataArray = new JSONArray();
		
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			List<String> typeNames = reasonTypeService.getChilderByParentName(jsonData.getString("parentName"));
			if(!CollectionUtils.isEmpty(typeNames)){
				dataArray = JSONArray.fromObject(typeNames);
			}
			
			result.put("success", true);
			result.put("msg", "成功");
		} catch (Exception e) {
			logger.error("获取工单配置失败",e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		
		result.put("data", dataArray);
		
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	@RequestMapping("/queryWorkOrders")
	@ResponseBody
	public String queryWorkOrders(String phone,HttpServletResponse response){
		canCrossDomain(response);
		JSONObject result = new JSONObject();
		JSONArray dataArray = new JSONArray();
		
		try {
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("phone", phone);
			List<WorkOrder> workOrderList = (List<WorkOrder>) workOrderService.fetchList(param);
			if(!CollectionUtils.isEmpty(workOrderList)){
				JSONObject obj;
				for (WorkOrder workOrder : workOrderList) {
					obj = new JSONObject();
					obj.put("id", workOrder.getId());
					obj.put("addTime", DateUtil.formatDateTime(workOrder.getAddTime()));
					obj.put("phoneMatter", workOrder.getPhoneMatter());
					obj.put("department", workOrder.getDepartment());
					obj.put("businessClass", workOrder.getBusinessClass());
					obj.put("addPerson", workOrder.getAddPerson());
					obj.put("state", workOrder.getState());
					dataArray.add(obj);
				}
			}
			
			result.put("success", true);
			result.put("msg", "成功");
		} catch (Exception e) {
			logger.error("获取工单列表失败",e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		
		result.put("data", dataArray);
		
		
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	@RequestMapping(value="addWorkOrder", method = RequestMethod.POST)
	@ResponseBody
	public String addWorkOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			WorkOrder workOrder = (WorkOrder) JSONObject.toBean(jsonData,WorkOrder.class);
			workOrderService.insert(workOrder);
			Integer workOrderId = workOrder.getId();
			WorkOrderOperationLog workOrderOperationLog=new WorkOrderOperationLog();
			workOrderOperationLog.setWoId(workOrderId);
			workOrderOperationLog.setContent("发起工单");
			workOrderOperationLog.setEvent("发起");
			workOrderOperationLog.setOperatePerson(workOrder.getAddPerson());
			workOrderOperationLogService.insert(workOrderOperationLog);
			result.put("success", true);
			result.put("msg", "成功");
			result.put("data", workOrderId);
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	@RequestMapping(value="launchNoOrderComplaint", method = RequestMethod.POST)
	@ResponseBody
	public String launchNoOrderComplaint(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			String complaintType = JsonUtil.getStringValue(jsonData, "complaintType").trim();
			Integer complaintLevel = JsonUtil.getIntValue(jsonData, "complaintLevel");
			String cmpPerson = JsonUtil.getStringValue(jsonData, "cmpPerson").trim();
			String cmpPhone = JsonUtil.getStringValue(jsonData, "cmpPhone").trim();
			Integer ownId = JsonUtil.getIntValue(jsonData, "ownId");
			String ownerName = JsonUtil.getStringValue(jsonData, "ownerName").trim();
			String levelOne = JsonUtil.getStringValue(jsonData, "levelOne").trim();
			String levelTwo = JsonUtil.getStringValue(jsonData, "levelTwo").trim();
			String content = JsonUtil.getStringValue(jsonData, "content").trim();
			boolean isHotel = JsonUtil.getBooleanValue(jsonData, "isHotel");
			
			ComplaintEntity complaintEntity=new ComplaintEntity();
			complaintEntity.setCmpPerson(cmpPerson);
			complaintEntity.setCmpPhone(cmpPhone);
			complaintEntity.setContactPerson(cmpPerson);
			complaintEntity.setContactPhone(cmpPhone);
			complaintEntity.setOwnerId(ownId);
			complaintEntity.setOwnerName(ownerName);
			complaintEntity.setLevel(complaintLevel);
			complaintEntity.setComeFrom(complaintType);

			complaintEntity.setState(1);// 投诉状态为投诉待指定
			complaintEntity.setDelFlag(0);
			complaintEntity.setComplaintNum(1);
			complaintEntity.setCustId(0);
			complaintEntity.setCustomer("-");
			complaintEntity.setCustomerLeader("-");
			complaintEntity.setServiceManager("-");
			complaintEntity.setAgencyTel("");
			complaintEntity.setProductLineId(0);
			complaintEntity.setSecondaryDepId(0);
			complaintEntity.setDestCategoryId(0);
			complaintEntity.setDestCategoryName("");
			
			if(isHotel){
				complaintEntity.setDealDepart(Constans.HOTEL); //酒店的无订单投诉交给酒店组处理岗处理
			}else{
				complaintEntity.setDealDepart(Constans.SPECIAL_BEFORE_TRAVEL); // 无订单投诉有出游前客服组处理
			}
			ComplaintReasonEntity complaintReasonEntity=new ComplaintReasonEntity();
			complaintReasonEntity.setType(levelOne);
			complaintReasonEntity.setSecondType(levelTwo);
			complaintReasonEntity.setTypeDescript(content);
			List<ComplaintReasonEntity> complaintReasonList=new ArrayList<ComplaintReasonEntity>();
			complaintReasonList.add(complaintReasonEntity);
			complaintEntity.setReasonList(complaintReasonList);
			complaintService.insertNonOrdComp(complaintEntity);
			Integer cmpId = complaintEntity.getId();
			result.put("success", true);
			result.put("msg", "成功");
			result.put("data", cmpId);
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	
	/**
	 * 发起回呼
	 */
	@RequestMapping(value="sendReminder", method = RequestMethod.POST)
	public void sendReminder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			logger.info("sendReminder begin:"+jsonData);
			Integer cmpId = JsonUtil.getIntValue(jsonData, "cmpId");
			String cmpPerson = JsonUtil.getStringValue(jsonData, "cmpPerson");
			String cmpPhone = JsonUtil.getStringValue(jsonData, "cmpPhone");
			String content = JsonUtil.getStringValue(jsonData, "content");
			String userName = JsonUtil.getStringValue(jsonData, "userName");
			if (cmpId > 0) {
			    ComplaintEntity complaintEntity = (ComplaintEntity) complaintService.fetchOne(cmpId);
			    TaskReminderEntity entity = cmpTaskReminderService.getCallBackInfoFromCmp(complaintEntity);
			    entity.setAddPerson(userName);
			    entity.setCmpPerson(cmpPerson);
			    entity.setCmpPhone(cmpPhone);
			    entity.setContent(content);
			    //回呼时间为当前时间三十分钟后
				Date thirtyMinsLater=DateUtil.addMinute(new Date(), 30);
				String orginalTime = DateUtil.formatDateTime(thirtyMinsLater);
				entity.setCallBackTime(orginalTime);
			    int flag = cmpTaskReminderService.addTaskReminder(entity);
				if( flag == 0){
					responseStr = generateResultString("Success", 0, "");
				}else{
					responseStr = generateResultString("sendReminder error：保存记录失败",230000, "");
				}
			}else{
					
				 responseStr = generateResultString("sendReminder error:投诉单不能为0", 230000, 0);
			}
		} catch (Exception e) {
			
			responseStr = generateResultString("sendReminder error:"+e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		logger.info("sendReminder result"+responseStr);
		write(response, responseStr);
	}
	
	
    private TaskReminderEntity getCallBack(ComplaintEntity complaint ){
		
		TaskReminderEntity entity =new TaskReminderEntity();
		entity.setGuestName(complaint.getGuestName());
		entity.setGuestLevel(complaint.getGuestLevel());
		entity.setCmpId(complaint.getId());
		entity.setOrderId(complaint.getOrderId());
		entity.setStartTime(complaint.getStartTime());
		entity.setOrderState(complaint.getOrderState());
		entity.setBuildDate(complaint.getBuildDate());
		entity.setCustomerLeader(complaint.getCustomerLeader());
		entity.setCustomerLeaderId(complaint.getCustomerLeaderId());
		entity.setDealDepart(complaint.getDealDepart());
		entity.setDealName(complaint.getDealName());
		entity.setDeal(complaint.getDeal());
		entity.setLevel(complaint.getLevel());
		entity.setRepeateTime(complaint.getRepeateTime());
		entity.setCmpState(complaint.getState());
		return entity;
		
		
	}
    
    /**
	 * 根据月份查询计件数
	 */
	@RequestMapping(value="getCompleteCount", method = RequestMethod.GET)
	public void getCompleteCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRestServer server = new TRestServer(request, response);
		String responseStr = "";
		String requestData = "";
		try {
			requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			String countMonth = JsonUtil.getStringValue(jsonData, "countMonth").trim();
			if("".equals(countMonth)){
				Date nowDate=new Date();
				countMonth=DateUtil.formatDate(nowDate).substring(0,7);
			}
			Map<String, Object> paramMap =new HashMap<String, Object>();
			paramMap.put("countMonth", countMonth);
			List<ComplaintCompleteCountEntity> completeCountList=complaintReportService.getCompleteCountByWorknum(paramMap);
			responseStr = generateResultString("Success", 0, completeCountList);
		} catch (TRestException e) {
			responseStr = generateResultString("getCompleteCount error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseStr = generateResultString("getCompleteCount error"+ e.getMessage(), 230000, 0);
			logger.error(e.getMessage(), e);
		}
		write(response, responseStr);
	}
	
	@RequestMapping(value="getComplaintCancelState", method = RequestMethod.GET)
	@ResponseBody
	public String getComplaintCancelState(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer complaint_id = JsonUtil.getIntValue(jsonData, "complaint_id");
			Map<String, Object> paramMap =new HashMap<String, Object>();
			if(complaint_id.equals(0)){
				result.put("success", false);
				result.put("errorCode", 230000);
				result.put("msg", "入参格式错误");
			}else{
				paramMap.put("complaint_id", complaint_id);
				Boolean cancelState=complaintService.getComplaintCancelState(paramMap);
				result.put("success", true);
				result.put("msg", "成功");
				result.put("isCancel", cancelState);
			}
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	@RequestMapping(value="getComplaintByOrderIdOrCustId", method = RequestMethod.GET)
	@ResponseBody
	public String getComplaintByOrderIdOrCustId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer orderId = JsonUtil.getIntValue(jsonData, "orderId");
			Integer custId = JsonUtil.getIntValue(jsonData, "custId");
			String oneflag = JsonUtil.getStringValue(jsonData, "oneflag");
			Integer pageNum = JsonUtil.getIntValue(jsonData, "pageNum");
			Integer pageSize = JsonUtil.getIntValue(jsonData, "pageSize");
			if(orderId.equals(0)&&custId.equals(0)){
				result.put("success", false);
				result.put("errorCode", 230000);
				result.put("msg", "入参格式错误");
			}else{
				Map<String, Object> paramMap =new HashMap<String, Object>();
				if(!orderId.equals(0)){
					paramMap.put("orderId", orderId);
					paramMap.put("oneflag", oneflag);
				}else{
					paramMap.put("custId", custId);
					paramMap.put("oneflag", oneflag);
				}
				if(!pageNum.equals(0)&&!pageSize.equals(0)){
					paramMap.put("pageStart", (pageNum-1)*pageSize);
					paramMap.put("pageSize", pageSize);
					Integer resultCount=complaintService.getComplaintByOrderIdOrCustIdCount(paramMap);
					result.put("dataCount", resultCount);
				}
				List<ComplaintCrmEntity> complaintCrmList=(List<ComplaintCrmEntity>) complaintService.getComplaintByOrderIdOrCustId(paramMap);
				result.put("success", true);
				result.put("msg", "成功");
				result.put("data", complaintCrmList);
			}
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	/**
	 * 给Crm提供投诉数据
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="getCrmComplaint", method = RequestMethod.GET)
	@ResponseBody
	public String getCrmComplaint(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer complaint_id = JsonUtil.getIntValue(jsonData, "complaint_id");
			String startDate = JsonUtil.getStringValue(jsonData, "startDate");
			String endDate = JsonUtil.getStringValue(jsonData, "endDate");
			Integer pageNum = JsonUtil.getIntValue(jsonData, "pageNum");
			Integer pageSize = JsonUtil.getIntValue(jsonData, "pageSize");
			Map<String, Object> paramMap =new HashMap<String, Object>();
			if(!complaint_id.equals(0)){
				paramMap.put("complaint_id", complaint_id);
			}else{
				if(pageNum.equals(0)||pageSize.equals(0)){
					result.put("success", false);
					result.put("errorCode", 230000);
					result.put("msg", "入参格式错误");
					return Base64.encodeBase64String(result.toString().getBytes());
				}
				Date yesterdayDate=DateUtil.getSomeDay(new Date(), -1);
				if (StringUtils.isBlank(startDate)) {
					startDate=DateUtil.formatDate(yesterdayDate)+" 00:00:00";
				}
				if (StringUtils.isBlank(endDate)) {
					endDate = DateUtil.formatDate(yesterdayDate)+" 23:59:59";
				}
				paramMap.put("startDate", startDate);
				paramMap.put("endDate", endDate);
				paramMap.put("pageStart", (pageNum-1)*pageSize);
				paramMap.put("pageSize", pageSize);
				Integer resultCount=complaintService.getCrmComplaintCount(paramMap);
				result.put("dataCount", resultCount);
			}
			List<ComplaintSynchCrmEntity> complaintCrmList=(List<ComplaintSynchCrmEntity>) complaintService.getCrmComplaint(paramMap);
			result.put("success", true);
			result.put("msg", "成功");
			result.put("data", complaintCrmList);
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	/**
	 * 给Crm提供投诉事宜数据
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="getCrmComplaintReason", method = RequestMethod.GET)
	@ResponseBody
	public String getCrmComplaintReason(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer complaint_id = JsonUtil.getIntValue(jsonData, "complaint_id");
			String startDate = JsonUtil.getStringValue(jsonData, "startDate");
			String endDate = JsonUtil.getStringValue(jsonData, "endDate");
			Integer pageNum = JsonUtil.getIntValue(jsonData, "pageNum");
			Integer pageSize = JsonUtil.getIntValue(jsonData, "pageSize");
			Map<String, Object> paramMap =new HashMap<String, Object>();
			if(!complaint_id.equals(0)){
				paramMap.put("complaint_id", complaint_id);
			}else{
				if(pageNum.equals(0)||pageSize.equals(0)){
					result.put("success", false);
					result.put("errorCode", 230000);
					result.put("msg", "入参格式错误");
					return Base64.encodeBase64String(result.toString().getBytes());
				}
				Date yesterdayDate=DateUtil.getSomeDay(new Date(), -1);
				if (StringUtils.isBlank(startDate)) {
					startDate=DateUtil.formatDate(yesterdayDate)+" 00:00:00";
				}
				if (StringUtils.isBlank(endDate)) {
					endDate = DateUtil.formatDate(yesterdayDate)+" 23:59:59";
				}
				paramMap.put("startDate", startDate);
				paramMap.put("endDate", endDate);
				paramMap.put("pageStart", (pageNum-1)*pageSize);
				paramMap.put("pageSize", pageSize);
				Integer resultCount=complaintReasonService.getCrmComplaintReasonCount(paramMap);
				result.put("dataCount", resultCount);
			}
			List<ReasonSynchCrmEntity> reasonCrmList=(List<ReasonSynchCrmEntity>) complaintReasonService.getCrmComplaintReason(paramMap);
			result.put("success", true);
			result.put("msg", "成功");
			result.put("data", reasonCrmList);
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	/**
	 * 给Crm提供投诉跟进记录数据
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="getCrmComplaintFollow", method = RequestMethod.GET)
	@ResponseBody
	public String getCrmComplaintFollow(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			Integer complaint_id = JsonUtil.getIntValue(jsonData, "complaint_id");
			String startDate = JsonUtil.getStringValue(jsonData, "startDate");
			String endDate = JsonUtil.getStringValue(jsonData, "endDate");
			Integer pageNum = JsonUtil.getIntValue(jsonData, "pageNum");
			Integer pageSize = JsonUtil.getIntValue(jsonData, "pageSize");
			Map<String, Object> paramMap =new HashMap<String, Object>();
			if(!complaint_id.equals(0)){
				paramMap.put("complaint_id", complaint_id);
			}else{
				if(pageNum.equals(0)||pageSize.equals(0)){
					result.put("success", false);
					result.put("errorCode", 230000);
					result.put("msg", "入参格式错误");
					return Base64.encodeBase64String(result.toString().getBytes());
				}
				Date yesterdayDate=DateUtil.getSomeDay(new Date(), -1);
				if (StringUtils.isBlank(startDate)) {
					startDate=DateUtil.formatDate(yesterdayDate)+" 00:00:00";
				}
				if (StringUtils.isBlank(endDate)) {
					endDate = DateUtil.formatDate(yesterdayDate)+" 23:59:59";
				}
				paramMap.put("startDate", startDate);
				paramMap.put("endDate", endDate);
				paramMap.put("pageStart", (pageNum-1)*pageSize);
				paramMap.put("pageSize", pageSize);
				Integer resultCount=followUpRecordService.getCrmComplaintFollowCount(paramMap);
				result.put("dataCount", resultCount);
			}
			List<FollowUpRecordSynchEntity> reasonCrmList=(List<FollowUpRecordSynchEntity>) followUpRecordService.getCrmComplaintFollow(paramMap);
			result.put("success", true);
			result.put("msg", "成功");
			result.put("data", reasonCrmList);
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
	
	@RequestMapping(value="uploadComplaintFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadComplaintFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		String reqData = server.getRestData();
		ResponseEntity res = new ResponseEntity();
		String uploadUrl = Constant.CONFIG.getProperty("GFS_URL");
		String url = null;
		try {
			FileEntity entity = com.tuniu.operation.platform.tsg.base.core.utils.JsonUtil.toBean(reqData, FileEntity.class);
			if (entity.getFileContents().length > 2 * 1024 * 1024) {
				res.setErrorCode(1);
				res.setMsg("文件大小超过2M");
				res.setUrl(null);
			} else {
				ByteArrayInputStream bis = new ByteArrayInputStream(entity.getFileContents());
				url = FileBrokerUtils.uploadFile(entity.getFileName(), bis, uploadUrl, false);
				res.setErrorCode(0);
				res.setMsg("上传文件成功");
				res.setUrl(url);
			}
		} catch (Exception e) {
			res.setErrorCode(-1);
			res.setMsg("系统异常");
			res.setUrl(null);
		}
		return Base64.encodeBase64String(com.tuniu.operation.platform.tsg.base.core.utils.JsonUtil.toString(res).getBytes());
	}
	
	/**
	 * 赔偿金承担方修改申请oa回调接口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="appealAgengcyPayout", method = RequestMethod.POST)
	@ResponseBody
	public String appealAgengcyPayout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		canCrossDomain(response);
		TRestServer server = new TRestServer(request, response);
		JSONObject result = new JSONObject();
		try {
			String requestData = server.getRestData();
			JSONObject jsonData = JSONObject.fromObject(requestData);
			String oaId = JsonUtil.getStringValue(jsonData, "oaId");
			Integer auditorId = JsonUtil.getIntValue(jsonData, "ucId");
			Integer approvalResult = JsonUtil.getIntValue(jsonData, "approvalResult");
			String comment = JsonUtil.getStringValue(jsonData, "comment");
			
			Integer complaintId = JsonUtil.getIntValue(jsonData, "complaintId");
			Integer agencyId = JsonUtil.getIntValue(jsonData, "agencyId");
			String agencyName = JsonUtil.getStringValue(jsonData, "agencyName");
			BigDecimal agencyCharge = JsonUtil.getBigDecimalValue(jsonData, "agencyCharge");
			Integer foreignCurrencyType = JsonUtil.getIntValue(jsonData, "foreignCurrencyType");
			String foreignCurrencyName = JsonUtil.getStringValue(jsonData, "foreignCurrencyName");
			BigDecimal foreignCurrencyCharge = JsonUtil.getBigDecimalValue(jsonData, "foreignCurrencyCharge");
			SupportShareEntity newsupport = new SupportShareEntity();
			newsupport.setCode(agencyId);
			newsupport.setComplaintId(complaintId);
			newsupport.setName(agencyName);
			newsupport.setForeignCurrencyName(foreignCurrencyName);
			newsupport.setForeignCurrencyNumber(foreignCurrencyCharge.doubleValue());
			newsupport.setForeignCurrencyType(foreignCurrencyType);
			newsupport.setNumber(agencyCharge.doubleValue());
			//公司承担金额
			BigDecimal companyCharge = JsonUtil.getBigDecimalValue(jsonData, "companyCharge");
			shareSolutionService.appealShareSolution(approvalResult,complaintId,oaId,newsupport,
					auditorId,companyCharge,comment);
			result.put("success", true);
			result.put("msg", "成功");
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return Base64.encodeBase64String(result.toString().getBytes());
	}
}
