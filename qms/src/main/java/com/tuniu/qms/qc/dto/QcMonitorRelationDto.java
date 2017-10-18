package com.tuniu.qms.qc.dto;


public class QcMonitorRelationDto {

    /** 相关人员 */
    private String relevantPerson;

    /** 关联岗位 */
    private String relevantPost;

    /** 关联部门 */
    private String relevantDepartment;
    
    /** 监测外键*/
    private Integer monitorId;

    public QcMonitorRelationDto() {
        super();
    }

    public QcMonitorRelationDto(Integer id, String relevantPerson, String relevantPost, String relevantDepartment,
            Integer monitorId) {
        super();
        this.relevantPerson = relevantPerson;
        this.relevantPost = relevantPost;
        this.relevantDepartment = relevantDepartment;
        this.monitorId = monitorId;
    }

    public String getRelevantPerson() {
        return relevantPerson;
    }

    public void setRelevantPerson(String relevantPerson) {
        this.relevantPerson = relevantPerson;
    }

    public String getRelevantPost() {
        return relevantPost;
    }

    public void setRelevantPost(String relevantPost) {
        this.relevantPost = relevantPost;
    }

    public String getRelevantDepartment() {
        return relevantDepartment;
    }

    public void setRelevantDepartment(String relevantDepartment) {
        this.relevantDepartment = relevantDepartment;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    @Override
    public String toString() {
        return "QcMonitorRelation " + "[relevantPerson=" + relevantPerson + ", relevantPost=" + relevantPost
                + ", relevantDepartment=" + relevantDepartment + ", monitorId=" + monitorId + "]";
    }   
}
