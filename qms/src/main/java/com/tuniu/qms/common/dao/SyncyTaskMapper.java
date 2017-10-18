package com.tuniu.qms.common.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.common.dto.SyncyTaskDto;
import com.tuniu.qms.common.model.SyncyTask;

public interface SyncyTaskMapper extends BaseMapper<SyncyTask, SyncyTaskDto>{

	List<SyncyTask> getTasksWithCmpFinish();

}
