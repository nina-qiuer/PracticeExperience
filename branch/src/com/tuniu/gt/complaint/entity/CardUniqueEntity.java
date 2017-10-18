package com.tuniu.gt.complaint.entity;

public class CardUniqueEntity {
	private String cardUniqueId;//银行卡id
	
	private String cardNo;//账号
	
	private String province;//所在省
	
	private String city;//所在市
	
	private String bankName;//银行名称
	
	private String bankBranchName;//分行名称
	
	private String bankCode;
	
	private String accName;//收款人
	
	private String userId;
	
	private Integer cardType;
	
	private String phone;
	
	private Integer idType;
	
	private String idCode;
	
	private String realBind;

	public String getCardUniqueId() {
		return cardUniqueId;
	}

	public void setCardUniqueId(String cardUniqueId) {
		this.cardUniqueId = cardUniqueId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getRealBind() {
		return realBind;
	}

	public void setRealBind(String realBind) {
		this.realBind = realBind;
	}
}
