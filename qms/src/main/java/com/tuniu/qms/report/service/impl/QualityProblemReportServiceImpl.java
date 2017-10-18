package com.tuniu.qms.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.report.dao.QualityProblemReportMapper;
import com.tuniu.qms.report.dto.QualityProblemReportDto;
import com.tuniu.qms.report.model.QualityProblemReport;
import com.tuniu.qms.report.service.QualityProblemReportService;

@Service
public class QualityProblemReportServiceImpl implements QualityProblemReportService {

	@Autowired QualityProblemReportMapper mapper;
	
	@Override
	public void add(QualityProblemReport obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {

		mapper.delete(id);
	}

	@Override
	public void update(QualityProblemReport obj) {

		mapper.update(obj);
	}

	@Override
	public QualityProblemReport get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<QualityProblemReport> list(QualityProblemReportDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QualityProblemReportDto dto) {

		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
	}

	@Override
	public void deleteByQcId(Integer qcId) {

		
		mapper.deleteByQcId(qcId);
	}

	@Override
	public List<Map<String, Object>> getGraphByQpType(QualityProblemReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getGraphByQpType(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num = 0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByDep(QualityProblemReportDto dto) {

		List<Map<String, Object>> list = mapper.getGraphByDep(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>12){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-12, list.size());
				for(int i =0 ;i<list.size()-12;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByAgency(QualityProblemReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getGraphByAgency(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByResp(QualityProblemReportDto dto) {
	
		List<Map<String, Object>> list = mapper.getGraphByResp(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByJob(QualityProblemReportDto dto) {

		List<Map<String, Object>> list = mapper.getGraphByJob(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getTrendGraph(
			QualityProblemReportDto dto) {
		
		return mapper.getTrendGraph(dto);
	}

	@Override
	public List<Map<String, Object>> getRankGraphImplementQp(
			QualityProblemReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getRankGraphImplementQp(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getRankGraphAgencyQp(
			QualityProblemReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getRankGraphAgencyQp(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getRankGraphDep(QualityProblemReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getRankGraphDep(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getRankGraphAgency(
			QualityProblemReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getRankGraphAgency(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getRankGraphInnerResp(
			QualityProblemReportDto dto) {
		
		
		List<Map<String, Object>> list = mapper.getRankGraphInnerResp(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getRankGraphOutResp(
			QualityProblemReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getRankGraphOutResp(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过12条 设置其他
				long num =0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					long otherNum =  (Long) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}




}
