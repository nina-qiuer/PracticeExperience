package com.tuniu.gt.complaint.entity;


public class HandlerResult {

	
		//返回编码
		private String retCode;
		//系统返回码/错误码
	    private String sysCode;
		//返回消息
		private String resMsg;
		//返回结果集
		private Object retObj;

		public static HandlerResult newInstance()
		{
			HandlerResult result = new HandlerResult();
			result.setRetCode("0");
			return result;
		}

		public static HandlerResult newInstance(String retCode)
		{
			HandlerResult result = new HandlerResult();
			result.setRetCode(retCode);
			return result;
		}

		public String getRetCode()
		{
			return retCode;
		}
		public void setRetCode(String retCode)
		{
			this.retCode = retCode;
		}
		public String getResMsg()
		{
			return resMsg;
		}
		public void setResMsg(String resMsg)
		{
			this.resMsg = resMsg;
		}
		public Object getRetObj()
		{
			return retObj;
		}
		public void setRetObj(Object retObj)
		{
			this.retObj = retObj;
		}

		public String getSysCode() {
			return sysCode;
		}

		public void setSysCode(String sysCode) {
			this.sysCode = sysCode;
		}

	   

	}

