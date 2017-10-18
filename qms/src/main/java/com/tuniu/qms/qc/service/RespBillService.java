package com.tuniu.qms.qc.service;

import java.util.Map;

import com.tuniu.qms.common.util.Page;

public interface RespBillService {

	/**
	 * 查询责任单列表
	 * @param map
	 * @return
	 */
    public Page queryRespBillList(Map<String, Object> map);	
    
    /**
     * 删除责任单
     * @param map
     */
    public int deleteResp(Map<String, Object> map);
}
