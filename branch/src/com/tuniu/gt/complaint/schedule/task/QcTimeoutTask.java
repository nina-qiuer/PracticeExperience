package com.tuniu.gt.complaint.schedule.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.dao.impl.AgencyPayoutDao;
import com.tuniu.gt.complaint.dao.impl.GiftDao;
import com.tuniu.gt.complaint.dao.impl.GiftNoteDao;
import com.tuniu.gt.complaint.dao.impl.SolutionTourticketDao;
import com.tuniu.gt.complaint.dao.impl.SolutionVoucherDao;
import com.tuniu.gt.complaint.dao.impl.SupportShareDao;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.GiftNoteEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SolutionTourticketEntity;
import com.tuniu.gt.complaint.entity.SolutionVoucherEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.service.IQcTimeoutService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

/**
 * 计算质检单超时定时任务
 * 
 * @author
 * 
 */
public class QcTimeoutTask {

    private static Logger logger = Logger.getLogger(QcTimeoutTask.class);

    @Autowired
    @Qualifier("complaint_service_impl-qctimeout")
    private IQcTimeoutService qcTimeoutService;
    
    @Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;
    
    @Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
    
    @Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	private IComplaintSolutionService compSolutionService;
    
    @Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;
    
    @Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
    
    @Autowired
	@Qualifier("complaint_dao_impl-solution_voucher")
	private SolutionVoucherDao voucherDao;
    
    @Autowired
	@Qualifier("complaint_dao_impl-solution_tourticket")
	private SolutionTourticketDao tourticketDao;
    
    @Autowired
	@Qualifier("complaint_dao_impl-gift_note")
	private GiftNoteDao giftNoteDao;
    
    @Autowired
	@Qualifier("complaint_dao_impl-gift")
	private GiftDao giftDao;
    
    @Autowired
	@Qualifier("complaint_dao_impl-support_share")
	private SupportShareDao supportShareDao;
    
    @Autowired
	@Qualifier("complaint_dao_impl-agency_payout")
	private AgencyPayoutDao payoutDao;
	
    public void checkRun() {
        logger.info("--质检单超时未完成定时任务开始...");
        qcTimeoutService.checkQctimeoutUndone();
        logger.info("--质检单超时未完成定时任务结束...");
    }
    
