package com.tuniu.gt.innerqc.dao.sqlmap.imap;


import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.innerqc.entity.InnerQcDutyEntity;


@Repository("innerqc_dao_sqlmap-inner_qc_duty")
public interface InnerQcDutyMap extends IMapBase {
	
	List<InnerQcDutyEntity> getDutyList(InnerQcDutyEntity entity);
	
	void deleteByQuestionId(int questionId);
	
}
