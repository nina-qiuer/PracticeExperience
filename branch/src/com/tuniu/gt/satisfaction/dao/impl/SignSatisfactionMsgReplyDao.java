package com.tuniu.gt.satisfaction.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.satisfaction.dao.sqlmap.imap.ISignSatisfactionMsgReplyMap;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionMsgReplyEntity;

@Repository("satisfaction_dao_impl-signSatisfactionMsgReply")
public class SignSatisfactionMsgReplyDao  extends DaoBase<SignSatisfactionMsgReplyEntity, ISignSatisfactionMsgReplyMap>  implements ISignSatisfactionMsgReplyMap {

	public SignSatisfactionMsgReplyDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "satisfaction_reply_record";		
	}
	
	@Autowired
	@Qualifier("satisfaction_dao_sqlmap-signSatisfactionMsgReply")
	public void setMapper(ISignSatisfactionMsgReplyMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public void fixSatisfactionSocre(Map map) {
		mapper.fixSatisfactionSocre(map);
		
	}

}
