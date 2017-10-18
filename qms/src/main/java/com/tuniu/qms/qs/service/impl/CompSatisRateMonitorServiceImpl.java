package com.tuniu.qms.qs.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.bi.dao.DataMapper;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.qs.dao.CompSatisRateMonitorMapper;
import com.tuniu.qms.qs.dao.TargetConfigMapper;
import com.tuniu.qms.qs.dto.CompSatisRateMonitorDto;
import com.tuniu.qms.qs.model.CompSatisRateMonitor;
import com.tuniu.qms.qs.model.TargetConfig;
import com.tuniu.qms.qs.service.CompSatisRateMonitorService;

@Service
public class CompSatisRateMonitorServiceImpl implements CompSatisRateMonitorService {

	
	@Autowired
	private CompSatisRateMonitorMapper mapper;
	
	@Autowired
	private DataMapper dataMapper;
	
	 @Autowired
	 private MailTaskService mailTaskService;
	
	 @Autowired
	 private TargetConfigMapper targetMapper;
	
	@Override
	public void add(CompSatisRateMonitor obj) {
		
		mapper.add(obj);
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(CompSatisRateMonitor obj) {
		mapper.update(obj);
		
	}

	@Override
	public CompSatisRateMonitor get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<CompSatisRateMonitor> list(CompSatisRateMonitorDto dto) {
		
		List<CompSatisRateMonitor> list = mapper.list(dto);
		
		for(CompSatisRateMonitor statisfy : list){
			
			String quarter = String.valueOf(statisfy.getYearQuarter());//根据季度判断 目标值
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("01")){
				
				statisfy.setTargetValue(statisfy.getOneTargetValue());
			}
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("02")){
							
				statisfy.setTargetValue(statisfy.getTwoTargetValue());
			}
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("03")){
				
				statisfy.setTargetValue(statisfy.getThreeTargetValue());

			}if(quarter.substring(quarter.length()-2, quarter.length()).equals("04")){
				
				statisfy.setTargetValue(statisfy.getFourTargetValue());
			}
			if(statisfy.getOrdNum()!=0){
				
				statisfy.setCmpRate(MathUtil.div(statisfy.getCmpNum(), statisfy.getOrdNum(),4));//投诉率
			}else{
				
				statisfy.setCmpRate(0.0);
			}
		 
