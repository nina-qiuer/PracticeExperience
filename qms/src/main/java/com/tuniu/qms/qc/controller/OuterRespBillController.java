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
import com.tuniu.qms.qc.dto.OuterRespBillDto;
import com.tuniu.qms.qc.model.OuterRespBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.service.OuterRespBillService;
import com.tuniu.qms.qc.service.QcBillService;


@Controller
@RequestMapping("/qc/outerResp")
public class OuterRespBillController {

	@Autowired
	private OuterRespBillService  outerRespService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartmentService  depService;
	
	@Autowired
	private JobService  jobService;
	
	@Autowired
	private QcBillService qcBillService;
	
	/**
	 * 初始化外部责任单列表
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String queryOuterRespBill(OuterRespBillDto dto, Model model){
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
		
		outerRespService.loadPage(dto);// 查询外部责任单数据
		model.addAttribute("dto", dto);
		return "qc/outerRespBillList";
	}
	

	/**
	 * 初始化外部责任页面
	 * @param qpId
	 * @param model
	 * @return
	 */
	@RequestMapping("/{qpId}/{qcId}/toAddOuter")
	public ModelAndView toAddOuter(@PathVariable("qpId") Integer qpId, @PathVariable("qcId") Integer qcId , ModelAndView mv){
	   OuterRespBill outerResp = new OuterRespBill();
	   outerResp.setQpId(qpId);
	   outerResp.setQcId(qcId);
	   
	   addModelAndView(mv, outerResp);
	   
	   return mv;
	}
	
	/**
	 * 修改外部责任单初始化页面
	 * @param request
	 * @param iqcId
	 * @return
	 */
	@RequestMapping("/{outerId}/updateOuter")
	public ModelAndView updateInner(@PathVariable("outerId") Integer outerId, ModelAndView mv) {
		OuterRespBill outerBill =  outerRespService.get(outerId);
		outerBill.setDepName(depService.getDepFullNameById(outerBill.getDepId()));
		
		addModelAndView(mv, outerBill);
		return mv;
	}
	
	private void addModelAndView(ModelAndView mv, OuterRespBill outerResp) {
		List<Department> depList = depService.getAllDepDataCache();
		mv.addObject("depNames", CommonUtil.getDepNames(depList));
		
		List<User> userList = userService.getAllUserData();
		mv.addObject("userNames", CommonUtil.getUserNames(userList));
		
		List<Job> jobList = jobService.getJobDataCache();
	    mv.addObject("jobNames", CommonUtil.getJobNames(jobList));
		   
		mv.addObject("outerResp",  outerResp);
		
		QcBill qcBill = qcBillService.get(outerResp.getQcId());
	    if(qcBill != null){//质检单类型
	    	mv.addObject("userFlag", qcBill.getGroupFlag());
	    }else{
	    	mv.addObject("userFlag", Constant.QC_COMPLAINT);
	    }
		
		mv.setViewName("qc/outerRespForm");
	}

	/**
	 * 新增外部责任页面
	 * @param qpId
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOuterResp")
	@ResponseBody
	public HandlerResult addouterResp(OuterRespBill outerBill,HttpServletRequest request){
		HandlerResult result =  HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		outerBill.setAddPerson(user.getRealName());  
		
		try {
			int count =  outerRespService.getOuterRespIsExist(outerBill);
			if(count>0){
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("供应商已存在");
				return result;
			}
			outerRespService.addRespAndPunish(outerBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 更新外部责任单
	 * @param request
	 * @param innerBill
	 * @return
	 */
	@RequestMapping("/updateOuterResp")
	@ResponseBody
	public HandlerResult updateouterResp(HttpServletRequest request,OuterRespBill outerBill) {
		HandlerResult result =  HandlerResult.newInstance();
		User user = (User) request.getSession().getAttribute("loginUser");
		outerBill.setUpdatePerson(user.getRealName());
		
		try {
			outerRespService.updateOuterResp(outerBill);
			result.setRetCode(Constant.SYS_SUCCESS);
		} catch (Exception e) {
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("更新失败");
		}
		
		return result;
	}
	
	
    /**
     * 删除外部责任单
     * @param request
     * @param innerId
     * @return
     */
	@RequestMapping("/deleteOuterResp")
	@ResponseBody
	public HandlerResult deleteouterResp(HttpServletRequest request,@RequestParam("outerId") Integer outerId) {
		
		HandlerResult result =  HandlerResult.newInstance();
		try {
			
			outerRespService.deleteRespBill(outerId);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("删除失败");
		}
		
		return result;
	}

}
