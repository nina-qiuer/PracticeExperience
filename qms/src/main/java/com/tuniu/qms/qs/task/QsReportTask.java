package com.tuniu.qms.qs.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.model.SendMailConfig;
import com.tuniu.qms.qc.service.SendMailConfigService;
import com.tuniu.qms.qs.dto.SubstdOrderSnapshotDto;
import com.tuniu.qms.qs.dto.SubstdProductSnapshotDto;
import com.tuniu.qms.qs.model.SubstdOrderSnapshot;
import com.tuniu.qms.qs.model.SubstdProductSnapshot;
import com.tuniu.qms.qs.service.SubstdOrderSnapshotService;
import com.tuniu.qms.qs.service.SubstdProductSnapshotService;

public class QsReportTask {
	
	@Autowired
	private SubstdProductSnapshotService productService;
	
	@Autowired
	private SubstdOrderSnapshotService orderService;
	
	@Autowired
	private MailTaskService mailTaskService;
	
	@Autowired
	private SendMailConfigService maillService;
	
	public void sendQsReport() {
		String yesterday = DateUtil.getSqlYesterday().toString();
		List<String> bus = getBusinessUnits(yesterday);
		
		MailTaskDto mt = new MailTaskDto();
		mt.setReAddrs(getReAddrs());
		mt.setCcAddrs(new String[]{});
		mt.setTemplateName("qsReport.ftl");
		mt.setAddPerson("Robot");
		
		SubstdProductSnapshotDto prdDto = new SubstdProductSnapshotDto();
		prdDto.setStatisticDate(yesterday);
		
		SubstdOrderSnapshotDto ordDto = new SubstdOrderSnapshotDto();
		ordDto.setStatisticDate(yesterday);
		for (String bu : bus) {
			prdDto.setBusinessUnit(bu);
			ordDto.setBusinessUnit(bu);
			
			Map<String, Object> dataMap = getDataMap(prdDto, ordDto);
			if (null != dataMap) {
				mt.setSubject(getSubject(bu, yesterday));
				mt.setDataMap(dataMap);
				mailTaskService.addTask(mt);
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String[] getReAddrs() {
		
		SendMailConfig config = maillService.getByType("质监报告发送人");
		String reEmails[] = null;
		if(null!=config && null!=config.getSendAddrs()){
			
			reEmails = config.getSendAddrs().split(";");
		}
		
		return reEmails;
	}
	
	private Map<String, Object> getDataMap(SubstdProductSnapshotDto prdDto, SubstdOrderSnapshotDto ordDto) {
		prdDto.setSubstdKey("salesPeriodLength");
		List<SubstdProductSnapshot> splList = productService.list(prdDto); // 销售期长不合格产品列表
		
		prdDto.setSubstdKey("groupRichness");
		List<SubstdProductSnapshot> grList = productService.list(prdDto); // 团期丰富度不合格产品列表
		
		prdDto.setSubstdKey("aloneGroup");
		List<SubstdProductSnapshot> agList = productService.list(prdDto); // 非独立成团牛人专线列表
		
		List<SubstdOrderSnapshot> ntList = orderService.list(ordDto); // 出团通知超时订单列表
		
		Map<String, Object> dataMap = null;
		if ((null != splList && !splList.isEmpty()) || 
				(null != grList && !grList.isEmpty()) || 
				(null != agList && !agList.isEmpty()) || 
				(null != ntList && !ntList.isEmpty())) {
			dataMap = new HashMap<String, Object>();
			dataMap.put("splList", splList);
			dataMap.put("grList", grList);
			dataMap.put("agList", agList);
			dataMap.put("ntList", ntList);
		}
		return dataMap;
	}
	
	private String getSubject(String bu, String yesterday) {
		StringBuffer sb = new StringBuffer();
		sb.append("【质监报告】——牛人专线监控——").append(bu).append("——").append(yesterday);
		return sb.toString();
	}
	
	private List<String> getBusinessUnits(String yesterday) {
		SubstdProductSnapshotDto prdDto = new SubstdProductSnapshotDto();
		prdDto.setStatisticDate(yesterday);
		List<String> bus = productService.getBusinessUnits(prdDto);
		
		SubstdOrderSnapshotDto ordDto = new SubstdOrderSnapshotDto();
		ordDto.setStatisticDate(yesterday);
		bus.addAll(orderService.getBusinessUnits(ordDto));
		return new ArrayList<String>(new HashSet<String>(bus));
	}
	
}
