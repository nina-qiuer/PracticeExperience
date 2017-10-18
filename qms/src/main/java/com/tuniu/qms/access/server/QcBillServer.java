package com.tuniu.qms.access.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.RtxRemindService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QualityProblemDetailDto;
import com.tuniu.qms.qc.model.AirAndTrafficBill;
import com.tuniu.qms.qc.model.CmpAirAndTrafficBill;
import com.tuniu.qms.qc.model.CmpAndInnerQcBill;
import com.tuniu.qms.qc.model.CmpQcDetailResponse;
import com.tuniu.qms.qc.model.DevQcDetailResponse;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcPoint;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.QualityProblemDetail;
import com.tuniu.qms.qc.model.QualityProblemResponse;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.model.ResDevExportBill;
import com.tuniu.qms.qc.service.InnerQcBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.service.ResDevQcBillService;
import com.tuniu.qms.qc.util.QcConstant;

@Controller
@RequestMapping("/access/qcBill")
public class QcBillServer {
	
	private static final Logger logger = LoggerFactory.getLogger(QcBillServer.class);
	
	@Autowired
	private QcBillService service;
	
    @Autowired
    private ResDevQcBillService devBillService;
    
    @Autowired
    private InnerQcBillService innerService;
    
    @Autowired
    private DepartmentService  depService;
    
	@Autowired
	private QualityProblemTypeService qptService;
	
	@Autowired
	private RtxRemindService rtxRemindService;
	
	@Autowired
	private QcTypeService qcTypeService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		QcBill qc = server.getRestData(QcBill.class);
		java.sql.Date groupDate = DateUtil.parseSqlDate("1970-01-01");
		if(qc.getGroupDate().toString().equals(groupDate.toString())){
			
			qc.setGroupDate(null);
		}
		qc.setState(QcConstant.QC_BILL_STATE_WAIT);
		qc.setVerification("");
		service.add(qc);
		
