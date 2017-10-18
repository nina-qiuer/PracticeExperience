package com.tuniu.qms.common.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.common.dao.SyncyTaskMapper;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.dto.SyncyTaskDto;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.SyncyTask;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.SyncyTaskService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.qc.model.OuterRespBill;
import com.tuniu.qms.qc.model.SendMailConfig;
import com.tuniu.qms.qc.service.OuterRespBillService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.service.SendMailConfigService;

@Service
public class SyncyTaskServiceImpl implements SyncyTaskService {
	
	private final static Logger logger = LoggerFactory.getLogger(SyncyTaskServiceImpl.class);

	@Autowired
	private SyncyTaskMapper syncyTaskMapper;
	
	@Autowired
	private MailTaskService mailTaskService;
	
	@Autowired
	private OuterRespBillService outerRespBillService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QualityProblemTypeService qualityProblemTypeService;
	
	@Autowired
	private SendMailConfigService sendMailConfigService;
	
	@Override
	public void add(SyncyTask obj) {
		syncyTaskMapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		syncyTaskMapper.delete(id);
	}

	@Override
	public void update(SyncyTask obj) {
		syncyTaskMapper.update(obj);
	}

	@Override
	public SyncyTask get(Integer id) {
		return null;
	}

	@Override
	public List<SyncyTask> list(SyncyTaskDto dto) {
		return null;
	}

	@Override
	public void loadPage(SyncyTaskDto dto) {
	}

	@Override
	public void builTask(Integer dataId, int taskType) {
		SyncyTask task = new SyncyTask();
		
		task.setDataId(dataId);
		task.setTaskType(taskType);
		
		this.add(task);
	}

	@Override
	public void createSupplierRespMonitor() {
		List<SyncyTask> list = syncyTaskMapper.getTasksWithCmpFinish();
		
		for(SyncyTask task : list){
			try{
				if((BigDecimal.ZERO).compareTo(task.getSupplierAmount()) >= 0){
					buildMoitorMail(task.getDataId());
				}
				
				this.delete(task.getId());
			}catch(Exception e){
				logger.error("供应商责任赔款查漏任务失败，投诉单号： " + task.getDataId(), e, e.getMessage());
				
				task.setFailTimes(task.getFailTimes() + 1);
				this.update(task);
			}
		}
	}

	/**
	 * 创建监控邮件
	 * @param dataId
	 */
	private void buildMoitorMail(Integer dataId) {
		OuterRespBill outerRespBill = outerRespBillService.getRespInfoByComplaintId(dataId);
		
		ComplaintBill complaintBill = CmpClient.getComplaintInfo(dataId);
		
		if(null != outerRespBill && null != complaintBill && complaintBill.getId() > 0 && complaintBill.getClaimAmount() <= 0.001){
			MailTaskDto mailTaskDto = new MailTaskDto();
	        
	        mailTaskDto.setTemplateName("supplierRespMonitor.ftl");
	        mailTaskDto.setSubject("供应商责任赔款查漏提醒");
	        mailTaskDto.setReAddrs(getReEmails(outerRespBill.getImpPersonId()));
	        mailTaskDto.setCcAddrs(getCcEmails(complaintBill.getDealPersonId()));
	        mailTaskDto.setDataMap(getDataMap(outerRespBill, complaintBill));
	        
	        mailTaskService.addTask(mailTaskDto);
		}
		
	}
	
	private Map<String, Object> getDataMap(OuterRespBill outerRespBill,	ComplaintBill complaintBill) {
		String qualityProblemName = qualityProblemTypeService.getQpFullNameById(outerRespBill.getQualityPrombleTypeId(), 
				qualityProblemTypeService.getQpTypeDataCache(Constant.QC_COMPLAINT));
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("qcId", outerRespBill.getQcId());
		map.put("agencyName", outerRespBill.getAgencyName());
		map.put("qualityProblemName", qualityProblemName);
		map.put("cmpId", complaintBill.getId());
		map.put("ordId", complaintBill.getOrdId());
		map.put("prdId", complaintBill.getRouteId());
		map.put("prdName", complaintBill.getRouteName());
		map.put("shareAmount", complaintBill.getShareAmount());
		
		return map;
	}

	/**
	 * 收件人  外部责任单改进人、改进人上级
	 * @param userId 外部责任单改进人id
	 * @return
	 */
	private String[] getReEmails(Integer userId) {
		StringBuffer sb = new StringBuffer();
		
		User user = userService.get(userId);
		if(null != user){
			sb.append(user.getEmail()).append(";");
			
			User manageUser =  OaClient.getReporter(user.getRealName());
			if(null != manageUser && null != manageUser.getUserName()){
				sb.append(manageUser.getUserName()).append("@tuniu.com").append(";");
			}
		}
		
		return sb.toString().split(";");
	}
	
	/**
	 * 抄送人   处理人、处理人经理（上级）、处理人总监（上上级）、邮件配置人
	 * @param dealPersonId  投诉单处理人id
	 * @return
	 */
	private String[] getCcEmails(Integer dealPersonId) {
		StringBuffer sb = new StringBuffer();
		
		User user = userService.get(dealPersonId);
		if(null != user){
			sb.append(user.getEmail()).append(";");
			
			User manageUser =  OaClient.getReporter(user.getRealName());
			if(null != manageUser && null != manageUser.getUserName()){
				sb.append(manageUser.getUserName()).append("@tuniu.com").append(";");
				
				User generalUser =  OaClient.getReporter(manageUser.getRealName());
				if(null != generalUser && null != generalUser.getUserName()){
					sb.append(generalUser.getUserName()).append("@tuniu.com").append(";");
				}
			}
		}
		
		SendMailConfig config = sendMailConfigService.getByType("供应商责任赔款查漏抄送人");
		
		if(null != config && null != config.getSendAddrs()){
			sb.append(config.getSendAddrs());
		}
		
		return sb.toString().split(";");
	}

}
