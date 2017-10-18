package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;

/**
 * 任务提醒
 * @author chenhaitao
 *
 */
public class TaskReminderEntity  implements Serializable {
	private static final long serialVersionUID = 7059273457258055322L;
	
	private Integer id;
	
	private Integer cmpId;
	
	private String guestName; //客人姓名
	
	private String guestLevel; //客人等级

	private Integer orderId; //关联订单id

	private Date startTime; //出发时间
	
	private String orderState; //订单状态

	private Date buildDate; //投诉时间

	private String customerLeader; //客服经理
	
	private Integer customerLeaderId; //客服经理Id
	
	private String dealDepart ; // 处理岗位
	
	private String dealName; //处理人姓名

	private Integer deal; //处理人
	
	private Integer level; //投诉等级
	
	private Date repeateTime; //多次投诉世间
	
	private Integer cmpState; //投诉单状态 

	private String cmpPerson;//投诉人姓名
	
	private String cmpPhone;//投诉人电话
	
	private String content;//回呼备注
	
	private Date addTime;//添加时间
	
	private String addPerson;
	
	private Integer cbState;//回呼状态
	
	private Integer fcState;//首呼状态
	
	private Integer flag;
	
	private String call_back_time;//回呼时间
	
	private Integer update_id;//更新人编号
	
	private String update_name;//更新人姓名
	
	private Date update_time;//更新时间
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFcState() {
		return fcState;
	}

	public void setFcState(Integer fcState) {
		this.fcState = fcState;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getCmpState() {
		return cmpState;
	}

	public void setCmpState(Integer cmpState) {
		this.cmpState = cmpState;
	}

	public Integer getCbState() {
		return cbState;
	}

	public void setCbState(Integer cbState) {
		this.cbState = cbState;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	
	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public String getCmpPerson() {
		return cmpPerson;
	}

	public void setCmpPerson(String cmpPerson) {
		this.cmpPerson = cmpPerson;
	}

	public String getCmpPhone() {
		return cmpPhone;
	}

	public void setCmpPhone(String cmpPhone) {
		this.cmpPhone = cmpPhone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestLevel() {
		return guestLevel;
	}

	public void setGuestLevel(String guestLevel) {
		this.guestLevel = guestLevel;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public String getCustomerLeader() {
		return customerLeader;
	}

	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
	}

	public Integer getCustomerLeaderId() {
		return customerLeaderId;
	}

	public void setCustomerLeaderId(Integer customerLeaderId) {
		this.customerLeaderId = customerLeaderId;
	}

	public String getDealDepart() {
		return dealDepart;
	}

	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public Integer getDeal() {
		return deal;
	}

	public void setDeal(Integer deal) {
		this.deal = deal;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getRepeateTime() {
		return repeateTime;
	}

	public void setRepeateTime(Date repeateTime) {
		this.repeateTime = repeateTime;
	}

	public String getCallBackTime() {
		return call_back_time;
	}

	public void setCallBackTime(String call_back_time) {
		this.call_back_time = call_back_time;
	}

	public Integer getUpdateId() {
		return update_id;
	}

	public void setUpdateId(Integer update_id) {
		this.update_id = update_id;
	}

	public String getUpdateName() {
		return update_name;
	}

	public void setUpdateName(String update_name) {
		this.update_name = update_name;
	}

	public Date getUpdateTime() {
		return update_time;
	}

	public void setUpdateTime(Date update_time) {
		this.update_time = update_time;
	}
}
