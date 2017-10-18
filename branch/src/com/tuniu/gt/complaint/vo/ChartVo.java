package com.tuniu.gt.complaint.vo;

public class ChartVo {
	
	private String title; // 图片标题
	
	private String xAxisName; // X轴名称
	
	private String yAxisName; // Y轴名称
	
	private int width; // 图片宽度
	
	private int hight; // 图片高度
	
	private String[] rowKeys; // 行名称（图例中包含的统计项目名称）
	
	private String[] columnKeys; // 列名称（维度名称）
	
	private double[][] data; // 数据（第一维是统计项，第二维是维度）
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getxAxisName() {
		return xAxisName;
	}
	public void setxAxisName(String xAxisName) {
		this.xAxisName = xAxisName;
	}
	public String getyAxisName() {
		return yAxisName;
	}
	public void setyAxisName(String yAxisName) {
		this.yAxisName = yAxisName;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHight() {
		return hight;
	}
	public void setHight(int hight) {
		this.hight = hight;
	}
	public String[] getRowKeys() {
		return rowKeys;
	}
	public void setRowKeys(String[] rowKeys) {
		this.rowKeys = rowKeys;
	}
	public String[] getColumnKeys() {
		return columnKeys;
	}
	public void setColumnKeys(String[] columnKeys) {
		this.columnKeys = columnKeys;
	}
	public double[][] getData() {
		return data;
	}
	public void setData(double[][] data) {
		this.data = data;
	}
	
}
