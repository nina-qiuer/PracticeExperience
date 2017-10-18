package com.tuniu.qms.qc.model;

import java.util.Date;

import com.tuniu.qms.common.model.BaseModel;

public class QcMonitor extends BaseModel {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

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

    public QcMonitor() {
        super();
    }

    public QcMonitor(Integer id, Integer circle, String qcType, String type, Integer orderId, Integer routeId,
            Integer isQualified, String questionType, Double poorMoney, Double receivableMoney, String qcConclusion,
            String qcPerson, Date qcDate) {
        super();
        this.id = id;
        this.circle = circle;
        this.qcType = qcType;
        this.type = type;
        this.orderId = orderId;
        this.routeId = routeId;
        this.isQualified = isQualified;
        this.questionType = questionType;
        this.poorMoney = poorMoney;
        this.receivableMoney = receivableMoney;
        this.qcConclusion = qcConclusion;
        this.qcPerson = qcPerson;
        this.qcDate = qcDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "QcMonitor [id=" + id + ", circle=" + circle + ", qcType=" + qcType + ", type=" + type + ", orderId="
                + orderId + ", routeId=" + routeId + ", isQualified=" + isQualified + ", questionType=" + questionType
                + ", poorMoney=" + poorMoney + ", receivableMoney=" + receivableMoney + ", qcConclusion="
                + qcConclusion + ", qcPerson=" + qcPerson + ", qcDate=" + qcDate + "]";
    }


}
