package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.CcEmailEntity;
import com.tuniu.gt.complaint.service.ICcEmailService;

@Service("complaint_action-cc_email")
@Scope("prototype")
public class CcEmailAction extends FrmBaseAction<ICcEmailService, CcEmailEntity> {  

	private static final long serialVersionUID = 1L;
	
	private List<CcEmailEntity> ccEmailList = new ArrayList<CcEmailEntity>();

	public List<CcEmailEntity> getCcEmailList() {
		return ccEmailList;
	}

	public void setCcEmailList(List<CcEmailEntity> ccEmailList) {
		this.ccEmailList = ccEmailList;
	}

	public CcEmailAction() {
		setManageUrl("cc_email");
	}
	
	@Autowired
	@Qualifier("complaint_service_impl-cc_email")
	public void setService(ICcEmailService service) {
		this.service = service;
	}
	
	@SuppressWarnings("unchecked")
	public String execute() {
		this.setPageTitle("基础配置");
		ccEmailList = (List<CcEmailEntity>) service.fetchList();
		return "cc_email_list";
	}
	
	
	/**
	* 跳转到添加页面
	*/
	public String toAdd() {
		return "cc_email_add";
	}
	
	public String add() {
		service.insert(entity);
		return "cc_email_add";
	}
	
	public String delete() {
		service.delete(entity);
		return execute();
	}
	
}
