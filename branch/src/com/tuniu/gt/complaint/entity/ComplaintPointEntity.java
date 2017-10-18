/**
 * 
 */
package com.tuniu.gt.complaint.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
 *
 *
 */
public class ComplaintPointEntity extends EntityBase {
    private Integer complaintId;
    private String mainType; // 投诉一级分类
    private String secondType; // 投诉二级分类
    private String typeDescript; // 投诉点详情
    private String payoutBase; // 投诉点赔付理据
    private String verify; // 核实情况
    private Double theoryPayout; // 理论赔付金额
    private Double theoryPayoutQuality;//质量工具理论赔付金额
    private Double theoryPayoutLaw;//法律法规理论赔付金额
    private Date addTime; // 添加时间
    private Date updateTime; // 更新时间
    private Integer del_flag; // 0为有效，1为无效
    private Integer detailFirst;//投诉点一级明细
    private Integer detailSecond;//投诉点二级明细
    private Integer detailThird;//投诉点三级明细
    
    public Integer getComplaintId() {
        return complaintId;
    }
    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }
    public String getMainType() {
        return mainType;
    }
    public void setMainType(String mainType) {
        this.mainType = mainType;
    }
    public String getSecondType() {
        return secondType;
    }
    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }
    public String getTypeDescript() {
        return typeDescript;
    }
    public void setTypeDescript(String typeDescript) {
        this.typeDescript = typeDescript;
    }
    public String getPayoutBase() {
        return payoutBase;
    }
    public void setPayoutBase(String payoutBase) {
        this.payoutBase = payoutBase;
    }
    public String getVerify() {
        return verify;
    }
    public void setVerify(String verify) {
        this.verify = verify;
    }
    public Double getTheoryPayout() {
        return theoryPayout;
    }
    public void setTheoryPayout(Double theoryPayout) {
        this.theoryPayout = theoryPayout;
    }
    public Double getTheoryPayoutQuality() {
		return theoryPayoutQuality;
	}
	public void setTheoryPayoutQuality(Double theoryPayoutQuality) {
		this.theoryPayoutQuality = theoryPayoutQuality;
	}
	public Double getTheoryPayoutLaw() {
		return theoryPayoutLaw;
	}
	public void setTheoryPayoutLaw(Double theoryPayoutLaw) {
		this.theoryPayoutLaw = theoryPayoutLaw;
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
    public Integer getDel_flag() {
        return del_flag;
    }
    public void setDel_flag(Integer del_flag) {
        this.del_flag = del_flag;
    }
    
    public Integer getDetailFirst() {
		return detailFirst;
	}
	public void setDetailFirst(Integer detailFirst) {
		this.detailFirst = detailFirst;
	}
	public Integer getDetailSecond() {
		return detailSecond;
	}
	public void setDetailSecond(Integer detailSecond) {
		this.detailSecond = detailSecond;
	}
	public Integer getDetailThird() {
		return detailThird;
	}
	public void setDetailThird(Integer detailThird) {
		this.detailThird = detailThird;
	}
	
	public static ComplaintPointEntity fromComplaintReason(ComplaintReasonEntity complaintReason){
        ComplaintPointEntity complaintPoint = new ComplaintPointEntity();
        complaintPoint.setComplaintId(complaintReason.getComplaintId());
        complaintPoint.setMainType(complaintReason.getType());
        complaintPoint.setSecondType(complaintReason.getSecondType());
        complaintPoint.setTypeDescript(complaintReason.getTypeDescript());
        complaintPoint.setAddTime(complaintReason.getAddTime());
        return complaintPoint;
    }
    
    public static List<ComplaintPointEntity> fromComplaintReasonList(List<ComplaintReasonEntity> complaintReasonList){
        List<ComplaintPointEntity> complaintPointList = new ArrayList<ComplaintPointEntity>();
        for(ComplaintReasonEntity complaintReasonEntity : complaintReasonList) {
            complaintPointList.add(fromComplaintReason(complaintReasonEntity));
        }
        return complaintPointList;
    }
    
}
