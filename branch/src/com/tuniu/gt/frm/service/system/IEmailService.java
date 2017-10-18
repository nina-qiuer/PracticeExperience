package com.tuniu.gt.frm.service.system;

import java.util.List;

import com.tuniu.gt.complaint.entity.ComplaintEntity;

public interface IEmailService{
	
	// 获取收件人列表
	List<String> getReceiverEmails(ComplaintEntity entity);
}
