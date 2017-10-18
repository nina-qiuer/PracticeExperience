package com.tuniu.config.db.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.config.db.model.DbConfig;
import com.tuniu.config.db.service.DBConfigService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.lang.CollectionUtil;


@Controller
@RequestMapping("/businessconfig")
public class BusinessConfigController { 
	
    @Autowired
    private DBConfigService service;
    
    @Autowired
    @Qualifier("frm_service_system_impl-user")
    private IUserService userService;
    
	@RequestMapping("/list")
	public String list(Model model) {
	    List<String> warningIds = DBConfigManager.getConfigAsList("business.cmp.samegroup.warningid");
	    List<String> checkedWarningIds = DBConfigManager.getConfigAsList("business.cmp.samegroup.checkedwarningid");
	    List<UserEntity> warningUserList = new ArrayList<UserEntity>();
	    UserEntity warningUser = new UserEntity();
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    List< Map<String, Object>> checkboxMapList = new ArrayList< Map<String, Object>>();
	    for(String warningId : warningIds) {
	        Map<String, Object> checkboxMap = new HashMap<String, Object>();
	        checkboxMap.put("id", warningId);
	        paramMap.put("id", warningId);
	        warningUser = (UserEntity) userService.get(paramMap);
	        checkboxMap.put("name", warningUser.getRealName());
	        if(!CollectionUtil.isEmpty(checkedWarningIds)&&checkedWarningIds.contains(warningId)){
	            checkboxMap.put("checked", true);
	        }else{
	            checkboxMap.put("checked", false);
	        }
	        checkboxMapList.add(checkboxMap);
        }
	    
	    JSONArray checkboxs = JSONArray.fromObject(checkboxMapList);
	    model.addAttribute("checkboxs", checkboxs);
	    
		return "config/business_config_list";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest req){
	    Map<String, Object> info = new HashMap<String, Object>();
        String checkedWarningPerson = StringUtils.join(req.getParameterValues("warningIds"), ",");
        if(StringUtils.isNotEmpty(checkedWarningPerson)){
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("dataId", "business.cmp.samegroup.checkedwarningid");
            DbConfig dbConfig = service.fetchOne(paramMap);
            dbConfig.setContent(checkedWarningPerson);
            service.update(dbConfig);
            info.put("msg", "保存成功");
        }else{
            info.put("msg", "至少选择一个");
        }
        return info;
	}
	
}
