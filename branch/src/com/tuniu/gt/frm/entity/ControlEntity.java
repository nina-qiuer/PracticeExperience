package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;


public class ControlEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -5227683027595071095L;


	private String controlHtml=""; //控件HTML

	private String controlName=""; //控件名称

	private String dataSource=""; //默认数据源

	private Integer needDs=0; //是否需要数据源

	private String controlRes=""; //控件需要的资源，如JS,CSS等

	private Integer userDefine=0; //0:标准控件,1:用自定义控件

	private String aftCode=""; //控件使用后附加代码

	private String initCode=""; //控件初始化代码



	public String getControlHtml() {
		return controlHtml;
	}
	public void setControlHtml(String controlHtml) {
		this.controlHtml = controlHtml; 
	}

	public String getControlName() {
		return controlName;
	}
	public void setControlName(String controlName) {
		this.controlName = controlName; 
	}

	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource; 
	}

	public Integer getNeedDs() {
		return needDs;
	}
	public void setNeedDs(Integer needDs) {
		this.needDs = needDs; 
	}

	public String getControlRes() {
		return controlRes;
	}
	public void setControlRes(String controlRes) {
		this.controlRes = controlRes; 
	}

	public Integer getUserDefine() {
		return userDefine;
	}
	public void setUserDefine(Integer userDefine) {
		this.userDefine = userDefine; 
	}

	public String getAftCode() {
		return aftCode;
	}
	public void setAftCode(String aftCode) {
		this.aftCode = aftCode; 
	}

	public String getInitCode() {
		return initCode;
	}
	public void setInitCode(String initCode) {
		this.initCode = initCode; 
	}
	
}
