package com.tuniu.gt.complaint.service;

import java.util.List;

import tuniu.frm.core.IServiceBase;
public interface IReasonTypeService extends IServiceBase {

    /**
     * @param parentName
     * @return
     */
    List<String> getChilderByParentName(String parentName);

}
