/**
 * 
 */
package com.tuniu.qms.qs.dto;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qs.model.PreSaleReturnVisit;

/**
 * @author chenhaitao
 *
 */
public class PreSaleReturnVisitDto extends BaseDto<PreSaleReturnVisit> {
	
	
    /**订单号**/
    private String ordId;
    
    /**售前客服**/
    private String customer;
    
    /**客服经理**/
    private String customerLeader ;
    
    /**服务评分**/
    private String score;
    
    /**不满意原因**/
    private String unsatisfyReason;
    
    /**回访开始日期**/
    private String returnVisitDateBgn;
    
    /**回访结束日期**/
    private String returnVisitDateEnd;

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerLeader() {
		return customerLeader;
	}

	public void setCustomerLeader(String customerLeader) {
		this.customerLeader = customerLeader;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUnsatisfyReason() {
		return unsatisfyReason;
	}

	public void setUnsatisfyReason(String unsatisfyReason) {
		this.unsatisfyReason = unsatisfyReason;
	}

	public String getReturnVisitDateBgn() {
		return returnVisitDateBgn;
	}

	public void setReturnVisitDateBgn(String returnVisitDateBgn) {
		this.returnVisitDateBgn = returnVisitDateBgn;
	}

	public String getReturnVisitDateEnd() {
		return returnVisitDateEnd;
	}

	public void setReturnVisitDateEnd(String returnVisitDateEnd) {
		this.returnVisitDateEnd = returnVisitDateEnd;
	}
    
  
}
