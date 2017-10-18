package com.tuniu.gt.complaint.mq;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.trest.TRestException;

public class ChangeFlight4Complaint implements MessageListener {
	
	private static Logger logger = Logger.getLogger("ChangeFlight4Complaint.class");
	
	private JmsTemplate jmsTemplate;
	private Destination destination;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void receive() throws JMSException {

	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	@Override
	public void onMessage(Message message) {
		String msg = "";
		if (message instanceof TextMessage) {
			try {
				msg = ((TextMessage) message).getText();
				logger.info("----------ActiveMQ received infomation: " + msg);
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}
		launchComplaint(msg);
	}
	
	@SuppressWarnings("unchecked")
	private void launchComplaint(String msg){
		try {
			msg = msg.substring(msg.indexOf("{"), msg.indexOf("}") + 1);
			JSONObject jsonData = JSONObject.fromObject(msg);
			Integer orderId = JsonUtil.getIntValue(jsonData, "orderId");
			int complaintLevel = JsonUtil.getIntValue(jsonData, "complaintLevel");
			String levelOne = JsonUtil.getStringValue(jsonData, "levelOne");
			String levelTwo = JsonUtil.getStringValue(jsonData, "levelTwo");
			String content = JsonUtil.getStringValue(jsonData, "content");
			int ownerId = JsonUtil.getIntValue(jsonData, "ownId");
			String ownerName =JsonUtil.getStringValue(jsonData, "ownerName");
			if (orderId != null) {
				int compId = -1;
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", String.valueOf(orderId));
				params.put("state", "1,2,5,7");
				List<ComplaintEntity> complaintList = (List<ComplaintEntity>) complaintService.fetchList(params);
				int addReasonFlag = 0;
				if (!complaintList.isEmpty() && complaintList.size() > 0) {
					ComplaintEntity compEnt = complaintList.get(0);
					if (!("出游中".equals(compEnt.getOrderState()) && Constans.SPECIAL_BEFORE_TRAVEL.equals(compEnt.getDealDepart()))) {
						addReasonFlag = 1; // 新增投诉事宜
						compId = compEnt.getId();
						List<ComplaintReasonEntity> reasonList = getReasonList(orderId, levelOne, levelTwo, content);
						reasonService.insertReasonList(ownerId, ownerName, compId, "", reasonList,-1);
						
						compEnt.setChgFlightFlag(1);
						if (complaintLevel < compEnt.getLevel()) {
							compEnt.setLevel(complaintLevel);
						}
						complaintService.update(compEnt);
						
						compEnt.setOwnerId(ownerId);
						compEnt.setOwnerName(ownerName);
						compEnt.setLevel(complaintLevel);
						compEnt.setReasonList(reasonList);
						complaintService.sendLaunchCompEmail(compEnt);
						
						logger.info("----------Add comp-Reason success, complaintId is " + compId);
					}
				}

				if (0 == addReasonFlag) {
					ComplaintEntity entity = complaintService.getOrderInfoMain(String.valueOf(orderId));
					entity.setLevel(complaintLevel);
					entity.setOwnerId(ownerId);
					entity.setOwnerName(ownerName);
					entity.setComeFrom("其他");
					entity.setChgFlightFlag(1);
					entity.setOrderState("出游中");
					entity.setReasonList(getReasonList(orderId, levelOne, levelTwo, content));
					complaintService.insertComplaint(entity);
					compId = entity.getId();
					
					logger.info("----------create complaint success, complaintId is " + compId);
				}
			}
		} catch (TRestException e) {
			logger.error("----------create complaint failed, Parameter decode error");
		} catch (ParseException e) {
			logger.error("----------create complaint failed, Date format error");
		} catch (Exception e) {
			logger.error("----------create complaint failed, Data insert error");
		}
	}

	private List<ComplaintReasonEntity> getReasonList(int orderId,String levelOne,String levelTwo,String content) {
		List<ComplaintReasonEntity> reasonList = new ArrayList<ComplaintReasonEntity>();
		
		ComplaintReasonEntity reason = new ComplaintReasonEntity();
		reason.setType(levelOne);
		reason.setSecondType(levelTwo);
		reason.setTypeDescript(content);
		reason.setOrderId(orderId);
		reasonList.add(reason);
		
		return reasonList;
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
	
	public String isNull(String value){
		return value == null ? "": "null".equals(value)? "": value; 
	}
	
	public static boolean isNum(String str){
		 Pattern pattern = Pattern.compile("[0-9]*");
		    return pattern.matcher(str).matches();    
	}
	
}
