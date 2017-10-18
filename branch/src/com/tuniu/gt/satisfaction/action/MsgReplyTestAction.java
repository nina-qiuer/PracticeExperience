package com.tuniu.gt.satisfaction.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionEntity;
import com.tuniu.gt.satisfaction.service.IPreSaleSatisfactionService;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;

@Service("complaint_action-msgReplyTest")
@Scope("prototype")
public class MsgReplyTestAction extends
FrmBaseAction<IPreSaleSatisfactionService, PreSaleSatisfactionEntity> {

	
	/**
	 * 返回主页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String execute() {
		String res = "msg_reply_test";
		return res;
	}
	
	public String insertRecord() throws TRestException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String telNo = request.getParameter("tel");
		String msg = request.getParameter("msg");
		String receiveTime = new Date().toString();
		String str = "{\"md5\":\"dac3e29530b2f607a763aae585482a21\",\"data\":{\"smsContent\":"+msg+",\"tel\":"+telNo+",\"receiveTime\":\""+receiveTime+"\",\"extendCode\":\"001\"}}";
		JSONObject result = null;
		//String url = "http://crm.dev.tuniu.org/interface/rest/server/order/OrderInfoQueryInterface.php";
		String url = "http://10.10.30.112:10801/ssi/satisfactionserver/satisfaction";
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("post");
		trestClient.setData(JSONObject.fromObject(str).toString());

			String execute = trestClient.execute();
			System.out.println(execute);
			return execute;
	}
}
