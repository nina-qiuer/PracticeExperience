/**
 * 
 */
package com.tuniu.gt.tac.action;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.service.IAttachService;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.tac.entity.CommissionSummary;
import com.tuniu.gt.tac.entity.NewEmployeesSalaryAdjustment;
import com.tuniu.gt.tac.service.INewEmployeesSalaryAdjustmentService;
import com.tuniu.gt.tac.vo.CommissionSummaryVo;
import com.tuniu.gt.tac.vo.NewEmployeesSalaryAdjustmentVo;
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
@Service("tac_action-newEmployeesSalaryAdjustment")
@Scope("prototype")
public class NewEmployeesSalaryAdjustmentAction  extends FrmBaseAction<INewEmployeesSalaryAdjustmentService, NewEmployeesSalaryAdjustment> {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private NewEmployeesSalaryAdjustmentVo searchVo; //列表搜索条件
	private Page page;
	private Map<String,Object>  info;
	
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	
	public NewEmployeesSalaryAdjustmentAction() {
		setManageUrl("tac");
		info = new HashMap<String, Object>(1);
	}
	
	@Autowired
	@Qualifier("tac_service_impl-newEmployeesSalaryAdjustment")
	public void setService(INewEmployeesSalaryAdjustmentService service) {
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
		
		List<NewEmployeesSalaryAdjustment>  dataList = (List<NewEmployeesSalaryAdjustment>) service.fetchList(paramMap);
		page.setCount(service.count(paramMap));
		page.setCurrentPageCount(dataList.size());
		request.setAttribute("dataList", dataList);
		
		return "new_employees_salary_adjustment_list";
	}
	
	public String toAddOrUpdate(){
		if(id!=null){
			entity = (NewEmployeesSalaryAdjustment) service.get(id);
		}else{
		    entity.setIsFilToRprtLstMnth(0);
		}
		
		request.setAttribute("entity", entity);
		return "new_employees_salary_adjustment_form";
	}
	
	
	public String add(){
	    entity.setCustomerManager(user.getRealName());
	    service.insert(entity);
		return "success";
	}

    public String update(){
        entity.setId(id);
		service.update(entity);
		return "success";
	}
	
	
	public String checkExportsCount(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (null == searchVo) {
            searchVo = new NewEmployeesSalaryAdjustmentVo();
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
        
        List<NewEmployeesSalaryAdjustment>  dataList = (List<NewEmployeesSalaryAdjustment>) service.fetchList(paramMap);
        if(CollectionUtil.isNotEmpty(dataList)) {
            try {
                String[] headers = new String[]{"编号", "添加日期", "类别","客服经理", "工号","一级部门","二级部门","三级部门","客服人员","是否为上月漏报员工"};
                String[] exportsFields = new String[]{"id", "addTime", "category","customerManager", "workNum","businessUnitName","departmentName","groupName","customer","isFilToRprtLstMnth"};

                new POIUtil<NewEmployeesSalaryAdjustment>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields), dataList,
                        new CellDataMapperHandler() {
                            @Override
                            public String mapToCell(String fieldName, Object value) {
                                String textValue = "";
                                if(value instanceof Date) {
                                    Date date = (Date) value;
                                    textValue = DateUtil.formatDate(date);
                                    return textValue;
                                }
                                
                                if("isFilToRprtLstMnth".equals(fieldName)){
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
	
	public String getUserDepartmentVo(){
	    String realName = request.getParameter("realName");
	    if(StringUtils.isNotBlank(realName)){
	        UserDepartmentVo userDepartmentVo = userService.getUserDepartmentVoByRealName(realName);
	        info = JSONObject.fromObject(userDepartmentVo);
	        info.put("success", true);
	    }else{
	        info.put("success", false);
	    }
	    return "info";
	}
	
    public NewEmployeesSalaryAdjustmentVo getSearchVo() {
        return searchVo;
    }

    public void setSearchVo(NewEmployeesSalaryAdjustmentVo searchVo) {
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
