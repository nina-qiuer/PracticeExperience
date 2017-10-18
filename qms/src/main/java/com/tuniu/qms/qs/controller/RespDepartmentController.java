package com.tuniu.qms.qs.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qs.dto.RespDepartmentDto;
import com.tuniu.qms.qs.model.DepRespRate;
import com.tuniu.qms.qs.model.RespDepartment;
import com.tuniu.qms.qs.service.RespDepartmentService;


/**
 * 2015-12-23 责任部门
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qs/respDep")
public class RespDepartmentController {

	@Autowired
	private RespDepartmentService service;
	
	private final static Logger logger = LoggerFactory.getLogger(RespDepartmentController.class);
	
	@RequestMapping("/list")
	public String list(RespDepartmentDto dto, HttpServletRequest request) {
		
		    service.loadPage(dto);
			request.setAttribute("dto", dto);
			return "qs/qualityCost/respDepartmentList";
		}
		
	
	/**
     * 处理
     * @param request
     * @return
     */
    @RequestMapping("/{id}/dealDep")
    public String dealDep(@PathVariable("id") Integer id,HttpServletRequest request){
    	
    	 RespDepartment dep = service.get(id);
    	 request.setAttribute("dep",dep);
         return "qs/qualityCost/respDepartmentForm";
        
    }
    
	
	
	@RequestMapping(value = "/toAdd")
    @ResponseBody
    public HandlerResult toAdd( HttpServletRequest request) {
		
		HandlerResult result = HandlerResult.newInstance();
		try {
			
		   	  User user = (User) request.getSession().getAttribute("loginUser");
			  String obj  = request.getParameter("tempLateList");
			  List<DepRespRate> list =  JSON.parseArray(obj, DepRespRate.class);
			  int num = 0;  
		      for(int i=0;i<list.size()-1;i++){  
		            List<DepRespRate> list2 = new ArrayList<DepRespRate>();  
		            for(int j=list.size()-1;j>i;j--){ 
		            	
		                if(list.get(j).getDepName().equals(list.get(i).getDepName())){  
		                    list2.add(list.get(j));  
		                    list.remove(j);  
		                }  
		            }  
		            if((list2.size()+1) >1){  
		                num =(list2.size()+1);  
		            }  
		        }  
		        if(num > 1){  
		        	
		        	result.setRetCode(Constant.SYS_FAILED);
		        	result.setResMsg("不能填写重复责任部门");
		        	return result;
		        }  
			  
			  service.updateDep(list, user);
			  result.setRetCode(Constant.SYS_SUCCESS);
			    
		} catch (Exception e) {
			
			logger.error("respDepartment save fail" +e.getMessage());
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("保存失败");
		}
	    
	    return result;
	}

    
  
}
