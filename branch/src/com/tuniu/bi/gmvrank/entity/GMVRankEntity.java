package com.tuniu.bi.gmvrank.entity;

import java.util.Date;

import com.tuniu.gt.frm.entity.EntityBase;

public class GMVRankEntity{

	private Integer id;
	
	/**线路id*/
	private Long routeKey;
	
	/**线路名称*/
	private String routeName;
	
	/**产品名称(目的地)*/
	private String productName;
	
	/**产品方向*/
	private String productArea;
	
	/**平均满意度*/
	private Double avgScore;
	
	/**满意度正排名 */
	private Integer scoreRank;
	
	/**同方向gmv后5%线路毛收入排名*/
	private Integer routeRank;
	
	/**同方向后5%线路数*/
	private Integer routeCnt;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getRouteKey() {
		return routeKey;
	}

	public void setRouteKey(Long routeKey) {
		this.routeKey = routeKey;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}

	public Double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
	}

	public Integer getScoreRank() {
		return scoreRank;
	}

	public void setScoreRank(Integer scoreRank) {
		this.scoreRank = scoreRank;
	}

	public Integer getRouteCnt() {
		return routeCnt;
	}

	public void setRouteCnt(Integer routeCnt) {
		this.routeCnt = routeCnt;
	}

	public Integer getRouteRank() {
		return routeRank;
	}

	public void setRouteRank(Integer routeRank) {
		this.routeRank = routeRank;
	}

}
