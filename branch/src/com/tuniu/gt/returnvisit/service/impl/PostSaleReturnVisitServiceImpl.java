/**
 * 
 */
package com.tuniu.gt.returnvisit.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.dao.impl.ComplaintReasonDao;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.frm.dao.impl.UserDao;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.returnvisit.dao.impl.PostSaleReturnVisitDao;
import com.tuniu.gt.returnvisit.entity.PostSaleReturnVisitEntity;
import com.tuniu.gt.returnvisit.service.IPostSaleReturnVisitService;
import com.tuniu.gt.returnvisit.vo.ReportResultSetVo;
import com.tuniu.gt.returnvisit.vo.ReturnVisitReportVo;
import com.tuniu.gt.sms.entity.MessageParamEntity;
import com.tuniu.gt.sms.entity.MessageTemplateParamValuePair;
import com.tuniu.gt.uc.dao.impl.DepartmentDao;

/**
 * @author jiangye
 */
@Service("rv_service_impl-postSaleReturnVisit")
public class PostSaleReturnVisitServiceImpl extends ServiceBaseImpl<PostSaleReturnVisitDao> implements
        IPostSaleReturnVisitService {

    private static final Integer PREFIX_SATISFACTION = 1; // 满意度回复前缀
    private static final Integer PREFIX_REASON = 2; // 不满意原因回复前缀

    private static final Integer SATISFACTION_SATISFIED_SCORE = 3;
    private static final Integer SATISFACTION_NORMAL_SCORE = 2;
    private static final Integer SATISFACTION_DISSATISFIED_SCORE = 0;
    
    private static Logger  logger= Logger.getLogger(PostSaleReturnVisitServiceImpl.class);
    
    
    @Autowired
    @Qualifier("complaint_dao_impl-complaint_reason")
    private ComplaintReasonDao complaintReasonDao;
    
    @Autowired
    @Qualifier("uc_dao_impl-department")
    private DepartmentDao departmentDao;
    
    @Autowired
    @Qualifier("frm_dao_impl-user")
    private UserDao userDao;

    @Autowired
    @Qualifier("returnVisit_dao_impl-postSaleReturnVisit")
    public void setDao(PostSaleReturnVisitDao dao) {
        this.dao = dao;
    }


    @Override
    public boolean hasRecordByComplaintIdAndDealDepart(Integer complaintId, String dealDepart) {
        return dao.getCount(complaintId, dealDepart) > 0;
    }

    /**
     * 处理短信平台回调接口
     * 
     * @param tel
     *            回复号码
     * @param receiveTime
     *            接收到回复短信时间
     * @param prefix
     *            回复前缀
     * @param choosedItem
     *            回复选项
     */
    public int dealWithMsgCallBack(String tel, String receiveTime, Integer prefix, Integer choosedItem) {
        int resultCode = 0;
        boolean legal = preCheckParamFromMsgPlatForm(prefix, choosedItem);
        if(legal) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("tel", tel);
            paramMap.put("receiveTime", receiveTime);
            PostSaleReturnVisitEntity entity = new PostSaleReturnVisitEntity();
            Integer id = 0;
            if(PREFIX_SATISFACTION.equals(prefix)) {
                paramMap.put("returnType", "satisfaction");
                id = dao.getIdByParam(paramMap);
                if(id != null && id!=0) {
                    entity.setId(id);
                    Integer score = -1;
                    switch(choosedItem) {
                        case 1: // 满意
                            score = SATISFACTION_SATISFIED_SCORE;
                            break;
                        case 2: // 一般
                            score = SATISFACTION_NORMAL_SCORE;
                            break;
                        case 3: // 不满意
                            score = SATISFACTION_DISSATISFIED_SCORE;
                            break;
                        default:
                            break;
                    }
                    
                    if(score != -1) {
                        entity.setScore(score);
                        entity.setUpdateTime(new Date());
                        dao.update(entity);
                        
                        if(score == SATISFACTION_DISSATISFIED_SCORE){ // 如果回复不满意，则触发不满意原因回访短息
                            MessageParamEntity mpe = new MessageParamEntity();
                            mpe.setTemplateId(MessageParamEntity.TEMPLATE_UNSATISFIED_REASON);
                            PostSaleReturnVisitEntity postSaleReturnVisitEntity = dao.get(id);
                            mpe.setMobileNum(Arrays.asList(new String[]{postSaleReturnVisitEntity.getTel()}));
                            mpe.setOrderId(postSaleReturnVisitEntity.getOrderId());
                            ComplaintRestClient.sendMessageNew(mpe);
                        }
                    }
                }
            } else if(PREFIX_REASON.equals(prefix)) {
                paramMap.put("returnType", "reason");
                id = dao.getIdByParam(paramMap);
                if(id != null && id!=0) {
                    entity.setId(id);
                    entity.setUnsatisfyReason(choosedItem); // 回复不满意原因内容与数据库中字段一致，直接设置
                    entity.setUpdateTime(new Date());
                    dao.update(entity);
                }
            }
        }else{
            resultCode = 240001; // 入参格式错误
        }
        
        return resultCode;
    }

    /**
     * 短信回调参数检查
     * 
     * @param tel
     * @param receiveTime
     * @param prefix
     * @param choosedItem
     */
    private boolean preCheckParamFromMsgPlatForm(Integer prefix, Integer choosedItem) {
        boolean result = true;

        // 回复内容校验(prefix+choosedItem)
        if(choosedItem != null) {
            if(prefix==1) {
                if(!Arrays.asList("1,2,3".split(",")).contains(choosedItem + "")) {
                    result = false;
                }
            } else if(prefix==2) {
                if(!Arrays.asList("1,2,3,4".split(",")).contains(choosedItem + "")) {
                    result = false;
                }
            } else {
                result = false;
            }

        }

        return result;
    }


    @Override
    public void triggerReturnVisit(ComplaintEntity cmpEntity,ComplaintSolutionEntity csEntity) {
        
        try {
            //1.是否需要触发回访检查
            boolean needReturnVisit = needReturnVisit(cmpEntity,csEntity);
            //2.触发短信回访
            if(needReturnVisit) {
                //2.1调用短信接口
                MessageParamEntity messageParamEntity = new MessageParamEntity();
                messageParamEntity.setMobileNum(Arrays.asList(new String[]{cmpEntity.getCmpPhone()}));
                messageParamEntity.setTemplateId(MessageParamEntity.TEMPLATE_SATISFIED);
                messageParamEntity.setOrderId(cmpEntity.getOrderId());
                List<MessageTemplateParamValuePair> smsTemplateParams = new ArrayList<MessageTemplateParamValuePair>();
                MessageTemplateParamValuePair  pair1 = new MessageTemplateParamValuePair("orderId", cmpEntity.getOrderId());
                Integer deal = cmpEntity.getDeal();
                UserEntity dealUser = userDao.get(deal);
                MessageTemplateParamValuePair  pair2 = new MessageTemplateParamValuePair("workNum", dealUser.getExtension());//用分机号作为参数
                smsTemplateParams.add(pair1);
                smsTemplateParams.add(pair2);
                messageParamEntity.setSmsTemplateParams(smsTemplateParams);
                if(ComplaintRestClient.sendMessageNew(messageParamEntity)) { // 2.2发送成功后插入回访列表
                    PostSaleReturnVisitEntity entity = new PostSaleReturnVisitEntity();
                    
                    String dealName = cmpEntity.getDealName();
                    if(StringUtils.isNotEmpty(dealName)) {
                         entity = departmentDao.getFullDepartmentsByUserRealName(cmpEntity.getDealName());
                    }
                    
                    entity.setComplaintId(cmpEntity.getId()); //投诉单id
                    entity.setOrderId(cmpEntity.getOrderId()); //订单id
                    entity.setPrdName(cmpEntity.getRoute()); //线路名称
                    entity.setDealDepart(cmpEntity.getDealDepart()); //处理岗
                    entity.setDealName(cmpEntity.getDealName()); // 投诉处理人
                    entity.setTel(cmpEntity.getCmpPhone()); // 回访电话
                    entity.setReturnVisitDate(new Date()); //回访日期
                    UserEntity dealManagerUser = ComplaintRestClient.getReporter(dealName);
                    entity.setDealManagerName(dealManagerUser.getRealName()); //投诉处理人经理
                    dao.insert(entity);
                }
            }
        } catch(Exception e) {
            logger.error(e.getMessage(),e);
        }
        
    }


    /**
     * [判断是否需要回访]
     * 1. 无订单、赔款单投诉不触发短信 ✔
     * 2.投诉单内增加“关闭回访短信”按钮，关闭后不发送回访短信，只有经理有权限 ✔
     * 3. 每个订单的售后回访短信只触发一次（区分处理岗）  ✔
     * 4.只有低满意度的投诉不触发短信
     * 
     * @param cmpEntity
     * @return
     */
    private boolean needReturnVisit(ComplaintEntity cmpEntity,ComplaintSolutionEntity csEntity) {
        boolean needReturnVisit = true;
        Integer cmpId = cmpEntity.getId();
        StringBuilder loggerMsg = new StringBuilder("回访校验[").append(cmpId).append("]");
        
        String unNeedReason = null;
        if(cmpEntity.getOrderId()==0 || cmpEntity.getIsReparations()==1) { // 无订单、赔款单不触发
            needReturnVisit = false;
            unNeedReason = "无订单或赔款单不触发";
        }else if(cmpEntity.getCmpPhone().trim().length()!=11){ //投诉人电话号码不为11位不触发
            needReturnVisit = false;
            unNeedReason = "投诉人电话不是11位，无法触发";
        }else if(cmpEntity.getReturnVisitSwitch()==0) { // 投诉单回访开关关闭不触发
            needReturnVisit = false;
            unNeedReason = "投诉单回访开关处于关闭状态";
//        }else if(csEntity.getSatisfactionFlag()==0){
//            needReturnVisit = false;
//            unNeedReason = "不满意结单";
        }else {
            if(hasRecordByComplaintIdAndDealDepart(cmpEntity.getId(),cmpEntity.getDealDepart())) { //  每个投诉单的售后回访短信只触发一次（区分处理岗）
                needReturnVisit = false;
                unNeedReason = "当前处理岗只触发一次";
//            }else if(complaintReasonDao.isPureDisSatisfiedComplaint(cmpEntity.getId())){ //只有低满意度的投诉不触发短信
//                needReturnVisit = false;
//                unNeedReason = "只有低满意投诉不触发短信";
            }
        }
        
        if(needReturnVisit){
            logger.info(loggerMsg.append("需要回访"));
        }else{
            logger.info(loggerMsg.append("不需要回访，原因：").append(unNeedReason));
        }
        
        return needReturnVisit;
    }
    
    
    public List<ReturnVisitReportVo> buildReport(Map<String, Object> paramMap){
        String fields = "businessUnitName,departmentName,groupName,dealName";
        paramMap.put("fields", fields);
        List<ReportResultSetVo>  voList = dao.getReport(paramMap);
        
        List<ReturnVisitReportVo> reportVoList = new ArrayList<ReturnVisitReportVo>();
        
        for(ReportResultSetVo reportResultSetVo : voList) {
            String businessUnitName = reportResultSetVo.getBusinessUnitName();
            String departmentName =  reportResultSetVo.getDepartmentName();
            String groupName = reportResultSetVo.getGroupName();
            String dealName = reportResultSetVo.getDealName();
            
            ReturnVisitReportVo topReportVo = getReportVo(businessUnitName,reportVoList);
            ReturnVisitReportVo secondReportVo = getReportVo(departmentName,topReportVo.getChildren());
            ReturnVisitReportVo groupReportVo = getReportVo(groupName,secondReportVo.getChildren());
            ReturnVisitReportVo dealReportVo = getReportVo(dealName,groupReportVo.getChildren());
            dealReportVo.setData(reportResultSetVo.getData());
        }
        
        fields = "businessUnitName,departmentName,groupName";
        paramMap.put("fields", fields);
        List<ReportResultSetVo> groupDatas = dao.getReport(paramMap);
        fields = "businessUnitName,departmentName";
        paramMap.put("fields", fields);
        List<ReportResultSetVo> departmentDatas = dao.getReport(paramMap);
        fields = "businessUnitName";
        paramMap.put("fields", fields);
        List<ReportResultSetVo> businessUnitDatas = dao.getReport(paramMap);
        
        for(ReturnVisitReportVo businessUnitVo : reportVoList) {
            String businessUnitName = businessUnitVo.getName();
            
            for(ReportResultSetVo businessUnitData : businessUnitDatas) {
                if(businessUnitName.equals(businessUnitData.getBusinessUnitName())){
                    businessUnitVo.setData(businessUnitData.getData());
                    break;
                }
            }
            
            List<ReturnVisitReportVo> departmentsVo = businessUnitVo.getChildren();
            for(ReturnVisitReportVo departmentVo : departmentsVo) {
                String departmentName = departmentVo.getName();
                for(ReportResultSetVo departmentData : departmentDatas) {
                    if(businessUnitName.equals(departmentData.getBusinessUnitName())&&departmentName.equals(departmentData.getDepartmentName())){
                        departmentVo.setData(departmentData.getData());
                        break;
                    }
                }
                
                List<ReturnVisitReportVo> groupsVo = departmentVo.getChildren();
                for(ReturnVisitReportVo returnVisitReportVo : groupsVo) {
                    String groupName = returnVisitReportVo.getName();
                    for(ReportResultSetVo groupDada : groupDatas) {
                        if(businessUnitName.equals(groupDada.getBusinessUnitName())&&departmentName.equals(groupDada.getDepartmentName())&&groupName.equals(groupDada.getGroupName())){
                            returnVisitReportVo.setData(groupDada.getData());
                            break;
                        }
                        
                    }
                }
                
            }
        }
        
        return reportVoList;
    }




    /**
     * @param i
     * @param name
     * @param reportVoList 
     * @param groupReportVo 
     * @return
     */
    private ReturnVisitReportVo getReportVo(String name, List<ReturnVisitReportVo> reportVoList) {
        
        if(reportVoList == null){
            reportVoList = new ArrayList<ReturnVisitReportVo>();
        }
        
        ReturnVisitReportVo  reportVo = null;
            for(ReturnVisitReportVo returnVisitReportVo : reportVoList) {
                if(name.equals(returnVisitReportVo.getName())){
                    reportVo = returnVisitReportVo;
                    break;
                }
            }
        
        if(reportVo == null){
            reportVo = new ReturnVisitReportVo();
            reportVo.setName(name);
            reportVoList.add(reportVo);
        }
        
        return reportVo;
    }


    @Override
    public List<ComplaintEntity> getComplaintList(Map<String, Object> paramMap) {
        return dao.getComplaintList(paramMap);
    }

}
