package com.tuniu.qms.qs.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.DepartmentDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.qs.dto.CompSatisRateMonitorDto;
import com.tuniu.qms.qs.model.TargetConfig;
import com.tuniu.qms.qs.service.CompSatisRateMonitorService;
import com.tuniu.qms.qs.service.TargetConfigService;

/**
 * 综合满意度达成率监控列表
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qs/compSatisRate")
public class CompSatisRateMonitorController {

	 @Autowired
	 private CompSatisRateMonitorService service;
	
	 @Autowired
	 private TargetConfigService targetService;
	 
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private DepartmentService depService;
	
	/**
	 * 事业部列表
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String list(CompSatisRateMonitorDto dto, HttpServletRequest request) {
		
		
		 if (StringUtils.isBlank(dto.getqYear()) && StringUtils.isBlank(dto.getQuarter()) ) {
			  
			 dto.setSearchQuarter(String.valueOf(DateUtil.getYearAndQuarter(new Date())));	
			 dto.setqYear(String.valueOf(DateUtil.getYear(new Date())));	
			 dto.setQuarter("0"+String.valueOf(DateUtil.getQuarter(new Date())));	
		  }else{
			  
				String searchQuarter = dto.getqYear()+dto.getQuarter();
				dto.setSearchQuarter(searchQuarter);
			  
		  }
		
		String searchMonth = dto.getmYear()+dto.getMonth();
		dto.setSearchMonth(searchMonth);
		dto.setNowYear(DateUtil.getField(Calendar.YEAR));  
		if(null==dto.getTimeType()){
			
			dto.setTimeType(1);
		}
		
	    request.setAttribute("dataList", service.list(dto));
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("dto", dto);
		return "qs/compSatisRateMonitor/businessUnitStatisfyList";
	}
	
	/**
	 * 产品部
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/prdDepList/{targetId}/{timeType}/{nextSearch}")
	public String prdDepList(@PathVariable("targetId") Integer targetId,@PathVariable("timeType") Integer timeType,@PathVariable("nextSearch") String nextSearch,
			CompSatisRateMonitorDto dto, HttpServletRequest request) {
		
		 TargetConfig target = targetService.get(targetId);
		 if(null!=target){
			 
			 dto.setBusinessUnit(target.getBusinessUnit());
		 }
		 dto.setTargetId(targetId);
		 
		 String year ="";
		 String quarter ="";
		 String month ="";
		 if(timeType ==1){
			 
			 year = nextSearch.substring(0, 4);
			 quarter = nextSearch.substring(nextSearch.length()-2, nextSearch.length());
			 dto.setSearchQuarter(nextSearch);
			 dto.setqYear(year);
			 dto.setQuarter(quarter);
			 
		 }else{
			 
			 year = nextSearch.substring(0, 4);
			 month = nextSearch.substring(nextSearch.length()-2, nextSearch.length());
			 dto.setSearchMonth(nextSearch);
			 dto.setmYear(year);
			 dto.setMonth(month);
		 }
		 dto.setTimeType(timeType);
	    request.setAttribute("dataList", service.list(dto));
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("dto", dto);
		return "qs/compSatisRateMonitor/prdDepStatisfyList";
	}
	
	/**
	 * 产品组
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/prdTeamList/{targetId}/{timeType}/{nextSearch}")
	public String prdTeamList(@PathVariable("targetId") Integer targetId,@PathVariable("timeType") Integer timeType,@PathVariable("nextSearch") String nextSearch,
			CompSatisRateMonitorDto dto, HttpServletRequest request) {
		
		 TargetConfig target = targetService.get(targetId);
		 if(null!=target){
			 
			 dto.setBusinessUnit(target.getBusinessUnit());
			 dto.setPrdDep(target.getPrdDep());
		 }
		 dto.setTargetId(targetId);
		 String year ="";
		 String quarter ="";
		 String month ="";
		 if(timeType ==1){
			 
			 year = nextSearch.substring(0, 4);
			 quarter = nextSearch.substring(nextSearch.length()-2, nextSearch.length());
			 dto.setSearchQuarter(nextSearch);
			 dto.setqYear(year);
			 dto.setQuarter(quarter);
			 
		 }else{
			 
			 year = nextSearch.substring(0, 4);
			 month = nextSearch.substring(nextSearch.length()-2, nextSearch.length());
			 dto.setSearchMonth(nextSearch);
			 dto.setmYear(year);
			 dto.setMonth(month);
		 }
	    request.setAttribute("dataList", service.list(dto));
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("dto", dto);
		return "qs/compSatisRateMonitor/prdTeamStatisfyList";
	}
	
	/**
	 * 产品经理
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/prdManagerList/{targetId}/{timeType}/{nextSearch}")
	public String prdManagerList(@PathVariable("targetId") Integer targetId,@PathVariable("timeType") Integer timeType,@PathVariable("nextSearch") String nextSearch,
			CompSatisRateMonitorDto dto, HttpServletRequest request) {
		
		 TargetConfig target = targetService.get(targetId);
		 if(null!=target){
			 
			 dto.setBusinessUnit(target.getBusinessUnit());
			 dto.setPrdDep(target.getPrdDep());
			 dto.setPrdTeam(target.getPrdTeam());
		 }
		 dto.setTargetId(targetId);
		 
		 
		 String year ="";
		 String quarter ="";
		 String month ="";
		 if(timeType ==1){
			 
			 year = nextSearch.substring(0, 4);
			 quarter = nextSearch.substring(nextSearch.length()-2, nextSearch.length());
			 dto.setSearchQuarter(nextSearch);
			 dto.setqYear(year);
			 dto.setQuarter(quarter);
			 
		 }else{
			 
			 year = nextSearch.substring(0, 4);
			 month = nextSearch.substring(nextSearch.length()-2, nextSearch.length());
			 dto.setSearchMonth(nextSearch);
			 dto.setmYear(year);
			 dto.setMonth(month);
		 }
		 dto.setTimeType(timeType);
		
		/*dto.setNowYear(DateUtil.getField(Calendar.YEAR));  
		dto.setUrl("prdManagerList");*/
	    request.setAttribute("dataList", service.list(dto));
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("dto", dto);
		return "qs/compSatisRateMonitor/prdManagerStatisfyList";
	}
	
	/**
	 * 产品专员
	 * @param dto
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/prdSpecialistList/{prdManager}/{targetId}/{timeType}/{nextSearch}")
	public String prdSpecialistList(@PathVariable("prdManager") String prdManager, @PathVariable("targetId") Integer targetId,@PathVariable("timeType") Integer timeType,@PathVariable("nextSearch") String nextSearch,
			CompSatisRateMonitorDto dto, HttpServletRequest request) throws Exception {
		
		 TargetConfig target = targetService.get(targetId);
		 if(null!=target){
			 
			 dto.setBusinessUnit(target.getBusinessUnit());
			 dto.setPrdDep(target.getPrdDep());
			 dto.setPrdTeam(target.getPrdTeam());
		 }
		 //get 方法中文乱码转换
		 prdManager = new String(prdManager.getBytes("iso8859-1"),"utf-8");
		 
		 dto.setTargetId(targetId);
		 dto.setPrdManager(prdManager);
		 
		 String year ="";
		 String quarter ="";
		 String month ="";
		 if(timeType ==1){
			 
			 year = nextSearch.substring(0, 4);
			 quarter = nextSearch.substring(nextSearch.length()-2, nextSearch.length());
			 dto.setSearchQuarter(nextSearch);
			 dto.setqYear(year);
			 dto.setQuarter(quarter);
			 
		 }else{
			 
			 year = nextSearch.substring(0, 4);
			 month = nextSearch.substring(nextSearch.length()-2, nextSearch.length());
			 dto.setSearchMonth(nextSearch);
			 dto.setmYear(year);
			 dto.setMonth(month);
		 }
		 dto.setTimeType(timeType);
		
	    request.setAttribute("dataList", service.list(dto));
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("dto", dto);
		return "qs/compSatisRateMonitor/prdSpecialistStatisfyList";
	}
	
	
	/**
	 * 邮件初始化
	 * @param targetId
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/{targetId}/{search}/{timeType}/toAddEmail")
	public String toAddEmail(@PathVariable("targetId") Integer targetId,@PathVariable("search") String search,
			@PathVariable("timeType") Integer timeType,CompSatisRateMonitorDto dto, HttpServletRequest request){
		
		 User user = (User) request.getSession().getAttribute("loginUser");
		 StringBuffer reEmails= new StringBuffer();
		 StringBuffer ccEmails= new StringBuffer();
		 reEmails.append("");
		 TargetConfig target = targetService.get(targetId);
		 dto.setTargetId(targetId);
		 String year ="";
		 String quarter ="";
		 String month ="";
		 String name ="";
		 if(timeType ==1){
			 
			 year = search.substring(0, 4);
			 quarter = search.substring(search.length()-2, search.length());
			 name = year+"年"+Integer.parseInt(quarter) +"季度综合满意度预警";
			 dto.setSearchQuarter(search);
		 }else{
			 
			 year = search.substring(0, 4);
			 month = search.substring(search.length()-2, search.length());
			 name = year+"年"+Integer.parseInt(month) +"月度综合满意度预警";
			 dto.setSearchMonth(search);
		 }
		 dto.setTimeType(timeType);
		
		 if(null!=target){
			 
			 dto.setBusinessUnit(target.getBusinessUnit());
			 dto.setPrdDep(target.getPrdDep());
			 dto.setPrdTeam(target.getPrdTeam());
		
			 //事业部 事业部一级部门负责人（主送）
			 if(!"".equals(target.getBusinessUnit()) && "".equals(target.getPrdDep()) && "".equals(target.getPrdTeam())){
				
				 Map<String, Object> map =new HashMap<String, Object>();
				 map.put("depName", target.getBusinessUnit());
				 Department dep = depService.getDepByName(map);
				 if(null!=dep){
					 
					 List<Integer> list =new ArrayList<Integer>();
					 list.add(dep.getId());
					 map.put("depIds", list);
					 List<User> userList  =  userService.listByDepId(map); 
					 for(User sendUser: userList){
						 
						 if(!sendUser.getEmail().equals(QcConstant.CEO_EMAIL) && !sendUser.getEmail().equals(QcConstant.COO_EMAIL)){
							 
							 reEmails.append(sendUser.getEmail()+";");
						 }
						
					 }
				 }
				
			 }
			 //产品部 	产品部维度的预警邮件发送内容及范围 产品部所属的二级部门负责人、所属事业部一级部门负责人（主送）
			if(!"".equals(target.getBusinessUnit()) && !"".equals(target.getPrdDep()) && "".equals(target.getPrdTeam())){
				
				 List<Integer> list =new ArrayList<Integer>();
				 Map<String, Object> map =new HashMap<String, Object>();
				 map.put("depName", target.getBusinessUnit());
				 Department dep = depService.getDepByName(map);
				 if(null!=dep){
					 
					 list.add(dep.getId());
					 DepartmentDto depDto =new DepartmentDto();
					 depDto.setPid(dep.getId());
					 depDto.setDelFlag(0);
					 depDto.setDepName(target.getPrdDep());
					 Department prdDep = depService.getDepByNameAndPid(depDto);
					 if(null!=prdDep){
						 
						 list.add(prdDep.getId());
					 }
					 map.put("depIds", list);
					 List<User> userList  =  userService.listByDepId(map); 
					 for(User sendUser: userList){
						 
						 if(!sendUser.getEmail().equals(QcConstant.CEO_EMAIL) && !sendUser.getEmail().equals(QcConstant.COO_EMAIL)){
							 
							 reEmails.append(sendUser.getEmail()+";");
						 }
					 }
				 }
				 
			 }
			//产品组 	产品组维度的预警邮件发送内容及范围
			if(!"".equals(target.getBusinessUnit()) && !"".equals(target.getPrdDep()) && !"".equals(target.getPrdTeam())){
				
				 List<Integer> list =new ArrayList<Integer>();
				 Map<String, Object> map =new HashMap<String, Object>();
				 map.put("depName", target.getBusinessUnit());
				 Department dep = depService.getDepByName(map);
				 if(null!=dep){
					 
					 list.add(dep.getId());
					 DepartmentDto depDto =new DepartmentDto();
					 depDto.setPid(dep.getId());
					 depDto.setDelFlag(0);
					 depDto.setDepName(target.getPrdDep());
					 Department prdDep = depService.getDepByNameAndPid(depDto);
					 if(null!=prdDep){
						 
						 list.add(prdDep.getId());
						 DepartmentDto teamDto =new DepartmentDto();
						 teamDto.setPid(prdDep.getId());
						 teamDto.setDelFlag(0);
						 teamDto.setDepName(target.getPrdTeam());
						 Department prdTeam = depService.getDepByNameAndPid(teamDto);
						 if(null!=prdTeam){
							 
							 list.add(prdTeam.getId());
						 }
					 }
					 map.put("depIds", list);
					 List<User> userList  =  userService.listByDepId(map); 
					 for(User sendUser: userList){
						 
						 if(!sendUser.getEmail().equals(QcConstant.CEO_EMAIL) && !sendUser.getEmail().equals(QcConstant.COO_EMAIL)){
							 
							 reEmails.append(sendUser.getEmail()+";");
						 }
					 }
				 }
						 
			}
		 }
		 if(reEmails.length()>1){
			 
			 String reEmail =  reEmails.substring(0, reEmails.length()-1);
			 request.setAttribute("reEmails", reEmail); 
		 }
		 ccEmails.append(user.getEmail()+";").append("yanghuifan@tuniu.com;").append("liuyajun@tuniu.com");
		
		 request.setAttribute("ccEmails", ccEmails); 
		 request.setAttribute("dto", dto);
		 request.setAttribute("emailTitle", target.getBusinessUnit() + target.getPrdDep() +target.getPrdTeam() +name); 
		return "qs/compSatisRateMonitor/satisfyMail";
	}
	
	
	
	@RequestMapping(value = "/sendEmail")
    @ResponseBody
    public HandlerResult sendEmail(HttpServletRequest request) {
		
		HandlerResult result = HandlerResult.newInstance();
		try {
			    User user = (User) request.getSession().getAttribute("loginUser");
	            String reEmails  = request.getParameter("reEmails");
	            String ccEmails = request.getParameter("ccEmails");
	            String subject = request.getParameter("title");
	            String targetId = request.getParameter("targetId");
	            String timeType = request.getParameter("timeType");
	            String searchQuarter = request.getParameter("searchQuarter");
	            String searchMonth = request.getParameter("searchMonth");
	            CompSatisRateMonitorDto dto = new CompSatisRateMonitorDto();
	            dto.setTimeType(Integer.parseInt(timeType));
	            dto.setSearchMonth(searchMonth);
	            dto.setSearchQuarter(searchQuarter);
	            dto.setTargetId(Integer.parseInt(targetId));
	            
	            service.sendEmail(reEmails, ccEmails, subject, user, dto);
	          
			    result.setRetCode(Constant.SYS_SUCCESS);
			    result.setResMsg("发送成功");
			    
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("发送邮件失败");
		}
	  
	    
	    return result;
	}
	
	/**
	 * 事业部同比
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/businessUnitTrend")
	public String businessUnitTrend(CompSatisRateMonitorDto dto, HttpServletRequest request) {
		
		
		dto.setNowYear(DateUtil.getField(Calendar.YEAR));  
		  if (StringUtils.isBlank(dto.getqYear())) {
	    	 
	    	 dto.setqYear(String.valueOf(DateUtil.getYear(new Date())));	
		 }
	     
	    if(null==dto.getTimeType()){
			
			dto.setTimeType(1);
		}
	    if(dto.getTimeType()==1){
	    	
	    	dto.setYear(dto.getqYear());
	    	dto.setSearchYear(dto.getYear());
	    	dto.setQuarter1(dto.getqYear()+"01");
	    	dto.setQuarter2(dto.getqYear()+"02");
	    	dto.setQuarter3(dto.getqYear()+"03");
	    	dto.setQuarter4(dto.getqYear()+"04");
	    }else {
	    	
	    	dto.setYear(dto.getmYear());
	    	dto.setSearchYear(dto.getmYear());
	    	dto.setMonth1(dto.getmYear()+"01");
	    	dto.setMonth2(dto.getmYear()+"02");
	    	dto.setMonth3(dto.getmYear()+"03");
	    	dto.setMonth4(dto.getmYear()+"04");
	    	dto.setMonth5(dto.getmYear()+"05");
	    	dto.setMonth6(dto.getmYear()+"06");
	    	dto.setMonth7(dto.getmYear()+"07");
	    	dto.setMonth8(dto.getmYear()+"08");
	    	dto.setMonth9(dto.getmYear()+"09");
	    	dto.setMonth10(dto.getmYear()+"10");
	    	dto.setMonth11(dto.getmYear()+"11");
	    	dto.setMonth12(dto.getmYear()+"12");
	    }
	    //当前年数据
	    if(dto.getBusinessUnit()!=null &&!"".equals(dto.getBusinessUnit())){
	    	
	    	  request.setAttribute("dataList", service.getTrendGraph(dto));
	    	  
	    }else{
	    
	    		request.setAttribute("dataList",  service.getNationalGraph(dto));
	    }
	    
	    CompSatisRateMonitorDto befDto =new CompSatisRateMonitorDto();
	    
	    if(dto.getTimeType()==1){
	    	
	    	  Integer befYear =Integer.parseInt(dto.getqYear()) -1;
	    	  befDto.setBefYear(String.valueOf(befYear));
	    	  dto.setBefYear(String.valueOf(befYear));
	    	  dto.setSearchYear(dto.getBefYear());
	    	  befDto.setQuarter1(dto.getBefYear()+"01");
	    	  befDto.setQuarter2(dto.getBefYear()+"02");
	    	  befDto.setQuarter3(dto.getBefYear()+"03");
	    	  befDto.setQuarter4(dto.getBefYear()+"04");
	  	   
	    }else {
	    	
	    	  Integer befYear =Integer.parseInt(dto.getmYear()) -1;
	    	  befDto.setBefYear(String.valueOf(befYear));
	    	  dto.setBefYear(String.valueOf(befYear));
	    	  dto.setSearchYear(dto.getBefYear());
	    	  befDto.setMonth1(dto.getBefYear()+"01");
	    	  befDto.setMonth2(dto.getBefYear()+"02");
	    	  befDto.setMonth3(dto.getBefYear()+"03");
	    	  befDto.setMonth4(dto.getBefYear()+"04");
	    	  befDto.setMonth5(dto.getBefYear()+"05");
	    	  befDto.setMonth6(dto.getBefYear()+"06");
	    	  befDto.setMonth7(dto.getBefYear()+"07");
	    	  befDto.setMonth8(dto.getBefYear()+"08");
	    	  befDto.setMonth9(dto.getBefYear()+"09");
	    	  befDto.setMonth10(dto.getBefYear()+"10");
	    	  befDto.setMonth11(dto.getBefYear()+"11");
	    	  befDto.setMonth12(dto.getBefYear()+"12");
	    }
	  
	    befDto.setTimeType(dto.getTimeType());
	    //前一年数据
	    if(dto.getBusinessUnit()!=null &&!"".equals(dto.getBusinessUnit())){
	    
	    	befDto.setBusinessUnit(dto.getBusinessUnit());
	        request.setAttribute("befDataList", service.getTrendGraph(befDto));
	       
	    }else{
	    	
	    	 request.setAttribute("befDataList", service.getNationalGraph(befDto));
	    }
	
	    request.setAttribute("monthList", DateUtil.monthList());
	    request.setAttribute("weekList",DateUtil.weekList());
	    request.setAttribute("depList",service.getBusinessUnit());
		request.setAttribute("dto", dto);
		return "qs/compSatisRateMonitor/compSatisfyGraph";
	}
	
	/**
	 * 获取缓存质量问题
	 */
	@RequestMapping("/getBusinessUnit")
	@ResponseBody
	public List<String> getBusinessUnit() {
		
		return  service.getBusinessUnit();
	}
}
