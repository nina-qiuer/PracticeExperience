package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;


public class GiftEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer giftNoteId;
	
	private Integer giftId; //礼品id
	
	private String type; //礼品分类
	
	private String name; //礼品名称
	
	private double price; //礼品单价
	
	private Integer number; //礼品数量
	
	private String remark; //礼品备注
	
	private Integer delFlag; //删除标示, 1:正常，0：删除
	
	/* 以下字段废弃 */

	private Integer complaintId; //关联投诉id

	private Integer solutionId; //关联解决方案id

	private GiftTypeEntity giftType; // 1:1

	public Integer getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId; 
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name; 
	}

	public Integer getGiftId() {
		return giftId;
	}
	public void setGiftId(Integer giftId) {
		this.giftId = giftId; 
	}

	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number; 
	}

	public Integer getSolutionId() {
		return solutionId;
	}
	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}
	public GiftTypeEntity getGiftType() {
		return giftType;
	}
	public void setGiftType(GiftTypeEntity giftType) {
		this.giftType = giftType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getGiftNoteId() {
		return giftNoteId;
	}
	public void setGiftNoteId(Integer giftNoteId) {
		this.giftNoteId = giftNoteId;
	}
	
}
