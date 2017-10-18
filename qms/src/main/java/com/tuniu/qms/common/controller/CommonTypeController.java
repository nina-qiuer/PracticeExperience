package com.tuniu.qms.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.CommonTypeDto;
import com.tuniu.qms.common.model.CommonType;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.CommonTypeService;

/**
 * 通用类型配置
 * @author zhangsensen
 *
 */
@RequestMapping("/common/cmType")
@Controller
public class CommonTypeController {
	
	@Autowired
	private CommonTypeService commonTypeService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request){
		List<CommonType> typelist = commonTypeService.list(new CommonTypeDto());
		
		for(CommonType type : typelist){//便于页面辨识， 增加标识   CR：质检关联关闭原因类型  OT：其他
			if(type.getPid() != 0){
				StringBuilder sb = new StringBuilder(type.getName());
				
				sb.append(" - ");
				switch(type.getTypeFlag()){
					case 1: sb.append("[CR]"); break;
					case 2: sb.append("[OT]"); break;
					default: sb.append("[OT]");
				}
				
				type.setName(sb.toString());
			}
			
		}
		
		request.setAttribute("dataList", typelist);
		return "common/cmTypeList";
	}
	
	/**
	 * 添加子节点
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping("/{pid}/toAddChild")
	public String toAddChild(@PathVariable Integer pid, HttpServletRequest request){
		CommonType cmType = new CommonType();
		
		cmType.setPid(pid);
		cmType.setTypeFlag(1);
		request.setAttribute("cmType", cmType);
		
		return "common/cmTypeForm";
	}
	
	/**
	 * 修改节点
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable Integer id, HttpServletRequest request){
		CommonType cmType = commonTypeService.get(id);
		
		request.setAttribute("cmType", cmType);
		
		return "common/cmTypeForm";
	}
	
	@RequestMapping("/addChild")
	@ResponseBody
	public String addChild(CommonType cmType, HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		
		cmType.setAddPerson(user.getRealName());
		commonTypeService.add(cmType);
		
		return "Success";
	}
	
	@RequestMapping("/updateType")
	@ResponseBody
	public String updateIndType(CommonType cmType, HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		
		cmType.setUpdatePerson(user.getRealName());
		commonTypeService.update(cmType);
		
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable Integer id){
		commonTypeService.delete(id);
		
		return "Success";
	}
}
