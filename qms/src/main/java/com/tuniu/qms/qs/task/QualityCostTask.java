package com.tuniu.qms.qs.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.dto.PrdLineDepInfoDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.model.QualityCostAuxAccount;
import com.tuniu.qms.qs.model.QualityCostLedger;
import com.tuniu.qms.qs.model.RespDepartment;
import com.tuniu.qms.qs.model.TargetConfig;
import com.tuniu.qms.qs.service.CompSatisRateMonitorService;
import com.tuniu.qms.qs.service.QualityCostAuxAccountService;
import com.tuniu.qms.qs.service.QualityCostLedgerService;
import com.tuniu.qms.qs.service.RespDepartmentService;
import com.tuniu.qms.qs.service.TargetConfigService;

/**
 * 质量成本辅助账维护任务
 */
public class QualityCostTask {

	private final static Logger logger = LoggerFactory.getLogger(QualityCostTask.class);

	@Autowired
	private RespDepartmentService respDepService;

	@Autowired
	private MailTaskService mailTaskService;
	
	@Autowired
	private QualityCostLedgerService ledgerService;

	@Autowired
	private QualityCostAuxAccountService auxService;

	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private TspService tspService;
	
	@Autowired
	private CompSatisRateMonitorService satisfyService;
	
	@Autowired
	private TargetConfigService targetService;

