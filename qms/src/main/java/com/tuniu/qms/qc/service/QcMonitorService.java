package com.tuniu.qms.qc.service;

import java.util.List;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcMonitorDto;
import com.tuniu.qms.qc.model.QcMonitor;

public interface QcMonitorService extends BaseService<QcMonitor, QcMonitorDto> {
    void batchAddInQcMonitor(List<QcMonitor> list);
    List<QcMonitorDto> listAll(QcMonitorDto dto);
    List<QcMonitorDto> listByCondition(QcMonitorDto dto);
}
