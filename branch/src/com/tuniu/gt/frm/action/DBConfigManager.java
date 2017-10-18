/**
 * 
 */
package com.tuniu.gt.frm.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tuniu.config.db.service.DBConfigService;

/**
 * @author jiangye
 */
@Service
public class DBConfigManager {

    private static DBConfigService dbConfigService;
    
    public static List<String> getConfigAsList(String key){
        return getConfig(key)==null?new ArrayList<String>():Arrays.asList(getConfig(key).split(","));
    }
    
    public static <T>T getConfig(String key, Class<T> clazz){
        String value = dbConfigService.getConfig(key);
        T result;
        if(clazz == Integer.class){
            result = (T) Integer.valueOf(value);
        }else{
            result = (T) value;
        }
        
        return result;
    }
    
    public static <T> List<T> getConfigAsList(String key,Class<T> clazz){
    	List<T> list = new ArrayList<T>();
    	List<String> orinList = getConfig(key)==null?new ArrayList<String>():Arrays.asList(getConfig(key).split(","));
    	if(!CollectionUtils.isEmpty(orinList)){
    		if(clazz == Integer.class){
	    		for (String string : orinList) {
	    			list.add((T) Integer.valueOf(string));
				}
    		}
    	}
        return list;
    }
    
    
    public static String getConfig(String key) {
        return getConfig(key,String.class);
    }

    public  DBConfigService getDbConfigService() {
        return dbConfigService;
    }

    @Autowired
    public  void setDbConfigService(DBConfigService dbConfigService) {
        DBConfigManager.dbConfigService = dbConfigService;
    }
    
    
    
}
