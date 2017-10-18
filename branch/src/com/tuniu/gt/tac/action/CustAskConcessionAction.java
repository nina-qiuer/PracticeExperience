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
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.tac.entity.ConcessionalBiddingAgainstRival;
import com.tuniu.gt.tac.entity.CustAskConcession;
import com.tuniu.gt.tac.service.IConcessionalBiddingAgainstRivalService;
import com.tuniu.gt.tac.service.ICustAskConcessionService;
import com.tuniu.gt.tac.vo.ConcessionalBiddingAgainstRivalVo;
import com.tuniu.gt.tac.vo.CustAskConcessionVo;
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
@Service("tac_action-custAskConcession")
@Scope("prototype")
public class CustAskConcessionAction  extends FrmBaseAction<ICustAskConcessionService, CustAskConcession> {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private CustAskConcessionVo searchVo; //列表搜索条件
	private Page page;
	private Map<String,Object>  info;
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
	
	public CustAskConcessionAction() {
		setManageUrl("tac");
		info = new HashMap<String, Object>(1);
	}
	
	@Autowired
	@Qualifier("tac_service_impl-custAskConcession")
	public void setService(ICustAskConcessionService service) {
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
		
		List<CustAskConcession>  dataList = (List<CustAskConcession>) service.fetchList(paramMap);
		page.setCount(service.count(paramMap));
		page.setCurrentPageCount(dataList.size());
		request.setAttribute("dataList", dataList);
		
		return "cust_ask_concession_list";
	}
	
	public String toAddOrUpdate(){
		if(id!=null){
			entity = (CustAskConcession) service.get(id);
		}
		
		UserDepartmentVo departmentVo = userService.getUserDepartmentVoByRealName(user.getRealName());
		entity.setCustomerDepartment(departmentVo.getDepartmentName());
		entity.setCustomerGroup(departmentVo.getGroupName());
		request.setAttribute("entity", entity);
		return "cust_ask_concession_form";
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
            searchVo = new CustAskConcessionVo();
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
        
        List<CustAskConcession>  dataList = (List<CustAskConcession>) service.fetchList(paramMap);
        if(CollectionUtil.isNotEmpty(dataList)) {
            try {
                String[] headers = new String[]{"编号", "添加日期", "订单号", "客服部", "客服组", "客服专员", "产品原价", "让价额度", "订单人数", "附加赠送", "让价承担方", "产品姓名"};
                String[] exportsFields = new String[]{"id", "addTime", "orderId", "customerDepartment", "customerGroup","customer", "price", "concessionalLimit", "personNum","additionPresentation","concessionalBearer","prdOfficerName"};

                new POIUtil<CustAskConcession>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields), dataList,
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
	
	public String fillRelatedFields(){
        
        OrderEntity orderEntity = complaintService.queryNewOrderInfo(orderId);
        //订单相关信息
        if(orderEntity.getErrCode()==0){
            info.put("personNum", orderEntity.getAdultNum()+orderEntity.getChildrenNum());
            Integer routeId = orderEntity.getRouteId();
            Map<String, Object> productInfo = tspService.getProductInfo((long)routeId);
            info.put("prdOfficerName", productInfo.get("ownerName"));
        }
        
        return "info";
    }
	
	public CustAskConcessionVo getSearchVo() {
        return searchVo;
    }

    public void setSearchVo(CustAskConcessionVo searchVo) {
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
