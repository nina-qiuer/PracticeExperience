package com.tuniu.gt.frm.vo;


public class FestivalVo {
	
	private String fYear; // 年份
	
    private Integer fesId; // 节日ID
	
	private String festivalName; // 节日名称
	
	private String fesDateStr; // 放假日期
	
	private String offsetDateStr; // 补休日期

	public Integer getFesId() {
		return fesId;
	}

	public void setFesId(Integer fesId) {
		this.fesId = fesId;
	}

	public String getFestivalName() {
		return festivalName;
	}

	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}

	public String getFesDateStr() {
		return fesDateStr;
	}

	public void setFesDateStr(String fesDateStr) {
		this.fesDateStr = fesDateStr;
	}

	public String getOffsetDateStr() {
		return offsetDateStr;
	}

	public void setOffsetDateStr(String offsetDateStr) {
		this.offsetDateStr = offsetDateStr;
	}

	public String getfYear() {
		return fYear;
	}

	public void setfYear(String fYear) {
		this.fYear = fYear;
	}
	
}
