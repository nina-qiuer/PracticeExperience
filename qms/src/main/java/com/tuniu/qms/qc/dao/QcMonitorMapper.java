package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcMonitorDto;
import com.tuniu.qms.qc.model.QcMonitor;

public interface QcMonitorMapper extends BaseMapper<QcMonitor, QcMonitorDto> {    
    void batchAddInQcMonitor(List<QcMonitor> list);
    
    List<QcMonitorDto> listAll(QcMonitorDto dto);
    
    List<QcMonitorDto> listByCondition(QcMonitorDto dto);
    
    Integer countByCondition(QcMonitorDto dto);
}
