package com.tuniu.qms.common.util;

import java.util.List;

public class Rtx implements Cloneable{
	
	private static String FROM_EMAI = "rtx@tuniu.com";
	private static String FROM_NAME = "rtxSys";
	private static Integer STEP_ID = 514;
	private static Integer ORDER_ID =0;

	private Integer orderId =ORDER_ID;
	private String fromEmail = FROM_EMAI;
	private String fromName= FROM_NAME;
	private Integer stepId = STEP_ID;

	/**发送至员工id（非工号，是oa系统中的id*/
	private List<Integer> uids;
	
	/**rtx标题*/
	private String title;
	
	/**rtx内容*/
	private String content;
	
	

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public Integer getStepId() {
		return stepId;
	}
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}
	public List<Integer> getUids() {
		return uids;
	}
	public void setUids(List<Integer> uids) {
		this.uids = uids;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	 
}
