package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Map;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-support_share")
public interface ISupportShareMap extends IMapBase { 

	/**
	 * 供应商赔付确认到期自动确认
	 */
	void confirmPayoutAuto();
	
	void deleteByShareId(Integer shareId);
	
	void updateByCodeAndCompId(Map<String, Object> params);

}
