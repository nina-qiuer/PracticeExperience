package com.tuniu.gt.uc.entity; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tuniu.gt.frm.entity.EntityBase;
import com.tuniu.gt.satisfaction.entity.FrmUserDelEntity;


public class UcDepartmentDelEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4479319503262123972L;
	
	private Integer currentId;

	private Integer delFlag=0; //是否删除标识

	private Integer depth=0; //深度

	private Integer fatherId; //直接上级ID

	private String depName=""; //部门名称
	
	private Integer useDept;//质检使用的部门
	
	private String selectedDeptName;
	
	private Date updateTime;
	
	private List<FrmUserDelEntity> personList = new ArrayList<FrmUserDelEntity>();

	public Integer getUseDept() {
		return useDept;
	}
	public void setUseDept(Integer useDept) {
		this.useDept = useDept;
	}
	
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}
	
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth; 
	}

	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId; 
	}

	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName; 
	}
	/**
	 * @return the selectedDeptName
	 */
	public String getSelectedDeptName() {
		return selectedDeptName;
	}
	/**
	 * @param selectedDeptName the selectedDeptName to set
	 */
	public void setSelectedDeptName(String selectedDeptName) {
		this.selectedDeptName = selectedDeptName;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the personList
	 */
	public List<FrmUserDelEntity> getPersonList() {
		return personList;
	}
	/**
	 * @param personList the personList to set
	 */
	public void setPersonList(List<FrmUserDelEntity> personList) {
		this.personList = personList;
	}
	/**
	 * @return the currentId
	 */
	public Integer getCurrentId() {
		return currentId;
	}
	/**
	 * @param currentId the currentId to set
	 */
	public void setCurrentId(Integer currentId) {
		this.currentId = currentId;
	}
	
}
