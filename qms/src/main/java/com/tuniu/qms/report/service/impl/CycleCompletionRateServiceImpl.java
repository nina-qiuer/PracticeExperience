package com.tuniu.qms.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.report.dao.CycleCompletionRateMapper;
import com.tuniu.qms.report.dto.CycleCompletionRateDto;
import com.tuniu.qms.report.model.CycleCompletionRate;
import com.tuniu.qms.report.service.CycleCompletionRateService;

@Service
public class CycleCompletionRateServiceImpl implements CycleCompletionRateService {
	
	
	@Autowired
	private CycleCompletionRateMapper mapper;
	

	@Override
	public void add(CycleCompletionRate obj) {
		
		mapper.add(obj);
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(CycleCompletionRate obj) {
		
		mapper.update(obj);
		
	}

	@Override
	public CycleCompletionRate get(Integer id) {

		return mapper.get(id);
	}


	@Override
	public void loadPage(CycleCompletionRateDto dto) {
		
	}
	
	@Override
	public List<CycleCompletionRate> list(CycleCompletionRateDto dto) {
		
		List<CycleCompletionRate> dataList = mapper.list(dto);
		for (CycleCompletionRate data : dataList) {
			
			int qcPeriodBgnNum = data.getQcPeriodBgnNum();
			if (qcPeriodBgnNum > 0) {
				double doneRate = MathUtil.div(data.getQcPeriodBgnDoneNum(), qcPeriodBgnNum, 3);
				data.setDoneRate(doneRate);
			}
			
		}
		
		return dataList; 
	}
	

}
