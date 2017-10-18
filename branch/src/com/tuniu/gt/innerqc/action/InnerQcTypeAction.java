package com.tuniu.gt.innerqc.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.innerqc.entity.InnerQcTypeEntity;
import com.tuniu.gt.innerqc.service.InnerQcTypeService;
import com.tuniu.gt.toolkit.CommonUtil;


@Service("innerqc_action-inner_qc_type")
@Scope("prototype")
public class InnerQcTypeAction extends FrmBaseAction<InnerQcTypeService, InnerQcTypeEntity> {

	private static final long serialVersionUID = 1L;
	
	public InnerQcTypeAction() {
		setManageUrl("inner_qc_type");
	}
	
	@Autowired
	@Qualifier("innerqc_service_impl-inner_qc_type")
	public void setService(InnerQcTypeService service) {
		this.service = service;
	};
	
	private List<InnerQcTypeEntity> typeList;
	
	public List<InnerQcTypeEntity> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<InnerQcTypeEntity> typeList) {
		this.typeList = typeList;
	}

	public String execute() {
		typeList = service.getIqcTypeList();
		
		return "inner_qc_type_list";
	}
	
	public String add() {
		service.insert(entity);
		CommonUtil.writeResponse(entity.getId());
		return "inner_qc_type_list";
	}
	
	public String update() {
		service.update(entity);
		return "inner_qc_type_list";
	}
	
}
