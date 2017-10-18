/**
 * 
 */
package com.tuniu.gt.returnvisit.vo;

/**
 * @author jiangye
 *
 */
public class ReturnVisitReportData {
    private String comprehensiveSatisfaction; // 综合满意率
    private String serviceSatisfaction; // 服务满意率
    private String planSatisfaction; // 服务满意率
    
    private String satisfactionCount; // 满意数
    private String normalCount; // 一般数
    private String unsatisfactionCount; // 不满意数
    
    //不满意原因
    private String attitudeReason; // 态度数
    private String planReason; // 方案数
    private String followNotInTimeReason; // 跟进不及时数
    private String notProfessionalReason; // 专业程度数
    private String noReason; // 无理由数
    public String getComprehensiveSatisfaction() {
        return comprehensiveSatisfaction;
    }
    public void setComprehensiveSatisfaction(String comprehensiveSatisfaction) {
        this.comprehensiveSatisfaction = comprehensiveSatisfaction;
    }
    public String getServiceSatisfaction() {
        return serviceSatisfaction;
    }
    public void setServiceSatisfaction(String serviceSatisfaction) {
        this.serviceSatisfaction = serviceSatisfaction;
    }
    public String getPlanSatisfaction() {
        return planSatisfaction;
    }
    public void setPlanSatisfaction(String planSatisfaction) {
        this.planSatisfaction = planSatisfaction;
    }
    public String getSatisfactionCount() {
        return satisfactionCount;
    }
    public void setSatisfactionCount(String satisfactionCount) {
        this.satisfactionCount = satisfactionCount;
    }
    public String getNormalCount() {
        return normalCount;
    }
    public void setNormalCount(String normalCount) {
        this.normalCount = normalCount;
    }
    public String getUnsatisfactionCount() {
        return unsatisfactionCount;
    }
    public void setUnsatisfactionCount(String unsatisfactionCount) {
        this.unsatisfactionCount = unsatisfactionCount;
    }
    public String getAttitudeReason() {
        return attitudeReason;
    }
    public void setAttitudeReason(String attitudeReason) {
        this.attitudeReason = attitudeReason;
    }
    public String getPlanReason() {
        return planReason;
    }
    public void setPlanReason(String planReason) {
        this.planReason = planReason;
    }
    public String getFollowNotInTimeReason() {
        return followNotInTimeReason;
    }
    public void setFollowNotInTimeReason(String followNotInTimeReason) {
        this.followNotInTimeReason = followNotInTimeReason;
    }
    public String getNotProfessionalReason() {
        return notProfessionalReason;
    }
    public void setNotProfessionalReason(String notProfessionalReason) {
        this.notProfessionalReason = notProfessionalReason;
    }
    public String getNoReason() {
        return noReason;
    }
    public void setNoReason(String noReason) {
        this.noReason = noReason;
    }
    
}
