package com.tuniu.qms.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tuniu.qms.common.util.CacheConstant;
import com.whalin.MemCached.MemCachedClient;

@Controller
@RequestMapping("/common/cache")
public class CacheController {
	
	@Autowired
	private MemCachedClient memCachedClient;
	
	@RequestMapping("/list")
    public String list(HttpServletRequest request) {
		List<String> keys = CacheConstant.getCacheKeys();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for (String key : keys) {
			Object obj = memCachedClient.get(key);
			if (null != obj) {
				String value = JSON.toJSONString(obj);
				dataMap.put(key, value);
			}
		}
		request.setAttribute("dataMap", dataMap);
		
        return "common/cacheList";
    }
	
	@ResponseBody
	@RequestMapping("/remove")
    public boolean remove(String key, HttpServletRequest request) {
		return memCachedClient.delete(key);
    }
	
}
