package com.tuniu.gt.complaint.schedule.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.entity.ClaimAuditHistory;
import com.tuniu.gt.complaint.entity.ClaimsAuditEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.ClaimAuditHistoryService;
import com.tuniu.gt.complaint.service.ClaimsAuditService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.frm.dao.impl.UserDao;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.Rtx;
import com.tuniu.gt.toolkit.lang.CollectionUtil;
import com.tuniu.gt.uc.vo.UserDepartmentVo;
import com.tuniu.trest.TRestException;

/**
 *超时自动审批和提醒定时任务
 *@author zhangxian2
 * 
*/
public class AutoAuditofTimoutScheduledTask {
	
	@Autowired
    @Qualifier("complaint_service_complaint_impl-claims_audit")
	private ClaimsAuditService claimsAuditService;
	
	// 引入解决方案service
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_solution")
    private IComplaintSolutionService complaintSolutionService;
    
    //引入赔款审核历史记录service
    @Autowired
    @Qualifier("complaint_service_complaint_impl-claim_audit_history")
    private ClaimAuditHistoryService claimAuditHistoryService;
    
    // 引入跟进记录service
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_follow_note")
    private IComplaintFollowNoteService complaintFollowNoteService;
    
    @Autowired
    @Qualifier("frm_dao_impl-user")
    private UserDao userDao;
    
    @Autowired
    @Qualifier("frm_service_system_impl-user")
    private IUserService userService;
    
    @Autowired
    @Qualifier("tspService")
    private IComplaintTSPService tspService;
	
	/**待审核  0*/
    private  static final int UNTRIAL = 0;
    
    /**已初审  1*/
    private  static final int PASS_FIRST_TRIAL = 1;
    
    /**复审一 2*/
    private  static final int RETRIAL_ONE = 2;
    
    /**复审二 3*/
    private  static final int RETRIAL_TWO = 3;
    
    /**复审三 5*/
    private  static final int RETRIAL_THREE = 5;
    
    /**审核完成 4*/
    private  static final int PASS_TRIAL = 4;
    
    
    private static Logger logger = Logger.getLogger(AutoAuditofTimoutScheduledTask.class);
    
    /**
     * 超时发送rtx提醒
     * @param overTimeHour
     * @throws TRestException 
     */
	public void sendRTX(String overTimeHourStr) throws TRestException{
		
	    // 找出所有超时的对客解决方案（updateTime）
	    int strLen = overTimeHourStr.length();
        String convertStr = overTimeHourStr.substring(1, strLen-1);
        int overTimeHour = Integer.parseInt(convertStr);
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.HOUR, 0 - overTimeHour);
	    Date conditionDate = cal.getTime();
	    paramMap = new HashMap<String, Object>();
	    paramMap.put("audit_flag", "0,1,2,3,5");
	    paramMap.put("updateTime", conditionDate);
	    List<ClaimsAuditEntity> claimsAuditEntities  = claimsAuditService.queryClaimsAuditList(paramMap, "");
	    
	    // 根据对客解决方案及其所属审批阶段查询出所有相关的审批人列表
	    List<String> step1List = DBConfigManager.getConfigAsList("authority.audit.step1");// 初审权限
        List<String> step21List = DBConfigManager.getConfigAsList("authority.audit.step21");//复审一
        List<String> step22List = DBConfigManager.getConfigAsList("authority.audit.step22");//复审二
        Map<Integer, String> step22Map = convertStepListToMap(step22List);
        List<String> step23List = DBConfigManager.getConfigAsList("authority.audit.step23");//复审3
        Map<Integer, String> step23Map = convertStepListToMap(step23List);
        List<String> step3List = DBConfigManager.getConfigAsList("authority.audit.step3");//终审
        Map<Integer, String> step3Map = convertStepListToMap(step3List);        
        
