package com.tuniu.gt.complaint.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IQcMap;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.toolkit.CommonUtil;

@Repository("complaint_dao_impl-qc")
public class QcDao extends DaoBase<QcEntity, IQcMap> implements IQcMap {
	public QcDao() {
		tableName = Constant.appTblPreMap.get("complaint") + "qc";
	}

	@Autowired
	@Qualifier("complaint_dao_sqlmap-qc")
	public void setMapper(IQcMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<?> fetchList(Map<String, Object> paramMap) {
		return mapper.search(paramMap);
	}

	@Override
	public List<?> fetchListMap(Map<String, Object> paramMap) {
		return mapper.searchMap(paramMap);
	}

	public QcEntity searchById(int id) {
		return mapper.searchById(id);
	}
	
	public int insert(QcEntity qcEntity) {
		return super.insert(qcEntity);
	}
	
	public List<QcEntity> getQcListByState(int status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		return mapper.search(params);
	}
	
	public List<QcEntity> search(Map<String,Object> paramMap) {
		return mapper.search(paramMap);
	}
	
	public List<Map<String, Object>> searchMap(Map<String, Object> paramMap) {
		return mapper.searchMap(paramMap);
	}

	public int updateByOrderId(QcEntity updateOrderId) {
		return mapper.updateByOrderId(updateOrderId);
	}

	public List<Map<String, Object>> searchByName(Map<String, Object> paramMap) {
		return mapper.searchByName(paramMap);
	}
	
	public void updateByComplaintId(QcEntity qcEntity) {
		mapper.updateByComplaintId(qcEntity);
	}

	public void updateSpecialConsultationByIds(Map map) {
		mapper.updateSpecialConsultationByIds(map);
		
	}
	
	public void updateConsultationById(Map map){
		mapper.updateConsultationById(map);
	}

	@Override
	public List<Map<String, Object>> getExportData(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.getExportData(paramMap);
		for (Map<String, Object> map : list) {
			String cmpLevel = String.valueOf(map.get("投诉问题等级"));
			if ("-1".equals(cmpLevel)) {
				map.put("投诉问题等级", "");
			}
			
			String response1 = String.valueOf(map.get("response1"));
			String response2 = String.valueOf(map.get("response2"));
			String response3 = String.valueOf(map.get("response3"));
			Object responsibility = map.get("responsibility");
			if (null != responsibility && "".equals(String.valueOf(responsibility))) {
				int resp1 = (Integer) responsibility;
				switch (resp1) {
				case 1:
					response1 = "不可抗力";
					break;
				case 2:
					response1 = "系统/流程问题";
					break;
				case 14:
					response1 = "其他";
					break;
				default:
					break;
				}
			}
			StringBuffer response = new StringBuffer();
			if (!"null".equals(response1) && !"".equals(response1)) {
				response.append(response1);
			}
			if (!"null".equals(response2) && !"".equals(response2)) {
				response.append("/").append(response2);
			}
			if (!"null".equals(response3) && !"".equals(response3)) {
				response.append("/").append(response3);
			}
			map.put("责任归属", response.toString());
			
			String position = (String) CommonUtil.getPositionMap().get(String.valueOf(map.get("position")));
			map.put("执行岗位", position);
			
			long totalValue = 0;
			if (null != map.get("记分值1")) {
				totalValue += (Long) map.get("记分值1");
			}
			if (null != map.get("记分值2")) {
				totalValue += (Long) map.get("记分值2");
			}
			map.put("总记分", totalValue);
		}
		return list;
	}

	@Override
	public int getExportDataTotal(Map<String, Object> paramMap) {
		return mapper.getExportDataTotal(paramMap);
	}
}
