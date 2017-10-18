package com.tuniu.gt.toolkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import tuniu.frm.service.Bean;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;

public class SendRtxTaskOfNotAssignDeal extends TimerTask {
    //投诉service
    private IComplaintService complaintService =
        (IComplaintService)Bean.get("complaint_service_complaint_impl-complaint");

    //用户service
    private IUserService userService = (IUserService)Bean.get("frm_service_system_impl-user");

	/**
	 * 定时发送下次跟进时间rtx提醒
	 */
	public void run() {
        //	    Date date = new Date();
        //        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //        Calendar calendar = Calendar.getInstance();
        //        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //        calendar.set(Calendar.MINUTE, 0);
        //        calendar.set(Calendar.SECOND, 0);
        //        calendar.set(Calendar.MILLISECOND, 0);
        //        Db db = new Db();
        //        java.sql.Connection conn = db.connect();
        //        String sql =
        //            "SELECT a.id,a.customer_leader, a.service_manager FROM ct_complaint a WHERE a.state = 1"
        //                + " AND a.order_state = '出游前'" + " AND a.deal_depart = '出游前'" + " AND a.deal = ''"
        //                + " AND a.build_date < DATE_SUB(CURDATE(),INTERVAL 3 DAY)";
        if (Constant.CONFIG.getProperty("sendFlag").equals("1")) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("state", 1);
        paramMap.put("orderStateTemp", "出游前");
        paramMap.put("dealDepart", Constans.BEFORE_TRAVEL);
        paramMap.put("deal", "");
        paramMap.put("buildDateOfNotAssignDeal", "usedByRTX");
        List<ComplaintEntity> notAssignDealMap = (List<ComplaintEntity>)complaintService.fetchList(paramMap);
        if (notAssignDealMap != null && notAssignDealMap.size() > 0) {
            String note = "您名下有超过三天的投诉单未分配投诉处理人，请及时登录boss-投诉质检系统及时处理";
            for (ComplaintEntity complaint : notAssignDealMap) {
                String customerLeaderName = complaint.getCustomerLeader();
                String serviceManagerName = complaint.getServiceManager();
                String title = "投诉单号：" + String.valueOf(complaint.getId()) + "待处理提醒";
                if (StringUtils.isNotEmpty(customerLeaderName)) {
                    UserEntity customerLeader = userService.getUserByName(customerLeaderName);
                    Rtx.sendSingleRtx(customerLeader.getId(), customerLeaderName, title, note);
                }
                if (StringUtils.isNotEmpty(serviceManagerName)) {
                    UserEntity serviceManager = userService.getUserByName(serviceManagerName);
                    Rtx.sendSingleRtx(serviceManager.getId(), customerLeaderName, title, note);
                }
            }
        }
	}
    }

}
