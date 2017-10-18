package com.tuniu.gt.complaint;

/**
* @ClassName: Constans
* @Description:(常量类)
* @author Ocean Zhong
* @date 2012-1-18 下午3:59:51
*
*/
public final class Constans {
	
	public static final String SYSTEM_ADMINISTRATOR_ACCOUNT = "robot";
	
	/** 发起中 */
	public static final int LAUNCHING = 0;
	
	/** 分配中 */
	public static final int ASSIGNING = 1;
	
	/** 处理中 */
	public static final int PROCESSING = 2;
	
	/** 已完成 */
	public static final int FINISHED = 3;
	
	/**
	 * 投诉单号详情页面URL,供投诉系统提供给订单组的服务接口使用
	 */
	public static final String COMPLAINT_DETAIL_URL = "http://crm.tuniu.com/complaint/complaint/action/complaint-toBill?id=";
	public static final String BEFORE_TRAVEL = "售前组";
	public static final String IN_TRAVEL = "售后组";
	public static final String AFTER_TRAVEL = "资深组";
	public static final String SPECIAL_BEFORE_TRAVEL = "出游前客户服务";
	public static final String AIR_TICKIT = "机票组";
	public static final String HOTEL = "酒店组";
	public static final String DISTRIBUTE = "分销组";
	public static final String TRAFFIC = "交通组";
	public static final String CUST_DEPART = "会员事业部";
	public static final String STAR_CUST_DEPART = "途致事业部";
	public static final String PREDETERMINE_DEPART = "预订中心";
	public static final String CUSTOMER_DEPART = "客户事业部";
	public static final String VIP_DEPART = "会员顾问";
	
	public static final String QUERY_ORDER_ERROR = "查询订单出错";
	
	public static final String SUBMIT_COMPLAINT_INFO = "发起投诉";
	public static final String UPDATE_COMPLAINT_INFO = "更新投诉";
	public static final String DELETE_COMPLAINT_INFO = "删除投诉";
	public static final String CANCEL_COMPLAINT_INFO = "撤销投诉";
	public static final String BACK_COMPLAINT_INFO = "升级售后退回";
	public static final String UPGRADE_COMPLAINT_INFO = "投诉单升级";
	public static final String UPGRADE_SHARE_INFO = "升级售后填写分担方案";
	public static final String ASSIGN_DEALER = "分配处理人";
	public static final String ASSIGN_NEW_DEALER = "重新分配处理人";
	public static final String ASSOCIATE_DEALER = "设置工作交接人";
	public static final String COMPLAINT_SHARE_CONFIRM = "确认投诉分担";
	public static final String EMPLOYEE_SHARE_CONFIRM = "确认员工分担";
	public static final String SUPPLY_SHARE_CONFIRM = "确认供应商分担";
	
	public static final String FIRST_CALL = "首呼";
	public static final String CHANGE_DEAL_DEPT = "变更处理岗";
	public static final String SUBMIT_COMPLAINT_REASON = "填写投诉事宜";
	public static final String UPDATE_COMPLAINT_REASON = "修改投诉事宜";
	public static final String SUBMIT_CHECK_REQUEST = "发起核实请求";
	public static final String FEEDBACK_CHECK_REQUEST = "反馈核实请求";
	public static final String SET_FOLLOW_TIME = "跟进提醒";
	public static final String SET_FOLLOW_UP_RECORD = "跟进记录";
	public static final String UPDATE_FOLLOW_UP_RECORD = "修改跟进记录";
	public static final String WORK_HANDOVER = "下班交接";
	public static final String OUT_WORK_HANDOVER = "不在班交接";
	public static final String SYSTEM_ASSIGN = "系统分配";
	public static final String ADD_COMPLAINT_REASON = "新增投诉事宜";
	public static final String FLIGHT_CHANGE="航班变动";
	public static final String BIG_NIGHT_HANDOVER = "大夜交接";
	
	public static final String SUBMIT_SOLUTION = "提交对客解决方案";
	public static final String SAVE_SOLUTION = "保存对客解决方案";
	public static final String BACK_TO_STATUS = "投诉已待结状态退回投诉处理中状态";
	public static final String NO_NEED_INDEMNITY = "无需赔偿，转入投诉处已完成状态";
	public static final String SUBMIT_SHARE_SOLUTION = "提交分担方案";
	public static final String SAVE_SHARE_SOLUTION = "保存分担方案";
	public static final String ADJUST_SHARE_SOLUTION = "调整分担方案";
	public static final String SUBMIT_GIFT_SOLUTION = "申请礼品，提交礼品申请至OA礼品出库审核列表";
	public static final String SUBMIT_REFUND_APPLY = "提交退款申请";
	public static final String PRODUCT_APPEAL = "产品发起申诉";
	public static final String DEFAULT_SIGN_MANAGER_NAME = "北京国际旅行社有限公司";
	
