/**
 * 
 */
package com.tuniu.gt.workorder.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.warning.common.Page;
import com.tuniu.gt.workorder.constant.WorkOrderConstent;
import com.tuniu.gt.workorder.entity.WorkOrder;
import com.tuniu.gt.workorder.entity.WorkOrderConfig;
import com.tuniu.gt.workorder.entity.WorkOrderOperationLog;
import com.tuniu.gt.workorder.entity.WorkOrderReport;
import com.tuniu.gt.workorder.service.IWorkOrderConfigService;
import com.tuniu.gt.workorder.service.IWorkOrderOperationLogService;
import com.tuniu.gt.workorder.service.IWorkOrderService;
import com.tuniu.gt.workorder.vo.WorkOrderVo;

import net.sf.json.JSONArray;
import tuniu.frm.core.FrmBaseAction;

/**
 * @author jiangye
 *
 */
@Service("wo_action-work_order")
@Scope("prototype")
public class WorkOrderAction  extends FrmBaseAction<IWorkOrderService, WorkOrder> {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private WorkOrderVo searchVo; //列表搜索条件
	private List<String> members; // 员工列表 
	private List<Integer> ids; // 待分配的工单号
	private boolean isAssigner ;// 是否为分配人
	private Page page;
	private Map<String,Object>  info;
	
	private List<WorkOrderConfig> firstConfig = new ArrayList<WorkOrderConfig>(); //一级配置项
	private List<WorkOrderConfig> responsibleConfig = new ArrayList<WorkOrderConfig>(); //负责人相关配置
	private List<String> responsibledeps = new ArrayList<String>(); //负责人负责部门列表
	
	private Integer configId; //配置id
	
	private Integer dealBySelf;//分配给自己处理
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("wo_service_impl-config")
	private IWorkOrderConfigService configService;
	
	@Autowired
	@Qualifier("wo_service_impl-operation_log")
	private IWorkOrderOperationLogService  operationLogService;
	
	public WorkOrderAction() {
		setManageUrl("wo");
		info = new HashMap<String, Object>(1);
	}
	
	@Autowired
	@Qualifier("wo_service_impl-work_order")
	public void setService(IWorkOrderService service) {
		this.service = service;
	}
	
	public String index() {
		return "index";
	}
	
	/* 
	 * 查询列表与发起工单页
	 */
	public String execute(){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(searchVo != null){
			paramMap = searchVo.toMap();
		}
		
		if(page == null){
			page = new Page();
			page.setPageSize(20);
		}
		
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("dataLimitEnd", page.getPageSize());
		
		List<WorkOrder>  dataList = (List<WorkOrder>) service.fetchList(paramMap);
		page.setCount(service.count(paramMap));
		page.setCurrentPageCount(dataList.size());
		request.setAttribute("dataList", dataList);
		request.setAttribute("user", user);
		
		return "work_order_list";
	}
	
	public String toAddOrUpdate(){
		List<WorkOrderConfig>  secondConfigList = new ArrayList<WorkOrderConfig>();
		
		if(id!=null){
			entity = (WorkOrder) service.get(id);
			
			if(entity.getParentConfigId() != null && entity.getParentConfigId() > 0){
				entity.setSonConfigId(entity.getConfigId());
				entity.setConfigId(entity.getParentConfigId());
				
				secondConfigList = configService.getConfigListByPid(entity.getParentConfigId());
			}
			
			request.setAttribute("entity", entity);
		}
		
		List<WorkOrderConfig>  firstConfigList = configService.getConfigListByPid(0);
		if(!configService.isPrincipalOrAssigner(user.getRealName())){
			for(int i=0; i < firstConfigList.size(); i++){
				if(firstConfigList.get(i).getDepartment().contains("回转")){
					firstConfigList.remove(i);
					i--;
				}
			}
		}
		
		request.setAttribute("firstConfigList", firstConfigList);
		request.setAttribute("secondConfigList", secondConfigList);
		
		return "work_order_form";
	}
	
	public String toDeal() {
		entity = (WorkOrder) service.get(id);
		if(entity.getParentConfigId() != null && entity.getParentConfigId() > 0){
			entity.setSonConfigId(entity.getConfigId());
			entity.setConfigId(entity.getParentConfigId());
		}
		
		return "work_order_deal_form";
	}
	
