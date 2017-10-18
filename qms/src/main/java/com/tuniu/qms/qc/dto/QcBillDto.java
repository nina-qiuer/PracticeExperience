package com.tuniu.qms.qc.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QcBill;

@Component
public class QcBillDto extends BaseDto<QcBill> {

	private Integer qcId;//质检单号
	
	private String qualityEventClass;//质量问题等级
	
	private String statisticDate;//统计日期
	
	private Double lossAmountBgn;// 损失金额下边界
	private Double lossAmountEnd;// 损失金额上

	private String addTimeFrom;// 添加时间上边界
	private String addTimeTo;// 添加时间下边界

	private String submitTimeFrom;// 提交时间上边界
	private String submitTimeTo;// 提交时间下边界
	
	private String departDateBgn;// 出游日期上边界
	private String departDateEnd;// 出游日期下边界

	private String distribTimeBgn;// 分配时间上边界
	private String distribTimeEnd;// 分配时间下边界

	private String returnDateBgn;// 归来日期下边界
	private String returnDateEnd;// 归来日期上边界

	private String cmpAddTimeBgn;// 投诉添加日期上边界
	private String cmpAddTimeEnd;// 投诉添加日期下边界

	private String finishTimeBgn;// 完成时间上边界
	private String finishTimeEnd;// 完成时间下边界

	private String cmpFinishTimeBgn;// 投诉处理完成时间上边界

	private String cmpFinishTimeEnd;// 投诉处理完成时间下边界

	private Integer state = 3;// 质检状态

	private Integer cmpLevel;// 投诉级别
	private Integer cmpState /*= 4*/;// 投诉处理状态

	private Integer prdId;// 产品编号
	private Integer orderId;// 订单编号
	private Integer cmpId;// 投诉编号

	private Integer qcPersonId;
	private String qcPerson;// 质检员姓名

	private String cateName;// 产品品类
	private String subCateName;// 产品子品类

	private Integer groupFlag;//组别
	
	private String brandName;// 产品品牌

	private String departCity;// 出发地

	private String dealPerson;// 处理人
	
	private String comeFrom;// 投诉来源

	private String businessUnitName;// 事业部

	private Double prdAdultPriceBgn;// 成人途牛价上边界
	private Double prdAdultPriceEnd;// 成人途牛价下边界

	private Double indemnifyAmountBgn;// 对客赔偿总额上边界
	private Double indemnifyAmountEnd;// 对客赔偿总额下边界

	private Double claimAmountBgn;// 供应商赔付总额上边界
	private Double claimAmountEnd;// 供应商赔付总额下边界

	private String prdLineDestName;// 目 的 地

	private Integer	faultSource ;
	
	private String assignTo;// 分配给质检员姓名
	
	private String prdManager; // 产品经理（订单）
	private String producter; // 产品专员（订单）
	private String salerManagerName; // 客服经理
	private String salerName; // 客服专员
	
	private String qcJobType = "qcPoint"; // 质检任务类型

	private List<Integer> qcBillIds;// 非配给质检员的质检单号数组
	
	private String destCateName ; // 目的地大类
	
	private List<String> qualityTypes;//研发质量问题等级

	private Integer launchFlag ;//发起权限
	
	private Integer qcTypeId;//质检类型
	
	private List<Integer> states;//状态列表
	
	private Integer checkFlag =1;//管理权限标识
	
	private String updatePerson ;
	
	private Integer returnFlag ;//返回标识
	
	private Integer  cancelFlag =0;//撤销标识
	
	private String qcCancelTimeBgn;// 撤销时间上边界
	private String qcCancelTimeEnd;// 撤销时间下边界
	
	/**重要标记   0:普通  1:重要 2：非常重要*/
    private Integer importantFlag;
    
    /**改进报告查询条件     0：全部  1：有   2：没有 */
    private Integer impBillFlag;
    
    private String faultHappenTimeFrom;
	private String faultHappenTimeTo;

	private String faultFinishTimeFrom;
	private String faultFinishTimeTo;
	
