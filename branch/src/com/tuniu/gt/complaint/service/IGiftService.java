package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.GiftEntity;

import tuniu.frm.core.IServiceBase;
public interface IGiftService extends IServiceBase {
	public List<GiftEntity> searchByComplaintId(int id);
}