	public String showInfo(){
		entity = (WorkOrder) service.get(id);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("woId", id);
		List<WorkOrderOperationLog>  operationLogList = (List<WorkOrderOperationLog>) operationLogService.fetchList(paramMap);
		request.setAttribute("operationLogList", operationLogList);
		return "work_order_info";
	}
	
	public String getSonConfigList(){
		int pid =Integer.valueOf(request.getParameter("pid")).intValue();
		List<WorkOrderConfig> list = null;
		
		if(pid > 0){
			list = configService.getConfigListByPid(pid);
		}
		JsonUtil.writeListResponse(list);
		
		return "work_order_form";
	}
	
	public String add(){
		
		entity.setAddPerson(user.getRealName());
		if(dealBySelf!=null&&dealBySelf==1){
			entity.setDealPerson(user.getRealName());
			entity.setState(2);
		}
		
		if(null != entity.getSonConfigId() && entity.getSonConfigId() > 0){
			entity.setConfigId(entity.getSonConfigId());
		}
		
		service.insert(entity);
		addOperationLog(entity.getId(), WorkOrderConstent.LogEvent.ADD, "发起工单");
		if(dealBySelf!=null&&dealBySelf==1){
			addOperationLog(entity.getId(), WorkOrderConstent.LogEvent.ASSIGN, "分配给:"+user.getRealName());
			remindDealPerson(entity,user);
		}else {
			remindAssigners(entity,"add",null);
		}
		return "success";
	}

    private void addOperationLog(Integer woId,String event,String content) {
        WorkOrderOperationLog  operationLog = new WorkOrderOperationLog();
		operationLog.setWoId(woId);
		operationLog.setEvent(event);
		operationLog.setContent(content);
		operationLog.setOperatePerson(user.getRealName());
		operationLogService.insert(operationLog);
    }
	

    public String update(){
		entity.setId(id);
		
		String event = WorkOrderConstent.LogEvent.EDIT;
		String logContent = "工单内容编辑";
		
		// 处理
		if(StringUtils.isNotBlank(entity.getSolveResult())) { 
			entity.setState(3);
			entity.setConfigId(null);
			
			event = WorkOrderConstent.LogEvent.DEAL;
			logContent = "处理结果："+ entity.getSolveResult();
		}else {
			if(null != entity.getSonConfigId() && entity.getSonConfigId() > 0){
				entity.setConfigId(entity.getSonConfigId());
			}
			
			WorkOrder oldEntity = (WorkOrder) service.get(id);
		    if(oldEntity.getConfigId()!= entity.getConfigId()){ //更新项目组时按退回处理
	            entity.setAddPerson(user.getRealName());
	            entity.setDealPerson("");
	            entity.setState(1);
	            remindAssigners(entity,"update",oldEntity);
	            
	            WorkOrderConfig config = (WorkOrderConfig) configService.get(entity.getConfigId());
	            
	            logContent = "项目组变更："+oldEntity.getDepartment()+"-->"+config.getDepartment()+"&nbsp;  &nbsp;  &nbsp;  &nbsp;变更原因："+entity.getRemark();
	        }else if(!oldEntity.getRemark().equals(entity.getRemark())){
	            
	        	event = WorkOrderConstent.LogEvent.REMARK;
	            logContent = "添加备注："+entity.getRemark();
	        }
		}
		
		service.update(entity);
		addOperationLog(entity.getId(), event, logContent);
		
		return "success";
	}
	
	public String delete(){
		service.delete(id);
		return "success	";
	}
	
