package com.tuniu.bi.gmvrank.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.bi.gmvrank.dao.sqlmap.imap.IGMVRankMap;
import com.tuniu.bi.gmvrank.entity.GMVRankEntity;
import com.tuniu.bi.prdweeksatisfy.dao.sqlmap.imap.IPrdWeekSatisfyMap;
import com.tuniu.bi.secondarydep.dao.sqlmap.imap.ISecondaryDepMap;

@Repository("bi_dao_impl-GMVRank")
public class GMVRankDao extends DaoBase<GMVRankEntity, IGMVRankMap> implements IGMVRankMap {

	@Autowired
	@Qualifier("bi_dao_sqlmap-GMVRank")
	public void setMapper(IGMVRankMap mapper) {
		this.mapper = mapper;
	}
}
