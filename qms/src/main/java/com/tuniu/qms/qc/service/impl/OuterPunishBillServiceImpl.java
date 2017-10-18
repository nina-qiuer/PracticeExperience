package com.tuniu.qms.qc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.OuterPunishBasisMapper;
import com.tuniu.qms.qc.dao.OuterPunishBillMapper;
import com.tuniu.qms.qc.dto.OuterPunishBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;
import com.tuniu.qms.qc.model.OuterPunishBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.model.RespPunishRelation;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.service.RespPunishRelationService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class OuterPunishBillServiceImpl implements OuterPunishBillService{

	@Autowired
	private OuterPunishBillMapper mapper;
	
	@Autowired
	private OuterPunishBasisMapper  basisMapper;
	
	@Autowired
	private QcTypeService qcTypeService;
	
	@Autowired
	private RespPunishRelationService respPunishRelationService;
	
	@Override
	public void add(OuterPunishBill obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(OuterPunishBill obj) {
		mapper.update(obj);
		//同时更新责任处罚关联单
		updateRespPunishRelation(obj);
	}
	
	/**
	 * 更新责任处罚关联单
	 * @param obj
	 */
	private void updateRespPunishRelation(OuterPunishBill obj) {
		if(null != obj.getRespId() && obj.getRespId() > 0){
			//先删除该处罚单下的关联单
			deleteRelation(obj.getId());
			
			//插入该处罚单下的关联单
			RespPunishRelation relation = new RespPunishRelation();
			relation.setRespId(obj.getRespId());
			relation.setRespType(obj.getRespType());
			relation.setPunishId(obj.getId());
			relation.setPunishType(QcConstant.OUTER_RESP_PUN_FALG);
			
			respPunishRelationService.add(relation);
		}
	}

	/**
	 * 删除责任处罚关联单
	 * @param id
	 */
	private void deleteRelation(Integer id) {
		//删除该处罚单下的关联单
		RespPunishRelationDto dto = new RespPunishRelationDto();
		dto.setPunishId(id);
		dto.setPunishType(QcConstant.OUTER_RESP_PUN_FALG);
		
		respPunishRelationService.deleteByPunish(dto);
	}

	@Override
	public OuterPunishBill get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<OuterPunishBill> list(OuterPunishBillDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(OuterPunishBillDto dto) {
		
		List<QcType> qtList = qcTypeService.getQcTypeDataCache();
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		List<OuterPunishBill> list = this.list(dto);
		 for(OuterPunishBill outer: list){
			  if(null != outer.getQcTypeId()){
				  String qtName = qcTypeService.getQtFullNameById(outer.getQcTypeId(),qtList);
				  outer.setQcTypeName(qtName);
			  }
		  }
		dto.setDataList(list);
	}

	@Override
	public int getOuterPunishIsExist(OuterPunishBill outerBill) {
		
		return mapper.getOuterPunishIsExist(outerBill);
	}

	
	@Override
	public void deletePunish(Integer outerId) {
		this.delete(outerId);
		
		deleteOuterPunishBasis(outerId);
		//删除该处罚单下的关联单
		deleteRelation(outerId);
	}
	
	/**
	 * 删除供应商处罚依据
	 * @param outerId
	 */
	private void deleteOuterPunishBasis(Integer outerId) {
		OuterPunishBasis outerBill =new OuterPunishBasis();
		outerBill.setOpbId(outerId);
		basisMapper.deletePunish(outerBill);
	}

	/**
	 * 删除标记位为1的数据
	 */
	public void deleteUnUsePunish(OuterPunishBillDto dto) {
		
		mapper.deleteUnUsePunish(dto);
	}
	
	/**
	 * 插入处罚单获取主键ID
	 * @param innerBill
	 */
	public void addPunish(OuterPunishBill outerBill) {
	
		mapper.addPunish(outerBill);
	}
	
	public void updatePunishBill(OuterPunishBasis basis) {
		
		mapper.updatePunishBill(basis);
	}

	@Override
	public List<OuterPunishBill> listOuterPunish(Integer qcId) {
		return mapper.listOuterPunishBill(qcId);
	}

	@Override
	public Integer copyOuterPunishBill(QcDetailCopyDto qcDetailCopyDto) {
		OuterPunishBill outerPunishBill = new OuterPunishBill();
		outerPunishBill.setQcId(qcDetailCopyDto.getQcIdOld());
		outerPunishBill.setAddPerson(qcDetailCopyDto.getAddPerson());
		outerPunishBill.setOldId(qcDetailCopyDto.getPunishIdOld());
		
		mapper.copyOuterPunishBill(outerPunishBill);
		
		return outerPunishBill.getId() != null ? outerPunishBill.getId() : 0;
	}

	@Override
	public boolean getPunishIsExistByqcId(Integer qcId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qcId", qcId);
		map.put("delFlag", QcConstant.DEL_FLAG_FALSE);
		
		List<Integer> outerList = mapper.getByQcId(map);
		
		return (null != outerList && outerList.size() >0) ? true : false;
	}

}
