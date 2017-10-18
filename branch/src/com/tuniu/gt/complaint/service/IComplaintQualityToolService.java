package com.tuniu.gt.complaint.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintQualityToolEntity;
/**
* @ClassName: IComplaintQualityToolService
* @Description:分配负责service接口
* @author zhoupanpan
* @date 2012-05-09
* @version 1.0
* Copyright by Tuniu.com
*/
public interface IComplaintQualityToolService extends IServiceBase {
	
	/**
	 * 根据分担方案号取投诉单与质量工具关联表信息
	 * @param solutionId
	 * @return List<ComplaintQualityToolEntity>
	 */
	public List<ComplaintQualityToolEntity> getQualityToolListBySolutionId(int solutionId);

}
