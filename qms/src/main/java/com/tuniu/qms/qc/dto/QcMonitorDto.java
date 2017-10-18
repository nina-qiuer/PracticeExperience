package com.tuniu.qms.qc.dto;

import java.util.Date;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcMonitor;

public class QcMonitorDto extends BaseDto<QcMonitorDto>{
    
    private static final long serialVersionUID = 1L;

    /** 周期 */
    private Integer circle;

    /** 质检类型 */
    private String qcType;

    /** 分类 */
    private String type;

    /** 订单号 */
    private Integer orderId;

    /** 线路编号 */
    private Integer routeId;

    /** 相关人员 */
    private String relevantPerson;

    /** 关联岗位 */
    private String relevantPost;

    /** 关联部门 */
    private String relevantDepartment;
    
    /** 是否连带责任 */
    private Integer relatedFlag;
    
    /** 是否合格 */
    private Integer isQualified;

    /** 问题分类 */
    private String questionType;

    /** 欠收金额 */
    private Double poorMoney;

    /** 应收金额 */
    private Double receivableMoney;

    /** 质检结论 */
    private String qcConclusion;

    /** 质检员 */
    private String qcPerson;

    /** 质检日期 */
    private Date qcDate;
    
    private String addTimeFrom;// 添加时间上边界
    
    private String addTimeTo;// 添加时间下边界
    
    /** 相关人员1 */
    private String relevantPerson1;

    /** 关联岗位1 */
    private String relevantPost1;

    /** 关联部门1 */
    private String relevantDepartment1;
    
    /** 相关人员2 */
    private String relevantPerson2;

    /** 关联岗位2 */
    private String relevantPost2;

    /** 关联部门2 */
    private String relevantDepartment2;

    public String getRelevantPerson1() {
        return relevantPerson1;
    }

    public void setRelevantPerson1(String relevantPerson1) {
        this.relevantPerson1 = relevantPerson1;
    }

    public String getRelevantPost1() {
        return relevantPost1;
    }

    public void setRelevantPost1(String relevantPost1) {
        this.relevantPost1 = relevantPost1;
    }

    public String getRelevantDepartment1() {
        return relevantDepartment1;
    }

    public void setRelevantDepartment1(String relevantDepartment1) {
        this.relevantDepartment1 = relevantDepartment1;
    }

    public String getRelevantPerson2() {
        return relevantPerson2;
    }

    public void setRelevantPerson2(String relevantPerson2) {
        this.relevantPerson2 = relevantPerson2;
    }

    public String getRelevantPost2() {
        return relevantPost2;
    }

    public void setRelevantPost2(String relevantPost2) {
        this.relevantPost2 = relevantPost2;
    }

    public String getRelevantDepartment2() {
        return relevantDepartment2;
    }

    public void setRelevantDepartment2(String relevantDepartment2) {
        this.relevantDepartment2 = relevantDepartment2;
    }

    public String getAddTimeFrom() {
        return addTimeFrom;
    }

    public void setAddTimeFrom(String addTimeFrom) {
        this.addTimeFrom = addTimeFrom;
    }

    public String getAddTimeTo() {
        return addTimeTo;
    }

    public void setAddTimeTo(String addTimeTo) {
        this.addTimeTo = addTimeTo;
    }

    public QcMonitorDto() {
        super();
    }

    public Integer getCircle() {
        return circle;
    }

    public void setCircle(Integer circle) {
        this.circle = circle;
    }

    public String getQcType() {
        return qcType;
    }

    public void setQcType(String qcType) {
        this.qcType = qcType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
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

    public Integer getIsQualified() {
        return isQualified;
    }

    public void setIsQualified(Integer isQualified) {
        this.isQualified = isQualified;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Double getPoorMoney() {
        return poorMoney;
    }

    public void setPoorMoney(Double poorMoney) {
        this.poorMoney = poorMoney;
    }

    public Double getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(Double receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public String getQcConclusion() {
        return qcConclusion;
    }

    public void setQcConclusion(String qcConclusion) {
        this.qcConclusion = qcConclusion;
    }

    public String getQcPerson() {
        return qcPerson;
    }

    public void setQcPerson(String qcPerson) {
        this.qcPerson = qcPerson;
    }

    public Date getQcDate() {
        return qcDate;
    }

    public void setQcDate(Date qcDate) {
        this.qcDate = qcDate;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getRelatedFlag() {
        return relatedFlag;
    }

    public void setRelatedFlag(Integer relatedFlag) {
        this.relatedFlag = relatedFlag;
    }
}
