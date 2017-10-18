package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.RespPunishRelationMapper;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.model.RespPunishRelation;
import com.tuniu.qms.qc.service.RespPunishRelationService;

/**
 * 责任处罚关联单
 * @author zhangsensen
 *
 */
@Service
public class RespPunishRelationServiceImpl implements RespPunishRelationService {
	
	@Autowired
	private RespPunishRelationMapper respPunishRelationMapper;
	
	@Override
	public void add(RespPunishRelation obj) {
		respPunishRelationMapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		respPunishRelationMapper.delete(id);
	}

	@Override
	public void update(RespPunishRelation obj) {
		respPunishRelationMapper.update(obj);
	}

	@Override
	public RespPunishRelation get(Integer id) {
		return null;
	}

	@Override
	public List<RespPunishRelation> list(RespPunishRelationDto dto) {
		return respPunishRelationMapper.list(dto);
	}

	@Override
	public void loadPage(RespPunishRelationDto dto) {
	}

	@Override
	public void deleteByPunish(RespPunishRelationDto dto) {
		respPunishRelationMapper.deleteByPunish(dto);
	}

	@Override
	public void addRespPunishRelation(QcDetailCopyDto qcDetailCopyDto) {
		//插入该处罚单下的关联单
		RespPunishRelation relation = new RespPunishRelation();
		relation.setRespId(qcDetailCopyDto.getRespIdNew());
		relation.setRespType(qcDetailCopyDto.getRespType());
		relation.setPunishId(qcDetailCopyDto.getPunishIdNew());
		relation.setPunishType(qcDetailCopyDto.getPunishType());
		
		this.add(relation);
	}

	@Override
	public boolean checkPunishRelation(Integer qcBillId) {
		boolean flag = true;
		
		List<RespPunishRelation> relationList = respPunishRelationMapper.checkPunishRelation(qcBillId);
		
		for(RespPunishRelation relationBill : relationList){
			if(null == relationBill.getId() ){
				flag = false;
				break;
			}
		}
		
		return flag;
	}

	@Override
	public RespPunishRelation getRelationByPunish(Integer id, int innerRespPunFalg) {
		RespPunishRelationDto dto = new RespPunishRelationDto();
		dto.setPunishId(id);
		dto.setPunishType(innerRespPunFalg);
		
		return respPunishRelationMapper.getRelationByPunish(dto);
	}

}