	public static final String COMPLAINT_OWNER_NOTE = "投诉发起人备注";
	public static final String SAND_SMS = "发送短信";
	public static final String CHANGE_LEVL = "修改投诉等级";
	
	public static final String AUDIT_BACK = "审核环节退回";
	public static final String FINANCE_BACK = "财务退回";
	
	public static final String GOTO_AFTER_GROUP = "流转至后处理组";
	public static final String GOTO_SENIOR_GROUP = "流转至资深坐席组";
	
	public static final String GOTO_UPCOMING_FINISH = "转至已待结";
	public static final String GOTO_PROCESSING = "返回处理中";
	
	public static final String ORDER_STATE = "出游中";
	
	public static final String ORDER_TYPE_NAME ="团队";
	
	public static final String NEW_TEAM_ORDER_TYPE ="新团队游";
	
	public static final String NIU_LINE_FLAG ="牛人专线";
	
	public static final String CALL_STATUS ="拨号";
	
	public static final String CALL_STATUS_OUT ="工作";
	
	public static final String CALL_LOGOUT ="登出";
	
	public static final String CALL_DIR ="呼出";
	
	public static final String CALL_TYPE ="外部";
	
	public static final int IS_NIU_LINE = 1;
	
	public static final int NOT_NIU_LINE = 0;
	
	public static final String WEIBO ="微博";
	
	public static final String LOCAL_QUALITY ="当地质检";
	
	public static final String TOURISM ="旅游局";
	
	public static final String ASSURANCE ="保险";
	
	public static final String PRODUCT_TYPE="团队游";//团队游
	
	public static final int AGENT_CUSTOMER =1;//客服专员
	
	public static final int AGENT_OPERATE =2;//运营专员
	
	public static final int AGENT_PRODUCT = 3;//产品专员
	
	public static final Integer COMPLAINT_STATE = 1;//待处理
	
	public static final Integer NO_CUST_AGREE_FLAG =0 ;//对客未达成
	
	public static final int  CUST_AGREE_FLAG =1 ;//对客已达成
	
	public static final String TIME_DAY01 = "01";//1号
	
	public static final String TIME_DAY02 = "02";//2号
	
	public static final String TIME_DAY03 = "03";//3号 。。。以此类推
	
	public static final String TIME_DAY04 = "04";
	
	public static final String TIME_DAY05 = "05";
	
	public static final String TIME_DAY06 = "06";
	
	public static final String TIME_DAY07 = "07";
	
	public static final String TIME_DAY08 = "08";
	
	public static final String TIME_DAY09 = "09";
	
	public static final String TIME_DAY10 = "10";
	
	public static final String TIME_DAY11 = "11";
	
	public static final String TIME_DAY12 = "12";
	
	public static final String TIME_DAY13 = "13";
	
	public static final String TIME_DAY14 = "14";
	
	public static final String TIME_DAY15 = "15";
	
	public static final String TIME_DAY16 = "16";
	
	public static final String TIME_DAY17 = "17";
	
	public static final String TIME_DAY18 = "18";
	
	public static final String TIME_DAY19 = "19";
	
	public static final String TIME_DAY20 = "20";
	
	public static final String TIME_DAY21 = "21";
	
	public static final String TIME_DAY22 = "22";
	
	public static final String TIME_DAY23 = "23";
	
	public static final String TIME_DAY24 = "24";
	
	public static final String TIME_DAY25 = "25";
	
	public static final String TIME_DAY26 = "26";
	
	public static final String TIME_DAY27 = "27";
	
	public static final String TIME_DAY28 = "28";
	
	public static final String TIME_DAY29 = "29";
	
	public static final String TIME_DAY30 = "30";
	
	public static final String TIME_DAY31 = "31";
	
	
	public static final String  COMMIT_CONSULT ="1";
	
	public static final String  COMMIT_CONSULT_NAME="咨询";
	
	public static final String  COMMIT_COMPLAINT="2";
	
	public static final String  COMMIT_COMPLAINT_NAME="投诉";
	
	public static final String  COMMIT_NO_BEGIN ="1";
	
	public static final String  COMMIT_NO_BEGIN_NAME="未发起";
	
	public static final String  COMMIT_NO_ANSWER="2";
	
	public static final String  COMMIT_NO_ANSWER_NAME="未回复";
	
    public static final String  COMMIT_ING ="3";
	
	public static final String  COMMIT_ING_NAME="沟通中";
	
	public static final String  COMMIT_FINISH="4";
	
	public static final String  COMMIT_FINISH_NAME="已完成";
	
	public static final String  TIME_OUT_NAME="超时";
	
