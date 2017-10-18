package com.tuniu.gt.complaint.entity; 
import java.io.Serializable;
import java.util.Date;
import com.tuniu.gt.frm.entity.EntityBase;


public class ComplaintSendRecordEntity extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 2599078535454645324L;
	
	private Date lastSendTime;

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}
	
}
