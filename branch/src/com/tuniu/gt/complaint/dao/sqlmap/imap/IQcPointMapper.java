package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.QcPointEntity;

@Repository
public interface IQcPointMapper {

	public void  saveQcPoint(QcPointEntity entity);
	
	public QcPointEntity getQcPoint(Map<String, Object> map);
	
	public  void updateQcPoint(QcPointEntity entity);
	
	public void updateAttach(Integer complaintId);
	
	public void deleteQcPoint(Integer id);
	
	public void deleteAttach(Integer complaintId);
}
