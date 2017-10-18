package com.tuniu.gt.uc.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class PositionEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 4351788877563903278L;


	private Integer delFlag=0; //标记字段,0未删除，1:已删除

	private Integer level; //职位等级

	private String positionName=""; //职位名称

	private Date updateTime=new Date(); //更新时间

	private Date addTime=new Date(); //添加时间



	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level; 
	}

	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}
	
}
