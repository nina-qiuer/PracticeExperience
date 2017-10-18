package com.tuniu.gt.satisfaction.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class PreSaleSatisfactionEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = -2172194652448081793L;
	
	
	private Integer orderId;
	private Integer telCnt;
	private String routeName;
	private String salerName;
	private String salermanagerName;
	private String orderType;
	private Date startTime;
	private Date backTime;
	private Integer contactId;
	private String contactName;
	private String contactTel;
	private Date addTime = new Date();
	private Date updateTime = new Date();
	private Integer delFlag;
	private Integer commentStatus;
	private String preSaleSatisfaction;
	private Integer star_level;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getTelCnt() {
		return telCnt;
	}
	public void setTelCnt(Integer telCnt) {
		this.telCnt = telCnt;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	/**
	 * @return the salerName
	 */
	public String getSalerName() {
		return salerName;
	}
	/**
	 * @param salerName the salerName to set
	 */
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}
	/**
	 * @return the salermanagerName
	 */
	public String getSalermanagerName() {
		return salermanagerName;
	}
	/**
	 * @param salermanagerName the salermanagerName to set
	 */
	public void setSalermanagerName(String salermanagerName) {
		this.salermanagerName = salermanagerName;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return the backTime
	 */
	public Date getBackTime() {
		return backTime;
	}
	/**
	 * @param backTime the backTime to set
	 */
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	/**
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the contactTel
	 */
	public String getContactTel() {
		return contactTel;
	}
	/**
	 * @param contactTel the contactTel to set
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	/**
	 * @return the addTime
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the delFlag
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * @return the commentStatus
	 */
	public Integer getCommentStatus() {
		return commentStatus;
	}
	/**
	 * @param commentStatus the commentStatus to set
	 */
	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}
	/**
	 * @return the preSaleSatisfaction
	 */
	public String getPreSaleSatisfaction() {
		return preSaleSatisfaction;
	}
	/**
	 * @param preSaleSatisfaction the preSaleSatisfaction to set
	 */
	public void setPreSaleSatisfaction(String preSaleSatisfaction) {
		this.preSaleSatisfaction = preSaleSatisfaction;
	}
	public Integer getStar_level() {
		return star_level;
	}
	public void setStar_level(Integer star_level) {
		this.star_level = star_level;
	}
	
}
