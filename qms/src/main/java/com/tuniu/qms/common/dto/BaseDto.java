package com.tuniu.qms.common.dto;

import java.util.List;


public class BaseDto<T> {
	
	private String id;
	
	/** 添加人 */
	private String addPerson;
	
	/** 添加时间Bgn */
	private String addTimeBgn;
	
	/** 添加时间End */
	private String addTimeEnd; 
	
	/** 删除标识位，0：未删除，1：已删除 */
	private String delFlag; 
	
	/** 每页显示记录数 */
	private int pageSize = 20;
	
	/** 当前页码 */
	private int pageNo = 1;
	
	/** 总记录数 */
	private int totalRecords;
	
	/** 数据集 */
	private List<T> dataList;
	
	/** 获取分页查询开始位置 */
	public int getDataLimitStart() {
		return 0 + pageSize * (pageNo - 1);
	}
	
	/** 获取总页数 */
	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public String getAddTimeBgn() {
		return addTimeBgn;
	}

	public void setAddTimeBgn(String addTimeBgn) {
		this.addTimeBgn = addTimeBgn;
	}

	public String getAddTimeEnd() {
		return addTimeEnd;
	}

	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
}
