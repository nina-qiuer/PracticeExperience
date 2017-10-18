package com.tuniu.gt.satisfaction.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

@Repository("satisfaction_dao_sqlmap-signSatisfactionMsgReply")
public interface ISignSatisfactionMsgReplyMap extends IMapBase  {

	public void fixSatisfactionSocre(Map map);
}
