package com.tuniu.qms.qs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.qs.dao.QualityCostAuxAccountMapper;
import com.tuniu.qms.qs.dao.RespDepartmentMapper;
import com.tuniu.qms.qs.dto.RespDepartmentDto;
import com.tuniu.qms.qs.model.DepRespRate;
import com.tuniu.qms.qs.model.QualityCostAuxAccount;
import com.tuniu.qms.qs.model.RespDepartment;
import com.tuniu.qms.qs.service.RespDepartmentService;

@Service
public class RespDepartmentServiceImpl implements RespDepartmentService {

	@Autowired
	private RespDepartmentMapper mapper;
	
	@Autowired
	private DepartmentService depService;
	
	@Autowired
	private QualityCostAuxAccountMapper auxMapper;

	@Override
	public void add(RespDepartment obj) {
		
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
	}

	@Override
	public void update(RespDepartment obj) {
		
		mapper.update(obj);
	}


	@Override
	public RespDepartment get(Integer id) {
		return mapper.get(id);
	}


	@Override
	public List<RespDepartment> list(RespDepartmentDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(RespDepartmentDto dto) {
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}

	@Override
	public void addBatch(List<QualityCostAuxAccount> list) {
		
		mapper.addBatch(list);
	}

	@Override
	public void addIsNotExist(RespDepartment dep) {
		
		mapper.addIsNotExist(dep);
		
	}

	@Override
	public void updateDep(List<DepRespRate> list,User user) {
		
		Integer id = Integer.parseInt(list.get(0).getId());
		RespDepartment respDep = mapper.get(id);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("firstDepId", respDep.getFirstDepId());
		map.put("twoDepId", respDep.getTwoDepId());
		map.put("threeDepId", respDep.getThreeDepId());
		List<QualityCostAuxAccount> auxList = auxMapper.listByDep(map);
		
		for(QualityCostAuxAccount aux : auxList){
			
			for(DepRespRate dep :list){
				
				QualityCostAuxAccount costAux = new QualityCostAuxAccount();
				costAux.setIspId(aux.getIspId());
				costAux.setQcId(aux.getQcId());
				costAux.setCmpId(aux.getCmpId());
				costAux.setFirstCostAccount(aux.getFirstCostAccount());
				costAux.setTwoCostAccount(aux.getTwoCostAccount());
				costAux.setThreeCostAccount(aux.getThreeCostAccount());
				costAux.setFourCostAccount(aux.getFourCostAccount());
				costAux.setAddPerson(aux.getAddPerson());
				costAux.setDealPersonId(user.getId());
				costAux.setDealPersonName(user.getRealName());
				costAux.setAddTime(aux.getAddTime());
				String []depNames = dep.getDepName().split("-");
		     	Department depart =  depService.getDepByFullName(dep.getDepName());
		     	
		     	if(depNames.length==1){
       				
		     		costAux.setFirstDepId(depart.getId());
		     		costAux.setFirstDepName(depNames[0]);
		     		costAux.setTwoDepId(0);
		     		costAux.setTwoDepName("");
		     		costAux.setThreeDepId(0);
		     		costAux.setThreeDepName("");
       			}
				if(depNames.length==2){
			       				
					costAux.setTwoDepId(depart.getId());
					costAux.setTwoDepName(depNames[1]);
			       	costAux.setFirstDepId(depart.getPid());
			       	costAux.setFirstDepName(depNames[0]);
			       	costAux.setThreeDepId(0);
			       	costAux.setThreeDepName("");
			     }
				if(depNames.length>=3){
						
					costAux.setThreeDepId(depart.getId());
					costAux.setThreeDepName(depNames[depNames.length-1]);
				   	costAux.setTwoDepId(depart.getPid());
				   	costAux.setTwoDepName(depNames[depNames.length-2]);
			       	Department tdep = depService.get(depart.getPid());
			       	costAux.setFirstDepId(tdep.getPid());
			       	costAux.setFirstDepName(depNames[depNames.length-3]);
				}
				Double respRate = aux.getRespRate();
				Double newRespRate = MathUtil.div( MathUtil.mul(respRate,Double.parseDouble(dep.getRespRate())),100,2);
				Double qualityCost =  MathUtil.div( MathUtil.mul(aux.getQualityCost(),newRespRate),respRate,2);
				
				costAux.setRespRate(newRespRate);
				costAux.setQualityCost(qualityCost);
				auxMapper.add(costAux);
			}
			auxMapper.delete(aux.getId());
			
		}
	
		mapper.delete(id);
		
	}



}