		ResponseDto dto = new ResponseDto();
		dto.setData(qc.getId());
		server.writeResponse(dto);
	}
	
	@RequestMapping(value="/addQcRemark", method=RequestMethod.POST)
	public void addQcRemark(HttpServletRequest request, HttpServletResponse response) {
		// 在投诉单上添加质检备注，刷新数据时刷新
		RestServer server = new RestServer(request, response);
		QcBill qc = server.getRestData(QcBill.class);
		service.addQcRemark(qc);
		server.writeResponseDefault();
	}
	
    @RequestMapping(value = "/{qcBillId}/getTouchRedEmailContent", method = RequestMethod.GET)
    public void getTouchRedEmailContent(@PathVariable("qcBillId") Integer qcBillId, HttpServletRequest request, HttpServletResponse response) {
        // 在投诉单上添加质检备注，刷新数据时刷新
        RestServer server = new RestServer(request, response);
        String content = service.getCmpQcReportEmailContent(qcBillId);
        ResponseDto dto = new ResponseDto();
        dto.setData(content);
        server.writeResponse(dto);
    }

	/**
	 * 接收cmp推送的质检点数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addQcPoint", method=RequestMethod.GET)
	public void addQcPoint(HttpServletRequest request, HttpServletResponse response) {
		 
		RestServer server = new RestServer(request, response);
		QcPoint  qcPoint  = server.getRestData(QcPoint.class);
		service.addQcPoint(qcPoint);
		server.writeResponseDefault();
	}
	
	
	/**
	 * 接收cmp推送的点评数据进入内部质检
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addInnerQc", method=RequestMethod.GET)
	public void addInnerQc(HttpServletRequest request, HttpServletResponse response) {
		 
		RestServer server = new RestServer(request, response);
		QcBill  qcBill  = server.getRestData(QcBill.class);
		innerService.addInnerQc(qcBill);
		
		RtxRemind  rtxRemind = new RtxRemind();
		rtxRemind.setUid("2966");//薛桂霞
		rtxRemind.setTitle(qcBill.getAddPerson()+"发起了点评修改");
		rtxRemind.setContent("订单号["+qcBill.getOrdId()+"]的投诉单需要点评修改");
		rtxRemind.setSendTime(new Date());
		rtxRemind.setAddPerson(qcBill.getAddPerson());
		rtxRemindService.add(rtxRemind);
		
		server.writeResponseDefault();
	}
	
	/**
	 * 对客赔付金额不为0并且不全是质量工具赔款，并且投诉事宜分类不是“不成团”、存在理论赔付的投诉单，进入质检列表
	 *  1. 对客有赔偿，并且对客赔偿金额 不全是质量工具赔款，并且投诉事宜分类  一级-二级 ！= ”旅行前咨询 - 不成团“
     *  2. 投诉单存在理论赔付发质检
     *  3. 改进报告发质检
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/sendToQms", method=RequestMethod.POST)
	public void sendToQms(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		ResponseDto dto = new ResponseDto();
		try{
			QcBill qc = server.getRestData(QcBill.class);
			QcBillDto qdto = new QcBillDto();
			qdto.setCmpId(qc.getCmpId());
			QcBill qcBill = service.getQcBill(qdto);
			Integer qcId = 0;
		    if(null == qcBill){
		    	
		    	java.sql.Date groupDate = DateUtil.parseSqlDate("1970-01-01");
				if(qc.getGroupDate().toString().equals(groupDate.toString())){
					
					qc.setGroupDate(null);
				}
				qc.setState(QcConstant.QC_BILL_STATE_WAIT);
				qc.setVerification("");
				service.add(qc);
				qcId = qc.getId();
		    }else{
		    	qcId = qcBill.getId();
		    }
		    
		    logger.info(qcId.toString());
			dto.setData(qcId);
		}catch(Exception e){
			dto.setSuccess(false);
			dto.setMsg("质检生成失败！");
			logger.error("质检生成失败 ", e);
		}
		
		server.writeResponse(dto);
	}
	
	
	/**
	 * 获取导游所有质量问题明细信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getGuideQualityProblemInfo", method=RequestMethod.GET)
	public void getGuideQualityProblemInfo(HttpServletRequest request, HttpServletResponse response) {
	
		ResponseDto dto = new ResponseDto();
		RestServer server = new RestServer(request, response);
		try {
		
			QualityProblemDetailDto detailDto = server.getRestData(QualityProblemDetailDto.class);
			if(null==detailDto.getGuideId() || "".equals(detailDto.getGuideId())){
				
				dto.setSuccess(false);
				dto.setMsg("导游ID不能为空");
				server.writeResponse(dto);
				return;
			}
			List<QualityProblemDetail> list = service.getGuideDetail(detailDto);
		    Integer count = service.getGuideCount(detailDto);
		    QualityProblemResponse qpResponse = new QualityProblemResponse();
		    qpResponse.setRows(list);
		    qpResponse.setCount(count);
			dto.setData(qpResponse);
		
			
		} catch (Exception e) {
			e.printStackTrace();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
		}
		
		server.writeResponse(dto);
	}

	

	/**
	 * 获取导游质量问题统计信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getGuideQualityProblemStat", method=RequestMethod.GET)
	public void getGuideQualityProblemStat(HttpServletRequest request, HttpServletResponse response) {
	
		ResponseDto dto = new ResponseDto();
		RestServer server = new RestServer(request, response);
		try {
		
			QualityProblemDetailDto detailDto = server.getRestData(QualityProblemDetailDto.class);
			if(null==detailDto.getGuideId() || "".equals(detailDto.getGuideId())){
			
				dto.setSuccess(false);
				dto.setMsg("导游ID不能为空");
				server.writeResponse(dto);
				return;
			}
		    Map<String, Object> count = service.getGuideSateCount(detailDto);
			dto.setData(count);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
		}
		
		server.writeResponse(dto);
	}
	
	
	/**
	 * 获取研发质检数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getDevQcDetail", method=RequestMethod.GET)
	public void getDevQcDetail(HttpServletRequest request, HttpServletResponse response) {
	
		ResponseDto dto = new ResponseDto();
		RestServer server = new RestServer(request, response);
		try {
		
			QcBillDto detailDto = server.getRestData(QcBillDto.class);
			if(null==detailDto.getStatisticDate()|| "".equals(detailDto.getStatisticDate())){
			
				dto.setSuccess(false);
				dto.setMsg("统计日期不能为空");
				server.writeResponse(dto);
				return;
			}
			detailDto.setFinishTimeBgn(detailDto.getStatisticDate());
			detailDto.setFinishTimeEnd(detailDto.getStatisticDate());
			detailDto.setState(QcConstant.QC_BILL_STATE_FINISH);
		    List<ResDevExportBill> qcList = devBillService.exportList(detailDto);
		    List<QualityProblemType> list = qptService.getQpTypeDataCache(Constant.QC_DEVELOP);
		
		    List<AirAndTrafficBill> airlist  = getListByDev(qcList,list);
		    DevQcDetailResponse devResponse = new DevQcDetailResponse();
		    devResponse.setRows(airlist);
			dto.setData(devResponse);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
		}
		
		server.writeResponse(dto);
	}
	
	
	private List<AirAndTrafficBill> getListByDev( List<ResDevExportBill> qcList,    List<QualityProblemType> list ){
		
		List<AirAndTrafficBill> airList =new ArrayList<AirAndTrafficBill>();
	    for(ResDevExportBill qcBill : qcList){
        	
	    	   if(null!= qcBill.getPunishDepId()){
	    		   
	    		   String depName =depService.getDepFullNameById(qcBill.getPunishDepId());
	    		   qcBill.setDepName(depName);
	    	   }else{
	    		   
	    		   if(null!=qcBill.getDepId()){
	    			   
	    			   String depName =depService.getDepFullNameById(qcBill.getDepId());
		    		   qcBill.setDepName(depName);
	    		   }
	    	   }
	    	   
	    	   
	    	   if(null!=qcBill.getQptId()){
	    		   
	    	     String fullName =  qptService.getQpFullNameById(qcBill.getQptId(),list);
	       		 qcBill.setQptName(fullName);
	    	   }
	    		
	        	qcBill.setWeek(DateUtil.getYearAndWeek(qcBill.getFinishTime()));
	        	qcBill.setMonth(DateUtil.getYearAndMonth(qcBill.getFinishTime()));
	        	//（S级加权25、A级加权25、B级加权5，C级1、非研发故障0） * 影响时长）
	        	if(QcConstant.QUALITY_PROBLEM_S.equals(qcBill.getQualityEventClass())){//S级
	        		
	        		qcBill.setFailureScore(25*qcBill.getInfluenceTime());
	        		
	        	}
				if(QcConstant.QUALITY_PROBLEM_A.equals(qcBill.getQualityEventClass())){//A级
				        		
					qcBill.setFailureScore(25*qcBill.getInfluenceTime());
				}
				if(QcConstant.QUALITY_PROBLEM_B.equals(qcBill.getQualityEventClass())){//B级
					
					qcBill.setFailureScore(5*qcBill.getInfluenceTime());
				}
				if(QcConstant.QUALITY_PROBLEM_C.equals(qcBill.getQualityEventClass())){//C级
	        		
					qcBill.setFailureScore(1*qcBill.getInfluenceTime());
	        	}
				if(QcConstant.NO_QUALITY_PROBLEM.equals(qcBill.getQualityEventClass())){//非研发故障
	        		
					qcBill.setFailureScore(0);
	        	}
	        }
	    
	   if (null != qcList  && qcList.size()>0) {
			for(int i= 0;i<qcList.size();i++){
				
				  if(isRemove(qcList.get(i).getDepName())){
			    	   
			    	    qcList.remove(i);
						i--;
			       }
					
				}
		}
		 for(ResDevExportBill devBill: qcList){
			 
			 AirAndTrafficBill bill = new AirAndTrafficBill();
			 bill.setQcId(devBill.getQcId());
			 bill.setGroupFlag(devBill.getGroupFlag());
			 bill.setQcPerson(devBill.getQcPerson());
			 bill.setWeek(devBill.getWeek());
			 bill.setMonth(devBill.getMonth());
			 bill.setFaultSource(devBill.getFaultSource()==null?"":devBill.getFaultSource());
			 bill.setQcAffairSummary(devBill.getQcAffairSummary()==null?"":devBill.getQcAffairSummary());
			 bill.setQualityEventClass(devBill.getQualityEventClass());
			 bill.setInfluenceTime(devBill.getInfluenceTime()==null?0:devBill.getInfluenceTime());
			 bill.setInfluenceSystem(devBill.getInfluenceSystem()==null?"":devBill.getInfluenceSystem());
			 bill.setInfluenceResult(devBill.getInfluenceResult()==null?"":devBill.getInfluenceResult());
			 bill.setQptName(devBill.getQptName()==null?"":devBill.getQptName());
			 bill.setTreMeasures(devBill.getTreMeasures()==null?"":devBill.getTreMeasures());
			 bill.setRespPersonName(devBill.getRespPersonName());
			 bill.setDepName(devBill.getDepName()==null?"":devBill.getDepName());
			 bill.setCauseAnalysis(devBill.getCauseAnalysis()==null?"":devBill.getCauseAnalysis());
			 bill.setRespSystem(devBill.getRespSystem()==null?"":devBill.getRespSystem()); 
			 bill.setImpMeasures(devBill.getImpMeasures()==null?"":devBill.getImpMeasures());
			 bill.setFailureScore(devBill.getFailureScore());
			 bill.setScorePunish(devBill.getScorePunish());
			 bill.setEconomicPunish(devBill.getEconomicPunish());
			 bill.setFinishTime(DateUtil.formatAsDefaultDateTime(devBill.getFinishTime()));
			 airList.add(bill);
		 }
		
		return airList;
	}
	/**
	 * 获取投诉质检、内部质检数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getCmpAndInnerQcDetail", method=RequestMethod.GET)
	public void getCmpAndInnerQcDetail(HttpServletRequest request, HttpServletResponse response) {
	
		ResponseDto dto = new ResponseDto();
		RestServer server = new RestServer(request, response);
		try {
		
			QcBillDto detailDto = server.getRestData(QcBillDto.class);
			if(null==detailDto.getStatisticDate()|| "".equals(detailDto.getStatisticDate())){
			
				dto.setSuccess(false);
				dto.setMsg("统计日期不能为空");
				server.writeResponse(dto);
				return;
			}
			
			detailDto.setFinishTimeBgn(detailDto.getStatisticDate());
			detailDto.setFinishTimeEnd(detailDto.getStatisticDate());
			detailDto.setState(QcConstant.QC_BILL_STATE_FINISH);
			
			//投诉质检
			 detailDto.setGroupFlag(Constant.QC_COMPLAINT);
			 List<CmpAndInnerQcBill> cmpList = service.getAirAndTraffic(detailDto);
			 List<QualityProblemType> qptCmplist = qptService.getQpTypeDataCache(Constant.QC_COMPLAINT);
			 List<CmpAirAndTrafficBill> cmplist  = getListByCmp(cmpList,qptCmplist);
			
			 //内部质检
			 detailDto.setGroupFlag(Constant.QC_INNER);
			 List<CmpAndInnerQcBill> innerList = service.getAirAndTraffic(detailDto);
			 List<QualityProblemType> qptInnerList = qptService.getQpTypeDataCache(Constant.QC_INNER);
			 List<CmpAirAndTrafficBill> innerlist  = getListByCmp(innerList,qptInnerList);
			 
			 cmplist.addAll(innerlist);
			 
			 CmpQcDetailResponse cmpResponse = new CmpQcDetailResponse();
			 cmpResponse.setRows(cmplist);
		     dto.setData(cmpResponse);
				
		} catch (Exception e) {
			
			e.printStackTrace();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
		}
		
		server.writeResponse(dto);
	}

	private List<CmpAirAndTrafficBill> getListByCmp( List<CmpAndInnerQcBill> qcList, List<QualityProblemType> list ){
		
		List<CmpAirAndTrafficBill> airList =new ArrayList<CmpAirAndTrafficBill>();
	    for(CmpAndInnerQcBill qcBill : qcList){
        	
	    	   if(null!= qcBill.getPunishDepId()){
	    		   
	    		   String depName =depService.getDepFullNameById(qcBill.getPunishDepId());
	    		   qcBill.setDepName(depName);
	    	   }else{
	    		   
	    		   if(null!=qcBill.getDepId()){
	    			   
	    			   String depName =depService.getDepFullNameById(qcBill.getDepId());
		    		   qcBill.setDepName(depName);
	    		   }
	    	   }
	    	   
	    	   if(null!=qcBill.getQptId()){
	    		   
	    	     String fullName =  qptService.getQpFullNameById(qcBill.getQptId(),list);
	       		 qcBill.setQptName(fullName);
	    	   }
	    		
	        	qcBill.setWeek(DateUtil.getYearAndWeek(qcBill.getFinishTime()));
	        	qcBill.setMonth(DateUtil.getYearAndMonth(qcBill.getFinishTime()));
	        	
	        }
	    
	    if (null != qcList  && qcList.size()>0) {
			for(int i= 0;i<qcList.size();i++){
				
			       if(isRemove(qcList.get(i).getDepName())){
			    	   
			    	    qcList.remove(i);
						i--;
			       }
					
				}
		}
		 for(CmpAndInnerQcBill cmpBill: qcList){
			 
			 CmpAirAndTrafficBill bill = new CmpAirAndTrafficBill();
			 bill.setQcId(cmpBill.getQcId());
			 bill.setGroupFlag(cmpBill.getGroupFlag());
			 bill.setQcPerson(cmpBill.getQcPerson());
			 bill.setWeek(cmpBill.getWeek());
			 bill.setMonth(cmpBill.getMonth());
			 bill.setOrderId(cmpBill.getOrderId());
			 bill.setPrdId(cmpBill.getPrdId());
			 bill.setQcAffairSummary(cmpBill.getQcAffairSummary()==null?"":cmpBill.getQcAffairSummary());
			 bill.setDescription(cmpBill.getDescription()==null?"":cmpBill.getDescription());
			 bill.setImpAdvice(cmpBill.getImpAdvice()==null?"":cmpBill.getImpAdvice());
			 bill.setQptName(cmpBill.getQptName()==null?"":cmpBill.getQptName());
			 bill.setRespPersonName(cmpBill.getRespPersonName());
			 bill.setDepName(cmpBill.getDepName()==null?"":cmpBill.getDepName());
			 bill.setScorePunish(cmpBill.getScorePunish());
			 bill.setEconomicPunish(cmpBill.getEconomicPunish());
			 bill.setFinishTime(DateUtil.formatAsDefaultDateTime(cmpBill.getFinishTime()));
			 airList.add(bill);
		 }
		
		return airList;
	}
	
	/**
	 * 是否移除 
	 * @param depName
	 * @return   判断部门是否包含  ：机票事业部、交通运营中心、火车票BU、汽车票BU、旅游用车BU，包含其中则不移除
	 */
	private boolean isRemove(String depName) {
		if(!depName.contains(QcConstant.AIR_UNIT) && !depName.contains(QcConstant.TRAFFIC_CENTRE) 
				&& !depName.contains(QcConstant.TRAIN_TICKET_BU) && !depName.contains(QcConstant.BUS_TICKET_BU) 
				&& !depName.contains(QcConstant.TRAVEL_CAR_BU)) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 低满意度发质检接口
	 * 报文：{"prdId":2,"ordId":63,"groupDate":"2016-09-11","qcAffairDesc":"点评","remark":"其他","addPersonId":1,"addPerson":"张"}
	 * 返回值：{"data":25389,"errorCode":0,"msg":"Success","success":true}
	 */
	@RequestMapping(value="/addNoComplaintQcBill", method = RequestMethod.POST)
	public void addNoComplaintQcBill(HttpServletRequest request, HttpServletResponse response){
		RestServer server = new RestServer(request, response);
		QcBill qc = server.getRestData(QcBill.class);
		
		QcType qcType = qcTypeService.getTypeName("点评低满意度质检");
		// 投诉质检组
		qc.setGroupFlag(1);
		//质检类型   点评低满意度质检类型
		if(qcType != null){
			qc.setQcTypeId(qcType.getId());
		}else{
			qc.setQcTypeId(2);
		}
		//质检概述
		qc.setQcAffairSummary("");
		//质检单状态
		qc.setState(QcConstant.QC_BILL_STATE_WAIT);
		//核实情况
		qc.setVerification("");
		//备注
		qc.setRemark("");
		
		//质检单添加
		service.add(qc);
		
		//请求返回值
		ResponseDto dto = new ResponseDto();
		dto.setData(qc.getId());
		server.writeResponse(dto);
	}
	
}
