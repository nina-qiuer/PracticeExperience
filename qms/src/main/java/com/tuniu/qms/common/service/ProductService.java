package com.tuniu.qms.common.service;

import java.util.List;

import com.tuniu.qms.common.dto.ProductDto;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.service.base.BaseService;

public interface ProductService extends BaseService<Product, ProductDto> {
	
	void increFailTimes(Integer prdId);
	
	List<Integer> listPrdSyncTask();

	void addPrdSyncTask(Integer prdId);

	void syncProduct();
	
}
