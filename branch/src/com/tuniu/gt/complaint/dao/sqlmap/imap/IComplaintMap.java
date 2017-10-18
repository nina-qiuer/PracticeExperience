package com.tuniu.gt.complaint.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tuniu.frm.core.IMapBase;

import com.tuniu.gt.complaint.entity.ComplaintCrmEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSynchCrmEntity;
import com.tuniu.gt.complaint.vo.AfterSaleReportVo;
import com.tuniu.gt.complaint.vo.ComplaintDtAnalyseVo;
import com.tuniu.gt.complaint.vo.ComplaintDtReportVo;
import com.tuniu.gt.complaint.vo.ComplaintReasonVo;
import com.tuniu.gt.complaint.vo.ComplaintVo;

@Repository("complaint_dao_sqlmap-complaint")
public interface IComplaintMap extends IMapBase { 

	/**
	 * 根据条件获取投诉ID
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getComplaintIdByParameter(Map<String, Object> paramMap);
	/**
	 * 获取同团的投诉
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public Integer getSameGroupComplaintCount(Map<String, Object> paramMap);
	
	/**
	 * 根据订单号获取投诉信息
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getComplaintInfoByOrderIds(Map<String, Object> paramMap);
	
	/**
	 * 模糊查询产品经理
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> getProductLeader(Map<String, Object> paramMap);
	
	/**
	 * 获取定时提醒任务数据
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> getInfo4ComplaintScheduleSendTask();
	
	/**
	 * 获取定时提醒任务数据
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public void updateComplaintUpdateTime(int id);
	
	/**
	 * 按小时粒度统计查询投诉量
	 */
	public List<Map<String, Object>> getComplaintNumListForHourTable(Map<String, Object> paramMap);
	
	public List getComplaintEntityListByBuildDateAndState(Map map);
	
	public void updateCustIdByOrderId(Map map);
	
	/**
	 * 根据second_type获取投诉id
	 * @param Integer
	 * @return String
	 */
	public List<Integer> getComplaintIdsByCon(String secondType);
	
	/**
     * 获取各处理岗超时未分配投诉单信息
     */
	Map<String, Object> getOvertimeUnAssignInfo(String dealDepart);
	
    /**
     * 功能描述: 根据一串订单号查询哪些已经有投诉<br>
     * 
     * @param orderIds
     * @return
     */
    public List<Integer> queryHasCtOrders(Map<String, Object> paramMap);
    
    /**
     * 查找出游前售前客服、客服经理、高级客服经理均为空的数据
     */
    List<ComplaintEntity> getBefThreeNullList();
    
    List<ComplaintEntity> getComplaintList(Map<String, Object> paramMap);
    
    int compareMax(Map<String, Object> paramMap);
    
    List<Map<String, Object>> getPayInfoOnOrder(int orderId);
    
    List<Map<String, Object>> getPayInfoOnTel(String tel);
    
    /**
	 * 获取投诉未完成或质检未完成的投诉单
	 */
	List<Map<String, Object>> getUndoneCompBills();
	
	ComplaintVo getCmpBillInfo(Integer cmpId);
    
	  /**
	   * 第三方投诉
	   * @param paramMap
	   * @return
	   */
	  List<Map<String, Object>>  getThirdParty(Map<String, Object> paramMap); 
	  /**
	   * 回呼
	   * @return
	   */
	  List<ComplaintEntity>  getCallBack(Map<String, Object> paramMap);
	  
	 /**
	  * 获取投诉单中的一级部门列表
	 * @return
	 */
	List<String> getBdpNames();
	/**
	 * 修改投诉等级   
	 * @param paramMap
	 */
	void updateCmpLevel(Map<String, Object> paramMap);
	
	/**
	 * @param 分单时间起始
	 * @return
	 */
	List<AfterSaleReportVo>  getAfterSaleReport(Map<String, Object> paramMap);
	
	List<AfterSaleReportVo>  getPostSaleUnDoneComplaintReport();
	
	 List<ComplaintVo> getReasonList(Map<String, Object> map) ;
	 
	 List<AfterSaleReportVo> getPostSaleCompleteCmpReport(Map<String, Object> paramMap);
    
	 /**
     * @param paramMap
     * @return
     */
    List<Integer> getCompleteCmpOrderList(Map<String, Object> paramMap);
    
