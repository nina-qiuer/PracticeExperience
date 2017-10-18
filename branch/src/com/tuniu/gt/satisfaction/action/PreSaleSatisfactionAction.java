package com.tuniu.gt.satisfaction.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;

import com.tuniu.gt.complaint.enums.CustStarLevelEnum;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.satisfaction.entity.FrmUserDelEntity;
import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionEntity;
import com.tuniu.gt.satisfaction.entity.PreSaleSatisfactionSocreEntity;
import com.tuniu.gt.satisfaction.service.IFrmUserDelService;
import com.tuniu.gt.satisfaction.service.IPreSaleSatisfactionService;
import com.tuniu.gt.satisfaction.service.IPreSaleSatisfactionSocreService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DataExportExcel;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.uc.entity.UcDepartmentDelEntity;
import com.tuniu.gt.uc.entity.UcDepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.gt.uc.service.user.IUcDepartmentDelService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

@Service("complaint_action-preSaleSatisfaction")
@Scope("prototype")
public class PreSaleSatisfactionAction extends FrmBaseAction<IPreSaleSatisfactionService, PreSaleSatisfactionEntity> {

	private static final long serialVersionUID = 1L;

	public PreSaleSatisfactionAction() {
		setManageUrl("preSaleSatisfaction");
	}

	//用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-preSaleSatisfaction")
	public void setService(IPreSaleSatisfactionService service) {
		this.service = service;
	};
	
	//售前客服满意度评分service
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-preSaleSatisfactionSocre")
	private IPreSaleSatisfactionSocreService socreService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-ucdepartment")
	private IUcDepartmentDelService departmentDelService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-ucuserdel")
	private IFrmUserDelService userDelService;

	public String execute() {
		request.setAttribute("pageTitle", "售前客服满意度管理");
		return "pre_sale_satisfaction_list";
	}
	
	/**
	 * 封装查询条件
	 * @return
	 */
	public Map<String, Object> paramSearch() {
		Map<String, Object> paramMap = new HashMap<String, Object>();  //search使用的map
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		if (StringUtil.isEmpty(search.get("yearMonth"))) {
			search.put("yearMonth", DateUtil.formatDate(new Date(), "yyyy-MM"));
		}
		paramMap.putAll(search);// 放入search内容
		request.setAttribute("search", search);
		return paramMap;
	}
	
	/**
	 * 查看售前服务评价页面
	 * 
	 */
	public String operation() {
	   String id = request.getParameter("id");
	   String parent_salerName = request.getParameter("parent_salerName");
	   String parent_deptId = request.getParameter("parent_deptId");
	   request.setAttribute("satisId", id);
	   request.setAttribute("parent_salerName", parent_salerName);
	   request.setAttribute("parent_deptId", parent_deptId);
		int count = socreService.getSocreCountBySatisId(Integer.parseInt(id));
		String res = "pre_sale_satisfaction_socre_add";
		if(count>0){
			PreSaleSatisfactionSocreEntity preSaleSatisfactionSocreEntity = socreService.getSocreEntityBySatisId(Integer.parseInt(id));
			request.setAttribute("entity", preSaleSatisfactionSocreEntity);
			res = "pre_sale_satisfaction_socre_update";
		}
		return res;
	}
	
	/**
	 * 添加售前服务评价
	 * @throws Exception 
	 * 
	 */
	public  String addSocre() throws Exception {
		PreSaleSatisfactionSocreEntity socreEntity = getPreSaleSatisfactionSocreEntity();
		socreEntity.setCreateTime(new Date());
		socreService.insert(socreEntity);
		
		int id = socreEntity.getPreSaleSatisId();
		PreSaleSatisfactionEntity satisfactionEntity = new PreSaleSatisfactionEntity();
		satisfactionEntity.setId(id);
		satisfactionEntity.setPreSaleSatisfaction(socreEntity.getPreSaleSatisfaction());
		satisfactionEntity.setCommentStatus(socreEntity.getStatus());
		satisfactionEntity.setOrderId(service.getOrderIdById(id));
		satisfactionEntity.setUpdateTime(new Date());
		service.update(satisfactionEntity);
		if(socreEntity.getStatus() == 1){
			computeNum(id);
		}
		return "pre_sale_satisfaction_socre_add";
	}
	
