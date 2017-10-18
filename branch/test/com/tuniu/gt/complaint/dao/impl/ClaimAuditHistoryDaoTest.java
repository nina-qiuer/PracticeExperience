/**
 * 
 */
package com.tuniu.gt.complaint.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.entity.ClaimAuditHistory;

/**
 * @author jiangye
 * 
 */
public class ClaimAuditHistoryDaoTest extends TestCaseExtend {
	
	
	ClaimAuditHistoryDao dao = (ClaimAuditHistoryDao) Bean.get("complaint_dao_impl-claim_audit_history");

	@Test
	public void testFetchList() {
		assertNotNull(dao);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("claimType", ClaimAuditHistory.SHARE_TYPE);
		List<ClaimAuditHistory> list = (List<ClaimAuditHistory>) dao.fetchList(map);
		System.out.println(list);
	}
	
	@Test
	public void testAdd() throws Exception {
		assertNotNull(dao);
		ClaimAuditHistory e = new ClaimAuditHistory();
		e.setClaimType(ClaimAuditHistory.SHARE_TYPE);
		e.setForeignId(29522);
		e.setPhrase(ClaimAuditHistory.RETRIAL_ONE);
		e.setClaimTime(new Date());
		e.setAssessor("姜野");
		dao.insert(e);
	}
}
