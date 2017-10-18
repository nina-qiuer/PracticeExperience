package com.tuniu.gt.uc.entity; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javassist.expr.NewArray;

import com.tuniu.gt.frm.entity.EntityBase;


public class DepartmentEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4479319503262123972L;
	
	private Integer id;

	private Integer delFlag=0; //是否删除标识

	private Integer depth=0; //深度

	private String treeId=""; //树形结点id

	private String treeFatherId=""; //树形结点父id

	private Integer fatherId; //直接上级ID

	private String depName=""; //部门名称
	
	private Integer useDept;//质检使用的部门
	
	private List<DepartmentEntity> childDept = new ArrayList<DepartmentEntity>();//

	public Integer getUseDept() {
		return useDept;
	}
	public void setUseDept(Integer useDept) {
		this.useDept = useDept;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

	public String getTreeId() {
		return treeId;
	}
	public void setTreeId(String treeId) {
		this.treeId = treeId; 
	}

	public String getTreeFatherId() {
		return treeFatherId;
	}
	public void setTreeFatherId(String treeFatherId) {
		this.treeFatherId = treeFatherId; 
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
	public List<DepartmentEntity> getChildDept() {
		return childDept;
	}
	public void setChildDept(List<DepartmentEntity> childDept) {
		this.childDept = childDept;
	}
	
}
