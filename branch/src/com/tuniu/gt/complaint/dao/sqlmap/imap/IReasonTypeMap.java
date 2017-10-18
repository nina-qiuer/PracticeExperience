package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

@Repository("complaint_dao_sqlmap-reason_type")
public interface IReasonTypeMap extends IMapBase {

    /**
     * @param parentName
     * @return
     */
    List<String> getChilderByParentName(String parentName); 


}
