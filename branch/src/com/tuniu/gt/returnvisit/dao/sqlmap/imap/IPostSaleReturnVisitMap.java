package com.tuniu.gt.returnvisit.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.returnvisit.vo.ReportResultSetVo;

@Repository("returnVisit_dao_sqlmap-postSaleReturnVisit")
public interface IPostSaleReturnVisitMap extends IMapBase {
    
    Integer getCount(Map<String,Object> paramMap);
    
    Integer getIdByParam(Map<String,Object>  paramMap);
    
    List<ReportResultSetVo> getReport(Map<String,Object>  paramMap);

    /**
     * @param paramMap
     * @return
     */
    List<ComplaintEntity> getComplaintList(Map<String, Object> paramMap);
}
