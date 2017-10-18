package com.tuniu.qms.qc.util;


public class QcConstant {
	
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
	
	public static final int QC_BILL_STATE_BEGIN = 1; // 发起中
	public static final int QC_BILL_STATE_WAIT = 2; // 待分配
	public static final int QC_BILL_STATE_QCBEGIN = 3; // 质检中
	public static final int QC_BILL_STATE_FINISH = 4; // 已完成
	public static final int QC_BILL_STATE_CANCEL = 5; // 已撤销
	public static final int QC_BILL_STATE_WAITFINISH = 6; // 已待结
	public static final int QC_BILL_STATE_AUDIT = 7; // 审核中
	public static final int QC_BILL_STATE_UNUSE = 8; // 无用
	
	public static final int EXECFLAG_YES = 1;//已执行
	
	public static final int EXECFLAG_NO = 0;//未执行
	
	public static final int PUNISHOBJ_INNER = 1;//内部员工
	
	public static final int PUNISHOBJ_OUTER = 2;//外部员工
	
	public static final String DEL_FLAG_TRUE ="1";//已删除
	
	public static final String DEL_FLAG_FALSE ="0";//未删除
	
	public static final String DEV_MANAGER="运营质检经理";
	
	public static final String DEV_COMMISSIONER="运营质检专员";
	
	/** 产品专员 */
	public static final int PERSON_TYPE_PRODUCTER = 11;
	
	/** 产品经理 */
	public static final int PERSON_TYPE_PRODUCTMGR = 12;
	
	/** 高级产品经理 */
	public static final int PERSON_TYPE_PRODUCTHIGHMGR = 13;
	
	/** 团队订单产品专员 */
	public static final int PERSON_TYPE_PRODUCTER_GRP = 20;
	
	/** 客服专员 */
	public static final int PERSON_TYPE_CUSTSER = 21;
	
	/** 客服经理 */
	public static final int PERSON_TYPE_CUSTSERMGR = 22;
	
	/** 高级客服经理 */
	public static final int PERSON_TYPE_CUSTSERHIGHMGR = 23;
	
	/** 运营专员 */
	public static final int PERSON_TYPE_OPERATER = 31;
	
	/** 运营经理 */
	public static final int PERSON_TYPE_OPERATEMGR = 32;
	
	/** 高级运营经理 */
	public static final int PERSON_TYPE_OPERATEHIGHMGR = 33;
	
	public static final String TYPE_NAME_TSUPPORT = "技术支持";
	
	public static final String TYPE_NAME_EONLINE = "紧急上线";
	
	public static final String TYPE_NAME_TICKET_EONLINE = "机票紧急上线";
	
	public static final String TYPE_NAME_TRAFFIC_EONLINE = "交通产品紧急上线";
	
	public static final String TYPE_NAME_ONLINE_BUG = "线上Bug";
	
	public static final String NO_QUALITY_PROBLEM = "非研发故障";
	
	public static final String QUALITY_PROBLEM_S = "S";
	
	public static final String QUALITY_PROBLEM_A = "A";
	
	public static final String QUALITY_PROBLEM_B = "B";
	
	public static final String QUALITY_PROBLEM_C = "C";
	
	public static final String STATUS_RESOLVED = "Resolved";//已解决
	
	public static final String STATUS_CLOSED = "Closed";//已关闭
	
	public static final String STATUS_CLOSED_NORMAL = "已关闭（正常）";//已关闭正常
	
	public static final String JIRA_MIAN_REASON = "问题主要原因";
	
	public static final String JIRA_REASON_DETAIL = "问题原因明细";
	
	public static final String JIRA_SOLUTION  = "解决方案";
	
	public static final String JIRA_EVENT_CLASS  = "严重等级";
	
	public static final String JIRA_DEVPRO_PEOPLE  = "研发处理人";
	
	public static final String JIRA_APP_PEOPLE  = "需求提出人";
	
	public static final int JIRA_NO_CONNECT = 0; // 未关联
	
	public static final int JIRA_CONNECTED = 1; // 已关联
	
	public static final int JIRA_CLOSED = 2 ; // 已关闭
	
	public static final int QC_NO_CONNECT = 0; // 未关联
	
	public static final int QC_CONNECTED = 1; // 已关联
	
	public static final int QC_CLOSED = 2 ; // 已关闭
	
	public static final int QC_BACK = 3 ; // 已返回
	
	public static final String QP_TYPE = "非质量问题" ; 
	
	public static final String NIU_LINE_FLAG ="牛人专线";
	
	public static final int RELATED_FLAG_TURE = 1;//连带责任是
	
	public static final int RELATED_FLAG_FALSE = 0; //连带责任否
	
	public static final String DEV_TYPE = "研发质检"; //研发质检类型
	
	public static final String FINALPAY_TYPE = "尾款收取质检"; //尾款收取质检
	
	public static final int USER_FLAG_TRUE = 1;//人工发起
	
	public static final int USER_FLAG_FALSE = 0; //非人工发起
	
	public static final int USE_FLAG_DEV = 0;//开发故障单
	
	public static final int USE_FLAG_TEST= 1; //测试故障单
	
	public static final String ROBOT= "robot"; //系统
	
	public static final String PROPORTION= "【人均】"; 
	
	public static final String PRODUCT_MANAGER= "产品经理"; 
	
	public static final int INNER_RESP_FALG = 1;//内部责任单标识
	
	public static final int OUTER_RESP_FALG = 2;//外部责任单标识
	
	public static final int RESP_FALG = 0;//责任单标识
	
	public static final String CEO_EMAIL ="yudunde@tuniu.com";
	
	public static final String COO_EMAIL ="yanhaifeng@tuniu.com";
	
	public static final String STANDARD_LEVEL ="一级";
	
	public static final String TALK_TYPE ="质检报告提示信息配置";
	
	public static final String TALK_TYPE_NOCMP ="无投诉质检报告配置";
	
	public static final String INNER_QC = "内部申请质检";
	
	public static final String AIR_UNIT = "机票事业部";
	
	public static final String TRAFFIC_CENTRE = "交通运营中心";
	
	public static final String TRAIN_TICKET_BU = "火车票BU";
	
	public static final String BUS_TICKET_BU = "汽车票BU";
	
	public static final String TRAVEL_CAR_BU = "旅游用车BU";
	
	public static final String REVIEW_NAME ="点评修改";
	
	public static final String SYSTEM_CODE = "QMS";
	
	public static final String BUSINESS_ID ="QMS_20160524";
	
	public static final String SEND_TYPE = "发送中";
	
	public static final int INNER_RESP_PUN_FALG = 1;//内部标识 (责任处罚关联标示)
	
	public static final int OUTER_RESP_PUN_FALG = 2;//外部标识 (责任处罚关联标示)
	
	public static final int CMP_QC_GROUP_DEPARTMENT_ID = 973;//投诉质检组部门id
	
	public static final int DEV_QC_GROUP_DEPARTMENT_ID = 1952;//研发质检组部门id
	
	public static final int COMPLAINT_QC_TYPE_COMMON = 4;//投诉推质检类型   内外部客户反馈、投诉-投诉质检
	
	public static final int SYNCY_DATA_TYPE_COMPLAINT = 1;//通用任务表任务类型标识  投诉单Id 供应商责任赔款查漏
}