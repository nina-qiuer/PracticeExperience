package com.tuniu.qms.qc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.access.client.OaClient;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.service.RtxRemindService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dao.InnerPunishBasisMapper;
import com.tuniu.qms.qc.dao.InnerPunishBillMapper;
import com.tuniu.qms.qc.dao.InnerQcBillMapper;
import com.tuniu.qms.qc.dao.InnerRespBillMapper;
import com.tuniu.qms.qc.dao.QcTypeMapper;
import com.tuniu.qms.qc.dao.QualityProblemMapper;
import com.tuniu.qms.qc.dao.UpLoadAttachMapper;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.InnerQcBillService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.QcPunishSegmentTaskService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class InnerQcBillServiceImpl implements InnerQcBillService {
	
	@Autowired
	private InnerQcBillMapper mapper;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private RtxRemindService rtxRemindService;
	
	@Autowired
	private QualityProblemMapper qpMapper;
	
	@Autowired
	private InnerRespBillMapper innerMapper;
	
	@Autowired
	private UpLoadAttachMapper upMapper;
	
	@Autowired
	private InnerPunishBillMapper innerPunishMapper;
	
	@Autowired
	private InnerPunishBasisMapper innerBasisMapper;
	
    @Autowired
    private MailTaskService mailTaskService;
    
    @Autowired
    private InnerPunishBillService innerPunishService;
    
    @Autowired
    private QualityProblemService qualityProblemService;
    
	@Autowired
	private OperationLogService operLogService;
	
    @Autowired
    private QcTypeMapper qcTypeMapper;
    
    @Autowired
	private QcPunishSegmentTaskService qcPunishSegmentTaskService;
    
    @Autowired
    private OuterPunishBillService outerPunishService;
    
	@Override
	public void add(QcBill qc) {
		mapper.add(qc);
		
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QcBill qc) {
		mapper.update(qc);
	}

	@Override
	public QcBill get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<QcBill> list(QcBillDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcBillDto dto) {
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
	}


	@Override
	public void updateQcPerson(List<Integer> qcBillIds, String assignTo,User operUser) {
		QcBill qcBill = null;
		User user = userService.getUserByRealName(assignTo);
		String oldQcPerson = "";
		//1.遍历待分配质检单列表
		for (Integer qcBillId : qcBillIds) {
			qcBill = this.get(qcBillId);
			oldQcPerson = qcBill.getQcPerson();
			//1.1分配质检员
			qcBill.setQcPerson(assignTo);
			qcBill.setQcPersonId(user.getId());
			//1.2变更质检状态
			qcBill.setState(3);
			//1.3设置分配时间
			qcBill.setDistribTime(new Date());
			this.update(qcBill);
			
			operLogService.addQcOperationLog(qcBill.getId(), operUser, "分配质检单", "质检单："+ qcBill.getId()+"，分配给："+qcBill.getQcPerson());
			
			if(operUser.getRole().getType()==1){//基础员工
				// 发送RTX提醒给经理
				User manager = OaClient.getReporter(oldQcPerson);
				if(!StringUtils.isEmpty(manager.getRealName())){
					RtxRemind  rtxRemind = new RtxRemind();
					rtxRemind.setUid(manager.getId().toString());
					rtxRemind.setTitle("质检单工作交接["+qcBill.getId()+"]");
					rtxRemind.setContent(oldQcPerson+"交接给"+assignTo);
					rtxRemind.setSendTime(new Date());
					rtxRemind.setAddPerson(oldQcPerson);
					rtxRemindService.add(rtxRemind);
				}
			}
		}
		
	}


	
	public void back2Processing(Integer id) {
		QcBill qcBill = this.get(id);
		qcBill.setState(3);
		qcBill.setFinishTime(null);
		this.update(qcBill);
	}

	/**
	 * 退回质检单
	 * @param qcBill
	 * @return
	 */
	public void returnQcBill(QcBill qcBill,User user) {
			
			List<QualityProblem> qpList =  qpMapper.getQpByQcId(qcBill.getId());
			this.update(qcBill);//更新质检单状态
			for(QualityProblem qp :qpList){
			
				if(qp!=null&&qp.getId()>0){
			
					innerMapper.deleteInnerRespBill(qp.getId());//删除质量问题单对应的内部责任单
					upMapper.deleteAttach(qp.getId());//删除质量问题对应的附件
					qpMapper.delete(qp.getId());//删除质量问题单
				}  
			
			}
			
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("qcId", qcBill.getId());
			List<Integer> innerList = innerPunishMapper.getByQcId(map);
			map.put("innerList", innerList);
			if(innerList.size()>0){
				
				innerBasisMapper.deletePunishByIpb(map);//删除内部处罚单对应的处罚依据
			}
			innerPunishMapper.deleteInnerPunishBill(qcBill.getId());//删除内部处罚单
			
			operLogService.addQcOperationLog(qcBill.getId(), user, "质检已退回", "质检单："+qcBill.getId()+"，已退回");
			
			//发送rtx
			RtxRemind  rtxRemind = new RtxRemind();
			rtxRemind.setUid(qcBill.getAddPersonId().toString());
			rtxRemind.setTitle("质检单["+qcBill.getId()+"]已退回");
			rtxRemind.setContent("质检单退回给"+qcBill.getAddPerson());
			rtxRemind.setSendTime(new Date());
			rtxRemind.setAddPerson(user.getRealName());
			rtxRemindService.add(rtxRemind);
			
	}

	@Override
	public void addQcRemark(QcBill qc) {
		mapper.addQcRemark(qc);
	}


	@Override
	public void listNormalQcJob(QcBillDto dto) {
		dto.setDataList(mapper.listNormalQcJob(dto));
	}
	

	@Override
	public QcBill getQcBill(QcBillDto dto){
	
		return mapper.getQcBill(dto);
	}

    @Override
    public void updateInnerQcReportEmail(QcBill qcBill, User user) {
	    qcBill.setState(QcConstant.QC_BILL_STATE_FINISH);
	    qcBill.setFinishTime(new Date());
       
	    this.update(qcBill);
        //添加处罚单数据添加切片任务
        qcPunishSegmentTaskService.builTask(qcBill.getId(), 1);
        
        operLogService.addQcOperationLog(qcBill.getId(), user, "质检已完成", "质检单："+qcBill.getId()+"，已完成");
        
        addMailTask(qcBill, user);
    }
    
	private void addMailTask(QcBill qcBill, User user) {
    	MailTaskDto mailTaskDto = new MailTaskDto();
	    
    	mailTaskDto.setTemplateName("innerQcReport.ftl");
	    mailTaskDto.setSubject(qcBill.getSubject());
	    mailTaskDto.setReAddrs(qcBill.getReAddrs().split(";"));
	    mailTaskDto.setCcAddrs(qcBill.getCcAddrs().split(";"));
	    mailTaskDto.setDataMap(getQcReportContentDataMap(qcBill.getId()));
	    mailTaskDto.setAddPersonRoleId(user.getRole().getId());
	    mailTaskDto.setAddPerson(user.getRealName());
	    
	    mailTaskService.addTask(mailTaskDto);
	}

	private Map<String,Object> getQcReportContentDataMap(Integer id) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        QcBill qcBill = get(id);
        dataMap.put("qcBill", qcBill);
        dataMap.put("qcPerson", qcBill.getQcPerson());
       
        List<QualityProblem> qualityProblemlist = qualityProblemService.listQpDetail(id,Constant.QC_INNER);//质量问题单
        dataMap.put("qualityProblemlist", qualityProblemlist);
     
        List<InnerPunishBill> innerPunishList = innerPunishService.listInnerPunish(id);//内部处罚单
        dataMap.put("innerPunishList", innerPunishList);
        dataMap.put("dateTime", DateUtil.formatAsDefaultDate(new Date()));
        
        List<OuterPunishBill> outerPunishList = outerPunishService.listOuterPunish(id);//外部处罚单
        dataMap.put("outerPunishList", outerPunishList);
        
        return dataMap;
    }

	@Override
	public int getOrderIsExist(Map<String, Object> map) {
		return mapper.getOrderIsExist(map);
	}

	@Override
	public List<Integer> listQcId() {
		return mapper.listQcId();
	}

	@Override
	public List<Integer> listQcAndQuality() {
		return mapper.listQcAndQuality();
	}

	/**
	 * 清除无用数据
	 */
	public void deleteUnUseQcAndAttach(Map<String, Object> map) {
		mapper.deleteAttachByQcId(map);
		mapper.deleteQc(map);
	}

	@Override
	public void addInnerQc(QcBill qc) {
	
		qc.setQcAffairSummary("点评修改申请");
		
		QcType qcType = qcTypeMapper.getTypeName(QcConstant.REVIEW_NAME);
		if(null!=qcType){
			
			qc.setQcTypeId(qcType.getId());
		}
		qc.setGroupFlag(4); 
		qc.setState(QcConstant.QC_BILL_STATE_WAIT);
		qc.setUserFlag(QcConstant.USER_FLAG_TRUE);
		qc.setDelFlag(Constant.NO);
		qc.setSubmitTime(new Date());
		mapper.add(qc);
		
	}

	@Override
	public void batchAddInQcBill(List<QcBill> qcBillList) {
		mapper.batchAddInQcBill(qcBillList);
	}

	@Override
	public String getQcPersonByOrderId(Integer ordId) {
		return mapper.getQcPersonByOrderId(ordId);
	}

}
