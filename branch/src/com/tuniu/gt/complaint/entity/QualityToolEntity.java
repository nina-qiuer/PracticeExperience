package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;

/**
* @ClassName: QualityToolEntity
* @Description:质量工具实体类
* @author zhoupanpan
* @version 1.0
* Copyright by Tuniu.com
*/
public class QualityToolEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4866652416142480918L;


	private Integer delFlag = 0;//删除标志
	private Integer level = 1;//质量工具级别
	private Integer type = 1;//质量工具类别
	private String name = "";//质量工具名称
	private String remark = "";//质量工具备注
	private Integer useFlag = 1;//启用标记

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	@Override
	public String toString() {
		return "QualityToolEntity [delFlag=" + delFlag + ", level=" + level
				+ ", type=" + type + ", name=" + name + ", remark=" + remark
				+ ", useFlag=" + useFlag + "]";
	}

	

}
