package com.tuniu.gt.complaint.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.ReasonTypeEntity;
import com.tuniu.gt.complaint.entity.TaskReminderEntity;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTaskReminderService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.service.IReasonTypeService;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

import net.sf.json.JSONArray;
import tuniu.frm.core.FrmBaseAction;


/**
* @ClassName: ComplaintReasonAction
* @Description:投诉事宜action
* @author Ocean Zhong
* @date 2012-1-19 上午11:39:00
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_action-complaint_reason")
@Scope("prototype")
public class ComplaintReasonAction extends FrmBaseAction<IComplaintReasonService,ComplaintReasonEntity> { 
	
    private Logger logger = LoggerFactory.getLogger(ComplaintReasonAction.class);
	public ComplaintReasonAction() {
		setManageUrl("complaint_reason");
		info = new HashMap<String, Object>();
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	public void setService(IComplaintReasonService service) {
		this.service = service;
	}
	
	// 引入投诉service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-reason_type")
	private IReasonTypeService reasonTypeService;
	
	// 引入投诉事宜service
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
	@Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@Autowired
	@Qualifier("cmpTaskReminderService")
	private IComplaintTaskReminderService cmpTaskReminderService;
	
	private Map<String,Object> info;
	
	private int specialEventFlag;//是否是特殊事件
	
	public int getSpecialEventFlag() {
		return specialEventFlag;
	}

	public void setSpecialEventFlag(int specialEventFlag) {
		this.specialEventFlag = specialEventFlag;
	}

	public String addComplaint(){
		this.setPageTitle("投诉事宜");
		Map<Integer,List<ReasonTypeEntity>> firstTypeMap = new LinkedHashMap<Integer, List<ReasonTypeEntity>>();//存放一级分类，key--旅行前1001，旅行中1002，旅行后1003
		Map<Integer, List<ReasonTypeEntity>> twoTypeMap = new LinkedHashMap<Integer, List<ReasonTypeEntity>>();//存放二级分类，key--二级分类father_id，list--同一个一级分类的二级分类list
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ReasonTypeEntity> allFirstReasonTypeList = new ArrayList<ReasonTypeEntity>();
		
		String isOrder = request.getParameter("isOrder");
		if (null == isOrder) {
			isOrder = (String) request.getAttribute("isOrder");
		}
		
		int fatherId = -1;
		if (1 == Integer.valueOf(isOrder)) { //有订单投诉
			//获取一级分类数据
			fatherId = 1001; // 旅行前
			paramMap.put("fatherId", fatherId);
			List<ReasonTypeEntity> bfrTrvlReasonTypeList = new ArrayList<ReasonTypeEntity>();
			bfrTrvlReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
			firstTypeMap.put(fatherId, bfrTrvlReasonTypeList);
			
			fatherId = 1002; // 旅行中
			paramMap.put("fatherId", fatherId);
			List<ReasonTypeEntity> inTrvlReasonTypeList = new ArrayList<ReasonTypeEntity>();
			inTrvlReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
			firstTypeMap.put(fatherId, inTrvlReasonTypeList);
			
			fatherId = 1003; // 旅行后
			paramMap.put("fatherId", fatherId);
			List<ReasonTypeEntity> aftrTrvlReasonTypeList = new ArrayList<ReasonTypeEntity>();
			aftrTrvlReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
			firstTypeMap.put(fatherId, aftrTrvlReasonTypeList);
			
			fatherId = 1004; // 机票
            paramMap.put("fatherId", fatherId);
            List<ReasonTypeEntity> airticketReasonTypeList = new ArrayList<ReasonTypeEntity>();
            airticketReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
            firstTypeMap.put(fatherId, airticketReasonTypeList);
            
            fatherId = 1005; // 交通
            paramMap.put("fatherId", fatherId);
            List<ReasonTypeEntity> trafficReasonTypeList = new ArrayList<ReasonTypeEntity>();
            trafficReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
            firstTypeMap.put(fatherId, trafficReasonTypeList);
            
            fatherId = 1133; // 酒店
            paramMap.put("fatherId", fatherId);
            List<ReasonTypeEntity> hotelReasonTypeList = new ArrayList<ReasonTypeEntity>();
            hotelReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
            firstTypeMap.put(fatherId, hotelReasonTypeList);
			
			allFirstReasonTypeList.addAll(bfrTrvlReasonTypeList);
			allFirstReasonTypeList.addAll(inTrvlReasonTypeList);
			allFirstReasonTypeList.addAll(aftrTrvlReasonTypeList);
			allFirstReasonTypeList.addAll(airticketReasonTypeList);
			allFirstReasonTypeList.addAll(trafficReasonTypeList);
			allFirstReasonTypeList.addAll(hotelReasonTypeList);
		}else { // 无订单投诉
			fatherId = 1006; // 无订单投诉
			paramMap.put("fatherId", fatherId);
			List<ReasonTypeEntity> noOrderReasonTypeList = new ArrayList<ReasonTypeEntity>();
			noOrderReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
			firstTypeMap.put(fatherId, noOrderReasonTypeList);
			
			allFirstReasonTypeList.addAll(noOrderReasonTypeList);
		}
		
		
		
		
		// 循环获取一级分类对应的二级分类数据
		for (ReasonTypeEntity reasonTypeEntity : allFirstReasonTypeList) {
			fatherId = reasonTypeEntity.getId();
			paramMap.put("fatherId", fatherId);
			twoTypeMap.put(fatherId, (List<ReasonTypeEntity>) reasonTypeService
					.fetchList(paramMap));
		}
		
		String complaintId = request.getParameter("complaintId");
		if (null == complaintId) {
			complaintId = ((Integer) request.getAttribute("complaintId")).toString();
		}
		
		ComplaintEntity complaint = (ComplaintEntity) complaintService.get(complaintId);
		String orderId = request.getParameter("orderId");
		
		if (StringUtil.isNotEmpty(complaintId)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("complaintId", complaintId);
			List<ComplaintReasonEntity> complaintReason = (List<ComplaintReasonEntity>) reasonService.fetchList(params);
			request.setAttribute("complaintReason", complaintReason);
			request.setAttribute("specialEventFlag", complaint.getSpecial_event_flag());
		}
		
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("orderId", orderId);
		request.setAttribute("firstTypeMap", firstTypeMap);
		request.setAttribute("twoTypeMap", twoTypeMap);
		request.setAttribute("complaint", complaint);
		jspTpl = "add_edit_reason";
		return jspTpl;
	}
	
	
	public String execute() {
		this.setPageTitle("投诉事宜");
		Map<Integer,List<ReasonTypeEntity>> firstTypeMap = new LinkedHashMap<Integer, List<ReasonTypeEntity>>();//存放一级分类，key--旅行前1001，旅行中1002，旅行后1003 机票1004 交通1005  
		Map<Integer, List<ReasonTypeEntity>> twoTypeMap = new LinkedHashMap<Integer, List<ReasonTypeEntity>>();//存放二级分类，key--二级分类father_id，list--同一个一级分类的二级分类list
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ReasonTypeEntity> allFirstReasonTypeList = new ArrayList<ReasonTypeEntity>();
		
		
		String complaintId = request.getParameter("complaintId");
		ComplaintEntity compEnt = (ComplaintEntity) complaintService.get(Integer.valueOf(complaintId));
		
		int fatherId = -1;
		if (0 == compEnt.getOrderId()) { //无订单投诉
			fatherId = 1006; // 无订单投诉
			paramMap.put("fatherId", fatherId);
			List<ReasonTypeEntity> noOrderReasonTypeList = new ArrayList<ReasonTypeEntity>();
			noOrderReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
			firstTypeMap.put(fatherId, noOrderReasonTypeList);
			
			allFirstReasonTypeList.addAll(noOrderReasonTypeList);
		}else {
			//获取一级分类数据
			fatherId = 1001; // 旅行前
			paramMap.put("fatherId", fatherId);
			List<ReasonTypeEntity> bfrTrvlReasonTypeList = new ArrayList<ReasonTypeEntity>();
			bfrTrvlReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
			firstTypeMap.put(fatherId, bfrTrvlReasonTypeList);
			
			fatherId = 1002; // 旅行中
			paramMap.put("fatherId", fatherId);
			List<ReasonTypeEntity> inTrvlReasonTypeList = new ArrayList<ReasonTypeEntity>();
			inTrvlReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
			firstTypeMap.put(fatherId, inTrvlReasonTypeList);
			
			fatherId = 1003; // 旅行后
			paramMap.put("fatherId", fatherId);
			List<ReasonTypeEntity> aftrTrvlReasonTypeList = new ArrayList<ReasonTypeEntity>();
			aftrTrvlReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
			firstTypeMap.put(fatherId, aftrTrvlReasonTypeList);
			
			fatherId = 1004; // 机票
            paramMap.put("fatherId", fatherId);
            List<ReasonTypeEntity> airticketReasonTypeList = new ArrayList<ReasonTypeEntity>();
            airticketReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
            firstTypeMap.put(fatherId, airticketReasonTypeList);
            
            fatherId = 1005; // 交通
            paramMap.put("fatherId", fatherId);
            List<ReasonTypeEntity> trafficReasonTypeList = new ArrayList<ReasonTypeEntity>();
            trafficReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
            firstTypeMap.put(fatherId, trafficReasonTypeList);
            
            fatherId = 1133; // 酒店
            paramMap.put("fatherId", fatherId);
            List<ReasonTypeEntity> hotelReasonTypeList = new ArrayList<ReasonTypeEntity>();
            hotelReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
            firstTypeMap.put(fatherId, hotelReasonTypeList);
            
            allFirstReasonTypeList.addAll(bfrTrvlReasonTypeList);
            allFirstReasonTypeList.addAll(inTrvlReasonTypeList);
            allFirstReasonTypeList.addAll(aftrTrvlReasonTypeList);
            allFirstReasonTypeList.addAll(airticketReasonTypeList);
            allFirstReasonTypeList.addAll(trafficReasonTypeList);
            allFirstReasonTypeList.addAll(hotelReasonTypeList);
            

		}

		// 循环获取一级分类对应的二级分类数据
		for (ReasonTypeEntity reasonTypeEntity : allFirstReasonTypeList) {
			fatherId = reasonTypeEntity.getId();
			paramMap.put("fatherId", fatherId);
			twoTypeMap.put(fatherId, (List<ReasonTypeEntity>) reasonTypeService
					.fetchList(paramMap));
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", complaintId);
		List<ComplaintReasonEntity> complaintReason = (List<ComplaintReasonEntity>) reasonService.fetchList(params);
		
		request.setAttribute("complaintReason", complaintReason);
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("orderId", compEnt.getOrderId());
		request.setAttribute("dealName", compEnt.getDealName());
		request.setAttribute("associaterName", compEnt.getAssociaterName());
		request.setAttribute("firstTypeMap", firstTypeMap);
		request.setAttribute("allFirstReasonTypeList", allFirstReasonTypeList);
		request.setAttribute("twoTypeMap", twoTypeMap);
		request.setAttribute("complaint", compEnt);

		String needWriteCallBack = request.getParameter("needWriteCallBack");
		if(StringUtil.isNotEmpty(needWriteCallBack)){
			request.setAttribute("needWriteCallBack", true);
		}
		request.setAttribute("cmpPerson", compEnt.getCmpPerson());
		request.setAttribute("cmpPhone", compEnt.getCmpPhone());
		Date thirtyMinsLater=DateUtil.addMinute(new Date(), 30);
		String orginalTime = DateUtil.formatDateTime(thirtyMinsLater);
		request.setAttribute("orginalTime", orginalTime);
		
		jspTpl = "edit_reason";
		return jspTpl;
	}
	
	/**
	 * 保存一条投诉事宜
	 * @return
	 */
	public String saveType() {
		String compCity = request.getParameter("compCity");
		Integer complaintId = entity.getComplaintId();
		try {
			service.insertReasonList(user.getId(), user.getRealName(), complaintId, compCity, getReasonList(),specialEventFlag);
		} catch (Exception e) {
			logger.error("addReason Exception:[errorMsg:"+e.getMessage()+"]", e);
		}
		//新增投诉事宜默认新增回呼任务
//		complaintService.autoReturnNotAssigned(complaintId);
		cmpTaskReminderService.addDefaultComplaintTask(complaintId,user);
		return this.execute();
	}
	
	/**
	 * 保存一条投诉事宜
	 * @return
	 */
	public String saveTypeAndAndTask() {
		String compCity = request.getParameter("compCity");
		Integer complaintId = entity.getComplaintId();
		String cmpPerson = request.getParameter("cmpPerson");//投诉人
	    String cmpPhone = request.getParameter("cmpPhone");//投诉人电话
	    String content = request.getParameter("entity.descript");//投诉备注
	    if(StringUtil.isEmpty(content)){
	    	content = Constans.CALL_BACK_DEFAULT_CONTENT;
	    }
	    String callBackTime = request.getParameter("callBackTime");//投诉备注
		try {
			service.insertReasonList(user.getId(), user.getRealName(), complaintId, compCity, getReasonList(),specialEventFlag);
			TaskReminderEntity taskEntity = cmpTaskReminderService.getCallBackInfoFromCmp((ComplaintEntity) complaintService.get(complaintId));
			taskEntity.setAddPerson(user.getRealName());
			taskEntity.setCmpPerson(cmpPerson);
			taskEntity.setCmpPhone(cmpPhone);
			taskEntity.setContent(content);
			taskEntity.setCallBackTime(callBackTime);
			complaintFollowNoteService.addFollowNote(complaintId, user.getId(), user.getRealName(), content,0,Constans.LAUNCH_CALL_BACK);
			cmpTaskReminderService.addTaskReminder(taskEntity);
		} catch (Exception e) {
			logger.error("addReason Exception:[errorMsg:"+e.getMessage()+"]", e);
		}
		return this.execute();
	}
	
	private List<ComplaintReasonEntity> getReasonList() {
		List<ComplaintReasonEntity> list = new ArrayList<ComplaintReasonEntity>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String[] type = request.getParameterValues("type");//选中的一级分类
		if(type != null && type.length > 0) {
			for (int i = 0; i < type.length; i++) {
				paramMap.put("id", type[i]);
				ReasonTypeEntity rtEntity = (ReasonTypeEntity) reasonTypeService.get(paramMap);
				String typeName = rtEntity.getName();
				String[] secondTypeName = request.getParameterValues("secondType[" + type[i] + "]");
				if(secondTypeName != null && secondTypeName.length > 0){
					for (int j = 0; j < secondTypeName.length; j++) {
						ComplaintReasonEntity reason = new ComplaintReasonEntity();
						reason.setOrderId(entity.getOrderId());
						reason.setTypeDescript(entity.getTypeDescript());
						if(entity.getDescript().equals("用以填写其他相关与客人的核实情况（可空）")) {
							reason.setDescript("");
						} else {
							reason.setDescript(entity.getDescript());
						}
						reason.setType(typeName);
						reason.setSecondType(secondTypeName[j]);
						list.add(reason);
					}
				}
			}
		}
		return list;
	}
	
	/*
	 * 删除编辑投诉记录
	 */
	@SuppressWarnings("unchecked")
	public String toEditDeleteHis(){
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");
		ComplaintReasonEntity reasonEnt = (ComplaintReasonEntity) reasonService.get(Integer.valueOf(id));
		if (flag.equals("0")) {
			reasonEnt.setDelFlag(0);
			reasonEnt.setUpdateTime(new Date());
			reasonService.update(reasonEnt);
		} else if (flag.equals("1")) {
			String type = request.getParameter("type");
			String secondType = request.getParameter("secondType");
			String typeDescript = request.getParameter("typeDescript");
			String descript = request.getParameter("descript");
			reasonEnt.setType(type);
			reasonEnt.setSecondType(secondType);
			reasonEnt.setTypeDescript(typeDescript);
			reasonEnt.setDescript(descript);
			reasonEnt.setUpdateTime(new Date());
			reasonService.update(reasonEnt);
		}
		
		Integer compId = reasonEnt.getComplaintId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", compId);
		QcEntity qc = (QcEntity) qcService.get(params);
		if (1 == qc.getConsultation() && 0 == qc.getSpecialConsultation()) { // 如果是咨询单，则再次校验咨询单规则
			List<ComplaintReasonEntity> reasonList = (List<ComplaintReasonEntity>) reasonService.fetchList(params);
			ComplaintEntity compEnt = (ComplaintEntity) complaintService.get(compId);
			Integer isConsultation = ComplaintUtil.judgeConsultation(reasonList, compEnt.getRouteType());
//			if (0 == isConsultation) { // 如果校验结果为非咨询单，则分单并更新质检单
//				qc.setConsultation(isConsultation);
//				qcService.assignQcPerson(qc, compEnt);
//				qcService.update(qc);
//				
//				/* 非咨询单则发起质检到质量管理系统 */
//				QcVo qcVo = new QcVo();
//				qcVo.setPrdId(compEnt.getRouteId());
//				qcVo.setGroupDate(compEnt.getStartTime().getTime());
//				qcVo.setOrdId(compEnt.getOrderId());
//				qcVo.setCmpId(compEnt.getId());
//				qcVo.setQcAffairDesc(getQcAffairDesc(compEnt.getReasonList()));
//				ComplaintRestClient.addQcBill(qcVo);
//			}
		}
		
		String noteContent = Constans.UPDATE_COMPLAINT_REASON;
		complaintFollowNoteService.addFollowNote(compId, user.getId(), user.getRealName(), noteContent, 0, Constans.UPDATE_COMPLAINT_REASON);
		
		return "success";
	}

	private String getQcAffairDesc(List<ComplaintReasonEntity> reasonList) {
		StringBuffer sb = new StringBuffer();
		for (ComplaintReasonEntity r : reasonList) {
			sb.append(r.getType()).append("-").append(r.getSecondType())
			.append("：").append(r.getTypeDescript()).append("<br>");
		}
		return sb.toString();
	}
	
	public String toSearchHis(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String fatherId = request.getParameter("id");
		paramMap.put("fatherId", fatherId);
		List<ReasonTypeEntity> twoTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
		String json = JSONArray.fromObject(twoTypeList).toString();

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
		return "success";
	}
	
	public String getChilderByParentName(){
	    String parentName = request.getParameter("parentTypeName");
	    logger.info("parentName is {}", parentName);
	    List<String>  subTyps = reasonTypeService.getChilderByParentName(parentName);
	    logger.info("subTyps are:{}",subTyps);
	    info.put("secondTypes", subTyps);
	    return "info";
	}

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
	
}
