package com.tuniu.qms.qc.dao;

import java.util.List;

import com.tuniu.qms.common.dao.base.BaseMapper;
import com.tuniu.qms.qc.dto.QcMonitorRelationDto;
import com.tuniu.qms.qc.model.QcMonitorRelation;

public interface QcMonitorRelationMapper extends BaseMapper<QcMonitorRelation, QcMonitorRelationDto> {    
    void batchAddInQcMonitorRelation(List<QcMonitorRelation> list);
    
    void deleteByMonitorId(Integer monitorId);
}