    /**
     * 获取同团投诉
     * @param paramMap
     * @return
     */
    List<ComplaintVo> getSameGroupComplaints(Map<String, Object> paramMap);
    
    /**
     * @param paramMap
     * @return
     */
    int getComplaintCountByDealNameAndAssignTimePeriod(Map<String, Object> paramMap);
    /**
     * @param paramMap
     * @return
     */
    Map<String, Object> getComplaintNumListForHourGraph(Map<String, Object> paramMap);
    /**
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getComplaintNumListForDayTable(Map<String, Object> paramMap);
    /**
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> getComplaintNumListForDayGraph(Map<String, Object> paramMap);
    
    public List<ComplaintEntity> listAll(Map<String, Object> paramMap);
    
    int getListCount(Map<String, Object> paramMap);
  
    /**
     * 获取投诉处理数据报表
     * @param paramMap
     * @return
     */
	List<ComplaintDtReportVo> getDealPayoutReport(Map<String, Object> paramMap); 
	
	/**
     * 获取投诉处理数据订单详细
     * @param paramMap
     * @return
     */
	List<ComplaintDtReportVo> getDealPayoutDetail(Map<String, Object> paramMap); 
	
	/**
     * 获取投诉处理数据图表数据
     * @param paramMap
     * @return
     */
    List<ComplaintDtReportVo> getDealPayoutEcharts(Map<String, Object> paramMap);
    
    /**
     * 获取投诉处理数据分析列表
     * @param paramMap
     * @return
     */
    List<ComplaintDtAnalyseVo> getDealPayoutAnalyseList(Map<String, Object> paramMap);
    
    /**
     * 获取投诉处理数据总数
     * @param paramMap
     * @return
     */
    int getDtAnalyseCount(Map<String, Object> paramMap);
    
    /**
     * 根据订单或者会员id查询投诉单信息
     * @param paramMap
     * @return
     */
    List<ComplaintCrmEntity> getComplaintByOrderIdOrCustId(Map<String, Object> paramMap);
    
    /**
     * 根据订单或者会员id查询投诉单总数
     * @param paramMap
     * @return
     */
	Integer getComplaintByOrderIdOrCustIdCount(Map<String, Object> paramMap);
	
	/**
	 * 根据投诉单号获取投诉质检的撤销状态
	 * @param paramMap
	 * @return
	 */
	Boolean getComplaintCancelState(Map<String, Object> paramMap);
	
	/**
	 * 根据投诉单号或时间区间同步投诉数据
	 * @param paramMap
	 * @return
	 */
	List<ComplaintSynchCrmEntity> getCrmComplaint(Map<String, Object> paramMap);
	
	/**
     * 根据时间区间查询投诉数据数量
     * @param paramMap
     * @return
     */
	Integer getCrmComplaintCount(Map<String, Object> paramMap);
	
	/**
	 * 根据投诉单id 获得投诉事宜
	 * @param cmpId
	 * @return
	 */
	public List<ComplaintReasonVo> getReasonListByCmpId(Integer cmpId);
	
	/**
	 * 查询该投诉单下相关赔付信息
	 *  有订单投诉，根据订单号查询
	 *  无订单投诉，根据手机号查询
	 * @param queryConMap
	 * @return
	 */
	public List<Map<String, Object>> getHistoryPayInfo(Map<String, Object> queryConMap);
	
	/**
	 * 根据投诉单号，获得是否是订单标识 client_type_expand字段
	 * @param complaintId
	 * @return
	 */
	public Integer getClientTypeExpand(Integer complaintId);
	
	/**
	 * 获得某一天未完结的投诉单id
	 * @param map
	 * @return
	 */
	public List<Integer> getHandingComplaintIds(Map<String, Object> map);
	
	/**
	 * 根据条件查询分单最少、
	 * @param map
	 * @return
	 */
	String getLeastCountEarliestPerson(Map<String, Object> map);
	
	/**
	 * 获取超时未收呼的投诉单
	 * @param paramMap
	 * @return
	 */
	List<ComplaintEntity> getOvertimeNotCallbackLists(Map<String, Object> paramMap);
	
	/**
	 * 批量根据id升级投诉单
	 * @param paramMap
	 */
	void upgradeComplaintByIdList(Map<String, Object> paramMap);
}
