/**
 * 
 */
package com.tuniu.gt.tac.action;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.AgencyDto;
import com.tuniu.gt.complaint.entity.OrderEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.tac.entity.PrdProcurementIssusFeedbak;
import com.tuniu.gt.tac.service.IPrdProcurementIssuesFeedbackService;
import com.tuniu.gt.tac.vo.PrdProcurementIssusFeedbakVo;
import com.tuniu.gt.toolkit.CellDataMapperHandler;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.POIUtil;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.uc.vo.UserDepartmentVo;
import com.tuniu.gt.warning.common.Page;

/**
 * @author jiangye
 *
 */
@Service("tac_action-prdProcurementIssuesFeedback")
@Scope("prototype")
public class PrdProcurementIssuesFeedbackAction  extends FrmBaseAction<IPrdProcurementIssuesFeedbackService, PrdProcurementIssusFeedbak> {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private PrdProcurementIssusFeedbakVo searchVo; //列表搜索条件
	private Page page;
	private Map<String,Object>  info;
	
	private List<String> issueTypes;
	private String orderId;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	public PrdProcurementIssuesFeedbackAction() {
		setManageUrl("tac");
		info = new HashMap<String, Object>(1);
	}
	
	@Autowired
	@Qualifier("tac_service_impl-prdProcurementIssuesFeedback")
	public void setService(IPrdProcurementIssuesFeedbackService service) {
		this.service = service;
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
		
		List<PrdProcurementIssusFeedbak>  dataList = (List<PrdProcurementIssusFeedbak>) service.fetchList(paramMap);
		page.setCount(service.count(paramMap));
		page.setCurrentPageCount(dataList.size());
		request.setAttribute("dataList", dataList);
		
		return "product_procurement_issues_feedback_list";
	}
	
	public String toAddOrUpdate(){
		if(id!=null){
			entity = (PrdProcurementIssusFeedbak) service.get(id);
		}else{
		    entity.setResolveState(0);//struts2 radio设置默认值
		}
		
		UserDepartmentVo departmentVo = userService.getUserDepartmentVoByUserId(user.getId());
		entity.setDepartment(departmentVo.getDepartmentName()+"/"+departmentVo.getGroupName());
		
		request.setAttribute("entity", entity);
		
		return "product_procurement_issues_feedback_form";
	}
	
	
	public String add(){
	    entity.setAddPerson(user.getRealName());
	    service.insert(entity);
		return "success";
	}

    public String update(){
        entity.setId(id);
		service.update(entity);
		return "success";
	}
	
	public String delete(){
		service.delete(id);
		return "success	";
	}
	
	public String fillRelatedFields(){
	    OrderEntity orderEntity = complaintService.queryNewOrderInfo(orderId);
	    //订单相关信息
	    info.put("departDate", DateUtil.formatDate(orderEntity.getStartTime()));
	    Integer routeId = orderEntity.getRouteId();
	    info.put("routeId", routeId);
	    if(CollectionUtil.isNotEmpty(orderEntity.getAgencyList())){
	        AgencyDto agencyDto = orderEntity.getAgencyList().get(0);
	        info.put("supplier", agencyDto.getAgencyName());
	    }
	    //BOH获取产品信息
	    if(routeId!=null){
	        Map<String, Object> productInfo = tspService.getProductInfo((long)routeId);
	        info.put("prdOfficer", productInfo.get("ownerName"));
	        info.put("prdManager", productInfo.get("managerName"));
	        UserEntity prdMajordomoUser = ComplaintRestClient.getReporter(productInfo.get("managerName")+"");
	        info.put("prdMajordomo", prdMajordomoUser.getRealName());
	    }
        
	    return "info";
	}
	
	public String checkExportsCount(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (null == searchVo) {
            searchVo = new PrdProcurementIssusFeedbakVo();
        }
        paramMap.putAll(searchVo.toMap());
        Integer count  = service.count(paramMap);
        
        if(count ==0 ) {
            info.put("success", false);
            info.put("msg", "没有数据");
        }else if(count > 10000){
            info.put("success", false);
            info.put("msg", "导出数据过大，请增加筛选条件后再执行");
        }else {
            info.put("success", true);
        }
        
        return "info";
    }
	
	public String exports() {
        
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = searchVo.toMap();
        
        List<PrdProcurementIssusFeedbak>  dataList = (List<PrdProcurementIssusFeedbak>) service.fetchList(paramMap);
        if(CollectionUtil.isNotEmpty(dataList)) {
            try {
                String[] headers = new String[]{"编号", "添加日期", "组别", "客服", "(预定)区域", "目的地类型", "订单号", "出发日期", "线路编号", "问题类型", "采购/产品专员", "采购/产品经理", "产品经理部门", "采购/产品总监(分总)", "供应商", "问题描述", "是否解决"};
                String[] exportsFields = new String[]{"id", "addTime", "department", "addPerson", "area","destType", "orderId", "departDate", "routeId","issueType","prdOfficer","prdManager","prdManagerDep","prdMajordomo","supplier","description","resolveState"};

                new POIUtil<PrdProcurementIssusFeedbak>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields), dataList,
                        new CellDataMapperHandler() {
                            @Override
                            public String mapToCell(String fieldName, Object value) {
                                String textValue = "";
                                if(value instanceof Date) {
                                    Date date = (Date) value;
                                    textValue = DateUtil.formatDate(date);
                                    return textValue;
                                }
                                
                                if ("resolveState".equals(fieldName)) {
                                    switch ((Integer) value) {
                                    case 0:
                                        textValue = "否";
                                        break;
                                    case 1:
                                        textValue = "是";
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
	
	
	public PrdProcurementIssusFeedbakVo getSearchVo() {
        return searchVo;
    }

    public void setSearchVo(PrdProcurementIssusFeedbakVo searchVo) {
        this.searchVo = searchVo;
    }

    public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

    public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

    public List<String> getIssueTypes() {
        return DBConfigManager.getConfigAsList("business.tac.prd.issueType");
    }

    public void setIssueTypes(List<String> issueTypes) {
        this.issueTypes = issueTypes;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
