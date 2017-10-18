package com.tuniu.qms.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.OperationLogMapper;
import com.tuniu.qms.common.dto.OperationLogDto;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.util.Constant;

@Service
public class OperationLogServiceImpl implements OperationLogService {

	
	@Autowired
	private OperationLogMapper mapper;
	
	@Override
	public void add(OperationLog obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(OperationLog obj) {
		mapper.update(obj);
		
	}

	@Override
	public OperationLog get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<OperationLog> list(OperationLogDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(OperationLogDto dto) {
	}

	@Override
	public void addQcOperationLog(Integer qcId, User user, String flowName, String content) {
		OperationLog log = new OperationLog();
      	
		if(null != user){
			log.setDealPeople(user.getId());
	      	log.setDealPeopleName(user.getRealName());
	      	log.setDealDepart(user.getRole().getName());
		}else{
			log.setDealPeopleName(Constant.ROBOT);
		}
    	
      	log.setQcId(qcId);
	    log.setFlowName(flowName);
	    log.setContent(content);
      	
	    this.add(log);
	}
}
