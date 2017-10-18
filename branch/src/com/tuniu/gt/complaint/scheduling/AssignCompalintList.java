package com.tuniu.gt.complaint.scheduling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.enums.TourTimeNodeEnum;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.gt.toolkit.lang.CollectionUtil;

/**
 * 自动分单处理类
 * @author zhangqingsong
 */
public class AssignCompalintList {
	
	private static Logger logger = Logger.getLogger(AssignCompalintList.class);

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;

	// 引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-auto_assign")
	private IAutoAssignService autoAssignService;
	
	// 用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	/**
	 * 出游前投诉自动分单
	 */
	public synchronized void assginBeforeTravel() throws Exception {
	    logger.info("assginBeforeTravel invoked");
		assginComplaint(Constans.SPECIAL_BEFORE_TRAVEL, "1,6,7", 5);
	}
	
	/**
	 * 售后组投诉自动分单
	 */
	public synchronized void assginInTravel() throws Exception {
	    logger.info("assginInTravel invoked");
		assginComplaint(Constans.IN_TRAVEL, "1,5,6", 5);
	}
	
	/**
	 * 资深组投诉自动分单
	 */
	public synchronized void assginAfterTravel() throws Exception {
	    logger.info("assginAfterTravel invoked");
		assginComplaint(Constans.AFTER_TRAVEL, "1,10", 5);
	}
	
	/**
	 * 机票组投诉自动分单
	 */
	public synchronized void assginAirTicket() throws Exception {
	    logger.info("assginAirTicket invoked");
		assginComplaint("机票组", "1", 5);
	}
	
	/**
     * 酒店组投诉自动分单
     */
    public synchronized void assginHotel() throws Exception {
        logger.info("assginHotel invoked");
        assginComplaint("酒店组", "1", 5);
    }
    
    /**
     * 交通组投诉自动分单
     */
    public synchronized void assginTraffic() throws Exception {
        logger.info("assginTraffic invoked");
        assginComplaint("交通组", "1", 5);
    }
    
    /**
     * 分销机票组投诉自动分单
     */
//    public synchronized void assginDistributeAir() throws Exception {
//        logger.info("assginDistributeAir invoked");
//        assginComplaint("分销组", "1", 5);
//    }

