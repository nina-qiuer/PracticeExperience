package com.tuniu.gt.warning.entity;

import java.io.Serializable;

import com.tuniu.gt.frm.entity.EntityBase;

public class WetherCodeEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1142530108878643356L;
	
	private String code; // 天气编码
	
	private String name; // 天气名称
	
	private int keyWordFlag; // 关键字标志位，0：No，1：Yes
	
	private int delFlag; // 删除标志位，0：未删除，1：已删除

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKeyWordFlag() {
		return keyWordFlag;
	}

	public void setKeyWordFlag(int keyWordFlag) {
		this.keyWordFlag = keyWordFlag;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

}
