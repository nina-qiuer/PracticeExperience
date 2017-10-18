package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.qc.dao.InnerRespBillMapper;
import com.tuniu.qms.qc.dto.InnerRespBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.InnerRespBill;
import com.tuniu.qms.qc.model.RespPunishRelation;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.InnerRespBillService;
import com.tuniu.qms.qc.service.RespPunishRelationService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class InnerRespBillServiceImpl implements InnerRespBillService{

	@Autowired
	private InnerRespBillMapper mapper;
	
	@Autowired
	private DepartmentService  depService;
	
	@Autowired
	private JobService  jobService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InnerPunishBillService innerPunishService;
	
	@Autowired
	private RespPunishRelationService respPunishRelationService;
	
	@Override
	public void add(InnerRespBill obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(InnerRespBill obj) {
		mapper.update(obj);
	}

	@Override
	public InnerRespBill get(Integer id) {
		return mapper.get(id);
	}

	
	public List<InnerRespBill> list(InnerRespBillDto dto) {
		return mapper.list(dto);
	}

	/**
	 * 分页查询内部责任单列表
	 */
	public void loadPage(InnerRespBillDto dto) {
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
	}

	@Override
	public int getInnerRespIsExist(InnerRespBill innerBill) {
		return mapper.getInnerRespIsExist(innerBill);
	}

	@Override
	public List<InnerRespBill> listInnerResp(InnerRespBillDto dto) {
		List<InnerRespBill> list = list(dto);
		for(InnerRespBill inner: list){
			String fullName = depService.getDepFullNameById(inner.getDepId());
			inner.setDepName(fullName);
		}
		return list;
	}

	/**
	 * 增加责任单同时添加责任人处罚单 和改进人处罚单
	 */
	public void addRespAndPunish(InnerRespBill innerBill) {
		User respUser =  userService.getUserByRealName(innerBill.getRespPersonName());
		User impUser =  userService.getUserByRealName(innerBill.getImpPersonName());
		
		fillInnerRespBill(impUser, innerBill);//内部责任单信息填充
		
		this.add(innerBill);
		
		//添加处罚单
		addInnerPunishBill(innerBill, respUser);
        //当前改进人不存在处罚单的情况下添加处罚单
        if(null != impUser){
        	addInnerPunishBill(innerBill, impUser);
        }
    }
	
	/**
	 * 添加内部处罚单、责任处罚关联单
	 * @param innerBill
	 * @param respUser
	 */
	private void addInnerPunishBill(InnerRespBill innerBill, User punishUser) {
		InnerPunishBill punishBill = new InnerPunishBill();
        
		punishBill.setQcId(innerBill.getQcId());
        punishBill.setPunishPersonId(punishUser.getId());
        punishBill.setPunishPersonName(punishUser.getRealName());
        punishBill.setDepId(punishUser.getDepId() == null ? 0 : punishUser.getDepId());
        punishBill.setJobId(punishUser.getJobId() == null ? 0 : punishUser.getJobId());
        punishBill.setPunishReason("");
        punishBill.setAddPerson(innerBill.getAddPerson());
        punishBill.setRelatedFlag(QcConstant.RELATED_FLAG_TURE);
        innerPunishService.add(punishBill);
        
        addResPunishRelationBill(innerBill.getId(), punishBill.getId());
	}

	/**
	 * 责任单信息填充
	 * @param impUser
	 * @param innerBill
	 */
	private void fillInnerRespBill(User impUser, InnerRespBill innerBill) {
		if(null != impUser){
			innerBill.setImpPersonId(impUser.getId());
		}
		
		Department dep =  depService.getDepByFullName(innerBill.getDepName());
		innerBill.setDepId(dep.getId());
		
		Integer jobId = jobService.getJobIdByName(innerBill.getJobName());
		innerBill.setJobId(jobId);
		
		if(innerBill.getAppealPersonName() != null && !("").equals(innerBill.getAppealPersonName())){
			User appealUser =  userService.getUserByRealName(innerBill.getAppealPersonName());
			if(null != appealUser){
				innerBill.setAppealPersonId(appealUser.getId());
			}
			
		    innerBill.setImpJobId(jobService.getJobIdByName(innerBill.getImpJobName()));
			innerBill.setAppealJobId(jobService.getJobIdByName(innerBill.getAppealJobName()));
		}
		innerBill.setRespReason("");
		
		if(null != innerBill.getRespManagerName() && !("").equals(innerBill.getRespManagerName())){
			User managerUser = userService.getUserByRealName(innerBill.getRespManagerName());
			if(null != managerUser){
				innerBill.setRespManagerId(managerUser.getId());
			}else{
				innerBill.setRespManagerName("");
			}
			
			if(null != innerBill.getManagerJobName() && !("").equals(innerBill.getManagerJobName())){
				Integer managerJobId = jobService.getJobIdByName(innerBill.getManagerJobName());
				innerBill.setManagerJobId(null != managerJobId ? managerJobId : 0);
			}
		}
		
		if(null != innerBill.getRespGeneralName() && !("").equals(innerBill.getRespGeneralName())){
			User generalUser = userService.getUserByRealName(innerBill.getRespGeneralName());
			if(null != generalUser){
				innerBill.setRespGeneralId(generalUser.getId());
			}else{
				innerBill.setRespGeneralName("");
			}
			
			if(null != innerBill.getGeneralJobName() && !("").equals(innerBill.getGeneralJobName())){
				Integer generalJobId = jobService.getJobIdByName(innerBill.getGeneralJobName());
				innerBill.setGeneralJobId(null != generalJobId ? generalJobId : 0);
			}
		}
	}
	

	@Override
	public void updateInnerResp(InnerRespBill innerBill) {
		User impUser =  userService.getUserByRealName(innerBill.getImpPersonName());//根据改进人名字获取ID
		
		fillInnerRespBill(impUser, innerBill);
		this.update(innerBill);
	}

	/**
	 * 增加责任处罚关联单
	 * @param id  责任单id
	 * @param id2   处罚单id
	 */
	private void addResPunishRelationBill(Integer id, Integer id2) {
		RespPunishRelation relation = new RespPunishRelation();
		
		relation.setRespId(id);
		relation.setRespType(QcConstant.INNER_RESP_PUN_FALG);
		relation.setPunishId(id2);
		relation.setPunishType(QcConstant.INNER_RESP_PUN_FALG);
		
		respPunishRelationService.add(relation);
	}

	@Override
	public Double getRespRate(Integer qcId) {
		return mapper.getRespRate(qcId);
	}

	@Override
	public void deleteRespBill(Integer innerId) {
		this.delete(innerId);
		
		deletePunishBill(innerId);
	}
	
	/**
	 * 删除责任单关联的处罚单
	 * @param innerId
	 */
	private void deletePunishBill(Integer innerId) {
		RespPunishRelationDto dto = new RespPunishRelationDto();
		dto.setRespId(innerId);
		dto.setRespType(QcConstant.INNER_RESP_PUN_FALG);
		List<RespPunishRelation> relationList = respPunishRelationService.list(dto);
		
		for(RespPunishRelation relation : relationList){
			innerPunishService.deletePunish(relation.getPunishId());
		}
	}

	@Override
	public Integer copyInnerRespBill(QcDetailCopyDto qcDetailCopyDto) {
		InnerRespBill innerRespBill = new InnerRespBill();
		innerRespBill.setQpId(qcDetailCopyDto.getQpIdNew());
		innerRespBill.setAddPerson(qcDetailCopyDto.getAddPerson());
		innerRespBill.setOldRespId(qcDetailCopyDto.getRespIdOld());
		
		mapper.copyInnerRespBill(innerRespBill);//复制内部责任单
		
		return innerRespBill.getId() != null ? innerRespBill.getId() : 0;
	}

}
