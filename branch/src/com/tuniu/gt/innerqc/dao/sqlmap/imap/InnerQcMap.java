package com.tuniu.gt.innerqc.dao.sqlmap.imap;


import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.innerqc.entity.InnerQcEntity;
import com.tuniu.gt.innerqc.vo.InnerQcPage;


@Repository("innerqc_dao_sqlmap-inner_qc")
public interface InnerQcMap extends IMapBase {
	
	long getTotal(InnerQcPage page);
	
	List<InnerQcEntity> getInnerQcList(InnerQcPage page);
	
}
