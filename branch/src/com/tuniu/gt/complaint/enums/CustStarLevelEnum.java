package com.tuniu.gt.complaint.enums;

public enum CustStarLevelEnum {
	// 注册会员
	LEVEL_REGISTER(0, "注册会员"),
	// 一星会员
	LEVEL_ONE(1, "一星会员"),
	// 二星会员
	LEVEL_TWO(2, "二星会员"),
	// 三星会员
	LEVEL_THREE(3, "三星会员"),
	// 四星会员
	LEVEL_FOUR(4, "四星会员"),
	// 五星会员
	LEVEL_FIVE(5, "五星会员"),
	// 白金会员
	LEVEL_SIX(6, "白金会员"),
	// 钻石会员
	LEVEL_SEVEN(7, "钻石会员"),
	// 未知
	LEVEL_UNKOWN(-1, "未知"),;

	private Integer starLevel;

	private String starLevelName;

	private CustStarLevelEnum(Integer starLevel, String starLevelName) {
		this.starLevel = starLevel;
		this.starLevelName = starLevelName;
	}

	private Integer getStarLevel() {
		return starLevel;
	}

	private String getStarLevelName() {
		return starLevelName;
	}

	public static String getValue(Integer starLevel) {
		for (CustStarLevelEnum custStarLevel : CustStarLevelEnum.values()) {
			if (custStarLevel.getStarLevel().equals(starLevel)) {
				return custStarLevel.getStarLevelName();
			}
		}
		return null;
	}
}
