package com.tuniu.qms.common.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.RtxRemindDto;
import com.tuniu.qms.common.model.RtxRemind;

public interface RtxRemindMapper extends BaseMapper<RtxRemind, RtxRemindDto>{
	List<RtxRemind> getList2Send();
}
