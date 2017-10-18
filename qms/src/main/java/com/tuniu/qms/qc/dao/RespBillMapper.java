package com.tuniu.qms.qc.dao;

import java.util.List;
import java.util.Map;

import com.tuniu.qms.qc.model.RespBill;

public interface RespBillMapper {

	 /**
	  * 查询责任单列表
	  * @param map
	  * @return
	  */
	 List<RespBill> queryRespBillList(Map<String, Object> map); 
	
	 /**
	  * 查询责任单列表数量
	  * @param map
	  * @return
	  */
	 int queryRespBillCount(Map<String, Object> map);
	 
	 /**
	  * 删除责任单
	  * @param map
	  * @return
	  */
	 int deleteResp(Map<String, Object> map);
}
