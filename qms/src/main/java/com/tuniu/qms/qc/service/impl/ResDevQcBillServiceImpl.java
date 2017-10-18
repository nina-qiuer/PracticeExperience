package com.tuniu.qms.qc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dao.DevFaultBillMapper;
import com.tuniu.qms.qc.dao.DevRespBillMapper;
import com.tuniu.qms.qc.dao.DevUpLoadAttachMapper;
import com.tuniu.qms.qc.dao.InnerPunishBasisMapper;
import com.tuniu.qms.qc.dao.InnerPunishBillMapper;
import com.tuniu.qms.qc.dao.JiraBillMapper;
import com.tuniu.qms.qc.dao.QcBillRelationMapper;
import com.tuniu.qms.qc.dao.ResDevQcBillMapper;
import com.tuniu.qms.qc.dto.InnerPunishBillDto;
import com.tuniu.qms.qc.dto.JiraBillDto;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QcBillRelationDto;
import com.tuniu.qms.qc.model.DevFaultBill;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.JiraBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcInfluenceSystem;
import com.tuniu.qms.qc.model.ResDevExportBill;
import com.tuniu.qms.qc.service.DevFaultBillService;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.JiraRelationService;
import com.tuniu.qms.qc.service.ResDevQcBillService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class ResDevQcBillServiceImpl implements ResDevQcBillService{

	@Autowired
	private ResDevQcBillMapper mapper;
	
	@Autowired
	private DevFaultBillMapper devMapper;
	
	@Autowired
	private DevRespBillMapper respMapper;
	
	@Autowired
	private DevUpLoadAttachMapper upMapper;
	
	@Autowired
	private JiraBillMapper jiraMapper;
	
	@Autowired
	private InnerPunishBasisMapper innerBasisMapper;
	
	@Autowired
	private InnerPunishBillService  innerPunishService;
	
	@Autowired
	private InnerPunishBillMapper innerPunishMapper;
	
	@Autowired
    private DevFaultBillService devFaultService;
	
    @Autowired
    private JiraRelationService jiraService;
    
    @Autowired
    private MailTaskService mailTaskService;
    
    @Autowired
    private QcBillRelationMapper relationMapper;
    
	@Override
	public void add(QcBill obj) {
		
		Date nowDate = new Date();
		Date expirationTime  = getExpirationDate(nowDate,6);
		obj.setDistribTime(nowDate);
		obj.setQcPeriodBgnTime(nowDate);
		obj.setQcPeriodEndTime(expirationTime);
		   
		mapper.add(obj);
	    
		if(obj.getJiraIds()!=null && !"".equals(obj.getJiraIds())){
		    List<String> list =new ArrayList<String>();
			String[] jiraIds = obj.getJiraIds().split(",");
		    for(int i = 0;i<jiraIds.length;i++){
					
					list.add(jiraIds[i]);
				}	
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("jiraIds",list);
			map.put("flag", QcConstant.JIRA_CONNECTED);
			map.put("qcId", obj.getId());
			jiraMapper.updateJiraRelation(map);
	   }
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QcBill obj) {
		mapper.update(obj);
	}

	@Override
	public QcBill get(Integer id) {
		
		QcBill qcBill =  mapper.get(id);
		List<QcInfluenceSystem> list  = mapper.getInfluenceSystem(id);
		qcBill.setInfluenceSystem(list);
		return qcBill;
	}

	@Override
	public List<QcBill> list(QcBillDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public List<QcBill> queryList(QcBillDto dto) {
		
		return mapper.queryList(dto);
	}
	@Override
	public void loadPage(QcBillDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
		
	}

	@Override
	public int getQcBillIsExist(Integer qcId) {
		
		return mapper.getQcBillIsExist(qcId);
	}

	/**
	 * 撤销研发质检单
	 */
	@Override
	public void deleteDevBill(QcBill qcBill) {

		//更新关联表状态
		QcBillRelationDto dto =new QcBillRelationDto();
		dto.setFlag(QcConstant.QC_CLOSED);//研发质检撤销 关闭关联单
		dto.setDevId(String.valueOf(qcBill.getId()));
		relationMapper.updateByCmpAndDev(dto);
		
		List<DevFaultBill>  faultList =  devMapper.getFaultByQcId(qcBill.getId());
		this.update(qcBill);//更新质检单状态
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("qcId", qcBill.getId());
		map.put("flag", QcConstant.JIRA_CLOSED);
		jiraMapper.updateJiraByQcId(map);
		for(DevFaultBill dev :faultList){
		
			    respMapper.deleteDevRespBill(dev.getId());//删除故障单对应的责任单 
				upMapper.deleteAttach(dev.getId());//删除故障单对应的附件
				devMapper.delete(dev.getId());//删除故障单
		
		}
		Map<String, Object> pMap =new HashMap<String, Object>();
		pMap.put("qcId", qcBill.getId());
		List<Integer> innerList = innerPunishMapper.getByQcId(pMap);
		map.put("innerList", innerList);
		if(innerList.size()>0){
		 innerBasisMapper.deletePunishByIpb(map);//删除内部处罚单对应的处罚依据
		}
		innerPunishMapper.deleteInnerPunishBill(qcBill.getId());//删除内部处罚单
	}

	@Override
	public void deleteInner(Integer qcId) {
		
		InnerPunishBillDto innerDto =new InnerPunishBillDto();
		innerDto.setQcId(qcId);
		innerDto.setDelFlag(QcConstant.DEL_FLAG_TRUE);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("qcId",qcId);
		map.put("delFlag", QcConstant.DEL_FLAG_TRUE);
		List<Integer> innerList = innerPunishMapper.getByQcId(map);
		map.put("innerList", innerList);
		if(innerList.size()>0){
	    	innerBasisMapper.deletePunishByIpb(map);//删除内部处罚单对应的处罚依据
		}
		innerPunishService.deleteUnUsePunish(innerDto);
		
	}

	
	public void updateQcReportEmail( QcBill qcBill,User user,String reEmails,String ccEmails, String subject){
		
		  qcBill.setState(4);
          qcBill.setFinishTime(new Date());
		
		  MailTaskDto mailTaskDto = new MailTaskDto();
          mailTaskDto.setTemplateName("devQcReport.ftl");
          mailTaskDto.setSubject(subject);
          mailTaskDto.setReAddrs(reEmails.split(";"));
          mailTaskDto.setCcAddrs(ccEmails.split(";"));
          mailTaskDto.setDataMap(getQcReportContentDataMap(qcBill, subject));
          mailTaskDto.setAddPersonRoleId(user.getRole().getId());
          mailTaskDto.setAddPerson(user.getRealName());
          mailTaskService.addTask(mailTaskDto);
         
          this.update(qcBill);
		
		
	}
	
	  private Map<String, Object> getQcReportContentDataMap(QcBill qcBill, String subject) {
		  
	        Map<String, Object> dataMap = new HashMap<String, Object>();
	        dataMap.put("subject", subject);
	        dataMap.put("qcBill", qcBill);
	        // 故障单
	        List<DevFaultBill> devFaultList = devFaultService.listDevFaultDetail(qcBill.getId());
	        dataMap.put("devFaultList", devFaultList);
	        //内部处罚单（研发质检只有内部处罚单）
	        List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(qcBill.getId());//内部处罚单
	        dataMap.put("innerPunishList", innerPunishList);
	        
	        //关联jira列表
	        JiraBillDto jiraDto =new JiraBillDto();
	        jiraDto.setQcId(qcBill.getId());
	        List<JiraBill>  jiraList = jiraService.listByQcId(jiraDto);
	        dataMap.put("jiraList", jiraList);
	       
	        // 质检员
	        dataMap.put("qcPerson", qcBill.getQcPerson());
	        // 日期
	        dataMap.put("dateTime", DateUtil.formatAsDefaultDate(new Date()));

	        //根据研发质检单号找到关联对应的投诉单号
	        List<Integer> complaintIdList = this.getComplainIdByDevId(qcBill.getId());
	        
	        List<ComplaintBill> complaintBillList = new ArrayList<ComplaintBill>();
	        if(complaintIdList != null && complaintIdList.size() > 0){
	        	//投诉信息
	        	ComplaintBill complaintBill = new ComplaintBill();
	        	for(Integer id : complaintIdList){
	        		complaintBill = CmpClient.getComplaintInfo(id);
	        		complaintBillList.add(complaintBill);
	        	}
	        }
	        
	        dataMap.put("complaintBillList", complaintBillList);
	        
	        return dataMap;
	 }
	  
	@Override
	public void deleteSystem(Integer id) {
		
		mapper.deleteSystem(id);
		
	}

	@Override
	public void addSystem(QcInfluenceSystem qcInfo) {
		
		mapper.addSystem(qcInfo);
		
	}

	@Override
	public void loadQueryPage(QcBillDto dto) {
		
		int totalRecords = mapper.queryCount(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.queryList(dto));
	}

	@Override
	public List<ResDevExportBill> exportList(QcBillDto dto) {

		return mapper.exportList(dto);
	}

	@Override
	public Integer exportCount(QcBillDto dto) {
		return mapper.count(dto);
	}

	@Override
	public int getInfluenceSystemCount(Integer qcId) {
		return mapper.getInfluenceSystemCount(qcId);
	}

	@Override
	public void addRelationDev(QcBill qcBill) {
		Date nowDate = new Date();
		Date expirationTime  = getExpirationDate(nowDate,6);
		qcBill.setDistribTime(nowDate);
		qcBill.setQcPeriodBgnTime(nowDate);
		qcBill.setQcPeriodEndTime(expirationTime);
	    mapper.add(qcBill);
	    if(qcBill.getRelationIds()!=null && !"".equals(qcBill.getRelationIds())){
		    List<String> list =new ArrayList<String>();
			String[] relationIds = qcBill.getRelationIds().split(",");
		    for(int i = 0;i<relationIds.length;i++){
					
					list.add(relationIds[i]);
				}	
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("relationIds",list);
			map.put("flag", QcConstant.QC_CONNECTED);
			map.put("devId", qcBill.getId());
			relationMapper.updateRelation(map);
	   }
		
	}

	 public static Date  getExpirationDate(Date beginTime,int days){
		 
		 Date endTime = new Date();
		 if(OaClient.getDayIsWork(beginTime)!=9){
		 
			 for (int i=0; i<=days; i++) {
					endTime = DateUtil.addSqlDates(beginTime, i);
		            if (OaClient.getDayIsWork(endTime)!=0) { // 如果是休息日，则往后推一天
		            	days++;
		            }
			  }
		 }else{
			 
			 endTime = DateUtil.addSqlDates(beginTime, 6);
		 }
		 return endTime;
	 }

	@Override
	public List<QcBill> getQcPeriodList() {
		return mapper.getQcPeriodList();
	}

	@Override
	public List<Integer> getComplainIdByDevId(Integer qcId) {
		return mapper.getComplainIdByDevId(qcId);
	}
}
