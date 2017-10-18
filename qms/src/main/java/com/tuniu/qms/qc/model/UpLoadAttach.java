package com.tuniu.qms.qc.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 上传
 * @author chenhaitao
 *
 */
public class UpLoadAttach implements Serializable{


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    private Integer id ;
    
    /**质量问题ID*/
    private Integer qpId;
    
    /**附件路径 */
    private String path ;
    
    /** 附件名称*/
    private String name;
    
    /**类型*/
    private String type;
    
    /**添加人姓名*/
    private String addPerson;
    
    /**更新人姓名*/
    private String updatePerson;
    
    /** 添加时间*/
    private Date addTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    /**删除标识位,0：未删除，1：已删除  */
    private Integer delFlag;

    
    
	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Integer getQpId() {
		return qpId;
	}

	public void setQpId(Integer qpId) {
		this.qpId = qpId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
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

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	



}
