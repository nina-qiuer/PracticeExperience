package com.tuniu.qms.qc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dao.InnerPunishBasisMapper;
import com.tuniu.qms.qc.dao.InnerPunishBillMapper;
import com.tuniu.qms.qc.dto.InnerPunishBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.InnerRespBill;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.RespPunishRelation;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.InnerRespBillService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.RespPunishRelationService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class InnerPunishBillServiceImpl implements InnerPunishBillService {

	//private final static Logger logger = LoggerFactory.getLogger(InnerPunishBillServiceImpl.class);
	
	@Autowired
	private InnerPunishBillMapper mapper;
	
	@Autowired
	private InnerPunishBasisMapper  basisMapper;
	
	@Autowired
	private DepartmentService  depService;
	
	@Autowired
	private QcTypeService qcTypeService;
	
	@Autowired
	private QcBillService qcBillService;
	
	@Autowired
	private RespPunishRelationService respPunishRelationService;
	
	@Autowired
	private JobService  jobService;
	
	@Autowired
	private QcBillService qcService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InnerRespBillService innerRespBillService;
	
	@Override
	public void add(InnerPunishBill obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
		
		deletePunishBasis(id);
	}
	
	@Override
	public InnerPunishBill get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<InnerPunishBill> list(InnerPunishBillDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void update(InnerPunishBill obj) {
		mapper.update(obj);
		
		//同时更新责任处罚关联单
		updateRespPunishRelation(obj);
	}
	
	/**
	 * 删除内部处罚依据
	 * @param id
	 */
	private void deletePunishBasis(Integer id) {
		InnerPunishBasis innerBill =new InnerPunishBasis();
		innerBill.setIpbId(id);
		basisMapper.deletePunish(innerBill);
	}
	
	@Override
	public void detetePunishById(InnerPunishBill bill) {
		qcBillService.delete(bill.getQcId());//删除质检单
		
		this.delete(bill.getId());
	}
	
	@Override
	public void deletePunish(Integer innerId) {
		this.delete(innerId);
		
		deleteRelation(innerId);
	}
	
	/**
	 * 更新责任处罚关联单
	 * @param obj
	 */
	private void updateRespPunishRelation(InnerPunishBill obj) {
		if(null != obj.getRespId() && obj.getRespId() > 0){
			//先删除该处罚单下的关联单
			deleteRelation(obj.getId());
			
			//插入该处罚单下的关联单
			RespPunishRelation relation = new RespPunishRelation();
			relation.setRespId(obj.getRespId());
			relation.setRespType(obj.getRespType());
			relation.setPunishId(obj.getId());
			relation.setPunishType(QcConstant.INNER_RESP_PUN_FALG);
			
			respPunishRelationService.add(relation);
		}
	}
	
	/**
	 * 删除责任处罚关联单
	 * @param id
	 */
	private void deleteRelation(Integer id) {
		RespPunishRelationDto dto = new RespPunishRelationDto();
		dto.setPunishId(id);
		dto.setPunishType(QcConstant.INNER_RESP_PUN_FALG);
		
		respPunishRelationService.deleteByPunish(dto);
	}
	
	@Override
	public void loadPage(InnerPunishBillDto dto) {
		convertDto(dto);
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		
		if(totalRecords > 0){
			List<InnerPunishBill> list = this.list(dto);
			fillInnerDepName(list);
			
			dto.setDataList(list);
		}
	}
	
	private void convertDto(InnerPunishBillDto dto) {
		
		if (!StringUtils.isEmpty(dto.getPubPersonId())) {
			String newId = dto.getPubPersonId().replaceAll("^(0+)", "");
			dto.setPunishPersonId(Integer.parseInt(newId));
		}

		if (!StringUtils.isEmpty(dto.getDepName())) {
			Department dep = depService.getDepByFullName(dto.getDepName());
			dto.setDepId(dep.getId());
		}
		if (!StringUtils.isEmpty(dto.getJobName())) {
			Integer jobId = jobService.getJobIdByName(dto.getJobName());
			dto.setJobId(jobId);
		}

		if (!StringUtils.isEmpty(dto.getQcTypeName())) {
			QcType qcType = qcTypeService.getTypeByFullName(dto.getQcTypeName());
			dto.setQcTypeId(qcType.getId());
		}
	}

	private void fillInnerDepName(List<InnerPunishBill> list) {
		List<QcType> qcTypeList = qcTypeService.getQcTypeDataCache();
		
		for (InnerPunishBill inner : list) {

			String depName = depService.getDepFullNameById(inner.getDepId());
			inner.setDepName(depName);
			
			String qcTypeName = qcTypeService.getQtFullNameById(inner.getQcTypeId(), qcTypeList);// 获取质量类型全程
			inner.setQcTypeName(qcTypeName);
		}
	}

	@Override
	public int getInnerPunishIsExist(InnerPunishBill innerBill) {
		
		return mapper.getInnerPunishIsExist(innerBill);
	}

	@Override
	public List<InnerPunishBill> listInnerPunish(Integer qcBillId) {
		List<InnerPunishBill> list = mapper.listInnerPunishBill(qcBillId);
		for (InnerPunishBill inner : list) {
			String fullName = depService.getDepFullNameById(inner.getDepId());
			inner.setDepName(fullName);
		}
		return list;
	}

	/**
	 *  删除标记位为1的数据
	 */
	public void deleteUnUsePunish(InnerPunishBillDto dto) {
		mapper.deleteUnUsePunish(dto);
	}

	/**
	 * 插入处罚单获取主键ID
	 * @param innerBill
	 */
	public void addPunish(InnerPunishBill innerBill) {
		mapper.addPunish(innerBill);
	}

	public void updatePunishBill(InnerPunishBasis basis) {
		mapper.updatePunishBill(basis);
	}

	@Override
	public List<InnerPunishBill> listInnerPunishByQcId(Integer qcId) {
		return mapper.listInnerPunishByQcId(qcId);
	}		

	@Override
	public Integer copyInnerPunishBill(QcDetailCopyDto qcDetailCopyDto) {
		InnerPunishBill innerPunishBill = new InnerPunishBill();
		innerPunishBill.setQcId(qcDetailCopyDto.getQcIdOld());
		innerPunishBill.setAddPerson(qcDetailCopyDto.getAddPerson());
		innerPunishBill.setOldId(qcDetailCopyDto.getPunishIdOld());
		
		mapper.copyInnerPunishBill(innerPunishBill);
		
		return innerPunishBill.getId();
	}

	@Override
	public void updateInnerPunish(InnerPunishBill innerBill, User user) {
		Department dep = depService.getDepByFullName(innerBill.getDepName());
		innerBill.setDepId(dep.getId());
		
		Integer jobId = jobService.getJobIdByName(innerBill.getJobName());
		innerBill.setJobId(jobId);
		
		if(innerBill.getPunishPersonId() == null || innerBill.getPunishPersonId() == 0){
			User punishUser = userService.getUserByRealName(innerBill.getPunishPersonName());
			innerBill.setPunishPersonId(punishUser.getId());
		}

		innerBill.setUpdatePerson(user.getRealName());
		innerBill.setDelFlag(Integer.parseInt(QcConstant.DEL_FLAG_FALSE));
		
		if(innerBill.getUseFlag() == QcConstant.QC_OPERATE){//运营质检，虚拟生成质检单、责任单
			updateQcBill(innerBill, user);
			
			addRespBill(innerBill, user);
		}
		
		this.update(innerBill);
	}
	
	/**
	 * 运营质检添加处罚单，虚拟生成责任单，进行责任单处罚单关联
	 * @param innerBill
	 * @param user
	 */
	private void addRespBill(InnerPunishBill innerBill, User user) {
		InnerRespBill innerRespBill = new InnerRespBill();
		
		if(null != innerBill.getRespManagerName() && !("").equals(innerBill.getRespManagerName())){
			
			User managerUser = userService.getUserByRealName(innerBill.getRespManagerName());
			if(null != managerUser){
				innerRespBill.setRespManagerId(managerUser.getId());
				innerRespBill.setRespManagerName(managerUser.getRealName());
				innerRespBill.setManagerJobId(managerUser.getJobId());
			}else{
				innerRespBill.setRespManagerId(0);
				innerRespBill.setRespManagerName("");
				innerRespBill.setManagerJobId(0);
			}
		}
		
		if(null != innerBill.getRespGeneralName() && !("").equals(innerBill.getRespGeneralName())){
			User generalUser = userService.getUserByRealName(innerBill.getRespGeneralName());
			
			if(null != generalUser){
				innerRespBill.setRespGeneralId(generalUser.getId());
				innerRespBill.setRespGeneralName(generalUser.getRealName());
				innerRespBill.setGeneralJobId(generalUser.getJobId());
			}else{
				innerRespBill.setRespGeneralId(0);
				innerRespBill.setRespGeneralName("");
				innerRespBill.setGeneralJobId(0);
			}
		}
		
		innerRespBill.setRespPersonId(innerBill.getPunishPersonId());
		innerRespBill.setRespPersonName(innerBill.getPunishPersonName());
		innerRespBill.setDepId(innerBill.getDepId());
		innerRespBill.setJobId(innerBill.getJobId());
		innerRespBill.setRespReason("");
		
		RespPunishRelation relationBill = respPunishRelationService.getRelationByPunish(innerBill.getId(), QcConstant.INNER_RESP_PUN_FALG);
		
		if(null != relationBill){
			innerRespBill.setId(relationBill.getRespId());
			
			innerRespBillService.update(innerRespBill);
		}else{
			innerRespBillService.add(innerRespBill);
		}
		
		innerBill.setRespId(innerRespBill.getId());
		innerBill.setRespType(QcConstant.INNER_RESP_PUN_FALG);
	}
	
	/**
	 * 运营质检添加处罚单，虚拟生成质检单
	 * @param innerBill
	 * @param user
	 * @return
	 */
	private void updateQcBill(InnerPunishBill innerBill, User user) {
		QcBill qcBill = new QcBill();
		qcBill.setOrdId(innerBill.getOrdId());
		qcBill.setPrdId(innerBill.getPrdId());
		
		if(null !=innerBill.getQcId() && innerBill.getQcId() > 0){
			qcBill.setId(innerBill.getQcId());
			
			qcService.update(qcBill);
		}else{
			qcBill.setQcAffairSummary("运营质检处罚");
			qcBill.setGroupFlag(QcConstant.QC_OPERATE); 
			qcBill.setState(QcConstant.QC_BILL_STATE_FINISH);
			qcBill.setUserFlag(QcConstant.USER_FLAG_TRUE);
			qcBill.setQcPerson(user.getRealName());
			qcBill.setQcPersonId(user.getId());
			qcBill.setAddPerson(user.getRealName());
			qcBill.setAddPersonId(user.getId());
			qcBill.setDelFlag(Constant.NO);
			qcBill.setFinishTime(new Date());
			
			QcType qcType = qcTypeService.getTypeByFullName(innerBill.getQcTypeName());
			if (null != qcType) {
				qcBill.setQcTypeId(qcType.getId());
			}
			
			qcService.addQcBill(qcBill);
		}
		
		innerBill.setQcId(qcBill.getId());
	}

	@Override
	public Integer getTotalScores(InnerPunishBillDto dto) {
		return mapper.getTotalScores(dto);
	}
	
	@Override
	public Integer exportCount(InnerPunishBillDto dto) {
		convertDto(dto);
		
		return mapper.count(dto);
	}

	@Override
	public List<InnerPunishBill> exportList(InnerPunishBillDto dto) {
		convertDto(dto);
		
		List<InnerPunishBill> innerList = mapper.exportList(dto);
		
		List<QcType> qcTypeList = qcTypeService.getQcTypeDataCache();
		
		for (InnerPunishBill inner : innerList) {

			String depName = depService.getDepFullNameById(inner.getDepId());
			String depNames[] = depName.split("-");
			if (depNames.length == 3) {
				inner.setBusinessUnit(depNames[0]);
				inner.setDep(depNames[1]);
				inner.setTeam(depNames[2]);
			}
			if (depNames.length == 2) {
				inner.setBusinessUnit(depNames[0]);
				inner.setDep(depNames[1]);
			}
			if (depNames.length == 1) {
				inner.setBusinessUnit(depNames[0]);
			}
			
			String qcTypeName = qcTypeService.getQtFullNameById(inner.getQcTypeId(), qcTypeList);
			String qcTypeNames[] = qcTypeName.split("-");
			if (qcTypeNames.length == 3) {
				inner.setQcTypeNature(qcTypeNames[0]);
				inner.setQcTypeName(qcTypeNames[1]);
			}
			if (qcTypeNames.length == 2) {
				inner.setQcTypeNature(qcTypeNames[0]);
				inner.setQcTypeName(qcTypeNames[1]);
			}
			if (qcTypeNames.length == 1) {
				inner.setQcTypeNature(qcTypeNames[0]);
			}
			
			if (null != inner.getQcFinishTime()) {
				inner.setWeek(DateUtil.getYearAndWeek(inner.getQcFinishTime()));
			}
		}
		
		return innerList;
	}

}
