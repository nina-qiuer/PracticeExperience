package com.tuniu.gt.complaint.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import tuniu.frm.service.Bean;

import com.tuniu.gt.TestCaseExtend;
import com.tuniu.gt.complaint.entity.QcNoteEntity;
import com.tuniu.gt.uc.service.user.impl.DepartmentServiceImpl;

public class DepartmentServiceImplTest extends TestCaseExtend {
	
	DepartmentServiceImpl departmentServiceImpl = (DepartmentServiceImpl)Bean.get("uc_service_user_impl-department");
	
	
	@Test
	public void testInsert() {
	
		assertNotNull(departmentServiceImpl);
		
		Map<String, Object> otherParamMap = new HashMap<String, Object>();
		otherParamMap.put("useDept", 0);
		departmentServiceImpl.batchUpdateUseDeptForQc(otherParamMap);
	}

}
