package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.frm.action.DBConfigManager;

/**
 * 投诉量统计Action
 * @author WangMingFang
 * 20140226
 */
@Service("complaint_action-complaintNum_stat")
@Scope("prototype")
public class ComplaintNumStatAction extends FrmBaseAction<IComplaintService, ComplaintEntity> {

	private List<String> dealDepartments = new ArrayList<String>();
	
	private String startDate;
	    
	private String endDate;
	
	private String dealDepartment;
	    
	private Integer showType;
	
	private String timeDimension;
	
	private String statisticUnit;
	
	private static final String STATIC_UNIT_HOUR = "hour";
	
	
	private Map<String,Object> info;

	public ComplaintNumStatAction() {
		setManageUrl("complaintNum_stat");
		info = new LinkedHashMap<String, Object>();
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	public void setService(IComplaintService service) {
		this.service = service;
	}
	
	/**
	 * 打开投诉统计主页面
	 */
	public String execute() {
		return "complaintNum_list";
	}
	
	public String getTableData(){
	    if(STATIC_UNIT_HOUR.equals(statisticUnit)){
	        info.put("rows", service.getComplaintNumListForHourTable(getParamMap()));
	    }else{
	        info.put("rows",  service.getComplaintNumListForDayTable(getParamMap()));
	    }
	    return "info";
	}
	
	public String getHourGraphData(){
        info = service.getComplaintNumListForHourGraph(getParamMap());
        return "info";
	}
	
	
	public String getComplaintNumListForDayGraph(){
        info.put("rows",  service.getComplaintNumListForDayGraph(getParamMap()));
        return "info";
    }
	
    private Map<String, Object> getParamMap() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("timeDimension", timeDimension);
        paramMap.put("dealDepart", dealDepartment);
        paramMap.put("assignDateBgn", startDate);
        paramMap.put("assignDateEnd", endDate);
        return paramMap;
    }
	
	
	public String getDayGraphData(){
	    return "info";
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public List<String> getDealDepartments() {
        return DBConfigManager.getConfigAsList("sys.dealDepart");
    }

    public void setDealDepartments(List<String> dealDepartments) {
        this.dealDepartments = dealDepartments;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public String getStatisticUnit() {
        return statisticUnit;
    }

    public void setStatisticUnit(String statisticUnit) {
        this.statisticUnit = statisticUnit;
    }

    public String getDealDepartment() {
        return dealDepartment;
    }

    public void setDealDepartment(String dealDepartment) {
        this.dealDepartment = dealDepartment;
    }

	public String getTimeDimension() {
		return timeDimension;
	}

	public void setTimeDimension(String timeDimension) {
		this.timeDimension = timeDimension;
	}
}
