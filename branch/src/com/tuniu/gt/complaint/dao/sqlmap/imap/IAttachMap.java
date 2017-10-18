package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IMapBase;

import org.springframework.stereotype.Repository;

import com.tuniu.gt.complaint.entity.AttachEntity;

@Repository("complaint_dao_sqlmap-attach")
public interface IAttachMap extends IMapBase { 

   void insertQcAttach(AttachEntity entity);
	
   List<AttachEntity> fetchQcList(Map<String, Object> paramMap);
   
   AttachEntity  getQcAttach(Map<String, Object> paramMap);
   
   void  updateQcAttach(AttachEntity entity);

   void deleteImproveAttach(Map<String, Object> paramMap);
}
