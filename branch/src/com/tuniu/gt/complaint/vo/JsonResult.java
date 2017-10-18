package com.tuniu.gt.complaint.vo;

public class JsonResult {

	// 返回编码
	private String retCode;

	private Integer resultCount;

	private Integer pageNum;
	// 返回结果集
	private Object resultList;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Object getResultList() {
		return resultList;
	}

	public void setResultList(Object resultList) {
		this.resultList = resultList;
	}

}
