package com.tuniu.qms.common.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.service.RtxRemindService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.util.Rtx;

/**
 * Rtx提醒任务
 * 
 * @author jiangye
 */
public class RtxRemindTask {

    private final static Logger logger = LoggerFactory.getLogger(RtxRemindTask.class);

    @Autowired
    private RtxRemindService rtxRemindService;
    
    @Autowired
    private TspService tspService;

    /**
     * rtx提醒
     */
    public void execute() {
        Boolean sucFlag = false;
        Rtx rtx = new Rtx();
        List<RtxRemind> rtxReminds = rtxRemindService.getList2Send();
        for(RtxRemind remind : rtxReminds) {
            rtx = buildRtx(remind);
            sucFlag = tspService.sendRtx(rtx);
            if(sucFlag) {
                // 提醒成功后直接删除
                rtxRemindService.delete(remind.getId());
            } else {
                Integer failTimes = remind.getFailTimes();
                if(failTimes < 3) {
                    // 更新失败次数
                    remind.setFailTimes(failTimes + 1);
                    rtxRemindService.update(remind);
                    logger.warn("===rtx提醒已失败" + remind.getFailTimes() + "次-->rtx提醒id： " + remind.getId() + "===");
                } else {
                    logger.warn("===rtx提醒失败次数达到最大-->rtx提醒id： " + remind.getId() + "===");
                }
            }
        }

    }

    private Rtx buildRtx(RtxRemind remind) {
        Rtx rtx = new Rtx();
        rtx.setTitle(remind.getTitle());
        rtx.setContent(remind.getContent());
        List<Integer> uidList = new ArrayList<Integer>();
        String uid= remind.getUid();
        String uids[] = uid.split(",");
        for(int i =0 ;i<uids.length;i++){
			
        	uidList.add(Integer.parseInt(uids[i]));
		}
        rtx.setUids(uidList);
        return rtx;
    }
}
