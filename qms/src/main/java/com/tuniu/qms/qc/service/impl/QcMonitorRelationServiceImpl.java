package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.QcMonitorRelationMapper;
import com.tuniu.qms.qc.dto.QcMonitorRelationDto;
import com.tuniu.qms.qc.model.QcMonitorRelation;
import com.tuniu.qms.qc.service.QcMonitorRelationService;

@Service
public class QcMonitorRelationServiceImpl implements QcMonitorRelationService {
    
    @Autowired
    private QcMonitorRelationMapper qcMonitorRelationMapper;

    @Override
    public void add(QcMonitorRelation obj) {
        qcMonitorRelationMapper.add(obj);
    }

    @Override
    public void delete(Integer id) {
        
    }

    @Override
    public void update(QcMonitorRelation obj) {
        
    }

    @Override
    public QcMonitorRelation get(Integer id) {
        return null;
    }

    @Override
    public List<QcMonitorRelation> list(QcMonitorRelationDto dto) {
        return null;
    }

    @Override
    public void loadPage(QcMonitorRelationDto dto) {
        
    }

    @Override
    public void deleteByMonitorId(Integer monitorId) {
        qcMonitorRelationMapper.deleteByMonitorId(monitorId);
    }
   

}
