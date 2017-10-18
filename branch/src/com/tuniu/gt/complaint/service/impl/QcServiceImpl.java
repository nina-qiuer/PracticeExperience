package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintDao;
import com.tuniu.gt.complaint.dao.impl.QcDao;
import com.tuniu.gt.complaint.dao.impl.QcQuestionDao;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.QcQuestionEntity;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.frm.service.system.IFestivalService;

/**
* @ClassName: QCServiceImpl
* @Description:qc接口实现
* @author: YUANYOULEI
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_service_impl-qc")
public class QcServiceImpl extends ServiceBaseImpl<QcDao> implements IQcService {
	
	// 法定假日service
	@Autowired
	@Qualifier("frm_service_system_impl-festival")
	private IFestivalService festivalService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-auto_assign")
	private IAutoAssignService autoAssignService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
	private ComplaintDao complaintDao;
	private QcQuestionDao questionDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-qc")
	public void setDao(QcDao dao) {
		this.dao = dao;
	}

	@Override
	public QcEntity find(int id) {
		QcEntity qc = this.dao.searchById(id);
		
		return qc;
	}
	
	@Override
	public List<Map<String, Object>> findNames(Map<String, Object> paramMap) {
		return this.dao.searchByName(paramMap);
	}
	
	@Override
	public int add(QcEntity qc) {
		return this.dao.insert(qc);
	}

	@Override
	public void delete(int id) {
		QcEntity qc = this.dao.get(id);
		
		// Set QC delete flag, to indicate entity deleted.
		if (null != qc) {
			qc.setDelFlag(QcEntity.DELETE_FLAG);
			update(qc);
		}		
	}

	@Override
	public List<QcEntity> search(Map<String, Object> paramMap) {

		List<QcEntity> qcList = new ArrayList<QcEntity>();
		qcList = this.dao.search(paramMap);
		return qcList;
	}

	public ComplaintDao getComplaintDao() {
		return complaintDao;
	}

	// complaintDao setter 方法
	@Autowired
	@Qualifier("complaint_dao_impl-complaint")
	public void setComplaintDao(ComplaintDao complaintDao) {
		this.complaintDao = complaintDao;
	}

	public QcQuestionDao getQuestionDao() {
		return questionDao;
	}

	// questionDao setter 方法
	@Autowired
	@Qualifier("complaint_dao_impl-qc_question")
	public void setQuestionDao(QcQuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	@Override
	public List<QcQuestionEntity> getQuestionList(int id) {
		List<QcQuestionEntity> questions = new ArrayList<QcQuestionEntity>();
		questions = this.questionDao.getQuestionListByReportId(id);
		return questions;
	}
	
	public List<Map<String, Object>> searchMap(Map<String, Object> paramMap) {
		return dao.searchMap(paramMap);
	}

	public void updateByOrderId(QcEntity updateOrderId){
		this.dao.updateByOrderId(updateOrderId);
	}

	@Override
	public void updateByComplaintId(QcEntity qcEntity) {
		this.dao.updateByComplaintId(qcEntity);
	}

	@Override
	public boolean isTimeout(QcEntity qcEnt) {
		boolean flag = false;
		ComplaintEntity cmpEnt = qcEnt.getComplaint();
		if (cmpEnt.getState() == 4 && qcEnt.getQcPerson() != 0) { // 投诉处理已完成且质检已分配
			Date bgnTime = new Date();
			if (qcEnt.getDistributionDate().after(cmpEnt.getFinishDate())) { // 质检分配时间和投诉处理完成时间比较，哪个大用哪个
				bgnTime = qcEnt.getDistributionDate();
			} else {
				bgnTime = cmpEnt.getFinishDate();
			}
			Date endTime = festivalService.getWorkDaysEndTime(3, bgnTime); // 查询三个工作日后的结束时间点
			
			Date destTime = new Date();
			if (2 == qcEnt.getStatus()) { // 如果质检未完成和当前时间比较，如果已经完成，和质检完成时间比较
				destTime = qcEnt.getFinishDate();
			}
			if (endTime.before(destTime)) { // 如果结束时间在三个工作日之前，说明已超时
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public void updateSpecialConsultationByIds(Map map) {
		this.dao.updateSpecialConsultationByIds(map);
		
	}

	@Override
	public void updateConsultationById(Map map) {
		this.dao.updateConsultationById(map);
		
	}

	@Override
	public void assignQcPerson(QcEntity qcEnt, ComplaintEntity compEnt) {
		AutoAssignEntity qcPerson = null;
		
		List<ComplaintReasonEntity> reasonList = compEnt.getReasonList();
		for (ComplaintReasonEntity reason : reasonList) {
			if ("点评".equals(reason.getType())) {
				AutoAssignEntity qcPerson1 = new AutoAssignEntity();
				qcPerson1.setUserId(3653);
				qcPerson1.setUserName("蒋鹂");
				AutoAssignEntity qcPerson2 = new AutoAssignEntity();
				qcPerson2.setUserId(8214);
				qcPerson2.setUserName("范冰");
				AutoAssignEntity qcPerson3 = new AutoAssignEntity();
				qcPerson3.setUserId(11244);//增加孙晴3 add by chenhaitao 20150821
				qcPerson3.setUserName("孙晴3");
				
				List<AutoAssignEntity> aaList = new ArrayList<AutoAssignEntity>();
				aaList.add(qcPerson1);
				aaList.add(qcPerson2);
				aaList.add(qcPerson3);
				qcPerson = autoAssignService.getDestAssignEnt(aaList);
				break;
			}
		}
		
		if (null == qcPerson) { // 质检自动分单，投诉分类一级类目为“点评”的分给蒋鹂
			qcPerson = autoAssignService.getQcPerson(compEnt);
		}
		
		if (null != qcPerson && null != qcPerson.getUserId()) {
			int personId = qcPerson.getUserId();
			qcEnt.setQcPerson(personId);
			qcEnt.setQcPersonName(qcPerson.getUserName());
			qcEnt.setDistributionDate(new Date());
			qcEnt.setUpdateTime(new Date());
			qcEnt.setStatus(QcEntity.PROCESSING_STATE); // 分配后，转换为处理中状态
			/* 分单计数 */
			ComplaintUtil.recordUserNums(personId, ComplaintUtil.MEM_PRE_QC);
			ComplaintUtil.recordUserOrders(personId, String.valueOf(qcEnt.getOrderId()), ComplaintUtil.MEM_PRE_QC);
			ComplaintUtil.updateAssignTime(personId, ComplaintUtil.MEM_PRE_QC);
		}
	}

	@Override
	public List<Map<String, Object>> getExportData(Map<String, Object> paramMap) {
		return dao.getExportData(paramMap);
	}

	@Override
	public int getExportDataTotal(Map<String, Object> paramMap) {
		return dao.getExportDataTotal(paramMap);
	}
	
}