	/**
	 * 处理入口列表页 
	 */
	public String list(){
		//判断登录人是否是负责人
	    responsibleConfig = configService.getResponsibleConfig(user.getRealName());
		
        if(searchVo != null) {
            // 根据menuId设置显示数据状态 menu_1:待分配 menu_2:处理中 menu_3:已处理
            searchVo.setState(searchVo.getMenuId());
        } else {
            searchVo = new WorkOrderVo();
            if(CollectionUtil.isNotEmpty(responsibleConfig)){ // 是负责人则获取所在项目组的数据、拥有分配权限
                searchVo.setResponsibleConfig(responsibleConfig);
                searchVo.setMenuId(1);
                searchVo.setState(1);
                isAssigner = true;
            }else{ //不是则获取自己为处理人的数据
                searchVo.setMenuId(2);
                searchVo.setState(2);
                isAssigner = false;
            }
        }
		
		//根据登陆人是否为项目组负责人设置数据权限
        if(CollectionUtil.isNotEmpty(responsibleConfig)){ // 是负责人则获取所在项目组的数据、拥有分配权限
            searchVo.setResponsibleConfig(responsibleConfig);
            searchVo.setAssignerName(user.getRealName());
            isAssigner = true;
        }else{ //不是则获取自己为处理人的数据
            isAssigner = false;
            searchVo.setDealPerson(user.getRealName());
            responsibleConfig = configService.getAllConfig();
        }
        
        Map<String, Object> paramMap = searchVo.toMap();
		
		if(page == null){
			page = new Page();
			page.setPageSize(20);
		}
		
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("dataLimitEnd", page.getPageSize());
		
		List<WorkOrder>  dataList = (List<WorkOrder>) service.fetchList(paramMap);
		members = getMembers(searchVo.getConfigId()==null?null:searchVo.getConfigId().toString(), responsibleConfig);
		
		page.setCount(service.count(paramMap));
		page.setCurrentPageCount(dataList.size());
		request.setAttribute("dataList", dataList);
		request.setAttribute("isAssigner", isAssigner);
		request.setAttribute("user", user);
		
		return "work_order_deal_list";
	}
	
	public String workOrderReport(){
		
		return "work_order_report";
	}
	
	public String getWorkOrderNumsByAjax(){
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("addTimeBgn", searchVo.getAddTimeBgn() + " 00:00:00");
		parameterMap.put("addTimeEnd", searchVo.getAddTimeEnd() + " 23:59:59");
		
		List<WorkOrderReport> reportData = service.getWorkOrderReportData(parameterMap);
		String json = JSONArray.fromObject(reportData).toString();
		
		// 获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图
		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置字符集
		response.setContentType("text/plain");// 设置输出为文字流
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(json);
		} catch (IOException e) {
			return "error";
		}finally{
			out.flush();
			out.close();
		}
		
