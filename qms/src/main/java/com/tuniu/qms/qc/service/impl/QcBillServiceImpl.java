package com.tuniu.qms.qc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.OrderBill;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.ComplaintBillService;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.service.OrderBillService;
import com.tuniu.qms.common.service.ProductService;
import com.tuniu.qms.common.service.RtxRemindService;
import com.tuniu.qms.common.service.SyncyTaskService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CollectionUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dao.InnerPunishBasisMapper;
import com.tuniu.qms.qc.dao.InnerPunishBillMapper;
import com.tuniu.qms.qc.dao.InnerRespBillMapper;
import com.tuniu.qms.qc.dao.OuterPunishBasisMapper;
import com.tuniu.qms.qc.dao.OuterPunishBillMapper;
import com.tuniu.qms.qc.dao.OuterRespBillMapper;
import com.tuniu.qms.qc.dao.QcBillMapper;
import com.tuniu.qms.qc.dao.QcPointAttachMapper;
import com.tuniu.qms.qc.dao.QualityProblemMapper;
import com.tuniu.qms.qc.dao.UpLoadAttachMapper;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.QualityProblemDetailDto;
import com.tuniu.qms.qc.dto.QualityProblemDto;
import com.tuniu.qms.qc.model.CmpAndInnerQcBill;
import com.tuniu.qms.qc.model.InnerPunishBasis;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.InnerRespBill;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.OuterRespBill;
import com.tuniu.qms.qc.model.PunishStandard;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcPoint;
import com.tuniu.qms.qc.model.QcPointAttach;
import com.tuniu.qms.qc.model.QcVo;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.model.QualityProblemDetail;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.QcBillRelationService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.qs.dao.QualityCostAuxAccountMapper;
import com.tuniu.qms.qs.dao.QualityCostLedgerMapper;
import com.tuniu.qms.qs.model.PunishPrd;
import com.tuniu.qms.qs.model.QualityCostLedger;
import com.tuniu.qms.qs.service.PunishPrdService;
import com.tuniu.qms.qs.util.QsConstant;
import com.tuniu.qms.report.dao.QualityProblemReportMapper;
import com.tuniu.qms.report.model.QualityProblemReport;

@Service
public class QcBillServiceImpl implements QcBillService {
	
	@Autowired
	private QcBillMapper mapper;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private RtxRemindService rtxRemindService;
	
	@Autowired
	private QualityProblemMapper qpMapper;
	
	@Autowired
	private InnerRespBillMapper innerMapper;
	
	@Autowired
	private OuterRespBillMapper outerMapper;
	
	@Autowired
	private UpLoadAttachMapper upMapper;
	
	@Autowired
	private InnerPunishBillMapper innerPunishMapper;
	
	@Autowired
	private OuterPunishBillMapper outerPunishMapper;
	
	@Autowired
	private InnerPunishBasisMapper innerBasisMapper;
	
	@Autowired
	private OuterPunishBasisMapper outerBasisMapper;
	
	@Autowired
	private QcPointAttachMapper qcPointAttachMapper;
	
    @Autowired
    private MailTaskService mailTaskService;
    
    @Autowired
    private InnerPunishBillService innerPunishService;
    
    @Autowired
    private DepartmentService  depService;
    
    @Autowired
    private OrderBillService orderBillService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private QualityProblemService qualityProblemService;
    
    @Autowired
    private OuterPunishBillService outerPunishService;
	
    @Autowired
    private QualityCostLedgerMapper ledgerMapper;
    
    @Autowired
    private QualityCostAuxAccountMapper auxMapper;
    
    @Autowired
    private QualityProblemTypeService qptService;
	
	@Autowired
	private OperationLogService operLogService;
	
	@Autowired
	private JobService jobService;
	
    @Autowired
    private QualityProblemReportMapper qptMapper;
    
    @Autowired
    private PunishPrdService punishPrdService;
    
    @Autowired
	private QcPunishSegmentTaskService qcPunishSegmentTaskService;
    
    @Autowired
    private ComplaintBillService complaintBillService;
    
    @Autowired
    private QcBillRelationService qcBillRelationService;
    
    @Autowired
    private SyncyTaskService syncyTaskService;
    
	@Override
	public void add(QcBill qc) {
		mapper.add(qc);
		
		productService.addPrdSyncTask(qc.getPrdId());
		
		orderBillService.addOrdSyncTask(qc.getOrdId());
		
		complaintBillService.addCmpSyncTask(qc.getCmpId());
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QcBill qc) {
		mapper.update(qc);
	}

	@Override
	public QcBill get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<QcBill> list(QcBillDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcBillDto dto) {
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
	}


