/**
 * 
 */
package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.data.DataUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AutoAssignEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.enums.AssignType;
import com.tuniu.gt.complaint.service.IAutoAssignService;
import com.tuniu.gt.complaint.service.IComplaintAssignService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IRobComplaintService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.toolkit.lang.CollectionUtil;

/**
 * @author jiangye
 */
@Service("complaint_service_complaint_impl-robComplaint")
public class RobComplaintServiceImpl implements IRobComplaintService {

    private static Logger logger = Logger.getLogger(RobComplaintServiceImpl.class);
    
    private List<ComplaintEntity> cmpList; // 符合抢单人条件的所有投诉单

    @Autowired
    @Qualifier("complaint_service_complaint_impl-auto_assign")
    private IAutoAssignService autoAssignService;

    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint")
    private IComplaintService complaintService;
    
    @Autowired
    @Qualifier("complaint_service_impl-assign")
    private IComplaintAssignService complaintAssignService;

    @Override
    public Map<String, Integer> robComplaint(int userId, int amount) {
        logger.info("rob complaints begin[userId-" + userId + "][amount-" + amount + "]");
        Map<String, Integer> resultMap = new HashMap<String, Integer>(3); // 3*0.75>2 不浪费容量不rehash
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", 1);
        paramMap.put("userId", userId);

        AutoAssignEntity autoAssignEntity = autoAssignService.getByTypeAndUserId(paramMap);
        // TODO 缓存autoAssignEntity
        if(autoAssignEntity != null && autoAssignEntity.getTourTimeNode() == 2) { // 在自动分单配置处配置了且为售后组处理岗的员工才可以抢单
            //如果抢单人处理中的单数>= maxProcessingCount  则不允许抢单
            paramMap.clear();
            paramMap.put("dealName", autoAssignEntity.getUserName());
            paramMap.put("state", "2");
            cmpList =  (List<ComplaintEntity>) complaintService.fetchList(paramMap);
            if(CollectionUtil.isNotEmpty(cmpList)){
                //最大处理中数量
                int maxProcessingCount = DBConfigManager.getConfig("business.robcmp.maxProcessingCount",Integer.class) ==null?0:DBConfigManager.getConfig("business.robcmp.maxProcessingCount",Integer.class);
                if(cmpList.size()+amount>maxProcessingCount){
                    throw new RuntimeException("处理中单数过多,当前可抢"+(maxProcessingCount-cmpList.size()<0?0:maxProcessingCount-cmpList.size())+"单");
                }
            }
            
            paramMap.clear();
            paramMap = buildCmpCondition(autoAssignEntity);

            synchronized(this) {
                cmpList = (List<ComplaintEntity>) complaintService.fetchList(paramMap); // 捞取符合抢单人配置条件的投诉单列表
                int currentRobableCmpAmount = 0;
                if(CollectionUtil.isNotEmpty(cmpList)) {
                    currentRobableCmpAmount = cmpList.size();

                    logger.info("rob complaints ing[userId-" + userId + "][amount-" + amount
                            + "]:current robable complaints amount  " + currentRobableCmpAmount);

                    ComplaintEntity toAssignComplaintEntity;
                    int successfulAmount = 0;
                    UserEntity assignedUser = new UserEntity(userId,autoAssignEntity.getUserName());
                    for(int i = 0; i < (amount < currentRobableCmpAmount ? amount : currentRobableCmpAmount); i++) {
                        toAssignComplaintEntity = cmpList.get(i);

                        if(complaintAssignService.assign(toAssignComplaintEntity, assignedUser,AssignType.ROB)) {
                            successfulAmount++;
                        }

                    }
                    
                    logger.info("rob complaints end[userId-" + userId + "][amount-" + amount + "]:success to rob "
                            + (amount < currentRobableCmpAmount ? amount : currentRobableCmpAmount) + " complaints,there are "
                            + (currentRobableCmpAmount - successfulAmount) + " robable complaints left!");
                    
                    resultMap.put("robedAmount", (amount < currentRobableCmpAmount ? amount : currentRobableCmpAmount)); //记录成功抢到的单数
                    resultMap.put("restAmount", (currentRobableCmpAmount - successfulAmount)); //记录剩余可抢单数
                } else {
                    logger.info("rob complaints end[userId-" + userId + "][amount-" + amount + "]: no rotable complaints");
                    throw new RuntimeException("无可抢投诉单");
                }
                
            }

        } else {
            logger.info("rob complaints end[userId-" + userId + "][amount-" + amount
                    + "]: not suitable config for rob complaint(configed at auto assign page and in travle deal depart)");
            throw new RuntimeException("权限不足，请联系系统管理员");
        }

        return resultMap;
    }

