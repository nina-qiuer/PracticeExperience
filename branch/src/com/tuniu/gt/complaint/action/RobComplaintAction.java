/**
 * 
 */
package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.enums.AssignType;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.service.IRobComplaintService;
import com.tuniu.gt.toolkit.DateUtil;

/**
 * @author jiangye
 *
 */
@Service("complaint_action-robComplaint")
@Scope("prototype")
public class RobComplaintAction extends FrmBaseAction<IRobComplaintService,Object>{
    private Integer robAmount;
    private Map<String,Object> info;
    
    //抢单统计相关属性
    private java.sql.Date statDate;
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-auto_assign")
    private IAutoAssignService autoAssignService;
    
    private RobComplaintAction() {
        info = new HashMap<String, Object>();
    }
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-robComplaint")
    public void setService(IRobComplaintService service) {
        this.service = service;
    };
    
    public String index(){
        return "rob_complaint_index";
    }
    
    public String toRob(){
        return "rob_complaint_rob";
    }
    
    public String rob(){
        try {
            Map<String,Integer> resultMap =  service.robComplaint(user.getId(), robAmount);
            info.put("success", true);
            info.put("robedAmount", resultMap.get("robedAmount"));
            info.put("restAmount", resultMap.get("restAmount"));
        } catch(Exception e) {
            info.put("success", false);
            info.put("errorMsg", e.getMessage());
        }
        return "info";
    }
    
    public String toStatistics(){
        if (null == statDate) {
            statDate = DateUtil.getSqlToday();
        }
        List<AutoAssignEntity> complaintList = new ArrayList<AutoAssignEntity>(); // 投诉分单
        complaintList = autoAssignService.getAssignCompInfoList(statDate, 2, AssignType.ROB);
        request.setAttribute("complaintList", complaintList);
        return "rob_complaint_statistics";
    }
    
    public String execute(){
        System.out.println("RobComplaintAction invoked");
        return "info";
    }

    
    public Integer getRobAmount() {
        return robAmount;
    }

    public void setRobAmount(Integer robAmount) {
        this.robAmount = robAmount;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public java.sql.Date getStatDate() {
        return statDate;
    }

    public void setStatDate(java.sql.Date statDate) {
        this.statDate = statDate;
    }
    
}
