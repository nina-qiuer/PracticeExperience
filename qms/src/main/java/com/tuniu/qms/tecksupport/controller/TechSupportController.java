/**
 * 
 */
package com.tuniu.qms.tecksupport.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.tecksupport.service.ITechSupportService;

/**
 * @author jiangye
 */
@Controller
@RequestMapping("/ts/tecksupport")
public class TechSupportController{

	private Map<String,Object>  info;
	
	@Autowired
	private ITechSupportService service;


	public TechSupportController() {
		info = new HashMap<String, Object>();
	}

	@RequestMapping("/index")
	public String index(){
		return "ts/index";
	}
	
	@RequestMapping("/execute")
	@ResponseBody
	public Map<String,Object> execute(String sqlParam){
			System.out.println(sqlParam);
			String[] sqls = sqlParam.split(";");
			try {
				for (String sql : sqls) {
					service.execute(sql);
				}
			} catch (Exception e) {
				info.put("success", false);
				info.put("msg", e.getMessage());
				return info;
			}
			
			info.put("success", true);
			info.put("msg", "操作成功");
			
			return info;
	}
	
	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}
}
