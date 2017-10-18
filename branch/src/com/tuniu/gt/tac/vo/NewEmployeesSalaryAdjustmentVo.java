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
public class NewEmployeesSalaryAdjustmentVo {
    
	private String addTimeBgn; // 添加时间开始
	private String addTimeEnd; // 添加时间结束
	private String customer; // 客服

	
	public Map toMap(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("addTimeBgn", this.addTimeBgn);
		paramMap.put("addTimeEnd", this.addTimeEnd);
		paramMap.put("customer", this.customer);
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


    public String getCustomer() {
        return customer;
    }


    public void setCustomer(String customer) {
        this.customer = customer;
    }

}
