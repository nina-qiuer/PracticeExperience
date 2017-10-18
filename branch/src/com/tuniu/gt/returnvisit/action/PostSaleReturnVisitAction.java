/**
 * 
 */
package com.tuniu.gt.returnvisit.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.returnvisit.entity.PostSaleReturnVisitEntity;
import com.tuniu.gt.returnvisit.service.IPostSaleReturnVisitService;
import com.tuniu.gt.returnvisit.vo.PostSaleReturnVisitVo;
import com.tuniu.gt.returnvisit.vo.ReturnVisitReportVo;

/**
 * @author jiangye
 *
 */
@Service("rv_action-postSaleReturnVisit")
@Scope("prototype")
public class PostSaleReturnVisitAction extends FrmBaseAction<IPostSaleReturnVisitService, PostSaleReturnVisitEntity>{

    private PostSaleReturnVisitVo vo;
    
    private String businessUnitName;
    private String departmentName;
    private String groupName;
    private String dealName;
    private Integer score;
    private Integer unsatisfyReason;
    private String returnVisitDateBgn;
    private String returnVisitDateEnd;
    
    private List<String> dealDepartments;
    
    public PostSaleReturnVisitAction() {
    }
    
    @Autowired
    @Qualifier("rv_service_impl-postSaleReturnVisit")
    public void setService(IPostSaleReturnVisitService service) {
        this.service = service;
    };

    public String list(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (null == vo) {
            vo = new PostSaleReturnVisitVo();
        }
        paramMap.putAll(vo.toMap());
        super.execute(paramMap);
        
        request.setAttribute("vo", vo);
        
        return "post_sale_return_visit_list";
    }
    
    public String getReport(){
        
        if(vo==null){
            vo = new PostSaleReturnVisitVo();
        }
        
        List<ReturnVisitReportVo> dataList = service.buildReport(vo.toMap());
        request.setAttribute("dataList", dataList);
        
        return "post_sale_return_visit_report";
    }
    
    public String getDetail(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put("businessUnitName", java.net.URLDecoder.decode(businessUnitName,"utf-8"));
            paramMap.put("departmentName", java.net.URLDecoder.decode(departmentName,"utf-8"));
            paramMap.put("groupName", java.net.URLDecoder.decode(groupName,"utf-8"));
            paramMap.put("dealName", java.net.URLDecoder.decode(dealName,"utf-8"));
            paramMap.put("score", score);
            paramMap.put("unsatisfyReason", unsatisfyReason);
            paramMap.put("returnVisitDateBgn", returnVisitDateBgn);
            paramMap.put("returnVisitDateEnd", returnVisitDateEnd);
            List<ComplaintEntity> cmpList = service.getComplaintList(paramMap);
            
            request.setAttribute("compDetailList", cmpList);
        } catch(Exception e) {
            
        }
        
        return "post_sale_return_visit_report_detail";
    }

    public PostSaleReturnVisitVo getVo() {
        return vo;
    }

    public void setVo(PostSaleReturnVisitVo vo) {
        this.vo = vo;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getUnsatisfyReason() {
        return unsatisfyReason;
    }

    public void setUnsatisfyReason(Integer unsatisfyReason) {
        this.unsatisfyReason = unsatisfyReason;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getReturnVisitDateBgn() {
        return returnVisitDateBgn;
    }

    public void setReturnVisitDateBgn(String returnVisitDateBgn) {
        this.returnVisitDateBgn = returnVisitDateBgn;
    }

    public String getReturnVisitDateEnd() {
        return returnVisitDateEnd;
    }

    public void setReturnVisitDateEnd(String returnVisitDateEnd) {
        this.returnVisitDateEnd = returnVisitDateEnd;
    }

    public List<String> getDealDepartments() {
        return DBConfigManager.getConfigAsList("sys.dealDepart");
    }

    public void setDealDepartments(List<String> dealDepartments) {
        this.dealDepartments = dealDepartments;
    }

}
