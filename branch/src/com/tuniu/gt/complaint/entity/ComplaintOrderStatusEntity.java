package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class ComplaintOrderStatusEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4281951748764001348L;


	private Date opTime=new Date(); //添加时间

	private Integer flag=0; //调接口成败标记 0：成功 1：失败

	private Integer state=1; //状态 1：提交投诉 2：完成投诉 3：撤销投诉
	
	private String status=""; //传给网站状态

	private Integer orderId=0; //订单编号



	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime; 
	}

	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag; 
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId; 
	}
	
}
