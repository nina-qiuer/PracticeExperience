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
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.tac.entity.CustAskConcession;
import com.tuniu.gt.tac.entity.IssueImprovementFeedback;
import com.tuniu.gt.tac.service.ICustAskConcessionService;
import com.tuniu.gt.tac.service.IIssueImprovementFeedbackService;
import com.tuniu.gt.tac.vo.CustAskConcessionVo;
import com.tuniu.gt.tac.vo.IssueImprovementFeedbackVo;
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
@Service("tac_action-issueImprovementFeedback")
@Scope("prototype")
public class IssueImprovementFeedbackAction  extends FrmBaseAction<IIssueImprovementFeedbackService, IssueImprovementFeedback> {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private IssueImprovementFeedbackVo searchVo; //列表搜索条件
	private Page page;
	private Map<String,Object>  info;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	public IssueImprovementFeedbackAction() {
		setManageUrl("tac");
		info = new HashMap<String, Object>(1);
	}
	
	@Autowired
	@Qualifier("tac_service_impl-issueImprovementFeedback")
	public void setService(IIssueImprovementFeedbackService service) {
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
		
		List<IssueImprovementFeedback>  dataList = (List<IssueImprovementFeedback>) service.fetchList(paramMap);
		page.setCount(service.count(paramMap));
		page.setCurrentPageCount(dataList.size());
		request.setAttribute("dataList", dataList);
		
		return "issue_improvement_feedback_list";
	}
	
	public String toAddOrUpdate(){
		if(id!=null){
			entity = (IssueImprovementFeedback) service.get(id);
			request.setAttribute("entity", entity);
		}
		
		UserDepartmentVo departmentVo = userService.getUserDepartmentVoByRealName(user.getRealName());
		info.put("department", JSONObject.fromObject(departmentVo));
		entity.setCustomerDepartment(departmentVo.getBusinessUnitName());
		entity.setCustomerScndDepartment(departmentVo.getDepartmentName());
		entity.setCustomerGroup(departmentVo.getGroupName());
		
		return "issue_improvement_feedback_form";
	}
	
	
	public String add(){
	    entity.setCustomer(user.getRealName());
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
	
	public String checkExportsCount(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (null == searchVo) {
            searchVo = new IssueImprovementFeedbackVo();
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
        
        List<IssueImprovementFeedback>  dataList = (List<IssueImprovementFeedback>) service.fetchList(paramMap);
        if(CollectionUtil.isNotEmpty(dataList)) {
            try {
                String[] headers = new String[]{"编号", "一级部门", "二级部门", "三级组", "客服人员", "关联订单号", "问题描述", "改进建议", "添加时间"};
                String[] exportsFields = new String[]{"id", "customerDepartment", "customerScndDepartment", "customerGroup", "customer","orderId", "issueDescription", "advice", "addTime"};

                new POIUtil<IssueImprovementFeedback>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields), dataList,
                        new CellDataMapperHandler() {
                            @Override
                            public String mapToCell(String fieldName, Object value) {
                                String textValue = "";
                                if(value instanceof Date) {
                                    Date date = (Date) value;
                                    textValue = DateUtil.formatDate(date);
                                    return textValue;
                                }
                                
                                textValue=value==null?"":value+"";
                                
                                return textValue;
                            }
                        });
            } catch(Exception e) {
            	logger.error(e.getMessage(), e);
            }

        }
        
        return null;
	}
	
	public IssueImprovementFeedbackVo getSearchVo() {
        return searchVo;
    }

    public void setSearchVo(IssueImprovementFeedbackVo searchVo) {
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

}
