package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.QualityToolDao;
import com.tuniu.gt.complaint.entity.QualityToolEntity;
import com.tuniu.gt.complaint.service.IQualityToolService;

/**
* @ClassName: QualityToolServiceImpl
* @Description:接口实现
* @author zhoupanpan
* @date 2012-05-03
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_service_complaint_impl-quality_tool")
public class QualityToolServiceImpl extends ServiceBaseImpl<QualityToolDao> implements IQualityToolService {
	@Autowired
	@Qualifier("complaint_dao_impl-quality_tool")
	public void setDao(QualityToolDao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QualityToolEntity> getList() {
		return (List<QualityToolEntity>) this.fetchList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QualityToolEntity> getListInUse() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("useFlag", "1");
		return (List<QualityToolEntity>) dao.fetchList(paramMap);
	}

}