	@Override
	public void updateQcPerson(List<Integer> qcBillIds, String assignTo,User operUser) {
		QcBill qcBill = null;
		User user = userService.getUserByRealName(assignTo);
		String oldQcPerson = "";
		//1.遍历待分配质检单列表
		for (Integer qcBillId : qcBillIds) {
			qcBill = this.get(qcBillId);
			oldQcPerson = qcBill.getQcPerson();
			//1.1分配质检员
			qcBill.setQcPerson(assignTo);
			qcBill.setQcPersonId(user.getId());
			qcBill.setUpdatePerson(operUser.getRealName());
			//1.2变更质检状态
			qcBill.setState(3);
			//1.3设置分配时间
			qcBill.setDistribTime(new Date());
			qcBill.setQcPeriodBgnTime(null);
			qcBill.setQcPeriodEndTime(null);
			qcBill.setTimeFlag(1);
			this.update(qcBill);
			
			OperationLog log = new OperationLog();
			log.setDealPeople(operUser.getId());
			log.setDealPeopleName(operUser.getRealName());
			log.setQcId(qcBill.getId());
			log.setFlowName("分配质检单");
			log.setDealDepart(operUser.getRole().getName());
			log.setContent("质检单："+ qcBill.getId()+"，分配给："+qcBill.getQcPerson());
			operLogService.add(log);
			
			if(operUser.getRole().getType()==1){//基础员工
				// 发送RTX提醒给经理
				User manager = OaClient.getReporter(oldQcPerson);
				if(!StringUtils.isEmpty(manager.getRealName())){
					RtxRemind  rtxRemind = new RtxRemind();
					rtxRemind.setUid(manager.getId().toString());
					rtxRemind.setTitle("质检单工作交接["+qcBill.getId()+"]");
					rtxRemind.setContent(oldQcPerson+"交接给"+assignTo);
					rtxRemind.setSendTime(new Date());
					rtxRemind.setAddPerson(oldQcPerson);
					rtxRemindService.add(rtxRemind);
				}
			}
		}
		
	}


	@Override
	public void backToProcessing(Integer id) {
		QcBill qcBill = this.get(id);
		
		qcBill.setState(QcConstant.QC_BILL_STATE_QCBEGIN);
		
		this.update(qcBill);
	}
	/**
	 * 撤销质检单
	 * @param qcBill
	 * @return
	 */
	public void deleteQcBill(QcBill qcBill) {
			
		List<QualityProblem> qpList =  qpMapper.getQpByQcId(qcBill.getId());
		this.update(qcBill);//更新质检单状态
		for(QualityProblem qp :qpList){
		
			if(qp!=null&&qp.getId()>0){
		
				outerMapper.deleteOutRespBill(qp.getId());//删除质量问题单对应的外部责任单
				innerMapper.deleteInnerRespBill(qp.getId());//删除质量问题单对应的外部责任单
				upMapper.deleteAttach(qp.getId());//删除质量问题对应的附件
				qpMapper.delete(qp.getId());//删除质量问题单
			}  
		
		}
		
		innerPunishMapper.deleteInnerPunishBill(qcBill.getId());//删除内部处罚单
		outerPunishMapper.deleteOuterPunishBill(qcBill.getId());//删除外部处罚单
		
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("qcId",qcBill.getId());
		
		List<Integer> innerList = innerPunishMapper.getByQcId(map);
		List<Integer> outerList = outerPunishMapper.getByQcId(map);
		map.put("innerList", innerList);
		map.put("outerList", outerList);
		if(innerList.size()>0){
			innerBasisMapper.deletePunishByIpb(map);//删除内部处罚单对应的处罚依据
		}
		if(outerList.size()>0){
			outerBasisMapper.deletePunishByOpb(map);//删除外部处罚单对应的处罚依据
		}
	}

	@Override
	public void addQcRemark(QcBill qc) {
		mapper.addQcRemark(qc);
	}

	@Override
	public QcBill getComplaintLevel(Integer qcId) {
		return mapper.getComplaintLevel(qcId);
	}

	/**
	 * 修改投诉等级
	 */
	public void updateCmpLevel(QcBill qcBill) {
		mapper.updateCmpLevel(qcBill);
	}

	@Override
	public void listNormalQcJob(QcBillDto dto) {
		dto.setDataList(mapper.listNormalQcJob(dto));
	}
	
	@Override
	public List<QcBill> listWaitDistrib() {
		return mapper.listWaitDistrib();
	}
	
	@Override
	public List<QcBill> listWaitDistribNonOrd() {
		return mapper.listWaitDistribNonOrd();
	}
	
	/**
	 * 保存质检点相关信息
	 */
	public void addQcPoint(QcPoint qcPoint) {
		
		QcVo qcVo  = qcPoint.getQcVo();
		
		QcBillDto dto = new QcBillDto();
		dto.setCmpId(qcPoint.getComplaintId());
		QcBill qcBill = mapper.getQcBill(dto);
		
		if(null == qcPoint.getQcPoint() || ("").equals(qcPoint.getQcPoint())){  //撤销质检点
			
			if(null != qcBill){
				qcBill.setUserFlag(QcConstant.USER_FLAG_FALSE);
				//更新 质检单状态为非人工
				mapper.update(qcBill);
			}
			
		}else{
			
			if(null != qcBill){
				qcBill.setQcAffairDesc(qcPoint.getQcPoint());
				qcBill.setEvidence(qcPoint.getEvidence());
				
				mapper.update(qcBill);
				
				qcPointAttachMapper.deleteAttach(qcBill.getId());
			}else{
				
				qcBill =new QcBill();
				qcBill.setGroupFlag(QcConstant.QC_COMPLAINT); 
				qcBill.setPrdId(qcVo.getPrdId());
				qcBill.setOrdId(qcVo.getOrdId());
				qcBill.setCmpId(qcPoint.getComplaintId());
				if(null != qcVo.getGroupDate() && 0 != qcVo.getGroupDate()){
					qcBill.setGroupDate(DateUtil.getLongTime(qcVo.getGroupDate()));
				}else{
					qcBill.setGroupDate(null);
				}
				qcBill.setAddPersonId(qcPoint.getUpdatePersonId());
				qcBill.setAddPerson(qcPoint.getUpdatePerson());
				qcBill.setUserFlag(QcConstant.USER_FLAG_TRUE);
				qcBill.setQcTypeId(QcConstant.COMPLAINT_QC_TYPE_COMMON); 
				qcBill.setQcAffairSummary("客人投诉");
				qcBill.setRemark("");
				qcBill.setEvidence(qcPoint.getEvidence());
				qcBill.setQcAffairDesc(qcPoint.getQcPoint());
				qcBill.setDelFlag(Constant.NO);
				qcBill.setState(qcPoint.isSystemProblemFlag() ? QcConstant.QC_BILL_STATE_CANCEL : QcConstant.QC_BILL_STATE_WAIT);
				
				this.add(qcBill);
				
				if(qcPoint.isSystemProblemFlag()){//系统问题  质检单为已撤销，模拟转研发质检
					qcBillRelationService.addRelationBill(qcPoint, qcBill.getId());
					
					StringBuffer str = new StringBuffer("投诉质检单：").append(qcBill.getId()).append("，由robot自动操作进入研发质检");
					operLogService.addQcOperationLog(qcBill.getId(), null, "质检已撤销", str.toString());
				}
			}
			
			for(QcPointAttach attach : qcPoint.getAttachList() ){
				
				attach.setQcId(qcBill.getId());
				attach.setDelFlag(QcConstant.NO);
				qcPointAttachMapper.add(attach);
			}

		}
	}

