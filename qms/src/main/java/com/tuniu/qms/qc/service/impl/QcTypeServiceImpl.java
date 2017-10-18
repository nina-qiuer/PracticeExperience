package com.tuniu.qms.qc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.CacheConstant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dao.QcTypeMapper;
import com.tuniu.qms.qc.dto.QcTypeDto;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.QcTypeService;
import com.whalin.MemCached.MemCachedClient;

@Service
public class QcTypeServiceImpl implements QcTypeService {

	@Autowired
	private QcTypeMapper mapper;

	@Autowired
	private MemCachedClient memCachedClient;
	@Override
	public void add(QcType obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(QcType obj) {
		mapper.update(obj);
	}

	@Override
	public QcType get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<QcType> list(QcTypeDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(QcTypeDto dto) {
		
	}

	@Override
	public List<QcType> getTypeFullNameList() {
		List<QcType> typeList = mapper.list(new QcTypeDto());
		for (QcType qt : typeList) {
			if (qt.getPid()> 1) {
				String fatherFullName = getFatherType(typeList, qt).getFullName();
				qt.setFullName(fatherFullName + "-" + qt.getName());
			} else {
				qt.setFullName(qt.getName());
			}
		}
		return typeList;
	}
	
	private QcType getFatherType(List<QcType> typeList, QcType qt) {
		QcType fatherDep = null;
		for (QcType depTemp : typeList) {
			if (qt.getPid().equals(depTemp.getId())) {
				fatherDep = depTemp;
				break;
			}
		}
		return fatherDep;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QcType> getQcTypeDataCache() {
		List<QcType> qcList = (List<QcType>) memCachedClient.get(CacheConstant.QT_DATA);
		if (null == qcList || qcList.isEmpty()) {
			qcList = getTypeFullNameList();
			memCachedClient.set(CacheConstant.QT_DATA, qcList, DateUtil.getTodaySurplusTime());
		}
		return qcList;
	}

	@Override
	public QcType getTypeByFullName(String fullName) {
		QcType qt = new QcType();
		int pid = 1;
		String[] qtNames = fullName.split("-");
		for (String qtName : qtNames) {
			QcTypeDto dto = new QcTypeDto();
			dto.setQtName(qtName);
			dto.setPid(pid);
			qt = mapper.getQtByFullName(dto);
			if(qt ==null){
				
				return qt;
			}
			pid = qt.getId();
		}
		return qt;
	}
	
	
	public String getQtFullNameById(int qtId,List<QcType> list) {
		
		String fullName ="";
		try {
			
			for(QcType qcType :list){
				
				if(qcType.getId() == qtId){
					
					fullName = qcType.getFullName();
					break;
					
				}
				
			}
			
		} catch (Exception e) {
			
		}
		
		return fullName;
	}

	@Override
	public QcType getTypeName(String name) {
		
		
		return mapper.getTypeName(name);
	}

	@Override
	public int getQcTypeCount(Integer id) {
		
		return mapper.getQcTypeCount(id);
	}

	@Override
	public List<QcType> getQcTypeByName(String name) {
		
		
		return mapper.getQcTypeByName(name);
	}

	@Override
	public QcType getQtByFullName(QcTypeDto dto) {
		
		return mapper.getQtByFullName(dto);
	}
	
	/**
	 * 获得质检类型全名
	 * 
	 */
	@Override
	public QcType getQcTypeByFullName(String qcName) {
		QcType qcType = new QcType();
		String[] qcTypeNames = qcName.split("-");
		int pid = 1;
		for (String qcTypeName : qcTypeNames) {
			QcTypeDto dto = new QcTypeDto();
			dto.setQtName(qcTypeName);
			dto.setPid(pid);
			qcType = mapper.getQtByFullName(dto);
			if(qcType ==null){
				
				return qcType;
			}
			pid = qcType.getId();
		}
		return qcType;
	}
}
