package com.tuniu.gt.complaint.vo;

import java.util.List;
import java.util.Map;

public class AgencyConfirmInfoPage {
	
	private String menuId;
	
	private int agencyId;
	
	private String agencyName;
	
	private int orderId;
	
	private int complaintId;
	
	private int routeId;
	
	private String startCity;
	
	private String complaintDateBgn;
	
	private String complaintDateEnd;
	
	private String startDateBgn;
	
	private String startDateEnd;
	
	private int confirmState=-2;
	
	private int pageNo; // 页码
	
	private int pageSize; //每页显示条数
	
	private int totalPages; //总页数
	
	private int totalRecords; //总记录数
	
	List<Map<String, Object>> dataList; // 当页数据集

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public int getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getComplaintDateBgn() {
		return complaintDateBgn;
	}

	public void setComplaintDateBgn(String complaintDateBgn) {
		this.complaintDateBgn = complaintDateBgn;
	}

	public String getComplaintDateEnd() {
		return complaintDateEnd;
	}

	public void setComplaintDateEnd(String complaintDateEnd) {
		this.complaintDateEnd = complaintDateEnd;
	}

	public String getStartDateBgn() {
		return startDateBgn;
	}

	public void setStartDateBgn(String startDateBgn) {
		this.startDateBgn = startDateBgn;
	}

	public String getStartDateEnd() {
		return startDateEnd;
	}

	public void setStartDateEnd(String startDateEnd) {
		this.startDateEnd = startDateEnd;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public int getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(int confirmState) {
		this.confirmState = confirmState;
	}
	
}
