/**
 * 
 */
package com.tuniu.gt.complaint.service;

import java.util.Date;
import java.util.List;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.FinanceSubbankEntity;
import com.tuniu.gt.complaint.vo.FinanceSubbankVo;

/**
 * @author jiangye
 *
 */
public interface IFinanceSubbankService extends IServiceBase {
	void batchInsert(List<FinanceSubbankEntity> entList);
	
	List<FinanceSubbankEntity> fetchList(FinanceSubbankVo searchVo);

    /**
     * @return
     */
    Date getMaxLastUpdateDate();

    /**
     * @param subbankList
     */
    void batchSych(List<FinanceSubbankEntity> subbankList);
}
