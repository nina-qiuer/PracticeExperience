package com.tuniu.gt.frm.webservice.uc;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import tuniu.frm.core.webservice.WebServiceBase;
import tuniu.frm.service.PHPSerializer;

public class XmlrpcBase extends WebServiceBase {
	
	private Logger logger = Logger.getLogger(getClass());
	
	public Message sendMsg = new Message();
	public Message recvMsg = new Message(); 
	public Map<String, Object> errMap = new HashMap<String, Object>();
	public Map<String, Object> xmlrpcMap = (Map<String, Object>)settings.get("uc_xmlrpc");
	
	public String  encodeMsg() throws UnsupportedEncodingException {
		

		sendMsg.data = URLEncoder.encode(new String(PHPSerializer.serialize(sendMsg.data)),"UTF-8");
		sendMsg.extraData = URLEncoder.encode(new String(PHPSerializer.serialize(sendMsg.extraData)),"UTF-8");
		Map<String, Object> tmpMap = msg2map(sendMsg);
		JSONObject obj  = JSONObject.fromObject(tmpMap);
		return obj.toString();
	}
	
	
	protected Map<String, Object> msg2map(Message msg) {
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		
		tmpMap.put("type", msg.type);
		tmpMap.put("data", msg.data);
		tmpMap.put("extra_data", msg.extraData); 
		tmpMap.put("code", msg.code);
		return tmpMap;
	}

	public Object decodeMsg(String encodeMsg) throws IllegalAccessException, UnsupportedEncodingException {
		Message msg = new Message();
		try{
			JSONObject jsonObject = JSONObject.fromObject(encodeMsg);
		
			if(!jsonObject.containsKey("data")) {
				return jsonObject.toString();
			} else {
				msg.type = jsonObject.get("type").toString();
				msg.data = PHPSerializer.unserialize(URLDecoder.decode(jsonObject.getString("data"),"UTF-8").getBytes("UTF-8"), "UTF-8");
				if(jsonObject.containsKey("extra_data")) {
					msg.extraData = PHPSerializer.unserialize(URLDecoder.decode(jsonObject.getString("extra_data"),"UTF-8").getBytes("UTF-8"), "UTF-8");
				} else {
					msg.extraData = null;
				}
				
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return msg;
		
		
	}
	
	/**
	 * 获取本机IP
	 *
	 * @return 返回IP地址
	 * @throws UnknownHostException 
	 */
	protected  String myIp() throws UnknownHostException{
		return InetAddress.getLocalHost().getHostAddress();
	 }
	 
	 public  Boolean checkClient(String msg) {
//		return true;
		Object o = null; 
		try {
			o = decodeMsg(msg);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} 
		
		if(o instanceof String) {
			return false;
		} else {
			recvMsg = (Message)o;
		}
		

//	 	if($this->settings['allow_all']) { //不检查来源
//	 		return true;
//	 	} else { 
//		 	if(!$this->recv_msg->src->client_id) {
//		 		$this->setErrMsg(NO_CLIENT);
//		 		return false;
//		 	} 
//		 	$conf_client_info = $this->settings['allow_client_list'][$this->recv_msg->src->client_id];
//		 	 if(!$conf_client_info) {
//		 		$this->setErrMsg(CLIENT_NOT_ALLOW);
//		 		return false;
//		 	} else {
//		 		if($conf_client_info['client_key']  != $this->recv_msg->src->client_key) {
//		 			$this->setErrMsg(KEY_INCORRECT);
//		 			return false;
//		 		}
//		 	}
//	 	}
	 	return true; 
	 }
	 
	 protected void setErrMsg(Object errCode) {
	 	sendMsg.type = "ERROR";
	 	sendMsg.code = errCode;
	 	sendMsg.data = "ERROR";
	 
	 }
	 
	 protected void setMsg(Object data) {
		 sendMsg.type = "SUCCESS";
		 sendMsg.data = data;
	 }

	 /*
		客户端填充数据使用
	 */
	 protected Object fillMsg(Object data,Object  extraData) throws UnsupportedEncodingException { 
	 	sendMsg.type = "SUCCESS";
	 	sendMsg.data = data;
	 	sendMsg.extraData = extraData;
	 	sendMsg.src.clientId =  Integer.parseInt(xmlrpcMap.get("client_id").toString());
	 	sendMsg.src.clientKey = xmlrpcMap.get("client_key").toString();
	 	return encodeMsg();
	 }
	
}


class Message {
	public String type; //消息类型  SUCCESS 成功 ERROR 失败
	public Object code; //消息代码  任意类型 
	public Object data; //数据;
	public Object extraData;//额外数据
	public Client src; //源客户端
	public Client des;//求请的客户端
}


class Client {
	public String clientKey;
	public Integer clientId;
	public String file;
	public String method;
}