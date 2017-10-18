package com.tuniu.gt.complaint.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IAgencyAppealMap;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IFinanceSubbankMap;
import com.tuniu.gt.complaint.entity.AgencyAppealEntity;
import com.tuniu.gt.complaint.entity.FinanceSubbankEntity;
import com.tuniu.gt.complaint.vo.FinanceSubbankVo;

@Repository("complaint_dao_impl-finance_subbank")
public class FinanceSubbankDao extends DaoBase<FinanceSubbankEntity, IFinanceSubbankMap> implements IFinanceSubbankMap {
    public FinanceSubbankDao() {
        tableName = Constant.appTblPreMap.get("complaint") + "finance_subbank";
    }

    @Autowired
    @Qualifier("complaint_dao_sqlmap-finance_subbank")
    public void setMapper(IFinanceSubbankMap mapper) {
        this.mapper = mapper;
    }

    @Override
    public void batchInsert(List<FinanceSubbankEntity> entList) {
        mapper.batchInsert(entList);
    }

    /**
     * @param searchVo
     * @return
     */
    public List<FinanceSubbankEntity> fetchList(FinanceSubbankVo searchVo) {
        return mapper.fetchList(searchVo);
    }

    /**
     * @return
     */
    public Date getMaxLastUpdateDate() {
        return mapper.getMaxLastUpdateDate();
    }
}
