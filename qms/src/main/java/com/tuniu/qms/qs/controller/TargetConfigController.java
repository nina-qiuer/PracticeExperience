package com.tuniu.qms.qs.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qs.dto.TargetConfigDto;
import com.tuniu.qms.qs.model.TargetConfig;
import com.tuniu.qms.qs.service.TargetConfigService;

/**
 * 满意度 目标值
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qs/targetConfig")
public class TargetConfigController {
	
	@Autowired
	private TargetConfigService service;
	@Autowired
	private DepartmentService depService;
	
	@RequestMapping("/list")
	public String apply(TargetConfigDto dto, HttpServletRequest request) {
		
		dto.setNowYear(DateUtil.getField(Calendar.YEAR));  
		if(!StringUtils.isEmpty(dto.getDepName())){
			  
		    if(!StringUtils.isEmpty(dto.getDepName().trim())){
		    	
		    	String []depNames = dto.getDepName().split("-");
	         	if(depNames.length==1){
	         		
	         		dto.setBusinessUnit(depNames[0]);
					dto.setPrdDep("");
					dto.setPrdTeam("");
	         	}
				if(depNames.length==2){
				     
					dto.setBusinessUnit(depNames[0]);
					dto.setPrdDep(depNames[1]);
					dto.setPrdTeam("");
				 }
				if(depNames.length==3){
					
					dto.setBusinessUnit(depNames[0]);
					dto.setPrdDep(depNames[1]);
					dto.setPrdTeam(depNames[2]);
					
		         }
		    }
         
	  }else{
		  
			dto.setBusinessUnit("");
			dto.setPrdDep("");
			dto.setPrdTeam("");
		  
	  }
		if(null==dto.getYear()){
			
			dto.setYear(DateUtil.getField(Calendar.YEAR));
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		return "qs/compSatisRateMonitor/targetConfig";
	}
	
	
	/**
	 * 保存目标值
	 * @param request
	 * @param id
	 * @param flag
	 * @param targetValue
	 * @return
	 */
	@RequestMapping("/updateTarget")
	@ResponseBody
	public HandlerResult updateTarget(HttpServletRequest request,@RequestParam("id") Integer id,@RequestParam("flag") Integer flag,
			@RequestParam("oneTargetValue") Double oneTargetValue,@RequestParam("twoTargetValue") Double twoTargetValue,
			@RequestParam("threeTargetValue") Double threeTargetValue,@RequestParam("fourTargetValue") Double fourTargetValue,
			@RequestParam("businessUnitName") String businessUnitName){
		
		
		 HandlerResult result = HandlerResult.newInstance();
		
		 try {
			    TargetConfig target = new TargetConfig();
			    target.setId(id);
			    target.setBusinessUnit(businessUnitName);
			    target.setOneTargetValue(oneTargetValue);
			    target.setTwoTargetValue(twoTargetValue);
			    target.setThreeTargetValue(threeTargetValue);
			    target.setFourTargetValue(fourTargetValue);
	            if(flag == Constant.NO){ //不是一级组织
	            	
	            	service.update(target);
	            	
	            }else{ //一级组织
	            	
	            	service.updateBatchTarget(target);
	            }
			    
	            result.setRetCode(Constant.SYS_SUCCESS);	
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            result.setRetCode(Constant.SYS_FAILED);
	            result.setResMsg("保存失败!");
	        }
	            
	        return result;
	}
	
	/**
	 * 获取缓存质量问题
	 */
	@RequestMapping("/getAllDep")
	@ResponseBody
	public List<String> getAllDep() {
		
		return  service.getAllDep();
	}
}
