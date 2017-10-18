package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.ReasonTypeEntity;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-reason_type_detail")
public interface IReasonTypeDetailMap extends IMapBase {

	//获取所有的投诉事宜分类明细
	List<ReasonTypeEntity> getReasonTypeDetailList();
}
