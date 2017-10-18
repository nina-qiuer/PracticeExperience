package com.tuniu.qms.common.model;

public class Resource extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	
	private Integer pid;
	private String name; // 资源名称，既操作释义
	private String widgetCode; // 界面控件编码，用于控制界面控件的显示，规则：控件类型_操作
	private String url; // 访问地址（相对）
	private Integer menuFlag; // 菜单标识位，0：非菜单，1：菜单
	private Integer operType; // 操作类型，0：查询，1：增删改
	private Integer chkAuthFlag;
	
	public Integer getChkAuthFlag() {
		return chkAuthFlag;
	}
	public void setChkAuthFlag(Integer chkAuthFlag) {
		this.chkAuthFlag = chkAuthFlag;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWidgetCode() {
		return widgetCode;
	}
	public void setWidgetCode(String widgetCode) {
		this.widgetCode = widgetCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getMenuFlag() {
		return menuFlag;
	}
	public void setMenuFlag(Integer menuFlag) {
		this.menuFlag = menuFlag;
	}
	public Integer getOperType() {
		return operType;
	}
	public void setOperType(Integer operType) {
		this.operType = operType;
	}

}
