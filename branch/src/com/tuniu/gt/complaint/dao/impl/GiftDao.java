package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IGiftMap;
import com.tuniu.gt.complaint.entity.GiftEntity;

@Repository("complaint_dao_impl-gift")
public class GiftDao extends DaoBase<GiftEntity, IGiftMap> implements IGiftMap {
	public GiftDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "gift";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-gift")
	public void setMapper(IGiftMap mapper) {
		this.mapper = mapper;
	}

	public List<GiftEntity> searchByComplaintId(int id) {
		return mapper.searchByComplaintId(id);
	}
	
	public void insertList(Integer giftNoteId, List<GiftEntity> list) {
		for (GiftEntity entity : list) {
			entity.setGiftNoteId(giftNoteId);
			super.insert(entity);
		}
	}

	@Override
	public void deleteByGiftNoteId(Integer giftNoteId) {
		mapper.deleteByGiftNoteId(giftNoteId);
	}

	@Override
	public void updateGiftNoteId(Map<String, Object> params) {
		mapper.updateGiftNoteId(params);
	}
	
}
