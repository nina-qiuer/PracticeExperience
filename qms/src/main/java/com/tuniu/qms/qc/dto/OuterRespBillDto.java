package com.tuniu.qms.qc.dto;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.OuterRespBill;

/**
 * 外部责任单
 * @author chenhaitao
 *
 */
public class OuterRespBillDto extends BaseDto<OuterRespBill>{
   
	private Integer qpId;
	
	private Integer agencyId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date addTimeFrom;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date addTimeTo;

	private Calendar calendar = Calendar.getInstance();
	
	public Integer getQpId() {
		return qpId;
	}

	public void setQpId(Integer qpId) {
		this.qpId = qpId;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public Date getAddTimeFrom() {
		return addTimeFrom;
	}

	public void setAddTimeFrom(Date addTimeFrom) {
		this.addTimeFrom = addTimeFrom;
	}

	public Date getAddTimeTo() {
		return addTimeTo;
	}

	public void setAddTimeTo(Date addTimeTo) {
		this.addTimeTo = getDate(addTimeTo);
	}
	
	private Date getDate(Date date)
    {
		if(date!=null){
			calendar.setTime(date);
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			date = calendar.getTime();
		}
	    return date;
    }

}
