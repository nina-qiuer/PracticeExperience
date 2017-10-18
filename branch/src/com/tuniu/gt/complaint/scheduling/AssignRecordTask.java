package com.tuniu.gt.complaint.scheduling;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.entity.AssignRecordEntity;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.enums.AssignType;
import com.tuniu.gt.complaint.service.IAssignRecordService;
import com.tuniu.gt.complaint.service.IAutoAssignService;

public class AssignRecordTask {

    @Autowired
    @Qualifier("complaint_service_complaint_impl-auto_assign")
    private IAutoAssignService assignService;

    @Autowired
    @Qualifier("complaint_service_complaint_impl-assign_record")
    private IAssignRecordService recordService;

    private static Logger logger = Logger.getLogger(AssignRecordTask.class);

    public void recordAssignInfo() {
        logger.info("Record assign info Begin: " + new java.util.Date());

        // 23:50 读取当日分单信息
        List<AutoAssignEntity> autoAssignComplaintRecords = assignService.getAssignCompInfoList(0, AssignType.AUTO);

        /* 转换信息 */
        List<AssignRecordEntity> recordList = new ArrayList<AssignRecordEntity>();
        for(AutoAssignEntity assignEntity : autoAssignComplaintRecords) {
            AssignRecordEntity recordEntity = new AssignRecordEntity();
            recordEntity.setType(assignEntity.getType());
            recordEntity.setUserId(assignEntity.getUserId());
            recordEntity.setUserName(assignEntity.getUserName());
            recordEntity.setDepartmentName(assignEntity.getDepartmentName());
            recordEntity.setTourTimeNode(assignEntity.getTourTimeNode());
            recordEntity.setOrderIds(assignEntity.getOrderIds());
            recordEntity.setOrderNum(assignEntity.getListNums().intValue());
            recordEntity.setStatDate(new Date(new java.util.Date().getTime()));
            recordList.add(recordEntity);
        }

        // 保存自动分单信息
        recordService.addAssignRecordBatch(recordList);

        List<AutoAssignEntity> robComplaintRecords = assignService.getAssignCompInfoList(2, AssignType.ROB);

        /* 转换信息 */
        recordList.clear();
        for(AutoAssignEntity assignEntity : robComplaintRecords) {
            AssignRecordEntity recordEntity = new AssignRecordEntity();
            recordEntity.setType(AssignType.ROB.getPersistentType());
            recordEntity.setUserId(assignEntity.getUserId());
            recordEntity.setUserName(assignEntity.getUserName());
            recordEntity.setDepartmentName(assignEntity.getDepartmentName());
            recordEntity.setTourTimeNode(assignEntity.getTourTimeNode());
            recordEntity.setOrderIds(assignEntity.getOrderIds());
            recordEntity.setOrderNum(assignEntity.getListNums().intValue());
            recordEntity.setStatDate(new Date(new java.util.Date().getTime()));
            recordList.add(recordEntity);
        }

        // 保存抢单信息
        recordService.addAssignRecordBatch(recordList);

        logger.info("Record assign info End: " + new java.util.Date());
    }

}
