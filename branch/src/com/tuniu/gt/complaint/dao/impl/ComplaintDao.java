package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tuniu.frm.core.DaoBase;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintMap;
import com.tuniu.gt.complaint.entity.ComplaintCrmEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSynchCrmEntity;
import com.tuniu.gt.complaint.vo.AfterSaleReportVo;
import com.tuniu.gt.complaint.vo.ComplaintDtAnalyseVo;
import com.tuniu.gt.complaint.vo.ComplaintDtReportVo;
import com.tuniu.gt.complaint.vo.ComplaintReasonVo;
import com.tuniu.gt.complaint.vo.ComplaintVo;

@Repository("complaint_dao_impl-complaint")
public class ComplaintDao extends DaoBase<ComplaintEntity, IComplaintMap>  implements IComplaintMap {
	public ComplaintDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "complaint";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint")
	public void setMapper(IComplaintMap mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<Integer> getComplaintIdByParameter(Map<String, Object> paramMap) {
		
		return mapper.getComplaintIdByParameter(paramMap);
	}

	@Override
	public Integer getSameGroupComplaintCount(
			Map<String, Object> paramMap) {
	
		return mapper.getSameGroupComplaintCount(paramMap);
	}

	@Override
	public List<Map<String, Object>> getComplaintInfoByOrderIds(
			Map<String, Object> paramMap) {
		
		return mapper.getComplaintInfoByOrderIds(paramMap);
	}

	@Override
	public List<Map<String, String>> getProductLeader(
			Map<String, Object> paramMap) {
		return mapper.getProductLeader(paramMap);
	}

	@Override
	public List<Map<String, String>> getInfo4ComplaintScheduleSendTask() {
		return mapper.getInfo4ComplaintScheduleSendTask();
	}

	@Override
	public void updateComplaintUpdateTime(int id) {
		mapper.updateComplaintUpdateTime(id);
	}

	@Override
	public List<Map<String, Object>> getComplaintNumListForHourTable(
			Map<String, Object> paramMap) {
		return mapper.getComplaintNumListForHourTable(paramMap);
	}
	
	@Override
	public List getComplaintEntityListByBuildDateAndState(Map map) {
		return mapper.getComplaintEntityListByBuildDateAndState(map);
	}

	@Override
	public void updateCustIdByOrderId(Map map) {
		mapper.updateCustIdByOrderId(map);
	}

	@Override
	public List<Integer> getComplaintIdsByCon(String secondType) {
		return mapper.getComplaintIdsByCon(secondType);
	}

	@Override
	public Map<String, Object> getOvertimeUnAssignInfo(String dealDepart) {
		return mapper.getOvertimeUnAssignInfo(dealDepart);
	}

    @Override
    public List<Integer> queryHasCtOrders(Map<String, Object> paramMap) {
        return mapper.queryHasCtOrders(paramMap);
    }

	@Override
	public List<ComplaintEntity> getBefThreeNullList() {
		return mapper.getBefThreeNullList();
	}

	@Override
	public List<ComplaintEntity> getComplaintList(Map<String, Object> paramMap) {
		return mapper.getComplaintList(paramMap);
	}
	
	@Override
	public int compareMax(Map<String, Object> paramMap){
		return mapper.compareMax(paramMap);
	}

	@Override
	public List<Map<String, Object>> getPayInfoOnOrder(int orderId) {
		return mapper.getPayInfoOnOrder(orderId);
	}

	@Override
	public List<Map<String, Object>> getPayInfoOnTel(String tel) {
		return mapper.getPayInfoOnTel(tel);
	}

	@Override
	public List<Map<String, Object>> getUndoneCompBills() {
		return mapper.getUndoneCompBills();
	}

	@Override
	public ComplaintVo getCmpBillInfo(Integer cmpId) {
		return mapper.getCmpBillInfo(cmpId);
	}

	/**
	 * 第三方投诉
	 */
	public List<Map<String, Object>> getThirdParty(Map<String, Object> paramMap){
		
		return mapper.getThirdParty(paramMap);
		
	}
	/**
	 * 回呼
	 * @return
	 */
	public List<ComplaintEntity> getCallBack(Map<String, Object> paramMap){
		
		return mapper.getCallBack(paramMap);
		
	}

	@Override
	public List<String> getBdpNames() {
		
		return mapper.getBdpNames();
	}
	
	public  void updateCmpLevel(Map<String, Object> paramMap){
		
		 mapper.updateCmpLevel(paramMap);
		
	}

    @Override
    public List<AfterSaleReportVo> getAfterSaleReport(Map<String, Object> paramMap) {
        return mapper.getAfterSaleReport(paramMap);
    }
    
    
    public List<AfterSaleReportVo> getPostSaleUnDoneComplaintReport() {
        return mapper.getPostSaleUnDoneComplaintReport();
    }
    
    
	public List<ComplaintVo> getReasonList(Map<String, Object> map) {
		
		
		return mapper.getReasonList(map);
	}

    /**
     * @return
     */
    public List<AfterSaleReportVo> getPostSaleCompleteCmpReport(Map<String, Object> paramMap) {
        return mapper.getPostSaleCompleteCmpReport(paramMap);
    }

    /**
     * @param paramMap
     * @return
     */
    @Override
    public List<Integer> getCompleteCmpOrderList(Map<String, Object> paramMap) {
        return mapper.getCompleteCmpOrderList(paramMap);
    }

    /**
     * @param paramMap
     * @return
     */
    public List<ComplaintVo> getSameGroupComplaints(Map<String, Object> paramMap) {
        return mapper.getSameGroupComplaints(paramMap);
    }

    /**
     * @param paramMap
     * @return
     */
    public int getComplaintCountByDealNameAndAssignTimePeriod(Map<String, Object> paramMap) {
        return mapper.getComplaintCountByDealNameAndAssignTimePeriod(paramMap);
    }

    /**
     * @param paramMap
     * @return
     */
    public Map<String, Object> getComplaintNumListForHourGraph(Map<String, Object> paramMap) {
        return mapper.getComplaintNumListForHourGraph(paramMap);
    }

    /**
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> getComplaintNumListForDayTable(Map<String, Object> paramMap) {
        return mapper.getComplaintNumListForDayTable(paramMap);
    }

    /**
     * @param paramMap
     * @return 
     */
    public List<Map<String, Object>> getComplaintNumListForDayGraph(Map<String, Object> paramMap) {
        return mapper.getComplaintNumListForDayGraph(paramMap);
    }
    
    public List<ComplaintEntity> listAll(Map<String, Object> paramMap){
    	
    	 return mapper.listAll(paramMap);
    }

	@Override
	public int getListCount(Map<String, Object> paramMap) {
		
		return mapper.getListCount(paramMap);
	}
	
	/**
     * 获取投诉处理数据报表
     * @param paramMap
     * @return
     */
	@Override
    public List<ComplaintDtReportVo> getDealPayoutReport(Map<String, Object> paramMap) {
        return mapper.getDealPayoutReport(paramMap);
    }
	
	/**
     * 获取投诉处理数据订单详细
     * @param paramMap
     * @return
     */
	@Override
    public List<ComplaintDtReportVo> getDealPayoutDetail(Map<String, Object> paramMap) {
        return mapper.getDealPayoutDetail(paramMap);
    }
	
	/**
     * 获取投诉处理数据图表数据
     * @param paramMap
     * @return
     */
	@Override
	public List<ComplaintDtReportVo> getDealPayoutEcharts(Map<String, Object> paramMap){
		return mapper.getDealPayoutEcharts(paramMap);
	}
	
	@Override
	public List<ComplaintDtAnalyseVo> getDealPayoutAnalyseList(Map<String, Object> paramMap){
		return mapper.getDealPayoutAnalyseList(paramMap);
	}
	
	@Override
	public int getDtAnalyseCount(Map<String, Object> paramMap){
		return mapper.getDtAnalyseCount(paramMap);
	}
	
	@Override
	public List<ComplaintCrmEntity> getComplaintByOrderIdOrCustId(Map<String, Object> paramMap){
		return mapper.getComplaintByOrderIdOrCustId(paramMap);
	}
	
	@Override
	public Integer getComplaintByOrderIdOrCustIdCount(Map<String, Object> paramMap){
		return mapper.getComplaintByOrderIdOrCustIdCount(paramMap);
	}

	public Boolean getComplaintCancelState(Map<String, Object> paramMap) {
		return mapper.getComplaintCancelState(paramMap);
	}

	public List<ComplaintSynchCrmEntity> getCrmComplaint(Map<String, Object> paramMap) {
		return mapper.getCrmComplaint(paramMap);
	}
	
	@Override
	public Integer getCrmComplaintCount(Map<String, Object> paramMap){
		return mapper.getCrmComplaintCount(paramMap);
	}
	
	/**
	 * 根据投诉单id 获得投诉事宜
	 * @param cmpId
	 * @return
	 */
	public List<ComplaintReasonVo> getReasonListByCmpId(Integer cmpId) {
		return mapper.getReasonListByCmpId(cmpId);
	}
	
	/**
	 * 查询该投诉单下相关赔付信息
	 *  有订单投诉，根据订单号查询
	 *  无订单投诉，根据手机号查询
	 * @param queryConMap
	 * @return
	 */
	public List<Map<String, Object>> getHistoryPayInfo(Map<String, Object> queryConMap) {
		return mapper.getHistoryPayInfo(queryConMap);
	}
	
	/**
	 * 根据投诉单号，获得是否是订单标识 client_type_expand字段
	 * @param complaintId
	 * @return
	 */
	public Integer getClientTypeExpand(Integer complaintId) {
		return mapper.getClientTypeExpand(complaintId);
	}

	/**
	 * 获得某一天未完结的投诉单id
	 * @param map
	 * @return
	 */
	public List<Integer> getHandingComplaintIds(Map<String, Object> map) {
		return mapper.getHandingComplaintIds(map);
	}
	
	public String getLeastCountEarliestPerson(Map<String, Object> map){
		return mapper.getLeastCountEarliestPerson(map);
	}
	
	@Override
	public List<ComplaintEntity> getOvertimeNotCallbackLists(Map<String, Object> paramMap){
		return mapper.getOvertimeNotCallbackLists(paramMap);
	}
	
	@Override
	public void upgradeComplaintByIdList(Map<String, Object> paramMap){
		mapper.upgradeComplaintByIdList(paramMap);
	}
}
