package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.qc.dao.DevRespBillMapper;
import com.tuniu.qms.qc.dto.DevRespBillDto;
import com.tuniu.qms.qc.model.DevRespBill;
import com.tuniu.qms.qc.service.DevRespBillService;

@Service
public class DevRespBillServiceImpl implements DevRespBillService{

	@Autowired
	private DevRespBillMapper mapper;
	@Autowired
	private DepartmentService  depService;
	@Override
	public void add(DevRespBill obj) {
	
		mapper.add(obj);
		
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(DevRespBill obj) {
		mapper.update(obj);
		
	}

	@Override
	public DevRespBill get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<DevRespBill> list(DevRespBillDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(DevRespBillDto dto) {
		
		
	}

	@Override
	public int getDevRespIsExist(DevRespBill respBill) {
	
		
		return mapper.getDevRespIsExist(respBill);
	}

	@Override
	public List<DevRespBill> listResp(DevRespBillDto dto) {
		
		
		 List<DevRespBill> list = list(dto);
		  for(DevRespBill respBill: list){
			  
			  String fullName = depService.getDepFullNameById(respBill.getDepId());
			  respBill.setDepName(fullName);
		  }
		return list;
	}

	
	
}
