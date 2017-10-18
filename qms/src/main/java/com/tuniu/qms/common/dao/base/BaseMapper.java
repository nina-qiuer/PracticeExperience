package com.tuniu.qms.common.dao.base;

import java.util.List;

public interface BaseMapper<Obj, Dto> {
	
	void add(Obj obj);
	
	void delete(Integer id);
	
	void update(Obj obj);
	
	Obj get(Integer id);
	
	int count(Dto dto);
	
	List<Obj> list(Dto dto);
	
}
