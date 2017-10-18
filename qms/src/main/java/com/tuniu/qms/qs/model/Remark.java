package com.tuniu.qms.qs.model;


/**
 * 点评接口获取数据封装实体
 * @author chenhaitao
 *
 */
public class Remark {
    
    private Integer routeId; // 线路id
	private Integer orderId;//订单id
	private Integer satisfaction;//满意度  以两位整数表示
	private String remarkTime;//点评时间
	private String startTime; // 出游时间
	private String compTextContent;//整体评价
	private String subTextContent;//分项评价
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(Integer satisfaction) {
		this.satisfaction = satisfaction;
	}
	
	public String getRemarkTime() {
		return remarkTime;
	}
	public void setRemarkTime(String remarkTime) {
		this.remarkTime = remarkTime;
	}
	public String getCompTextContent() {
		return compTextContent;
	}
	public void setCompTextContent(String compTextContent) {
		this.compTextContent = compTextContent;
	}
	
	public String getSubTextContent() {
		return subTextContent;
	}
	public void setSubTextContent(String subTextContent) {
		this.subTextContent = subTextContent;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
    public Integer getRouteId() {
        return routeId;
    }
    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }
    @Override
    public String toString() {
        return "RemarkEntity [routeId=" + routeId + ", orderId=" + orderId + ", satisfaction=" + satisfaction + ", remarkTime="
                + remarkTime + ", startTime=" + startTime + ", compTextContent=" + compTextContent + ", subTextContent="
                + subTextContent + "]";
    }
    
    
}
