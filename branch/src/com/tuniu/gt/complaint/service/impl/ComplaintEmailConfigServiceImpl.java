package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.dao.impl.ComplaintEmailConfigDao;
import com.tuniu.gt.complaint.entity.ComplaintEmailConfigEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintEmailConfigService;

import tuniu.frm.core.ServiceBaseImpl;

@Service("complaint_service_complaint_impl-complaint_email_config")
public class ComplaintEmailConfigServiceImpl extends ServiceBaseImpl<ComplaintEmailConfigDao>
		implements IComplaintEmailConfigService {
	private static Logger logger = Logger.getLogger(ComplaintEmailConfigServiceImpl.class);

	@Autowired
	@Qualifier("complaint_dao_impl-complaint_email_config")
	public void setDao(ComplaintEmailConfigDao dao) {
		this.dao = dao;
	}

	public Integer getEmailConfigCount(Map<String, Object> paramMap){
		return dao.getEmailConfigCount(paramMap);
	}
	
	public List<String> getLaunchReEmails(ComplaintEntity complaint) {
		Map<String, Object> paramMap = getParameterFromComplaint(complaint,Constans.MAIL_TYPE_RECEIVE);
		return getEmailListByParameter(paramMap);
	}
	
	public List<String> getLaunchCcEmails(ComplaintEntity complaint) {
		Map<String, Object> paramMap = getParameterFromComplaint(complaint,Constans.MAIL_TYPE_CARBON_COPY);
		return getEmailListByParameter(paramMap);
	}
	
	//从boh查询到的类目信息传入投诉单对象，根据此对象获取查询邮件参数
	private Map<String, Object> getParameterFromComplaint(ComplaintEntity complaint,Integer mailType){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deal_depart", complaint.getDealDepart());
		paramMap.put("complaint_level", complaint.getLevel());
		paramMap.put("come_from", complaint.getComeFrom());
		paramMap.put("class_brand_parent_id", complaint.getClassBrandParentId());
		paramMap.put("class_brand_id", complaint.getClassBrandId());
		paramMap.put("product_new_line_type_id", complaint.getProductNewLineTypeId());
		paramMap.put("dest_group_id", complaint.getDestGroupId());
		paramMap.put("mail_type", mailType);//收件人
		return paramMap;
	}
	
	//根据条件获取需要发送的邮件集合
	private List<String> getEmailListByParameter(Map<String, Object> paramMap){
		List<String> emailList = new ArrayList<String>();
		List<ComplaintEmailConfigEntity> emailConfigLists = (List<ComplaintEmailConfigEntity>) fetchList(paramMap);
		for(ComplaintEmailConfigEntity complaintConfig:emailConfigLists){
			String reEmailStr = complaintConfig.getEmails();
			String[] reEmailArr = reEmailStr.split(",");
			for(String reEmail:reEmailArr){
				emailList.add(reEmail);
			}
		}
		return emailList;
	}
}
