package com.tuniu.gt.complaint.mq;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ComplaintResultMQProducer {
	
	private Logger logger = Logger.getLogger(ComplaintResultMQProducer.class);
	
	private JmsTemplate jmsTemplate;
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(String complaintNo, String result) {
		logger.info("Begin Send Message: " + complaintNo + "---" + result);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("complaintNo", complaintNo);
		param.put("result", result);
		final String value = JSONObject.fromObject(param).toString();
		
		jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setText(value);
                return message;
            }
        });
		
		logger.info("End Send Message: " + complaintNo + "---" + result);
	}

}
