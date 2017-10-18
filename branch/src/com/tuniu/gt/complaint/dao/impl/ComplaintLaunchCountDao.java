package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintLaunchCountMap;
import com.tuniu.gt.complaint.entity.ComplaintLaunchCountEntity;
import com.tuniu.gt.complaint.entity.ComplaintLaunchEntity;

@Repository("complaint_dao_impl-complaint_launch_count")
public class ComplaintLaunchCountDao extends
		DaoBase<ComplaintLaunchCountEntity, IComplaintLaunchCountMap>
		implements IComplaintLaunchCountMap {
	public ComplaintLaunchCountDao() {
		tableName = Constant.appTblPreMap.get("complaint")
				+ "complaint_launch_count";
	}

	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_launch_count")
	public void setMapper(IComplaintLaunchCountMap mapper) {
		this.mapper = (IComplaintLaunchCountMap) mapper;
	}

	@Override
	public void insertComplaintLaunchByDay(Map<String, Object> paramMap) {
		mapper.insertComplaintLaunchByDay(paramMap);
	}
	
	@Override
	public List<ComplaintLaunchCountEntity> getComplaintLaunchCountList(Map<String, Object> paramMap){
		return mapper.getComplaintLaunchCountList(paramMap);
	}
	
	@Override
	public Integer getComplaintLaunchCountNum(Map<String, Object> paramMap){
		return mapper.getComplaintLaunchCountNum(paramMap);
	}
	
	@Override
	public void updateDepartmentToReportOne(){
		mapper.updateDepartmentToReportOne();
	}
	
	@Override
	public void updateDepartmentToReportTwo(){
		mapper.updateDepartmentToReportTwo();
	}
	
	@Override
	public void updateDepartmentToReportThree(){
		mapper.updateDepartmentToReportThree();
	}
	
	@Override
	public List<ComplaintLaunchEntity> getComplaintLaunchList(Map<String, Object> paraMap){
		return mapper.getComplaintLaunchList(paraMap);
	}
	
	@Override
	public Integer getComplaintLaunchListCount(Map<String, Object> paraMap){
		return mapper.getComplaintLaunchListCount(paraMap);
	}
}
