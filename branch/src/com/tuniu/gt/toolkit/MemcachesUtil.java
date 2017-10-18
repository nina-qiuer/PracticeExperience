/*
 * Copyright 2012 Asiainfo-Linkage Security UAM department, Inc. All rights
 * reserved. Asiainfo-Linkage PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com.tuniu.gt.toolkit;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import tuniu.frm.service.Constant;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * @Summary memcached缓存服务
 * @Desc memcached提供方法
 */
public class MemcachesUtil {
    // private static Logger log = LoggerFactory.getLogger(MemcachesUtil.class);
    private static MemCachedClient mcc;
    static {

        // Memcached服务器地址列表，例如: 10.142.195.61:11211 127.0.0.1:11211
        // String addresses =
        // EsfProperties.getProperty(ServiceConstants.MEMCACHED_ADDRESS);
        //String addresses = getConfigMgr().getString("memcacheServer");
        //String addresse = "10.10.30.112:11211";
    	Constant constant = new Constant();
    	String address=Constant.CONFIG.getProperty("MEMCACHE_SERVER");
        //log.info("Reader memcachedAddress = " + " : " + addresses);

        String[] servers = new String[] { address };
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setFailover(true);
        pool.setInitConn(10);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        // pool.setMaintSleep( 30 );
        pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setAliveCheck(true);
        pool.initialize();

        mcc = new MemCachedClient();// binaryConnection
    }

    /**
     * 从缓存中获取对象。
     * 
     * @param key
     *            对象关键字
     * @return 缓存对象，如果缓存过期或者不存在或者获取缓存失败，则返回null
     */
    public static String get(String key) {
    	String value = (String) mcc.get(key);
    	value = null == value ? null : value.trim();
        return value;
    }

    public static boolean set(String key, Object value) {
        return mcc.set(key, value);
    }
    
    public static boolean add(String key, Object value, int expired) {
        return mcc.add(key, value, new Date(expired * 1000));
    }
    
    public static boolean setInToday(String key, Object value) {
        return mcc.set(key, value, new Date(getExpireTimeToday()));
    }

    public static boolean set(String key, Object value, long expired) {
        return mcc.set(key, value, new Date(expired));
    }

    public static boolean delete(String key) {
        return mcc.delete(key);
    }

    public static Long increment(String key, Long value) {
        return mcc.incr(key, value);
    }
    
    public static Long decrement(String key, Long value) {
    	return mcc.decr(key, value);
    }

    public static MemCachedClient getMemCachedClient() {
        return mcc;
    }

    /**
    * 从缓存中获取对象。
    * 
    * @param key
    *            对象关键字
    * @return 缓存对象，如果缓存过期或者不存在或者获取缓存失败，则返回null
    */
    public static Object getObj(String key) {
        return mcc.get(key);
    }

    public static boolean set(Map inMap) {
        return mcc.set((String) inMap.get("key"), inMap.get("value"));
    }
    
    private static Long getExpireTimeToday() {
		Long curMills = System.currentTimeMillis();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date(curMills));
		calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() - curMills;
	}
    
    public static void main(String[] args){
    	MemcachesUtil.set("key1","111");
    	System.out.println(MemcachesUtil.get("key1"));
    }
}
