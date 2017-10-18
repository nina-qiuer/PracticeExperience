/**
 * 
 */
package com.tuniu.gt.complaint.service;

import java.util.Map;

import tuniu.frm.core.IServiceBase;

/**
 * @author jiangye
 *
 */
public interface ClaimAuditHistoryService extends IServiceBase {
	String getHistoryString(Map<String,Object> paramMap);
}
