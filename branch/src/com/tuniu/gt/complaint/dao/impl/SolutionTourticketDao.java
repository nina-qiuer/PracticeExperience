package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ISolutionTourticketMap;
import com.tuniu.gt.complaint.entity.SolutionTourticketEntity;

@Repository("complaint_dao_impl-solution_tourticket")
public class SolutionTourticketDao extends DaoBase<SolutionTourticketEntity, ISolutionTourticketMap>  implements ISolutionTourticketMap {
	public SolutionTourticketDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "solution_tourticket";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-solution_tourticket")
	public void setMapper(ISolutionTourticketMap mapper) {
		this.mapper = mapper;
	}
	
	public void insertList(Integer solutionId, List<SolutionTourticketEntity> list) {
		for (SolutionTourticketEntity entity : list) {
			entity.setSolutionId(solutionId);
			super.insert(entity);
		}
	}
	
	public void deleteBySolutionId(Integer solutionId) {
		mapper.deleteBySolutionId(solutionId);
	}
	
}
