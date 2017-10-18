package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dto.JiraBillDto;
import com.tuniu.qms.qc.model.JiraBill;
import com.tuniu.qms.qc.service.JiraRelationService;
import com.tuniu.qms.qc.service.ResDevQcBillService;
import com.tuniu.qms.qc.util.FaultSourceEnum;
import com.tuniu.qms.qc.util.QcConstant;

/**
 * jira关联
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/jiraRelation")
public class JiraRelationController {

	@Autowired
	private JiraRelationService service;
	
	@Autowired
	private ResDevQcBillService qcBillService;
	
	/**
	 * 初始化jira关联
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String queryInnerPunishBill(JiraBillDto dto,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		String beginTime =  DateUtil.getBeforeDay();
		String endTime =  DateUtil.getTodayTime();
		if (null == dto.getAddTimeFrom() && null == dto.getAddTimeTo()) {
			dto.setAddTimeFrom(beginTime);
			dto.setAddTimeTo(endTime);
		} 
		if(Constant.ROLE_EMPLOYEE == user.getRole().getType()){
			dto.setQcPersonId(user.getId());
		}
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		
		List<String> mainReasonList = service.listDistMainReason();
		List<String> eventClassList = service.listDistEventClass();
		request.setAttribute("mainReasonList", mainReasonList);
		request.setAttribute("eventClassList", eventClassList);
		
		return "qc/qc_development/jiraBillList";
	}
	
	
	/**
	 * 关闭jira
	 * @param request
	 * @return
	 */
	@RequestMapping("/closeJira")
	@ResponseBody
	public HandlerResult closeJira(@RequestParam("jiraId") Integer jiraId,HttpServletRequest request){
		
		HandlerResult result = HandlerResult.newInstance();
	
		try {
			
			JiraBill jiarBill =new JiraBill();
			jiarBill.setId(jiraId);
			jiarBill.setQcId(0);
			jiarBill.setFlag(QcConstant.JIRA_CLOSED);//已关闭
			service.update(jiarBill);
			result.setRetCode(Constant.SYS_SUCCESS);
			
		} catch (Exception e) {
		
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 初始化关联研发质检单
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("/relationQcId")
	public String relationQcId(HttpServletRequest request,@RequestParam("jiraIds") String jiraIds){
		
		
		request.setAttribute("jiraIds", jiraIds);
		return "qc/qc_development/jiraBillForm";
		
	}
	
	/**
	 * 关联质检单
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateQcId")
	@ResponseBody
	public HandlerResult updateQcId(@RequestParam("jiraId") String jiraId,@RequestParam("qcId") Integer qcId,HttpServletRequest request){
		
		HandlerResult result = HandlerResult.newInstance();
	
		try {
			
		   //校验研发质检单号存不存在
			int count =  qcBillService.getQcBillIsExist(qcId);
			if(count < 1){
				
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg("研发质检单号不存在");
				return result;
				
			}
		   //校验jira单是否绑定其他研发质检单
			  List<String> list =new ArrayList<String>();
			  String[] jiraIds = jiraId.split(",");
			  for(int i = 0;i<jiraIds.length;i++){
					
					list.add(jiraIds[i]);
				}	
			  Map<String, Object> map =new HashMap<String, Object>();		
			  map.put("jiraIds", list);
			  map.put("flag", QcConstant.JIRA_CONNECTED);
			  map.put("qcId", qcId);
			  service.updateJiraRelation(map);
			  result.setRetCode(Constant.SYS_SUCCESS);
			  return result;
		} catch (Exception e) {
		
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
			return result;
		}
		
	}
	
	
	/**
	 * 初始化批量创建质检单页面
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("/toAddQcBill")
	public String toAddQcBill(HttpServletRequest request,@RequestParam("jiraIds") String jiraIds){
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("jiraIds", Arrays.asList(jiraIds.split(",")));
		List<JiraBill> jiraList =  service.listJira(map);
		
		StringBuffer sb =new  StringBuffer();
		for(JiraBill jira : jiraList){
			sb.append(jira.getJiraName()).append(",");
		}
		
		request.setAttribute("jiraNames", sb.substring(0, sb.length()-1));
		request.setAttribute("jiraIds", jiraIds);
		request.setAttribute("falutSourceList", FaultSourceEnum.getSourceList());
		
		return "qc/qc_development/batchQcBillForm";
		
	}
}
