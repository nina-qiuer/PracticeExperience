package com.tuniu.qms.qc.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dto.QcTypeDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcPunishSegmentTask;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.report.model.QcPunishReport;
import com.tuniu.qms.report.service.QcPunishReportService;

/**
 * 处罚单切片任务
 * @author zhangsensen
 *
 */
public class PunishBillSectionedTask {
	
	private static final Logger LOG = LoggerFactory.getLogger(PunishBillSectionedTask.class);
	
	@Autowired
	private QcPunishSegmentTaskService qcPunishSegmentTaskService;
	
	@Autowired
	private InnerPunishBillService innerPunishBillService;
	
	@Autowired
	private QcBillService qcBillService;
	
	@Autowired
	private QcPunishReportService qcPunishReportService;
	
	@Autowired
    private DepartmentService  depService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QcTypeService qcTypeService;
	
	/**
	 * 更新处罚单数据数据切片表
	 */
	public void updateQcPunishReport(){
		//获得任务表中需要执行的数据
		List<QcPunishSegmentTask> list = qcPunishSegmentTaskService.getTaskList();
		
		int size = list.size();
		//通过质检单号，找到内部处罚单
		for(int i = 0; i < size; i++){
			try{
				//失败次数是否小于三次
				if(list.get(i).getFailedTimes() <= 3){
					buildQcPunishData(list.get(i));
				}
				
				//删除任务数据
				qcPunishSegmentTaskService.delete(list.get(i).getId());
	 		} catch (Exception e){
	 			LOG.error("数据切片生成失败[{}]",list.get(i).getId() ,e);
	 			list.get(i).setFailedTimes(list.get(i).getFailedTimes()+1);
	 			qcPunishSegmentTaskService.update(list.get(i));
	 		}
		}
		
	}
	
	/**
	 * 处罚单数据切片构建添加
	 * @param qcPunishSegmentTask
	 */
	private void buildQcPunishData(QcPunishSegmentTask qcPunishSegmentTask) {
		Integer qcId = qcPunishSegmentTask.getDataId();
		//判断任务类型   1：数据切片添加  2 ：数据切片删除
		if(qcPunishSegmentTask.getTaskType() == 1){
			
			List<InnerPunishBill> inPunishList = innerPunishBillService.listInnerPunishByQcId(qcId);
			
			if(inPunishList != null && inPunishList.size() > 0){
				QcBill qcBill = qcBillService.get(qcId);
				//构造数据切片表
				for(InnerPunishBill bill : inPunishList){
					QcPunishReport qcPunishReport = new QcPunishReport();
					
					qcPunishReport.setQcId(qcId);
					qcPunishReport.setInnerPunishId(bill.getId());
					qcPunishReport.setScorePunish(bill.getScorePunish());
					qcPunishReport.setEconomicPunish(bill.getEconomicPunish());
					//质检类型 三级
					setQcType(qcPunishReport, qcBill.getQcTypeId());
					//关联部门 三级
					setDepOrQcGroup(qcPunishReport, bill.getDepId(), true);
					//质检组  三级   根据质检员查询质检组
					User user = userService.get(qcBill.getQcPersonId());
					if(user != null ){
						setDepOrQcGroup(qcPunishReport, user.getDepId(), false);
					}else{
						qcPunishReport.setFirstQcTypeId(0);
						qcPunishReport.setFirstQcTypeName("");
						qcPunishReport.setSecondQcTypeId(0);
						qcPunishReport.setSecondQcTypeName("");
						qcPunishReport.setThirdQcTypeId(0);
						qcPunishReport.setThirdQcTypeName("");
					}
					//关联岗位
					qcPunishReport.setJobId(bill.getJobId());
					qcPunishReport.setJobName(bill.getJobName());
					//被处罚人
					qcPunishReport.setPunPersonId(bill.getPunishPersonId());
					qcPunishReport.setPunishPersonName(bill.getPunishPersonName());
					//质检人
					qcPunishReport.setQcPersonId(qcBill.getQcPersonId());
					qcPunishReport.setQcPersonName(qcBill.getQcPerson());
					//质检完成时间 年季月周日
					qcPunishReport.setYear(DateUtil.getYear(qcBill.getFinishTime()));
					qcPunishReport.setYearQuarter(DateUtil.getYearAndQuarter(qcBill.getFinishTime()));
					qcPunishReport.setYearMonth(DateUtil.getYearAndMonth(qcBill.getFinishTime()));
					qcPunishReport.setYearWeek(DateUtil.getYearAndWeek(qcBill.getFinishTime()));
					qcPunishReport.setQcFinishTime(DateUtil.formatAsDefaultDate(qcBill.getFinishTime()));
					
					//数据切片添加
					qcPunishReportService.add(qcPunishReport);
				}
			}
		}else{
			qcPunishReportService.deleteByQcId(qcId);
		}
	}