	/**
	 * 更新质量损失的责任部门，并记录已撤销的部门
	 */
	public void updateQualityLossDep() {
		try {
			List<QualityCostAuxAccount> depList = auxService.listAllDep(); // 辅助账中的所有一级科目为“质量损失”的部门
			removeNormal(depList); // 剔除正常的部门
			List<QualityCostAuxAccount> list = dealAndGetAbnormalDeps(depList); // 将异动的部门数据更新，将撤销的部门记录下来
			if (list.size() > 0) {
				sendEmail(list);
			}
		} catch (Exception e) {
			logger.error("SyncDepTask error：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void removeNormal(List<QualityCostAuxAccount> depList) {
		List<QualityCostAuxAccount> useDepList = auxService.listUseDep(); // 正常的部门
		for (int i = 0; i < depList.size(); i++) { // 剔除正常的部门
			for (QualityCostAuxAccount useDep : useDepList) {
				if (depList.get(i).getFirstDepId().intValue() == useDep.getFirstDepId().intValue()
						&& depList.get(i).getTwoDepId().intValue() == useDep.getTwoDepId().intValue()
						&& depList.get(i).getThreeDepId().intValue() == useDep.getThreeDepId().intValue()
						&& depList.get(i).getFirstDepName().equals(useDep.getFirstDepName())
						&& depList.get(i).getTwoDepName().equals(useDep.getTwoDepName())
						&& depList.get(i).getThreeDepName().equals(useDep.getThreeDepName())) {
					depList.remove(i);
					i--;
					break;
				}
			}
		}
	}
	
	/**
	 * 将异动的部门数据更新，将撤销的部门记录下来
	 */
	private List<QualityCostAuxAccount> dealAndGetAbnormalDeps(List<QualityCostAuxAccount> depList) {
		List<QualityCostAuxAccount> list = new ArrayList<QualityCostAuxAccount>();
		if (depList.size() > 0) {
			for (QualityCostAuxAccount useDep : depList) {
				Map<String, Object> depInfo = getDepInfoThreeLevel(useDep);
				if (null != depInfo) {
					useDep.setFirstDepId((Integer) depInfo.get("depIdlv1"));
					useDep.setFirstDepName((String) depInfo.get("depNamelv1"));
					int level = (Integer) depInfo.get("level");
					if (level > 1) {
						useDep.setTwoDepId((Integer) depInfo.get("depIdlv2"));
						useDep.setTwoDepName((String) depInfo.get("depNamelv2"));
						if (level > 2) {
							useDep.setThreeDepId((Integer) depInfo.get("depIdlv3"));
							useDep.setThreeDepName((String) depInfo.get("depNamelv3"));
						}
					}
					useDep.setFlag(level);
					auxService.updateDep(useDep); // 依据现行组织架构更新异动的部门
				} else {
					list.add(useDep);
					addIsNotExist(useDep); // 记录被撤销的部门
				}
			}
		}
		return list;
	}
	
	private Map<String, Object> getDepInfoThreeLevel(QualityCostAuxAccount dep) {
		Map<String, Object> depInfo = null;
		if (dep.getThreeDepId() > 0) {
			Department depLv3 = depService.get(dep.getThreeDepId());
			if (null != depLv3 && 0 == depLv3.getDelFlag()) {
				Department depLv2 = depService.get(depLv3.getPid());
				if (null != depLv2 && 0 == depLv2.getDelFlag()) {
					Department depLv1 = depService.get(depLv2.getPid());
					if (null != depLv1 && 0 == depLv1.getDelFlag()) {
						depInfo = new HashMap<String, Object>();
						depInfo.put("level", 3);
						depInfo.put("depIdlv1", depLv1.getId());
						depInfo.put("depNamelv1", depLv1.getName());
						depInfo.put("depIdlv2", depLv2.getId());
						depInfo.put("depNamelv2", depLv2.getName());
						depInfo.put("depIdlv3", depLv3.getId());
						depInfo.put("depNamelv3", depLv3.getName());
					}
				}
			}
		} else if (dep.getTwoDepId() > 0) {
			Department depLv2 = depService.get(dep.getTwoDepId());
			if (null != depLv2 && 0 == depLv2.getDelFlag()) {
				Department depLv1 = depService.get(depLv2.getPid());
				if (null != depLv1 && 0 == depLv1.getDelFlag()) {
					depInfo = new HashMap<String, Object>();
					depInfo.put("level", 2);
					depInfo.put("depIdlv1", depLv1.getId());
					depInfo.put("depNamelv1", depLv1.getName());
					depInfo.put("depIdlv2", depLv2.getId());
					depInfo.put("depNamelv2", depLv2.getName());
				}
			}
			
		} else if (dep.getFirstDepId() > 0) {
			Department depLv1 = depService.get(dep.getFirstDepId());
			if (null != depLv1 && 0 == depLv1.getDelFlag()) {
				depInfo = new HashMap<String, Object>();
				depInfo.put("level", 1);
				depInfo.put("depIdlv1", depLv1.getId());
				depInfo.put("depNamelv1", depLv1.getName());
			}
		}
		return depInfo;
	}
	
	private void sendEmail(List<QualityCostAuxAccount> list) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("sList", list);
		MailTaskDto mailTaskDto = new MailTaskDto();
		mailTaskDto.setTemplateName("depConfig.ftl");
		mailTaskDto.setSubject(DateUtil.formatAsDefaultDate(new Date()) + "需要修改的质量成本责任部门-发送自root");
		mailTaskDto.setReAddrs(new String[]{"zhangnan3@tuniu.com", "liuyajun@tuniu.com"});
		mailTaskDto.setCcAddrs(new String[]{"zhangsensen@tuniu.com", "liwang2@tuniu.com", "liubing@tuniu.com"});
		mailTaskDto.setDataMap(dataMap);
		mailTaskDto.setAddPersonRoleId(0);
		mailTaskDto.setAddPerson("root");
		mailTaskService.addTask(mailTaskDto);
	}

	private void addIsNotExist(QualityCostAuxAccount useDep) {
		try {
			RespDepartment dep = new RespDepartment();
			dep.setFirstDepId(useDep.getFirstDepId());
			dep.setFirstDepName(useDep.getFirstDepName());
			dep.setTwoDepId(useDep.getTwoDepId());
			dep.setTwoDepName(useDep.getTwoDepName());
			dep.setThreeDepId(useDep.getThreeDepId());
			dep.setThreeDepName(useDep.getThreeDepName());
			respDepService.addIsNotExist(dep);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新质量损失
	 */
	public void updateQualityLoss() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			java.sql.Date yesterday = DateUtil.getSqlYesterday();
			map.put("yesterday", yesterday.toString());
			map.put("lastYearDay", DateUtil.addSqlDates(yesterday, -365)); // 获取昨天-往前推一年的数据，目的是减少数据量压力
			List<Integer> cmpIdList = ledgerService.getCmpIdFromResp(map);
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			while (true) {
				if (cmpIdList.size() <= 0) {
					break;
				} else if (cmpIdList.size() < 20) {
					list.addAll(CmpClient.getCmpSpecial(cmpIdList));
					break;
				} else {
					List<Integer> tempList = cmpIdList.subList(0, 20);
					list.addAll(CmpClient.getCmpSpecial(tempList));
					
					List<Integer> tempList2 = new ArrayList<Integer>(); // 规避ConcurrentModificationException问题
					for (Integer cmpId : tempList) {
						tempList2.add(cmpId);
					}
					cmpIdList.removeAll(tempList2);
				}
			}
			
			for (int m=0; m<list.size(); m++) {
				QualityCostLedger ledger = new QualityCostLedger();
				ledger.setCmpId(Integer.parseInt(list.get(m).get("cmpId").toString()));
				ledger.setQualityCost(Double.parseDouble(list.get(m).get("special").toString()));
				ledgerService.updateByCmpId(ledger);
				
				QualityCostAuxAccount auxAccount = new QualityCostAuxAccount();
				auxAccount.setCmpId(Integer.parseInt(list.get(m).get("cmpId").toString()));
				auxAccount.setQualityCost(Double.parseDouble(list.get(m).get("special").toString()));
				auxService.updateByCmpId(auxAccount);
			}

		} catch (Exception e) {
			logger.error("SyncSpecialTask error：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 辅助账的责任部门跟随产品线-组织架构对应关系而变动
	 */
	public void updateQTandUGdep() {
		List<QualityCostAuxAccount> auxDepInfos = auxService.listPrdLineDepInfos();
		List<Integer> prdLineIds = getPrdLineIds(auxDepInfos);
		List<PrdLineDepInfoDto> depInfos = tspService.getPrdLineDepInfos(prdLineIds);
		
		List<PrdLineDepInfoDto> unSameDepInfos = getUnSameDepInfos(auxDepInfos, depInfos);
		auxService.updateDepByPrdLineId(unSameDepInfos);
	}
	
	private List<Integer> getPrdLineIds(List<QualityCostAuxAccount> auxDepInfos) {
		List<Integer> prdLineIds = new ArrayList<Integer>();
		for (QualityCostAuxAccount aux : auxDepInfos) {
			prdLineIds.add(aux.getPrdLineId());
		}
		return prdLineIds;
	}
	
	private List<PrdLineDepInfoDto> getUnSameDepInfos(List<QualityCostAuxAccount> auxDepInfos, List<PrdLineDepInfoDto> depInfos) {
		List<PrdLineDepInfoDto> unSameDepInfos = new ArrayList<PrdLineDepInfoDto>();
		for (QualityCostAuxAccount aux : auxDepInfos) {
			for (PrdLineDepInfoDto dep : depInfos) {
				if (aux.getPrdLineId().equals(dep.getPrdLineId())) {
					if (!isSame(aux, dep)) {
						unSameDepInfos.add(dep);
					}
					continue;
				}
			}
		}
		return unSameDepInfos;
	}
	
	private boolean isSame(QualityCostAuxAccount aux, PrdLineDepInfoDto dep) {
		if (dep.getBusinessUnitId() > 0 && !aux.getFirstDepId().equals(dep.getBusinessUnitId())) {
			return false;
		}
		if (StringUtils.isNotBlank(dep.getBusinessUnitName()) && !aux.getFirstDepName().equals(dep.getBusinessUnitName())) {
			return false;
		}
		if (dep.getPrdDepId() > 0 && !aux.getTwoDepId().equals(dep.getPrdDepId())) {
			return false;
		}
		if (StringUtils.isNotBlank(dep.getPrdDepName()) && !aux.getTwoDepName().equals(dep.getPrdDepName())) {
			return false;
		}
		if (dep.getPrdTeamId() > 0 && !aux.getThreeDepId().equals(dep.getPrdTeamId())) {
			return false;
		}
		if (StringUtils.isNotBlank(dep.getPrdTeamName()) && !aux.getThreeDepName().equals(dep.getPrdTeamName())) {
			return false;
		}
		return true;
	}

	/**
	 * 更新综合满意度组织
	 */
	public void updateSatisfyDep() {
		
		try {
			
			List<String> list = satisfyService.getAllDep(); //每日新的产品线组织
			
			List<String> oldList = targetService.getAllOldDep(); //目标值旧数据
			
			for(int i =0 ;i<list.size();i++){
				
				String newName = list.get(i);
				for(int j =0 ;j< oldList.size();j++ ){
					
					String noName = oldList.get(j);
		    		if(newName.equals(noName)){
		    			
		    			list.remove(i);
		    			i--;
		    			break;
		    		}
					
				}
				
			}
			List<TargetConfig> targetList =new ArrayList<TargetConfig>();
			for(String depName : list){
				
				TargetConfig target =new TargetConfig();
				String []depNames = depName.split("-");
				if(depNames.length==1){
	         		
					target.setBusinessUnit(depNames[0]);
					target.setPrdDep("");
					target.setPrdTeam("");
	         	}
				if(depNames.length==2){
				      
					target.setBusinessUnit(depNames[0]);
					target.setPrdDep(depNames[1]);
					target.setPrdTeam("");
				 }
				if(depNames.length==3){
					
					target.setBusinessUnit(depNames[0]);
					target.setPrdDep(depNames[1]);
					target.setPrdTeam(depNames[2]);
		         }
				target.setYear(DateUtil.getYear(new Date()));
				targetList.add(target);
			}
			
			targetService.addBatch(targetList);
			
		} catch (Exception e) {
			
			logger.error("updateSatisfyDep error：" + e.getMessage());
			e.printStackTrace();
		
		}
	}
	
}