	/**
	 * 更新售前服务评价
	 * @throws Exception 
	 * 
	 */
	public  String updateSocre() throws Exception {
		PreSaleSatisfactionSocreEntity preSaleSatisfactionSocreEntity = getPreSaleSatisfactionSocreEntity();
		String preSaleSatisfaction = preSaleSatisfactionSocreEntity.getPreSaleSatisfaction();
		int commentStatus = preSaleSatisfactionSocreEntity.getStatus();
		int id = preSaleSatisfactionSocreEntity.getPreSaleSatisId();
		int orderId = service.getOrderIdById(id);
		PreSaleSatisfactionEntity preSaleSatisfactionEntity = new PreSaleSatisfactionEntity();
		preSaleSatisfactionEntity.setId(id);
		preSaleSatisfactionEntity.setPreSaleSatisfaction(preSaleSatisfaction);
		preSaleSatisfactionEntity.setCommentStatus(commentStatus);
		preSaleSatisfactionEntity.setOrderId(orderId);
		preSaleSatisfactionEntity.setUpdateTime(new Date());
		socreService.update(preSaleSatisfactionSocreEntity);
		service.update(preSaleSatisfactionEntity);
		if(commentStatus==1){
			computeNum(id);
		}
		request.setAttribute("entity", preSaleSatisfactionSocreEntity);
		return "pre_sale_satisfaction_socre_update";
	}
	//确认点评成功，次数+1
	@SuppressWarnings("unchecked")
	public void computeNum(int id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		PreSaleSatisfactionEntity PreSaleSatisfactionEntity = (PreSaleSatisfactionEntity) service.get(map);
		String salerName = PreSaleSatisfactionEntity.getSalerName();
		map.remove(id);
		map.put("realName", salerName);
		//frmUserDelService.addCompletePNum(map);
		userDelService.update(map);
		//更新组织架构颜色
		FrmUserDelEntity frmUserDelEntity = (FrmUserDelEntity)userDelService.selectByName(map);
		if(frmUserDelEntity!=null){
			int depId = frmUserDelEntity.getDepId();
			map.put("depId", depId);
			List<FrmUserDelEntity> FrmUserDelList = (List<FrmUserDelEntity>)userDelService.fetchList(map);
			int depth=0;
			for(int i=0;i<FrmUserDelList.size();i++){
				if(FrmUserDelList.get(i).getApprovalNumber()>=5){
					depth = depth +1;
				}
			}
			if(depth==FrmUserDelList.size()){
				map.put("showColor", "1");
				departmentDelService.updateForColor(map);
			}else{
				map.put("showColor", "0");
				departmentDelService.updateForColor(map);
			}
		}
	}
	private PreSaleSatisfactionSocreEntity getPreSaleSatisfactionSocreEntity(){
		String preSaleSatisId = request.getParameter("satisId");
		String professional = request.getParameter("professional");
		String professionalComment = request.getParameter("professional_text");
		String timeliness = request.getParameter("timeliness");
		String timelinessComment = request.getParameter("timeliness_text");
		String patience = request.getParameter("patience");
		String patienceComment = request.getParameter("patience_text");
		String responsibility = request.getParameter("responsibility");
		String responsibilityComment = request.getParameter("responsibility_text");
		String suggestion = request.getParameter("other_text");
		String total = request.getParameter("total");
		String preSaleSatisfaction = request.getParameter("preSaleSatisfaction");
		String status = request.getParameter("status");
		PreSaleSatisfactionSocreEntity preSaleSatisfactionSocreEntity = new PreSaleSatisfactionSocreEntity();
		preSaleSatisfactionSocreEntity.setPreSaleSatisId(Integer.parseInt(preSaleSatisId));
		preSaleSatisfactionSocreEntity.setProfessional(Integer.parseInt(professional));
		preSaleSatisfactionSocreEntity.setProfessionalComment(professionalComment);
		preSaleSatisfactionSocreEntity.setTimeliness(Integer.parseInt(timeliness));
		preSaleSatisfactionSocreEntity.setTimelinessComment(timelinessComment);
		preSaleSatisfactionSocreEntity.setPatience(Integer.parseInt(patience));
		preSaleSatisfactionSocreEntity.setPatienceComment(patienceComment);
		preSaleSatisfactionSocreEntity.setResponsibility(Integer.parseInt(responsibility));
		preSaleSatisfactionSocreEntity.setResponsibilityComment(responsibilityComment);
		preSaleSatisfactionSocreEntity.setSuggestion(suggestion);
		preSaleSatisfactionSocreEntity.setTotal(Integer.parseInt(total));
		preSaleSatisfactionSocreEntity.setPreSaleSatisfaction(preSaleSatisfaction);
		preSaleSatisfactionSocreEntity.setStatus(Integer.parseInt(status));
		preSaleSatisfactionSocreEntity.setLastModifyTime(new Date());
		return preSaleSatisfactionSocreEntity;
	}
	