	@Override
	public QcBill getQcBill(QcBillDto dto){
	
		return mapper.getQcBill(dto);
	}

    @Override
    public void updateCmpQcReportEmail(Integer qcId,String reEmails,String ccEmails, String subject, User user,String qcState) {
    	
    	int state = Integer.parseInt(qcState);
        QcBill qcBill = get(qcId);
        qcBill.setSubject(subject);
        qcBill.setReAddrs(reEmails);
        qcBill.setCcAddrs(ccEmails);
        OperationLog log = new OperationLog();
      	log.setDealPeople(user.getId());
      	log.setDealPeopleName(user.getRealName());
      	log.setQcId(qcId);
      	log.setDealDepart(user.getRole().getName());
        if(QcConstant.QC_BILL_STATE_WAITFINISH == state){
        	
        	 qcBill.setState(QcConstant.QC_BILL_STATE_WAITFINISH);
        	 log.setFlowName("质检已待结");
           	 log.setContent("质检单："+qcId+"，已待结");
           	 
        }else if(QcConstant.QC_BILL_STATE_AUDIT == state){
        	
        	 qcBill.setState(QcConstant.QC_BILL_STATE_AUDIT);
        	 log.setFlowName("转入审核中");
           	 log.setContent("质检单："+qcId+"，进入审核中");
        	
        }else {
        	
	       	 qcBill.setState(QcConstant.QC_BILL_STATE_FINISH);
	       	 log.setFlowName("质检已完成");
	         log.setContent("质检单："+qcId+"，已完成");
	         qcBill.setFinishTime(new Date());
	         //质量成本台账  +质量成本辅助账
	         addCostLedgerAndAux(qcBill,user);
	          //质量问题切片报表
	         addQualityProblemReport(qcBill,user);
	         //添加处罚单数据添加切片任务
	         qcPunishSegmentTaskService.builTask(qcId, 1);
	         
	         if(qcBill.getCmpId() > 0 && outerPunishService.getPunishIsExistByqcId(qcBill.getId())){
	        	 syncyTaskService.builTask(qcBill.getCmpId(), QcConstant.SYNCY_DATA_TYPE_COMPLAINT);
	         }
       }
       
      	operLogService.add(log);
        
        update(qcBill);
        
        if(QcConstant.QC_BILL_STATE_AUDIT != state){//审核中不进行邮件以及其他操作
        	
	        MailTaskDto mailTaskDto = new MailTaskDto();
	        //是否有投诉 质检报告
	        if(qcBill.getCmpId() > 0){
	        	mailTaskDto.setTemplateName("cmpQcReport.ftl");
	        }else{
	        	mailTaskDto.setTemplateName("noCmpQcReport.ftl");
	        }
	        mailTaskDto.setSubject(subject);
	        mailTaskDto.setReAddrs(reEmails.split(";"));
	        mailTaskDto.setCcAddrs(ccEmails.split(";"));
	        mailTaskDto.setDataMap(getQcReportContentDataMap(qcId));
	        
	        mailTaskDto.setAddPersonRoleId(user.getRole().getId());
	        mailTaskDto.setAddPerson(user.getRealName());
	        mailTaskService.addTask(mailTaskDto);
	        
	        //向投诉质检系统推送处罚单数据
	        //pushPunishDataToCmp(qcId);
	        
	        //判断是否触红
	        Map<String,Object>  dataMap = mailTaskDto.getDataMap();
	        InnerPunishBill innerPunishBill = getTouchRedInnerPunishBill(dataMap);
	        if(innerPunishBill != null) { //触红处理
	        	
	            QcBill newQcBill = (QcBill) dataMap.get("qcBill");
	            if(newQcBill.getOrdId()>0){
	            	
	            	PunishPrd prd =new PunishPrd();
	   	            prd.setOrderId( newQcBill.getOrdId());
	   	            
	   	            OrderBill orderBill = (OrderBill) dataMap.get("orderBill");
	   	            prd.setTravelDateBgn(newQcBill.getGroupDate());
	   	            prd.setPrdManager(orderBill.getPrdManager());
	   	            prd.setPrdOfficer(orderBill.getProducter());
	   	            
	   	            Product product = (Product) dataMap.get("product");
	   	            prd.setRouteName(product.getPrdName());
	   	            prd.setBusinessUnit(product.getBusinessUnitName());
	   	            prd.setRouteId(newQcBill.getPrdId());
	   	            prd.setSupplier("");
	   	            prd.setQcId(qcId);
	   	            prd.setCmpId(newQcBill.getCmpId());
	   	            punishPrdService.touchRedDeal(prd);
	            }
	         
	        }
        }
    }
    
   
    private int checkRedLine(Integer qcId){
    	
    	 QualityProblemDto dto =new QualityProblemDto();
		 dto.setQcId(qcId);
		 List<QualityProblem> qplist = qualityProblemService.list(dto);
		 List<QualityProblemType> listType = new ArrayList<QualityProblemType>();
	     listType =  qptService.getQpTypeDataCache(Constant.QC_COMPLAINT);
	     for(QualityProblem qp: qplist){
			  
	    	   if(qp.getLowSatisDegree()==0){
	    		   
		    	   QualityProblemType qpType = qptService.getQpType(qp.getQptId(), listType);
				   if(qpType!=null){
					   
					   if(qpType.getFullName().startsWith("供应商问题") && qpType.getTouchRedFlag() ==1){
						   
						   return 1;
					   }
				   }
	    	   }
	      }
    	return 0;
    	
    }

