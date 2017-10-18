package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.OrderBillMapper;
import com.tuniu.qms.common.dto.OrderBillDto;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.service.OrderBillService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.util.CommonUtil;

@Service
public class OrderBillServiceImpl implements OrderBillService {
	
	@Autowired
	private OrderBillMapper mapper;
	
	@Autowired
	private TspService tspService;

	@Override
	public void add(OrderBill ord) {
		
		mapper.add(ord);
		
		mapper.deleteOrdSyncTask(ord.getId());
		
		updateOrderBillOperator(ord);
		
		updateOrderBillAgency(ord);
	}

	private void updateOrderBillAgency(OrderBill ord) {
		mapper.deleteAgencyByOrdId(ord.getId());
		
		if (null != ord.getAngencies() && ord.getAngencies().size()>0) {
			mapper.addAgencies(ord.getAngencies());
		}
	}

	private void updateOrderBillOperator(OrderBill ord) {
		mapper.deleteOperatorByOrdId(ord.getId());
		
		if (null != ord.getOperators() && ord.getOperators().size() > 0) {
			mapper.addOperators(ord.getOperators());
		}
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(OrderBill obj) {
		mapper.update(obj);
	}

	@Override
	public OrderBill get(Integer id) {
		
		 OrderBill orderBill = mapper.get(id);
		
	     if(null == orderBill) {
	    	  orderBill = tspService.getOrderInfo(id);
	     }else{
	    	 orderBill.setAvlPriceFlag(0);
	     }
	    
		return null == orderBill ? new OrderBill() : orderBill;
	}

	@Override
	public List<OrderBill> list(OrderBillDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(OrderBillDto dto) {
		
	}

	@Override
	public List<Integer> listOrdSyncTask() {
		return mapper.listOrdSyncTask();
	}

	@Override
	public void increFailTimes(Integer ordId) {
		mapper.increFailTimes(ordId);
	}

	@Override
	public void addOrdSyncTask(Integer ordId) {
		if(null != ordId && ordId > 0){
			mapper.addOrdSyncTask(ordId);
		}
	}

	@Override
	public void syncOrder() {
		List<Integer> ordIds = this.listOrdSyncTask();
		
		for (Integer ordId : ordIds) {
			
			OrderBill ord = tspService.getOrderInfo(ordId);
			
			if (CommonUtil.isGreaterThanZero(ord.getId())) {
				this.add(ord);
			}else{
				this.increFailTimes(ordId);
			}
		}
	}


}
