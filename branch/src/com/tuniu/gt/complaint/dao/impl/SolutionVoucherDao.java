package com.tuniu.gt.complaint.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.ISolutionVoucherMap;
import com.tuniu.gt.complaint.entity.SolutionVoucherEntity;

@Repository("complaint_dao_impl-solution_voucher")
public class SolutionVoucherDao extends DaoBase<SolutionVoucherEntity, ISolutionVoucherMap>  implements ISolutionVoucherMap {
	public SolutionVoucherDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "solution_voucher";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-solution_voucher")
	public void setMapper(ISolutionVoucherMap mapper) {
		this.mapper = mapper;
	}
	
	public void insertList(Integer solutionId, List<SolutionVoucherEntity> list) {
		for (SolutionVoucherEntity entity : list) {
			entity.setSolutionId(solutionId);
			super.insert(entity);
		}
	}

	@Override
	public void deleteBySolutionId(Integer solutionId) {
		mapper.deleteBySolutionId(solutionId);
	}
	
}
