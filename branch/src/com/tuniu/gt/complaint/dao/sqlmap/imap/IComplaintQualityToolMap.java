package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.ComplaintQualityToolEntity;
import com.tuniu.gt.complaint.entity.QualityToolEntity;

@Repository("complaint_dao_sqlmap-complaint_quality_tool")
public interface IComplaintQualityToolMap extends IMapBase {

	/**
	 * 根据投诉单号和分担号取质量工具
	 * @param complaintId 投诉单号
	 * @param shareId 分单号
	 * @return List<QualityToolEntity>
	 */
	public List<QualityToolEntity> searchById(int complaintId, int shareId);

	/**
	 * 根据分担方案号取投诉单与质量工具关联表信息
	 * @param solutionId
	 * @return List<ComplaintQualityToolEntity>
	 */
	public List<ComplaintQualityToolEntity> getQualityToolListBySolutionId(int solutionId);
	
	void deleteByShareId(Integer shareId);

}
