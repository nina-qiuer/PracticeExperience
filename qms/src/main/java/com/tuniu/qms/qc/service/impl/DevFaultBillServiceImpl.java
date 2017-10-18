package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.qc.dao.DevFaultBillMapper;
import com.tuniu.qms.qc.dao.DevRespBillMapper;
import com.tuniu.qms.qc.dao.DevUpLoadAttachMapper;
import com.tuniu.qms.qc.dto.DevFaultBillDto;
import com.tuniu.qms.qc.dto.DevRespBillDto;
import com.tuniu.qms.qc.dto.DevUpLoadAttachDto;
import com.tuniu.qms.qc.model.DevFaultBill;
import com.tuniu.qms.qc.model.DevRespBill;
import com.tuniu.qms.qc.model.DevUpLoadAttach;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.service.DevFaultBillService;
import com.tuniu.qms.qc.service.DevRespBillService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;

@Service
public class DevFaultBillServiceImpl implements DevFaultBillService{

	@Autowired
	private DevFaultBillMapper mapper;
	
	@Autowired
	QualityProblemTypeService  qptService;
	
	@Autowired
	private DevUpLoadAttachMapper upMapper;
	
	@Autowired
	private DevRespBillMapper  respMapper;
	
	@Autowired
	private DevRespBillService  devRespService;
	
	@Override
	public void add(DevFaultBill obj) {
		
		mapper.add(obj);
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(DevFaultBill obj) {
		
		mapper.update(obj);
		
	}

	@Override
	public DevFaultBill get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<DevFaultBill> list(DevFaultBillDto dto) {
		
		List<DevFaultBill> list = mapper.list(dto);
		for(DevFaultBill dev :list){
			
			DevUpLoadAttachDto  upDto = new DevUpLoadAttachDto();
			upDto.setDevId(dev.getId());
			List<DevUpLoadAttach> upList = upMapper.list(upDto);
			dev.setUpLoadList(upList);
		}
		return list;
	}

	@Override
	public void loadPage(DevFaultBillDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
		
	}

	@Override
	public List<DevFaultBill> listFault(DevFaultBillDto dto) {

		  List<DevFaultBill> list = list(dto);
		  List<QualityProblemType> listType =  qptService.getQpTypeDataCache(Constant.QC_DEVELOP);
		  for(DevFaultBill dev: list){
			  
			  String fullName =  qptService.getQpFullNameById(dev.getQptId(),listType);
			  dev.setQptName(fullName);
		  }
		return list;
	}

	/**
	 * 删除故障单对应相关信息
	 */
	public void deleteFault(Integer devId) {
		
		mapper.delete(devId);//删除故障单
		respMapper.deleteDevRespBill(devId);//删除故障单对应责任人
		upMapper.deleteAttach(devId);//删除质量问题对应的附件
		
	}

	@Override
	public List<DevFaultBill> listDevFaultDetail(Integer qcId) {
		
		
		 DevFaultBillDto dto =new DevFaultBillDto();
		 dto.setQcId(qcId);
		 List<DevFaultBill> list = list(dto);
		 List<QualityProblemType> listType =  qptService.getQpTypeDataCache(Constant.QC_DEVELOP);
		 for(DevFaultBill dev: list){
			  
				DevRespBillDto devDto =new DevRespBillDto();
				devDto.setDevId(dev.getId());
				List<DevRespBill>  respList = devRespService.listResp(devDto);
				if(null!=respList){
					
					dev.setDevList(respList);
				}
			
			  String fullName =  qptService.getQpFullNameById(dev.getQptId(),listType);
			  dev.setQptName(fullName);
		  }
		  return list;
	}

	
	

}
