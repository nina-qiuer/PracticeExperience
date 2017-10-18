package com.tuniu.qms.common.service;

import java.util.List;

import com.tuniu.qms.common.dto.RtxRemindDto;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.service.base.BaseService;

public interface RtxRemindService extends BaseService<RtxRemind, RtxRemindDto>{
	List<RtxRemind> getList2Send();
}
