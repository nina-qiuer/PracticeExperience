package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.Date;

public class ComplaintCompleted implements Serializable {

	private static final long serialVersionUID = -8846925903601455255L;
	
	private Integer id;//主键
	
	private Integer order_id;//订单id
	
	private String deal_depart;//处理岗
	
	private Date finish_time;//完成时间

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public String getDeal_depart() {
		return deal_depart;
	}

	public void setDeal_depart(String deal_depart) {
		this.deal_depart = deal_depart;
	}

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
