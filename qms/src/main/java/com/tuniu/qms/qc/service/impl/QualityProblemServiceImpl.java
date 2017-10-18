package com.tuniu.qms.qc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.qc.dao.InnerPunishBasisMapper;
import com.tuniu.qms.qc.dao.InnerPunishBillMapper;
import com.tuniu.qms.qc.dao.OuterPunishBasisMapper;
import com.tuniu.qms.qc.dao.OuterPunishBillMapper;
import com.tuniu.qms.qc.dao.QualityProblemMapper;
import com.tuniu.qms.qc.dao.UpLoadAttachMapper;
import com.tuniu.qms.qc.dto.InnerPunishBillDto;
import com.tuniu.qms.qc.dto.InnerRespBillDto;
import com.tuniu.qms.qc.dto.OuterPunishBillDto;
import com.tuniu.qms.qc.dto.OuterRespBillDto;
import com.tuniu.qms.qc.dto.QcDetailCopyDto;
import com.tuniu.qms.qc.dto.QualityProblemDto;
import com.tuniu.qms.qc.dto.RespPunishRelationDto;
import com.tuniu.qms.qc.dto.UpLoadAttachDto;
import com.tuniu.qms.qc.model.InnerRespBill;
import com.tuniu.qms.qc.model.OuterRespBill;
import com.tuniu.qms.qc.model.QualityProblem;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.model.RespPunishRelation;
import com.tuniu.qms.qc.model.UpLoadAttach;
import com.tuniu.qms.qc.service.InnerPunishBillService;
import com.tuniu.qms.qc.service.InnerRespBillService;
import com.tuniu.qms.qc.service.OuterPunishBillService;
import com.tuniu.qms.qc.service.OuterRespBillService;
import com.tuniu.qms.qc.service.QualityProblemService;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.service.RespPunishRelationService;
import com.tuniu.qms.qc.util.QcConstant;

@Service
public class QualityProblemServiceImpl implements QualityProblemService {

	@Autowired
	private QualityProblemMapper mapper;
	
	@Autowired
	private UpLoadAttachMapper upMapper;
	
	@Autowired
	QualityProblemTypeService  qptService;
	
    @Autowired
    private InnerRespBillService innerRespService;
    
    @Autowired
    private OuterRespBillService outerRespService;
    
	@Autowired
	private InnerPunishBasisMapper innerBasisMapper;
	
	@Autowired
	private OuterPunishBasisMapper outerBasisMapper;
	
	@Autowired
	private InnerPunishBillMapper innerPunishMapper;
	
	@Autowired
	private OuterPunishBillMapper outerPunishMapper;
	
	@Autowired
	private InnerPunishBillService innerPunishService;
	
	@Autowired
	private OuterPunishBillService outerPunishService;
	
	@Autowired
	private RespPunishRelationService respPunishRelationService;
	
	@Override
	public void add(QualityProblem obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QualityProblem obj) {
		mapper.update(obj);
	}

	@Override
	public QualityProblem get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<QualityProblem> list(QualityProblemDto dto) {
		
		List<QualityProblem> list = mapper.list(dto);
		for(QualityProblem qpBean :list){
			
			UpLoadAttachDto  upDto = new UpLoadAttachDto();
			upDto.setQpId(qpBean.getId());
			List<UpLoadAttach> upList = upMapper.list(upDto);
			qpBean.setUpLoadList(upList);
		}
		return list;
	}

	@Override
	public void loadPage(QualityProblemDto dto) {
	}

	/**
	 * 删除质量问题对应相关信息
	 */
	public void deleteQp(Integer qpId) {
		this.delete(qpId);//删除质量问题单
		upMapper.deleteAttach(qpId);//删除质量问题对应的附件
		
		//删除责任处罚关联关系
		List<RespPunishRelationDto> dtoList = mapper.getRespInfoByQpId(qpId);
		for(RespPunishRelationDto dto : dtoList){
			if(dto.getRespType() == QcConstant.INNER_RESP_PUN_FALG){
				innerRespService.deleteRespBill(dto.getRespId());//删除内部责任单
			}else{
				outerRespService.deleteRespBill(dto.getRespId());//删除外部责任单
			}
		}
	}

