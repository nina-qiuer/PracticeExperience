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
import com.tuniu.qms.qs.dto.KcpSourceDto;
import com.tuniu.qms.qs.model.KcpSource;
import com.tuniu.qms.qs.service.KcpSourceService;


/**
 * 2015-11-24 KCP来源配置
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qs/kcpSource")
public class KcpSourceController {

	@Autowired
	private KcpSourceService service;
	 
	@RequestMapping("/list")
	public String list(KcpSourceDto dto, HttpServletRequest request) {
			
		    List<KcpSource> list = service.list(dto);
		    dto.setDataList(list);
			request.setAttribute("dto", dto);
			return "qs/kcp/kcpSourceList";
		}
		
	
	 /**
     * 新增KCP来源初始化
     * @param request
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request){
         KcpSource kcpSource = new KcpSource();	
    	 request.setAttribute("kcpSource",kcpSource);
         return "qs/kcp/kcpSourceForm";
        
    }
	
	
	/**
	 * 新增KCP来源
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public HandlerResult add(KcpSource kcpSource,HttpServletRequest request){
			
		HandlerResult result = HandlerResult.newInstance();
			
		try {
			  KcpSourceDto dto = new KcpSourceDto();
			  dto.setName(kcpSource.getName());
		      int count =service.getKcpSourceIsExist(dto);
			  if(count > 0 ){
				  
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("已存在该来源");
				return result;
			  }
			  User user = (User) request.getSession().getAttribute("loginUser");
			  kcpSource.setAddPerson(user.getRealName());
			  service.add(kcpSource);
			  result.setRetCode(Constant.SYS_SUCCESS);
			
			
			} catch (Exception e) {

				e.printStackTrace();
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("新增KCP来源失败");
			}
		
			return result;
		}	

	
	 /**
     * 更新KCP来源初始化
     * @param request
     * @return
     */
    @RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id,HttpServletRequest request) {
    	
         KcpSource kcpSource = 	service.get(id);
    	 request.setAttribute("kcpSource", kcpSource);
         return "qs/kcp/kcpSourceForm";
        
    }
	
	
	/**
	 * 更新KCP来源
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateKcp")
	@ResponseBody
	public HandlerResult updateKcp(KcpSource kcpSource,HttpServletRequest request){
			
		HandlerResult result = HandlerResult.newInstance();
			
		try {
			
			  KcpSourceDto dto = new KcpSourceDto();
			  dto.setName(kcpSource.getName());
		      int count =service.getKcpSourceIsExist(dto);
			  if(count > 0 ){
				  
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("已存在该来源");
				return result;
			  }
			  User user = (User) request.getSession().getAttribute("loginUser");
			  kcpSource.setUpdatePerson(user.getRealName());
			  service.update(kcpSource);
			  result.setRetCode(Constant.SYS_SUCCESS);
			
			
			} catch (Exception e) {

				e.printStackTrace();
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("更新KCP来源失败");
			}
		
			return result;
		}
	
	/**
	 * 逻辑删除KCP来源
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
			  KcpSource kcp = new KcpSource();
			  kcp.setId(id);
			  kcp.setDelFlag(Constant.YES);//已删除
			  kcp.setUpdatePerson(user.getRealName());
			  service.update(kcp);
			  result.setRetCode(Constant.SYS_SUCCESS);
			
			
			} catch (Exception e) {

				e.printStackTrace();
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("删除KCP来源失败");
			}
		
			return result;
		}
}
