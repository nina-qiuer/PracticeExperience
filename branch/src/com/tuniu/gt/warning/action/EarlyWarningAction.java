package com.tuniu.gt.warning.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.vo.ComplaintSearchVo;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.warning.common.EarlyWarningOrderPage;
import com.tuniu.gt.warning.entity.EarlyWarningEntity;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;
import com.tuniu.gt.warning.entity.EwOrderAgencyEntity;
import com.tuniu.gt.warning.entity.EwOrderFlightEntity;
import com.tuniu.gt.warning.entity.WetherCodeEntity;
import com.tuniu.gt.warning.service.IEarlyWarningOrderService;
import com.tuniu.gt.warning.service.IEarlyWarningService;
import com.tuniu.gt.warning.service.IWetherCodeService;


@Service("warning_action-early_warning")
@Scope("prototype")
public class EarlyWarningAction extends FrmBaseAction<IEarlyWarningService, EarlyWarningEntity>{

    private static final long serialVersionUID = 6024405611986764006L;
    
    @Autowired
	@Qualifier("warning_service_impl-early_warning")
	public void setService(IEarlyWarningService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("warning_service_impl-wether_code")
	private IWetherCodeService wetherCodeService;
	
	@Autowired
	@Qualifier("warning_service_impl-early_warning_order")
	private IEarlyWarningOrderService ewoService;
	
	private EarlyWarningOrderPage page;
	
	private String[] fileds = new String[]{"orderIdT","routeNameT","destCategoryT","orderTypeT","startDateT","backDateT","startCityT","backCityT","touristNumT","contactNameT","contactPhoneT","agencyT"};
    
    private ComplaintSearchVo search;
    
	public String[] getFileds() {
		return fileds;
	}

	public void setFileds(String[] fileds) {
		this.fileds = fileds;
	}

	public EarlyWarningOrderPage getPage() {
		return page;
	}

	public void setPage(EarlyWarningOrderPage page) {
		this.page = page;
	}

	public ComplaintSearchVo getSearch() {
		return search;
	}

	public void setSearch(ComplaintSearchVo search) {
		this.search = search;
	}
	
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	public EarlyWarningAction() {
		setManageUrl("early_warning");
	}
	
	public String execute() {
		Map<String, String> searchMap = new HashMap<String, String>();
		if (null != search) {
			searchMap = search.toMap();
		}
		paramMap.putAll(searchMap);
		super.execute(paramMap);
		
		return "early_warning_list";
	}
	
	@SuppressWarnings("unchecked")
	public String toWetherKeywordSet() {
		List<WetherCodeEntity> wcList = (List<WetherCodeEntity>) wetherCodeService.fetchList(paramMap);
		request.setAttribute("wcList", wcList);
		return "wether_keyword_set";
	}
	
	public String wetherKeywordSet() {
		paramMap.put("id", request.getParameter("id"));
		paramMap.put("keyWordFlag", request.getParameter("keyWordFlag"));
		wetherCodeService.keywordSet(paramMap);
		return "wether_keyword_set";
	}
	
	public String toAdd() {
		if (null != page) {
			page.setPageSize(30);
			ComplaintRestClient.queryOrderBatch(page);
		}
		
		request.setAttribute("fieldStr", CommonUtil.arrToStr(fileds));
		
		return "early_warning_add";
	}
	
	public String createEarlyWarning() {
		List<EarlyWarningOrderEntity> ewoList = new ArrayList<EarlyWarningOrderEntity>();
		if ("searched".equals(page.getCreateScope())) {
			page.setPageNo(0);
			page.setPageSize(0);
			ComplaintRestClient.queryOrderBatch(page);
			ewoList = page.getEwoList();
		}
		
		/*
		else if ("checked".equals(page.getCreateScope())) {
			EarlyWarningOrderPage ewoPage2 = new EarlyWarningOrderPage();
			ewoPage2.setStart(-1);
			//ewoPage2.setIdsStr(CommonUtil.arrToStr(ids));
			
			//orderList = (List<EarlyWarningOrderEntity>) service.getEwoList(ewoPage2);
			
		}*/
		
		String succ = "false";
		if (null != ewoList && ewoList.size() > 0) {
			entity.setAddPerson(user.getRealName());
			service.insert(entity);
			
			for (EarlyWarningOrderEntity ewo : ewoList) {
				ewo.setEwId(entity.getId());
				ewoService.insert(ewo);
				for (EwOrderFlightEntity flight : ewo.getFlightList()) {
					flight.setEwOrderId(ewo.getId());
					ewoService.insertFlight(flight);
				}
				for (EwOrderAgencyEntity agency : ewo.getAgencyList()) {
					agency.setEwOrderId(ewo.getId());
					ewoService.insertAgency(agency);
				}
			}
			
			succ = "true";
		}
		
		CommonUtil.writeResponse(succ);
		return "early_warning_add";
	}
	
	public String toUpdateEarlyWarning() {
		entity = (EarlyWarningEntity) service.get(entity.getId());
		return "early_warning_update";
	}
	
	public String updateEarlyWarning() {
		service.update(entity);
		return "early_warning_update";
	}
	
}
