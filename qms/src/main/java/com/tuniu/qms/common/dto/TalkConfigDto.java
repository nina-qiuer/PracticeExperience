package com.tuniu.qms.common.dto;

import com.tuniu.qms.common.model.TalkConfig;

public class TalkConfigDto extends BaseDto<TalkConfig> {
    private String content;    private String type;    public String getContent() {      return this.content;    }    public void setContent(String content) {      this.content=content;    }    public String getType() {      return this.type;    }    public void setType(String type) {      this.type=type;    }}