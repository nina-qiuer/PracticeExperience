/**
 * 
 */
package com.tuniu.gt.returnvisit.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangye
 *
 */
public class PostSaleReturnVisitVo {
    private Integer complaintId;
    private String dealDepart;
    private String dealName;
    private String dealManagerName;
    private Integer score;
    private Integer unsatisfyReason;
    private String returnVisitDateBgn;
    private String returnVisitDateEnd;
    
    public Map<String,Object> toMap(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("complaintId", this.complaintId);
        paramMap.put("dealDepart", this.dealDepart);
        paramMap.put("dealName", this.dealName);
        paramMap.put("dealManagerName", this.dealManagerName);
        paramMap.put("score", this.score);
        paramMap.put("unsatisfyReason", this.unsatisfyReason);
        paramMap.put("returnVisitDateBgn", this.returnVisitDateBgn);
        paramMap.put("returnVisitDateEnd", this.returnVisitDateEnd);
        return paramMap;
    }
    
    public Integer getComplaintId() {
        return complaintId;
    }
    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
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

    public String getDealDepart() {
        return dealDepart;
    }

    public void setDealDepart(String dealDepart) {
        this.dealDepart = dealDepart;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealManagerName() {
        return dealManagerName;
    }

    public void setDealManagerName(String dealManagerName) {
        this.dealManagerName = dealManagerName;
    }
    
}
