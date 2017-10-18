package com.tuniu.gt.innerqc.service;

import java.io.File;

import tuniu.frm.core.IServiceBase;

public interface InnerQcAttachService extends IServiceBase {
	
	void addAttach(Integer iqcId, File file, String fileName);
	
}
