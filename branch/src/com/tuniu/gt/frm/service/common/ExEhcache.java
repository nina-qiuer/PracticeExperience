package com.tuniu.gt.frm.service.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import tuniu.frm.service.Common;

@Service("service_common-excache")
public class ExEhcache {
	
	private static Logger logger = Logger.getLogger(ExEhcache.class);
	
	private static CacheManager manager;
	private static Cache cache;
	
	
	
	static{
		manager = CacheManager.create();
		String names[] = manager.getCacheNames();
		cache =  manager.getCache(names[0]);
	}

	/**
	 * 根据指定的key获取元素
	 * @param key
	 * @return
	 */
	
	public static Object get(String key,Object... param) {
		//从cache中取回元素
		String newKey = makeKey(key, param);
		if(!cache.isKeyInCache(newKey)) {
			String methodName = Common.uFirst(key, 1) + "Recache"; //通过反映来取
			Recache recache = (Recache) tuniu.frm.service.Bean.get("frm_service_common-recache");  
			Class<?> c = recache.getClass();
			try {
				
				if(param != null) {
					Class<?> ca[] = new Class<?>[param.length];
					for(int i=0;i<param.length;i++) {
						ca[i] = param[i].getClass();
					}
					Method method = c.getDeclaredMethod(methodName,ca);
					method.invoke(recache,param);
				} else {
					Method method = c.getDeclaredMethod(methodName);
					method.invoke(recache);
				}
			
			} catch (NoSuchMethodException e) {
				logger.error(e.getMessage(), e);
			} catch (SecurityException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage(), e);
			}
	
		}
		Element element = cache.get(newKey);
		return (Object) element.getValue();
	}
	
	
	public static String makeKey(String key,Object... param) {
		if(key.length() > 15) { 
			key = key.substring(0,15); 
		}
		String ret = key;
		
		if(param.length > 0) {   
			String jsonString = JSONArray.fromObject(param).toString(); 
			
			ret += "_" + Common.md5(jsonString,16);
		}
		return ret;
	}
	
	/**
	 * 设置cache元素
	 * @param key
	 * @param val
	 */
	public static void set(String key,Object val) {
		Element element = new Element(key, val);
		cache.put(element);
	}
}