        for(ClaimsAuditEntity claimsAuditEntity : claimsAuditEntities){
            
            // 二次筛选：因为之前的查询结果为share solution和complaint solution的并集，所以此处需要筛选出complaint solution的解决方案
            List<Integer> choosenAuditFlagList = new ArrayList<Integer>();
            choosenAuditFlagList.add(0);
            choosenAuditFlagList.add(1);
            choosenAuditFlagList.add(2);
            choosenAuditFlagList.add(3);
            choosenAuditFlagList.add(5);
            
            if(!choosenAuditFlagList.contains(claimsAuditEntity.getComplaintSolutionEntity().getAuditFlag())){
                continue;
            }
            
            // 三次筛选：过滤掉指定日期的老数据，避免出现影响之前的业务流程
            String autoAuditBeginDateStr = DBConfigManager.getConfig("authority.audit.autoAuditBeginDate", String.class)==null?"":DBConfigManager.getConfig("authority.audit.autoAuditBeginDate", String.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date autoAuditBeginDate = null;
              
            try{
                autoAuditBeginDate = sdf.parse(autoAuditBeginDateStr);
            }
            catch(ParseException e1){
                logger.error("审核超时提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + "时间字符串转换错误", e1);
            }
            
            if(claimsAuditEntity.getComplaintSolutionEntity().getUpdateTime().getTime() < autoAuditBeginDate.getTime()){
                continue;
            }
            int auditFlag = claimsAuditEntity.getComplaintSolutionEntity().getAuditFlag();
            
            // 向相关审批人推送rtx提醒
            String title = "超时赔款审核提醒";
            String content = "有投诉单" + claimsAuditEntity.getComplaintEntity().getId() + "解决方案（" + "[" + Constant.CONFIG.getProperty("app_url").toString() + "complaint/action/claims_audit" + "]" + "）需要审批，请尽快处理！";
            
            // 通过员工id获取该员工基本信息及员工组织架构相关信息
            int dealId = claimsAuditEntity.getComplaintSolutionEntity().getDealId(); // 方案提交人
            UserDepartmentVo userDepartmentVo = userService.getUserDepartmentVoByUserId(dealId);
            Integer departmentId = userDepartmentVo.getDepartmentId(); // 二级部门
            List<Integer> deptIds = new ArrayList<Integer>();
            deptIds.add(departmentId);
            JSONObject deptJson = ComplaintRestClient.queryDepts(deptIds); // 获取二级部门详细信息
            
            // Rtx发送Map
            Map<Integer, String> sendMap = new HashMap<Integer, String>();
            Integer leadId = -1; // 负责人id
            String leadName = ""; // 负责人姓名
           
            
            switch(auditFlag){
                case UNTRIAL:
                    
                    // 找到解决方案提交人所在二级部门下所有三级部门负责人列表
                    String childDeptStr = ""; // 子部门列表字符串
                    String[] childIds = null;
                    List<Integer> childDepts = new ArrayList<Integer>(); // 三级部门
                    List<String> childLeads = new ArrayList<String>(); // 三级部门负责人
                    
                    if ((Boolean) deptJson.get("success")) {
                        JSONObject dataObj = deptJson.getJSONObject("data");
                        JSONObject deptsObj = dataObj.getJSONObject("depts");
                        JSONObject deptObj = deptsObj.getJSONObject(departmentId.toString());
                        childDeptStr = JsonUtil.getStringValue(deptObj, "childIds");
                    }
                    
                    if(!"".equals(childDeptStr)){
                        childIds = childDeptStr.split(",");
                        
                        if(childIds.length > 0){
                            
                            for(int i=0;i<childIds.length;i++){
                                
                                if(departmentId != Integer.parseInt(childIds[i])){
                                    childDepts.add(Integer.parseInt(childIds[i]));
                                }
                            }
                            JSONObject childDeptJson = ComplaintRestClient.queryDepts(childDepts); // 获取三级部门详细信息
                            
                            if ((Boolean) childDeptJson.get("success")) {
                                JSONObject dataObj = childDeptJson.getJSONObject("data");
                                JSONObject deptsObj = dataObj.getJSONObject("depts");
                                
                                for(Integer childDeptId : childDepts){
                                    JSONObject deptObj = deptsObj.getJSONObject(childDeptId.toString());
                                    
                                    try{
                                        leadId = JsonUtil.getIntValue(deptObj, "leadId"); // 三级部门负责人
                                    }
                                    catch(Exception e){
                                        logger.error("审核超时提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + "三级部门负责人为空", e);
                                        continue;
                                    }
                                    childLeads.add(JsonUtil.getStringValue(deptObj, "leadId"));
                                }
                            }
                        }
                    }
                    
                    // 判断三级部门负责人与step1List的交集
                    childLeads.retainAll(step1List);
                    
                    if(childLeads.size() >0){
                        for(String childLeadStr : childLeads){
                            Integer childLead = Integer.parseInt(childLeadStr);
                            leadName = userService.getUserByID(childLead).getUserName();
                            sendMap.put(childLead, leadName);
                        }
                        logger.info("审核超时提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",当前审核状态为" + UNTRIAL + ",审核人员为" + sendMap.toString());
                        Rtx.sendMultiRtx(sendMap, title, content);
                    }
                    break;
                case PASS_FIRST_TRIAL:
                    
                    // 找到解决方案提交人对应的二级部门负责人                    
                    if ((Boolean) deptJson.get("success")) {
                        JSONObject dataObj = deptJson.getJSONObject("data");
                        JSONObject deptsObj = dataObj.getJSONObject("depts");
                        JSONObject deptObj = deptsObj.getJSONObject(departmentId.toString());
                        leadId = JsonUtil.getIntValue(deptObj, "leadId");
                        leadName = JsonUtil.getStringValue(deptObj, "leadName");
                    }
                    
                    // 如果step21List包含leadId，则向他发出提醒
                    if(step21List.contains(leadId.toString())){
                        sendMap.put(leadId, leadName);
                        logger.info("审核超时提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",当前审核状态为" + PASS_FIRST_TRIAL + ",审核人员为" + sendMap.toString());
                        Rtx.sendMultiRtx(sendMap, title, content);
                    }
                    break;
                case RETRIAL_ONE:
                    logger.info("审核超时提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",当前审核状态为" + RETRIAL_ONE + ",审核人员为" + sendMap.toString());
                    Rtx.sendMultiRtx(step22Map, title, content);
                    break;
                case RETRIAL_TWO:
                    logger.info("审核超时提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",当前审核状态为" + RETRIAL_TWO + ",审核人员为" + sendMap.toString());
                    Rtx.sendMultiRtx(step23Map, title, content);
                    break;
                case RETRIAL_THREE:
                    logger.info("审核超时提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",当前审核状态为" + RETRIAL_THREE + ",审核人员为" + sendMap.toString());
                    Rtx.sendMultiRtx(step3Map, title, content);
                    break;
            }
        }
	}
	
	/**
	 * 超时自动审核
	 * @param overTimeHour
	 */
	public void overTimeAutoAudit(String overTimeHourStr){
	    
	    // 找出所有超时的对客解决方案（updateTime）
	    int strLen = overTimeHourStr.length();
	    String convertStr = overTimeHourStr.substring(1, strLen-1);
	    int overTimeHour = Integer.parseInt(convertStr);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 0 - overTimeHour);
        Date conditionDate = cal.getTime();
        paramMap = new HashMap<String, Object>();
        paramMap.put("audit_flag", "0,1,2,3,5");
        paramMap.put("updateTime", conditionDate);
        List<ClaimsAuditEntity> claimsAuditEntities  = claimsAuditService.queryClaimsAuditList(paramMap, "");
        
        // 根据对客解决方案及其所属审批阶段查询出所有相关的审批人列表
        List<String> step1List = DBConfigManager.getConfigAsList("authority.audit.step1");// 初审权限
        List<String> step21List = DBConfigManager.getConfigAsList("authority.audit.step21");//复审一
        List<String> step22List = DBConfigManager.getConfigAsList("authority.audit.step22");//复审二
        List<String> step23List = DBConfigManager.getConfigAsList("authority.audit.step23");//复审3
        List<String> step3List = DBConfigManager.getConfigAsList("authority.audit.step3");//终审
        
        Integer oldAuditFlag = 0;
        
	    // 根据审核金额对上述对客解决方案进行筛选
        for(ClaimsAuditEntity claimsAuditEntity : claimsAuditEntities){
            
            // 二次筛选：因为之前的查询结果为share solution和complaint solution的并集，所以此处需要筛选出complaint solution的解决方案
            List<Integer> choosenAuditFlagList = new ArrayList<Integer>();
            choosenAuditFlagList.add(0);
            choosenAuditFlagList.add(1);
            choosenAuditFlagList.add(2);
            choosenAuditFlagList.add(3);
            choosenAuditFlagList.add(5);
            
            if(!choosenAuditFlagList.contains(claimsAuditEntity.getComplaintSolutionEntity().getAuditFlag())){
                continue;
            }
            
            // 三次筛选：过滤掉指定日期的老数据，避免出现影响之前的业务流程
            String autoAuditBeginDateStr = DBConfigManager.getConfig("authority.audit.autoAuditBeginDate", String.class)==null?"":DBConfigManager.getConfig("authority.audit.autoAuditBeginDate", String.class);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date autoAuditBeginDate = null;
              
            try{
                autoAuditBeginDate = sdf.parse(autoAuditBeginDateStr);
            }
            catch(ParseException e1){
                logger.error("时间字符串转换错误", e1);
            }
            if(claimsAuditEntity.getComplaintSolutionEntity().getUpdateTime().getTime() < autoAuditBeginDate.getTime()){
                continue;
            }
            Map<String,Object> info = new HashMap<String, Object>();
            info.put("success", true);
            info.put("msg", "操作成功");
                
            try{
//                ComplaintSolutionEntity csEntity = claimsAuditEntity.getComplaintSolutionEntity();
                ComplaintSolutionEntity csEntity = complaintSolutionService.getComplaintSolution(claimsAuditEntity.getComplaintEntity().getId());
                double custTotal = csEntity.getCash() + csEntity.getTouristBook();
                
                //重复审核校验
                oldAuditFlag = csEntity.getAuditFlag();
                Integer newAuditFlag = computeAuditFlag(oldAuditFlag, custTotal); // 计算下一步审核状态
                csEntity.setAuditFlag(newAuditFlag);
                
                if(oldAuditFlag == csEntity.getAuditFlag()){
                    info.put("success", false);
                    info.put("msg", "对客方案该环节已由他人审核通过，请勿重复提交！");
                }else if(csEntity.getSubmitFlag()!=1){
                    info.put("success", false);
                    info.put("msg", "对客方案该环节已由他人退回！");
                }else{
                    csEntity.setAuditName("robot");
                    
                    // complaintId补充
//                    csEntity.setComplaintId(claimsAuditEntity.getComplaintEntity().getId());
                    complaintSolutionService.auditComplaintSolution(csEntity,info); // 审核
                    
                    if(((Boolean) info.get("success"))){
                        ClaimAuditHistory auditHistory  = new ClaimAuditHistory();
                        auditHistory.setClaimType(ClaimAuditHistory.COMPLAINT_TYPE);
                        auditHistory.setForeignId(csEntity.getId());
                        auditHistory.setClaimTime(new Date());
                        auditHistory.setAssessor("robot");
                        auditHistory.setPhrase(mapAuditFlagToPhrase(oldAuditFlag));
                        claimAuditHistoryService.insert(auditHistory);
                        String noteContent = "对客解决方案审核通过";
                        
                        if(!"".equals(noteContent)){
                            complaintFollowNoteService.addFollowNote(claimsAuditEntity.getComplaintEntity().getId(), 0, "robot", noteContent,0,"审核");
                            logger.info("overTimeAutoAudit() invoked {complaintId="+claimsAuditEntity.getComplaintEntity().getId()+",operator="+ "robot" +",noteContent="+noteContent+"}");
                        }
                        
                        // 完成审批后向对应未审批人员发送邮件提醒
                        String title = "投诉单"+ csEntity.getComplaintId() +"订单" + csEntity.getOrderId() +"已自动审批通过";
                        String content = "投诉单"+ csEntity.getComplaintId() +"订单" + csEntity.getOrderId() +"赔偿金额" + csEntity.getCash() + "元因超过24小时未处理已自动审批完毕！";
                        
                        // 通过员工id获取该员工基本信息及员工组织架构相关信息
                        int dealId = claimsAuditEntity.getComplaintSolutionEntity().getDealId(); // 方案提交人
                        UserDepartmentVo userDepartmentVo = userService.getUserDepartmentVoByUserId(dealId);
                        Integer departmentId = userDepartmentVo.getDepartmentId(); // 二级部门
                        List<Integer> deptIds = new ArrayList<Integer>();
                        deptIds.add(departmentId);
                        JSONObject deptJson = ComplaintRestClient.queryDepts(deptIds); // 获取二级部门详细信息
                        
                        // Rtx发送Map
                        Integer leadId = -1; // 负责人id
                        
                        // email发送人列表
                        List<String> resEmails = new ArrayList<String>();
                        
                        switch(oldAuditFlag){
                            case UNTRIAL:
                                
                                
                                // 找到解决方案提交人所在二级部门下所有三级部门负责人列表
                                String childDeptStr = ""; // 子部门列表字符串
                                String[] childIds = null;
                                List<Integer> childDepts = new ArrayList<Integer>(); // 三级部门
                                List<String> childLeads = new ArrayList<String>(); // 三级部门负责人
                                
                                if ((Boolean) deptJson.get("success")) {
                                    JSONObject dataObj = deptJson.getJSONObject("data");
                                    JSONObject deptsObj = dataObj.getJSONObject("depts");
                                    JSONObject deptObj = deptsObj.getJSONObject(departmentId.toString());
                                    childDeptStr = JsonUtil.getStringValue(deptObj, "childIds");
                                }
                                
                                if(!"".equals(childDeptStr)){
                                    childIds = childDeptStr.split(",");
                                    
                                    if(childIds.length > 0){
                                        
                                        for(int i=0;i<childIds.length;i++){
                                            
                                            if(departmentId != Integer.parseInt(childIds[i])){
                                                childDepts.add(Integer.parseInt(childIds[i]));
                                            }
                                        }
                                        JSONObject childDeptJson = ComplaintRestClient.queryDepts(childDepts); // 获取三级部门详细信息
                                        
                                        if ((Boolean) childDeptJson.get("success")) {
                                            JSONObject dataObj = childDeptJson.getJSONObject("data");
                                            JSONObject deptsObj = dataObj.getJSONObject("depts");
                                            
                                            for(Integer childDeptId : childDepts){
                                                JSONObject deptObj = deptsObj.getJSONObject(childDeptId.toString());
                                                leadId = JsonUtil.getIntValue(deptObj, "leadId"); // 三级部门负责人
                                                childLeads.add(JsonUtil.getStringValue(deptObj, "leadId"));
                                            }
                                        }
                                    }
                                }
                                
                                // 判断三级部门负责人与step1List的交集
                                childLeads.retainAll(step1List);
                                
                                for(String childLeadStr : childLeads){
                                    Integer childLead = Integer.parseInt(childLeadStr);
                                    resEmails.add(userService.getUserByID(childLead).getEmail());
                                }
                                
                                if(CollectionUtil.isNotEmpty(resEmails)){
                                    tspService.sendMail(new MailerThread(resEmails.toArray(new String[resEmails.size()]), new String[]{""}, title, content));
                                }
                                logger.info("自动审核邮件提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",审核状态为" + RETRIAL_THREE + ",发送人为" + resEmails.toString());
                                break;
                            case PASS_FIRST_TRIAL:
                                
                                // 找到解决方案提交人对应的二级部门负责人                    
                                if ((Boolean) deptJson.get("success")) {
                                    JSONObject dataObj = deptJson.getJSONObject("data");
                                    JSONObject deptsObj = dataObj.getJSONObject("depts");
                                    JSONObject deptObj = deptsObj.getJSONObject(departmentId.toString());
                                    leadId = JsonUtil.getIntValue(deptObj, "leadId");
                                }
                                
                                // 如果step21List包含leadId，则向他发出提醒
                                if(step21List.contains(leadId.toString())){
                                    resEmails.add(userService.getUserByID(leadId).getEmail());
                                }
                                if(CollectionUtil.isNotEmpty(resEmails)){
                                    tspService.sendMail(new MailerThread(resEmails.toArray(new String[resEmails.size()]), new String[]{""}, title, content));
                                }
                                logger.info("自动审核邮件提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",审核状态为" + PASS_FIRST_TRIAL + ",发送人为" + resEmails.toString());
                                break;
                            case RETRIAL_ONE:
                                
                                for(String step22UserIdStr : step22List){
                                    resEmails.add(userService.getUserByID(Integer.parseInt(step22UserIdStr)).getEmail());
                                }
                                
                                if(CollectionUtil.isNotEmpty(resEmails)){
                                    tspService.sendMail(new MailerThread(resEmails.toArray(new String[resEmails.size()]), new String[]{""}, title, content));
                                }
                                logger.info("自动审核邮件提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",审核状态为" + RETRIAL_ONE + ",发送人为" + resEmails.toString());
                                break;
                            case RETRIAL_TWO:

                                for(String step23UserIdStr : step23List){
                                    resEmails.add(userService.getUserByID(Integer.parseInt(step23UserIdStr)).getEmail());
                                }
                                
                                if(CollectionUtil.isNotEmpty(resEmails)){
                                    tspService.sendMail(new MailerThread(resEmails.toArray(new String[resEmails.size()]), new String[]{""}, title, content));
                                }
                                logger.info("自动审核邮件提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",审核状态为" + RETRIAL_TWO + ",发送人为" + resEmails.toString());
                                break;
                            case RETRIAL_THREE:
                                
                                for(String step3UserIdStr : step3List){
                                    resEmails.add(userService.getUserByID(Integer.parseInt(step3UserIdStr)).getEmail());
                                }
                                
                                if(CollectionUtil.isNotEmpty(resEmails)){
                                    tspService.sendMail(new MailerThread(resEmails.toArray(new String[resEmails.size()]), new String[]{""}, title, content));
                                }
                                logger.info("自动审核邮件提醒：投诉id" + claimsAuditEntity.getComplaintEntity().getId() + ",审核状态为" + RETRIAL_THREE + ",发送人为" + resEmails.toString());
                                break;
                        }
                    }
                }
            }catch(Exception e){
                logger.error("自动审核失败，投诉id" + claimsAuditEntity.getComplaintEntity().getId()  + ",当前审核状态为" + oldAuditFlag, e);
                info.put("success", false);
                info.put("msg", "操作失败！");
            }finally{
                logger.info("删除key: ");
            }
        }
	    // 修改上述方案的审批状态
	}
	
	/**
	 * 根据当前审核状态计算下一步审核状态
	 * @param auditFlag
	 * @param total
	 * @return
	 */
    private int computeAuditFlag(int auditFlag, double total) {
            int newAuditFlag = 0;
            switch(auditFlag){
                case UNTRIAL: 
                    
                    if(total <= 5000 && total >= 200){
                        newAuditFlag = PASS_TRIAL;
                    }else{
                        newAuditFlag = auditFlag + 1;
                    }
                    break;
                case PASS_FIRST_TRIAL: 
                    
                    if(total <= 20000){
                        newAuditFlag = PASS_TRIAL;
                    }else{
                        newAuditFlag = auditFlag + 1;
                    }
                    break;
                case RETRIAL_ONE: 
                    
                    if(total <= 100000){
                        newAuditFlag = PASS_TRIAL;
                    }else{
                        newAuditFlag = RETRIAL_THREE;
                    }
                    break;
                case RETRIAL_THREE: 
                    newAuditFlag = PASS_TRIAL;
                    break;
            }
            return newAuditFlag;
        }
    
    public Map<Integer, String> convertStepListToMap(List<String> stepList){
        Map<Integer, String> userMap = new HashMap<Integer, String>();
        
        for(String userIdStr : stepList){
//            String[] stepArr = stepStr.split(" ");
//            Integer userId = Integer.parseInt(stepArr[0]);
//            String userName = stepArr[1];
//            userMap.put(userId, userName);
            Integer userId = Integer.parseInt(userIdStr);
            
            // 根据userId获取到userName
            UserEntity c = (UserEntity) userService.getUserByID(userId);
            userMap.put(userId, c.getUserName());
            logger.info("用户id:" + userId + ";用户名：" + c.getUserName());
        }
        return userMap;
    }
    
    /**
     * @param auditFlag
     * @return
     */
    private Integer mapAuditFlagToPhrase(int auditFlag) {
        Integer phrase = 0 ;
        phrase = auditFlag+1;
        if(auditFlag ==5){
            phrase = auditFlag;
        }
        return phrase;
    }
}
