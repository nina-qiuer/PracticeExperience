package com.tuniu.qms.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.common.dao.ComplaintBillMapper;
import com.tuniu.qms.common.dto.ComplaintBillDto;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.DataSyncModel;
import com.tuniu.qms.common.service.ComplaintBillService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.model.QcSameGroupBill;

@Service
public class ComplaintBillServiceImpl implements ComplaintBillService {
	
	@Autowired
	private ComplaintBillMapper mapper;

	@Override
	public void add(ComplaintBill cmp) {
		mapper.add(cmp);
		
		this.deleteCmpSyncTask(cmp.getId());
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(ComplaintBill cmp) {
		mapper.update(cmp);
	}

	@Override
	public ComplaintBill get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<ComplaintBill> list(ComplaintBillDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(ComplaintBillDto dto) {
		
	}

	@Override
	public List<DataSyncModel> listCmpSyncTask() {
		return mapper.listCmpSyncTask();
	}

	@Override
	public void addCmpSyncTasks() {
		mapper.addCmpSyncTasks();
	}

	@Override
	public void increFailTimes(DataSyncModel cmpData) {
		if(cmpData.getFailTimes() < 3){
			cmpData.setFailTimes(cmpData.getFailTimes() + 1);
		}
		
		mapper.increFailTimes(cmpData);
	}

	@Override
	public void deleteCmpSyncTask(Integer cmpId) {
		mapper.deleteCmpSyncTask(cmpId);
	}

	@Override
	public List<QcSameGroupBill> queryHasCtOrders(Map<String, Object> paramMap) {
		
		return mapper.queryHasCtOrders(paramMap);
	}

	@Override
	public void syncComplaint() {
		List<DataSyncModel> cmpDataList = this.listCmpSyncTask();
		
		for(DataSyncModel cmpData : cmpDataList) {
			
			ComplaintBill cmp = CmpClient.getComplaintInfo(cmpData.getDataId());
			
			if (CommonUtil.isGreaterThanZero(cmp.getId())) {
				Date date = DateUtil.parseDateTime("1990-01-01 00:00:00");
				if (cmp.getFinishTime().before(date)) {
					cmp.setFinishTime(null);
				}
				this.add(cmp);
			}else{
				
				this.increFailTimes(cmpData);
			}
		}
	}

	@Override
	public void addCmpSyncTask(Integer cmpId) {
		if(CommonUtil.isGreaterThanZero(cmpId)){
			mapper.addCmpSyncTask(cmpId);
		}
	}

}