	@SuppressWarnings("unchecked")
	public void repairData() throws Exception {
		int scopeNum = 10000;
    	for (int i=15; i>0; i--) {
    		List<ComplaintSolutionEntity> csList = compSolutionService.getNeedRepairList(scopeNum * i);
    		for (ComplaintSolutionEntity cs : csList) {
    			logger.info("RepairData Begin, complaintId is " + cs.getComplaintId());
    			
    			Map<String, Object> data = shareSolutionService.getDataForRepair(cs.getComplaintId());
    			cs.setOrderId((Integer) data.get("order_id"));
    			
				String userName = (String) data.get("username");
				String phone = (String) data.get("phone");
				int contactId = (Integer) data.get("contact_id");
				if (contactId > 0) {
					Map<String, Object> contact = ComplaintRestClient.getTouristById(contactId);
					if (null != contact) {
						if (StringUtil.isEmpty(userName)) {
							userName = (String) contact.get("name");
						}
						if (StringUtil.isEmpty(phone)) {
							phone = (String) contact.get("tel");
						}
					}
				}
				if (null == phone) {
					phone = "";
				}
				if (null == userName) {
					userName = "";
				}
				cs.setPhone(phone);
				cs.setUserName(userName);
				
				cs.setPayment(2);
				
				Object at = data.get("appointed_time");
				if (null != at) {
					cs.setAppointedTime((Date) at);
				}
				
				if (cs.getReplaceBook() > 0) { // 插入抵用券数据
					SolutionVoucherEntity sv = new SolutionVoucherEntity();
					sv.setSolutionId(cs.getId());
					sv.setMobileNo(phone);
					sv.setCustId("0");
					sv.setAmount(cs.getReplaceBook());
					voucherDao.insert(sv);
				}
				
    			Object shareIdObj = data.get("share_id");
    			if (null != shareIdObj) {
    				int shareId = (Integer) shareIdObj;
    				
    				int payment = (Integer) data.get("payment");
    				if (1 == payment || 8 == payment) { // 8：支票
    					cs.setPayment(1);
    					cs.setReceiver((String) data.get("collection_unit"));
    				} else if (2 == payment || 7 == payment || 6 == payment || 4 == payment) { // 7:信用卡, 6:刷卡，4：支付宝
    					cs.setPayment(2);
    					cs.setReceiver((String) data.get("collection_unit"));
    					cs.setBank((String) data.get("bank"));
    					cs.setAccount((String) data.get("account"));
    				} else if (3 == payment) {
    					cs.setPayment(3);
    					cs.setCollectionUnit((String) data.get("collection_unit"));
    					cs.setBank((String) data.get("bank"));
    					cs.setAccount((String) data.get("account"));
    				} else if (25 == payment) {
    					cs.setPayment(25);
    					cs.setToOrderId((String) data.get("collection_unit"));
    				} else if (10 == payment) { // 旅游券, 向旅游券表插入数据
    					SolutionTourticketEntity st = new SolutionTourticketEntity();
    					st.setSolutionId(cs.getId());
    					String mobileNo = ((String) data.get("collection_unit")).trim();
    					if (!CommonUtil.isNumeric(mobileNo)) {
    						mobileNo = phone;
    					}
    					st.setMobileNo(mobileNo);
    					st.setAmount(cs.getTouristBook());
    					tourticketDao.insert(st);
    				} else {
    					cs.setPayment(2);
    					cs.setReceiver((String) data.get("collection_unit"));
    					cs.setCollectionUnit((String) data.get("collection_unit"));
    					cs.setBank((String) data.get("bank"));
    					cs.setAccount((String) data.get("account"));
    					cs.setToOrderId((String) data.get("collection_unit"));
    				}
    				
    				Map<String, Object> params = new HashMap<String, Object>();
    				params.put("shareId", shareId);
    				List<SupportShareEntity> supportList = (List<SupportShareEntity>) supportShareDao.fetchList(params);
    				if (null != supportList) {
    					for (SupportShareEntity support : supportList) {
    						params.clear();
    						params.put("supportId", support.getId());
    						params.put("agencyId", support.getCode());
    						params.put("complaintId", support.getComplaintId());
    						payoutDao.updateSupportId(params);
    					}
    				}
    			} else if ((Integer) data.get("state") == 4) { // 分担方案不存在，插入一条
    				ShareSolutionEntity ss = new ShareSolutionEntity();
    				ss.setComplaintId(cs.getComplaintId());
    				ss.setOrderId((Integer) data.get("order_id"));
    				shareSolutionService.insert(ss);
    			}
    			
    			if (cs.getGift() > 0) { // gift
    				Map<String, Object> params = new HashMap<String, Object>();
    				params.put("solutionId", cs.getId());
    				params.put("complaintId", cs.getComplaintId());
    				giftNoteDao.updateSolutionId(params);
    				
    				params.clear();
    				params.put("complaintId", cs.getComplaintId());
    				GiftNoteEntity giftNote = giftNoteDao.fetchOne(params);
    				if (null != giftNote) {
    					params.put("giftNoteId", giftNote.getId());
        				giftDao.updateGiftNoteId(params);
    				}
    			}
    			
    			int state = (Integer) data.get("state");
    			if (4 == state) { // 根据投诉单状态，修改对客方案、分担方案状态
    				ShareSolutionEntity ss = shareSolutionService.getShareSolutionBycompalintId(cs.getComplaintId());
    				
    				String dealName = (String) data.get("dealname");
    				cs.setDealName(dealName);
    				UserEntity user = userService.getUserByName(dealName);
    				if (null != user) {
    					cs.setDealId(user.getId());
    					ss.setDealId(user.getId());
    				}
    				cs.setSubmitFlag(1);
    				cs.setSubmitTime(cs.getAddTime());
    				int auditFlag = 4;
    				if (cs.getCash() + cs.getTouristBook() <= 0) {
    					auditFlag = -1;
    				}
    				cs.setAuditFlag(auditFlag);
    				
    				ss.setSubmitFlag(1);
    				ss.setSubmitTime(ss.getAddTime());
    				int auditFlag2 = 4;
    				if (ss.getTotal() <= 0) {
    					auditFlag2 = -1;
    				}
    				ss.setAuditFlag(auditFlag2);
    				
    				shareSolutionService.update(ss);
    			}
//    			else if (3 == state) {
//    				String dealName = (String) data.get("dealname");
//    				cs.setDealName(dealName);
//    				UserEntity user = userService.getUserByName(dealName);
//    				if (null != user) {
//    					cs.setDealId(user.getId());
//    				}
//    				cs.setSubmitFlag(1);
//    				cs.setSubmitTime(cs.getAddTime());
//    				int auditFlag = 4;
//    				if (cs.getCash() + cs.getTouristBook() <= 0) {
//    					auditFlag = -1;
//    				}
//    				cs.setAuditFlag(auditFlag);
//    			}
    			
    			compSolutionService.update(cs);
    			
    			logger.info("RepairData End, complaintId is " + cs.getComplaintId());
    			Thread.sleep(500);
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
	public void repairData2() throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("status", 2);
    	params.put("timeOutFlag", -1);
    	params.put("closedStartTime", "2014-01-01");
    	List<QcEntity> qcList = (List<QcEntity>) qcService.fetchList(params);
    	if (null != qcList) {
    		for (QcEntity qc : qcList) {
    			int timeOutFlag = qcService.isTimeout(qc) ? 1 : 0;
    			qc.setTimeOutFlag(timeOutFlag);
    			qcService.update(qc);
    			logger.info("repairData, QcId is " + qc.getId());
    			Thread.sleep(500);
    		}
    	}
    }
    
}
