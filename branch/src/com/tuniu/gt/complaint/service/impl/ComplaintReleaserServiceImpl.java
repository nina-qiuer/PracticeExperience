package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintReleaserDao;
import com.tuniu.gt.complaint.entity.ComplaintReleaserEntity;
import com.tuniu.gt.complaint.service.IComplaintReleaserService;
@Service("complaint_service_impl-complaint_releaser")
public class ComplaintReleaserServiceImpl extends ServiceBaseImpl<ComplaintReleaserDao> implements IComplaintReleaserService {
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_releaser")
	public void setDao(ComplaintReleaserDao dao) {
		this.dao = dao;
	}

	@Override
	public ComplaintReleaserEntity getByUserId(Integer userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return dao.getByUserId(params);
	}

	@Override
	public String getDistinctCitys() {
		StringBuffer sb = new StringBuffer();
		List<String> citys = dao.getDistinctCitys();
		for (int i=0; i<citys.size(); i++) {
			sb.append(citys.get(i));
			if (i < citys.size()-1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
}
