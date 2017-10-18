package com.tuniu.qms.qc.service;

import org.springframework.web.multipart.MultipartFile;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qc.dto.UpLoadAttachDto;
import com.tuniu.qms.qc.model.UpLoadAttach;

/**
 * 
 * @author chenhaitao
 *
 */
public interface UpLoadService extends BaseService<UpLoadAttach, UpLoadAttachDto>{
	
	/**
	 * 导入记分，保存记分处罚记录
	 * @param mf
	 * @param user
	 */
	void saveScoreRecord(MultipartFile mf, User user) throws Exception;
	
}
