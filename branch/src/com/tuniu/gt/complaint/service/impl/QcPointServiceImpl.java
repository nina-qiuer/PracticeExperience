package com.tuniu.gt.complaint.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcPointMapper;
import com.tuniu.gt.complaint.entity.QcPointEntity;
import com.tuniu.gt.complaint.service.IQcPointService;

@Service("qcPointService")
public class QcPointServiceImpl implements IQcPointService{

	private static Logger logger = Logger.getLogger(QcPointServiceImpl.class);

	@Autowired
	private IQcPointMapper qcPointMapper;
	
	
    /**
   * 新增质检点
   */
	public  int  saveQcPoint(QcPointEntity entity) {
		
		try {
			
			qcPointMapper.saveQcPoint(entity);
			return 0;
		} catch (Exception e) {
			logger.error(e.getMessage()+"新增质检点失败",e);
			return 1;
		}
		
	}


	@Override
	public QcPointEntity getQcPoint(Map<String, Object> map) {
		
		return qcPointMapper.getQcPoint(map);
	}


	@Override
	public int updateQcPoint(QcPointEntity qcEntity) {
		

		try {
			
			qcPointMapper.updateQcPoint(qcEntity);
			return 0;
		} catch (Exception e) {
			logger.error(e.getMessage()+"修改质检点失败",e);
			return 1;
		}
		
	}
	
	public int revokeQcPoint(QcPointEntity qcEntity) {
		

		try {
			
			qcPointMapper.updateQcPoint(qcEntity);
			qcPointMapper.updateAttach(qcEntity.getComplaintId());//逻辑删除附件
			return 0;
		} catch (Exception e) {
			logger.error(e.getMessage()+"撤销质检点失败",e);
			return 1;
		}
		
	}


	@Override
	public void deleteQcPoint(QcPointEntity qcEntity) {
		
		qcPointMapper.deleteQcPoint(qcEntity.getId());
		qcPointMapper.deleteAttach(qcEntity.getComplaintId());
	}

	
	
}
