package com.tuniu.gt.complaint.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.PassingLogRecordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.google.common.collect.Lists;
import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.dao.impl.AgencyAppealDao;
import com.tuniu.gt.complaint.dao.impl.ComplaintDao;
import com.tuniu.gt.complaint.dao.impl.ComplaintQualityToolDao;
import com.tuniu.gt.complaint.dao.impl.ComplaintSolutionDao;
import com.tuniu.gt.complaint.dao.impl.EmployeeShareDao;
import com.tuniu.gt.complaint.dao.impl.ShareSolutionDao;
import com.tuniu.gt.complaint.dao.impl.SupportShareDao;
import com.tuniu.gt.complaint.entity.AgencyAppealEntity;
import com.tuniu.gt.complaint.entity.AgencyEntity;
import com.tuniu.gt.complaint.entity.AgengcyPayoutEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintQualityToolEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.EmployeeShareEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SupportAppealRecordEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.enums.ApprovalResultEnum;
import com.tuniu.gt.complaint.enums.SupportConfirmStateEnum;
import com.tuniu.gt.complaint.mq.ComplaintResultMQProducer;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.complaint.service.ISupportAppealRecordService;
import com.tuniu.gt.complaint.util.PageUtil;
import com.tuniu.gt.complaint.vo.AgencyConfirmInfoPage;
import com.tuniu.gt.complaint.vo.AgencyPayInfoPage;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IFestivalService;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.MailUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.operation.platform.base.text.StringUtils;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;
@Service("complaint_service_complaint_impl-share_solution")
public class ShareSolutionServiceImpl extends ServiceBaseImpl<ShareSolutionDao> implements IShareSolutionService {
	
	private static Logger logger = Logger.getLogger(ShareSolutionServiceImpl.class);
	
