package com.tuniu.gt.warning.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.util.PageUtil;
import com.tuniu.gt.sms.entity.SmsSendRecordEntity;
import com.tuniu.gt.sms.service.ISmsSendRecordService;
import com.tuniu.gt.warning.common.EarlyWarningOrderPage;
import com.tuniu.gt.warning.dao.impl.EarlyWarningOrderDao;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;
import com.tuniu.gt.warning.entity.EwOrderAgencyEntity;
import com.tuniu.gt.warning.entity.EwOrderFlightEntity;
import com.tuniu.gt.warning.service.IEarlyWarningOrderService;

@Service("warning_service_impl-early_warning_order")
public class EarlyWarningOrderServiceImpl extends ServiceBaseImpl<EarlyWarningOrderDao> implements IEarlyWarningOrderService {
	
	@Autowired
	@Qualifier("warning_dao_impl-early_warning_order")
	public void setDao(EarlyWarningOrderDao dao) {
		this.dao = dao;
	}
	
	@Autowired
    @Qualifier("smsSendRecord_service_smsSendRecord_impl-sms_send_record")
    private ISmsSendRecordService smsSendRecordService;

	@SuppressWarnings("unchecked")
	@Override
	public EarlyWarningOrderPage getEwoPage(EarlyWarningOrderPage ewoPage) {
		int totalRecords = dao.getEwoNum(ewoPage);
		if (totalRecords > 0) {
			int totalPages = PageUtil.getTotalPages(totalRecords);
			int pageNo = PageUtil.processPageNo(ewoPage.getPageNo(), totalPages);;
			ewoPage.setTotalRecords(totalRecords);
			ewoPage.setTotalPages(totalPages);
			ewoPage.setPageNo(pageNo);
			ewoPage.setPageSize(PageUtil.PAGE_SIZE);
			ewoPage.setStart(PageUtil.getStartPosition(pageNo));
			List<EarlyWarningOrderEntity> ewoList = dao.getEwoList(ewoPage);
			if (!ewoList.isEmpty()) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				for (EarlyWarningOrderEntity ewo : ewoList) {
					List<EwOrderFlightEntity> flightList = dao.getFlightList(ewo.getId());
					ewo.setFlightList(flightList);
					
					List<EwOrderAgencyEntity> agencyList = dao.getAgencyList(ewo.getId());
					ewo.setAgencyList(agencyList);
					
					paramMap.clear();
					paramMap.put("businessId", ewo.getId());
					paramMap.put("type", 2);
					List<SmsSendRecordEntity> smsList = (List<SmsSendRecordEntity>) smsSendRecordService.fetchList(paramMap);
					ewo.setSmsList(smsList);
				}
			}
			ewoPage.setEwoList(ewoList);
		}
		return ewoPage;
	}

	@Override
	public List<EarlyWarningOrderEntity> getEwoList(EarlyWarningOrderPage ewoPage) {
		return dao.getEwoList(ewoPage);
	}

	@Override
	public void toggleEwo(String idsStr, int toggleFlag) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idsStr", idsStr);
		params.put("toggleFlag", toggleFlag);
		dao.toggleEwo(params);
	}

	@Override
	public void insertFlight(EwOrderFlightEntity flight) {
		dao.insertFlight(flight);
	}

	@Override
	public void insertAgency(EwOrderAgencyEntity agency) {
		dao.insertAgency(agency);
	}

}