	/**
	 * 投诉自动分单
	 * dealDepart：'出游前客户服务','售后组','资深组'
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void assginComplaint(String dealDepart, String stateStr, int num) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("state", stateStr);
		paramMap.put("dealDepart", dealDepart);
		if(Constans.IN_TRAVEL.equals(dealDepart)) {
		    //抢单时长
		    int robTimeDuration = DBConfigManager.getConfig("business.robcmp.duration", Integer.class)==null?2:DBConfigManager.getConfig("business.robcmp.duration", Integer.class);
		    paramMap.put("moreMinutesAfterBuildTime", robTimeDuration+1);
		    Integer hour = DateUtil.getField(new Date(), Calendar.HOUR_OF_DAY);
		    if(hour < 9 || hour >= 18){// 18点到第二天9点点评发起投诉不分配
		    	paramMap.put("noCommentComeFrom", 1);
            }
		}
		if(Constans.DISTRIBUTE.equals(dealDepart)) {
			paramMap.put("isDistribute", 1);
		}
		paramMap.put("dataLimitStart", 0);
		paramMap.put("dataLimitEnd", num);
		List<ComplaintEntity> complaints = (List<ComplaintEntity>) complaintService.fetchList(paramMap);
		String memPre = ComplaintUtil.MEM_PRE_COMPLAINT;
		for (ComplaintEntity complaint : complaints) {
		    logger.info("assginComplaint begin["+complaint.getId()+"]");
		    /* 根据投诉单出游时间节点、投诉级别、产品品类、目的地大类查询符合该条件的手上单子最少的客服 */
			AutoAssignEntity dealCustom = queryLeastListCS(AutoAssignEntity.TYPE_COMPLAINT, complaint, isCustState(dealDepart));
			if (dealCustom != null) {
				logger.info("assignTo" + dealCustom.getUserName());
				dealPeople(complaint, dealCustom);
				ComplaintUtil.recordUserNums(dealCustom.getUserId(), memPre);
				ComplaintUtil.recordUserOrders(dealCustom.getUserId(), String.valueOf(complaint.getOrderId()), memPre);
				Thread.sleep(1000L);
			}
		}
	}
	
	/**
     * @param dealDepart
     * @return
     */
    private Boolean isCustState(String dealDepart) {
        Boolean isCustState  = true;
        
        if(Constans.IN_TRAVEL.equals(dealDepart)||Constans.HOTEL.equals(dealDepart)){
            isCustState = false;
        }
        
        return isCustState;
    }

	/**
	 * @param type
	 * @param complaint
	 * @param isCusState
	 * @return
	 */
	private AutoAssignEntity queryLeastListCS(Integer type, ComplaintEntity complaint, Boolean isCusState) {
		List<AutoAssignEntity> currStateList = new ArrayList<AutoAssignEntity>();
		if (complaint != null) {
			//出游前客户服务的赔款单和无订单平均分配
			if(Constans.SPECIAL_BEFORE_TRAVEL.equals(complaint.getDealDepart())
					&&(complaint.getIsReparations()==1||complaint.getOrderId()==0)){
				Map<String,Object> spBeforeMap = new HashMap<String, Object>();
				spBeforeMap.put("type", type);
				spBeforeMap.put("tourTimeNode", TourTimeNodeEnum.getValue(complaint.getDealDepart()));
				currStateList = (List<AutoAssignEntity>) autoAssignService.fetchList(spBeforeMap);
			}else{//根据投诉单信息进行分配
				currStateList = autoAssignService.getCurrStateList(type, complaint);
			}
		} else {
			currStateList = autoAssignService.getListByType(type);
		}
		// 去掉状态不符合分单要求的客服
		List<AutoAssignEntity> delList = new ArrayList<AutoAssignEntity>();
        for(AutoAssignEntity entity : currStateList) {
            if(entity.getDelFlag() == 1) { // 分单关闭则排除
                delList.add(entity);
                continue;
            }

            if(isCusState) { // 需要考虑客服坐席状态
                if(!CommonUtil.isStatus(userService.getExtensionByUserId(entity.getUserId()), complaint.getDealDepart())) {
                    delList.add(entity);
                }
            }
        }
		currStateList.removeAll(delList);
		
		AutoAssignEntity result = null;
		if (CollectionUtil.isNotEmpty(currStateList)) {
			result = currStateList.get(0);
			/*出游前客户服务和资深组按照时间段统计（0点到11:59和12点到23：59）在此时间段内分配的所有单数，分配给单数最少的客服。*/
			if(Constans.SPECIAL_BEFORE_TRAVEL.equals(complaint.getDealDepart())||Constans.AFTER_TRAVEL.equals(complaint.getDealDepart())){ 
			    Map<String,AutoAssignEntity> currStateListMap = new HashMap<String, AutoAssignEntity>();
			    List<String>  userNameList = new ArrayList<String>();
			    //排序
			    Collections.shuffle(currStateList);
			    for (AutoAssignEntity entity : currStateList) {
			        //根据当前时间获取该时间段范围内单数最少的客服姓名
			        currStateListMap.put(entity.getUserName(), entity);
			        userNameList.add(entity.getUserName());
 			    }
			    Map<String, Object> paramMap = new HashMap<String, Object>();
			    if(Constans.AFTER_TRAVEL.equals(complaint.getDealDepart())){
					
				}else{
					Integer is_reparations=complaint.getIsReparations();
					Integer order_id=complaint.getOrderId();
					if(order_id==0){
						paramMap.put("no_order", 1);
					}else{
						paramMap.put("has_order", 1);
					}
					paramMap.put("is_reparations", is_reparations);
				}
			    paramMap.put("userNameList", userNameList);
			    paramMap.put("assignTimeBgn", DateUtil.formatDate(new Date())+" 00:00:00");
		        paramMap.put("assignTimeEnd", DateUtil.formatDate(new Date())+" 23:59:59");
		        paramMap.put("tour_time_node", TourTimeNodeEnum.getValue(complaint.getDealDepart()));
			    String leastUserName = complaintService.getLeastCountEarliestPerson(paramMap);
			    
			    result = currStateListMap.get(leastUserName);
			}else{
			    for (AutoAssignEntity entity : currStateList) {
	                Date lastAssignTime = result.getLastAssignTime();
	                if (null == lastAssignTime) {
	                    break;
	                }
	                if (null == entity.getLastAssignTime()) {
	                    result = entity;
	                    break;
	                }
	                if (lastAssignTime.after(entity.getLastAssignTime())) {
	                    result = entity;
	                }
	            }
			}
			
		}
		return result;
	}
	
	/**
	 * 分配处理人
	 * 
	 * @return
	 */
	public void dealPeople(ComplaintEntity complaint, AutoAssignEntity user) {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		// 将负责人分配给各个投诉，同时将状态改成投诉处理中
		paramMap.put("id", complaint.getId());
		paramMap.put("deal", user.getUserId());
		paramMap.put("dealName", user.getUserName());
		paramMap.put("state", 2);// 状态变成投诉处理中
		paramMap.put("assignTime", new Date());

		// 添加有效操作记录
		String noteContent = "分配给：" + user.getUserName();
		complaintFollowNoteService.addFollowNote(complaint.getId(), 0, "robot",
				noteContent ,0 , Constans.ASSIGN_DEALER);
		complaintService.update(paramMap);
		autoAssignService.updateLastAssignTime(user.getId()); // 更新最后分单时间

		paramMap.clear();// 清空map
		String title="自动分单提醒";
		String content="【投诉质检-新分配单】"+"\n投诉单号:"+complaint.getId()+"\n订单号:"+complaint.getOrderId()+"\n";
		new RTXSenderThread(user.getUserId(), user.getUserName(), title, content).start();
	}

}
