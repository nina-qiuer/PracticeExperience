package com.tuniu.gt.complaint.enums;

public enum DataDictionaryConfigEnum {
	// 第三方投诉来源大类
	UPGRADE_THIRD_PART_COMEFROM(1),
	// 第三方投诉媒体明细
	UPGRADE_THIRD_PART_MEDIA(2),
	// 第三方投诉机构城市
	UPGRADE_THIRD_PART_CITY(3),
	//第三方投诉机构-媒体
	UPGRADE_THIRD_MEDIA(1000);

	private Integer value;

	private DataDictionaryConfigEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}
}
