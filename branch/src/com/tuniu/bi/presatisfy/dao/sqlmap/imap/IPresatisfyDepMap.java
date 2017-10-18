package com.tuniu.bi.presatisfy.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.bi.presatisfy.entity.PresatisfyEntity;

@Repository("bi_dao_sqlmap-presatisfydep")
public interface IPresatisfyDepMap extends IMapBase{
	
	/**
	 * 插入来自bi的售前满意度字段
	 * @return null
	 */
	void addPresatisfyDep(PresatisfyEntity presatisfy);

	public List<PresatisfyEntity> getPresatisfyDep(Map<String, Object> paramMap);
	
	Integer getPresatisfyDepCount();
	
	public List<Integer> getPresatisfyContactId();
	
	public int queryPresatisfyListCount(Map<String, Object> paramMap);
	
	void updatePresatisfyDep(PresatisfyEntity presatisfy);
}
