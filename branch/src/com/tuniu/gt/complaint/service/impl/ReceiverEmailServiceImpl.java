package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ReceiverEmailDao;
import com.tuniu.gt.complaint.entity.ReceiverEmailEntity;
import com.tuniu.gt.complaint.service.IReceiverEmailService;
@Service("complaint_service_impl-receiver_email")
public class ReceiverEmailServiceImpl extends ServiceBaseImpl<ReceiverEmailDao> implements IReceiverEmailService {
	@Autowired
	@Qualifier("complaint_dao_impl-receiver_email")
	public void setDao(ReceiverEmailDao dao) {
		this.dao = dao;
	}

	@Override
	public List<ReceiverEmailEntity> getListByType(int type, int receiverType,int orderState) {
		if(type>0){
			Map<String,Object> paramMap = new HashMap<String,Object>();
			if (-1 != receiverType) {
				paramMap.put("receiverType", receiverType);			
						}
			if (-1 != orderState) {
				paramMap.put("orderState", orderState);
			}
			
			this.fetchList(paramMap);
			return (List<ReceiverEmailEntity>) this.fetchList(paramMap);
		}else{
			return null;
		}
	}
}
