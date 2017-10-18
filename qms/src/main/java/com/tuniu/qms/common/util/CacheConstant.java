package com.tuniu.qms.common.util;

import java.util.ArrayList;
import java.util.List;

public class CacheConstant {
	
	public static final String DEP_DATA = "QMS_DEP_DATA";
	
	public static final String DEP_ALL_DATA = "QMS_DEP_ALL_DATA";//包含撤销部门
	
	public static final String USER_DATA = "QMS_USER_DATA";
	
	public static final String USER_UNUSE_DATA = "QMS_USER_UNUSE_DATA";
	
	public static final String JOB_DATA = "QMS_JOB_DATA";
	
	public static final String RES_DATA = "QMS_RES_DATA";
	
	public static final String DEST_STD = "QMS_DEST_STD";
	
	public static final String QP_CMP_DATA = "QMS_CMP_DATA";//qc_quality_problem_type 投诉质检
	
	public static final String QP_DEV_DATA = "QMS_DEV_DATA";//qc_quality_problem_type 研发质检
	
	public static final String QP_OPE_DATA = "QMS_OPE_DATA";//qc_quality_problem_type  运营质检
	
	public static final String QP_INNER_DATA = "QMS_INNER_DATA";//qc_quality_problem_type  内部质检
	
	public static final String QT_DATA = "QMS_QT_DATA";//qc_qc_type
	
	public static List<String> getCacheKeys() {
		List<String> keys = new ArrayList<String>();
		keys.add(DEP_DATA);
		keys.add(USER_DATA);
		keys.add(JOB_DATA);
		keys.add(RES_DATA);
		keys.add(DEST_STD);
		keys.add(QP_CMP_DATA);
		keys.add(QP_DEV_DATA);
		keys.add(QP_INNER_DATA);
		keys.add(QT_DATA);
		keys.add(DEP_ALL_DATA);
		keys.add(QP_OPE_DATA);
		keys.add(USER_UNUSE_DATA);
		return keys;
	}

}
