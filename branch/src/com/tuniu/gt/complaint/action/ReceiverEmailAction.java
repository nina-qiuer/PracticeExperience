package com.tuniu.gt.complaint.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.entity.ReceiverEmailEntity;
import com.tuniu.gt.complaint.service.IReceiverEmailService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;

import tuniu.frm.core.FrmBaseAction;


/**
* @ClassName: ReceiverEmailAction
* @Description:邮件收件人设置action
* @author Ocean Zhong
* @date 2012-1-30 上午10:11:48
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_action-receiver_email")
@Scope("prototype")
public class ReceiverEmailAction extends FrmBaseAction<IReceiverEmailService,ReceiverEmailEntity> {  
	
	private Logger logger = Logger.getLogger(getClass());
	
	private IDepartmentService departmentService;
	int type;
	
	int orderState;
	private String jsonDepartmentTree;
	
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	//用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public String getStrDepartmentTree() {
		return jsonDepartmentTree;
	}

	public void setStrDepartmentTree(String strDepartmentTree) {
		this.jsonDepartmentTree = strDepartmentTree;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getOrderState() {
		return orderState;
	}

	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	public ReceiverEmailAction() {
		setManageUrl("receiver_email");
	}
	
	@Autowired
	@Qualifier("complaint_service_impl-receiver_email")
	public void setService(IReceiverEmailService service) {
		this.service = service;
	};
	
	public String execute() {
		
		this.setPageTitle("基础配置");

		// 根据类型分别获取1~5级收件人列表和质检报告收件人列表
		List<ReceiverEmailEntity> emailList1 = service.getListByType(1, 0,0);
		List<ReceiverEmailEntity> emailList2 = service.getListByType(2, 0,0);
		List<ReceiverEmailEntity> emailList3 = service.getListByType(3, 0,0);
		//List<ReceiverEmailEntity> emailList4 = service.getListByType(4);
		//List<ReceiverEmailEntity> emailList5 = service.getListByType(5);
		List<ReceiverEmailEntity> emailList6 = service.getListByType(6, 0,0);
		//微博，网络投诉收件人
		List<ReceiverEmailEntity> emailList7 = service.getListByType(7, 0,0);
		
		List<ReceiverEmailEntity> emailListBefore1 = service.getListByType(1, 0,1);
		List<ReceiverEmailEntity> emailListBefore2 = service.getListByType(2, 0,1);
		List<ReceiverEmailEntity> emailListBefore3 = service.getListByType(3, 0,1);
		
		List<ReceiverEmailEntity> emailListIn1 = service.getListByType(1, 0,2);
		List<ReceiverEmailEntity> emailListIn2 = service.getListByType(2, 0,2);
		List<ReceiverEmailEntity> emailListIn3 = service.getListByType(3, 0,2);
		List<ReceiverEmailEntity> emailListAfter1 = service.getListByType(1, 0,3);
		List<ReceiverEmailEntity> emailListAfter2 = service.getListByType(2, 0,3);
		List<ReceiverEmailEntity> emailListAfter3 = service.getListByType(3, 0,3);
		//马代采购人员邮件配置
		List<ReceiverEmailEntity> madaiPurchase8 = service.getListByType(8, 0,0);
		
		request.setAttribute("emailList1", emailList1);
		request.setAttribute("emailList2", emailList2);
		request.setAttribute("emailList3", emailList3);
		//request.setAttribute("emailList4", emailList4);
		//request.setAttribute("emailList5", emailList5);
		request.setAttribute("emailList6", emailList6);
		request.setAttribute("emailList7", emailList7);
		request.setAttribute("emailListIn1", emailListIn1);
		request.setAttribute("emailListIn2", emailListIn2);
		request.setAttribute("emailListIn3", emailListIn3);
		request.setAttribute("emailListAfter1", emailListAfter1);
		request.setAttribute("emailListAfter2", emailListAfter2);
		request.setAttribute("emailListAfter3", emailListAfter3);
		request.setAttribute("emailListBefore1", emailListBefore1);
		request.setAttribute("emailListBefore2", emailListBefore2);
		request.setAttribute("emailListBefore3", emailListBefore3);
		request.setAttribute("madaiPurchase8", madaiPurchase8);
		
		//取1-3级邮件组
		List<ReceiverEmailEntity> emailGroupList1 = service.getListByType(1, 1,0);
		List<ReceiverEmailEntity> emailGroupList2 = service.getListByType(2, 1,0);
		List<ReceiverEmailEntity> emailGroupList3 = service.getListByType(3, 1,0);
		List<ReceiverEmailEntity> emailGroupList6 = service.getListByType(6, 1,0);
		//微博，网络
		List<ReceiverEmailEntity> emailGroupList7 = service.getListByType(7, 1,0);
		List<ReceiverEmailEntity> emailListInGroup1 = service.getListByType(1, 1,2);
		List<ReceiverEmailEntity> emailListInGroup2 = service.getListByType(2, 1,2);
		List<ReceiverEmailEntity> emailListInGroup3 = service.getListByType(3, 1,2);
		List<ReceiverEmailEntity> emailListAfterGroup1 = service.getListByType(1, 1,3);
		List<ReceiverEmailEntity> emailListAfterGroup2 = service.getListByType(2, 1,3);
		List<ReceiverEmailEntity> emailListAfterGroup3 = service.getListByType(3, 1,3);
		List<ReceiverEmailEntity> emailListBeforeGroup1 = service.getListByType(1, 1,1);
		List<ReceiverEmailEntity> emailListBeforeGroup2 = service.getListByType(2, 1,1);
		List<ReceiverEmailEntity> emailListBeforeGroup3 = service.getListByType(3, 1,1);
		//马代采购人员邮件配置
		List<ReceiverEmailEntity> madaiPurchaseGroup8 = service.getListByType(8, 1,0);
		
		request.setAttribute("emailGroupList1", emailGroupList1);
		request.setAttribute("emailGroupList2", emailGroupList2);
		request.setAttribute("emailGroupList3", emailGroupList3);
		request.setAttribute("emailGroupList6", emailGroupList6);
		request.setAttribute("emailGroupList7", emailGroupList7);
		request.setAttribute("emailListInGroup1", emailListInGroup1);
		request.setAttribute("emailListInGroup2", emailListInGroup2);
		request.setAttribute("emailListInGroup3", emailListInGroup3);
		request.setAttribute("emailListAfterGroup1", emailListAfterGroup1);
		request.setAttribute("emailListAfterGroup2", emailListAfterGroup2);
		request.setAttribute("emailListAfterGroup3", emailListAfterGroup3);
		request.setAttribute("emailListBeforeGroup1", emailListBeforeGroup1);
		request.setAttribute("emailListBeforeGroup2", emailListBeforeGroup2);
		request.setAttribute("emailListBeforeGroup3", emailListBeforeGroup3);
		request.setAttribute("madaiPurchaseGroup8", madaiPurchaseGroup8);
		
		jspTpl = "receiver_email_list";
		return jspTpl;
	}
	
	/**
	 * 添加邮件组
	 * @return
	 */
	public String addGroup(){
		
		return "receiver_email_group";
	}
	/**
	 * 添加邮件组
	 * @return
	 */
	public String doAddGroup(){
		entity.setType(type);
		entity.setOrderState(orderState);
		entity.setDelFlag(1);// 1未删除
		entity.setReceiverType(1);
		String groupEmail = this.getRequestParam("group_email");
		if (groupEmail != null && !groupEmail.equals("")) {
			groupEmail += "@tuniu.com";
		}
		entity.setUserMail(groupEmail);
		entity.setDepartmentName(this.getRequestParam("group_email_name"));
		//添加
		service.insert(entity);
		return this.execute();
	}
	
	/**
	* 跳转到添加页面
	* @return    
	* String    
	* @throws
	*/
	public String add(){
		// 读取部门列表
		@SuppressWarnings("unchecked")
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService
				.fetchList();

		DepartmentEntity department;
		String field;
		int i = 0;

		// 将部门列表转换成JOSN数据
		jsonDepartmentTree = "[";
		for (i = 0; i < departments.size() - 1; i++) {
			department = departments.get(i);
			field = "{id:" + department.getId() + ", pId:"
					+ department.getFatherId() + ", name:\""
					+ department.getDepName() + "\"},\n";
			jsonDepartmentTree += field;
		}
		// last one
		department = departments.get(i);
		field = "{id:" + department.getId() + ", pId:"
				+ department.getFatherId() + ", name:\""
				+ department.getDepName() + "\"}";
		jsonDepartmentTree += field + "]";

		String type = request.getParameter("type");
		String orderState = request.getParameter("orderState");
		request.setAttribute("type", type);
		request.setAttribute("orderState", orderState);
		jspTpl="receiver_email_edit";
		return jspTpl;
	}
	
	/**
	* 保存邮件收件人的信息
	* @return    
	* String    
	* @throws
	*/
	public String doAdd(){
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		entity.setType(type);
		entity.setOrderState(orderState);
		entity.setDelFlag(1);// 1未删除
		sqlParams.put("id", entity.getUserId());
		//获取添加用户邮箱
		UserEntity userEntity = (UserEntity) userService.get(sqlParams);
		if(userEntity != null){
			entity.setUserMail(userEntity.getEmail());
		}
		
		try {
			sqlParams.clear();
			sqlParams.put("userId", entity.getUserId());
			sqlParams.put("type", type);
			sqlParams.put("orderState", orderState);
			@SuppressWarnings("unchecked")
			List<AppointManagerEntity> apmList = (List<AppointManagerEntity>) service.fetchList(sqlParams);
			if (apmList == null || apmList.size() == 0) { // 该用户已经不存在
				service.insert(entity);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return this.execute(); // update list
	}
	
	/**
	* 删除一条数据
	* @return    
	* String    
	* @throws
	*/
	public String del(){
		
		String id = request.getParameter("id");
		if (id != null) {
			entity = (ReceiverEmailEntity) service.get(id);//根据id获取对象
			entity.setDelFlag(0);//设置删除标示位为0
			service.update(entity);
		}
		
		return this.execute();
	}
}
