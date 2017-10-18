package com.tuniu.qms.qc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.InnerRespBillDto;
import com.tuniu.qms.qc.model.InnerRespBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.InnerRespBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcTypeService;

/**
 * 
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/innerResp")
public class InnerRespBillController {

	@Autowired
	private InnerRespBillService  innerRespService;
	
	@Autowired
	private DepartmentService  depService;
	
	@Autowired
	private JobService  jobService;
	
	@Autowired
	private QcTypeService  qcTypeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QcBillService qcBillService;
	
	/**
	 * 初始化内部责任单列表
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String queryOuterRespBill(InnerRespBillDto dto,Model model){
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 当前月的最后一天
			cal.set(Calendar.DATE, 1);
			cal.roll(Calendar.DATE, -1);
			Date endTime = cal.getTime();
			String billEndTime = datef.format(endTime) + " 23:59:59";
			Date endTimes = dateT.parse(billEndTime);
			// 当前月的第一天
			cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
			Date beginTime = cal.getTime();
			String billStartTime = datef.format(beginTime) + " 00:00:00";
			Date beginTimes = dateT.parse(billStartTime);
			if (null == dto.getAddTimeFrom() && null == dto.getAddTimeTo()) {

				dto.setAddTimeFrom(beginTimes);
				dto.setAddTimeTo(endTimes);
			} 

		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		innerRespService.loadPage(dto);// 查询内部责任单数据
		model.addAttribute("dto", dto);
		return "qc/innerRespBillList";
	}

	
	/**
	 * 初始化内部责任页面
	 * @param qpId
	 * @param model
	 * @return
	 */
	@RequestMapping("/{qpId}/{qcId}/toAddInner")
	public ModelAndView toAddInner(@PathVariable("qpId") Integer qpId,@PathVariable("qcId") Integer qcId, ModelAndView mv){
	    InnerRespBill innerResp =new InnerRespBill();
	    innerResp.setQpId(qpId);
	    innerResp.setQcId(qcId);
	   
	    addModelAndView(mv, innerResp);
	    return mv;
	}
	
	/**
	 * 修改内部责任单初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{innerId}/updateInner")
	public ModelAndView updateInner(@PathVariable("innerId") Integer innerId, ModelAndView mv) {
		InnerRespBill innerBill =  innerRespService.get(innerId);
		innerBill.setDepName(depService.getDepFullNameById(innerBill.getDepId()));
		
		addModelAndView(mv, innerBill);
		return mv;
	}
	
	private void addModelAndView(ModelAndView mv, InnerRespBill innerResp) {
		QcBill qcBill = qcBillService.get(innerResp.getQcId());
	    if(qcBill != null && qcBill.getGroupFlag() == 1){//质检单类型，是否是投诉质检
		    mv.addObject("isComplaintQc", true);
	    }else{
		    mv.addObject("isComplaintQc", false);
	    }
	   
	    List<Department> depList = depService.getAllDepDataCache();
	    mv.addObject("depNames", CommonUtil.getDepNames(depList));
	    
	    List<Job> jobList = jobService.getJobDataCache();
	    mv.addObject("jobNames", CommonUtil.getJobNames(jobList));
	    
	    List<User> userList = userService.getAllUserData();
	    mv.addObject("userNames", CommonUtil.getUserNames(userList));
	    
	    mv.addObject("innerResp", innerResp);
	    
	    mv.setViewName("qc/innerRespForm");
	}


	/**
	 * 新增内部责任页面
	 * @param qpId
	 * @param model
	 * @return
	 */
	@RequestMapping("/addInnerResp")
	@ResponseBody
	public HandlerResult addInnerResp(InnerRespBill innerBill,HttpServletRequest request){
		HandlerResult result =  HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		innerBill.setAddPerson(user.getRealName());  
		try {
			
			int count =  innerRespService.getInnerRespIsExist(innerBill);
			if(count>0){
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("责任人已存在");
				return result;
			}
			innerRespService.addRespAndPunish(innerBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
			
		}
		   return result;
	}
	
	/**
	 * 更新内部责任单
	 * @param request
	 * @param innerBill
	 * @return
	 */
	@RequestMapping("/updateInnerResp")
	@ResponseBody
	public HandlerResult updateInnerResp(HttpServletRequest request, InnerRespBill innerBill) {
		
		HandlerResult result =  HandlerResult.newInstance();
		try {
			User user = (User) request.getSession().getAttribute("loginUser");
			innerBill.setUpdatePerson(user.getRealName());
			
			innerRespService.updateInnerResp(innerBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("更新失败");
		}
		
		return result;
	}
	
	
    /**
     * 删除内部责任单
     * @param request
     * @param innerId
     * @return
     */
	@RequestMapping("/deleteInnerResp")
	@ResponseBody
	public HandlerResult deleteInnerResp(HttpServletRequest request,@RequestParam("innerId") Integer innerId) {
		
		HandlerResult result =  HandlerResult.newInstance();
		try {
			
			innerRespService.deleteRespBill(innerId);
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
	
	@RequestMapping("/getRealQcType")
	@ResponseBody
	public HandlerResult getRealQcType(HttpServletRequest request,@RequestParam("qcTypeName") String qcTypeName){
		
		HandlerResult result = new HandlerResult();
	     try {
			
	    	 QcType qcType = qcTypeService.getTypeByFullName(qcTypeName);
	    	 if(qcType == null){
					
					result.setRetCode(Constant.SYS_FAILED);
					result.setResMsg("质检类型名称不存在!");
					return result;
			}
	    	result.setRetObj(qcType.getId());
			result.setRetCode(Constant.SYS_SUCCESS); 
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("质检类型名称不存在!");
			return result;
		}		
		
	}
	
	
	/**
	 * 更新责任占比
	 * @return
	 */
	@RequestMapping("/updateRespAccount")
	@ResponseBody
	public HandlerResult updateRespAccount(HttpServletRequest request,@RequestParam("innerId") Integer innerId,@RequestParam("account") Double account) {
		
		HandlerResult reslut = HandlerResult.newInstance();
		try {

			InnerRespBill bill =new InnerRespBill();
			bill.setId(innerId);
			bill.setRespRate(account);
			innerRespService.update(bill);
			reslut.setRetCode(Constant.SYS_SUCCESS);
				
		} catch (Exception e) {
			
			e.printStackTrace();
			reslut.setRetCode(Constant.SYS_FAILED);
			reslut.setResMsg("更新责任占比失败");
		}
		
		return reslut;
	}
	
}
