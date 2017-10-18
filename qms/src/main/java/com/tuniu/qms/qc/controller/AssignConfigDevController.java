package com.tuniu.qms.qc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.dto.UserDto;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.AssignConfigDevDto;
import com.tuniu.qms.qc.model.AssignConfigDev;
import com.tuniu.qms.qc.model.JiraProject;
import com.tuniu.qms.qc.model.QcPersonProjectDev;
import com.tuniu.qms.qc.service.AssignConfigDevService;

/**
 * 研发质检人员配置
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/assignConfigDev")
public class AssignConfigDevController {

	@Autowired
	private AssignConfigDevService service;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public String list(AssignConfigDevDto dto,HttpServletRequest request){
		
		
		try {
			
			List<AssignConfigDev> list = service.list(new AssignConfigDevDto());
			request.setAttribute("list", list);
						
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		return "qc/qc_development/assignConfigDevList";
	}
	
	/**
	 * 添加质检员
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletRequest request) {
		
		AssignConfigDev ac = new AssignConfigDev();
		request.setAttribute("ac", ac);
		List<User> userList = userService.getUserDataCache();
		request.setAttribute("userNames", CommonUtil.getUserNames(userList));
		
		return "qc/qc_development/assignConfigDevAdd";
	}
	
	
	/**
	 * 检查质检员是否已存在
	 * @param request
	 * @param qcPersonName
	 * @return
	 */
	@RequestMapping("/checkUser")
	@ResponseBody
	public HandlerResult checkUser(HttpServletRequest request,@RequestParam("qcPersonName") String qcPersonName){
		HandlerResult  result = HandlerResult.newInstance();
		try {
			
			int count =  service.getUserIsExist(qcPersonName);
			if(count>0){
				
			    result.setResMsg("质检员已存在");
				result.setRetCode(Constant.SYS_FAILED);
				return result;
			}
			result.setRetCode(Constant.SYS_SUCCESS);
			return result;
			
		} catch (Exception e) {
			
		    e.printStackTrace();
            result.setResMsg(e.getMessage());
			result.setRetCode(Constant.SYS_FAILED);
			return result;
		}
		
	}
	
	/**
	 * 添加质检员
	 * @param request
	 * @param qcPersonName
	 * @return
	 */
	@RequestMapping("/addQcPerson")
	@ResponseBody
	public HandlerResult addQcPerson(HttpServletRequest request,AssignConfigDev dev){
		HandlerResult  result = HandlerResult.newInstance();
		try {
			User user = (User) request.getSession().getAttribute("loginUser");
			UserDto dto = new UserDto();
			dto.setRealName(dev.getQcPersonName());
			User qcUser = userService.list(dto).get(0);
			dev.setQcPersonId(qcUser.getId());
			dev.setAddPerson(user.getRealName());
			service.add(dev);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
		    e.printStackTrace();
            result.setResMsg(e.getMessage());
			result.setRetCode(Constant.SYS_FAILED);
		
		}
		return result;
	}
	
	/**
	 * 删除质检员
	 * @param request
	 * @param qcPersonName
	 * @return
	 */
	@RequestMapping("/deleteQc")
	@ResponseBody
	public HandlerResult deleteQc(HttpServletRequest request,@RequestParam("id") Integer id){
		HandlerResult  result = HandlerResult.newInstance();
		try {
			
			service.delete(id);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
		    e.printStackTrace();
            result.setResMsg(e.getMessage());
			result.setRetCode(Constant.SYS_FAILED);
		
		}
		return result;
	}

	/**
	 * 获得人员配置的系统数据
	 * @param qcPersonId
	 * @param request
	 * @return
	 */
	@RequestMapping("/{qcPersonId}/doProjectConfig")
	public String doProjectConfig(@PathVariable("qcPersonId") Integer qcPersonId, HttpServletRequest request) {
		
		List<JiraProject> projectList =  service.getProjectFromJira();//到jira系统获取系统名称
		request.setAttribute("projectList", projectList);
		
		List<QcPersonProjectDev> doProjectList = service.getProjectDeployList();
		request.setAttribute("doProjectList", doProjectList);
		
		User qcPerson = userService.get(qcPersonId);
		request.setAttribute("qcPerson", qcPerson);
		
		return "qc/qc_development/doProjectConfig";
	}

	/**
	 * 保存配置
	 * @param request
	 * @return
	 */
	@RequestMapping("/savePrjectDeploy")
	@ResponseBody
	public HandlerResult savePrjectDeploy( HttpServletRequest request,@RequestParam("qcPersonId") Integer qcPersonId,
			@RequestParam("qcPersonName") String qcPersonName,@RequestParam("projectIds") String projectIds){
		
		HandlerResult result = HandlerResult.newInstance();
		
		try {
			
			  service.addProjectDeploy(qcPersonId, qcPersonName, projectIds);
			  result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			 e.printStackTrace();
	         result.setResMsg(e.getMessage());
	         result.setRetCode(Constant.SYS_FAILED);
			
		}
		
		return result;
		
	}
}
