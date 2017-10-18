package com.tuniu.qms.qs.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qs.dto.KcpTypeDto;
import com.tuniu.qms.qs.model.KcpType;
import com.tuniu.qms.qs.service.KcpTypeService;


/**
 * 2015-11-24 KCP类型配置
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qs/kcpType")
public class KcpTypeController {

	@Autowired
	private KcpTypeService service;
	 
	@RequestMapping("/list")
	public String list(KcpTypeDto dto, HttpServletRequest request) {
		
		    List<KcpType> list = service.list(dto);
		    dto.setDataList(list);
			request.setAttribute("dto", dto);
			return "qs/kcp/kcpTypeList";
		}
		
	
	 /**
     * 新增KCP类型初始化
     * @param request
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request){
         KcpType kcpType = new KcpType();	
    	 request.setAttribute("kcpType",kcpType);
         return "qs/kcp/kcpTypeForm";
        
    }
	
	
	/**
	 * 新增KCP类型
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public HandlerResult add(KcpType kcpType,HttpServletRequest request){
			
		HandlerResult result = HandlerResult.newInstance();
			
		try {
			  KcpTypeDto dto = new KcpTypeDto();
			  dto.setName(kcpType.getName());
		      int count =service.getKcpTypeIsExist(dto);
			  if(count > 0 ){
				  
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("已存在该类型");
				return result;
			  }
			  User user = (User) request.getSession().getAttribute("loginUser");
			  kcpType.setAddPerson(user.getRealName());
			  service.add(kcpType);
			  result.setRetCode(Constant.SYS_SUCCESS);
			
			
			} catch (Exception e) {

				e.printStackTrace();
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("新增KCP类型失败");
			}
		
			return result;
		}	

	
	 /**
     * 更新KCP类型初始化
     * @param request
     * @return
     */
    @RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id,HttpServletRequest request) {
    	
         KcpType kcpType = 	service.get(id);
    	 request.setAttribute("kcpType", kcpType);
         return "qs/kcp/kcpTypeForm";
        
    }
	
	
	/**
	 * 更新KCP类型
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateKcp")
	@ResponseBody
	public HandlerResult updateKcp(KcpType kcpType,HttpServletRequest request){
			
		HandlerResult result = HandlerResult.newInstance();
			
		try {
			
			  KcpTypeDto dto = new KcpTypeDto();
			  dto.setName(kcpType.getName());
		      int count =service.getKcpTypeIsExist(dto);
			  if(count > 0 ){
				  
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("已存在该类型");
				return result;
			  }
			  User user = (User) request.getSession().getAttribute("loginUser");
			  kcpType.setUpdatePerson(user.getRealName());
			  service.update(kcpType);
			  result.setRetCode(Constant.SYS_SUCCESS);
			
			
			} catch (Exception e) {

				e.printStackTrace();
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("更新KCP类型失败");
			}
		
			return result;
		}
	
	/**
	 * 逻辑删除KCP类型
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteKcp")
	@ResponseBody
	public HandlerResult deleteKcp(@RequestParam("id") Integer id,HttpServletRequest request){
			
		HandlerResult result = HandlerResult.newInstance();
			
		try {
			  User user = (User) request.getSession().getAttribute("loginUser");
			  KcpType kcp = new KcpType();
			  kcp.setId(id);
			  kcp.setDelFlag(Constant.YES);//已删除
			  kcp.setUpdatePerson(user.getRealName());
			  service.update(kcp);
			  result.setRetCode(Constant.SYS_SUCCESS);
			
			
			} catch (Exception e) {

				e.printStackTrace();
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("删除KCP类型失败");
			}
		
			return result;
		}
}
