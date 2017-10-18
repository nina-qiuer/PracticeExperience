/**
 * 
 */
package com.tuniu.gt.returnvisit.vo;

/**
 * @author jiangye
 *
 */
public class ReportResultSetVo {
    private String businessUnitName;
    private String departmentName;
    private String groupName;
    private String dealName;
    private ReturnVisitReportData data;
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
    public String getDealName() {
        return dealName;
    }
    public void setDealName(String dealName) {
        this.dealName = dealName;
    }
    public ReturnVisitReportData getData() {
        return data;
    }
    public void setData(ReturnVisitReportData data) {
        this.data = data;
    }
    
}
