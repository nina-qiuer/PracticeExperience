package com.tuniu.qms.access.dto;


public class ResponseDto {
	
	/** 成功与否 */
	private boolean success = true;
	
	/** 错误码 */
	private int errorCode = 0;
	
	/** 返回消息 */
	private String msg = "Success";
	
	/** 返回的数据 */
	private Object data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
