package com.tuniu.gt.complaint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.GiftTypeDao;
import com.tuniu.gt.complaint.service.IGiftTypeService;
@Service("complaint_service_complaint_gift_type_impl-gift_type")
public class GiftTypeServiceImpl extends ServiceBaseImpl<GiftTypeDao> implements IGiftTypeService {
	@Autowired
	@Qualifier("complaint_dao_impl-gift_type")
	public void setDao(GiftTypeDao dao) {
		this.dao = dao;
	};
}
