package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tuniu.gt.frm.entity.EntityBase;


public class ComplaintEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 7059273457258055322L;
	
	private String guestNum=""; //客人数量

	private String guestName=""; //客人姓名
	
	private String guestLevel=""; //客人等级
	
	/**
	 * @author Jiang Sir
	 * 客人等级数
	 * 未知 -1
	 * 注册会员 0
	 * 一星会员 1
	 * 二星会员 2
	 * ......
	 * 五星会员 5
	 * 白金会员 6
	 * 钻石会员 7
	 */
	private Integer guestLevelNum = -1;

	private Integer orderId=0; //关联订单id

	private String orderComeFrom=""; //订单来源

	private String routeType=""; //线路类型

	private String routeTypeSp=""; //一级品类
	
	private String orderType=""; //订单类型

	private String seniorManager=""; //高级经理

	private String producter=""; //产品专员

	private String customer=""; //售前客服

	private String route=""; //线路
	
	private Integer routeId=0; //线路ID

	private String customerLeader=""; //客服经理
	
	private Integer customerLeaderId=0; //客服经理Id
	
	private String startCity=""; //出发城市
	
	private Date startTime; //出发时间

	private Date backTime; //归来时间
	
	private String agencyName="";//订单线路供应商名称
	
	private Integer agencyId=0;//订单线路供应商ID
	
	private String comeFrom=""; //投诉来源

	private Integer manager=0; //负责人

	private Integer level=0; //投诉等级

	private String managerName=""; //负责人姓名

	private String ownerPartment=""; //发起人所在部门

	private String productLeader=""; //产品经理

	private String ownerName=""; //发起人姓名

	private String orderState=""; //订单状态

	private String dealName=""; //处理人姓名

	private Integer deal=0; //处理人
	
	private String associaterName=""; //交接人姓名

	private Integer associater = 0; //交接人
	
	private String oldDealName=""; //原处理人姓名

	private Integer oldDeal = 0; //原处理人

	private Date updateTime=new Date(); //更新时间

	private Date addTime=new Date(); //添加时间

	private Integer delFlag=0; //删除标示位

	private Integer state=0; //状态位

	private String requirement=""; //客人要求

	private String descript=""; //投诉说明

	private Date finishDate; //完成时间

	private Integer ownerId=0; //投诉发起人

	private Date buildDate=new Date(); //投诉时间
	
	private String endCity = ""; // 目的地
	
	private String title = ""; // 投诉题目
	
	private String reasonTime ;//新增投诉事宜时间
	
	private String contactPerson = ""; // 联系人姓名
	
	private String contactPhone = ""; // 联系人手机
	
	private String contactMail = ""; // 联系人邮箱
	
	private int createType = 0; // 订单类型，19-团队游，12-自助游，其他跟团
	
	private String dealDepart = ""; // 处理岗位
	
	private String serviceManager = "";//高级客服经理
	
	private String depName = "";//事业部
	
	private String depManager = "";//事业部总经理
	
	private String productManager = "";//产品总监
	
	private float score = 0;//满意度
	
	private int isMedia; // 是否有媒体参与，0：否，1：是
	
	private int isHotel; // 是否为酒店订单
	
	private int contactId = 0; // 联系人id
	
	private int isSticky = 0; //是否置顶
	
	private Date assignTime; //分配处理人时间
	
	private Date repeateTime; //多次投诉世间
	
	private Integer complaintNum; //投诉次数
	
	private Date upgradeTime; //升级售后时间
	
	private int sameGroup = 0; //同团投诉0为否，1为是
	
	private String agencyTel; //供应商联系电话
	
	private String bdpName; //所属事业部处理
	
	private Integer custId;
	
	private Integer productLineId; // 产品线ID
	
	private Integer secondaryDepId; // 二级部门ID
	
	private Integer destCategoryId; // 目的地大类ID
	
	private String destCategoryName; // 目的地大类Name
	
	private Integer inProcessState;//投诉单处于处理中时 0:待首呼 、1:待跟进、2:已跟进、3:待交接
	
	private Date firstWorkdayOvertime;//出境5个工作日，国内3个工作日
	
	private Date secondWorkdayOvertime;//15个工作日
	
	private String followTime;//跟进时间
	
	private Date minFollowTime;//最小跟进时间
	
	private int chgFlightFlag; // 航变标志位
	
	private int warningFlag; // 预警标志位，0：非预警，1：预警
	
	private String compCity; // 投诉来源第三方城市
	
	private Date compTimeTh;
	
	private Integer releaseId; // 发布人ID
	
	private String releaseName; // 发布人姓名
	
	private int thFinishFlag; // 投诉事宜或跟进记录处理完成标志位，0：未完成，1：已完成
	
	private String cmpPerson; // 投诉人
	
	private String cmpPhone; // 投诉人电话
	
	private double productPrice; // 产品价格
	
	private int airFlag; // 是否含机票，0：否，1：是
	
	private double airfare; // 机票价格
	
	private int niuLineFlag; // 是否牛人专线，0：否，1：是
	
	private int batchCompNum; // 批量投诉涉及订单数量
	
	private Integer custAgreeFlag; // 对客达成标志位，0：未达成，1：已达成
	
	private Integer signCityCode; // 签约城市Code
	
	private String signCity; // 签约城市
	
	private String commitStatusName; // 沟通状态
	
	private String commitFlag; // 超时标识
	
	private String commitFlagName; // 超时
	
	private String statisticDate ;//通话时间
	
	private String extension;//客服分机号
	
	private int followNum;//及时拨打跟进次数
	
	private int reasonNum;//及时拨打新增投诉事宜次数
	
	private int sFollowNum;//不及时拨打跟进次数
	
	private int sReasonNum;//不及时拨打新增投诉事宜次数
	
	private String guideId;//导游编号
	
	private String guideName;//导游名字
	
	private String guideCall;//联系电话
	
    private Integer isReparations; //是否为赔款单
    
    private String brandName=""; // 产品品牌
    
    private String operateName ; //运营专员
    
    private String operateManagerName ;//运营经理
    
    private int returnVisitSwitch ; // 回访开关  0：关闭  1：打开
    
    private Integer priority;//优先级 1：紧急，2：重要，3：普通，4：一般
    
    private String priorityContent;//备注
    
    private String finalConclution;// 投诉处理报告最终处理方案
    
    private int special_event_flag;//特殊事件
    
    private int dealBySelf=0;//是否分给自己处理
    
    private Integer clientTypeExpand;//订单标识 620000新笛风订单类型
    
    private Integer classBrandParentId;//一级品类id
    
    private Integer classBrandId;//二级品类id

    private Integer productNewLineTypeId;//目的地大类id

    private Integer destGroupId;//一级品类id
    
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getPriorityContent() {
		return priorityContent;
	}

	public void setPriorityContent(String priorityContent) {
		this.priorityContent = priorityContent;
	}

	private List<ComplaintReasonEntity> reasonList = new ArrayList<ComplaintReasonEntity>();
	
	private ComplaintSolutionEntity compSolution = new ComplaintSolutionEntity();
	
	private List<SupportShareEntity> supportShareList = new ArrayList<SupportShareEntity>();
	
	private ShareSolutionEntity shareSolutionList = new ShareSolutionEntity();
	
	private List<Map<String, Object>> operatorList = new ArrayList<Map<String, Object>>();
	
	private int errCode;//异常编码
	
	private String errorMesg ;//异常消息

	
	
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateManagerName() {
		return operateManagerName;
	}

	public void setOperateManagerName(String operateManagerName) {
		this.operateManagerName = operateManagerName;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrorMesg() {
		return errorMesg;
	}
	
	
	public void setErrorMesg(String errorMesg) {
		this.errorMesg = errorMesg;
	}

	public int getFollowNum() {
		return followNum;
	}
	public void setFollowNum(int followNum) {
		this.followNum = followNum;
	}
	public int getReasonNum() {
		return reasonNum;
	}
	public void setReasonNum(int reasonNum) {
		this.reasonNum = reasonNum;
	}
	public int getsFollowNum() {
		return sFollowNum;
	}
	public void setsFollowNum(int sFollowNum) {
		this.sFollowNum = sFollowNum;
	}
	public int getsReasonNum() {
		return sReasonNum;
	}
	public void setsReasonNum(int sReasonNum) {
		this.sReasonNum = sReasonNum;
	}
	public Integer getSignCityCode() {
		return signCityCode;
	}
	public void setSignCityCode(Integer signCityCode) {
		this.signCityCode = signCityCode;
	}
	public String getSignCity() {
		return signCity;
	}
	public void setSignCity(String signCity) {
		this.signCity = signCity;
	}
	public Integer getCustAgreeFlag() {
		return custAgreeFlag;
	}
	public void setCustAgreeFlag(Integer custAgreeFlag) {
		this.custAgreeFlag = custAgreeFlag;
	}
	public List<Map<String, Object>> getOperatorList() {
		return operatorList;
	}
	public void setOperatorList(List<Map<String, Object>> operatorList) {
		this.operatorList = operatorList;
	}
	public int getBatchCompNum() {
		return batchCompNum;
	}
	public void setBatchCompNum(int batchCompNum) {
		this.batchCompNum = batchCompNum;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public int getAirFlag() {
		return airFlag;
	}
	public void setAirFlag(int airFlag) {
		this.airFlag = airFlag;
	}
	public double getAirfare() {
		return airfare;
	}
	public void setAirfare(double airfare) {
		this.airfare = airfare;
	}
	public int getNiuLineFlag() {
		return niuLineFlag;
	}
	public void setNiuLineFlag(int niuLineFlag) {
		this.niuLineFlag = niuLineFlag;
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
	
	
	
	public String getRouteTypeSp() {
		return routeTypeSp;
	}

	public void setRouteTypeSp(String routeTypeSp) {
		this.routeTypeSp = routeTypeSp;
	}



	/**
	 * (售后组，组别) 1：呼入组  2：后处理组  3：资深坐席组
	 */
	private int touringGroupType;
	
	public int getWarningFlag() {
		return warningFlag;
	}
	public void setWarningFlag(int warningFlag) {
		this.warningFlag = warningFlag;
	}
	public Date getCompTimeTh() {
		return compTimeTh;
	}
	public void setCompTimeTh(Date compTimeTh) {
		this.compTimeTh = compTimeTh;
	}
	public int getThFinishFlag() {
		return thFinishFlag;
	}
	public void setThFinishFlag(int thFinishFlag) {
		this.thFinishFlag = thFinishFlag;
	}
	public Integer getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(Integer releaseId) {
		this.releaseId = releaseId;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	public String getCompCity() {
		return compCity;
	}
	public void setCompCity(String compCity) {
		this.compCity = compCity;
	}
	public List<ComplaintReasonEntity> getReasonList() {
		return reasonList;
	}
	public void setReasonList(List<ComplaintReasonEntity> reasonList) {
		this.reasonList = reasonList;
	}
	public String getFollowTime() {
		return followTime;
	}
	public void setFollowTime(String followTime) {
		this.followTime = followTime;
	}
	public Date getMinFollowTime() {
		return minFollowTime;
	}
	public void setMinFollowTime(Date minFollowTime) {
		this.minFollowTime = minFollowTime;
	}
	public Integer getInProcessState() {
		return inProcessState;
	}
	public void setInProcessState(Integer inProcessState) {
		this.inProcessState = inProcessState;
	}
	public Date getFirstWorkdayOvertime() {
		return firstWorkdayOvertime;
	}
	public void setFirstWorkdayOvertime(Date firstWorkdayOvertime) {
		this.firstWorkdayOvertime = firstWorkdayOvertime;
	}
	public Date getSecondWorkdayOvertime() {
		return secondWorkdayOvertime;
	}
	public void setSecondWorkdayOvertime(Date secondWorkdayOvertime) {
		this.secondWorkdayOvertime = secondWorkdayOvertime;
	}
	public Integer getSecondaryDepId() {
		return secondaryDepId;
	}
	public void setSecondaryDepId(Integer secondaryDepId) {
		this.secondaryDepId = secondaryDepId;
	}
	public Integer getDestCategoryId() {
		return destCategoryId;
	}
	public void setDestCategoryId(Integer destCategoryId) {
		this.destCategoryId = destCategoryId;
	}
	public String getDestCategoryName() {
		return destCategoryName;
	}
	public void setDestCategoryName(String destCategoryName) {
		this.destCategoryName = destCategoryName;
	}
	public Integer getProductLineId() {
		return productLineId;
	}
	public void setProductLineId(Integer productLineId) {
		this.productLineId = productLineId;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getAgencyTel() {
		return agencyTel;
	}
	public void setAgencyTel(String agencyTel) {
		this.agencyTel = agencyTel;
	}
	public int getSameGroup() {
		return sameGroup;
	}
	public void setSameGroup(int sameGroup) {
		this.sameGroup = sameGroup;
	}
	public Date getUpgradeTime() {
		return upgradeTime;
	}
	public void setUpgradeTime(Date upgradeTime) {
		this.upgradeTime = upgradeTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	public String getGuestNum() {
		return guestNum;
	}
	public void setGuestNum(String guestNum) {
		this.guestNum = guestNum; 
	}

	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName; 
	}

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId; 
	}

	public String getOrderComeFrom() {
		return orderComeFrom;
	}
	public void setOrderComeFrom(String orderComeFrom) {
		this.orderComeFrom = orderComeFrom; 
	}

	public String getRouteType() {
		return routeType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType; 
	}

	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType; 
	}

	public String getSeniorManager() {
		return seniorManager;
	}
	public void setSeniorManager(String seniorManager) {
		this.seniorManager = seniorManager; 
	}

	public String getProducter() {
		return producter;
	}
	public void setProducter(String producter) {
		this.producter = producter; 
	}

	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer; 
	}

	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route; 
	}

	public String getCustomerLeader() {
		return customerLeader;
	}
	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader; 
	}

	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity; 
	}

	public String getComeFrom() {
		return comeFrom;
	}
	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom; 
	}

	public Integer getManager() {
		return manager;
	}
	public void setManager(Integer manager) {
		this.manager = manager; 
	}

	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level; 
	}

	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName; 
	}

	public String getOwnerPartment() {
		return ownerPartment;
	}
	public void setOwnerPartment(String ownerPartment) {
		this.ownerPartment = ownerPartment; 
	}

	public String getProductLeader() {
		return productLeader;
	}
	public void setProductLeader(String productLeader) {
		this.productLeader = productLeader; 
	}

	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName; 
	}

	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState; 
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

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state; 
	}

	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement; 
	}

	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript; 
	}

	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate; 
	}

	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId; 
	}

	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate; 
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getBackTime() {
		return backTime;
	}
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	public Integer getRouteId() {
		return routeId;
	}
	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Integer getCustomerLeaderId() {
		return customerLeaderId;
	}
	public void setCustomerLeaderId(Integer customerLeaderId) {
		this.customerLeaderId = customerLeaderId;
	}

	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactMail() {
		return contactMail;
	}
	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getAssociaterName() {
		return associaterName;
	}
	public void setAssociaterName(String associaterName) {
		this.associaterName = associaterName;
	}
	public Integer getAssociater() {
		return associater;
	}
	public void setAssociater(Integer associater) {
		this.associater = associater;
	}
	public String getOldDealName() {
		return oldDealName;
	}
	public void setOldDealName(String oldDealName) {
		this.oldDealName = oldDealName;
	}
	public Integer getOldDeal() {
		return oldDeal;
	}
	public void setOldDeal(Integer oldDeal) {
		this.oldDeal = oldDeal;
	}
	public int getCreateType() {
		return createType;
	}
	public void setCreateType(int createType) {
		this.createType = createType;
	}
	public String getDealDepart() {
		return dealDepart;
	}
	public void setDealDepart(String dealDepart) {
		this.dealDepart = dealDepart;
	}
	public String getServiceManager() {
		return serviceManager;
	}
	public void setServiceManager(String serviceManager) {
		this.serviceManager = serviceManager;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getDepManager() {
		return depManager;
	}
	public void setDepManager(String depManager) {
		this.depManager = depManager;
	}
	public String getProductManager() {
		return productManager;
	}
	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getIsMedia() {
		return isMedia;
	}
	public void setIsMedia(int isMedia) {
		this.isMedia = isMedia;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public int getIsSticky() {
		return isSticky;
	}
	public void setIsSticky(int isSticky) {
		this.isSticky = isSticky;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	public Date getRepeateTime() {
		return repeateTime;
	}
	public void setRepeateTime(Date repeateTime) {
		this.repeateTime = repeateTime;
	}
	public Integer getComplaintNum() {
		return complaintNum;
	}
	public void setComplaintNum(Integer complaintNum) {
		this.complaintNum = complaintNum;
	}
	public void setGuestLevel(String guestLevel) {
		this.guestLevel = guestLevel;
	}
	public String getGuestLevel() {
		return guestLevel;
	}
	public String getBdpName() {
		return bdpName;
	}
	public void setBdpName(String bdpName) {
		this.bdpName = bdpName;
	}
	public ShareSolutionEntity getShareSolutionList() {
		return shareSolutionList;
	}
	public void setShareSolutionList(ShareSolutionEntity shareSolutionList) {
		this.shareSolutionList = shareSolutionList;
	}
	public List<SupportShareEntity> getSupportShareList() {
		return supportShareList;
	}
	public void setSupportShareList(List<SupportShareEntity> supportShareList) {
		this.supportShareList = supportShareList;
	}
	public ComplaintSolutionEntity getCompSolution() {
		return compSolution;
	}
	public void setCompSolution(ComplaintSolutionEntity compSolution) {
		this.compSolution = compSolution;
	}
	public int getChgFlightFlag() {
		return chgFlightFlag;
	}
	public void setChgFlightFlag(int chgFlightFlag) {
		this.chgFlightFlag = chgFlightFlag;
	}
	public int getTouringGroupType() {
		return touringGroupType;
	}
	public void setTouringGroupType(int touringGroupType) {
		this.touringGroupType = touringGroupType;
	}
	public String getCommitStatusName() {
		return commitStatusName;
	}
	public void setCommitStatusName(String commitStatusName) {
		this.commitStatusName = commitStatusName;
	}
	
	public String getCommitFlag() {
		return commitFlag;
	}
	public void setCommitFlag(String commitFlag) {
		this.commitFlag = commitFlag;
	}
	public String getCommitFlagName() {
		return commitFlagName;
	}
	public void setCommitFlagName(String commitFlagName) {
		this.commitFlagName = commitFlagName;
	}
	public String getStatisticDate() {
		return statisticDate;
	}
	public void setStatisticDate(String statisticDate) {
		this.statisticDate = statisticDate;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getReasonTime() {
		return reasonTime;
	}
	public void setReasonTime(String reasonTime) {
		this.reasonTime = reasonTime;
	}

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getGuideCall() {
		return guideCall;
	}

	public void setGuideCall(String guideCall) {
		this.guideCall = guideCall;
	}

    public Integer getIsReparations() {
        return isReparations;
    }

    public void setIsReparations(Integer isReparations) {
        this.isReparations = isReparations;
    }

    public int getIsHotel() {
        return isHotel;
    }

    public void setIsHotel(int isHotel) {
        this.isHotel = isHotel;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getReturnVisitSwitch() {
        return returnVisitSwitch;
    }

    public void setReturnVisitSwitch(int returnVisitSwitch) {
        this.returnVisitSwitch = returnVisitSwitch;
    }

    public Integer getGuestLevelNum() {
        return guestLevelNum;
    }

    public void setGuestLevelNum(Integer guestLevelNum) {
        this.guestLevelNum = guestLevelNum;
    }

    public String getFinalConclution() {
        return finalConclution;
    }

    public void setFinalConclution(String finalConclution) {
        this.finalConclution = finalConclution;
    }

	public int getSpecial_event_flag() {
		return special_event_flag;
	}

	public void setSpecial_event_flag(int special_event_flag) {
		this.special_event_flag = special_event_flag;
	}

	public int getDealBySelf() {
		return dealBySelf;
	}

	public void setDealBySelf(int dealBySelf) {
		this.dealBySelf = dealBySelf;
	}

	public Integer getClientTypeExpand() {
		return clientTypeExpand;
	}

	public void setClientTypeExpand(Integer clientTypeExpand) {
		this.clientTypeExpand = clientTypeExpand;
	}

	public Integer getClassBrandParentId() {
		return classBrandParentId;
	}

	public void setClassBrandParentId(Integer classBrandParentId) {
		this.classBrandParentId = classBrandParentId;
	}

	public Integer getClassBrandId() {
		return classBrandId;
	}

	public void setClassBrandId(Integer classBrandId) {
		this.classBrandId = classBrandId;
	}

	public Integer getProductNewLineTypeId() {
		return productNewLineTypeId;
	}

	public void setProductNewLineTypeId(Integer productNewLineTypeId) {
		this.productNewLineTypeId = productNewLineTypeId;
	}

	public Integer getDestGroupId() {
		return destGroupId;
	}

	public void setDestGroupId(Integer destGroupId) {
		this.destGroupId = destGroupId;
	}
}
