package com.tuniu.qms.qs.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.util.CellDataMapperHandler;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.common.util.POIUtil;
import com.tuniu.qms.qc.model.QcPointAttach;
import com.tuniu.qms.qc.service.QcPointAttachService;
import com.tuniu.qms.qs.dto.PunishPrdDto;
import com.tuniu.qms.qs.model.LowSatisfyDetail;
import com.tuniu.qms.qs.model.PunishPrd;
import com.tuniu.qms.qs.service.PunishPrdService;

@Controller
@RequestMapping("/qs/punishPrd")
public class PunishPrdController {
	
	private final static Logger logger = LoggerFactory.getLogger(PunishPrdController.class);
	
	@Autowired
	private PunishPrdService service;
	
	@Autowired
	private QcPointAttachService qcPointAttachService;
	
	@RequestMapping("/list")
	public String apply(PunishPrdDto dto, HttpServletRequest request) {
		
		
		dto.setNowYear(DateUtil.getYear(new Date()));  
		if(null==dto.getWeek() && null==dto.getYear()){
			
			dto.setYear(DateUtil.getYear(new Date()));
			dto.setWeek(DateUtil.getWeek(new Date()));
		}
		dto.setNowWeek(DateUtil.getWeek(new Date()));
		dto.setTriggerTimeFrom(DateUtil.formatAsDefaultDate(DateUtil.getFirstDayOfWeek(dto.getYear(), dto.getWeek())));
		dto.setTriggerTimeTo(DateUtil.formatAsDefaultDate(DateUtil.getFirstDayOfWeek(dto.getYear(), dto.getWeek()+1)));
		service.loadPage(dto);
		request.setAttribute("weekList",DateUtil.newWeekList());
		request.setAttribute("dto", dto);
		return "qs/punishPrd/punishPrdList";
	}
	
	
	@RequestMapping("/{id}/lowSatisfyDetail")
	public String lowSatisfyDetail(@PathVariable("id") Integer id, HttpServletRequest request){
		LowSatisfyDetail vo = service.getLowSatisfactionDetail(id);
		request.setAttribute("vo", vo);
		return "qs/punishPrd/lowsatisfy_detail";
	}
	
	@RequestMapping("/{id}/punishPrdDetail")
	public String punishPrdDetail(@PathVariable("id") Integer id, HttpServletRequest request){
		List<QcPointAttach> attachList = qcPointAttachService.getAttachList(id, Constant.ATTACH_BILL_TYPE_PUNISHPRD_ONLINE);
		
		PunishPrd prd = service.get(id);
		request.setAttribute("punishPrd", prd);
		request.setAttribute("attachList", attachList);
		
		return "qs/punishPrd/punishPrddetail";
	}
	
	@RequestMapping("/{id}/qcRemark")
	public String qcRemark(@PathVariable("id") Integer id, HttpServletRequest request){
		PunishPrd prd = service.get(id);
		
		request.setAttribute("prd", prd);
		
		return "qs/punishPrd/qcRemark";
	}
	
