package com.tuniu.gt.complaint.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.enums.AssignType;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;

@Service("complaint_action-auto_assign")
@Scope("prototype")
public class AutoAssignAction extends FrmBaseAction<IAutoAssignService, AutoAssignEntity> {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(getClass());

	private Integer type;
	
	private String departmentTree;
	
	private java.sql.Date statDate;
	
	private Integer fid;
	
	private Integer cid;
	
	private String isDel;

	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-appoint_manager")
	private IAppointManagerService appointManagerService;

	public AutoAssignAction() {
		setManageUrl("auto_assign");
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-auto_assign")
	public void setService(IAutoAssignService service) {
		this.service = service;
	}
	
	/**
	 * 自动分单配置列表
	 */
	public String execute() {
		// 根据基本配置，进行权限控制
		List<AppointManagerEntity> beforeList = appointManagerService.getListByType(AppointManagerEntity.SPECIAL_BEFORE_OFFICER);
		List<AppointManagerEntity> middleList = appointManagerService.getListByType(AppointManagerEntity.TOURING_OFFICER);
		List<AppointManagerEntity> afterList = appointManagerService.getListByType(AppointManagerEntity.POST_SALES_OFFICER);
		List<AppointManagerEntity> airList = appointManagerService.getListByType(AppointManagerEntity.AIR_TICKIT_OFFICER);
		List<AppointManagerEntity> trafficList = appointManagerService.getListByType(AppointManagerEntity.TRAFFIC_OFFICER);
		List<AppointManagerEntity> hotelList = appointManagerService.getListByType(AppointManagerEntity.HOTEL_OFFICER);
		List<AppointManagerEntity> qtList = appointManagerService.getListByType(AppointManagerEntity.QC_OFFICER);
		List<AppointManagerEntity> devList = appointManagerService.getListByType(AppointManagerEntity.DEV_OFFICER);
		List<AppointManagerEntity> vipDepartOffierList = appointManagerService.getListByType(AppointManagerEntity.VIP_DEPART_OFFICER);
//		List<AppointManagerEntity> distributeList = appointManagerService.getListByType(AppointManagerEntity.DISTRIBUTE_OFFICER);
		
		List<AutoAssignEntity> complaintList = new ArrayList<AutoAssignEntity>(); // 投诉配置List
		List<AutoAssignEntity> qcList = new ArrayList<AutoAssignEntity>(); // 质检配置List
		List<AutoAssignEntity> knowledgeList = new ArrayList<AutoAssignEntity>(); // 在线问答配置List
		String authStr = "";
		for (AppointManagerEntity entity : beforeList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, 1);
				authStr = "isBefore";
				break;
			}
		}

		for (AppointManagerEntity entity : middleList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, 2);
				authStr = "isMiddle";
				break;
			}
		}

		for (AppointManagerEntity entity : afterList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, 3);
				authStr = "isAfter";
				break;
			}
		}
		
		for (AppointManagerEntity entity : airList) {
            if (user.getId().equals(entity.getUserId())) {
                complaintList = service.getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, 4);
                authStr = "isAir";
                break;
            }
        }
		
		for (AppointManagerEntity entity : hotelList) {
		    if (user.getId().equals(entity.getUserId())) {
		        complaintList = service.getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, 5);
		        authStr = "isHotel";
		        break;
		    }
		}
		
		for (AppointManagerEntity entity : trafficList) {
            if (user.getId().equals(entity.getUserId())) {
                complaintList = service.getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, 6);
                authStr = "isTraffic";
                break;
            }
        }
		for (AppointManagerEntity entity : vipDepartOffierList) {
            if (user.getId().equals(entity.getUserId())) {
                complaintList = service.getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, 8);
                authStr = "isVipDepart";
                break;
            }
        }
