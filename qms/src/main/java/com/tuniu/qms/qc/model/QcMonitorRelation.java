package com.tuniu.qms.qc.model;

import com.tuniu.qms.common.model.BaseModel;

public class QcMonitorRelation extends BaseModel {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 相关人员 */
    private String relevantPerson;

    /** 关联岗位 */
    private String relevantPost;

    /** 关联部门 */
    private String relevantDepartment;
    
    /** 监测外键*/
    private Integer monitorId;
    
    private Integer relatedFlag;

    public Integer getRelatedFlag() {
        return relatedFlag;
    }

    public void setRelatedFlag(Integer relatedFlag) {
        this.relatedFlag = relatedFlag;
    }

    public QcMonitorRelation() {
        super();
    }

    public QcMonitorRelation(Integer id, String relevantPerson, String relevantPost, String relevantDepartment,
            Integer monitorId) {
        super();
        this.id = id;
        this.relevantPerson = relevantPerson;
        this.relevantPost = relevantPost;
        this.relevantDepartment = relevantDepartment;
        this.monitorId = monitorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "QcMonitorRelation [id=" + id + ", relevantPerson=" + relevantPerson + ", relevantPost=" + relevantPost
                + ", relevantDepartment=" + relevantDepartment + ", monitorId=" + monitorId + "]";
    }   
}
