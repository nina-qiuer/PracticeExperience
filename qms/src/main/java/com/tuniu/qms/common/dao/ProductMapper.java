package com.tuniu.qms.common.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.ProductDto;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.qc.model.QcBill;

public interface ProductMapper extends BaseMapper<Product, ProductDto> {
	
	void addPrdSyncTask(Integer prdId);
	
	void addAttachPrdSyncTask(List<QcBill> list);
	
	List<Integer> listPrdSyncTask();
	
	void deletePrdSyncTask(Integer prdId);
	
	void increFailTimes(Integer prdId);
	
}
