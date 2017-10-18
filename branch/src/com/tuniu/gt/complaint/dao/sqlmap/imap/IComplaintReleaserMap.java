package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.ComplaintReleaserEntity;

@Repository("complaint_dao_sqlmap-complaint_releaser")
public interface IComplaintReleaserMap extends IMapBase {
	
	ComplaintReleaserEntity getByUserId(Map<String, Object> params);
	
	List<String> getDistinctCitys();

}
