package com.tuniu.qms.qc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.OuterPunishBasisMapper;
import com.tuniu.qms.qc.dto.OuterPunishBasisDto;
import com.tuniu.qms.qc.dto.PunishStandardDto;
import com.tuniu.qms.qc.model.OuterPunishBasis;
import com.tuniu.qms.qc.service.OuterPunishBasisService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class OuterPunishBasisServiceImpl implements OuterPunishBasisService{

	
	@Autowired
	private OuterPunishBasisMapper mapper;
	
	@Autowired
	private OuterPunishBillService pbService;
	
	@Override
	public void add(OuterPunishBasis obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
		
	}

	@Override
	public void update(OuterPunishBasis obj) {
		
		mapper.update(obj);
		
	}

	@Override
	public OuterPunishBasis get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<OuterPunishBasis> list(OuterPunishBasisDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(OuterPunishBasisDto dto) {
		
	}

	/**
	 * 插入外部处罚依据表
	 */
	public void addOuterPunish(PunishStandardDto dto) {
		
		OuterPunishBasis basis =new OuterPunishBasis();
		basis.setOpbId(dto.getPunishId());
		basis.setAddPerson(dto.getAddPerson());
		//先清空外部处罚依据
		mapper.deletePunish(basis);
		//插入新的处罚依据
		for(int i=0;i<dto.getStandardIds().size();i++){
			
			Integer standardId  = dto.getStandardIds().get(i);
			basis.setPsId(standardId);
			if(i == 0){
			
				basis.setExecFlag(QcConstant.EXECFLAG_YES);
				this.add(basis);
			//	pbService.updatePunishBill(basis);
				
			}else{
				
				basis.setExecFlag(QcConstant.EXECFLAG_NO);
				this.add(basis);
			}
			
		}
	}

	
	public void updatePunish(OuterPunishBasis basisBean) {
		
		//先把内部处罚依据表execFlag 置为0
		basisBean.setExecFlag(QcConstant.EXECFLAG_NO);//执行标记
		mapper.updatePunish(basisBean);
		//再更新某一个执行
		basisBean.setExecFlag(QcConstant.EXECFLAG_YES);//执行标记
		mapper.update(basisBean);
		//更新外部处罚单中的金额和分数
	    mapper.updatePunishBill(basisBean);
	}

	/**
	 * 查询处罚单有无关联处罚依据
	 * @return
	 */
	public int getPunishBasisIsExist(Map<String, Object> map) {
		
		
		return mapper.getPunishBasisIsExist(map);
	}
	
}
