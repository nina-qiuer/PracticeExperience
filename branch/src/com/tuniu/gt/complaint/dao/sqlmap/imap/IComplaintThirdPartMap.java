/**
 * 
 */
package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.ComplaintThirdPartEntity;

import tuniu.frm.core.IMapBase;

/**
 * @author chenyu8
 *
 */
@Repository("complaint_dao_sqlmap-complaint_third_part")
public interface IComplaintThirdPartMap extends IMapBase {

	Integer getComplaintThirdPartCount(Map<String,Object> paramMap);

	List<ComplaintThirdPartEntity> getComplaintThirdPartLists(Map<String, Object> paramMap);

}
