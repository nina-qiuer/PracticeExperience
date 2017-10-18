/*
 * Copyright (C) 2006-2017 Tuniu All rights reserved
 * Author:fangyouming
 * Date：2017年7月24日
 * Description: 功能描述
 */
package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.ComplaintSolutionEmailEntity;

/**
 * IComplaintSolutionEmailMap.java
 *
 * @author fangyouming
 */
@Repository("complaint_dao_sqlmap-complaint_solution_email")
public interface IComplaintSolutionEmailMap extends IMapBase {

    ComplaintSolutionEmailEntity getBySolutionId(Integer solutionId);

    void updateById(Map<String, Object> map);
}
