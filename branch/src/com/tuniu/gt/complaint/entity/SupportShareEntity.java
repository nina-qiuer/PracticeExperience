package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;


public class SupportShareEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer complaintId; //关联投诉id

	private Integer shareId; //关联分担id
	
	private Integer code; //供应商编号

	private String name; //供应商名字
	
	private double number; //承担费用
	
	private int  foreignCurrencyType; //外币类型
	
	private double foreignCurrencyNumber; //外币数额
	
	private String foreignCurrencyName; // 外币名称
	
	private String remark; // 备注

	private Date addTime; //添加时间

	private Date updateTime; //更新时间

	private Integer delFlag=1; //删除标示位，1-正常,0-删除
	
	private int confirmState; // 供应商确认状态，0：未确认，1：已确认，2：到期默认，3：已申诉，-1：历史数据（或非线上确认）
	
	private String confirmer; // 确认人
	
	private Date confirmTime; // 确认时间
	
	private Date expireTime; // 系统默认到期时间
	
	private List<AgencyPayoutEntity> agencyPayoutList = new ArrayList<AgencyPayoutEntity>(); //对应的赔款详情
	
	private int nbFlag; // 是否走NB确认流程
	
	private String oa_id;//申诉oaid
	
	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public Integer getShareId() {
		return shareId;
	}

	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public int getForeignCurrencyType() {
		return foreignCurrencyType;
	}

	public void setForeignCurrencyType(int foreignCurrencyType) {
		this.foreignCurrencyType = foreignCurrencyType;
	}

	public double getForeignCurrencyNumber() {
		return foreignCurrencyNumber;
	}

	public void setForeignCurrencyNumber(double foreignCurrencyNumber) {
		this.foreignCurrencyNumber = foreignCurrencyNumber;
	}

	public String getForeignCurrencyName() {
		return foreignCurrencyName;
	}

	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public int getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(int confirmState) {
		this.confirmState = confirmState;
	}

	public String getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public List<AgencyPayoutEntity> getAgencyPayoutList() {
		return agencyPayoutList;
	}

	public void setAgencyPayoutList(List<AgencyPayoutEntity> agencyPayoutList) {
		this.agencyPayoutList = agencyPayoutList;
	}

	public int getNbFlag() {
		return nbFlag;
	}

	public void setNbFlag(int nbFlag) {
		this.nbFlag = nbFlag;
	}

	public String getOa_id() {
		return oa_id;
	}

	public void setOa_id(String oa_id) {
		this.oa_id = oa_id;
	}
}
