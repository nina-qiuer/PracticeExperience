package com.tuniu.qms.common.model;

import java.io.Serializable;

/**
 * 产品，维护公司所有产品数据
 * @author wangmingfang
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	/** 产品名称 */
	private String prdName;
	
	/** 品类ID */
	private Integer cateId;
	
	/** 品类名称 */
	private String cateName;
	
	/** 子品类ID */
	private Integer subCateId;
	
	/** 子品类名称 */
	private String subCateName;
	
	/** 品牌ID */
	private Integer brandId;
	
	/** 品牌名称 */
	private String brandName;
	
	/** 产品线ID */
	private Integer productLineId;
	
	/** 产品线目的地 */
	private String prdLineDestName;
	
	/** 目的地大类ID */
	private Integer destCateId;
	
	/** 目的地大类Name */
	private String destCateName;
	
	/** 事业部ID */
	private Integer businessUnitId;
	
	/** 事业部Name */
	private String businessUnitName;
	
	/** 产品部ID */
	private Integer prdDepId;
	
	/** 产品部Name */
	private String prdDepName;
	
	/** 产品组ID */
	private Integer prdTeamId;
	
	/** 产品组Name */
	private String prdTeamName;
	
	/** 产品经理ID */
	private Integer prdManagerId;
	
	/** 产品经理 */
	private String prdManager;
	
	/** 产品专员ID */
	private Integer producterId;
	
	/** 产品专员 */
	private String producter;
	
	/** 删除标识位，0：未删除，1：已删除 */
	private Integer delFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getSubCateId() {
		return subCateId;
	}

	public void setSubCateId(Integer subCateId) {
		this.subCateId = subCateId;
	}

	public String getSubCateName() {
		return subCateName;
	}

	public void setSubCateName(String subCateName) {
		this.subCateName = subCateName;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getProductLineId() {
		return productLineId;
	}

	public void setProductLineId(Integer productLineId) {
		this.productLineId = productLineId;
	}

	public String getPrdLineDestName() {
		return prdLineDestName;
	}

	public void setPrdLineDestName(String prdLineDestName) {
		this.prdLineDestName = prdLineDestName;
	}

	public Integer getDestCateId() {
		return destCateId;
	}

	public void setDestCateId(Integer destCateId) {
		this.destCateId = destCateId;
	}

	public String getDestCateName() {
		return destCateName;
	}

	public void setDestCateName(String destCateName) {
		this.destCateName = destCateName;
	}

	public Integer getBusinessUnitId() {
		return businessUnitId;
	}

	public void setBusinessUnitId(Integer businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	public Integer getPrdDepId() {
		return prdDepId;
	}

	public void setPrdDepId(Integer prdDepId) {
		this.prdDepId = prdDepId;
	}

	public String getPrdDepName() {
		return prdDepName;
	}

	public void setPrdDepName(String prdDepName) {
		this.prdDepName = prdDepName;
	}

	public Integer getPrdTeamId() {
		return prdTeamId;
	}

	public void setPrdTeamId(Integer prdTeamId) {
		this.prdTeamId = prdTeamId;
	}

	public String getPrdTeamName() {
		return prdTeamName;
	}

	public void setPrdTeamName(String prdTeamName) {
		this.prdTeamName = prdTeamName;
	}

	public Integer getPrdManagerId() {
		return prdManagerId;
	}

	public void setPrdManagerId(Integer prdManagerId) {
		this.prdManagerId = prdManagerId;
	}

	public String getPrdManager() {
		return prdManager;
	}

	public void setPrdManager(String prdManager) {
		this.prdManager = prdManager;
	}

	public Integer getProducterId() {
		return producterId;
	}

	public void setProducterId(Integer producterId) {
		this.producterId = producterId;
	}

	public String getProducter() {
		return producter;
	}

	public void setProducter(String producter) {
		this.producter = producter;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
}
