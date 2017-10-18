package com.tuniu.gt.complaint.enums;

public enum SpecialListTypeEnum {
	// 第三方投诉来源大类
	C0101001001("C0101001001","黑名单"),
	// 第三方投诉媒体明细
	C0101001002("C0101001002","白名单"),
	// 第三方投诉机构城市
	C0101001003("C0101001003","红名单");

	private String listType;
	
	private String listTypeName;

	private SpecialListTypeEnum(String listType, String listTypeName) {
		this.listType = listType;
		this.listTypeName = listTypeName;
	}
	

	private String getListType() {
        return listType;
    }

	private String getListTypeName() {
        return listTypeName;
    }

	public static String getValue(String listType) {
		for (SpecialListTypeEnum  specialListType: SpecialListTypeEnum.values()) {
            if (specialListType.getListType().equals(listType)) {
                return specialListType.getListTypeName();
            }
        }
		return null;
	}
}
