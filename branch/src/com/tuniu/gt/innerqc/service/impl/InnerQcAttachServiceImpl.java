package com.tuniu.gt.innerqc.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.innerqc.dao.impl.InnerQcAttachDao;
import com.tuniu.gt.innerqc.entity.InnerQcAttachEntity;
import com.tuniu.gt.innerqc.service.InnerQcAttachService;


@Service("innerqc_service_impl-inner_qc_attach")
public class InnerQcAttachServiceImpl extends ServiceBaseImpl<InnerQcAttachDao> implements InnerQcAttachService {
	
	@Autowired
	@Qualifier("innerqc_dao_impl-inner_qc_attach")
	public void setDao(InnerQcAttachDao dao) {
		this.dao = dao;
	}

	@Override
	public void addAttach(Integer iqcId, File file, String fileName) {
		String url = ComplaintRestClient.uploadFile(file, fileName);
		
		InnerQcAttachEntity iqcAttach = new InnerQcAttachEntity();
		iqcAttach.setIqcId(iqcId);
		iqcAttach.setName(fileName);
		iqcAttach.setPath(url);
		dao.insert(iqcAttach);
	}

}
