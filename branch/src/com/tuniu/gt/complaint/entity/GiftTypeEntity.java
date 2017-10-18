package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;


public class GiftTypeEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 8237472984884803810L;


	private String descript=""; //描述

	private Integer cost=0; //成本

	private Integer price=0; //价格

	private String name=""; //名字



	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript; 
	}

	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost; 
	}

	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price; 
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name; 
	}
	
}
