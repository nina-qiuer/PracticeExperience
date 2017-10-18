package com.tuniu.gt.toolkit;

/**
 * 邮件的数据结构类
 * 定义邮件常用常用数据
 * @author yuliang
 *
 */
public class EmailData {
	private String from   = null;  //发件人 
	private String[] recipients = null;  //收件人,可以多个
	private String[] cc = null;  //抄送人,可以多个 
	private String subject   = null;  //邮件主题 
	private String content   = null;  //邮件内容 
	private String contentType  = null;  //邮件内容格式(文本或html) 
	private String fileName  = null;  //附件文件名(目前只提供一个附件) 
    
	public EmailData(String from, String[] recipients, String[] cc, String subject, String content, String contentType) {
		this.from = from;
		this.recipients = recipients;
		this.cc = cc;
		this.subject = subject;
		this.content = content;
		this.contentType = contentType;
	}
	
	public EmailData(String from, String[] recipients, String[] cc, String subject, String content, String contentType, String fileName) {
		this.from = from;
		this.recipients = recipients;
		this.cc = cc;
		this.subject = subject;
		this.content = content;
		this.contentType = contentType;
		this.fileName = fileName;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String[] getRecipients() {
		return recipients;
	}
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}
    
}
