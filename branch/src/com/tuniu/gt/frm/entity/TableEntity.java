package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class TableEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 5947346092253870024L;


	public static enum TableType {
		frm,app
	};



	private Integer makeModuleTime=0; //模块生成时间

	private Integer makeFormTime=0; //表单生成时间

	private Integer makeListTime=0; //列表生成时间 

	private String tableName=""; //表名

	private String appId=""; //应用标识,默认frm

	private Integer tableExist=1; //表是否存在

	private Date addTime=new Date(); //添加时间

	private TableType tableType=TableType.frm; //表类型

	private Date updateTime=new Date(); //更新时间

	private Integer formAction=0; //生成action

	private Integer cacheData=0; //是否缓存数据

	private String dirPath=""; //程序生成目录



	public Integer getMakeModuleTime() {
		return makeModuleTime;
	}
	public void setMakeModuleTime(Integer makeModuleTime) {
		this.makeModuleTime = makeModuleTime; 
	}

	public Integer getMakeFormTime() {
		return makeFormTime;
	}
	public void setMakeFormTime(Integer makeFormTime) {
		this.makeFormTime = makeFormTime; 
	}

	public Integer getMakeListTime() {
		return makeListTime;
	}
	public void setMakeListTime(Integer makeListTime) {
		this.makeListTime = makeListTime; 
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName; 
	}

	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId; 
	}

	public Integer getTableExist() {
		return tableExist;
	}
	public void setTableExist(Integer tableExist) {
		this.tableExist = tableExist; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public TableType getTableType() {
		return tableType;
	}
	public void setTableType(TableType tableType) {
		this.tableType = tableType; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public Integer getFormAction() {
		return formAction;
	}
	public void setFormAction(Integer formAction) {
		this.formAction = formAction; 
	}

	public Integer getCacheData() {
		return cacheData;
	}
	public void setCacheData(Integer cacheData) {
		this.cacheData = cacheData; 
	}

	public String getDirPath() {
		return dirPath;
	}
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath; 
	}
	
}
