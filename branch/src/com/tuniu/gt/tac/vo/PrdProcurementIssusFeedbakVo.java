/**
 * 
 */
package com.tuniu.gt.tac.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tuniu.gt.workorder.entity.WorkOrderConfig;

/**
 * @author jiangye
 *
 */
public class PrdProcurementIssusFeedbakVo {
    
	private String addTimeBgn; // 添加时间开始
	private String addTimeEnd; // 添加时间结束
	private String addPerson; // 处理人
	private String department; // 组别
	private String area; // 区域

	
	public Map toMap(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("addTimeBgn", this.addTimeBgn);
		paramMap.put("addTimeEnd", this.addTimeEnd);
		paramMap.put("addPerson", this.addPerson);
		paramMap.put("department", this.department);
		paramMap.put("area", this.area);
		return paramMap;
	}


    public String getAddTimeBgn() {
        return addTimeBgn;
    }


    public void setAddTimeBgn(String addTimeBgn) {
        this.addTimeBgn = addTimeBgn;
    }


    public String getAddTimeEnd() {
        return addTimeEnd;
    }


    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }


    public String getAddPerson() {
        return addPerson;
    }


    public void setAddPerson(String addPerson) {
        this.addPerson = addPerson;
    }


    public String getDepartment() {
        return department;
    }


    public void setDepartment(String department) {
        this.department = department;
    }


    public String getArea() {
        return area;
    }


    public void setArea(String area) {
        this.area = area;
    }
	
	
	
}