	/**
	 * 导出数据
	 * @return String 页面
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String exports() throws Exception{
		
		List<List<Object>> data = new ArrayList<List<Object>>();
		
		//设置标题	
		List<Object> listColumn = new ArrayList<Object>();
		listColumn.add("订单编号");
		//listColumn.add("订单联系人姓名");
		//listColumn.add("订单联系人电话");
		listColumn.add("电话呼出量");
		listColumn.add("线路名称");
		listColumn.add("客人星级");
		listColumn.add("客服姓名");
		listColumn.add("客服经理");
		listColumn.add("订单类型");
		listColumn.add("出游日期");
		listColumn.add("归来日期");
		listColumn.add("满意度");
		data.add(listColumn);
		
		//查询数据
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap = paramSearch();
		Date paramDate;
		String time = request.getParameter("time");
		if(StringUtils.isNotEmpty(time)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");                
			paramDate = sdf.parse(time);  
			paramMap.put("paramDate", paramDate);
		}else{
			paramDate = new Date();
			paramMap.put("paramDate", paramDate);
		}
		List<PreSaleSatisfactionEntity> preSaleSatisfactionList = (List<PreSaleSatisfactionEntity>)service.fetchList(paramMap);
		
		//封装数据
		if(preSaleSatisfactionList.size() > 0){
			for(PreSaleSatisfactionEntity preSaleSatisfactionEntity : preSaleSatisfactionList){
				List<Object> listData = new ArrayList<Object>();
				listData.add(preSaleSatisfactionEntity.getOrderId());
				//listData.add(SatisfactionUtil.isNull(preSaleSatisfactionEntity.getContactName()));
				//listData.add(preSaleSatisfactionEntity.getContactTel());
				listData.add(preSaleSatisfactionEntity.getTelCnt());
				String routeName = SatisfactionUtil.isNull(preSaleSatisfactionEntity.getRouteName());
				routeName = routeName.replace("&lt;", "<");
				routeName = routeName.replace("&gt;", ">");
				listData.add(routeName);
				listData.add(SatisfactionUtil.isNull(CustStarLevelEnum.getValue(preSaleSatisfactionEntity.getStar_level())));
				listData.add(SatisfactionUtil.isNull(preSaleSatisfactionEntity.getSalerName()));
				listData.add(SatisfactionUtil.isNull(preSaleSatisfactionEntity.getSalermanagerName()));
				listData.add(SatisfactionUtil.isNull(preSaleSatisfactionEntity.getOrderType()));
				listData.add(DateUtil.formatDate(preSaleSatisfactionEntity.getStartTime(), "yyyy-MM-dd"));
				listData.add(DateUtil.formatDate(preSaleSatisfactionEntity.getBackTime(), "yyyy-MM-dd"));   
				listData.add(preSaleSatisfactionEntity.getPreSaleSatisfaction()==null?0:preSaleSatisfactionEntity.getPreSaleSatisfaction());
				// 添加数据
				data.add(listData);
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String filename = "preSaleSatisfaction_" + (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
		DataExportExcel dee = new DataExportExcel(data, filename, "", response);
		dee.createExcelFromList();
		return "pre_sale_satisfaction_list";
	}
	
	/**
	 * 导出今日数据
	 * @return String 页面
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String exportsToday() throws Exception{
		
		List<List<Object>> data = new ArrayList<List<Object>>();
		//设置标题	
		List<Object> listColumn = new ArrayList<Object>();
		listColumn.add("订单编号");
		//listColumn.add("订单联系人姓名");
		//listColumn.add("订单联系人电话");
		listColumn.add("电话呼出量");
		listColumn.add("线路名称");
		listColumn.add("客人星级");
		listColumn.add("客服姓名");
		listColumn.add("客服经理");
		listColumn.add("订单类型");
		listColumn.add("出游日期");
		listColumn.add("归来日期");
		listColumn.add("满意度");
		data.add(listColumn);
		
		//查询数据
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String updateTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
		paramMap.put("updateTime", updateTime);
		Date paramDate;
		String time = request.getParameter("time");
		if(StringUtils.isNotEmpty(time)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");                
			paramDate = sdf.parse(time);  
			paramMap.put("paramDate", paramDate);
		}else{
			paramDate = new Date();
			paramMap.put("paramDate", paramDate);
		}
		List<PreSaleSatisfactionEntity> preSaleSatisfactionList = (List<PreSaleSatisfactionEntity>)service.fetchList(paramMap);
		
		//封装数据
		if(preSaleSatisfactionList.size() > 0){
			for(PreSaleSatisfactionEntity preSaleSatisfactionEntity : preSaleSatisfactionList){
				List<Object> listData = new ArrayList<Object>();
				listData.add(preSaleSatisfactionEntity.getOrderId());
			  //listData.add(SatisfactionUtil.isNull(preSaleSatisfactionEntity.getContactName()));
			//	listData.add(preSaleSatisfactionEntity.getContactTel());
				listData.add(preSaleSatisfactionEntity.getTelCnt());
				String routeName = SatisfactionUtil.isNull(preSaleSatisfactionEntity.getRouteName());
				routeName = routeName.replace("&lt;", "<");
				routeName = routeName.replace("&gt;", ">");
				listData.add(routeName);
				listData.add(SatisfactionUtil.isNull(CustStarLevelEnum.getValue(preSaleSatisfactionEntity.getStar_level())));
				listData.add(SatisfactionUtil.isNull(preSaleSatisfactionEntity.getSalerName()));
				listData.add(SatisfactionUtil.isNull(preSaleSatisfactionEntity.getSalermanagerName()));
				listData.add(SatisfactionUtil.isNull(preSaleSatisfactionEntity.getOrderType()));
				listData.add(DateUtil.formatDate(preSaleSatisfactionEntity.getStartTime(), "yyyy-MM-dd"));
				listData.add(DateUtil.formatDate(preSaleSatisfactionEntity.getBackTime(), "yyyy-MM-dd"));  
				listData.add(preSaleSatisfactionEntity.getPreSaleSatisfaction()==null?0:preSaleSatisfactionEntity.getPreSaleSatisfaction());
				// 添加数据
				data.add(listData);
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String filename = "preSaleSatisfaction_" + (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
		DataExportExcel dee = new DataExportExcel(data, filename, "", response);
		dee.createExcelFromList();

		return "pre_sale_satisfaction_list";
	}
	public String doAddConstruts(){
		List<UcDepartmentEntity> UcDepartmentList = departmentService.doSearchConstruts();
		String field;
		UcDepartmentEntity ucDepartment;
		String jsonDepartmentTree = "[";
		int i;
		for(i=0;i<UcDepartmentList.size()-1;i++){
			ucDepartment = UcDepartmentList.get(i);
			field = "{id:"+ ucDepartment.getId() +",pId:"+ ucDepartment.getpId() +",name:\""+ ucDepartment.getName() +"\"},\n";
			jsonDepartmentTree += field;
		}
		ucDepartment = UcDepartmentList.get(i);
		field = "{id:"+ ucDepartment.getId() +",pId:"+ ucDepartment.getpId() +",name:\""+ ucDepartment.getName() +"\"}";
		jsonDepartmentTree +=field + "]";
		request.setAttribute("dep_construts", jsonDepartmentTree);
		return "pre_sale_satisfaction_ucconstruts";
	}
	@SuppressWarnings("unchecked")
	public void doBackConstruts(){
		String ids = request.getParameter("ids");
		List<UcDepartmentDelEntity> departmentList = new ArrayList<UcDepartmentDelEntity>();
		List<FrmUserDelEntity> userDelList = new ArrayList<FrmUserDelEntity>();
		if(ids != null){
			String[] params = ids.split("\\^");
			departmentDelService.deleteExitDepartments();
			userDelService.deleteExitPerson();
			for(int i=0; i<params.length; i++) {
				String[] uc_del = params[i].split("#");
				UcDepartmentDelEntity department = new UcDepartmentDelEntity();
				department.setSelectedDeptName(uc_del[0].toString());
				department.setCurrentId(Integer.parseInt(uc_del[1]));
				department.setDepName(uc_del[2]);
				department.setFatherId(!"null".equals(uc_del[3]) ? Integer.parseInt(uc_del[3]) : 1);
				department.setUpdateTime(new Date());
				departmentList.add(department);
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("depId", uc_del[1]);
				param.put("delFlag", 0);
				List<UserEntity> userList = (List<UserEntity>) userService.fetchList(param);
				for (UserEntity user : userList) {
					FrmUserDelEntity userDel = new FrmUserDelEntity();
					userDel.setDepId(user.getDepId());
					userDel.setRealName(user.getRealName());
					userDel.setUserName(user.getUserName());
					userDel.setWorkNum(user.getWorknum());
					userDel.setUpdateTime(new Date());
					userDelList.add(userDel);
				}
			}
			departmentDelService.insertBatch(departmentList);
			userDelService.insertBatch(userDelList);
			
			request.setAttribute("UcDepartmentDelList", departmentList);
			CommonUtil.writeResponse("success");
		}
	}
	
	public String toLeft() {
		String yearMonth = request.getParameter("yearMonth");
		if (StringUtil.isEmpty(yearMonth)) {
			yearMonth = DateUtil.formatDate(new Date(), "yyyy-MM");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("yearMonth", yearMonth);
		List<UcDepartmentDelEntity> ucDepartmentDelList = (List<UcDepartmentDelEntity>)departmentDelService.getSelectedInfoFromHis(param);
		request.setAttribute("UcDepartmentDelList", ucDepartmentDelList);
		request.setAttribute("selectedM", yearMonth);
		return "showLeftDept";
	}
	
	@SuppressWarnings("unchecked")
	public String toRight() {
		this.perPage = 30;
		Map<String, Object> paramMap = paramSearch();
		List<UcDepartmentDelEntity> departmentList = (List<UcDepartmentDelEntity>)departmentDelService.getSelectedInfoFromHis(paramMap);
		request.setAttribute("departmentList", departmentList);
		
		String depId = (String) paramMap.get("depId");
		if (depId!=null && !"".equals(depId)) {
			String salerNames="";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("depId", depId);
			List<UserEntity> getRealNames = (List<UserEntity>)userService.fetchList(param);
			for(int i=0; i<getRealNames.size(); i++){
				salerNames = salerNames + "'"+getRealNames.get(i).getRealName()+"'" +",";
			}
			salerNames = salerNames.substring(0, salerNames.length()-1);
			paramMap.put("salerNames", salerNames);
			request.setAttribute("parent_dep_id", id);
			request.setAttribute("parent_deptId", id);
		}
		super.execute(paramMap); // 调用父类方法返回页面列表
		
		return "showRightPerson";
	}
	
}
