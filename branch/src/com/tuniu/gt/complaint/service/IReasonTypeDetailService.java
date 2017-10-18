package com.tuniu.gt.complaint.service;

import java.util.List;

import net.sf.json.JSONObject;
import tuniu.frm.core.IServiceBase;

public interface IReasonTypeDetailService extends IServiceBase {
	
	//构建投诉事宜分类明细树
	List<JSONObject> buildDetailTree();
}
