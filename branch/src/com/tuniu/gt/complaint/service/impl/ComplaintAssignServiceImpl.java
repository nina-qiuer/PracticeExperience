/**
 * 
 */
package com.tuniu.gt.complaint.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.enums.AssignType;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintAssignService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.toolkit.RTXSenderThread;

/**
 * @author jiangye
 *
 */
@Service("complaint_service_impl-assign")
public class ComplaintAssignServiceImpl implements IComplaintAssignService{
    
    private static Logger logger = Logger.getLogger(ComplaintAssignServiceImpl.class);

    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_follow_note")
    private IComplaintFollowNoteService complaintFollowNoteService;
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint")
    private IComplaintService complaintService;

    @Override
    public boolean assign(ComplaintEntity cmpEntity, int userId) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean assign(ComplaintEntity cmpEntity, UserEntity user,AssignType assignType) {
        boolean success = true;
        
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();

            // 将负责人分配给投诉，同时将状态改成投诉处理中
            paramMap.put("id", cmpEntity.getId());
            paramMap.put("deal", user.getId());
            paramMap.put("dealName", user.getRealName());
            paramMap.put("state", 2);// 状态变成投诉处理中
            paramMap.put("assignTime", new Date());

            // 添加有效操作记录
            String noteContent = "分配给：" + user.getRealName();
            complaintFollowNoteService.addFollowNote(cmpEntity.getId(), 0, "robot",
                    noteContent ,0 , Constans.ASSIGN_DEALER);
            complaintService.update(paramMap);

            String title=assignType.getDescription()+"提醒";
            String content="【投诉质检-新分配单】"+"\n投诉单号:"+cmpEntity.getId()+"\n订单号:"+cmpEntity.getOrderId()+"\n";
            new RTXSenderThread(user.getId(), user.getRealName(), title, content).start();
            
            assignType.recordNums(user.getId());
            assignType.recordUserOrders(user.getId(), cmpEntity.getOrderId()+"");
            
        } catch(Exception e) {
            logger.error("分单失败，投诉id["+cmpEntity.getId()+"]");
            success = false;
        }
        return success;
    }


    @Override
    public int assign(List<ComplaintEntity> cmpList, int userId) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public int assign(List<ComplaintEntity> cmpList, UserEntity user) {
        // TODO Auto-generated method stub
        return 0;
    }

}
