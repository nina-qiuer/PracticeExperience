package com.tuniu.qms.qs.service;

import com.tuniu.qms.common.service.base.BaseService;
import com.tuniu.qms.qs.dto.PreSaleReturnVisitDto;
import com.tuniu.qms.qs.model.PreSaleReturnVisit;

public interface PreSaleReturnVisitService extends BaseService<PreSaleReturnVisit, PreSaleReturnVisitDto>{

	
    /**
     * 处理短信平台回调接口
     * @param tel
     * @param receiveTime
     * @param prefix
     * @param choosedItem
     * @return
     */
    int dealWithMsgCallBack(String tel, String receiveTime, Integer choosedItem);
}
