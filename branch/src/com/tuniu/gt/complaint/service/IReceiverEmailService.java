package com.tuniu.gt.complaint.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;
import com.tuniu.gt.complaint.entity.ReceiverEmailEntity;
public interface IReceiverEmailService extends IServiceBase {
	
	/**
	* 根据类型获取不同的收件人列表
	* @param type
	* @return    
	* List<ReceiverEmailEntity>    
	* @throws
	*/
	public List<ReceiverEmailEntity> getListByType(int type, int receiverType,int orderState);

}
