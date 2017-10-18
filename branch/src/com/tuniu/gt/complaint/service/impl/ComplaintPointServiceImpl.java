package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintPointDao;
import com.tuniu.gt.complaint.entity.ComplaintPointEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.service.IComplaintPointService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.toolkit.lang.CollectionUtil;

@Service("complaint_service_complaint_reason_impl-complaint_point")
public class ComplaintPointServiceImpl extends ServiceBaseImpl<ComplaintPointDao> implements IComplaintPointService {
	
    @Autowired
    @Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
    private IComplaintReasonService reasonService;
    
    
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_point")
	public void setDao(ComplaintPointDao dao) {
		this.dao = dao;
	}
	

    @Override
    public void batchInsert(List<ComplaintPointEntity> affectivePointList) {
        dao.batchInsert(affectivePointList);
    }

    @Override
    public List<ComplaintPointEntity> fetchInitList(Map<String, Object> paramMap) {
        List<ComplaintPointEntity> pointList = new ArrayList<ComplaintPointEntity>();
        //从投诉点表中取
        pointList = (List<ComplaintPointEntity>) fetchList(paramMap);

        if(CollectionUtil.isEmpty(pointList)){         //2.如果没有从投诉事宜里面取
            List<ComplaintReasonEntity> reasonList = (List<ComplaintReasonEntity>) reasonService.fetchList(paramMap);
            pointList = ComplaintPointEntity.fromComplaintReasonList(reasonList);
        }
        
        return pointList;
    }


	@Override
	public List<Integer> getTheoryPayout(Map<String, Object> map) {
		return dao.getTheoryPayout(map);
	}

}
