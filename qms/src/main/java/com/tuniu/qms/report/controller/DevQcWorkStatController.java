package com.tuniu.qms.report.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.report.dto.DevQcWorkStatDto;
import com.tuniu.qms.report.model.DevQcWorkProportion;
import com.tuniu.qms.report.model.DevQcWorkStat;
import com.tuniu.qms.report.service.DevQcWorkStatService;

/**
 * 研发质检工作量统计
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/report/devQcWorkStat")
public class DevQcWorkStatController {
	
	@Autowired
	private DevQcWorkStatService service;
	
	@RequestMapping("/list")
	public String list(DevQcWorkStatDto dto, HttpServletRequest request) {
		
		if (StringUtils.isBlank(dto.getStatDateBgn()) && StringUtils.isBlank(dto.getStatDateEnd())) {
			dto.setStatDateBgn(DateUtil.getMonthFirstDay());
			dto.setStatDateEnd(DateUtil.formatAsDefaultDate(new Date()));
		}
		if (StringUtils.isBlank(dto.getStatDateBgn()) ) {
			dto.setStatDateBgn(dto.getStatDateEnd());
		}
		if (StringUtils.isBlank(dto.getStatDateEnd())) {
			dto.setStatDateEnd(DateUtil.formatAsDefaultDate(new Date()));
		}
		DevQcWorkProportion workPro = new DevQcWorkProportion();
		List<DevQcWorkStat> list = service.list(dto);
		
		if(list.size()>1){
			
			workPro = getWorkPro(list);
			
		}else{
			
			workPro.setQcPerson(QcConstant.PROPORTION);
		}
		request.setAttribute("dataList", list);
		request.setAttribute("workPro", workPro);
		request.setAttribute("dto", dto);
		return "report/devQcWorkStatList";
	}
	
	
	public DevQcWorkProportion getWorkPro(List<DevQcWorkStat> list ){
		
		DevQcWorkProportion wrok = new DevQcWorkProportion();
		DevQcWorkStat allWork = list.get(list.size()-1);
		wrok.setQcPerson(QcConstant.PROPORTION);
		wrok.setDistribNum(MathUtil.div(allWork.getDistribNum(), list.size()-1,1));	//人均分配单数
		wrok.setDoneNum(MathUtil.div(allWork.getDoneNum(), list.size()-1,1));//人均完成单数
		wrok.setDoneTotalNum(MathUtil.div(allWork.getDoneTotalNum(), list.size()-1,1));//人均 总完成单数 
		wrok.setCancelNum(MathUtil.div(allWork.getCancelNum(), list.size()-1,1));//人均撤销单数
		wrok.setCancelRate(allWork.getCancelRate());//人均撤销率
		wrok.setProblemTotalNum(MathUtil.div(allWork.getProblemTotalNum(), list.size()-1,1));  //人均研发故障单总数
		wrok.setProblemRate(allWork.getProblemRate());//人均故障率
		
		int num =0;
		for(int i =0;i<list.size()-1;i++){
			
			num+=list.get(i).getWorkDayNum();
			
		}
		wrok.setWorkDayNum(MathUtil.div(num, list.size()-1,1));//人均上班天数
		if(num>0){
			
			wrok.setAvgDailyDoneNum(MathUtil.div(allWork.getAvgDailyDoneNum(), num,1));	//人均 日均完成单数
			wrok.setAvgDailyProblemNum(MathUtil.div(allWork.getAvgDailyProblemNum(),num,1));//人均 日均完成研发故障单数
		}
		wrok.setsDevNum(MathUtil.div(allWork.getsDevNum(), list.size()-1,1)); //人均 研发故障S
		wrok.setaDevNum(MathUtil.div(allWork.getaDevNum(), list.size()-1,1));//人均 研发故障A
		wrok.setbDevNum(MathUtil.div(allWork.getbDevNum(), list.size()-1,1));//人均 研发故障B
		wrok.setcDevNum(MathUtil.div(allWork.getcDevNum(), list.size()-1,1));//人均 研发故障C
		wrok.setNoDevNum(MathUtil.div(allWork.getNoDevNum(), list.size()-1,1));//人均 非研发故障
		wrok.setTimelyDoneNum(MathUtil.div(allWork.getTimelyDoneNum(), list.size()-1,1));//人均 及时完成单数
		wrok.setExpireNum(MathUtil.div(allWork.getExpireNum(), list.size()-1,1)); //人均 到期单数
		wrok.setTimelyRate(allWork.getTimelyRate());//人均及时率
		wrok.setDoneRate(allWork.getDoneRate());//人均及时率
		return wrok;
		
	}
}
