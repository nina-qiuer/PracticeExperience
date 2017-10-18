package com.tuniu.qms.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.report.dto.QcPunishReportDto;
import com.tuniu.qms.report.service.QcPunishReportService;

@Controller
@RequestMapping("/report/QcPunishReportReport")
public class QcPunishReportController {
	
	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private JobService  jobService;
	
	@Autowired
	private QcTypeService qcService;
	
	@Autowired
	private QcPunishReportService QcPunishReportService;
	
	/**
	 * 记分处罚占比
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/scoreRecordProportion")
	public String scoreRecordProportion(QcPunishReportDto dto, HttpServletRequest request){
		dataTransfer(dto, false);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//统计纬度 1:质检类型  2：关联部门  3：关联岗位  4：被处罚人
		if(dto.getViewFlag()== 1){
			dataList = QcPunishReportService.getGraphByQcType(dto);
		}else if(dto.getViewFlag()== 2){
			dataList = QcPunishReportService.getGraphByDep(dto);
		}else if(dto.getViewFlag()== 3){
			dataList = QcPunishReportService.getGraphByJob(dto);
		}else if(dto.getViewFlag()== 4){
			dataList = QcPunishReportService.getGraphByPunishPerson(dto);
		}
		
		request.setAttribute("monthList", DateUtil.monthList());
		request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("dataList", dataList);
		request.setAttribute("dto", dto);
		return "report/punish/scoreRecordProportion";
	}
	
	/**
	 * 记分处罚趋势
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/scoreRecordTrend")
	public String scoreRecordTrend(QcPunishReportDto dto, HttpServletRequest request){
		dataTransfer(dto, true);
		//分数汇总
		 List<Map<String, Object>> scoreDataList = QcPunishReportService.getscoreSumTrendGrah(dto);
		//次数汇总
		List<Map<String, Object>> punishTimeDataList = QcPunishReportService.getPunishTimeTrendGrah(dto);
		
		request.setAttribute("monthList", DateUtil.monthList());
		request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("scoreDataList", scoreDataList);
		request.setAttribute("punishTimeDataList", punishTimeDataList);
		request.setAttribute("dto", dto);
		return "report/punish/scoreRecordTrend";
	}
	
	@RequestMapping("/getGraphByQcType")
	public List<Map<String, Object>> getGraphByQcType(QcPunishReportDto dto, HttpServletRequest request){
		dataTransfer(dto, false);
		return QcPunishReportService.getGraphByQcType(dto);
	}
	
	/**
	 * 记分处罚排名
	 * @param dto
	 * @param request
	 * @return
	 */
	@RequestMapping("/scoreRecordRank")
	public String scoreRecordRank(QcPunishReportDto dto, HttpServletRequest request){
		dataTransfer(dto, false);
		
		//质检类型分数汇总排名
		List<Map<String, Object>> scoreDataListByQcType = QcPunishReportService.getGraphByQcType(dto);
		//质检类型记分次数排名
		List<Map<String, Object>> punishTimeDataListByQcType = QcPunishReportService.getPunishTimeByQcType(dto);
		//部门分数汇总排名
		List<Map<String, Object>> scoreDataListByDep = QcPunishReportService.getGraphByDep(dto);
		//部门记分次数排名
		List<Map<String, Object>> punishTimeDataListByDep = QcPunishReportService.getPunishTimeByDep(dto);
		//被处罚人分数汇总排名
		List<Map<String, Object>> scoreDataListByPunPer = QcPunishReportService.getGraphByPunishPerson(dto);
		//被处罚人记分次数排名
		List<Map<String, Object>> punishTimeDataListByPunPerson = QcPunishReportService.getPunishTimeByPunPerson(dto);
		
		request.setAttribute("monthList", DateUtil.monthList());
		request.setAttribute("weekList", DateUtil.weekList());
		request.setAttribute("scoreDataListByQcType", scoreDataListByQcType);
		request.setAttribute("punishTimeDataListByQcType", punishTimeDataListByQcType);
		request.setAttribute("scoreDataListByDep", scoreDataListByDep);
		request.setAttribute("punishTimeDataListByDep", punishTimeDataListByDep);
		request.setAttribute("scoreDataListByPunPer", scoreDataListByPunPer);
		request.setAttribute("punishTimeDataListByPunPerson", punishTimeDataListByPunPerson);
		request.setAttribute("dto", dto);
		return "report/punish/scoreRecordRank";
	}
	
