package com.tuniu.gt.complaint.service;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.ComplaintPointEntity;

import tuniu.frm.core.IServiceBase;
public interface IComplaintPointService extends IServiceBase {

    /**
     * @param affectivePointList
     */
    void batchInsert(List<ComplaintPointEntity> affectivePointList);

    /**
     * @param paramMap
     * @return
     */
    List<ComplaintPointEntity> fetchInitList(Map<String, Object> paramMap);
    
    /**
     * 获得固定时间存在有效理论赔付的投诉id
     * @param map
     * @return
     */
	List<Integer> getTheoryPayout(Map<String, Object> map);

}
