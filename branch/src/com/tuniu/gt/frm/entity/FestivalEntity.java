package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.sql.Date;


public class FestivalEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer fesId; // 节日ID
	
	private String festivalName; // 节日名称
	
	private Date festivalDate; // 节日相关日期
	
	private Integer dateType; // 日期类型

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFestivalName() {
		return festivalName;
	}

	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}

	public Date getFestivalDate() {
		return festivalDate;
	}

	public void setFestivalDate(Date festivalDate) {
		this.festivalDate = festivalDate;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public Integer getFesId() {
		return fesId;
	}

	public void setFesId(Integer fesId) {
		this.fesId = fesId;
	}
	
}
