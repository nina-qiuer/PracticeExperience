package com.tuniu.gt.complaint.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.PayoutBaseEntity;
import com.tuniu.gt.complaint.entity.ReasonTypeEntity;
import com.tuniu.gt.complaint.service.IPayoutBaseService;


/**
* @ClassName: PayoutBaseAction
* @Description:投诉事宜action
* @author hucunzhen
* @date 2013-6-20
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_action-payout_base")
@Scope("prototype")
public class PayoutBaseAction extends FrmBaseAction<IPayoutBaseService,PayoutBaseEntity> { 
	
	public PayoutBaseAction() {
		setManageUrl("payout_base");
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-payout_base")
	public void setService(IPayoutBaseService service) {
		this.service = service;
	}
	
	public String execute() {
		this.setPageTitle("赔付理据");
		
		 String name = request.getParameter("name");
		Map<String, List<PayoutBaseEntity>> twoTypeMap = new LinkedHashMap<String, List<PayoutBaseEntity>>();//存放二级分类，key--二级分类father_id，list--同一个一级分类的二级分类list
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//获取一级分类数据
		int fatherId = 0;
		paramMap.put("fatherId", fatherId);
		List<PayoutBaseEntity> payoutbaseList = (List<PayoutBaseEntity>) service.fetchList(paramMap);
		
		//循环获取一级分类对应的二级分类数据
		for (int i = 0; i < payoutbaseList.size(); i++) {
			fatherId = payoutbaseList.get(i).getId();
			paramMap.clear();
			paramMap.put("fatherId", fatherId);
			List<PayoutBaseEntity> twoTypeList = (List<PayoutBaseEntity>) service.fetchList(paramMap);
			twoTypeMap.put(payoutbaseList.get(i).getPayoutBase(), twoTypeList);
		}
		
		request.setAttribute("payoutbaseList", payoutbaseList);
		request.setAttribute("twoTypeMap", twoTypeMap);
		request.setAttribute("name", "\'"+name+"\'");
		jspTpl = "edit_payout_base";
		return jspTpl;
	}


}
