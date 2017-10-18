package com.tuniu.gt.complaint.vo;

import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;

/**
 * 
 * @author weiweiqin 2012/4/13
 * 用于分担供应商页面展示的辅助vo
 * 
 */
public class SupportShareVo {

	/**
	 * 分担供应商数据类
	 */
	private SupportShareEntity supprotShareEntity;
	
	/**
	 * 附件数据类
	 */
	private AttachEntity attachEntity;

	public SupportShareEntity getSupprotShareEntity() {
		return supprotShareEntity;
	}

	public void setSupprotShareEntity(SupportShareEntity supprotShareEntity) {
		this.supprotShareEntity = supprotShareEntity;
	}

	public AttachEntity getAttachEntity() {
		return attachEntity;
	}

	public void setAttachEntity(AttachEntity attachEntity) {
		this.attachEntity = attachEntity;
	}
}