    /**
     * 根据自动分单配置构造查询投诉单条件
     * 
     * @param autoAssignEntity
     * @return
     */
    private Map<String, Object> buildCmpCondition(AutoAssignEntity autoAssignEntity) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 基础条件构造
        paramMap.put("state", "1");
        paramMap.put("dealDepart", Constans.IN_TRAVEL);
        int robTimeDuration = DBConfigManager.getConfig("business.robcmp.duration", Integer.class)==null?2:DBConfigManager.getConfig("business.robcmp.duration", Integer.class);
         paramMap.put("lessMinutesAfterBuildTime", robTimeDuration);

        // 投诉级别转换
        List<Integer> levels = new ArrayList<Integer>();
        if(autoAssignEntity.getComplaintLevel1Flag() == 1) {
            levels.add(1);
        }
        if(autoAssignEntity.getComplaintLevel2Flag() == 1) {
            levels.add(2);
        }
        if(autoAssignEntity.getComplaintLevel3Flag() == 1) {
            levels.add(3);
        }

        paramMap.put("levels", levels);

        // 出游中组别配置转换
        Integer touringGroupType = autoAssignEntity.getTouringGroupType();
        paramMap.put("touringGroupType", touringGroupType);

        // 目的地大类配置转换
        Integer othersFlag = autoAssignEntity.getOthersFlag();
        paramMap.put("destCategoryNamesInOrNotFlag", othersFlag); // 为1的时候使用not In 0使用in

        List<String> destCategoryNames = new ArrayList<String>();

        Integer aroundFlag = autoAssignEntity.getAroundFlag();
        Integer inlandLongFlag = autoAssignEntity.getInlandLongFlag();
        Integer abroadShortFlag = autoAssignEntity.getAbroadShortFlag();
        Integer abroadLongFlag = autoAssignEntity.getAbroadLongFlag();
        if(othersFlag == 1) {
            if(aroundFlag == 0) {
                destCategoryNames.add("周边");
            }
            if(inlandLongFlag == 0) {
                destCategoryNames.add("国内长线");
            }
            if(abroadShortFlag == 0) {
                destCategoryNames.add("出境短线");
            }
            if(abroadLongFlag == 0) {
                destCategoryNames.add("出境长线");
            }
        } else {
            if(aroundFlag == 1) {
                destCategoryNames.add("周边");
            }
            if(inlandLongFlag == 1) {
                destCategoryNames.add("国内长线");
            }
            if(abroadShortFlag == 1) {
                destCategoryNames.add("出境短线");
            }
            if(abroadLongFlag == 1) {
                destCategoryNames.add("出境长线");
            }
        }

        paramMap.put("destCategoryNames", destCategoryNames);

        if(destCategoryNames.isEmpty()) {
            paramMap.remove("destCategoryNamesInOrNotFlag");
        }
        
        // 产品品类配置转换
        String productCategorys = autoAssignEntity.getProductCategory();
        if(StringUtils.isNotEmpty(productCategorys)){
            paramMap.put("routeTypeSps",productCategorys.split(","));
        }
        
        //会员星级配置转换
        Integer guestLevel = autoAssignEntity.getGuestLevel();
        if(guestLevel==1){//五星以下
            paramMap.put("guestLevel", "normal");
        }else if(guestLevel==2){
            paramMap.put("guestLevel", "vip");
        }

        return paramMap;
    }

}
