package com.tuniu.gt.datafix.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class DataFixEntity extends EntityBase implements Serializable {

	private static final long serialVersionUID = 2005458576856136855L;
	
	private Integer maxId;
	private String description;
	private Date createTime;
	private String creator;
	private Date lastModifyTime;

	public Integer getMaxId() {
		return maxId;
	}
	public void setMaxId(Integer maxId) {
		this.maxId = maxId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
}
