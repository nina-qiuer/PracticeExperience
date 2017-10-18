package com.tuniu.config.db.model;



/**
 * @author jiangye
 *
 */
public class DbConfig{

    private Integer id;
    
    private String dataId;
    
    private String content;
    
    private Integer config_level;//配置级别
    
    private String description; // 配置描述

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getConfig_level() {
		return config_level;
	}

	public void setConfig_level(Integer config_level) {
		this.config_level = config_level;
	}

	@Override
    public String toString() {
        return "DbConfig [id=" + id + ", dataId=" + dataId + ", content=" + content + ", description=" + description + "]";
    }
    
}
