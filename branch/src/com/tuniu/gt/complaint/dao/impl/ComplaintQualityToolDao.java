package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintQualityToolMap;
import com.tuniu.gt.complaint.entity.ComplaintQualityToolEntity;
import com.tuniu.gt.complaint.entity.QualityToolEntity;

@Repository("complaint_dao_impl-complaint_quality_tool")
public class ComplaintQualityToolDao extends DaoBase<ComplaintQualityToolEntity, IComplaintQualityToolMap> implements IComplaintQualityToolMap {
	public ComplaintQualityToolDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_quality_tool";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_quality_tool")
	public void setMapper(IComplaintQualityToolMap mapper) {
		this.mapper = mapper;
	}
	/**
	 * 根据投诉单号和分担号取质量工具
	 * @param complaintId 投诉单号
	 * @param shareId 分单号
	 * @return List<QualityToolEntity>
	 */
	public List<QualityToolEntity> searchById(int complaintId, int shareId) {
		return mapper.searchById(complaintId, shareId);
	}
	
	/**
	 * 根据分担方案号取投诉单与质量工具关联表信息
	 * @param solutionId
	 * @return List<ComplaintQualityToolEntity>
	 */
	public List<ComplaintQualityToolEntity> getQualityToolListBySolutionId(
			int solutionId) {
		return mapper.getQualityToolListBySolutionId(solutionId);
	}
	
	public void insertList(Integer shareId, List<ComplaintQualityToolEntity> list) {
		for (ComplaintQualityToolEntity entity : list) {
			entity.setShareId(shareId);
			super.insert(entity);
		}
	}

	@Override
	public void deleteByShareId(Integer shareId) {
		mapper.deleteByShareId(shareId);
	}
	
}
