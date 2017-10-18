package com.tuniu.gt.complaint;

import tuniu.frm.service.Constant;

/**
* 系统常量类
*/
public final class SysConstans {
	
	/* NG产品系统配置 */
	public static final String NG_BOSS_PRD_NAME = "NG_BOSS_PRD";
	
	public static final Integer NG_BOSS_PRD_CODE = 10010;
	
	public static final Integer NG_BOSS_PRD_TYPE = 1;

	public static final String NG_BOSS_PRD_URL = Constant.CONFIG.getProperty("NG_BOSS_PRD_URL");
	
	/* 团队产品系统配置 */
	public static final String TD_PRD_NAME = "TD_PRD";
	
	public static final Integer TD_PRD_CODE = 10060;
	
	public static final Integer TD_PRD_TYPE = 1;

	public static final String TD_PRD_URL = Constant.CONFIG.getProperty("TD_PRD_URL");
	
	/* Boss产品系统配置 */
	public static final String BOSS_PRD_NAME = "BOSS_PRD";
	
	public static final Integer BOSS_PRD_CODE = 10030;
	
	public static final Integer BOSS_PRD_TYPE = 1;

	public static final String BOSS_PRD_URL = Constant.CONFIG.getProperty("BOSS_PRD_URL");
	
}
