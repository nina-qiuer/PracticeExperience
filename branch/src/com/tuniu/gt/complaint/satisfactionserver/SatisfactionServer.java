package com.tuniu.gt.complaint.satisfactionserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionMsgReplyEntity;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionMsgReplyService;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionService;
import com.tuniu.operation.platform.annotation.MethodRestful;
import com.tuniu.operation.platform.annotation.ParamRestful;
import com.tuniu.operation.platform.annotation.ReturnRestful;
import com.tuniu.operation.platform.annotation.TypeRestful;
import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestServer;

import net.sf.json.JSONObject;

@TypeRestful(desc = "短信回复更新满意度", author = "韩旭亮", version = "1.0")
@Controller
@RequestMapping("/satisfaction")
public class SatisfactionServer {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private static final String FCMY = "非常满意";
	private static final String MY = "满意";
	private static final String YB = "一般";
	private static final String BMY = "不满意";

	// 引入门市/上门签约service
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-signSatisfaction")
	private ISignSatisfactionService signSatisfactionService;

	// 引入短信回复记录service
		@Autowired
		@Qualifier("satisfaction_service_satisfaction_impl-signSatisfactionMsgReply")
		private ISignSatisfactionMsgReplyService signSatisfactionMsgReplyService;
	
		private JSONObject formatJsonObject(String restData){
			JSONObject jsonObject = null;
			if(restData!=null){
				String jsonStr = restData.replaceAll("\\\\\"", "\"");
				jsonStr = jsonStr.replaceAll("data\":\"", "data\":");
				jsonStr = jsonStr.replaceAll("\"}\"", "\"}");
				System.out.println(jsonStr);
				jsonObject = JSONObject.fromObject(jsonStr);
			}
			return jsonObject;
		}
		
		private boolean isNum(String msg){
			msg = msg.replace(" ", "");
			boolean flag = true;
			String firstChar = msg.substring(0,1);
			try{
				Integer num = Integer.parseInt(firstChar);
			}catch(Exception e){
				flag = false;
			}
			return flag;
		}
		
		private String calSocreByNum(String msg){
			msg = msg.replace(" ", "");
			float value = 0;
			String result = null;
			boolean flag = false;
			String socre = msg.substring(0,1);
			if ("1".equals(socre)) {
				value = 3;
				flag = true;
			} else if ("2".equals(socre)) {
				value = 2;
				flag = true;
			} else if ("3".equals(socre)) {
				value = 1;
				flag = true;
			} else if ("4".equals(socre)) {
				value = 0;
				flag = true;
			} 
			if(flag){
				result = Math.round(value/3*100)+"%";
			}
			return result;
		}
		
		private String calSocreByStr(String msg){
			msg = msg.replace(" ", "");
			float value = 0;
			String result = null;
			boolean flag = false;
			if(msg.indexOf(FCMY)>=0){
				value = 3;
				flag = true;
			}else if (msg.indexOf(BMY)>=0) {
				value = 0;
				flag = true;
			} else if (msg.indexOf(MY)>=0) {
				value = 2;
				flag = true;
			} else if (msg.indexOf(YB)>=0) {
				value = 1;
				flag = true;
			} 
			if(flag){
				result = Math.round(value/3*100)+"%";
			}
			return result;
		}
		
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@MethodRestful(mapping = "/satisfactionserver/satisfaction", name = "updateSatisfaction", method = "post", desc = "更新满意度数值", params = {
			@ParamRestful(type = "string", name = "tel", desc = "电话号码"),
			@ParamRestful(type = "string", name = "smsContent", desc = "短信内容") }, returns = @ReturnRestful(type = "json", desc = "{\"data\":\"{\"result\":true,\"errormsg\"\":\"errormsg\"}\"}"), example = "")
	@RequestMapping(value="/satisfactionserver/satisfaction",method = RequestMethod.POST)
	public void updateSatisfaction(HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("-------------------------------------SatisfactionServer updateSatisfaction begin--------------------------------------------------------------------");
		// 访问路径http://localhost:8080/ssi/satisfactionserver/satisfaction
		TRestServer server = new TRestServer(request, response);
		String restData = "";
		String responseStr = "";
		try {
			restData = server.getRestData();
			System.out.println("-------------------------------------restData =--------------------------------------------------------------------");
			System.out.println(restData);
			//restData ="{\"md5\":\"6b63b2aba01e56b5ed9534f4a2b25210\",\"data\":\"{\\\"smsContent\\\":\\\"1\\\",\\\"tel\\\":\\\"13451818824\\\",\\\"receiveTime\\\":\\\"2014-03-25 09:18:52\\\",\\\"extendCode\\\":\\\"58\\\"}\"}";
			//System.out.println(restData);
			
			JSONObject jsonObject = formatJsonObject(restData);
			JSONObject jsonData = jsonObject.getJSONObject("data");
			String telNo = jsonData.getString("tel");
			String msg = jsonData.getString("smsContent");
			SignSatisfactionEntity signSatisfactionEntity = signSatisfactionService.getLastestEntityByTelNo(telNo);
			if(signSatisfactionEntity!=null){
				insertMsgReplyRecord(telNo, msg , signSatisfactionEntity.getId());
				String socre = null;
				if(isNum(msg)){
					socre = calSocreByNum(msg);
				}else{
					socre = calSocreByStr(msg);
				}
				if(socre!=null){
					signSatisfactionEntity.setFaceSaleSatisfaction(socre);
					signSatisfactionEntity.setLastModifyTime(new Date());
					signSatisfactionService.update(signSatisfactionEntity);
				}
				responseStr = generateResultString(true,"");
			}else{
				insertMsgReplyRecord(telNo, msg , 0 );
				responseStr = generateResultString(false,"satisfaction record is not found!");
			}
		} catch (TRestException e) {
			responseStr = generateResultString(false,e.getMessage());
			logger.error(e.getMessage(), e);
		}finally{
			write(response, responseStr);
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
	
	/**
	 * 生成接口输出字符串
	 * 
	 * @param msg
	 * @param errorCode
	 * @param data
	 * @return
	 */
	private String generateResultString(boolean flag, String msg) {
		String resStr = "";
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		//flag=true表示执行成功
		paraMap.put("result", flag);
		paraMap.put("errormsg", msg);
		returnMap.put("data", paraMap);
		try {
			resStr = Base64.encodeBase64String(JSONObject.fromObject(returnMap).toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		//resStr = JSONObject.fromObject(returnMap).toString();
		System.out.println(resStr);
		return resStr;
	}
	
	/**
	 * 保存短信回复记录
	 * @param telNo 手机号码
	 * @param msg 短信内容
	 * @param updateOrderId 更新的订单编号
	 */
	private void insertMsgReplyRecord(String telNo, String msg, int satisId){
		SignSatisfactionMsgReplyEntity signSatisfactionMsgReplyEntity = new SignSatisfactionMsgReplyEntity();
		signSatisfactionMsgReplyEntity.setTelNo(telNo);
		signSatisfactionMsgReplyEntity.setMsg(msg);
		signSatisfactionMsgReplyEntity.setCreateTime(new Date());
		signSatisfactionMsgReplyEntity.setUpdateSatisId(satisId);
		signSatisfactionMsgReplyService.insert(signSatisfactionMsgReplyEntity);
	}
}
