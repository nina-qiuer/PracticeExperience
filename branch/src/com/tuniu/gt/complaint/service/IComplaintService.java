package com.tuniu.gt.complaint.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tuniu.gt.complaint.entity.AgencySetDto;
import com.tuniu.gt.complaint.entity.ComplaintCrmEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSynchCrmEntity;
import com.tuniu.gt.complaint.entity.HandlerResult;
import com.tuniu.gt.complaint.entity.OrderEntity;
import com.tuniu.gt.complaint.vo.CmpImproveVo;
import com.tuniu.gt.complaint.vo.ComplaintDtAnalyseVo;
import com.tuniu.gt.complaint.vo.ComplaintDtReportVo;
import com.tuniu.gt.complaint.vo.ComplaintVo;
import com.tuniu.gt.frm.entity.UserEntity;

import net.sf.json.JSONArray;
import tuniu.frm.core.IServiceBase;
public interface IComplaintService extends IServiceBase {
	
	/**
	 * 通过订单ID查询订单信息主方法
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public ComplaintEntity getOrderInfoMain(String orderId) throws Exception;
	
	/**
	 * 通过订单信息获取投诉信息
	 * @param orderId
	 * @return 投诉信息
	 */
	public List<ComplaintEntity> getComplaintsByOrderId(String orderId);
	
	/**
     * 功能描述: 根据一串订单号查询哪些已经有投诉<br>
     */
    public List<Integer> queryHasCtOrders(Map<String, Object> paramMap);
	
	/**
	 * 调用网站接口，向网站传送订单状态
	 * @param orderId 订单号
	 * @param state 投诉状态
	 */
	public void sendOrderStatus(int orderId, int state);
	
	/**
	 * 产生投诉单时回调ngboss接口
	 * @param orderId
	 * @throws Exception
	 */
	public void callbackNgboss(String orderId) throws Exception;
	
	/**
	 * 根据id获取投诉数据
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public List<ComplaintEntity> getComplaintById(String id );
	
	/**
	 * 根据条件获取投诉ID
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getComplaintIdByParameter(Map<String, Object> paramMap);
	
	/**
	 * 获取首呼不及时的投诉ID
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getNotInTimeComplaintId(List<Integer> complaintIds, double hour);
	
	/**
	 * 获取同团的投诉
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public Integer getSameGroupComplaintCount(Integer routeId, Date startTime);
	
	/**
	 * 根据订单号获取投诉信息
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getComplaintInfoByOrderIds(String orderIds);
	
	/**
	 * 模糊查询产品经理
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, String>> getProductLeader(Map<String, Object> paramMap);
	
	
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
	
	public Integer getCustIdByOrderId(Integer orderId);
	
	/**
	 * 根据second_type获取投诉id
	 * @param Integer
	 * @return String
	 */
	public List<Integer> getComplaintIdsByCon(String secondType);
	
	public void updateCustIdByOrderId(Map map);
	
	/**
	 * 根据客人id获取客人标签
	 * @param map
	 * @return
	 */
	public JSONArray getCustTagsByCustIds(Map map);
	
    /**
     * 获取各处理岗超时未分配投诉单信息
     */
    Map<String, Object> getOvertimeUnAssignInfo(String dealDepart);
    
    /**
     * 查找出游前售前客服、客服经理、高级客服经理均为空的数据
     */
    List<ComplaintEntity> getBefThreeNullList();
    
    List<ComplaintEntity> getComplaintList(Map<String, Object> paramMap);
    
    int getCompareMax(Map<String, Object> paramMap);
    
    void insertComplaint(ComplaintEntity entity);
    
    void insertNonOrdComp(ComplaintEntity entity);
    
    List<Map<String, Object>> getPayInfoOnOrder(int orderId);
    
    void sendLaunchCompEmail(ComplaintEntity entity);
    
    List<Map<String, Object>> getPayInfoOnTel(String tel);
    
	void sendEmailForChangeLevl(ComplaintEntity complaint, String changeReason, String changeDesc, String userEmail);
	
	/**
	 * 获取投诉未完成或质检未完成的投诉单
	 */
	List<Map<String, Object>> getUndoneCompBills();
	
	ComplaintVo getCmpBillInfo(Integer cmpId);
	/**
	 * 第三方投诉
	 * @return
	 */
	List<Map<String,Object>> getThirdParty(Map<String, Object> paramMap);
	 
	/**
	 * 回呼率
	 * @param paramMap
	 * @return
	 */
   	List<ComplaintEntity>  getCallBack(Map<String, Object> paramMap);
   	
   	/**
   	 * 获得投诉单中的一级部门列表
   	 * @return
   	 */
   	List<String> getBdpNames();
   	
   		/**
   	 * 修改投诉等级 
   	 * @param map
   	 */
   	void updateCmpLevel(Map<String, Object> map);
   	
