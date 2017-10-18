package com.tuniu.qms.qc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.CacheConstant;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dao.QualityProblemTypeMapper;
import com.tuniu.qms.qc.dto.QualityProblemTypeDto;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.service.QualityProblemTypeService;
import com.tuniu.qms.qc.util.QcConstant;
import com.whalin.MemCached.MemCachedClient;

@Service
public class QualityProblemTypeServiceImpl implements QualityProblemTypeService {

	@Autowired
	private QualityProblemTypeMapper mapper;
	
	@Autowired
	private MemCachedClient memCachedClient;
	
	@Override
	public void add(QualityProblemType obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QualityProblemType obj) {
		mapper.update(obj);
	}

	@Override
	public QualityProblemType get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<QualityProblemType> list(QualityProblemTypeDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QualityProblemTypeDto dto) {
		
	}

	public List<QualityProblemType> getQpTypeByFlag(Integer qcFlag){
		
		List<QualityProblemType> qpList  = getQpTypeFullNameList(qcFlag);
		
		if(null == qpList){
			return new ArrayList<QualityProblemType>();
		}
		
		for(int i= 0; i<qpList.size(); i++){
			int count = mapper.getQpTypeCount(qpList.get(i).getId());
			if(count>0){
				qpList.remove(i);
				i--;
			}
		}
		
		if(qcFlag == QcConstant.QC_COMPLAINT){
			List<QualityProblemType> notypeList  = getNoType();
		    for(int i =0 ;i<qpList.size();i++){
		    	for(int j = 0;j<notypeList.size();j++){
		    		if(qpList.get(i).getName().equals(notypeList.get(j).getName())){
		    			
		    			qpList.remove(i);
		    			i--;
		    		}
		    	}
		    }
		}
		
		return qpList;
	}
	
	private List<QualityProblemType> getNoType(){
		List<QualityProblemType> qpList =new ArrayList<QualityProblemType>();
		QualityProblemTypeDto dto =	new QualityProblemTypeDto();
		dto.setCmpQcUse(Constant.QC_USE);
		dto.setName(QcConstant.QP_TYPE);//非质量问题
		List<QualityProblemType> qpTypeList = this.list(dto);//查询第一级
		QualityProblemType qpType = qpTypeList.get(0);
		qpList.add(qpType);
		QualityProblemTypeDto dto1 =	new QualityProblemTypeDto();
		dto1.setCmpQcUse(Constant.QC_USE);
		dto1.setPid(qpType.getId());
		List<QualityProblemType> list1 = this.list(dto1);//查询第二级
		for(QualityProblemType qp_type:list1){
			
			qpList.add(qp_type);
			QualityProblemTypeDto dto2 = new QualityProblemTypeDto();
			dto2.setCmpQcUse(Constant.QC_USE);
			dto2.setPid(qp_type.getId());
			List<QualityProblemType> list2 = this.list(dto2);//查询第三级
			for(QualityProblemType qp_type2:list2){
				
				qpList.add(qp_type2);
				
			}
		}
		return qpList;
	}
	
