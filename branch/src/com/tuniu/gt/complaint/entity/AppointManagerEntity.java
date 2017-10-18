package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


/**
* @ClassName: AppointManagerEntity
* @Description:指定负责人对象
* @author Ocean Zhong
* @date 2012-1-29 下午4:19:02
* @version 1.0
* Copyright by Tuniu.com
*/
public class AppointManagerEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -2697133319590084092L;


	private Integer delFlag=0; //删除标示位

	private Date updateTime=new Date(); //更新时间

	private Date addTime=new Date(); //添加时间

	private String userName=""; //姓名

	private Integer userId=0; //用户id

	private String departmentName=""; //部门名称

	private Integer departmentId=0; //所在部门

	private Integer type=0; //类别


	// 各类负责人定义,youlei 2012/2/23
	public static final int PRE_SALES_OFFICER = 1; // 售前客服负责人
	public static final int TOURING_OFFICER = 2; // 出游中客服负责人
	public static final int POST_SALES_OFFICER = 3; // 售后客服负责人
	public static final int QC_OFFICER = 4; // 质检负责人
	public static final int AIR_TICKIT_OFFICER = 6; // 机票组负责人
	public static final int HOTEL_OFFICER = 7; // 酒店组负责人
	public static final int TRAFFIC_OFFICER = 15; // 交通组负责人
	public static final int DISTRIBUTE_OFFICER = 8; // 分销组负责人
	/** add by hucunzhen */
	public static final int SPECIAL_BEFORE_OFFICER = 5; // 出游前客户服务负责人
	public static final int ALLOW_DEPARTMENT = 9; // 发起投诉时被投诉订单所属事业部
	public static final int COMPLAINT_DEPARTMENT = 10; // 部门信息
	/** add by wanghaijun 2012-09-21 */
	public static final int TOURING_OFFICER_TRANSFER = 11;	//出游中借调人员
	public static final int DEV_OFFICER = 12;				//研发负责人
	public static final int AFT_OFFICER_TRANSFER = 13;	//出游后借调人员
	public static final int MSG_RETURN_VISIT = 14; //短信回访开关负责人
	/** add by chenyu */
	public static final int VIP_DEPART_GROUP = 16; //能处理投诉的会员维护人三级组配置表
	
	/** add by fangyouming */
    public static final int VIP_DEPART_OFFICER = 17; //会员顾问组负责人

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName; 
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId; 
	}

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName; 
	}

	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId; 
	}

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type; 
	}
	
}
