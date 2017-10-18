package com.tuniu.gt.innerqc.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.innerqc.entity.InnerQcEntity;
import com.tuniu.gt.innerqc.vo.InnerQcPage;

public interface InnerQcService extends IServiceBase {
	
	void getInnerQcPage(InnerQcPage page);
	
	List<InnerQcEntity> getInnerQcList(InnerQcPage page);
	
	void deleteInnerQc(InnerQcEntity entity);
	
	InnerQcEntity getInnerQc(int id);
	
}
