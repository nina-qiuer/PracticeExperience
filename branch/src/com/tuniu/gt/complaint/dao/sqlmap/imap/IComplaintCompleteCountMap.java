package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.ComplaintCompleteCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintCompleted;
import com.tuniu.gt.complaint.vo.AfterSaleReportVo;

@Repository("complaint_dao_sqlmap-complaint_complete_count")
public interface IComplaintCompleteCountMap extends IMapBase{
	List<AfterSaleReportVo> getComplaintCompleteCount(
			Map<String, Object> paramMap);
	
	List<Integer> getCompleteCmpOrderList(Map<String, Object> paramMap);

	void updateDepartmentByUserId();
	
	void insertComplaintIdByOrderId(Map<String, Object> paramMap);
	
	List<ComplaintCompleteCountEntity> getCompleteCountByWorknum(Map<String, Object> paramMap);
	
	ComplaintCompleted getDataByOrderIdAndDepart(Map<String, Object> paramMap);
	
	void insertOrUpdate(Map<String, Object> paramMap);
}
