package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.ComplaintPointEntity;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-complaint_point")
public interface IComplaintPointMap extends IMapBase {

    /**
     * @param affectivePointList
     */
    void batchInsert(List<ComplaintPointEntity> affectivePointList);
    
    /**
     * 获得固定时间存在能有效理论赔付的投诉id
     * @param map
     * @return
     */
	List<Integer> getTheoryPayout(Map<String, Object> map); 

}
