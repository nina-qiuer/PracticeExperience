package com.tuniu.qms.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.report.dao.QualityCostReportMapper;
import com.tuniu.qms.report.dto.QualityCostReportDto;
import com.tuniu.qms.report.model.QualityCostReport;
import com.tuniu.qms.report.service.QualityCostReportService;

@Service
public class QualityCostReportServiceImpl implements QualityCostReportService {

	
	@Autowired
	private QualityCostReportMapper mapper;
	@Override
	public void add(QualityCostReport obj) {
		
		mapper.add(obj);
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(QualityCostReport obj) {
		
		mapper.update(obj);
		
	}

	@Override
	public QualityCostReport get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<QualityCostReport> list(QualityCostReportDto dto) {
	
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QualityCostReportDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
		
	}

	@Override
	public List<Map<String, Object>> getGraphByQpType(QualityCostReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getGraphByQpType(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过15条 设置其他
				Double num =0.0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					Double otherNum = (Double) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",MathUtil.round(num,2));
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByDep(QualityCostReportDto dto) {

		List<Map<String, Object>> list = mapper.getGraphByDep(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>12){ //超过12条 设置其他
				Double num =0.0;
				topList = list.subList(list.size()-12, list.size());
				for(int i =0 ;i<list.size()-12;i++){
					
					Map<String, Object> otherMap = list.get(i);
					Double otherNum = (Double) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",MathUtil.round(num,2));
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByJob(QualityCostReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getGraphByJob(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过15条 设置其他
				Double num =0.0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					Double otherNum = (Double) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",MathUtil.round(num,2));
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByResp(QualityCostReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getGraphByResp(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过15条 设置其他
				Double num =0.0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					Double otherNum = (Double) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",MathUtil.round(num,2));
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getTrendGraph(QualityCostReportDto dto) {
		
		return mapper.getTrendGraph(dto);
	}

	@Override
	public List<Map<String, Object>> getRankGraphImplementQp(
			QualityCostReportDto dto) {

		List<Map<String, Object>> list = mapper.getRankGraphImplementQp(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过15条 设置其他
				Double num =0.0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					Double otherNum = (Double) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",MathUtil.round(num,2));
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getRankGraphDep(QualityCostReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getRankGraphDep(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过15条 设置其他
				Double num =0.0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					Double otherNum = (Double) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",MathUtil.round(num,2));
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getRankGraphResp(QualityCostReportDto dto) {
		
		List<Map<String, Object>> list = mapper.getRankGraphResp(dto);
		List<Map<String, Object>> topList =new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			
			if(list.size()>15){ //超过15条 设置其他
				Double num =0.0;
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					
					Map<String, Object> otherMap = list.get(i);
					Double otherNum = (Double) otherMap.get("num");
					num += otherNum;
				}
				map.put("qpTypeName", "其他");
				map.put("num",MathUtil.round(num,2));
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		
		return topList;
	}

	


}
