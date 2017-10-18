package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.config.db.service.DBConfigService;
import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.dao.impl.AutoAssignDao;
import com.tuniu.gt.complaint.entity.AssignRecordEntity;
import com.tuniu.gt.complaint.entity.AutoAssignCfgQcEntity;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.enums.AssignType;
import com.tuniu.gt.complaint.enums.TourTimeNodeEnum;
import com.tuniu.gt.complaint.service.IAssignRecordService;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.service.Constant;

/**
* @ClassName: AppointManagerServiceImpl
* @Description:接口实现
* @author Ocean Zhong
* @date 2012-1-29 下午2:53:14
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_service_complaint_impl-auto_assign")
public class AutoAssignServiceImpl extends ServiceBaseImpl<AutoAssignDao> implements IAutoAssignService {
	
	private static Logger logger = Logger.getLogger(AutoAssignServiceImpl.class);
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-assign_record")
	private IAssignRecordService recordService;
	
	@Autowired
	@Qualifier("complaint_dao_impl-auto_assign")
	public void setDao(AutoAssignDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;
	
	@Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;

	// 用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
    private DBConfigService dbConfigService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoAssignEntity> getListByType(int type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		return (List<AutoAssignEntity>) this.fetchList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AutoAssignEntity> getListAllByType(int type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("delFlag", true);
		return (List<AutoAssignEntity>) this.fetchList(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AutoAssignEntity> getAllListByType(int type, int tourTimeNode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("tourTimeNode", tourTimeNode);
		paramMap.put("delFlag", true);
		return (List<AutoAssignEntity>) this.fetchList(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<AutoAssignEntity> getAssignCompInfoList(java.sql.Date statDate, int tourTimeNode,AssignType assignType) {
		List<AutoAssignEntity> list = new ArrayList<AutoAssignEntity>();
		
		if (DateUtil.getSqlToday().toString().equals(statDate.toString())) {
			list = getAssignCompInfoList(tourTimeNode,assignType);
			if (Constant.CONFIG.getProperty("sendFlag").equals("1")) {
				if (null != list) {
					for (AutoAssignEntity ent : list) {
						String dealDepart = "";
						if (1 == tourTimeNode) {
							dealDepart = Constans.SPECIAL_BEFORE_TRAVEL;
						}
						int readyFlag = 0;
						if (CommonUtil.isStatus(userService.getExtensionByUserId(ent.getUserId()), dealDepart)) {
							readyFlag = 1;
						}
						ent.setReadyFlag(readyFlag);
					}
				}
			}
		} else {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("type", assignType.getPersistentType());
			if (tourTimeNode > 0) {
				paramMap.put("tourTimeNode", tourTimeNode);
			}
			paramMap.put("statDate", statDate);
			List<AssignRecordEntity> recordList = (List<AssignRecordEntity>) recordService.fetchList(paramMap);
			for (AssignRecordEntity record : recordList) {
				AutoAssignEntity assign = new AutoAssignEntity();
				assign.setUserName(record.getUserName());
				assign.setDepartmentName(record.getDepartmentName());
				assign.setTourTimeNode(record.getTourTimeNode());
				assign.setOrderIds(record.getOrderIds());
				assign.setListNums((long) record.getOrderNum());
				list.add(assign);
			}
		}
		
		return list;
	}

	public List<AutoAssignEntity> getAssignCompInfoList(int tourTimeNode,AssignType assignType) {
		List<AutoAssignEntity> destList = new ArrayList<AutoAssignEntity>();
		List<AutoAssignEntity> complaintList = new ArrayList<AutoAssignEntity>();
		if (0 == tourTimeNode) {
			complaintList = getListAllByType(AutoAssignEntity.TYPE_COMPLAINT);
		} else {
			complaintList = getAllListByType(AutoAssignEntity.TYPE_COMPLAINT, tourTimeNode);
		}
		// 查询当前处理岗下 所有客服的 单子数
		for (AutoAssignEntity entity : complaintList) {
			String orderIds = "";
			String tmpValue = MemcachesUtil.get(assignType.getMemPre() + "order_" + entity.getUserId());
			if (StringUtils.isNotBlank(tmpValue)) {
				String[] idsArr = tmpValue.split(",");
				StringBuffer sb = new StringBuffer();
				for (int i=0; i<idsArr.length; i++) {
					String id = idsArr[i];
					sb.append(id);
					if (i != (idsArr.length - 1)) {
						sb.append(",");
						if ((i+1) % 10 == 0) {
							sb.append("<br>");
						}
					}
				}
				orderIds = sb.toString();
			}
			entity.setListNums(getValue(entity,assignType));
			entity.setOrderIds(orderIds);
			destList.add(entity);
		}
		return destList;
	}
	
	private Long getValue(AutoAssignEntity entity,AssignType assignType) {
		Long value = 0L;
		String key = assignType.getMemPre() + entity.getUserId();
		String strValue = MemcachesUtil.get(key);
		if (StringUtils.isNotBlank(strValue)) {
			value = Long.parseLong(strValue);
		}
		return value;
	}

	@Override
	public AutoAssignEntity getByTypeAndUserId(Map<String, Object> params) {
		return dao.getByTypeAndUserId(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AutoAssignEntity> getQcList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", AutoAssignEntity.TYPE_QC);
		paramMap.put("delFlag", true);
		List<AutoAssignEntity> list = (List<AutoAssignEntity>) this.fetchList(paramMap);
		for (AutoAssignEntity ent : list) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("assignId", ent.getId());
			List<AutoAssignCfgQcEntity> cfgQcList = dao.getCfgQcList(params);
			List<AutoAssignCfgQcEntity> noList = new ArrayList<AutoAssignCfgQcEntity>();
			List<AutoAssignCfgQcEntity> noSpList = new ArrayList<AutoAssignCfgQcEntity>();
			for (AutoAssignCfgQcEntity cfgQc : cfgQcList) {
				setDepNames(cfgQc);
				if(cfgQc.getDepIds()!=null && !"".equals(cfgQc.getDepIds())){
					noList.add(cfgQc);
				}else{
					noSpList.add(cfgQc);
				}
			}
			ent.setCfgQcList(noList);
			ent.setCfgQcSpList(noSpList);
		}
		return list;
	}
	
	private void setDepNames(AutoAssignCfgQcEntity cfgQc) {
		String[] depIds = null;
		if(cfgQc.getDepIds()!=null && !"".equals(cfgQc.getDepIds())){
			depIds = cfgQc.getDepIds().split(",");
		}else{
			depIds = cfgQc.getSpDepIds().split(",");
		}
		StringBuffer depNames = new StringBuffer();
		for (int i=0; i<depIds.length; i++) {
			DepartmentEntity dep = departmentService.getDepartmentById(Integer.valueOf(depIds[i]));
			if (null != dep) {
				if (i < depIds.length - 1) {
					depNames.append(dep.getDepName()).append(",");
				} else {
					depNames.append(dep.getDepName());
				}
			}
		}
		cfgQc.setDepNames(depNames.toString());
	}

	@Override
	public List<AutoAssignEntity> getQcInfoList() {
		List<AutoAssignEntity> qcList = getListByType(AutoAssignEntity.TYPE_QC);
		for (AutoAssignEntity entity : qcList) {
			String key = ComplaintUtil.MEM_PRE_QC + entity.getUserId();
			Long value = 0L;
			String strValue = MemcachesUtil.get(key);
			if (StringUtils.isNotBlank(strValue)) {
				value = Long.parseLong(strValue);
			}
			
			String orderIds="";
			String tmpValue = MemcachesUtil.get(ComplaintUtil.MEM_PRE_QC + "order_" + entity.getUserId());
			if (StringUtils.isNotBlank(tmpValue)) {
				orderIds = tmpValue;
			}
			entity.setListNums(value);
			entity.setOrderIds(orderIds);
		}
		return qcList;
	}

	@Override
	public AutoAssignEntity getQcPerson(ComplaintEntity compEnt) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", 2);
		if (compEnt.getOrderId() > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", compEnt.getOrderId());
			params.put("status", "1,2");
			params.put("consultation", 0);
			QcEntity qc = (QcEntity) qcService.get(params);
			if (null != qc && qc.getQcPerson() > 0) {
				paramMap.put("userId", qc.getQcPerson());
				return dao.fetchOne(paramMap);
			} else {
				if ("微博".equals(compEnt.getComeFrom()) || "旅游局".equals(compEnt.getComeFrom()) || compEnt.getLevel()==1 || compEnt.getIsMedia()==1){
					paramMap.put("secondarySpDepId", "%," + compEnt.getSecondaryDepId() + ",%");
				} else {
					paramMap.put("secondaryDepId", "%," + compEnt.getSecondaryDepId() + ",%");
				}
			}
		} else {
			paramMap.put("noOrdFlag", 1);
		}
		List<AutoAssignEntity> list = dao.getQcPersonList(paramMap);
		return getDestAssignEnt(list);
	}
	
	public AutoAssignEntity getDestAssignEnt(List<AutoAssignEntity> list) {
		AutoAssignEntity destEnt = null;
		Date tempTime = new Date();
		for (AutoAssignEntity ent : list) {
			String key = ComplaintUtil.MEM_PRE_QC + "time_" + ent.getUserId();
			String timeStr = MemcachesUtil.get(key);
			if (StringUtil.isEmpty(timeStr)) {
				destEnt = ent;
				break;
			} else {
				Date assignTime = DateUtil.parseDate(timeStr, "yyyy-MM-dd HH:mm:ss");
				if (tempTime.after(assignTime)) {
					tempTime = assignTime;
					destEnt = ent;
				}
			}
		}
		return destEnt;
	}

	@Override
	public void insertQc(AutoAssignEntity entity) {
		this.insert(entity);
		insertCfgQc(entity.getDepIds(), entity.getId());
		insertCfgQcSp(entity.getSpDepIds(), entity.getId());
	}
	
	private void insertCfgQc(List<String> depIds, int assignId) {
		for(int i = 0 ; i < depIds.size();i++){
			if (null != depIds.get(i) && depIds.get(i).length() > 0) {
				AutoAssignCfgQcEntity depCfg = new AutoAssignCfgQcEntity();
				depCfg.setAssignId(assignId);
				depCfg.setDepName((depIds.get(i)!=null && !"null".equals(depIds.get(i)))?depIds.get(i).split("@")[0]:"");
				depCfg.setDepIds((depIds.get(i)!=null && !"null".equals(depIds.get(i)))?depIds.get(i).replaceAll(depCfg.getDepName()+"@", ""):"");
				depCfg.setSpDepIds("");
				dao.insertCfgQc(depCfg);
			}
		}
	}
	
	private void insertCfgQcSp(List<String> spDepIds, int assignId) {
		for(int i = 0 ; i < spDepIds.size();i++){
			if (null != spDepIds.get(i) && spDepIds.get(i).length() > 0) {
				AutoAssignCfgQcEntity spDepCfg = new AutoAssignCfgQcEntity();
				spDepCfg.setAssignId(assignId);
				spDepCfg.setDepName((spDepIds.get(i)!=null && !"null".equals(spDepIds.get(i)))?spDepIds.get(i).split("@")[0]:"");
				spDepCfg.setDepIds("");
				spDepCfg.setSpDepIds((spDepIds.get(i)!=null && !"null".equals(spDepIds.get(i)))?spDepIds.get(i).replaceAll(spDepCfg.getDepName()+"@", ""):"");
				dao.insertCfgQc(spDepCfg);
			}
		}
	}

	@Override
	public AutoAssignEntity getQcPerson(Integer id) {
		AutoAssignEntity ent = (AutoAssignEntity) dao.get(id);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assignId", ent.getId());
		List<AutoAssignCfgQcEntity> cfgQcList = dao.getCfgQcList(params);
		List<AutoAssignCfgQcEntity> noList = new ArrayList<AutoAssignCfgQcEntity>();
		List<AutoAssignCfgQcEntity> noSpList = new ArrayList<AutoAssignCfgQcEntity>();
		for (AutoAssignCfgQcEntity cfgQc : cfgQcList) {
			setDepNames(cfgQc);
			if(cfgQc.getDepIds()!=null && !"".equals(cfgQc.getDepIds())){
				noList.add(cfgQc);
			}else{
				noSpList.add(cfgQc);
			}
		}
		ent.setCfgQcList(noList);
		ent.setCfgQcSpList(noSpList);
		return ent;
	}

	@Override
	public void updateQc(AutoAssignEntity entity) {
		entity.setType(entity.TYPE_QC);
		dao.update(entity);
		
		int assignId = entity.getId();
		dao.deleteCfgQc(assignId);
		insertCfgQc(entity.getDepIds(), assignId);
		insertCfgQcSp(entity.getSpDepIds(), assignId);
	}

	@Override
	public void updateLastAssignTime(int id) {
		dao.updateLastAssignTime(id);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<AutoAssignEntity> getCurrStateList(Integer type, ComplaintEntity complaint) {
		int tourTimeNode = 0;
//		int touringGroupType = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String levelField = "";
		if(tourTimeNode!=6){
    		switch (complaint.getLevel()) {
    			case 1:
    				levelField = "complaint_level1_flag";
    				break;
    			case 2:
    				levelField = "complaint_level2_flag";
    				break;
    			case 3:
    				levelField = "complaint_level3_flag";
    				break;
    		}
		}
		String productCategory = complaint.getRouteTypeSp();
		//String cate = ComplaintRestClient.queryRouteCatById(complaint.getRouteId());
		String destField = "";
		String destCategory = complaint.getDestCategoryName();
		if (StringUtil.isNotEmpty(destCategory) && tourTimeNode!=6) {
			if ("周边".equals(destCategory)) {
				destField = "around_flag";
			} else if ("国内长线".equals(destCategory)) {
				destField = "inland_long_flag";
			} else if ("出境短线".equals(destCategory)) {
				destField = "abroad_short_flag";
			} else if ("出境长线".equals(destCategory)) {
				destField = "abroad_long_flag";
			} else {
				destField = "others_flag";
			}
		}
		String signCity = complaint.getSignCity();
//		String signCityParam="其他";
//		String signCityStr= dbConfigService.getConfig("auto.assign.city");
//		String[] signCityArr= signCityStr.split(",");
//		for(int index=0;index<signCityArr.length;index++){
//			if(signCityArr[index].equals(signCity)){
//				signCityParam = signCityArr[index];
//				break;
//			}
//		}
		if(!"北京".equals(signCity)&&!"上海".equals(signCity)&&!"深圳".equals(signCity)&&!"广州".equals(signCity)){
			signCity="其他";
		}
		String comeFrom = complaint.getComeFrom();
		Integer guestLevel=complaint.getGuestLevelNum();
		if (Constans.SPECIAL_BEFORE_TRAVEL.equals(complaint.getDealDepart())) {//出游前
			tourTimeNode = 1;
			paramMap.put("guestLevel", guestLevel);
			if( 1 == complaint.getIsReparations()) { // 赔款单
				paramMap.put("payforOrder", 1);
			}
		} else if (Constans.IN_TRAVEL.equals(complaint.getDealDepart())) {//售后组
			tourTimeNode = 2;
			paramMap.put("guestLevel", guestLevel);
			paramMap.put("signCity", signCity);
//			touringGroupType = complaint.getTouringGroupType();
		} else if (Constans.AFTER_TRAVEL.equals(complaint.getDealDepart())) {//资深组
			tourTimeNode = 3;
			paramMap.put("signCity", signCity);
			paramMap.put("guestLevel", guestLevel);
			//todo signCity
			String other = "回访,当地质检,网站,门市";
			if(other.indexOf(comeFrom)!=-1){
				comeFrom="其他";
			}
			if("来电投诉".equals(comeFrom)){
				comeFrom = "来电";
			}
			paramMap.put("comeFrom", comeFrom);
		} else if (Constans.AIR_TICKIT.equals(complaint.getDealDepart())) {//机票组
			tourTimeNode = 4;
			productCategory="";
		} else if (Constans.HOTEL.equals(complaint.getDealDepart())) {//酒店组
            tourTimeNode = 5;
        }else if (Constans.TRAFFIC.equals(complaint.getDealDepart())) {//交通组
            tourTimeNode = 6;
            productCategory="";
        }else if (Constans.DISTRIBUTE.equals(complaint.getDealDepart())) {//分销机票
            tourTimeNode = 7;
            productCategory="";
        }
		paramMap.put("type", type);
		paramMap.put("tourTimeNode", tourTimeNode);
		paramMap.put("levelField", levelField);
		paramMap.put("productCategory", productCategory);
		paramMap.put("destField", destField);
		return (List<AutoAssignEntity>) this.fetchList(paramMap);
	}
	
	public Boolean assignStateIsWork(Integer userId,String dealDepart){
		Boolean result = false;
		try{
			if(userId !=null && userId>0 && dealDepart !=null && StringUtil.isNotEmpty(dealDepart)){
				result = Constans.autoAssignIsWork.equals(getRepeatAssignState(userId,dealDepart));
			}
		}catch (Exception e){
			logger.error("assignStateIsWork userId:" + userId + " dealDepart:" + dealDepart, e);
		}
		return result;
	}
	
	public Boolean assignStateIsNotWork(Integer userId,String dealDepart){
		Boolean result = false;
		try{
			if(userId !=null && userId>0 && dealDepart !=null && StringUtil.isNotEmpty(dealDepart)){
				result = Constans.autoAssignNotWork.equals(getRepeatAssignState(userId,dealDepart));
			}
		}catch (Exception e){
			logger.error("assignStateIsWork userId:" + userId + " dealDepart:" + dealDepart, e);
		}
		return result;
	}
	
	/**
	 * 根据人员和处理刚获取二次分配状态
	 */
	private Integer getRepeatAssignState(Integer userId,String dealDepart){
		return getRepeatAssignState(userId,dealDepart,AutoAssignEntity.TYPE_COMPLAINT);
	}
	
	private Integer getRepeatAssignState(Integer userId,String dealDepart,Integer type){
		Integer result = Constans.noAutoAssign;
		Integer tourTimeNode = getTourTimeNodeByDealDepart(dealDepart);
		if(tourTimeNode!=null){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("type", type);
			paramMap.put("delFlag", true);
			paramMap.put("userId", userId);
			paramMap.put("tourTimeNode", tourTimeNode);// 交通组
			@SuppressWarnings("unchecked")
			List<AutoAssignEntity> autoAssignList = (List<AutoAssignEntity>) this.fetchList(paramMap);
			if(autoAssignList.size() > 0){
				result = Constans.autoAssignIsWork;
			}
			for (int i = 0; i < autoAssignList.size(); i++) {
				AutoAssignEntity assignEntity = autoAssignList.get(i);
				if (assignEntity.getDelFlag() == 1) {
					result = Constans.autoAssignNotWork;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据处理刚获取处理岗节点
	 * @param dealDepart
	 * @return
	 */
	private Integer getTourTimeNodeByDealDepart(String dealDepart){
		return TourTimeNodeEnum.getValue(dealDepart);
	}
	
	public void updateLastAssignTimeByUser(Integer userId,String dealDepart){
		Integer tourTimeNode=getTourTimeNodeByDealDepart(dealDepart);
		if(tourTimeNode!=null){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("tourTimeNode", tourTimeNode);
			paramMap.put("userId", userId);
			dao.updateLastAssignTimeByUser(paramMap);
		}
	}
}
