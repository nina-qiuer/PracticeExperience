package com.tuniu.qms.common.util;


public class Constant {
	
	public static final String ENCODING = "utf-8";
	
	/** 否 */
	public static final int NO = 0;
	
	/** 是 */
	public static final int YES = 1;
	
	/** 基础员工 */
	public static final int ROLE_EMPLOYEE = 1;
	
	/** 经理 */
	public static final int ROLE_MANAGER = 2;
	
	/** 管理员 */
	public static final int ROLE_ADMINISTRATOR = 3;
	
	/** 查询操作 */
	public static final int R_OPERATE = 0;
	
	/** 增删改操作 */
	public static final int CUD_OPERATE = 1;
	
	/** 投诉质检 */
	public static final int QC_COMPLAINT = 1;
	
	/** 运营质检 */
	public static final int QC_OPERATE = 2;
	
	/** 研发质检 */
	public static final int QC_DEVELOP = 3;
	
	/** 内部质检 */
	public static final int QC_INNER = 4;
	
	public static final int QC_USE = 1;//选择哪一个质检使用
	
	public static final String SYS_SUCCESS = "0"; // 成功
	
	public static final String SYS_FAILED = "1"; // 失败
	
	public static final String SYS_WARNING = "2"; // 警告
	
	public static final String GROUP_TYPE = "跟团"; // 跟团
	
	public static final int RESOURCE_TYPE = 27; // 地接
	/** 客服人员 */
	public static final int PGA_AGENTTYPE_CUST = 1;
	
	/** 运营人员 */
	public static final int PGA_AGENTTYPE_OPER = 2;
	
	/** 产品人员 */
	public static final int PGA_AGENTTYPE_PRD = 3;
	
	/**  投诉单已完成  */
	public static final int CMP_BILL_STATE_FINISH = 4; 
	
	public static final String BEFORE_TOUR = "出游前"; 
	
	public static final String MIDDLE_TOUR = "出游中"; 
	
	public static final String AFTER_TOUR = "出游后"; 
	
	public static final String PRD_GROUP_TYPE = "跟团游";
	
	public static final String PRD_TEAM_TYPE = "团队游";
	
	/** 投诉改进报告状态*/
	public static final int IMPROVE_STATE_WAIT_FIX_RESP = 1; // 待定责
	public static final int IMPROVE_STATE_HANDLE = 2; // 处理中
	public static final int IMPROVE_STATE_WAIT_CONFIRM = 3; // 待确认
	public static final int IMPROVE_STATE_FINISH = 4; // 已完成
	public static final String IMPROVE_HANDLE_SYSTEM = "系统";//改进人为系统Attach
	
	public static final int ATTACH_BILL_TYPE_QC = 1;//附件类型投诉质检
	public static final int ATTACH_BILL_TYPE_IMPROVE = 2;//附件类型改进报告
	public static final int ATTACH_BILL_TYPE_PUNISHPRD_ONLINE = 3;//附件类型处罚产品上线
	
	public static final String IMPROVE_DEV_HANDLE_PERSON = "石小筱";//改进报告系统处理人是石小筱
	
	public static final String ROBOT = "robot";//代表虚拟人员
	
	public static final String COMPLAINT_QC_GROUP_EMAIL = "g-sup-res@tuniu.com";//投诉质检组邮件
}