	/**
	 * 查询质量问题
	 */
	public List<QualityProblem> listQp(QualityProblemDto dto) {
		
		  List<QualityProblem> list = list(dto);
		  List<QualityProblemType> listType =  new ArrayList<QualityProblemType>();
		  if(dto.getFlag() == Constant.QC_COMPLAINT){
			  
			  listType =  qptService.getQpTypeDataCache(Constant.QC_COMPLAINT);
			  
		  }else if(dto.getFlag() == Constant.QC_OPERATE){
			  
			  listType = qptService.getQpTypeDataCache(Constant.QC_OPERATE);
		  }
		  else if(dto.getFlag() == Constant.QC_INNER){
			  
			  listType = qptService.getQpTypeDataCache(Constant.QC_INNER);
		  }
		
		  for(QualityProblem qp: list){
			  
			  String fullName =  qptService.getQpFullNameById(qp.getQptId(),listType);
			  qp.setQptName(fullName);
		  }
		return list;
	}

	/**
	 * 删除垃圾数据
	 */
	public void deleteInnerAndOuter(Integer qcId) {
		
		InnerPunishBillDto innerDto =new InnerPunishBillDto();
		innerDto.setQcId(qcId);
		innerDto.setDelFlag(QcConstant.DEL_FLAG_TRUE);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("qcId",qcId);
		map.put("delFlag", QcConstant.DEL_FLAG_TRUE);
		List<Integer> innerList = innerPunishMapper.getByQcId(map);
		map.put("innerList", innerList);
		if(innerList.size()>0){
			
			innerBasisMapper.deletePunishByIpb(map);//删除内部处罚单对应的处罚依据
		}
	
		innerPunishMapper.deleteUnUsePunish(innerDto);
	
		OuterPunishBillDto outerDto =new OuterPunishBillDto();
		outerDto.setQcId(qcId);
		outerDto.setDelFlag(QcConstant.DEL_FLAG_TRUE);
		
		List<Integer> outerList = outerPunishMapper.getByQcId(map);
		map.put("outerList", outerList);
		if(outerList.size()>0){
			
			outerBasisMapper.deletePunishByOpb(map);//删除外部处罚单对应的处罚依据
		}
		outerPunishMapper.deleteUnUsePunish(outerDto);
		
	}

	@Override
	public List<QualityProblem> listQpDetail(Integer qcId,Integer groupFlag) {
		
		 QualityProblemDto dto =new QualityProblemDto();
		 dto.setQcId(qcId);
		 List<QualityProblem> list = list(dto);
		 List<QualityProblemType> listType = new ArrayList<QualityProblemType>();
		 if(groupFlag == Constant.QC_COMPLAINT){
			 
			 listType =  qptService.getQpTypeDataCache(Constant.QC_COMPLAINT);
			 
		 }else if(groupFlag == Constant.QC_OPERATE){
			 
			 listType =  qptService.getQpTypeDataCache(Constant.QC_OPERATE);
			 
		 }else if(groupFlag == Constant.QC_INNER){
			 
			 listType =  qptService.getQpTypeDataCache(Constant.QC_INNER);
		 }
	
		 for(QualityProblem qp: list){
			  
				InnerRespBillDto innerDto =new InnerRespBillDto();
				innerDto.setQpId(qp.getId());
				List<InnerRespBill>  innerList = innerRespService.listInnerResp(innerDto);
				if(null!=innerList){
					
					qp.setInnerList(innerList);
				}
				OuterRespBillDto outerDto =new OuterRespBillDto();
				outerDto.setQpId(qp.getId());
				List<OuterRespBill>  outerList  = outerRespService.list(outerDto);
				if(null!=outerList){
					
					qp.setOuterList(outerList);
				}
			  String fullName =  qptService.getQpFullNameById(qp.getQptId(),listType);
			  qp.setQptName(fullName);
		  }
		  return list;
	}

	/**
	 * 通过质量问题单获取产品经理
	 */
	public Map<String, Object> getImpPersonByQcId(Integer qpId) {
		
		return mapper.getImpPersonByQcId(qpId);
	}

	@Override
	public void copyQualityDetails(QcDetailCopyDto qcDetailCopyDto) {
		QualityProblemDto dto =new QualityProblemDto();
		dto.setQcId(qcDetailCopyDto.getQcIdNew());
		
		List<QualityProblem> qplist = this.list(dto);
		 
		for(QualityProblem qp :qplist){
			 //添加质量问题单
			 QualityProblem quality =new QualityProblem();
			 quality.setAddPerson(qcDetailCopyDto.getAddPerson());
			 quality.setQcId(qcDetailCopyDto.getQcIdOld());
			 quality.setOldQpId(qp.getId());
			 mapper.addCopyQpBill(quality);
			 
			 qcDetailCopyDto.setQpIdOld(qp.getId());
			 qcDetailCopyDto.setQpIdNew(quality.getId());
			 
			 this.copyQualityAttach(qcDetailCopyDto);
			 
			 this.copyRespBill(qcDetailCopyDto);//复制责任单
		 }
	}
	
