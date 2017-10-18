package com.tuniu.qms.qc.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.model.InnerRespBill;
import com.tuniu.qms.qc.model.OuterRespBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.service.OuterRespBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.qs.dao.QualityCostAuxAccountMapper;
import com.tuniu.qms.qs.dao.QualityCostLedgerMapper;
import com.tuniu.qms.qs.model.QualityCostLedger;
import com.tuniu.qms.qs.util.QsConstant;
import com.tuniu.qms.report.dao.QualityProblemReportMapper;
import com.tuniu.qms.report.model.QualityProblemReport;


/**
 * 质量问题一级分类
 * @author chenhaitao
 */
public class QualityTask {
	
     @Autowired
	 private QualityProblemReportMapper qptMapper;
	
	@Autowired
	private QualityProblemTypeService qpTservice;

	@Autowired
	private QualityProblemService service;
	
	@Autowired
	private QcBillService qcService;
    
    @Autowired
    private DepartmentService  depService;
	
    @Autowired
	private QualityCostLedgerMapper ledgerMapper;
    
    @Autowired
    private QualityCostAuxAccountMapper auxMapper;
    
    @Autowired
    private OuterRespBillService outRespService;
    
    @Autowired
    private QualityProblemService qualityProblemService;
    
    @Autowired
	private JobService jobService;
	
    @Autowired
    private QualityProblemTypeService qptService;
    
	public void updateQualityLv() {
		
	//更新质量成本台账 辅助账
		
		 List<Integer> qcList = qcService.listQcId();
		 List<QualityCostLedger> list = new ArrayList<QualityCostLedger>();  
         for(Integer qcId:qcList){
        	 
        	  QcBill qcBill = qcService.get(qcId);
        	 
        	  List<QualityProblem> qualityProblemlist = service.listQpDetail(qcBill.getId(),Constant.QC_COMPLAINT);
              for (QualityProblem qp : qualityProblemlist){
           	   
           	     String qptName  =qp.getQptName();
           	     String []qptNames =  qptName.split("-");
           	     
           	     for(InnerRespBill respBill : qp.getInnerList()){
           	    	 
           	    	 QualityCostLedger leger = new QualityCostLedger();
           			 String fullName = depService.getDepFullNameById(respBill.getDepId());
           			 String []depNames  = fullName.split("-");
           			 leger.setQcId(qcBill.getId());
           			 leger.setAddTime(qcBill.getFinishTime());
           			 leger.setIspId(respBill.getId());
           			 leger.setCmpId(qcBill.getCmpId());
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
           			leger.setDealPersonId(qcBill.getQcPersonId());
           			leger.setDealPersonName(qcBill.getQcPerson());
           			leger.setAddPerson(qcBill.getQcPerson());
           			//年季月周
           			leger.setQualityCost(0.0);
           			leger.setPrdLineId(0);
           			leger.setYear(DateUtil.getYear(qcBill.getFinishTime()));
           			leger.setYearQuarter(DateUtil.getYearAndQuarter(qcBill.getFinishTime()));
           			leger.setYearMonth(DateUtil.getYearAndMonth(qcBill.getFinishTime()));
           			leger.setYearWeek(DateUtil.getYearAndWeek(qcBill.getFinishTime()));
           			list.add(leger);
           	     }
           	     
              }
        	 
         }
         if(list.size()>0){
       	  
	          ledgerMapper.addBatch(list);
	          auxMapper.addBatch(list);
         }
	}
	
	public void updateQualityProblem(){
		
		  List<Integer> qcList = qcService.listQcAndQuality();
		  List<QualityProblemReport> list = new ArrayList<QualityProblemReport>();  

		  for(Integer qcId:qcList){
			  
			  QcBill qcBill = qcService.get(qcId);
	    	  List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(qcId,Constant.QC_COMPLAINT);
	    	  Job job = jobService.getJobByName(QcConstant.PRODUCT_MANAGER);
	    	  
	    	  for (QualityProblem qp : qualityProblemlist){
	   
	       	     String qptName  = qp.getQptName();
	       	     String []qptNames =  qptName.split("-");
	   			 QualityProblemType type = qptService.getQpTypeFullName(qptName);
	   	 	    //内部责任单
	   			 if((qp.getInnerList()==null || qp.getInnerList().size()==0)&& (qp.getOuterList()==null || qp.getOuterList().size()==0)){
	   				 
	   				 QualityProblemReport qpReport = new QualityProblemReport();
	   				 qpReport.setQcId(qcId);
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
	       			qpReport.setAddPerson(qcBill.getQcPerson());
	       			//年季月周
	       			qpReport.setYear(DateUtil.getYear(qcBill.getFinishTime()));
	       			qpReport.setYearQuarter(DateUtil.getYearAndQuarter(qcBill.getFinishTime()));
	       			qpReport.setYearMonth(DateUtil.getYearAndMonth(qcBill.getFinishTime()));
	       			qpReport.setYearWeek(DateUtil.getYearAndWeek(qcBill.getFinishTime()));
	       			qpReport.setAgencyId(0);
	      			qpReport.setAgencyName("");
	      			qpReport.setAddTime(qcBill.getFinishTime());
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
		           			qpReport.setAddPerson(qcBill.getQcPerson());
		           			//年季月周
		           			qpReport.setYear(DateUtil.getYear(qcBill.getFinishTime()));
		           			qpReport.setYearQuarter(DateUtil.getYearAndQuarter(qcBill.getFinishTime()));
		           			qpReport.setYearMonth(DateUtil.getYearAndMonth(qcBill.getFinishTime()));
		           			qpReport.setYearWeek(DateUtil.getYearAndWeek(qcBill.getFinishTime()));
		           			qpReport.setAddTime(qcBill.getFinishTime());
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
		          			qpReport.setAddPerson(qcBill.getQcPerson());
		          			//年季月周
		          			qpReport.setYear(DateUtil.getYear(qcBill.getFinishTime()));
		           			qpReport.setYearQuarter(DateUtil.getYearAndQuarter(qcBill.getFinishTime()));
		           			qpReport.setYearMonth(DateUtil.getYearAndMonth(qcBill.getFinishTime()));
		           			qpReport.setYearWeek(DateUtil.getYearAndWeek(qcBill.getFinishTime()));
		          			qpReport.setAgencyId(outerBill.getAgencyId());
		          			qpReport.setAgencyName(outerBill.getAgencyName());
		          			qpReport.setAddTime(qcBill.getFinishTime());
		          			list.add(qpReport);
		          	     }
	   			 }
	          }
		  }
		 
          if(list.size()>0){
        	  
        	  qptMapper.addBatch(list);
          }
		
	}
}
