package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ReasonTypeDao;
import com.tuniu.gt.complaint.service.IReasonTypeService;
@Service("complaint_service_complaint_impl-reason_type")
public class ReasonTypeServiceImpl extends ServiceBaseImpl<ReasonTypeDao> implements IReasonTypeService {
	@Autowired
	@Qualifier("complaint_dao_impl-reason_type")
	public void setDao(ReasonTypeDao dao) {
		this.dao = dao;
	}

    @Override
    public List<String> getChilderByParentName(String parentName) {
        return dao.getChilderByParentName(parentName);
    };
}
