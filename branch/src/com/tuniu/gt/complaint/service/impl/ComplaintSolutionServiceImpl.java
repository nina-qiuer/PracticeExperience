package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.dao.impl.ComplaintDao;
import com.tuniu.gt.complaint.dao.impl.ComplaintSolutionDao;
import com.tuniu.gt.complaint.dao.impl.GiftNoteDao;
import com.tuniu.gt.complaint.dao.impl.ShareSolutionDao;
import com.tuniu.gt.complaint.dao.impl.SolutionTourticketDao;
import com.tuniu.gt.complaint.dao.impl.SolutionVoucherDao;
import com.tuniu.gt.complaint.entity.CardUniqueEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.FollowNoteThEntity;
import com.tuniu.gt.complaint.entity.GiftEntity;
import com.tuniu.gt.complaint.entity.GiftNoteEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SolutionTourticketEntity;
import com.tuniu.gt.complaint.entity.SolutionVoucherEntity;
import com.tuniu.gt.complaint.enums.DealDepartEnum;
import com.tuniu.gt.complaint.mq.ComplaintResultMQProducer;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IComplaintTaskReminderService;
import com.tuniu.gt.complaint.service.IFollowNoteThService;
import com.tuniu.gt.complaint.service.IGiftNoteService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.returnvisit.service.IPostSaleReturnVisitService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.MailUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;
@Service("complaint_service_complaint_impl-complaint_solution")
public class ComplaintSolutionServiceImpl extends ServiceBaseImpl<ComplaintSolutionDao> implements IComplaintSolutionService {
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_solution")
	public void setDao(ComplaintSolutionDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-solution_tourticket")
	private SolutionTourticketDao tourticketDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-solution_voucher")
	private SolutionVoucherDao voucherDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-gift_note")
	private GiftNoteDao giftNoteDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint")
	private ComplaintDao compDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-share_solution")
	private ShareSolutionDao shareSolutionDao;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-gift_note")
	private IGiftNoteService giftNoteService;
	
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService followNoteService;
	
	@Autowired
	@Qualifier("complaint_service_impl-follow_note_th")
	private IFollowNoteThService noteThService;
	
    @Autowired
    private ComplaintResultMQProducer complaintResultMQProducer;

    @Autowired
    @Qualifier("rv_service_impl-postSaleReturnVisit")
    private IPostSaleReturnVisitService postSaleReturnVisitService;
    
    @Autowired
    @Qualifier("tspService")
    private ComplaintTSPServiceImpl tspService;
    
    @Autowired
    @Qualifier("frm_service_system_impl-user")
    private IUserService userService;
    
    @Autowired
	private IComplaintTaskReminderService cmpTaskReminderService;
	
	@Override
	public ComplaintSolutionEntity getComplaintSolutionBycompalintId(int complaintId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		ComplaintSolutionEntity complaintSolutionEntity = dao.fetchOne(paramMap);
		return complaintSolutionEntity;
	}

	@Override
	public void saveComplaintSolution(ComplaintSolutionEntity entity) {
		dealBlank(entity);
		dealPayment(entity);
		computeTotal(entity);
		clearDefaultValue(entity);
		
		// 如果金额小于200时，audit_flag从0变成-1（无需审核）
		int minCash = DBConfigManager.getConfig("authority.audit.minCash", Integer.class)==null?200:DBConfigManager.getConfig("authority.audit.minCash", Integer.class);
		double cashTotal = entity.getCash() + entity.getTouristBook();
		if(cashTotal < minCash){
		    entity.setAuditFlag(-1);
		}
		dao.insertComplaintSolution(entity);
	}
	
	private void dealBlank(ComplaintSolutionEntity entity) {
		String bank = entity.getBank();
		if (StringUtil.isNotEmpty(bank)) {
			bank = bank.replaceAll(" ", "");
			entity.setBank(bank);
		}
		String account = entity.getAccount();
		if (StringUtil.isNotEmpty(account)) {
			account = account.replaceAll(" ", "");
			entity.setAccount(account);
		}
	}
	
	private void dealPayment(ComplaintSolutionEntity entity) {
		if (1 == entity.getPayment()) { // 现金方式
			entity.setCollectionUnit("");
			entity.setBigBank("");
			entity.setBankProvince("");
			entity.setBankCity("");
			entity.setBank("");
			entity.setAccount("");
			entity.setToOrderId("");
		} else if (2 == entity.getPayment()) { // 对私汇款
			entity.setIdCardNo("");
			entity.setCollectionUnit("");
			entity.setToOrderId("");
		} else if (3 == entity.getPayment()) { // 对公汇款
			entity.setReceiver("");
			entity.setIdCardNo("");
			entity.setToOrderId("");
		} else if (25 == entity.getPayment()) { // 转单
			entity.setReceiver("");
			entity.setIdCardNo("");
			entity.setCollectionUnit("");
			entity.setBigBank("");
            entity.setBankProvince("");
            entity.setBankCity("");
			entity.setBank("");
			entity.setAccount("");
		}
	}
	
	private void computeTotal(ComplaintSolutionEntity entity) {
		double tourTotal = 0;
		for (SolutionTourticketEntity tour : entity.getTourticketList()) {
			tourTotal += tour.getAmount();
		}
		entity.setTouristBook(tourTotal);
		
		double voucherTotal = 0;
		for (SolutionVoucherEntity voucher : entity.getVoucherList()) {
			voucherTotal += voucher.getAmount();
		}
		entity.setReplaceBook(voucherTotal);
		
		double giftTotal = 0;
		for (GiftNoteEntity giftNote : entity.getGiftInfoList()) {
			for (GiftEntity gift : giftNote.getGiftList()) {
				giftTotal += gift.getPrice()*gift.getNumber();
			}
		}
		entity.setGift(giftTotal);
	}
	
	private void clearDefaultValue(ComplaintSolutionEntity entity) {
		if ("开户行城市 银行名称 支行/分行/分理处".equals(entity.getBank())) {
			entity.setBank("");
		}
		for (GiftNoteEntity giftNote : entity.getGiftInfoList()) {
			if ("收件人".equals(giftNote.getReceiver())) {
				giftNote.setReceiver("");
			}
			if ("联系电话".equals(giftNote.getPhone())) {
				giftNote.setPhone("");
			}
			if ("收件地址".equals(giftNote.getAddress())) {
				giftNote.setAddress("");
			}
		}
	}

	@Override
	public Map<String,Object> submitComplaintSolution(ComplaintSolutionEntity entity, int userId, String realName) {
	    Map<String,Object> info = new HashMap<String, Object>();
	    info.put("success", true);
        info.put("msg", "操作成功");
		Integer complaintId = entity.getComplaintId();
		ComplaintSolutionEntity csEnt = getComplaintSolution(complaintId);
		int auditFlag = getAuditFlag(csEnt);
		csEnt.setDealId(entity.getDealId());
		csEnt.setDealName(entity.getDealName());
		csEnt.setSubmitFlag(1);
		csEnt.setSubmitTime(new Date());
		csEnt.setAuditFlag(auditFlag);
		if (0 == csEnt.getCompPersonId()) {
			int compPersonId = ComplaintRestClient.addTourist(csEnt.getUserName(), csEnt.getPhone());
			csEnt.setCompPersonId(compPersonId);
		}
		
		
		if (-1 == auditFlag) { // 如果无需审核，礼品和抵用券数据直接推送；如需要审核，待审核通过后推送
		    
		    //如果投诉人id为0，则将对客中填写的联系人作为投诉人
            int cmpPersonId = csEnt.getCompPersonId();
            if(cmpPersonId == 0){ //如果投诉人id为0，则调用crm接口，获取投诉人在crm中的id
                int retryTimes = 0;
                while(cmpPersonId==0&&retryTimes<3){//重试三次
                    cmpPersonId = ComplaintRestClient.addTourist(csEnt.getUserName(), csEnt.getPhone());
                    retryTimes++;
                    
                    if(cmpPersonId != 0) {
                        csEnt.setCompPersonId(Integer.valueOf(cmpPersonId));
                        break;
                    }
                }
            }
            
		    // 推送数据至财务
            if (csEnt.getCash() > 0) {
                int refundId = tspService.refundForCust(getParamCash(csEnt));
                if(refundId == 0) {
                    info.put("success", false);
                    info.put("msg", "调用财务接口失败，请重试");
                    return info;
                }
                csEnt.setRefundId(refundId);
            }
            
            if (csEnt.getTouristBook() > 0) {
                List<SolutionTourticketEntity> tourticketList = csEnt.getTourticketList();
                if (null != tourticketList) {
                    String resultMsg="";
                    for (SolutionTourticketEntity st : tourticketList) {
                        int refundId = tspService.refundForCust(getParamSt(csEnt, st));
                        st.setRefundId(refundId);
                        tourticketDao.update(st);
                        if(refundId == 0) {
                            if(!resultMsg.equals("")){
                                resultMsg+=",";
                            }
                            resultMsg+=st.getMobileNo();
                        }
                    }
                    if(!resultMsg.equals("")){
                        info.put("msg", resultMsg+"发放旅游券失败");
                    }
                }
            }
			giftNoteService.addGiftApplyToOa(csEnt); // 提交礼品数据至OA
			
			// （2）	调用会员中心抵用券充值接口（新） TODO
			
		}
		
		dao.update(csEnt);
		
		ComplaintEntity compEnt = compDao.get(complaintId);
		compEnt.setState(3); // 已待结
		boolean cashFlag = csEnt.getCash() + csEnt.getTouristBook()==0?true : false; // 判断金额是否大于0的状态
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", complaintId);
		params.put("submitFlag", 1);
		ShareSolutionEntity shareSolution = (ShareSolutionEntity) shareSolutionDao.fetchOne(params);
		if (null != shareSolution || (-1 == auditFlag && cashFlag)) {

	        compEnt.setFinishDate(new Date());
            compEnt.setState(4); // 已完成
            
            /* 投诉已完成或撤销时将消息推送至MQ */
            complaintResultMQProducer.sendMessage(complaintId.toString(), "Success");
            
            //调用前台网站接口，发送投诉订单状态
            complaintService.sendOrderStatus(compEnt.getOrderId(), 2);
		}
		compEnt.setThFinishFlag(0);
		compDao.update(compEnt);
		
		//回访逻辑处理
		postSaleReturnVisitService.triggerReturnVisit(compEnt,csEnt);
		
		//处理不满意结单逻辑
		dealWithSatisfaction(compEnt,csEnt);
		
		/*
		 * 出游前客户服务投诉单完结后发送rtx给相关人员(客服、客服经理、产品、产品经理)
		 * 提醒内容：投诉单（编号），已处理完成，请注意查收投诉处理邮件
		 */
		sendRtxToRelatedUsers(compEnt);
		
		// 添加有效操作记录
		String noteContent = Constans.SUBMIT_SOLUTION;
		followNoteService.addFollowNote(complaintId, userId, realName, noteContent, 0, Constans.SUBMIT_SOLUTION);
		
		// 对客解决方案，插入第三方中间层
		if (null != compEnt.getCompCity() && !"".equals(compEnt.getCompCity())) {
			FollowNoteThEntity noteThEnt = new FollowNoteThEntity();
			noteThEnt.setComplaintId(complaintId);
			noteThEnt.setPersonId(userId);
			noteThEnt.setPersonName(realName);
			noteThEnt.setFlowName(Constans.SUBMIT_SOLUTION);
			noteThEnt.setContent(noteContent);
			noteThService.insert(noteThEnt);
		}
		
		Map<String, Object> taskRemindMap = new HashMap<String, Object>();
		taskRemindMap.put("cmpId", complaintId);
		taskRemindMap.put("userId", userId);
		taskRemindMap.put("userName", realName);
		cmpTaskReminderService.updateTaskReminder(taskRemindMap);
		
		return info;
	}
	
	/**
     * @param compEnt
     */
    private void sendRtxToRelatedUsers(ComplaintEntity compEnt) {
        if(DealDepartEnum.SPECIAL_BEFORE_TRAVEL.getValue().equals(compEnt.getDealDepart())){
            List<String> relatedUsers = getCmpRelatedUsers(compEnt);
            String title = "投诉单完结提醒["+compEnt.getId()+"]";
            String content = "投诉单（"+compEnt.getId()+"），已处理完成，请注意查收投诉处理邮件";
            sendRtxToUsers(relatedUsers,title,content);
        }
    }

    /**
     * @param relatedUsers
     * @param title
     * @param content
     */
    private void sendRtxToUsers(List<String> relatedUsers, String title, String content) {
        if(CollectionUtil.isNotEmpty(relatedUsers)){
            for(String userName : relatedUsers) {
                if(StringUtils.isNotEmpty(userName)){
                    UserEntity customerEntity = userService.getUserByRealName(userName);
                    if(customerEntity!=null&&customerEntity.getId()!=null&&customerEntity.getId()!=0){
                        new RTXSenderThread(customerEntity.getId(), customerEntity.getRealName(), title, content).start();
                    }
                }
            }
        }
    }

    /**
     * @param compEnt
     * @return
     */
    private List<String> getCmpRelatedUsers(ComplaintEntity cmpEntity) {
        List<String> relatedUsers = new ArrayList<String>();
        relatedUsers.add(cmpEntity.getCustomer());
        relatedUsers.add(cmpEntity.getCustomerLeader());
        relatedUsers.add(cmpEntity.getProducter());
        relatedUsers.add(cmpEntity.getProductLeader());
        return relatedUsers;
    }

    /**
	 * 处理不满意结单逻辑
     * @param compEnt
     * @param csEnt
     */
    private void dealWithSatisfaction(ComplaintEntity compEnt, ComplaintSolutionEntity csEnt) {
        if(csEnt.getSatisfactionFlag()==0){
            String descript = csEnt.getDescript(); // 对客备注
            Integer complaintId = compEnt.getId();
            String dealName = compEnt.getDealName();
            UserEntity dealUser = userService.getUserByRealName(dealName);
            UserEntity managerUser = ComplaintRestClient.getReporter(dealName);
            
            String title = "投诉单不满意完结提醒["+complaintId+"]";
            StringBuilder content = new StringBuilder("投诉单号：").append(complaintId).append("\r");
            content.append("处理人：").append(dealName).append("\r");
            content.append("对客解决方案备注：").append(descript);
            
            //1.rtx提醒
            if(managerUser.getRealName()!=""){
                new RTXSenderThread(managerUser.getId(), managerUser.getRealName(), title, content.toString()).start();
            }
            
            //2.邮件提醒
            String[] receips = new String[]{dealUser.getEmail(),managerUser.getEmail()};
            String dealDepart = compEnt.getDealDepart();
            String ccEmail = "";
            if(Constans.SPECIAL_BEFORE_TRAVEL.equals(dealDepart)){
                ccEmail = "caoye@tuniu.com";
            }else if(Constans.IN_TRAVEL.equals(dealDepart)){
                ccEmail = "fangyong2@tuniu.com";
            }else if(Constans.AFTER_TRAVEL.equals(dealDepart)){
                ccEmail = "daizhoufeng@tuniu.com";
            }
            String[] ccEmails = new String[]{ccEmail};
            tspService.sendMail(new MailerThread(receips, ccEmails, title, content.toString().replaceAll("\\r", "<br/>")));
        }
    }

    private int getAuditFlag(ComplaintSolutionEntity csEnt) {
        int minCash = DBConfigManager.getConfig("authority.audit.minCash", Integer.class)==null?200:DBConfigManager.getConfig("authority.audit.minCash", Integer.class);
		return (csEnt.getCash() + csEnt.getTouristBook()) >= minCash ? 0 : -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ComplaintSolutionEntity getComplaintSolution(Integer complaintId) {
		ComplaintSolutionEntity entity = dao.getByComplaintId(complaintId);
		if (null != entity) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("solutionId", entity.getId());
			List<SolutionTourticketEntity> tourticketList = (List<SolutionTourticketEntity>) tourticketDao.fetchList(paramMap);
			if (null != tourticketList) {
				for (SolutionTourticketEntity st : tourticketList) {
					if (st.getRefundId() > 0) {
						st.setRefundState(tspService.getRefundState(st.getRefundId()));
					}
				}
			}
			entity.setTourticketList(tourticketList);
			entity.setVoucherList((List<SolutionVoucherEntity>) voucherDao.fetchList(paramMap));
			entity.setGiftInfoList(giftNoteDao.getListBySolutionId(paramMap));
			if (entity.getRefundId() != null && entity.getRefundId() > 0) {
				entity.setRefundState(tspService.getRefundState(entity.getRefundId()));
			}
		}
		return entity;
	}

	@Override
	public void updateComplaintSolution(ComplaintSolutionEntity entity) {
		entity.setDelFlag(1);
		dealBlank(entity);
		dealPayment(entity);
		computeTotal(entity);
		clearDefaultValue(entity);
		dao.update(entity);
		
		tourticketDao.deleteBySolutionId(entity.getId());
		tourticketDao.insertList(entity.getId(), entity.getTourticketList());
		
		voucherDao.deleteBySolutionId(entity.getId());
		voucherDao.insertList(entity.getId(), entity.getVoucherList());
		
		giftNoteDao.deleteBySolutionId(entity.getId());
		giftNoteDao.insertList(entity.getId(), entity.getGiftInfoList());
	}

	@Override
	public void auditComplaintSolution(ComplaintSolutionEntity entity,Map<String,Object> info) {
	    if (4 == entity.getAuditFlag()) {
			entity.setAuditTime(new Date());
			
			//如果投诉人id为0，则将对客中填写的联系人作为投诉人
			int cmpPersonId = entity.getCompPersonId();
			if(cmpPersonId == 0){ //如果投诉人id为0，则调用crm接口，获取投诉人在crm中的id
			    int retryTimes = 0;
			    while(cmpPersonId==0&&retryTimes<3){//重试三次
			        cmpPersonId = ComplaintRestClient.addTourist(entity.getUserName(), entity.getPhone());
			        retryTimes++;
			        
			        if(cmpPersonId != 0) {
			            entity.setCompPersonId(Integer.valueOf(cmpPersonId));
			            break;
			        }
			    }
			}
			
			if(cmpPersonId == 0) {
			    info.put("success", false);
                info.put("msg", "调用CRM接口失败，请重试");
                return;
			}
			
			// 推送数据至财务
			if (entity.getCash() > 0) {
				int refundId = tspService.refundForCust(getParamCash(entity));
				if(refundId == 0) {
				    info.put("success", false);
				    info.put("msg", "调用财务接口失败，请重试");
				    return;
				}
				entity.setRefundId(refundId);
			}
			
			List<SolutionTourticketEntity> tourticketList = entity.getTourticketList();
			if (null != tourticketList) {
				String resultMsg="";
				for (SolutionTourticketEntity st : tourticketList) {
					int refundId = tspService.refundForCust(getParamSt(entity, st));
					st.setRefundId(refundId);
					tourticketDao.update(st);
					if(refundId == 0) {
						if(!resultMsg.equals("")){
							resultMsg+=",";
						}
						resultMsg+=st.getMobileNo();
					}
				}
				if(!resultMsg.equals("")){
					info.put("msg", resultMsg+"发放旅游券失败");
				}
			}
			
			giftNoteService.addGiftApplyToOa(entity); // 提交礼品数据至OA
		}
		dao.update(entity);
	}
	
	/**
	 * complainId  新增
	 * 
	 * */
	private Map<String, Object> getParamCash(ComplaintSolutionEntity entity) {
		Map<String, Object> paramCash = new HashMap<String, Object>();
		paramCash.put("orderId", entity.getOrderId());
		Integer refundCompanyId = tspService.queryRefundCompanyId(entity.getOrderId());
		paramCash.put("refundCompanyId", refundCompanyId);
		paramCash.put("applyAmount", entity.getCash());
		paramCash.put("personId", entity.getCompPersonId());
		String collection = "";
		switch (entity.getPayment()) {
		case 1:
			collection = entity.getReceiver();
			paramCash.put("collectionCompany", collection);
			paramCash.put("cardUniqueId", "0");
			break;
		case 2:
			collection = entity.getCardUniqueId();
			paramCash.put("cardUniqueId", collection);
			break;
		case 3:
			collection = entity.getCardUniqueId();
			paramCash.put("cardUniqueId", collection);
			break;
		case 25:
			collection = entity.getToOrderId();
			paramCash.put("collectionCompany", collection);
			paramCash.put("cardUniqueId", "0");
			break;
		}
		paramCash.put("refundMethod", entity.getPayment());
		Date appointedTime =  entity.getAppointedTime();
		if(appointedTime == null) {
		    appointedTime = new Date();
		}
		paramCash.put("preRefundTime", DateUtil.formatDate(appointedTime, "yyyy-MM-dd HH:mm:ss"));
		paramCash.put("remark", entity.getDescript());
		paramCash.put("salerId", entity.getDealId());
		paramCash.put("complainId", entity.getComplaintId());
		return paramCash;
	}
	
	private Map<String, Object> getParamSt(ComplaintSolutionEntity entity, SolutionTourticketEntity st) {
		Map<String, Object> paramSt = new HashMap<String, Object>();
		paramSt.put("orderId", entity.getOrderId());
		Integer refundCompanyId = tspService.queryRefundCompanyId(entity.getOrderId());
		paramSt.put("refundCompanyId", refundCompanyId);
		paramSt.put("applyAmount", st.getAmount());
		paramSt.put("cardUniqueId", "0");
		//财务根据personId去查询电话，并将旅游券打给该号码
		int personId = ComplaintRestClient.addTourist(entity.getUserName(), st.getMobileNo());
		paramSt.put("personId", personId);
		paramSt.put("collectionCompany", st.getMobileNo());
		paramSt.put("refundMethod", 10);
		Date appointedTime =  entity.getAppointedTime();
        if(appointedTime == null) {
            appointedTime = new Date();
        }
		paramSt.put("preRefundTime", DateUtil.formatDate(appointedTime, "yyyy-MM-dd HH:mm:ss"));
		paramSt.put("remark", entity.getDescript());
		paramSt.put("salerId", entity.getDealId());
		paramSt.put("complainId", entity.getComplaintId());
		return paramSt;
	}

	@Override
	public void returnComplaintSolution(Integer complaintId, String backName, String backMsg) {
		ComplaintSolutionEntity entity = getComplaintSolution(complaintId);
		entity.setAuditFlag(9);
		dao.logicDel(entity);
		dao.insertComplaintSolution(entity);
		
		ComplaintEntity complaint = compDao.get(entity.getComplaintId());
		complaint.setState(2);
		compDao.update(complaint);
		if (Constans.AUDIT_BACK.equals(backName)) {
			/* 发送退回RTX提醒 */
			String title="对客方案退回提醒";
			String content="对客解决方案被" + backName + "\n" +
					"投诉单号:" + complaint.getId() + "\n" +
					"订单号:" + complaint.getOrderId() + "\n" + 
					"原因描述：" + backMsg + "\n";
			new RTXSenderThread(entity.getDealId(), entity.getDealName(), title, content).start();
		}
		if (Constans.FINANCE_BACK.equals(backName)) {
			/* 发送退回RTX提醒 */
			String title="对客方案退回提醒";
			String content="赔款单作废,对客解决方案退回\n" +
					"投诉单号:" + complaint.getId() + "\n" +
					"订单号:" + complaint.getOrderId() + "\n" +
					"原因描述：" + backMsg + "\n";
			if(entity.getTouristBook()>0||entity.getGift()>0){
				content+="<br><br>如果旅游券（礼品）发过了，再次提交的时候 把旅游券（礼品）删掉。"
						+ "<br>在备注里面说明 ，已经发过旅游券（礼品）了，不要重复提交旅游券（礼品）。"
						+ "<br>旅游券发放信息请联系xuyunfeng，礼品信息联系lihui2。";
			}
			new RTXSenderThread(complaint.getDeal(), complaint.getDealName(), title, content).start();
			UserEntity deal = userService.getUserByRealName(complaint.getDealName());
			List<String> recipientsNames = new ArrayList<String>();
			recipientsNames.add(deal.getEmail());
			List<String> ccEmails = new ArrayList<String>();
			UserEntity reportUser=null;
			try {
				reportUser= ComplaintRestClient.getReporter(complaint.getDealName());
				if(reportUser!=null){
					new RTXSenderThread(reportUser.getId(), reportUser.getRealName(), title, content).start();
					ccEmails.add(reportUser.getEmail());
				}
			} catch (Exception e) {
				
			}
			tspService.sendMail(new MailerThread(recipientsNames.toArray(new String[recipientsNames.size()]),
					ccEmails.toArray(new String[ccEmails.size()]), title, content));
		}
	}

	@Override
	public List<ComplaintSolutionEntity> getNeedRepairList(int scope) {
		return dao.getNeedRepairList(scope);
	}

	@Override
	public List<ComplaintSolutionEntity> getCmpSolutionByCmpId(
			Map<String, Object> map) {
		
		
		return dao.getCmpSolutionByCmpId(map);
	}
	
	@Override
	public List<ComplaintSolutionEntity> getNeedSynchSolution(Map<String, Object> map){
		return dao.getNeedSynchSolution(map);
	}
	
	@Override
	public void updateCardUniqueId(Map<String, Object> map){
		dao.updateCardUniqueId(map);
	}
	
	@Override
	public ComplaintSolutionEntity setCardInfo(ComplaintSolutionEntity entity){
		if(entity!=null&&entity.getCardUniqueId()!=""){
			CardUniqueEntity cardUniqueEntity=tspService.queryCardInfoByCardId(entity.getCardUniqueId());
			if(cardUniqueEntity!=null){
				entity.setBank(cardUniqueEntity.getBankBranchName());
				entity.setAccount(cardUniqueEntity.getCardNo());
				entity.setBankProvince(cardUniqueEntity.getProvince());
				entity.setBankCity(cardUniqueEntity.getCity());
				entity.setBigBank(cardUniqueEntity.getBankName());
				entity.setCollectionUnit(cardUniqueEntity.getAccName());
			}
		}
		return entity;
	}
	
	@Override
	public List<ComplaintSolutionEntity> getComplaintIdByReFundId(Map<String, Object> paramMap){
		return dao.getComplaintIdByReFundId(paramMap);
	}
}
