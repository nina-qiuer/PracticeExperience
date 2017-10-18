package com.tuniu.qms.qs.model;
import java.util.Date;
import com.tuniu.qms.common.model.BaseModel;


public class PunishPrd  extends BaseModel {

	/**
	 * 处罚产品
	 */
	private static final long serialVersionUID = 4116055323412615355L;

	private Date triggerTime;// 触发时间

	private Integer orderId;// 订单号

	private Integer routeId;// 线路编号
	
	private String routeName;// 线路名

	private String businessUnit;// 事业部

	private String prdManager;// 产品经理
	
	private String prdOfficer;// 产品专员

	private String supplier;// 供应商

	private Integer offlineType;// 下线类型 1:触红 2：低满意度 3：低质量
	
	private Integer offlineCount;// 下线次数
	
	private Date offlineTime;// 下线时间
	
	private Date onlineTime;// 上线时间
	
	private String offlineOperPerson;//下线操作人
	
	private Date offlineOperTime;//下线操作时间
	
	private String onlineOperPerson;//上线操作人
	
	private Date onlineOperTime;//上线操作时间
	
	private Integer status;// 状态 1：待整改 2：整改中 3：已整改 4:永久下线 5：放过
	
	private Integer delFlag;// 删除标志位，0：正常，1：删除
	
	private Date travelDateBgn;//出游开始日期
	
	private Integer qcId;//质检单id
	
	private Integer gmvRankId;//gmv 排名数据表中的id
	
	private Integer offlineCause;//低质量下线原因： 1、上个自然月连续两次触红    2、上个自然月连续两次地满意    3、GMV排名在对应方向后5%的后5%
	
	
	private Integer cmpId; // qms触红数据使用
	// 实际下线次数
	private Integer realOffLineCount;
	// 放过操作人
	private String passOperPerson;
	// 放过时间
	private Date passOperTime;
	// 情况说明
	private String remark;
	

	public Date getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}



	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getPrdManager() {
		return prdManager;
	}

	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}

	public String getPrdOfficer() {
		return prdOfficer;
	}

	public void setPrdOfficer(String prdOfficer) {
		this.prdOfficer = prdOfficer;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Integer getOfflineType() {
		return offlineType;
	}

	public void setOfflineType(Integer offlineType) {
		this.offlineType = offlineType;
	}

	public Integer getOfflineCount() {
		return offlineCount;
	}

	public void setOfflineCount(Integer offlineCount) {
		this.offlineCount = offlineCount;
	}

	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getTravelDateBgn() {
		return travelDateBgn;
	}

	public void setTravelDateBgn(Date travelDateBgn) {
		this.travelDateBgn = travelDateBgn;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public String getOfflineOperPerson() {
		return offlineOperPerson;
	}

	public void setOfflineOperPerson(String offlineOperPerson) {
		this.offlineOperPerson = offlineOperPerson;
	}

	public Date getOfflineOperTime() {
		return offlineOperTime;
	}

	public void setOfflineOperTime(Date offlineOperTime) {
		this.offlineOperTime = offlineOperTime;
	}

	public String getOnlineOperPerson() {
		return onlineOperPerson;
	}

	public void setOnlineOperPerson(String onlineOperPerson) {
		this.onlineOperPerson = onlineOperPerson;
	}

	public Date getOnlineOperTime() {
		return onlineOperTime;
	}

	public void setOnlineOperTime(Date onlineOperTime) {
		this.onlineOperTime = onlineOperTime;
	}

	
	public Integer getGmvRankId() {
		return gmvRankId;
	}

	public void setGmvRankId(Integer gmvRankId) {
		this.gmvRankId = gmvRankId;
	}

	public Integer getOfflineCause() {
		return offlineCause;
	}

	public void setOfflineCause(Integer offlineCause) {
		this.offlineCause = offlineCause;
	}


    public Integer getCmpId() {
        return cmpId;
    }

    public void setCmpId(Integer cmpId) {
        this.cmpId = cmpId;
    }

	public Integer getRealOffLineCount() {
		return realOffLineCount;
	}

	public void setRealOffLineCount(Integer realOffLineCount) {
		this.realOffLineCount = realOffLineCount;
	}

	public String getPassOperPerson() {
		return passOperPerson;
	}

	public void setPassOperPerson(String passOperPerson) {
		this.passOperPerson = passOperPerson;
	}

	public Date getPassOperTime() {
		return passOperTime;
	}

	public void setPassOperTime(Date passOperTime) {
		this.passOperTime = passOperTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
