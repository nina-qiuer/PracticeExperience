package com.tuniu.gt.complaint.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;

/**
 * @ClassName: AppointManagerAction
 * @Description:分配负责人处理类
 * @author Ocean Zhong
 * @date 2012-1-29 下午2:29:24
 * @version 1.0 Copyright by Tuniu.com
 */
@Service("complaint_action-appoint_manager")
@Scope("prototype")
public class AppointManagerAction extends
		FrmBaseAction<IAppointManagerService, AppointManagerEntity> {
	private static final long serialVersionUID = -8752665545891633921L;
	
	Logger logger = Logger.getLogger(getClass());

	private IDepartmentService departmentService;
	int type;
	private String jsonDepartmentTree;
	
	private Map<String,Object> info;

	public AppointManagerAction() {
		setManageUrl("appoint_manager");
		info = new HashMap<String, Object>();
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-appoint_manager")
	public void setService(IAppointManagerService service) {
		this.service = service;
	};

	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

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

	/**
	 * 默认打开指派负责人列表
	 */
	public String execute() {
        //this.setPageTitle("基础设置");

        // 获取各种负责人列表，后台类型值分别是1,2,3,4对应售前，出游中，售后和质检
        //如果当前登录用户属于供应商质量组，则前台页面显示售前客服负责人选项
//        if (user.getDepId() == 1083) {
//            request.setAttribute("showbeforeList", "1");
//        }
        List<AppointManagerEntity> beforeList = service.getListByType(AppointManagerEntity.PRE_SALES_OFFICER);
        List<AppointManagerEntity> middleList = service.getListByType(AppointManagerEntity.TOURING_OFFICER);
        List<AppointManagerEntity> afterList = service.getListByType(AppointManagerEntity.POST_SALES_OFFICER);
        List<AppointManagerEntity> airTicketList = service.getListByType(AppointManagerEntity.AIR_TICKIT_OFFICER);
        List<AppointManagerEntity> hotelList = service.getListByType(AppointManagerEntity.HOTEL_OFFICER);
        List<AppointManagerEntity> trafficList = service.getListByType(AppointManagerEntity.TRAFFIC_OFFICER);
        List<AppointManagerEntity> distributeList = service.getListByType(AppointManagerEntity.DISTRIBUTE_OFFICER);
        List<AppointManagerEntity> qtList = service.getListByType(AppointManagerEntity.QC_OFFICER);
        List<AppointManagerEntity> departmentList = service.getListByType(AppointManagerEntity.ALLOW_DEPARTMENT);
        List<AppointManagerEntity> complaintDepList = service.getListByType(AppointManagerEntity.COMPLAINT_DEPARTMENT);
        List<AppointManagerEntity> middleTransferList = service.getListByType(AppointManagerEntity.TOURING_OFFICER_TRANSFER);
        List<AppointManagerEntity> aftTransferList = service.getListByType(AppointManagerEntity.AFT_OFFICER_TRANSFER);
        List<AppointManagerEntity> devList = service.getListByType(AppointManagerEntity.DEV_OFFICER);
        List<AppointManagerEntity> specialBeforeList = service.getListByType(AppointManagerEntity.SPECIAL_BEFORE_OFFICER);
        List<AppointManagerEntity> returnVisitList = service.getListByType(AppointManagerEntity.MSG_RETURN_VISIT);
        
        List<AppointManagerEntity> vipDepartGroupList = service.getVipDepartGroupList(AppointManagerEntity.VIP_DEPART_GROUP);
        List<AppointManagerEntity> vipDepartOffierList = service.getListByType(AppointManagerEntity.VIP_DEPART_OFFICER);
		List<Integer> devDepartMentList = new ArrayList<Integer>();
		for(AppointManagerEntity ame : devList){
			devDepartMentList.add(ame.getDepartmentId());
		}
		if(devDepartMentList.contains(user.getDepId())){
			request.setAttribute("showbeforeList", "1");
			request.setAttribute("showVipDepart", true);
			request.setAttribute("returnVisitList", returnVisitList);
			request.setAttribute("vipDepartGroupList", vipDepartGroupList);
			request.setAttribute("vipDepartOffierList", vipDepartOffierList);
			request.setAttribute("devList", devList);
			request.setAttribute("qtList", qtList);
		}
		
		List<String> vipDepartGroupManagerList = new ArrayList<String>(DBConfigManager.getConfigAsList("vip.depart.group"));
		if(vipDepartGroupManagerList.contains(""+user.getId())){
			request.setAttribute("showVipDepart", true);
			request.setAttribute("vipDepartGroupList", vipDepartGroupList);
			request.setAttribute("vipDepartOffierList", vipDepartOffierList);
		}

        request.setAttribute("beforeList", beforeList);
        request.setAttribute("middleList", middleList);
        request.setAttribute("afterList", afterList);
        request.setAttribute("airTicketList", airTicketList);
        request.setAttribute("hotelList", hotelList);
        request.setAttribute("trafficList", trafficList);
        request.setAttribute("distributeList", distributeList);
        request.setAttribute("departmentList", departmentList);
        request.setAttribute("complaintDepList", complaintDepList);
        request.setAttribute("middleTransferList", middleTransferList);
        request.setAttribute("aftTransferList", aftTransferList);
        request.setAttribute("specialBeforeList", specialBeforeList);
        
        jspTpl = "appoint_manager_list";
        return jspTpl;
    }

	/**
	 * 跳转到添加页面
	 * 
	 * @return String
	 * @throws
	 */
	public String add() {
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
		request.setAttribute("type", type);
		jspTpl = "appoint_manager_edit";
		return jspTpl;
	}

	/**
	 * 
	 * 保存负责人
	 * 
	 * @return String
	 * @throwsk
	 */
	public String doAdd() {
		entity.setType(type);
		entity.setDelFlag(0);// 1未删除

		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("userId", entity.getUserId());
		sqlParams.put("type", type);
		try {
			@SuppressWarnings("unchecked")
			List<AppointManagerEntity> apmList = (List<AppointManagerEntity>) service.fetchList(sqlParams);
			if (apmList == null || apmList.size() == 0) { // 该用户已经不存在
				service.insert(entity);
                info.put("success", true);
			}
		} catch (Exception e) {
			logger.error("保存负责人失败", e);
			info.put("success", false);
		}
		
        return "info";
	}

	/**
	 * 删除负责人
	 * 
	 * @return String
	 * @throws
	 */
	public String del() {

		String id = request.getParameter("id");
		info.put("success", false);
		if (id != null) {
			entity = (AppointManagerEntity) service.get(id);
			entity.setDelFlag(1);// 设置删除标示位为0
			service.update(entity);
            info.put("success", true);
		}
        
		return "info";
	}
	

	/**
	 * 添加投诉处理部门
	 * @return
	 */
	public String addDepartment(){
		// 读取部门列表
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService.fetchList();

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
		
		int type = AppointManagerEntity.COMPLAINT_DEPARTMENT;
		request.setAttribute("type", type);
		
		return "add_department";
	}
	
	
	/**
	 * 添加投诉处理部门
	 * @return
	 */
	public String doAddDepartment(){
		int tag = 0;
		entity.setType(type);
		entity.setDelFlag(0);

		Set<DepartmentEntity> departmentSet = departmentService.getAllDepartmentByFatherId(entity.getDepartmentId());
		for (DepartmentEntity departmentEntity : departmentSet) {
			System.out.println(departmentEntity.getDepName());
			Map<String, Object> sqlParams = new HashMap<String, Object>();
			sqlParams.put("departmentId", departmentEntity.getId());
			sqlParams.put("type", type);
	        
			try {
				List<AppointManagerEntity> apmList = (List<AppointManagerEntity>) service.fetchList(sqlParams);
				if (apmList == null || apmList.size() == 0) { // 该部门不存在
					entity.setDepartmentId(departmentEntity.getId());
					entity.setDepartmentName(departmentEntity.getDepName());
					service.insert(entity);
	                tag = 1;
				}
				
			} catch (Exception e) {
				tag = 0;
			}
		}
		
		return this.execute();
	}

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
