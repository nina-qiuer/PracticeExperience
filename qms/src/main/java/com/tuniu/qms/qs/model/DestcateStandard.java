package com.tuniu.qms.qs.model;

import com.tuniu.qms.common.model.BaseModel;

public class DestcateStandard extends BaseModel {
	
	private static final long serialVersionUID = 1L;

	/** 目的地大类 */
	private String destCate;
	
	/** 团期丰富度 */
	private Double groupRichness;
	
	/** 出团通知时限 */
	private Integer noticeTimeLimit;

	public String getDestCate() {
		return destCate;
	}

	public void setDestCate(String destCate) {
		this.destCate = destCate;
	}

	public Double getGroupRichness() {
		return groupRichness;
	}

	public void setGroupRichness(Double groupRichness) {
		this.groupRichness = groupRichness;
	}

	public Integer getNoticeTimeLimit() {
		return noticeTimeLimit;
	}

	public void setNoticeTimeLimit(Integer noticeTimeLimit) {
		this.noticeTimeLimit = noticeTimeLimit;
	}
	
}
