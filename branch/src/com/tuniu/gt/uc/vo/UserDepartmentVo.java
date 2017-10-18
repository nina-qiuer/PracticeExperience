/**
 * 
 */
package com.tuniu.gt.uc.vo;

/**
 * 人员部门VO
 * @author jiangye
 */
public class UserDepartmentVo {
    private Integer userId;
    private String realName; //中文姓名
    private String userName; // 拼音姓名
    private String workNum; // 工号
    private Integer depId; // 主部门id
    
    private Integer businessUnitId; // 一级部门ID
    private String businessUnitName; // 一级部门名称
    
    private Integer departmentId; // 二级部门ID
    private String departmentName; // 二级部门名称
    
    private Integer groupId; // 三级部门ID
    private String groupName; // 三级部门名称
    
    
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getBusinessUnitId() {
        return businessUnitId;
    }
    public void setBusinessUnitId(Integer businessUnitId) {
        this.businessUnitId = businessUnitId;
    }
    public String getBusinessUnitName() {
        return businessUnitName;
    }
    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }
    public Integer getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public Integer getGroupId() {
        return groupId;
    }
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Integer getDepId() {
        return depId;
    }
    public void setDepId(Integer depId) {
        this.depId = depId;
    }
    public String getWorkNum() {
        return workNum;
    }
    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }
    
}
