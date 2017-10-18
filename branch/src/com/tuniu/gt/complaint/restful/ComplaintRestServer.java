package com.tuniu.gt.complaint.restful;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;

import tuniu.frm.service.Bean;
import tuniu.frm.service.Constant;

/**
 * 
 * @author weiweiqin
 * 
 */
public class ComplaintRestServer implements RestServerHandlerInterface {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private IComplaintService complaintService = (IComplaintService) Bean
			.get("complaint_service_complaint_impl-complaint");

	private IUserService userService = (IUserService) Bean
			.get("frm_service_system_impl-user");

	/**
	 * 通过订单id获取投诉信息
	 * 
	 * @param orderId
	 *            订单id
	 * @return 投诉信息
	 */
	public List<Map<String, String>> queryComplaintByOrderId(String orderId) {
		List<ComplaintEntity> compList = complaintService
				.getComplaintsByOrderId(orderId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		String url = Constant.CONFIG.getProperty("COMPLAINT_DETAIL_URL");
		List<Map<String, String>> mpList = new ArrayList<Map<String, String>>();
		Map<String, String> mp = null;
		for (ComplaintEntity comp : compList) {
			mp = new HashMap<String, String>();
			mp.put("id", comp.getId().toString());
			mp.put("url", url + comp.getId());
			String formatBuildDate = sdf.format(comp.getBuildDate());
			mp.put("complaintDate", formatBuildDate);
			mp.put("orderState", comp.getOrderState());
			mp.put("dealDepart", comp.getDealDepart());
			mp.put("comeFrom", comp.getComeFrom());
			mp.put("level", comp.getLevel().toString());
			UserEntity user = userService.getUserByID(comp.getDeal());
			if (null != user) {
				mp.put("deal", user.getRealName());
			} else {
				mp.put("deal", "");
			}
			mp.put("state", comp.getState().toString());
			mpList.add(mp);
		}
		return mpList;
	}

	public Map<String, Object> queryComplaintByOrderIds(String orderIds) {
		
		Map<String, Object> reMap = new HashMap<String, Object>();
		
		if (null == orderIds || "".equals(orderIds)) {
			return reMap;
		}
		
		try {
			
			List<Map<String, Object>> complaintInfoList =  complaintService.getComplaintInfoByOrderIds(orderIds);
			
			if (null != complaintInfoList) {
				for (Map<String, Object> map : complaintInfoList) {
					reMap.put(String.valueOf(map.get("order_id")), map);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return reMap;
	}
	
	public Object execute(String data) {
		Map<String, String> params = RestDataParse.getParamsMpByClientData(data);
		String method = RestDataParse.getMethodByClientData(data);
		String[] split = method.split("\\.");
		if("queryComplaintByOrderId".equals(split[1])){
			return queryComplaintByOrderId(params.get("orderId"));
		} else if ("queryComplaintByOrderIds".equals(split[1])) {
			return queryComplaintByOrderIds(params.get("orderIds"));
		}
		return null;
		
	}

}
