package com.tuniu.qms.qc.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.QcMonitorRelationDto;
import com.tuniu.qms.qc.model.QcMonitorRelation;

public interface QcMonitorRelationService extends BaseService<QcMonitorRelation, QcMonitorRelationDto> {
    void deleteByMonitorId(Integer monitorId);
}
