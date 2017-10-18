package com.tuniu.qms.qc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.model.DevRespBill;
import com.tuniu.qms.qc.service.DevRespBillService;
import com.tuniu.qms.qc.service.QcTypeService;

/**
 * 研发责任单
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/devResp")
public class DevRespBillController {

	@Autowired
	private DevRespBillService  devRespService;
	
	@Autowired
	private DepartmentService  depService;
	
	@Autowired
	private JobService  jobService;
	
	@Autowired
	private QcTypeService  qcTypeService;
	
	@Autowired
	private UserService userService;
	

	
	/**
	 * 初始化责任页面
	 * @param qpId
	 * @param model
	 * @return
	 */
	@RequestMapping("/{devId}/toAdd")
	public String addResp(@PathVariable("devId") Integer devId,HttpServletRequest request){
		
		   DevRespBill devResp =new DevRespBill();
		   devResp.setDevId(devId);
		   List<Department> depList = depService.getDepDataCache();
		   request.setAttribute("depNames", CommonUtil.getDepNames(depList));
		   List<Job> jobList = jobService.getJobDataCache();
		   request.setAttribute("jobNames", CommonUtil.getJobNames(jobList));
		   List<User> userList = userService.getUserDataCache();
		   request.setAttribute("userNames", CommonUtil.getUserNames(userList));
		   request.setAttribute("devResp",  devResp);
		   return "qc/qc_development/devRespForm";
		
	}
	
	/**
	 * 新增内部责任页面
	 * @param qpId
	 * @param model
	 * @return
	 */
	@RequestMapping("/addDevResp")
	@ResponseBody
	public HandlerResult addDevResp(DevRespBill respBill,HttpServletRequest request){
		HandlerResult result =  HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		respBill.setAddPerson(user.getRealName());  
	
		try {
			
			int count =  devRespService.getDevRespIsExist(respBill);
			if(count>0){
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("责任人已存在");
				return result;
			}
			User impUser =  userService.getUserByRealName(respBill.getImpPersonName());//根据改进人名字获取ID
			respBill.setImpPersonId(impUser.getId());
			Department dep =  depService.getDepByFullName(respBill.getDepName());
		    Integer jobId = jobService.getJobIdByName(respBill.getJobName());
		    respBill.setJobId(jobId);
		    respBill.setDepId(dep.getId());
			devRespService.add(respBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
			
		}
		   return result;
	}
	
	/**
	 * 修改内部责任单初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{respId}/updateResp")
	public String updateResp(@PathVariable("respId") Integer respId,HttpServletRequest request) {
		
		
		 DevRespBill devResp =  devRespService.get(respId);
		 Integer depId = devResp.getDepId();
		 String fullName = depService.getDepFullNameById(depId);
		 devResp.setDepName(fullName);
		 List<Department> depList = depService.getDepDataCache();
		 request.setAttribute("depNames", CommonUtil.getDepNames(depList));
		 List<Job> jobList = jobService.getJobDataCache();
		 request.setAttribute("jobNames", CommonUtil.getJobNames(jobList));
		 request.setAttribute("devResp", devResp);
		 List<User> userList = userService.getUserDataCache();
		  request.setAttribute("userNames", CommonUtil.getUserNames(userList));
		  return "qc/qc_development/devRespForm";

	}
	
	
	/**
	 * 更新内部责任单
	 * @param request
	 * @param innerBill
	 * @return
	 */
	@RequestMapping("/updateDevResp")
	@ResponseBody
	public HandlerResult updateDevResp(HttpServletRequest request,DevRespBill respBill) {
		
		HandlerResult result =  HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		respBill.setUpdatePerson(user.getRealName());
		try {
			
			User impUser =  userService.getUserByRealName(respBill.getImpPersonName());//根据改进人名字获取ID
			respBill.setImpPersonId(impUser.getId());
			Department dep =  depService.getDepByFullName(respBill.getDepName());
		    Integer jobId = jobService.getJobIdByName(respBill.getJobName());
		    respBill.setJobId(jobId);
		    respBill.setDepId(dep.getId());
			devRespService.update(respBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("更新失败");
		}
		
		return result;
	}
	
	
    /**
     * 删除责任单
     * @param request
     * @param respId
     * @return
     */
	@RequestMapping("/deleteDevResp")
	@ResponseBody
	public HandlerResult deleteDevResp(HttpServletRequest request,@RequestParam("respId") Integer respId) {
		
		HandlerResult result =  HandlerResult.newInstance();
		try {
			
			devRespService.delete(respId);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("删除失败");
		}
		
		return result;
	}

	
	@RequestMapping("/getRealDep")
	@ResponseBody
	public HandlerResult getRealDep(HttpServletRequest request,@RequestParam("depName") String depName) {
		
		HandlerResult result = new HandlerResult();
		
		try {
			
			Department dep =  depService.getDepByFullName(depName);
			if(dep!=null){
				
				result.setRetObj(dep.getId());
				result.setRetCode(Constant.SYS_SUCCESS);
				
			}else{
				result.setResMsg("部门选择不正确!");
				result.setRetCode(Constant.SYS_FAILED);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("部门选择不正确!");
		}
		
		return result;
	}
	
	@RequestMapping("/getRealJob")
	@ResponseBody
	public HandlerResult getRealJob(HttpServletRequest request,@RequestParam("jobName") String jobName){
		
		HandlerResult result = new HandlerResult();
	     try {
			
	    	 Integer jobId = jobService.getJobIdByName(jobName);
	    	 if(jobId==null||jobId<=0){
					
					result.setRetCode(Constant.SYS_FAILED);
					result.setResMsg("岗位名称不存在!");
					return result;
			}
	    	result.setRetObj(jobId);
			result.setRetCode(Constant.SYS_SUCCESS); 
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("岗位选择不正确!");
			return result;
		}		
		
	}
	
}
