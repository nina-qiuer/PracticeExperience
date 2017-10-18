/**
 * 
 */
package com.tuniu.gt.workorder.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * @author jiangye
 *
 */
public class WorkOrder extends EntityBase{
	
    private Integer configId; // 关联配置id
	private String department; // 工单项目组
    private String businessClass; // 业务分类
	private String orderId; // 订单id
	private String addPerson; // 发起人
	private Date addTime; // 发起时间
	private Date updateTime; // 更新时间
	private String dealPerson; // 处理人
	private Integer state; // 工单状态 1-待分配 2-处理中 3-已处理
	private String customerName; // 客户姓名
	private String phone; // 联系电话
	private String phoneMatter; // 来电事由
	private String answerTime; // 回复、解决时间
	private String solveResult; // 处理结果
	private Integer delFlag; // 删除标志位，0：正常，1：删除   
	private String remark; // 备注
	private String assigners; // 项目组分配人
	private Integer parentConfigId;
	private Integer sonConfigId;
	
	public String getAddPerson() {
		return addPerson;
	}
    public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDealPerson() {
		return dealPerson;
	}
	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoneMatter() {
		return phoneMatter;
	}
	public void setPhoneMatter(String phoneMatter) {
		this.phoneMatter = phoneMatter;
	}
	public String getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}
	public String getSolveResult() {
		return solveResult;
	}
	public void setSolveResult(String solveResult) {
		this.solveResult = solveResult;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    public String getAssigners() {
        return assigners;
    }
    public void setAssigners(String assigners) {
        this.assigners = assigners;
    }
    public Integer getConfigId() {
        return configId;
    }
    public void setConfigId(Integer configId) {
        this.configId = configId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getBusinessClass() {
        return businessClass;
    }
    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }
	public Integer getSonConfigId() {
		return sonConfigId;
	}
	public void setSonConfigId(Integer sonConfigId) {
		this.sonConfigId = sonConfigId;
	}
	public Integer getParentConfigId() {
		return parentConfigId;
	}
	public void setParentConfigId(Integer parentConfigId) {
		this.parentConfigId = parentConfigId;
	}
    
}
