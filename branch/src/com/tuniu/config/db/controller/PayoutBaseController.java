package com.tuniu.config.db.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.gt.complaint.entity.DataDictionaryConfigEntity;
import com.tuniu.gt.complaint.entity.PayoutBaseEntity;
import com.tuniu.gt.complaint.service.IPayoutBaseService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.uc.datastructure.TreeNode;

@Controller
@RequestMapping("/payout_base")
public class PayoutBaseController {

	@Autowired
	@Qualifier("complaint_service_complaint_impl-payout_base")
	private IPayoutBaseService payoutBaseService;

	@RequestMapping("/payoutBaseBill")
	public String payoutBaseBill(HttpServletRequest request) {
		String name = request.getParameter("name");
		request.setAttribute("parentname", name);
		return "payout_base/payout_base_bill";
	}
	
	@RequestMapping("/payoutBaseConfig")
	public String payoutBaseConfig(HttpServletRequest request) {
		return "payout_base/payout_base_config";
	}

	@RequestMapping("/getPayoutBaseData")
	@ResponseBody
	public List<TreeNode<PayoutBaseEntity>> getPayoutBaseData(HttpServletRequest request,
			HttpServletResponse response) {
		String fatherIdStr = request.getParameter("father_id");
		Integer father_id = fatherIdStr == null ? 0 : Integer.valueOf(fatherIdStr);
		List<TreeNode<PayoutBaseEntity>> payoutBaseTree = payoutBaseService.getPayoutBaseTree(father_id);
		return payoutBaseTree;
	}
	
	@RequestMapping("/deletePayoutBaseData")
	@ResponseBody
	public void deletePayoutBaseData(HttpServletRequest request,
			HttpServletResponse response) {
		String config_id = request.getParameter("config_id");
		PayoutBaseEntity payoutBase = new PayoutBaseEntity();
		payoutBase.setId(Integer.valueOf(config_id));
		payoutBase.setDelFlag(0);
		payoutBaseService.update(payoutBase);
	}
	
	@RequestMapping("/addOrUpdateConfigData")
	@ResponseBody
	public void addOrUpdateConfigData(HttpServletRequest request, PayoutBaseEntity payoutBaseEntity) {
		if (payoutBaseEntity.getId() != null) {
			payoutBaseService.update(payoutBaseEntity);
		} else {
			payoutBaseService.insert(payoutBaseEntity);
		}
	}
}
