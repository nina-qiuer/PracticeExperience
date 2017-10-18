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

import org.apache.commons.lang.StringUtils;
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
import com.tuniu.gt.tac.entity.PrdProcurementIssusFeedbak;
import com.tuniu.gt.tac.service.IConcessionalBiddingAgainstRivalService;
import com.tuniu.gt.tac.service.IPrdProcurementIssuesFeedbackService;
import com.tuniu.gt.tac.vo.ConcessionalBiddingAgainstRivalVo;
import com.tuniu.gt.tac.vo.PrdProcurementIssusFeedbakVo;
import com.tuniu.gt.toolkit.CellDataMapperHandler;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.POIUtil;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.uc.vo.UserDepartmentVo;
import com.tuniu.gt.warning.common.Page;
import com.tuniu.gt.workorder.entity.WorkOrder;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

/**
 * @author jiangye
 *
 */
@Service("tac_action-concessionalBiddingAgainstRival")
@Scope("prototype")
public class ConcessionalBiddingAgainstRivalAction  extends FrmBaseAction<IConcessionalBiddingAgainstRivalService, ConcessionalBiddingAgainstRival> {
	
	private Logger logger = Logger.getLogger(getClass());
	
	private ConcessionalBiddingAgainstRivalVo searchVo; //列表搜索条件
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
	
	public ConcessionalBiddingAgainstRivalAction() {
		setManageUrl("tac");
		info = new HashMap<String, Object>(1);
	}
	
	@Autowired
	@Qualifier("tac_service_impl-concessionalBiddingAgainstRival")
	public void setService(IConcessionalBiddingAgainstRivalService service) {
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
		
		List<ConcessionalBiddingAgainstRival>  dataList = (List<ConcessionalBiddingAgainstRival>) service.fetchList(paramMap);
		page.setCount(service.count(paramMap));
		page.setCurrentPageCount(dataList.size());
		request.setAttribute("dataList", dataList);
		
		return "concessional_bidding_against_rival_list";
	}
	
	public String toAddOrUpdate(){
		if(id!=null){
			entity = (ConcessionalBiddingAgainstRival) service.get(id);
		}
		
		UserDepartmentVo customerDepartmentVo = userService.getUserDepartmentVoByRealName(user.getRealName());
		entity.setCustomerDepartment(customerDepartmentVo.getDepartmentName());
		entity.setCustomerGroup(customerDepartmentVo.getGroupName());
		request.setAttribute("entity", entity);
		
		return "concessional_bidding_against_rival_form";
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
            searchVo = new ConcessionalBiddingAgainstRivalVo();
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
        
        List<ConcessionalBiddingAgainstRival>  dataList = (List<ConcessionalBiddingAgainstRival>) service.fetchList(paramMap);
        if(CollectionUtil.isNotEmpty(dataList)) {
            try {
                String[] headers = new String[]{"编号", "添加日期", "订单号","竞争对手", "对手价格", "对接线路连接", "对手价格证据", "我司价格", "让价额度", "附加赠送", "让价方", "让价人名", "产品不予让价原因", "客服部", "客服组", "客服专员","产品部", "产品组"};
                String[] exportsFields = new String[]{"id", "addTime","orderId", "rival", "rivalPrice", "routeHref","rivalPriceProof", "tuniuPrice", "concessionalLimit", "additionPresentation","concessionalSide","concessionalName","unConcessionalReason","customerDepartment","customerGroup","customer","productDepartment","productGroup"};

                new POIUtil<ConcessionalBiddingAgainstRival>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields), dataList,
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
	
    public String fillRelatedFields() {
        OrderEntity orderEntity = complaintService.queryNewOrderInfo(orderId);
        
        UserDepartmentVo prdOfficerDepartmentVo = null;
        // BOH获取产品信息
        if(orderEntity.getErrCode()==0){
            Integer routeId = orderEntity.getRouteId();
            Map<String, Object> productInfo = tspService.getProductInfo((long) routeId);
            if(StringUtil.isNotEmpty(productInfo.get("ownerName") + "")) {
                prdOfficerDepartmentVo = userService.getUserDepartmentVoByRealName(productInfo.get("ownerName")
                        + "");
            }
        }

        info.put("prdOfficerDepartment", JSONObject.fromObject(prdOfficerDepartmentVo));
        return "info";
    }
	
    public ConcessionalBiddingAgainstRivalVo getSearchVo() {
        return searchVo;
    }

    public void setSearchVo(ConcessionalBiddingAgainstRivalVo searchVo) {
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
