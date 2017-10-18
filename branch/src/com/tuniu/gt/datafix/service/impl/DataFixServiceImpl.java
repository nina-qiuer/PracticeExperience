package com.tuniu.gt.datafix.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.datafix.dao.impl.DataFixDao;
import com.tuniu.gt.datafix.service.IDataFixService;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;

/**
* @ClassName: ComplaintServiceImpl
* @Description:complaint接口实现
* @author Ocean Zhong
* @date 2012-1-20 下午5:04:51
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("datafix_service_datafix_impl-datafix")
public class DataFixServiceImpl extends ServiceBaseImpl<DataFixDao> implements IDataFixService {
	@Autowired
	@Qualifier("datafix_dao_impl-datafix")
	public void setDao(DataFixDao dao) {
		this.dao = dao;
	}

	@Override
	public Integer getSignSatisfactionDataFixMaxId() {
		return dao.getSignSatisfactionDataFixMaxId();
	}

	@Override
	public SignSatisfactionEntity getNextSignSatisfactionDataFixId(Map map) {
		return dao.getNextSignSatisfactionDataFixId(map);
	}

	@Override
	public void updateCityInfo(Map map) {
		dao.updateCityInfo(map);
	}

	@Override
	public void updateMaxId(Integer id) {
		dao.updateMaxId(id);
	}

	
}
