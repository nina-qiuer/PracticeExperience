package com.tuniu.gt.satisfaction.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.satisfaction.dao.sqlmap.imap.ISignSatisfactionMsgSendMap;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionMsgSendEntity;

@Repository("satisfaction_dao_impl-signSatisfactionMsgSend")
public class SignSatisfactionMsgSendDao  extends DaoBase<SignSatisfactionMsgSendEntity, ISignSatisfactionMsgSendMap>  implements ISignSatisfactionMsgSendMap {

	public SignSatisfactionMsgSendDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "satisfaction_send_record";		
	}
	
	@Autowired
	@Qualifier("satisfaction_dao_sqlmap-signSatisfactionMsgSend")
	public void setMapper(ISignSatisfactionMsgSendMap mapper) {
		this.mapper = mapper;
	}

}
