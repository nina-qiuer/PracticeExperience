package com.tuniu.bi.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.qs.model.OrderUncollectionMonitor;

public interface OrderUncollectionMapper {
	
	/**
	 * 获得未收团款订单数据
	 * @param map 
	 * @return
	 */
	List<OrderUncollectionMonitor> getUncollectionList(Map<String, Object> map);

}
