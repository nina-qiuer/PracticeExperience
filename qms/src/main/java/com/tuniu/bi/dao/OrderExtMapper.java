package com.tuniu.bi.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.bi.dto.OrderExtDto;

public interface OrderExtMapper {
	
	List<OrderExtDto> list(Map<String, Object> map);
	
}
