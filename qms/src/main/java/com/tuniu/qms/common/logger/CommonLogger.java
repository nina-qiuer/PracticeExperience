package com.tuniu.qms.common.logger;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class CommonLogger {
	
	private final static Logger logger = LoggerFactory.getLogger(CommonLogger.class);
	
	public void logBef(JoinPoint point) {
		// 可添加日志开关
		String className = point.getTarget().getClass().getName(); // 拦截的实体类名称
		String methodName = point.getSignature().getName(); // 拦截的方法名称
		Object[] args = point.getArgs(); // 拦截的方法参数
        logger.info(className + "." + methodName + "【Bgn】, requestArgs：" + JSON.toJSONString(args));
	}
	
	public void logAft(JoinPoint point, Object returnVal) {
		String className = point.getTarget().getClass().getName(); // 拦截的实体类名称
		String methodName = point.getSignature().getName(); // 拦截的方法名称
        logger.info(className + "." + methodName + "【End】, returnValue：" + JSON.toJSONString(returnVal));
	}

}
