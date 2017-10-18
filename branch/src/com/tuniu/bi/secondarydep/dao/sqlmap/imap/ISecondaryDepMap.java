package com.tuniu.bi.secondarydep.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("bi_dao_sqlmap-secondarydep")
public interface ISecondaryDepMap {
	
	/**
	 * 通过产品线ID查询二级部门
	 * @return 一级部门名称、二级部门名称
	 */
	Map<String, Object> getSecondaryDep(int productLineId);

}
