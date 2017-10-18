package com.tuniu.gt.complaint.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IShareSolutionMap;
import com.tuniu.gt.complaint.entity.AgencyAppealEntity;
import com.tuniu.gt.complaint.entity.ComplaintQualityToolEntity;
import com.tuniu.gt.complaint.entity.EmployeeShareEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;

@Repository("complaint_dao_impl-share_solution")
public class ShareSolutionDao extends DaoBase<ShareSolutionEntity, IShareSolutionMap>  implements IShareSolutionMap {
	public ShareSolutionDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "share_solution";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-support_share")
	private SupportShareDao supportShareDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-employee_share")
	private EmployeeShareDao employeeShareDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_quality_tool")
	private ComplaintQualityToolDao qualityToolDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-agency_appeal")
	private AgencyAppealDao appealDao;
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-share_solution")
	public void setMapper(IShareSolutionMap mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getConfirmInfoNum(int agencyId) {
		return mapper.getConfirmInfoNum(agencyId);
	}

	@Override
	public List<Map<String, Object>> getAgencyPayInfoList(Map<String, Object> paramMap) {
		List<Map<String, Object>> dataList = mapper.getAgencyPayInfoList(paramMap);
		for (Map<String, Object> map : dataList) {
			Map<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("agencyId", (Integer) map.get("agencyId"));
			requestMap.put("complaintId", (Integer) map.get("complaintId"));
			AgencyAppealEntity appealInfo = appealDao.getAppealInfo(requestMap);
			map.put("appealInfo", appealInfo);
		}
		return dataList;
	}

	@Override
	public Map<String, Object> getAgencyPayInfoDetail(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = mapper.getAgencyPayInfoDetail(paramMap);
		AgencyAppealEntity appealInfo = appealDao.getAppealInfo(paramMap);
		resultMap.put("appealInfo", appealInfo);
		return resultMap;
	}

	@Override
	public int getAgencyPayoutNum(Map<String, Object> paramMap) {
		return mapper.getAgencyPayoutNum(paramMap);
	}

	@Override
	public void confirmPayout(Map<String, Object> paramMap) {
		mapper.confirmPayout(paramMap);
	}

	@Override
	public void appealPayout(Map<String, Object> paramMap) {
		mapper.appealPayout(paramMap);
	}
	
	public void logicDel(ShareSolutionEntity entity) {
		List<SupportShareEntity> ssList = entity.getSupportShareList();
		if (null != ssList) { // 删除供应商赔付相关数据
			for (SupportShareEntity ss : ssList) {
				supportShareDao.logicDel(ss);
			}
		}
		
		List<EmployeeShareEntity> esList = entity.getEmployeeShareList();
		if (null != esList) { // 删除员工承担相关数据
			for (EmployeeShareEntity es : esList) {
				es.setDelFlag(0);
				employeeShareDao.update(es);
			}
		}
		
		List<ComplaintQualityToolEntity> qtList = entity.getQualityToolList();
		if (null != qtList) { // 删除质量工具承担相关数据
			for (ComplaintQualityToolEntity qt : qtList) {
				qt.setDelFlag(1);
				qualityToolDao.update(qt);
			}
		}
		
		entity.setDelFlag(0);
		super.update(entity);
	}
	
	public void insertShareSolution(ShareSolutionEntity entity) {
		super.insert(entity);
		Integer shareId = entity.getId();
		supportShareDao.insertList(entity.getComplaintId(), shareId, entity.getSupportShareList());
		employeeShareDao.insertList(shareId, entity.getEmployeeShareList());
		qualityToolDao.insertList(shareId, entity.getQualityToolList());
	}
	
	public Map<String, Object> getDataForRepair(Integer complaintId) {
		return mapper.getDataForRepair(complaintId);
	}

	@Override
	public Map<String, Object> getOrderIndemnityShareInfo(Integer orderId) {
		return mapper.getOrderIndemnityShareInfo(orderId);
	}
	
	public List<Integer> getShareSolution(Map<String, Object> map){
		
		return mapper.getShareSolution(map);
	}
	
	public List<Map<String, Object>> getCmpSpecialByCmpId(
			Map<String, Object> map) {
		
		return mapper.getCmpSpecialByCmpId(map);
	}

	@Override
	public List<Map<String, Object>> getQualityToolCost(String dateStr) {
		return mapper.getQualityToolCost(dateStr);
	}

	@Override
	public List<Map<String, Object>> getUnGroupCost(String dateStr) {
		return mapper.getUnGroupCost(dateStr);
	}
	
}
