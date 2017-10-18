package com.tuniu.gt.toolkit;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

public class Mailer {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private Session session;
	
	private static Mailer mailer;
	
	private Mailer () {
		Properties props = new Properties();
		// 设置邮件服务器地址，连接超时时限等信息
		props.put("mail.smtp.host", "mail.tuniu.com"); // 邮件服务器
		//props.put("mail.smtp.host", "172.20.1.106"); // 邮件服务器104、105、106
		props.put("mail.smtp.port", 25); // 要连接的SMTP服务器的端口号，如果connect没有指明端口号就使用它，缺省值25
		props.put("mail.smtp.auth", true); // 如果为true，尝试使用AUTH命令认证用户
		
		// 创建缺省的session对象
		MyAuthenticator myAuthenticator = new MyAuthenticator("robot", "tuniu520");
		session = Session.getDefaultInstance(props, myAuthenticator);
		session.setDebug(false);
	}
	
	public static synchronized Mailer getInstance() {
		if (mailer == null) {
			mailer = new Mailer();
		}
		return mailer;
	}

	private void send(EmailData emailData) {
		boolean isFailure = false; // 发送失败日志
		String[] recipients = emailData.getRecipients();
		String[] cc = emailData.getCc();
		String subject = emailData.getSubject();
		String content = emailData.getContent();
		String contentType = emailData.getContentType();
		String fileName = emailData.getFileName();
		try {
			if (recipients != null && recipients.length > 0) {
				Message msg = new MimeMessage(session); // 创建message对象
				msg.setFrom(new InternetAddress(emailData.getFrom())); // 设置发件人
				msg.setRecipients(Message.RecipientType.TO, getReAddressArr(recipients)); // 设置收件人
				if (cc != null && cc.length > 0) {
					msg.setRecipients(Message.RecipientType.CC, getCcAddressArr(cc)); // 设置抄送人
				}
				msg.setSubject(MimeUtility.encodeText(subject, "GBK", "B")); // 设置邮件标题，中文编码

				// 设置邮件内容,区分文本格式和HTML格式
				if (contentType == null || contentType.equals("text")) {
					msg.setText(content);
				} else if (contentType.equals("html")) {
					msg.setContent(getMultipartContent(content, fileName)); // 设置邮件内容及附件
				}
				msg.setSentDate(new java.util.Date()); // 设置邮件发送时间
				
				Transport.send(msg);
			}
		} catch (Exception e) {
			isFailure = true;
			logger.error(e.getMessage(), e);
		} finally {
			if (isFailure) { // TODO 记录发送失败日志 recipients, cc, subject, content, contentType, fileName
				new RTXSenderThread(7579, "wangmingfang", "邮件发送失败提醒", subject).start();
			}
		}

	}
	
	public static void  sendEmail(EmailData emailData) {
	    getInstance().send(emailData);
	}
	
	private Multipart getMultipartContent(String content, String fileName) throws Exception {
		Multipart multipart = new MimeMultipart();// 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
		BodyPart bodyContent = new MimeBodyPart(); // 新建一个存放信件内容的BodyPart对象
		bodyContent.setContent(content, "text/html;charset=gbk"); // 设置邮件内容
		multipart.addBodyPart(bodyContent);
		if (fileName != null && fileName.equals("")) { // 设置邮件附件
			BodyPart bodyAttchment = new MimeBodyPart();
			FileDataSource fileDataSource = new FileDataSource(fileName);
			bodyAttchment.setDataHandler(new DataHandler(fileDataSource));
			bodyAttchment.setFileName(MimeUtility.encodeText(new File(fileName).getName()));
			multipart.addBodyPart(bodyAttchment);
		}
		return multipart;
	}
	
	private InternetAddress[] getReAddressArr(String[] recipients) throws Exception {
		InternetAddress[] reAddressArr = null;
		InternetAddress[] addressToTmp = new InternetAddress[recipients.length]; // 设置收件人
		int j = 0;
		for (int i = 0; i < recipients.length; i++) {
			if (recipients[i] != null && !"".equals(recipients[i])) {
				addressToTmp[j] = new InternetAddress(recipients[i]);
				j++;
			}
		}
		reAddressArr = convertAddress(addressToTmp, j);
		return reAddressArr;
	}

	private InternetAddress[] getCcAddressArr(String[] cc) throws Exception {
		InternetAddress[] ccAddressArr = null;
		InternetAddress[] ccAdressesTmp = new InternetAddress[cc.length];
		int j = 0;
		for (int i = 0; i < cc.length; i++) {
			if (cc[i] != null && !"".equals(cc[i])) {
				ccAdressesTmp[j] = new InternetAddress(cc[i]);
				j++;
			}
		}
		ccAddressArr = convertAddress(ccAdressesTmp, j);
		return ccAddressArr;
	}

	public InternetAddress[] convertAddress(InternetAddress[] iaPre, int iaLength) {
		InternetAddress[] InternetAddress = new InternetAddress[iaLength];
		for (int i = 0; i < iaLength; i++) {
			InternetAddress[i] = iaPre[i];
		}
		return InternetAddress;
	}

}


// smtp需要验证时候的验证类
class MyAuthenticator extends javax.mail.Authenticator {
	private String user;
	private String password;

	public MyAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.user, this.password);
	}
}