		return "work_order_report";
	}
	
	/*public String checkExportsCount() {
	    //判断登录人是否是负责人
        responsibleConfig = configService.getResponsibleConfig(user.getRealName());
        
        if(searchVo != null) {
            // 根据menuId设置显示数据状态 menu_1:待分配 menu_2:处理中 menu_3:已处理
            searchVo.setState(searchVo.getMenuId());
        } else {
            searchVo = new WorkOrderVo();
            if(CollectionUtil.isNotEmpty(responsibleConfig)){ // 是负责人则获取所在项目组的数据、拥有分配权限
                searchVo.setResponsibleConfig(responsibleConfig);
                searchVo.setMenuId(1);
                searchVo.setState(1);
            }else{ //不是则获取自己为处理人的数据
                searchVo.setMenuId(2);
                searchVo.setState(2);
            }
        }
        
        //根据登陆人是否为项目组负责人设置数据权限
        if(CollectionUtil.isNotEmpty(responsibleConfig)){ // 是负责人则获取所在项目组的数据、拥有分配权限
            searchVo.setResponsibleConfig(responsibleConfig);
            searchVo.setAssignerName(user.getRealName());
        }else{ //不是则获取自己为处理人的数据
            searchVo.setDealPerson(user.getRealName());
            responsibleConfig = this.getAllConfig();
        }
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = searchVo.toMap();
	    Integer count  = service.count(paramMap);
	    if(count ==0 ) {
            info.put("success", false);
            info.put("msg", "没有数据");
        }else if(count > 2000){
            info.put("success", false);
            info.put("msg", "导出数据过大，请增加筛选条件后再执行");
        }else {
            info.put("success", true);
        }
	    return "info";
	}
	
	public String exports() {
	  //判断登录人是否是负责人
        responsibleConfig = configService.getResponsibleConfig(user.getRealName());
        
        if(searchVo != null) {
            // 根据menuId设置显示数据状态 menu_1:待分配 menu_2:处理中 menu_3:已处理
            searchVo.setState(searchVo.getMenuId());
        } else {
            searchVo = new WorkOrderVo();
            if(CollectionUtil.isNotEmpty(responsibleConfig)){ // 是负责人则获取所在项目组的数据、拥有分配权限
                searchVo.setResponsibleConfig(responsibleConfig);
                searchVo.setMenuId(1);
                searchVo.setState(1);
            }else{ //不是则获取自己为处理人的数据
                searchVo.setMenuId(2);
                searchVo.setState(2);
            }
        }
        
        //根据登陆人是否为项目组负责人设置数据权限
        if(CollectionUtil.isNotEmpty(responsibleConfig)){ // 是负责人则获取所在项目组的数据、拥有分配权限
            searchVo.setResponsibleConfig(responsibleConfig);
            searchVo.setAssignerName(user.getRealName());
        }else{ //不是则获取自己为处理人的数据
            searchVo.setDealPerson(user.getRealName());
            responsibleConfig = this.getAllConfig();
        }
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = searchVo.toMap();
        
        List<WorkOrder>  dataList = (List<WorkOrder>) service.fetchList(paramMap);
        if(CollectionUtil.isNotEmpty(dataList)) {
            try {
                String[] headers = new String[]{"单号", "发起时间", "来电事由", "完成时间", "项目组", "业务分类", "发起人", "处理人", "工单状态"};
                String[] exportsFields = new String[]{"id", "addTime", "phoneMatter", "updateTime", "department",
                        "businessClass", "addPerson", "dealPerson", "state"};
                for(WorkOrder wo : dataList) {
                    if(wo.getState()!=3) {
                        wo.setUpdateTime(null);
                    }else{
                        break;
                    }
                }
                
                new POIUtil<WorkOrder>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields), dataList,
                        new CellDataMapperHandler() {
                            @Override
                            public String mapToCell(String fieldName, Object value) {
                                String textValue = "";
                                if(value instanceof Date) {
                                    Date date = (Date) value;
                                    textValue = DateUtil.formatDateTime(date);
                                    return textValue;
                                }
                                
                                if ("state".equals(fieldName)) {
                                    switch ((Integer) value) {
                                    case 1:
                                        textValue = "待分配";
                                        break;
                                    case 2:
                                        textValue = "处理中";
                                        break;
                                    case 3:
                                        textValue = "已处理";
                                        break;
                                    default:
                                        break;
                                    }
                                }else{
                                    textValue=value==null?"":value+"";
                                }
                                return textValue;
                            }
                        });
            } catch(Exception e) {
            	logger.error(e.getMessage(), e);
            }

        }
        
        return null;
	}
	*/
	
	public String getMembersByDepartment(){
	    List<String> departments = new ArrayList<String>();
	    String configId = request.getParameter("configId");
	    List<String> memberList = getMembers(configId,responsibleConfig);
	    info.put("memberList", JSONArray.fromObject(memberList));
	    return "info";
	}
	
	/**
     * @param configId
     * @param responsibleConfig
     * @return
     */
    private List<String> getMembers(String configId, List<WorkOrderConfig> responsibleConfig) {
        List<String> memberList = new ArrayList<String>();
        responsibleConfig = configService.getResponsibleConfig(user.getRealName());
        if(StringUtils.isNotBlank(configId)){
            for(WorkOrderConfig workOrderConfig : responsibleConfig) {
                if(workOrderConfig.getId() == Integer.parseInt(configId)){
                    memberList = Arrays.asList(workOrderConfig.getMembers().split(","));
                }
            }
        }else {
            for(WorkOrderConfig workOrderConfig : responsibleConfig) {
                    List<String> tempMemberList = Arrays.asList(workOrderConfig.getMembers().split(","));
                    memberList = (List<String>) CollectionUtils.union(memberList, tempMemberList);
            }
        }
        return memberList;
    }

    public String assign(){
		String assignTo = request.getParameter("searchVo.dealPerson");
		UserEntity assignedUser = userService.getUserByRealName(assignTo);
		
		if(assignedUser.getId() == null){
		    info.put("success", false);
			info.put("msg",  "指定的分配人不存在，请重新分配");
		}else{
			WorkOrder e = null;
			for (Integer id : ids) {
				e = (WorkOrder) service.get(id);
				e.setDealPerson(assignTo);
				e.setState(2);
				service.update(e);
				remindDealPerson(e,assignedUser);
				
				addOperationLog(id, WorkOrderConstent.LogEvent.ASSIGN, "分配给:"+assignTo);
			}
			info.put("success", true);
			info.put("msg", "成功分配给"+assignTo);
		}
		
		return "info";
	}
	
    private void remindAssigners(WorkOrder entity, String remindType, WorkOrder oldEntity) {
      //发送rtx
        //取项目组负责人
        WorkOrderConfig config = (WorkOrderConfig) configService.get(entity.getConfigId());
        String assigners = config.getAssigners();
        StringBuilder rtxContent = new StringBuilder();
        String title = "";
        if("add".equals(remindType)) {
            title = "工单发起提醒";
            rtxContent.append("您有新的工单等待分配，工单号[").append(entity.getId()).append("]\r");
        }else if("update".equals(remindType)) {
            title = "工单项目组变更提醒";
            WorkOrderConfig oldConfig = (WorkOrderConfig) configService.get(oldEntity.getConfigId());
            rtxContent.append("工单[").append(entity.getId()).append("]项目组变更：[").append(oldConfig.getDepartment()).append("]-->[").append(config.getDepartment()).append("]\r")
                .append("变更原因：").append(entity.getRemark()).append("\r")
                .append("操作人：").append(user.getRealName()).append("\r请重新指定分配人\r");
        }
        UserEntity assignerUser;
        for(String assigner:assigners.split(",")){
            assignerUser = userService.getUserByRealName(assigner);
            if(assignerUser!=null && assignerUser.getId()!=null) {
                new RTXSenderThread(assignerUser.getId(), assigner, title, rtxContent.toString()).start();
            }
        }
        
    }
    
    private void remindDealPerson(WorkOrder workOrder, UserEntity assignedUser) {
        // 发送rtx
        StringBuilder rtxContent = new StringBuilder();
        rtxContent.append("您有新的工单等待处理，工单号[").append(workOrder.getId()).append("]\r")
            .append("如有疑问请联系分配人：").append(user.getRealName()).append("\r");
        if(StringUtils.isNotBlank(assignedUser.getEmail())) {
            new RTXSenderThread(assignedUser.getId(), assignedUser.getRealName(), "工单分配提醒", rtxContent.toString()).start();
        }
    }
    
    public String isPhoneUnique(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("phone", entity.getPhone());
        if(service.count(paramMap)==0){
            info.put("unique", true);
        }else{
            info.put("unique", false);
            WorkOrder wo = (WorkOrder) service.get(paramMap);
            info.put("id", wo.getId());
        }

        return "info";
    }

	public WorkOrderVo getSearchVo() {
		return searchVo;
	}

	public void setSearchVo(WorkOrderVo searchVo) {
		this.searchVo = searchVo;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public boolean isAssigner() {
        return isAssigner;
    }

    public void setAssigner(boolean isAssigner) {
        this.isAssigner = isAssigner;
    }

    public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<WorkOrderConfig> getResponsibleConfig() {
        return responsibleConfig;
    }

    public void setResponsibleConfig(List<WorkOrderConfig> responsibleConfig) {
        this.responsibleConfig = responsibleConfig;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public List<String> getResponsibledeps() {
        if(CollectionUtil.isNotEmpty(responsibleConfig)) {
            for(WorkOrderConfig config : responsibleConfig) {
                responsibledeps.add(config.getDepartment());
            }
        }
        return responsibledeps;
    }

    public void setResponsibledeps(List<String> responsibledeps) {
        this.responsibledeps = responsibledeps;
    }

	public Integer getDealBySelf() {
		return dealBySelf;
	}

	public void setDealBySelf(Integer dealBySelf) {
		this.dealBySelf = dealBySelf;
	}

	public List<WorkOrderConfig> getFirstConfig() {
		return configService.getConfigListByPid(0);
	}

	public void setFirstConfig(List<WorkOrderConfig> firstConfig) {
		this.firstConfig = firstConfig;
	}
}