	/**
	 * 日期、质检类型、部门数据处理
	 * @param dto
	 * @param b 是否是趋势图
	 */
	private void dataTransfer(QcPunishReportDto dto, boolean isTrend) {
		//部门处理   一 级   - 二级 - 三级
		if(!StringUtils.isEmpty(dto.getDepName())){
			  
			  if(!StringUtils.isEmpty(dto.getDepName().trim())){
		         	String []depNames = dto.getDepName().split("-");
		         	Department dep =  depService.getDepByFullName(dto.getDepName());
		         	if(depNames.length==1){
		         		dto.setFirstDepId(dep.getId());
		         		dto.setDepType(2);
		         	}
					if(depNames.length==2){
						dto.setTwoDepId(dep.getId());
						dto.setDepType(3);
					 }
					if(depNames.length==3){
						dto.setThreeDepId(dep.getId());
						dto.setDepType(3);
			        }
			  }else{
				  dto.setDepType(1);
			  }
		  }else{
				dto.setDepType(1);
		  }
		//质检类型处理
		if(!StringUtils.isEmpty(dto.getQcName())){
			  
			 if(!StringUtils.isEmpty(dto.getQcName().trim())){
		         	String []qptNames = dto.getQcName().split("-");
		         	QcType qct =  qcService.getQcTypeByFullName(dto.getQcName());
		         	if(qptNames.length==1){
		         		dto.setFirstQcTypeId(qct.getId());
		         		dto.setQcType(2);
		         	}
					if(qptNames.length==2){
						dto.setSecondQcTypeId(qct.getId());
						dto.setQcType(3);
					 }
					if(qptNames.length==3){
						dto.setThirdQcTypeId(qct.getId());
						dto.setQcType(3);
			         }
			  }else{
				  dto.setQcType(1);
			  }
		 }else{
			 dto.setQcType(1);
		 }
		 //关联岗位处理
		if(!StringUtils.isEmpty(dto.getJobName())){
			Integer jobId = jobService.getJobIdByName(dto.getJobName());	
			dto.setJobId(jobId);    	
		}
		//被处罚人
		if(!StringUtils.isEmpty(dto.getPunishPersonName())){
			 dto.setPunishPersonName(dto.getPunishPersonName().trim());
		 }
		//质检人
		if(!StringUtils.isEmpty(dto.getQcPerson())){
			 dto.setQcPerson(dto.getQcPerson().trim());
		 }
		//质检组
		if(!StringUtils.isEmpty(dto.getQcGroupName())){
			  
			  if(!StringUtils.isEmpty(dto.getQcGroupName().trim())){
		         	String []depNames = dto.getQcGroupName().split("-");
		         	Department dep =  depService.getDepByFullName(dto.getQcGroupName());
		         	if(depNames.length==1){
		         		dto.setFirstQcGroupId(dep.getId());
		         	}
					if(depNames.length==2){
						dto.setTwoQcGroupId(dep.getId());
					 }
					if(depNames.length==3){
						dto.setThreeQcGroupId(dep.getId());
			        }
			  }
		  }
		//日期处理
		if (StringUtils.isBlank(dto.getYearBgn()) && StringUtils.isBlank(dto.getYearEnd())) {
			dto.setYearBgn(String.valueOf(DateUtil.getYear(new Date())));
			dto.setYearEnd(String.valueOf(DateUtil.getYear(new Date())));
		}
		if(isTrend){
			if (StringUtils.isBlank(dto.getwYearBgn()) && StringUtils.isBlank(dto.getwYearEnd())&& StringUtils.isBlank(dto.getWeekBgn()) && StringUtils.isBlank(dto.getWeekEnd())) {
			    Map<String, Object> map = DateUtil.getYearAndWeek();
				dto.setwYearBgn(String.valueOf(map.get("wYearBgn")));
				dto.setwYearEnd(String.valueOf(map.get("wYearEnd")));
				dto.setWeekBgn(String.valueOf(map.get("weekBgn")));
				dto.setWeekEnd(String.valueOf(map.get("weekEnd")));
			}
			if(null==dto.getTimeType()){
			  dto.setTimeType(4);
			}
		}else{
			if(null==dto.getTimeType()){
				  dto.setTimeType(1);
			  }
		}
		dto.setNowYear(DateUtil.getField(Calendar.YEAR)); 
		
		String weekStart = dto.getwYearBgn()+dto.getWeekBgn();
		String weekFinish = dto.getwYearEnd()+dto.getWeekEnd();
		dto.setWeekStart(weekStart);
		dto.setWeekFinish(weekFinish);
		
		String quarterStart = dto.getqYearBgn()+dto.getQuarterBgn();
		String quarterFinish = dto.getqYearEnd()+dto.getQuarterEnd();
		dto.setQuarterStart(quarterStart);
		dto.setQuarterFinish(quarterFinish);
		
		String monthStart = dto.getmYearBgn()+dto.getMonthBgn();
		String monthFinish = dto.getmYearEnd()+dto.getMonthEnd();
		dto.setMonthStart(monthStart);
		dto.setMonthFinish(monthFinish);
		 
	}
}
