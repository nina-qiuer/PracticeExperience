package com.tuniu.qms.qs.model;
import java.util.Date;
import com.tuniu.qms.common.model.BaseModel;

/**
 * 售前回访
 * 对应数据表：qs_pre_sale_rv
 * 
 * @author 陈海涛
 */
public class PreSaleReturnVisit extends BaseModel {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 订单号 */
    private Integer ordId;
    
    /**产品名称*/
    private String prdName;
    
    /**售前客服**/
    private String customer;
    
    /**坐席号**/
    private String extension;
    
    /**客服经理**/
    private String customerLeader ;
    
    /**回访电话*/
    private String tel;

    /** 客服所在事业部名 */
    private String businessUnitName;

    /** 客服所在二级部门名 */
    private String departmentName;

    /** 客服所在组名 */
    private String groupName;

    /** 售前综合服务评分 满意3分，一般2分，不满意0分 */
    private Integer score;

    /** 不满意原因1.业务熟练度2. 解决问题及时性3.服务态度4. 工作责任心 */
    private Integer unsatisfyReason;

    /** 回访日期 */
    private Date returnVisitDate;
    
    /** 发送日期 */
    private Date sendTime;
   
    /**是否取消 1:未取消,2:已取消**/
    private Integer isCancel;
    
    /**出游日期**/
    private  Date beginDate ;
    
    /**签约日期**/
    private Date signDate;
    
    /**下单日期**/
    private Date orderTime;
    
    /**回访标识 0未回访 1 已回访**/
    private Integer  isSign;
    
    /** 所有联系人手机号*/
    private String allPersonPhone;
    
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getUnsatisfyReason() {
		return unsatisfyReason;
	}

	public void setUnsatisfyReason(Integer unsatisfyReason) {
		this.unsatisfyReason = unsatisfyReason;
	}

	public Date getReturnVisitDate() {
		return returnVisitDate;
	}

	public void setReturnVisitDate(Date returnVisitDate) {
		this.returnVisitDate = returnVisitDate;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerLeader() {
		return customerLeader;
	}

	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
	}

	public String getAllPersonPhone() {
		return allPersonPhone;
	}

	public void setAllPersonPhone(String allPersonPhone) {
		this.allPersonPhone = allPersonPhone;
	}
    
}
