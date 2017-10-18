package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintQualityToolDao;
import com.tuniu.gt.complaint.entity.ComplaintQualityToolEntity;
import com.tuniu.gt.complaint.service.IComplaintQualityToolService;

/**
* @ClassName: QualityToolServiceImpl
* @Description:接口实现
* @author zhoupanpan
* @date 2012-05-03
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_service_complaint_impl-complaint_quality_tool")
public class ComplaintQualityToolServiceImpl extends ServiceBaseImpl<ComplaintQualityToolDao> implements IComplaintQualityToolService {
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_quality_tool")
	public void setDao(ComplaintQualityToolDao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public List<ComplaintQualityToolEntity> getQualityToolListBySolutionId(
			int solutionId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(solutionId > 0){
			paramMap.put("shareId", solutionId);
			return  (List<ComplaintQualityToolEntity>) dao.fetchList(paramMap);
		}else{
			return null;
		}
	}


}
