package com.tuniu.qms.report.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.report.dto.QualityCostReportDto;
import com.tuniu.qms.report.service.QualityCostReportService;

@Controller
@RequestMapping("/report/qualityCostReport")
public class QualityCostReportController {
	
	@Autowired
	private QualityCostReportService service;
	
	@Autowired
	private QualityProblemTypeService qptService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private JobService  jobService;
	
	
	
	/**
	 * 质量成本占比
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/qualityCostProportion")
	public String qualityCostProportion(QualityCostReportDto dto, HttpServletRequest request) {
		
		  if(!StringUtils.isEmpty(dto.getDepName())){
			  
			  if(!StringUtils.isEmpty(dto.getDepName().trim())){
		         	String []depNames = dto.getDepName().split("-");
		         	Department dep =  depService.getDepByFullName(dto.getDepName());
		         	if(depNames.length==1){
		         		
		         		dto.setFirstDepId(dep.getId());
		         		dto.setDepType(2);
		         	}
					if(depNames.length==2){
					      
						dto.setTwoDepId(dep.getId());
						dto.setDepType(3);
					 }
					if(depNames.length==3){
						
						dto.setThreeDepId(dep.getId());
						dto.setDepType(3);
			        }
			  }else{
				  
				  dto.setDepType(1);
			  }
		  }else{
			  
				dto.setDepType(1);
		  }
		  if(!StringUtils.isEmpty(dto.getQptName())){
			  
			 if(!StringUtils.isEmpty(dto.getQptName().trim())){
		         	String []qptNames = dto.getQptName().split("-");
		         	if(qptNames.length==1){
		         		
		         		dto.setFirstCostAccount(qptNames[0]);
		         		dto.setQptType(2);
		         	}
					if(qptNames.length==2){
						
						dto.setFirstCostAccount(qptNames[0]);
						dto.setTwoCostAccount(qptNames[1]);
						dto.setQptType(3);
					 }
					if(qptNames.length==3){
						
						dto.setFirstCostAccount(qptNames[0]);
						dto.setTwoCostAccount(qptNames[1]);
						dto.setThreeCostAccount(qptNames[2]);
						dto.setQptType(3);
			         }
			  }else{
				  
				  dto.setQptType(1);
			  }
		  }else{
			  
			  dto.setQptType(1);
		  }
		  if(!StringUtils.isEmpty(dto.getJobName())){
          	
				 Integer jobId = jobService.getJobIdByName(dto.getJobName());	
				 dto.setJobId(jobId);    	
		 }
		  if(!StringUtils.isEmpty(dto.getRespPersonName())){
			  
			  dto.setRespPersonName(dto.getRespPersonName().trim());
		  }
		  if (StringUtils.isBlank(dto.getYearBgn()) && StringUtils.isBlank(dto.getYearEnd())) {
				dto.setYearBgn(String.valueOf(DateUtil.getYear(new Date())));
				dto.setYearEnd(String.valueOf(DateUtil.getYear(new Date())));
			}
		String weekStart = dto.getwYearBgn()+dto.getWeekBgn();
		String weekFinish = dto.getwYearEnd()+dto.getWeekEnd();
		dto.setWeekStart(weekStart);
		dto.setWeekFinish(weekFinish);
		
		String quarterStart = dto.getqYearBgn()+dto.getQuarterBgn();
		String quarterFinish = dto.getqYearEnd()+dto.getQuarterEnd();
		dto.setQuarterStart(quarterStart);
		dto.setQuarterFinish(quarterFinish);
		
		String monthStart = dto.getmYearBgn()+dto.getMonthBgn();
		String monthFinish = dto.getmYearEnd()+dto.getMonthEnd();
		dto.setMonthStart(monthStart);
		dto.setMonthFinish(monthFinish);
		dto.setNowYear(DateUtil.getField(Calendar.YEAR));  
		if(null==dto.getTimeType()){
			
			dto.setTimeType(1);
		}
		if(dto.getViewFlag()==1){
			
			 request.setAttribute("dataList", service.getGraphByQpType(dto));
			 
		}else if(dto.getViewFlag()==2){
			
			 request.setAttribute("dataList", service.getGraphByDep(dto));
			
		}else if(dto.getViewFlag()==3){
			
			 request.setAttribute("dataList", service.getGraphByJob(dto));
			 
		}else if(dto.getViewFlag()==4){
			
			 request.setAttribute("dataList", service.getGraphByResp(dto));
		}
	   
	
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("dto", dto);
		return "report/qualityCostReport/qualityCostProportionGraph";
	}
	
	/**
	 * 质量成本趋势
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/qualityCostTrend")
	public String qualityProblemTrend(QualityCostReportDto dto, HttpServletRequest request) {
		
		
		  if(!StringUtils.isEmpty(dto.getDepName())){
			  
			  if(!StringUtils.isEmpty(dto.getDepName().trim())){
	         	String []depNames = dto.getDepName().split("-");
	         	Department dep =  depService.getDepByFullName(dto.getDepName());
	         	if(depNames.length==1){
	         		
	         		dto.setFirstDepId(dep.getId());
	         	}
				if(depNames.length==2){
				      
					dto.setTwoDepId(dep.getId());
				 }
				if(depNames.length==3){
					
					dto.setThreeDepId(dep.getId());
		        }
			  }
		  }
		  if(!StringUtils.isEmpty(dto.getQptName())){
			  
			  if(!StringUtils.isEmpty(dto.getQptName().trim())){
	         	String []qptNames = dto.getQptName().split("-");
	         	if(qptNames.length==1){
	         		
	         		dto.setFirstCostAccount(qptNames[0]);
	         	}
				if(qptNames.length==2){
				      
					dto.setFirstCostAccount(qptNames[0]);
					dto.setTwoCostAccount(qptNames[1]);
				 }
				if(qptNames.length==3){
					
					dto.setFirstCostAccount(qptNames[0]);
					dto.setTwoCostAccount(qptNames[1]);
					dto.setThreeCostAccount(qptNames[2]);
		         }
			  }
		  }
		  if(!StringUtils.isEmpty(dto.getJobName())){
          	
				 Integer jobId = jobService.getJobIdByName(dto.getJobName());	
				 dto.setJobId(jobId);    	
			 }
		  if(!StringUtils.isEmpty(dto.getRespPersonName())){
			  
			  dto.setRespPersonName(dto.getRespPersonName().trim());
		  }
		    if (StringUtils.isBlank(dto.getYearBgn()) && StringUtils.isBlank(dto.getYearEnd())) {
				dto.setYearBgn(String.valueOf(DateUtil.getYear(new Date())));
				dto.setYearEnd(String.valueOf(DateUtil.getYear(new Date())));
			}
		  if (StringUtils.isBlank(dto.getwYearBgn()) && StringUtils.isBlank(dto.getwYearEnd())&&
				  StringUtils.isBlank(dto.getWeekBgn()) && StringUtils.isBlank(dto.getWeekEnd())) {
			  
				Map<String, Object> map = DateUtil.getYearAndWeek();
				dto.setwYearBgn(String.valueOf(map.get("wYearBgn")));
				dto.setwYearEnd(String.valueOf(map.get("wYearEnd")));
				dto.setWeekBgn(String.valueOf(map.get("weekBgn")));
				dto.setWeekEnd(String.valueOf(map.get("weekEnd")));
				
			}
		dto.setNowYear(DateUtil.getField(Calendar.YEAR));  
		
		String weekStart = dto.getwYearBgn()+dto.getWeekBgn();
		String weekFinish = dto.getwYearEnd()+dto.getWeekEnd();
		dto.setWeekStart(weekStart);
		dto.setWeekFinish(weekFinish);
		
		String quarterStart = dto.getqYearBgn()+dto.getQuarterBgn();
		String quarterFinish = dto.getqYearEnd()+dto.getQuarterEnd();
		dto.setQuarterStart(quarterStart);
		dto.setQuarterFinish(quarterFinish);
		
		String monthStart = dto.getmYearBgn()+dto.getMonthBgn();
		String monthFinish = dto.getmYearEnd()+dto.getMonthEnd();
		dto.setMonthStart(monthStart);
		dto.setMonthFinish(monthFinish);
	    if(null==dto.getTimeType()){
			
			dto.setTimeType(4);
		}
	    request.setAttribute("dataList", service.getTrendGraph(dto));
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList",DateUtil.weekList());
		request.setAttribute("dto", dto);
		return "report/qualityCostReport/qualityCostTrendGraph";
	}
	
	
	
	/**
	 * 质量成本排名
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/qualityCostRank")
	public String qualityProblemRank(QualityCostReportDto dto, HttpServletRequest request) {
		
		
		  if(!StringUtils.isEmpty(dto.getDepName())){
			  
			  if(!StringUtils.isEmpty(dto.getDepName().trim())){
	         	String []depNames = dto.getDepName().split("-");
	         	Department dep =  depService.getDepByFullName(dto.getDepName());
	         	if(depNames.length==1){
	         		
	         		dto.setFirstDepId(dep.getId());
	         		dto.setDepType(1);
	         	}
				if(depNames.length==2){
				      
					dto.setTwoDepId(dep.getId());
					dto.setDepType(2);
				 }
				if(depNames.length==3){
					
					dto.setThreeDepId(dep.getId());
					dto.setDepType(3);
		        }
			  }
		  }
		  if(!StringUtils.isEmpty(dto.getQptName())){
			  
			  if(!StringUtils.isEmpty(dto.getQptName().trim())){
		         	String []qptNames = dto.getQptName().split("-");
		         	if(qptNames.length==1){
		         		
		         		dto.setFirstCostAccount(qptNames[0]);
		         	}
					if(qptNames.length==2){
					      
						dto.setFirstCostAccount(qptNames[0]);
						dto.setTwoCostAccount(qptNames[1]);
					 }
					if(qptNames.length==3){
						
						dto.setFirstCostAccount(qptNames[0]);
						dto.setTwoCostAccount(qptNames[1]);
						dto.setThreeCostAccount(qptNames[2]);
			         }
			  }
		  }
		  if(!StringUtils.isEmpty(dto.getJobName())){
          	
				 Integer jobId = jobService.getJobIdByName(dto.getJobName());	
				 dto.setJobId(jobId);    	
		  }
		  if(!StringUtils.isEmpty(dto.getRespPersonName())){
			  
			  dto.setRespPersonName(dto.getRespPersonName().trim());
		  }
		  if (StringUtils.isBlank(dto.getYearBgn()) && StringUtils.isBlank(dto.getYearEnd())) {
				dto.setYearBgn(String.valueOf(DateUtil.getYear(new Date())));
				dto.setYearEnd(String.valueOf(DateUtil.getYear(new Date())));
			}
		dto.setNowYear(DateUtil.getField(Calendar.YEAR));  
		
		String weekStart = dto.getwYearBgn()+dto.getWeekBgn();
		String weekFinish = dto.getwYearEnd()+dto.getWeekEnd();
		dto.setWeekStart(weekStart);
		dto.setWeekFinish(weekFinish);
		
		String quarterStart = dto.getqYearBgn()+dto.getQuarterBgn();
		String quarterFinish = dto.getqYearEnd()+dto.getQuarterEnd();
		dto.setQuarterStart(quarterStart);
		dto.setQuarterFinish(quarterFinish);
		
		String monthStart = dto.getmYearBgn()+dto.getMonthBgn();
		String monthFinish = dto.getmYearEnd()+dto.getMonthEnd();
		dto.setMonthStart(monthStart);
		dto.setMonthFinish(monthFinish);
		if(null==dto.getTimeType()){
			
			dto.setTimeType(1);
		}
	    request.setAttribute("impQpList", service.getRankGraphImplementQp(dto));
	    request.setAttribute("depList", service.getRankGraphDep(dto));
	    request.setAttribute("respList", service.getRankGraphResp(dto));
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("dto", dto);
		return "report/qualityCostReport/qualityCostRankGraph";
	}
	
	
	
}