	public List<QualityProblemType> getQueryQpTypeByFlag(Integer qcFlag){
		
		List<QualityProblemType> qpList =new ArrayList<QualityProblemType>();
		qpList  = getQpTypeFullNameList(qcFlag);
		return qpList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<QualityProblemType> getQpTypeDataCache(Integer qcFlag) {
		
		  List<QualityProblemType> qpList =new ArrayList<QualityProblemType>();
		  if(qcFlag == Constant.QC_COMPLAINT){//投诉质检
			   
			 qpList = (List<QualityProblemType>) memCachedClient.get(CacheConstant.QP_CMP_DATA);
			   
		   }else if(qcFlag == Constant.QC_DEVELOP){//研发质检
			   
			   qpList = (List<QualityProblemType>) memCachedClient.get(CacheConstant.QP_DEV_DATA);
			   
		   }else if(qcFlag == Constant.QC_OPERATE){//运营质检
			   
			   qpList = (List<QualityProblemType>) memCachedClient.get(CacheConstant.QP_OPE_DATA);
		   }else if(qcFlag == Constant.QC_INNER){//内部质检
			   
			   qpList = (List<QualityProblemType>) memCachedClient.get(CacheConstant.QP_INNER_DATA);
		   }
			
			if (null == qpList || qpList.isEmpty()) {
				qpList  = getQpTypeFullNameList(qcFlag);
				for(int i= 0;i<qpList.size();i++){
					
					int count = mapper.getQpTypeCount(qpList.get(i).getId());
					if(count>0){
						
						qpList.remove(i);
						i--;
					}
				}
				if(qcFlag == Constant.QC_COMPLAINT){//投诉质检
					
					memCachedClient.set(CacheConstant.QP_CMP_DATA, qpList, DateUtil.getTodaySurplusTime());
					
				}else if(qcFlag == Constant.QC_DEVELOP){//研发质检
					   
					memCachedClient.set(CacheConstant.QP_DEV_DATA, qpList, DateUtil.getTodaySurplusTime());
					   
				 }else if(qcFlag == Constant.QC_OPERATE){//运营质检
					   
					memCachedClient.set(CacheConstant.QP_OPE_DATA, qpList, DateUtil.getTodaySurplusTime());
				 }else if(qcFlag == Constant.QC_INNER){//内部检
					   
					memCachedClient.set(CacheConstant.QP_INNER_DATA, qpList, DateUtil.getTodaySurplusTime());
				 }
				
			}
			return qpList;
			
		}

	@Override
	public List<QualityProblemType> getQpTypeFullNameList(Integer qcFlag) {
		QualityProblemTypeDto dto =new QualityProblemTypeDto();
		if(qcFlag == Constant.QC_COMPLAINT){//投诉质检
			
			dto.setCmpQcUse(Constant.QC_USE);
			
		}else if(qcFlag == Constant.QC_DEVELOP){//研发质检
			   
			dto.setDevQcUse(Constant.QC_USE);
			   
		 }else if(qcFlag == Constant.QC_OPERATE){//运营质检
			   
			 dto.setOperQcUse(Constant.QC_USE);
			 
		 }else if(qcFlag == Constant.QC_INNER){//内部质检
			   
			 dto.setInnerQcUse(Constant.QC_USE);
		 }
		List<QualityProblemType> qpList = mapper.list(dto);
		for (QualityProblemType qp : qpList) {
			if (qp.getPid() >1) {
				String fatherFullName = getFatherQp(qpList, qp).getFullName();
				qp.setFullName(fatherFullName + "-" + qp.getName());
			} else {
				qp.setFullName(qp.getName());
			}
		}
		return qpList;
	}
	
	
	private QualityProblemType getFatherQp(List<QualityProblemType> qpList, QualityProblemType qp) {
		QualityProblemType fatherQp = null;
		for (QualityProblemType qpTemp : qpList) {
			if (qp.getPid().equals(qpTemp.getId())) {
				fatherQp = qpTemp;
				break;
			}
		}
		return fatherQp;
	}

	public QualityProblemType getQpTypeByFullName(String fullName) {
		QualityProblemType qpType = new QualityProblemType();
		String[] qpTypeNames = fullName.split("-");
		String qptLv1Name = qpTypeNames[0];
		int pid = 1;
		for (String qpTypeName : qpTypeNames) {
			QualityProblemTypeDto dto = new QualityProblemTypeDto();
			dto.setQpTypeName(qpTypeName);
			dto.setPid(pid);
			qpType = mapper.getQpTypeByFullName(dto);
			if(qpType ==null){
				
				return qpType;
			}
			pid = qpType.getId();
		}
		int count = mapper.getQpTypeCount(qpType.getId());
		if(count>0){
			
			qpType =null;
			return qpType;
		}
		//获取质量问题的一级问题分类
		QualityProblemTypeDto lvdto = new QualityProblemTypeDto();
		lvdto.setQpTypeName(qptLv1Name);
		lvdto.setPid(1);
		QualityProblemType lvQpType = mapper.getQpTypeByFullName(lvdto);
		if( null != lvQpType){
				
			qpType.setQptLv1Id(lvQpType.getId());
		}
		return qpType;
	}

	public QualityProblemType getQpTypeFullName(String fullName) {
		QualityProblemType qpType = new QualityProblemType();
		String[] qpTypeNames = fullName.split("-");
		int pid = 1;
		for (String qpTypeName : qpTypeNames) {
			QualityProblemTypeDto dto = new QualityProblemTypeDto();
			dto.setQpTypeName(qpTypeName);
			dto.setPid(pid);
			qpType = mapper.getQpTypeByFullName(dto);
			if(qpType ==null){
				
				return qpType;
			}
			pid = qpType.getId();
		}
		return qpType;
	}

	
	public String getQpFullNameById(int qptId,List<QualityProblemType> list) {
		
		String fullName ="";
		try {
			
			for(QualityProblemType qpType :list){
				
				if(qpType.getId() == qptId){
					
					fullName = qpType.getFullName();
					break;
					
				}
				
			}
		} catch (Exception e) {
			
		}
	
		return fullName;
	}

	@Override
	public QualityProblemType getQpType(int qptId,
			List<QualityProblemType> list) {
		 try {
			
			for(QualityProblemType qpType :list){
				
				if(qpType.getId() == qptId){
					
					return qpType;
					
				}
				
			}
		} catch (Exception e) {
			
		}
		return null;
	}

}