   	/**
     * @param 分单时间起始
     * @return Map<员工姓名,Map<统计字段,统计数量>>
     */
    Map<String,Map<String,Integer>> getAfterSaleReport(Map<String, Object> paramMap);
    
    Map<String,Map<String,Integer>> getPostSaleUnDoneComplaintReport();
    
    /**
     * 获取售后投诉单完成报表（订单维度统计）
     * @return
     */
    Map<String, Map<String, Integer>> getPostSaleCompleteCmpReport(Map<String, Object> paramMap);
    
    List<ComplaintVo> getReasonList(Map<String, Object> map);
    
    void sendToQms(List<Integer> cmpIds);

    /**
     * 获取指定时间范围内归属于某客服完成的投诉（订单维度）
     * @param paramMap
     * @return
     */
    List<Integer> getCompleteCmpOrderList(Map<String, Object> paramMap);

    /**
     * @param orderId
     * @return
     */
    OrderEntity queryNewOrderInfo(String orderId);

    /**
     * @param userNameList
     * @return
     */
    String getLeastCompalintCountUserName(List<String> userNameList,Map<String, Object> paramMap);

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
    JSONArray getComplaintNumListForDayGraph(Map<String, Object> paramMap);
    
    List<ComplaintEntity> listAll(Map<String, Object> paramMap);
    
    int getListCount(Map<String, Object> paramMap);
 
    /**
     * 获取投诉处理数据报表
     * @param paramMap
     * @return
     */
    Map<String,Map<String,BigDecimal>> getDealPayoutReport(Map<String, Object> paramMap);
    
    /**
     * 获取投诉处理数据汇总数据
     * @param paramMap
     * @return
     */
	List<ComplaintDtReportVo> getDealPayoutTotal(Map<String, Object> paramMap);
    
    /**
     * 获取投诉处理数据订单详细
     * @param paramMap
     * @return
     */
    List<ComplaintDtReportVo> getDealPayoutDetail(Map<String, Object> paramMap);

    /**
     * @param phoneNum
     * @return
     */
    List<ComplaintEntity> getComplaintsByPhoneNum(String phoneNum);

    /**
     * 获取投诉处理数据图表数据
     * @param paramMap
     * @return
     */
    Map<String,BigDecimal[]> getDealPayoutEcharts(Map<String, Object> paramMap);
    
    /**
     * 投诉单返回未处理
     * @param paramMap
     * @return
     */
    void returnNotAssigned(Map<String, Object> paramMap);
    
    /**
     * 投诉单返回未处理
     * @param complaintId
     * @return
     */
    Boolean autoReturnNotAssigned(ComplaintEntity complaint);
    
    /**
     * 投诉单返回未处理
     * @param complaintId
     * @return
     */
    Boolean autoReturnNotAssigned(Integer complaintId);
    
    /**
     * 投诉单批量返回未处理
     * @param complaintIds
     */
    void returnNotAssignedByComplaintIds(String[] complaintIds,UserEntity user);
    
    /**
     * 添加跟进记录
     */
    void insertFollowRecord(Map<String, Object> paramMap);
    
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
	 * 生成投诉改进报告
	 * @param cmpImproveVo
	 * @param result
	 */
	public void saveImproveBill(CmpImproveVo cmpImproveVo, HandlerResult result);
	
	/**
	 * 查询该投诉单下相关赔付信息, 对客或分担金额任意不为0
	 *  有订单投诉，根据订单号查询
	 *  无订单投诉，根据手机号查询
	 * @param queryConMap
	 * @return
	 */
	public List<Map<String, Object>> getHistoryPayInfo(Map<String, Object> queryConMap);
	
	/**
	 * 根据投诉单号，查询该投诉单对应订单是否是新分销订单
	 * @param complaintId
	 * @return
	 */
	public boolean getDistributeFlag(Integer complaintId);
	
	/**
	 * 根据供应商id判断该是否使用NB系统
	 * @param agencyId
	 * @param complaintId
	 * @return
	 */
	public int getNbFlag(int agencyId, Integer complaintId);
	
	/**
	 * 手动按逻辑分配投诉单
	 * @param complaint
	 */
	public void manualAssignComplaint(ComplaintEntity complaint);

	/**
	 * 修改投诉单至未分配
	 * @param complaint
	 */
	void changeComplaintToUnAssigned(Map<String, Object> paramMap);
	
	/**
	 * 获得某一天未完结的投诉单id
	 * @param map
	 * @return
	 */
	public List<Integer> getHandingComplaintIds(Map<String, Object> map);

	/**
	 * 改变投诉单处理岗
	 * @param complaint
	 */
	void changeDealDepart(ComplaintEntity complaint,String dealDepart);
	
	/**
	 * 根据条件查询分单最少、
	 * @param map
	 * @return
	 */
	String getLeastCountEarliestPerson(Map<String, Object> map);

	/**
	 * 根据订单号获取不重复供应商
	 * @param orderId
	 * @return
	 */
	Set<AgencySetDto> getAgencySet(Integer orderId);

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
