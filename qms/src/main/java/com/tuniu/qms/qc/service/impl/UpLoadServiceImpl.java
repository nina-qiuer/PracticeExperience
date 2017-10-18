package com.tuniu.qms.qc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.Job;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.ExcelImportUtil;
import com.tuniu.qms.common.util.UpLoadUtil;
import com.tuniu.qms.qc.dao.UpLoadAttachMapper;
import com.tuniu.qms.qc.dto.UpLoadAttachDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.InnerRespBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.RespPunishRelation;
import com.tuniu.qms.qc.model.UpLoadAttach;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.InnerRespBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.RespPunishRelationService;
import com.tuniu.qms.qc.service.UpLoadService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class UpLoadServiceImpl implements UpLoadService {
	
	@Autowired
	private UpLoadAttachMapper mapper;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private InnerPunishBillService innerPunishBillService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QcBillService qcBillService;
	
	@Autowired
	private QcTypeService qcTypeService;
	
	@Autowired
	private InnerRespBillService innerRespBillService;
	
	@Autowired
	private RespPunishRelationService respPunishRelationService;
	
	@Override
	public void add(UpLoadAttach obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(UpLoadAttach obj) {
		
	}

	@Override
	public UpLoadAttach get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<UpLoadAttach> list(UpLoadAttachDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(UpLoadAttachDto dto) {
		
	}
	
	@Override
	public void saveScoreRecord(MultipartFile mf, User user) throws Exception {
		Workbook book = ExcelImportUtil.createWorkBook(UpLoadUtil.mfToFile(mf), mf.getOriginalFilename());
		Sheet sheet = book.getSheetAt(0);
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			
			Integer qcId = addOperateQcBill(row, user);
			
			InnerPunishBill innerPunishBill = addInnerPunishBill(qcId, row, user);
			
			Integer innerRespId = addInnerRespBill(innerPunishBill, row);
			
			addRespPunishRelation(innerRespId, innerPunishBill.getId());
		}
	}

	/**
	 * 添加责任处罚关联关系
	 * @param innerRespId
	 * @param punishId
	 */
	private void addRespPunishRelation(Integer innerRespId, Integer punishId) {
		RespPunishRelation relation = new RespPunishRelation();
		relation.setRespId(innerRespId);
		relation.setRespType(QcConstant.INNER_RESP_PUN_FALG);
		relation.setPunishId(punishId);
		relation.setPunishType(QcConstant.INNER_RESP_PUN_FALG);
		
		respPunishRelationService.add(relation);
	}
	
	/**
	 * 添加内部责任单
	 * @param innerPunishBill
	 * @param row
	 * @return
	 */
	private Integer addInnerRespBill(InnerPunishBill innerPunishBill, Row row) {
		InnerRespBill innerRespBill = new InnerRespBill();
		
		if(!("").equals(row.getCell(14).getStringCellValue().trim())){
			
			User managerUser = userService.getUserByRealName(row.getCell(14).getStringCellValue());
			if(null != managerUser){
				innerRespBill.setRespManagerId(managerUser.getId());
				innerRespBill.setRespManagerName(managerUser.getRealName());
				innerRespBill.setManagerJobId(managerUser.getJobId());
			}
		}
		
		if(!("").equals(row.getCell(15).getStringCellValue().trim())){
			User generalUser = userService.getUserByRealName(row.getCell(15).getStringCellValue());
			
			if(null != generalUser){
				innerRespBill.setRespGeneralId(generalUser.getId());
				innerRespBill.setRespGeneralName(generalUser.getRealName());
				innerRespBill.setGeneralJobId(generalUser.getJobId());
			}
		}
		
		innerRespBill.setRespPersonId(innerPunishBill.getPunishPersonId());
		innerRespBill.setRespPersonName(innerPunishBill.getPunishPersonName());
		innerRespBill.setDepId(innerPunishBill.getDepId());
		innerRespBill.setJobId(innerPunishBill.getJobId());
		innerRespBill.setRespReason("");
		
		innerRespBillService.add(innerRespBill);
		return innerRespBill.getId();
	}
	
	/**
	 * 添加内部处罚单
	 * @param qcId
	 * @param row
	 * @param user
	 * @return
	 */
	private InnerPunishBill addInnerPunishBill(Integer qcId, Row row, User user) {
		InnerPunishBill innerPunish = new InnerPunishBill();
		
		innerPunish.setQcId(qcId);
		innerPunish.setAddPerson(user.getRealName());
		innerPunish.setDelFlag(Constant.NO);
		innerPunish.setRelatedFlag(Constant.YES);

		String businessUnit = row.getCell(5).getStringCellValue();
		String dep = row.getCell(6).getStringCellValue();
		String team = row.getCell(7).getStringCellValue();

		String depName = "";
		if (!("").equals(team.trim()) && !("").equals(businessUnit.trim()) && !("").equals(dep.trim())) {
			depName = businessUnit + "-" + dep + "-" + team;
		}
		if (!("").equals(businessUnit.trim()) && !("").equals(dep.trim()) && ("").equals(team.trim())) {
			depName = businessUnit + "-" + dep;
		}
		if (!("").equals(businessUnit.trim()) && ("").equals(dep.trim()) && ("").equals(team.trim())) {
			depName = businessUnit;
		}

		Department depart = depService.getDepByFullName(depName);
		if (null != depart) {
			innerPunish.setDepId(depart.getId());
		}
		
		User innerUser = getUserDetail(row.getCell(9).getStringCellValue(), row.getCell(8).getStringCellValue());
		if(null != innerUser){
			innerPunish.setPunishPersonId(innerUser.getId());
			innerPunish.setPunishPersonName(innerUser.getRealName());
			innerPunish.setJobId(innerUser.getJobId());
		}
		
		innerPunish.setScorePunish((int) row.getCell(10).getNumericCellValue());
		innerPunish.setEconomicPunish(row.getCell(11).getNumericCellValue());
		innerPunish.setPunishReason(row.getCell(12).getStringCellValue());
		
		innerPunishBillService.addPunish(innerPunish);
		
		return innerPunish;
	}
	
	private User getUserDetail(String userName, String jobName) {
		User user = null;
		
		if(!("").equals(userName.trim())){
			
			user = userService.getUserByRealName(userName);
			if(null != user && !("").equals(jobName.trim())){
				
				Job job = jobService.getJobByName(jobName);
				if(null != job){
					user.setJobId(job.getId());
				}
			}
		}
		
		return user;
	}
	
	/**
	 * 添加运营质检单
	 * @param row
	 * @param user
	 * @return
	 */
	private Integer addOperateQcBill(Row row, User user) {
		QcBill qc = new QcBill();
		
		qc.setQcAffairSummary("运营质检处罚");
		qc.setGroupFlag(QcConstant.QC_OPERATE);
		qc.setState(QcConstant.QC_BILL_STATE_FINISH);
		qc.setUserFlag(QcConstant.USER_FLAG_TRUE);
		qc.setDelFlag(Constant.NO);
		qc.setAddPerson(user.getRealName());
		qc.setAddPersonId(user.getId());
		qc.setFinishTime(new Date(row.getCell(1).getDateCellValue().getTime()));
		qc.setOrdId((int) row.getCell(2).getNumericCellValue());
		qc.setPrdId((int) row.getCell(3).getNumericCellValue());
		
		QcType qcType = qcTypeService.getTypeByFullName(row.getCell(4).getStringCellValue());
		if (null != qcType) {
			qc.setQcTypeId(qcType.getId());
		}
		
		qc.setQcPerson(row.getCell(13).getStringCellValue());
		User qcUser = userService.getUserByRealName(qc.getQcPerson());
		if (null != qcUser) {
			qc.setQcPersonId(qcUser.getId());
		}

		qcBillService.addQcBill(qc);
		
		return qc.getId();
	}
}