			if(statisfy.getCommentNum()!=0){
				
				statisfy.setSatisfaction(MathUtil.div(statisfy.getCommentScore(), statisfy.getCommentNum())/100);//满意度

			}else{
				
				statisfy.setSatisfaction(0.0);
			}
			statisfy.setCompSatisfaction(statisfy.getSatisfaction() -2*statisfy.getCmpRate());//综合满意度
			if(statisfy.getTargetValue()!=0){
				
				statisfy.setReacheRate(MathUtil.div(statisfy.getCompSatisfaction(),statisfy.getTargetValue()/100,4));//达成率

			}else{
				
				statisfy.setReacheRate(0.0);
			}
		}
		
	/*	if("prdManagerList".equals(dto.getUrl())){
			
			Collections.sort(list, new Comparator<CompSatisRateMonitor>()
					{
	                  
						public int compare(CompSatisRateMonitor arg0,
								CompSatisRateMonitor arg1) {
							if( arg1.getCompSatisfaction() - arg0.getCompSatisfaction() >=0){
								
								return 1 ;
							}else{
								
								return -1 ;
							}
						}
						
					});
			
		}else{*/
		//按照达成率排序
		Collections.sort(list, new Comparator<CompSatisRateMonitor>()
				{
                  
					public int compare(CompSatisRateMonitor arg0,
							CompSatisRateMonitor arg1) {
						if( arg1.getReacheRate() - arg0.getReacheRate() >=0){
							
							return 1 ;
						}else{
							
							return -1 ;
						}
					}
					
				});
			
	/*	}*/
		
		
		return list ;
	}

	@Override
	public void loadPage(CompSatisRateMonitorDto dto) {
		
		
	}

	@Override
	public void createCompStaisRateSnapshot() {

		try {
			
		java.sql.Date yesterday = DateUtil.getSqlYesterday();
		mapper.deleteByAddDate(yesterday);
		
		int pageSize = 2000;
		int dataLimitStart = 0;
		while (true) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yesterday", yesterday);
			map.put("dataLimitStart", dataLimitStart);
			map.put("pageSize", pageSize);
			List<CompSatisRateMonitor> caList = dataMapper.listCompStaisfy(map);
			if (!caList.isEmpty()) {
				
					mapper.addBatch(caList);
					
			} else {
				
				break;
			}
			
			dataLimitStart += pageSize;
		}
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}


	/**
	 * 同步全量数据
	 */
	public void createCompSatisfyAll() {
		
		try {
			
			int pageSize = 2000;
			int dataLimitStart = 0;
			while (true) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dataLimitStart", dataLimitStart);
				map.put("pageSize", pageSize);
				List<CompSatisRateMonitor> caList = dataMapper.listCompStaisfyAll(map);
				if (!caList.isEmpty()) {
					
						mapper.addBatch(caList);
						
				} else {
					
					break;
				}
				
				dataLimitStart += pageSize;
			}
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
		
	}
	
	
	@Override
	public void sendEmail(String reEmails, String ccEmails, String subject,
			User user, CompSatisRateMonitorDto dto) {
		
	        TargetConfig target = targetMapper.get(dto.getTargetId());
		 	dto.setBusinessUnit(target.getBusinessUnit());
		    dto.setPrdDep(target.getPrdDep());
		    dto.setPrdTeam(target.getPrdTeam());
		    CompSatisRateMonitor statisfy = mapper.getDepSatisfy(dto);
		    
		    String quarter = String.valueOf(statisfy.getYearQuarter());//根据季度判断 目标值
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("01")){
				
				statisfy.setTargetValue(statisfy.getOneTargetValue());
			}
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("02")){
							
				statisfy.setTargetValue(statisfy.getTwoTargetValue());
			}
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("03")){
				
				statisfy.setTargetValue(statisfy.getThreeTargetValue());

			}if(quarter.substring(quarter.length()-2, quarter.length()).equals("04")){
				
				statisfy.setTargetValue(statisfy.getFourTargetValue());
			}
			if(statisfy.getOrdNum()!=0){
				
				statisfy.setCmpRate(MathUtil.div(statisfy.getCmpNum(), statisfy.getOrdNum(),4));//投诉率
			}else{
				
				statisfy.setCmpRate(0.0);
			}
		 
			if(statisfy.getCommentNum()!=0){
				
				statisfy.setSatisfaction(MathUtil.div(statisfy.getCommentScore(), statisfy.getCommentNum())/100);//满意度

			}else{
				
				statisfy.setSatisfaction(0.0);
			}
			statisfy.setCompSatisfaction(statisfy.getSatisfaction() -2*statisfy.getCmpRate());//综合满意度
			if(statisfy.getTargetValue()!=0){
				
				statisfy.setReacheRate(MathUtil.div(statisfy.getCompSatisfaction(),statisfy.getTargetValue()/100,4));//达成率

			}else{
				
				statisfy.setReacheRate(0.0);
			}
		    
		    
		    MailTaskDto mailTaskDto = new MailTaskDto();
	        mailTaskDto.setTemplateName("compSatisfy.ftl");
	        mailTaskDto.setSubject(subject);
	        mailTaskDto.setReAddrs(reEmails.split(";"));
	        mailTaskDto.setCcAddrs(ccEmails.split(";"));
	        mailTaskDto.setDataMap(getContentDataMap(statisfy,user,subject));
	        
	        mailTaskDto.setAddPersonRoleId(user.getRole().getId());
	        mailTaskDto.setAddPerson(user.getRealName());
	        mailTaskService.addTask(mailTaskDto);
		
	}

	
	  private Map<String,Object> getContentDataMap(CompSatisRateMonitor statisfy,User user,String subject) {
	        Map<String, Object> dataMap = new HashMap<String, Object>();

	        dataMap.put("title", subject);
	        dataMap.put("compSatisfy",MathUtil.round(statisfy.getCompSatisfaction()*100,2) +"%");
	        dataMap.put("targetValue", MathUtil.round(statisfy.getTargetValue(),2)+"%");
	        dataMap.put("cmpRate", MathUtil.round(statisfy.getCmpRate()*100,2)+"%");
	        dataMap.put("reachRate", MathUtil.round(statisfy.getReacheRate()*100,2)+"%");
	        dataMap.put("qcPerson",user.getRealName());
	        dataMap.put("dateTime", DateUtil.formatAsDefaultDate(new Date()));
	        return dataMap;
	  }

	@Override
	public List<String> getAllDep() {

		return mapper.getAllDep();
	}

	@Override
	public List<Map<String, Object>> getTrendGraph(CompSatisRateMonitorDto dto) {
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();

		List<CompSatisRateMonitor> list = mapper.getTrendGraph(dto);
	    for(CompSatisRateMonitor statisfy : list){
			Map<String, Object> map =new HashMap<String, Object>();
			String quarter = String.valueOf(statisfy.getYearQuarter());//根据季度判断 目标值
			if(statisfy.getYearQuarter()==0){
				
				statisfy.setTargetValue(0.0);
			}else{
				
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("01")){
				
				statisfy.setTargetValue(statisfy.getOneTargetValue());
			}
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("02")){
							
				statisfy.setTargetValue(statisfy.getTwoTargetValue());
			}
			if(quarter.substring(quarter.length()-2, quarter.length()).equals("03")){
				
				statisfy.setTargetValue(statisfy.getThreeTargetValue());

			}if(quarter.substring(quarter.length()-2, quarter.length()).equals("04")){
				
				statisfy.setTargetValue(statisfy.getFourTargetValue());
			}
			}
			if(statisfy.getOrdNum()!=0){
				
				statisfy.setCmpRate(MathUtil.div(statisfy.getCmpNum(), statisfy.getOrdNum(),4));//投诉率
			}else{
				
				statisfy.setCmpRate(0.0);
			}
		 
			if(statisfy.getCommentNum()!=0){
				
				statisfy.setSatisfaction(MathUtil.div(statisfy.getCommentScore(), statisfy.getCommentNum())/100);//满意度

			}else{
				
				statisfy.setSatisfaction(0.0);
			}
			statisfy.setCompSatisfaction(statisfy.getSatisfaction() -2*statisfy.getCmpRate());//综合满意度
			if(statisfy.getTargetValue()!=0){
				
				statisfy.setReacheRate(MathUtil.div(statisfy.getCompSatisfaction(),statisfy.getTargetValue()/100,4));//达成率

			}else{
				
				statisfy.setReacheRate(0.0);
			}
			String statisDate = statisfy.getStatisDate();
			String date = statisDate.substring(statisDate.length()-2, statisDate.length());
			map.put("reacheRate",  MathUtil.round(statisfy.getReacheRate()*100,2));
			map.put("satisfaction",  MathUtil.round(statisfy.getSatisfaction()*100,2));
			map.put("cmpRate",  MathUtil.round(statisfy.getCmpRate()*100,2));
			map.put("statisticDate", Integer.parseInt(date));
			map.put("businessUnit", statisfy.getBusinessUnit());
			listMap.add(map);
		}
		
		return listMap;
	}

	@Override
	public List<String> getBusinessUnit() {
		return mapper.getBusinessUnit();
	}

	/**
	 * 获取全国趋势
	 */
	public List<Map<String, Object>> getNationalGraph(
			CompSatisRateMonitorDto dto) {

		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();

		List<CompSatisRateMonitor> list = mapper.getNationalGraph(dto);
	    for(CompSatisRateMonitor statisfy : list){
	    	
			Map<String, Object> map =new HashMap<String, Object>();
			String quarter = String.valueOf(statisfy.getYearQuarter());//根据季度判断 目标值
			if(statisfy.getYearQuarter()==0){
				
				statisfy.setTargetValue(0.0);
				
			}else{
			    
				List<TargetConfig> targetList = targetMapper.getNationalTarget();
				for(TargetConfig target :targetList){
					
					Integer year = target.getYear();
					if(quarter.substring(0, 4).equals(String.valueOf(year)) && 
							quarter.substring(quarter.length()-2, quarter.length()).equals("01")){
						
						statisfy.setTargetValue(target.getOneTargetValue());
						break;
					}
					if(quarter.substring(0, 4).equals(String.valueOf(year)) &&
							quarter.substring(quarter.length()-2, quarter.length()).equals("02")){
						
						statisfy.setTargetValue(target.getTwoTargetValue());
						break;
					}
					if(quarter.substring(0, 4).equals(String.valueOf(year)) && 
							quarter.substring(quarter.length()-2, quarter.length()).equals("03")){
						
						statisfy.setTargetValue(target.getThreeTargetValue());
						break;
					}
					if(quarter.substring(0, 4).equals(String.valueOf(year)) && 
							quarter.substring(quarter.length()-2, quarter.length()).equals("04")){
	
						statisfy.setTargetValue(target.getFourTargetValue());
						break;
				   }
				}
				if(null == statisfy.getTargetValue()){
					
					statisfy.setTargetValue(0.0);
				}
			
			}
			if(statisfy.getOrdNum()!=0){
				
				statisfy.setCmpRate(MathUtil.div(statisfy.getCmpNum(), statisfy.getOrdNum(),4));//投诉率
			}else{
				
				statisfy.setCmpRate(0.0);
			}
		 
			if(statisfy.getCommentNum()!=0){
				
				statisfy.setSatisfaction(MathUtil.div(statisfy.getCommentScore(), statisfy.getCommentNum())/100);//满意度

			}else{
				
				statisfy.setSatisfaction(0.0);
			}
			statisfy.setCompSatisfaction(statisfy.getSatisfaction() -2*statisfy.getCmpRate());//综合满意度
			if(statisfy.getTargetValue()!=0){
				
				statisfy.setReacheRate(MathUtil.div(statisfy.getCompSatisfaction(),statisfy.getTargetValue()/100,4));//达成率

			}else{
				
				statisfy.setReacheRate(0.0);
			}
			String statisDate = statisfy.getStatisDate();
			String date = statisDate.substring(statisDate.length()-2, statisDate.length());
			map.put("reacheRate",  MathUtil.round(statisfy.getReacheRate()*100,2));
			map.put("satisfaction",  MathUtil.round(statisfy.getSatisfaction()*100,2));
			map.put("cmpRate",  MathUtil.round(statisfy.getCmpRate()*100,2));
			map.put("statisticDate", Integer.parseInt(date));
			listMap.add(map);
		}
		
		return listMap;
	}

	
}
