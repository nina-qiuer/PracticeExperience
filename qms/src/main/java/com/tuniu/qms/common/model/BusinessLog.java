package com.tuniu.qms.common.model;

public class BusinessLog extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	private String clientIP; // 客户端IP
	private Integer operatorId; // 操作人ID
	private String operatorName; // 操作人姓名
	private String resName; // 资源名称
	private String requestUri; // 访问地址
	private String requestArgs; // 访问参数
	
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getRequestUri() {
		return requestUri;
	}
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	public String getRequestArgs() {
		return requestArgs;
	}
	public void setRequestArgs(String requestArgs) {
		this.requestArgs = requestArgs;
	}

}
