package com.tuniu.gt.uc.entity; 
import java.io.Serializable;
import com.tuniu.gt.frm.entity.EntityBase;


public class JobEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = -8702958846665732154L;


	private String jobName=""; //岗位名称

	private Integer delFlag=0; //是否删除标识



	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName; 
	}

	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag; 
	}
	
}
