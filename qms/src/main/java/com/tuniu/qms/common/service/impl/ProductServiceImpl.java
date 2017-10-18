package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.ProductMapper;
import com.tuniu.qms.common.dto.ProductDto;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.service.ProductService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.util.CommonUtil;

@Service
public class ProductServiceImpl implements ProductService {
	
    @Autowired
    private ProductMapper mapper;

    @Autowired
    private TspService tspService;

	@Override
	public void add(Product prd) {
		mapper.add(prd);
		
		mapper.deletePrdSyncTask(prd.getId());
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Product obj) {
		mapper.update(obj);
	}

	@Override
	public Product get(Integer id) {
	    Product prd = mapper.get(id);  
	    if(null == prd ) {
	    	
	        prd =  tspService.getProductInfo(id);
	    }
		return prd==null?new Product():prd;
	}

	@Override
	public List<Product> list(ProductDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(ProductDto dto) {
		
	}

	@Override
	public List<Integer> listPrdSyncTask() {
		return mapper.listPrdSyncTask();
	}

	@Override
	public void increFailTimes(Integer prdId) {
		mapper.increFailTimes(prdId);
	}

	@Override
	public void addPrdSyncTask(Integer prdId) {
		if(null != prdId && prdId > 0){
			mapper.addPrdSyncTask(prdId);
		}
	}

	@Override
	public void syncProduct() {
		List<Integer> prdIds = this.listPrdSyncTask();
		
		for (Integer prdId : prdIds) {
			
			Product prd = tspService.getProductInfo(prdId);
			
			if (CommonUtil.isGreaterThanZero(prd.getId())) {
				this.add(prd);
			}else{
				this.increFailTimes(prdId);
			}
		}
	}

}
