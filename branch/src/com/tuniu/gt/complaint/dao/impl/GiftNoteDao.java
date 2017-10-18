package com.tuniu.gt.complaint.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IGiftNoteMap;
import com.tuniu.gt.complaint.entity.GiftEntity;
import com.tuniu.gt.complaint.entity.GiftNoteEntity;

@Repository("complaint_dao_impl-gift_note")
public class GiftNoteDao extends DaoBase<GiftNoteEntity, IGiftNoteMap>  implements IGiftNoteMap {
	public GiftNoteDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "gift_note";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-gift_note")
	public void setMapper(IGiftNoteMap mapper) {
		this.mapper = mapper;
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-gift")
	private GiftDao giftDao;
	
	public void insertList(Integer solutionId, List<GiftNoteEntity> list) {
		for (GiftNoteEntity entity : list) {
			entity.setSolutionId(solutionId);
			super.insert(entity);
			giftDao.insertList(entity.getId(), entity.getGiftList());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<GiftNoteEntity> getListBySolutionId(Map<String, Object> paramMap) {
		List<GiftNoteEntity> list = (List<GiftNoteEntity>) super.fetchList(paramMap);
		
		Map<String, Object> params = new HashMap<String, Object>();
		for (GiftNoteEntity giftNote : list) {
			params.put("giftNoteId", giftNote.getId());
			giftNote.setGiftList((List<GiftEntity>) giftDao.fetchList(params));
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteBySolutionId(Integer solutionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("solutionId", solutionId);
		List<GiftNoteEntity> list = (List<GiftNoteEntity>) super.fetchList(params);
		for (GiftNoteEntity giftNote : list) {
			giftDao.deleteByGiftNoteId(giftNote.getId());
		}
		mapper.deleteBySolutionId(solutionId);
	}
	
	public void logicDel(GiftNoteEntity entity) {
		List<GiftEntity> giftList = entity.getGiftList();
		if (null != giftList) {
			for (GiftEntity gift : giftList) {
				gift.setDelFlag(0);
				giftDao.update(gift);
			}
		}
		entity.setDelFlag(0);
		super.update(entity);
	}

	@Override
	public void updateSolutionId(Map<String, Object> params) {
		mapper.updateSolutionId(params);
	}
	
}