//		//机票分销
//		for (AppointManagerEntity entity : distributeList) {
//            if (user.getId().equals(entity.getUserId())) {
//                complaintList = service.getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, 7);
//                authStr = "isDistribute";
//                break;
//            }
//        }

		for (AppointManagerEntity entity : qtList) {
			if (user.getId().equals(entity.getUserId())) {
				qcList = service.getQcList();
				authStr = "isQc";
				break;
			}
		}

		for (AppointManagerEntity entity : devList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getListAllByType(AutoAssignEntity.TYPE_COMPLAINT);
				qcList = service.getQcList();
				knowledgeList = service.getListAllByType(AutoAssignEntity.ONLINE_QUESTION);
				authStr = "isDev";
				break;
			}
		}
		
		if ("戴周锋".equals(user.getRealName())) {
			complaintList = service.getListAllByType(AutoAssignEntity.TYPE_COMPLAINT);
			authStr = "isComp";
		}
		
		//拥有在线问答自动分单配置权限的用户
		String realName = user.getRealName();
		if("李波2".equals(realName)||"林露".equals(realName)||"王婉君".equals(realName)||"严敏".equals(realName)||"王丽2".equals(realName)||"张宏5".equals(realName))
		{
			knowledgeList = service.getListAllByType(AutoAssignEntity.ONLINE_QUESTION);
			authStr="isOQ";
		}
		
		request.setAttribute(authStr, true);
		request.setAttribute("complaintList", complaintList);
		request.setAttribute("qcList", qcList);
		request.setAttribute("knowledgeList", knowledgeList);

		return "auto_assign_list";
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String add() {
		// 读取部门列表
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService.fetchList();

		DepartmentEntity department;
		String field;
		int i = 0;

		// 将部门列表转换成JOSN数据
		departmentTree = "[";
		for (i = 0; i < departments.size() - 1; i++) {
			department = departments.get(i);
			field = "{id:" + department.getId() + ", pId:"
					+ department.getFatherId() + ", name:\""
					+ department.getDepName() + "\"},\n";
			departmentTree += field;
		}
		// last one
		department = departments.get(i);
		field = "{id:" + department.getId() + ", pId:"
				+ department.getFatherId() + ", name:\""
				+ department.getDepName() + "\"}";
		departmentTree += field + "]";
		
		// 根据基本配置，进行权限控制
		List<AppointManagerEntity> beforeList = appointManagerService.getListByType(AppointManagerEntity.SPECIAL_BEFORE_OFFICER);
		List<AppointManagerEntity> middleList = appointManagerService.getListByType(AppointManagerEntity.TOURING_OFFICER);
		List<AppointManagerEntity> afterList = appointManagerService.getListByType(AppointManagerEntity.POST_SALES_OFFICER);
		List<AppointManagerEntity> airList = appointManagerService.getListByType(AppointManagerEntity.AIR_TICKIT_OFFICER);
		List<AppointManagerEntity> hotelList = appointManagerService.getListByType(AppointManagerEntity.HOTEL_OFFICER);
		List<AppointManagerEntity> devList = appointManagerService.getListByType(AppointManagerEntity.DEV_OFFICER);
		
		String authStr = "";
		for (AppointManagerEntity entity : beforeList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isBefore";
				break;
			}
		}
		for (AppointManagerEntity entity : middleList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isMiddle";
				break;
			}
		}
		for (AppointManagerEntity entity : afterList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isAfter";
				break;
			}
		}
		
		for (AppointManagerEntity entity : airList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isAir";
				break;
			}
		}
		
		for (AppointManagerEntity entity : hotelList) {
		    if (user.getId().equals(entity.getUserId())) {
		        authStr = "isHotel";
		        break;
		    }
		}
		
		for (AppointManagerEntity entity : devList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isDev";
				break;
			}
		}
		request.setAttribute(authStr, true);

		String type = request.getParameter("type");
		request.setAttribute("type", type);
		String tourTimeNode = request.getParameter("tourTimeNode");
		request.setAttribute("tourTimeNode", tourTimeNode);
		
		if (2 == Integer.valueOf(type)) {
			request.setAttribute("deptList", getDeptList());
		}
		jspTpl = "auto_assign_add";
		return jspTpl;
	}
	
	private List<DepartmentEntity> getDeptList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("useDept", 1); // 质检一级事业部
		params.put("fatherId", 1); // 质检一级事业部
		List<DepartmentEntity> deptList = (List<DepartmentEntity>) departmentService.fetchList(params);
		for(DepartmentEntity faDept : deptList){
			Map<String, Object> paramss = new HashMap<String, Object>();
			paramss.put("fatherId", faDept.getId()); // 质检二级事业部
			paramss.put("useDept", 1);
			List<DepartmentEntity> chDepts = (List<DepartmentEntity>) departmentService.fetchList(paramss);
			faDept.setChildDept(chDepts);
		}
		return deptList;
	}

	/**
	 * 跳转到配置事业部页面
	 * 
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String configureDept() {
		// 读取部门列表
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService.fetchList();

		DepartmentEntity department;
		String field;
		int i = 0;

		// 将部门列表转换成JOSN数据
		departmentTree = "[";
		for (i = 0; i < departments.size() - 1; i++) {
			department = departments.get(i);
			field = "{id:" + department.getId() + ", pId:"
					+ department.getFatherId() + ", name:\""
					+ department.getDepName() + "\"},\n";
			departmentTree += field;
		}
		// last one
		department = departments.get(i);
		field = "{id:" + department.getId() + ", pId:"
				+ department.getFatherId() + ", name:\""
				+ department.getDepName() + "\"}";
		departmentTree += field + "]";
		
		// 根据基本配置，进行权限控制
		List<AppointManagerEntity> beforeList = appointManagerService.getListByType(AppointManagerEntity.SPECIAL_BEFORE_OFFICER);
		List<AppointManagerEntity> middleList = appointManagerService.getListByType(AppointManagerEntity.TOURING_OFFICER);
		List<AppointManagerEntity> afterList = appointManagerService.getListByType(AppointManagerEntity.POST_SALES_OFFICER);
		List<AppointManagerEntity> airList = appointManagerService.getListByType(AppointManagerEntity.AIR_TICKIT_OFFICER);
		List<AppointManagerEntity> hotelList = appointManagerService.getListByType(AppointManagerEntity.HOTEL_OFFICER);
		List<AppointManagerEntity> devList = appointManagerService.getListByType(AppointManagerEntity.DEV_OFFICER);
		
		String authStr = "";
		for (AppointManagerEntity entity : beforeList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isBefore";
				break;
			}
		}
		for (AppointManagerEntity entity : middleList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isMiddle";
				break;
			}
		}
		for (AppointManagerEntity entity : afterList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isAfter";
				break;
			}
		}
		for (AppointManagerEntity entity : airList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isAir";
				break;
			}
		}
		for (AppointManagerEntity entity : hotelList) {
		    if (user.getId().equals(entity.getUserId())) {
		        authStr = "isHotel";
		        break;
		    }
		}
		for (AppointManagerEntity entity : devList) {
			if (user.getId().equals(entity.getUserId())) {
				authStr = "isDev";
				break;
			}
		}
		if ("陈长庆".equals(user.getRealName())) {
			authStr = "isComp";
		}
		request.setAttribute(authStr, true);

		String type = request.getParameter("type");
		request.setAttribute("type", type);
		
		if (2 == Integer.valueOf(type)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fatherId", 1); 
			List<DepartmentEntity> deptList= (List<DepartmentEntity>) departmentService.fetchList(params);
			for(DepartmentEntity faDept : deptList){
				Map<String, Object> paramss = new HashMap<String, Object>();
				paramss.put("fatherId", faDept.getId()); // 出境长线事业部
				List<DepartmentEntity> chDepts = (List<DepartmentEntity>) departmentService.fetchList(paramss);
				faDept.setChildDept(chDepts);
			}
			request.setAttribute("deptList", deptList);
		}
		
		jspTpl = "set_dapt";
		return jspTpl;
	}
	
	/**
	 * 增加一条配置
	 */
	public String doAdd() {
		entity.setType(type);
		entity.setDelFlag(0);
		unSelected();
		
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		sqlParams.put("userId", entity.getUserId());
		sqlParams.put("type", type);
		int flag = 0;
		try {
			if (entity.getUserId() != null && entity.getUserId() > 0) {
				AutoAssignEntity assignEntity = (AutoAssignEntity) service.getByTypeAndUserId(sqlParams);
				if (assignEntity == null) { // 该用户配置不存在
					Date date = new Date();
					entity.setAddTime(date);
					entity.setUpdateTime(date);
					if (2 == type) {
						service.insertQc(entity);
					} else {
						service.insert(entity);
					}
				}else{
					flag = 1;
				}
			}
			// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
			HttpServletResponse response = ServletActionContext.getResponse();
			// 设置字符集
			response.setContentType("text/plain");// 设置输出为文字流
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			out = response.getWriter();
			// 直接输出响应的内容
			out.println(flag);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		jspTpl = "auto_assign_add";
		return jspTpl;
	}
	
	/**
	 * 增加一条部门配置
	 */
	@SuppressWarnings("unchecked")
	public String doAddDept() {
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		int flag = 0;
		if("add".equals(isDel)){
			sqlParams.put("id", fid);
			sqlParams.put("useDept", 1);
			departmentService.update(sqlParams);
			sqlParams.put("id", cid);
			departmentService.update(sqlParams);
		}else if("del".equals(isDel)){
			sqlParams.put("useDept", 0);
			sqlParams.put("id", cid);
			departmentService.update(sqlParams);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fatherId", fid);
			params.put("useDept", 1);
			List<DepartmentEntity> depts = (List<DepartmentEntity>) departmentService.fetchList(params);
			if(depts==null || depts.size() < 1){
				sqlParams.put("id", fid);
				departmentService.update(sqlParams);
			}
			flag = 1;
		}
		try {
			// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
			HttpServletResponse response = ServletActionContext.getResponse();
			// 设置字符集
			response.setContentType("text/plain");// 设置输出为文字流
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			out = response.getWriter();
			// 直接输出响应的内容
			out.println(JSONArray.fromObject(flag).toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		jspTpl = "set_dapt";
		return jspTpl;
	}
	
	/**
	 * 增加或删除全部部门配置
	 */
	public String batchAddDept()
	{
		Map<String, Object> sqlParams = new HashMap<String, Object>();
		int useDept = 0;
		if("add".equals(isDel))
		{
			useDept=1;
		}
		
		sqlParams.put("useDept", useDept);
		departmentService.batchUpdateUseDeptForQc(sqlParams);

		jspTpl = "set_dapt";
		return jspTpl;
	}
	
	/**
	 * 跳转到添加页面
	 * 
	 * @return String
	 * @throws
	 */
	public String modify() {
		entity = (AutoAssignEntity) service.get(entity.getId());
		return "auto_assign_modify";
	}
	
	public String doModify() {
		entity.setUpdateTime(new Date());
		entity.setType(entity.TYPE_COMPLAINT);
		unSelected();
		service.update(entity);
		return "auto_assign_modify";
	}
	
	public String modifyQc() {
		entity = service.getQcPerson(entity.getId());
		request.setAttribute("deptList", getDeptList());
		return "auto_assign_modify_qc";
	}
	
	public String doModifyQc() {
		service.updateQc(entity);
		return "auto_assign_modify_qc";
	}
	
	/**
	 * 如果投诉等级或事业部或目的地大类某项未做任何选择，则按全选处理
	 */
	private void unSelected() {
		if (entity.getComplaintLevel1Flag() == 0 && entity.getComplaintLevel2Flag() == 0 && entity.getComplaintLevel3Flag() == 0) {
			entity.setComplaintLevel1Flag(1);
			entity.setComplaintLevel2Flag(1);
			entity.setComplaintLevel3Flag(1);
		}
		
		if (entity.getAroundFlag() == 0 && entity.getInlandLongFlag() == 0 
				&& entity.getAbroadShortFlag() == 0 && entity.getAbroadLongFlag() == 0 && entity.getOthersFlag() == 0) {
			entity.setAroundFlag(1);
			entity.setInlandLongFlag(1);
			entity.setAbroadShortFlag(1);
			entity.setAbroadLongFlag(1);
			entity.setOthersFlag(1);
		}
	}

	/**
	 * 修改配置
	 * 
	 * @return String
	 * @throws
	 */
	public String del() {

		String id = request.getParameter("id");
		int delFlag = Integer.parseInt(request.getParameter("delFlag"));
		int flag = 0;
		if (id != null) {
			entity = (AutoAssignEntity) service.get(id);
			if (delFlag == 0) {
				entity.setDelFlag(1);// 设置删除标示位为0
				logger.info(user.getRealName()+"关闭"+entity.getUserName()+"分单配置");
			} else {
				entity.setDelFlag(0);
				logger.info(user.getRealName()+"开启"+entity.getUserName()+"分单配置");
			}
			service.update(entity);
			flag = 1;
		}
		try {
			// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
			HttpServletResponse response = ServletActionContext.getResponse();
			// 设置字符集
			response.setContentType("text/plain");// 设置输出为文字流
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			out = response.getWriter();
			// 直接输出响应的内容
			out.println(flag);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		jspTpl = "auto_assign_add";
		return jspTpl;
	}
	
	/**
	 * 删除配置
	 * 
	 * @return String
	 * @throws
	 */
	public String delete() {

		String id = request.getParameter("id");
		int flag = 0;
		if (id != null) {
			Map<String ,Object> map=new HashMap<String, Object>();
			map.put("id", id);
			service.delete(map);
			flag = 1;
		}
		try {
			// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
			HttpServletResponse response = ServletActionContext.getResponse();
			// 设置字符集
			response.setContentType("text/plain");// 设置输出为文字流
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			out = response.getWriter();
			// 直接输出响应的内容
			out.println(flag);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		jspTpl = "auto_assign_add";
		return jspTpl;
	}

	public String queryDetail() {
		// 根据基本配置，进行权限控制
		List<AppointManagerEntity> beforeList = appointManagerService.getListByType(AppointManagerEntity.SPECIAL_BEFORE_OFFICER);
		List<AppointManagerEntity> middleList = appointManagerService.getListByType(AppointManagerEntity.TOURING_OFFICER);
		List<AppointManagerEntity> afterList = appointManagerService.getListByType(AppointManagerEntity.POST_SALES_OFFICER);
		List<AppointManagerEntity> airList = appointManagerService.getListByType(AppointManagerEntity.AIR_TICKIT_OFFICER);
		List<AppointManagerEntity> hotelList = appointManagerService.getListByType(AppointManagerEntity.HOTEL_OFFICER);
		List<AppointManagerEntity> trafficList = appointManagerService.getListByType(AppointManagerEntity.TRAFFIC_OFFICER);
		List<AppointManagerEntity> qtList = appointManagerService.getListByType(AppointManagerEntity.QC_OFFICER);
		List<AppointManagerEntity> devList = appointManagerService.getListByType(AppointManagerEntity.DEV_OFFICER);
		List<AppointManagerEntity> vipDepartOffierList = appointManagerService.getListByType(AppointManagerEntity.VIP_DEPART_OFFICER);
		if (null == statDate) {
			statDate = DateUtil.getSqlToday();
		}
		
		List<AutoAssignEntity> complaintList = new ArrayList<AutoAssignEntity>(); // 投诉分单
		List<AutoAssignEntity> qcList = new ArrayList<AutoAssignEntity>(); // 质检分单
		String authStr = "";
		for (AppointManagerEntity entity : beforeList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getAssignCompInfoList(statDate, 1, AssignType.AUTO);
				authStr = "isBefore";
				break;
			}
		}

		for (AppointManagerEntity entity : middleList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getAssignCompInfoList(statDate, 2, AssignType.AUTO);
				authStr = "isMiddle";
				break;
			}
		}

		for (AppointManagerEntity entity : afterList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getAssignCompInfoList(statDate, 3, AssignType.AUTO);
				authStr = "isAfter";
				break;
			}
		}
		
		for (AppointManagerEntity entity : airList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getAssignCompInfoList(statDate, 4, AssignType.AUTO);
				authStr = "isAir";
				break;
			}
		}
		
		for (AppointManagerEntity entity : hotelList) {
		    if (user.getId().equals(entity.getUserId())) {
		        complaintList = service.getAssignCompInfoList(statDate, 5, AssignType.AUTO);
		        authStr = "isHotel";
		        break;
		    }
		}
		
		for (AppointManagerEntity entity : trafficList) {
		    if (user.getId().equals(entity.getUserId())) {
		        complaintList = service.getAssignCompInfoList(statDate, 6, AssignType.AUTO);
		        authStr = "isTraffic";
		        break;
		    }
		}
		for (AppointManagerEntity entity : vipDepartOffierList) {
            if (user.getId().equals(entity.getUserId())) {
                complaintList = service.getAssignCompInfoList(statDate, 8, AssignType.AUTO);
                authStr = "isVipDepart";
                break;
            }
        }
		for (AppointManagerEntity entity : qtList) {
			if (user.getId().equals(entity.getUserId())) {
				qcList = service.getQcInfoList();
				authStr = "isQc";
				break;
			}
		}

		for (AppointManagerEntity entity : devList) {
			if (user.getId().equals(entity.getUserId())) {
				complaintList = service.getAssignCompInfoList(statDate, 0, AssignType.AUTO);
				qcList = service.getQcInfoList();
				authStr = "isDev";
				break;
			}
		}
		
		if ("戴周锋".equals(user.getRealName())) {
			complaintList = service.getAssignCompInfoList(statDate, 0, AssignType.AUTO);
			authStr = "isComp";
		}
		
		request.setAttribute(authStr, true);
		request.setAttribute("complaintList", complaintList);
		request.setAttribute("qcList", qcList);
		//request.setAttribute("knowledgeList", knowledgeList);

		jspTpl = "auto_assign_detail";
		return jspTpl;
	}
	
	private boolean isContain(List<UserEntity> list, UserEntity user) {
		boolean flag = false;
		for (UserEntity u : list) {
			if (user.getId().equals(u.getId())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDepartmentTree() {
		return departmentTree;
	}

	public void setDepartmentTree(String departmentTree) {
		this.departmentTree = departmentTree;
	}

	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public IAppointManagerService getAppointManagerService() {
		return appointManagerService;
	}

	public void setAppointManagerService(
			IAppointManagerService appointManagerService) {
		this.appointManagerService = appointManagerService;
	}

	public java.sql.Date getStatDate() {
		return statDate;
	}

	public void setStatDate(java.sql.Date statDate) {
		this.statDate = statDate;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	
}
