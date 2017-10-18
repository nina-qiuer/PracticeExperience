package com.tuniu.qms.qc.task;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.dto.DepartmentDto;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.service.AssignConfigCmpService;


/**
 * 自动分单部门配置
 * @author chenhaitao
 */
public class DepAllocationTask {
	
	private final static Logger logger = LoggerFactory.getLogger(DepAllocationTask.class);
	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private AssignConfigCmpService assignService;
	
	@Autowired
	private MailTaskService mailTaskService;
	    
	public void depAllocation() {

		try {
			
			//先把投诉质检使用标识 设置为不使用
			Department dep = new Department();
			dep.setCmpQcUseFlag(Constant.NO);
		    depService.updateDep(dep);
		    //根据产品组取他的父级 直到父级的pid <=1 跳出
		    List<Integer> depSet = new ArrayList<Integer>();
		    DepartmentDto dto = new DepartmentDto();
		    dto.setCmpQcUseFlag(Constant.NO);
		    List<Department> list = depService.getUseDepData(dto);
		    for(Department depa :list){
		    	
		    	depSet.add(depa.getId());
		    	while(true){
		    		
		    		if(depa.getPid()>1){
			    		
			    		Department fatherDep = depService.getDepById(depa.getPid());
			    		if(fatherDep != null){
			    			depSet.add(fatherDep.getId());
				    		depa.setPid(fatherDep.getPid());
			    		}else{
			    			break;
			    		}
			    	}else{
			    		
			    		break;
			    	}
		    		
		    	}
		    	
		    }
		    List<Integer> depList = new ArrayList<Integer>();
		    Iterator<Integer> it = depSet.iterator();
			Set<Integer> set = new HashSet<Integer>(); 
		    while(it.hasNext())
		     {
		           Integer o = (Integer) it.next();
			       	if (set.add(o))
			       	{
			       		depList.add(o); 
			    	} 
			  }
		    Map<String, Object>  map =new HashMap<String, Object>();
		    map.put("depIds", depList);
		    map.put("cmpQcUseFlag", Constant.YES);   
		    depService.updateDepCmpQcUseFlag(map);
	    
		    //对于没有设置人员的组织 发送邮件
			List<Map<String, Object>> dockdepList = assignService.getDockdepList();
			for(int i=0;i<depList.size();i++ ){
				
			    for(Map<String, Object> dockMap :dockdepList ){
			    	
			    	Integer dockId = (Integer) dockMap.get("dockdepId");
			    	if(dockId.intValue() == depList.get(i).intValue()){
			    		
			    		depList.remove(i);
			    		i--;
			    		break;
			    	}
			    }
			}
			List<Department> fullNameList = new  ArrayList<Department>();
			if(depList.size()>0){
				
				List<Department> departList = depService.getDepDataCache();
				
			    for(Integer depId:depList){
			    		
			    	 for(Department depart : departList){
			    		
			    		if(depart.getId().intValue() == depId.intValue()){
			    			
			    			fullNameList.add(depart);
			    			break;
			    		}
			    	}
			    }
			     Map<String, Object> dataMap = new HashMap<String, Object>();
				 dataMap.put("sList", fullNameList);
			     String reEmails = "wangtao5@tuniu.com;huangyanli2@tuniu.com;songshuang@tuniu.com";
			     String ccEmails ="liwang2@tuniu.com;liubing@tuniu.com;zhangsensen@tuniu.com";
				 MailTaskDto mailTaskDto = new MailTaskDto();
			     mailTaskDto.setTemplateName("orgConfig.ftl");
			     mailTaskDto.setSubject(DateUtil.formatAsDefaultDate(new Date()) + "需要配置的投诉质检自动分单组织架构-发送自root");
			     mailTaskDto.setReAddrs(reEmails.split(";"));
			     mailTaskDto.setCcAddrs(ccEmails.split(";"));
			     mailTaskDto.setDataMap(dataMap);
			     mailTaskDto.setAddPersonRoleId(0);
			     mailTaskDto.setAddPerson("root");
			     mailTaskService.addTask(mailTaskDto);
			}
			
		    
		} catch (Exception e) {
			
			logger.error("depAllocationTask error：" + e.getMessage());
			e.printStackTrace();
		}
	}

}
