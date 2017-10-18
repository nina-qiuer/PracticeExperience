package com.tuniu.gt.complaint.entity;

public class ResponseEntity {

	//错误码
	private int errorCode;
	
	//错误描述
	private String msg;
	
	//上传的url
	private String url;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
