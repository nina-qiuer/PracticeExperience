package com.tuniu.qms.qc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.InnerPunishBasisMapper;
import com.tuniu.qms.qc.dto.InnerPunishBasisDto;
import com.tuniu.qms.qc.dto.PunishStandardDto;
import com.tuniu.qms.qc.model.InnerPunishBasis;
import com.tuniu.qms.qc.service.InnerPunishBasisService;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class InnerPunishBasisServiceImpl implements InnerPunishBasisService{

	
	@Autowired
	private InnerPunishBasisMapper mapper;
	
	@Autowired
	private InnerPunishBillService pbService;
	@Override
	public void add(InnerPunishBasis obj) {
	
		mapper.add(obj);
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(InnerPunishBasis obj) {
		
		mapper.update(obj);
		
	}

	@Override
	public InnerPunishBasis get(Integer id) {
	
		return mapper.get(id);
	}

	@Override
	public List<InnerPunishBasis> list(InnerPunishBasisDto dto) {
		
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(InnerPunishBasisDto dto) {
		
		
	}

	/**
	 * 插入内部处罚依据表
	 */
	public void addInnerPunish(PunishStandardDto dto) {
		
		InnerPunishBasis basis =new InnerPunishBasis();
		basis.setIpbId(dto.getPunishId());
		basis.setAddPerson(dto.getAddPerson());
		//先清空内部处罚依据
		mapper.deletePunish(basis);
		//插入新的处罚依据
//		for(Integer standardId:dto.getStandardIds()){
//			
//			basis.setPsId(standardId);
//			this.add(basis);
//		}
		
		for(int i=0;i<dto.getStandardIds().size();i++){
			
			Integer standardId  = dto.getStandardIds().get(i);
			basis.setPsId(standardId);
			if(i == 0){
			
				basis.setExecFlag(QcConstant.EXECFLAG_YES);
				this.add(basis);
				//pbService.updatePunishBill(basis);
			}else{
				
				basis.setExecFlag(QcConstant.EXECFLAG_NO);
				this.add(basis);
			}
			
		}
	}

	@Override
	public void updatePunish(InnerPunishBasis basisBean) {
		//先把内部处罚依据表execFlag 置为0
		basisBean.setExecFlag(QcConstant.EXECFLAG_NO);//执行标记
		mapper.updatePunish(basisBean);
		//再更新某一个执行
		basisBean.setExecFlag(QcConstant.EXECFLAG_YES);//执行标记
		mapper.update(basisBean);
		//更新内部处罚单中的金额和分数
	  //  mapper.updatePunishBill(basisBean);
	}

	/**
	 * 查询处罚单有无关联处罚依据
	 * @return
	 */
	public int getPunishBasisIsExist(Map<String, Object> map) {
		
		
		return mapper.getPunishBasisIsExist(map);
	}

}
