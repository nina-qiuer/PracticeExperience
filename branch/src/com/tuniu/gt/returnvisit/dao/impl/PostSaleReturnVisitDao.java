package com.tuniu.gt.returnvisit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.returnvisit.dao.sqlmap.imap.IPostSaleReturnVisitMap;
import com.tuniu.gt.returnvisit.entity.PostSaleReturnVisitEntity;
import com.tuniu.gt.returnvisit.vo.ReportResultSetVo;

@Repository("returnVisit_dao_impl-postSaleReturnVisit")
public class PostSaleReturnVisitDao  extends DaoBase<PostSaleReturnVisitEntity, IPostSaleReturnVisitMap>  implements IPostSaleReturnVisitMap {

	public PostSaleReturnVisitDao() {  
		tableName ="rv_post_sale  ";
	}
	
	@Autowired
	@Qualifier("returnVisit_dao_sqlmap-postSaleReturnVisit")
	public void setMapper(IPostSaleReturnVisitMap mapper) {
		this.mapper = mapper;
	}

    @Override
    public Integer getIdByParam(Map<String, Object> paramMap) {
        return mapper.getIdByParam(paramMap);
    }

    public Integer getCount(Integer complaintId, String dealDepart) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("complaintId", complaintId);
        paramMap.put("dealDepart", dealDepart);
        return mapper.getCount(paramMap);
    }
    
    public Integer getCount(Map<String, Object> paramMap) {
        return mapper.getCount(paramMap);
    }


    @Override
    public List<ReportResultSetVo> getReport(Map<String, Object> paramMap) {
        return mapper.getReport(paramMap);
    }
    
    public List<ComplaintEntity> getComplaintList(Map<String, Object> paramMap){
        return mapper.getComplaintList(paramMap);
    }
    
}
