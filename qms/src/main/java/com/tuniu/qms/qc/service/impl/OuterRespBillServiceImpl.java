package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.JobService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.qc.dao.OuterRespBillMapper;
import com.tuniu.qms.qc.dto.OuterRespBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.model.InnerPunishBill;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.OuterRespBill;
import com.tuniu.qms.qc.model.RespPunishRelation;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.OuterRespBillService;
import com.tuniu.qms.qc.service.RespPunishRelationService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class OuterRespBillServiceImpl implements OuterRespBillService{

	
	@Autowired
	private OuterRespBillMapper mapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OuterPunishBillService outerPunishService;

	@Autowired
	private InnerPunishBillService innerPunishService;
	
	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private RespPunishRelationService respPunishRelationService;
	
	@Autowired
	private JobService jobService;
	
	@Override
	public void add(OuterRespBill obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(OuterRespBill obj) {
		mapper.update(obj);
	}

	@Override
	public OuterRespBill get(Integer id) {
		return mapper.get(id);
	}

	public List<OuterRespBill> list(OuterRespBillDto dto) {
		return mapper.list(dto);
	}

	/**
	 * 分页查询外部责任单数据
	 */
	public void loadPage(OuterRespBillDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
	}

	@Override
	public int getOuterRespIsExist(OuterRespBill outerBill) {
		
		return mapper.getOuterRespIsExist(outerBill);
	}

	/**
	 * 插入外部责任单 并插入外部处罚单以及改进人处罚单
	 */
	public void addRespAndPunish(OuterRespBill outerBill) {
		fillOuterRespBill(outerBill);
		//插入责任单
		this.add(outerBill);
		
		//插入供应商处罚单
		addOuterPunishBill(outerBill);
		//插入责任人处罚单
		addInnerPunishBill(outerBill);
	}
	
	@Override
	public void updateOuterResp(OuterRespBill outerBill) {
		fillOuterRespBill(outerBill);
		
		this.update(outerBill);
	}
	
	private void fillOuterRespBill(OuterRespBill outerBill) {
		User respUser =  userService.getUserByRealName(outerBill.getRespPersonName());//根据责任人名字获取信息
		outerBill.setRespPersonId(respUser.getId());
		
		User impUser =  userService.getUserByRealName(outerBill.getImpPersonName());//根据改进人名字获取ID
		outerBill.setImpPersonId(impUser.getId());
		
		if(null != outerBill.getAppealPersonName() && !("").equals(outerBill.getAppealPersonName())){
			User appealUser =  userService.getUserByRealName(outerBill.getAppealPersonName());//根据异议提出人名字获取ID
			outerBill.setAppealPersonId(appealUser.getId());
		}
		
		Department dep =  depService.getDepByFullName(outerBill.getDepName());
		outerBill.setDepId(dep.getId());
		
		outerBill.setRespReason("");
		
		if(null != outerBill.getRespManagerName() && !("").equals(outerBill.getRespManagerName())){
			User managerUser = userService.getUserByRealName(outerBill.getRespManagerName());
			if(null != managerUser){
				outerBill.setRespManagerId(managerUser.getId());
			}else{
				outerBill.setRespManagerName("");
			}
			
			if(null != outerBill.getManagerJobName() && !("").equals(outerBill.getManagerJobName())){
				Integer managerJobId = jobService.getJobIdByName(outerBill.getManagerJobName());
				outerBill.setManagerJobId(null != managerJobId ? managerJobId : 0);
			}
		}
		
		if(null != outerBill.getRespGeneralName() && !("").equals(outerBill.getRespGeneralName())){
			User generalUser = userService.getUserByRealName(outerBill.getRespGeneralName());
			if(null != generalUser){
				outerBill.setRespGeneralId(generalUser.getId());
			}else{
				outerBill.setRespGeneralName("");
			}
			
			if(null != outerBill.getGeneralJobName() && !("").equals(outerBill.getGeneralJobName())){
				Integer generalJobId = jobService.getJobIdByName(outerBill.getGeneralJobName());
				outerBill.setGeneralJobId(null != generalJobId ? generalJobId : 0);
			}
		}
	}

	private void addInnerPunishBill(OuterRespBill outerBill) {
		User respUser =  userService.getUserByRealName(outerBill.getRespPersonName());//根据责任人名字获取信息
		
		InnerPunishBill impPunishBill = new InnerPunishBill();
		impPunishBill.setQcId(outerBill.getQcId());
		impPunishBill.setPunishPersonId(respUser.getId());
        impPunishBill.setPunishPersonName(respUser.getRealName());
        impPunishBill.setDepId(respUser.getDepId() == null ? 0 : respUser.getDepId());
        impPunishBill.setJobId(respUser.getJobId() == null ? 0 : respUser.getJobId());
        impPunishBill.setPunishReason("");
        impPunishBill.setAddPerson(outerBill.getAddPerson());
        impPunishBill.setRelatedFlag(QcConstant.RELATED_FLAG_TURE);
        innerPunishService.add(impPunishBill);
        
        //增加责任处罚关联单
        addResPunishRelationBill(outerBill.getId(), impPunishBill.getId(), false);
	}

	private void addOuterPunishBill(OuterRespBill outerBill) {
		OuterPunishBill outerPunish = new OuterPunishBill();
		outerPunish.setQcId(outerBill.getQcId());
		outerPunish.setAgencyId(outerBill.getAgencyId());
		outerPunish.setAgencyName(outerBill.getAgencyName());
		outerPunish.setAddPerson(outerBill.getAddPerson());
	    outerPunish.setPunishReason("");
	    outerPunishService.add(outerPunish);
	    
	    //增加责任处罚关联单
        addResPunishRelationBill(outerBill.getId(), outerPunish.getId(), true);
	}

	/**
	 * 增加责任处罚关联单
	 * @param id  责任单id
	 * @param id2   处罚单id
	 * @param b   是否是外部处罚单
	 */
	private void addResPunishRelationBill(Integer id, Integer id2, boolean b) {
		RespPunishRelation relation = new RespPunishRelation();
		
		relation.setRespId(id);
		relation.setRespType(QcConstant.OUTER_RESP_PUN_FALG);
		relation.setPunishId(id2);
		if(b){
			relation.setPunishType(QcConstant.OUTER_RESP_PUN_FALG);
		}else{
			relation.setPunishType(QcConstant.INNER_RESP_PUN_FALG);
		}
		
		respPunishRelationService.add(relation);
	}

	@Override
	public List<OuterRespBill> listOuterResp(OuterRespBillDto dto) {
		
		 List<OuterRespBill> list = list(dto);
		  for(OuterRespBill outer: list){
			  
			  String fullName = depService.getDepFullNameById(outer.getDepId());
			  outer.setDepName(fullName);
		  }
		return list;
	}

	@Override
	public List<OuterRespBill> listResp() {
		
		return mapper.listResp();
	}

	@Override
	public void deleteRespBill(Integer outerId) {
		this.delete(outerId);
		
		deletePunishBill(outerId);
	}
	
	/**
	 * 删除责任单关联的处罚单
	 * @param innerId
	 */
	private void deletePunishBill(Integer outerId) {
		RespPunishRelationDto dto = new RespPunishRelationDto();
		dto.setRespId(outerId);
		dto.setRespType(QcConstant.OUTER_RESP_PUN_FALG);
		List<RespPunishRelation> relationList = respPunishRelationService.list(dto);
		
		for(RespPunishRelation relation : relationList){
			if(relation.getPunishType() == 1){
				innerPunishService.deletePunish(relation.getPunishId());
			}else{
				outerPunishService.deletePunish(relation.getPunishId());
			}
			
		}
	}

	@Override
	public Integer copyOuterRespBill(QcDetailCopyDto qcDetailCopyDto) {
		OuterRespBill outerRespBill = new OuterRespBill();
		
		outerRespBill.setQpId(qcDetailCopyDto.getQpIdNew());
		outerRespBill.setAddPerson(qcDetailCopyDto.getAddPerson());
		outerRespBill.setOldRespId(qcDetailCopyDto.getRespIdOld());
		
		mapper.copyOuterRespBill(outerRespBill);//复制内部责任单
		
		return outerRespBill.getId() != null ? outerRespBill.getId() : 0;
	}

	@Override
	public OuterRespBill getRespInfoByComplaintId(Integer dataId) {
		return mapper.getRespInfoByComplaintId(dataId);
	}

}
