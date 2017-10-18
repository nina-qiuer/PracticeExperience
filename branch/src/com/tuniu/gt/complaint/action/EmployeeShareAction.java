package com.tuniu.gt.complaint.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.tuniu.gt.complaint.entity.EmployeeShareEntity;
import com.tuniu.gt.complaint.service.IEmployeeShareService;
/**
 * 
 * @author weiweiqin create by 2010/4/13
 */

@SuppressWarnings("serial")
@Service("complaint_action-employee_share")
@Scope("prototype")
public class EmployeeShareAction extends
		FrmBaseAction<IEmployeeShareService, EmployeeShareEntity> {

	/**
	 * 通过投诉id查看员工承担人列表 页面
	 */
	private static final String EMPLOYEE_SHARE_LIST = "employee_share_list";
	/**
	 * 用于页面显示员工承担列表
	 */
	private List<EmployeeShareEntity> employeeShareEntityList;

	/**
	 * 投诉id
	 */
	private String complaintId;

	/**
	 * 页面中点击员工承担人详细 展示页面，页面输入参数 complaintId
	 * 
	 * @return
	 * @throws MySQLSyntaxErrorException
	 */
	public String getEmployeeSharesByCompId() throws MySQLSyntaxErrorException {
		this.employeeShareEntityList = service
				.getEmployeeListByComplaintId(this.complaintId);
		return EMPLOYEE_SHARE_LIST;
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-employee_share")
	public void setService(IEmployeeShareService service) {
		this.service = service;
	}

	public List<EmployeeShareEntity> getEmployeeShareEntityList() {
		return employeeShareEntityList;
	}

	public void setEmployeeShareEntityList(
			List<EmployeeShareEntity> employeeShareEntityList) {
		this.employeeShareEntityList = employeeShareEntityList;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

}
