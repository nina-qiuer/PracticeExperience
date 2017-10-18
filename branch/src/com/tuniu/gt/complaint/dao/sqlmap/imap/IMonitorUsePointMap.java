package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.MonitorUsePointEntity;

@Repository("complaint_dao_sqlmap-monitor_use_point")
public interface IMonitorUsePointMap extends IMapBase {
	
	Integer insertMonitorInfo(MonitorUsePointEntity MonitorUsePointEntity);
	
	Map<String,Object> getUseDeptInfo(Map param);

}