	@RequestMapping("/updatePassStatus")
	@ResponseBody
	public HandlerResult updatePassStatus(PunishPrdDto dto, HttpServletRequest request){
		HandlerResult result = HandlerResult.newInstance();
		
		try{
			User user = (User) request.getSession().getAttribute("loginUser");
			
			PunishPrd prd = new PunishPrd();
			prd.setPassOperPerson(user.getRealName());
			prd.setPassOperTime(new Date());
			prd.setStatus(5);
			prd.setId(new Integer(dto.getId()));
			prd.setRemark(dto.getRemark());
			
			service.update(prd);
			
			result.setRetCode(Constant.SYS_SUCCESS);
			result.setResMsg("放过成功");
		}catch(Exception e) {
			logger.info("放过失败："+e.getMessage(),e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("放过失败");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/chgPrdStatus")
    @ResponseBody
    public HandlerResult chgPrdStatus(PunishPrdDto dto, HttpServletRequest request) {
		
		HandlerResult result = HandlerResult.newInstance();
	    User user = (User) request.getSession().getAttribute("loginUser");
		StringBuilder mesg = new StringBuilder("");
		int flag = dto.getPrdStatus();
		if(flag==0){
			mesg.append("下线");
		}else{
			mesg.append("上线");
		}
		
		try {
			dto.setUpdatePerson(user.getRealName());
			dto.setUpdatePersonId(user.getId());
			boolean isSucc = service.chgPrdStatus(dto);
			
			if(isSucc==true){
				 result.setRetCode(Constant.SYS_SUCCESS);
				 result.setResMsg(mesg.append("成功").toString());
			}else{
				result.setRetCode(Constant.SYS_FAILED);
				result.setResMsg(mesg.append("失败").toString());
			}
			 
		} catch (Exception e) {
			
			logger.info("上下线失败："+e.getMessage(),e);
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(mesg.append("失败").toString());
		}
	    
	    return result;
	}
	
	/**
     * 导出个数限制
     * @param request
     * @param qcBill
     * @return
     */
    @RequestMapping( "/checkExport")
    @ResponseBody
    public HandlerResult checkExport(PunishPrdDto dto,HttpServletRequest request) {
        
        HandlerResult result = new HandlerResult();
      
        try {
        
        	dto.setNowYear(DateUtil.getYear(new Date()));  
    		if(null==dto.getWeek() && null==dto.getYear()){
    			
    			dto.setYear(DateUtil.getYear(new Date()));
    			dto.setWeek(DateUtil.getWeek(new Date()));
    		}
    		dto.setNowWeek(DateUtil.getWeek(new Date()));
    		dto.setTriggerTimeFrom(DateUtil.formatAsDefaultDate(DateUtil.getFirstDayOfWeek(dto.getYear(), dto.getWeek())));
    		dto.setTriggerTimeTo(DateUtil.formatAsDefaultDate(DateUtil.getFirstDayOfWeek(dto.getYear(), dto.getWeek()+1)));
    		
        	int count = service.exportCount(dto);
        	if(count<=5000){
        		
        		  result.setRetCode(Constant.SYS_SUCCESS);
        		  
        	}else{
        		
        		 result.setRetCode(Constant.SYS_FAILED);
                 result.setResMsg("导出数量不能超过五千条");
        	}
            
        } catch (Exception e) {
            
            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setResMsg("校验导出数量失败");
        }
        
        return result;
    }
    
    
    @RequestMapping("/exports")
    public String exports(PunishPrdDto dto,HttpServletRequest request,HttpServletResponse response){
        
    	dto.setNowYear(DateUtil.getYear(new Date()));  
		if(null==dto.getWeek() && null==dto.getYear()){
			
			dto.setYear(DateUtil.getYear(new Date()));
			dto.setWeek(DateUtil.getWeek(new Date()));
		}
		dto.setNowWeek(DateUtil.getWeek(new Date()));
		dto.setTriggerTimeFrom(DateUtil.formatAsDefaultDate(DateUtil.getFirstDayOfWeek(dto.getYear(), dto.getWeek())));
		dto.setTriggerTimeTo(DateUtil.formatAsDefaultDate(DateUtil.getFirstDayOfWeek(dto.getYear(), dto.getWeek()+1)));
		List<PunishPrd> list = service.exportList(dto);
       //导出数据
        exportData(list,response);
        request.setAttribute("dto", dto);
        return null;
    }
    
    private void  exportData( List<PunishPrd> list,HttpServletResponse response){
        try {
            String[] headers = new String[]{"触发时间","订单号","线路编号","线路名称", "事业部", "产品经理", "产品专员"/*, "供应商"*/,"下线类型","应下线次数", "实际下线次数", "下线时间", "上线时间","情况说明","状态"};
            String[] exportsFields = new String[]{"triggerTime","orderId","routeId","routeName","businessUnit","prdManager","prdOfficer"/*,"supplier"*/,"offlineType","offlineCount", "realOffLineCount", "offlineTime","onlineTime","remark","status"};
            
            String fileName = "prd_offline"+ (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
            new POIUtil<PunishPrd>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields),fileName, list,
            	new CellDataMapperHandler() {
				@Override
				public String mapToCell(String fieldName,
						Object value) {
					String textValue = "";
					if(value instanceof Date) {
                       Date date = (Date) value;
                       textValue = DateUtil.formatAsDefaultDate(date);
                       return textValue;
					}
					if ("offlineType".equals(fieldName)) {
						switch ((Integer) value) {
						case 1:
							textValue = "触红";
							break;
						case 2:
							textValue = "低满意度";
							break;
						case 3:
							textValue = "低质量";
							break;
						default:
							break;
						}
					} else if ("status".equals(fieldName)) {
						switch ((Integer) value) {
						case 1:
							textValue = "待整改";
							break;
						case 2:
							textValue = "整改中";
							break;
						case 3:
							textValue = "已整改";
							break;
						case 4:
							textValue = "永久下线";
							break;
						case 5:
							textValue = "已放过";
							break;
						default:
							break;
						}
					}else{
						textValue= value==null?"":value+"";
					}
					return textValue;
				}},
				response);
        } catch(Exception e) {
        	
        	logger.error(e.getMessage(), e);
        }
   }
    
    /**
     * 初始化附件
     * @param request
     * @return
     */
    @RequestMapping("{id}/toShowAttach")
    public String toShowAttach(HttpServletRequest request, @PathVariable("id")Integer id){
    	List<QcPointAttach> attachList = qcPointAttachService.getAttachList(id, Constant.ATTACH_BILL_TYPE_PUNISHPRD_ONLINE);
    	
		request.setAttribute("list", attachList);		
		request.setAttribute("punishPrdId", id);
		
        return "qs/punishPrd/attachList";
        
    }
}
