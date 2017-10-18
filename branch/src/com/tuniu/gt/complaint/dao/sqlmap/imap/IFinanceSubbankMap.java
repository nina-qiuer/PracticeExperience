package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.FinanceSubbankEntity;
import com.tuniu.gt.complaint.vo.FinanceSubbankVo;

@Repository("complaint_dao_sqlmap-finance_subbank")
public interface IFinanceSubbankMap extends IMapBase {
	
    void batchInsert(List<FinanceSubbankEntity> entList);

    /**
     * @param searchVo
     * @return
     */
    List<FinanceSubbankEntity> fetchList(FinanceSubbankVo searchVo);

    /**
     * @return
     */
    Date getMaxLastUpdateDate();

}