	public Integer getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(Integer cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(String statisticDate) {
		this.statisticDate = statisticDate;
	}

	public Integer getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(Integer returnFlag) {
		this.returnFlag = returnFlag;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getSubmitTimeFrom() {
		return submitTimeFrom;
	}

	public void setSubmitTimeFrom(String submitTimeFrom) {
		this.submitTimeFrom = submitTimeFrom;
	}

	public String getSubmitTimeTo() {
		return submitTimeTo;
	}

	public void setSubmitTimeTo(String submitTimeTo) {
		this.submitTimeTo = submitTimeTo;
	}

	public List<Integer> getStates() {
		return states;
	}

	public void setStates(List<Integer> states) {
		this.states = states;
	}

	public Integer getQcTypeId() {
		return qcTypeId;
	}

	public void setQcTypeId(Integer qcTypeId) {
		this.qcTypeId = qcTypeId;
	}

	public Integer getLaunchFlag() {
		return launchFlag;
	}

	public void setLaunchFlag(Integer launchFlag) {
		this.launchFlag = launchFlag;
	}

	public List<String> getQualityTypes() {
		return qualityTypes;
	}

	public void setQualityTypes(List<String> qualityTypes) {
		this.qualityTypes = qualityTypes;
	}

	public String getDestCateName() {
		return destCateName;
	}

	public void setDestCateName(String destCateName) {
		this.destCateName = destCateName;
	}

	/**排序字段*/
    private String orderField = "id";
    
    /**排序方向  0：不排序  1：升序  2：降序*/
    private Integer orderDirect = 0;
    
	private List<String> fields = new ArrayList<String>(Arrays.asList("importantFlag,cancelFlag,companyLose,orderId,qcPeriodEndTime,cmpId,userFlag,returnFlag,id,qcPerson,prdId,groupDate,cateName,prdLineDestName,businessUnitName,prdManager,salerManagerName,cmpLevel,cmpAddTime,cmpFinishTime,indemnifyAmount,claimAmount,isExistImpBill".split(",")));

	public Integer getQcPersonId() {
		return qcPersonId;
	}

	public void setQcPersonId(Integer qcPersonId) {
		this.qcPersonId = qcPersonId;
	}

	public Double getLossAmountBgn() {
		return lossAmountBgn;
	}

	public void setLossAmountBgn(Double lossAmountBgn) {
		this.lossAmountBgn = lossAmountBgn;
	}

	public Double getLossAmountEnd() {
		return lossAmountEnd;
	}

	public void setLossAmountEnd(Double lossAmountEnd) {
		this.lossAmountEnd = lossAmountEnd;
	}
	

	public String getAddTimeFrom() {
		return addTimeFrom;
	}

	public void setAddTimeFrom(String addTimeFrom) {
		this.addTimeFrom = addTimeFrom;
	}

	public String getAddTimeTo() {
		return addTimeTo;
	}

	public void setAddTimeTo(String addTimeTo) {
		this.addTimeTo = addTimeTo;
	}

	public String getDepartDateBgn() {
		return departDateBgn;
	}

	public void setDepartDateBgn(String departDateBgn) {
		this.departDateBgn = departDateBgn;
	}

	public String getDepartDateEnd() {
		return departDateEnd;
	}

	public void setDepartDateEnd(String departDateEnd) {
		this.departDateEnd = departDateEnd;
	}

	public String getDistribTimeBgn() {
		return distribTimeBgn;
	}

	public Integer getFaultSource() {
		return faultSource;
	}

	public void setFaultSource(Integer faultSource) {
		this.faultSource = faultSource;
	}

	public void setDistribTimeBgn(String distribTimeBgn) {
		this.distribTimeBgn = distribTimeBgn;
	}

	public String getDistribTimeEnd() {
		return distribTimeEnd;
	}

	public void setDistribTimeEnd(String distribTimeEnd) {
		this.distribTimeEnd = distribTimeEnd;
	}

	public String getReturnDateBgn() {
		return returnDateBgn;
	}

	public void setReturnDateBgn(String returnDateBgn) {
		this.returnDateBgn = returnDateBgn;
	}

	public String getReturnDateEnd() {
		return returnDateEnd;
	}

	public void setReturnDateEnd(String returnDateEnd) {
		this.returnDateEnd = returnDateEnd;
	}

	public String getCmpAddTimeBgn() {
		return cmpAddTimeBgn;
	}

	public void setCmpAddTimeBgn(String cmpAddTimeBgn) {
		this.cmpAddTimeBgn = cmpAddTimeBgn;
	}

	public String getCmpAddTimeEnd() {
		return cmpAddTimeEnd;
	}

	public void setCmpAddTimeEnd(String cmpAddTimeEnd) {
		this.cmpAddTimeEnd = cmpAddTimeEnd;
	}

	public String getFinishTimeBgn() {
		return finishTimeBgn;
	}

	public void setFinishTimeBgn(String finishTimeBgn) {
		this.finishTimeBgn = finishTimeBgn;
	}

	public String getFinishTimeEnd() {
		return finishTimeEnd;
	}

	public void setFinishTimeEnd(String finishTimeEnd) {
		this.finishTimeEnd = finishTimeEnd;
	}

	public String getCmpFinishTimeBgn() {
		return cmpFinishTimeBgn;
	}

	public void setCmpFinishTimeBgn(String cmpFinishTimeBgn) {
		this.cmpFinishTimeBgn = cmpFinishTimeBgn;
	}

	public String getCmpFinishTimeEnd() {
		return cmpFinishTimeEnd;
	}

	public void setCmpFinishTimeEnd(String cmpFinishTimeEnd) {
		this.cmpFinishTimeEnd = cmpFinishTimeEnd;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCmpLevel() {
		return cmpLevel;
	}

	public void setCmpLevel(Integer cmpLevel) {
		this.cmpLevel = cmpLevel;
	}

	public Integer getCmpState() {
		return cmpState;
	}

	public void setCmpState(Integer cmpState) {
		this.cmpState = cmpState;
	}

	public Integer getPrdId() {
		return prdId;
	}

	public void setPrdId(Integer prdId) {
		this.prdId = prdId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCmpId() {
		return cmpId;
	}

	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}

	public String getQcPerson() {
		return qcPerson;
	}

	public void setQcPerson(String qcPerson) {
		this.qcPerson = qcPerson;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getSubCateName() {
		return subCateName;
	}

	public void setSubCateName(String subCateName) {
		this.subCateName = subCateName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public Double getPrdAdultPriceBgn() {
		return prdAdultPriceBgn;
	}

	public void setPrdAdultPriceBgn(Double prdAdultPriceBgn) {
		this.prdAdultPriceBgn = prdAdultPriceBgn;
	}

	public Double getPrdAdultPriceEnd() {
		return prdAdultPriceEnd;
	}

	public void setPrdAdultPriceEnd(Double prdAdultPriceEnd) {
		this.prdAdultPriceEnd = prdAdultPriceEnd;
	}

	public Double getIndemnifyAmountBgn() {
		return indemnifyAmountBgn;
	}

	public void setIndemnifyAmountBgn(Double indemnifyAmountBgn) {
		this.indemnifyAmountBgn = indemnifyAmountBgn;
	}

	public Double getIndemnifyAmountEnd() {
		return indemnifyAmountEnd;
	}

	public void setIndemnifyAmountEnd(Double indemnifyAmountEnd) {
		this.indemnifyAmountEnd = indemnifyAmountEnd;
	}

	public Double getClaimAmountBgn() {
		return claimAmountBgn;
	}

	public void setClaimAmountBgn(Double claimAmountBgn) {
		this.claimAmountBgn = claimAmountBgn;
	}

	public Double getClaimAmountEnd() {
		return claimAmountEnd;
	}

	public void setClaimAmountEnd(Double claimAmountEnd) {
		this.claimAmountEnd = claimAmountEnd;
	}

	public String getPrdLineDestName() {
		return prdLineDestName;
	}

	public void setPrdLineDestName(String prdLineDestName) {
		this.prdLineDestName = prdLineDestName;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public List<Integer> getQcBillIds() {
		return qcBillIds;
	}

	public void setQcBillIds(List<Integer> qcBillIds) {
		this.qcBillIds = qcBillIds;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public String getQcJobType() {
		return qcJobType;
	}

	public void setQcJobType(String qcJobType) {
		this.qcJobType = qcJobType;
	}

	public Integer getQcId() {
		return qcId;
	}

	public void setQcId(Integer qcId) {
		this.qcId = qcId;
	}

	public String getQualityEventClass() {
		return qualityEventClass;
	}

	public void setQualityEventClass(String qualityEventClass) {
		this.qualityEventClass = qualityEventClass;
	}

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public Integer getOrderDirect() {
        return orderDirect;
    }

    public void setOrderDirect(Integer orderDirect) {
        this.orderDirect = orderDirect;
    }

    public String getPrdManager() {
        return prdManager;
    }

    public void setPrdManager(String prdManager) {
        this.prdManager = prdManager;
    }

    public String getProducter() {
        return producter;
    }

    public void setProducter(String producter) {
        this.producter = producter;
    }

    public String getSalerManagerName() {
        return salerManagerName;
    }

    public void setSalerManagerName(String salerManagerName) {
        this.salerManagerName = salerManagerName;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	public Integer getImportantFlag() {
		return importantFlag;
	}

	public void setImportantFlag(Integer importantFlag) {
		this.importantFlag = importantFlag;
	}

	public Integer getImpBillFlag() {
		return impBillFlag;
	}

	public void setImpBillFlag(Integer impBillFlag) {
		this.impBillFlag = impBillFlag;
	}

	public String getQcCancelTimeBgn() {
		return qcCancelTimeBgn;
	}

	public void setQcCancelTimeBgn(String qcCancelTimeBgn) {
		this.qcCancelTimeBgn = qcCancelTimeBgn;
	}

	public String getQcCancelTimeEnd() {
		return qcCancelTimeEnd;
	}

	public void setQcCancelTimeEnd(String qcCancelTimeEnd) {
		this.qcCancelTimeEnd = qcCancelTimeEnd;
	}

	public String getFaultHappenTimeFrom() {
		return faultHappenTimeFrom;
	}

	public void setFaultHappenTimeFrom(String faultHappenTimeFrom) {
		this.faultHappenTimeFrom = faultHappenTimeFrom;
	}

	public String getFaultHappenTimeTo() {
		return faultHappenTimeTo;
	}

	public void setFaultHappenTimeTo(String faultHappenTimeTo) {
		this.faultHappenTimeTo = faultHappenTimeTo;
	}

	public String getFaultFinishTimeFrom() {
		return faultFinishTimeFrom;
	}

	public void setFaultFinishTimeFrom(String faultFinishTimeFrom) {
		this.faultFinishTimeFrom = faultFinishTimeFrom;
	}

	public String getFaultFinishTimeTo() {
		return faultFinishTimeTo;
	}

	public void setFaultFinishTimeTo(String faultFinishTimeTo) {
		this.faultFinishTimeTo = faultFinishTimeTo;
	}
	
}
