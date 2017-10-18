package com.tuniu.qms.qs.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.bi.dao.PrdWeekSatisfyMapper;
import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.RtxRemindService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.model.SendMailConfig;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.SendMailConfigService;
import com.tuniu.qms.qs.dao.PunishPrdMapper;
import com.tuniu.qms.qs.dto.PunishPrdDto;
import com.tuniu.qms.qs.model.LowSatisfyDetail;
import com.tuniu.qms.qs.model.PunishPrd;
import com.tuniu.qms.qs.model.Remark;
import com.tuniu.qms.qs.service.PunishPrdService;
import com.tuniu.qms.qs.util.ProductRemarkSatisfyEnum;

@Service
public class PunishPrdServiceImpl implements PunishPrdService {

	@Autowired
	private PunishPrdMapper mapper;
	
	@Autowired
	private TspService tspService;
	
	@Autowired
	private PrdWeekSatisfyMapper prdWeekMapper;
	
	@Autowired
	private QcBillService qcBillService;
	
	@Autowired
	private RtxRemindService rtxService;
	
	@Autowired
	private MailTaskService mailTaskService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SendMailConfigService maillService;
	
	private final static Logger logger = LoggerFactory.getLogger(PunishPrdServiceImpl.class);

	
	@Override
	public void add(PunishPrd obj) {
		
		mapper.add(obj);
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(PunishPrd obj) {
		
		mapper.update(obj);
	}

	@Override
	public PunishPrd get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<PunishPrd> list(PunishPrdDto dto) {
	
		return mapper.list(dto);
	}

	@Override
	public void loadPage(PunishPrdDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void lowSatisfactionDeal(List<Long> routeIds) {
		int year = DateUtil.getYear(new Date());
		int lastWeek = DateUtil.getWeek(new Date())-1;
		
		//获取整改中和上周执行过上线的低满意度线路列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastWeekBgn", DateUtil.getFirstDayOfWeek(year, lastWeek));
		
		List<Long> excludeRouteIds = mapper.queryExcludeRouteIds(paramMap);
		
		routeIds = (List<Long>) CollectionUtils.subtract(routeIds, excludeRouteIds);
		for (Long routeId : routeIds) {
			
			try {
				//确保满意度数据为点评次数累计大于5
				if(CmpClient.getRemarkAmount(routeId) < 5 ) {
					continue;
				}
				
				PunishPrd e = getBaseEntity(Integer.valueOf(routeId.toString()), null); // 获得包含基本产品信息的下线实体
				//如果构造下线产品实体失败，目前放过，日后增加定时任务扫描失败列表，再次触发。
				if(e == null) {
				    continue;
				}
				
				e.setOfflineType(2); // 设置下线类型为低满意度
				buildByOfflineType(e);
				
				mapper.add(e);
			} catch (Exception e) {
				
				logger.error("低满意度触发规则过程出现异常", e.getMessage(), e);
			}
		}

	}

	/**
	 * 获得低满意度产品实体
	 * @param routeId
	 * @param prd 
	 * @return
	 */
	private PunishPrd getBaseEntity(Integer routeId, Product productInfo) {
		
		if(null == productInfo){
			//调用boh接口获取产品信息，增加失败重试2次，失败三次后放弃
			int failTimes=0;
			do{
			    productInfo = tspService.getProductInfo(routeId);
			    failTimes++;
			}while(null == productInfo && failTimes < 3);
		}
		
		if(null != productInfo && null != productInfo.getId() &&  productInfo.getId() > 0){
			PunishPrd e = new PunishPrd();
			
			e.setRouteId(routeId);
			e.setRouteName(productInfo.getPrdName());
			e.setPrdManager(productInfo.getPrdManager());
			e.setPrdOfficer(productInfo.getProducter());
			e.setBusinessUnit(productInfo.getBusinessUnitName());
			
			return e;
		}
		
		return null;
	}

	private void buildByOfflineType(PunishPrd e) {
		// 创建处罚产品单状态、上下线时间，系统下线次数、实际下线次数
		// 触红处罚：状态根据实际触红次数  大于等于4， 状态为4（永久下线）； 系统下线次数=实际下线次数
		// 低满意度：状态为2 待整改， 上下线时间为null，系统上下次数 +1 ，实际下线次数=上次实际次数
		
		// 下线次数
		buildOfflineCount(e);
		// 状态
		buildStatus(e);
		// 设置上下线时间
		buildOnlineTime(e);
	}
	
	/**
	 * 下线次数
	 * 触红：系统：上次系统下线次数 》= 4  次数 4 ，其他为 上次数 + 1 ；  实际下线次数 = 系统下线次数
	 * 低满意单 ： 系统：上次系统下线次数 》= 5  次数 5，其他为 上次数 + 1 ；  实际下线次数 = 上次实际下线次数
	 * @param e
	 */
	private void buildOfflineCount(PunishPrd e) {
		int offlineCount;
		int realOffLineCount = 0;

		if (e.getOfflineType() == 3) {//低质量下线次数设为1
			offlineCount = 1;
		} else {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("routeId", e.getRouteId());
			paramMap.put("offlineType", e.getOfflineType());

			Map<String, Object> maxOfflineCountRecord = mapper.getMaxOfflineCount(paramMap);
			if(maxOfflineCountRecord != null){
				offlineCount = (Integer)maxOfflineCountRecord.get("offlineCount") + 1;
				realOffLineCount = (Integer)maxOfflineCountRecord.get("realOffLineCount");
			}else{
				offlineCount = 1;
				realOffLineCount = 0;
			}
			
			if(e.getOfflineType()==1 && offlineCount>4){
			    offlineCount = 4;
			}else if(e.getOfflineType()==2 && offlineCount>5){
			    offlineCount = 5;
			}
			//触红处罚单，实际下线数和系统下线数一样
			if(e.getOfflineType()==1){
				realOffLineCount = offlineCount;
			}
		}

		e.setOfflineCount(offlineCount);
		e.setRealOffLineCount(realOffLineCount);
	}
	
	/**
	 * 设置状态
	 *  触红：实际下线次数 = 4 status = 4 ； 其他为 1
	 *  低满意单 ： 实际下线次数 = 5  status = 4 ； 其他为 1
	 * @param e
	 */
	private void buildStatus(PunishPrd e) {
		boolean isAllwarysOffLine = isAllwarysOffLine(e.getRouteId());
		if (isAllwarysOffLine) { // 已有处于永久下线状态的记录
			e.setStatus(4);
		} else { // 根据实际下线规则确定线路状态
			switch (e.getRealOffLineCount()) {
			case 0:
				//待整改状态
				e.setStatus(1);
				break;
			case 1:
				if(e.getOfflineType() == 3) {
					e.setStatus(4);//永久下线状态
				}else {
					e.setStatus(1);//待整改状态
				}
				break;
			case 2:
				e.setStatus(1);
				break;
			case 3:
				e.setStatus(1);
				break;
			case 4:
			    if(e.getOfflineType()==2){
			        e.setStatus(1);
			    }else if(e.getOfflineType()==1){
                    e.setStatus(4);
			    }
				break;
			case 5:
				e.setStatus(4);// 永久下线
				break;
			default:
				break;
			}
		}
		
	}

	/**
	 * 上下线时间， 低满意单时间为空
	 * @param e
	 */
	private void buildOnlineTime(PunishPrd e) {
		if(e.getOfflineType() != 2){
			//下线时间
			e.setOfflineTime(new Date());
			
			boolean isAllwarysOffLine = isAllwarysOffLine(e.getRouteId());
			if (isAllwarysOffLine) { // 已有处于永久下线状态的记录
				e.setOnlineTime(null);
			} else { // 根据规则确定上线日期
				switch (e.getRealOffLineCount()) {
				case 1:
					e.setOnlineTime(DateUtil.addSqlDates(new Date(), 7));
					break;
				case 2:
					e.setOnlineTime(DateUtil.addSqlDates(new Date(), 15));
					break;
				case 3:
					e.setOnlineTime(DateUtil.addSqlDates(new Date(), 30));
					break;
				default:
					break;
				}
				
				calculateOnlineTime(e); 
			}
		}
	}
	/**
	 * 取最远上线日期，即用规则计算结果与记录中现有最大上线日期比较，取较大值 更新该线路所有记录的上线日期为该较大值
	 * 【记录中不存在永久下线记录时调用该方法】
	 * 
	 * @param e
	 */
	private void calculateOnlineTime(PunishPrd e) {
		Date date = e.getOnlineTime();
		Date recordMaxDate = mapper.getMaxOnlineTime(e.getRouteId());
		if (e.getStatus() == 4) {// 如果本次触发永久下线
			mapper.offlineByRouteId(e.getRouteId());
		} else if (recordMaxDate != null && date.before(recordMaxDate)) {// 非永久下线计算时间小于记录中最大时间
			e.setOnlineTime(recordMaxDate);
		} else {// 非永久下线计算时间大于记录中最大时间
			// 更新routeId下所有记录的上线时间为当前计算时间
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("routeId", e.getRouteId());
			paramMap.put("onlineTime", e.getOnlineTime());
			mapper.updateOnlineTimeByRouteId(paramMap);
		}

	}
	
	
	private boolean isAllwarysOffLine(Integer routeId) {
		boolean isAllwarysOffLine = false;
		int count = mapper.getAllwaysOfflineCount(routeId);
		if (count > 0) {
			isAllwarysOffLine = true;
		}
		return isAllwarysOffLine;

	}

	@Override
	public LowSatisfyDetail getLowSatisfactionDetail(Integer id) {
		
		PunishPrd ppe = this.get(id);
		
		LowSatisfyDetail vo = new LowSatisfyDetail();
		vo.setRouteId(ppe.getRouteId());
		
		buildRemarkDetail(ppe, 2, vo); // 上上周满意度、点评详情列表
		buildRemarkDetail(ppe, 1, vo); // 上周周满意度、点评详情列表

		//历史下线记录
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("routeId", ppe.getRouteId());
		vo.setOfflineHistory(mapper.getHistoryOfflineListOper(paramMap));
		vo.setPasslineHistory(mapper.getHistoryPasslineListOper(paramMap));
		return vo;
	}
	
	
	/**
	 * 构造几周前（weeksAgo）的起始日期、满意度和点评详情
	 * @param ppe
	 * @param weeksAgo 几周前
	 * @param vo
	 */
	private void buildRemarkDetail(PunishPrd ppe, int weeksAgo, LowSatisfyDetail vo) {
		Integer year = DateUtil.getField(ppe.getTriggerTime(), Calendar.YEAR);
		Integer week = DateUtil.getField(ppe.getTriggerTime(), Calendar.WEEK_OF_YEAR) - weeksAgo;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (week <= 0) { // 考虑跨年情况
			Date weeksAgoBgn = DateUtil.getFirstDayOfWeek(year, week);
			paramMap.put("year", year - 1);
			paramMap.put("week", DateUtil.getField(weeksAgoBgn, Calendar.WEEK_OF_YEAR));
		} else {
			paramMap.put("year", year);
			paramMap.put("week", week);
		}
		
		paramMap.put("routeId", ppe.getRouteId());
		//某周满意度数值
		Double weekAgoSatisfaction = prdWeekMapper.getSatisfactionByRouteIdAndWeek(paramMap);
		
		String weekBgn = DateUtil.formatAsDefaultDate(DateUtil.getFirstDayOfWeek(year, week));
		String weekEnd = DateUtil.formatAsDefaultDate(DateUtil.getLastDayOfWeek(year, week));
		
		paramMap.clear();
		paramMap.put("remarkTimeStart", weekBgn);
		paramMap.put("remarkTimeEnd", DateUtil.formatAsDefaultDate(DateUtil.getFirstDayOfWeek(year, week + 1)));
		paramMap.put("productId", ppe.getRouteId().intValue());
		List<Remark>  weekRemarkList  = getLowWeekRemarkDetailList(CmpClient.getContentList(paramMap), ppe.getRouteId());
		
		if (weeksAgo == 1) {
			
			vo.setLastWeekSatisfaction(weekAgoSatisfaction);
			vo.setLastWeekBgn(weekBgn);
			vo.setLastWeekEnd(weekEnd);
			vo.setLastWeekRemarkList(weekRemarkList);
		} else if (weeksAgo == 2) {
			
			vo.setTwoWeeksAgoSasisfaction(weekAgoSatisfaction);
			vo.setTwoWeeksAgoBgn(weekBgn);
			vo.setTwoWeeksAgoEnd(weekEnd);
			vo.setTwoWeeksAgoRemarkList(weekRemarkList);
		}
	}
	
	/**
	 * 获得低满意度的点评详情
	 * @param remarkEntityList
	 * @param routeId
	 * @return
	 */
	private List<Remark> getLowWeekRemarkDetailList(List<Remark> remarkList, Integer routeId) {
		//根据品牌名获得满意度下线值
		int satisfyScoreLimit = ProductRemarkSatisfyEnum.getScore(prdWeekMapper.getNiuFlagByRouteId(routeId));
		
		logger.info("低满意度产品阈值： " + satisfyScoreLimit);
		logger.info("低满意度产品点评详情： " + remarkList);
		
		List<Remark>  weekRemarkList = new ArrayList<Remark>();
		for (Remark remarkEntity : remarkList) {
			if(remarkEntity.getSatisfaction() < satisfyScoreLimit){
				
				weekRemarkList.add(remarkEntity);
			}
		}
	
		return weekRemarkList;
	}

	@Override
	public boolean chgPrdStatus(PunishPrdDto vo) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", vo.getRouteId());
		paramMap.put("status", vo.getPrdStatus());
		paramMap.put("uid", vo.getUpdatePersonId());
		boolean isSucc =  tspService.changePrdStatus(paramMap);
		
		int id = Integer.parseInt(vo.getId());
		if (isSucc) {
			logger.info("下线次数");
			PunishPrd ppe = new PunishPrd();
			ppe.setId(id);
			
			if (0 == vo.getPrdStatus()) {// 下线操作
				ppe.setOfflineOperPerson(vo.getUpdatePerson());
				ppe.setOfflineOperTime(new Date());
				
				int realOffLineCount = vo.getRealOffLineCount();
				// 对永久下线的线路执行下线操作不改变当前下线状态
				if (vo.getOfflineType() != 3 && (vo.getOfflineType()==2 && realOffLineCount < 4) || (vo.getOfflineType()==1 && realOffLineCount < 4)) { 
					
					ppe.setStatus(2); // 非永久下线操作改变当前状态为整改中
				}else if(vo.getOfflineType()==2 && realOffLineCount >= 4){
					
					ppe.setStatus(4);//低满意产品，实际下线次数大于等于4时，再下线为永久下线
				}
				
				
				if(vo.getOfflineType() == 1){
					
					ppe.setRealOffLineCount(vo.getRealOffLineCount());
				}else if(vo.getOfflineType() == 2){//低满意度的实际下线次数、上下线时间 
					
					ppe.setRouteId(vo.getRouteId());
					ppe.setRealOffLineCount((realOffLineCount + 1) < 5 ? (realOffLineCount + 1) : 5);
					
					ppe.setOfflineTime(new Date());
					switch (ppe.getRealOffLineCount()) {
					case 1:
						ppe.setOnlineTime(DateUtil.addSqlDates(new Date(), 3));
						break;
					case 2:
						ppe.setOnlineTime(DateUtil.addSqlDates(new Date(), 7));
						break;
					case 3:
						ppe.setOnlineTime(DateUtil.addSqlDates(new Date(), 15));
						break;
					case 4:
						ppe.setOnlineTime(DateUtil.addSqlDates(new Date(), 30));
						break;
					default:
						break;
					}
					
					calculateOnlineTime(ppe); 
				}
				
				/* 发送下线通知 */
				logger.info("发送下线通知" + vo.getRouteId());
				sendMessageByOfflineType(id, ppe.getRealOffLineCount(), vo.getOfflineType());
			} else{
				
				ppe.setOnlineOperPerson(vo.getUpdatePerson());
				ppe.setOnlineOperTime(new Date());
				
				if (vo.getLineType() == 3) {// 永久下线线路恢复页签点击上线操作
					ppe.setDelFlag(1);// 逻辑删除该数据
				} else {
					ppe.setStatus(3); // 状态改为已整改
				}
			}
			
			ppe.setRemark(vo.getRemark());
			mapper.update(ppe);
		}
		
		return isSucc;
	}
	
	/**
	 * 根据不同的下线类型发送提醒
	 * @param id
	 * @param offlineCount 下线次数
	 * @param offlineType 下线类型
	 */
	private void sendMessageByOfflineType(int id, int offlineCount,	Integer offlineType) {
		if(offlineType==1){
			sendTouchredMsg(id, offlineCount);
		}else if(offlineType==2){
			sendLowSatisfyMsg(id,offlineCount);
		}else if(offlineType==3){
//			sendLowQualityMsg(id,offlineCount);
		}
		
	}
	
	/**
	 * 发送触红下线提醒
	 * @param id
	 * @param num
	 */
	private void sendTouchredMsg(int id, int num) {
         
		PunishPrd ppe = this.get(id);
	    String remark = "请产品经理通知供应商针对触红原因进行整改,并提交整改报告给SQE进行审核!";
	    if (ppe.getOfflineCount() > 3) {
	    	
	           remark = "请产品经理通知供应商，触红已达4次，产品已永久下线!";
	     }
		ComplaintBill compalint = CmpClient.getComplaintInfo(ppe.getCmpId());
		sendTouchRedOfflineRtxRemind(ppe.getRouteId(), compalint); // 发送RTX通知
		String emailTitle = getTouchEmailTitle(compalint,ppe.getOfflineCount());// 邮件标题
		MailTaskDto mailTaskDto = new MailTaskDto();
	    mailTaskDto.setTemplateName("touchRedReport.ftl");
	    mailTaskDto.setSubject(emailTitle);
	    mailTaskDto.setReAddrs(getRedRes(compalint));
	    mailTaskDto.setCcAddrs(getRedCcs());
	    Map<String, Object> map =   qcBillService.QcReportContent(ppe.getQcId());
	    map.put("offlineCount", ppe.getOfflineCount() );
	    map.put("remark", remark);
        mailTaskDto.setDataMap(map);
	    mailTaskService.addTask(mailTaskDto);		
	}

	private String[] getRedRes(ComplaintBill compEnt) {
		List<String> list = new ArrayList<String>();
		
		list.add(compEnt.getProducter());
		list.add(compEnt.getProductLeader());
		list.add(compEnt.getProductManager());
		list.add(compEnt.getDepManager());
		list.add(compEnt.getSeniorManager());
		
		List<String> nameList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (StringUtils.isNotEmpty(list.get(i))) {
				
				nameList.add(list.get(i));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nameList", nameList);
		List<User> recipients = new ArrayList<User>();
		if (nameList.size()>0) {
			recipients = userService.listByRealName(map);
		}
		List<String> reEmails = new ArrayList<String>();
		for (User user : recipients) {
			
			reEmails.add(user.getEmail());
		}
		
		List<String> resList = new ArrayList<String>();
		//过滤老板
		for(String s : reEmails){
			if(!("yanhaifeng@tuniu.com").equals(s)){
				resList.add(s);
			}
		}
		
		return resList.toArray(new String[resList.size()]);
	}

	private String[] getRedCcs() {
		
		SendMailConfig config = maillService.getByType("触红下线抄送人");
		String ccEmails[] = null;
		if(null!=config && null!=config.getSendAddrs()){
			
			ccEmails = config.getSendAddrs().split(";");
		}
		
		return ccEmails;
	}
	
	/**
	 * 发送低满意度下线提醒
	 * @param id
	 * @param offlineCount
	 */
	private void sendLowSatisfyMsg(int id, int offlineCount) {
		//发送邮件
		//设置邮件标题
		PunishPrd ppe = this.get(id);
		String emailTitle = "第"+ offlineCount +"次低满意度下线整改通知";
		// 发送RTX通知
		String title = "产品低满意度下线通知";
		String content = "产品：" + ppe.getRouteId() + "因满足低满意度产品条件，已被强制下线";
		String uids = "";
		List<String> nameList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(ppe.getPrdOfficer())) {
			
			nameList.add(ppe.getPrdOfficer());
		}
		if (StringUtils.isNotEmpty(ppe.getPrdManager())) {
			
			nameList.add(ppe.getPrdManager());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nameList", nameList);
		List<User> recipients = new ArrayList<User>();
		if (nameList.size()>0) {
			recipients = userService.listByRealName(map);
		}
		for(User user : recipients){
			
			uids += user.getId() +",";
		}
		
		if (StringUtils.isNotEmpty(uids)) {
			
			uids = uids.substring(0, uids.length() - 1);
			RtxRemind  rtxRemind = new RtxRemind();
			rtxRemind.setTitle(title);
			rtxRemind.setUid(uids);
			rtxRemind.setContent(content);
			rtxRemind.setSendTime(new Date());
			rtxService.add(rtxRemind);
        }
		
		MailTaskDto mailTaskDto = new MailTaskDto();
	    mailTaskDto.setTemplateName("lowSatisfactionRouteNotice.ftl");
	    mailTaskDto.setSubject(emailTitle);
	    mailTaskDto.setReAddrs(getLowSatisfyRes(ppe));
	    mailTaskDto.setCcAddrs(getLowSatisfyCcs(ppe));
	    Map<String, Object> hashMap =  new HashMap<String, Object>();
	    hashMap.put("vo", getLowSatisfyEmailContent(ppe));
        mailTaskDto.setDataMap(hashMap);
	    mailTaskService.addTask(mailTaskDto);	
		
	}
	
	/**
	 * 获取低满意度下线通知邮件内容详情
	 * @param ppe
	 * @return
	 */
	private LowSatisfyDetail getLowSatisfyEmailContent(PunishPrd ppe) {
		//1.获取模板参数
		LowSatisfyDetail vo = new LowSatisfyDetail();
		Integer realOffLineCount = ppe.getRealOffLineCount() + 1;
		//线路名称、产品编号、供应商、低满意度下线次数
		vo.setRouteName(ppe.getRouteName());
		vo.setRouteId(ppe.getRouteId());
		vo.setOfflineCount((realOffLineCount < 5 ? realOffLineCount : 5));
		buildRemarkDetail(ppe, 2, vo); // 上上周满意度、上上周前起始日期、上上周点评详情列表
		buildRemarkDetail(ppe, 1, vo); // 上周周满意度、上周起始日期、上周点评详情列表
		return vo;
	}
	
	/**
	 * 低满意度收件人
	 * @param e
	 * @return
	 */
	private String[] getLowSatisfyRes(PunishPrd e) {
		Set<String> resSet = new HashSet<String>();
		if( !("").equals(e.getPrdOfficer()) ){
			User prdOfficerEntity = userService.getUserByRealName(e.getPrdOfficer());
			if(null!=prdOfficerEntity && StringUtils.isNotEmpty(prdOfficerEntity.getEmail())){
				resSet.add(prdOfficerEntity.getEmail());
			}
		}
		if( !("").equals(e.getPrdManager()) ){
			User prdManagerEntity = userService.getUserByRealName(e.getPrdManager());
			if(null!=prdManagerEntity && StringUtils.isNotEmpty(prdManagerEntity.getEmail())){
				resSet.add(prdManagerEntity.getEmail());
			}
			
			User seniorManager = OaClient.getReporter(e.getPrdManager());
			if(null!=seniorManager && StringUtils.isNotEmpty(seniorManager.getRealName())){
				User senior = userService.getUserByRealName(seniorManager.getRealName());
				if(null!=senior && StringUtils.isNotEmpty(senior.getEmail())){
					resSet.add(senior.getEmail());
				}
			}
		}
		
		List<String> resList = new ArrayList<String>();
		//过滤老板
		for(String s : resSet){
			if(!("yanhaifeng@tuniu.com").equals(s) && !("yudunde@tuniu.com").equals(s)){
				resList.add(s);
			}
		}
		
		return resList.toArray(new String[resList.size()]);
	}
	
	/**
	 * 低满意度抄送人
	 * @param e
	 * @return
	 */
	private String[] getLowSatisfyCcs(PunishPrd e) {
		
		SendMailConfig config = maillService.getByType("低满意度下线抄送人");
		List<String> ccEmailList = new ArrayList<String>();
		String ccEmails[] =null;
		if(null!=config && null!=config.getSendAddrs()){
			
			ccEmails = config.getSendAddrs().split(";");
			for(int i= 0;i<ccEmails.length;i++){
				
				ccEmailList.add(ccEmails[i]);
			}
		}
		User seniorManager = OaClient.getReporter(e.getPrdManager());
		if(StringUtils.isNotEmpty(seniorManager.getRealName())){
			
				User senior = userService.getUserByRealName(seniorManager.getRealName());
				if(null!=senior && StringUtils.isNotEmpty(senior.getEmail())){
					if(senior.getId()!=19 && senior.getId()!=16 ){
					   ccEmailList.add(senior.getEmail());
					}
				}
				User productManager = OaClient.getReporter(seniorManager.getRealName());
				if(null!=productManager && StringUtils.isNotEmpty(productManager.getRealName())){
					User product = userService.getUserByRealName(productManager.getRealName());
					if(null!=product && StringUtils.isNotEmpty(product.getEmail())){
						if(product.getId()!=19 && product.getId()!=16 ){
							
							ccEmailList.add(product.getEmail());
						}
						
					}
				}
		}
		
		List<String> ccEmailLists = new ArrayList<String>();
		//过滤老板
		for(String s : ccEmailList){
			if(!("yanhaifeng@tuniu.com").equals(s) && !("yudunde@tuniu.com").equals(s)){
				ccEmailLists.add(s);
			}
		}
		
		return ccEmailLists.toArray(new String[ccEmailLists.size()]);
	}
	
    private void sendTouchRedOfflineRtxRemind(Integer routeId, ComplaintBill cmp) {
        String title = "产品触红下线通知";
		String content = "产品："	+ routeId +"因满足触红产品条件，已被强制下线";
		String uids = "";
		List<String> nameList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(cmp.getProducter())) {
			
			nameList.add(cmp.getProducter());
		}
		if (StringUtils.isNotEmpty(cmp.getProductLeader())) {
			
			nameList.add(cmp.getProductLeader());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nameList", nameList);
		List<User> recipients = new ArrayList<User>();
		if (nameList.size()>0) {
			recipients = userService.listByRealName(map);
		}
		for(User user : recipients){
			
			uids += user.getId() +",";
		}
	
		if (StringUtils.isNotEmpty(uids)) {
			uids = uids.substring(0, uids.length() - 1);
			RtxRemind  rtxRemind = new RtxRemind();
			rtxRemind.setTitle(title);
			rtxRemind.setUid(uids);
			rtxRemind.setContent(content);
			rtxRemind.setSendTime(new Date());
			rtxService.add(rtxRemind);
        }
		
    }
	private String getTouchEmailTitle(ComplaintBill compEnt,int offlineCount) {
		String title = "";
		String titleStart = "";
		titleStart = "第"+offlineCount +"次触红下线通知-----" ;
		if (compEnt.getOrderState().equals(Constant.BEFORE_TOUR)) {
			titleStart += "出游前投诉";
		} else if (compEnt.getOrderState().equals(Constant.MIDDLE_TOUR)) {
			titleStart += "出游中投诉";
		} else if (compEnt.getOrderState().equals(Constant.AFTER_TOUR)) {
			titleStart += "出游后投诉";
		}
		if(compEnt.getNiuLineFlag()==1)
		{
			titleStart += "[牛人专线]";
		}
		
		//投诉等级取质检报告中质检结论1的投诉问题等级
		title = titleStart + "[" + compEnt.getCmpLevel()+ "级]["
		+ compEnt.getOrdId() + "]";
		
		String depName = "";// 事业部
		String depManager = "";// 事业部总经理
		String productManagert = "";// 产品总监
		String seniorLeader = "";// 高级经理
		String productLeader = "";// 产品经理
		if (!compEnt.getDepName().equals("")) {// 取事业部
			depName = compEnt.getDepName();
			title += "[" + depName + "]";
		}
		if (!compEnt.getDepManager().equals("")) {// 取事业部总经理
			depManager = compEnt.getDepManager();
			title += "[" + depManager + "]";
		}
		if (!compEnt.getProductManager().equals("")) {// 取产品总监
			productManagert = compEnt.getProductManager();
			title += "[" + productManagert + "]";
		}
		if (!compEnt.getSeniorManager().equals("")) {// 取高级经理
			seniorLeader = compEnt.getSeniorManager();
			title += "[" + seniorLeader + "]";
		} else if (!compEnt.getProductLeader().equals("")) {// 如果没有高级经理。取产品经理
			productLeader = compEnt.getProductLeader();
			title += "[" + productLeader + "]";
		}
		title += compEnt.getOrderState();
		title += "[投诉单号" + compEnt.getId() + "]";
		
		return title;
	}

	@Override
	public void touchRedDeal(PunishPrd prd) {

	    Integer mainRouteId = tspService.queryMainRouteId(prd.getRouteId().intValue());
        prd.setRouteId(mainRouteId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("routeId", mainRouteId);
        paramMap.put("offlineType", 1);
        List<PunishPrd> prdList = mapper.listByTouchRed(paramMap);

        for(PunishPrd pp : prdList) {// 该线路已有同团期或者处理中的触红信息本次不进入列表
            if(pp.getTravelDateBgn().equals(prd.getTravelDateBgn()) || 2 == pp.getStatus()) {
                return;
            }
        }
        prd.setOfflineType(1);

        buildByOfflineType(prd);

        mapper.add(prd);
        String uids = "";
        // 发送rtx提醒
        String title = "触红提醒";
        String content = "产品名称：" + prd.getRouteName() + "\n" + "产品编号：" + prd.getRouteId() + "\n"
                + "产品专员：" + prd.getPrdOfficer() + "\n" + "产品经理：" + prd.getPrdManager() + "\n"
                + "问题订单号：" + prd.getOrderId() + "\n" + "供应商：" + prd.getSupplier() + "\n";
        List<User> userList = userService.getUsersByDepId(920); // SQE组
        for(User user : userList){
			
			uids += user.getId() +",";
		}
	
		if (StringUtils.isNotEmpty(uids)) {
			uids = uids.substring(0, uids.length() - 1);
			RtxRemind  rtxRemind = new RtxRemind();
			rtxRemind.setTitle(title);
			rtxRemind.setUid(uids);
			rtxRemind.setContent(content);
			rtxRemind.setSendTime(new Date());
			rtxService.add(rtxRemind);
        }
        
        //邮件提醒
        String emailTitle = "产品[" + prd.getRouteId() + "]第" + prd.getOfflineCount() + "次触红通知";
        MailTaskDto mailTaskDto = new MailTaskDto();
	    mailTaskDto.setTemplateName("touchRedNotice.ftl");
	    mailTaskDto.setSubject(emailTitle);
	    mailTaskDto.setReAddrs(new String[]{"g-sup-qual@tuniu.com"});
	    mailTaskDto.setCcAddrs(null);
	    Map<String, Object> hashMap =  new HashMap<String, Object>();
	    hashMap.put("prd", prd);
	    mailTaskDto.setDataMap(hashMap);
	    mailTaskService.addTask(mailTaskDto);	
		
	}

	@Override
	public int exportCount(PunishPrdDto dto) {
		
		return mapper.exportCount(dto);
	}

	@Override
	public List<PunishPrd> exportList(PunishPrdDto dto) {
		
		return mapper.exportList(dto);
	}

	@Override
	public void lowSatisSendEmail(List<Long> routeIds) {

		for (Long routeId : routeIds) {
			
			Product prd = tspService.getProductInfo(routeId.intValue());
			
			//跟团游和团队游发送邮件通知
			if(Constant.PRD_GROUP_TYPE.equals(prd.getCateName()) || Constant.PRD_TEAM_TYPE.equals(prd.getCateName())){
				try {
					//确保满意度数据为点评次数累计数量大于5, 
					if(CmpClient.getRemarkAmount(routeId) < 5 ) {
						continue;
					}
					
					PunishPrd e = getBaseEntity(Integer.valueOf(routeId.toString()), prd);
					if(null == e){
						continue;
					}
					
					addLowSatisSendEmail(e);
				
				} catch (Exception e) {
					logger.error("低满意度发送邮件出现异常",e.getMessage(), e);
				}
			}
		}
		
	}
	
	/**
	 * 低满意度提醒邮件任务添加
	 * @param e
	 */
	private void addLowSatisSendEmail(PunishPrd e) {
		MailTaskDto mailTaskDto = new MailTaskDto();
		
		mailTaskDto.setTemplateName("lowSatisRemind.ftl");
		
		StringBuilder emailTitle = new StringBuilder("[").append(e.getBusinessUnit()).append("]低满意度提醒通知 ");
		mailTaskDto.setSubject(emailTitle.toString());
		
		mailTaskDto.setReAddrs(getLowSatisfyRes(e));
		mailTaskDto.setCcAddrs(new String[]{"g-sup-qual@tuniu.com"});
	   
		Map<String, Object> hashMap =  new HashMap<String, Object>();
	    hashMap.put("vo", getLastWeekLowSatisfyRemark(e));
	    mailTaskDto.setDataMap(hashMap);
	    
		mailTaskService.addTask(mailTaskDto);	
	}
	
	/**
	 * 获得上周的低满意度点评详情
	 * @param ppe
	 * @return
	 */
	private LowSatisfyDetail getLastWeekLowSatisfyRemark(PunishPrd ppe) {
		LowSatisfyDetail vo = new LowSatisfyDetail();
		
		vo.setRouteName(ppe.getRouteName());
		vo.setRouteId(ppe.getRouteId());
		ppe.setTriggerTime(new Date());
		
		// 上周周满意度、上周起始日期、上周点评详情列表
		buildRemarkDetail(ppe, 1, vo); 
		
		return vo;
	}
	
}
