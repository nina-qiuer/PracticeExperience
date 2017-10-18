package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;


public class ComplaintSolutionEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = -8753408614685071482L;

	private Integer complaintId; //关联投诉id
	
	private Integer orderId; //关联订单id
	
	private String userName; // 联系人
	
	private String phone; // 联系电话
	
	private Integer compPersonId; // 投诉联系人ID，FAB生成
	
	private double cash; // 赔偿现金
	
	private int payment; // 退款方式，1：现金，2：对私汇款，3：对公汇款，25：转单
	
	private String receiver; // 收款人
	
	private String idCardNo; // 身份证号码
	
	private String collectionUnit; // 收款单位
	
	private String bankProvince; // 收款方开户行省
	
	private String bankCity; // 收款方开户行市
	
	private String bank; // 分行名称
	
	private String bigBank; // 银行名称
	
	private String account; // 账号
	
	private String toOrderId; // 转单订单号
	
	private Integer refundId; // 退款单ID，财务生成
	
	private String refundState; // 退款单状态
	
	private double touristBook; // 旅游卷合计
	
	private double replaceBook; // 抵用券合计

	private double gift; //礼品折合
	
	private Date appointedTime; // 约定退款时间
	
	private String descript; // 备注
	
	private Date addTime; // 添加时间
	
	private Date updateTime; // 更新时间
	
	private int delFlag; // 删除标志位，0--删除， 1--正常
	
	private int submitFlag; // 保存提交标志位，0：保存，1：提交
	
	private int dealId; // 提交人ID
	
	private String dealName; // 提交人姓名
	
	private int auditFlag; // 审核状态标志位，-1：无需审核，0：待审核，1：已初审，2：已复审一，3：已复审二，4：通过审核，9：审核不通过
	
	private Date auditTime; // 审核时间
	
	private int confirmFlag; // 确认状态标志位，-1：无需确认，0：待确认，1：已确认
	
	private List<SolutionTourticketEntity> tourticketList = new ArrayList<SolutionTourticketEntity>(); // 旅游券
	
	private List<SolutionVoucherEntity> voucherList = new ArrayList<SolutionVoucherEntity>(); // 抵用券
	
	private List<GiftNoteEntity> giftInfoList = new ArrayList<GiftNoteEntity>(); // 礼品
	
	private int saveOrSubmit; // 非表字段，标志保存或提交操作，0：保存，1：提交
	
	private Date submitTime;
	
	private Integer satisfactionFlag; // 满意完结标识，1：满意  0：不满意
	
	private String cmpState;//投诉单状态
	
	private String cardUniqueId;//银行卡id
	
	private String auditName;//最终审核人
	
	public String getCmpState() {
		return cmpState;
	}

	public void setCmpState(String cmpState) {
		this.cmpState = cmpState;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public int getSaveOrSubmit() {
		return saveOrSubmit;
	}

	public void setSaveOrSubmit(int saveOrSubmit) {
		this.saveOrSubmit = saveOrSubmit;
	}

	public String getToOrderId() {
		return toOrderId;
	}

	public void setToOrderId(String toOrderId) {
		this.toOrderId = toOrderId;
	}

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getCollectionUnit() {
		return collectionUnit;
	}

	public void setCollectionUnit(String collectionUnit) {
		this.collectionUnit = collectionUnit;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public double getTouristBook() {
		return touristBook;
	}

	public void setTouristBook(double touristBook) {
		this.touristBook = touristBook;
	}

	public double getReplaceBook() {
		return replaceBook;
	}

	public void setReplaceBook(double replaceBook) {
		this.replaceBook = replaceBook;
	}

	public double getGift() {
		return gift;
	}

	public void setGift(double gift) {
		this.gift = gift;
	}

	public Date getAppointedTime() {
		return appointedTime;
	}

	public void setAppointedTime(Date appointedTime) {
		this.appointedTime = appointedTime;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
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

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getSubmitFlag() {
		return submitFlag;
	}

	public void setSubmitFlag(int submitFlag) {
		this.submitFlag = submitFlag;
	}

	public int getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(int auditFlag) {
		this.auditFlag = auditFlag;
	}

	public int getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(int confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public List<SolutionTourticketEntity> getTourticketList() {
		return tourticketList;
	}

	public void setTourticketList(List<SolutionTourticketEntity> tourticketList) {
		this.tourticketList = tourticketList;
	}

	public List<SolutionVoucherEntity> getVoucherList() {
		return voucherList;
	}

	public void setVoucherList(List<SolutionVoucherEntity> voucherList) {
		this.voucherList = voucherList;
	}

	public List<GiftNoteEntity> getGiftInfoList() {
		return giftInfoList;
	}

	public void setGiftInfoList(List<GiftNoteEntity> giftInfoList) {
		this.giftInfoList = giftInfoList;
	}

	public Integer getCompPersonId() {
		return compPersonId;
	}

	public void setCompPersonId(Integer compPersonId) {
		this.compPersonId = compPersonId;
	}

	public Integer getRefundId() {
		return refundId;
	}

	public void setRefundId(Integer refundId) {
		this.refundId = refundId;
	}

    public Integer getSatisfactionFlag() {
        return satisfactionFlag;
    }

    public void setSatisfactionFlag(Integer satisfactionFlag) {
        this.satisfactionFlag = satisfactionFlag;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBigBank() {
        return bigBank;
    }

    public void setBigBank(String bigBank) {
        this.bigBank = bigBank;
    }

	public String getCardUniqueId() {
		return cardUniqueId;
	}

	public void setCardUniqueId(String cardUniqueId) {
		this.cardUniqueId = cardUniqueId;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
}
