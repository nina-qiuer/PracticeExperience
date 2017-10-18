package com.tuniu.gt.punishprd.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.core.webservice.WebServiceClientBase;

import com.tuniu.bi.gmvrank.entity.GMVRankEntity;
import com.tuniu.bi.gmvrank.service.IGMVRankService;
import com.tuniu.bi.prdweeksatisfy.service.IPrdWeekSatisfyService;
import com.tuniu.gt.complaint.SysConstans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.QcQuestionEntity;
import com.tuniu.gt.complaint.entity.QcReportEntity;
import com.tuniu.gt.complaint.entity.QcTrackerEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IQcReportService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.service.impl.ComplaintTSPServiceImpl;
import com.tuniu.gt.complaint.vo.SystemVo;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.punishprd.dao.impl.PunishPrdDao;
import com.tuniu.gt.punishprd.entity.PunishPrdEntity;
import com.tuniu.gt.punishprd.entity.RemarkEntity;
import com.tuniu.gt.punishprd.service.IPunishPrdService;
import com.tuniu.gt.punishprd.vo.LowQualityDetailVo;
import com.tuniu.gt.punishprd.vo.LowSatisfyDetailVo;
import com.tuniu.gt.punishprd.vo.PunishPrdVo;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.FreemarkerUtil;
import com.tuniu.gt.toolkit.MailUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;
import com.tuniu.trest.TRestClient;

/**
 * 两大功能： 1.输入 2.处理
 * 
 * @author jiangye
 * 
 */
