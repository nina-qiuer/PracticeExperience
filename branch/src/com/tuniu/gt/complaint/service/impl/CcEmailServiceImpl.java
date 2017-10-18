package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.CcEmailCfgDao;
import com.tuniu.gt.complaint.dao.impl.CcEmailDao;
import com.tuniu.gt.complaint.entity.CcEmailCfgEntity;
import com.tuniu.gt.complaint.entity.CcEmailEntity;
import com.tuniu.gt.complaint.service.ICcEmailService;
import com.tuniu.gt.toolkit.CommonUtil;

@Service("complaint_service_impl-cc_email")
public class CcEmailServiceImpl extends ServiceBaseImpl<CcEmailDao> implements ICcEmailService {
	
	@Autowired
	@Qualifier("complaint_dao_impl-cc_email")
	public void setDao(CcEmailDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-cc_email_cfg")
	public CcEmailCfgDao cfgDao;
	
	@Override
	public int insert(Object obj) {
		CcEmailEntity entity = (CcEmailEntity) obj;
		String emailAddress = entity.getEmailAddress() + "@tuniu.com";
		entity.setEmailAddress(emailAddress);
		dao.insert(entity);
		
		List<CcEmailCfgEntity> cfgList = entity.getCfgList();
		for (CcEmailCfgEntity cfgEnt : cfgList) {
			if (0 != cfgEnt.getCompLevel()) {
				String orderStates = CommonUtil.arrToStr(cfgEnt.getOrderStateArr());
				String comeFroms = CommonUtil.arrToStr(cfgEnt.getComeFromArr());
				cfgEnt.setOrderStates(orderStates);
				cfgEnt.setComeFroms(comeFroms);
				cfgEnt.setCmid(entity.getId());
				cfgDao.insert(cfgEnt);
			}
		}
		return 0;
	}
	
	@Override
	public int delete(Object obj) {
		CcEmailEntity cc = (CcEmailEntity) obj;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cmid", cc.getId());
		cfgDao.delete(params);
		dao.delete(cc.getId());
		return 0;
	}

	@Override
	public List<String> getCcEmails(Map<String, Object> params) {
		return dao.getCcEmails(params);
	}

}
