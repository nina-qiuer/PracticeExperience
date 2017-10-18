package com.tuniu.gt.innerqc.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.innerqc.dao.sqlmap.imap.InnerQcDutyMap;
import com.tuniu.gt.innerqc.entity.InnerQcDutyEntity;
import com.tuniu.gt.toolkit.CommonUtil;


@Repository("innerqc_dao_impl-inner_qc_duty")
public class InnerQcDutyDao extends DaoBase<InnerQcDutyEntity, InnerQcDutyMap> implements InnerQcDutyMap {

	public InnerQcDutyDao() {
		tableName = "qc_inner_qc_duty";
	}

	@Autowired
	@Qualifier("innerqc_dao_sqlmap-inner_qc_duty")
	public void setMapper(InnerQcDutyMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<InnerQcDutyEntity> getDutyList(InnerQcDutyEntity entity) {
		List<InnerQcDutyEntity> dutyList = mapper.getDutyList(entity);
		if (null != dutyList && dutyList.size() > 0) {
			for (InnerQcDutyEntity duty : dutyList) {
				String position = (String) CommonUtil.getPositionMap().get(String.valueOf(duty.getPositionId()));
				duty.setPositionName(position);
				switch (duty.getDepId1()) {
				case 1:
					duty.setDepName1("不可抗力");
					break;
				case 2:
					duty.setDepName1("系统/流程问题");
					break;
				case 14:
					duty.setDepName1("其他");
					break;
				default:
					break;
				}
			}
		}
		return dutyList;
	}

	@Override
	public void deleteByQuestionId(int questionId) {
		mapper.deleteByQuestionId(questionId);
	}

}
