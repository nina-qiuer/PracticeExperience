package com.tuniu.gt.toolkit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tuniu.frm.core.webservice.WebServiceClientBase;
import tuniu.frm.service.Constant;

import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;

public class Rtx {

	private final static Logger logger = LoggerFactory.getLogger(Rtx.class);

	/**
	 * 发送单人rtx提醒
	 * 
	 * @param userId
	 *            接收信息人id
	 * @param userName
	 *            接收信息人姓名拼音
	 * @param title
	 *            rtx提醒标题
	 * @param content
	 *            rtx提醒内容
	 * @return boolean 发送成功-true 发送失败-false
	 */
	public static synchronized Boolean sendSingleRtx(int userId, String userName, String title,
			String content) {

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put(userId + "", userName);
		Boolean flag = Rtx.postRtx(userMap, title, content);
		return flag;
	}

	/**
	 * 发送多人rtx提醒
	 * 
	 * @param Map
	 *            <Integer, String> 接收信息人 key-接收信息人id value-接收信息人姓名拼音
	 * @param title
	 *            rtx提醒标题
	 * @param content
	 *            rtx提醒内容
	 * @return boolean 发送成功-true 发送失败-false
	 */
	public static Boolean sendMultiRtx(Map<Integer, String> map, String title,
			String content) {

		Map<String, Object> userMap = new HashMap<String, Object>();

		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			userMap.put(entry.getKey() + "", entry.getValue());
		}

		Boolean flag = Rtx.postRtx(userMap, title, content);
		return flag;
	}

	/**
	 * 发送rtx提醒
	 * 
	 * @param userMap
	 * @param title
	 * @param content
	 * @return
	 */
	private static Boolean postRtx(Map<String, Object> userMap, String title,
			String content) {
		
		Boolean flag = false;
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("title", title);
		mp.put("order_id", 0);
		mp.put("from_email", "rtx@tuniu.com");
		mp.put("from_name", "rtxSys");
		mp.put("content", content);
		mp.put("step_id", 46);
		Iterator<Map.Entry<String, Object>> it = userMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			Integer userId = Integer.parseInt(entry.getKey());
			mp.put("uid", userId);
			String result = sendRTXMessage(mp);
			if(result==null){
				logger.error("---------------RTX发送失败：接口返回结果为null, id="+userId+"&name="+entry.getValue()+"---------------");
			}else{
				JSONObject obj = JSONObject.fromObject(result);
				if(!obj.getString("code").equals("0")){
					logger.error("---------------RTX发送失败：id="+userId+"&name="+entry.getValue()+", 错误信息: "+result+"---------------");
				}else{
					logger.info("---------------RTX发送成功：id="+userId+"&name="+entry.getValue()+"---------------");
					flag = true;
				}
			}
		}
		return flag;
	}

	private static String sendRTXMessage(Map<String, Object> map){
		String url = Constant.CONFIG.getProperty("NEW_RTX_URL");
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("post");
		trestClient.setData(JSONObject.fromObject(map).toString());
		String execute = null;
		try {
			execute = trestClient.execute();
		} catch (TRestException e) {
			logger.error("---------------调用NEW_RTX_URL错误----------------", e);
		}
		return execute;
	}
}
