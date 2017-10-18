package com.tuniu.gt.warning.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.sms.SMSConstans;
import com.tuniu.gt.sms.service.ISmsSendRecordService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DataExportExcel;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.warning.common.EarlyWarningOrderPage;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;
import com.tuniu.gt.warning.service.IEarlyWarningOrderService;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;


@Service("warning_action-ew_order")
@Scope("prototype")
public class EarlyWarningOrderAction extends FrmBaseAction<IEarlyWarningOrderService, EarlyWarningOrderEntity>{

    private static final long serialVersionUID = 6024405611986764006L;
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
	@Qualifier("warning_service_impl-early_warning_order")
	public void setService(IEarlyWarningOrderService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
    private EarlyWarningOrderPage page;
    
    private String[] fileds = new String[]{"orderIdT","routeNameT","destCategoryT","orderTypeT","startDateT","backDateT","startCityT","backCityT","touristNumT","contactNameT","contactPhoneT","agencyT","smsSendRecordT","compLaunchRecordT"};
    
    private String[] ids;
    
    public EarlyWarningOrderPage getPage() {
		return page;
	}

	public void setPage(EarlyWarningOrderPage page) {
		this.page = page;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getFileds() {
		return fileds;
	}

	public void setFileds(String[] fileds) {
		this.fileds = fileds;
	}

	private Map<String, Object> paramMap = new HashMap<String, Object>();

	public EarlyWarningOrderAction() {
		setManageUrl("ew_order");
	}
	
    @Autowired
    @Qualifier("smsSendRecord_service_smsSendRecord_impl-sms_send_record")
    private ISmsSendRecordService smsSendRecordService;
    
	public String execute() {
		service.getEwoPage(page);
		request.setAttribute("fieldStr", CommonUtil.arrToStr(fileds));
		return "ew_order_list";
	}
	
	public String sendSMS() {
		List<EarlyWarningOrderEntity> orderList = new ArrayList<EarlyWarningOrderEntity>();
		if ("searched".equals(page.getSendScope())) {
			page.setStart(-1);
			orderList = (List<EarlyWarningOrderEntity>) service.getEwoList(page);
		} else if ("checked".equals(page.getSendScope())) {
			EarlyWarningOrderPage ewoPage2 = new EarlyWarningOrderPage();
			ewoPage2.setStart(-1);
			ewoPage2.setIdsStr(CommonUtil.arrToStr(ids));
			orderList = (List<EarlyWarningOrderEntity>) service.getEwoList(ewoPage2);
		}
		String smsContent = page.getSmsContent();
		paramMap.clear();
		paramMap.put("timeBegin", DateUtil.getSomeDay(new Date(), -2));
		paramMap.put("content", smsContent);
		List<String> arSendList = smsSendRecordService.getAlreadySendNos(paramMap); // 获取两天内发送过该内容的手机号
		int succNum = 0;
		for (EarlyWarningOrderEntity ewOrder : orderList) {
			String phoneNum = ewOrder.getContactPhone();
			if (null != phoneNum && !"".equals(phoneNum)) {
				if (null != arSendList) {
					if (!arSendList.contains(phoneNum)) { // 判断两天内是否发送过重复短信（相同手机号48小时内不允许发送相同短信）
						// 2代表预警短信，TODO 【待申请task_id】
						succNum += smsSendRecordService.sendMessages(phoneNum, ewOrder.getId(), smsContent, user.getId().toString(), SMSConstans.CUST_SERVICE_TASK_ID, 2);
					}
				} else {
					succNum += smsSendRecordService.sendMessages(phoneNum, ewOrder.getId(), smsContent, user.getId().toString(), SMSConstans.CUST_SERVICE_TASK_ID, 2);
				}
			}
		}
		return writeResponse(succNum);
	}
	
	@SuppressWarnings("unchecked")
	public String launchComplaint() {
		List<EarlyWarningOrderEntity> orderList = new ArrayList<EarlyWarningOrderEntity>();
		if ("searched".equals(page.getLaunchScope())) {
			page.setStart(-1);
			orderList = (List<EarlyWarningOrderEntity>) service.getEwoList(page);
		} else if ("checked".equals(page.getLaunchScope())) {
			EarlyWarningOrderPage ewoPage2 = new EarlyWarningOrderPage();
			ewoPage2.setStart(-1);
			ewoPage2.setIdsStr(CommonUtil.arrToStr(ids));
			orderList = (List<EarlyWarningOrderEntity>) service.getEwoList(ewoPage2);
		}
		int succNum = 0;
		int ownerId = user.getId();
		String ownerName = user.getRealName();
		int level = Integer.parseInt(page.getLevel());
		for (EarlyWarningOrderEntity ewOrder : orderList) {
			int compId = 0;
			Map<String, Object> params = new HashMap<String, Object>();
			int orderId = ewOrder.getOrderId();
			params.put("orderId", String.valueOf(orderId));
			params.put("state", "1,2,5,7");
			List<ComplaintEntity> complaintList = (List<ComplaintEntity>) complaintService.fetchList(params);
			int addReasonFlag = 0;
			if (null != complaintList && complaintList.size() > 0) {
				ComplaintEntity compEnt = complaintList.get(0);
				if (!("出游中".equals(compEnt.getOrderState()) && Constans.SPECIAL_BEFORE_TRAVEL.equals(compEnt.getDealDepart()))) {
					addReasonFlag = 1; // 新增投诉事宜
					compId = compEnt.getId();
					try {
						reasonService.insertReasonList(ownerId, ownerName, compId, "", getReasonList(orderId),-1);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("addReason Exception:[errorMsg:"+e.getMessage()+"]", e);
					}
					compEnt.setWarningFlag(1);
					if (level < compEnt.getLevel()) {
						compEnt.setLevel(level);
					}
					complaintService.update(compEnt);
				}
			}
			
			if (0 == addReasonFlag) {
				try {
					ComplaintEntity entity = complaintService.getOrderInfoMain(String.valueOf(orderId));
					entity.setComeFrom("其他");
					entity.setLevel(level);
					entity.setOwnerId(ownerId);
					entity.setOwnerName(ownerName);
					entity.setWarningFlag(1);
					entity.setReasonList(getReasonList(orderId));
					complaintService.insertComplaint(entity);
					compId = entity.getId();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					return writeResponse(succNum);
				}
			}
			
			ewOrder.setComplaintId(compId);
			service.update(ewOrder);
			succNum++;
		}
		return writeResponse(succNum);
	}
	
	private List<ComplaintReasonEntity> getReasonList(Integer orderId) {
		List<ComplaintReasonEntity> reasonList = new ArrayList<ComplaintReasonEntity>();
		
		ComplaintReasonEntity reason = new ComplaintReasonEntity();
		reason.setType("其他");
		reason.setSecondType("不可抗力");
		reason.setTypeDescript(page.getCompContent());
		reason.setDescript(page.getCompRemark());
		reason.setOrderId(orderId);
		reasonList.add(reason);
		
		return reasonList;
	}
	
	public String toggleEwo() {
		service.toggleEwo(CommonUtil.arrToStr(ids), page.getToggleFlag());
		return writeResponse(0);
	}
	
	private String writeResponse(Object data) {
		HttpServletResponse response = ServletActionContext.getResponse(); // 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
		response.setContentType("text/plain"); // 设置输出为文字流
		response.setCharacterEncoding("UTF-8"); // 设置字符集
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(data);
		} catch (IOException e) {
			return "error";
		} finally {
			out.flush();
			out.close();
		}
		return "ew_order_list";
	}
	
	public String exportPool(){
		List<List<Object>> data = new ArrayList<List<Object>>();
		List<Object> listColumn = new ArrayList<Object>();
		HttpServletResponse response = ServletActionContext.getResponse();
		listColumn.add("订单号");
		listColumn.add("出游日期");
		listColumn.add("归来日期");
		listColumn.add("出发地");
		listColumn.add("目的地");
		listColumn.add("供应商");
		listColumn.add("航班号");
		listColumn.add("线路编号");
		listColumn.add("预警类型");
		listColumn.add("预警等级");
		listColumn.add("预警日期");
		listColumn.add("对应措施");
		data.add(listColumn);// 设置标题
//		if(page == null){
//			page = new Page();
//			page.setPageSize(10);
//		}
		if (paramMap == null){
			paramMap = new HashMap<String, Object>();
			Map<String, String> search = Common.getSqlMap(getRequestParam(),
					"warning.");
			paramMap.putAll(search);
//			paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
//			paramMap.put("dataLimitEnd", page.getPageSize());
		}
//		List<Map> warningPoolList = service.queryPoolList(paramMap);
		List<Map> warningPoolList = null;
		for (Map map : warningPoolList) {
			List<Object> listData = new ArrayList<Object>();
			listData.add(map.get("orderId"));
			listData.add(map.get("startTime"));
			listData.add(map.get("backTime"));
			listData.add(map.get("startCity"));
			listData.add(map.get("backCity"));
			listData.add(map.get("agencyName"));
			listData.add(map.get("startPlane"));
			listData.add(map.get("routeId"));
			listData.add(map.get("ewType"));
			listData.add(map.get("ewLevel"));
			listData.add(map.get("ewTime"));
			listData.add(map.get("ewPlan"));
			data.add(listData);
		}
		String filename = "list"
				+ (new java.text.SimpleDateFormat("yyyyMMddHHmmss"))
						.format(new Date()) + ".xls";
		DataExportExcel dee = new DataExportExcel(data, filename, "", response);
		dee.createExcelFromList();
		String res = "pool_list";
		return res;
	}
	
}