	public static final String ROUTETYPE_1 ="跟团游";
	public static final String ROUTETYPE_2 ="自助游";
	public static final String ROUTETYPE_3 ="酒店";
	public static final String ROUTETYPE_5 ="团队游";
	public static final String ROUTETYPE_6 ="门票";
	public static final String ROUTETYPE_8 ="自驾游";
	public static final String ROUTETYPE_9 ="签证";
	public static final String ROUTETYPE_10 ="邮轮";
	public static final String ROUTETYPE_11 ="火车票";
	public static final String ROUTETYPE_202 ="零售";
	public static final String ROUTETYPE_0= "机票";
	public static final String ROUTE_FLY = "国内散客票";
	
	public static final String AGENT_NULL ="无";
	
	/**
	 * 回呼相关参数
	 */
	public static final int  CALL_BACK_WAIT = 1;
	public static final int  CALL_BACK_FINISH = 2;
	public static final String LAUNCH_CALL_BACK = "添加回呼任务";
	public static final String CALL_BACK_DEFAULT_CONTENT = "新增投诉事宜须回呼";
	public static final String INTIME_CALL_BACK_REMIND = "及时回呼提醒";
	public static final String OVERTIMEUPGRADE = "超时未首呼升级";
	
	
	public static final Integer noAutoAssign = -1;//分单配置未配置
	public static final Integer autoAssignNotWork = 0;//分单配置未打开
	public static final Integer autoAssignIsWork = 1;//分单配置已经打开
	
	
	public static final int  FIRST_CALL_WAIT= 1;
	
	public static final int  FIRST_CALL_FINISH= 2;

	public static final String SYSTEM_CODE = "CMP";
	
	public static final String BUSINESS_ID ="CMP_20160524";
	
	public static final String ORDERTYPE_19 = "通信";
	
	public static final String ROUTE_TYPE_DERIVATIVE = "衍生品";
	
	public static final String ORDERTYPE_20 = "旅游券订单"; 
	
	public static final String ORDERTYPE_41 = "保险门户订单";
	
	/**
	 * 邮件配置
	 */
	public static final Integer MAIL_TYPE_RECEIVE = 1;
	public static final Integer MAIL_TYPE_CARBON_COPY = 2;
	
	/**
	 * 投诉升级第三方
	 */
	public static final String UPGRADE_THIRD_PART = "投诉升级第三方";
	public static final String ADD_UPGRADE_THIRD_PART = "新增第三方来源";
	public static final String MERGE_UPGRADE_THIRD_PART = "修改第三方来源";
	public static final String DELETE_UPGRADE_THIRD_PART = "删除第三方来源";
	
	public static interface ComplaintState
	{
		/**
		 * 投诉待处理  1
		 */
		Integer ON_HAND = 1;
		
		/**
		 * 投诉处理中 2
		 */
		Integer PROCESSING = 2;
		
		/**
		 * 投诉已待结 3
		 */
		Integer UPCOMING_FINISH = 3;
		
		/**
		 * 投诉待处理 4
		 */
		Integer FINISHED = 4;
		
		/**
		 * 投诉待指定（升级售后   5
		 *  CTS is short for "Complaint to Specify"
		 */
		Integer CTS_UPTO_AFT_SALE = 5;
		
		/**
		 * 投诉待指定（提交售后填写分担方案  6 
		 * CTS is short for "Complaint to Specify"
		 */
		Integer CTS_AFTSALE_SHARE_SOLUTION = 6;
		
		/**
		 * 投诉待指定（升级售前  7
		 * CTS is short for "Complaint to Specify"
		 */
		Integer CTS_UPTO_PRE_SALE = 7;
		
		/**
		 * 已撤销 9
		 */
		Integer CANCELED = 9;
		
		/**
		 * 投诉待指定（升级客服中心售后   10
		 * CTS is short for "Complaint to Specify"
		 */
		Integer CTS_UPTO_AFTSALE_CALLCENTER = 10;
	}


	public static String getDestCateName(Integer destCateId) {
		String destCateName = "";
		switch (destCateId) {
		case 2:
			destCateName = "亚洲";
			break;
		case 5:
			destCateName = "出境当地参团";
			break;
		case 8:
			destCateName = "出境短线";
			break;
		case 9:
			destCateName = "出境长线";
			break;
		case 10:
			destCateName = "国内当地参团";
			break;	
		case 11:
			destCateName = "国内长线";
			break;
		case 12:
			destCateName = "周边";
			break;
		case 15:
			destCateName = "非洲";
			break;
		case 19:
			destCateName = "美洲";
			break;
		case 20:
			destCateName = "欧洲";
			break;
		case 71:
			destCateName = "澳洲";
			break;
		case 76:
			destCateName = "出境";
			break;
		case 80:
			destCateName = "境内";
			break;
		case 81:
			destCateName = "境外";
			break;
		default:
			break;
		}
		return destCateName;
	}
	
	public static final int PGA_NEW_DISTRIBUTION_ORDER_CODE = 620000;//pga 新分销订单标识码
	
	public static final Integer COMPLAINT_LEVEL_FIRST = 1;//投诉等级
}
