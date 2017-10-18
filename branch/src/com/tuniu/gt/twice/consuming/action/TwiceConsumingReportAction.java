package com.tuniu.gt.twice.consuming.action;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.satisfaction.service.IPreSaleSatisfactionSocreService;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;
import tuniu.frm.service.Constant;

@Service("complaint_action-twiceConsumingReport")
@Scope("prototype")
public class TwiceConsumingReportAction extends
		FrmBaseAction<IComplaintService, ComplaintEntity> {
	
	private Logger logger = Logger.getLogger(getClass());

	public TwiceConsumingReportAction() {
		setManageUrl("twiceConsumingReport");
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	public void setService(IComplaintService service) {
		this.service = service;
	};
	
	/**
	 * 返回主页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String execute() {
	    //投诉处理列表每页显示个数为30
        this.perPage = 30;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap = paramSearch();
		// 调用父类方法返回页面列表
		if(paramMap.size()>0){
			String buildDateBegin = (String) paramMap.get("startBuildDate");
			String buildDateEnd = (String) paramMap.get("endBuildDate");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("buildDateBegin", buildDateBegin);
			map.put("buildDateEnd", buildDateEnd);
			List<String> getDatatList = service.getComplaintEntityListByBuildDateAndState(map);
			if(getDatatList.size()>0){
				//int complaintCustTotalCount = getDatatList.size();
				String orderIds = "";
				for(String orderId : getDatatList){
					orderIds += orderId+",";
				}
				orderIds = orderIds.substring(0, orderIds.length()-1);
				System.out.println(orderIds);
				String addedTimeBegin = (String) paramMap.get("twiceConsumingDateBegin");
				String addedTimeEnd = (String) paramMap.get("twiceConsumingDateEnd");
				
				JSONObject result = null;
				//String url = "http://crm.dev.tuniu.org/interface/rest/server/order/OrderInfoQueryInterface.php";
				String url = Constant.CONFIG.getProperty("COMPLAINT_TWICE_CONSUMING");
				TRestClient trestClient = new TRestClient(url);
				trestClient.setMethod("post");
				Map<String, Object> mp = new HashMap<String, Object>();
				mp.put("func", "getOrderCountAndPeopleNum");
				Map<String, String> routeMp = new HashMap<String, String>();
				routeMp.put("orderIds", orderIds);
				routeMp.put("addedTimeBegin", addedTimeBegin);
				routeMp.put("addedTimeEnd", addedTimeEnd);
				mp.put("params", routeMp);
				trestClient.setData(JSONObject.fromObject(mp).toString());
				int normalOrderNum = 0;
				int ticketOrderNum = 0;
				int normalCustNum = 0;
				int ticketCustNum = 0;
				try {
					String execute = trestClient.execute();
					JSONObject jsonObject = JSONObject.fromObject(execute);
					Boolean isSuccess = jsonObject.getBoolean("success");
					if(isSuccess){
						result = jsonObject.getJSONObject("data");
						int complaintCustTotalCount=result.getInt("complaintCustTotalCount");
						JSONArray jsonArray = result.getJSONArray("data");
						for(int i=0;i<jsonArray.size();i++){
							JSONObject obj = (JSONObject) jsonArray.get(i);
							String agencyType = obj.getString("agencyType");
							String orderCount = obj.getString("order_count");
							String peopleNum = obj.getString("people_num");
							if("9".equals(agencyType)||"10".equals(agencyType)||"11".equals(agencyType)){
								ticketOrderNum = ticketOrderNum + Integer.parseInt(orderCount);
								ticketCustNum = ticketCustNum + Integer.parseInt(peopleNum);
							}else{
								normalOrderNum = normalOrderNum + Integer.parseInt(orderCount);
								normalCustNum = normalCustNum + Integer.parseInt(peopleNum);
							}
						}
						
						NumberFormat nf = NumberFormat.getPercentInstance(); 
						nf.setMinimumFractionDigits(2);
						float num =Float.parseFloat(String.valueOf(normalOrderNum))/complaintCustTotalCount;
						String normalPercent = nf.format(num);
						num =Float.parseFloat(String.valueOf(ticketOrderNum))/complaintCustTotalCount;
						String ticketPercent = nf.format(num);
						num =Float.parseFloat(String.valueOf(normalOrderNum+ticketOrderNum))/complaintCustTotalCount;
						String totalPercent = nf.format(num);
						
						request.setAttribute("complaintCustTotalCount", String.valueOf(complaintCustTotalCount));
						request.setAttribute("custTwiceConsumingCountNormal", String.valueOf(normalOrderNum));
						request.setAttribute("custTwiceConsumingCountTicket", String.valueOf(ticketOrderNum));
						request.setAttribute("custTwiceConsumingCountTotal", String.valueOf(normalOrderNum+ticketOrderNum));
						request.setAttribute("percentNormal", String.valueOf(normalPercent));
						request.setAttribute("percentTicket", String.valueOf(ticketPercent));
						request.setAttribute("percentTotal", String.valueOf(totalPercent));
						request.setAttribute("peopleNumNormal", String.valueOf(normalCustNum));
						request.setAttribute("peopleNumTicket", String.valueOf(ticketCustNum));
						request.setAttribute("peopleNumTotal", String.valueOf(normalCustNum+ticketCustNum));
						
					}
				} catch (TRestException e) {
					logger.error(e.getMessage(), e);
				}
			}
			
		}
		request.setAttribute("pageTitle", "老客户消费率");
		String res = "twice_consuming_report_list";
		return res;
	}
	
	
	
	
	//用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	
	
	//售前客服满意度评分service
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-preSaleSatisfactionSocre")
	private IPreSaleSatisfactionSocreService preSaleSatisfactionSocreService;
	
	private List<UserEntity> productManagers = new ArrayList<UserEntity>(); // 产品经理列表
	
	
	
	/**
	 * 封装查询条件
	 * @return
	 */
	public Map<String, Object> paramSearch(){
		
				
		Map<String, Object> paramMap = new HashMap<String, Object>();  //search使用的map
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		if(paramMap.size()>0){
			search.put("state", "4");
		}
		paramMap.putAll(search);// 放入search内容
		
		request.setAttribute("search", search);
		
		return paramMap;
	}
	
	
}
