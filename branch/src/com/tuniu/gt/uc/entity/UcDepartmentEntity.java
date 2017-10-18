package com.tuniu.gt.uc.entity; 
import java.io.Serializable;

import com.tuniu.gt.frm.entity.EntityBase;


public class UcDepartmentEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4479319503262123972L;
	
	private Integer id;

	private Integer pId; //直接上级ID

	private String name=""; //部门名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
}