	/**
	 * 质检类型填充  三级
	 * @param qcPunishReport
	 * @param qcTypeId
	 */
	private void setQcType(QcPunishReport qcPunishReport, Integer qcTypeId) {
		List<QcType> list = qcTypeService.getTypeFullNameList();
		String fullName = qcTypeService.getQtFullNameById(qcTypeId, list);
		String []qctNames  = fullName.split("-");
		
		if(qctNames.length == 1){
			qcPunishReport.setFirstQcTypeId(qcTypeId);
			qcPunishReport.setFirstQcTypeName(qctNames[0]);
			qcPunishReport.setSecondQcTypeId(0);
			qcPunishReport.setSecondQcTypeName("");
			qcPunishReport.setThirdQcTypeId(0);
			qcPunishReport.setThirdQcTypeName("");
		}
		if(qctNames.length == 2){
			qcPunishReport.setSecondQcTypeId(qcTypeId);
			qcPunishReport.setSecondQcTypeName(qctNames[1]);
			QcType qcType = qcTypeService.get(qcTypeId);
			if(qcType != null){
				qcPunishReport.setFirstQcTypeId(qcType.getPid());
			}else{
				qcPunishReport.setFirstQcTypeId(0);
			}
			qcPunishReport.setFirstQcTypeName(qctNames[0]);
			qcPunishReport.setThirdQcTypeId(0);
			qcPunishReport.setThirdQcTypeName("");
		}
		if(qctNames.length >= 3){
			qcPunishReport.setThirdQcTypeId(qcTypeId);
			qcPunishReport.setThirdQcTypeName(qctNames[qctNames.length - 1]);
			QcType qcType = qcTypeService.get(qcTypeId);
			if(qcType != null){
				qcPunishReport.setSecondQcTypeId(qcType.getPid());
				
				QcType tQcType = qcTypeService.get(qcType.getPid());
				
				if(tQcType != null){
					qcPunishReport.setFirstQcTypeId(tQcType.getPid());
				}else{
					qcPunishReport.setFirstQcTypeId(0);
				}
			}else{
				qcPunishReport.setSecondQcTypeId(0);
				qcPunishReport.setFirstQcTypeId(0);
			}
			qcPunishReport.setSecondQcTypeName(qctNames[qctNames.length - 2]);
			qcPunishReport.setFirstQcTypeName(qctNames[qctNames.length - 3]);
		}
	}
	/**
	 * 关联部门或质检组三级填充
	 * @param qcPunishReport
	 * @param depId
	 * @param b  是否是关联部门
	 */
	private void setDepOrQcGroup(QcPunishReport qcPunishReport, Integer depId, boolean b) {
		
		String fullName = depService.getDepFullNameById(depId);
		String []depNames  = fullName.split("-");
		
		if(b){
			if(depNames.length==1){
   				
   				qcPunishReport.setFirstDepId(depId);
   				qcPunishReport.setFirstDepName(depNames[0]);
   			 	qcPunishReport.setTwoDepId(0);
		       	qcPunishReport.setTwoDepName("");
		        qcPunishReport.setThreeDepId(0);
				qcPunishReport.setThreeDepName("");
   			}
			if(depNames.length==2){
		       				
		       	qcPunishReport.setTwoDepId(depId);
		       	qcPunishReport.setTwoDepName(depNames[1]);
		       	Department dep = depService.get(depId);
		       	if(dep!=null){
		       		
		       		qcPunishReport.setFirstDepId(dep.getPid());
		       	}else{
		       		qcPunishReport.setFirstDepId(0);
		       	}
		       	qcPunishReport.setFirstDepName(depNames[0]);
		        qcPunishReport.setThreeDepId(0);
				qcPunishReport.setThreeDepName("");
		     }
			if(depNames.length>=3){
				
			    qcPunishReport.setThreeDepId(depId);
				qcPunishReport.setThreeDepName(depNames[depNames.length-1]);
			   	Department dep = depService.get(depId);
			   	if(dep!=null){
		       		
			   		qcPunishReport.setTwoDepId(dep.getPid());
			       	Department tdep = depService.get(dep.getPid());
			       	if(tdep!=null){
			       		qcPunishReport.setFirstDepId(tdep.getPid());
			       	}else{
			       		qcPunishReport.setFirstDepId(0);
			       	}
		       	}else{
		       		qcPunishReport.setTwoDepId(0);
		       		qcPunishReport.setFirstDepId(0);
		       	}
			   	qcPunishReport.setTwoDepName(depNames[depNames.length-2]);
		       	qcPunishReport.setFirstDepName(depNames[depNames.length-3]);
			}
		}else{
			if(depNames.length==1){
   				
   				qcPunishReport.setFirstQcGroupId(depId);
   				qcPunishReport.setFirstQcGroupName(depNames[0]);
   			 	qcPunishReport.setTwoQcGroupId(0);
		       	qcPunishReport.setTwoQcGroupName("");
		        qcPunishReport.setThreeQcGroupId(0);
				qcPunishReport.setThreeQcGroupName("");
   			}
			if(depNames.length==2){
		       				
		       	qcPunishReport.setTwoQcGroupId(depId);
		       	qcPunishReport.setTwoQcGroupName(depNames[1]);
		       	Department dep = depService.get(depId);
		       	if(dep!=null){
		       		qcPunishReport.setFirstQcGroupId(dep.getPid());
		       	}else{
		       		qcPunishReport.setFirstQcGroupId(0);
		       	}
		       	qcPunishReport.setFirstQcGroupName(depNames[0]);
		        qcPunishReport.setThreeQcGroupId(0);
				qcPunishReport.setThreeQcGroupName("");
		     }
			if(depNames.length>=3){
				
			    qcPunishReport.setThreeQcGroupId(depId);
				qcPunishReport.setThreeQcGroupName(depNames[depNames.length-1]);
			   	Department dep = depService.get(depId);
			   	if(dep!=null){
		       		
			   		qcPunishReport.setTwoQcGroupId(dep.getPid());
			       	Department tdep = depService.get(dep.getPid());
			       	if(tdep!=null){
			       		qcPunishReport.setFirstQcGroupId(tdep.getPid());
			       	}else{
			       		qcPunishReport.setFirstQcGroupId(0);
			       	}
		       	}else{
		       		qcPunishReport.setTwoQcGroupId(0);
		       		qcPunishReport.setFirstQcGroupId(0);
		       	}
			   	qcPunishReport.setTwoQcGroupName(depNames[depNames.length-2]);
		       	qcPunishReport.setFirstQcGroupName(depNames[depNames.length-3]);
			}
		}
		
	}
}
