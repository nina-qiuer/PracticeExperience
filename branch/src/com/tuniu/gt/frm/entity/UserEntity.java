package com.tuniu.gt.frm.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class UserEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -107215124030703546L;

	

	public UserEntity() {
        super();
    }
	
    public UserEntity(Integer id, String realName) {
        this.setId(id);
        this.realName = realName;
    }
    
    private String mobile=""; //手机

	private String tel=""; //电话

	private String email=""; //邮件

	private String password=""; //密码

	private String realName=""; //真实姓名

	private String userName=""; //用户名

	private Integer jobId=0; //岗位ID

	private Integer delFlag=0; //是否删除标识

	private Integer positionId=0; //主部门职位

	private Integer locationId=0; //实际工作地点

	private Integer depId=0; //主部门ID

	private Integer canAdmin=0; //是否可以管理UC

	private String salt=""; //密码干扰码

	private String blackberryCode=""; //黑莓号

	private String sourcePassword=""; //明文密码

	private Integer mainDepSeq=99; //

	private Date updateTime=new Date(); //更新时间

	private String extension=""; //分机号

	private String worknum=""; //工号

	private Date addTime=new Date(); //添加时间
	
	
	private Integer needUpdate=0; //是否需要重新获取权限 



	public Integer getNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(Integer needUpdate) {
		this.needUpdate = needUpdate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile; 
	}

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel; 
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email; 
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password; 
	}

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName; 
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName; 
	}

	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}

	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId; 
	}

	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId; 
	}

	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId; 
	}

	public Integer getCanAdmin() {
		return canAdmin;
	}
	public void setCanAdmin(Integer canAdmin) {
		this.canAdmin = canAdmin; 
	}

	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt; 
	}

	public String getBlackberryCode() {
		return blackberryCode;
	}
	public void setBlackberryCode(String blackberryCode) {
		this.blackberryCode = blackberryCode; 
	}

	public String getSourcePassword() {
		return sourcePassword;
	}
	public void setSourcePassword(String sourcePassword) {
		this.sourcePassword = sourcePassword; 
	}

	public Integer getMainDepSeq() {
		return mainDepSeq;
	}
	public void setMainDepSeq(Integer mainDepSeq) {
		this.mainDepSeq = mainDepSeq; 
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime; 
	}

	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension; 
	}

	public String getWorknum() {
		return worknum;
	}
	public void setWorknum(String worknum) {
		this.worknum = worknum; 
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime; 
	}
	
}
