package com.tuniu.gt.complaint.restful;


public class RestServerFactory {
	
	/**
	 * 创建RestServer工厂方法
	 * @param method server唯一标示
	 * @return RestServerHandlerInterface
	 * @throws ClassNotFoundException 
	 */
	public static RestServerHandlerInterface getRestServer(String data){
		String method = RestDataParse.getMethodByClientData(data);
		String[] split = method.split("\\.");
		if("ComplaintRestServer".equals(split[0])){
			return new ComplaintRestServer();
		}
		return null;
	}
}
