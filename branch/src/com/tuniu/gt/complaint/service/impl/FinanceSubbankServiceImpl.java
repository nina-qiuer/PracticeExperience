package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.FinanceSubbankDao;
import com.tuniu.gt.complaint.entity.FinanceSubbankEntity;
import com.tuniu.gt.complaint.service.IFinanceSubbankService;
import com.tuniu.gt.complaint.vo.FinanceSubbankVo;
import com.tuniu.gt.toolkit.lang.CollectionUtil;

@Service("complaint_service_complaint_impl-finance_subbank")
public class FinanceSubbankServiceImpl extends ServiceBaseImpl<FinanceSubbankDao> implements IFinanceSubbankService {
    @Autowired
    @Qualifier("complaint_dao_impl-finance_subbank")
    public void setDao(FinanceSubbankDao dao) {
        this.dao = dao;
    }

    @Override
    public void batchInsert(List<FinanceSubbankEntity> entList) {
        if(CollectionUtil.isNotEmpty(entList)) {
            dao.batchInsert(entList);
        }
    }

    @Override
    public List<FinanceSubbankEntity> fetchList(FinanceSubbankVo searchVo) {
        return dao.fetchList(searchVo);
    }

    @Override
    public Date getMaxLastUpdateDate() {
        return dao.getMaxLastUpdateDate();
    }

    @Override
    public void batchSych(List<FinanceSubbankEntity> subbankList) {
        List<FinanceSubbankEntity> batchInsertList = new ArrayList<FinanceSubbankEntity>();
        List<FinanceSubbankEntity> updateList = new ArrayList<FinanceSubbankEntity>();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        for(FinanceSubbankEntity financeSubbankEntity : subbankList) {
            paramMap.put("bankBranchId", financeSubbankEntity.getBankBranchId());
            FinanceSubbankEntity entity = dao.get(paramMap);
            if(entity == null) {
                batchInsertList.add(financeSubbankEntity);
            } else {
                updateList.add(financeSubbankEntity);
            }
        }

        if(CollectionUtil.isNotEmpty(batchInsertList)) {
            dao.batchInsert(batchInsertList); // 不存在的批量插入
        }

        for(FinanceSubbankEntity financeSubbankEntity : updateList) {
            dao.update(financeSubbankEntity); // 存在的更新
        }

    }

}
