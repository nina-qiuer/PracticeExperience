package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.GiftEntity;

@Repository("complaint_dao_sqlmap-gift")
public interface IGiftMap extends IMapBase { 

	public List<GiftEntity> searchByComplaintId(int id);
	
	void deleteByGiftNoteId(Integer giftNoteId);
	
	void updateGiftNoteId(Map<String, Object> params);
	
}
