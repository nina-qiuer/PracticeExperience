package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ReasonTypeDetailDao;
import com.tuniu.gt.complaint.entity.ReasonTypeEntity;
import com.tuniu.gt.complaint.service.IReasonTypeDetailService;
@Service("complaint_service_complaint_impl-reason_type_detail")
public class ReasonTypeDetailServiceImpl extends ServiceBaseImpl<ReasonTypeDetailDao> implements IReasonTypeDetailService {
	@Autowired
	@Qualifier("complaint_dao_impl-reason_type_detail")
	public void setDao(ReasonTypeDetailDao dao) {
		this.dao = dao;
	}

	//构建投诉事宜分类明细树
	@Override
	public List<JSONObject> buildDetailTree(){
		List<JSONObject> result = new ArrayList<JSONObject>();
		List<ReasonTypeEntity> reasonTypeList=dao.getReasonTypeDetailList();
		result=buildDetailTreeByfatherId(reasonTypeList,0);
		return result;
	}
	
	/**
	 * 递归构建投诉事宜分类明细 
	 * @param reasonTypeList
	 * @param fatherId
	 * @return
	 */
	public List<JSONObject> buildDetailTreeByfatherId(List<ReasonTypeEntity> reasonTypeList,Integer fatherId){
		List<JSONObject> jsonObjectList=new ArrayList<JSONObject>();
		for (int i = 0; i < reasonTypeList.size(); i++) {
			ReasonTypeEntity reasonTypeEntity=reasonTypeList.get(i);
			//递归出口
			if(fatherId.equals(reasonTypeEntity.getFatherId())){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", reasonTypeEntity.getId());
				jsonObject.put("name", reasonTypeEntity.getName());
				//执行递归
				jsonObject.put("child", buildDetailTreeByfatherId(reasonTypeList,reasonTypeEntity.getId()));
				jsonObjectList.add(jsonObject);
			}
		}
		return jsonObjectList;
	}
}
