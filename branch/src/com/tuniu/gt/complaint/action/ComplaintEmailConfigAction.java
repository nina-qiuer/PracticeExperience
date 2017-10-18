package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.common.BaseEntity;
import com.tuniu.gt.complaint.entity.ComplaintEmailConfigEntity;
import com.tuniu.gt.complaint.service.IComplaintEmailConfigService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.warning.common.Page;

import tuniu.frm.core.FrmBaseAction;

/**
 * @author Yu sir
 */

@Service("complaint_action-email_config")
@Scope("prototype")
public class ComplaintEmailConfigAction
		extends FrmBaseAction<IComplaintEmailConfigService, ComplaintEmailConfigEntity> {

	private static Logger logger = Logger.getLogger(ComplaintEmailConfigAction.class);

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_email_config")
	public void setService(IComplaintEmailConfigService service) {
		this.service = service;
	}

	@Autowired
	@Qualifier("tspService")
	private IComplaintTSPService tspService;

	private List<String> dealDepartments;

	private List<BaseEntity> productInfoList;

	public String execute() {
		List<String> authAgencyConfirmList = new ArrayList<String>(
				DBConfigManager.getConfigAsList("authority.agency.confirm"));
		authAgencyConfirmList.addAll(DBConfigManager.getConfigAsList("authority.audit.super"));
		if (authAgencyConfirmList.contains("" + user.getId())) {
			List<BaseEntity> classBrandParentList = tspService.getClassBrandParentFromBoh(null);
			request.setAttribute("classBrandParentList", classBrandParentList);
			return "email_config";
		} else {
			return "authority_not_enough";
		}
	}

	public String getEmailConfigData() {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String mail_type = request.getParameter("mail_type");
			String deal_depart = request.getParameter("deal_depart");
			String complaint_level = request.getParameter("complaint_level");
			String come_from = request.getParameter("come_from");
			String class_brand_parent_id = request.getParameter("class_brand_parent_id");
			paramMap.put("mail_type", mail_type);
			paramMap.put("deal_depart", deal_depart);
			paramMap.put("complaint_level", complaint_level);
			paramMap.put("come_from", come_from);
			paramMap.put("class_brand_parent_id", class_brand_parent_id);
			List<ComplaintEmailConfigEntity> emailConfigLists = (List<ComplaintEmailConfigEntity>) service
					.fetchList(paramMap);
			JsonUtil.writeListResponse(emailConfigLists);
		} catch (Exception e) {
			JsonUtil.writeErrorResponse();
		}
		return "email_config";
	}

	public String addOrEditConfig() {
		String id = request.getParameter("id");
		List<BaseEntity> classBrandParentList = tspService.getClassBrandParentFromBoh(null);
		request.setAttribute("classBrandParentList", classBrandParentList);
		if (id != null) {
			entity = (ComplaintEmailConfigEntity) service.get(id);
			Integer class_brand_parent_id = entity.getClass_brand_parent_id();
			if (class_brand_parent_id != -1) {
				List<BaseEntity> classBrandList = tspService.getClassBrandFromBoh(class_brand_parent_id);
				request.setAttribute("classBrandList", classBrandList);
				Integer class_brand_id = entity.getClass_brand_id();
				if (class_brand_id != -1) {
					List<BaseEntity> destClassList = tspService.getDestClassFormBoh(class_brand_id);
					request.setAttribute("destClassList", destClassList);
					Integer dest_class_id = entity.getProduct_new_line_type_id();
					if (dest_class_id != -1) {
						List<BaseEntity> destGroupList = tspService.getDestGroupInfoFromBoh(class_brand_id,
								dest_class_id);
						request.setAttribute("destGroupList", destGroupList);
					}
				}
			}
		}
		return "email_config_add";
	}

	public String getProductInfoListData() {
		try {
			Integer product_level = Integer.valueOf(request.getParameter("product_level"));
			Integer product_id = Integer.valueOf(request.getParameter("product_id"));
			switch (product_level) {
			case (1):
				productInfoList = tspService.getClassBrandParentFromBoh(product_id);
				break;
			case (2):
				productInfoList = tspService.getClassBrandFromBoh(product_id);
				break;
			case (3):
				productInfoList = tspService.getDestClassFormBoh(product_id);
				break;
			}
		} catch (Exception e) {

		}
		JsonUtil.writeListResponse(productInfoList);
		return "email_config_add";
	}

	public String getDestGroupData() {
		try {
			Integer type_id = Integer.valueOf(request.getParameter("type_id"));
			Integer dest_class_id = Integer.valueOf(request.getParameter("dest_class_id"));
			productInfoList = tspService.getDestGroupInfoFromBoh(type_id, dest_class_id);
		} catch (Exception e) {

		}
		JsonUtil.writeListResponse(productInfoList);
		return "email_config_add";
	}

	public String submintEmailConfig() {
		try {
			if (entity.getId() != null) {
				entity.setUpdate_id(user.getId());
				service.update(entity);
			} else {
				entity.setAdd_id(user.getId());
				service.insert(entity);
			}
			JsonUtil.writeResponse("success");
		} catch (Exception e) {
			logger.error("submintEmailConfig error" + e.getMessage(), e);
			JsonUtil.writeResponse("failed");
		}
		return "email_config_add";
	}

	public String delEmailConfig() {
		try {
			entity.setUpdate_id(user.getId());
			entity.setDel_flag(1);// 设置已删除
			service.update(entity);
			JsonUtil.writeResponse("success");
		} catch (Exception e) {
			logger.error("delEmailConfig error" + e.getMessage(), e);
			JsonUtil.writeResponse("failed");
		}
		return "email_config_add";
	}

	public List<String> getDealDepartments() {
		return DBConfigManager.getConfigAsList("sys.dealDepart");
	}

	public void setDealDepartments(List<String> dealDepartments) {
		this.dealDepartments = dealDepartments;
	}

	public List<BaseEntity> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(List<BaseEntity> productInfoList) {
		this.productInfoList = productInfoList;
	}
}
