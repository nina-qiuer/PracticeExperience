package com.tuniu.gt.complaint.service;

import java.util.List;

import com.tuniu.gt.complaint.entity.QcQuestionClassEntity;

import tuniu.frm.core.IServiceBase;
public interface IQcQuestionClassService extends IServiceBase {
	public List<QcQuestionClassEntity> list();
}