@Service("pp_service_impl-punishprd")
public class PunishPrdServiceImpl extends ServiceBaseImpl<PunishPrdDao>
		implements IPunishPrdService {

	private static Logger logger = Logger.getLogger(PunishPrdServiceImpl.class);
	private WebServiceClientBase xBase = new WebServiceClientBase();

	private Map<String, String> officerDeptOld = new LinkedHashMap<String, String>();// 旧责任归属
	private Map<String, Object> positionMap = new LinkedHashMap<String, Object>();// 定义执行岗位
	private Map<String, Object> officerDept = new LinkedHashMap<String, Object>();// 一级责任归属

	@Autowired
	@Qualifier("pp_dao_impl-punish_prd")
	public void setDao(PunishPrdDao dao) {
		this.dao = dao;
	}

	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;

	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;
	
	@Resource
	private IComplaintTSPService tspService;
	
	@Autowired
	@Qualifier("bi_service_impl-prdWeekSatisfy")
	private IPrdWeekSatisfyService prdWeekSatisfyService;
	
	@Autowired
	@Qualifier("bi_service_gmvrank_impl-GMVRank")
	private IGMVRankService gmvRankService;
	
	@Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;
	
	@Autowired
	@Qualifier("complaint_service_impl-qc_report")
	private IQcReportService qcReportService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService cmpService;
	
	@Autowired
	@Qualifier("tspService")
	private IComplaintTSPService cmpTSPService; 


	public PunishPrdServiceImpl() {
		// 定义责任归属（老）
		officerDeptOld.put("1", "不可抗力");
		officerDeptOld.put("2", "系统/流程问题");
		officerDeptOld.put("3", "客服中心");
		officerDeptOld.put("4", "运营中心");
		officerDeptOld.put("5", "营销中心");
		officerDeptOld.put("6", "财务法务中心");
		officerDeptOld.put("7", "人力资源中心");
		officerDeptOld.put("8", "华东事业部");
		officerDeptOld.put("9", "华北事业部");
		officerDeptOld.put("10", "南方事业部");
		officerDeptOld.put("11", "团队事业部");
		officerDeptOld.put("12", "自助游事业部");
		officerDeptOld.put("13", "新产品事业部");
		officerDeptOld.put("14", "其他");

		// 定义执行岗位
		positionMap = CommonUtil.getPositionMap();
	}

	@Override
	public void touchRedDeal(ComplaintEntity compEnt, QcEntity qcEnt,
			List<QcQuestionEntity> qcQuestions) {
		Long mainRouteId = cmpTSPService.queryMainRouteId(compEnt.getRouteId());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("routeId", mainRouteId);
		paramMap.put("offlineType", 1);
		List<PunishPrdEntity> ppList = (List<PunishPrdEntity>) dao
				.fetchList(paramMap);
		
		for (PunishPrdEntity ppe : ppList) {// 该线路已有同团期或者处理中的触红信息本次不进入列表
			if (ppe.getTravelDateBgn().equals(compEnt.getStartTime()) || 2 == ppe.getStatus()) {
				return;
			}
		}

		for (QcQuestionEntity question : qcQuestions) {
			if ("一级乙等-红线".equals(question.getScoreLevel())) {
				PunishPrdEntity e = new PunishPrdEntity();
				e.setOrderId(compEnt.getOrderId());
				e.setRouteId(mainRouteId);//以后要改投诉实体中的id为long类型
				e.setRouteName(compEnt.getRoute());
				e.setBU(compEnt.getDepName());
				e.setPrdManager(compEnt.getProductLeader());
				e.setPrdOfficer(compEnt.getProducter());
				e.setSupplier(question.getTrackers().get(0)
						.getResponsiblePerson());
				e.setOfflineType(1);
				e.setTravelDateBgn(compEnt.getStartTime());
				e.setQcId(qcEnt.getId());
				
				buildByOfflineType(e);
				
				dao.insert(e);
				
				//发送rtx提醒和邮件提醒
				String title = "触红提醒";
				String content = "产品名称：" + compEnt.getRoute() + "\n" +
								 "产品编号：" + compEnt.getRouteId() + "\n" +
								 "产品专员：" + compEnt.getProducter() + "\n" + 
								 "产品经理：" + compEnt.getProductLeader() + "\n" +
								 "问题订单号：" + compEnt.getOrderId() + "\n" +
								 "供应商：" + question.getTrackers().get(0).getResponsiblePerson() + "\n";
				List<UserEntity> userList = userService.getSameDeptUsers(920); // SQE组
				for (UserEntity user : userList) {
					new RTXSenderThread(user.getId(), user.getRealName(), title, content).start();
				}
				
				String emailTitle = "产品[" + compEnt.getRouteId() + "]第" + e.getOfflineCount() + "次触红通知";
				String emailContent = "<strong>产品名称：</strong>" + compEnt.getRoute() + "<br>" + 
									"<strong>产品编号：</strong>" + compEnt.getRouteId() + "<br>" + 
									"<strong>产品专员：</strong>" + compEnt.getProducter() + "<br>" + 
									"<strong>产品经理：</strong>" + compEnt.getProductLeader() + "<br>" + 
									"<strong>问题订单号：</strong>" + compEnt.getOrderId() + "<br>" + 
									"<strong>供应商：</strong>" + question.getTrackers().get(0).getResponsiblePerson() + "<br>" +
									"<strong>触红次数：</strong>" + e.getOfflineCount() + "<br>" + 
									"<strong>备注：</strong><span style='color: red;font: bold;'>请SQE审核下线</span>";
				tspService.sendMail(new MailerThread(new String[]{"g-sup-qual@tuniu.com"}, null, emailTitle, emailContent));

				break;//多个问题单也只记录一次：因为同团期只记录一次，多个问题单都是同一个投诉单，对应的产品肯定一样，团期也就一样。
			}
		}

	}
	
    @Override
    public void outerTouchRedDeal(PunishPrdEntity punishPrdEntity) {
        Long mainRouteId = cmpTSPService.queryMainRouteId(punishPrdEntity.getRouteId().intValue());
        punishPrdEntity.setRouteId(mainRouteId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("routeId", mainRouteId);
        paramMap.put("offlineType", 1);
        List<PunishPrdEntity> ppList = (List<PunishPrdEntity>) dao.fetchList(paramMap);

        for(PunishPrdEntity ppe : ppList) {// 该线路已有同团期或者处理中的触红信息本次不进入列表
            if(ppe.getTravelDateBgn().equals(punishPrdEntity.getTravelDateBgn()) || 2 == ppe.getStatus()) {
                return;
            }
        }

        punishPrdEntity.setOfflineType(1);
        punishPrdEntity.setSystem(1);// qms使用

        buildByOfflineType(punishPrdEntity);

        dao.insert(punishPrdEntity);

        // 发送rtx提醒和邮件提醒
        String title = "触红提醒";
        String content = "产品名称：" + punishPrdEntity.getRouteName() + "\n" + "产品编号：" + punishPrdEntity.getRouteId() + "\n"
                + "产品专员：" + punishPrdEntity.getPrdOfficer() + "\n" + "产品经理：" + punishPrdEntity.getPrdManager() + "\n"
                + "问题订单号：" + punishPrdEntity.getOrderId() + "\n" + "供应商：" + punishPrdEntity.getSupplier() + "\n";
        List<UserEntity> userList = userService.getSameDeptUsers(920); // SQE组
        for(UserEntity user : userList) {
            new RTXSenderThread(user.getId(), user.getRealName(), title, content).start();
        }

        String emailTitle = "产品[" + punishPrdEntity.getRouteId() + "]第" + punishPrdEntity.getOfflineCount() + "次触红通知";
        String emailContent = "<strong>产品名称：</strong>" + punishPrdEntity.getRouteName() + "<br>" + "<strong>产品编号：</strong>"
                + punishPrdEntity.getRouteId() + "<br>" + "<strong>产品专员：</strong>" + punishPrdEntity.getPrdOfficer() + "<br>"
                + "<strong>产品经理：</strong>" + punishPrdEntity.getPrdManager() + "<br>" + "<strong>问题订单号：</strong>"
                + punishPrdEntity.getOrderId() + "<br>" + "<strong>供应商：</strong>" + punishPrdEntity.getSupplier() + "<br>"
                + "<strong>触红次数：</strong>" + punishPrdEntity.getOfflineCount() + "<br>"
                + "<strong>备注：</strong><span style='color: red;font: bold;'>请SQE审核下线</span>";
        tspService.sendMail(new MailerThread(new String[]{"g-sup-qual@tuniu.com"}, null, emailTitle, emailContent));
    }

	@Override
	public void lowSatisfactionDeal(List<Long> routeIds) {

		//获取整改中和上周执行过上线的低满意度线路列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int year = DateUtil.getField(Calendar.YEAR);
		int lastWeek = DateUtil.getField(Calendar.WEEK_OF_YEAR)-1;
		paramMap.put("lastWeekBgn", DateUtil.getFirstDayOfWeek(year, lastWeek));
		List<Long> excludeRouteIds = dao.queryExcludeRouteIds(paramMap);
		routeIds = (List<Long>) CollectionUtils.subtract(routeIds, excludeRouteIds);
		paramMap.clear();
		
		for (Long routeId : routeIds) {
			
			try {
				//确保前两周的满意度数据为点评次数大于5
				paramMap.put("productId ", routeId);
				int remarkAmount = ComplaintRestClient.getRemarkAmount(paramMap);
				
				if(remarkAmount < 5 ) {
					continue;
				}
				
				PunishPrdEntity e = getBaseEntity(routeId); // 获得包含基本产品信息的下线实体
				//如果构造下线产品实体失败，目前放过，日后增加定时任务扫描失败列表，再次触发。
				if(e == null) {
				    continue;
				}
				e.setOfflineType(2); // 设置下线类型为低满意度
				buildByOfflineType(e);
				
				dao.insert(e);
			} catch (Exception e) {
				logger.error("低满意度触发规则过程出现异常",e);
			}
		}

	}
	

	@Override
	public void lowQualityDeal(List<GMVRankEntity> gmvRankList) {
		boolean adapt = false; // 满足低质量下线条件标志位
		int adaptType = 0; // 引起低质量下线原因类型    1：触红   2：低满意度   3：满意度排名在同方向后5%
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offlineType", 1);
		List<Long> touchRedRouteIds = dao.getPreMonthOfflineIds(paramMap);
		paramMap.put("offlineType", 2);
		List<Long> lowSatisfactionRouteIds = dao
				.getPreMonthOfflineIds(paramMap);
		Long routeId = null;
		for (GMVRankEntity gmvRankEntity : gmvRankList) {
			try {
				routeId = gmvRankEntity.getRouteKey();

				adapt = false;// 是否符合低质量条件
				adaptType = 0;// 适配类型 即满足低质量下线的原因
				// 1.上个自然月进入过两次触红列表中的线路
				if (touchRedRouteIds.contains(routeId)) {
					adapt = true;
					adaptType = 1;
				}

				if (!adapt) {
					if (lowSatisfactionRouteIds.contains(routeId)) {
						adapt = true;
						adaptType = 2;
					}
				}

				if (!adapt) {
					// 3.上个自然月综合满意度后5%的产品线路
					int scoreRank = gmvRankEntity.getScoreRank();
					int routeCnt = gmvRankEntity.getRouteCnt();
					if (scoreRank > Math.ceil(routeCnt * 0.95)) { // 取排名后5%的数据    如41  41*0.95 =38.95  向上取整后为39  大于39的排名为40和41
						adapt = true;
						adaptType = 3;
					}
				}

				if (!adapt) {
					continue;
				}

				// 满足上述条件之一则入列表
				PunishPrdEntity e = getBaseEntity(gmvRankEntity);
				e.setOfflineCause(adaptType);
				
				e.setOfflineType(3); // 设置下线类型为低质量
				buildByOfflineType(e);

				dao.insert(e);
			} catch (Exception e) {
				logger.error("低质量规则触发过程出现异常", e);
			}
		}
	}
	
	@Override
	public boolean chgPrdStatus(PunishPrdVo vo) {
		int id = vo.getId();
		long routeId = vo.getRouteId();
		int prdStatus = vo.getPrdStatus();
		int updatePersonId = vo.getUpdatePersonId();
		
		boolean isSucc = false;
		
		/*SystemVo sys = tspService.getSystem(routeId, 1);
		if (null == sys) {
			String url = SysConstans.NG_BOSS_PRD_URL + "/manage/product/status/change";
			isSucc = chgNGPrdStatus(routeId, prdStatus, url);
		} else {
			if (SysConstans.NG_BOSS_PRD_CODE.equals(sys.getSysCode())) {
				String url = sys.getSysUrl() + "/manage/product/status/change";
				isSucc = chgNGPrdStatus(routeId, prdStatus, url);
			} else if (SysConstans.TD_PRD_CODE.equals(sys.getSysCode())) {
				String url = sys.getSysUrl() + "/gottProduct/xmlrpc";
				isSucc = chgTDPrdStatus(routeId, prdStatus, url);
			} else if (SysConstans.BOSS_PRD_CODE.equals(sys.getSysCode())) {
				String url = sys.getSysUrl() + "/interface/rest/server/product/RouteApi.cls.php";
				isSucc = chgBossPrdStatus(routeId, prdStatus, url, updatePersonId);
			}
		}*/
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", routeId);
		paramMap.put("status", prdStatus);
		paramMap.put("uid", updatePersonId);
		isSucc = tspService.changePrdStatus(paramMap);
		
		String updatePerson = vo.getUpdatePerson();
		int offlineCount = vo.getOfflineCount();
		
		if (isSucc) {
			PunishPrdEntity ppe = new PunishPrdEntity();
			ppe.setId(id);
			if (0 == prdStatus) {// 下线操作
				ppe.setOfflineOperPerson(vo.getUpdatePerson());
				ppe.setOfflineOperTime(new Date());

				if (vo.getOfflineType() != 3 && (vo.getOfflineType()==2 && offlineCount < 5) || (vo.getOfflineType()==1 && offlineCount < 4)) { // 对永久下线的线路执行下线操作不改变当前下线状态
					ppe.setStatus(2); // 非永久下线操作改变当前状态为整改中
				}

				/* 发送下线通知 */
				sendMessageByOfflineType(id, offlineCount, vo.getOfflineType());
			} else if (1 == prdStatus) {
				ppe.setOnlineOperPerson(vo.getUpdatePerson());
				ppe.setOnlineOperTime(new Date());
				if (vo.getMenuId() == 3) {// 永久下线线路恢复页签点击上线操作
					ppe.setDelFlag(1);// 逻辑删除该数据
				} else {
					ppe.setStatus(3); // 状态改为已整改
				}
			}
			
			super.update(ppe);
		}
		
		return isSucc;
	}

	//根据下线次数设置下线时间、上线时间、状态字段
	@Override
	public LowSatisfyDetailVo getLowSatisfactionDetail(Integer id) {
		PunishPrdEntity ppe = (PunishPrdEntity) get(id);
		LowSatisfyDetailVo vo = new LowSatisfyDetailVo();
		vo.setRouteId(ppe.getRouteId()); // 产品编号
		buildRemarkDetail(ppe, 2, vo); // 上上周满意度、上上周前起始日期、上上周点评详情列表
		buildRemarkDetail(ppe, 1, vo); // 上周周满意度、上周起始日期、上周点评详情列表

		//历史下线记录
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("routeId", ppe.getRouteId());
		vo.setOfflineHistory(dao.getHistoryOfflineListOper(paramMap));
		return vo;
	}
	
	@Override
	public LowQualityDetailVo getLowQualityDetail(Integer id) {
		PunishPrdEntity ppe = (PunishPrdEntity) get(id);
		LowQualityDetailVo vo = new LowQualityDetailVo();
		
		// 1.设置线路id和线路名
		vo.setRouteId(ppe.getRouteId());
		vo.setRouteName(ppe.getRouteName());
		vo.setPrdManager(ppe.getPrdManager());
		vo.setPrdOfficer(ppe.getPrdOfficer());
		
		// 2.设置线路部门
		Map<String, Object> productInfo = tspService.getProductInfo(ppe
				.getRouteId());
		
		if((Integer)productInfo.get("errCode")==0){ //调用BOH获取产品信息成功
			Map<String, Object> departmentInfo = (Map<String, Object>) productInfo.get("departmentInfo");
			StringBuilder sb = new StringBuilder();
			sb.append(departmentInfo.get("businessUnitName")).append("/");
			sb.append(departmentInfo.get("departmentName")).append("/");
			sb.append(departmentInfo.get("groupName"));
			vo.setPrdDepartment(sb.toString().replace("null", "无"));
		}
		
		// 3.设置统计周期
		vo.setMonth(DateUtil.getField(ppe.getTriggerTime(), Calendar.MONTH)-1);
		// 4.设置满意度和点评次数
		//4.1获取点评次数
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", ppe.getRouteId());
		paramMap.put("remarkTimeStart", DateUtil.getPreMonthFirstShortDay(ppe.getTriggerTime()));
		paramMap.put("remarkTimeEnd", DateUtil.getMonthFirstShortDay(ppe.getTriggerTime()));
		Integer remarkAmount = ComplaintRestClient.getRemarkAmount(paramMap);
		vo.setRemarkAmount(remarkAmount);
		//4.2设置满意度，从bi提供的排名数据中获取
		GMVRankEntity gmvRankEntity = (GMVRankEntity) gmvRankService.get(ppe.getGmvRankId());
		vo.setSatisfation(gmvRankEntity.getAvgScore());
		//5 设置gmv排名相关数据
		vo.setScoreRank(gmvRankEntity.getScoreRank());
		vo.setAreaPrdCnt(gmvRankEntity.getRouteCnt());
		vo.setPrdArea(gmvRankEntity.getProductArea());
		// 6 设置触红次数
		paramMap.clear();
		paramMap.put("routeId", ppe.getRouteId());
		paramMap.put("offlineType", 1);
		paramMap.put("triggerTime", ppe.getTriggerTime());
		vo.setTouchRedCount(dao.getMaxOfflineCount(paramMap));
		// 7设置历史质量事件记录
		// 7.1触红
		List<Date> touchRedHistoryDateList = dao.getOfflineHistoryDateList(paramMap);
		//7.2 低满意度
		paramMap.put("offlineType", 2);
		List<Date> lowSatisfationHistoryDateList = dao.getOfflineHistoryDateList(paramMap);
		//7.3 低质量
		paramMap.put("offlineType", 3);
		List<Date> lowQualityHistoryDateList = dao.getOfflineHistoryDateList(paramMap);
		
		Map<Integer,List<Date>> historyDateList = new HashMap<Integer, List<Date>>();
		historyDateList.put(1, touchRedHistoryDateList);
		historyDateList.put(2, lowSatisfationHistoryDateList);
		historyDateList.put(3, lowQualityHistoryDateList);
		vo.setHistoryDateList(historyDateList);
		return vo;
	}
	
	private void buildStatusAndOnlineTime(PunishPrdEntity e) {
		boolean isAllwarysOffLine = isAllwarysOffLine(e.getRouteId());
		if (isAllwarysOffLine) { // 已有处于永久下线状态的记录
			e.setOnlineTime(null);
			e.setStatus(4);
		} else { // 根据规则确定线路状态和上线日期
			switch (e.getOfflineCount()) {
			case 1:
				if(e.getOfflineType() == 3) {
					e.setStatus(4);//永久下线状态
				}else {
					e.setOnlineTime(DateUtil.getSomeDay(new Date(), e.getOfflineType()==2?3:7));
					e.setStatus(1);//待整改状态
				}
				break;
			case 2:
				e.setOnlineTime(DateUtil.getSomeDay(new Date(), e.getOfflineType()==2?7:15));
				e.setStatus(1);
				break;
			case 3:
				e.setOnlineTime(DateUtil.getSomeDay(new Date(), e.getOfflineType()==2?15:30));
				e.setStatus(1);
				break;
			case 4:
			    if(e.getOfflineType()==2){
			        e.setOnlineTime(DateUtil.getSomeDay(new Date(), 30));
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
			
			calculateOnlineTime(e); 
		}

	}

	private boolean isAllwarysOffLine(Long routeId) {
		boolean isAllwarysOffLine = false;
		int count = dao.getAllwaysOfflineCount(routeId);
		if (count > 0) {
			isAllwarysOffLine = true;
		}
		return isAllwarysOffLine;

	}

	/**
	 * 取最远上线日期，即用规则计算结果与记录中现有最大上线日期比较，取较大值 更新该线路所有记录的上线日期为该较大值
	 * 【记录中不存在永久下线记录时调用该方法】
	 * 
	 * @param e
	 */
	private void calculateOnlineTime(PunishPrdEntity e) {
		Date date = e.getOnlineTime();
		Date recordMaxDate = dao.getMaxOnlineTime(e.getRouteId());
		if (e.getStatus() == 4) {// 如果本次触发永久下线
			dao.offlineByRouteId(e.getRouteId());
		} else if (recordMaxDate != null && date.before(recordMaxDate)) {// 非永久下线计算时间小于记录中最大时间
			e.setOnlineTime(recordMaxDate);
		} else {// 非永久下线计算时间大于记录中最大时间
			// 更新routeId下所有记录的上线时间为当前计算时间
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("routeId", e.getRouteId());
			paramMap.put("onlineTime", e.getOnlineTime());
			dao.updateOnlineTimeByRouteId(paramMap);
		}

	}
	
	/**
	 * 构造几周前（weeksAgo）的起始日期、满意度和点评详情
	 * @param ppe
	 * @param weeksAgo 几周前
	 * @param vo
	 */
	private void buildRemarkDetail(PunishPrdEntity ppe, int weeksAgo,
			LowSatisfyDetailVo vo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date triggerTime = ppe.getTriggerTime();
		Integer year = DateUtil.getField(triggerTime, Calendar.YEAR);
		Integer week = DateUtil.getField(triggerTime, Calendar.WEEK_OF_YEAR)
				- weeksAgo;
		if (week <= 0) { // 考虑跨年情况
			Date weeksAgoBgn = DateUtil.getFirstDayOfWeek(year, week);
			paramMap.put("year", year - 1);
			paramMap.put("week",
					DateUtil.getField(weeksAgoBgn, Calendar.WEEK_OF_YEAR));

		} else {
			paramMap.put("year", year);
			paramMap.put("week", week);
		}
		paramMap.put("routeId", ppe.getRouteId());
		Double weekAgoSatisfaction = prdWeekSatisfyService
				.getSatisfactionByRouteIdAndWeek(paramMap);

		Date dateTemp = DateUtil.getFirstDayOfWeek(year, week);
		List<RemarkEntity>  tempRemarkEntityList = new ArrayList<RemarkEntity>();
		String niuFlag = prdWeekSatisfyService.getNiuFlagByRouteId(ppe.getRouteId());
		if (weeksAgo == 1) {
			vo.setLastWeekSatisfaction(weekAgoSatisfaction);
			vo.setLastWeekBgn(DateUtil.formatDate(dateTemp));
			dateTemp = DateUtil.getLastDayOfWeek(year, week);
			vo.setLastWeekEnd(DateUtil.formatDate(dateTemp));
			
			paramMap.clear();
			paramMap.put("remarkTimeStart", vo.getLastWeekBgn());
			paramMap.put("remarkTimeEnd", DateUtil.formatDate(DateUtil.getFirstDayOfWeek(year, week+1)));
			paramMap.put("productId", ppe.getRouteId().intValue());
			tempRemarkEntityList.addAll(ComplaintRestClient.getContentList(paramMap));
			
			List<RemarkEntity>  lastWeekRemarkList  =  new ArrayList<RemarkEntity>();
			if("牛人专线".equals(niuFlag)){  //牛人专线取满意度小于0.9的
				for (RemarkEntity  remarkEntity:tempRemarkEntityList) {
					if(remarkEntity.getSatisfaction()<90){
						lastWeekRemarkList.add(remarkEntity);
					}
				}
			}else{  //非牛人专线取满意度小于0.75的
				for (RemarkEntity  remarkEntity:tempRemarkEntityList) {
					if(remarkEntity.getSatisfaction()<75){
						lastWeekRemarkList.add(remarkEntity);
					}
				}
			}
			vo.setLastWeekRemarkList(lastWeekRemarkList);
		} else if (weeksAgo == 2) {
			vo.setTwoWeeksAgoSasisfaction(weekAgoSatisfaction);
			vo.setTwoWeeksAgoBgn(DateUtil.formatDate(dateTemp));
			dateTemp = DateUtil.getLastDayOfWeek(year, week);
			vo.setTwoWeeksAgoEnd(DateUtil.formatDate(dateTemp));
			
			paramMap.clear();
			paramMap.put("remarkTimeStart", vo.getTwoWeeksAgoBgn());
			paramMap.put("remarkTimeEnd", DateUtil.formatDate(DateUtil.getFirstDayOfWeek(year, week+1)));
			tempRemarkEntityList.clear();
			paramMap.put("productId",ppe.getRouteId().intValue() );
    		tempRemarkEntityList.addAll(ComplaintRestClient.getContentList(paramMap));
			List<RemarkEntity>  twoWeeksAgoRemarkList  =  new ArrayList<RemarkEntity>();
			if("牛人专线".equals(niuFlag)){  //牛人专线取满意度小于0.9的
				for (RemarkEntity  remarkEntity:tempRemarkEntityList) {
					if(remarkEntity.getSatisfaction()<90){
						twoWeeksAgoRemarkList.add(remarkEntity);
					}
				}
			}else{  //非牛人专线取满意度小于0.75的
				for (RemarkEntity  remarkEntity:tempRemarkEntityList) {
					if(remarkEntity.getSatisfaction()<75){
						twoWeeksAgoRemarkList.add(remarkEntity);
					}
				}
			}
			vo.setTwoWeeksAgoRemarkList(twoWeeksAgoRemarkList);
		}

	}
	
	/**
	 * 获取低满意度下线通知邮件内容
	 * @param ppe
	 * @return
	 */
	private String getLowSatisfyEmailContent(PunishPrdEntity ppe) {
		//1.获取模板参数
		LowSatisfyDetailVo vo = new LowSatisfyDetailVo();
		//线路名称、产品编号、供应商、低满意度下线次数
		vo.setRouteName(ppe.getRouteName());
		vo.setRouteId(ppe.getRouteId());
		vo.setOfflineCount(ppe.getOfflineCount());
		buildRemarkDetail(ppe, 2, vo); // 上上周满意度、上上周前起始日期、上上周点评详情列表
		buildRemarkDetail(ppe, 1, vo); // 上周周满意度、上周起始日期、上周点评详情列表
		String content = null;
		
		try {
			content = FreemarkerUtil.getContent("LowSatisfactionRouteNotice.ftl", vo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return content;
	}
	
	/**
	 * 获取低质量下线通知邮件内容
	 * @param ppe
	 * @return
	 */
	private String getLowQualityEmailContent(PunishPrdEntity ppe) {
		//1.获取模板参数
		LowQualityDetailVo vo = new LowQualityDetailVo();
		vo.setId(ppe.getId());
		vo.setRouteName(ppe.getRouteName());
		vo.setRouteId(ppe.getRouteId());
		vo.setPrdOfficer(ppe.getPrdOfficer());
		vo.setPrdManager(ppe.getPrdManager());
		GMVRankEntity gmvRankEntity = (GMVRankEntity) gmvRankService.get(ppe.getGmvRankId());
		vo.setPrdArea(gmvRankEntity.getProductArea());
		vo.setPrdName(gmvRankEntity.getProductName());
		Integer offlineCause = ppe.getOfflineCause();
		vo.setOfflineCause(offlineCause);
		
		switch (offlineCause) {
		case 1://触红原因
			setTouchRedCauseList(vo,ppe);
			break;
		case 2://低满意度原因
			
			break;
		case 3://gmv排名后5%线路满意度后5%原因
			vo.setSatisfation(gmvRankEntity.getAvgScore());
			break;

		default:
			break;
		}
		
		String content = null;
		try {
			content = FreemarkerUtil.getContent("LowQualityRouteNotice.ftl", vo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return content;
	}

	private void setTouchRedCauseList(LowQualityDetailVo vo, PunishPrdEntity ppe) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("routeId", ppe.getRouteId());
		paramMap.put("triggerTime", ppe.getTriggerTime());
		List<PunishPrdEntity> touchRedCauseList = dao.getOfflineCauseInfo(paramMap);
		vo.setTouchRedCauseList(touchRedCauseList);
	}

	private String[] getLowSatisfyOrLowQualityCcs(PunishPrdEntity e) {
		Set<String> ccsSet = new HashSet<String>();
		ccsSet.add("changjingyong@tuniu.com");
		ccsSet.add("yanghuifan@tuniu.com");
		ccsSet.add("hanqiang@tuniu.com");
		ccsSet.add("shenwei@tuniu.com");
		ccsSet.add("liuyajun@tuniu.com");
		ccsSet.add("lengjing2@tuniu.com");
		ccsSet.add("g-sup-qual@tuniu.com");
		
		UserEntity seniorManager = ComplaintRestClient.getReporter(e.getPrdManager());
		if(StringUtil.isNotEmpty(seniorManager.getRealName())){
		    ccsSet.add(seniorManager.getEmail());
			UserEntity productManager = ComplaintRestClient.getReporter(seniorManager.getRealName());
			if(StringUtil.isNotEmpty(productManager.getEmail())){
				ccsSet.add(productManager.getEmail());
			}
		}
		
		return ccsSet.toArray(new String[ccsSet.size()]);
	}

	private String[] getLowSatisfyOrLowQualityRes(PunishPrdEntity e) {
		Set<String> resSet = new HashSet<String>();
		UserEntity prdOfficerEntity = userService.getUserByRealName(e.getPrdOfficer());
		if(StringUtil.isNotEmpty(prdOfficerEntity.getEmail())){
			resSet.add(prdOfficerEntity.getEmail());
		}
		
		UserEntity prdManagerEntity = userService.getUserByRealName(e.getPrdManager());
		if(StringUtil.isNotEmpty(prdManagerEntity.getEmail())){
			resSet.add(prdManagerEntity.getEmail());
		}
		
		UserEntity seniorManager = ComplaintRestClient.getReporter(e.getPrdManager());
		if(StringUtil.isNotEmpty(seniorManager.getEmail())){
			resSet.add(seniorManager.getEmail());
		}
		
		return resSet.toArray(new String[resSet.size()]);
	}

	private String getTouchEmailTitle(ComplaintEntity compEnt,int offlineCount) {
		String title = "";
		String titleStart = "";
		titleStart = "第"+offlineCount +"次触红下线通知-----" ;
		if (compEnt.getOrderState().equals("出游前")) {
			titleStart += "出游前投诉";
		} else if (compEnt.getOrderState().equals("出游中")) {
			titleStart += "出游中投诉";
		} else if (compEnt.getOrderState().equals("出游后")) {
			titleStart += "出游后投诉";
		}
		if(compEnt.getNiuLineFlag()==1)
		{
			titleStart += "[牛人专线]";
		}
		
		//投诉等级取质检报告中质检结论1的投诉问题等级
		title = titleStart + "[" + compEnt.getLevel() + "级]["
		+ compEnt.getOrderId() + "]";
		
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

	private String getTouchEmailContent(List<QcQuestionEntity> qcQuestions,
			ComplaintEntity compEnt, QcEntity qc,PunishPrdEntity ppe) {
		String remark = "请产品经理通知供应商针对触红原因进行整改,并提交整改报告给SQE进行审核!";
		if (ppe.getOfflineCount() > 3) {
			remark = "请产品经理通知供应商，触红已达4次，产品已永久下线!";
		}
		
		positionMap = CommonUtil.getPositionMap();
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type='text/css'>.datatable {border:1px solid #fff;border-collapse:collapse;font-size:13px;}.datatable th{border:1px solid #fff;color:#355586;background:#DFEAFB; padding:2px;}.datatable td{padding:2px;background-color: #F9F5F2;border: 1px solid #fff;}</style></head><body>");
		sb.append("<table width='1200' class='datatable'><tr><td td colspan=8 align='center'>触红下线通知</td></tr><tr><th width='60' align='right'>线路名称</th><td colspan=7>").append(ppe.getRouteName()).append("</td></tr>")
		.append("<tr><th width='60' align='right'>产品编号</th><td colspan=3>").append(ppe.getRouteId()).append("</td><th width='60' align='right'>订单号</th><td  colspan=3>").append(compEnt.getOrderId()).append("</td></tr>")
		.append("<tr><th width='60' align='right'>产品专员</th><td colspan=3>").append(ppe.getPrdOfficer()).append("</td><th width='60' align='right'>产品经理</th><td  colspan=3>").append(ppe.getPrdManager()).append("</td></tr>")
		.append("<tr><th width='60' align='right'>供应商</th><td colspan=3>").append(ppe.getSupplier()).append("</td><th width='60' align='right'>触红次数</th><td  colspan=3>").append(ppe.getOfflineCount()).append("</td></tr>")
		.append("<tr><th width='60' align='right'>处理措施</th><td colspan=7>").append(remark).append("</td></tr><tr><td width='60' align='right'>质检报告</td><td colspan=7></td></tr>");
		sb.append("<tr><th align='right' width='60'>问题描述：</th><td colspan='7'><table style='border-collapse: collapse;font-size:12px;'>");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId",compEnt.getId());
		List<ComplaintReasonEntity> complaintReasons = (List<ComplaintReasonEntity>) reasonService.fetchList(paramMap);
		for (ComplaintReasonEntity reason : complaintReasons) {
			sb.append("<tr><td width='195'>").append(reason.getType()).append("&nbsp;-&nbsp;").append(reason.getSecondType())
			.append("</td><td>").append(reason.getTypeDescript()).append("</td></tr>");
		}
		sb.append("</table></td></tr><tr><th align='right' width='150'>核实情况：</th><td colspan='7'>").append(CommonUtil.replaceEnter(qc.getVerify())).append("</td></tr>");//核实情况邮件内容换行转义
		for (int i=0; i<qcQuestions.size(); i++) {
			QcQuestionEntity question = qcQuestions.get(i);
			sb.append("<tr><td colspan='8'><b>质检结论").append(i+1).append("</b></td></tr>")
			.append("<tr><th align='right' width='60'>问题类型：</th><td width='150'>").append(question.getBigClassName()).append("—").append(question.getSmallClassName())
			.append("</td><th align='right' width='150'>投诉问题等级：</th><td colspan='5'>");
			if (question.getCompLevel() != -1) {
				sb.append(question.getCompLevel()).append("&nbsp;级");
			}
			sb.append("</td></tr><tr><th align='right' width='60'>质检结论：</th><td colspan='7'>").append(CommonUtil.replaceEnter(question.getConclusion()))//质检结论邮件内容换行转义
			.append("</td></tr><tr><th align='right' width='60'>记分等级：</th><td>").append(question.getScoreLevel())
			.append("</td><th align='right' width='150'>记分值：</th><td width='75'>");
			if (0 == question.getScoreValue()) {
				sb.append("无");
			} else {
				sb.append(question.getScoreValue());
			}
			sb.append("</td><th align='right' width='150'>记分对象1：</th><td width='75'>").append(question.getScoreTarget1().trim().equals("")?"无":question.getScoreTarget1())
			.append("</td><th align='right' width='150'>记分对象2：</th><td width='75'>").append(question.getScoreTarget2().trim().equals("")?"无":question.getScoreTarget2()).append("</td></tr>");
			if ("一级乙等-红线".equals(question.getScoreLevel())) {
				sb.append("<tr><th align='right' width='150'>出发地：</th><td>").append(question.getStartCity())
				.append("</td><th align='right' width='150'>目的地：</th><td>").append(question.getEndCity())
				.append("</td><th align='right' width='150'>机票价格（元）：</th><td>").append(question.getAirfare())
				.append("</td><th align='right' width='150'>产品价格（元）：</th><td>").append(question.getProductPrice())
				.append("</td></tr>");
			}
			
			List<QcTrackerEntity> trackerList = question.getTrackers();
			for (int j=0; j<trackerList.size(); j++) {
				QcTrackerEntity tracker = trackerList.get(j);
				String responsibility = getresponsibilities(tracker);
				String position = "无";
				if (!"0".equals(tracker.getPosition())) {
					position = (String) positionMap.get(tracker.getPosition());
				}
				sb.append("<tr><th align='right' width='150'>责任归属").append(j+1).append("：</th><td>")
				.append(responsibility).append("</td><th align='right' width='150'>执行岗位：</th><td>").append(position)
				.append("</td><th align='right' width='60'>责任人：</th><td>").append(tracker.getResponsiblePerson().trim().equals("")?"无":tracker.getResponsiblePerson())
				.append("</td><th align='right' width='60'>改进人：</th><td>").append(tracker.getImprover().trim().equals("")?"无":tracker.getImprover()).append("</td></tr>");
			}
		}
		sb.append("</table></body></html>");
		
		//System.out.println(sb.toString());		
		return sb.toString();
		
	}
	
	private String getTouchEmailContent(Integer qcId,Integer offlineCount) {
	    String originEmailContent = ComplaintRestClient.getTouchRedEmailContentFromQms(qcId);
	    String remark = "请产品经理通知供应商针对触红原因进行整改,并提交整改报告给SQE进行审核!";
        if (offlineCount > 3) {
            remark = "请产品经理通知供应商，触红已达4次，产品已永久下线!";
        }
        
        String emailContent = originEmailContent.replace("<!-- 投诉系统触红邮件使用 -->", "<tr><th>触红次数：</th><td colspan='9'><p>"+offlineCount+"</p></td></tr><tr><th>处理措施：</th><td colspan='9'><p>"+remark+"</p></td></tr>" +
                "<tr><th>触红下线规则：</th><td colspan='9' style='color:red;'><p>当产品第1次达不到触红要求时，产品下线整改时间为7天</p><p>当产品第2次达不到触红要求时，产品下线整改时间为15天；</p><p>当产品第3次达不到触红要求时，产品下线整改时间为30天；</p><p>当产品第4次达不到触红要求时，产品永久下线。</p></td></tr>");
	    emailContent = emailContent.replace("<!-- touchRedTitle -->", "<tr><td td colspan='10' align='center'>触红下线通知</td></tr>");
        return emailContent;
	}

	private String[] getRedRes(ComplaintEntity compEnt) {
		List<String> list = new ArrayList<String>();
		list.add(compEnt.getProducter());
		list.add(compEnt.getProductLeader());
		list.add(compEnt.getProductManager());
		list.add(compEnt.getDepManager());
		list.add(compEnt.getSeniorManager());
		

		List<String> nameList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (StringUtil.isNotEmpty(list.get(i))) {
				nameList.add("'" + list.get(i) + "'");
			}
		}
		String realNames = CommonUtil.arrToStr(nameList
				.toArray(new String[nameList.size()]));
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

	private String[] getRedCcs() {
		List<String> ccEmails = new ArrayList<String>();
		ccEmails.add("changjingyong@tuniu.com");
		ccEmails.add("g-qa@tuniu.com"); // 质量部
		ccEmails.add("g-sup-cs@tuniu.com"); // 质检部
		ccEmails.add("g-sup-qual@tuniu.com"); // SQE组
		ccEmails.add("g-ci@tuniu.com"); // 流程部
		// 杨惠藩和韩强
		ccEmails.add("yanghuifan@tuniu.com");
		ccEmails.add("hanqiang@tuniu.com");
		ccEmails.add("yanhaifeng@tuniu.com");
		ccEmails.add("yudunde@tuniu.com");

		return ccEmails.toArray(new String[ccEmails.size()]);
	}

	/**
	 * 获取责任归属中文显示
	 * 
	 * @param QcTrackerEntity
	 *            tracker
	 * @return String
	 */
	private String getresponsibilities(QcTrackerEntity tracker) {
		String responsibility = "";// 一级责任归属
		String respSecond = "";// 二级责任归属
		String respThird = "";// 三级责任归属
		if (!tracker.getResponsibility().equals("0")) {
			if (!getOldRecord(tracker.getResponsibility()).equals("")) {// 判断是否是老数据
				responsibility += getOldRecord(tracker.getResponsibility());
			} else {
				officerDept = responsibility(); // 获取一级责任归属
				responsibility += (String) officerDept.get(tracker
						.getResponsibility());
				if (!tracker.getRespSecond().equals("0")) {
					tracker.setRespSecond(getDepartmentNameById(Integer
							.valueOf(tracker.getRespSecond())));
					respSecond = tracker.getRespSecond();
					responsibility += "/" + respSecond;
					if (!tracker.getRespThird().equals("0")) {
						tracker.setRespThird(getDepartmentNameById(Integer
								.valueOf(tracker.getRespThird())));
						respThird = tracker.getRespThird();
						responsibility += "/" + respThird;
					}
				}
			}
			if (!responsibility.equals("")) {
				responsibility += " ";
			} else {
				responsibility += "无 ";
			}
		} else {
			responsibility += "无 ";
		}
		return responsibility;
	}

	/**
	 * 获取责任归属历史信息
	 * 
	 * @param String
	 *            mapId
	 * @return String
	 */
	private String getOldRecord(String mapId) {
		String returnStr = "";
		if (Integer.valueOf(mapId) > 0 && Integer.valueOf(mapId) < 14) {
			returnStr = officerDeptOld.get(mapId);
		}
		return returnStr;
	}

	/**
	 * 根据部门号获取部门名
	 * 
	 * @param int depId
	 * @return String depName
	 */
	public String getDepartmentNameById(int depId) {
		DepartmentEntity department = departmentService
				.getDepartmentById(depId);
		if (department != null) {
			return department.getDepName();
		} else {
			return "";
		}
	}
	
	/**
	 * 获取一级责任归属
	 * 
	 * @return
	 */
	private Map<String, Object> responsibility() {
		Map<String, Object> responsibility = new LinkedHashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fatherId", 1);
		// 获取一级部门
		@SuppressWarnings("unchecked")
		List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService
				.fetchList(paramMap);
		for (DepartmentEntity department : departments) {
			if (department.getFatherId() == 1
					&& !department.getDepName().equals("其它人员")
					&& department.getDelFlag() == 0) {
				responsibility.put(String.valueOf(department.getId()),
						department.getDepName());
			}
		}
		responsibility.put("1", "不可抗力");
		responsibility.put("2", "系统/流程问题");
		responsibility.put("14", "其他");
		return responsibility;
	}
	
	/** NG产品上下线 */
	private boolean chgNGPrdStatus(long prdId, int status, String url) {
		boolean isSucc = false;
		logger.info("chgNGPrdStatus Begin: prdId is " + prdId + ", status is " + status);
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("POST");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", String.valueOf(prdId));
		params.put("status", status);

		trestClient.setData(JSONObject.fromObject(params).toString());
		try {
			String respMsg = trestClient.execute();
			logger.info("chgNGPrdStatus Ing: ResponseMessage is " + respMsg);
			JSONObject jObject = JSONObject.fromObject(respMsg);
			isSucc = jObject.getBoolean("success");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("chgNGPrdStatus End: prdId is " + prdId + ", status is " + status);
		return isSucc;
	}
	
	/** 团队产品上下线 */
	private boolean chgTDPrdStatus(long prdId, int status, String url) {
		boolean isSucc = false;
		logger.info("chgTDPrdStatus Begin: prdId is " + prdId + ", status is " + status);
		if (0 == status) {
			status = 6;
		} else if (1 == status) {
			status = 2;
		}
		
		Vector<String> params = new Vector<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", String.valueOf(prdId));
		map.put("status", status);
		params.add(JSONObject.fromObject(map).toString());
		Object temp = xBase.xmlrpc(url, "RouteServer.changeProductStatus", params);
		String respMsg = temp.toString();
		logger.info("chgTDPrdStatus Ing: ResponseMessage is " + respMsg);
		JSONObject jObject = JSONObject.fromObject(respMsg);
		isSucc = jObject.getBoolean("success");
		
		logger.info("chgTDPrdStatus End: prdId is " + prdId + ", status is " + status);
		return isSucc;
	}
	
	/** 跟团产品上下线 */
	private boolean chgBossPrdStatus(long prdId, int status, String url, int userId) {
		boolean isSucc = false;
		logger.info("chgBossPrdStatus Begin: prdId is " + prdId + ", status is " + status);
		if (0 == status) {
			status = 11;
		} else if (1 == status) {
			status = 2;
		}
		
		TRestClient trestClient = new TRestClient(url);
		trestClient.setMethod("POST");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("func", "operateRoute");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("route_id", prdId);
		params.put("status", status);
		params.put("uid", userId);
		map.put("params", params);
		trestClient.setData(JSONObject.fromObject(map).toString());
		try {
			String respMsg = trestClient.execute();
			logger.info("chgBossPrdStatus Ing: ResponseMessage is " + respMsg);
			JSONObject jObject = JSONObject.fromObject(respMsg);
			isSucc = jObject.getBoolean("success");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("chgBossPrdStatus End: prdId is " + prdId + ", status is " + status);
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
			sendLowQualityMsg(id,offlineCount);
		}
		
	}

	/**
	 * 发送触红下线提醒
	 * @param id
	 * @param num
	 */
	private void sendTouchredMsg(int id, int num) {


		PunishPrdEntity ppe = (PunishPrdEntity) get(id);
		ComplaintEntity cmp = null;
		String emailContent = "";
        if(ppe.getSystem() == 1) { // qms触红数据
            cmp = (ComplaintEntity) cmpService.get(ppe.getCmpId());
            emailContent = getTouchEmailContent(ppe.getQcId(),ppe.getOfflineCount()); 
        } else { // 本系统数据
            QcEntity qc = (QcEntity) qcService.get(ppe.getQcId());
            cmp = (ComplaintEntity) cmpService.get(qc.getComplaintId());
            List<QcQuestionEntity> qcQuestions = new ArrayList<QcQuestionEntity>();
            List<QcReportEntity> qcReports = new ArrayList<QcReportEntity>();
            qcReports = qcReportService.getReportListByQid(ppe.getQcId());
            QcReportEntity report = qcReports.get(qcReports.size() - 1);
            qcQuestions = report.getQuestions();
            emailContent = getTouchEmailContent(qcQuestions, cmp, qc,ppe); // 邮件内容
        }
		
		sendTouchRedOfflineRtxRemind(ppe.getRouteId(), cmp); // 发送RTX通知
		String emailTitle = getTouchEmailTitle(cmp,ppe.getOfflineCount());// 邮件标题
		tspService.sendMail(new MailerThread(getRedRes(cmp), getRedCcs(), emailTitle, emailContent));
				
	}

    private void sendTouchRedOfflineRtxRemind(Long routeId, ComplaintEntity cmp) {
        String title = "产品触红下线通知";
		String content = "产品："	+ routeId +"因满足触红产品条件，已被强制下线";
		String realNames = "";
		if (StringUtil.isNotEmpty(cmp.getProducter())) {
			realNames += "'" + cmp.getProducter() + "',";
		}
		if (StringUtil.isNotEmpty(cmp.getProductLeader())) {
			realNames += "'" + cmp.getProductLeader() + "',";
		}
		if (StringUtil.isNotEmpty(realNames)) {
			realNames = realNames.substring(0, realNames.length() - 1);
			List<UserEntity> userList = userService.getUsers("realNames",
					realNames);
			for (UserEntity user : userList) {
				new RTXSenderThread(user.getId(), user.getRealName(), title,
						content).start();
			}
		}
    }
	
	/**
	 * 发送低满意度下线提醒
	 * @param id
	 * @param offlineCount
	 */
	private void sendLowSatisfyMsg(int id, int offlineCount) {
		//发送邮件
		//设置邮件标题
		PunishPrdEntity ppe = (PunishPrdEntity) get(id);
		String emailTitle = "第"+ppe.getOfflineCount()+"次低满意度下线整改通知";
		//设置邮件内容
		String emailContent = getLowSatisfyEmailContent(ppe);
		
		String prdManager = ppe.getPrdManager();
		UserEntity seniorManager = ComplaintRestClient.getReporter(prdManager);
		if(seniorManager!=null){
			UserEntity productManager = ComplaintRestClient.getReporter(seniorManager.getRealName());
		}
		
		// 发送RTX通知
		String title = "产品低满意度下线通知";
		String content = "产品：" + ppe.getRouteId() + "因满足低满意度产品条件，已被强制下线";
		String realNames = "";
		if (StringUtil.isNotEmpty(ppe.getPrdOfficer())) {
			realNames += "'" + ppe.getPrdOfficer() + "',";
		}
		if (StringUtil.isNotEmpty(ppe.getPrdManager())) {
			realNames += "'" + ppe.getPrdManager()+"',";
		}
		if (StringUtil.isNotEmpty(realNames)) {
			realNames = realNames.substring(0, realNames.length() - 1);
			List<UserEntity> userList = userService.getUsers("realNames",
					realNames);
			for (UserEntity user : userList) {
				new RTXSenderThread(user.getId(), user.getRealName(), title,
						content).start();
			}
		}
		
		tspService.sendMail(new MailerThread(getLowSatisfyOrLowQualityRes(ppe),getLowSatisfyOrLowQualityCcs(ppe),
                emailTitle, emailContent));
	}
	
	private void sendLowQualityMsg(int id, int offlineCount) {
		//发送邮件
		//设置邮件标题
		PunishPrdEntity ppe = (PunishPrdEntity) get(id);
		String emailTitle = "低质量产品下线通知";
		//设置邮件内容
		String emailContent = getLowQualityEmailContent(ppe);
		
		// 发送RTX通知
		String title = "产品低质量下线通知";
		String content = "产品：" + ppe.getRouteId() + "因满足低质量产品条件，已被强制下线";
		String realNames = "";
		if (StringUtil.isNotEmpty(ppe.getPrdOfficer())) {
			realNames += "'" + ppe.getPrdOfficer() + "',";
		}
		if (StringUtil.isNotEmpty(ppe.getPrdManager())) {
			realNames += "'" + ppe.getPrdManager() + "',";
		}
		if (StringUtil.isNotEmpty(realNames)) {
			realNames = realNames.substring(0, realNames.length() - 1);
			List<UserEntity> userList = userService.getUsers("realNames",
					realNames);
			for (UserEntity user : userList) {
				new RTXSenderThread(user.getId(), user.getRealName(), title,
						content).start();
			}
		}
		
		tspService.sendMail(new MailerThread(getLowSatisfyOrLowQualityRes(ppe),getLowSatisfyOrLowQualityCcs(ppe),
				emailTitle, emailContent));
	}
	
private void buildByOfflineType(PunishPrdEntity e) {
		buildOfflineCount(e);
		e.setOfflineTime(new Date());//设置下线时间
		buildStatusAndOnlineTime(e); // 设置下线状态和上线时间
	}

	private void buildOfflineCount(PunishPrdEntity e) {
		int offlineCount;

		if (e.getOfflineType() == 3) {//低质量下线次数设为1
			offlineCount = 1;
		} else {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("routeId", e.getRouteId());
			paramMap.put("offlineType", e.getOfflineType());

			Integer maxOfflineCountRecord = dao.getMaxOfflineCount(paramMap);

			offlineCount = maxOfflineCountRecord == null ? 1
					: (maxOfflineCountRecord + 1);

			if(e.getOfflineType()==1 && offlineCount>4){
			    offlineCount = 4;
			}else if(e.getOfflineType()==2 && offlineCount>5){
			    offlineCount = 5;
			}
			
		}

		e.setOfflineCount(offlineCount);
	}
	
	private PunishPrdEntity getBaseEntity(Long routeId) {
		PunishPrdEntity e = new PunishPrdEntity();
		Map<String, Object> productInfo = null;
		
		//调用boh接口获取产品信息，增加失败重试2次，失败三次后放弃
		int failTimes=0;
		do{
		    productInfo = tspService.getProductInfo(routeId);
		    failTimes++;
		}while(productInfo.size()==0&&failTimes<3);
		
		if((Integer)productInfo.get("errCode")==0){
			e.setRouteId(routeId);
			e.setRouteName((String) productInfo.get("productName"));
			e.setPrdManager((String) productInfo.get("managerName"));
			e.setPrdOfficer((String) productInfo.get("ownerName"));
			Map<String, Object> depMap = (Map<String, Object>)productInfo.get("departmentInfo");
			e.setBU((String) depMap.get("businessUnitName"));
			return e;
		}
		return null;
	}
	
	private PunishPrdEntity getBaseEntity(GMVRankEntity gmvRankEntity) {
		PunishPrdEntity e = new PunishPrdEntity();
		e.setGmvRankId(gmvRankEntity.getId());
		e.setRouteId(gmvRankEntity.getRouteKey());
		e.setRouteName(gmvRankEntity.getRouteName());
		Map<String, Object> productInfo = null;
		//调用boh接口获取产品信息，增加失败重试2次，失败三次后放弃
        int failTimes=0;
        do{
            productInfo = tspService.getProductInfo(e.getRouteId());
            failTimes++;
        }while(productInfo.size()==0&&failTimes<3);
        
		if((Integer)productInfo.get("errCode")==0){
			e.setPrdManager((String) productInfo.get("managerName"));
			e.setPrdOfficer((String) productInfo.get("ownerName"));
			Map<String, Object> depMap = (Map<String, Object>)productInfo.get("departmentInfo");
			e.setBU((String) depMap.get("businessUnitName"));
		}
		
		return e;
	};



	public Map<String, String> getOfficerDeptOld() {
		return officerDeptOld;
	}

	public void setOfficerDeptOld(Map<String, String> officerDeptOld) {
		this.officerDeptOld = officerDeptOld;
	}

	public Map<String, Object> getPositionMap() {
		return positionMap;
	}

	public void setPositionMap(Map<String, Object> positionMap) {
		this.positionMap = positionMap;
	}

	public Map<String, Object> getOfficerDept() {
		return officerDept;
	}

	public void setOfficerDept(Map<String, Object> officerDept) {
		this.officerDept = officerDept;
	}

	@Override
	public Integer getListCount(Map<String, Object> paramMap) {
		return dao.getListCount(paramMap);
	}

}
