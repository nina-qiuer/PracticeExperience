package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.service.ICenterReportService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.warning.common.Page;
import com.tuniu.infobird.vdnlog.entity.CriticalReportEntity;

/**
 * 售后中心业务关键指标报表 
 * @author chenhaitao
 *
 */

@Service("complaint_action-criticalReport")
@Scope("prototype")
public class CenterReportAction extends FrmBaseAction<ICenterReportService, CriticalReportEntity> {

	
	private Page page;
	
	private Integer year ;//年份
	
	private String dealName;//客服姓名
	
	private String state;//客服岗位
	
	private Integer week;//周
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	public CenterReportAction() {
		setManageUrl("criticalReport");
	}
	
	@Autowired
	private ICenterReportService centerReportService;
	
	
	/**
	 * 打开业务关键指标报表 主页面
	 */
	public String execute() {
		
		if(page == null){
			page = new Page();
			page.setPageSize(30);
		}
		Calendar c = Calendar.getInstance();  
		Map<String, Object>  map =new HashMap<String, Object>();
		map.put("dealName", dealName);
		if(null==year||"".equals(year)){
			
			  year = DateUtil.getField(Calendar.YEAR);    //获取年
			
		}
		if(null==week||"".equals(week)){
			
			 week = DateUtil.getField(DateUtil.getWeek(c.getTime()) -1);//本周的上一周
			
		}
		map.put("week", year+"-"+week);
		List<String> states = new ArrayList<String>();
		if("".equals(state)||null==state){
			
			states.add(Constans.BEFORE_TRAVEL);
			states.add(Constans.IN_TRAVEL);
			states.add(Constans.AFTER_TRAVEL);
			states.add("出游前客服服务");
		     
		}else{
			
			states.add(state);
			
		}
		map.put("states", states);
		map.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		map.put("dataLimitEnd", page.getPageSize());
		List<CriticalReportEntity> list =  centerReportService.queryCriticalReport(map);
		int count = centerReportService.queryCriticalReportCount(map);
		page.setCount(count);
		page.setCurrentPageCount(list==null?0:list.size());
		request.setAttribute("cirticallist", list==null?"":list);
		request.setAttribute("nowYear", DateUtil.getField(Calendar.YEAR));
		request.setAttribute("nowWeek", DateUtil.getWeek(c.getTime()));
		return "cirticalReport_list";
	}


	
	
	
	public Page getPage() {
		return page;
	}


	public void setPage(Page page) {
		this.page = page;
	}


	public String getDealName() {
		return dealName;
	}


	public void setDealName(String dealName) {
		this.dealName = dealName;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}





	public Integer getYear() {
		return year;
	}





	public void setYear(Integer year) {
		this.year = year;
	}





	public Integer getWeek() {
		return week;
	}





	public void setWeek(Integer week) {
		this.week = week;
	}


	
	
	
}
