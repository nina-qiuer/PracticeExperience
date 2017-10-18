package com.tuniu.bi.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.bi.dto.ProductExtDto;

public interface ProductExtMapper {
	
	List<ProductExtDto> list(Map<String, Object> map);
	
}
