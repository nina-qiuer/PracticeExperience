package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.QcMonitorMapper;
import com.tuniu.qms.qc.dto.QcMonitorDto;
import com.tuniu.qms.qc.model.QcMonitor;
import com.tuniu.qms.qc.service.QcMonitorService;

@Service
public class QcMonitorServiceImpl implements QcMonitorService {

    @Autowired
    private QcMonitorMapper qcMonitorMapper;   

    @Override
    public void loadPage(QcMonitorDto dto) {
        int totalRecords = qcMonitorMapper.countByCondition(dto);
        dto.setTotalRecords(totalRecords);
        dto.setDataList(this.listByCondition(dto));
    }

    @Override
    public void batchAddInQcMonitor(List<QcMonitor> list) {
        qcMonitorMapper.batchAddInQcMonitor(list);
    }

    @Override
    public void add(QcMonitor obj) {
        qcMonitorMapper.add(obj);
    }

    @Override
    public void delete(Integer id) {
        
    }

    @Override
    public void update(QcMonitor obj) {

    }

    @Override
    public QcMonitor get(Integer id) {
        return null;
    }

    @Override
    public List<QcMonitor> list(QcMonitorDto dto) {
        return null;
    }

    @Override
    public List<QcMonitorDto> listAll(QcMonitorDto dto) {
        return qcMonitorMapper.listAll(dto);
    }

    @Override
    public List<QcMonitorDto> listByCondition(QcMonitorDto dto) {
        return qcMonitorMapper.listByCondition(dto);
    }
}
