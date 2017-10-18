package com.tuniu.qms.report.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.report.dao.QcPunishReportMapper;
import com.tuniu.qms.report.dto.QcPunishReportDto;
import com.tuniu.qms.report.model.QcPunishReport;
import com.tuniu.qms.report.service.QcPunishReportService;

@Service
public class QcPunishReportServiceImpl implements QcPunishReportService{
	
	@Autowired
	private QcPunishReportMapper mapper;
	
	@Override
	public void add(QcPunishReport obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
	}

	@Override
	public void update(QcPunishReport obj) {
		
	}

	@Override
	public QcPunishReport get(Integer id) {
		return null;
	}

	@Override
	public List<QcPunishReport> list(QcPunishReportDto dto) {
		return null;
	}

	@Override
	public void loadPage(QcPunishReportDto dto) {
		
	}

	@Override
	public List<Map<String, Object>> getGraphByQcType(QcPunishReportDto dto) {
		List<Map<String, Object>> topList = getTopList(mapper.getGraphByQcType(dto));
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByDep(QcPunishReportDto dto) {
		List<Map<String, Object>> topList = getTopList(mapper.getGraphByDep(dto));
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByJob(QcPunishReportDto dto) {
		List<Map<String, Object>> topList = getTopList(mapper.getGraphByJob(dto));
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getGraphByPunishPerson(QcPunishReportDto dto) {
		List<Map<String, Object>> topList = getTopList(mapper.getGraphByPunishPerson(dto));
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getscoreSumTrendGrah(QcPunishReportDto dto) {
		return mapper.getscoreSumTrendGrah(dto);
	}

	@Override
	public List<Map<String, Object>> getPunishTimeTrendGrah(QcPunishReportDto dto) {
		return mapper.getPunishTimeTrendGrah(dto);
	}

	@Override
	public List<Map<String, Object>> getPunishTimeByQcType(QcPunishReportDto dto) {
		List<Map<String, Object>> topList = getTopListInt(mapper.getPunishTimeByQcType(dto));
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getPunishTimeByDep(QcPunishReportDto dto) {
		List<Map<String, Object>> topList = getTopListInt(mapper.getPunishTimeByDep(dto));
		
		return topList;
	}

	@Override
	public List<Map<String, Object>> getPunishTimeByPunPerson(QcPunishReportDto dto) {
		List<Map<String, Object>> topList = getTopListInt(mapper.getPunishTimeByPunPerson(dto));
		
		return topList;
	}
	
	/**
	 * int 型
	 * @param punishTimeByPunPerson
	 * @return
	 */
	private List<Map<String, Object>> getTopListInt(List<Map<String, Object>> list) {
		List<Map<String, Object>> topList = new ArrayList<Map<String,Object>>();
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
				map.put("qcTypeName", "其他");
				map.put("num",num);
				map.put("rank",0);
				topList.add(0,map);
				
			}else{
				
				topList = list;
			}
			
		}
		return topList;
	}

	/**
	 * 超过15条，15条之后合并为一条
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> getTopList(List<Map<String, Object>> list) {
		List<Map<String, Object>> topList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		if(null!=list && list.size()>0){
			if(list.size()>15){ //超过15条 设置其他
				BigDecimal num = new BigDecimal("0");
				topList = list.subList(list.size()-15, list.size());
				for(int i =0 ;i<list.size()-15;i++){
					Map<String, Object> otherMap = list.get(i);
					BigDecimal otherNum =  (BigDecimal) otherMap.get("num");
					num = num.add(otherNum);
				}
				map.put("qcTypeName", "其他");
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
	public void deleteByQcId(Integer qcId) {
		mapper.deleteByQcId(qcId);
	}

	

}
