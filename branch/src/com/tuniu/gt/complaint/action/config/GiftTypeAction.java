package com.tuniu.gt.complaint.action.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;

import com.tuniu.gt.complaint.entity.GiftTypeEntity;
import com.tuniu.gt.complaint.service.IGiftTypeService;


@Service("complaint_complaint_gift_type-gift_type")
@Scope("prototype")
public class GiftTypeAction extends FrmBaseAction<IGiftTypeService,GiftTypeEntity> { 
	
	public GiftTypeAction() {
		setManageUrl(manageUrl + "complaint/action/complaint/gift_type/gift_type");
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_gift_type_impl-gift_type")
	public void setDao(IGiftTypeService service) {
		this.service = service;
	};
	
	public String execute() {
		this.setPageTitle("表管理");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);  
		String res = super.execute(paramMap);
		request.setAttribute("search",search);
		return res;
	}
}
