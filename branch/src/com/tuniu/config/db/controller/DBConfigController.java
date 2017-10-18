package com.tuniu.config.db.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.config.db.model.DbConfig;
import com.tuniu.config.db.service.DBConfigService;


@Controller
@RequestMapping("/db")
public class DBConfigController { 
	
    @Autowired
    private DBConfigService service;
    
	@RequestMapping("/list")
	public String list(String  dataIdPre, Model model) {
	    
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("config_level", 0);
	    if(dataIdPre!=null){
	        paramMap.put("dataId", dataIdPre);
	    }
        List<DbConfig>  dataList = service.fetchList(paramMap);
        
        model.addAttribute("dataList", dataList);
        model.addAttribute("dataIdPre", dataIdPre);
        
		return "config/list";
	}
	
	@RequestMapping("/customer_config")
	public String customerConfig(String  dataIdPre, Model model) {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("config_level", 1);
	    if(dataIdPre!=null){
	        paramMap.put("dataId", dataIdPre);
	    }
        List<DbConfig>  dataList = service.fetchList(paramMap);
        model.addAttribute("dataList", dataList);
        model.addAttribute("dataIdPre", dataIdPre);
		return "config/customer_config";
	}
	
	@RequestMapping("/email_list")
	public String emailListConfig(String  dataIdPre, Model model) {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("config_level", 2);
	    if(dataIdPre!=null){
	        paramMap.put("dataId", dataIdPre);
	    }
        List<DbConfig>  dataList = service.fetchList(paramMap);
        model.addAttribute("dataList", dataList);
        model.addAttribute("dataIdPre", dataIdPre);
		return "config/email_list";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public void add(DbConfig dbConfig){
	    service.insert(dbConfig);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public void update(DbConfig entity){
        service.update(entity);
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public void  delete(@PathVariable("id")Integer id){
	    service.delete(id);
	}

}
