package com.tuniu.qms.qs.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qs.dto.QualityCostAuxAccountDto;
import com.tuniu.qms.qs.service.QualityCostAuxAccountService;

/**
 * 2015-12-22 质量成本辅助账
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qs/costAuxAccount")
public class QualityCostAuxAccountController {

	@Autowired
	private QualityCostAuxAccountService service;
	
	@Autowired
	private DepartmentService  depService;
	
	@Autowired
	private QualityProblemTypeService qptService;
	
	@RequestMapping("/list")
	public String list(QualityCostAuxAccountDto dto, HttpServletRequest request) {
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
		    service.loadPage(dto);
			request.setAttribute("dto", dto);
			return "qs/qualityCost/qualityCostAuxAccountList";
		}
		
	

}