	/**
	 * 复制质量问题附件
	 * @param qcDetailCopyDto
	 */
	private void copyQualityAttach(QcDetailCopyDto qcDetailCopyDto) {
		Map<String, Object> qualityMap =new HashMap<String, Object>();
		 qualityMap.put("qpId", qcDetailCopyDto.getQpIdNew());
		 qualityMap.put("addPerson", qcDetailCopyDto.getAddPerson());
		 qualityMap.put("oldQpId", qcDetailCopyDto.getQpIdOld());
		 
		 upMapper.addCopyFile(qualityMap);//添加质量问题单附件
	}

	/**
	 * 复制责任单
	 * @param qcDetailCopyDto
	 * oldQpId  旧质量问题id
	 * qpId 新质量问题id
	 * realName
	 */
	private void copyRespBill(QcDetailCopyDto qcDetailCopyDto) {
		//获得责任单信息
		List<RespPunishRelationDto> respList = mapper.getRespInfoByQpId(qcDetailCopyDto.getQpIdOld());

		for(RespPunishRelationDto respBill : respList){
			qcDetailCopyDto.setRespIdOld(respBill.getRespId());
			qcDetailCopyDto.setRespType(respBill.getRespType());
			qcDetailCopyDto.setRespIdNew(getNewRespId(qcDetailCopyDto));
			
			this.copyPunishDetail(qcDetailCopyDto);
		}
	}
	
	/**
	 * 获得新责任单Id
	 * @param qcDetailCopyDto
	 * @return
	 */
	private Integer getNewRespId(QcDetailCopyDto qcDetailCopyDto) {
		
		return QcConstant.INNER_RESP_PUN_FALG == qcDetailCopyDto.getRespType() ?
				innerRespService.copyInnerRespBill(qcDetailCopyDto) : 
					outerRespService.copyOuterRespBill(qcDetailCopyDto);
	}

	/**
	 * 复制责任单下关联的处罚单
	 * @param id
	 * @param qcDetailCopyDto
	 */
	private void copyPunishDetail( QcDetailCopyDto qcDetailCopyDto) {
		
		RespPunishRelationDto dto = new RespPunishRelationDto();
		dto.setRespId(qcDetailCopyDto.getRespIdOld());
		dto.setRespType(qcDetailCopyDto.getRespType());
		List<RespPunishRelation> relationList = respPunishRelationService.list(dto);//该责任单下的处罚单
		
		for(RespPunishRelation relationBill : relationList){
			qcDetailCopyDto.setPunishIdOld(relationBill.getPunishId());
			qcDetailCopyDto.setPunishType(relationBill.getPunishType());
			qcDetailCopyDto.setPunishIdNew(getNewPunishId(qcDetailCopyDto));
			
			this.copyPunishBasis(qcDetailCopyDto);
			
			respPunishRelationService.addRespPunishRelation(qcDetailCopyDto);
		}
		
	}
	
	/**
	 * 获得新处罚单id
	 * @param qcDetailCopyDto
	 * @return
	 */
	private Integer getNewPunishId(QcDetailCopyDto qcDetailCopyDto) {
		
		return QcConstant.INNER_RESP_PUN_FALG == qcDetailCopyDto.getPunishType() ?
				innerPunishService.copyInnerPunishBill(qcDetailCopyDto) :
					outerPunishService.copyOuterPunishBill(qcDetailCopyDto);
	}

	/**
	 * 添加处罚依据
	 * @param qcDetailCopyDto
	 */
	private void copyPunishBasis(QcDetailCopyDto qcDetailCopyDto) {
		 Map<String, Object> basisMap =new HashMap<String, Object>();
		 basisMap.put("punishId", qcDetailCopyDto.getPunishIdNew());
		 basisMap.put("addPerson", qcDetailCopyDto.getAddPerson());
		 basisMap.put("oldPunishId", qcDetailCopyDto.getPunishIdOld());
		 
		 if(QcConstant.INNER_RESP_PUN_FALG == qcDetailCopyDto.getPunishType()){
			 innerBasisMapper.addCopyInnerBasis(basisMap);
		 }else{
			 outerBasisMapper.addCopyOuterBasis(basisMap);
		 }
		 
	}

}
