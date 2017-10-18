package com.tuniu.qms.qs.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tuniu.qms.qs.util.SaleTypeEnum;

public class OrderUncollection implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 统计日期*/
	private String datekey;
	/** 一级品类名称*/
	private String oneProducttypeName;
	/** 订单号*/
	private Integer orderId;
	/** 签约日期*/
	private String signTime;
	/** 出游日期 */
	private String startDate;
	/** 销售类型  1.自营 2.零售 3.分销 4.APSP(自营-增值推荐) 5.大客户 6.KA*/
	private int saleTypeKey;
	
	private String saleTypeKeyName;
	/** 下单经理（订单客服**） 名称 三级部门名称*/
	private String salerManagerName;
	
	private String salerManagerOneDept;
	
	private String salerManagerTwoDept;
	
	private String salerManagerThreeDept;
	/** 下单客服（订单客服） 名称 三级部门名称*/
	private String followSalerName;
	
	private String followOneDept;
	
	private String followTwoDept;
	
	private String followThreeDept;
	
	/** 旅游卷*/
	private BigDecimal travelAmount;
	/** 抵用卷*/
	private BigDecimal couponAmount;
	
	/** 成人数*/
	private Integer adultNum;
	/** 儿童数*/
	private Integer childrenNum;
	
	/** 赔款金额*/
	private BigDecimal refundAll;
	
	/** 已收金额*/
	private BigDecimal received;
	
	/** 订单金额*/
	private BigDecimal orderAmount;
	
	/** 订单实收金额*/
	private BigDecimal factReceived;
	
	/** 订单未收金额*/
	private BigDecimal orderLostAmount;
	
	/** 距离出游日天数*/
	private int distanceTravelDayNum;
	
	//增加字段
	//售卖方式               
	private String  saleType;
	//分销商id               
    private Integer  distributorId;
    //分销商品牌名   
    private String  distributeCompanyBrand;
    //分销经理               
    private String  distributeManager; 
    
    //客服经理               
    private String  salerManager;
    //客服经理一级部门      
    private String managerOneDept;
    //客服经理二级部门   
    private String managerTwoDept;
    // 客服经理三级组     
    private String managerThreeDept;  
    
    //押款金额
    private BigDecimal orderMortgageSum;
    //押款回收日期
    private String  estimateRecoveryDate;











	public String getDatekey() {
		return datekey;
	}

	public void setDatekey(String datekey) {
		this.datekey = datekey;
	}

	public String getOneProducttypeName() {
		return oneProducttypeName;
	}

	public void setOneProducttypeName(String oneProducttypeName) {
		this.oneProducttypeName = oneProducttypeName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getSaleTypeKey() {
		return saleTypeKey;
	}

	public void setSaleTypeKey(int saleTypeKey) {
		this.saleTypeKey = saleTypeKey;
	}

	public String getSalerManagerName() {
		return salerManagerName;
	}

	public void setSalerManagerName(String salerManagerName) {
		this.salerManagerName = salerManagerName;
	}

	public String getSalerManagerOneDept() {
		return salerManagerOneDept;
	}

	public void setSalerManagerOneDept(String salerManagerOneDept) {
		this.salerManagerOneDept = salerManagerOneDept;
	}

	public String getSalerManagerTwoDept() {
		return salerManagerTwoDept;
	}

	public void setSalerManagerTwoDept(String salerManagerTwoDept) {
		this.salerManagerTwoDept = salerManagerTwoDept;
	}

	public String getSalerManagerThreeDept() {
		return salerManagerThreeDept;
	}

	public void setSalerManagerThreeDept(String salerManagerThreeDept) {
		this.salerManagerThreeDept = salerManagerThreeDept;
	}

	public String getFollowSalerName() {
		return followSalerName;
	}

	public void setFollowSalerName(String followSalerName) {
		this.followSalerName = followSalerName;
	}

	public String getFollowOneDept() {
		return followOneDept;
	}

	public void setFollowOneDept(String followOneDept) {
		this.followOneDept = followOneDept;
	}

	public String getFollowTwoDept() {
		return followTwoDept;
	}

	public void setFollowTwoDept(String followTwoDept) {
		this.followTwoDept = followTwoDept;
	}

	public String getFollowThreeDept() {
		return followThreeDept;
	}

	public void setFollowThreeDept(String followThreeDept) {
		this.followThreeDept = followThreeDept;
	}

	public BigDecimal getTravelAmount() {
		return travelAmount;
	}

	public void setTravelAmount(BigDecimal travelAmount) {
		this.travelAmount = travelAmount;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}

	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}

	public BigDecimal getReceived() {
		return received;
	}

	public void setReceived(BigDecimal received) {
		this.received = received;
	}

	public BigDecimal getRefundAll() {
		return refundAll;
	}

	public void setRefundAll(BigDecimal refundAll) {
		this.refundAll = refundAll;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getFactReceived() {
		return factReceived;
	}

	public void setFactReceived(BigDecimal factReceived) {
		this.factReceived = factReceived;
	}

	public BigDecimal getOrderLostAmount() {
		return orderLostAmount;
	}

	public void setOrderLostAmount(BigDecimal orderLostAmount) {
		this.orderLostAmount = orderLostAmount;
	}

	public int getDistanceTravelDayNum() {
		return distanceTravelDayNum;
	}

	public void setDistanceTravelDayNum(int distanceTravelDayNum) {
		this.distanceTravelDayNum = distanceTravelDayNum;
	}
	
	
	

	

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributeCompanyBrand() {
        return distributeCompanyBrand;
    }

    public void setDistributeCompanyBrand(String distributeCompanyBrand) {
        this.distributeCompanyBrand = distributeCompanyBrand;
    }

    public String getDistributeManager() {
        return distributeManager;
    }

    public void setDistributeManager(String distributeManager) {
        this.distributeManager = distributeManager;
    }

    public String getSalerManager() {
        return salerManager;
    }

    public void setSalerManager(String salerManager) {
        this.salerManager = salerManager;
    }

    public String getManagerOneDept() {
        return managerOneDept;
    }

    public void setManagerOneDept(String managerOneDept) {
        this.managerOneDept = managerOneDept;
    }

    public String getManagerTwoDept() {
        return managerTwoDept;
    }

    public void setManagerTwoDept(String managerTwoDept) {
        this.managerTwoDept = managerTwoDept;
    }

    public String getManagerThreeDept() {
        return managerThreeDept;
    }

    public void setManagerThreeDept(String managerThreeDept) {
        this.managerThreeDept = managerThreeDept;
    }

    public BigDecimal getOrderMortgageSum() {
        return orderMortgageSum;
    }

    public void setOrderMortgageSum(BigDecimal orderMortgageSum) {
        this.orderMortgageSum = orderMortgageSum;
    }

    public String getEstimateRecoveryDate() {
        return estimateRecoveryDate;
    }

    public void setEstimateRecoveryDate(String estimateRecoveryDate) {
        this.estimateRecoveryDate = estimateRecoveryDate;
    }

    public String getSaleTypeKeyName() {
		if(this.saleTypeKey > 0){
			saleTypeKeyName = SaleTypeEnum.getContents(this.saleTypeKey);
		}else{
			saleTypeKeyName = "";
		}
		
		return saleTypeKeyName;
	}
	
}
