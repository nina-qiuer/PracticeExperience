package com.tuniu.qms.qc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.qc.dao.InnerPunishBasisMapper;
import com.tuniu.qms.qc.dao.InnerPunishBillMapper;
import com.tuniu.qms.qc.dao.InnerRespBillMapper;
import com.tuniu.qms.qc.dao.OperateQcBillMapper;
import com.tuniu.qms.qc.dao.QualityProblemMapper;
import com.tuniu.qms.qc.dao.UpLoadAttachMapper;
import com.tuniu.qms.qc.dto.InnerPunishBillDto;
import com.tuniu.qms.qc.dto.QcBillDto;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.OperateQcBillService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class OperateQcBillServiceImpl implements OperateQcBillService{

	@Autowired
	private OperateQcBillMapper mapper;
	
	@Autowired
	private InnerPunishBasisMapper innerBasisMapper;
	
	@Autowired
	private InnerPunishBillService  innerPunishService;
	
	@Autowired
	private InnerPunishBillMapper innerPunishMapper;
	
	@Autowired
	private QualityProblemMapper qpMapper;
	
	@Autowired
	private InnerRespBillMapper innerMapper;
	
	@Autowired
	private UpLoadAttachMapper upMapper;
	
	
	@Override
	public void add(QcBill obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QcBill obj) {
		mapper.update(obj);
	}

	@Override
	public QcBill get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<QcBill> list(QcBillDto dto) {
		
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcBillDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
		
	}

	@Override
	public int getQcBillIsExist(Integer qcId) {
		
		return mapper.getQcBillIsExist(qcId);
	}

	/**
	 * 撤销研发质检单
	 */
	@Override
	public void deleteOperateBill(QcBill qcBill) {

		List<QualityProblem> qpList =  qpMapper.getQpByQcId(qcBill.getId());
		this.update(qcBill);//更新质检单状态
		for(QualityProblem qp :qpList){
		
			if(qp!=null&&qp.getId()>0){
		
				innerMapper.deleteInnerRespBill(qp.getId());//删除质量问题单对应的外部责任单
				upMapper.deleteAttach(qp.getId());//删除质量问题对应的附件
				qpMapper.delete(qp.getId());//删除质量问题单
			}  
		
		}
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("qcId", qcBill.getId());
		List<Integer> innerList = innerPunishMapper.getByQcId(map);
		map.put("innerList", innerList);
		if(innerList.size()>0){
		 innerBasisMapper.deletePunishByIpb(map);//删除内部处罚单对应的处罚依据
		}
		innerPunishMapper.deleteInnerPunishBill(qcBill.getId());//删除内部处罚单
	}

	@Override
	public void deleteInner(Integer qcId) {
		
		InnerPunishBillDto innerDto =new InnerPunishBillDto();
		innerDto.setQcId(qcId);
		innerDto.setDelFlag(QcConstant.DEL_FLAG_TRUE);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("qcId", qcId);
		map.put("delFlag", QcConstant.DEL_FLAG_TRUE);
		List<Integer> innerList = innerPunishMapper.getByQcId(map);
		map.put("innerList", innerList);
		if(innerList.size()>0){
	 	innerBasisMapper.deletePunishByIpb(map);//删除内部处罚单对应的处罚依据
		}
		innerPunishService.deleteUnUsePunish(innerDto);
		
	}


}
