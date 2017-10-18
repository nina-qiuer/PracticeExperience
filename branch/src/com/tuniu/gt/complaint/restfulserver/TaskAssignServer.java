package com.tuniu.gt.complaint.restfulserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.toolkit.Rtx;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestServer;

import net.sf.json.JSONObject;
import tuniu.frm.core.webservice.WebServiceClientBase;
import tuniu.frm.service.Constant;

@Controller
@RequestMapping("/")
public class TaskAssignServer {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private static WebServiceClientBase xBase = new WebServiceClientBase();
	@Autowired
	@Qualifier("complaint_service_complaint_impl-auto_assign")
	private IAutoAssignService autoAssignService;
	
	// 用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;

	public static String MEM_PRE_KNOWLEDGE = "knowledge_";
	/** 用于计数器 */
	private static final String INCRVALUE = "1";

	@RequestMapping(value = "/taskAssign", method = RequestMethod.GET)
	public void taskAssign(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		TRestServer server = new TRestServer(request, response);
		String restData = "";
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			restData = server.getRestData();
			JSONObject jsonObject = JSONObject.fromObject(restData);
			System.out.println(jsonObject.toString());
			String identity = jsonObject.getString("identity");
			// String productLineId=jsonObject.getString("productLineId");
			String callbackUrl = jsonObject.getString("callbackUrl");
			String taskName = jsonObject.getString("taskName");
			AutoAssignEntity dealPeople = null;
			List<AutoAssignEntity> knowledgeList = autoAssignService
					.getListByType(AutoAssignEntity.ONLINE_QUESTION);
			Map<String, Integer> nums = new HashMap<String, Integer>();
			// 查询当前处理岗下 所有客服的 单子数
			for (AutoAssignEntity entity : knowledgeList) {
				String key = MEM_PRE_KNOWLEDGE + entity.getUserId();
				Long value = 0L;
				String strValue = MemcachesUtil.get(key);
				if (StringUtils.isNotBlank(strValue)) {
					value = Long.parseLong(strValue);
				}
				entity.setListNums(value);
			}
			// 按照处理单数排序
			Collections.sort(knowledgeList, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Comparable) ((AutoAssignEntity) o1).getListNums())
							.compareTo(((AutoAssignEntity) o2).getListNums());
				}
			});

			for (AutoAssignEntity entity : knowledgeList) {
				if (CommonUtil.isStatus(userService.getUserByID(entity.getUserId()).getExtension())) {
					dealPeople = entity;
					break;
				}
			}

			if (dealPeople != null && StringUtils.isNotBlank(callbackUrl)) {
				// 回到在线问答处理接口
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("askId", identity);
				paramMap.put("salerId", dealPeople.getUserId());
				if (callbackAskUpdate(paramMap, callbackUrl)) {
					returnMap.put("success", true);
					returnMap.put("isHandled", true);
					recordUserNums(dealPeople, MEM_PRE_KNOWLEDGE);
					if (Constant.CONFIG.getProperty("sendFlag").equals("1")) {
						Rtx.sendSingleRtx(
								dealPeople.getUserId(),
								dealPeople.getUserName(),
								"您有新的在线咨询需要回复 ",
								"====*问题更新通知*====\r\n编号："
										+ identity
										+ "\r\n[http://crm.tuniu.com/main.php?do=my_knowledge]\r\n");
					}
				} else {
					returnMap.put("success", false);
				}
			} else {
				returnMap.put("success", true);
				returnMap.put("isHandled", false);
			}

		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
		} finally {
			write(response,
					Base64.encodeBase64String(JSONObject.fromObject(returnMap)
							.toString().getBytes("utf-8")));
		}
	}

	/**
	 * 回调boss通用知识分配处理人
	 * 
	 * @param paramMap
	 * @param url
	 * @return
	 */
	public boolean callbackAskUpdate(Map<String, Object> paramMap, String url) {
		boolean result = false;
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("get");
		trestClient.setData(JSONObject.fromObject(paramMap).toString());
		String execute;
		try {
			// String base64Data=JSONObject.fromObject(paramMap).toString();
			// String binaryData=
			// Base64.encodeBase64String(base64Data.getBytes("utf-8"));
			execute = trestClient.execute();
			System.out.println(execute);
			JSONObject fromObject = JSONObject.fromObject(execute);
			Object retStatusObj = fromObject.get("errorCode");

			if (null != retStatusObj
					&& retStatusObj.toString().equals("130000")) {
				result = true;
			} else {
				result = false;
			}
		} catch (TRestException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * 记录每个客服当天的单数
	 */
	private void recordUserNums(AutoAssignEntity dealCustom, String memPre) {
		// 计数处理
		String key = memPre + dealCustom.getUserId();
		System.out.println(key);
		if (StringUtils.isNotBlank(MemcachesUtil.get(key))) {
			// 如果已经存在，账号计数器加1
			MemcachesUtil.increment(key, new Long(INCRVALUE));
		} else {
			Calendar calendar = new GregorianCalendar();
			Long curMills = System.currentTimeMillis();
			calendar.setTime(new Date(curMills));
			calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			// Calendar newCalendar=new GregorianCalendar();
			// newCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			// newCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
			// newCalendar.set(Calendar.DAY_OF_MONTH,
			// calendar.get(Calendar.DAY_OF_MONTH));
			// newCalendar.getTime();
			System.out.println("expire: " + calendar.getTime());

			Long expire = calendar.getTimeInMillis() - curMills;
			MemcachesUtil.set(key, INCRVALUE, expire);
		}
	}

	/**
	 * 接口返回值写入输出流
	 * 
	 * @param response
	 * @param responseStr
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

}
