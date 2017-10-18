package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;


/**
 * 自动分单实体类
 */
public class AutoAssignEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 投诉
	 */
	public static final int TYPE_COMPLAINT=1; // 抢单和投诉处理用同一套配置
	
	/**
	 * 质检
	 */
	public static final int TYPE_QC=2;
	
	/**
	 * 在线问答
	 */
	public static final int  ONLINE_QUESTION=5;
	
	/**
	 * 配置类型，1：投诉处理配置，3：抢单   5：在线问答配置，42：质检处理人-自助游，43：质检处理人-团队，44：质检处理人-基础
	 */
	private Integer type;
	
	/**
	 * 用户id
	 */
	private Integer userId; 
	
	/**
	 * 姓名
	 */
	private String userName="";
	
	private int readyFlag; // 客服就绪状态，0：否，1：是

	/**
	 * 部门名称
	 */
	private String departmentName="";

	/**
	 * 所在部门
	 */
	private Integer departmentId;
	
	/**
	 * 最后分单时间
	 */
	private Date lastAssignTime; 

	/**
	 * 删除标示位
	 */
	private Integer delFlag;

	/**
	 * 更新时间
	 */
	private Date updateTime; 

	/**
	 * 添加时间
	 */
	private Date addTime;
	
	/**
	 * 处理岗，1：售前组，2：售后组，3：资深组   4：机票组  5：酒店组
	 */
	private int tourTimeNode;
	
	/**
	 * 一级投诉标识，1：Yes，0：No
	 */
	private int complaintLevel1Flag;
	
	/**
	 * 二级投诉标识，1：Yes，0：No
	 */
	private int complaintLevel2Flag;
	
	/**
	 * 三级投诉标识，1：Yes，0：No
	 */
	private int complaintLevel3Flag;
	
	/**
	 * 跟团事业部标识，1：Yes，0：No
	 */
	private int withGroupFlag;
	
	/**
	 * 团队事业部标识，1：Yes，0：No
	 */
	private int groupFlag;
	
	/**
	 * 自助游事业部标识，1：Yes，0：No
	 */
	private int selfServiceFlag;
	
	/**
	 * 周边标识，1：Yes，0：No
	 */
	private int aroundFlag;
	
	/**
	 * 国内长线标识，1：Yes，0：No
	 */
	private int inlandLongFlag;
	
	/**
	 * 出境短线标识，1：Yes，0：No
	 */
	private int abroadShortFlag;
	
	/**
	 * 出境长线标识，1：Yes，0：No
	 */
	private int abroadLongFlag;
	
	/**
	 * 目的地大类-其他标识，1：Yes，0：No
	 */
	private int othersFlag;
	
	/**
	 * (售后组，组别) 1：呼入组  2：后处理组  3：资深坐席组
	 */
	private int touringGroupType;
	
	/**
	 * 签约城市Code
	 */
	private String signCity;
	
	/**
	 * 产品品类
	 */
	private String productCategory ;
	
	/**
	 * 投诉来源
	 */
	private String comeFrom;
	
	/**会员级别*/
	private Integer guestLevel;
	
	/**
	 * 每天处理的投诉单数目
	 */
	private Long listNums;
	
	private String orderIds;
	
	private List<String> depNames = new ArrayList<String>();//二级部门名称
	
	private List<String> depIds = new ArrayList<String>();//普通-二级部门ID
	
	private List<String> spDepIds = new ArrayList<String>();//特殊-二级部门ID
	
	private int noOrdFlag; // 无订单投诉处理标志 0：不处理，1：处理
	
	private int payforOrder; // 赔款单处理标志  0：不处理，1：处理 
	
	private List<AutoAssignCfgQcEntity> cfgQcList;
	
	private List<AutoAssignCfgQcEntity> cfgQcSpList;
	
	
	
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getSignCity() {
		return signCity;
	}

	public void setSignCity(String signCity) {
		this.signCity = signCity;
	}

	public int getReadyFlag() {
		return readyFlag;
	}

	public void setReadyFlag(int readyFlag) {
		this.readyFlag = readyFlag;
	}

	public int getNoOrdFlag() {
		return noOrdFlag;
	}

	public void setNoOrdFlag(int noOrdFlag) {
		this.noOrdFlag = noOrdFlag;
	}

	public List<AutoAssignCfgQcEntity> getCfgQcSpList() {
		return cfgQcSpList;
	}

	public void setCfgQcSpList(List<AutoAssignCfgQcEntity> cfgQcSpList) {
		this.cfgQcSpList = cfgQcSpList;
	}


	public List<String> getDepNames() {
		return depNames;
	}

	public void setDepNames(List<String> depNames) {
		this.depNames = depNames;
	}

	public List<AutoAssignCfgQcEntity> getCfgQcList() {
		return cfgQcList;
	}

	public void setCfgQcList(List<AutoAssignCfgQcEntity> cfgQcList) {
		this.cfgQcList = cfgQcList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
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

	public int getTourTimeNode() {
		return tourTimeNode;
	}

	public void setTourTimeNode(int tourTimeNode) {
		this.tourTimeNode = tourTimeNode;
	}

	public int getComplaintLevel1Flag() {
		return complaintLevel1Flag;
	}

	public void setComplaintLevel1Flag(int complaintLevel1Flag) {
		this.complaintLevel1Flag = complaintLevel1Flag;
	}

	public int getComplaintLevel2Flag() {
		return complaintLevel2Flag;
	}

	public void setComplaintLevel2Flag(int complaintLevel2Flag) {
		this.complaintLevel2Flag = complaintLevel2Flag;
	}

	public int getComplaintLevel3Flag() {
		return complaintLevel3Flag;
	}

	public void setComplaintLevel3Flag(int complaintLevel3Flag) {
		this.complaintLevel3Flag = complaintLevel3Flag;
	}

	public int getWithGroupFlag() {
		return withGroupFlag;
	}

	public void setWithGroupFlag(int withGroupFlag) {
		this.withGroupFlag = withGroupFlag;
	}

	public int getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(int groupFlag) {
		this.groupFlag = groupFlag;
	}

	public int getSelfServiceFlag() {
		return selfServiceFlag;
	}

	public void setSelfServiceFlag(int selfServiceFlag) {
		this.selfServiceFlag = selfServiceFlag;
	}

	public int getAroundFlag() {
		return aroundFlag;
	}

	public void setAroundFlag(int aroundFlag) {
		this.aroundFlag = aroundFlag;
	}

	public int getInlandLongFlag() {
		return inlandLongFlag;
	}

	public void setInlandLongFlag(int inlandLongFlag) {
		this.inlandLongFlag = inlandLongFlag;
	}

	public int getAbroadShortFlag() {
		return abroadShortFlag;
	}

	public void setAbroadShortFlag(int abroadShortFlag) {
		this.abroadShortFlag = abroadShortFlag;
	}

	public int getAbroadLongFlag() {
		return abroadLongFlag;
	}

	public void setAbroadLongFlag(int abroadLongFlag) {
		this.abroadLongFlag = abroadLongFlag;
	}

	public Long getListNums() {
		return listNums;
	}

	public void setListNums(Long listNums) {
		this.listNums = listNums;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public List<String> getDepIds() {
		return depIds;
	}

	public void setDepIds(List<String> depIds) {
		this.depIds = depIds;
	}

	public List<String> getSpDepIds() {
		return spDepIds;
	}

	public void setSpDepIds(List<String> spDepIds) {
		this.spDepIds = spDepIds;
	}

	public int getTouringGroupType() {
		return touringGroupType;
	}

	public void setTouringGroupType(int touringGroupType) {
		this.touringGroupType = touringGroupType;
	}

	public int getOthersFlag() {
		return othersFlag;
	}

	public void setOthersFlag(int othersFlag) {
		this.othersFlag = othersFlag;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

    public int getPayforOrder() {
        return payforOrder;
    }

    public void setPayforOrder(int payforOrder) {
        this.payforOrder = payforOrder;
    }

	public Date getLastAssignTime() {
		return lastAssignTime;
	}

	public void setLastAssignTime(Date lastAssignTime) {
		this.lastAssignTime = lastAssignTime;
	}

    public Integer getGuestLevel() {
        return guestLevel;
    }

    public void setGuestLevel(Integer guestLevel) {
        this.guestLevel = guestLevel;
    }
	
}
