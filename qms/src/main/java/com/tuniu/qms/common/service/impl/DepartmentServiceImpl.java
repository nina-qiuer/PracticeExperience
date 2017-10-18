package com.tuniu.qms.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.dao.DepartmentMapper;
import com.tuniu.qms.common.dto.DepartmentDto;
import com.tuniu.qms.common.dto.DepartmentJobDto;
import com.tuniu.qms.common.dto.DepartmentUserDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.util.CacheConstant;
import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.whalin.MemCached.MemCachedClient;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentMapper mapper;
	
	@Autowired
	private MemCachedClient memCachedClient;

	@Override
	public void add(Department dep) {
		mapper.add(dep);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Department dep) {
		mapper.update(dep);
	}

	@Override
	public Department get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<Department> list(DepartmentDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void addDepUser(DepartmentUserDto dto) {
		mapper.addDepUser(dto);
	}

	@Override
	public void updateDepUser(DepartmentUserDto dto) {
		mapper.updateDepUser(dto);
	}

	@Override
	public void addDepJob(DepartmentJobDto dto) {
		mapper.addDepJob(dto);
	}

	@Override
	public void updateDepJob(DepartmentJobDto dto) {
		mapper.updateDepJob(dto);
	}

	@Override
	public void loadPage(DepartmentDto dto) {
		
	}

	@Override
	public List<Department> getDepFullNameList() {
		List<Department> depList = mapper.list(new DepartmentDto());
		for (Department dep : depList) {
			if (dep.getDepth() > 2) {
				Department fatherDep = getFatherDep(depList, dep);
				if (null != fatherDep) {
					String fatherFullName = fatherDep.getFullName();
					dep.setFullName(fatherFullName + "-" + dep.getName());
				}
			} else {
				dep.setFullName(dep.getName());
			}
		}
		return depList;
	}
	
	@Override
	public List<Department> getAllDepFullNameList() {
		
		List<Department> depList = mapper.listAll(new DepartmentDto());
		for (Department dep : depList) {
			if (dep.getDepth() > 2) {
				Department fatherDep = getFatherDep(depList, dep);
				if (null != fatherDep) {
					String fatherFullName = fatherDep.getFullName();
					dep.setFullName(fatherFullName + "-" + dep.getName());
				}
			} else {
				dep.setFullName(dep.getName());
			}
		}
		return depList;
	}
	
	
	
	private Department getFatherDep(List<Department> depList, Department dep) {
		Department fatherDep = null;
		for (Department depTemp : depList) {
			if (dep.getPid().equals(depTemp.getId())) {
				fatherDep = depTemp;
				break;
			}
		}
		return fatherDep;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getDepDataCache() {
		List<Department> depList = (List<Department>) memCachedClient.get(CacheConstant.DEP_DATA);
		if (null == depList || depList.isEmpty()) {
			depList = getDepFullNameList();
			memCachedClient.set(CacheConstant.DEP_DATA, depList, DateUtil.getTodaySurplusTime());
		}
		return depList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getAllDepDataCache() {
		List<Department> depList = (List<Department>) memCachedClient.get(CacheConstant.DEP_ALL_DATA);
		if (null == depList || depList.isEmpty()) {
			depList = getAllDepFullNameList();
			memCachedClient.set(CacheConstant.DEP_ALL_DATA, depList, DateUtil.getTodaySurplusTime());
		}
		return depList;
	}
	
	@Override
	public Department getDepByFullName(String fullName) {
		Department dep = new Department();
		int pid = 1;
		String[] depNames = fullName.split("-");
		for (String depName : depNames) {
			DepartmentDto dto = new DepartmentDto();
			dto.setDepName(depName);
			dto.setPid(pid);
			dep = mapper.getDepByNameAndPid(dto);
			if(dep ==null){
				
				return dep;
			}
			pid = dep.getId();
		}
		return dep;
	}

	@Override
	public void deleteDepUser(Integer id) {
		mapper.deleteDepUser(id);
	}

	@Override
	public void updateCmpQcUseFlag(String[] checkedIds, String[] unCheckedIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != checkedIds && checkedIds.length > 0) {
			map.put("cmpQcUseFlag", Constant.YES);
			map.put("ids", CommonUtil.arrToStr(checkedIds));
			mapper.updateCmpQcUseFlag(map);
		}
		if (null != unCheckedIds && unCheckedIds.length > 0) {
			map.put("cmpQcUseFlag", Constant.NO);
			map.put("ids", CommonUtil.arrToStr(unCheckedIds));
			mapper.updateCmpQcUseFlag(map);
		}
	}

	@Override
	public String getDepFullNameById(int depId) {
		
		if(depId == 0){
			return "";
		}
		
		String fullName ="";
		List<Department> list =  getAllDepDataCache();
		for(Department dep :list){
			
			if(dep.getId() == depId){
				
				fullName = dep.getFullName();
				break;
				
			}
			
		}
		return fullName;
	}

	/*@Override
	public String getDepFullName(int depId,List<Department> list) {
		
		String fullName ="";
		for(Department dep :list){
			
			if(dep.getId() == depId){
				
				fullName = dep.getFullName();
				break;
			}
		}
		return fullName;
	}*/

	@Override
	public Department getDepById(int  depId) {
	
		
		return mapper.getDepById(depId);
	}

	@Override
	public List<Department> getUseDepData(DepartmentDto dto) {
		
		
		return mapper.getUseDepData(dto);
	}

	@Override
	public void updateDepCmpQcUseFlag(Map<String, Object> map) {
	
		mapper.updateDepCmpQcUseFlag(map);
		
	}

	@Override
	public void updateDep(Department dep) {
		
		mapper.updateDep(dep);
		
	}

	@Override
	public List<Department> getDepDetailList(Map<String, Object> map) {
		
		return mapper.getDepDetailList(map);
	}

	@Override
	public Department getDepByName(Map<String, Object> map) {

		return mapper.getDepByName(map);
	}

	@Override
	public Department getDepByNameAndPid(DepartmentDto dto) {
		
		return mapper.getDepByNameAndPid(dto);
	}
	
	@Override
	public Stack<Department> getDepartmentList(Integer depId){
		Stack<Department> dataStack = new Stack<Department>();
		
		List<Department> depList = this.getAllDepDataCache();
		Department dep = getDepById(depId);
		while(dep != null && dep.getDepth() > 1){
			dataStack.push(dep);
			dep = this.getFatherDep(depList, dep);
		}
		return dataStack;
	}
	
}
