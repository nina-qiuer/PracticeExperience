/**
 * 
 */
package com.tuniu.gt.complaint.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ClaimAuditHistoryDao;
import com.tuniu.gt.complaint.entity.ClaimAuditHistory;
import com.tuniu.gt.complaint.service.ClaimAuditHistoryService;
import com.tuniu.gt.toolkit.lang.CollectionUtil;

/**
 * @author jiangye
 *
 */
@Service("complaint_service_complaint_impl-claim_audit_history")
public class ClaimAuditHistoryServiceImpl extends ServiceBaseImpl<ClaimAuditHistoryDao>  implements ClaimAuditHistoryService {
	
	@Autowired
	@Qualifier("complaint_dao_impl-claim_audit_history")
	public void setDao(ClaimAuditHistoryDao dao) {
		this.dao = dao;
	}
	
	

	@Override
	public String getHistoryString(Map<String, Object> paramMap) {
		return convertHistoryListToString(
				(List<ClaimAuditHistory>) dao.fetchList(paramMap));
	}
	
	
	private String convertHistoryListToString(List<ClaimAuditHistory>  list){
		StringBuilder sb = new StringBuilder();
		if(CollectionUtil.isNotEmpty(list)){
			sb.append("( ");
			String phrase = "";
			for (ClaimAuditHistory claimAuditHistory : list) {
				switch (claimAuditHistory.getPhrase()) {
				case 1:
					phrase = "初审";
					break;
				case 2:
					phrase = "复审一";
					break;
				case 3:
					phrase = "复审二";
					break;
				case 4:
					phrase = "复审三";
					break;
				case 5:
					phrase = "终审";
					break;
				default:
					break;
				}
				
				sb.append(phrase).append("：").append(claimAuditHistory.getAssessor()).append("， ");
			}
			
			sb.replace(sb.lastIndexOf("，"), sb.lastIndexOf("，")+2, ")");
		}
		return sb.toString();
	}

	
}
