package com.tuniu.gt.satisfaction.entity;

import java.io.Serializable;
import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class PreSaleSatisfactionSocreEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = -6355585211195185236L;
	
	private int preSaleSatisId;		//'关联满意度id',
	private int professional;		//'业务专业性评分',
	private String professionalComment;		//'专业性不满意问题记录',
	private int timeliness;		//'解决问题及时性评分',
	private String timelinessComment;		//'解决问题及时不满意问题记录',
	private int patience;		//'热情耐心评分',
	private String patienceComment;		//'热情耐心不满意问题记录',
	private int responsibility;		//'责任心评分',
	private String responsibilityComment;		//'责任心不满意问题记录',
	private String suggestion;		//'服务改进建议',
	private int total;		//'评分总计',
	private String preSaleSatisfaction;		//'售前客服服务满意度',
	private int status;		//'点评状态0:待点评,1:点评成功,2:待再次回访,3:点评失败',
	private Date createTime;		//'记录创建时间',
	private Date lastModifyTime;		//'最后修改时间',
	
	public int getPreSaleSatisId() {
		return preSaleSatisId;
	}
	public void setPreSaleSatisId(int preSaleSatisId) {
		this.preSaleSatisId = preSaleSatisId;
	}
	public int getProfessional() {
		return professional;
	}
	public void setProfessional(int professional) {
		this.professional = professional;
	}
	public String getProfessionalComment() {
		return professionalComment;
	}
	public void setProfessionalComment(String professionalComment) {
		this.professionalComment = professionalComment;
	}
	public int getTimeliness() {
		return timeliness;
	}
	public void setTimeliness(int timeliness) {
		this.timeliness = timeliness;
	}
	public String getTimelinessComment() {
		return timelinessComment;
	}
	public void setTimelinessComment(String timelinessComment) {
		this.timelinessComment = timelinessComment;
	}
	public int getPatience() {
		return patience;
	}
	public void setPatience(int patience) {
		this.patience = patience;
	}
	public String getPatienceComment() {
		return patienceComment;
	}
	public void setPatienceComment(String patienceComment) {
		this.patienceComment = patienceComment;
	}
	public int getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(int responsibility) {
		this.responsibility = responsibility;
	}
	public String getResponsibilityComment() {
		return responsibilityComment;
	}
	public void setResponsibilityComment(String responsibilityComment) {
		this.responsibilityComment = responsibilityComment;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getPreSaleSatisfaction() {
		return preSaleSatisfaction;
	}
	public void setPreSaleSatisfaction(String preSaleSatisfaction) {
		this.preSaleSatisfaction = preSaleSatisfaction;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
}