	@Autowired
	@Qualifier("complaint_dao_impl-share_solution")
	public void setDao(ShareSolutionDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	private ComplaintResultMQProducer complaintResultMQProducer;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint")
	private ComplaintDao compDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_solution")
	private ComplaintSolutionDao cmpSolutionDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-support_share")
	private SupportShareDao supportShareDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-employee_share")
	private EmployeeShareDao employeeShareDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_quality_tool")
	private ComplaintQualityToolDao qualityToolDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-agency_appeal")
	private AgencyAppealDao appealDao;
	
	@Autowired
	@Qualifier("frm_service_system_impl-festival")
	private IFestivalService festivalService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
    @Qualifier("tspService")
	private ComplaintTSPServiceImpl tspService;
	
	@Autowired
    @Qualifier("complaint_service_complaint_impl-support_appeal_record")
	private ISupportAppealRecordService supportAppealRecordService;
	
	
	@Override
	public ShareSolutionEntity getShareSolutionBycompalintId(int complaintId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		ShareSolutionEntity shareSolutionEntity=dao.fetchOne(paramMap);
		
		return shareSolutionEntity;
	}

	@Override
	public List<Map<String, Object>> getConfirmInfoNum(int agencyId) {
		return dao.getConfirmInfoNum(agencyId);
	}

	@Override
	public AgencyPayInfoPage getAgencyPayInfoPage(Map<String, Object> paramMap) {
		AgencyPayInfoPage page = new AgencyPayInfoPage();
		
		int totalRecords = dao.getAgencyPayoutNum(paramMap);
		page.setCount(totalRecords);
		
		List<Map<String, Object>> dataList = dao.getAgencyPayInfoList(paramMap);
		page.setRows(dataList);
		
		return page;
	}
	
	/**
	 * 获取对接nb供应商赔付
	 */
	@Override
	public AgencyConfirmInfoPage getAgencyConfirmInfoPage(Map<String, Object> paramMap) {
		AgencyConfirmInfoPage page=(AgencyConfirmInfoPage) paramMap.get("page");
		paramMap = getParamMap(paramMap,page);
		
		int totalRecords = dao.getAgencyPayoutNum(paramMap);
		if (totalRecords > 0) {
			int totalPages = PageUtil.getTotalPages(totalRecords);
			int pageNo = PageUtil.processPageNo(page.getPageNo(), totalPages);;
			page.setTotalRecords(totalRecords);
			page.setTotalPages(totalPages);
			page.setPageNo(pageNo);
			page.setPageSize(PageUtil.PAGE_SIZE);
			
			paramMap.put("start", PageUtil.getStartPosition(pageNo));
			paramMap.put("limit", PageUtil.PAGE_SIZE);
			
			List<Map<String, Object>> dataList = dao.getAgencyPayInfoList(paramMap);
			page.setDataList(dataList);
		}
		
		return page;
	}
	
	/**
	 * 获取非nb供应商赔付
	 */
	@Override
	public AgencyConfirmInfoPage getNonbShareAppealInfoPage(Map<String, Object> paramMap) {
		AgencyConfirmInfoPage page=(AgencyConfirmInfoPage) paramMap.get("page");
		paramMap.put("nonbShareFlag",true);
		paramMap = getParamMap(paramMap,page);
		int totalRecords = dao.getAgencyPayoutNum(paramMap);
		if (totalRecords > 0) {
			int totalPages = PageUtil.getTotalPages(totalRecords);
			int pageNo = PageUtil.processPageNo(page.getPageNo(), totalPages);;
			page.setTotalRecords(totalRecords);
			page.setTotalPages(totalPages);
			page.setPageNo(pageNo);
			page.setPageSize(PageUtil.PAGE_SIZE);
			
			paramMap.put("start", PageUtil.getStartPosition(pageNo));
			paramMap.put("limit", PageUtil.PAGE_SIZE);
			
			List<Map<String, Object>> dataList = dao.getAgencyPayInfoList(paramMap);
			page.setDataList(dataList);
		}
		
		return page;
	}

	private Map<String, Object> getParamMap(Map<String, Object> paramMap,AgencyConfirmInfoPage page) {
		paramMap.put("agencyId", page.getAgencyId());
		String agencyName = page.getAgencyName();
		if (StringUtil.isNotEmpty(agencyName)) {
			paramMap.put("agencyName", "%" + page.getAgencyName() + "%");
		}
		paramMap.put("complaintId", page.getComplaintId());
		paramMap.put("orderId", page.getOrderId());
		paramMap.put("routeId", page.getRouteId());
		
//		if(paramMap.get("nonbShareFlag")==null){
//			String menuId = "menu_1";
//			if (StringUtil.isNotEmpty(page.getMenuId())) {
//				menuId = page.getMenuId();
//			} else {
//				page.setMenuId(menuId);
//			}
//			if ("menu_1".equals(menuId)) {
//				paramMap.put("processStatus", 0);
//			} else if ("menu_2".equals(menuId)) {
//				paramMap.put("processStatus", 1);
//				String confirmStateStr = "1,2,3,4,5,6";
//				if (page.getConfirmState() > 0) {
//					confirmStateStr = String.valueOf(page.getConfirmState());
//				}
//				paramMap.put("confirmStateStr", confirmStateStr);
//			}
//		}

        paramMap.put("confirmState", page.getConfirmState());
		
		String startCity = page.getStartCity();
		if (StringUtil.isNotEmpty(startCity)) {
			paramMap.put("startCity", "%" + startCity + "%");
		}
		
		String compTimeBgn = page.getComplaintDateBgn();
		if (StringUtil.isNotEmpty(compTimeBgn)) {
			paramMap.put("compTimeBgn", compTimeBgn + " 00:00:00");
		}
		
		String compTimeEnd = page.getComplaintDateEnd();
		if (StringUtil.isNotEmpty(compTimeEnd)) {
			paramMap.put("compTimeEnd", compTimeEnd + " 23:59:59");
		}
		
		String startTimeBgn = page.getStartDateBgn();
		if (StringUtil.isNotEmpty(startTimeBgn)) {
			paramMap.put("startTimeBgn", startTimeBgn + " 00:00:00");
		}
		
		String startTimeEnd = page.getStartDateEnd();
		if (StringUtil.isNotEmpty(startTimeEnd)) {
			paramMap.put("startTimeEnd", startTimeEnd + " 23:59:59");
		}
		
		return paramMap;
	}

	@Override
	public Map<String, Object> getAgencyPayInfoDetail(Map<String, Object> paramMap) {
		return dao.getAgencyPayInfoDetail(paramMap);
	}

	@Override
	public int confirmPayout(Map<String, Object> paramMap) {
		int resultCode = 0;
		
		dao.confirmPayout(paramMap);
		
		Map<String, Object> resultMap = dao.getAgencyPayInfoDetail(paramMap);
		if (null != resultMap && !resultMap.isEmpty()) {
			String confirmState = (String) resultMap.get("confirmState");
			if (null == confirmState || !"已确认".equals(confirmState)) {
				double confirmAmount = (Double) paramMap.get("localCurrencyAmount");
				double localCurrencyAmount = ((BigDecimal) resultMap.get("localCurrencyAmount")).doubleValue();
				if (confirmAmount != localCurrencyAmount) {
					resultCode = 230002;
				} else {
					resultCode = 230003;
				}
			}
		} else {
			resultCode = 230001;
		}
		
		return resultCode;
	}

	@Override
	public int appealPayout(Map<String, Object> paramMap) {
		int resultCode = 0;
		
		dao.appealPayout(paramMap); // 申诉
		
		Map<String, Object> resultMap = dao.getAgencyPayInfoDetail(paramMap);
		if (null != resultMap && !resultMap.isEmpty()) {
			String confirmStateStr = (String) resultMap.get("confirmState");
			if (null == confirmStateStr || !"已申诉".equals(confirmStateStr)) {
				double confirmAmount = (Double) paramMap.get("localCurrencyAmount");
				double localCurrencyAmount = ((BigDecimal) resultMap.get("localCurrencyAmount")).doubleValue();
				if (confirmAmount != localCurrencyAmount) {
					resultCode = 230005;
				} else {
					resultCode = 230006;
				}
			}
		} else {
			resultCode = 230004;
		}
		
		if (0 == resultCode) {
			/* 记录申诉信息 */
			Map<String, Object> detail = dao.getAgencyPayInfoDetail(paramMap);
			int orderId = (Integer) detail.get("orderId");
			AgencyAppealEntity appealEntity = new AgencyAppealEntity();
			appealEntity.setAgencyId((Integer) paramMap.get("agencyId"));
			appealEntity.setComplaintId((Integer) paramMap.get("complaintId"));
			appealEntity.setOrderId(orderId);
			appealEntity.setAmount((Double) paramMap.get("localCurrencyAmount"));
			appealEntity.setAppealer((String) paramMap.get("appealer"));
			appealEntity.setContactPhone((String) paramMap.get("contactPhone"));
			appealEntity.setContactEmail((String) paramMap.get("contactEmail"));
			appealEntity.setContactQQ((String) paramMap.get("contactQQ"));
			appealEntity.setAppealContent((String) paramMap.get("appealContent"));
			appealDao.insert(appealEntity);
		}
		return resultCode;
	}
	
	

	String[] getCcForAppealPayout(ComplaintEntity compEnt) {
		//List<String> ccList = new ArrayList<String>();
		List<String> ccList = Lists.newArrayList();
		ccList.add("supplier@tuniu.com");
		
		if(compEnt!=null && Constans.ROUTE_FLY.equals(compEnt.getRoute())){
			//孙智超2、符超、卢雪、马丽9、张劲松、吴婷4、吴姝月、贾昊若、韩正磊
			ccList.add("sunzhichao2@tuniu.com");
			ccList.add("fuchao@tuniu.com");
			ccList.add("luxue@tuniu.com");
			ccList.add("mali9@tuniu.com");
			ccList.add("zhangjinsong@tuniu.com");
			ccList.add("wuting4@tuniu.com");
			ccList.add("wushuyue@tuniu.com");
			ccList.add("jiahaoruo@tuniu.com");
			ccList.add("hanzhenglei@tuniu.com");
		}

		return ccList.toArray(new String[ccList.size()]);
	}
	
	private String[] getReEmails(ComplaintEntity compEnt) {
		List<String> nameList = new ArrayList<String>();
		if (StringUtil.isNotEmpty(compEnt.getProducter())) {
			nameList.add("'" + compEnt.getProducter() + "'");
		}
		if (StringUtil.isNotEmpty(compEnt.getProductLeader())) {
			nameList.add("'" + compEnt.getProductLeader() + "'");
		}
		if (StringUtil.isNotEmpty(compEnt.getSeniorManager())) {
			nameList.add("'" + compEnt.getSeniorManager() + "'");
		}
		
		String realNames = CommonUtil.arrToStr(nameList.toArray(new String[nameList.size()]));
		List<UserEntity> recipients = new ArrayList<UserEntity>();
		if (StringUtil.isNotEmpty(realNames)) {
			recipients = userService.getUsers("realNames", realNames);
		}
		
		List<String> reEmails = new ArrayList<String>();
		for (UserEntity user : recipients) {
			reEmails.add(user.getEmail());
		}
		
		return reEmails.toArray(new String[reEmails.size()]);
	}
	
	private String getTitle(Map<String, Object> detail) {
		StringBuffer sbTitle = new StringBuffer();
		sbTitle.append("订单号[").append((Integer) detail.get("orderId")).append("]—供应商[")
		       .append((String) detail.get("agencyName")).append("]—申诉信");
		return sbTitle.toString();
	}
	
	private String getContent(Map<String, Object> detail, Map<String, Object> paramMap, ComplaintEntity compEnt) {
		StringBuffer sbContent = new StringBuffer();
		sbContent.append("<table><tr><td align='right' width='100'><strong>订单号：</strong></td><td align='left'>").append((Integer) detail.get("orderId")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>投诉单号：</strong></td><td align='left'>").append((Integer) paramMap.get("complaintId")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>供应商ID：</strong></td><td align='left'>").append((Integer) paramMap.get("agencyId")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>供应商名称：</strong></td><td align='left'>").append((String) detail.get("agencyName")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>申诉人：</strong></td><td align='left'>").append((String) paramMap.get("appealer")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>联系电话：</strong></td><td align='left'>").append((String) paramMap.get("contactPhone")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>联系邮箱：</strong></td><td align='left'>").append((String) paramMap.get("contactEmail")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>联系QQ：</strong></td><td align='left'>").append((String) paramMap.get("contactQQ")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>线路号：</strong></td><td align='left'>").append((Integer) detail.get("routeId")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>线路名称：</strong></td><td align='left'>").append((String) detail.get("routeName")).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>产品经理：</strong></td><td align='left'>").append(compEnt.getProductLeader()).append("</td></tr>")
		         .append("<tr><td align='right' width='100'><strong>高级产品经理：</strong></td><td align='left'>").append(compEnt.getSeniorManager()).append("</td></tr>")
		         .append("<tr><td align='right' width='100' valign='top'><strong>申诉内容：</strong></td><td align='left'>").append((String) paramMap.get("appealContent")).append("</td></tr></table>");
		return sbContent.toString().replaceAll("null", "");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShareSolutionEntity getShareSolution(Integer complaintId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		ShareSolutionEntity entity = dao.fetchOne(paramMap);
		if (null != entity) {
			paramMap.clear();
			paramMap.put("shareId", entity.getId());
			entity.setEmployeeShareList((List<EmployeeShareEntity>) employeeShareDao.fetchList(paramMap));
			entity.setQualityToolList((List<ComplaintQualityToolEntity>) qualityToolDao.fetchList(paramMap));
			entity.setSupportShareList(supportShareDao.getSupportShareList(entity.getId()));
		}
		return entity;
	}

	@Override
	public void saveShareSolution(ShareSolutionEntity entity) {
		logger.info("saveShareSolution Begin: complaintId=" + entity.getComplaintId());
		
		dao.insertShareSolution(entity);
		
		logger.info("saveShareSolution End: complaintId=" + entity.getComplaintId());
	}

	@Override
	public void updateShareSolution(ShareSolutionEntity entity) {
		logger.info("updateShareSolution Begin: complaintId=" + entity.getComplaintId());
		dao.update(entity);
		
		Integer shareId = entity.getId();
		supportShareDao.deleteByShareId(shareId);
		supportShareDao.insertList(entity.getComplaintId(), shareId, entity.getSupportShareList());
		employeeShareDao.deleteByShareId(shareId);
		employeeShareDao.insertList(shareId, entity.getEmployeeShareList());
		qualityToolDao.deleteByShareId(shareId);
		qualityToolDao.insertList(shareId, entity.getQualityToolList());
		
		logger.info("updateShareSolution End: complaintId=" + entity.getComplaintId());
	}

	@Override
	public void submitShareSolution(ShareSolutionEntity entity) {
		Integer complaintId = entity.getComplaintId();
		logger.info("submitShareSolution Begin: complaintId=" + complaintId);
		dao.update(entity);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", complaintId);
		params.put("submitFlag", 1);
		ComplaintSolutionEntity cmpSolution = (ComplaintSolutionEntity) cmpSolutionDao.fetchOne(params);
		if (null != cmpSolution) { // 判断对客解决方案是否已提交，如已提交，则投诉单状态变为已完成
			ComplaintEntity compEnt = compDao.get(complaintId);
			compEnt.setFinishDate(new Date());
			compEnt.setState(4); // 已完成
			compDao.update(compEnt);
			
			/* 投诉已完成或撤销时将消息推送至MQ */
			complaintResultMQProducer.sendMessage(complaintId.toString(), "Success");
			
			//调用前台网站接口，发送投诉单状态
			complaintService.sendOrderStatus(compEnt.getOrderId(), 2);
		}
		
		logger.info("submitShareSolution End: complaintId=" + complaintId);
	}

	@Override
	public void auditShareSolution(ShareSolutionEntity entity, int userId) {
		if (4 == entity.getAuditFlag()) {
			entity.setAuditTime(new Date());
			List<SupportShareEntity> ssList = entity.getSupportShareList();
			List<Map<String, Object>> nbLogList = new ArrayList<Map<String, Object>>();
			for (SupportShareEntity ssEnt : ssList) {
				/* 审核通过，同步数据至财务 */
				syncDataToFinance(ssEnt, 1, userId);
				
				if (1 == ssEnt.getNbFlag()) { // NbFlag为1，走NB审核流程
					ssEnt.setConfirmState(0);
					ssEnt.setExpireTime(festivalService.getWorkDaysEndTime(5)); // 设置5个工作日结束时间点
					supportShareDao.update(ssEnt);
					
					Map<String, Object> logMap = new HashMap<String, Object>();
					logMap.put("operateObjectId", entity.getComplaintId());
					logMap.put("agencyId", ssEnt.getCode());
					logMap.put("operateIp", "CMP");
					logMap.put("operateType", "7-41");
					logMap.put("operateSubType", 1);
					logMap.put("operateContent", "生成供应商赔偿通知单信息，待供应商确认");
					logMap.put("operateClient", 1);
					logMap.put("operator", entity.getDealId());
					nbLogList.add(logMap);
				}
			}
			if (nbLogList.size() > 0) {
				//查询投诉单判断是否为新分销，来区分调用的restful接口
				boolean flag = complaintService.getDistributeFlag(entity.getComplaintId());
				
				ComplaintRestClient.recordNbLog(nbLogList, flag);
			}
		}
		dao.update(entity);
	}

	@Override
	public void adjustShareSolution(ShareSolutionEntity entity, int userId) {
		logger.info("adjustShareSolution Begin: complaintId=" + entity.getComplaintId());
		
		/* 先将原数据设置为失效 */
		ShareSolutionEntity oldEntity = getShareSolution(entity.getComplaintId());
		oldEntity.setDelFlag(0);
		dao.logicDel(oldEntity);
		
		dao.insertShareSolution(entity);
		
		/* 修改供应商赔偿通知单确认信息 */
		ShareSolutionEntity newEntity = getShareSolution(entity.getComplaintId());
		List<SupportShareEntity> srcList = oldEntity.getSupportShareList();
		List<SupportShareEntity> destList = newEntity.getSupportShareList();
		
		updateConfirmInfo(srcList, destList, userId);
		logger.info("adjustShareSolution End: complaintId=" + entity.getComplaintId());
	}
	
	private void updateConfirmInfo(List<SupportShareEntity> srcList, List<SupportShareEntity> destList, int userId) {
		if (null != srcList) {
			for (SupportShareEntity srcEnt : srcList) {
				Map<String, Object> requestMap = new HashMap<String, Object>();
				requestMap.put("agencyId", srcEnt.getCode());
				requestMap.put("complaintId", srcEnt.getComplaintId());
				AgencyAppealEntity appealInfo = appealDao.getAppealInfo(requestMap);
				if (3==srcEnt.getConfirmState() && 1==appealInfo.getResultFlag()) { // 如果已申诉且已受理，则给财务冲账
					syncDataToFinance(srcEnt, -1, userId);
				}
			}
		}
		
		if (null != destList) {
			for (SupportShareEntity destEnt : destList) {
				if (null != destEnt) {
					SupportShareEntity srcEnt = getSupportShareByCode(destEnt.getCode(), srcList);
					if (null != srcEnt) {
						Map<String, Object> requestMap = new HashMap<String, Object>();
						requestMap.put("agencyId", srcEnt.getCode());
						requestMap.put("complaintId", srcEnt.getComplaintId());
						AgencyAppealEntity appealInfo = appealDao.getAppealInfo(requestMap);
						if (3==srcEnt.getConfirmState() && 1==appealInfo.getResultFlag()) { // 如果已申诉且已受理
							destEnt.setConfirmState(2);
							destEnt.setConfirmer("robot");
							destEnt.setConfirmTime(new Date());
							destEnt.setExpireTime(new Date());
							
							/* 重新给财务推送数据 */
							syncDataToFinance(destEnt, 1, userId);
						} else { // 如果未申诉或已申诉未受理，则copy信息
							destEnt.setConfirmState(srcEnt.getConfirmState());
							destEnt.setConfirmer(srcEnt.getConfirmer());
							destEnt.setConfirmTime(srcEnt.getConfirmTime());
							destEnt.setExpireTime(srcEnt.getExpireTime());
						}
					} else { // 原来没有此供应商的赔付信息
						destEnt.setConfirmState(0);
						destEnt.setExpireTime(festivalService.getWorkDaysEndTime(5)); // 设置5个工作日结束时间点
						
						syncDataToFinance(destEnt, 1, userId);
					}
					supportShareDao.update(destEnt);
				}
			}
		}
		
	}
	
	private void syncDataToFinance(SupportShareEntity ssEnt, int pnFlag, int userId) {
		ComplaintEntity compEnt = (ComplaintEntity) complaintService.get(ssEnt.getComplaintId());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("claimFormId", ssEnt.getId());
		paramMap.put("orderId", compEnt.getOrderId());
		paramMap.put("agencyId", ssEnt.getCode());
		paramMap.put("agencyName", ssEnt.getName());
		paramMap.put("amount", ssEnt.getNumber() * pnFlag);
		paramMap.put("foreignCurrencyType", ssEnt.getForeignCurrencyType());
		if (8 == ssEnt.getForeignCurrencyType()) {
			paramMap.put("foreignCurrencyAmount", ssEnt.getNumber() * pnFlag);
		} else {
			paramMap.put("foreignCurrencyAmount", ssEnt.getForeignCurrencyNumber() * pnFlag);
		}
		paramMap.put("salerId", compEnt.getDeal());
		paramMap.put("addTime", DateUtil.formatDate(ssEnt.getAddTime(), DateUtil.DATE_TIME_PAT));
		paramMap.put("checkerId", userId);
		paramMap.put("checkTime", DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_PAT));
		paramMap.put("complaintId", compEnt.getId());
		paramMap.put("routeName", compEnt.getRoute());
		paramMap.put("guestName", compEnt.getGuestName());
		tspService.claimForSupplier(paramMap);
	}
	
	private SupportShareEntity getSupportShareByCode(int code, List<SupportShareEntity> ssSrcList) {
		SupportShareEntity ssEnt = null;
		if (null != ssSrcList) {
			for (SupportShareEntity supportShare : ssSrcList) {
				if (code == supportShare.getCode()) {
					ssEnt = supportShare;
				}
			}
		}
		return ssEnt;
	}
	
	@Override
	public void returnShareSolution(ShareSolutionEntity entity, String backMsg) {
		entity.setAuditFlag(9);
		dao.logicDel(entity);
		entity.setDealId(0);
		entity.setDealName("");
		entity.setSubmitFlag(0);
		entity.setSubmitTime(null);
		entity.setAuditFlag(-1);
		dao.insertShareSolution(entity);
		
		ComplaintEntity complaint = compDao.get(entity.getComplaintId());
		if (4 == complaint.getState()) {
			complaint.setState(3);
			compDao.update(complaint);
		}
		
		/* 发送退回RTX提醒 */
		String title="分担方案退回提醒";
		String content="分担方案被" + Constans.AUDIT_BACK + "\n" + 
				"投诉单号:" + complaint.getId() + "\n" +
				"订单号:" + complaint.getOrderId() + "\n" + 
				"原因描述：" + backMsg + "\n";
		new RTXSenderThread(complaint.getDeal(), complaint.getDealName(), title, content).start();
	}

	@Override
	public Map<String, Object> getDataForRepair(Integer complaintId) {
		return dao.getDataForRepair(complaintId);
	}

	@Override
	public Map<String, Object> getOrderIndemnityShareInfo(Integer orderId) {
		return dao.getOrderIndemnityShareInfo(orderId);
	}

	@Override
	public List<Integer> getShareSolution(Map<String, Object> map) {
		return dao.getShareSolution(map);
	}

	@Override
	public List<Map<String, Object>> getCmpSpecialByCmpId(
			Map<String, Object> map) {
		
		return dao.getCmpSpecialByCmpId(map);
	}

	@Override
	public List<Map<String, Object>> getQualityToolCost(String dateStr) {
		return dao.getQualityToolCost(dateStr);
	}

	@Override
	public List<Map<String, Object>> getUnGroupCost(String dateStr) {
		return dao.getUnGroupCost(dateStr);
	}
	
	@Override
	public void launchSupplierAppeal(SupportShareEntity support,String appealContent,UserEntity user) {
		//投诉单号
		Integer complaintId = support.getComplaintId();
		ComplaintEntity complaint = (ComplaintEntity) complaintService.get(complaintId);
		if(complaint!=null){
			AgengcyPayoutEntity agengcyPayoutEntity = new AgengcyPayoutEntity(complaint);
			
			//申请人
			agengcyPayoutEntity.setAppealerId(user.getId());
			
			//供应商id
			Integer agencyId = support.getCode();
			agengcyPayoutEntity.setAgencyId(agencyId);
			//供应商名称
			String agencyName = support.getName();
			agengcyPayoutEntity.setAgencyName(agencyName);
			//获取供应商公司名
			AgencyEntity agencyInfo = tspService.getAgencyEntity(agencyName, complaintId);
			agengcyPayoutEntity.setCompanyName(agencyInfo.getCompanyName());
			
			//供应商申诉理由
			agengcyPayoutEntity.setAppealContent(appealContent);
			//供应商是否对接nb
			String agencyNbFlag = complaintService.getNbFlag(agencyId, complaintId)==0?"否":"是";
			agengcyPayoutEntity.setAgencyNbFlag(agencyNbFlag);
			agengcyPayoutEntity.setAgencyPayout(new BigDecimal(support.getNumber()));
			
			//TODO 对应质检单数据
			//质检系统未对接：填否
			agengcyPayoutEntity.setHaveQualityReport("否");
			agengcyPayoutEntity.setQualityReportUrl("");
			
			//oa发起申诉
			String oaId = tspService.launchSupplierAppeal(agengcyPayoutEntity);
			if(StringUtil.isEmpty(oaId)){
				return;
			}
			
			support.setOa_id(oaId);
			//状态改为申诉审批中
			Integer newConfirmState = "是".equals(agencyNbFlag)?SupportConfirmStateEnum.APPROVALING.getValue():SupportConfirmStateEnum.NOT_NB_APPROVALING.getValue();
			support.setConfirmState(newConfirmState);
			supportShareDao.update(support);
		}
	}
	
	@Override
	public void appealShareSolution(Integer approvalResult,Integer complaintId,String oaId,SupportShareEntity newsupport,
			Integer auditorId,BigDecimal companyCharge,String approval_comment) {
		logger.info("appealShareSolution Begin: complaintId=" + complaintId);
		/* 先将原数据设置为失效 */
		ComplaintEntity complaint = (ComplaintEntity) complaintService.get(complaintId);
		ShareSolutionEntity oldEntity = getShareSolution(complaintId);
		List<SupportShareEntity> supportShareLists = oldEntity.getSupportShareList();
		
		for (SupportShareEntity supportShareEntity : supportShareLists) {
			if (oaId.equals(supportShareEntity.getOa_id())) {
				if(ApprovalResultEnum.PASS.getValue().equals(approvalResult)){
					supportShareEntity.setDelFlag(0);
					supportShareDao.update(supportShareEntity);
					syncDataToFinance(supportShareEntity, -1,auditorId);
					if(newsupport!=null){
						newsupport.setShareId(oldEntity.getId());
						newsupport.setConfirmState(supportShareEntity.getNbFlag()==1?SupportConfirmStateEnum.APPROVAL_SUCC.getValue():SupportConfirmStateEnum.NOT_NB_APPROVAL_SUCC.getValue());
						newsupport.setConfirmer("robot");
						newsupport.setConfirmTime(new Date());
						newsupport.setExpireTime(new Date());
						if(StringUtils.isBlank(newsupport.getName()))
                        {
						    newsupport.setName(supportShareEntity.getName());
                        }
						if(newsupport.getCode() <=0)
                        {
                            newsupport.setCode(supportShareEntity.getCode());
                        }
						supportShareDao.insert(newsupport);
						syncDataToFinance(newsupport,1,auditorId);
					}
					BigDecimal newSupplierTotal = BigDecimal.valueOf(oldEntity.getSupplierTotal())
							.subtract(BigDecimal.valueOf(supportShareEntity.getNumber())).add(BigDecimal.valueOf(newsupport.getNumber()));
					oldEntity.setSupplierTotal(newSupplierTotal.doubleValue());
					BigDecimal newCompTotal = companyCharge.add(BigDecimal.valueOf(oldEntity.getSpecial()));
					oldEntity.setSpecial(newCompTotal.doubleValue());
					oldEntity.setTotal(newSupplierTotal.add(newCompTotal).doubleValue());
					this.update(oldEntity);
				}else{
				    supportShareEntity.setConfirmState(supportShareEntity.getNbFlag()==1?SupportConfirmStateEnum.APPROVAL_FAIL.getValue():SupportConfirmStateEnum.NOT_NB_APPROVAL_FAIL.getValue());
				    supportShareDao.update(supportShareEntity);
				}
				
				SupportAppealRecordEntity supportAppealRecord = new SupportAppealRecordEntity();
				supportAppealRecord.setOa_id(oaId);
				supportAppealRecord.setRoute_id(complaint.getRouteId());
				supportAppealRecord.setComplaint_id(complaintId);
				supportAppealRecord.setOrder_id(oldEntity.getOrderId());
				supportAppealRecord.setApproval_comment(approval_comment);
				supportAppealRecord.setCompany_payout(companyCharge);
				supportAppealRecord.setNew_agency_id(supportShareEntity.getCode());
				supportAppealRecord.setNew_agency_name(supportShareEntity.getName());
				supportAppealRecord.setNew_agency_payout(BigDecimal.valueOf(supportShareEntity.getNumber()));
				supportAppealRecord.setNew_agency_id(newsupport.getCode());
				supportAppealRecord.setNew_agency_name(newsupport.getName());
				supportAppealRecord.setNew_agency_payout(BigDecimal.valueOf(newsupport.getNumber()));
				supportAppealRecord.setApproval_id(auditorId);
				supportAppealRecordService.insert(supportAppealRecord);
			}
		}
	}
}
