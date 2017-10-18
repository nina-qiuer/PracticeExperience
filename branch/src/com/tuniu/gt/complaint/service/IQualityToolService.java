package com.tuniu.gt.complaint.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;
import com.tuniu.gt.complaint.entity.QualityToolEntity;
/**
* @ClassName: IQualityToolService
* @Description:分配负责service接口
* @author zhoupanpan
* @date 2012-05-03
* @version 1.0
* Copyright by Tuniu.com
*/
public interface IQualityToolService extends IServiceBase {
	
	/**
	* 根据所有质量工具
	* @return list
	* @throws
	*/
	public List<QualityToolEntity> getList();
	
	List<QualityToolEntity> getListInUse();

}
