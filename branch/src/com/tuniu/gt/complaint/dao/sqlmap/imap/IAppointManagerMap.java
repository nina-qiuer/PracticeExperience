package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.AppointManagerEntity;

import tuniu.frm.core.IMapBase;

@Repository("complaint_dao_sqlmap-appoint_manager")
public interface IAppointManagerMap extends IMapBase { 
    
    /**
     * 根据参数获取记录数量
     * @param paramMap
     * @return
     */
    int count(Map paramMap);

	List<AppointManagerEntity> getVipDepartGroupList(Map<String, Object> paramMap);
}
