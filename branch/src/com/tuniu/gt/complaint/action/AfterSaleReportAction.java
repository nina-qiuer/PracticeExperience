/**
 * 
 */
package com.tuniu.gt.complaint.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IAfterSaleReportService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.vo.AfterSaleReportVo;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.uc.datastructure.DepartUsersTree;
import com.tuniu.gt.uc.service.user.IDepartmentService;

/**
 * 售后报表action
 * @author jiangye
 *
 */
@Service("complaint_action-after_sale_report")
@Scope("prototype")
public class AfterSaleReportAction  extends FrmBaseAction<IAfterSaleReportService, AfterSaleReportVo>{
    
	private Logger logger = Logger.getLogger(getClass());
	
    private String assignTimeBgn;

    private String assignTimeEnd;
    
    private String finishTimeBgn;
    
    private String finishTimeEnd;
    
    private String dealName;
    
    private String dealDepart;
    
    private String statisticsField;
    
    private String statisticsFieldType;
    


    @Autowired
    @Qualifier("uc_service_user_impl-department")
    private IDepartmentService departmentService;
    
  //引入投诉sercie
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint")
    private IComplaintService complaintService;
    
    public String execute(){
        if(StringUtils.isBlank(assignTimeBgn)) {
            assignTimeBgn = DateUtil.formatDate(new Date());
        }
        if(StringUtils.isBlank(assignTimeEnd)) {
            assignTimeEnd = DateUtil.formatDate(new Date());
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("assignTimeBgn", assignTimeBgn);
        paramMap.put("assignTimeEnd", assignTimeEnd);
        DepartUsersTree vipSalerDepTree = departmentService.buildDepartUsersTree(2580, 4); //会员事业部
        DepartUsersTree guoneiDepTree = departmentService.buildDepartUsersTree(6196, 4); //国内旅游顾问中心
        DepartUsersTree postSaleDepTree = departmentService.buildDepartUsersTree(2581, 4);//售后服务中心
        DepartUsersTree trafficSaleDeptree = departmentService.buildDepartUsersTree(7443, 4); //旅行客服中心
        DepartUsersTree jtSaleDeptree = departmentService.buildDepartUsersTree(7118, 4); //交通运营中心
        List<DepartUsersTree>  depUsersTreeList = new ArrayList<DepartUsersTree>();
        depUsersTreeList.add(postSaleDepTree);
        depUsersTreeList.add(trafficSaleDeptree);
        depUsersTreeList.add(jtSaleDeptree);
        depUsersTreeList.add(vipSalerDepTree);
        depUsersTreeList.add(guoneiDepTree);
        request.setAttribute("depUsersTreeList", depUsersTreeList);
        
        Map<String, Map<String, Integer>> reportData = complaintService.getAfterSaleReport(paramMap);
        request.setAttribute("reportData", reportData);
        
        return "after_sale_report";
    }
    
    /**
     * 未完结即时统计报表
     * @return
     */
    public String unDoneCmpList(){
        DepartUsersTree vipSalerDepTree = departmentService.buildDepartUsersTree(2580, 4); //会员事业部
        DepartUsersTree guoneiDepTree = departmentService.buildDepartUsersTree(6196, 4); //国内旅游顾问中心
        DepartUsersTree postSaleDepTree = departmentService.buildDepartUsersTree(2581, 4);//售后部
        DepartUsersTree hotelDepTree = departmentService.buildDepartUsersTree(1526, 4);//酒店BU
        DepartUsersTree eastRegionDeptree = departmentService.buildDepartUsersTree(3274, 4); //   华东大区
        DepartUsersTree northRegionDeptree = departmentService.buildDepartUsersTree(3392, 4); //华北大区
        DepartUsersTree southRegionDeptree = departmentService.buildDepartUsersTree(3635, 4); //华南大区
        
        List<DepartUsersTree>  depUsersTreeList = new ArrayList<DepartUsersTree>();
        depUsersTreeList.add(postSaleDepTree);
        depUsersTreeList.add(hotelDepTree);
        depUsersTreeList.add(eastRegionDeptree);
        depUsersTreeList.add(northRegionDeptree);
        depUsersTreeList.add(southRegionDeptree);
        depUsersTreeList.add(vipSalerDepTree);
        depUsersTreeList.add(guoneiDepTree);
        request.setAttribute("depUsersTreeList", depUsersTreeList);
        Map<String, Map<String, Integer>> reportData = complaintService.getPostSaleUnDoneComplaintReport();
        request.setAttribute("reportData", reportData);
        
        return "undone_cmplist_report";
    }
    
    
    /**
     * 未完结即时统计报表  旅游顾问中心
     * @return
     */
    public String unDoneCmpListOfTravelConsultant(){
        DepartUsersTree tree1 = departmentService.buildDepartUsersTree(4873, 4); //华北旅游顾问中心
        DepartUsersTree tree2 = departmentService.buildDepartUsersTree(4875, 4); //西南旅游顾问中心
        DepartUsersTree tree3 = departmentService.buildDepartUsersTree(4944, 4); //华东旅游顾问中心
        
        List<DepartUsersTree>  depUsersTreeList = new ArrayList<DepartUsersTree>();
        depUsersTreeList.add(tree1);
        depUsersTreeList.add(tree2);
        depUsersTreeList.add(tree3);
        request.setAttribute("depUsersTreeList", depUsersTreeList);
        
        Map<String, Map<String, Integer>> reportData = complaintService.getPostSaleUnDoneComplaintReport();
        request.setAttribute("reportData", reportData);
        
        return"undone_cmplist_report_travel_consultant";
    }
    
    public String completeCmpList() {
        if(StringUtils.isBlank(finishTimeBgn)) {
            finishTimeBgn = DateUtil.formatDate(new Date());
        }
        if(StringUtils.isBlank(finishTimeEnd)) {
            finishTimeEnd = DateUtil.formatDate(new Date());
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("finishTimeBgn", finishTimeBgn);
        paramMap.put("finishTimeEnd", finishTimeEnd);
        List<DepartUsersTree>  depUsersTreeList = new ArrayList<DepartUsersTree>();
        DepartUsersTree eastRegionDeptree = departmentService.buildDepartUsersTree(3274, 4); //华东大区
        DepartUsersTree northRegionDeptree = departmentService.buildDepartUsersTree(3392, 4); //华北大区
        DepartUsersTree southRegionDeptree = departmentService.buildDepartUsersTree(3635, 4); //华南大区
        DepartUsersTree afterSaleDeptree = departmentService.buildDepartUsersTree(2581, 4); //售后服务中心
        DepartUsersTree vipSalerDepTree = departmentService.buildDepartUsersTree(2580, 4); //会员事业部
        DepartUsersTree guoneiDepTree = departmentService.buildDepartUsersTree(6196, 4); //国内旅游顾问中心
        depUsersTreeList.add(eastRegionDeptree);
        depUsersTreeList.add(northRegionDeptree);
        depUsersTreeList.add(southRegionDeptree);
        depUsersTreeList.add(afterSaleDeptree);
        depUsersTreeList.add(vipSalerDepTree);
        depUsersTreeList.add(guoneiDepTree);
        request.setAttribute("depUsersTreeList", depUsersTreeList);
        Map<String, Map<String, Integer>> reportData = complaintService.getPostSaleCompleteCmpReport(paramMap);
        request.setAttribute("reportData", reportData);
        return "complete_cmplist_report";
    }
    
    
    public String getCmpDetailList(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startAssignTime", assignTimeBgn);
        paramMap.put("endAssignTime", assignTimeEnd+" 23:59:59");
        try {
            paramMap.put("dealName",java.net.URLDecoder.decode(dealName,"utf-8"));
            if("routeType".equals(statisticsFieldType)) {
                paramMap.put("destCategoryName", java.net.URLDecoder.decode(statisticsField,"utf-8"));
            } else if( "singleResource".equals(statisticsFieldType)) {
                paramMap.put("afterSaleRouteType", java.net.URLDecoder.decode(statisticsField,"utf-8"));
            } else if( "noneOrder".equals(statisticsFieldType)) {
                paramMap.put("noneOrder", 1);
            } else if( "reparations".equals(statisticsFieldType)) {
                paramMap.put("isReparations", 1);
            } else if( "otherType".equals(statisticsFieldType)) {
                paramMap.put("otherType", 1);
            }else {
                
            }
        } catch(UnsupportedEncodingException e1) {
        	logger.error("getCmpDetailList failed",e1);
        }
        
        paramMap.put("complaintSolution", "1");
        List<ComplaintEntity> complaintDetailList = complaintService.getComplaintList(paramMap);
        request.setAttribute("complaintDetailList", complaintDetailList);
        return "complaint_detail_list";
    }
    
    public String getOrderList(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("finishTimeBgn", finishTimeBgn);
        paramMap.put("finishTimeEnd", finishTimeEnd);
        try {
            paramMap.put("dealName",java.net.URLDecoder.decode(dealName,"utf-8"));
            if("destCategoryName".equals(statisticsFieldType)) {
                paramMap.put("destCategoryName", java.net.URLDecoder.decode(statisticsField,"utf-8"));
            } else if( "routeType".equals(statisticsFieldType)) {
                paramMap.put("routeType", java.net.URLDecoder.decode(statisticsField,"utf-8"));
            }  else if("otherType".equals(statisticsFieldType)) {
                paramMap.put("otherType", java.net.URLDecoder.decode(statisticsField,"utf-8"));
            }
        } catch(UnsupportedEncodingException e1) {
            logger.error("getOrderList failed",e1);
        }
        
        List<Integer> orderList = complaintService.getCompleteCmpOrderList(paramMap);
        request.setAttribute("orderList", orderList);
        return "order_list";
    }
    
    public String getUnDoneCmpDetailList(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put("dealName",java.net.URLDecoder.decode(dealName,"utf-8"));
            
            String statisticsFieldParam = java.net.URLDecoder.decode(statisticsField,"utf-8");
            if("处理中".equals(statisticsFieldParam)) {
                paramMap.put("state", 2);
            } else if("处理中对客已达成".equals(statisticsFieldParam)) {
                paramMap.put("state", 2);
                paramMap.put("custAgreeFlag", 1);
            } else if("处理中首呼超时".equals(statisticsFieldParam)) {
                paramMap.put("state", 2);
                paramMap.put("firstCallOverTime", "firstCallOverTime");
            } else if("处理中超时出境".equals(statisticsFieldParam)) {
                paramMap.put("state", 2);
                paramMap.put("firstWorkdayOvertime", "firstWorkdayOvertime");
                String[] destCategoryNames = new String[]{"出境长线","出境短线","出境当地参团"};
                paramMap.put("destCategoryNames", destCategoryNames);
            } else if("处理中超时国内".equals(statisticsFieldParam)) {
                paramMap.put("state", 2);
                paramMap.put("firstWorkdayOvertime", "firstWorkdayOvertime");
                String[] destCategoryNames = new String[]{"国内长线","周边","国内当地参团"};
                paramMap.put("destCategoryNames", destCategoryNames);
            } else if("已待结".equals(statisticsFieldParam)) {
                paramMap.put("state", 3);
            } else if("已待结超时出境".equals(statisticsFieldParam)) {
                paramMap.put("state", 3);
                paramMap.put("firstWorkdayOvertime", "firstWorkdayOvertime");
                String[] destCategoryNames = new String[]{"出境长线","出境短线","出境当地参团"};
                paramMap.put("destCategoryNames", destCategoryNames);
            } else if("已待结超时国内".equals(statisticsFieldParam)) {
                paramMap.put("state", 3);
                paramMap.put("firstWorkdayOvertime", "firstWorkdayOvertime");
                String[] destCategoryNames = new String[]{"国内长线","周边","国内当地参团"};
                paramMap.put("destCategoryNames", destCategoryNames);
            } else {

            }
            
        } catch(UnsupportedEncodingException e1) {
        	logger.error("getUnDoneCmpDetailList failed", e1);
        }
        
        paramMap.put("complaintSolution", "1");
        List<ComplaintEntity> complaintDetailList = complaintService.getComplaintList(paramMap);
        request.setAttribute("complaintDetailList", complaintDetailList);
        return "complaint_detail_list";
    }

    public String getAssignTimeBgn() {
        return assignTimeBgn;
    }

    public void setAssignTimeBgn(String assignTimeBgn) {
        this.assignTimeBgn = assignTimeBgn;
    }

    public String getAssignTimeEnd() {
        return assignTimeEnd;
    }

    public void setAssignTimeEnd(String assignTimeEnd) {
        this.assignTimeEnd = assignTimeEnd;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getStatisticsField() {
        return statisticsField;
    }

    public void setStatisticsField(String statisticsField) {
        this.statisticsField = statisticsField;
    }

    public String getStatisticsFieldType() {
        return statisticsFieldType;
    }

    public void setStatisticsFieldType(String statisticsFieldType) {
        this.statisticsFieldType = statisticsFieldType;
    }

    public String getFinishTimeBgn() {
        return finishTimeBgn;
    }

    public void setFinishTimeBgn(String finishTimeBgn) {
        this.finishTimeBgn = finishTimeBgn;
    }

    public String getFinishTimeEnd() {
        return finishTimeEnd;
    }

    public void setFinishTimeEnd(String finishTimeEnd) {
        this.finishTimeEnd = finishTimeEnd;
    }

    public String getDealDepart() {
        return dealDepart;
    }

    public void setDealDepart(String dealDepart) {
        this.dealDepart = dealDepart;
    }
}