    private void addQualityProblemReport(QcBill qcBill,User user){
    	
    	try {
    		  List<QualityProblemReport> list = new ArrayList<QualityProblemReport>();  
        	  List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(qcBill.getId(),Constant.QC_COMPLAINT);
        	  Job job = jobService.getJobByName(QcConstant.PRODUCT_MANAGER);
              
        	  
        	  for (QualityProblem qp : qualityProblemlist){
       
           	     String qptName  = qp.getQptName();
           	     String []qptNames =  qptName.split("-");
       			 QualityProblemType type = qptService.getQpTypeFullName(qptName);
       	 	    //内部责任单
       			 if((qp.getInnerList()==null || qp.getInnerList().size()==0)&& (qp.getOuterList()==null || qp.getOuterList().size()==0)){
       				 
       				 QualityProblemReport qpReport = new QualityProblemReport();
       				 qpReport.setQcId(qcBill.getId());
           			 qpReport.setQpFlag(QcConstant.RESP_FALG);
           			 qpReport.setTouchRedFlag(type.getTouchRedFlag());
           			 if(qptNames.length==1){
           				
           				 qpReport.setFirstQpTypeId(type.getId());
           				 qpReport.setFirstQpTypeName(qptNames[0]);
           				 qpReport.setSecondQpTypeId(0);
           				 qpReport.setSecondQpTypeName("");
           				 qpReport.setThirdQpTypeId(0);
            			 qpReport.setThirdQpTypeName("");
           			 }
           			 if(qptNames.length==2){
           				 
           			    qpReport.setFirstQpTypeId(type.getPid());
           			    qpReport.setFirstQpTypeName(qptNames[0]);
           		    	qpReport.setSecondQpTypeId(type.getId());
           				qpReport.setSecondQpTypeName(qptNames[1]);
           				qpReport.setThirdQpTypeId(0);
           				qpReport.setThirdQpTypeName("");
           			 }
           			 if(qptNames.length>2){
           				 
           				QualityProblemType firstType = qptService.get(type.getPid());
           			    qpReport.setFirstQpTypeId(firstType.getPid());
           				qpReport.setFirstQpTypeName(qptNames[0]);
           		    	qpReport.setSecondQpTypeId(type.getPid());
           				qpReport.setSecondQpTypeName(qptNames[1]);
           			    qpReport.setThirdQpTypeId(type.getId());
           				qpReport.setThirdQpTypeName(qptNames[2]);
           			 } 
           			qpReport.setFirstDepId(0);
           			qpReport.setFirstDepName(""); 
           			qpReport.setTwoDepId(0);
           			qpReport.setTwoDepName("");
           			qpReport.setThreeDepId(0);
           			qpReport.setThreeDepName("");
           			qpReport.setJobId(0);
           			qpReport.setJobName("");
           			qpReport.setRespPersonId(0);
           			qpReport.setRespPersonName("");
           			qpReport.setAddPerson(user.getRealName());
           			//年季月周
           			qpReport.setYear(DateUtil.getYear(new Date()));
           			qpReport.setYearQuarter(DateUtil.getYearAndQuarter(new Date()));
           			qpReport.setYearMonth(DateUtil.getYearAndMonth(new Date()));
           			qpReport.setYearWeek(DateUtil.getYearAndWeek(new Date()));
           			qpReport.setAgencyId(0);
          			qpReport.setAgencyName("");
          			qpReport.setAddTime(new Date());
          			list.add(qpReport);
       			 }else{
       				 
		           	     for(InnerRespBill respBill : qp.getInnerList()){
		           	    	 
		           	    	 QualityProblemReport qpReport = new QualityProblemReport();
		           			 String fullName = depService.getDepFullNameById(respBill.getDepId());
		           			 String []depNames  = fullName.split("-");
		           			 qpReport.setQcId(qcBill.getId());
		           			 qpReport.setQpFlag(QcConstant.INNER_RESP_FALG);
		           			 qpReport.setTouchRedFlag(type.getTouchRedFlag());
		           			 if(qptNames.length==1){
		           				
		           				 qpReport.setFirstQpTypeId(type.getId());
		           				 qpReport.setFirstQpTypeName(qptNames[0]);
		           				 qpReport.setSecondQpTypeId(0);
		           				 qpReport.setSecondQpTypeName("");
		           				 qpReport.setThirdQpTypeId(0);
		            			 qpReport.setThirdQpTypeName("");
		           			 }
		           			 if(qptNames.length==2){
		           				 
		           			    qpReport.setFirstQpTypeId(type.getPid());
		           			    qpReport.setFirstQpTypeName(qptNames[0]);
		           		    	qpReport.setSecondQpTypeId(type.getId());
		           				qpReport.setSecondQpTypeName(qptNames[1]);
		           				qpReport.setThirdQpTypeId(0);
		           				qpReport.setThirdQpTypeName("");
		           			 }
		           			 if(qptNames.length>2){
		           				 
		           				QualityProblemType firstType = qptService.get(type.getPid());
		           			    qpReport.setFirstQpTypeId(firstType.getPid());
		           				qpReport.setFirstQpTypeName(qptNames[0]);
		           		    	qpReport.setSecondQpTypeId(type.getPid());
		           				qpReport.setSecondQpTypeName(qptNames[1]);
		           			    qpReport.setThirdQpTypeId(type.getId());
		           				qpReport.setThirdQpTypeName(qptNames[2]);
		           			 }
		           			if(depNames.length==1){
		           				
		           				qpReport.setFirstDepId(respBill.getDepId());
		           				qpReport.setFirstDepName(depNames[0]);
		           				qpReport.setTwoDepId(0);
		           				qpReport.setTwoDepName("");
		    			       	qpReport.setThreeDepId(0);
		    			       	qpReport.setThreeDepName("");
		           			}
		           			if(depNames.length==2){
		           				
		           				qpReport.setTwoDepId(respBill.getDepId());
		           				qpReport.setTwoDepName(depNames[1]);
		    			       	Department dep = depService.get(respBill.getDepId());
		    			       	if(dep!=null){
		    			       		
		    			       		qpReport.setFirstDepId(dep.getPid());
		    			       	}else{
		    			       		
		    			       		qpReport.setFirstDepId(0);
		    			       	}
		    			    	
		    			       	qpReport.setFirstDepName(depNames[0]);
		    			       	qpReport.setThreeDepId(0);
		    			       	qpReport.setThreeDepName("");
		    			     }
		           			if(depNames.length>=3){
		    					
		           				qpReport.setThreeDepId(respBill.getDepId());
		           				qpReport.setThreeDepName(depNames[depNames.length-1]);
		    				   	Department dep = depService.get(respBill.getDepId());
		    				   	if(dep!=null){
		    			       		
		    				   		qpReport.setTwoDepId(dep.getPid());
		    				       	Department tdep = depService.get(dep.getPid());
		    				       	if(tdep!=null){
		    				       		
		    				       		qpReport.setFirstDepId(tdep.getPid());
		    				       		
		    				       	}else{
		    				       		
		    				       		qpReport.setFirstDepId(0);
		    				       	}
		    				   		
		    			       	}else{
		    			       		
		    			       		qpReport.setTwoDepId(0);
		    			       		qpReport.setFirstDepId(0);
		    			       	}
		    				   	qpReport.setTwoDepName(depNames[depNames.length-2]);
		    				   	qpReport.setFirstDepName(depNames[depNames.length-3]);
		    				}
		           			
		           			qpReport.setJobId(respBill.getJobId());
		           			qpReport.setJobName(respBill.getJobName());
		           			qpReport.setRespPersonId(respBill.getRespPersonId());
		           			qpReport.setRespPersonName(respBill.getRespPersonName());
		           			qpReport.setAddPerson(user.getRealName());
		           			//年季月周
		           			qpReport.setYear(DateUtil.getYear(new Date()));
		           			qpReport.setYearQuarter(DateUtil.getYearAndQuarter(new Date()));
		           			qpReport.setYearMonth(DateUtil.getYearAndMonth(new Date()));
		           			qpReport.setYearWeek(DateUtil.getYearAndWeek(new Date()));
		           			qpReport.setAddTime(new Date());
		           			qpReport.setAgencyId(0);
		          			qpReport.setAgencyName("");
		           			list.add(qpReport);
		           	     }
		           	    //外部责任单
		           	    for(OuterRespBill outerBill : qp.getOuterList()){
		          	    	 
		          	    	 QualityProblemReport qpReport = new QualityProblemReport();
		          			 String fullName = depService.getDepFullNameById(outerBill.getDepId());
		          			 String []depNames  = fullName.split("-");
		          			 qpReport.setQcId(qcBill.getId());
		          			 qpReport.setQpFlag(QcConstant.OUTER_RESP_FALG);
		          			 qpReport.setTouchRedFlag(type.getTouchRedFlag());
		          			 if(qptNames.length==1){
		          				
		          				 qpReport.setFirstQpTypeId(type.getId());
		          				 qpReport.setFirstQpTypeName(qptNames[0]);
		          				 qpReport.setSecondQpTypeId(0);
		           				 qpReport.setSecondQpTypeName("");
		           				 qpReport.setThirdQpTypeId(0);
		            			 qpReport.setThirdQpTypeName("");
		          			 }
		          			 if(qptNames.length==2){
		          				 
		          			    qpReport.setFirstQpTypeId(type.getPid());
		          			    qpReport.setFirstQpTypeName(qptNames[0]);
		          		    	qpReport.setSecondQpTypeId(type.getId());
		          				qpReport.setSecondQpTypeName(qptNames[1]);
		          			    qpReport.setThirdQpTypeId(0);
		            			qpReport.setThirdQpTypeName("");
		          			 }
		          			 if(qptNames.length>2){
		          				 
		          				QualityProblemType firstType = qptService.get(type.getPid());
		          			    qpReport.setFirstQpTypeId(firstType.getPid());
		          				qpReport.setFirstQpTypeName(qptNames[0]);
		          		    	qpReport.setSecondQpTypeId(type.getPid());
		          				qpReport.setSecondQpTypeName(qptNames[1]);
		          			    qpReport.setThirdQpTypeId(type.getId());
		          				qpReport.setThirdQpTypeName(qptNames[2]);
		          			 }
		          			if(depNames.length==1){
		          				
		          				qpReport.setFirstDepId(outerBill.getDepId());
		          				qpReport.setFirstDepName(depNames[0]);
		          				qpReport.setTwoDepId(0);
		          				qpReport.setTwoDepName("");
		   			        	qpReport.setThreeDepId(0);
		   			        	qpReport.setThreeDepName("");
		          			}
		          			if(depNames.length==2){
		          				
		          				qpReport.setTwoDepId(outerBill.getDepId());
		          				qpReport.setTwoDepName(depNames[1]);
		   			          	Department dep = depService.get(outerBill.getDepId());
			   			       	if(dep!=null){
			   			       		
			   			       		qpReport.setFirstDepId(dep.getPid());
			   			       	}else{
			   			       		
			   			       		qpReport.setFirstDepId(0);
			   			       	}
		   			    	
		   			       	qpReport.setFirstDepName(depNames[0]);
		   			       	qpReport.setThreeDepId(0);
		   			       	qpReport.setThreeDepName("");
		          			}
		          			if(depNames.length>=3){
		   					
		          				qpReport.setThreeDepId(outerBill.getDepId());
		          				qpReport.setThreeDepName(depNames[depNames.length-1]);
		          				Department dep = depService.get(outerBill.getDepId());
			   				   	if(dep!=null){
			   			       		
			   				   		qpReport.setTwoDepId(dep.getPid());
			   				       	Department tdep = depService.get(dep.getPid());
			   				       	if(tdep!=null){
			   				       		
			   				       		qpReport.setFirstDepId(tdep.getPid());
			   				       		
			   				       	}else{
			   				       		
			   				       		qpReport.setFirstDepId(0);
			   				       	}
		   				   		
		   			       	}else{
		   			       		
		   			       		qpReport.setTwoDepId(0);
		   			       		qpReport.setFirstDepId(0);
		   			       	}
		   				   	qpReport.setTwoDepName(depNames[depNames.length-2]);
		   				   	qpReport.setFirstDepName(depNames[depNames.length-3]);
		   				}
		          			qpReport.setJobId(job.getId());
		          			qpReport.setJobName(QcConstant.PRODUCT_MANAGER);
		          			qpReport.setRespPersonId(outerBill.getRespPersonId());
		          			qpReport.setRespPersonName(outerBill.getRespPersonName());
		          			qpReport.setAddPerson(user.getRealName());
		          			//年季月周
		          			qpReport.setYear(DateUtil.getYear(new Date()));
		           			qpReport.setYearQuarter(DateUtil.getYearAndQuarter(new Date()));
		           			qpReport.setYearMonth(DateUtil.getYearAndMonth(new Date()));
		           			qpReport.setYearWeek(DateUtil.getYearAndWeek(new Date()));
		          			qpReport.setAgencyId(outerBill.getAgencyId());
		          			qpReport.setAgencyName(outerBill.getAgencyName());
		          			qpReport.setAddTime(new Date());
		          			list.add(qpReport);
		          	     }
       			 }
              }
              if(list.size()>0){
            	  
            	  qptMapper.addBatch(list);
              }
    		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
    }
    
    private void addCostLedgerAndAux(QcBill qcBill,User user){
    	
    	try {
		
    	  List<QualityCostLedger> list = new ArrayList<QualityCostLedger>();  
    	  List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(qcBill.getId(),Constant.QC_COMPLAINT);
          for (QualityProblem qp : qualityProblemlist){
       	   
       	     String qptName  =qp.getQptName();
       	     String []qptNames =  qptName.split("-");
       	     
       	     for(InnerRespBill respBill : qp.getInnerList()){
       	    	 
       	    	 QualityCostLedger leger = new QualityCostLedger();
       			 String fullName = depService.getDepFullNameById(respBill.getDepId());
       			 String []depNames  = fullName.split("-");
       			 leger.setQcId(qcBill.getId());
       			 leger.setIspId(respBill.getId());
       			 leger.setCmpId(qcBill.getCmpId());
       			 leger.setQualityCost(0.0);
       			 leger.setFirstCostAccount(QsConstant.FIRST_COST_ACCOUNT);
       			
       			 if(qptNames.length==1){
       				 
       				 leger.setTwoCostAccount(qptNames[0]);
       				 leger.setThreeCostAccount("");
       				 leger.setFourCostAccount("");
       			 }
       			 if(qptNames.length==2){
       				 
       				 leger.setTwoCostAccount(qptNames[0]);
       				 leger.setThreeCostAccount(qptNames[1]);
       				 leger.setFourCostAccount("");
       			 }
       			 if(qptNames.length>2){
       				 
       				 leger.setTwoCostAccount(qptNames[0]);
       				 leger.setThreeCostAccount(qptNames[1]);
       				 leger.setFourCostAccount(qptNames[2]);
       			 }
       			if(depNames.length==1){
       				
       				leger.setFirstDepId(respBill.getDepId());
       				leger.setFirstDepName(depNames[0]);
       			 	leger.setTwoDepId(0);
			       	leger.setTwoDepName("");
			        leger.setThreeDepId(0);
					leger.setThreeDepName("");
       			}
       			if(depNames.length==2){
       				
			       	leger.setTwoDepId(respBill.getDepId());
			       	leger.setTwoDepName(depNames[1]);
			       	Department dep = depService.get(respBill.getDepId());
			       	if(dep!=null){
			       		
			       		leger.setFirstDepId(dep.getPid());
			       	}else{
			       		
			       		leger.setFirstDepId(0);
			       	}
			    	
			       	leger.setFirstDepName(depNames[0]);
			        leger.setThreeDepId(0);
					leger.setThreeDepName("");
			     }
       			if(depNames.length>=3){
					
				    leger.setThreeDepId(respBill.getDepId());
					leger.setThreeDepName(depNames[depNames.length-1]);
				   	Department dep = depService.get(respBill.getDepId());
				   	if(dep!=null){
			       		
				   		leger.setTwoDepId(dep.getPid());
				       	Department tdep = depService.get(dep.getPid());
				       	if(tdep!=null){
				       		
				       		leger.setFirstDepId(tdep.getPid());
				       		
				       	}else{
				       		
				       		leger.setFirstDepId(0);
				       	}
				   		
			       	}else{
			       		
			       		leger.setTwoDepId(0);
			       		leger.setFirstDepId(0);
			       	}
				   	leger.setTwoDepName(depNames[depNames.length-2]);
			       	leger.setFirstDepName(depNames[depNames.length-3]);
				}
       			leger.setJobId(respBill.getJobId());
       			leger.setJobName(respBill.getJobName());
       			leger.setRespPersonId(respBill.getRespPersonId());
       			leger.setRespPersonName(respBill.getRespPersonName());
       			leger.setRespRate(respBill.getRespRate());
       			leger.setDealPersonId(user.getId());
       			leger.setDealPersonName(user.getRealName());
       			leger.setAddPerson(user.getRealName());
       			leger.setPrdLineId(0);
       			Date date = new Date();
       			leger.setAddTime(date);
       			leger.setYear(DateUtil.getYear(date));
       			leger.setYearQuarter(DateUtil.getYearAndQuarter(date));
       			leger.setYearMonth(DateUtil.getYearAndMonth(date));
       			leger.setYearWeek(DateUtil.getYearAndWeek(date));
       			list.add(leger);
       	     }
       	     
          }
          if(list.size()>0){
        	  
	          ledgerMapper.addBatch(list);
	          auxMapper.addBatch(list);
          }
    	} catch (Exception e) {
    		
			 e.printStackTrace();
		}
    }
    
    @Override
	public Map<String, Object> QcReportContent(Integer id) {
    	
		return getQcReportContentDataMap(id);
	}

    
    private Map<String,Object> getQcReportContentDataMap(Integer id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        QcBill qcBill = get(id);
        int redLine = checkRedLine(id);
        qcBill.setRedLine(redLine);
        dataMap.put("qcBill", qcBill);
        // 订单信息
        OrderBill orderBill =orderBillService.get(qcBill.getOrdId());
        if(null!=orderBill.getReturnDate() && null!=orderBill.getDepartDate()){
			
			int day = DateUtil.getDaysBetween(orderBill.getDepartDate(),orderBill.getReturnDate());
			orderBill.setDepartDay(day+1);
		}
        dataMap.put("orderBill", orderBill);
        // 产品信息
        Product product = productService.get(qcBill.getPrdId());
        dataMap.put("product", product);
        // 投诉信息
        ComplaintBill complaintBill = CmpClient.getComplaintInfo(qcBill.getCmpId());
        
        dataMap.put("complaintBill", complaintBill);
        //质量问题单
        List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(id,Constant.QC_COMPLAINT);
        List<QualityProblem> executeQpList = new ArrayList<QualityProblem>();//执行问题
        List<QualityProblem> supplierQpList = new ArrayList<QualityProblem>();//供应商问题
        List<QualityProblem> flowsQpList = new ArrayList<QualityProblem>();//流程系统问题
        List<QualityProblem> improveQpList = new ArrayList<QualityProblem>();//改进问题
        for(QualityProblem qp : qualityProblemlist) {
            if(qp.getQptName().startsWith("执行问题")) {
                executeQpList.add(qp);
            }else if(qp.getQptName().startsWith("供应商问题")) {
                supplierQpList.add(qp);
            }else if(qp.getQptName().startsWith("流程/系统问题")){
                flowsQpList.add(qp);
            }else if(qp.getQptName().startsWith("改进问题")){
                improveQpList.add(qp);
            }
        }
        dataMap.put("executeQpList", executeQpList);
        dataMap.put("supplierQpList", supplierQpList);
        dataMap.put("flowsQpList", flowsQpList);
        dataMap.put("improveQpList", improveQpList);
        
        //内部处罚单列表
        List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(id);//内部处罚单
        dataMap.put("innerPunishList", innerPunishList);
        //外部处罚单列表
        List<OuterPunishBill> outerPunishList = outerPunishService.listOuterPunish(id);//外部处罚单
        dataMap.put("outerPunishList", outerPunishList);
        
        //质检员
        dataMap.put("qcPerson", qcBill.getQcPerson());
        //日期
        dataMap.put("dateTime", DateUtil.formatAsDefaultDate(new Date()));
        return dataMap;
    }
    
    /**
     * 【根据内部处罚单来判断是否触红】
     * 程序逻辑是：遍历内部处罚单下的被执行的处罚依据，如果该依据对应的处罚标准是红线标准，则该单被人为是触红
     * @param dataMap
     * @return
     */
    @SuppressWarnings("unchecked")
	private InnerPunishBill getTouchRedInnerPunishBill(Map<String, Object> dataMap) {
        List<InnerPunishBill> innerPunishList = (List<InnerPunishBill>) dataMap.get("innerPunishList"); //内部处罚单才涉及触红下线
        if(CollectionUtil.isNotEmpty(innerPunishList)) {
            for(InnerPunishBill innerPunishBill : innerPunishList) {
                List<InnerPunishBasis> basis = innerPunishBill.getIpbList();
                if(CollectionUtil.isNotEmpty(basis)) {
                    for(InnerPunishBasis innerPunishBasis : basis) {
                        if(innerPunishBasis.getExecFlag()==1) {
                            PunishStandard standard  =innerPunishBasis.getPunishStandard();
                            if(standard.getRedLineFlag()==1){
                                return innerPunishBill;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public String getCmpQcReportEmailContent(Integer qcId) {
        String emailContent = mailTaskService.getMailText("cmpQcReport.ftl", getQcReportContentDataMap(qcId));
        return emailContent;
    }

	@Override
	public List<QcBill> getExpirationList(Map<String, Object> map) {
		
		return mapper.getExpirationList(map);
	}

	@Override
	public int getOrderIsExist(Map<String, Object> map) {
		
		return mapper.getOrderIsExist(map);
	}

	@Override
	public List<Integer> listQcId() {
		
		return mapper.listQcId();
	}

	@Override
	public List<Integer> listQcAndQuality() {
		return mapper.listQcAndQuality();
	}

	/**
	 * 退回质检单
	 */
	public void updateReturnQcBill(Integer qcId, User user) {
		
		QcBill qcBill = this.get(qcId);
		qcBill.setReturnFlag(QcConstant.YES);//退回标识
		qcBill.setState( QcConstant.QC_BILL_STATE_QCBEGIN );
        OperationLog log = new OperationLog();
      	log.setDealPeople(user.getId());
      	log.setDealPeopleName(user.getRealName());
      	log.setQcId(qcId);
      	log.setFlowName("质检处理中");
       	log.setContent("质检单："+qcId+"，审核不通过，返回处理中");
      	log.setDealDepart(user.getRole().getName());
	    operLogService.add(log);
	    update(qcBill);
	}

	/**
	 * 复制质检单
	 */
	public void addCopyQcBill(Integer id, QcBill copyQcBill, User user) {
		 //清除原质检单所有信息
		 QcBill qcBill = this.get(id);
		 qcBill.setVerification(copyQcBill.getVerification());//复制核实情况
		 deleteQcBill(qcBill);
		
		 QcDetailCopyDto qcDetailCopyDto = new QcDetailCopyDto();
		 qcDetailCopyDto.setQcIdOld(id);
		 qcDetailCopyDto.setQcIdNew(copyQcBill.getId());
		 qcDetailCopyDto.setAddPerson(user.getRealName());
         
		 //复制质量问题
		 qualityProblemService.copyQualityDetails(qcDetailCopyDto);
	}

	@Override
	public List<QualityProblemDetail> getGuideDetail(QualityProblemDetailDto dto) {
		return mapper.getGuideDetail(dto);
	}

	@Override
	public int getGuideCount(QualityProblemDetailDto dto) {
		
		return mapper.getGuideCount(dto);
	}

	@Override
	public  Map<String, Object> getGuideSateCount(QualityProblemDetailDto dto) {
		
		return mapper.getGuideSateCount(dto);
	}

	@Override
	public List<CmpAndInnerQcBill> getAirAndTraffic(QcBillDto dto) {

		return mapper.getAirAndTraffic(dto);
	}

	@Override
	public void addQcBill(QcBill qcBill) {
		
		mapper.addQcBill(qcBill);
	}

	@Override
	public QcBill getById(Integer qcId) {
		
		return mapper.getById(qcId);
	}

	@Override
	public List<QcBill> getComplaintQcBill(Map<String, Object> map) {
		return mapper.getComplaintQcBill(map);
	}

	@Override
	public void cancelQcBill(Integer id) {
		mapper.cancelQcBill(id);
	}

	@Override
	public void loadPageNoCmp(QcBillDto dto) {
		int totalRecords = mapper.countNoCmp(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(mapper.listNoCmp(dto));
	}

	@Override
	public List<QcBill> getNoCmpExpirationList() {
		return mapper.getNoCmpExpirationList();
	}

	@Override
	public void updateQcLevel(QcBillDto dto, User user) {
		OperationLog log = null;
		for(Integer qcId : dto.getQcBillIds()){
			log = new OperationLog();
			log.setDealPeople(user.getId());
			log.setDealPeopleName(user.getRealName());
			log.setQcId(qcId);
			log.setFlowName("修改质检等级");
			log.setDealDepart(user.getRole().getName());
			log.setContent("质检单："+ qcId +"，修改质检等级到：" + dto.getImportantFlag());
			operLogService.add(log);
		}
		
		mapper.updateQcLevel(dto);
	}

	@Override
	public Integer getQcLevelsById(QcBillDto dto) {
		return mapper.getQcLevelsById(dto);
	}

	
}
