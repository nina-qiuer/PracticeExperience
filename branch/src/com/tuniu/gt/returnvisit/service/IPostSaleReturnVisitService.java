/**
 * 
 */
package com.tuniu.gt.returnvisit.service;

import java.util.List;
import java.util.Map;

import tuniu.frm.core.IServiceBase;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.returnvisit.vo.ReturnVisitReportVo;

/**
 * @author jiangye
 *
 */
public interface IPostSaleReturnVisitService extends IServiceBase {

    /**
     * 是否有投诉单相关记录
     * @param complaintId
     * @return
     */
    boolean hasRecordByComplaintIdAndDealDepart( Integer complaintId, String dealDepart);
    
    
    /**
     * 处理短信平台回调接口
     * @param tel
     * @param receiveTime
     * @param prefix
     * @param choosedItem
     * @return
     */
    int dealWithMsgCallBack(String tel, String receiveTime, Integer prefix, Integer choosedItem);

    /**
     * @param csEnt 
     * @param compEnt
     */
    void triggerReturnVisit(ComplaintEntity cmpEntity, ComplaintSolutionEntity csEnt);
    
    /**
     * 构造售后短信回访报表
     * @param map 
     * @return
     */
    List<ReturnVisitReportVo> buildReport(Map<String, Object> map);


    /**
     * @param paramMap
     */
    List<ComplaintEntity> getComplaintList(Map<String, Object> paramMap);
}
