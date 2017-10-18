package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintCompleteCountMap;
import com.tuniu.gt.complaint.entity.ComplaintCompleteCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintCompleted;
import com.tuniu.gt.complaint.vo.AfterSaleReportVo;

@Repository("complaint_dao_impl-complaint_complete_count")
public class ComplaintCompleteCountDao extends
		DaoBase<ComplaintCompleteCountEntity, IComplaintCompleteCountMap>
		implements IComplaintCompleteCountMap {
	public ComplaintCompleteCountDao() {
		tableName = Constant.appTblPreMap.get("complaint")
				+ "complaint_complete_count";
	}

	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_complete_count")
	public void setMapper(IComplaintCompleteCountMap mapper) {
		this.mapper = (IComplaintCompleteCountMap) mapper;
	}

	@Override
	public List<AfterSaleReportVo> getComplaintCompleteCount(
			Map<String, Object> paramMap) {
		return mapper.getComplaintCompleteCount(paramMap);
	}

	@Override
	public List<Integer> getCompleteCmpOrderList(Map<String, Object> paramMap) {
		return mapper.getCompleteCmpOrderList(paramMap);
	};

	@Override
	public void updateDepartmentByUserId(){
		mapper.updateDepartmentByUserId();
	}
	
	@Override
	public void insertComplaintIdByOrderId(Map<String, Object> paramMap){
		mapper.insertComplaintIdByOrderId(paramMap);
	};
	
	@Override
	public List<ComplaintCompleteCountEntity> getCompleteCountByWorknum(Map<String, Object> paramMap){
		return mapper.getCompleteCountByWorknum(paramMap);
	}
	
	@Override
	public ComplaintCompleted getDataByOrderIdAndDepart(Map<String, Object> paramMap){
		return mapper.getDataByOrderIdAndDepart(paramMap);
	}
	
	@Override
	public void insertOrUpdate(Map<String, Object> paramMap){
		mapper.insertOrUpdate(paramMap);
	}
}
