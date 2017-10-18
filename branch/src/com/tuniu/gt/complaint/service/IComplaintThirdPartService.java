package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.ComplaintThirdPartEntity;

import tuniu.frm.core.IServiceBase;

public interface IComplaintThirdPartService extends IServiceBase {

	Integer getComplaintThirdPartCount(Map<String, Object> paramMap);

	List<ComplaintThirdPartEntity> getComplaintThirdPartLists(Map<String, Object> paramMap);
}
