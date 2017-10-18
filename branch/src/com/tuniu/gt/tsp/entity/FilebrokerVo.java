package com.tuniu.gt.tsp.entity;

import java.util.List;
import java.util.Map;

public class FilebrokerVo {

	private boolean success = true;
	
	private String msg = "";
	
	private int errorCode = 590000;
	
	private List<Map<String, String>> data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}
}
