package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;

/**
 * 投诉问题类型
 * @author yuanyoulei
 *
 */
public class QcQuestionClassEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -5353403911559477004L;
	
	private Integer parentId=0; // 父类型
	private String description = ""; // 类型描述
	private Integer delFlag = 0; // 删除标志
	private Date addTime = new Date(); // 添加时间
	private Date updateTime = new Date(); // 更新时间
	
	private List<QcQuestionClassEntity> childClasses = null; // 子类别
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<QcQuestionClassEntity> getChildClasses() {
		return childClasses;
	}

	public void setChildClasses(List<QcQuestionClassEntity> childClasses) {
		this.childClasses = childClasses;
	}
	
	public int getChildClassesCount() {
		return this.childClasses.size();
	}
	
	public boolean childClassesIsEmpty() {
		return this.childClasses.size()==0 ? true : false;
	}

	public void addChildClass(QcQuestionClassEntity child) {
		if (this.childClasses == null) {
			this.childClasses = new ArrayList<QcQuestionClassEntity>();
		}
		this.childClasses.add(child);
	}
}
