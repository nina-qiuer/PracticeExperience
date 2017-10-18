package com.tuniu.bi.presatisfy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.bi.presatisfy.dao.impl.PresatisfyDepDao;
import com.tuniu.bi.presatisfy.entity.PresatisfyEntity;
import com.tuniu.bi.presatisfy.service.IPreSatisfyDepService;

@Service("bi_service_presatisfydep_impl-presatisfydep")
public class PresatisfyDepServiceImpl extends ServiceBaseImpl<PresatisfyDepDao> implements IPreSatisfyDepService {
	
	/*@Autowired
	@Qualifier("bi_dao_impl-presatisfydep")
	private PresatisfyDepDao dao;
*/
	@Autowired
	@Qualifier("bi_dao_impl-presatisfydep")
	public void setDao(PresatisfyDepDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void addPresatisfyDep(PresatisfyEntity presatisfy) {
		dao.addPresatisfyDep(presatisfy);
	}
	
	@Override
	public List<PresatisfyEntity> getPresatisfyDep(Map<String, Object> paramMap){
		return dao.getPresatisfyDep(paramMap);
	}
	
	@Override
	public Integer getPresatisfyDepCount(){
		return dao.getPresatisfyDepCount();
	}
	
	@Override
	public List<Integer> getPresatisfyContactId(){
		return dao.getPresatisfyContactId();
	}
	
	@Override
	public int queryPresatisfyListCount(Map<String, Object> paramMap){
		return dao.queryPresatisfyListCount(paramMap);
	}
	
	@Override
	public void updatePresatisfyDep(PresatisfyEntity presatisfy){
		dao.updatePresatisfyDep(presatisfy);
	}
}

