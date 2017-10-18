package com.tuniu.qms.qs.model;

import java.util.Date;

import com.tuniu.qms.common.model.BaseModel;
/**
 * 综合满意度达成率
 * @author chenhaitao
 *
 */
public class CompSatisRateMonitor extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	private Integer year;//年
	
	private Integer yearQuarter;// 季度 
	
	private Integer yearMonth;//月份 
	
	private Date statisticDate;//统计时间
	
	private String statisDate;//统计时间
	
	private String  businessUnit ; // 一级部门

	private String prdDep ;// 二级部门 
	
	private String prdTeam;//三级部门
	
	private String prdManager ;//产品经理
	
	private Integer cmpNum;//投诉数 
	
	private Integer ordNum ;//订单数
	
	private Double cmpRate;//投诉率 
	
	private Integer commentNum;//点评量 
	
	private Integer commentScore;//点评分数和
	
	private Double satisfaction ;//满意度
	 
    private Double  compSatisfaction;  //综合满意度
    
    private  Double reacheRate ;//达成率
	
    private Double targetValue;//目标值
    
    private Double oneTargetValue;//一季度目标
    
    private Double twoTargetValue;//二季度目标
    
    private Double threeTargetValue;//三季度目标
    
    private Double fourTargetValue;//四季度目标
    
    private Integer targetId;
    
    //产品专员
    private String prdSpecialist;
    
	public String getStatisDate() {
		return statisDate;
	}

	public void setStatisDate(String statisDate) {
		this.statisDate = statisDate;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Double getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(Double targetValue) {
		this.targetValue = targetValue;
	}

	public Double getOneTargetValue() {
		return oneTargetValue;
	}

	public void setOneTargetValue(Double oneTargetValue) {
		this.oneTargetValue = oneTargetValue;
	}

	public Double getTwoTargetValue() {
		return twoTargetValue;
	}

	public void setTwoTargetValue(Double twoTargetValue) {
		this.twoTargetValue = twoTargetValue;
	}

	public Double getThreeTargetValue() {
		return threeTargetValue;
	}

	public void setThreeTargetValue(Double threeTargetValue) {
		this.threeTargetValue = threeTargetValue;
	}

	public Double getFourTargetValue() {
		return fourTargetValue;
	}

	public void setFourTargetValue(Double fourTargetValue) {
		this.fourTargetValue = fourTargetValue;
	}

	public Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}


	public String getPrdDep() {
		return prdDep;
	}

	public void setPrdDep(String prdDep) {
		this.prdDep = prdDep;
	}


	public String getPrdTeam() {
		return prdTeam;
	}

	public void setPrdTeam(String prdTeam) {
		this.prdTeam = prdTeam;
	}


	public String getPrdManager() {
		return prdManager;
	}

	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}

	public Integer getCmpNum() {
		return cmpNum;
	}

	public void setCmpNum(Integer cmpNum) {
		this.cmpNum = cmpNum;
	}

	public Integer getOrdNum() {
		return ordNum;
	}

	public void setOrdNum(Integer ordNum) {
		this.ordNum = ordNum;
	}

	public Double getCmpRate() {
		return cmpRate;
	}

	public void setCmpRate(Double cmpRate) {
		this.cmpRate = cmpRate;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getCommentScore() {
		return commentScore;
	}

	public void setCommentScore(Integer commentScore) {
		this.commentScore = commentScore;
	}

	public Double getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(Double satisfaction) {
		this.satisfaction = satisfaction;
	}

	public Double getCompSatisfaction() {
		return compSatisfaction;
	}

	public void setCompSatisfaction(Double compSatisfaction) {
		this.compSatisfaction = compSatisfaction;
	}

	public Double getReacheRate() {
		return reacheRate;
	}

	public void setReacheRate(Double reacheRate) {
		this.reacheRate = reacheRate;
	}

	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYearQuarter() {
		return yearQuarter;
	}

	public void setYearQuarter(Integer yearQuarter) {
		this.yearQuarter = yearQuarter;
	}

	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getPrdSpecialist() {
		return prdSpecialist;
	}

	public void setPrdSpecialist(String prdSpecialist) {
		this.prdSpecialist = prdSpecialist;
	}

	
}
