package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;

@Repository("complaint_dao_sqlmap-complaint_solution")
public interface IComplaintSolutionMap extends IMapBase {
	
	ComplaintSolutionEntity getByComplaintId(Integer complaintId);
	
	List<ComplaintSolutionEntity> getNeedRepairList(int scope);
	
	List<ComplaintSolutionEntity>  getCmpSolutionByCmpId(Map<String, Object> map);

	/**
	 * 获取需要同步的对客解决方案
	 * @param map
	 * @return
	 */
	List<ComplaintSolutionEntity> getNeedSynchSolution(Map<String, Object> map);
	
	/**
	 * 通过id更新对客解决方案银行信息
	 * @param complaintSolutionEntity
	 */
	void updateCardUniqueId(Map<String, Object> map);
	
	/**
	 * 根据财务现金id获取对客解决方案
	 * @param paramMap
	 * @return
	 */
	List<ComplaintSolutionEntity> getComplaintIdByReFundId(Map<String, Object> paramMap);
}
