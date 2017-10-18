package com.tuniu.gt.complaint.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.GiftDao;
import com.tuniu.gt.complaint.entity.GiftEntity;
import com.tuniu.gt.complaint.service.IGiftService;
@Service("complaint_service_complaint_impl-gift")
public class GiftServiceImpl extends ServiceBaseImpl<GiftDao> implements IGiftService {
	@Autowired
	@Qualifier("complaint_dao_impl-gift")
	public void setDao(GiftDao dao) {
		this.dao = dao;
	};
	
	@Override
	public List<GiftEntity> searchByComplaintId(int id) {
		List<GiftEntity> gift = this.dao.searchByComplaintId(id);
		return gift;
	}
}
