package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;


public class TableFieldEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4795005937204654682L;


	private String dbComment=""; //数据库描述

	private String fieldAttr=""; //额外属性

	private String defaultVal=""; //默认值

	private Integer tableId=0; //表id

	private String dataSource=""; //数据源

	private Integer position=0; //位置

	private Integer canCache=0; //该字段是否需要缓存，只有开启表缓存时有效

	private String formTitle=""; //表单显示名

	private String description=""; //详细描述

	private String tableName=""; //表名

	private String listTitle=""; //列表显示名

	private Integer formShow=1; //表单显示

	private String fieldName=""; //字段名

	private String fieldType=""; //字段类型

	private Integer searchShowType=0; //搜索时显示样式

	private String fieldLength=""; //字段长度

	private Integer listShow=0; //是否列表显示

	private String fieldDef=""; //字段定义

	private Integer formShowType=0; //显示方式

	private Integer canSearch=0; //是否可搜索

	private String indexType=""; //索引类型

	private Integer needFill=0; //必填

	private Integer titleOrder=0; //是否可点击标题排序



	public String getDbComment() {
		return dbComment;
	}
	public void setDbComment(String dbComment) {
		this.dbComment = dbComment; 
	}

	public String getFieldAttr() {
		return fieldAttr;
	}
	public void setFieldAttr(String fieldAttr) {
		this.fieldAttr = fieldAttr; 
	}

	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal; 
	}

	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId; 
	}

	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource; 
	}

	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position; 
	}

	public Integer getCanCache() {
		return canCache;
	}
	public void setCanCache(Integer canCache) {
		this.canCache = canCache; 
	}

	public String getFormTitle() {
		return formTitle;
	}
	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle; 
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description; 
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName; 
	}

	public String getListTitle() {
		return listTitle;
	}
	public void setListTitle(String listTitle) {
		this.listTitle = listTitle; 
	}

	public Integer getFormShow() {
		return formShow;
	}
	public void setFormShow(Integer formShow) {
		this.formShow = formShow; 
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName; 
	}

	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType; 
	}

	public Integer getSearchShowType() {
		return searchShowType;
	}
	public void setSearchShowType(Integer searchShowType) {
		this.searchShowType = searchShowType; 
	}

	public String getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength; 
	}

	public Integer getListShow() {
		return listShow;
	}
	public void setListShow(Integer listShow) {
		this.listShow = listShow; 
	}

	public String getFieldDef() {
		return fieldDef;
	}
	public void setFieldDef(String fieldDef) {
		this.fieldDef = fieldDef; 
	}

	public Integer getFormShowType() {
		return formShowType;
	}
	public void setFormShowType(Integer formShowType) {
		this.formShowType = formShowType; 
	}

	public Integer getCanSearch() {
		return canSearch;
	}
	public void setCanSearch(Integer canSearch) {
		this.canSearch = canSearch; 
	}

	public String getIndexType() {
		return indexType;
	}
	public void setIndexType(String indexType) {
		this.indexType = indexType; 
	}

	public Integer getNeedFill() {
		return needFill;
	}
	public void setNeedFill(Integer needFill) {
		this.needFill = needFill; 
	}

	public Integer getTitleOrder() {
		return titleOrder;
	}
	public void setTitleOrder(Integer titleOrder) {
		this.titleOrder = titleOrder; 
	}
	
}
