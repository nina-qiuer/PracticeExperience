/**
 * 
 */
package com.tuniu.gt.complaint.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
 *
 */
public class ClaimAuditHistory extends EntityBase {
	
	private Integer claimType; // 赔款审核类型   1：对客   2：分担
	public static final Integer COMPLAINT_TYPE = 1;
	public static final Integer SHARE_TYPE = 2;
	
	private Integer foreignId; // 赔款审核对象Id，赔款审核类型为对客时表示对客id，类型为分担时表示分担id
	
	private Integer phrase; // 赔款审核阶段   1：初审  2：复审一  3：复审二   4：复审三  5：终审
	public static final Integer FIRST_TRIAL = 1;
	public static final Integer RETRIAL_ONE = 2;
	public static final Integer RETRIAL_TWO = 3;
	public static final Integer RETRIAL_THREE = 4;
	public static final Integer LAST_TRIAL = 5;
	
	private Date claimTime; // 赔款审核时间
	private String assessor; // 审核人员
	public Integer getClaimType() {
		return claimType;
	}
	public void setClaimType(Integer claimType) {
		this.claimType = claimType;
	}
	public Integer getForeignId() {
		return foreignId;
	}
	public void setForeignId(Integer foreignId) {
		this.foreignId = foreignId;
	}
	public Integer getPhrase() {
		return phrase;
	}
	public void setPhrase(Integer phrase) {
		this.phrase = phrase;
	}
	public Date getClaimTime() {
		return claimTime;
	}
	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}
	public String getAssessor() {
		return assessor;
	}
	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}
}
