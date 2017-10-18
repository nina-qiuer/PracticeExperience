package com.tuniu.qms.common.service.base;

import java.util.List;

public interface BaseService<Obj, Dto> {
	
	void add(Obj obj);
	
	void delete(Integer id);
	
	void update(Obj obj);
	
	Obj get(Integer id);
	
	List<Obj> list(Dto dto);
	
	void loadPage(Dto dto);
	
}
