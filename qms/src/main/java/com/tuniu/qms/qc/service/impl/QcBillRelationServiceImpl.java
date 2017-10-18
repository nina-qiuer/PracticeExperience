package com.tuniu.qms.qc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.ComplaintBillMapper;
import com.tuniu.qms.common.dao.UserMapper;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.ComplaintBillService;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.service.RtxRemindService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.qc.dao.QcBillMapper;
import com.tuniu.qms.qc.dao.QcBillRelationMapper;
import com.tuniu.qms.qc.dao.ResDevQcBillMapper;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.dto.QcBillRelationDto;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcBillRelation;
import com.tuniu.qms.qc.model.QcPoint;
import com.tuniu.qms.qc.service.QcBillRelationService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class QcBillRelationServiceImpl implements QcBillRelationService{

	@Autowired
	private QcBillRelationMapper mapper;
	
	@Autowired
	private QcBillMapper qcMapper;
	
	@Autowired
	private QcBillService  qcService;
	
	@Autowired
	private ComplaintBillMapper  cmpMapper;
	
	@Autowired
	private RtxRemindService rtxRemindService;
	
	@Autowired
	private  ResDevQcBillMapper  devBillMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ComplaintBillService complaintBillService;
	
	@Autowired
	private OperationLogService operationLogService;
	
	private final static Logger logger = LoggerFactory.getLogger(QcBillRelationServiceImpl.class);

	
	@Override
	public void add(QcBillRelation obj) {
		
		mapper.add(obj);
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(QcBillRelation obj) {

		mapper.update(obj);
	}

	@Override
	public QcBillRelation get(Integer id) {
	
		return mapper.get(id);
	}

	@Override
	public List<QcBillRelation> list(QcBillRelationDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcBillRelationDto dto) {
			
			int totalRecords = mapper.count(dto);
			dto.setTotalRecords(totalRecords);
			dto.setDataList(this.list(dto));

		
	}

	/**
	 * 双方关联 /单关联
	 */
	public void saveCmpToDev(QcBillRelation bill) {
	
		try {
			String name ="";
			//插入关联表
			ComplaintBill cmpBill = cmpMapper.get(bill.getCmpId());
			QcBill qcBill = qcMapper.get(bill.getQcId());
			
			if(bill.getQcFlag()==2){ //变更为质检   投诉质检变更为已撤销 
				
				qcBill.setUpdatePerson(bill.getUpdatePerson());
				qcBill.setFinishTime(new Date());
				qcBill.setState(QcConstant.QC_BILL_STATE_CANCEL);
				qcBill.setCancelTime(new Date());
				qcService.deleteQcBill(qcBill);
				cmpBill.setQcFlag(2);//研发质检
				cmpMapper.update(cmpBill);
				name="研发质检";
			}else{
			
				cmpBill.setQcFlag(3);//双方质检
				cmpMapper.update(cmpBill);
				name="双方质检";
			}
			
			bill.setIndemnifyAmount(cmpBill.getIndemnifyAmount());
			bill.setClaimAmount(cmpBill.getClaimAmount());
			bill.setQcPerson(qcBill.getQcPerson());
			bill.setFlag(QcConstant.QC_NO_CONNECT);//待关联
			bill.setCmpTime(cmpBill.getAddTime());
			mapper.add(bill);
			
			addRtx(bill, name);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error("插入质检关联表失败"+e.getMessage());
			
		}
		
	}

	private void addRtx(QcBillRelation bill, String name) {
		//发送rtx提醒
		List<User> userList = userMapper.getUsersByDepId(QcConstant.DEV_QC_GROUP_DEPARTMENT_ID);//研发质检组
		String uids="";
		for(User user :userList){
			uids += user.getId()+",";
		}
		
		if (StringUtils.isNotEmpty(uids)) {
			
			uids = uids.substring(0, uids.length() - 1);
			RtxRemind  rtxRemind = new RtxRemind();
			rtxRemind.setUid(uids);
			rtxRemind.setTitle("投诉质检单["+bill.getQcId()+"]发起"+name);
			rtxRemind.setContent("投诉质检单["+bill.getQcId()+"]发起"+name+",投诉单号["+bill.getCmpId()+"]");
			rtxRemind.setSendTime(new Date());
			rtxRemind.setAddPerson(bill.getUpdatePerson());
			rtxRemindService.add(rtxRemind);
		}
	}

	/**
	 * 双方关联 /单关联
	 */
	public void saveDevToCmp(QcBillRelation bill) {
	
		String name ="";
		try {
			
			QcBill qcBill = devBillMapper.get(bill.getDevId());
			
			ComplaintBill cmpBill = cmpMapper.get(bill.getCmpId());
			
			//转成投诉质检 研发变更为已撤销 
			if(bill.getQcFlag()==1){ 
				
				QcBillRelationDto dto =new QcBillRelationDto();
				dto.setFlag(QcConstant.QC_CLOSED);//研发质检撤销 关闭关联单
				dto.setCmpId(String.valueOf(bill.getCmpId()));
				mapper.updateByCmpAndDev(dto);
				
				cmpBill.setQcFlag(1);//投诉质检
				cmpMapper.update(cmpBill);
				name="投诉质检";
				
			}else{
			
				cmpBill.setQcFlag(3);//双方之间
				cmpMapper.update(cmpBill);
				name="双方质检";
			}
			//投诉单对应的投诉质检单从撤销状态返回处理中状态
			QcBillDto dto =new QcBillDto();
			dto.setCmpId(cmpBill.getId());
			QcBill qBill = qcMapper.getQcBill(dto);
			if(null!=qBill){
				qcService.backToProcessing(qBill.getId());
			}
			
			List<User> userList = userMapper.getUsersByDepId(QcConstant.CMP_QC_GROUP_DEPARTMENT_ID);//投诉质检组
			String uids="";
			for(User user :userList){
				
				uids += user.getId()+",";
			}
			if (StringUtils.isNotEmpty(uids)) {
				
				uids = uids.substring(0, uids.length() - 1);
				RtxRemind  rtxRemind = new RtxRemind();
				rtxRemind.setUid(uids);
				rtxRemind.setTitle("研发质检单["+qcBill.getId()+"]发起"+name);
				rtxRemind.setContent("研发质检单["+qcBill.getId()+"]发起"+name+",投诉单号["+cmpBill.getId()+"]");
				rtxRemind.setSendTime(new Date());
				rtxRemind.setAddPerson(bill.getUpdatePerson());
				rtxRemindService.add(rtxRemind);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error("插入质检关联表失败"+e.getMessage());
			
		}
		
	}

	@Override
	public void updateRelation(Map<String, Object> map) {
		
		
		mapper.updateRelation(map);
	}

	@Override
	public List<QcBillRelation> listRelation(Map<String, Object> map) {

		return mapper.listRelation(map);
	}

	@Override
	public List<QcBillRelation> listByDevId(QcBillRelationDto dto) {
		return mapper.listByDevId(dto);
	}

	@Override
	public void updateByCmpAndDev(QcBillRelationDto dto) {
		
		mapper.updateByCmpAndDev(dto);
		
	}

	@Override
	public Integer getQcIdByDevIdAndCmpId(Integer devId, Integer cmpId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("devId", devId);
		map.put("cmpId", cmpId);
		
		return mapper.getQcIdByDevIdAndCmpId(map);
	}

	@Override
	public void backToCmpQcBill(QcBillRelationDto dto, HandlerResult result, User user) {
		
		QcBillRelation relationBill = this.get(new Integer(dto.getId()));
		if(!(relationBill != null && relationBill.getFlag() == 0)){//是否是待关联状态
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("返回失败，该关联单状态不为待关联！");
			return;
		}
		
		//查询投诉单，判断qcflag : 1：投诉质检      2：研发质检   3：双方质检
		ComplaintBill cmpBill = complaintBillService.get(relationBill.getCmpId());
		if(cmpBill == null){
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("返回失败，该关联单的投诉单不存在！");
			return;
		}
		//投诉单的质检类型改为投诉质检
		cmpBill.setQcFlag(1);
		complaintBillService.update(cmpBill);
				
		//该关联单状态为已返回
		relationBill.setFlag(QcConstant.QC_BACK);
		relationBill.setRemark(dto.getRemark());
		this.update(relationBill);
		
		if(Constant.ROBOT.equals(relationBill.getQcPerson())){
			QcBill qcBill = new QcBill();
			
			qcBill.setId(relationBill.getQcId());
			qcBill.setState(QcConstant.QC_BILL_STATE_WAIT);
			qcService.update(qcBill);
		}else{
			qcService.backToProcessing(relationBill.getQcId());
		}
		
		//给该投诉质检，记录操作日志
		addOperateLog(dto, relationBill, user);
		
		result.setRetCode(Constant.SYS_SUCCESS);
	}
	
	/**
	 * 添加操作日志
	 * @param dto
	 * @param relationBill
	 * @param user
	 */
	private void addOperateLog(QcBillRelationDto dto, QcBillRelation relationBill, User user) {
		OperationLog log = new OperationLog();
		
		StringBuilder str = new StringBuilder("投诉质检单：").append(relationBill.getQcId())
				.append(", 返回投诉质检. ").append("原因： ").append(dto.getRemark());
		
		log.setContent(str.toString());
		log.setDealPeople(user.getId());
		log.setDealPeopleName(user.getRealName());
		log.setDealDepart(user.getRole().getName());
		log.setQcId(relationBill.getQcId());
		log.setFlowName("返回投诉质检");
		
		operationLogService.add(log);
	}

	@Override
	public void addRelationBill(QcPoint qcPoint, Integer qcId) {
		QcBillRelation bill = new QcBillRelation();
		
		bill.setQcId(qcId);
		bill.setCmpId(qcPoint.getComplaintId());
		bill.setCmpTime(DateUtil.parseDateTime(qcPoint.getQcVo().getBuildDate()));
		bill.setFlag(QcConstant.QC_NO_CONNECT);//待关联
		bill.setQcPerson(Constant.ROBOT);
		
		this.add(bill);
		
		addRtx(bill, "研发质检");
	}
	
}
