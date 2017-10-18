package com.tuniu.qms.qc.dto;

import org.springframework.stereotype.Component;

import com.tuniu.qms.common.dto.BaseDto;
import com.tuniu.qms.qc.model.QualityProblemDetail;

@Component
public class QualityProblemDetailDto extends BaseDto<QualityProblemDetail> {

   /**导游编号**/
   private String guideId;
   
   private int start =0;
   
   private int limit =10;

   private String  qcFinishTimeBgn;
   
   private String  qcFinishTimeEnd;
   
   
	public String getQcFinishTimeBgn() {
	return qcFinishTimeBgn;
}

public void setQcFinishTimeBgn(String qcFinishTimeBgn) {
	this.qcFinishTimeBgn = qcFinishTimeBgn;
}

public String getQcFinishTimeEnd() {
	return qcFinishTimeEnd;
}

public void setQcFinishTimeEnd(String qcFinishTimeEnd) {
	this.qcFinishTimeEnd = qcFinishTimeEnd;
}

	public String getGuideId() {
		return guideId;
	}
	
	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	




}
