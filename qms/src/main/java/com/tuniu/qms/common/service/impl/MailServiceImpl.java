package com.tuniu.qms.common.service.impl;

import java.util.Arrays;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.init.Config;
import com.tuniu.qms.common.model.MailTask;
import com.tuniu.qms.common.service.MailService;
import com.tuniu.qms.common.util.Constant;

@Service("mailService")
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Override
	public boolean sendEmail(MailTask mail) {
		boolean flag = false;
		try {
			send(mail);
			flag = true;
		} catch (Exception e) {
			
			logger.error("发送邮件失败"+e.getMessage(),e);
		} 
		return flag;
	}
	
	private void send(MailTask mail) throws Exception {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, Constant.ENCODING);
		helper.setFrom("robot@tuniu.com");
		String[] reAddrs = mail.getReAddrs();
		if (null == reAddrs) {
			reAddrs = new String[]{};
		}
		String[] ccAddrs = mail.getCcAddrs();
		if (null == ccAddrs) {
			ccAddrs = new String[]{};
		}
		helper.setTo(reAddrs);
		helper.setCc(ccAddrs);
		helper.setSubject(MimeUtility.encodeText(mail.getSubject(), Constant.ENCODING, "B"));
		helper.setText(mail.getContent(), true); // true表示text的内容为html
		
		if (!"prd".equals(Config.get("env.name"))) { // 非生产环境，将邮件发送给测试人员
			helper.setTo("wangmingfang@tuniu.com;chenhaitao@tuniu.com;jiangye@tuniu.com".split(";"));
			helper.setCc("zhanchengzong@tuniu.com;guowei2@tuniu.com".split(";"));
			StringBuilder sb = new StringBuilder();
			sb.append("收件人：").append(Arrays.toString(mail.getReAddrs())).append("<br>");
			sb.append("抄送人：").append(Arrays.toString(mail.getCcAddrs())).append("<br>");
			sb.append(mail.getContent());
			helper.setText(sb.toString(),true);
		}
		
		mailSender.send(msg);
	}
	
}
