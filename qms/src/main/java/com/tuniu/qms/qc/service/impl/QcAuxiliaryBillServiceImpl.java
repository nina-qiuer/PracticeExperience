package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.QcAuxiliaryBillMapper;
import com.tuniu.qms.qc.dto.QcAuxiliaryBillDto;
import com.tuniu.qms.qc.model.QcAuxiliaryBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.QcAuxiliaryBillService;
import com.tuniu.qms.qc.service.QcTypeService;

@Service
public class QcAuxiliaryBillServiceImpl implements QcAuxiliaryBillService{

	
	@Autowired
	private QcAuxiliaryBillMapper mapper;
	
	 
    @Autowired
    private  QcTypeService qcTypeService;
    
	@Override
	public void add(QcAuxiliaryBill obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
	}

	@Override
	public void update(QcAuxiliaryBill obj) {
	
		mapper.update(obj);
	}

	@Override
	public QcAuxiliaryBill get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<QcAuxiliaryBill> list(QcAuxiliaryBillDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcAuxiliaryBillDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		List<QcAuxiliaryBill> list =  this.list(dto);
		List<QcType> qcList = qcTypeService.getQcTypeDataCache();
		for(QcAuxiliaryBill bill:list){
			
			String fullName = qcTypeService.getQtFullNameById(bill.getQcType(), qcList);
			bill.setQcTypeName(fullName);
			
		}
		
		dto.setDataList(list);
		
	}

	@Override
	public List<QcAuxiliaryBill> getByTemplate(QcAuxiliaryBillDto dto) {
		
		return mapper.getByTemplate(dto);
	}

	@Override
	public void deleteByIds(QcAuxiliaryBillDto dto) {
		
		List<Integer> ids = dto.getBillIds();
		for (Integer id : ids) {
			
			mapper.delete(id);
			
		 }
		
		
	}

	@Override
	public List<QcAuxiliaryBill> exportList(QcAuxiliaryBillDto dto) {
		
		return mapper.exportList(dto);
	}

	@Override
	public Integer exportCount(QcAuxiliaryBillDto dto) {
		
		return mapper.exportCount(dto);
	}

	


}
