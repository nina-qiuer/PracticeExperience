package com.tuniu.bi.secondarydep.service;

import java.util.Map;

public interface ISecondaryDepService {
	
	/**
	 * 通过产品线ID查询二级部门
	 * @return 一级部门名称、二级部门名称
	 */
	Map<String, Object> getSecondaryDep(int productLineId);

}
