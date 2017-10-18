package com.tuniu.qms.qs.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qs.dto.QualityCostLedgerDto;
import com.tuniu.qms.qs.service.QualityCostLedgerService;


/**
 * 2015-12-22 质量成本台账
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qs/costLedger")
public class QualityCostLedgerController {

	@Autowired
	private QualityCostLedgerService service;
	
	@Autowired
	private DepartmentService  depService;
	
	@Autowired
	private JobService  jobService;
	
	@Autowired
	private QualityProblemTypeService qptService;
	
	@RequestMapping("/list")
	public String list(QualityCostLedgerDto dto, HttpServletRequest request) {
		
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
		    service.loadPage(dto);
			request.setAttribute("dto", dto);
			return "qs/qualityCost/qualityCostLedgerList";
		}
		
	/**
	 * 获取缓存质量问题
	 */
	@RequestMapping("/getCostAccount")
	@ResponseBody
	public List<String> getCostAccount() {
		
		return  service.getCostAccount();
	} 

}
