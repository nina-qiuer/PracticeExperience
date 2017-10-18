package com.tuniu.gt.complaint.enums;

public enum ApprovalResultEnum {
	// 审核通过
	PASS(1),
	// 审核不通过
	NO_PASS(2);

	private Integer value;

	ApprovalResultEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}
}
